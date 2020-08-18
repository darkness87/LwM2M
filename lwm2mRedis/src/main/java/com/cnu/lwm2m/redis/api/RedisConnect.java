package com.cnu.lwm2m.redis.api;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.10
 */
public class RedisConnect {
	
	// 테스트 초기 세팅값
	String ipAddr = "127.0.0.1"; // 내부에서만 구동시 , 구성환경에 따라 변경될수 있음
	int port = 6379;
	int timeout = 1000;
	String passWord = null; // 추후 구축시 패스워드 설정
	// 데이터 접속 정보에 대한 내용을 java 코드에서 받을 지 or 호출시 데이터
	// 초기 설정 값 저장 방법 고민 (코드? 파일? 등등)
	
	JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	JedisPool pool = new JedisPool(jedisPoolConfig, ipAddr, port, timeout, passWord);
	Jedis jedis = pool.getResource();

	/**
	 * redis 연동 확인
	 * @return Jedis
	 * @throws Exception
	 */
	public Jedis connect() throws Exception{
		if (jedis.ping().equalsIgnoreCase("PONG")) {
	          return jedis;
	    }
		return null;
		// redis ping pong 에 따른 리턴
		// ping pong 확인 후 리턴을 할지 아니면 바로할지 생각해보자
	}
	
	/**
	 * redis, pool close
	 * @throws Exception
	 */
	public void close() throws Exception{
		if (jedis != null) {
			jedis.close();
		}
		pool.close();
	}
	
	public Object config() {

		Object object = new Object();
		
//		jedis.scan(cursor)
		
		System.out.println();
		
		return object;
	}
		
}
