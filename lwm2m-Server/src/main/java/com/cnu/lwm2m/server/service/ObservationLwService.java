package com.cnu.lwm2m.server.service;

import java.util.ArrayList;
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

	public List<Observation> getObservationList() {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());
		List<Observation> list = new ArrayList<Observation>();

		for (Registration registration : allRegistrations) {
			Set<Observation> observations = observeService.getObservations(registration);
			list.addAll(Lists.newArrayList(observations.iterator()));
		}
		log.info("{}", list);

		return list;
	}

	public int cancelObservations() {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());
		int result = 0;

		for (Registration registration : allRegistrations) {
			result = observeService.cancelObservations(registration);
			return result;
		}

		return result;
	}

}