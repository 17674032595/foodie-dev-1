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
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveStud() {

        Stu stu = new Stu();
        stu.setName("Jack");
        stu.setAge(19);

        stuMapper.insert(stu);//insert与insertSelective  方法比较，如果insertSelective有属性为空，则会采用默认值

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateStu(int id) {
        Stu stu = new Stu();
        stu.setName("lucy");
        stu.setAge(20);
        stu.setId(id);
        stuMapper.updateByPrimaryKey(stu);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteStu(int id) {

        stuMapper.deleteByPrimaryKey(id);

    }

    @Override
    public void saveParent() {
        Stu stu = new Stu();
        stu.setName("parent");
        stu.setAge(19);
        stuMapper.insert(stu);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void saveChildren() {
        saveChild1();
        int a = 1 / 0;
        saveChild2();
    }

    public void saveChild1() {
        Stu stu1 = new Stu();
        stu1.setName("child-1");
        stu1.setAge(11);
        stuMapper.insert(stu1);
    }

    public void saveChild2() {
        Stu stu2 = new Stu();
        stu2.setName("child-2");
        stu2.setAge(22);
        stuMapper.insert(stu2);
    }
}
