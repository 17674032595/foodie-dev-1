package com.wuyiccc.service.impl;

import com.wuyiccc.mapper.CarouselMapper;
import com.wuyiccc.pojo.Carousel;
import com.wuyiccc.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2019/12/30 12:24
 * 岂曰无衣，与子同袍~
 */

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;


    /**
     * 查询所有的轮播图
     * @param isShow
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Carousel> queryAll(Integer isShow) {

        Example example = new Example(Carousel.class);
        example.orderBy("sort").desc();//选择返回的排序，默认是正序 :参数是Carousel的isShow属性
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow",isShow);

        List<Carousel> results = carouselMapper.selectByExample(example);


        return results;
    }
}
