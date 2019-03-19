package com.karat.cn.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.karat.cn.pojo.Goods;
import com.karat.cn.pojo.Order;
import com.karat.cn.service.GoodsService;
import com.karat.cn.service.OrderService;
import com.karat.cn.util.TimeUtil;
import com.karat.cn.util.OrderNumUtil;
import com.karat.cn.util.ResultVOUtil;
import com.karat.cn.vo.ResultVo;
import com.karat.cn.zkLock.lockTest.LockTest;

import io.swagger.annotations.Api;

@RequestMapping("goods")
@RestController
@Api("商品秒杀")
public class SecKillGoodsController {

	@Autowired
	private GoodsService service;
	@Autowired
	private OrderService orderService;
	/**
	 * 下单加锁
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	@RequestMapping(value="zookeeperLock",produces="html/text;charset=UTF-8")
	public String zookeeperLock(){
		System.out.println("下单开始");
		//fluent风格创建会话连接
		CuratorFramework curatorFramework = CuratorFrameworkFactory
				.builder()
				.connectString("47.107.121.215:2181")
				.retryPolicy(new ExponentialBackoffRetry(2000, 10))
				.build();
		/*
		 * ExponentialBackoffRetry
		 * 定义重试策略：等待2秒,重试10次
		 * 第一个参数：等待时间
		 * 第二个参数：重试次数
		 */
		//启用
		curatorFramework.start();
		//获取zookeeper锁的信息
		final InterProcessMutex mutex = new InterProcessMutex(curatorFramework, "/LOCKS");
		//通过线程抢锁保证数据一致性
		ResultVo vo=new ResultVo<>();
		new Thread(()->{
				try {
					//请求锁资源，如果没有得到锁资源，就会执行重试策略
					mutex.acquire();
					BeanUtils.copyProperties(buyGoods(), vo);
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						//释放锁
						mutex.release();
					} catch (Exception e) {
						e.printStackTrace();
					}
		        }
		}).run();//启动线程
		return JSON.toJSONString(vo);
	}
	
	/*
	 * 抢购商品的方法 
	 * 作用：访问共享资源,获取并更新商品数量
	 */
	@SuppressWarnings("rawtypes")
	public ResultVo buyGoods() {
		ResultVo vo=null;
		//查看库存是否足够
        Goods goods=service.select("5c8f182cc6001853f8bd7fa3");
        if(goods!=null&&goods.getNum()>0){
        	System.out.println("当前库存:"+goods.getNum());
        	//创建订单
        	Order order=new Order();
        	order.setOrderNum(OrderNumUtil.getOrderIdByUUId());
        	order.setGoodsId(goods.getId());
        	order.setCreateDate(TimeUtil.formateDbDate(TimeUtil.systemtime()));
        	if(orderService.add(order)){
        		//减去库存
        		goods.setNum(goods.getNum()-1);
        		service.update(goods);
        		//抢成功
        		System.out.println("成功");
        		vo=ResultVOUtil.error(200, "创建订单成功");
        	}else{
        		vo=ResultVOUtil.error(201, "创建订单失败");
        	}
        }else{
        	vo=ResultVOUtil.error(201, "请检查传入参数");
        }
        return vo;
	}
	
	
	/*************************************************************************/
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="index",produces="html/text;charset=UTF-8")
	public String index(){
		ResultVo vo=new ResultVo<>();
		ExecutorService service = Executors.newFixedThreadPool(10);
		service.execute(()-> {
            LockTest test = new LockTest();
            try {
                test.lock();
                BeanUtils.copyProperties(buyGoods(), vo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            test.unlock();
        });
        service.shutdown();
		return JSON.toJSONString(vo);
	}
	
	
}
