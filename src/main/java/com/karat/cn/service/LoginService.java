package com.karat.cn.service;

import com.karat.cn.dto.UserLoginRequest;
import com.karat.cn.dto.UserLoginResponse;
import com.karat.cn.dto.UserRegisterRequest;
import com.karat.cn.dto.UserRegisterResponse;

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
}
