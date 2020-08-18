package com.red;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.red.api.RedisConnect;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.10
 */
public class App {
	
	static final Logger log = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("=== start test ===");

		RedisConnect redisConnect = new RedisConnect();
		ObjectMapper mapper = new ObjectMapper();
		Jedis jedis = redisConnect.connect();
		
		
		Set<String> keys = jedis.keys("*");
		System.out.println("keys * -> size: "+keys.size()+", "+keys.toString());
		Set<String> keys2 = jedis.keys("key*");
		System.out.println("keys key* -> size: "+keys2.size()+", "+keys2.toString());
		Set<String> keys3 = jedis.keys("*02190000003*");
		System.out.println("keys key?* -> size: "+keys3.size()+", "+keys3.toString());
		
		
		Boolean ex = jedis.exists("key_02190000003");
		System.out.println("exists key -> "+ex);
		Long count = jedis.exists("key", "key_02190000003");
		System.out.println("exists key xxx -> "+count);
		String keys4[] = "key xxx".split(" ");
		Long count2 = jedis.exists(keys4);
		System.out.println("exists key xxx -> "+count2+" (Array)");
		
		
		ScanResult<String> set = jedis.scan("0");
		set = jedis.scan(set.getCursor());
		System.out.println(set.getResult()+" "+set.getCursor());
	
		
		
		redisConnect.close();
		
		System.out.println("=== end test ===");

	}
}
