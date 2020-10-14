package com.cnu.lwm2m.client.models.impl.kepco;

public interface AMIServerInfo {
	public String getBootstrapIp();

	public int getBootstrapPort();

	public String getSecurityIp();

	public int getSecurityPort();

	public String getSecurityProxyIp();

	public int getSecurityProxyPort();

	public String getServerIp();

	public int getServerPort();

	public String getProxyServerIp();

	public int getProxyServerPort();

	public String getDlmsMasterIp();

	public int getDlmsMasterPort();

	public String getDlmsManagerIp();

	public int getDlmsManagerPort();
}