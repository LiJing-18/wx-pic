package com.karat.cn;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.karat.cn.pojo.Tag;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class TagDemo {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	@Test
	public void select(){
		List<Tag> tags=mongoTemplate.findAll(Tag.class);
		tags.forEach(t->{
			System.out.println(t);
		});
	}
	
}
