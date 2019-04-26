package com.karat.cn.service;

import com.karat.cn.util.dto.CheckAuthRequset;
import com.karat.cn.util.dto.CheckAuthResponse;
import com.karat.cn.util.dto.UserLoginRequest;
import com.karat.cn.util.dto.UserLoginResponse;
import com.karat.cn.util.dto.UserRegisterRequest;
import com.karat.cn.util.dto.UserRegisterResponse;

public interface LoginService {

	/**
     * 用户登录操作
     * @param userLoginRequest
     * @return
     */
    UserLoginResponse login(UserLoginRequest userLoginRequest);


    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);
    
    /**
     * 授权验证
     * @param request
     * @return
     */
    CheckAuthResponse checkAuth(CheckAuthRequset request);
}
