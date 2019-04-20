package com.karat.cn.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 段子
 * @author 开发
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="joke")
public class Joke {

	private String id;
	//标题
	private String title;
	//作者
	private String author;
	//正文
	private String content;
	
	
	public Joke(String title, String author, String content) {
		super();
		this.title = title;
		this.author = author;
		this.content = content;
	}
		
}
