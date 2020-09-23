package com.cnu.lwm2m.server.controller;

import java.util.List;

import org.eclipse.leshan.core.observation.Observation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cnu.lwm2m.server.service.ObservationLwService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ObservationController {
	@Autowired
	ObservationLwService observationLwService;

	@RequestMapping("/getObservationList.do")
	public @ResponseBody List<Observation> getObservationList(@RequestParam String endpoint) {
		log.info("");
		return observationLwService.getObservationList(endpoint);
	}

	@RequestMapping("/cancelObservations.do")
	public @ResponseBody int cancelObservations(@RequestParam String endpoint) {
		return observationLwService.cancelObservations(endpoint);
	}

}