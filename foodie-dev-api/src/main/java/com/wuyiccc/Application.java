package com.wuyiccc;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author wuyiccc
 * @date 2019/12/11 18:26
 * 岂曰无衣，与子同袍~
 */

@SpringBootApplication
//扫描mybatis通用mapper所在的包
@MapperScan(basePackages = "com.wuyiccc.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
