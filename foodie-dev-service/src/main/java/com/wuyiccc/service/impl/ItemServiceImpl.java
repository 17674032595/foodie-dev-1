package com.wuyiccc.service.impl;

import com.wuyiccc.mapper.ItemsImgMapper;
import com.wuyiccc.mapper.ItemsMapper;
import com.wuyiccc.mapper.ItemsParamMapper;
import com.wuyiccc.mapper.ItemsSpecMapper;
import com.wuyiccc.pojo.Items;
import com.wuyiccc.pojo.ItemsImg;
import com.wuyiccc.pojo.ItemsParam;
import com.wuyiccc.pojo.ItemsSpec;
import com.wuyiccc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/1/6 20:15
 * 岂曰无衣，与子同袍~
 */
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;


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
}
