package com.red.vo;

public class LpMaxDemandVo {

	private String aId; // Agent ID Agent의 고유 ID
	private String mId; // Meter ID 계기 ID
	private String cDv; // Capture Division 검침구분 (0: 정규, 1: 재검침, 2: 자율검침)
	private String pA0; // Valid Value Tariff 0 : 순방향 유효전력 #0
	private String pD0; // Valid Date Tariff 0 : 발생일자(유효) #0
	private String pT0; // Valid Total Tariff 0 : 누적순방향유효전력 #0
	private String aA0; // Apparent Value Tariff 0 : 순방향 피상전력 #0
	private String aD0; // apparent Date Tariff 0 : 발생일자(피상) #0
	private String aT0; // Apparent Total Tariff 0 : 누적순방향피상전력 #0
	private String pA1; // Valid Value Tariff 1 : 순방향 유효전력 #1
	private String pD1; // Valid Date Tariff 1 : 발생일자(유효) #1
	private String pT1; // Valid Total Tariff 1 : 누적순방향유효전력 #1
	private String aA1; // Apparent Value Tariff 1 : 순방향 피상전력 #1
	private String aD1; // apparent Date Tariff 1 : 발생일자(피상) #1
	private String aT1; // Apparent Total Tariff 1 : 누적순방향피상전력 #1
	private String pA2; // Valid Value Tariff 2 : 순방향 유효전력 #2
	private String pD2; // Valid Date Tariff 2 : 발생일자(유효) #2
	private String pT2; // Valid Total Tariff 2 : 누적순방향유효전력 #2
	private String aA2; // Apparent Value Tariff 2 : 순방향 피상전력 #2
	private String aD2; // apparent Date Tariff 2 : 발생일자(피상) #2
	private String aT2; // Apparent Total Tariff 2 : 누적순방향피상전력 #2
	private String pA3; // Valid Value Tariff 3 : 순방향 유효전력 #3
	private String pD3; // Valid Date Tariff 3 : 발생일자(유효) #3
	private String pT3; // Valid Total Tariff 3 : 누적순방향유효전력 #3
	private String aA3; // Apparent Value Tariff 3 : 순방향 피상전력 #3
	private String aD3; // apparent Date Tariff 3 : 발생일자(피상) #3
	private String aT3; // Apparent Total Tariff 3 : 누적순방향피상전력 #3
	private String pA4; // Valid Value Tariff 4 : 순방향 유효전력 #4
	private String pD4; // Valid Date Tariff 4 : 발생일자(유효) #4
	private String pT4; // Valid Total Tariff 4 : 누적순방향유효전력 #4
	private String aA4; // Apparent Value Tariff 4 : 순방향 피상전력 #4
	private String aD4; // apparent Date Tariff 4 : 발생일자(피상) #4
	private String aT4; // Apparent Total Tariff 4 : 누적순방향피상전력 #4
	private String rSt; // Requested Work Result 결과 (0:대기, 1:전달완료, 2:성공, 101:무응답, 102:미지원)
	private String bDt; // Base Date 수집기준일시
	private String cDt; // Captured Date 검침일시 (에이전트 저장시간)
	
	public String getaId() {
		return aId;
	}
	public void setaId(String aId) {
		this.aId = aId;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getcDv() {
		return cDv;
	}
	public void setcDv(String cDv) {
		this.cDv = cDv;
	}
	public String getpA0() {
		return pA0;
	}
	public void setpA0(String pA0) {
		this.pA0 = pA0;
	}
	public String getpD0() {
		return pD0;
	}
	public void setpD0(String pD0) {
		this.pD0 = pD0;
	}
	public String getpT0() {
		return pT0;
	}
	public void setpT0(String pT0) {
		this.pT0 = pT0;
	}
	public String getaA0() {
		return aA0;
	}
	public void setaA0(String aA0) {
		this.aA0 = aA0;
	}
	public String getaD0() {
		return aD0;
	}
	public void setaD0(String aD0) {
		this.aD0 = aD0;
	}
	public String getaT0() {
		return aT0;
	}
	public void setaT0(String aT0) {
		this.aT0 = aT0;
	}
	public String getpA1() {
		return pA1;
	}
	public void setpA1(String pA1) {
		this.pA1 = pA1;
	}
	public String getpD1() {
		return pD1;
	}
	public void setpD1(String pD1) {
		this.pD1 = pD1;
	}
	public String getpT1() {
		return pT1;
	}
	public void setpT1(String pT1) {
		this.pT1 = pT1;
	}
	public String getaA1() {
		return aA1;
	}
	public void setaA1(String aA1) {
		this.aA1 = aA1;
	}
	public String getaD1() {
		return aD1;
	}
	public void setaD1(String aD1) {
		this.aD1 = aD1;
	}
	public String getaT1() {
		return aT1;
	}
	public void setaT1(String aT1) {
		this.aT1 = aT1;
	}
	public String getpA2() {
		return pA2;
	}
	public void setpA2(String pA2) {
		this.pA2 = pA2;
	}
	public String getpD2() {
		return pD2;
	}
	public void setpD2(String pD2) {
		this.pD2 = pD2;
	}
	public String getpT2() {
		return pT2;
	}
	public void setpT2(String pT2) {
		this.pT2 = pT2;
	}
	public String getaA2() {
		return aA2;
	}
	public void setaA2(String aA2) {
		this.aA2 = aA2;
	}
	public String getaD2() {
		return aD2;
	}
	public void setaD2(String aD2) {
		this.aD2 = aD2;
	}
	public String getaT2() {
		return aT2;
	}
	public void setaT2(String aT2) {
		this.aT2 = aT2;
	}
	public String getpA3() {
		return pA3;
	}
	public void setpA3(String pA3) {
		this.pA3 = pA3;
	}
	public String getpD3() {
		return pD3;
	}
	public void setpD3(String pD3) {
		this.pD3 = pD3;
	}
	public String getpT3() {
		return pT3;
	}
	public void setpT3(String pT3) {
		this.pT3 = pT3;
	}
	public String getaA3() {
		return aA3;
	}
	public void setaA3(String aA3) {
		this.aA3 = aA3;
	}
	public String getaD3() {
		return aD3;
	}
	public void setaD3(String aD3) {
		this.aD3 = aD3;
	}
	public String getaT3() {
		return aT3;
	}
	public void setaT3(String aT3) {
		this.aT3 = aT3;
	}
	public String getpA4() {
		return pA4;
	}
	public void setpA4(String pA4) {
		this.pA4 = pA4;
	}
	public String getpD4() {
		return pD4;
	}
	public void setpD4(String pD4) {
		this.pD4 = pD4;
	}
	public String getpT4() {
		return pT4;
	}
	public void setpT4(String pT4) {
		this.pT4 = pT4;
	}
	public String getaA4() {
		return aA4;
	}
	public void setaA4(String aA4) {
		this.aA4 = aA4;
	}
	public String getaD4() {
		return aD4;
	}
	public void setaD4(String aD4) {
		this.aD4 = aD4;
	}
	public String getaT4() {
		return aT4;
	}
	public void setaT4(String aT4) {
		this.aT4 = aT4;
	}
	public String getrSt() {
		return rSt;
	}
	public void setrSt(String rSt) {
		this.rSt = rSt;
	}
	public String getbDt() {
		return bDt;
	}
	public void setbDt(String bDt) {
		this.bDt = bDt;
	}
	public String getcDt() {
		return cDt;
	}
	public void setcDt(String cDt) {
		this.cDt = cDt;
	}
	
}
