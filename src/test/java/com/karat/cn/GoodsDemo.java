package com.karat.cn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.karat.cn.pojo.Goods;
import com.karat.cn.service.GoodsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class GoodsDemo {
	
	@Autowired
	private GoodsService goodsService;
	
	
	@Test//增
	public void add(){
		Goods goods=new Goods();
		goods.setName("水杯");
		goods.setNum(50);
		goodsService.add(goods);
	}
	@Test//删
	public void del(){
		goodsService.del("5c8f182cc6001853f8bd7fa3");
	}
	@Test//改
	public void update(){
		Goods goods=goodsService.select("5c8f182cc6001853f8bd7fa3");
		goods.setNum(80);
		goodsService.update(goods);
	}
	@Test//查
	public void select(){
		System.out.println(goodsService.select("5c8f182cc6001853f8bd7fa3"));
	}
	@Test//查
	public void selectAll(){
		goodsService.selectAll().forEach(g->{System.out.println(g);});
	}
}
