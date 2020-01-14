package com.wuyiccc.service;

import com.wuyiccc.pojo.UserAddress;
import com.wuyiccc.pojo.bo.AddressBO;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/1/14 15:07
 * 岂曰无衣，与子同袍~
 */
public interface AddressService {


    /**
     * 根据用户id查询用户的收货地址列表
     *
     * @param userId
     * @return
     */
    public List<UserAddress> queryAll(String userId);


    /**
     * 添加用户收货地址
     *
     * @param addressBO
     */
    public void addNewUserAddress(AddressBO addressBO);


    /**
     * 更新用户收货地址
     *
     * @param addressBO
     */
    public void updateUserAddress(AddressBO addressBO);


    /**
     * 根据用户id和用户收货地址id 删除对应的用户收货地址
     *
     * @param userId
     * @param addressId
     */
    public void deleteUserAddress(String userId, String addressId);


}
