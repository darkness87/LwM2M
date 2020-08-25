package com.cnu.lwm2m.client.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonService extends AbsService {
	public long getTime() {
		Date date = new Date();
		log.info("getTime: {}", date);
		return date.getTime();
	}
}