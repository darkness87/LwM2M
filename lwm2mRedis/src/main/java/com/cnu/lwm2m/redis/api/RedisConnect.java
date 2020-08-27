package com.cnu.lwm2m.redis.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnu.lwm2m.redis.Config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.10
 */
public class RedisConnect {
	
	static final Logger log = LoggerFactory.getLogger(RedisConnect.class);
	
	String redisIpAddr = new Config().getIpAddr();
	int redisPort = new Config().getPort();
	int redisTimeout = new Config().getTimeout();
	String redisPassWord = new Config().getPassWord();

	JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	JedisPool pool = new JedisPool(jedisPoolConfig, redisIpAddr, redisPort, redisTimeout, redisPassWord);
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
		return null; // redis ping pong 에 따른 리턴 고려
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
		
}
