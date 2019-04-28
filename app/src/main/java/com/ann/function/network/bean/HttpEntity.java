package com.ann.function.network.bean;

import java.io.Serializable;


/**
 * 实体类基类
 */
public class HttpEntity<T> implements Serializable {

    /**
     * 提示消息
     */
    public String message;

    /**
     * 1：success  0：失败
     */
    public int code;

    /**
     * 具体数据类
     */
    public T data;

    public HttpEntity() {
    }

    public HttpEntity(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }


    @Override
    public String toString() {
        return "HttpEntity{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", data=" + data.toString() +
                '}';
    }
}
