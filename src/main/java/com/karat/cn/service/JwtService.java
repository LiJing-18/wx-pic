package com.karat.cn.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.karat.cn.util.jwt.JWTTokenUtil;
import com.karat.cn.util.jwt.JwtInfo;

@Component
public class JwtService {

	@Value("${jwt.expire}")
	private int expire;//过期时间
	
	//加密获得token
	public String generatorToken(JwtInfo jwtInfo){
		return JWTTokenUtil.generatorToken(jwtInfo, expire);
	}
	
	//解密token中存入的内容
	public JwtInfo getTokenInfo(String token){
		return JWTTokenUtil.getTokenInfo(token);
	}
	
}

