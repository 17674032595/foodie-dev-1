package com.wuyiccc.service.center;

import com.wuyiccc.pojo.OrderItems;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/1/18 10:27
 * 岂曰无衣，与子同袍~
 */
public interface MyCommentsService {

    /**
     * 根据订单id，查询待评价的商品
     * @param orderId
     * @return
     */
    public List<OrderItems> queryPendingComment(String orderId);
}
