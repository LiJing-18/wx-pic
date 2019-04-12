package com.karat.cn.util.jwt;
/**
 * 传递的对象
 * @author 开发
 *
 */
public class JwtInfo {

	//uid是需要放入到token中的属性
	private String uid;

	public JwtInfo() {
		super();
	}

	public JwtInfo(String uid) {
		super();
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
	
}
