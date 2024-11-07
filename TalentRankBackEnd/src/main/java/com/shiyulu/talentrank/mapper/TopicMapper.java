package com.shiyulu.talentrank.mapper;

import com.shiyulu.talentrank.pojo.Topic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TopicMapper {
    List<Topic> listTopic();
}
