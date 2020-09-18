package com.cnu.lwm2m.server.controller;

import java.util.List;

import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnu.lwm2m.server.service.DeviceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DeviceController {
	@Autowired
	DeviceService deviceService;

	@RequestMapping("/getAllRegistrations.do")
	public @ResponseBody List<Registration> getAllRegistrations() {
		return deviceService.getRegistrationsList();
	}

	@RequestMapping("/getObjectModel.do")
	public @ResponseBody LwM2mModel getObjectModel(@RequestParam String endpoint) {
		LwM2mModel model = deviceService.getObjectModel(endpoint);
		return model;
	}
	
	@RequestMapping("/getObservationList.do")
	public @ResponseBody List<Observation> getObservationList(@RequestParam String endpoint) {
		return deviceService.getObservationList(endpoint);
	}
	
	@RequestMapping("/cancelObservations.do")
	public @ResponseBody int cancelObservations(@RequestParam String endpoint) {
		return deviceService.cancelObservations(endpoint);
	}
	
	@RequestMapping("/awakeDevice.do")
	public @ResponseBody boolean awakeDevice() {
		boolean result = deviceService.awakeAllDevice();
		log.info("=== awakeAllDevice : "+result);
		return result;
	}
	
	
	
}
