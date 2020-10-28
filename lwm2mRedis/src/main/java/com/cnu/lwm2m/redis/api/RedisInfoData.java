package com.cnu.lwm2m.redis.api;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
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
	 * Class 생성시 DbConnect, ObjectMapper 선언하여 사용 레디스 연동을 위한 Jedis 라이브러리 사용 DbConnect
	 * 사용 후 종료 시 항상 close();
	 */

	RedisConnect redisConnect = new RedisConnect();

	/**
	 * Key 전체 조회
	 * 
	 * @return
	 * @throws Exception
	 */
	public Set<String> getAllKey() throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return null;
		}
		Set<String> keys = jedis.keys("*");
		redisConnect.close();
		return keys;
	}

	/**
	 * Key 검색 조회
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Set<String> getSearchKey(String key) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return null;
		}
		Set<String> keys = jedis.keys("*" + key + "*");
		redisConnect.close();
		return keys;
	}

	/**
	 * Key 값 유무
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Boolean getBooleanKey(String key) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return null;
		}
		Boolean ex = jedis.exists(key);
		redisConnect.close();
		return ex;
	}

	/**
	 * sec(초) 만큼 해당 key를 key로 갖는 데이터 유지 7일 : 604,800 초 , 1일 : 86400 초
	 * 
	 * @param key
	 * @param sec
	 * @return
	 * @throws Exception
	 */
	public int setExpireKey(String key, int sec) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return 1;
		}
		jedis.expire(key, sec);
		redisConnect.close();
		return 0;
	}

	/**
	 * expire 값 취소
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public int setPersistKey(String key) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return 1;
		}
		jedis.persist(key);
		redisConnect.close();
		return 0;
	}

	/**
	 * KEY 삭제
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public int setDelKey(String key) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return 1;
		}
		jedis.del(key);
		redisConnect.close();
		return 0;
	}

	/**
	 * Redis에 접속된 Client 정보 확인
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getClientList() throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return null;
		}
		String clientList = jedis.clientList();
		redisConnect.close();
		return clientList;
	}

	/**
	 * Redis 기본정보 확인
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getRedisInfo() throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return null;
		}
		String redisInfo = jedis.info();
		redisConnect.close();
		return redisInfo;
	}

	/**
	 * Object 중복 및 데이터형태 체크
	 * 
	 * @param <T>
	 * @param jedis
	 * @param key
	 * @param T
	 * @return
	 * @throws Exception
	 */
	public <T> int getRedisKeyClassCheck(Jedis jedis, String key, Class<?> T) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		if (jedis.exists(key) == true) {
			log.info("=== Redis Key Duplicate");
			try {
				mapper.readValue(jedis.get(key), T);
				mapper = null;
			} catch (Exception e) {
				log.info(e.toString());
				redisConnect.close();
				return 3; // 중복 키 , object 형태 틀림
			}
			redisConnect.close();
			return 2; // 중복 키 , object 형태 맞음
		}
		redisConnect.close();
		return 0; // 이상 없을 시
	}

	/**
	 * List<Object> 중복 및 데이터형태 체크
	 * 
	 * @param <T>
	 * @param jedis
	 * @param key
	 * @param T
	 * @return
	 * @throws Exception
	 */
	public <T> int getRedisKeyListCheck(Jedis jedis, String key, List<T> T) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		if (jedis.exists(key) == true) {
			log.info("=== Redis Key Duplicate");
			try {
				mapper.readValue(jedis.get(key), new TypeReference<List<T>>() {
				});
				mapper = null;
			} catch (Exception e) {
				log.info(e.toString());
				redisConnect.close();
				return 3; // 중복 키 , object 형태 틀림
			}
			redisConnect.close();
			return 2; // 중복 키 , object 형태 맞음
		}
		redisConnect.close();
		return 0; // 이상 없을 시
	}

	/**
	 * hset KEY, Field 삭제
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public int hdelKeyField(String key, String field) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return 1;
		}
		long value = jedis.hdel(key, field); // 리턴값 : 삭제된 수
		if (value == 0) {
			log.info("=== Redis hdel null");
			redisConnect.close();
			return 1;
		}
		redisConnect.close();
		return 0;
	}

	/**
	 * Hkey의 Field 정보 가져오기
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Set<String> getHkeyField(String key) throws Exception {
		Jedis jedis = redisConnect.connect();
		if (jedis == null) {
			log.info("=== Redis Connect Error");
			redisConnect.close();
			return null;
		}
		Set<String> setString = jedis.hkeys(key);
		redisConnect.close();
		return setString;
	}

}
