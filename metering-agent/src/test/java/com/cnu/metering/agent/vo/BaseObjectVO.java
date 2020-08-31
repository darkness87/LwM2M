package com.cnu.metering.agent.vo;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.10
 */
public class BaseObjectVO {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = -1234567891234567890L;
	
	private String key; // 키
	private String value; // 값

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
