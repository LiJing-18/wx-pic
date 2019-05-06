package com.karat.cn.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.karat.cn.base.BaseController;
import com.karat.cn.service.LoginService;
import com.karat.cn.util.dto.UserLoginRequest;
import com.karat.cn.util.dto.UserLoginResponse;
import com.karat.cn.util.dto.UserRegisterRequest;
import com.karat.cn.util.dto.UserRegisterResponse;
import com.karat.cn.util.support.Anonymous;
import com.karat.cn.util.vo.ResponseData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆注册
 * @author 开发
 *
 */
@Controller
@RequestMapping("index")
public class IndexController extends BaseController{

    @Autowired
    LoginService loginService;
    @Autowired
    JmsTemplate jmsTemplate;
    

    /**
     * 跳转登陆页面
     * @param request
     * @return
     */
    @RequestMapping("index")
    public String index(Model model){
        System.out.println("跳转登陆页面");
        return "login";
    }
    
    /**
     * jwt登陆
     * @param txtUser
     * @param userPwd
     * @param response
     * @return
     */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/login")
	@Anonymous
    public ResponseEntity login(String txtUser,String userPwd,HttpServletResponse response){
    	System.out.println("登录开始");
    	UserLoginRequest request=new UserLoginRequest();
    	request.setUsername(txtUser);
    	request.setPassword(userPwd);
        //登陆
        UserLoginResponse loginResponse=loginService.login(request);
        //判断是否成功
        if("000000".equals(loginResponse.getCode())){
        	//设置头信息中的token
        	response.addHeader("Set-Cookie", "access_token:"+loginResponse.getToken()+":Path=/:HttpOnly");
        }
        return ResponseEntity.ok(loginResponse);
    }

    
    /**
     * 注册
     * @param request
     * @param username
     * @param password
     * @param mobile
     * @return
     */
    @RequestMapping(value="register",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData register(HttpServletRequest request,String username,String password,String mobile){
        System.out.println("注册提交");
        System.out.println(username+"=***="+password);
        
        UserRegisterRequest userRequest=new UserRegisterRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setMobile(mobile);
        //注册
        UserRegisterResponse loginResponse=loginService.register(userRequest);
        //返回数据
        ResponseData data=new ResponseData();
        data.setMessage(loginResponse.getMsg());
        data.setCode(loginResponse.getCode());
        data.setData("/");
        
        if("000000".equals(loginResponse.getCode())){
            request.getSession().setAttribute("user","user");
        }
        return data;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * 退出
     * @return
     */
    @RequestMapping(value="logout",method =RequestMethod.GET)
    public String logout(HttpServletRequest request){
        try {
            request.getSession().removeAttribute("user");
        } catch (Exception e) {
            LOG.error("errorMessage:" + e.getMessage());
        }
        return redirectTo("/index/login.shtml");
    }
}
