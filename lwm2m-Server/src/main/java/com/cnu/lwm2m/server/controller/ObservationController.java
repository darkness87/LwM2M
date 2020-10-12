package com.cnu.lwm2m.server.controller;

import java.util.List;

import org.eclipse.leshan.core.observation.Observation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cnu.lwm2m.server.service.ObservationLwService;
import com.cnu.lwm2m.server.service.RegistrationLwService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ObservationController {
	@Autowired
	ObservationLwService observationLwService;

	@Autowired
	RegistrationLwService registrationLwService;

	@RequestMapping("/getObservationList.do")
	public @ResponseBody List<Observation> getObservationList() {
		log.info("=== Observe All List ===");
		return observationLwService.getObservationList();
	}

	@RequestMapping("/cancelObservations.do")
	public @ResponseBody int cancelObservations() {
		log.info("=== Observe All Cancel ===");
		return observationLwService.cancelObservations();
	}

}