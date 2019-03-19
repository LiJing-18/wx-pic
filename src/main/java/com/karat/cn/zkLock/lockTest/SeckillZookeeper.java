package com.karat.cn.zkLock.lockTest;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.karat.cn.pojo.Goods;
import com.karat.cn.pojo.Order;
import com.karat.cn.service.OrderService;
import com.karat.cn.util.OrderNumUtil;
import com.karat.cn.util.TimeUtil;

public class SeckillZookeeper implements Runnable{

	private int num=0;
	private Goods goods;
	private MongoTemplate mongoTemplate;
	private OrderService orderService;
	
	public SeckillZookeeper(int num,Goods goods,MongoTemplate mongoTemplate,OrderService orderService) {
		super();
		this.num = num;
		this.goods=goods;
		this.mongoTemplate=mongoTemplate;
		this.orderService=orderService;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		DistributedLock lock=null;
		try{
			lock=new DistributedLock("47.107.121.215:2181","shop");
			lock.lock();//上锁
			if(num>0){
				sale();//购买
				return ;
			}
		}finally{
			if(lock!=null){
				lock.unlock();
			}
		}
		System.out.println("很遗憾没抢到");
	}

	
	//购买
	private void sale(){
		if(num>0){	
			Order order=new Order();
        	order.setOrderNum(OrderNumUtil.getOrderIdByUUId());
        	order.setGoodsId(goods.getId());
        	order.setCreateDate(TimeUtil.formateDbDate(TimeUtil.systemtime()));
        	if(orderService.add(order)){
        		goods.setNum(num-1);
    			mongoTemplate.save(goods);
        	}
			System.out.println("获取锁"+":"+"购买成功--->"+num+"号商品被卖出去");
		}
	}
}
