package com.ann.function.network.bean;

/**
 * 用户信息
 */
public class UserInfo {

    public Long userId;

    public String name;

    public String nickName;

    public String mail;

    public String phone;

    public UserInfo() {
    }

    public UserInfo(Long userId, String name, String nickName, String mail, String phone) {
        this.userId = userId;
        this.name = name;
        this.nickName = nickName;
        this.mail = mail;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mail='" + mail + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
