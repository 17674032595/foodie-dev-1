package com.wuyiccc.controller;

import com.wuyiccc.pojo.Items;
import com.wuyiccc.pojo.ItemsImg;
import com.wuyiccc.pojo.ItemsParam;
import com.wuyiccc.pojo.ItemsSpec;
import com.wuyiccc.pojo.vo.ItemCommentsLevelCountsVO;
import com.wuyiccc.pojo.vo.ItemInfoVO;
import com.wuyiccc.pojo.vo.ShopcartVO;
import com.wuyiccc.service.ItemService;
import com.wuyiccc.utils.PagedGridResult;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/1/9 18:50
 * 岂曰无衣，与子同袍~
 */
@RestController
@RequestMapping("items")
@Api(value = "商品详情信息", tags = {"用于查询商品的详情信息"})
public class ItemsController extends BaseController{

    @Autowired
    private ItemService itemService;


    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public WUYICCCJSONResult info(
            @ApiParam(name = "itemId", value = "商品详情id", required = true)
            @PathVariable String itemId

    ) {

        if (StringUtils.isBlank(itemId)) {
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


    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public WUYICCCJSONResult commentLevel(
            @ApiParam(name = "itemId", value = "商品详情Id", required = true)
            @RequestParam String itemId

    ) {
        if (StringUtils.isBlank(itemId)) {
            return WUYICCCJSONResult.errorMsg("商品id不能为空");
        }
        ItemCommentsLevelCountsVO itemCommentsLevelCountsVO = itemService.queryItemCommentLevelCounts(itemId);

        return WUYICCCJSONResult.ok(itemCommentsLevelCountsVO);

    }

    @ApiOperation(value = "分页查询商品评价信息",notes = "分页查询商品评价信息",httpMethod = "GET")
    @GetMapping("/comments")
    public WUYICCCJSONResult comments(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level",value = "商品评价等级",required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page",value = "查询下一页的第几页",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "分页的每一页显示的条数",required = false)
            @RequestParam Integer pageSize
    ){
        if(StringUtils.isBlank(itemId)){
            return WUYICCCJSONResult.errorMsg("商品id不能为空");
        }
        if (page == null){
            page = 1;   //如果未传入，则默认为第一页
        }
        if(pageSize == null){
            pageSize = COMMON_PAGE_SIZE;  // 默认为10
        }

        PagedGridResult grid = itemService.queryPagedComments(itemId, level, page, pageSize);


        return WUYICCCJSONResult.ok(grid);
    }

    @ApiOperation(value = "根据子分类搜索商品列表",notes = "根据子分类搜索商品列表",httpMethod = "GET")
    @GetMapping("/catItems")
    public WUYICCCJSONResult searchItemsByCatId(
            @ApiParam(name = "catId",value = "三级分类id",required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort",value = "排序",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",value = "查询下一页的第几页",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "分页的每一页显示的数据条数",required = false)
            @RequestParam Integer pageSize
    ){

        if(catId == null){
            return WUYICCCJSONResult.errorMsg("查询的商品分类id不能为空");
        }


        if (page == null){
            page = 1;
        }
        if(pageSize == null){
            pageSize = PAGE_SIZE;  // 默认20
        }

        PagedGridResult grid = itemService.searchItemsByThirdCat(catId, sort, page, pageSize);

        return  WUYICCCJSONResult.ok(grid);
    }

    @ApiOperation(value = "根据商品规格ids查找最新的商品数据",notes = "根据商品规格ids查找最新的商品数据",httpMethod = "GET")
    @GetMapping("/refresh")
    public WUYICCCJSONResult refresh(
            @ApiParam(name = "itemSpecIds",value = "拼接的规格ids",required = true,example = "1001,1003,1005")
            @RequestParam String itemSpecIds
    ){
        if (StringUtils.isBlank(itemSpecIds)){
            return WUYICCCJSONResult.ok();
        }
        List<ShopcartVO> list = itemService.queryItemsBySpecIds(itemSpecIds);

        return WUYICCCJSONResult.ok(list);
    }
}
