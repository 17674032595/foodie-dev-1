package com.wuyiccc.controller;

import com.wuyiccc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyiccc
 * @date 2019/12/11 18:29
 * 岂曰无衣，与子同袍~
 */

@RestController
public class StuFooController {

    @Autowired
    private StuService stuService;

    @GetMapping("/getStu")
    public Object getStu(int id){

        return stuService.getStuInfo(id);

    }

    @PostMapping("/saveStu")
    public Object getStu(){
        stuService.saveStud();
        return "OK";
    }

    @PostMapping("/updateStu")
    public Object updateStu(int id){
        stuService.updateStu(id);
        return "OK";
    }

    @PostMapping("/deleteStu")
    public Object deleteStu(int id){
        stuService.deleteStu(id);
        return "OK";
    }


}
