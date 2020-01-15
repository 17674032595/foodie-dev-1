package com.wuyiccc.enums;

/**
 * @author wuyiccc
 * @date 2020/1/15 8:48
 * 岂曰无衣，与子同袍~
 */
public enum PayMethod {
    WEIXIN(1,"微信"),
    ALIPAY(2,"支付宝");

    public final Integer type;
    public final String value;

    PayMethod(Integer type,String value){
        this.type = type;
        this.value = value;
    }
}
