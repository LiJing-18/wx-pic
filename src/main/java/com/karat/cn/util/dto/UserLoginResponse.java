package com.karat.cn.util.dto;
import java.io.Serializable;

import com.karat.cn.abs.AbstractResponse;
/**
 * 登陆返回给前端的信息(vo)
 * @author 开发
 *
 */
public class UserLoginResponse extends AbstractResponse implements Serializable{

    private static final long serialVersionUID = 2073944735436288078L;
    //用户真是姓名
    private String realName;
    //用户虚拟姓名
    private String avatar;
    //用户电话
    private String mobile;
    //用户性别
    private String sex;
    /*****************************************/
    private	String token;//返回给前端的token

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
    
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
