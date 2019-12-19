package com.wuyiccc.service;

import com.wuyiccc.pojo.Stu;

/**
 * @author wuyiccc
 * @date 2019/12/19 19:01
 * 岂曰无衣，与子同袍~
 */
public interface StuService {

    public Stu getStuInfo(int id);

    public void saveStud();

    public void updateStu(int id);

    public void deleteStu(int id);
}
