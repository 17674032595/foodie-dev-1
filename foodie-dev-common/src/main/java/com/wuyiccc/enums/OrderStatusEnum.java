package com.wuyiccc.enums;

/**
 * @author wuyiccc
 * @date 2020/1/15 16:27
 * 岂曰无衣，与子同袍~
 */
public enum OrderStatusEnum {

    WAIT_PAY(10,"待付款"),
    WAIT_DELIVER(20,"已付款，待发货"),
    WAIT_RECEIVE(30,"已发货，待收货"),
    SUCCESS(40,"交易成功"),
    CLOSE(50,"交易关闭");



    public final Integer type;
    public final String value;

    OrderStatusEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
