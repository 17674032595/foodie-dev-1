package com.wuyiccc.controller;

import com.wuyiccc.service.UserService;
import com.wuyiccc.utils.WUYICCCJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyiccc
 * @date 2019/12/23 17:58
 * 岂曰无衣，与子同袍~
 */

@RestController
@RequestMapping("password") // 不加/ 代表是相对路径 加/代表是绝对路径
public class PasswordController {

    @Autowired
    private UserService userService;


    @GetMapping("/usernameIsExist")
    public WUYICCCJSONResult usernameExist(@RequestParam String username){


        //1.判断用户名不能为空
        if(StringUtils.isBlank(username)){//判断是否是null和空字符串 org.apache.commons.lang3.StringUtils
            return WUYICCCJSONResult.errorMsg("用户名不能为空");
        }

        //2.查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameExist(username);

        if(isExist){//如果用户名已经存在，那么返回500
            return WUYICCCJSONResult.errorMsg("用户名已经存在");
        }


        //请求成功，用户名没有重复
        return WUYICCCJSONResult.ok();



    }

}
