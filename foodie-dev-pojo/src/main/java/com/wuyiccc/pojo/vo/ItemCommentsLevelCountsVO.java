package com.wuyiccc.pojo.vo;

/**
 * @author wuyiccc
 * @date 2020/1/10 8:37
 * 岂曰无衣，与子同袍~
 */

/**
 * 存储返回给前端的商品评价分类统计数量
 */
public class ItemCommentsLevelCountsVO {

    private Integer totalCounts;
    private Integer goodCounts;
    private Integer normalCounts;
    private Integer badCounts;

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }

    public Integer getGoodCounts() {
        return goodCounts;
    }

    public void setGoodCounts(Integer goodCounts) {
        this.goodCounts = goodCounts;
    }

    public Integer getNormalCounts() {
        return normalCounts;
    }

    public void setNormalCounts(Integer normalCounts) {
        this.normalCounts = normalCounts;
    }

    public Integer getBadCounts() {
        return badCounts;
    }

    public void setBadCounts(Integer badCounts) {
        this.badCounts = badCounts;
    }

    @Override
    public String toString() {
        return "ItemCommentsLevelCountsVO{" +
                "totalCounts=" + totalCounts +
                ", goodCounts=" + goodCounts +
                ", normalCounts=" + normalCounts +
                ", badCounts=" + badCounts +
                '}';
    }
}
