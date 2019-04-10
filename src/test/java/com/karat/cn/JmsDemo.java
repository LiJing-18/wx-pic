package com.karat.cn;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 以事件通知方式来配置消费者(接收端自动接收)
 * @author 开发
 *
 */
public class JmsDemo {
	
	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) {
        ClassPathXmlApplicationContext context=
                new ClassPathXmlApplicationContext(
                        "classpath:service-jms.xml");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
