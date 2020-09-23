package com.cnu.lwm2m.server.service;

import java.util.List;

import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RegistrationLwService {
	@Autowired
	RegistrationService regService;

	@Autowired
	LwM2mModelProvider modelProvider;

	public List<Registration> getRegistrationsList() {
		return Lists.newArrayList(regService.getAllRegistrations());
	}

	public Registration getById(String id) {
		return regService.getById(id);
	}

	public Registration getByEndpoint(String endpoint) {
		return regService.getByEndpoint(endpoint);
	}

	public LwM2mModel getObjectModel(String endpoint) {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());

		for (Registration registration : allRegistrations) {
			if (!registration.getEndpoint().equals(endpoint)) {
				continue;
			}
			
			log.info("{}", registration);
			
			return modelProvider.getObjectModel(registration);
		}

		return null;
	}

}