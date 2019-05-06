package com.karat.cn.controller;

public class BaseController {
	
	ThreadLocal<String> uidLocal=new ThreadLocal<>();

	public String getUid() {
		return uidLocal.get();
	}

	public void setUid(String uid) {
		uidLocal.set(uid);
	}
	
	
}
