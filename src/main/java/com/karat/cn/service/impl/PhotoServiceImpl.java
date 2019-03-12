package com.karat.cn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.karat.cn.pojo.Photo;
import com.karat.cn.service.PhotoService;
import com.karat.cn.util.ResultVOUtil;
import com.karat.cn.vo.ResultVo;

@Repository
public class PhotoServiceImpl implements PhotoService{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@SuppressWarnings("rawtypes")
	@Override
	public ResultVo addPhoto(Photo photo) {
		// TODO Auto-generated method stub
		mongoTemplate.insert(photo);
		return ResultVOUtil.success(null);
	}

}
