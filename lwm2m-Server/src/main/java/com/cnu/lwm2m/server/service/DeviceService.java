package com.cnu.lwm2m.server.service;

import java.util.List;
import java.util.Set;

import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
import org.eclipse.leshan.server.observation.ObservationService;
import org.eclipse.leshan.server.queue.PresenceService;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class DeviceService {
	@Autowired
	RegistrationService regService;

	@Autowired
	ObservationService observeService;

	@Autowired
	PresenceService prsenceService;

	@Autowired
	LwM2mModelProvider modelProvider;

	public List<Registration> getRegistrationsList() {
		return Lists.newArrayList(regService.getAllRegistrations());
	}

	public List<Observation> getObservationList(String endpoint) {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());

		for (Registration registration : allRegistrations) {
			if (!registration.getEndpoint().equals(endpoint)) {
				continue;
			}

			Set<Observation> observations = observeService.getObservations(registration);
			List<Observation> list = Lists.newArrayList(observations.iterator());

			for (Observation ob : list) {
				System.out.println(ob.getRegistrationId());
			}

			return list;
		}

		return null;
	}

	public void awakeAllDevice() {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());

		for (Registration registration : allRegistrations) {
			prsenceService.isClientAwake(registration);
		}
	}

	public LwM2mModel getObjectModel(String endpoint) {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());

		for (Registration registration : allRegistrations) {
			if (!registration.getEndpoint().equals(endpoint)) {
				continue;
			}

			return modelProvider.getObjectModel(registration);
		}

		return null;
	}
}
