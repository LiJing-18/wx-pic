package com.karat.cn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.karat.cn.pojo.Photo;
import com.karat.cn.service.PhotoService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("photo")
@Api("图片接口")
public class PhotoController {
	@Autowired
	private PhotoService service;
	
	@RequestMapping(value="uploadPhoto",produces="html/text;charset=UTF-8")
	public String uploadPhoto(@RequestBody Photo photo){
		return JSON.toJSONString(service.addPhoto(photo));
	}
	
	@RequestMapping(value="selectPhoto",produces="html/text;charset=UTF-8")
	public String selectPhoto(){
		return JSON.toJSONString(service.selectAllPhoto());
	}

	@RequestMapping(value="selectById",produces="html/text;charset=UTF-8")
	public String selectById(String id){
		return JSON.toJSONString(service.selectById(id));
	}
}
