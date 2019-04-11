package com.karat.cn;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
/**
 * p2p测试
 * @author 开发
 *
 */
public class SpringJms {

    public static void main(String[] args) {
    	send();
    	//getText();
    }
    //生产者
    @SuppressWarnings("resource")
	public static void send(){
    	ClassPathXmlApplicationContext context=
                new ClassPathXmlApplicationContext(
                        "classpath:service-jms.xml");

        JmsTemplate jmsTemplate=(JmsTemplate) context.getBean("jmsTemplate");

        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage message=session.createTextMessage();
                message.setText("Hello");
                return message;
            }
        });
    }
    //消费者
    @SuppressWarnings({ "resource" })
	public static void getText(){
    	ClassPathXmlApplicationContext context=
                new ClassPathXmlApplicationContext(
                        "classpath:service-jms.xml");
        JmsTemplate jmsTemplate=(JmsTemplate) context.getBean("jmsTemplate");
        String msg=(String)jmsTemplate.receiveAndConvert();
        System.out.println(msg);      
    }
}
