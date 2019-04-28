package com.ann.function.network.bean;

/**
 * 登录信息
 */
public class LoginInfo {

    public String token;

    public UserInfo userInfo;

    public LoginInfo() {
    }

    public LoginInfo(String token, UserInfo userInfo) {
        this.token = token;
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "token='" + token + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }
}
