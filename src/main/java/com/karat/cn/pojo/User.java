package com.karat.cn.pojo;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户信息
 * @author 开发
 *
 */
@Document(collection="user")
public class User {
	//主键
    private String id;
    //用户名
    private String username;
    //用户密码
    private String password;
    //真是姓名
    private String realname;
    //虚拟姓名
    private String avatar;
    //用户电话
    private String mobile;
    //用户性别
    private String sex;
    //用户状态
    private Integer status;
    //创建时间
    private Date createTime;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex='" + sex + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
