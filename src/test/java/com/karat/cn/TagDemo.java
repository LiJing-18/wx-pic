package com.karat.cn;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;


import com.karat.cn.pojo.Tag;

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
	
}
