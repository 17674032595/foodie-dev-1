package com.wuyiccc.utils;

/**
 * @author wuyiccc
 * @date 2019/12/23 19:54
 * 岂曰无衣，与子同袍~
 */

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

public class MD5Utils {

    /**
     * @Description: 对字符串进行md5和base64加密
     */

    public static String getMD5Str(String strValue) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5"); //获取md5
        String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes())); //先进行md5，再base64 ，返回加密之后的密文base64
        return newstr;
    }

}