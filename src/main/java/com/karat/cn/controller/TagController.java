package com.karat.cn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.karat.cn.dto.TagDto;
import com.karat.cn.service.TagService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("tag")
@Api(tags="标签接口")
public class TagController {

	@Autowired
	private TagService service;
	
	@RequestMapping(value="tagSelectAll",produces="html/text;charset=UTF-8")
	public String tagSelectAll(){
		return JSON.toJSONString(service.selectAllTag());
	}
	
	@RequestMapping(value="insert",produces="html/text;charset=UTF-8")
	public String insert(@RequestBody TagDto dto){
		return JSON.toJSONString(service.addTag(dto));
	}
	
	@RequestMapping(value="add",produces="html/text;charset=UTF-8")
	public String add(TagDto dto){
		return JSON.toJSONString(service.addTag(dto));
	}
	
	
}
