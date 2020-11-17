package com.cnu.lwm2m.server.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnu.lwm2m.server.service.MainService;
import com.cnu.lwm2m.server.service.ObservationLwService;
import com.cnu.lwm2m.server.service.RegistrationLwService;
import com.cnu.lwm2m.server.vo.MainUsageVO;

@Controller
public class MainController {

	@Autowired
	MainService mainService;

	@Autowired
	ObservationLwService observationLwService;

	@Autowired
	RegistrationLwService registrationLwService;

	@RequestMapping({ "/", "/main" })
	public String main() {
		return "login.html";
	}

	@RequestMapping("/getUsage.do")
	public @ResponseBody MainUsageVO getUsage() {
		MainUsageVO mainUsageVO = mainService.getUsage();
		mainUsageVO.setObserveCount(observationLwService.getObservationListCount());// 등록기기수
		mainUsageVO.setRegistrationCount(registrationLwService.getAllRegistrationsListCount());// Observe수
		return mainUsageVO;
	}

	@RequestMapping("/getExternalIP.do")
	public @ResponseBody String getExternalIP() {
		return mainService.getExternalIP();
	}

	@RequestMapping("/getExternalIPLocation.do")
	public @ResponseBody HashMap<String, Object> getExternalIPLocation() {
		try {
			return mainService.getExternalIPLocation();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}