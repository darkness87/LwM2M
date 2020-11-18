package com.cnu.lwm2m.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnu.lwm2m.server.service.LocationService;
import com.cnu.lwm2m.server.service.MainService;
import com.cnu.lwm2m.server.service.RegistrationLwService;
import com.cnu.lwm2m.server.vo.LocationVO;
import com.cnu.lwm2m.server.vo.RegistrationDataVO;

@Controller
public class LocationController {

	@Autowired
	LocationService locationService;

	@Autowired
	MainService mainService;

	@Autowired
	RegistrationLwService registrationLwService;

	@RequestMapping("/getLocation.do")
	public @ResponseBody List<LocationVO> getLocation() {

		List<RegistrationDataVO> reg = registrationLwService.getRegistrationsList();
		HashMap<String, Object> location = null;

		List<LocationVO> list = new ArrayList<LocationVO>();
		LocationVO locationVO = new LocationVO();

		for (int i = 0; i < reg.size(); i++) {
			locationVO = new LocationVO();
			location = locationService.getLocation(reg.get(i).getAddress().toString());

			if (location.get("message").equals("private range") && location.get("status").equals("fail") || location == null) {
				location = locationService.getLocation(mainService.getExternalIP());
			}

			locationVO.setRegistrationId(reg.get(i).getId());
			locationVO.setEndpoint(reg.get(i).getEndpoint());
			locationVO.setAddress(reg.get(i).getAddress());
			locationVO.setCountry(location.get("country").toString());
			locationVO.setRegionName(location.get("regionName").toString());
			locationVO.setCity(location.get("city").toString());
			locationVO.setQuery(location.get("query").toString());
			locationVO.setLat(location.get("lat").toString());
			locationVO.setLon(location.get("lon").toString());

			list.add(locationVO);
		}

		return list;
	}

}