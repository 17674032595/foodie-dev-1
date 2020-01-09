package com.wuyiccc.controller;

import com.wuyiccc.pojo.Items;
import com.wuyiccc.pojo.ItemsImg;
import com.wuyiccc.pojo.ItemsParam;
import com.wuyiccc.pojo.ItemsSpec;
import com.wuyiccc.pojo.vo.ItemInfoVO;
import com.wuyiccc.service.ItemService;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.IEEE754rUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/1/9 18:50
 * 岂曰无衣，与子同袍~
 */
@RestController
@RequestMapping("items")
@Api(value = "商品详情信息",tags = {"用于查询商品的详情信息"})
public class ItemsController {

    @Autowired
    private ItemService itemService;



    @ApiOperation(value = "查询商品详情",notes = "查询商品详情",httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public WUYICCCJSONResult info(
            @ApiParam(name = "itemId",value = "商品详情id",required = true)
            @PathVariable String itemId

    ){

        if(StringUtils.isBlank(itemId)){
            return WUYICCCJSONResult.errorMsg("商品id不能为空");
        }

        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemImgs = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemSpecs = itemService.queryItemSpecList(itemId);
        ItemsParam itemParams = itemService.queryItemParam(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemImgs);
        itemInfoVO.setItemSpecList(itemSpecs);
        itemInfoVO.setItemParams(itemParams);


        return WUYICCCJSONResult.ok(itemInfoVO);

    }
}
