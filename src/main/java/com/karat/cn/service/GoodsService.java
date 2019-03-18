package com.karat.cn.service;

import java.util.List;

import com.karat.cn.pojo.Goods;

public interface GoodsService {
	
	void add(Goods goods);
	
	void del(String id);
	
	void update(Goods goods);
	
	List<Goods> selectAll();
	
	Goods select(String id);
}
