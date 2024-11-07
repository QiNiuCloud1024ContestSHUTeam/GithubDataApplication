package com.shiyulu.talentrank.controller;

import com.shiyulu.talentrank.pojo.Result;
import com.shiyulu.talentrank.pojo.ScoreUserAndRepos;
import com.shiyulu.talentrank.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/localSearch")
    public Result localSearchUser(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "16") Integer pageSize,
                                  @RequestParam(required = true) String keyword) {
        ScoreUserAndRepos scoreUserAndRepos = userService.localSearchUser(page, pageSize, keyword);
        log.info("localSearchUser请求成功, 参数为: page:{}, pageSize:{}, keyword:{}", page, pageSize, keyword);
        return Result.success(scoreUserAndRepos);
    }

    //todo 用户详情页的AI总结接口
    @GetMapping("/aiSummary")
    public Result aiSummary(@RequestParam(required = true) String html_url) {
        System.out.println(html_url);
        String summary = userService.aiSummary(html_url);
        log.info("aiSummary请求成功, 参数为: html_url:{}", html_url);
        return Result.success(summary);
    }

}
