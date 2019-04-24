package com.karat.cn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.karat.cn.service.JokeService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("duanzi")
@Api(tags="段子接口")
public class DuanZiController {
	@Autowired
	private JokeService service;
	
	/**
	 * 查看所有段子
	 * @return
	 */
	@RequestMapping(value="duanZiSelectAll",produces="html/text;charset=UTF-8")
	public String duanZiSelectAll(){
		System.out.println("查看所有段子");
		return JSON.toJSONString(service.selectAllJoke());
	}
}
