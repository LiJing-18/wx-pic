package com.karat.cn.service;

import com.karat.cn.pojo.Photo;
import com.karat.cn.vo.ResultVo;

public interface PhotoService {

	@SuppressWarnings("rawtypes")
	ResultVo addPhoto(Photo photo);
	
	@SuppressWarnings("rawtypes")
	ResultVo selectAllPhoto();
	
	@SuppressWarnings("rawtypes")
	ResultVo selectById(String id);
}
