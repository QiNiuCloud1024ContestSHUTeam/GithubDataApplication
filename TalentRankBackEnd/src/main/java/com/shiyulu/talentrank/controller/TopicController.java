package com.shiyulu.talentrank.controller;

import com.shiyulu.talentrank.pojo.PageBean;
import com.shiyulu.talentrank.pojo.Result;
import com.shiyulu.talentrank.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;

    // 查询全部的话题信息，用于搜索框下方的领域展示
    @GetMapping("/listTopic")
    public Result listTopic(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "16") Integer pageSize) {
        PageBean topicList = topicService.listTopic(page, pageSize);
        log.info("topicList请求成功, 参数为: page:{}, pageSize:{}", page, pageSize);
        return Result.success(topicList);
    }
}
