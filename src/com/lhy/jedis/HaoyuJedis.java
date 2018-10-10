package com.lhy.jedis;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 简单模拟Jedis，实现set(String key, String val)和get(String key)
 * 
 * <p>
 * Jedis的传输协议是TCP，传输过程的报文协议是RESP(REdis Serialization Protocol) RESP 协议的有点有三个
 * <p>
 * ·实现简单 ·解析速度快 ·可读性好
 * 
 * <p>
 * <p>
 * <p>
 * 在 RESP 中, 一些数据的类型通过它的第一个字节进行判断： 单行回复：回复的第一个字节是 "+"
 * <p>
 * 错误信息：回复的第一个字节是 "-"
 * <p>
 * 整形数字：回复的第一个字节是 ":"
 * <p>
 * 多行字符串：回复的第一个字节是 "$"
 * <p>
 * 数组：回复的第一个字节是 "*"
 * <p>
 * <p>
 * 传输过程：以 set name zhangsan 为例
 * <p>
 * 转化为RESP字符串如下：
 * <p>
 * *3 代表数组内3个元素
 * <p>
 * $3
 * <p>
 * SET
 * <p>
 * $4
 * <p>
 * name
 * <p>
 * $8
 * <p>
 * zhangsan
 * 
 * @author lhy
 * @time 2018年10月10日9:57:08
 *
 */
public class HaoyuJedis {
	/**
	 * 建立Socket链接
	 */
	private Socket socket;

	public HaoyuJedis() throws UnknownHostException, IOException {
		socket = new Socket("127.0.0.1", 6379);
	}

	public HaoyuJedis(String host, int port) throws UnknownHostException, IOException {
		socket = new Socket(host, port);
	}

	/**
	 * 设置键值对
	 * 
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public void set(String key, String value) throws IOException {
		String resp = fromStringtoRESPString("set", key, value);
		socket.getOutputStream().write(resp.getBytes());
		byte[] result = new byte[2048];
		socket.getInputStream().read(result);
		System.err.println(new String(result));
	}

	/**
	 * 通过Key值获取value
	 * 
	 * @param key
	 * @return value
	 * @throws IOException
	 */
	public String get(String key) throws IOException {
		String resp = fromStringtoRESPString("get", key, null);

		// StringBuilder sb = new StringBuilder();
		// sb.append("*2").append("\r\n").append("$3").append("\r\n").append("GET").append("\r\n").append("$")
		// .append(key.getBytes().length).append("\r\n").append(key).append("\r\n");
		socket.getOutputStream().write(resp.getBytes());
		byte[] result = new byte[2048];
		socket.getInputStream().read(result);
		return new String(result);
	}

	/**
	 * 将key value转化为 RESP字符串
	 * 
	 * 这里仅以set和get方法作简单介绍，因此仅有两种结果*3和*2，并且不考虑时效性等其他内容
	 * 
	 * @param oper
	 *            操作符 set，get
	 * @param key
	 * @param value
	 * @return RESP
	 */
	private String fromStringtoRESPString(String oper, String key, String value) {
		// 判断是set还是get
		int operLine = oper.equalsIgnoreCase("set") ? 3 : 2;
		// 拼接RESP字符串
		StringBuilder sb = new StringBuilder();
		sb.append("*").append(operLine).append("\r\n").append("$").append(oper.getBytes().length).append("\r\n")
				.append(oper).append("\r\n").append("$").append(key.getBytes().length).append("\r\n").append(key)
				.append("\r\n");
		// 如果value不为空继续拼接
		if (value != null) {
			sb.append("$").append(value.getBytes().length).append("\r\n").append(value).append("\r\n");
		}
		return sb.toString();
	}
}
