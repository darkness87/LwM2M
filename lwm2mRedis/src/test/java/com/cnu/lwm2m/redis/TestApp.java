package com.cnu.lwm2m.redis;

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
public class TestApp {
	public static void main(String[] args) {
		System.out.println("=== redis test start ===");

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		JedisPool pool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 1000, null);
		// Jedis풀 생성(JedisPoolConfig, host, port, timeout, password)
		Jedis jedis = pool.getResource();// thread, db pool처럼 필요할 때마다 getResource()로 받아서 쓰고 다 쓰면 close로 닫아야 함
		
		
		System.out.println("<< redis string start >>");
		jedis.set("redtest", "hello");// 데이터 입력
		System.out.println(jedis.get("redtest"));// 데이터 출력
		jedis.del("redtest");// 데이터 삭제
		System.out.println(jedis.get("redtest"));// null
		System.out.println("<< redis string end >>");


		System.out.println("<< redis expire start >>");
		try {
			jedis.set("key", "이 값은 5초 뒤에 지워집니다.");
			// 데이터 만료시간 지정
			jedis.expire("key", 5);// expire을 사용하여 5초 동안만 "key"를 key로 갖는 데이터 유지 // expire 사용시 추후 7일 이상 세팅?
			Thread.sleep(4000);// 쓰레드를 4초간 재우고
			System.out.println(jedis.get("key"));// 5초 이전에 데이터 확인
			Thread.sleep(2000);// 1초했더니 운좋으면 살아있어서 2초로 지정
			System.out.println(jedis.get("key"));// null = > 데이터 삭제 확인
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("<< redis expire end >>");

		
		 /* Lists 형태 입출력 */
		System.out.println("<< redis Lists start >>");
        jedis.lpush("lists", "11111");
        jedis.lpush("lists", "22222");
        jedis.lpush("lists", "33333");
        jedis.lpush("lists", "44444");
        jedis.lpush("lists", "55555");
        System.out.println(jedis.rpop("lists"));//first
        System.out.println(jedis.rpop("lists"));//second
        System.out.println(jedis.rpop("lists"));
        System.out.println(jedis.rpop("lists"));
        System.out.println(jedis.rpop("lists"));
        System.out.println("<< redis Lists end >>");
        
                
        /* Sets 형태 입출력 */
        System.out.println("<< redis Sets start >>");
        jedis.sadd("nicknames", "pb");
        jedis.sadd("nicknames", "sk");
        jedis.sadd("nicknames", "ys");
        Set<String> nickname = jedis.smembers("nicknames");
        Iterator iter = nickname.iterator();
        while(iter.hasNext()) {
            System.out.println(iter.next());
        }
        System.out.println("<< redis Sets end >>");
        
        
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
        
        
        /* Sorted Sets 형태 입출력 */
        System.out.println("<< redis Sorted Sets start >>");
        jedis.zadd("scores", 6200.5, "Player1"); // Map으로 데이터 만들어서 zadd에 값 넣을 수도 있음
        jedis.zadd("scores", 8000.5, "Player2");
        jedis.zadd("scores", 1000.5, "Player3");
        System.out.println(jedis.zrangeByScore("scores", 0, 10000));
        System.out.println(jedis.zrangeByScore("scores", 6000, 9000)); // 스코어 점수 사이 값
        System.out.println("<< redis Sorted Sets end >>");
        
        
        /* bit 형태 입출력 */
        System.out.println("<< redis bit start >>");
        jedis.setbit("bitkey", 123, true);
        System.out.println(jedis.getbit("bitkey", 123));
        System.out.println("<< redis bit end >>");
        
        
		/* 해당키에 대한 타입 형태 알기 */
        System.out.println(jedis.type("user"));
        System.out.println(jedis.type("scores"));
        
        
        /* 해당키에 대한 레디스에 있는 확인 유무 */
        System.out.println(jedis.exists("user"));
        
        
		if (jedis != null) {
			jedis.close();
		}
		pool.close();
		
		System.out.println("=== redis test end ===");

	}
}
