package com.cnu.lwm2m.server.controller;

import java.util.List;

import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cnu.lwm2m.server.service.DeviceService;

@Controller
public class DeviceController {
	@Autowired
	DeviceService deviceService;

	@RequestMapping("/getAllRegistrations.do")
	public List<Registration> getAllRegistrations() {
		return deviceService.getRegistrationsList();
	}

	@RequestMapping("/getObservationList.do")
	public List<Observation> getObservationList(@RequestParam String endpoint) {
		return deviceService.getObservationList(endpoint);
	}

	@RequestMapping("/awakeDevice.do")
	public void awakeDevice() {
		deviceService.awakeAllDevice();
	}

	@RequestMapping("/getObjectModel.do")
	public LwM2mModel getObjectModel(@RequestParam String endpoint) {
		LwM2mModel model = deviceService.getObjectModel(endpoint);

		return model;
	}
}
