package com.shiyulu.talentrank.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ticktok.doubao")
@Data
public class DouBaoProperties {
    private String apikey;
    private String model;
    private String baseurl;
    private String systemcontent;
    private String summarybotmodel;
    private String botsystemcontent;
}
