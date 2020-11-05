package com.cnu.lwm2m.server.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnu.lwm2m.server.service.SettingService;
import com.cnu.lwm2m.server.vo.PropertyVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SettingController {

	@Autowired
	SettingService settingService;

	@SuppressWarnings("rawtypes")
	@RequestMapping("/getProperty.do")
	public @ResponseBody List<PropertyVO> getProperty(@RequestParam String fileName) {

		List<PropertyVO> list = new ArrayList<PropertyVO>();
		PropertyVO propertyVO = new PropertyVO();

		Properties properties = settingService.getProperty(fileName);
		log.info("{}", properties);

		String key = null;
		Set<Object> keydata = properties.keySet();
		Iterator iterator = keydata.iterator();
		while (iterator.hasNext()) {
			propertyVO = new PropertyVO();
			key = (String) iterator.next();
			propertyVO.setKey(key);
			propertyVO.setValue(properties.getProperty(key));
			list.add(propertyVO);
		}

		return list;
	}

}
