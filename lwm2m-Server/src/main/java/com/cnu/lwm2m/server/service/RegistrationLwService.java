package com.cnu.lwm2m.server.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.leshan.core.Link;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
import org.eclipse.leshan.server.observation.ObservationService;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.lwm2m.server.vo.RegistrationDataVO;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RegistrationLwService {
	@Autowired
	RegistrationService regService;

	// TODO
	/*
	 * @Autowired RegistrationStore registrationStore;
	 */

	@Autowired
	LwM2mModelProvider modelProvider;

	@Autowired
	ObservationService observeService;

	public List<RegistrationDataVO> getRegistrationsList() {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());
		List<RegistrationDataVO> list = new ArrayList<RegistrationDataVO>();
		RegistrationDataVO registrationDataVO = new RegistrationDataVO();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (Registration registration : allRegistrations) {
			registrationDataVO = new RegistrationDataVO();
			registrationDataVO.setEndpoint(registration.getEndpoint());
			registrationDataVO.setId(registration.getId());
			registrationDataVO.setAddress(registration.getAddress());
			registrationDataVO.setPort(registration.getPort());
			registrationDataVO.setLwM2mVersion(registration.getLwM2mVersion());
			registrationDataVO.setBindingMode(registration.getBindingMode());
			registrationDataVO.setLifeTimeInSec(registration.getLifeTimeInSec());
			registrationDataVO.setRegistrationDate(dateFormat.format(registration.getRegistrationDate()));
			registrationDataVO.setLastUpdate(dateFormat.format(registration.getLastUpdate()));

			list.add(registrationDataVO);
		}

		return list;
	}

	public List<Registration> getAllRegistrationsList() {
		return Lists.newArrayList(regService.getAllRegistrations());
	}

	public int getAllRegistrationsListCount() {
		return Lists.newArrayList(regService.getAllRegistrations()).size();
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

			log.info("=== {}", registration);

			return modelProvider.getObjectModel(registration);
		}

		return null;
	}

	public Link[] getResourceModel(String endpoint) {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());

		for (Registration registration : allRegistrations) {
			if (!registration.getEndpoint().equals(endpoint)) {
				continue;
			}

			log.info("=== {}", registration);
			Link[] link = registration.getObjectLinks();

			return link;
		}

		return null;
	}

	// TODO 작업진행중
	public int unRegistration(String endpoint) {

		// TODO

//		Iterator<Registration> reg = regService.getAllRegistrations();

		while (regService.getAllRegistrations().hasNext()) {
			if (!regService.getAllRegistrations().next().getEndpoint().equals(endpoint)) {
				continue;
			}

//			registrationStore.removeRegistration(regService.getAllRegistrations().next().getId());
		}

		// TODO
		/*
		 * List<Registration> allRegistrations =
		 * Lists.newArrayList(regService.getAllRegistrations()); RegistrationListener
		 * listener = null;
		 * 
		 * for (Registration registration : allRegistrations) { if
		 * (!registration.getEndpoint().equals(endpoint)) { continue; }
		 * listener.unregistered(registration,
		 * observeService.getObservations(registration), false, null);
		 * regService.removeListener(listener); }
		 */
		return 0;
	}

}