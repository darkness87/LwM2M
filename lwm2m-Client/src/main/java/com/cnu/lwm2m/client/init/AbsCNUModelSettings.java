package com.cnu.lwm2m.client.init;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Date;
import java.util.TimeZone;

import org.eclipse.leshan.core.request.BindingMode;

import com.cnu.lwm2m.client.models.impl.DeviceInfo;
import com.cnu.lwm2m.client.models.impl.SecurityInfo;
import com.cnu.lwm2m.client.models.impl.ServerInfo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbsCNUModelSettings implements SecurityInfo, ServerInfo, DeviceInfo {
	/*******************
	  [0] SECURITY Info
	 *******************/



	/*****************
	  [1] SERVER Info
	 *****************/
	/** ID:0 서버 객체 인스턴스를 연결하는 링크로 사용한다.*/
	@Override public int getServerID() {
		return 12345;
	}

	/** ID:1 등록 유효기간을 초 단위로 지정한다. 30일 = 60s * 60m * 24h * 30d = 2,592,000 */
//	private long lifeTime = 60 * 60 * 24 * 30;
	private long lifeTime = 277;

	/** ID:2 Observation의 최소 시간 간격 정의, 0: (즉시, 계속) / 초기설정값: 3,600 (초) */
	private Long defaultMinPeriod = 3600L;

	/** ID:3 Observation의 최대 시간 간격 정의 (정의하지 않으면 최소 시간 간격 적용), 초기값 : 0 */
	private Long defaultMaxPeriod = 0L;

	/** [실행] ID:4 disable execute실행 */
	/** ID:5 서버를 비활성화하는 기간이며 이 기간이 지나면 모뎀은 서버에 등록 프로세스를 수행한다. 초기값 : 7,200 (2시간) */
	private int disableTimeout = 7200;

	/** ID:6 서버 계정 비활성화 또는 오프라인 상태에서 "Notify" 오퍼레이션을 1일 동안 저장 관리하고
	 *  서버 계정 활성화 또는 온라인 상태가 되면 저장된 "Notify" 오퍼레이션을 서버에 보고한다.*/
	private boolean notifyWhenDisable = true;

	/** ID:7 UDP 사용 (고정) */
	private BindingMode binding = BindingMode.U;

	/** [실행] ID:9 Registration Update Trigger 실행 */
	/** [실행] ID:10 Bootstrap-Request Trigger 실행 */



	/*****************
	  [3] DEVICE Info
	 *****************/
	/** ID:0, 제조사명 : 씨앤유 글로벌, 제조사 코드 : 07 */
	private String manufacturer = "씨앤유 글로벌";

	/** ID:1 제조업체별 제품모델 이름 (최대 20글자) */
	private String modelNumber = "LwM2M";

	/** ID:2 LTE 모뎀 제조 일련번호 */
	private String serialNumber = "serialNumber_1";

	/** [실행] ID:4 오류 발생 시 장치를 복원하기 위한 장치 Rebooting 명령 실행 */
	/** [실행] ID:5 LwM2M 설정(Observe, Notify)값을 공장 출하 상태의 설정값으로 변경
		장치는 설정값을 초기화한 후 재등록을 하기 위해서 상호인증 과정을 재실행하여야 한다.
		단 상호인증을 위한 설정이 선행되어야 한다. */

	/** ID:6 파워 리소스 0 – DC power, 동일한 Resource Instance ID는 동일한 파워 리소스를 사용하여야 한다. */
	private int availablePowerSources = 0;

	/** ID:7 장치가 동작하는 정격전압 */
	private int powerSourceVoltage = 12000;

	/** ID:8 제조사가 계기 30대 연결 환경에서 24시간 측정한 최소값, 최대값, 평균값 기록한다. 예) 최소갑, 최대값, 평균값 : “50, 200, 150” */
	@Override public int getPowerSourceCurrent() {	// OMA 규격 불량
		return 50;
	}

	/** ID:10 Res ID 21의 저장 메모리에 대한 가용 공간 또는 File 시스템의 남은 가용 영역 크기 Notify 설정: lt= (KB) / 초기설정값: 2,000 */
	@Override public int getMemoryFree() {
		return (int)(Runtime.getRuntime().freeMemory() / 1024);
	}

	/** ID:11 관찰 에러 코드에 대해 다음 파라메터로 관찰을 실행하며 값 변경 시 Notify 한다.
	 * 0=No error
	 * 1=Low battery power (미사용)
	 * 2=External power supply off (ID 26241_30: 정전시간 연동)
	 * 4=Low received signal strength (ID 4_2: RF 수신 레벨 연동)
	 * 5=Out of memory (ID 3_10: 가용 저장공간 연동)
	 * 7=IP connectivity failure (보안서버로 보안 로그 전송실패 시)
	 * 8=Peripheral malfunction (ID 26241_40~47 RS-485 DLB 테스트로 대체)
	 * (Pmin = 60(초), Pmax = 86,400(초), gt=0) / 초기설정값: 60(초)
	 */
	@Override public int getErrorCode() {
		return 0;
	}

	/** [실행] ID: 12 Error Code를 Reset 하여 오류가 없음을 의미하는 “0” 값으로 설정 */

	/** ID: 13 모뎀의 시간 설정 및 시간 확인 (UNIX 시간 1970년1월1일 0(+9)시 기준 0) */
	@Override public Date getCurrentTime() {
		return new Date();
//		return (int)(new Date().getTime() / 1000);
	}
	

	/** ID: 15 LwM2M 장치가 위치한 표준 시간대를 나타낸다. (설정값 : Asia/Seoul) */
	@Override public String getTimezone() {
		return TimeZone.getDefault().getID();
	}

	/** ID: 17 설비의 종류를 구분한다. 0: ETC, 1: SERVER, 2: G/W, 3: DCU, 4: K-DCU, 5: MODEM */
	private String deviceType = "3";

	/** ID: 18 모뎀의 현재 H/W 버전 소수점 두 자리로 표현 (Major Number. Minor Number, Revision Number) 예) “1.0.1” */
	@Override public String getHardwareVersion() {
		return "1.0.0";
	}

	/** ID: 21 데이터 및 소프트웨어를 저장할 수 있는 메모리(ROM) 총 저장공간 */
	@Override public int getMemoryTotal() {
		return (int)(Runtime.getRuntime().totalMemory() / 1024);
	}

	public void getHeapMemory() {
		MemoryMXBean membean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heap = membean.getHeapMemoryUsage();
		MemoryUsage nonheap = membean.getNonHeapMemoryUsage();
		System.out.println("Heap Memory: " + heap.toString());
		System.out.println("NonHeap Memory: " + nonheap.toString());
	}
}
