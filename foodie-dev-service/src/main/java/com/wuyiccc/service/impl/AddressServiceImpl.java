package com.wuyiccc.service.impl;

import com.wuyiccc.enums.YesOrNo;
import com.wuyiccc.mapper.UserAddressMapper;
import com.wuyiccc.pojo.UserAddress;
import com.wuyiccc.pojo.bo.AddressBO;
import com.wuyiccc.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {


        UserAddress userAddress = new UserAddress();

        userAddress.setUserId(userId);


        return userAddressMapper.select(userAddress);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {


        Integer isDefault = 0;

        //1.如果对应的用户没有收货地址那么就把这个收货地址新增为默认地址
        List<UserAddress> addressList = this.queryAll(addressBO.getUserId());
        if (addressList == null || addressList.isEmpty() || addressList.size() == 0) {
            isDefault = 1;
        }

        UserAddress userAddress = new UserAddress();

        BeanUtils.copyProperties(addressBO, userAddress); // 使用工具类进行属性复制到要存储的类

        String addressId = sid.nextShort();

        userAddress.setId(addressId);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        userAddress.setIsDefault(isDefault);

        userAddressMapper.insert(userAddress);


    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBO addressBO) {

        UserAddress pendingAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, pendingAddress);

        pendingAddress.setId(addressBO.getAddressId());
        pendingAddress.setUpdatedTime(new Date());

        userAddressMapper.updateByPrimaryKeySelective(pendingAddress);//selective 代表 如果有空的字段，那么就不更新，否则就会将未赋值的字段更新为null

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {

        UserAddress userAddress = new UserAddress();
        userAddress.setId(addressId);
        userAddress.setUserId(userId);

        userAddressMapper.delete(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {

        // 1.查找默认地址，设置为不默认
        UserAddress queryAddress = new UserAddress();
        queryAddress.setUserId(userId);

        queryAddress.setIsDefault(YesOrNo.YES.type);

        //考虑到数据库可能会出现数据紊乱的情况，我们返回一个list
        List<UserAddress> list = userAddressMapper.select(queryAddress);

        for (UserAddress ad : list) {
            ad.setIsDefault(YesOrNo.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(ad);
        }

        // 2.根据地址id修改为默认的地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setUserId(userId);
        defaultAddress.setId(addressId);
        defaultAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {

        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);

        UserAddress address = userAddressMapper.selectOne(userAddress);

        return address;
    }


}
