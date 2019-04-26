package com.karat.cn.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.karat.cn.base.BaseController;
import com.karat.cn.service.LoginService;
import com.karat.cn.util.dto.UserLoginRequest;
import com.karat.cn.util.dto.UserLoginResponse;
import com.karat.cn.util.dto.UserRegisterRequest;
import com.karat.cn.util.dto.UserRegisterResponse;
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


    /**
     * 跳转页面
     * @param request
     * @return
     */
    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        if(request.getSession().getAttribute("user")==null){
        	System.out.println("跳转登陆页");
            return "main";
        }
        System.out.println("跳转首页");
        return "index";
    }
    @RequestMapping(value = "regis",method = RequestMethod.GET)
    public String regis(HttpServletRequest request){
        if(request.getSession().getAttribute("user")==null){
        	System.out.println("跳转注册");
            return "register";
        }
        System.out.println("跳转首页");
        return "index";
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
    public ResponseEntity login(String txtUser,String userPwd,HttpServletResponse response){
    	System.out.println("登录页");
    	UserLoginRequest request=new UserLoginRequest();
    	request.setUsername(txtUser);
    	request.setPassword(userPwd);
        //登陆
        UserLoginResponse loginResponse=loginService.login(request);
        
        if("000000".equals(loginResponse.getCode())){
        	response.addHeader("Set-Cookie", "access_token:"+loginResponse.getToken()+":Path=/:HttpOnly");
        }
        return ResponseEntity.ok(loginResponse);
    }

    /**
     * 登陆
     * @param request
     * @param loginname
     * @param password
     * @return
     */
    @RequestMapping(value="submitLogin",method=RequestMethod.POST)
    @ResponseBody
    public ResponseData submitLogin(HttpServletRequest request,String loginname,String password){
        System.out.println("表单提交");
        System.out.println(loginname+"=***="+password);
        
    	UserLoginRequest loginRequest=new UserLoginRequest();
        loginRequest.setUsername(loginname);
        loginRequest.setPassword(password);
        //登陆
        UserLoginResponse loginResponse=loginService.login(loginRequest);
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
