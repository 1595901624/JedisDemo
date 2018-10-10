package com.lhy.jedis;

import java.io.IOException;

import redis.clients.jedis.Jedis;

/**
 * 测试
 * 
 * @author lhy
 *
 */
public class Main {

	public static void main(String[] args) throws IOException {
		//连接
		HaoyuJedis haoyuJedis = new HaoyuJedis("127.0.0.1", 6379);
		//设置测试数据
		haoyuJedis.set("name", "zhangsan");
		for (int i = 0; i < 10; i++) {
			haoyuJedis.set("name" + i, i + "zhangsan" + Math.random());
		}
		System.err.println(haoyuJedis.get("name"));
	}

}
