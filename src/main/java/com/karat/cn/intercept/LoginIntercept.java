package com.karat.cn.intercept;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.karat.cn.service.LoginService;
import com.karat.cn.util.dto.CheckAuthRequset;
import com.karat.cn.util.dto.CheckAuthResponse;
import com.karat.cn.util.support.Anonymous;
import com.karat.cn.util.support.CookieUtil;

/**
 * 登陆拦截器
 * @author 开发
 *
 */
public class LoginIntercept extends HandlerInterceptorAdapter{

	private final String ACCESS_TOKEN="access_token";
	
	@Autowired
	LoginService loginService;
	
	
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
		HandlerMethod handlerMethod=(HandlerMethod)handler;
		
		
		
		
		if(isAnonymous(handlerMethod)){
			return true;
		}
		String accessToken=CookieUtil.getCookie(request, ACCESS_TOKEN);
		if(StringUtils.isEmpty(accessToken)){
			if(CookieUtil.isAjax(request)){
				JSONObject object=new JSONObject();
				object.put("code", "-1");
				object.put("msg", "没有登陆");
				response.getWriter().write(object.toString());
				return false;
			}
			response.sendRedirect("login");//跳转页面
			return false;
		}	
		CheckAuthRequset checkAuthRequset=new CheckAuthRequset();
		checkAuthRequset.setToken(accessToken);
		
		CheckAuthResponse checkAuthResponse=loginService.checkAuth(checkAuthRequset);
		if("000000".equals(checkAuthResponse.getCode())){
			
		}
		
		
		return super.preHandle(request, response, handler);
	}
	
	private boolean isAnonymous(HandlerMethod handlerMethod){
		Object action=handlerMethod.getBean();
		Class c=action.getClass();
		if(c.getAnnotation(Anonymous.class)!=null){
			//返回空表示不需要拦截
			return true;
		}
		Method method=handlerMethod.getMethod();	
		return method.getAnnotation(Anonymous.class)!=null;
	}
	
}
