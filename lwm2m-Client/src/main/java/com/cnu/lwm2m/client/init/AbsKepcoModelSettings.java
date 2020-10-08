package com.cnu.lwm2m.client.init;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Date;

import com.cnu.lwm2m.client.models.impl.kepco.AMICommonControlInfo;
import com.cnu.lwm2m.client.models.impl.kepco.AMINetworkInfo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbsKepcoModelSettings implements AMICommonControlInfo, AMINetworkInfo {

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
	/**<pre> ID: 0 인증서 ID (5+3 = 8 Byte)
	 * 고유번호 (5 Byte): 제조사에서 각각의 기기별로 고유한 번호를 부여하여 납품
	 * 제조사 번호 (3 Byte): The FLAG Association Ltd.에서 부여받은 코드 </pre>*/
	@Override public byte[] getSystemTitle() {
		return new byte[] {0x41, 0x42, 0x43, 97};
	}

	/**<pre> ID: 1 AMI 사업명 (계약 후 통보 / 예: LTE 5차) </pre>*/
	private String amiBussinessName = "AMI CNU 사업 1차";

	/**<pre> ID: 2 설비 구분은 Ob_ID 3-17에서 정의 (LTE 모뎀이 사용되는 용도 표시)
	 * 0: ETC, 1: S-Type, 2: E-Type, 3: G-Type
	 * 4: AE-Type, 5: AMIGO, 6: K-DCU </pre>*/
	@Override public int getAmiModemType() {
		return 0;
	}

	/**<pre> ID: 3 설비 구분은 Ob_ID 3-17에서 정의
	 * 0: ETC, 1: 주상(전주), 2: 지상(지중) </pre>*/
	@Override public int getAmiDcuType() {
		return 0;
	}

	/**<pre> ID: 4 AMI 설비 제조 "년,월" 표시
	 * 예) 2020년 2월의 경우 "20, 12" (공백 제거) </pre>*/
	@Override public String getManufacturingDate() {
		return "20, 12";
	}

	/**<pre> ID: 7 모뎀 공통제어부 CPU 제조사 코드표 참조 (제조업체 요청 시 추가지정) </pre>*/
	@Override public String getCpuManufacturer() {
		return "08";
	}

	/**<pre> ID: 8 칩 제조사의 CPU(MCU) 모델 이름 (최대 20글자) </pre>*/
	@Override public String getCpuModelNumber() {
		return "CNU Micro chipset";
	}

	/**<pre> ID: 10 Observation 및 Notify 설정 기간마다 CPU 사용률의 값을 관찰 기록하고 전송한다.
	 * 실시간 측정 명령 수신 시 1초 단위로 기록 전송한다. </pre>*/
	@Override public int getCpuUsageRate() {
		return 0;
	}

	/**<pre> ID: 11 CPU 사용률에 대한 최소값, 최대값, 평균값을 관찰 기록한다.
	 * 예) 최소값, 최대값, 평균값 = “5, 40, 15” (공백 제거) 
	 * Res_ID_10 Observation 주기: Pmin=1(초), Pmax=3,600(초) / 초기설정값: 3,600(초)
	 * Res_ID_10 Notify 설정: gt= (%) / 초기설정값: 60(%)
	 * 예) 주기, 알람 = “3600, 60” </pre>*/
	private String cpuUsageRateObserveNotify = "3600,60";

	/** [실행] ID: 12 서버에서 측정 명령 수신 시 1(초) 단위로 현재값을 측정하고 30초간 서버로 전송한다. */

	/**<pre> ID: 13 Observation 및 Notify 설정 기간마다 RAM 사용률의 값을 관찰 기록하고 전송한다.
	 * 실시간 측정 명령 수신 시 1초 단위로 기록 전송한다. </pre>*/
	@Override public int getRamUsageRate() {
		getHeapMemory();
		return 0;
	}

	/**<pre> ID: 14 RAM 사용률에 대한 최소값, 최대값, 평균값을 관찰 기록한다.
	 * 예) 최소값, 최대값, 평균값 = “5, 40, 15” (공백 제거)
	 * Res_ID_13 Observation 주기: Pmin=1(초), Pmax=3,600(초) / 초기설정값: 3,600(초)
	 * Res_ID_13 Notify 설정: gt= (%) / 초기설정값: 60(%)
	 * 예) 주기, 알람 = “3600, 60” (공백 제거) </pre>*/
	private String ramUsageRateObserveNotify = "3600,60";

	/** [실행] ID: 15 서버에서 측정 명령 수신 시 1(초) 단위로 현재값을 측정하고 30초간 서버로 전송한다. */

	/**<pre> ID: 17 Observation 및 Notify 설정 기간마다 입력전압의 값을 관찰 기록하고 전송한다.
	 * 실시간 측정 명령 수신 시 1초 단위로 기록 전송한다. </pre>*/
	@Override public int getPowerVoltage() {
		return 0;
	}

	/**<pre> ID: 18 입력전압에 대한 최소값, 최대값, 평균값을 관찰 기록한다.
	 * 예) 최소값, 최대값, 평균값 = “11000, 12500, 12000” (공백 제거)
	 * Res_ID_17 Observation 주기: Pmin=1(초), Pmax=3,600(초) / 초기설정값: 3,600(초)
	 * Res_ID_17 Notify 설정: lt= (mV) / 초기설정값: 10000(mV)
	 * 예) 주기, 알람 = “3600, 10000” (공백 제거) </pre>*/
	private String powerVoltageObserveNotify = "3600, 10000";

	/** [실행] ID: 19 서버에서 측정 명령 수신 시 1(초) 단위로 현재값을 측정하고 30초간 서버로 전송한다. */

	/**<pre> ID: 20 Observation 및 Notify 설정 기간마다 소모전류의 값을 관찰 기록하고 전송한다.
	 * 실시간 측정 명령 수신 시 1초 단위로 기록 전송한다. </pre>*/
	@Override public int getCurrentConsumption() {
		return 0;
	}

	/**<pre> ID: 21 입력전압에 대한 최소값, 최대값, 평균값을 관찰 기록한다.
	 * 예) 최소값, 최대값, 평균값 = “100, 200, 150” (공백 제거)
	 * Res_ID_20 Observation 주기: Pmin=1(초), Pmax=3,600(초) / 초기설정값: 3,600(초)
	 * Res_ID_20 Notify 설정: gt= (%) / 초기설정값: 190(mA)
	 * 예) 주기, 알람 = “3600, 190” (공백 제거) </pre>*/
	private String CurrentConsumptionObserveNotify = "3600,190";

	/** [실행] ID: 22 */
	/** [실행] ID: 25 */
	/** [실행] ID: 26 */

	/**<pre> ID: 30 정전 발생 시 이벤트를 발생하고, 정전 발생 시각을 표시하여 Notify 한다. </pre>*/
	private Date blackout;

	/**<pre> ID: 31 정전 후 복전 시 이벤트를 발생하고, 복전 시각을 표시하여 Notify 한다. </pre>*/
	private Date recovery;

	/**<pre> [실행] ID: 35 H/W Watchdog 동작 실행 명령.
	 * 이 명령이 수행되면 Watch Dog Kick을 종료하여 Watch Dog Reset이 발생하여야 한다. </pre>*/

	/**<pre> ID: 36 스케줄에 의한 자체 리셋 주기 설정 (“0”은 자체 리셋 기능 미사용)
	 * (예: 30 → 30일 단위로 리소스 ID 37에서 정한 시간(분)에 리셋) / 초기값 (7) </pre>*/
	private int selfResetPeriod = 7;

	/**<pre> ID: 37 스케줄에 의한 자체 리셋 기능 사용 시 시간 설정(분) (리소스 ID 37이 0 이 아닌 경우)
	 * 0 : 00시 00분 ~ 1439 : 23시 59분 / 초기값 (1430 : 23시 50분) </pre>*/
	private int selfResetTime = 1430;

	/**<pre> ID: 40 RS485 Loopback 실행 시간을 설정한다.
	 * 0 인 경우 Loopback Stop 명령 시간 동안 유지한다. / 초기값 : 5초 </pre>*/
	private int rs485DLBPeriod = 5;

	/** [실행] ID: 41 RS-485 Port Loop_back 실행 명령 */
	/** [실행] ID: 42 RS-485 Port Loop_back 실행 중지 */

	/**<pre> ID: 43 Res_ID_41 실행될 경우 Loop_back 시험을 위한 data를 서버로부터 수신
	 * 예) “AMI 485 DLB TEST” </pre>*/
	private String rs485DLBInputData = "CNU DLB Test input";

	/**<pre> ID: 44 Res_ID_43의 실행 결과로 리소스 ID 44에 대한 Loop_back 결과 서버로 전송
	 * 예) “AMI 485 DLB TEST” </pre>*/
	private String rs485DLBOutputData = "CNU DLB Test output";

	/** [실행] ID: 47 10초간 모뎀의 모든 LED LAMP를 1초 간격으로 점멸한 후 정상 동작 */



	/*********************************
	  [26243] AMI Network Object Info
	 *********************************/
	/**<pre> ID: 0 간선망 DCU~서버간 통신방식 분류 코드
	 * 0: No Wan 1: HFC 2: Optical 3: LTE 4: Ethernet
	 * No Wan: 간선망 용도가 아닌 LTE 모뎀과 같은 인입망 회선임대의 경우 적용 </pre>*/
	private int wanCode;

	/**<pre> ID: 1 Cellular 0~20, Wireless 21~40, Wireline 41~60, 100~120 LTE 간선망 추후 지정 </pre>*/
	private int commTypeCode;

	/**<pre> ID: 2 None 1: SKT 2: KT 3: LGU+ </pre>*/
	private int teleCompany;

	/**<pre> ID: 3 LTE 모뎀 전화번호 ex> 01055559999 </pre>*/
	private String phoneNumber = "01055559999";

	/**<pre> ID: 4 모뎀의 사설 IP 주소 (IPv6)
	 * Ob_ID 4/4의 공인 IP 또는 26243/4의 사설 IP 둘 중 하나는 필수 항목임 </pre>*/
	@Override public String getIpAddress() {
		return "";
	}

	/**<pre> ID: 5 Res_ID: 4에서 지정한 각 인터페이스의 다음 홉 라우터의 IP 주소 (사설 IP / DCU 적용) </pre>*/
	@Override public String getBufferIpAddress() {
		return "";
	}

	/**<pre> ID: 8 통신 모듈 제조회사
	 * 붙임 8-3) 통신 모듈 제조사 코드표 참조 (제조업체 요청 시 추가지정) </pre>*/
	private String commModuleCompany = "";

	/**<pre> ID: 9 통신 모듈 칩 제조회사</pre>
	 * 붙임 8-4) 통신 모듈 칩 제조사 코드표 참조 (제조업체 요청 시 추가지정) */
	private String commModuleChipCompany = "";

	/**<pre> ID: 10 송신출력 (TX Power) 이 리소스는 RF 송신출력 값(설정값)을 관찰한다. </pre>*/
	@Override public int getTxPower() {
		return 0;
	}

	/** [실행] ID: 15 하향 CoAP Ping Test는 서버에서 모뎀으로 Ping Test를 별도 수행하고 결과를 표시한다.
	 * 상향 CoAP Ping Test는 서버에서 모뎀으로 상향 CoAP Ping Test 실행 명령을 전송하고,
	 * 모뎀은 명령 수신 시 서버로 CoAP Ping Test 명령을 4초간 실행 후 결과를 전송한다. */

	/**<pre> ID: 17 모뎀은 상향 테스트 결과를 서버로 평균 응답시간(ms)과 성공률(%) 측정결과를 전송한다.
	 * 예) 응답시간 1,230ms, 99% 성공 → ”1230ms, 99%“ (성공률은 소수점 3자리에서 절사) </pre>*/
	@Override public String getCoapPingResult() {
		return "1230ms, 99%";
	}

	/** [실행] ID: 20 Pay Load 상향 속도테스트 (Throughput Test Start) */

	/**<pre> ID: 21 Pay Load 상향 속도테스트 결과 (Throughput Test Result) 
	 * 전송된 DATA 총량을 전송된 시간(초)으로 나눈 초당 평균 전송량을 측정하여 전송한다.
	 * 예) 초당 평균 전송된 DATA (send: 528 kbytes / 52.8 kbytes/sec) </pre>*/
	@Override public String getThroughputTestResult() {
		return "52.8 kbytes/sec";
	}

	/**<pre> ID: </pre>*/
}
