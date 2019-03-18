package com.karat.cn.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="order")
public class Order {

	@Id
	private String id;
	//订单号
	@Indexed(unique=true)
	private String orderNum;
	//商品id
	private String goodsId;
	//创建时间
	private String createDate;
	
}
