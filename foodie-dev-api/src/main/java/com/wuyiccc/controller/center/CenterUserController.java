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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            @ApiParam(name = "userId",value = "用户id",required = true)
            @RequestParam String userId,
            @RequestBody CenterUserBO centerUserBO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        Users user = centerUserService.updateUserInfo(userId, centerUserBO);
        setNullProperty(user);
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(user),true);

        //TODO 后续要改，增加令牌token，会整合进redis，分布式会话


        return WUYICCCJSONResult.ok();
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
