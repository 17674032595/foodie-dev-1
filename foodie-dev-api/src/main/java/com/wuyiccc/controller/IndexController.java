package com.wuyiccc.controller;

import com.wuyiccc.enums.YesOrNo;
import com.wuyiccc.pojo.Carousel;
import com.wuyiccc.pojo.Category;
import com.wuyiccc.pojo.vo.CategoryVO;
import com.wuyiccc.service.CarouselService;
import com.wuyiccc.service.CategoryService;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private CategoryService categoryService;


    @ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public WUYICCCJSONResult carousel(){

        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);
        return WUYICCCJSONResult.ok(list);

    }

    @ApiOperation(value = "获取商品分类(一级分类)",notes = "获取商品分类(一级分类)",httpMethod = "GET")
    @GetMapping("/cats")
    public WUYICCCJSONResult cats(){

        List<Category> list = categoryService.queryAllRootLevelCats();
        return WUYICCCJSONResult.ok(list);

    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public WUYICCCJSONResult subCat(
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer rootCatId
    ){

        if(rootCatId == null){//一般前端返回的都是有id的，这里是为了反爬虫和api接口攻击
            return WUYICCCJSONResult.errorMsg("分类不存在");
        }

        List<CategoryVO> list = categoryService.getSubCatList(rootCatId);
        return WUYICCCJSONResult.ok(list);
    }





}
