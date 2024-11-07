package com.shiyulu.talentrank.mapper;

import com.shiyulu.talentrank.pojo.Repo;
import com.shiyulu.talentrank.pojo.ScoreUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<ScoreUser> localSearchUser(String keyword);

    List<Repo> getReposByUserId(int userId);
}
