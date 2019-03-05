package com.karat.cn.vo;

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
public class ResultVo {

	private String msg;
	
	private String code;
	
	
}
