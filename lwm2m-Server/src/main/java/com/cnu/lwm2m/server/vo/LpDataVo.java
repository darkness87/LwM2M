package com.cnu.lwm2m.server.vo;

/**
 * @author skchae@cnuglobal.com
 * @version 0.1
 * @since 2020.08.10
 */
public class LpDataVo {

	private String aId; // Agent ID Agent의 고유 ID
	private String mId; // Meter ID 계기 ID
	private String cDv; // Capture Division 검침 구분 (0: 정규, 1: 재검침, 2: 자율검침)
	private String pDv; // Capture Division 전력량 구분 (0: 정기, 1: 비정기(수시), 2: 이사정산)
	private String pDr; // Power Direction 전류 방향 (0: 순방향, 1: 역방향)
	private String pA0; // Valid Value Tariff 0 : 유효전력량 #0
	private String aA0; // Apparent Value Tariff 0 : 피상전력량 #0
	private String gA0; // Ground Value Tariff 0 : 지상무효전력량 #0 (Tariff 0)
	private String ra0; // Act Invalid Value Tariff 0 : 진상무효전력량 #0 (Tariff 0)
	private String aF0; // Average Factor Tariff 0 : 평균역률 #0 (Tariff 0)
	private String pA1; // Valid Value Tariff 1 : 유효전력량 #1 (Tariff 1)
	private String aA1; // Apparent Value Tariff 1 : 피상전력량 #1 (Tariff 1)
	private String gA1; // Ground Value Tariff 1 : 지상무효전력량 #1 (Tariff 1)
	private String ra1; // Act Invalid Value Tariff 1 : 진상무효전력량 #1 (Tariff 1)
	private String aF1; // Average Factor Tariff 1 : 평균역률 #1 (Tariff 1)
	private String pA2; // Valid Value Tariff 2 : 유효전력량 #2 (Tariff 2)
	private String aA2; // Apparent Value Tariff 2 : 피상전력량 #2
	private String gA2; // Ground Value Tariff 2 : 지상무효전력량 #2
	private String ra2; // Act Invalid Value Tariff 2 : 진상무효전력량 #2
	private String aF2; // Average Factor Tariff 2 : 평균역률 #2
	private String pA3; // Valid Value Tariff 3 : 유효전력량 #3
	private String aA3; // Apparent Value Tariff 3 : 피상전력량 #3
	private String gA3; // Ground Value Tariff 3 : 지상무효전력량 #3
	private String ra3; // Act Invalid Value Tariff 3 : 진상무효전력량 #3
	private String aF3; // Average Factor Tariff 3 : 평균역률 #3
	private String pA4; // Valid Value Tariff 4 : 유효전력량 #4
	private String aA4; // Apparent Value Tariff 4 : 피상전력량 #4
	private String gA4; // Ground Value Tariff 4 : 지상무효전력량 #4
	private String ra4; // Act Invalid Value Tariff 4 : 진상무효전력량 #4
	private String aF4; // Average Factor Tariff 4 : 평균역률 #4
	private String rSt; // Requested Work Result 결과 (0:대기, 1:전달완료, 2:성공, 101:무응답, 102:미지원)
	private String bDt; // Base Date 수집기준일시 (Self Read 일시)
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
	public String getpDv() {
		return pDv;
	}
	public void setpDv(String pDv) {
		this.pDv = pDv;
	}
	public String getpDr() {
		return pDr;
	}
	public void setpDr(String pDr) {
		this.pDr = pDr;
	}
	public String getpA0() {
		return pA0;
	}
	public void setpA0(String pA0) {
		this.pA0 = pA0;
	}
	public String getaA0() {
		return aA0;
	}
	public void setaA0(String aA0) {
		this.aA0 = aA0;
	}
	public String getgA0() {
		return gA0;
	}
	public void setgA0(String gA0) {
		this.gA0 = gA0;
	}
	public String getRa0() {
		return ra0;
	}
	public void setRa0(String ra0) {
		this.ra0 = ra0;
	}
	public String getaF0() {
		return aF0;
	}
	public void setaF0(String aF0) {
		this.aF0 = aF0;
	}
	public String getpA1() {
		return pA1;
	}
	public void setpA1(String pA1) {
		this.pA1 = pA1;
	}
	public String getaA1() {
		return aA1;
	}
	public void setaA1(String aA1) {
		this.aA1 = aA1;
	}
	public String getgA1() {
		return gA1;
	}
	public void setgA1(String gA1) {
		this.gA1 = gA1;
	}
	public String getRa1() {
		return ra1;
	}
	public void setRa1(String ra1) {
		this.ra1 = ra1;
	}
	public String getaF1() {
		return aF1;
	}
	public void setaF1(String aF1) {
		this.aF1 = aF1;
	}
	public String getpA2() {
		return pA2;
	}
	public void setpA2(String pA2) {
		this.pA2 = pA2;
	}
	public String getaA2() {
		return aA2;
	}
	public void setaA2(String aA2) {
		this.aA2 = aA2;
	}
	public String getgA2() {
		return gA2;
	}
	public void setgA2(String gA2) {
		this.gA2 = gA2;
	}
	public String getRa2() {
		return ra2;
	}
	public void setRa2(String ra2) {
		this.ra2 = ra2;
	}
	public String getaF2() {
		return aF2;
	}
	public void setaF2(String aF2) {
		this.aF2 = aF2;
	}
	public String getpA3() {
		return pA3;
	}
	public void setpA3(String pA3) {
		this.pA3 = pA3;
	}
	public String getaA3() {
		return aA3;
	}
	public void setaA3(String aA3) {
		this.aA3 = aA3;
	}
	public String getgA3() {
		return gA3;
	}
	public void setgA3(String gA3) {
		this.gA3 = gA3;
	}
	public String getRa3() {
		return ra3;
	}
	public void setRa3(String ra3) {
		this.ra3 = ra3;
	}
	public String getaF3() {
		return aF3;
	}
	public void setaF3(String aF3) {
		this.aF3 = aF3;
	}
	public String getpA4() {
		return pA4;
	}
	public void setpA4(String pA4) {
		this.pA4 = pA4;
	}
	public String getaA4() {
		return aA4;
	}
	public void setaA4(String aA4) {
		this.aA4 = aA4;
	}
	public String getgA4() {
		return gA4;
	}
	public void setgA4(String gA4) {
		this.gA4 = gA4;
	}
	public String getRa4() {
		return ra4;
	}
	public void setRa4(String ra4) {
		this.ra4 = ra4;
	}
	public String getaF4() {
		return aF4;
	}
	public void setaF4(String aF4) {
		this.aF4 = aF4;
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
