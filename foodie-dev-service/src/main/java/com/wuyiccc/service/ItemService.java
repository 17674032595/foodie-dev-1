package com.wuyiccc.service;

import com.wuyiccc.pojo.Items;
import com.wuyiccc.pojo.ItemsImg;
import com.wuyiccc.pojo.ItemsParam;
import com.wuyiccc.pojo.ItemsSpec;
import com.wuyiccc.pojo.vo.ItemCommentVo;
import com.wuyiccc.pojo.vo.ItemCommentsLevelCountsVO;
import com.wuyiccc.utils.PagedGridResult;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/1/6 20:11
 * 岂曰无衣，与子同袍~
 */
public interface ItemService {


    /**
     * 根据商品id查询商品信息
     * @param itemId
     * @return
     */
    public Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     * @param itemId
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格列表
     * @param itemId
     * @return
     */
    public List<ItemsSpec> queryItemSpecList (String itemId);

    /**
     * 根据商品id查询商品参数
     * @param itemId
     * @return
     */
    public ItemsParam queryItemParam(String itemId);


    public ItemCommentsLevelCountsVO queryItemCommentLevelCounts(String itemId);

    /**
     * 根据商品id与等级，查询对应的商品评价---带分页的
     * @param itemId
     * @param level
     * @return
     */
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);
}
