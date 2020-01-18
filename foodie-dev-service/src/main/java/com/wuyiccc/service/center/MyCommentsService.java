package com.wuyiccc.service.center;

import com.wuyiccc.pojo.OrderItems;
import com.wuyiccc.pojo.bo.center.OrderItemsCommentBO;
import com.wuyiccc.utils.PagedGridResult;

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

    /**
     * 保存用户对商品的评价
     * @param orderId
     * @param userId
     * @param commentList
     */
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);


    /**
     * 我的评价查询分页
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryMyComments(String userId,Integer page,Integer pageSize);


}
