package com.wuyiccc.service.impl.center;

import com.wuyiccc.mapper.UsersMapper;
import com.wuyiccc.pojo.Users;
import com.wuyiccc.service.center.CenterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wuyiccc
 * @date 2020/1/17 9:33
 * 岂曰无衣，与子同袍~
 */

@Service
public class CenterUserServiceImpl implements CenterUserService {

    @Autowired
    private UsersMapper usersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        Users user = usersMapper.selectByPrimaryKey(userId);
        user.setPassword(null); // 密码保密
        return user;
    }
}
