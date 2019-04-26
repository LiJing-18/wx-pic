package com.karat.cn.util.dto;

import java.io.Serializable;
import com.karat.cn.abs.AbstractResponse;

/**
 * 返回信息
 * @author 开发
 *
 */
public class CheckAuthResponse extends AbstractResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
