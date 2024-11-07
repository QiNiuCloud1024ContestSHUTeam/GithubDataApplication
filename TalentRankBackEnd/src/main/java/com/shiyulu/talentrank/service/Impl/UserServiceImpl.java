package com.shiyulu.talentrank.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shiyulu.talentrank.mapper.TalentRankMapper;
import com.shiyulu.talentrank.mapper.UserMapper;
import com.shiyulu.talentrank.pojo.PageBean;
import com.shiyulu.talentrank.pojo.Repo;
import com.shiyulu.talentrank.pojo.ScoreUser;
import com.shiyulu.talentrank.pojo.ScoreUserAndRepos;
import com.shiyulu.talentrank.service.UserService;
import com.shiyulu.talentrank.utils.DouBaoProperties;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionRequest;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionResult;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DouBaoProperties douBaoProperties;

    @Override
    public ScoreUserAndRepos localSearchUser(Integer page, Integer pageSize, String keyword) {
        PageHelper.startPage(page, pageSize);
        // mapper查询结果
        List<ScoreUser> scoreUserList = userMapper.localSearchUser(keyword);
        Page<ScoreUser> p = (Page<ScoreUser>) scoreUserList;
        ScoreUserAndRepos scoreUserAndRepos = new ScoreUserAndRepos();
        scoreUserAndRepos.setTotal(p.getTotal());
        scoreUserAndRepos.setScoreUserList(p.getResult());
        Map<Integer, List<Repo>> userIdToReposMap = new HashMap<>();
        for (ScoreUser scoreUser : p.getResult()) {
            List<Repo> repoList = userMapper.getReposByUserId((int) scoreUser.getId());
            //放入Map
            userIdToReposMap.put((int) scoreUser.getId(), repoList);
        }
        scoreUserAndRepos.setUserIdToReposMap(userIdToReposMap);
        return scoreUserAndRepos;
    }

    @Override
    public String aiSummary(String htmlUrl) {
        // 豆包API所需的参数
        String apiKey = douBaoProperties.getApikey();
        String summarybotmodel = douBaoProperties.getSummarybotmodel();
        String botsystemContent = douBaoProperties.getBotsystemcontent();
        String baseurl = douBaoProperties.getBaseurl();
        String q = htmlUrl + " 这是该用户的github网站个人主页html链接，爬取该链接的内容进行总结，从以下三个方面展开：" +
                "基本信息;主要成就;项目贡献。每个方面为一个段落。";

        // 调用豆包API
        ArkService service = ArkService.builder()
                .apiKey(apiKey)
                .baseUrl(baseurl)
                .timeout(Duration.ofSeconds(120))
                .connectTimeout(Duration.ofSeconds(20))
                .retryTimes(2)
                .build();

        log.info("============调用豆包API服务============");
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content(botsystemContent).build();
        ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(q).build();
        messages.add(systemMessage);
        messages.add(userMessage);

        BotChatCompletionRequest chatCompletionRequest = BotChatCompletionRequest.builder()
                .botId(summarybotmodel)
                .messages(messages)
                .build();

        BotChatCompletionResult chatCompletionResult =  service.createBotChatCompletion(chatCompletionRequest);
        // 使用 StringBuilder 来拼接字符串
        StringBuilder resultString = new StringBuilder();
        chatCompletionResult.getChoices().forEach(
            choice -> {
                resultString.append(choice.getMessage().getContent()).append("\n");
            }
        );
        String summary = resultString.toString();
        log.info("对html_url: {} 进行AI总结的回答为: {}", htmlUrl, summary);
        return summary;
    }
}
