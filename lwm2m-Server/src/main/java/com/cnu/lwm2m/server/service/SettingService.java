package com.cnu.lwm2m.server.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.cnu.lwm2m.server.vo.PropertyVO;

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

		return properties;
	}

	public int setProperty(PropertyVO propertyVO) throws Exception {

		Properties properties = new Properties();

		File profile = new File("RedisConfig.properties");
		FileOutputStream upfile = new FileOutputStream(profile);

		properties.setProperty("redis.ipAddr", "127.0.0.1");

		properties.store(upfile, null);
		upfile.close();

		return 0;
	}

}
