package com.wuyiccc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wuyiccc
 * @date 2019/12/24 13:43
 * 岂曰无衣，与子同袍~
 */

@Configuration   //表示这是一个配置类，能让springboot扫描到
@EnableSwagger2  //开启swagger2
public class Swagger2 {


    //官方的默认路径是 http://localhost:port/swagger-ui.html
    //换肤之后的路径(bootstrap)  http://localhost:port/doc.html
    //配置swagger2核心配置 docket
    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)//指定api文档类型为swagger2
                .apiInfo(apiInfo())                 //定义api文档汇总信息
                .select().apis(RequestHandlerSelectors
                        .basePackage("com.wuyiccc.controller"))//指定controller包
                .paths(PathSelectors.any())  //所有controller
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("foodie 电商平台接口api")
                .contact(new Contact("wuyiccc",
                        "https://www.wuyiccc.com",
                        "3406324191@qq.com"))//联系人信息
                .description("专为foodie电商提供的api文档")//详细信息
                .version("1.0.1")  //文档版本号
                .termsOfServiceUrl("https://www.wuyiccc.com") //电商网站地址
                .build();
    }

}
