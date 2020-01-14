package com.wuyiccc.controller;

import com.wuyiccc.pojo.UserAddress;
import com.wuyiccc.service.AddressService;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/1/14 14:52
 * 岂曰无衣，与子同袍~
 */
@Api(value = "收货地址相关接口",tags = {"收货地址相关接口"})
@RestController
@RequestMapping("address")
public class AddressController {
    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作
     * 1.查询用户的所有收货地址列表
     * 2.新增收货地址
     * 3.删除收货地址
     * 4.修改收货地址
     * 5.设置默认地址
     */

    @Autowired
    private AddressService addressService;

    @PostMapping("/list")
    @ApiOperation(value = "根据用户id查询用户地址列表",notes = "根据用户id查询用户地址列表",httpMethod = "POST")
    public WUYICCCJSONResult list(
            @ApiParam(name = "userId",value = "用户id",required = true)
            @RequestParam String userId
    ){


        if(StringUtils.isBlank(userId)){
            return WUYICCCJSONResult.errorMsg("请输入用户id");
        }

        List<UserAddress> userAddressList = addressService.queryAll(userId);

        return WUYICCCJSONResult.ok(userAddressList);
    }

}
