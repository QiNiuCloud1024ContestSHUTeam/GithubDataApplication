package com.shiyulu.talentrank.controller;

import com.shiyulu.talentrank.pojo.PageBean;
import com.shiyulu.talentrank.pojo.Result;
import com.shiyulu.talentrank.pojo.ScoreUserAndRepos;
import com.shiyulu.talentrank.service.TalentRankService;
import com.volcengine.ark.runtime.service.ArkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.ProviderNotFoundException;
import java.time.Duration;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rank")
public class TalentRankController {
    @Autowired
    private TalentRankService talentRankService;

    @GetMapping("/rankByStaticTopic")
    public Result rankByStaticTopic(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(required = true) List<Integer> topicIds,
                                    @RequestParam(required = false) String nation) {
        ScoreUserAndRepos scoreUserAndRepos = talentRankService.rankByStaticTopic(page, pageSize, topicIds, nation);
        log.info("选取静态topic进行排名成功, 参数为: page:{}, pageSize:{}", page, pageSize);
        return Result.success(scoreUserAndRepos);
    }

    @GetMapping("rankBySearchString")
    public Result rankBySearchString(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(required = true) String q,
                                     @RequestParam(required = false) String nation) {
        ScoreUserAndRepos scoreUserAndRepos = talentRankService.rankBySearchString(page, pageSize, q, nation);
        log.info("根据自定义搜索字符串进行排名成功, 参数为: page:{}, pageSize:{}, q:{}", page, pageSize, q);
        return Result.success(scoreUserAndRepos);
    }
}
