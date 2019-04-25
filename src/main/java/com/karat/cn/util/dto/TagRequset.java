package com.karat.cn.util.dto;

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
public class TagRequset{

	
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

}
