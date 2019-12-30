package com.wuyiccc.service.impl;

import com.wuyiccc.mapper.CategoryMapper;
import com.wuyiccc.pojo.Category;
import com.wuyiccc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2019/12/30 15:19
 * 岂曰无衣，与子同袍~
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> queryAllRootLevelCats() {

        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type",1);

        List<Category> results = categoryMapper.selectByExample(example);


        return results;
    }

}

