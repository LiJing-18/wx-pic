package com.karat.cn.service.impl;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.karat.cn.pojo.Order;
import com.karat.cn.service.OrderService;

@Repository
public class OrderServiceImpl implements OrderService{

	@Resource
	private MongoTemplate mongoTemplate;
	@Override
	public boolean add(Order order) {
		// TODO Auto-generated method stub
		mongoTemplate.insert(order);
		boolean flag=false;
		Order ord=mongoTemplate.findOne(new Query().addCriteria(Criteria.where("orderNum").is(order.getOrderNum())), Order.class);
		if(ord!=null){
			flag=true;
		}
		return flag;
	}

}
