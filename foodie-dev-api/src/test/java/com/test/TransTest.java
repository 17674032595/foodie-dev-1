package com.test;

import com.wuyiccc.Application;
import com.wuyiccc.service.StuService;
import com.wuyiccc.service.TestTransService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wuyiccc
 * @date 2019/12/20 14:50
 * 岂曰无衣，与子同袍~
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TransTest {

    @Autowired
    private StuService stuService;

    @Autowired
    private TestTransService testTransService;

    @Test
    public void myTest(){
        testTransService.testPropagationTrans();
    }
}
