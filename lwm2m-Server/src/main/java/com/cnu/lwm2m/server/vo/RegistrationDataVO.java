package com.cnu.lwm2m.server.vo;

import java.net.InetAddress;
import java.util.EnumSet;

import org.eclipse.leshan.core.LwM2m.Version;
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
	private Version lwM2mVersion;
	private EnumSet<BindingMode> bindingMode;
	private long lifeTimeInSec;
	private String registrationDate;
	private String lastUpdate;
	
}
