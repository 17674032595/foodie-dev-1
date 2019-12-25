package com.wuyiccc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author wuyiccc
 * @date 2019/12/25 18:01
 * 岂曰无衣，与子同袍~
 */
@Configuration
public class CorsConfig {

    public CorsConfig() {
    }


    @Bean
    public CorsFilter corsFilter(){  //org.springframework.web.filter.CorsFilter;

        //1.添加cors配置信息
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin("http://localhost:8080");//添加允许的前端地址

        config.setAllowCredentials(true);//允许发送cookie信息

        config.addAllowedMethod("*");//设置允许请求的方式

        config.addAllowedHeader("*");//设置允许的header

        //2.为url添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**",config);//允许访问所有的url

        //3.返回重新定义好的source
        return new CorsFilter(corsSource);
    }
}
