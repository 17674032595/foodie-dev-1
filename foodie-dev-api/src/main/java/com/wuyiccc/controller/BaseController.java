package com.wuyiccc.controller;

import com.wuyiccc.pojo.Orders;
import com.wuyiccc.service.center.MyOrdersService;
import com.wuyiccc.utils.WUYICCCJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;

/**
 * @author wuyiccc
 * @date 2020/1/11 23:04
 * 岂曰无衣，与子同袍~
 */
@Controller
public class BaseController {

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMENT_PAGE_SIZE = 10;
}
