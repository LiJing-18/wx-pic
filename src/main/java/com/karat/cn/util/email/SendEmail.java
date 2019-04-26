package com.karat.cn.util.email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
/**
 * 邮件注册激活
 * @author 开发
 *
 */
public class SendEmail {
    
    public static void main(String args[]) throws AddressException, MessagingException, FileNotFoundException, IOException{
    	String from = "1252515809@qq.com";
    	String to = "lijing13949080725@163.com";
    	String subject = "官方注册码";
    	
    	
    	//随机生成四位数字
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder stringBuilder=new StringBuilder(4);
        for(int i=0;i<4;i++){
            char ch=str.charAt(new Random().nextInt(str.length()));
            stringBuilder.append(ch);
        }
        //内容
    	String body="<html>"
    			+ "<head></head>"
    			+ "<body>"
    			+ "<h1>这是一封激活邮件,激活码是:"+stringBuilder.toString()+",激活请点击以下链接</h1>"
    			+ "<h3>"
    			+ "<a href='http://localhost:8080/photo/action?code="+stringBuilder.toString()+"</href>"
    			+ "</h3>"
    			+ "<h1>激活邮件</h1>"
    			+ "</body>"
    			+ "</html>";
    	
    	String smtpHost = "smtp.qq.com";
    	 
    	// 1. 创建参数配置, 用于连接邮件服务器的参数配置
    	Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", smtpHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
  	
    	
    	// 创建Session实例对象
    	Session session = Session.getDefaultInstance(props);
    	// 创建MimeMessage实例对象
    	MimeMessage message = new MimeMessage(session);
    	// 设置发件人
    	message.setFrom(new InternetAddress(from));
    	// 设置收件人
    	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
    	// 设置发送日期
    	message.setSentDate(new Date());
    	// 设置邮件主题
    	message.setSubject(subject);
    	// 设置纯文本内容的邮件正文
    	//message.setText("欢迎您注册,账号注册验证码为:"+body+",(一分钟有效)请勿回复此邮箱");
    	// 设置邮件内容（html）
    	message.setContent(body, "text/html;charset=UTF-8");
    	// 保存并生成最终的邮件内容
    	message.saveChanges();
    	// 设置为debug模式, 可以查看详细的发送 log
    	session.setDebug(true);
    	
    	// 获取Transport对象
    	Transport transport = session.getTransport("smtp");
        // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
        transport.connect(from, "eagmhcvnnlsdhghj");
        // 发送，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    	
    }
}
