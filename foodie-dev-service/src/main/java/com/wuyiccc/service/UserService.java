package com.wuyiccc.service;

import com.wuyiccc.pojo.Users;
import com.wuyiccc.pojo.bo.UserBO;

/**
 * @author wuyiccc
 * @date 2019/12/23 10:36
 * 岂曰无衣，与子同袍~
 */
public interface UserService {


    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    public boolean queryUsernameExist(String username);


    /**
     * 注册用户
     * @param userBO
     * @return
     */
    public Users createUser(UserBO userBO);

    public Users queryUserForLogin(String username,String password);



}
