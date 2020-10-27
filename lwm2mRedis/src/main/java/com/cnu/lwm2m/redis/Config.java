package com.cnu.lwm2m.redis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.21
 */
public class Config {

	static final Logger log = LoggerFactory.getLogger(Config.class);

	private Properties properties = null;

	public Config() {

		properties = new Properties();
		FileInputStream file = null;
		try {
			file = new FileInputStream("RedisConfig.properties"); // 파일 위치
			properties.load(file);

		} catch (FileNotFoundException e) {
			e.printStackTrace();

			// 파일이 없을 경우 config.properties 파일을 생성
			File profile = new File("RedisConfig.properties");
			FileOutputStream upfile = null;
			try {
				upfile = new FileOutputStream(profile);

				properties.setProperty("redis.ipAddr", "127.0.0.1"); // 내부 Redis
				properties.setProperty("redis.port", "6379");
				properties.setProperty("redis.timeout", "1000");
				properties.setProperty("redis.passWord", "");

				try {
					properties.store(upfile, null);
					upfile.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				log.info("RedisConfig.properties / File Created");

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getIpAddr() {
		Config config = new Config();
		String val = config.getProperties().getProperty("redis.ipAddr");

		if (val == null || val == "" || val.equals(null) || val.equals("")) {
			val = "127.0.0.1"; // 내부 Redis
			propertiesAdd("redis.ipAddr", val);
		}
		return val;
	}

	public int getPort() {
		Config config = new Config();
		String val = config.getProperties().getProperty("redis.port");

		if (val == null || val == "" || val.equals(null) || val.equals("")) {
			val = "6379";
			propertiesAdd("redis.port", val);
		}
		return Integer.parseInt(val);
	}

	public int getTimeout() {
		Config config = new Config();
		String val = config.getProperties().getProperty("redis.timeout");

		if (val == null || val == "" || val.equals(null) || val.equals("")) {
			val = "1000";
			propertiesAdd("redis.timeout", val);

		}
		return Integer.parseInt(val);
	}

	public String getPassWord() {
		Config config = new Config();
		String val = config.getProperties().getProperty("redis.passWord");

		if (val == "") {
			val = null;
		} else if (val == null || val.equals(null)) {
			val = null;
			propertiesAdd("redis.passWord", "");
		} else if (val.equals("")) {
			val = null;
		}

		return val;
	}

	public void propertiesAdd(String pro, String val) {

		File profile = new File("RedisConfig.properties");
		FileOutputStream upfile = null;
		try {
			upfile = new FileOutputStream(profile);
			properties.setProperty(pro, val);
			try {
				properties.store(upfile, null);
				upfile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			log.info("RedisConfig.properties / " + pro + " / " + val + " / create");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
