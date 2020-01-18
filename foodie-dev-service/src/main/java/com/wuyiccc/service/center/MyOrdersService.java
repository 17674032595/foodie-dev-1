package com.wuyiccc.service.center;

import com.wuyiccc.pojo.Orders;
import com.wuyiccc.utils.PagedGridResult;

/**
 * @author wuyiccc
 * @date 2020/1/17 21:56
 * 岂曰无衣，与子同袍~
 */
public interface MyOrdersService {

    /**
     * 查询我的订单列表
     *
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);


    /**
     * 把订单状态变更为商家发货
     *
     * @param orderId
     */
    public void updateDeliverOrderStatus(String orderId);

    /**
     * 根据用户id和订单id，查询订单
     *
     * @param userId
     * @param orderId
     * @return
     */
    public Orders queryMyOrder(String userId, String orderId);

    /**
     * 将订单状态更新为已收货
     *
     * @param orderId
     * @return
     */
    public boolean updateReceiveOrderStatus(String orderId);


    /**
     * 逻辑删除订单
     * @param userId
     * @param orderId
     * @return
     */
    public boolean deleteOrder(String userId, String orderId);
}
