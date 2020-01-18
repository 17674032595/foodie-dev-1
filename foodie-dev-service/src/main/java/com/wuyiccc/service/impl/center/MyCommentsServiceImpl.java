package com.wuyiccc.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.wuyiccc.enums.YesOrNo;
import com.wuyiccc.mapper.ItemsCommentsMapperCustom;
import com.wuyiccc.mapper.OrderItemsMapper;
import com.wuyiccc.mapper.OrderStatusMapper;
import com.wuyiccc.mapper.OrdersMapper;
import com.wuyiccc.pojo.OrderItems;
import com.wuyiccc.pojo.OrderStatus;
import com.wuyiccc.pojo.Orders;
import com.wuyiccc.pojo.bo.center.OrderItemsCommentBO;
import com.wuyiccc.pojo.vo.MyCommentVO;
import com.wuyiccc.service.center.MyCommentsService;
import com.wuyiccc.utils.PagedGridResult;
import org.aspectj.weaver.ast.Or;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuyiccc
 * @date 2020/1/18 10:30
 * 岂曰无衣，与子同袍~
 */
@Service
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {


    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    @Autowired
    private Sid sid;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {

        OrderItems query = new OrderItems();

        query.setOrderId(orderId);


        return orderItemsMapper.select(query);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {

        // 保存评价
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }

        Map<String, Object> map = new HashMap<>();

        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsMapperCustom.saveComments(map);


        // 修改订单表改已评价
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(order);

        // 修改订单状态表的留言时间
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {


        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);

        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);

        PagedGridResult pagedGridResult = setterPagedGrid(list, page);


        return pagedGridResult;
    }


}
