package com.cnu.lwm2m.server.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SettingService {

	public Properties getProperty(String fileName) {
		Properties properties = new Properties();
		FileInputStream file = null;
		try {
			file = new FileInputStream(fileName);
			properties.load(file);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		log.info("{}",properties);
		return properties;
	}

}
