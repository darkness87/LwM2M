package com.cnu.lwm2m.redis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class Config{

	private Properties properties = null;

	public Config() {

		properties = new Properties();
		FileInputStream file = null;
		try {
			file = new FileInputStream("config.properties"); //파일 위치
			properties.load(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
			// 파일이 없을 경우 config.properties 파일을 생성해야한다..
			File profile = new File("config.properties");
			FileOutputStream upfile = null;
			try {
				upfile = new FileOutputStream(profile);
				
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

	/*
	 * public static void main(String[] args) { Config testConfig = new Config();
	 * 
	 * System.out.println(testConfig.getProperties().getProperty("redis.ipAddr"));
	 * System.out.println(testConfig.getProperties().getProperty("redis.timeout"));
	 * System.out.println(testConfig.getProperties().getProperty("redis.port"));
	 * System.out.println(testConfig.getProperties().getProperty("redis.passWord"));
	 * 
	 * Enumeration<String> e = (Enumeration<String>)
	 * testConfig.getProperties().propertyNames(); while (e.hasMoreElements()) {
	 * System.out.println(e.nextElement()); } }
	 */
	
	public String getIpAddr() {
		Config config = new Config();
		String val = config.getProperties().getProperty("redis.ipAddr");
		
		if(val==null||val==""||val.equals(null)||val.equals("")) {
			val="127.0.0.1"; // 초기세팅값
		}
		return val;
	}
	public int getPort() {
		Config config = new Config();
		String val = config.getProperties().getProperty("redis.port");
		
		if(val==null||val==""||val.equals(null)||val.equals("")) {
			val="6379"; // 초기세팅값
		}
		return Integer.parseInt(val);
	}
	public int getTimeout() {
		Config config = new Config();
		String val = config.getProperties().getProperty("redis.timeout");
		
		if(val==null||val==""||val.equals(null)||val.equals("")) {
			val="1000"; // 초기세팅값
		}
		return Integer.parseInt(val);
	}
	public String getPassWord() {
		Config config = new Config();
		String val = config.getProperties().getProperty("redis.passWord");
		
		if(val==null||val==""||val.equals(null)||val.equals("")) {
			val=null; // 초기세팅값
		}
		return val;
	}
	
}
