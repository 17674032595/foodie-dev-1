package com.wuyiccc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyiccc
 * @date 2019/12/11 18:29
 * 岂曰无衣，与子同袍~
 */

//与@Controller类似，不过返回的是json对象
//@Controller
@RestController
public class HelloController {

    //@GetMapping：设置请求地址
    @GetMapping("/hello")
    public Object hello(){
        return "Hello world~";
    }

}
