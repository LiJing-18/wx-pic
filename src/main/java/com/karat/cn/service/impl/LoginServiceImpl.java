package com.karat.cn.service.impl;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.karat.cn.exception.ExceptionUtil;
import com.karat.cn.exception.ServiceException;
import com.karat.cn.exception.ValidateException;
import com.karat.cn.pojo.User;
import com.karat.cn.service.JwtService;
import com.karat.cn.service.LoginService;
import com.karat.cn.util.constants.Constants;
import com.karat.cn.util.constants.ResponseCodeEnum;
import com.karat.cn.util.dto.CheckAuthRequset;
import com.karat.cn.util.dto.CheckAuthResponse;
import com.karat.cn.util.dto.UserLoginRequest;
import com.karat.cn.util.dto.UserLoginResponse;
import com.karat.cn.util.dto.UserRegisterRequest;
import com.karat.cn.util.dto.UserRegisterResponse;
import com.karat.cn.util.jwt.JwtInfo;
import com.karat.cn.util.time.IdUtil;

@Repository
public class LoginServiceImpl implements LoginService{

	Logger logger= LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	JwtService service;
	
	
	//TODO 登陆
	@Override
	public UserLoginResponse login(UserLoginRequest userLoginRequest) {
		logger.info("begin UserCoreService.login,request:【"+userLoginRequest+"】");
		//TODO 返回信息vo
		UserLoginResponse response=new UserLoginResponse();
        try{
        	//TODO 登陆之前非空效验
            beforeValidate(userLoginRequest);
            //TODO 查看用户信息,判断用户状态
            User user=mongoTemplate.findOne(new Query().addCriteria(Criteria.where("username").is(userLoginRequest.getUsername())), User.class);
            if(user==null||!user.getPassword().equals(userLoginRequest.getPassword())){
                response.setCode(ResponseCodeEnum.USER_OR_PASSWORD_ERROR.getCode());
                response.setMsg(ResponseCodeEnum.USER_OR_PASSWORD_ERROR.getMsg());
                return response;
            }
            //TODO 返回用户信息
            response.setAvatar(user.getAvatar());
            response.setMobile(user.getMobile());
            response.setRealName(user.getRealname());
            response.setSex(user.getSex());
            //TODO 生成token并返回
            response.setToken(service.generatorToken(new JwtInfo(user.getId().toString())));
            //TODO 返回请求状态信息
            response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
            response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
            return response;
        }catch(Exception e){
            ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
            //TODO 返回请求状态信息
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }finally {
            logger.info("login response:【"+response+"】");
        }
        return response;
	}
	//TODO 注册
	@Override
	public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
		 logger.info("begin UserCoreService.register,request:【"+userRegisterRequest+"】");
		 //TODO 返回信息vo
	     UserRegisterResponse response=new UserRegisterResponse();
	     try{
	    	//TODO 注册之前非空效验
	        beforeRegisterValidate(userRegisterRequest);
	        //TODO bean注入数据
	        User user=new User();
	        user.setId(IdUtil.getUUID());
	        user.setUsername(userRegisterRequest.getUsername());
	        user.setPassword(userRegisterRequest.getPassword());
	        user.setRealname("小明");
	        user.setSex("男");
	        user.setMobile(userRegisterRequest.getMobile());
	        user.setAvatar("110");
	        user.setStatus(Constants.FORZEN_USER_STATUS);
	        user.setCreateTime(new Date());
	        //TODO 添加数据
	        mongoTemplate.insert(user);
	        //TODO 验证是否添加成功
	        User usr=mongoTemplate.findOne(new Query().addCriteria(Criteria.where("username").is(user.getUsername())), User.class);	            
	        if(usr!=null){
	        	 //TODO 返回请求状态信息
	             response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
	             response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
	             return  response;
	        }
	        //TODO 返回请求状态信息
	        response.setCode(ResponseCodeEnum.DATA_SAVE_ERROR.getCode());
	        response.setMsg(ResponseCodeEnum.DATA_SAVE_ERROR.getMsg());
	        return  response;
	     }catch(Exception e){
	        ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
	        //TODO 返回请求状态信息
	        response.setCode(serviceException.getErrorCode());
	        response.setMsg(serviceException.getErrorMessage());
	     }finally {
	        logger.info("register response:【"+response+"】");
	     }
	     return response;
	}
    
	//TODO 授权验证
	@Override
	public CheckAuthResponse checkAuth(CheckAuthRequset request) {
		logger.info("begin CheckAuthResponse.checkAuth,request:【"+request+"】");
		//TODO 返回信息vo
		CheckAuthResponse response=new CheckAuthResponse();
	     try{
	    	//TODO 授权验证之前非空效验
	    	beforeRegisterValidate(request);
	    	JwtInfo info=service.getTokenInfo(request.getToken());
	    	response.setUid(info.getUid());
	    	//TODO 返回请求状态信息
            response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
            response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());            
	     }catch(Exception e){
	        ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
	        //TODO 返回请求状态信息
	        response.setCode(serviceException.getErrorCode());
	        response.setMsg(serviceException.getErrorMessage());
	     }finally {
	        logger.info("register response:【"+response+"】");
	     }
	     return response;
	}
	
	
	
	
	
	
	
	
	
	
	
	//TODO 登陆之前非空效验
    private void beforeValidate(UserLoginRequest request){
        if(null==request){
            throw new ValidateException("请求对象为空");
        }
        if(StringUtils.isEmpty(request.getUsername())){
            throw new ValidateException("用户名为空");
        }
        if(StringUtils.isEmpty(request.getPassword())){
            throw new ValidateException("密码为空");
        }
    }   
    //TODO 注册之前非空效验
    private void beforeRegisterValidate(UserRegisterRequest request){
        if(null==request){
            throw new ValidateException("请求对象为空");
        }
        if(StringUtils.isEmpty(request.getUsername())){
            throw new ValidateException("用户名为空");
        }
        if(StringUtils.isEmpty(request.getPassword())){
            throw new ValidateException("密码为空");
        }
        if(StringUtils.isEmpty(request.getMobile())){
            throw new ValidateException("密码为空");
        }
    }
    //TODO 授权验证之前非空效验
    private void beforeRegisterValidate(CheckAuthRequset request){
        if(null==request){
            throw new ValidateException("请求对象为空");
        }
        if(StringUtils.isEmpty(request.getToken())){
            throw new ValidateException("token为空");
        }
    }
}
