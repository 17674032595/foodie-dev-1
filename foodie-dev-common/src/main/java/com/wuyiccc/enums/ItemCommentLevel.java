package com.wuyiccc.enums;

/**
 * @author wuyiccc
 * @date 2020/1/10 8:52
 * 岂曰无衣，与子同袍~
 */
public enum ItemCommentLevel {

    GOOD(1,"好评"),
    NORMAL(2,"中评"),
    BAD(3,"差评");

    public final  Integer type;

    public final String value;

    ItemCommentLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
