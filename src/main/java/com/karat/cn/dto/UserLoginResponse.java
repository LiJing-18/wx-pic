package com.karat.cn.dto;
import java.io.Serializable;

import com.karat.cn.abs.AbstractResponse;
/**
 * 登陆返回信息(vo)
 * @author 开发
 *
 */
public class UserLoginResponse extends AbstractResponse implements Serializable{

    private static final long serialVersionUID = 2073944735436288078L;
    private String realName;

    private String avatar;

    private String mobile;

    private String sex;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "UserLoginResponse{" +
                "realName='" + realName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex='" + sex + '\'' +
                "} " + super.toString();
    }
}
