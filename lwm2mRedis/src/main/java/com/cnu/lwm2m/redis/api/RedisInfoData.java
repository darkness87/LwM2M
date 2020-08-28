package com.cnu.lwm2m.redis.api;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.18
 */
public class RedisInfoData {

	static final Logger log = LoggerFactory.getLogger(RedisInfoData.class);
	/*
	 * Class 생성시 DbConnect, ObjectMapper 선언하여 사용
	 * 레디스 연동을 위한 Jedis 라이브러리 사용
	 * DbConnect 사용 후 종료 시 항상 close();
	 */

	RedisConnect redisConnect = new RedisConnect();
	
	/**
	 * Key 전체 조회
	 * @return
	 * @throws Exception
	 */
	public Set<String> getAllKey() throws Exception{
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("Redis 연결오류");
			return null;
		}
		Set<String> keys = jedis.keys("*");
		redisConnect.close();
		return keys;
	}
	
	/**
	 * Key 검색 조회
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Set<String> getSearchKey(String key) throws Exception{
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("Redis 연결오류");
			return null;
		}
		Set<String> keys = jedis.keys("*"+key+"*");
		redisConnect.close();
		return keys;
	}
	
	/**
	 * Key 값 유무
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Boolean getBooleanKey(String key) throws Exception{
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("Redis 연결오류");
			return null;
		}
		Boolean ex = jedis.exists(key);
		redisConnect.close();
		return ex;
	}
	
	/**
	 * sec(초) 만큼 해당 key를 key로 갖는 데이터 유지
	 * 7일 : 604,800 초 , 1일 : 86400 초
	 * @param key
	 * @param sec
	 * @return
	 * @throws Exception
	 */
	public int setExpireKey(String key, int sec) throws Exception{
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("Redis 연결오류");
			return 1;
		}
		jedis.expire(key, sec);
		redisConnect.close();
		return 0;
	}
	
	/**
	 * expire 값 취소
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public int setPersistKey(String key) throws Exception{
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("Redis 연결오류");
			return 1;
		}
		jedis.persist(key);
		redisConnect.close();
		return 0;
	}
	
	/**
	 * KEY 삭제
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public int setDelKey(String key) throws Exception{
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("Redis 연결오류");
			return 1;
		}
		jedis.del(key);
		redisConnect.close();
		return 0;
	}
	
	/**
	 * Redis에 접속된 Client 정보 확인
	 * @return
	 * @throws Exception
	 */
	public String getClientList() throws Exception{
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("Redis 연결오류");
			return null;
		}
		String clientList = jedis.clientList();
		redisConnect.close();
		return clientList;
	}
	
	/**
	 * Redis 기본정보 확인
	 * @return
	 * @throws Exception
	 */
	public String getRedisInfo() throws Exception{
		Jedis jedis = redisConnect.connect();
		if(jedis==null) {
			log.info("Redis 연결오류");
			return null;
		}
		String redisInfo = jedis.info();
		redisConnect.close();
		return redisInfo;
	}
	
}
