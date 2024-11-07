package com.shiyulu.talentrank.service;

import com.shiyulu.talentrank.pojo.PageBean;

public interface TopicService {
    PageBean listTopic(Integer page, Integer pageSize);
}
