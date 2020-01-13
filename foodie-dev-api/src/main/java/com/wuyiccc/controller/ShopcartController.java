package com.wuyiccc.controller;

import com.wuyiccc.pojo.bo.ShopcartBO;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuyiccc
 * @date 2020/1/13 16:28
 * 岂曰无衣，与子同袍~
 */
@Api(value = "购物车接口controller", tags = {"购物车接口相关api"})
@RestController
@RequestMapping("shopcart")
public class ShopcartController {

    @PostMapping("/add")
    @ApiOperation(value = "添加商品到购物车",notes = "添加商品到购物车",httpMethod = "POST")
    public WUYICCCJSONResult add(
            @RequestParam String userId,
            @RequestBody ShopcartBO shopcartBO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        if (StringUtils.isBlank(userId)){
            return WUYICCCJSONResult.errorMsg("用户id不能为空");
        }

        System.out.println(shopcartBO);

        //TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        return  WUYICCCJSONResult.ok();
    }


}
