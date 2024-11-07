package com.shiyulu.talentrank.mapper;

import com.shiyulu.talentrank.pojo.Repo;
import com.shiyulu.talentrank.pojo.ScoreUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TalentRankMapper {
    List<ScoreUser> rankByStaticTopic(List<Integer> topicIds, String nation);

    List<Repo> getReposByUserIdAndTopicIds(long userId, List<Integer> topicIds);
}
