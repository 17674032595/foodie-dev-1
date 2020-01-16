package com.wuyiccc.service.impl;

import com.wuyiccc.enums.OrderStatusEnum;
import com.wuyiccc.enums.YesOrNo;
import com.wuyiccc.mapper.*;
import com.wuyiccc.pojo.*;
import com.wuyiccc.pojo.bo.SubmitOrderBO;
import com.wuyiccc.pojo.vo.MerchantOrderVO;
import com.wuyiccc.pojo.vo.OrderVO;
import com.wuyiccc.service.AddressService;
import com.wuyiccc.service.ItemService;
import com.wuyiccc.service.OrderService;
import com.wuyiccc.utils.DateUtil;
import org.aspectj.weaver.ast.Or;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.annotation.Order;

import java.util.Date;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/1/15 9:54
 * 岂曰无衣，与子同袍~
 */
@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private AddressService addressService;


    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;


    @Autowired
    private Sid sid;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {

        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();

        //设置邮费为0
        Integer postAmount = 0;

        String orderId = sid.nextShort();
        UserAddress address = addressService.queryUserAddress(userId, addressId); // 查询到对应的收货地址信息

        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);

        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(address.getProvince() + " "
                + address.getCity() + " "
                + address.getDistrict() + " "
                + address.getDetail());


        newOrder.setPostAmount(postAmount);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);
        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());


        // 循环根据itemSpecIds保存订单商品信息表
        String[] itemSpecIdArr = itemSpecIds.split(",");
        Integer totalAmount = 0;
        Integer realPayAmount = 0;
        for (String itemSpecId : itemSpecIdArr) {

            ItemsSpec itemsSpec = itemService.queryItemSpecById(itemSpecId);

            //TODO 整合redis后，商品购买的数量重新从redis的购物车中获取
            int buyCounts = 1;
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;

            // 根据商品id，获得商品信息及商品图片
            String itemId = itemsSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);

            // 循环保存子订单到数据到数据库
            OrderItems subOrderItem = new OrderItems();
            String subOrderId = sid.nextShort();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemsSpec.getName());
            subOrderItem.setPrice(itemsSpec.getPriceDiscount());
            subOrderItem.setBuyCounts(buyCounts);

            orderItemsMapper.insert(subOrderItem);

            // 在用户提交订单之后，规格表中需要扣除库存
            itemService.decreaseItemSpecStock(itemSpecId, buyCounts);

        }


        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        //保存主订单数据
        ordersMapper.insert(newOrder);


        // 保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());

        orderStatusMapper.insert(waitPayOrderStatus);


        //构建商户订单，用于传给支付中心
        MerchantOrderVO merchantOrderVO = new MerchantOrderVO();
        merchantOrderVO.setMerchantOrderId(orderId);
        merchantOrderVO.setMerchantUserId(userId);
        merchantOrderVO.setAmount(realPayAmount + postAmount);
        merchantOrderVO.setPayMethod(payMethod);

        //构建自定义订单VO
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrderVO(merchantOrderVO);

        return orderVO;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {

        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {

        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void closeOrder() {

        //查询所有未付款订单，判断时间是否超时(1天),超时则关闭交易
        OrderStatus queryOrder = new OrderStatus();
        queryOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> list = orderStatusMapper.select(queryOrder);
        for (OrderStatus os : list) {
            //获得订单创建时间
            Date createdTime = os.getCreatedTime();
            //和当前时间进行对比
            int days = DateUtil.daysBetween(createdTime, new Date());
            if (days >= 1) {
                //超过一天，关闭订单
                doCloseOrder(os.getOrderId());
            }
        }
    }

    /**
     * 关闭订单
     * @param orderId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    void doCloseOrder(String orderId){
        OrderStatus close = new OrderStatus();
        close.setOrderId(orderId);
        close.setOrderStatus(OrderStatusEnum.CLOSE.type);
        close.setCloseTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(close);
    }

}
