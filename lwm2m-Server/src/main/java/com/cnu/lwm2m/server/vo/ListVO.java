package com.cnu.lwm2m.server.vo;

import java.util.ArrayList;
import java.util.List;

public class ListVO extends ResponseDataVO {
	private static final long serialVersionUID = 1L;

	private int totalCount;

	private List<ResponseDataVO> dataList;

	public ListVO() {
		super();
	}

	public ListVO(int totalCount) {
		super();
		this.totalCount = totalCount;
	}

	public ListVO(List<ResponseDataVO> dataList, int totalCount) {
		super();
		this.totalCount = totalCount;
		this.dataList = dataList;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalNum) {
		this.totalCount = totalNum;
	}

	public List<ResponseDataVO> getDataList() {
		if (dataList != null) {
			return new ArrayList<ResponseDataVO>(dataList);
		} else {
			return null;
		}
	}

	public void setDataList(List<ResponseDataVO> dataList) {
		this.dataList = dataList;
	}
}