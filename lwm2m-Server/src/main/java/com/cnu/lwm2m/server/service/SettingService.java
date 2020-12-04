package com.cnu.lwm2m.server.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
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

	public int setProperty(String fileName, List<PropertyVO> propertyData) {

		Properties properties = new Properties();

		File profile = new File(fileName + ".properties");
		FileOutputStream upfile;
		try {
			upfile = new FileOutputStream(profile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 1;
		}

		for (int i = 0; i < propertyData.size(); i++) {
			properties.setProperty(propertyData.get(i).getKey(), propertyData.get(i).getValue());
		}

		try {
			properties.store(upfile, null);
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
		try {
			upfile.close();
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}

		return 0;
	}

}
