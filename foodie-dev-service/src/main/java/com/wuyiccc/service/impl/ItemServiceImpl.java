package com.wuyiccc.service.impl;

import com.wuyiccc.enums.ItemCommentLevel;
import com.wuyiccc.mapper.*;
import com.wuyiccc.pojo.*;
import com.wuyiccc.pojo.vo.ItemCommentsLevelCountsVO;
import com.wuyiccc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/1/6 20:15
 * 岂曰无衣，与子同袍~
 */
@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {

        Items item = itemsMapper.selectByPrimaryKey(itemId);
        return item;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {

        Example itemsImgExp = new Example(ItemsImg.class);

        Example.Criteria criteria = itemsImgExp.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        List<ItemsImg> results = itemsImgMapper.selectByExample(itemsImgExp);

        return results;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {

        Example itemsSpecExp = new Example(ItemsSpec.class);
        Example.Criteria criteria = itemsSpecExp.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        List<ItemsSpec> results = itemsSpecMapper.selectByExample(itemsSpecExp);
        return results;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {

        Example itemParamExp = new Example(ItemsParam.class);
        Example.Criteria criteria = itemParamExp.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        ItemsParam result = itemsParamMapper.selectOneByExample(itemParamExp);
        return result;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemCommentsLevelCountsVO queryItemCommentLevelCounts(String itemId) {


        Integer goodCounts = getCommentsCounts(itemId, ItemCommentLevel.GOOD.type);
        Integer normalCounts = getCommentsCounts(itemId, ItemCommentLevel.NORMAL.type);
        Integer badCounts = getCommentsCounts(itemId, ItemCommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;


        ItemCommentsLevelCountsVO countsVO = new ItemCommentsLevelCountsVO();
        countsVO.setTotalCounts(totalCounts);
        countsVO.setGoodCounts(goodCounts);
        countsVO.setNormalCounts(normalCounts);
        countsVO.setBadCounts(badCounts);
        return countsVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommentsCounts(String itemId,Integer level){//只有动态绑定的方法才能添加事务

        ItemsComments comment = new ItemsComments();
        comment.setItemId(itemId);
        comment.setCommentLevel(level);
        int counts = itemsCommentsMapper.selectCount(comment);
        return counts;
    }
}
