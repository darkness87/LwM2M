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

import com.cnu.lwm2m.server.vo.ObserveVO;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ObservationLwService {
	@Autowired
	RegistrationService regService;

	@Autowired
	ObservationService observeService;

	public List<ObserveVO> getObservationList() {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());
		List<Observation> list = new ArrayList<Observation>();

		for (Registration registration : allRegistrations) {
			Set<Observation> observations = observeService.getObservations(registration);
			list.addAll(Lists.newArrayList(observations.iterator()));
		}
		log.info("{}", list);

		List<ObserveVO> observelist = new ArrayList<ObserveVO>();
		ObserveVO observeVO = new ObserveVO();

		for (int i = 0; i < list.size(); i++) {
			observeVO = new ObserveVO();
			String reid = list.get(i).getRegistrationId();
			Registration registration = regService.getById(reid);

//			log.info("{}", registration);

			observeVO.setId(list.get(i).getId());

			observeVO.setRegistrationId(list.get(i).getRegistrationId());
			observeVO.setContentFormat(list.get(i).getContentFormat());
			observeVO.setPath(list.get(i).getPath());
			observeVO.setContext(list.get(i).getContext());

			observeVO.setEndpoint(registration.getEndpoint());
			observeVO.setAddress(registration.getAddress());

			observelist.add(observeVO);
		}

		return observelist;
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

	public int cancelResourceObservation(String endpoint, String uri) {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());
		int result = 0;

		for (Registration registration : allRegistrations) {
			if (!registration.getEndpoint().equals(endpoint)) {
				continue;
			}
			result = observeService.cancelObservations(registration, uri);

			return result;
		}

		return result;
	}

	public int cancelRegistrationIdObservation(String id, String uri) {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());
		int result = 0;

		for (Registration registration : allRegistrations) {
			if (!registration.getId().equals(id)) {
				continue;
			}
			result = observeService.cancelObservations(registration, uri);

			return result;
		}

		return result;
	}

	public int getObservationListCount() {
		List<Registration> allRegistrations = Lists.newArrayList(regService.getAllRegistrations());
		List<Observation> list = new ArrayList<Observation>();

		for (Registration registration : allRegistrations) {
			Set<Observation> observations = observeService.getObservations(registration);
			list.addAll(Lists.newArrayList(observations.iterator()));
		}

		return list.size();
	}

}