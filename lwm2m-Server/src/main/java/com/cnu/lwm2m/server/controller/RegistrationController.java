package com.cnu.lwm2m.server.controller;

import java.util.List;

import org.eclipse.leshan.core.Link;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cnu.lwm2m.server.service.RegistrationLwService;
import com.cnu.lwm2m.server.vo.RegistrationDataVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RegistrationController {
	@Autowired
	RegistrationLwService registrationLwService;

	@RequestMapping("/getAllRegistrations.do")
	public @ResponseBody List<RegistrationDataVO> getAllRegistrations() {
		return registrationLwService.getRegistrationsList();
	}

	@RequestMapping("/getById.do")
	public @ResponseBody Registration getById(@RequestParam String id) {
		log.info("{}", registrationLwService.getById(id));
		return registrationLwService.getById(id);
	}

	@RequestMapping("/getByEndpoint.do")
	public @ResponseBody Registration getByEndpoint(@RequestParam String endpoint) {
		log.info("{}", registrationLwService.getByEndpoint(endpoint));
		return registrationLwService.getByEndpoint(endpoint);
	}

	@RequestMapping("/getObjectModel.do")
	public @ResponseBody LwM2mModel getObjectModel(@RequestParam String endpoint) {
		LwM2mModel model = registrationLwService.getObjectModel(endpoint);
		return model;
	}

	@RequestMapping("/getResourceModel.do")
	public @ResponseBody Link[] getResourceModel(@RequestParam String endpoint) {
		Link[] model = registrationLwService.getResourceModel(endpoint);
//		log.info("=== {}", model.getResourceModel(26241, 1));
		return model;
	}

}