package com.red;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Hello world!
 *
 */
public class TestAppLpData {
	public static void main(String[] args) {
		System.out.println("=== redis test start ===");

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		JedisPool pool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 1000, null);
		// Jedis풀 생성(JedisPoolConfig, host, port, timeout, password)
		Jedis jedis = pool.getResource();// thread, db pool처럼 필요할 때마다 getResource()로 받아서 쓰고 다 쓰면 close로 닫아야 함
        
        /* Hashes 형태 입출력 */
        System.out.println("<< redis Hashes start >>");
        jedis.hset("user", "name", "sk");
        jedis.hset("user", "job", "software engineer");
        jedis.hset("user", "hobby", "game");
        System.out.println(jedis.hget("user","name"));//
        Map<String, String> fields = jedis.hgetAll("user");
        System.out.println(fields.get("job"));
        System.out.println(fields.get("name")+"/"+fields.get("job")+"/"+fields.get("hobby"));
        System.out.println("<< redis Hashes end >>");
        
        
        
		if (jedis != null) {
			jedis.close();
		}
		pool.close();
		
		System.out.println("=== redis test end ===");

	}
}
