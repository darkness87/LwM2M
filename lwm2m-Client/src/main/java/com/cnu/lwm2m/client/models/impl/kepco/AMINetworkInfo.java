package com.cnu.lwm2m.client.models.impl.kepco;

public interface AMINetworkInfo {
	public int getWanCode();

	public int getCommTypeCode();

	public int getTeleCompany();

	public String getPhoneNumber();

	public String getIpAddress();

	public String getBufferIpAddress();

	public String getCommModuleCompany();

	public String getCommModuleChipCompany();

	public int getTxPower();

	public String getCoapPingResult();

	public String getThroughputTestResult();
}