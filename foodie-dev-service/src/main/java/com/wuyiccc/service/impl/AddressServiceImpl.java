package com.wuyiccc.service.impl;

import com.wuyiccc.mapper.UserAddressMapper;
import com.wuyiccc.pojo.UserAddress;
import com.wuyiccc.service.AddressService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/1/14 15:09
 * 岂曰无衣，与子同袍~
 */
@Service
public class AddressServiceImpl implements AddressService {


    @Autowired
    private UserAddressMapper userAddressMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {


        UserAddress userAddress = new UserAddress();

        userAddress.setUserId(userId);




        return userAddressMapper.select(userAddress);
    }
}
