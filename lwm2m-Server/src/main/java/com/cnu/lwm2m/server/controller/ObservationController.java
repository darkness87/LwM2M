package com.cnu.lwm2m.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cnu.lwm2m.server.service.ObservationLwService;
import com.cnu.lwm2m.server.service.RegistrationLwService;
import com.cnu.lwm2m.server.vo.ObserveVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ObservationController {
	@Autowired
	ObservationLwService observationLwService;

	@Autowired
	RegistrationLwService registrationLwService;

	@RequestMapping("/getObservationList.do")
	public @ResponseBody List<ObserveVO> getObservationList() {
		log.info("=== Observe All List ===");
		return observationLwService.getObservationList();
	}

	@RequestMapping("/cancelObservations.do")
	public @ResponseBody int cancelObservations() {
		log.info("=== Observe All Cancel ===");
		return observationLwService.cancelObservations();
	}

	@RequestMapping("/cancelResourceObservation.do")
	public @ResponseBody int cancelResourceObservation(@RequestParam String endpoint, @RequestParam String uri) {
		log.info("=== Observe " + uri + " Cancel ===");
		return observationLwService.cancelResourceObservation(endpoint, uri);
	}

	@RequestMapping("/cancelRegistrationIdObservation.do")
	public @ResponseBody int cancelRegistrationIdObservation(@RequestParam String id, @RequestParam String uri) {
		log.info("=== Observe " + uri + " Cancel ===");
		return observationLwService.cancelRegistrationIdObservation(id, uri);
	}

}