package com.shiyulu.talentrank.service;

import com.shiyulu.talentrank.pojo.PageBean;
import com.shiyulu.talentrank.pojo.ScoreUserAndRepos;

public interface UserService {
    ScoreUserAndRepos localSearchUser(Integer page, Integer pageSize, String keyword);

    String aiSummary(String htmlUrl);
}
