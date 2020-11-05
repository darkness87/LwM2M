package com.cnu.lwm2m.server.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainUsageVO {

	private String date;
	private String osCpu;
	private String osMemory;
	private String jvmUsed;
	private String jvmFree;
	private String jvmTotal;
	private String jvmMax;
	private int observeCount;
	private int registrationCount;

}
