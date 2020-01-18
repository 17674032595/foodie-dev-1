package com.wuyiccc.service.impl.center;

import com.wuyiccc.mapper.OrderItemsMapper;
import com.wuyiccc.pojo.OrderItems;
import com.wuyiccc.service.center.MyCommentsService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/1/18 10:30
 * 岂曰无衣，与子同袍~
 */
@Service
public class MyCommentsServiceImpl implements MyCommentsService {


    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {

        OrderItems query = new OrderItems();

        query.setOrderId(orderId);


        return orderItemsMapper.select(query);
    }
}
