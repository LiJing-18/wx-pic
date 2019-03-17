package com.karat.cn.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 标签
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="tag")
public class Tag{

	@Id
	private String id;
	/**
	 *姓名
	 */
	private String name;
	/**
	 * 权重
	 */
	private int weigth;
	/**
	 * (0显示1不显示)
	 */
	private String state;
	/**
	 * 创建时间
	 */
	private String createDate;
}
