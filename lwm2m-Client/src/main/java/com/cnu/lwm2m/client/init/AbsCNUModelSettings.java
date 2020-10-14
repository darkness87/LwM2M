package com.cnu.lwm2m.client.init;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.TimeZone;

import org.eclipse.leshan.core.request.BindingMode;

import com.cnu.lwm2m.client.models.impl.oma.AccessControlInfo;
import com.cnu.lwm2m.client.models.impl.oma.ConnectivityMonitoringInfo;
import com.cnu.lwm2m.client.models.impl.oma.ConnectivityStatisticsInfo;
import com.cnu.lwm2m.client.models.impl.oma.DeviceInfo;
import com.cnu.lwm2m.client.models.impl.oma.FirmwareUpdateInfo;
import com.cnu.lwm2m.client.models.impl.oma.LocationInfo;
import com.cnu.lwm2m.client.models.impl.oma.SecurityInfo;
import com.cnu.lwm2m.client.models.impl.oma.ServerInfo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbsCNUModelSettings extends AbsKepcoModelSettings
								implements SecurityInfo, ServerInfo, DeviceInfo
									, AccessControlInfo, ConnectivityMonitoringInfo, FirmwareUpdateInfo
									, LocationInfo, ConnectivityStatisticsInfo {
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



	/************************
	  [2] Acess Control Info
	 ************************/
	/** ID:0 Object ID 읽기  */
	@Override public int getObjectID() {
		return 0;
	}

	/** ID:1 Object 하위 리소스 (Instance) ID 읽기  */
	@Override public int getObjectInstanceID() {
		return 0;
	}

	/** ID:2 리소스 인스턴스 값에 설정된 각 비트는 해당 작업에 대한 모뎀관리 서버에 대한 액세스
	 * 권한을 부여하며 비트 순서는 다음과 같이 지정한다. 1st LSB: R(Read, Observe, Discover, Write-Attributes)
	 * 2nd LSB: W(Write)
	 * 3rd LSB: E(Execute)
	 * 4th LSB: D(Delete)
	 * 5th LSB: C(Create)
	 * Other bits are reserved for future use */
	@Override public int getAccessControl() {
		return 0;
	}

	/** ID:3 Access 권한을 갖는 모뎀관리 서버의 Short Server ID */
	@Override public int getAccessControlOwner() {
		return 0;
	}



	/*****************
	  [3] DEVICE Info
	 *****************/
	/** ID:0, 제조사명 : 씨앤유 글로벌, 제조사 코드 : 07 */
	private String manufacturer = "씨앤유 글로벌";

	/** ID:1 제조업체별 제품모델 이름 (최대 20글자) */
	private String modelNumber = "LwM2M";

	/** ID:2 LTE 모뎀 제조 일련번호 */
	private String serialNumber = "serialNumber_1";

	private String firmwareVersion = "1.0.0";

	/** [실행] ID:4 오류 발생 시 장치를 복원하기 위한 장치 Rebooting 명령 실행 */
	/** [실행] ID:5 LwM2M 설정(Observe, Notify)값을 공장 출하 상태의 설정값으로 변경
		장치는 설정값을 초기화한 후 재등록을 하기 위해서 상호인증 과정을 재실행하여야 한다.
		단 상호인증을 위한 설정이 선행되어야 한다. */

	/** ID:6 파워 리소스 0 – DC power, 동일한 Resource Instance ID는 동일한 파워 리소스를 사용하여야 한다. */
	private int availablePowerSources = 0;

	/** ID:7 장치가 동작하는 정격전압 */
	private int powerSourceVoltage = 12000;

	/** ID:8 제조사가 계기 30대 연결 환경에서 24시간 측정한 최소값, 최대값, 평균값 기록한다. 예) 최소갑, 최대값, 평균값 : “50, 200, 150” */
	@Override public String getPowerSourceCurrent() {	// OMA 규격 불량
		return "50, 200, 150";
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
	private int deviceType = 3;

	/** ID: 18 모뎀의 현재 H/W 버전 소수점 두 자리로 표현 (Major Number. Minor Number, Revision Number) 예) “1.0.1” */
	@Override public String getHardwareVersion() {
		return "1.0.0";
	}

	/** ID: 21 데이터 및 소프트웨어를 저장할 수 있는 메모리(ROM) 총 저장공간 */
	@Override public int getMemoryTotal() {
		return (int)(Runtime.getRuntime().totalMemory() / 1024);
	}



	/**********************************
	  [4] Connectivity Monitoring Info
	 **********************************/
	/** ID:2 RF 수신 세기: LTE 모뎀은 RSRP 값으로 표시한다.
	 * (GSM: RSSI, UMTS: RSCP, LTE: RSRP, NB-IoT: NRSRP)
	 * 서버에서 값 요청 시 1초 단위로 30초간 값을 전송한다.
	 * Notify 설정: lt= (dBm) / 초기설정값: 통신사에서 정하며 BMT 시 제출하여야 한다. */
	@Override public int getRadioSignalStrength() {
		return 0;
	}

	/** ID:3 수신된 신호의 링크품질: LTE 모뎀은 RSRQ 값으로 표시한다. (3GPP 36.214 참조)
	 * 서버에서 값 요청 시 1초 단위로 30초간 값을 전송한다.
	 * Notify 설정: lt= () / 초기설정값: 통신사에서 정하며 BMT 시 제출하여야 한다.
	 */
	@Override public int getLinkQuality() {
		return 0;
	}

	/** ID:4 모뎀의 IP 주소 (IPv6)
	 * Ob_ID 6/4의 공인 IP 또는 11/4의 사설 IP 둘 중 하나는 필수 항목임 */
	@Override public String getIpAddress() {
		try {
			String address = Inet4Address.getLocalHost().getHostAddress();
			Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();

			while (n.hasMoreElements()) {
				NetworkInterface e = n.nextElement();

				Enumeration<InetAddress> a = e.getInetAddresses();
				while (a.hasMoreElements()) {
					InetAddress addr = a.nextElement();
					System.out.println("  " + addr.getHostAddress());
				}
			}

			return address;
		} catch (UnknownHostException e) {
			return "null";
		} catch (SocketException e) {
			return "null";
		}
	}

	/** ID:5 Res_ID_4에서 지정한 각 인터페이스의 다음 홉 라우터의 IP 주소 (공인 IP / DCU 적용)
	 * 참조 : 이 IP 주소는 서버 IP 주소를 의미하지 않는다.  */
	@Override public String getRouterIpAddress() {
		return "null";
	}

	/** ID:8 Serving Cell ID, LTE Cell ID (3GPP 25.331 참조) */
	@Override public int getCellID() {
		return 0;
	}

	/** ID:9 Serving Mobile Network Code를 나타낸다. (3GPP 23.003 참조)
	 * [SKT: 03, 05]  [KT: 02, 04, 08]  [LGU+: 06] */
	@Override public int getSMNC() {
		return 0;
	}

	/** ID:10 Serving Mobile Country Code (대한민국 : 450) */
	private int SMCC = 450;

	/** ID: 신호대 잡음 비
	 * 서버에서 값 요청 시 1초 단위로 30초간 값을 전송한다.
	 * Notify 설정: lt= () / 초기설정값: 통신사에서 정하며 BMT 시 제출하여야 한다 */
	@Override public int getSignalSNR() {
		return 0;
	}



	/**************************
	  [5] Firmware Update Info
	 **************************/
	/** ID:0 Firmware package Image Next Version (BSP, BIOS, DRIVE 등) */
	@Override public byte[] getFirmwarePackage() {
		return null;
	}

	/** ID:1 펌웨어 패키지를 다운로드할 수 있는 URI (RFC 3986 참조)
	 * 예) coap://[IPv6]/firmware 또는 coap://[IPv6]/software (IP address는 한전 사설 IPv6 주소) */
	public String firmwarePackageURI = "";

	/** ID:3 펌웨어 업데이트와 관련된 현재 상태를 나타낸다. 0:Idle (before downloading or after successful updating)
	 * 1:Downloading (The data sequence is on the way)
	 * 2:Downloaded
	 * 3:Updating
	 * 펌웨어 패키지를 패키지 리소스에 쓰거나 장치가 패키지 URI에서 펌웨어 패키지를 다운로드한
	 * 경우 “dowloaded“ 상태로 변경된다. 패키지 또는 패키지 URI 리소스에 빈 문자열을 쓰면 업데이트 상태가 재설정되며, 이때 상태
	 * 리소스 값은 “Idle“ 상태로 설정되고 업데이트 결과 리소스 값은 0으로 설정된다.
	 * 다운로드 상태에서 실행 리소스 업데이트가 트리거 되면 상태가 “updating”으로 변경된다.
	 * 리소스 업데이트에 실패하면 “downloaded” 상태로 되돌아간다.
	 * 업데이트 리소스를 성공적으로 수행한 경우 상태가 “update”에서 “idle” 상태로 변경된다.*/
	@Override public int getFirmwareUpdateState() {
		return 0;
	}

	/** ID:5 Firmware update 결과
	 * 0: 초기값. 업데이트 프로세스가 시작되면 다운로드/업데이트 단계로 변환된다.
	 * 1: 업데이트 성공
	 * 2: 플래시 메모리 부족
	 * 3. 다운로드 프로세스 중 RAM 부족
	 * 4: 다운로드 프로세스 중 연결이 끊김
	 * 5: 다운로드 된 새 패키지에 대한 무결성 검사 실패
	 * 6: 미지원 패키지 유형
	 * 7: 잘못된 URI
	 * 8: 펌웨어 업데이트 실패
	 * 9: 미지원 프로토콜 */
	@Override public int getFirmwareUpdateResult() {
		return 0;
	}

	/** ID:6 Firmware package Image (Next Version Name) */
	public String firmwarePackageName = "null";

	/** ID:7 Firmware package Image (Next Version)
	 * 소수점 두 자리로 표현 (Major Number. Minor Number, Revision Number) 예) “1.0.1” */
	public String firmwarePackageVersion = "1.0.0";

	/** ID:8 지원되는 프로토콜을 정의한다. 0 – CoAP (as defined in RFC 7252) CoAP 기본 설정, block-wise transfer 지원
	 * 1 – CoAPS (as defined in RFC 7252) block-wise transfer 지원
	 * 2 – HTTP 1.1 (as defined in RFC 7230)
	 * 3 – HTTPS 1.1 (as defined in RFC 7230)
	 * 추가 값은 향후에 정의할 수 있어야 하며 서버가 인식하지 못하는 값은 무시해야 한다 */
	@Override public int getFirmwareUpdateProtocolSupport() {
		return 0;
	}

	/** ID:9 펌웨어 업데이트 파일 전달 방식
	 * 0 – Pull (Package URI 리소스 사용 시 클라이언트에서 서버를 Pull 하는 방식)
	 * 1 – Push (Package 리소스 사용 시 서버에서 클라이언트로 Push 하는 방식(일괄 다운로드)
	 * 2 – both
	 * Push 방식과 Pull 방식을 모두 지원하여야 한다. */
	@Override public int getFirmwareUpdateDeliveryMethod() {
		return 0;
	}



	/**************
	  [6] Location
	 **************/
	/** ID:0 위도의 십진 표기 */
	private float latitude = 0;

	/** ID:1 경도의 십진 표기 */
	private float longitude = 0;

	/** ID:5 위치 측정이 수행된 시간의 타임스탬프이다. */
	private Date timestamp = new Date();

	/*****************************
	  [7] Connectivity Statistics
	 *****************************/
	/** ID:2 수집 기간 전송된 데이터의 총량을 나타낸다. */
	public int getTxData() {
		return 0;
	}

	/** ID:3 수집 기간 수신된 데이터의 총량을 나타낸다. */
	public int getRxData() {
		return 0;
	}

	/** ID:4 수집 기간 최대 메시지 크기이다 */
	public int getMaxMessageSize() {
		return 0;
	}

	/** ID:5 수집 기간 평균 메시지 크기이다 */
	public int getAverageMessageSize() {
		return 0;
	}

	/** [실행] ID:6 Resource ID 2~5의 값을 초기화한다.
	 * Resource ID 8 Collection Period 동안 데이터 수집을 시작하는 명령이다.
	 * Resource ID 8 Collection Period가 0인 경우
	 * Resource ID 7 Stop 명령이 실행될 때까지 데이터 수집을 계속하며 3초 단위로 Notify 한다. */

	/** [실행] ID:7 Resource ID 8 Collection Period가 0인 경우, Resource 수집 중지 명령이다.
	 * 정보 수집을 중지하고, resource ID 2-5의 값을 재설정하지 않는다. */

	/** ID:8 Resource ID 2~5의 수집 기간을 나타낸다.
	 * 값이 0 인 경우 Start / Stop 리소스에 따라 동작한다. */
	private int collectionPeriod = 0;


	/** ID:  */
	

	/** ID:  */
	

	/** ID:  */
}
