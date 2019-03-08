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
	/**
	 * 可以配置头文件application/json(调通)
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="insert",produces="html/text;charset=UTF-8")
	public String insert(@RequestBody TagDto dto){
		return JSON.toJSONString(service.addTag(dto));
	}
	/**
	 * (暂时调不通了)
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="add",produces="html/text;charset=UTF-8")
	public String add(TagDto dto){
		return JSON.toJSONString(service.addTag(dto));
	}
	/**
	 * 定义头文件格式application/x-www-form-urlencoded才可以得到name
	 * @param name
	 * @return
	 */
	@RequestMapping(value="del",produces="html/text;charset=UTF-8")
	public String del(String name){
		System.out.println(name);
		return JSON.toJSONString(service.delTagByName(name));
	}
}
