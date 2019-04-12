package com.karat.cn.dto;
import java.io.Serializable;

import com.karat.cn.abs.AbstractRequest;

/**
 * 注册请求信息
 * @author 开发
 *
 */
public class UserRegisterRequest extends AbstractRequest implements Serializable{

    private static final long serialVersionUID = -4807481139973253990L;

    private String username;

    private String password;

    private String mobile;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "UserRegisterRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                "} " + super.toString();
    }
}
