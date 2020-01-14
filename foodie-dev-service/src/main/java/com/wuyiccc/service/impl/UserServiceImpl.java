package com.wuyiccc.service.impl;

import com.wuyiccc.enums.Sex;
import com.wuyiccc.mapper.UsersMapper;
import com.wuyiccc.pojo.Users;
import com.wuyiccc.pojo.bo.UserBO;
import com.wuyiccc.service.UserService;
import com.wuyiccc.utils.DateUtil;
import com.wuyiccc.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author wuyiccc
 * @date 2019/12/23 10:49
 * 岂曰无衣，与子同袍~
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UsersMapper usersMapper;

    private static final String USER_FACE = "https://c-ssl.duitang.com/uploads/item/201809/01/20180901154305_uuioq.thumb.700_0.jpg";

    @Autowired
    private Sid sid;


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


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {



        String userId = sid.nextShort();//使用短id

        Users user = new Users();

        user.setId(userId);

        user.setUsername(userBO.getUsername());

        //存储加密之后的密码
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //默认的nickname就是username
        user.setNickname(userBO.getUsername());
        //设置默认头像
        user.setFace(USER_FACE);
        //设置默认生日
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        //设置默认性别为保密,采用枚举
        user.setSex(Sex.secret.type);

        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        usersMapper.insert(user);


        return user;//返回是为了在页面显示用户的基本信息
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username,String password) {

        Example example = new Example(Users.class);
        Example.Criteria userCriteria = example.createCriteria();
        userCriteria.andEqualTo("username",username);//"username" 是实体类的属性，而不是数据库的字段名称
        userCriteria.andEqualTo("password",password);

        Users result = usersMapper.selectOneByExample(example); //只能返回一个数据或者null，否则就会报错

        return result;
    }
}
