package com.wuyiccc.controller;

import com.wuyiccc.enums.OrderStatusEnum;
import com.wuyiccc.enums.PayMethod;
import com.wuyiccc.pojo.bo.SubmitOrderBO;
import com.wuyiccc.pojo.vo.MerchantOrderVO;
import com.wuyiccc.pojo.vo.OrderVO;
import com.wuyiccc.service.OrderService;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuyiccc
 * @date 2020/1/15 7:52
 * 岂曰无衣，与子同袍~
 */
@Api(value = "订单相关",tags = {"订单相关接口"})
@RequestMapping("orders")
@RestController
public class OrdersController extends BaseController{

    @Autowired
    private OrderService orderService;


    @ApiOperation(value = "用户下单",notes = "用户下单",httpMethod = "POST")
    @PostMapping("/create")
    public WUYICCCJSONResult create(
            @ApiParam(name = "submitOrderBO",value = "提交订单BO对象",required = true)
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request,
            HttpServletResponse response
            ){

        if(submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type &&
            submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type
        ){
            return WUYICCCJSONResult.errorMsg("支付方式不支持");

        }

        System.out.println(submitOrderBO);


        // 1.创建订单
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();
        MerchantOrderVO merchantOrderVO = orderVO.getMerchantOrderVO();
        merchantOrderVO.setReturnUrl(payReturnUrl); // 将返回url存入商户订单


        // 2.创建订单以后，移除购物车中已结算(已提交)的商品
        // TODO 整合redis之后，完善购物车中的已结算商品清楚，并且同步到前端cookie
       // CookieUtils.setCookie(request,response,FOODIE_SHOPCART,"",true);//重置cookie



        // 3.向支付中心发送当前订单，用于保存支付中心的订单数据


        return WUYICCCJSONResult.ok(orderId);
    }


    /**
     * 被支付中心调用该接口
     * @param merchantOrderId
     * @return
     */
    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {

        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);

        return HttpStatus.OK.value(); // 返回http状态码 200
    }


}
