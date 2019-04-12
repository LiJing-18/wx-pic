package com.karat.cn.util.jwt;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.joda.time.DateTime;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**
 * 生成token的工具类
 * @author 开发
 *
 */
public class JWTTokenUtil {
	
	 //通过HS256算法以及aone字符串生成一个key(key在生成tolen中使用)
	 private static Key getKeyInstance(){
		 SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;
		 byte[] dc=DatatypeConverter.parseBase64Binary("aone");
		 return new SecretKeySpec(dc, signatureAlgorithm.getJcaName());
	 }
	
	 //生成token
	 public static String generatorToken(JwtInfo jwtInfo,int expire){	    
		String token=Jwts.builder()
				.claim(JwtConstance.JWT_KEY_USER_ID, jwtInfo.getUid())
				.setExpiration(DateTime.now().plusSeconds(expire).toDate())
				.signWith(SignatureAlgorithm.HS256,getKeyInstance()).compact();				
	    return token;
	 }
	 
	 //根据token获取token中存入的信息 
	 public static JwtInfo getTokenInfo(String token){
		 Jws<Claims> claimsJws=Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
		 Claims claims=claimsJws.getBody();
		 return new JwtInfo(claims.get(JwtConstance.JWT_KEY_USER_ID).toString());
	 }
}
