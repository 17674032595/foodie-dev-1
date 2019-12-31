package com.wuyiccc.pojo.vo;

/**
 * @author wuyiccc
 * @date 2019/12/31 15:15
 * 岂曰无衣，与子同袍~
 */


import java.util.List;

/**
 * 二级分类Vo
 */
public class CategoryVO {

    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;

    //三级分类VoList
    private List<SubCategoryVO> subCatList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public List<SubCategoryVO> getSubCatList() {
        return subCatList;
    }

    public void setSubCatList(List<SubCategoryVO> subCatList) {
        this.subCatList = subCatList;
    }
}
