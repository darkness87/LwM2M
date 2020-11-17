package com.cnu.lwm2m.server.vo;

import java.net.InetAddress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationVO {

	private String registrationId;
	private String endpoint;
	private InetAddress address;
	private String country;
	private String regionName;
	private String city;
	private String query;
	private String lat;
	private String lon;

}
