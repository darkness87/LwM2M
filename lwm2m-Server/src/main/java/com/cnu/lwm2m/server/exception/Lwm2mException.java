package com.cnu.lwm2m.server.exception;

import com.cnu.lwm2m.server.common.PropertyMessage;

public class Lwm2mException extends Exception {

	private static final long serialVersionUID = 4276715518711291345L;

	protected String faultCode;

	public Lwm2mException(String msg) {
		super(msg);
	}
	// code와 message가 동일 할 경우
	public Lwm2mException(int faultCode) {
		super(PropertyMessage.getCodeMessage(faultCode));
		this.faultCode = Integer.toString(faultCode);
	}
	public Lwm2mException(int faultCode, String...values) {
		super(PropertyMessage.getCodeMessage(faultCode));
		this.faultCode = Integer.toString(faultCode);
	}

	public Lwm2mException(int faultCode, String msg) {
		super(msg);
		this.faultCode = Integer.toString(faultCode);
	}

	// code와 message code가 동일 할경우
	public Lwm2mException(int faultCode, Throwable cause) {
		super(PropertyMessage.getCodeMessage(faultCode), cause);
		this.faultCode = Integer.toString(faultCode);
	}

	public Lwm2mException(int faultCode, String msg, Throwable cause) {
		super(msg, cause);
		this.faultCode = Integer.toString(faultCode);
	}

	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}

	public String getFaultCode() {
		return this.faultCode;
	}

	public String getMessage() {
		String message = super.getMessage();

		if (this.faultCode != null) {
			if (message != null) {
				message = new StringBuilder(message.length() + 16)
						.append("[").append(this.faultCode).append("] ")
						.append(message)
						.toString();
			} else {
				message = new StringBuffer(16).append("[").append(
						this.faultCode).append("]").toString();
			}
		}

		return message;
	}
}