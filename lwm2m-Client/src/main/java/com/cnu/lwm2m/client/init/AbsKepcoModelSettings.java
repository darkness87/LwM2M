package com.cnu.lwm2m.client.init;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Date;

import com.cnu.lwm2m.client.models.impl.AMICommonControlInfo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbsKepcoModelSettings implements AMICommonControlInfo {

	public void getHeapMemory() {
		MemoryMXBean membean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heap = membean.getHeapMemoryUsage();
		MemoryUsage nonheap = membean.getNonHeapMemoryUsage();
		System.out.println("Heap Memory: " + heap.toString());
		System.out.println("NonHeap Memory: " + nonheap.toString());
	}

	/*********************************
	  [26241] AMI Common Control Info
	 *********************************/
	/** ID: 0 인증서 ID (5+3 = 8 Byte)
	 * 고유번호 (5 Byte): 제조사에서 각각의 기기별로 고유한 번호를 부여하여 납품
	 * 제조사 번호 (3 Byte): The FLAG Association Ltd.에서 부여받은 코드*/
	@Override public byte[] getSystemTitle() {
		return null;
	}

	/** ID: 1 AMI 사업명 (계약 후 통보 / 예: LTE 5차) */
	@Override public String getAmiBussinessName() {
		return null;
	}

	/** ID: 2 설비 구분은 Ob_ID 3-17에서 정의 (LTE 모뎀이 사용되는 용도 표시)
	 * 0: ETC, 1: S-Type, 2: E-Type, 3: G-Type
	 * 4: AE-Type, 5: AMIGO, 6: K-DCU */
	@Override public int getAmiModemType() {
		return 0;
	}

	/** ID: 3 설비 구분은 Ob_ID 3-17에서 정의
	 * 0: ETC, 1: 주상(전주), 2: 지상(지중) */
	@Override public int getAmiDcuType() {
		return 0;
	}

	/** ID: 4 AMI 설비 제조 "년,월" 표시
	 * 예) 2020년 2월의 경우 "20, 12" (공백 제거) */
	@Override public String getManufacturingDate() {
		return null;
	}

	/** ID: 7 모뎀 공통제어부 CPU 제조사 코드표 참조 (제조업체 요청 시 추가지정) */
	@Override public String getCpuManufacturer() {
		return null;
	}

	/** ID: 8 칩 제조사의 CPU(MCU) 모델 이름 (최대 20글자) */
	@Override public String getCpuModelNumber() {
		return null;
	}

	/** ID: 10 Observation 및 Notify 설정 기간마다 CPU 사용률의 값을 관찰 기록하고 전송한다.
	 * 실시간 측정 명령 수신 시 1초 단위로 기록 전송한다. */
	@Override public int getCpuUsageRate() {
		return 0;
	}

	/** ID: 11 CPU 사용률에 대한 최소값, 최대값, 평균값을 관찰 기록한다.
	 * 예) 최소값, 최대값, 평균값 = “5, 40, 15” (공백 제거) 
	 * Res_ID_10 Observation 주기: Pmin=1(초), Pmax=3,600(초) / 초기설정값: 3,600(초)
	 * Res_ID_10 Notify 설정: gt= (%) / 초기설정값: 60(%)
	 * 예) 주기, 알람 = “3600, 60” */
	private String cpuUsageRateObserveNotify;

	/** [실행] ID: 12 서버에서 측정 명령 수신 시 1(초) 단위로 현재값을 측정하고 30초간 서버로 전송한다. */

	/** ID: 13 Observation 및 Notify 설정 기간마다 RAM 사용률의 값을 관찰 기록하고 전송한다.
	 * 실시간 측정 명령 수신 시 1초 단위로 기록 전송한다. */
	@Override public int getRamUsageRate() {
		getHeapMemory();
		return 0;
	}

	/** ID: 14 RAM 사용률에 대한 최소값, 최대값, 평균값을 관찰 기록한다.
	 * 예) 최소값, 최대값, 평균값 = “5, 40, 15” (공백 제거)
	 * Res_ID_13 Observation 주기: Pmin=1(초), Pmax=3,600(초) / 초기설정값: 3,600(초)
	 * Res_ID_13 Notify 설정: gt= (%) / 초기설정값: 60(%)
	 * 예) 주기, 알람 = “3600, 60” (공백 제거) */
	private String ramUsageRateObserveNotify;

	/** [실행] ID: 15 서버에서 측정 명령 수신 시 1(초) 단위로 현재값을 측정하고 30초간 서버로 전송한다. */

	/** ID: 17 Observation 및 Notify 설정 기간마다 입력전압의 값을 관찰 기록하고 전송한다.
	 * 실시간 측정 명령 수신 시 1초 단위로 기록 전송한다. */
	@Override public int getPowerVoltage() {
		return 0;
	}

	/** ID: 18 입력전압에 대한 최소값, 최대값, 평균값을 관찰 기록한다.
	 * 예) 최소값, 최대값, 평균값 = “11000, 12500, 12000” (공백 제거)
	 * Res_ID_17 Observation 주기: Pmin=1(초), Pmax=3,600(초) / 초기설정값: 3,600(초)
	 * Res_ID_17 Notify 설정: lt= (mV) / 초기설정값: 10000(mV)
	 * 예) 주기, 알람 = “3600, 10000” (공백 제거) */
	private String powerVoltageObserveNotify;

	/** [실행] ID: 19 서버에서 측정 명령 수신 시 1(초) 단위로 현재값을 측정하고 30초간 서버로 전송한다. */

	/** ID: 20 Observation 및 Notify 설정 기간마다 소모전류의 값을 관찰 기록하고 전송한다.
	 * 실시간 측정 명령 수신 시 1초 단위로 기록 전송한다. */
	@Override public int getCurrentConsumption() {
		return 0;
	}

	/** ID: 21 입력전압에 대한 최소값, 최대값, 평균값을 관찰 기록한다.
	 * 예) 최소값, 최대값, 평균값 = “100, 200, 150” (공백 제거)
	 * Res_ID_20 Observation 주기: Pmin=1(초), Pmax=3,600(초) / 초기설정값: 3,600(초)
	 * Res_ID_20 Notify 설정: gt= (%) / 초기설정값: 190(mA)
	 * 예) 주기, 알람 = “3600, 190” (공백 제거) */
	private String CurrentConsumptionObserveNotify;

	/** [실행] ID: 22 */
	/** [실행] ID: 25 */
	/** [실행] ID: 26 */

	/** ID: 30 정전 발생 시 이벤트를 발생하고, 정전 발생 시각을 표시하여 Notify 한다. */
	private Date blackout;

	/** ID: 31 정전 후 복전 시 이벤트를 발생하고, 복전 시각을 표시하여 Notify 한다. */
	private Date recovery;

	/** [실행] ID: 35 H/W Watchdog 동작 실행 명령.
	 * 이 명령이 수행되면 Watch Dog Kick을 종료하여 Watch Dog Reset이 발생하여야 한다.*/

	/** ID: 36 스케줄에 의한 자체 리셋 주기 설정 (“0”은 자체 리셋 기능 미사용)
	 * (예: 30 → 30일 단위로 리소스 ID 37에서 정한 시간(분)에 리셋) / 초기값 (7) */
	private int selfResetPeriod = 7;

	/** ID: 37 스케줄에 의한 자체 리셋 기능 사용 시 시간 설정(분) (리소스 ID 37이 0 이 아닌 경우)
	 * 0 : 00시 00분 ~ 1439 : 23시 59분 / 초기값 (1430 : 23시 50분) */
	private int selfResetTime = 1430;

	/** ID: 40 RS485 Loopback 실행 시간을 설정한다.
	 * 0 인 경우 Loopback Stop 명령 시간 동안 유지한다. / 초기값 : 5초 */
	private int rs485DLBPeriod = 5;

	/** [실행] ID: 41 RS-485 Port Loop_back 실행 명령 */
	/** [실행] ID: 42 RS-485 Port Loop_back 실행 중지 */

	/** ID: 43 Res_ID_41 실행될 경우 Loop_back 시험을 위한 data를 서버로부터 수신
	 * 예) “AMI 485 DLB TEST” */
	private String rs485DLBInputData;

	/** ID: 44 Res_ID_43의 실행 결과로 리소스 ID 44에 대한 Loop_back 결과 서버로 전송
	 * 예) “AMI 485 DLB TEST” */
	private String rs485DLBOutputData;

	/** [실행] ID: 47 10초간 모뎀의 모든 LED LAMP를 1초 간격으로 점멸한 후 정상 동작 */
}
