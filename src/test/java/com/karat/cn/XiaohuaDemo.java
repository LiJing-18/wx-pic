package com.karat.cn;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.karat.cn.pojo.Joke;
import com.karat.cn.util.http.ConnectionUtil;
/**
 * 爬段子
 * @author 开发
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class XiaohuaDemo {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Test
	public void duanzi(){
		for (int i = 1; i <= 10; i++) {
            System.out.println("正在爬取第" + i + "页内容。。。");
            // 建立连接，获取网页内容
            String html = ConnectionUtil.Connect("https://www.pengfu.com/xiaohua_" + i + ".html");
            // 将内容转换成dom格式，方便操作
            Document doc = Jsoup.parse(html);
            // 获取网页内所有标题节点
            Elements titles = doc.select("h1.dp-b");
            for (Element titleEle : titles) {
                Element parent = titleEle.parent();
                // 标题内容
                String title = titleEle.getElementsByTag("a").text();
                // 标题对应的作者
                String author = parent.select("p.user_name_list > a").text();
                // 标题对应的正文
                String content = parent.select("div.content-img").text();
                System.out.println(title+"=="+author+"&&&&&"+content);
                
                //随机点击量
                String click=String.valueOf((int)(100+Math.random()*(1000-100+100)));
                
                Joke joke=new Joke(title,author,content,click);
                mongoTemplate.insert(joke);
            }
        }
	}
	/**
	 * 添加点击量
	 */
	@Test
	public void update(){
		List<Joke> joke=mongoTemplate.findAll(Joke.class);
		joke.forEach(i->{
			//随机点击量
            String click=String.valueOf((int)(100+Math.random()*(1000-100+100)));
            i.setClick(click);
            mongoTemplate.save(i);
		});
	}
}
