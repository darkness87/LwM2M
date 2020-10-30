package com.cnu.lwm2m.server.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.lwm2m.server.dao.RedisDao;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RedisService {

	@Autowired
	RedisDao redisDao;
	@Autowired
	ObjectMapper mapper;

	public List<String> getKeyList() throws Exception {
		Set<String> strKey = redisDao.getAllKey();

		List<String> targetList = new ArrayList<String>(strKey);
		log.info("{}", targetList);

		List<String> tList = new ArrayList<String>();
		String targetKey = null;
		String keyType = null;
		for (int i = 0; i < targetList.size(); i++) {
			targetKey = targetList.get(i);
			keyType = redisDao.getKeyType(targetKey);
			tList.add(targetKey + "|" + keyType);
		}

		return tList;
	}

	public String getRedisKeyData(String key) throws Exception {
		String strKey = redisDao.getRedisStringData(key);
		return strKey;
	}

	public void setHistory(String keyValue, String type, String data) throws Exception {
		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String idate = baseFormat.format(date);

		String key = "HIS:" + type + ":" + keyValue + ":" + idate;
		String setString = data;
		log.info("{},{}", key, setString);

		int result = redisDao.setRedisStringData(key, setString);
		log.info("result : {}", result);
	}

	public String getHgetAllData(String key) throws Exception {
		String result = redisDao.hgetAllData(key);
		return result;
	}
}