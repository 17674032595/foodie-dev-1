package com.wuyiccc.controller;

import com.wuyiccc.pojo.Users;
import com.wuyiccc.pojo.bo.UserBO;
import com.wuyiccc.service.UserService;
import com.wuyiccc.utils.CookieUtils;
import com.wuyiccc.utils.JsonUtils;
import com.wuyiccc.utils.MD5Utils;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuyiccc
 * @date 2019/12/23 17:58
 * 岂曰无衣，与子同袍~
 */
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport") // 不加/ 代表是相对路径 加/代表是绝对路径
public class PassportController {

    @Autowired
    private UserService userService;


    /**
     * 判断用户名是否存在，提供给前端的api接口i
     *
     * @param username
     * @return
     */
    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public WUYICCCJSONResult usernameExist(@RequestParam String username) {


        //1.判断用户名不能为空
        if (StringUtils.isBlank(username)) {//判断是否是null和空字符串 org.apache.commons.lang3.StringUtils
            return WUYICCCJSONResult.errorMsg("用户名不能为空");
        }

        //2.查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameExist(username);

        if (isExist) {//如果用户名已经存在，那么返回500
            return WUYICCCJSONResult.errorMsg("用户名已经存在");
        }


        //请求成功，用户名没有重复
        return WUYICCCJSONResult.ok();

    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public WUYICCCJSONResult regist(@RequestBody UserBO userBO,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        //0.判断用户名，密码，确认密码是否为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            return WUYICCCJSONResult.errorMsg("用户名或密码不能为空");
        }

        //1.判断用户名是否存在 : 这里判断是为了防止有人绕过前端逻辑，直接进行api接口的访问
        boolean isExist = userService.queryUsernameExist(username);
        if (isExist) {
            return WUYICCCJSONResult.errorMsg("用户名已经存在");
        }

        //2.判断密码是否>=6位
        if (password.length() < 6) {
            return WUYICCCJSONResult.errorMsg("密码长度不能少于6位");
        }


        //3.判断密码与确认密码是否一致
        if (!password.equals(confirmPassword)) {
            return WUYICCCJSONResult.errorMsg("两次密码输入不一致");
        }


        //4.实现注册
        Users userResult = userService.createUser(userBO);


        userResult = setNullProperty(userResult);

        //设置cookie
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);//使用cookie与json工具类+与加密isEncode，键要写user，因为前端获取的时候是user


        //TODO 生成用户token，存入redis会话

        //TODO 同步购物车数据

        return WUYICCCJSONResult.ok();


    }


    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public WUYICCCJSONResult login(@RequestBody UserBO userBO,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return WUYICCCJSONResult.errorMsg("用户名或密码不能为空");
        }

        Users userResult = userService.queryUserForLogin(username,
                MD5Utils.getMD5Str(password));

        if (userResult == null) {
            return WUYICCCJSONResult.errorMsg("用户名或者密码不正确");
        }
        userResult = setNullProperty(userResult);

        //设置cookie
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);//使用cookie与json工具类+与加密isEncode，键要写user，因为前端获取的时候是user

        //TODO 生成用户token，存入redis会话

        //TODO 同步购物车数据


        return WUYICCCJSONResult.ok(userResult);
    }


    /**
     * 将users的部分属性设置为空
     *
     * @param userResult
     * @return
     */
    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

    @ApiOperation(value = "用户退出登录",notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public WUYICCCJSONResult logout(@RequestParam String userId,
                                    HttpServletRequest request,
                                    HttpServletResponse response
                                    ){

        //清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request,response,"user");

        //TODO 用户登录需要清空购物车

        //TODO 分布式会话中需要清除分布式数据


        return WUYICCCJSONResult.ok();
    }





}
