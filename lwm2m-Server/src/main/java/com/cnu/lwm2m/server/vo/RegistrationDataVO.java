package com.cnu.lwm2m.server.vo;

import java.net.InetAddress;
import java.util.Date;

import org.eclipse.leshan.core.request.BindingMode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDataVO {

	private String endpoint;
	private String id;
	private InetAddress address;
	private int port;
	private String lwM2mVersion;
	private BindingMode bindingMode;
	private long lifeTimeInSec;
	private String registrationDate;
	private String lastUpdate;
	
}
