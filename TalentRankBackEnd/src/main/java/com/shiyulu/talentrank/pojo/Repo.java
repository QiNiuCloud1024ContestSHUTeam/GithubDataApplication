package com.shiyulu.talentrank.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repo {
    private long id;
    private String nodeId;
    private String name;
    private String fullName;
    private boolean privateFlag;
    private String htmlUrl;
    private String description;
    private boolean fork;
    private String url;
    private String languagesUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pushedAt;
    private String gitUrl;
    private String sshUrl;
    private String cloneUrl;
    private String homepage;
    private int stargazersCount;
    private String language;
    private boolean hasIssues;
    private int forksCount;
    private int openIssuesCount;
    private boolean allowForking;
    private String visibility;
    private double score;
}
