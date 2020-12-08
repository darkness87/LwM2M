package com.cnu.lwm2m.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnu.lwm2m.server.service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {
	@Autowired
	LoginService loginService;

	@RequestMapping("/login.do")
	public @ResponseBody int login(@RequestParam String id, @RequestParam String pw) {

		boolean result = loginService.login(id,pw);
		log.info("{}", result);

		if (result == true) {
			return 0;
		} else if (result == false) {
			return 1;
		}

		return 1;
	}

}