package com.shiyulu.talentrank.service;

import com.shiyulu.talentrank.pojo.PageBean;
import com.shiyulu.talentrank.pojo.ScoreUserAndRepos;

import java.util.List;

public interface TalentRankService {
    ScoreUserAndRepos rankByStaticTopic(Integer page, Integer pageSize, List<Integer> topicIds, String nation);

    ScoreUserAndRepos rankBySearchString(Integer page, Integer pageSize, String q, String nation);
}
