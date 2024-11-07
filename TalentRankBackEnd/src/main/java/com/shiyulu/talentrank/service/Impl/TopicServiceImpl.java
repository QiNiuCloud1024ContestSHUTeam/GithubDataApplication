package com.shiyulu.talentrank.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shiyulu.talentrank.mapper.TopicMapper;
import com.shiyulu.talentrank.pojo.PageBean;
import com.shiyulu.talentrank.pojo.Topic;
import com.shiyulu.talentrank.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicMapper topicMapper;

    @Override
    public PageBean listTopic(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Topic> topicList = topicMapper.listTopic();
        Page<Topic> p = (Page<Topic>) topicList;
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }
}
