package com.wuyiccc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author wuyiccc
 * @date 2019/12/11 18:26
 * 岂曰无衣，与子同袍~
 */

@SpringBootApplication
//扫描mybatis通用mapper所在的包
@MapperScan(basePackages = "com.wuyiccc.mapper")
//重新定义包扫描的路径，会覆盖springboot默认的包扫描路径
@ComponentScan(basePackages = {"com.wuyiccc","org.n3r.idworker"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
