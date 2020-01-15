package com.wuyiccc.controller;

import com.wuyiccc.enums.PayMethod;
import com.wuyiccc.pojo.bo.SubmitOrderBO;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyiccc
 * @date 2020/1/15 7:52
 * 岂曰无衣，与子同袍~
 */
@Api(value = "订单相关",tags = {"订单相关接口"})
@RequestMapping("orders")
@RestController
public class OrdersController {


    @ApiOperation(value = "用户下单",notes = "用户下单",httpMethod = "POST")
    @PostMapping("/create")
    public WUYICCCJSONResult create(
            @ApiParam(name = "submitOrderBO",value = "提交订单BO对象",required = true)
            @RequestBody SubmitOrderBO submitOrderBO
            ){

        if(submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type &&
            submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type
        ){
            return WUYICCCJSONResult.errorMsg("支付方式不支持");

        }

        System.out.println(submitOrderBO);

        // 1.创建订单



        // 2.创建订单以后，移除购物车中已结算(已提交)的商品

        // 3.向支付中心发生当前订单，用于保存支付中心的订单数据

        return WUYICCCJSONResult.ok();
    }

}
