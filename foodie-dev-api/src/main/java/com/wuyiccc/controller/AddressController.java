package com.wuyiccc.controller;

import com.wuyiccc.pojo.UserAddress;
import com.wuyiccc.pojo.bo.AddressBO;
import com.wuyiccc.service.AddressService;
import com.wuyiccc.utils.MobileEmailUtils;
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
 * @date 2020/1/14 14:52
 * 岂曰无衣，与子同袍~
 */
@Api(value = "收货地址相关接口", tags = {"收货地址相关接口"})
@RestController
@RequestMapping("address")
public class AddressController {
    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作
     * 1.查询用户的所有收货地址列表
     * 2.新增收货地址
     * 3.删除收货地址
     * 4.修改收货地址
     * 5.设置默认地址
     */

    @Autowired
    private AddressService addressService;

    @PostMapping("/list")
    @ApiOperation(value = "根据用户id查询用户地址列表", notes = "根据用户id查询用户地址列表", httpMethod = "POST")
    public WUYICCCJSONResult list(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId
    ) {


        if (StringUtils.isBlank(userId)) {
            return WUYICCCJSONResult.errorMsg("请输入用户id");
        }

        List<UserAddress> userAddressList = addressService.queryAll(userId);

        return WUYICCCJSONResult.ok(userAddressList);
    }

    /**
     * 后端对收货地址信息进行二次校验的方法
     *
     * @param addressBO
     * @return
     */
    private WUYICCCJSONResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return WUYICCCJSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return WUYICCCJSONResult.errorMsg("收货人姓名不能太长");
        }
        String mobile = addressBO.getMobile();

        if (StringUtils.isBlank(mobile)) {
            return WUYICCCJSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return WUYICCCJSONResult.errorMsg("收货人手机号长度不正确");
        }

        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return WUYICCCJSONResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (
                StringUtils.isBlank(province) ||
                        StringUtils.isBlank(city) ||
                        StringUtils.isBlank(district) ||
                        StringUtils.isBlank(detail)
        ) {
            return WUYICCCJSONResult.errorMsg("收货地址信息不能为空");
        }
        return WUYICCCJSONResult.ok();
    }

    @PostMapping("/add")
    @ApiOperation(value = "用户新增地址", notes = "用户新增地址", httpMethod = "POST")
    public WUYICCCJSONResult add(
            @ApiParam(name = "addressBO", value = "新增地址信息json", required = true)
            @RequestBody AddressBO addressBO
    ) {

        WUYICCCJSONResult checkAddressResult = checkAddress(addressBO);//检查收货地址是否符合要求
        if (checkAddressResult.getStatus() != 200) {
            return checkAddressResult;
        }
        addressService.addNewUserAddress(addressBO);

        return WUYICCCJSONResult.ok();
    }

    @PostMapping("/update")
    @ApiOperation(value = "用户修改收货地址", notes = "用户修改收货地址", httpMethod = "POST")
    public WUYICCCJSONResult update(
            @ApiParam(name = "addressBO", value = "更新地址信息json", required = true)
            @RequestBody AddressBO addressBO
    ) {

        if (StringUtils.isBlank(addressBO.getAddressId())) {
            return WUYICCCJSONResult.errorMsg("修改地址错误：addressId不能为空");
        }

        WUYICCCJSONResult checkAddressResult = checkAddress(addressBO);
        if (checkAddressResult.getStatus() != 200) {
            return checkAddressResult;
        }

        addressService.updateUserAddress(addressBO);

        return WUYICCCJSONResult.ok();

    }


    @ApiOperation(value = "用户删除地址", notes = "用户删除地址", httpMethod = "POST")
    @PostMapping("/delete")
    public WUYICCCJSONResult delete(
            @ApiParam(name = "userId", value = "用户的id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "addressId", value = "地址的id", required = true)
            @RequestParam String addressId
    ) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return WUYICCCJSONResult.errorMsg("用户id或者地址id不能为空");
        }

        addressService.deleteUserAddress(userId, addressId);

        return WUYICCCJSONResult.ok();
    }


    @ApiOperation(value = "用户设置默认地址", notes = "用户设置默认地址", httpMethod = "POST")
    @PostMapping("/setDefalut")
    public WUYICCCJSONResult setDefault(
            @ApiParam(name = "userId", value = "用户的id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "addressId", value = "地址的id", required = true)
            @RequestParam String addressId
    ) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return WUYICCCJSONResult.errorMsg("用户id或者地址id不能为空");
        }

        addressService.updateUserAddressToBeDefault(userId, addressId);

        return WUYICCCJSONResult.ok();
    }

}
