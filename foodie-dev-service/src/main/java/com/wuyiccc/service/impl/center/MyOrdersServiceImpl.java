package com.wuyiccc.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuyiccc.enums.OrderStatusEnum;
import com.wuyiccc.enums.YesOrNo;
import com.wuyiccc.mapper.OrderStatusMapper;
import com.wuyiccc.mapper.OrdersMapper;
import com.wuyiccc.mapper.OrdersMapperCustom;
import com.wuyiccc.pojo.OrderStatus;
import com.wuyiccc.pojo.Orders;
import com.wuyiccc.pojo.vo.MyOrdersVO;
import com.wuyiccc.service.center.MyOrdersService;
import com.wuyiccc.utils.PagedGridResult;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.annotation.Order;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuyiccc
 * @date 2020/1/17 21:57
 * 岂曰无衣，与子同袍~
 */
@Service
public class MyOrdersServiceImpl implements MyOrdersService {


    @Autowired
    private OrdersMapperCustom ordersMapperCustom;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    //提供通用化的分页方法
    private PagedGridResult setterPagedGrid(List<?> list, Integer page) {

        PageInfo<?> pageList = new PageInfo<>(list);  // 将分页之后的数据交给com.github.pagehelper.PageInfo对象，用来获取当前页的信息

        // 为了和前端的数据名称对应，我们这里需要更改数据对应的key，进行如下变化
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);   // 将当前页index 交给grid对象
        grid.setRows(list);   // 将数据信息交给grid 对象
        grid.setTotal(pageList.getPages());  // 通过PageInfo 来获取页面的总页数，交给grid
        grid.setRecords(pageList.getTotal());  // 通过PageInfo 来获取当前页面的总记录条数，交给grid
        return grid;

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();

        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }

        PageHelper.startPage(page, pageSize);

        List<MyOrdersVO> list = ordersMapperCustom.queryMyOrders(map);
        return setterPagedGrid(list, page);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateDeliverOrderStatus(String orderId) {

        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);

        orderStatusMapper.updateByExampleSelective(updateOrder, example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Orders queryMyOrder(String userId, String orderId) {

        Orders order = new Orders();
        order.setUserId(userId);
        order.setId(orderId);

        order.setIsDelete(YesOrNo.NO.type);

        return ordersMapper.selectOne(order);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {

        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        updateOrder.setSuccessTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);

        int result = orderStatusMapper.updateByExampleSelective(updateOrder, example);

        return result == 1 ? true : false; //result == 1 代表更新成功


    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteOrder(String userId, String orderId) {

        Orders deleteOrder = new Orders();
        deleteOrder.setIsDelete(YesOrNo.YES.type);
        deleteOrder.setUpdatedTime(new Date());

        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", orderId);
        criteria.andEqualTo("userId", userId);

        int result = ordersMapper.updateByExampleSelective(deleteOrder, example);

        return result == 1 ? true : false; //result == 1 代表更新成功

    }
}
