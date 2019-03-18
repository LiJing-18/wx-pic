package com.karat.cn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karat.cn.service.GoodsService;

import io.swagger.annotations.Api;

@RequestMapping("goods")
@RestController
@Api("商品秒杀")
public class SecKillGoodsController {

	@Autowired
	private GoodsService service;
	
	
}
