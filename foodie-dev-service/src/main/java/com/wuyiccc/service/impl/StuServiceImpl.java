package com.wuyiccc.service.impl;

import com.wuyiccc.mapper.StuMapper;
import com.wuyiccc.pojo.Stu;
import com.wuyiccc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wuyiccc
 * @date 2019/12/19 19:05
 * 岂曰无衣，与子同袍~
 */
@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Transactional(propagation = Propagation.SUPPORTS)  //因为是查询，所以只需要事务支持就可以了
    @Override
    public Stu getStuInfo(int id) {


        return stuMapper.selectByPrimaryKey(id);

    }

    @Override
    public void saveStud() {

    }

    @Override
    public void updateStu(int id) {

    }

    @Override
    public void deleteStu(int id) {

    }
}
