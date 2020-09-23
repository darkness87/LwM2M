package com.cnu.lwm2m.server.vo;

import java.util.ArrayList;
import java.util.List;

import com.cnu.lwm2m.server.common.PropertyMessage;
import com.cnu.lwm2m.server.exception.Lwm2mException;
import com.cnu.lwm2m.server.exception.ExceptionConst;

public class ResponseVO {
	private ResponseHeadVO responseHead;
	private ResponseDataVO responseData;

	public ResponseVO() {
	}

	public ResponseVO(Lwm2mException e) {
		int resultCode = Integer.parseInt(e.getFaultCode());
		String message = e.getMessage();

		if (resultCode == ExceptionConst.SUCCESS) {
			message = PropertyMessage.getCodeMessage(resultCode);
		}

		this.responseHead = new ResponseHeadVO(resultCode, message);
	}

	public ResponseVO(int resultCode) {
		this(new Lwm2mException(resultCode));
	}

	public ResponseVO(int resultCode, String... values) {
		String resultMessage = PropertyMessage.getCodeMessage(resultCode, values);
		this.responseHead = new ResponseHeadVO(resultCode, resultMessage);
	}

	public ResponseVO(int resultCode, ResponseDataVO responseData) {
		this(resultCode);
		this.responseData = responseData;
	}

	public ResponseVO(int resultCode, List<?> responseDataList) throws Lwm2mException {
		this(resultCode, convertListToObject(responseDataList));
	}

	public ResponseVO(int resultCode, List<?> responseDataList, int count) throws Lwm2mException {
		this(resultCode, convertListToObject(responseDataList, count));
	}

	public ResponseVO(int resultCode, String resultMessage) {
		this.responseHead = new ResponseHeadVO(resultCode, resultMessage);
	}

	public ResponseVO(int resultCode, String resultMessage, ResponseDataVO responseData) {
		this(resultCode, resultMessage);
		this.responseData = responseData;
	}

	// Success Code목록
	public ResponseVO(ResponseDataVO responseData) {
		this(ExceptionConst.SUCCESS, responseData);
	}

	public ResponseVO(List<?> responseDataList) throws Lwm2mException {
		this(convertListToObject(responseDataList));
	}

	public ResponseVO(List<?> responseDataList, int count) throws Lwm2mException {
		this(convertListToObject(responseDataList, count));
	}

	public ResponseHeadVO getResponseHead() {
		if (responseHead != null) {
			return (ResponseHeadVO) responseHead.clone();
		} else {
			return null;
		}
	}

	public void setResponseHead(ResponseHeadVO responseHead) {
		this.responseHead = responseHead;
	}

	public ResponseDataVO getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseDataVO responseData) {
		this.responseData = responseData;
	}

	private static ListVO convertListToObject(List<?> object) throws Lwm2mException {
		int size = 0;

		if (object != null) {
			size = object.size();
		}

		return convertListToObject(object, size);
	}

	private static ListVO convertListToObject(List<?> object, int count) throws Lwm2mException {
		List<ResponseDataVO> list = null;

		try {
			if (object != null && object.size() > 0) {
				list = new ArrayList<ResponseDataVO>();

				if (!(object.get(0) instanceof ResponseDataVO)) {
					throw new Lwm2mException(9999,
							"[" + object + "] 해당 객체는 ResponseDataVO를 상속받지 않은 객체이므로 사용하실 수 없습니다.");
				}

				for (int i = 0; i < object.size(); i++) {
					list.add((ResponseDataVO) object.get(i));
				}
			}
		} catch (IndexOutOfBoundsException e) {
			return new ListVO();
		} catch (Exception e) {
			throw new Lwm2mException(9999, "해당 객체는 ResponseDataVO를 상속받지 않은 객체이므로 사용하실 수 없습니다.");
		}

		return new ListVO(list, count);
	}
}