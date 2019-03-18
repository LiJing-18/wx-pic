package com.karat.cn.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 商品表(模拟秒杀的被抢资源)
 * @author 开发
 *
 */
@Data
@Document(collection="goods")
public class Goods {

	@Id
	private String id;
	//商品名称
	private String name;
	//库存
	private int num;
	
}
