package com.wuyiccc.service;

import com.wuyiccc.pojo.UserAddress;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/1/14 15:07
 * 岂曰无衣，与子同袍~
 */
public interface AddressService {


    public List<UserAddress> queryAll(String userId);
}
