package com.cnu.lwm2m.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@RequestMapping({ "/", "/main" })
	public String main() {
		return "index.html";
	}
}