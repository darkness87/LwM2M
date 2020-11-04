package com.cnu.lwm2m.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnu.lwm2m.server.service.MainService;
import com.cnu.lwm2m.server.vo.MainUsageVO;

@Controller
public class MainController {

	@Autowired
	MainService mainService;

	@RequestMapping({ "/", "/main" })
	public String main() {
		return "index.html";
	}

	@RequestMapping("/getUsage.do")
	public @ResponseBody MainUsageVO getUsage() {
		MainUsageVO mainUsageVO = mainService.getUsage();
		return mainUsageVO;
	}
}