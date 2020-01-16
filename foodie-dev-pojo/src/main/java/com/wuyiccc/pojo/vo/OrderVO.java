package com.wuyiccc.pojo.vo;

/**
 * @author wuyiccc
 * @date 2020/1/16 14:16
 * 岂曰无衣，与子同袍~
 */
public class OrderVO {

    private String orderId;
    private MerchantOrderVO merchantOrderVO;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrderVO getMerchantOrderVO() {
        return merchantOrderVO;
    }

    public void setMerchantOrderVO(MerchantOrderVO merchantOrderVO) {
        this.merchantOrderVO = merchantOrderVO;
    }
}
