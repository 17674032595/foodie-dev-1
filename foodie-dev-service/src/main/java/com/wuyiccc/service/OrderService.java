package com.wuyiccc.service;

import com.wuyiccc.pojo.bo.SubmitOrderBO;

/**
 * @author wuyiccc
 * @date 2020/1/15 9:53
 * 岂曰无衣，与子同袍~
 */
public interface OrderService {

    /**
     * 创建订单
     * @param submitOrderBO
     */
    public String createOrder(SubmitOrderBO submitOrderBO);
}
