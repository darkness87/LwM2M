package com.cnu.lwm2m.client.models.impl.oma;

import org.eclipse.leshan.core.request.BindingMode;

public interface ServerInfo {
	public int getServerID();

	public long getLifeTime();

	public BindingMode getBinding();

	public boolean isNotifyWhenDisable();

	public Long getDefaultMinPeriod();

	public Long getDefaultMaxPeriod();

	public int getDisableTimeout();
}