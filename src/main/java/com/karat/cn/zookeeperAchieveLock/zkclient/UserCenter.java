package com.karat.cn.zookeeperAchieveLock.zkclient;

import java.io.Serializable;

/**
 * 选举的机器
 */
public class UserCenter implements Serializable{

    private static final long serialVersionUID = -1776114173857775665L;
    private int id; //机器信息

    private String name;//机器名称

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserCenter [id=" + id + ", name=" + name + "]";
	}
 
}
