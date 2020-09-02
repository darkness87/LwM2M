package com.cnu.lwm2m.client.init;

import org.eclipse.leshan.core.request.BindingMode;

import com.cnu.lwm2m.client.model.impl.SecurityInfo;
import com.cnu.lwm2m.client.model.impl.ServerInfo;

import lombok.Getter;

@Getter
public abstract class AbsCNUModelSettings implements SecurityInfo, ServerInfo {
	/*******************
	  [0] SECURITY Info
	 *******************/

	/*****************
	  [1] SERVER Info
	 *****************/
	/** ID:0 서버 객체 인스턴스를 연결하는 링크로 사용한다.*/
	private int serverID = 12345;

	/** ID:1 등록 유효기간을 초 단위로 지정한다. 30일 = 60s * 24h * 30d = 2,592,000 */
	private long lifeTime = 60 * 24 * 30;

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
}
