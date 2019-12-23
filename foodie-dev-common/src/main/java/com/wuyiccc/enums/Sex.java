package com.wuyiccc.enums;

/**
 * @author wuyiccc
 * @date 2019/12/23 20:19
 * 岂曰无衣，与子同袍~
 */
public enum Sex {

    woman(0,"女"),
    man(1,"男"),
    secret(2,"保密");

    public final Integer type;
    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
