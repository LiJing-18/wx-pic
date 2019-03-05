package com.karat.cn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.karat.cn.dto.TagDto;
import com.karat.cn.pojo.Tag;
import com.karat.cn.service.TagService;
import com.karat.cn.vo.ResultVo;

@Repository
public class TageServiceImpl implements TagService{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public ResultVo addTag(TagDto tag) {
		// TODO Auto-generated method stub
		Tag t=new Tag(tag.getName(),tag.getWeigth(),tag.getState(),tag.getCreateDate());
		mongoTemplate.insert(t);
		return new ResultVo("添加成功","200");
	}

	@Override
	public ResultVo delTag(String id) {
		ResultVo resultVo=null;
		// TODO Auto-generated method stub
		Tag tag=mongoTemplate.findById(id, Tag.class);
		if(tag!=null){
			mongoTemplate.remove(tag);
			resultVo=new ResultVo("删除成功","200");
		}else{
			resultVo=new ResultVo("当前标签不存在","201");
		}
		return resultVo;
	}

	@Override
	public ResultVo updateTag(TagDto tag) {
		// TODO Auto-generated method stub
		Tag t=mongoTemplate.findById(tag.getId(), Tag.class);
		t.setName(tag.getName());
		t.setWeigth(tag.getWeigth());
		t.setState(tag.getState());
		t.setCreateDate(tag.getCreateDate());
		mongoTemplate.save(tag);
		return new ResultVo("修改成功","200");
	}

	@Override
	public Tag selectById(String id) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, Tag.class);
	}

	@Override
	public List<Tag> selectAllTag() {
		// TODO Auto-generated method stub
		return mongoTemplate.findAll(Tag.class);
	}

}
