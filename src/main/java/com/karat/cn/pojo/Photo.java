package com.karat.cn.pojo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
/**
 * 图
 * @author 开发
 *
 */
@Data
@Document(collection="photo")
public class Photo {

	
	@Id
	private String id;
	/**
	 * 图人
	 */
	private String name;
	/**
	 * 图地址
	 */
	private String url;
	/**
	 * 备用
	 */
	private String other;
	/**
	 * 创建时间
	 */
	private String createDate;
}

/**
 * 
@Data ：注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
@Setter：注解在属性上；为属性提供 setting 方法
@Getter：注解在属性上；为属性提供 getting 方法
@Log4j ：注解在类上；为类提供一个 属性名为log 的 log4j 日志对象
@NoArgsConstructor：注解在类上；为类提供一个无参的构造方法
@AllArgsConstructor：注解在类上；为类提供一个全参的构造方法
 */