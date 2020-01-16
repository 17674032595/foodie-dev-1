package com.wuyiccc.controller;

import com.wuyiccc.enums.OrderStatusEnum;
import com.wuyiccc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyiccc
 * @date 2020/1/16 20:19
 * 岂曰无衣，与子同袍~
 */
@RequestMapping("payment")
@RestController
public class PaymentController {


    @Autowired
    private OrderService orderService;

    @PostMapping("/okPayAboutWeiXin")
    public String okPayAboutWeiXin(String merchantOrderId){

        // 直接修改对应订单的状态为已支付:foodie-dev服务---内部逻辑
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);

        return "恭喜您,支付成功!";

    }


}
