package com.karat.cn.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.karat.cn.util.QiniuUtil;
import com.karat.cn.vo.ResultVOUtil;
import com.karat.cn.vo.ResultVo;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("qiniu")
@Api(tags="七牛接口")
public class QiniuController {

	@SuppressWarnings("rawtypes")
	@RequestMapping(value="getQiniuToken",produces="html/text;charset=UTF-8")
	public String getQiniuToken(){
		String uptoken = QiniuUtil.getUpToken0();
		System.out.println(uptoken);
		ResultVo vo=null;
		if(StringUtils.isNotBlank(uptoken)){
			vo=ResultVOUtil.success(uptoken);
		}else{
			vo=ResultVOUtil.error(201,"获取token失败");
		}		
		return JSON.toJSONString(vo);
	}
}
