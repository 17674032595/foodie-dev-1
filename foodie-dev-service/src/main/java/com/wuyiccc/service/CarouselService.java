package com.wuyiccc.service;

import com.wuyiccc.pojo.Carousel;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2019/12/30 12:16
 * 岂曰无衣，与子同袍~
 */
public interface CarouselService {

    public List<Carousel> queryAll(Integer isShow);
}
