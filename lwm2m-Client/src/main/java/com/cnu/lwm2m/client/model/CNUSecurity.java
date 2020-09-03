package com.cnu.lwm2m.client.model;

import org.eclipse.leshan.client.object.Security;

import com.cnu.lwm2m.client.model.impl.SecurityInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 이곳에다가 kcmvp 모듈을 넣어야할듯...
 * @author darka87
 *
 */
@Slf4j
public class CNUSecurity extends Security {

	public CNUSecurity() {
		// should only be used at bootstrap time
	}

	public CNUSecurity(String serverUri, boolean bootstrapServer, int securityMode, byte[] publicKeyOrIdentity,
			byte[] serverPublicKey, byte[] secretKey, Integer shortServerId) {
		super(serverUri, bootstrapServer, securityMode, publicKeyOrIdentity, serverPublicKey, secretKey, shortServerId);
	}

	public static Security kepcoKcmvp(String serverUri, SecurityInfo securityInfo) {
		log.debug("임시 생성 {}", serverUri);
		return noSec(serverUri, securityInfo.getServerID());
//		return new Security(serverUri, true, SecurityMode.NO_SEC.code, new byte[0], new byte[0], new byte[0], 0);
	}
}