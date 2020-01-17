package com.wuyiccc.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wuyiccc
 * @date 2020/1/16 14:34
 * 岂曰无衣，与子同袍~
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    //实现静态资源的映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")//映射所有资源
                .addResourceLocations("file:/code_learn/java_structure_mooc_bus/data/")//映射本地静态资源
                .addResourceLocations("classpath:/META-INF/resources/"); //swagger2 的生成的html的地址


    }

    @Bean// key 默认为当前方法名称
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}
