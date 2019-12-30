package com.wuyiccc.controller;

import com.wuyiccc.enums.YesOrNo;
import com.wuyiccc.pojo.Carousel;
import com.wuyiccc.service.CarouselService;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2019/12/30 12:54
 * 岂曰无衣，与子同袍~
 */

@Api(value = "首页",tags = {"首页展示相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {


    @Autowired
    private CarouselService carouselService;


    @ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public WUYICCCJSONResult carousel(){

        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);
        return WUYICCCJSONResult.ok(list);

    }

}
