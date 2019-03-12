package com.karat.cn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.karat.cn.pojo.Photo;
import com.karat.cn.service.PhotoService;
import com.karat.cn.util.ResultVOUtil;
import com.karat.cn.vo.ResultVo;
import com.qiniu.util.StringUtils;

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

	@SuppressWarnings("rawtypes")
	@Override
	public ResultVo selectAllPhoto() {
		ResultVo vo=null;
		// TODO Auto-generated method stub
		List<Photo> photos=mongoTemplate.findAll(Photo.class);
		if(photos.size()>0){
			vo=ResultVOUtil.success(photos);
		}else{
			vo=ResultVOUtil.error(201,"数据为空");
		}
		return vo;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResultVo selectById(String id) {
		// TODO Auto-generated method stub
		ResultVo vo=null;
		if(!StringUtils.isNullOrEmpty(id)){
			Photo photo=mongoTemplate.findById(id, Photo.class);
			if(photo!=null){
				vo=ResultVOUtil.success(photo);
			}else{
				vo=ResultVOUtil.error(201, "信息为null");
			}
		}else{
			vo=ResultVOUtil.error(201, "请检查传入参数");
		}
		return vo;
	}

}
