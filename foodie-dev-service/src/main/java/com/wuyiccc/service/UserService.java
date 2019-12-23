package com.wuyiccc.service;

import com.wuyiccc.pojo.Users;
import com.wuyiccc.pojo.bo.UserBO;

/**
 * @author wuyiccc
 * @date 2019/12/23 10:36
 * 岂曰无衣，与子同袍~
 */
public interface UserService {


    public boolean queryUsernameExist(String username);


    public Users createUser(UserBO userBO);



}
