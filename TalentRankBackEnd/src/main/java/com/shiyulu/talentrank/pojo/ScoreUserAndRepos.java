package com.shiyulu.talentrank.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreUserAndRepos {
    private Long total;
    private List<ScoreUser> scoreUserList;
    private Map<Integer, List<Repo>> userIdToReposMap;
}
