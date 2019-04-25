package com.karat.cn.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.karat.cn.base.BaseController;
import com.karat.cn.service.LoginService;
import com.karat.cn.util.dto.UserLoginRequest;
import com.karat.cn.util.dto.UserLoginResponse;
import com.karat.cn.util.vo.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 登陆注册
 * @author 开发
 *
 */
@Controller
@RequestMapping("index")
public class IndexController extends BaseController{
	
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    @Autowired
    LoginService loginService;



    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        if(request.getSession().getAttribute("user")==null){
        	System.out.println("跳转登陆页");
            return "login";
        }
        System.out.println("跳转首页");
        return "index";
    }

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(){
    	System.out.println("登录页");
        return "login";
    }

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
