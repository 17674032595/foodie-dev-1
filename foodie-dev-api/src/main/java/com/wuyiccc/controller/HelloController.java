package com.wuyiccc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author wuyiccc
 * @date 2019/12/11 18:29
 * 岂曰无衣，与子同袍~
 */

//与@Controller类似，不过返回的是json对象
//@Controller
@ApiIgnore //生成文档时，忽略该controller
@RestController
public class HelloController {

    //@GetMapping：设置请求地址
    @GetMapping("/hello")
    public Object hello(){
        return "Hello world~";
    }


    @GetMapping("/setSession")
    public Object setSession(HttpServletRequest request){
        HttpSession session =  request.getSession();
        session.setAttribute("userInfo","new user");
        session.setMaxInactiveInterval(3600); //设置过期时间 0代表永不过时
        session.getAttribute("userInfo");
   //     session.removeAttribute("userInfo");
        return "ok";
    }

}
