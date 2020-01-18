package com.wuyiccc.controller.center;

import com.wuyiccc.controller.BaseController;
import com.wuyiccc.enums.YesOrNo;
import com.wuyiccc.pojo.OrderItems;
import com.wuyiccc.pojo.Orders;
import com.wuyiccc.pojo.bo.center.OrderItemsCommentBO;
import com.wuyiccc.service.center.MyCommentsService;
import com.wuyiccc.utils.PagedGridResult;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/1/18 10:34
 * 岂曰无衣，与子同袍~
 */
@Api(value = "用户中心评价模块", tags = {"用户中心评价模块相关接口"})
@RestController
@RequestMapping("mycomments")
public class MyCommentsController extends BaseController {


    @Autowired
    private MyCommentsService myCommentsService;

    @ApiOperation(value = "查询待评价的订单", notes = "查询待评价的订单", httpMethod = "POST")
    @PostMapping("/pending")
    public WUYICCCJSONResult pending(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId
    ) {

        WUYICCCJSONResult checkResult = checkUserOrder(userId, orderId);

        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }

        Orders myOrder = (Orders) checkResult.getData();

        if (myOrder.getIsComment() == YesOrNo.YES.type) {
            return WUYICCCJSONResult.errorMsg("该笔订单已经评价");
        }

        List<OrderItems> list = myCommentsService.queryPendingComment(orderId);

        return WUYICCCJSONResult.ok(list);
    }


    @ApiOperation(value = "保存评论列表", notes = "保存评论列表", httpMethod = "POST")
    @PostMapping("/saveList")
    public WUYICCCJSONResult saveList(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @RequestBody List<OrderItemsCommentBO> commentList
    ) {

        System.out.println(commentList);

        WUYICCCJSONResult checkResult = checkUserOrder(userId, orderId);

        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }

        // 判断评论内容不能为空
        if(commentList == null || commentList.isEmpty() || commentList.size() == 0){
            return WUYICCCJSONResult.errorMsg("评价内容不能为空");
        }

        myCommentsService.saveComments(orderId,userId,commentList);

        return WUYICCCJSONResult.ok();
    }

    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    @PostMapping("/query")
    public WUYICCCJSONResult query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize
    ) {

        if (StringUtils.isBlank(userId)) {
            return WUYICCCJSONResult.errorMsg("用户id不能为空");
        }
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = myCommentsService.queryMyComments(userId,  page, pageSize);

        return WUYICCCJSONResult.ok(grid);
    }






}
