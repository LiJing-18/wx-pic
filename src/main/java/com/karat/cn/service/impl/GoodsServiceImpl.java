package com.karat.cn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.karat.cn.pojo.Goods;
import com.karat.cn.service.GoodsService;

@Repository
public class GoodsServiceImpl implements GoodsService{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void add(Goods goods) {
		// TODO Auto-generated method stub
		mongoTemplate.save(goods);
	}

	@Override
	public void del(String id) {
		// TODO Auto-generated method stub
		mongoTemplate.remove(new Query().addCriteria(Criteria.where("id").is(id)),Goods.class);
	}

	@Override
	public void update(Goods goods) {
		// TODO Auto-generated method stub
		mongoTemplate.save(goods);
	}

	@Override
	public List<Goods> selectAll() {
		// TODO Auto-generated method stub
		return mongoTemplate.findAll(Goods.class);
	}

	@Override
	public Goods select(String id) {
		// TODO Auto-generated method stub
		return mongoTemplate.findById(id, Goods.class);
	}

}
