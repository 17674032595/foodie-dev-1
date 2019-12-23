package com.wuyiccc.utils;

/**
 * @author wuyiccc
 * @date 2019/12/23 19:02
 * 岂曰无衣，与子同袍~
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 自定义响应数据结构
 * 前端接受此类数据(json object)后，可自行根据业务取实现相关功能
 *
 *
 * 200: 成功
 * 500: 错误 , 错误信息在msg字段中
 * 501: bean验证错误，不管多少个错误都以map形式返回
 * 502: 拦截器拦截到用户token出错
 * 555：异常抛出信息
 * 556： 用户qq校验异常
 *
 */
public class WUYICCCJSONResult  {

    //定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    //响应业务状态
    private Integer status;

    //响应消息
    private String msg;

    //响应中的数据
    private Object data;

    @JsonIgnore
    private String ok; //不使用

    public static WUYICCCJSONResult build(Integer status, String msg, Object data) {
        return new WUYICCCJSONResult(status, msg, data);
    }

    public static WUYICCCJSONResult build(Integer status, String msg, Object data, String ok) {
        return new WUYICCCJSONResult(status, msg, data, ok);
    }

    public static WUYICCCJSONResult ok(Object data) {
        return new WUYICCCJSONResult(data);
    }

    public static WUYICCCJSONResult ok() {
        return new WUYICCCJSONResult(null);
    }

    public static WUYICCCJSONResult errorMsg(String msg) {
        return new WUYICCCJSONResult(500, msg, null);
    }

    public static WUYICCCJSONResult errorMap(Object data) {
        return new WUYICCCJSONResult(501, "error", data);
    }

    public static WUYICCCJSONResult errorTokenMsg(String msg) {
        return new WUYICCCJSONResult(502, msg, null);
    }

    public static WUYICCCJSONResult errorException(String msg) {
        return new WUYICCCJSONResult(555, msg, null);
    }

    public static WUYICCCJSONResult errorUserQQ(String msg) {
        return new WUYICCCJSONResult(556, msg, null);
    }

    public WUYICCCJSONResult() {

    }

    public WUYICCCJSONResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public WUYICCCJSONResult(Integer status, String msg, Object data, String ok) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.ok = ok;
    }

    public WUYICCCJSONResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }


}
