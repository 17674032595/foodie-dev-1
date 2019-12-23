package com.wuyiccc.service.impl;

import com.wuyiccc.mapper.UsersMapper;
import com.wuyiccc.pojo.Users;
import com.wuyiccc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * @author wuyiccc
 * @date 2019/12/23 10:49
 * 岂曰无衣，与子同袍~
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UsersMapper usersMapper;


    /**
     * 根据username判断数据库是否已经存在记录
     *
     * @param username
     * @return  存在返回true，不存在则返回false
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameExist(String username) {

        Example userExample = new Example(Users.class);//创建查询条间对象
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username", username);//添加查询的条件

        Users result = usersMapper.selectOneByExample(userExample);

        return result == null ? false : true;
    }
}
