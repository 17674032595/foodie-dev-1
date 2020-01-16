package com.wuyiccc.config;

import com.wuyiccc.service.OrderService;
import com.wuyiccc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author wuyiccc
 * @date 2020/1/16 23:18
 * 岂曰无衣，与子同袍~
 */

@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0 0/1 * * ?") //每隔一小时，任务执行
    public void autoCloseOrder(){
        orderService.closeOrder();
        System.out.println("执行定时任务，当前时间为: "+ DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
    }
}
