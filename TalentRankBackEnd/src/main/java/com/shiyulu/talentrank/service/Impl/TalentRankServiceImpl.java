package com.shiyulu.talentrank.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shiyulu.talentrank.mapper.TalentRankMapper;
import com.shiyulu.talentrank.pojo.*;
import com.shiyulu.talentrank.service.TalentRankService;
import com.shiyulu.talentrank.utils.DouBaoProperties;
import com.shiyulu.talentrank.utils.TopicMap;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class TalentRankServiceImpl implements TalentRankService {
    @Autowired
    private TalentRankMapper talentRankMapper;
    @Autowired
    private DouBaoProperties douBaoProperties;

    @Override
    public ScoreUserAndRepos rankByStaticTopic(Integer page, Integer pageSize, List<Integer> topicIds,
                                               String nation) {
        PageHelper.startPage(page, pageSize);
        List<ScoreUser> developerRankList = talentRankMapper.rankByStaticTopic(topicIds, nation);
        //用PageHelper自带的Page类型对查询结果进行强制转型
        Page<ScoreUser> p = (Page<ScoreUser>) developerRankList;
        //对查询结果进行封装
        ScoreUserAndRepos scoreUserAndRepos = new ScoreUserAndRepos();
        scoreUserAndRepos.setTotal(p.getTotal());
        scoreUserAndRepos.setScoreUserList(p.getResult());
        Map<Integer, List<Repo>> userIdToReposMap = new HashMap<>();
        for (ScoreUser scoreUser : p.getResult()) {
            List<Repo> repoList = talentRankMapper.getReposByUserIdAndTopicIds(scoreUser.getId(), topicIds);
            //放入Map
            userIdToReposMap.put((int) scoreUser.getId(), repoList);
        }
        scoreUserAndRepos.setUserIdToReposMap(userIdToReposMap);
        return scoreUserAndRepos;
    }

    @Override
    public ScoreUserAndRepos rankBySearchString(Integer page, Integer pageSize, String q, String nation) {
        // 创建TopicMap对象
        TopicMap topicMap = new TopicMap();
        // 豆包API所需的参数
        String apiKey = douBaoProperties.getApikey();
        String model = douBaoProperties.getModel();
        String systemContent = douBaoProperties.getSystemcontent();
        String baseUrl = douBaoProperties.getBaseurl();
        ArkService service = ArkService.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .timeout(Duration.ofSeconds(120))
                .connectTimeout(Duration.ofSeconds(20))
                .retryTimes(2)
                .build();
        log.info("============调用豆包API服务============");
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content(systemContent).build();
        ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(q).build();
        messages.add(systemMessage);
        messages.add(userMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(model)
                .messages(messages)
                .build();

        StringBuilder result = new StringBuilder();
        service.createChatCompletion(chatCompletionRequest)
                .getChoices()
                .forEach(choice -> result.append(choice.getMessage().getContent()));
        String answerstr = result.toString();
        log.info("对搜索字符串: {}处理的回答为: {}", q, answerstr);
        if (answerstr.equals("wrong")) {
            // shutdown service
            service.shutdownExecutor();
            return null;
        }
        else {
            // 使用正则表达式匹配中括号内的内容
            Pattern pattern = Pattern.compile("\\[(.*?)\\]");
            Matcher matcher = pattern.matcher(answerstr);
            String content = "";
            // 提取matcher的内容之前，先判断matcher是否有匹配到内容
            if (matcher.find()) {
                // 提取到的内容
                content = matcher.group(1);
                System.out.println(content);
            }
            else {
                // shutdown service
                System.out.println("No match found");
                service.shutdownExecutor();
                return null;
            }
            // 按照', '分隔
            List<String> topicNames = Arrays.asList(content.split(", "));
            List<Integer> topicIds = new ArrayList<>();
            for (String topicName : topicNames) {
                topicIds.add(topicMap.map.get(topicName));
            }

            // 调用rankByStaticTopic方法
            PageHelper.startPage(page, pageSize);
            List<ScoreUser> developerRankList = talentRankMapper.rankByStaticTopic(topicIds, nation);
            //用PageHelper自带的Page类型对查询结果进行强制转型
            Page<ScoreUser> p = (Page<ScoreUser>) developerRankList;
            //对查询结果进行封装
            ScoreUserAndRepos scoreUserAndRepos = new ScoreUserAndRepos();
            scoreUserAndRepos.setTotal(p.getTotal());
            scoreUserAndRepos.setScoreUserList(p.getResult());
            Map<Integer, List<Repo>> userIdToReposMap = new HashMap<>();
            for (ScoreUser scoreUser : p.getResult()) {
                List<Repo> repoList = talentRankMapper.getReposByUserIdAndTopicIds(scoreUser.getId(), topicIds);
                //放入Map
                userIdToReposMap.put((int) scoreUser.getId(), repoList);
            }
            scoreUserAndRepos.setUserIdToReposMap(userIdToReposMap);
            return scoreUserAndRepos;
        }
    }
}
