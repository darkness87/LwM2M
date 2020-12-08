package com.cnu.lwm2m.server.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

	public boolean login(String id, String pw) {
		boolean result = false;

		if (id.equals("admin") && pw.equals("admin123")) { // TODO 임시계정 // 추후 DB연동 필요
			result = true;
		}

		return result;
	}

}