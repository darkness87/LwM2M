package com.cnu.lwm2m.server.service;

import java.util.ArrayList;
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

		return targetList;
	}

	public String getRedisKeyData(String key) throws Exception {
		String strKey = redisDao.getRedisStringData(key);
		return strKey;
	}
}