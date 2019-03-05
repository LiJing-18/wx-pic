package com.karat.cn.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 主题
 * @author 开发
 *
 */
@Data
@Document(collection="topic")
public class Topic {

	@Id
	private String id;
	/**
	 * 主题名
	 */
	private String name;
	/**
	 * 主题图
	 */
	private String url;
	/**
	 * (0显示1不显示)
	 */
	private String state;
	/**
	 * 创建时间
	 */
	private String createDate;
}
