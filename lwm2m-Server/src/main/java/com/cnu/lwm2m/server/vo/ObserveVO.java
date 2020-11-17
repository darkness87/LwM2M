package com.cnu.lwm2m.server.vo;

import java.net.InetAddress;
import java.util.Map;

import org.eclipse.leshan.core.node.LwM2mPath;
import org.eclipse.leshan.core.request.ContentFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObserveVO {

	private byte[] id;
	private LwM2mPath path;
	private ContentFormat contentFormat;
	private String registrationId;
	private Map<String, String> context;
	private String endpoint;
	private InetAddress address;

}
