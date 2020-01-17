package com.wuyiccc.controller.center;

import com.wuyiccc.controller.BaseController;
import com.wuyiccc.pojo.Orders;
import com.wuyiccc.service.center.MyOrdersService;
import com.wuyiccc.utils.PagedGridResult;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuyiccc
 * @date 2020/1/17 22:17
 * 岂曰无衣，与子同袍~
 */
@Api(value = "用户中心我的订单", tags = {"用户中心我的订单相关接口"})
@RestController
@RequestMapping("myorders")
public class MyOrdersController extends BaseController {

    @Autowired
    private MyOrdersService myOrdersService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/query")
    public WUYICCCJSONResult query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单状态", required = false)
            @RequestParam Integer orderStatus,
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

        PagedGridResult grid = myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);

        return WUYICCCJSONResult.ok(grid);
    }

    //商家发货没有后端，所以这个接口仅仅只是用于模拟
    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "GET")
    @GetMapping("/deliver")
    public WUYICCCJSONResult deliver(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId
    ) throws Exception {

        if (StringUtils.isBlank(orderId)) {
            return WUYICCCJSONResult.errorMsg("订单id不能为空");
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
        return WUYICCCJSONResult.ok();

    }

    @ApiOperation(value = "用户确认收货", notes = "用户确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public WUYICCCJSONResult confirmReceive(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId

    ) throws Exception {


        WUYICCCJSONResult result = checkUserOrder(userId, orderId);

        if(result.getStatus() != HttpStatus.OK.value()){
            return result;
        }
        return WUYICCCJSONResult.ok();
    }

    @ApiOperation(value = "用户删除订单", notes = "用户删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public WUYICCCJSONResult delete(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId

    ) throws Exception {


        WUYICCCJSONResult result = checkUserOrder(userId, orderId);

        if(result.getStatus() != HttpStatus.OK.value()){
            return result;
        }
        return WUYICCCJSONResult.ok();
    }

    /**
     * 用于验证用户和订单是否有关系，避免非法用户调用
     *
     * @return
     */
    private WUYICCCJSONResult checkUserOrder(String userId, String orderId) {

        Orders order = myOrdersService.queryMyOrder(userId, orderId);

        if (order == null) {
            return WUYICCCJSONResult.errorMsg("订单不存在");
        }
        return WUYICCCJSONResult.ok();
    }


}