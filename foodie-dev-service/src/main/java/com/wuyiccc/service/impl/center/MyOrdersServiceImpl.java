package com.wuyiccc.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuyiccc.mapper.OrdersMapperCustom;
import com.wuyiccc.pojo.vo.MyOrdersVO;
import com.wuyiccc.service.center.MyOrdersService;
import com.wuyiccc.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

        PageHelper.startPage(page,pageSize);

        List<MyOrdersVO> list = ordersMapperCustom.queryMyOrders(map);
        return setterPagedGrid(list, page);
    }


}
