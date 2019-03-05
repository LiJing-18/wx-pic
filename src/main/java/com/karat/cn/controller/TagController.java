package com.karat.cn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.karat.cn.dto.TagDto;
import com.karat.cn.pojo.Tag;
import com.karat.cn.service.TagService;
import com.karat.cn.vo.ResultVo;

@RestController
@RequestMapping("tag")
public class TagController {

	@Autowired
	private TagService service;
	
	@RequestMapping("tagSelectAll")
	@ResponseBody
	public List<Tag> tagSelectAll(){
		return service.selectAllTag();
	}
	
	@RequestMapping("insert")
	@ResponseBody
	public ResultVo insert(Tag tag){
		TagDto dto=new TagDto(tag.getName(),tag.getWeigth(),tag.getState(),tag.getCreateDate());
		return service.addTag(dto);
	}
}
