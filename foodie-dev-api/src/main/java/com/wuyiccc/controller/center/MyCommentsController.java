package com.wuyiccc.controller.center;

import com.wuyiccc.controller.BaseController;
import com.wuyiccc.enums.YesOrNo;
import com.wuyiccc.pojo.OrderItems;
import com.wuyiccc.pojo.Orders;
import com.wuyiccc.service.center.MyCommentsService;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}
