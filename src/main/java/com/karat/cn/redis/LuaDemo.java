package com.karat.cn.redis;

import redis.clients.jedis.Jedis;
/**
 * 脚本
 * @author 开发
 *
 */
public class LuaDemo {

	public static void main(String args[]) {
		try {
			Jedis jedis = RedisManager.getJedis();//连接获取jedis实列
			
			String lua="redis.call('set','name','xiaoming')\n"
					+ "return redis.call('get','name')";//脚本内容
			
			/*Object object=jedis.eval(lua);
			System.out.println(object);*/
			Object object=jedis.evalsha(jedis.scriptLoad(lua));//scriptLoad得到摘要，根据摘要得到脚本
			System.out.println(object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
