package com.karat.cn.service;

import com.karat.cn.pojo.Tag;
import com.karat.cn.util.dto.TagRequset;
import com.karat.cn.util.vo.ResultVo;

public interface TagService {


	@SuppressWarnings("rawtypes")
	ResultVo addTag(TagRequset tag);
	
	@SuppressWarnings("rawtypes")
	ResultVo delTag(String id);
	
	@SuppressWarnings("rawtypes")
	ResultVo updateTag(TagRequset tag);
	
	Tag selectById(String id);
	
	@SuppressWarnings("rawtypes")
	ResultVo selectAllTag();
	
	@SuppressWarnings("rawtypes")
	ResultVo delTagByName(String name);
}
