package com.karat.cn.service.impl;

/*import java.util.ArrayList;*/
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.karat.cn.dto.TagDto;
import com.karat.cn.pojo.Tag;
/*import com.karat.cn.redis.dao.JedisClient;
import com.karat.cn.redis.redisKey.RedisKey;*/
import com.karat.cn.service.TagService;
import com.karat.cn.vo.ResultVOUtil;
import com.karat.cn.vo.ResultVo;

@Repository
public class TageServiceImpl implements TagService{

	@Autowired
	private MongoTemplate mongoTemplate;
	/*@Autowired
	private JedisClient redis;*/
	
	@SuppressWarnings("rawtypes")
	@Override
	public ResultVo addTag(TagDto tagDto) {
		// TODO Auto-generated method stub
		Tag tag=new Tag();
		BeanUtils.copyProperties(tagDto, tag);
		mongoTemplate.insert(tag);
		return ResultVOUtil.success(null);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResultVo delTag(String id) {
		// TODO Auto-generated method stub
		Tag tag=mongoTemplate.findById(id, Tag.class);
		if(tag!=null){
			mongoTemplate.remove(tag);
			return ResultVOUtil.success(null);
		}else{
			return ResultVOUtil.error(201,"当前标签不存在");
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResultVo updateTag(TagDto tag) {
		// TODO Auto-generated method stub
		Tag t=mongoTemplate.findById(tag.getId(), Tag.class);
		t.setName(tag.getName());
		t.setWeigth(tag.getWeigth());
		t.setState(tag.getState());
		t.setCreateDate(tag.getCreateDate());
		mongoTemplate.save(tag);
		return ResultVOUtil.success(null);
	}

	@Override
	public Tag selectById(String id) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, Tag.class);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResultVo selectAllTag() {
		// TODO Auto-generated method stub
		/*Set<String> set=redis.smembers(RedisKey.TAG);
		if(set.size()>0) {
			List<Tag> tags=new ArrayList<>();
			set.forEach(i->{
				tags.add(JSONObject.parseObject(i, Tag.class));//json字符串转对象
			});
			return ResultVOUtil.success(tags);
		}else {*/
			//没有缓存
			List<Tag> tags=mongoTemplate.findAll(Tag.class);
			if(tags.size()>0){
				Set<String> tagList=new HashSet<>();
				tags.forEach(t->{
					tagList.add(JSONObject.toJSONString(t));//对象转json
				});
				//添加缓存
				//redis.sadd(RedisKey.TAG,tagList);
				return ResultVOUtil.success(tags);
			}else{
				return ResultVOUtil.error(202,"数据为空");
			}
		//}		
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public ResultVo delTagByName(String name) {
		// TODO Auto-generated method stub
		ResultVo vo=null;
		Query query=new Query();
		query.addCriteria(Criteria.where("name").is(name));
		Tag tag=mongoTemplate.findOne(query, Tag.class);
		if(tag!=null){
			mongoTemplate.remove(tag);
			vo=ResultVOUtil.success(null);
		}else{
			vo=ResultVOUtil.error(201,"当前标签不存在");
		}
		return vo;
	}

}
