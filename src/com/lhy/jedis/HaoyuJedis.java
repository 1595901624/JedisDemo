package com.lhy.jedis;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * ��ģ��Jedis��ʵ��set(String key, String val)��get(String key)
 * 
 * <p>
 * Jedis�Ĵ���Э����TCP��������̵ı���Э����RESP(REdis Serialization Protocol) RESP Э����е�������
 * <p>
 * ��ʵ�ּ� �������ٶȿ� ���ɶ��Ժ�
 * 
 * <p>
 * <p>
 * <p>
 * �� RESP ��, һЩ���ݵ�����ͨ�����ĵ�һ���ֽڽ����жϣ� ���лظ����ظ��ĵ�һ���ֽ��� "+"
 * <p>
 * ������Ϣ���ظ��ĵ�һ���ֽ��� "-"
 * <p>
 * �������֣��ظ��ĵ�һ���ֽ��� ":"
 * <p>
 * �����ַ������ظ��ĵ�һ���ֽ��� "$"
 * <p>
 * ���飺�ظ��ĵ�һ���ֽ��� "*"
 * <p>
 * <p>
 * ������̣��� set name zhangsan Ϊ��
 * <p>
 * ת��ΪRESP�ַ������£�
 * <p>
 * *3 ����������3��Ԫ��
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
 * @time 2018��10��10��9:57:08
 *
 */
public class HaoyuJedis {
	/**
	 * ����Socket����
	 */
	private Socket socket;

	public HaoyuJedis() throws UnknownHostException, IOException {
		socket = new Socket("127.0.0.1", 6379);
	}

	public HaoyuJedis(String host, int port) throws UnknownHostException, IOException {
		socket = new Socket(host, port);
	}

	/**
	 * ���ü�ֵ��
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
	 * ͨ��Keyֵ��ȡvalue
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
	 * ��key valueת��Ϊ RESP�ַ���
	 * 
	 * �������set��get�������򵥽��ܣ���˽������ֽ��*3��*2�����Ҳ�����ʱЧ�Ե���������
	 * 
	 * @param oper
	 *            ������ set��get
	 * @param key
	 * @param value
	 * @return RESP
	 */
	private String fromStringtoRESPString(String oper, String key, String value) {
		// �ж���set����get
		int operLine = oper.equalsIgnoreCase("set") ? 3 : 2;
		// ƴ��RESP�ַ���
		StringBuilder sb = new StringBuilder();
		sb.append("*").append(operLine).append("\r\n").append("$").append(oper.getBytes().length).append("\r\n")
				.append(oper).append("\r\n").append("$").append(key.getBytes().length).append("\r\n").append(key)
				.append("\r\n");
		// ���value��Ϊ�ռ���ƴ��
		if (value != null) {
			sb.append("$").append(value.getBytes().length).append("\r\n").append(value).append("\r\n");
		}
		return sb.toString();
	}
}
