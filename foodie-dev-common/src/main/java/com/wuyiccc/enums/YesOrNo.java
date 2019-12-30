package com.wuyiccc.enums;

/**
 * @author wuyiccc
 * @date 2019/12/23 20:19
 * 岂曰无衣，与子同袍~
 */
public enum YesOrNo {

    NO(0,"否"),
    YES(1,"是");


    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
