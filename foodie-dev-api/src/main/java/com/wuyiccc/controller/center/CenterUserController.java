package com.wuyiccc.controller.center;

import com.wuyiccc.pojo.Users;
import com.wuyiccc.pojo.bo.center.CenterUserBO;
import com.wuyiccc.service.UserService;
import com.wuyiccc.service.center.CenterUserService;
import com.wuyiccc.utils.CookieUtils;
import com.wuyiccc.utils.JsonUtils;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuyiccc
 * @date 2020/1/17 9:58
 * 岂曰无衣，与子同袍~
 */

@Api(value = "用户信息相关接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController {


    @Autowired
    private CenterUserService centerUserService;


    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("/update")
    public WUYICCCJSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestBody @Valid CenterUserBO centerUserBO,
            BindingResult result, //存放centerUserBO验证失败的错误信息
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (result.hasErrors()) { //判断是否包含有错误的验证信息
            Map<String, String> errorMap = getErrors(result);
            return WUYICCCJSONResult.errorMap(errorMap);
        }

        if (StringUtils.isBlank(userId)) {
            return WUYICCCJSONResult.errorMsg("请输入用户id");
        }

        Users user = centerUserService.updateUserInfo(userId, centerUserBO);
        setNullProperty(user);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);

        //TODO 后续要改，增加令牌token，会整合进redis，分布式会话


        return WUYICCCJSONResult.ok();
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();//错误信息列表
        for (FieldError error : errorList) { //循环每一个错误
            String errorField = error.getField();//发生错误所对应的属性
            String errorMsg = error.getDefaultMessage(); // 获取错误内容
            map.put(errorField,errorMsg); //将错误信息存入map中
        }
        return map;

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

}
