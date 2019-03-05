package com.karat.cn.service;

import java.util.List;

import com.karat.cn.dto.TagDto;
import com.karat.cn.pojo.Tag;
import com.karat.cn.vo.ResultVo;

public interface TagService {

	ResultVo addTag(TagDto tag);
	
	ResultVo delTag(String id);
	
	ResultVo updateTag(TagDto tag);
	
	Tag selectById(String id);
	
	List<Tag> selectAllTag();
}
