package com.cnu.lwm2m.server.vo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResponseHeadVO implements Cloneable {
	static Logger logger = LogManager.getLogger(ResponseHeadVO.class);
	String resultCode;
	String resultMessage;

	public ResponseHeadVO() {
		super();
	}

	public ResponseHeadVO(String resultCode, String resultMessage) {
		super();
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}

	public ResponseHeadVO(int resultCode, String resultMessage) {
		this(String.valueOf(resultCode), resultMessage);
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			logger.info(e);
			return null;
		}
	}// end clone
}