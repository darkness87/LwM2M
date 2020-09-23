package com.cnu.lwm2m.server.service;

import java.util.List;

import org.eclipse.leshan.server.queue.PresenceService;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PresenceLwService {
	@Autowired
	RegistrationService regService;

	@Autowired
	PresenceService prsenceService;

	public boolean awakeAllDevice() {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());

		boolean result = false;
		for (Registration registration : allRegistrations) {
			result = prsenceService.isClientAwake(registration);
			log.info("{}", result);
		}

		return result;
	}

}