package com.wuyiccc.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author wuyiccc
 * @date 2020/1/16 14:34
 * 岂曰无衣，与子同袍~
 */

@Configuration
public class WebMvcConfig {

    @Bean// key 默认为当前方法名称
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}
