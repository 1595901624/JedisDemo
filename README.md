### JedisDemo

* * *

简单模拟Jedis，实现set(String key, String val)和get(String key)

Jedis的传输协议是TCP，传输过程的报文协议是RESP(REdis Serialization Protocol)

![img]https://raw.githubusercontent.com/1595901624/JedisDemo/master/JedisDemo.gif

RESP 协议的优点有三个

     * 实现简单
     * 解析速度快 
     * 可读性好<br>

在 RESP 中, 一些数据的类型通过它的第一个字节进行判断：

    单行回复：回复的第一个字节是 "+"
    错误信息：回复的第一个字节是 "-"
    整形数字：回复的第一个字节是 ":"
    多行字符串：回复的第一个字节是 "$"
    数组：回复的第一个字节是 "*"

传输过程：以 set name zhangsan 为例  
转化为RESP字符串如下：

    *3 
    $3
    SET
    $4
    name
    $8
    zhangsan
