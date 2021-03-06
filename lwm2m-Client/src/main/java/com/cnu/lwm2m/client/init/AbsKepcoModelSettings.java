package com.cnu.lwm2m.client.init;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.text.DecimalFormat;
import java.util.Date;

import com.cnu.lwm2m.client.models.impl.kepco.AMICommonControlInfo;
import com.cnu.lwm2m.client.models.impl.kepco.AMINetworkInfo;
import com.cnu.lwm2m.client.models.impl.kepco.AMISecurityInfo;
import com.cnu.lwm2m.client.models.impl.kepco.AMIServerInfo;
import com.cnu.lwm2m.client.models.impl.kepco.AMISoftwareInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter @Setter
public class AbsKepcoModelSettings implements AMICommonControlInfo, AMINetworkInfo
									, AMISecurityInfo, AMIServerInfo, AMISoftwareInfo {

	public int getJVMUsedMemory() {
		DecimalFormat f1 = new DecimalFormat("#,###KB");
		DecimalFormat f2 = new DecimalFormat("##.#");
		long free = Runtime.getRuntime().freeMemory() / 1024;
		long total = Runtime.getRuntime().totalMemory() / 1024;
		long max = Runtime.getRuntime().maxMemory() / 1024;
		long used = total - free;
		double ratio = (used * 100 / (double)total);
		log.debug("Java Memory 총합 = {}", f1.format(total));
		log.debug("Java Memory 사용 = {}", f1.format(used));
		log.debug("Java Memory 사용률 = {}%", f2.format(ratio));
		log.debug("Java Memory 사용가능 = {}", f1.format(max));
		getHeapMemory();

		return (int)ratio;
	}

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
		return getJVMUsedMemory();
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
	private int wanCode = 0;

	/**<pre> ID: 1 Cellular 0~20, Wireless 21~40, Wireline 41~60, 100~120 LTE 간선망 추후 지정 </pre>*/
	private int commTypeCode = 2;

	/**<pre> ID: 2 0: None, 1: SKT, 2: KT, 3: LGU+ </pre>*/
	private int teleCompany = 0;

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

	/**********************************
	  [26245] AMI SECURITY Object Info
	 **********************************/
	/**<pre> ID: 0 국정원 검증 KCMVP 보안 모듈 제조회사
	 * 00 None 01 한국전력 02 조폐공사 03 키페어 05 드림시큐리티 06 펜타시큐리티 </pre>*/
	private int encryptModuleCompany = 5;

	/**<pre> ID: 1 암호화 모듈 제조업체 모델명 (최대 20글자) </pre>*/
	private String encryptModuleModelNumber = "Dream Security";

	/**<pre> ID: 2 H/W 모듈 일련번호 또는 S/W 모듈 일련번호 </pre>*/
	private String encryptModuleSerialNumber = "ABCDEFGH";

	/**<pre> ID: 3 암호화 모듈 타입
	 * 00 None 01 USIM 06 F/W 버전 02 Chip 07 O/S 버전 03 Module 08 커널 버전 </pre>*/
	private int encryptModuleType = 0;

	/**<pre> ID: 5 암호화 모듈 S/W 버전
	 * 소수점 두 자리로 표현 (Major Number. Minor Number, Revision Number) 예) “1.0.1” </pre>*/
	private String encryptModuleVersion = "1.0.1";

	/**<pre> ID: 10 보안 모듈 적합성 검사 구분
	 * 0 적합성 검사 없음 또는 취소상태
	 * 1 인증서 적합성 체크
	 * 2 암호화 ARIA-GCM-128 알고리즘 체크 테스트 벡터 필요
	 * 3 복호화 ARIA-GCM-128 알고리즘 체크 테스트 벡터 필요
	 * 4 전자서명 ECDSA with P256 알고리즘 체크 테스트 벡터 필요
	 * 5 키 설정 ECDH with P256 알고리즘 체크 테스트 벡터 필요
	 * 6 해시함수 SHA-256 알고리즘 체크 테스트 벡터 필요
	 * 7 F/W 무결성 ECDSA-SHA256 알고리즘 체크 테스트 벡터 필요 </pre>*/
	private int securityModuleCompatible = 0;

	/**<pre> ID: 11 보안 모듈 테스트 벡터  </pre>*/
	private byte[] securityModuleTestVector = new byte[] {0x41, 0x42};

	/** [실행] ID: 12 보안 모듈 적합성 체크 시작  (Res_ID_10, 11에 대한 실행 명령) */
	/** [실행] ID: 13 보안 모듈 적합성 체크 중지 및 취소 */

	/**<pre> ID: 14 보안 모듈 적합성 체크 결과 응답 </pre>*/
	private byte[] securityModuleCompatibleResult = new byte[] {0x41, 0x42};

	/**<pre> ID: 20 F/W 무결성 검사 주기: 0~365
	 * “0”인 경우 주기적으로 무결성 검사 미수행 (초기설정값: 0) </pre>*/
	private int FWImageIntegrityCheckPeriod = 0;

	/**<pre> ID: 21 무결성 검사항목 구분 </pre>*/
	private int integrityCheckStatus = 0;

	/**<pre> ID: 22 Health Request 시 사용할 32bit 검사토큰(난수)을 서버에서 생성하여 모뎀으로 전송한다. </pre>*/
	private byte[] integrityCheckToken = new byte[] {0x41, 0x42};

	/** [실행] ID: 25 무결성 검사 요청 (Health Request) */
	/** [실행] ID: 26 무결성 검사 중지 및 취소 (Health Request Cancel) */

	/**<pre> ID: 27 요청 항목에 대한 검사 결과를 저장하고, 결과를 서버로 전송하여야 하며
	 * Res_ID_21 상태 값을 ‘0’으로 변경하여야 한다.
	 * Health_Rep (N, State, uptime, DS)
	 * N = F/W Image의 상태정보를 요청하는 난수값(검사토큰)
	 * State = H | BS
	 *  H : SHA-256(F/W Image), 부트 시점에 저장한 값
	 *  BS : Boot Seed, 부트 시점마다 변경되는 값
	 * uptime = 동작정보, 부트 이후 동작 시간(단위:초)
	 * DS = SignF/W Image 검사용 키(N | State | uptime)</pre>*/
	private byte[] FWImageIntegrityCheckResult = new byte[] {0x41, 0x42};

	/**<pre> ID: 28 F/W 무결성 검사에 사용할 장치 인증서의 공개키를 저장한다.
	 * 모뎀에서 보내온 DS를 복호화하여 메시지 검증 및 HASH 값 추출용 </pre>*/
	private byte[] FWImageIntegrityCheckKey = new byte[] {0x41, 0x42};

	/**<pre> ID: 30 현재 버전 Secure 부팅 결과 → 성공: true / 실패: false
	 * 현재 버전 부팅 성공 시: “true, current”
	 * 현재 버전 부팅 실패 시 LAST 또는 FACTORY 버전으로 부팅 후 “false” 결과 전송
	 *  - “false, factory” 또는 “false, last”
	 * 부팅 시 부팅 결과를 Notify 하여야 한다. </pre>*/
	private boolean secureBootSuccess = true;

	/**<pre> ID: 31 가장 최근에 성공한 Secure boot 성공시간 표시 : “년, 월, 일, 시, 분”
	 * Res ID 30과 함께 Notify 한다. </pre>*/
	private Date secureBootSuccessDate = new Date();


	/********************************
	  [26247] AMI SERVER Object Info
	 ********************************/
	/**<pre> ID: 0 LwM2M Bootstrap Server IP 주소 : 미사용</pre>*/
	private String bootstrapIp = "127.0.0.1";

	/**<pre> ID: 1 LwM2M Bootstrap Server Port Number : 미사용</pre>*/
	private int bootstrapPort = 5523;

	/**<pre> ID: 10 보안 Server IP 주소</pre>*/
	private String securityIp = "127.0.0.1";

	/**<pre> ID: 11 보안 Server Port Number</pre>*/
	private int securityPort = 5524;

	/**<pre> ID: 15 보안 Proxy Server IP 주소 (G/W 또는 DCU 에서 하위 장치로 자동 적용되어야 한다.)</pre>*/
	private String securityProxyIp = "127.0.0.1";

	/**<pre> ID: 16 보안 Proxy Server Port Number (G/W 또는 DCU 적용)</pre>*/
	private int securityProxyPort = 4423;

	/**<pre> ID: 20 LwM2M Server IP 주소</pre>*/
	private String serverIp = "127.0.0.1";

	/**<pre> ID: 21 LwM2M Server Port Number</pre>*/
	private int serverPort = 4424;

	/**<pre> ID: 25 LwM2M Proxy Server IP 주소 (G/W 또는 DCU 에서 하위 장치로 자동 적용되어야 한다.)</pre>*/
	private String proxyServerIp = "127.0.0.1";

	/**<pre> ID: 26 LwM2M Proxy Server Port Number (G/W 또는 DCU 적용)</pre>*/
	private int proxyServerPort = 144;

	/**<pre> ID: 30 DLMS Master IP Address</pre>*/
	private String dlmsMasterIp = "127.0.0.1";

	/**<pre> ID: 31 DLMS Master Port Number</pre>*/
	private int dlmsMasterPort = 244;

	/**<pre> ID: 35 DLMS Manager IP Address (G/W 또는 DCU 에서 하위 장치로 자동 적용되어야 한다.)</pre>*/
	private String dlmsManagerIp = "127.0.0.1";

	/**<pre> ID: 36 DLMS Manager Port Number (G/W 또는 DCU 적용)</pre>*/
	private int dlmsManagerPort = 245;



	/**********************************
	  [26249] AMI SOFTWARE Object Info
	 **********************************/
	/**<pre> ID: 0 Download 및 Update 상태 값
	 * 0: 초기상태 (업데이트 프로세스가 시작되면 다운로드 단계로 전환)
	 * 1: 다운로드 중
	 * 2: 다운로드 실패 (전송실패)
	 * 3: 다운로드 실패 (파일명세 불일치 : 일과 또는 개별 다운로드 시)
	 * 4: 다운로드 실패 (무결성 검사 실패)
	 * 5: 다운로드 성공
	 * 6: 업데이트 대기 (일정 업데이트 또는 업데이트 명령 대기 시)
	 * 7: 업데이트 취소
	 * 8: 업데이트 실패
	 * 9: 업데이트 성공 (업데이트 성공 시 값 전송 성공 후 초기 상태로 전환)  </pre>*/
	private int downloadUpdateStatus = 0;

	/** [쓰기] ID: 1 서버가 단말장치로 개별 다운로드를 수행하여 파일을 전송하면 장치는 이를 저장한다.
	 * 장치는 개별 다운로드 실패한 경우 확인 다운로드 절차를 진행한다. */
	/** [쓰기] ID: 2 서버가 모뎀으로 일괄 다운로드를 수행하여 파일을 전송하면 장치는 이를 저장하여야 한다.
	 * 서버는 일괄 다운로드 실패한 장치에 대하여 개별 다운로드 단계를 진행하여야 한다. */

	/**<pre> ID: 3 다운로드 파일 확인주기 설정 (“0”은 확인 다운로드 기능 미사용) / 초기설정값: “1” </pre>*/
	private int checkDownloadPeriod = 0;

	/**<pre> ID: 5 파일 다운로드 시작한 일시를 기록하고 Notify 하여야 한다.  </pre>*/
	private Date downloadStartDate = new Date();

	/**<pre> ID: 6 파일이 다운로드 완료되고 무결성 검사를 통과한 일시를 기록하고 Notify 하여야 한다.
	 * 다운로드 결과(Res_0) 전송 및 다운로드 실패 시 다운로드 재실행 </pre>*/
	private Date downloadCompleteDate = new Date();

	/** [실행] ID: 10 S/W 업데이트 방법이 명령 업데이트 즉 일정이 “FF, FF, FF, FF, FF”일 경우 명령 수행 */
	/** [실행] ID: 11 다운로드 완료 후 일정/명령 업데이트 대기 시 “취소” 명령 수신 시 다운로드 파일 삭제
	 * 다운로드 및 업데이트 결과(Res_0) 전송 및 상태 초기화하고 Notify 하여야 한다. */

	/**<pre> ID: 12 S/W 업데이트 완료 일시를 기록하고 Notify 하여야 한다. </pre>*/
	private Date updateCompleteDate = new Date(0);

	/**<pre> ID: 13 S/W 업데이트 예약 일시를 기록하고 Notify 하여야 한다. 패키지 명에 포함된 일정 분리 표시 </pre>*/
	private Date updateReservationDate = new Date(0);

	/**<pre> ID: 15 펌웨어 공장 출하 버전
	 * 소수점 두 자리로 표현 (Major Number. Minor Number, Revision Number) 예) “1.0.1” </pre>*/
	private String fwFactoryVersion = "1.0.0";

	/**<pre> ID: 16 가장 최근 부팅에 성공한 현재 운용 중인 버전 바로 이전의 펌웨어 버전
	 * 소수점 두 자리로 표현 (Major Number. Minor Number, Revision Number) 예) “1.0.1” </pre>*/
	private String fwLastVersion = "1.0.0";

	/**<pre> ID: 17 현재 운용 중인 펌웨어 버전
	 * 소수점 두 자리로 표현 (Major Number. Minor Number, Revision Number) 예) “1.0.1 </pre>*/
	private String fwCurrentVersion = "1.0.1";

	/**<pre> ID: 20 모뎀 공통제어부에 탑재되어 운영되는 OS 명 </pre>*/
	private String commonControlOSName = System.getProperty("os.name");

	/**<pre> ID: 21 모뎀에서 현재 운영 중인 O/S의 KERNEL 버전 / 예) 4.19.1 </pre>*/
	private String commonControlOSVersion = System.getProperty("os.arch") + ", " + System.getProperty("os.version");

	/**<pre> ID: 25 현재 운영 중인 M/W(예:JVM): 공급사, 구분(명칭)
	 * 예) 오라클 제공 JVM: “Oracle, SE” / Open Source일 경우: “open, SE”  </pre>*/
	private String mwSystem = "Oracle, SE";

	/**<pre> ID: 26 현재 운영 중인 JVM 버전 : 각 사에서 제공하는 버전 형식을 따른다. </pre>*/
	private String mwVersion = "8.2.1";

	/**<pre> ID: 30 상호인증 및 키 관리 등을 수행하고 관리하는 보안관리 프로그램 버전 (단말장치 적용)
	 * 소수점 두 자리로 표현 (Major Number. Minor Number, Revision Number) 예) “1.0.1 </pre>*/
	private String securityAgentVersion = "1.0.0";

	/**<pre> ID: 31 상호인증 및 키 관리 등을 중계하는 Proxy Server S/W 버전 (G/W 또는 DCU 적용)
	 * 소수점 두 자리로 표현 (Major Number. Minor Number, Revision Number) 예) “1.0.1” </pre>*/
	private String securityProxyVersion = "1.0.0";

	/**<pre> ID: 35 현재 운영 중인 LwM2M Agent S/W 버전 (단말장치 적용)
	 * 소수점 두 자리로 표현 (Major Number. Minor Number, Revision Number) 예) “1.0.1” </pre>*/
	private String lwm2mAgentVersion = "1.0.0";

	/**<pre> ID: 36 현재 운영 중인 LwM2M Proxy S/W 버전 (G/W 또는 DCU 적용)
	 * 소수점 두 자리로 표현 (Major Number. Minor Number, Revision Number) 예) “1.0.1” </pre>*/
	private String lwm2mProxyVersion = "1.0.0";

	/**<pre> ID: 40 현재 운영 중인 DLMS Agent S/W 버전 (단말장치 적용)
	 * 소수점 두 자리로 표현 (Major Number. Minor Number, Revision Number) 예) “1.0.1” </pre>*/
	private String dlmsAgentVersion = "1.0.0";

	/**<pre> ID: 41 현재 운영 중인 DLMS Manager S/W 버전 (G/W 또는 DCU 적용)
	 * 소수점 두 자리로 표현 (Major Number. Minor Number, Revision Number) 예) “1.0.1” </pre>*/
	private String dlmsManagerVersion = "1.0.0";
}
