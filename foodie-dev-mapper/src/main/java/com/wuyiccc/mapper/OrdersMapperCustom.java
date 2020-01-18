package com.wuyiccc.mapper;

import com.wuyiccc.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wuyiccc
 * @date 2020/1/17 21:14
 * 岂曰无衣，与子同袍~
 */
public interface OrdersMapperCustom {

    public List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String, Object> map);

    public int getMyOrderStatusCounts(@Param("paramsMap") Map<String,Object> map);
}
