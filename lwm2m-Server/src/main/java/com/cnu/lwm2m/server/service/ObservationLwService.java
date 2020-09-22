package com.cnu.lwm2m.server.service;

import java.util.List;
import java.util.Set;

import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.server.observation.ObservationService;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ObservationLwService {
	@Autowired
	RegistrationService regService;

	@Autowired
	ObservationService observeService;

	public List<Observation> getObservationList(String endpoint) {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());

		for (Registration registration : allRegistrations) {
			if (!registration.getEndpoint().equals(endpoint)) {
				continue;
			}

			Set<Observation> observations = observeService.getObservations(registration);
			List<Observation> list = Lists.newArrayList(observations.iterator());

			/*
			 * for (Observation ob : list) { log.info(ob.getRegistrationId()); }
			 */

			return list;
		}
		return null;
	}
	
	public int cancelObservations(String endpoint) {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());

		int result = 0;
		
		for (Registration registration : allRegistrations) {
			if (!registration.getEndpoint().equals(endpoint)) {
				continue;
			}
			result = observeService.cancelObservations(registration);
			log.info("{}",result);
			return result;
		}
		return result;
	}
	
}
