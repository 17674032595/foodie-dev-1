package com.wuyiccc.service;

import com.wuyiccc.pojo.Category;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2019/12/30 15:18
 * 岂曰无衣，与子同袍~
 */
public interface CategoryService {


    public List<Category> queryAllRootLevelCats();
}
