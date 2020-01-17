package com.wuyiccc.controller.center;

import com.wuyiccc.pojo.Users;
import com.wuyiccc.service.center.CenterUserService;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyiccc
 * @date 2020/1/17 9:20
 * 岂曰无衣，与子同袍~
 */

@Api(value = "center-用户中心",tags = {"用户中心展示的相关接口"})
@RestController
@RequestMapping("center")
public class CenterController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息",notes = "获取用户信息",httpMethod = "GET")
    @GetMapping("/userInfo")
    public WUYICCCJSONResult userInfo(
            @ApiParam(name = "userId",value = "用户id",required = true)
            @RequestParam String userId
    ){
        if(StringUtils.isBlank(userId)){
            return WUYICCCJSONResult.errorMsg("用户id不能为空");
        }

        Users user = centerUserService.queryUserInfo(userId);

        return WUYICCCJSONResult.ok(user);

    }


}
