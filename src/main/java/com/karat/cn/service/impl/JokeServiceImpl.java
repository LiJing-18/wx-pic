package com.karat.cn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.karat.cn.pojo.Joke;
import com.karat.cn.service.JokeService;
import com.karat.cn.util.vo.ResultVo;

@Service
public class JokeServiceImpl implements JokeService{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultVo selectAllJoke() {
		// TODO Auto-generated method stub
		System.out.println("业务实现层");
		ResultVo vo=new ResultVo();
		List<Joke> jokes=mongoTemplate.findAll(Joke.class);
		if(jokes.size()>0){
			vo.setCode(200);
			vo.setList(jokes);
			vo.setMsg("成功");	
		}else{
			vo.setCode(201);
			vo.setMsg("失败");
		}
		return vo;
	}
	

}
