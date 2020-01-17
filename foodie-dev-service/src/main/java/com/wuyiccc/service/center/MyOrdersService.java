package com.wuyiccc.service.center;

import com.wuyiccc.utils.PagedGridResult;

/**
 * @author wuyiccc
 * @date 2020/1/17 21:56
 * 岂曰无衣，与子同袍~
 */
public interface MyOrdersService {

    /**
     * 查询我的订单列表
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryMyOrders(String userId,Integer orderStatus,Integer page,Integer pageSize);
}
