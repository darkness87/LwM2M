package com.cnu.lwm2m.client.models.impl.oma;

import java.util.Date;

public interface LocationInfo {
	public float getLatitude();
	public float getLongitude();
	public Date getTimestamp();
}