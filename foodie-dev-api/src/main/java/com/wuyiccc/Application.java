package com.wuyiccc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@EnableScheduling  //开启定时任务
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
