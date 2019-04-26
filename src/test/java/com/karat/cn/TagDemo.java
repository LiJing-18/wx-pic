package com.karat.cn;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;


import com.karat.cn.pojo.Tag;
import com.karat.cn.pojo.User;
import com.karat.cn.util.constants.Constants;
import com.karat.cn.util.time.IdUtil;

public class TagDemo {

	@Autowired
    private MongoTemplate mongoTemplate;
	
	@SuppressWarnings("resource")
	@Test
	public void testSelect(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        mongoTemplate = (MongoTemplate) context.getBean("mongoTemplate");
		
        List<Tag> tags=mongoTemplate.findAll(Tag.class);
		tags.forEach(t->{
			System.out.println(t);
		});
	}
	
	@Test
	public void testAddUser(){
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        mongoTemplate = (MongoTemplate) context.getBean("mongoTemplate");
		
        User user=new User();
        user.setId(IdUtil.getUUID());
        user.setUsername("1");
        user.setPassword("1");
        user.setRealname("小明");
        user.setSex("男");
        user.setMobile("13000000000");
        user.setAvatar("110");
        user.setStatus(Constants.FORZEN_USER_STATUS);
        user.setCreateTime(new Date());
        //TODO 添加数据
        mongoTemplate.insert(user);
	}
}
