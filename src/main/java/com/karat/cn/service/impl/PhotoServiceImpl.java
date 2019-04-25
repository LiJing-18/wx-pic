package com.karat.cn.service.impl;

/*import java.util.ArrayList;*/
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.karat.cn.pojo.Photo;
/*import com.karat.cn.redis.dao.JedisClient;
import com.karat.cn.redis.redisKey.RedisKey;*/
import com.karat.cn.service.PhotoService;
import com.karat.cn.util.vo.ResultVOUtil;
import com.karat.cn.util.vo.ResultVo;
import com.qiniu.util.StringUtils;

@Repository
public class PhotoServiceImpl implements PhotoService{

	@Autowired
	private MongoTemplate mongoTemplate;
	/*@Autowired
	private JedisClient redis;*/
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public ResultVo addPhoto(Photo photo) {
		// TODO Auto-generated method stub
		mongoTemplate.insert(photo);
		return ResultVOUtil.success(null);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResultVo selectAllPhoto() {
		ResultVo vo=null;
		// TODO Auto-generated method stub
		/*Set<String> set=redis.smembers(RedisKey.PHOTO);
		if(set.size()>0) {
			System.out.println("all有缓存");
			List<Photo> photos=new ArrayList<>();
			set.forEach(i->{
				photos.add(JSONObject.parseObject(i, Photo.class));//json字符串转对象
			});
			return ResultVOUtil.success(photos);
		}else {*/
			System.out.println("all无缓存");
			List<Photo> photos=mongoTemplate.findAll(Photo.class);
			if(photos.size()>0){
				Set<String> photoList=new HashSet<>();
				photos.forEach(p->{
					photoList.add(JSONObject.toJSONString(p));//对象转json
					//单品缓存
					//redis.set(RedisKey.SHOP+p.getId(), JSONObject.toJSONString(p));
				});
				//添加集合缓存
				//redis.sadd(RedisKey.PHOTO,photoList);
				vo=ResultVOUtil.success(photos);
			}else{
				vo=ResultVOUtil.error(201,"数据为空");
			}
		//}
		return vo;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResultVo selectById(String id) {
		ResultVo vo=null;
		// TODO Auto-generated method stub
		//String strId=redis.get(RedisKey.SHOP+id);
		//if(StringUtils.isNullOrEmpty(strId)) {
			System.out.println("无缓存");
			if(!StringUtils.isNullOrEmpty(id)){
				Photo photo=mongoTemplate.findById(id, Photo.class);
				if(photo!=null){
					//redis.set(RedisKey.SHOP+photo.getId(), JSONObject.toJSONString(photo));
					vo=ResultVOUtil.success(photo);
				}else{
					vo=ResultVOUtil.error(201, "信息为null");
				}
			}else{
				vo=ResultVOUtil.error(201, "请检查传入参数");
			}
		/*}else {
			System.out.println("有缓存");
			vo=ResultVOUtil.success(JSONObject.parseObject(strId, Photo.class));
		}*/
		return vo;
	}

}
