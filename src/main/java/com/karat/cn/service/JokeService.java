package com.karat.cn.service;


import com.karat.cn.util.vo.ResultVo;

public interface JokeService {
	
	//查看所有段子
	@SuppressWarnings("rawtypes")
	ResultVo selectAllJoke();
}
