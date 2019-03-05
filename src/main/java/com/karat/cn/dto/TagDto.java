package com.karat.cn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 运输标签对象
 * @author 开发
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {

	
	private String id;
	/**
	 * 标签名
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
	public TagDto(String name, int weigth, String state, String createDate) {
		super();
		this.name = name;
		this.weigth = weigth;
		this.state = state;
		this.createDate = createDate;
	} 
	
	
}
