package com.wuyiccc.service;

import com.wuyiccc.pojo.OrderStatus;
import com.wuyiccc.pojo.bo.SubmitOrderBO;
import com.wuyiccc.pojo.vo.OrderVO;

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
    public OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    public void updateOrderStatus(String orderId,Integer orderStatus);

    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
    public OrderStatus queryOrderStatusInfo(String orderId);
}
