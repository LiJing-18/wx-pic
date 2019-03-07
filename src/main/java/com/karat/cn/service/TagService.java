package com.karat.cn.service;

import com.karat.cn.dto.TagDto;
import com.karat.cn.pojo.Tag;
import com.karat.cn.vo.ResultVo;

public interface TagService {

	@SuppressWarnings("rawtypes")
	ResultVo addTag(TagDto tag);
	
	@SuppressWarnings("rawtypes")
	ResultVo delTag(String id);
	
	@SuppressWarnings("rawtypes")
	ResultVo updateTag(TagDto tag);
	
	Tag selectById(String id);
	
	@SuppressWarnings("rawtypes")
	ResultVo selectAllTag();
	
	@SuppressWarnings("rawtypes")
	ResultVo delTagByName(String name);
}
