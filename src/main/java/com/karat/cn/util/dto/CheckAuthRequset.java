package com.karat.cn.util.dto;

import java.io.Serializable;

import com.karat.cn.abs.AbstractRequest;

/**
 * 传入信息
 * @author 开发
 *
 */
public class CheckAuthRequset extends AbstractRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
