package com.red.vo;

public class LpBlackoutVo {

	private String aId; // Agent ID Agent의 고유 ID
	private String mId; // Meter ID 계기 ID
	private String oCd; // Obis Code OBIS Code
	private String cDt; // Captured Date 검침 일시
	private String rSt; // Requested Work Result 결과 (0:대기, 1:전달완료, 2:성공, 101:무응답, 102:미지원)

	private String rVl; // Object / Array Requested Value Info 검침 결과에 대한 상세내용

	private String No; // 검침결과 응답 값 순번
	private String bDt; // Base Date 수집기준일시
	private String bCt; // Blackout Count 정복전 횟수
	
}
