package com.wuyiccc.service.impl;

import com.wuyiccc.mapper.CategoryMapper;
import com.wuyiccc.mapper.CategoryMapperCustom;
import com.wuyiccc.pojo.Category;
import com.wuyiccc.pojo.vo.CategoryVO;
import com.wuyiccc.pojo.vo.NewItemsVO;
import com.wuyiccc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuyiccc
 * @date 2019/12/30 15:19
 * 岂曰无衣，与子同袍~
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCats() {

        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type",1);

        List<Category> results = categoryMapper.selectByExample(example);


        return results;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {

        List<CategoryVO> results = categoryMapperCustom.getSubCatList(rootCatId);


        return results;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {

        Map<String,Object> map = new HashMap<>();
        map.put("rootCatId",rootCatId);
        List<NewItemsVO> results = categoryMapperCustom.getSixItemsLazy(map);
        return results;
    }


}

