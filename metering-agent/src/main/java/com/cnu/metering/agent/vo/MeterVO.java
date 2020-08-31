package com.cnu.metering.agent.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeterVO {
	String meterId; // 계량기번호
	String meterType; // 계량기타입
	String rDt; // 등록일시
	String info; // 기타정보
}
