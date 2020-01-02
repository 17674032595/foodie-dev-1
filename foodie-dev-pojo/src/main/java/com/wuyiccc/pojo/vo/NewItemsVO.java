package com.wuyiccc.pojo.vo;

/**
 * @author wuyiccc
 * @date 2020/1/2 16:00
 * 岂曰无衣，与子同袍~
 */

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 最新商品VO
 */
public class NewItemsVO {
/*
    f.id as rootCatId,
    f.`name` as rootCatName,
    f.slogan as slogan,
    f.cat_image as catImage,
    f.bg_color as bgColor,*/


    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;

    private List<SimpleItemVO> simpleItemList;

    public Integer getRootCatId() {
        return rootCatId;
    }

    public void setRootCatId(Integer rootCatId) {
        this.rootCatId = rootCatId;
    }

    public String getRootCatName() {
        return rootCatName;
    }

    public void setRootCatName(String rootCatName) {
        this.rootCatName = rootCatName;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public List<SimpleItemVO> getSimpleItemList() {
        return simpleItemList;
    }

    public void setSimpleItemList(List<SimpleItemVO> simpleItemList) {
        this.simpleItemList = simpleItemList;
    }

}
