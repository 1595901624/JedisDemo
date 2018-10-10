package com.lhy.jedis;

import java.io.IOException;

import redis.clients.jedis.Jedis;

/**
 * ����
 * 
 * @author lhy
 *
 */
public class Main {

	public static void main(String[] args) throws IOException {
		//����
		HaoyuJedis haoyuJedis = new HaoyuJedis("127.0.0.1", 6379);
		//���ò�������
		haoyuJedis.set("name", "zhangsan");
		for (int i = 0; i < 10; i++) {
			haoyuJedis.set("name" + i, i + "zhangsan" + Math.random());
		}
		System.err.println(haoyuJedis.get("name"));
	}

}
