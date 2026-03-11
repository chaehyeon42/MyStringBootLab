package com.rookies5.myspringbootLab;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "myprop") // application.properties의 myprop으로 시작하는 설정을 매핑
public class MyPropProperties {
    private String username;
    private int port;
}