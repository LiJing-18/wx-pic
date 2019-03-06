package com.karat.cn.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回状态以及描述
 * @author 开发
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo<T>{

	/**
	 * 错误码
	 */
	private Integer code;
	/**
	 * 提示信息
	 */
	private String msg;
	/**
	 *具体内容 
	 */
	private T data;
	/**
	 * 具体内容集合
	 */
	private List<T> list;
}
