package com.karat.cn.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.karat.cn.constants.Constants;
import com.karat.cn.constants.ResponseCodeEnum;
import com.karat.cn.dto.UserLoginRequest;
import com.karat.cn.dto.UserLoginResponse;
import com.karat.cn.dto.UserRegisterRequest;
import com.karat.cn.dto.UserRegisterResponse;
import com.karat.cn.exception.ExceptionUtil;
import com.karat.cn.exception.ServiceException;
import com.karat.cn.exception.ValidateException;
import com.karat.cn.pojo.User;
import com.karat.cn.service.LoginService;

@Repository
public class LoginServiceImpl implements LoginService{

	
	@Resource
	private MongoTemplate mongoTemplate;
	Logger logger= LoggerFactory.getLogger(LoginService.class);
	
	
	
	//登陆
	@Override
	public UserLoginResponse login(UserLoginRequest userLoginRequest) {
		logger.info("begin UserCoreService.login,request:【"+userLoginRequest+"】");
        UserLoginResponse response=new UserLoginResponse();
        try{
            beforeValidate(userLoginRequest);

            User user=mongoTemplate.findOne(new Query().addCriteria(Criteria.where("username").is(userLoginRequest.getUsername())), User.class);
            if(user==null||!user.getPassword().equals(userLoginRequest.getPassword())){
                response.setCode(ResponseCodeEnum.USER_OR_PASSWORD_ERROR.getCode());
                response.setMsg(ResponseCodeEnum.USER_OR_PASSWORD_ERROR.getMsg());
                return response;
            }
            //todo 判断用户状态
            response.setAvatar(user.getAvatar());
            response.setMobile(user.getMobile());
            response.setRealName(user.getRealname());
            response.setSex(user.getSex());

            response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
            response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
            return response;
        }catch(Exception e){
            ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }finally {
            logger.info("login response:【"+response+"】");
        }
        return response;
	}
	//注册
	@Override
	public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
		 logger.info("begin UserCoreService.register,request:【"+userRegisterRequest+"】");

	        UserRegisterResponse response=new UserRegisterResponse();
	        try{
	            beforeRegisterValidate(userRegisterRequest);

	            User user=new User();
	            user.setUsername(userRegisterRequest.getUsername());
	            user.setPassword(userRegisterRequest.getPassword());
	            user.setStatus(Constants.FORZEN_USER_STATUS);
	            user.setCreateTime(new Date());

	            mongoTemplate.insert(user);;//添加数据
	            User usr=mongoTemplate.findOne(new Query().addCriteria(Criteria.where("username").is(user.getUsername())), User.class);	            
	            if(usr!=null){
	                response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
	                response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
	                return  response;
	            }
	            response.setCode(ResponseCodeEnum.DATA_SAVE_ERROR.getCode());
	            response.setMsg(ResponseCodeEnum.DATA_SAVE_ERROR.getMsg());
	            return  response;
	        }catch (DuplicateKeyException e){
	            //TODO 用户名重复
	        }catch(Exception e){
	            ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
	            response.setCode(serviceException.getErrorCode());
	            response.setMsg(serviceException.getErrorMessage());
	        }finally {
	            logger.info("register response:【"+response+"】");
	        }

	        return response;
	}

	/**
	 * 登陆之前非空效验
	 * @param request
	 */
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
    
    /**
     * 注册之前非空效验
     * @param request
     */
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
}
