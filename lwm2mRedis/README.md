## cnuRedis.jar

@author skchae@cnuglobal.com
@version 0.1_20200818
@since 2020.08.10

* 해당 라이브러리는 Redis을 연동하기 위하여 Jedis 라이브러리 기반으로 제작되었다.
* Redis 연동을 위한 라이브러리는 Jedis, Lettuce가 있으며 빠른 개발을 위해 샘플링 코드가 많고 사용시 가독성 등으로 우선적으로 Jedis를 활용하여 개발하였다.
* jedis 동기식 - 단일성 구축시 장점 , 빠르게 구축 (레퍼런스 많음)
* lettuce 비동기식 - 확장성 , 코드 복잡성 (레퍼런스 적음), 통신속도 측면 조금 더 빠른 향상을 가지고 있음
* 라이브러리의 원할한 사용을 위하여 아래와 같이 추가 라이브러리가 포함된다. (라이브러리가 변경될 수 있다.)

  |라이브러리              |비고      |
  |----------------|-----------|
  |jedis-3.3.0.jar||
  |commons-pool2-2.6.2.jar||
  |jackson-databind-2.11.0.jar||
  |jackson-annotations-2.11.0.jar||
  |jackson-core-2.11.0.jar||
  |junit-3.8.1.jar|삭제|
  |slf4j-api-1.7.30.jar|삭제|
  |slf4j-simple-1.7.30.jar|삭제|


## API Class

- RedisConnect
  => Redis 연결을 위해 사용
  => 실질적으로 사용자가 사용하지 않으나 필요할 경우가 있을 경우 사용할 수 있다.
  ```java
  RedisConnect redisConnect = new RedisConnect();

  // connect
  redisConnect.connect();

  // close
  redisConnect.close();

  ```  

- RedisObjectData
  => Object 데이터를 get/set 하여 할용할 수 있도록 한다.
  => List 된 Object 정보도 get/set

  ```java
  RedisObjectData redisObjectData = new RedisObjectData();

  // 해당 키 정보로 Object 값 SET
  redisObjectData.setObjectData(String key, Object object);
  
  // 해당 키 정보로 Object 값 GET
  redisObjectData.getObjectStringData(String key);

  ```  
  
  ```java

	// 사용 예시 1
	RedisObjectData redisObjectData = new RedisObjectData();

	LpDataVo lpDataVo = new LpDataVo();
	
	lpDataVo.setId("1234567890");
	.
	.
	.

	redisObjectData.setObjectData(key, lpDataVo);


	// 사용 예시 2
	List<Object> list = new ArrayList<Object>();
		
	LpLoadProfileVo LoadProfileVo = new LpLoadProfileVo();
	
	for(int i=0;i<96;i++) {
		
		LoadProfileVo.setAid("1234567890");
		.
		.
		.
			
		list.add(LoadProfileVo);
			
	}
		
		redisObjectData.setObjectListData(key, list);


	// 데이터 가져오기 예시
	List<MeterVo> meterlist = mapper.readValue(redisObjectData.getObjectStringData(key),new TypeReference<List<MeterVo>>(){}); // 데이터 get, Object를 List<MeterVo> 타입으로

	MeterVo meterlist = mapper.readValue(redisObjectData.getObjectStringData(key),MeterVo.class); // 데이터 get, Object를 MeterVo 타입으로

	// Json 정렬
	String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(meterlist);

  ``` 

- RedisInfoData
  => 전체 KEY 조회, KEY 검색, KEY값 유무, KEY데이터 보관일(초) 설정, KEY 삭제, 접속 Client확인, 상태 등을 확인할 수 있다.

  ```java
  RedisInfoData redisInfoData = new RedisInfoData();
  
  // 전체 KEY 조회 , return Set<String>
  redisInfoData.getAllKey();

  // 검색 KEY 조회, return Set<String>
  redisInfoData.getSearchKey(String key);

  // KEY 값 유무, return true or false
  redisInfoData.getBooleanKey(String key);
  
  // KEY Expire 초 세팅 (해당 초 만큼 데이터 유지)
  redisInfoData.setExpireKey(String key, int sec);
  
  // KEY Expire 해제
  redisInfoData.setPersistKey(String key);
  
  // KEY 삭제
  redisInfoData.setDelKey(String key);
  
  // 현재 접속 Client 리스트
  redisInfoData.getClientList();
  
  // Radis 정보 출력
  redisInfoData.getRedisInfo();
  
  ```  
  

## KEY 규칙

- KEY 하나에 하나의 데이터가 들어간다.
- KEY 이름은 아래와 같은 규칙을 따른다. 예) Metering Data 일 경우
 * 데이터명:계량기번호:날짜
 * LoadProfile:11190000110:202008240930
 * LoadProfile:11190000110:202008240945
 * LoadProfile:11190000110:202008241000
 * LoadProfile:11190000110:202008241015

- KEY 구분시 이름 중간에 ':' 으로 구분하여 이름을 정한다. 
- KEY는 최소 7일 데이터를 가지고 있어야 하므로 expire 설정으로 해당 KEY의 유효 시간을 설정하면 관리하기 편하다.

- 초기화 진행시 flushAll 명령어로 모든 키가 삭제 될수 있어야 한다.
- expire 설정되지 않은 키에 대한 관리가 필요하다.
- 스케줄 관리를 위한 KEY 관리는 스케줄명:스케줄실행시간 으로 했을시 문제 없는지 확인한다. (확인 필요 - 즉시실행이 아닌 스케줄시간으로 처리시)

- KEYS * 로 모든키 검색 가능

- KEYS *202008240930 으로 검색하면 * 다음으로 입력한 키값 검색 (앞,뒤,중간 가능)
- KEYS *11190000110 *202008240930 으로도 검색 가능


## Config

- config.properties 파일 초기 세팅 정보
  => 단, config.properties 파일이 없을 경우 Exception 처리 후 파일 생성
  => 파일이 생성되면 아래 초기 정보가 들어가고 변경사항이 있을 경우 변경해야 함

  ```java

  ## Redis Connect Config
  redis.ipAddr=127.0.0.1
  redis.port=6379
  redis.timeout=1000
  redis.passWord=

  ```


## 배포

* Fat Jar Eclipse Plug-In 을 설치하여 배포한다.
* 해당 플러그인을 설치하면 사용되는 라이브러리까지 모두 포함하여 jar 파일 생성이 가능한다.
* 개발시 jar파일에 포함된 중복 라이브러리는 피한다.


## Sample

- RedisConnect 를 이용한 sample

  ```java
	
	// RedisConnect 직접코드 예시

	RedisConnect redisConnect = new RedisConnect();
	ObjectMapper mapper = new ObjectMapper();
	Jedis jedis = redisConnect.connect();
	
	List<Object> list = new ArrayList<Object>();
	meterVo meterVo = new meterVo();
	
	// 임의 30개 계량기 meterid 등록
	for(int i=100;i<130;i++) {
		meterVo = new meterVo();
		meterVo.setMeterId("11190000"+i);
		list.add(meterVo);
	}
	
	String setString1 = mapper.writeValueAsString(list);
	jedis.set("meterid", setString1);
	
	
	// 계량기 meterid 리스트 불러오기
	List<meterVo> meterObject = mapper.readValue(jedis.get("meterid"),new TypeReference<List<meterVo>>(){});
	
	
	// 임의 KEY명 지정하여 임의 데이터 값 넣기
	LpLoadProfileVo lp = new LpLoadProfileVo();
	Date date = new Date();
	SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmm");
	String fdate = baseFormat.format(date);
	String meterid = "";
	String key = "";
	Random rnd = new Random();
	
	for(int i=0;i<meterObject.size();i++) {
		lp = new LpLoadProfileVo();
		
		meterid = meterObject.get(i).getMeterId();
		key = "LoadProfile:"+meterid+":"+fdate;
		
		rnd = new Random();
		
		lp.setAid("agent_cnu_13493"); // Agent ID
		lp.setMid(meterid); // Meter ID
		lp.setcDv("0"); // 검침구분 (0:정규, 1:재검침, 2:자율검침)
		lp.setbDt(baseFormat.format(date)); // Base Date 수집기준일시
		lp.setfPa(String.valueOf(rnd.nextInt(10))); // 순방향 유효전력량
		lp.setfGa(String.valueOf(rnd.nextInt(10))); // 순방향 지상무효전력량
		lp.setfRa(String.valueOf(rnd.nextInt(10))); // 순방향 진상무효전력량
		lp.setfAa(String.valueOf(rnd.nextInt(10))); // 순방향 피상전력량
		lp.setmDt(baseFormat.format(date)); // Date // 검침일시 (계기기록시간)
		lp.setmSt("11"); // 계기 상태정보
		lp.setrPa(String.valueOf(rnd.nextInt(10))); // 역방향 유효전력량
		lp.setrRa(String.valueOf(rnd.nextInt(10))); // 역방향 진상무효전력량
		lp.setrGa(String.valueOf(rnd.nextInt(10))); // 역방향 지상무효전력량
		lp.setrSt("2"); // 결과 (0:대기, 1:전달완료, 2:성공, 101:실패, 102:미지원)
		lp.setcDt(baseFormat.format(date)); // Date // Master 요청에 대한 상세정보
	
		jedis.set(key, mapper.writeValueAsString(lp));
		jedis.expire(key, 86400); // 1일 
		
	}
	
	redisConnect.close();
	
	System.out.println("sample ===== "+fdate);
  
  ```  

- RedisObjectData 를 이용한 sample

  ```java

	// ObjectData를 이용한 Metering Data 정전복전 예시 

	ObjectMapper mapper = new ObjectMapper();
		
	RedisObjectData redisObjectData = new RedisObjectData(); 
	LpBlackoutVo lpBlackoutVo = new LpBlackoutVo(); // 정전복전VO
		
	lpBlackoutVo.setaId("1");
	lpBlackoutVo.setcDt("2");
	lpBlackoutVo.setmId("3");
	lpBlackoutVo.setoCd("4");
	lpBlackoutVo.setrSt("5");
		
	List<LpBlackoutRvlVo> list = new ArrayList<LpBlackoutRvlVo>();
	LpBlackoutRvlVo lpBlackoutRvlVo = new LpBlackoutRvlVo();  // 정전복전 상세VO
		
	for(int i=0;i<10;i++) {
		lpBlackoutRvlVo = new LpBlackoutRvlVo();
		lpBlackoutRvlVo.setbCt("50");
		lpBlackoutRvlVo.setbDt("70");
		list.add(lpBlackoutRvlVo);
	}
		
	lpBlackoutVo.setrVl(list);
	
	redisObjectData.setObjectData("testkey", lpBlackoutVo); // 키 규칙에 따른 키 값 생성 해야함
	
	// Json 값으로 정렬해서 보여주기
	String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(redisObjectData.getObjectData("testkey"));
        System.out.println(data);

  ```  


## Sample Code

- Sample Code

  ```java

	public void setMeterList(String key,String data) throws Exception {
		
		key = "meter:info";
		
		List<Object> list = new ArrayList<Object>();
		MeterVo meterVo = new MeterVo();
		
		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String rdate = baseFormat.format(date);
		
		for(int i=100;i<130;i++) {
			meterVo = new MeterVo();
			meterVo.setMeterId("11190000"+i);
			meterVo.setMeterType("Advance E-type");
			meterVo.setrDt(rdate);
			meterVo.setInfo("기타정보");
			list.add(meterVo);
		}
		
		redisDao.setRedisData(key, list); // 데이터 set, (Key, Value) 값으로 저장
		log.info(key+" => Test OK");
	}
	
	public String getMeterList(String key) throws Exception{
		
		key = "meter:info";
		String redisData = redisDao.getRedisData(key);
		List<MeterVo> meterlist = mapper.readValue(redisData,new TypeReference<List<MeterVo>>(){}); // 데이터 get, Object를 List<MeterVo> 타입으로

		String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(meterlist);
		log.info(data); // Json 정렬
		
		return data;
	}
	
	public void setLpData(String key,String data) throws Exception{
		
		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String fdate = baseFormat.format(date);
		String meterid = "11190000999";
		Random rnd = new Random();
		
		key = "LoadProfile:"+meterid+":"+fdate; // KEY명 규칙 필요

		LpLoadProfileVo lp = new LpLoadProfileVo();
		
		lp.setAid("agent_cnu_13493"); // Agent ID
		lp.setMid(meterid); // Meter ID
		lp.setcDv("0"); // 검침구분 (0:정규, 1:재검침, 2:자율검침)
		lp.setbDt(baseFormat.format(date)); // Base Date 수집기준일시
		lp.setfPa(String.valueOf(rnd.nextInt(10))); // 순방향 유효전력량
		lp.setfGa(String.valueOf(rnd.nextInt(10))); // 순방향 지상무효전력량
		lp.setfRa(String.valueOf(rnd.nextInt(10))); // 순방향 진상무효전력량
		lp.setfAa(String.valueOf(rnd.nextInt(10))); // 순방향 피상전력량
		lp.setmDt(baseFormat.format(date)); // Date // 검침일시 (계기기록시간)
		lp.setmSt("11"); // 계기 상태정보
		lp.setrPa(String.valueOf(rnd.nextInt(10))); // 역방향 유효전력량
		lp.setrRa(String.valueOf(rnd.nextInt(10))); // 역방향 진상무효전력량
		lp.setrGa(String.valueOf(rnd.nextInt(10))); // 역방향 지상무효전력량
		lp.setrAa(String.valueOf(rnd.nextInt(10))); // 역방향 피상전력량
		lp.setrSt("2"); // 결과 (0:대기, 1:전달완료, 2:성공, 101:실패, 102:미지원)
		lp.setcDt(baseFormat.format(date)); // Date
		
		redisDao.setRedisData(key, lp); // int 0 값 : 성공, 그외 실패
		redisDao.setExpireKey(key, 86400); // 키값 expire 설정 * 필수 * => 7일 : 604,800 초 , 1일 : 86400 초
		
		log.info(key+" => Test OK");
	}
	
	public String getLpData(String key) throws Exception{
		
		String meterid = "11190000999";
		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String fdate = baseFormat.format(date);
		key = "LoadProfile:"+meterid+":"+fdate;
		
		String redisData = redisDao.getRedisData(key);
		LpLoadProfileVo loadProfileData = mapper.readValue(redisData,LpLoadProfileVo.class);

		String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(loadProfileData);
		log.info(data); // Json 정렬
		
		return data;
	}

	public void setLpBlackout(String key,String data) throws Exception{
		
		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String fdate = baseFormat.format(date);
		String meterid = "11190000999";
		Random rnd = new Random();
		
		key = "BlackOut:"+meterid+":"+fdate; // KEY명 규칙 필요
		
		LpBlackoutVo lpBlackoutVo = new LpBlackoutVo();
		
		lpBlackoutVo.setaId("agent_cnu_13493");
		lpBlackoutVo.setcDt(fdate);
		lpBlackoutVo.setmId(meterid);
		lpBlackoutVo.setoCd("111");
		lpBlackoutVo.setrSt("222");
		
		List<LpBlackoutRviVo> list = new ArrayList<LpBlackoutRviVo>();
		LpBlackoutRviVo lpBlackoutRviVo = new LpBlackoutRviVo();
		
		for(int i=0;i<10;i++) {
			lpBlackoutRviVo = new LpBlackoutRviVo();
			lpBlackoutRviVo.setbCt(String.valueOf(rnd.nextInt(100)));
			lpBlackoutRviVo.setbDt(fdate);
			list.add(lpBlackoutRviVo);
		}
		
		lpBlackoutVo.setrVl(list);
		
		redisDao.setRedisData(key, lpBlackoutVo);
		
		log.info(key+" => Test OK");
		
	}
	
	public String getLpBlackout(String key) throws Exception{
		
		Date date = new Date();
		SimpleDateFormat baseFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String fdate = baseFormat.format(date);
		String meterid = "11190000999";
		
		key = "BlackOut:"+meterid+":"+fdate; // KEY명 규칙 필요
		
		String redisData = redisDao.getRedisData(key);
		
		LpBlackoutVo blackOutData = mapper.readValue(redisData,LpBlackoutVo.class);

		String aId = blackOutData.getaId();
		for(int i =0;blackOutData.getrVl().size()>i;i++){
			blackOutData.getrVl().get(i).getbCt();
			blackOutData.getrVl().get(i).getbDt();
		}
		
		String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(blackOutData);
		log.info(data);
				
		return data;
	}
	
  ```  

## CoAP

- 전달 정보 (테스트 샘플값)

//==[ CoAP Response ]============================================
MID    : 18773
Token  : 9C210F745C8C662C
Type   : ACK
Status : 2.05 - CONTENT
Options: {"Content-Format":"text/plain"}
RTT    : 129 ms
Payload: 256 Bytes
//---------------------------------------------------------------
[ {
  "meterId" : "11190000100",
  "meterType" : "Advance E-type",
  "rDt" : "20200828093300",
  "info" : "기타정보"
}, {
  "meterId" : "11190000101",
  "meterType" : "Advance E-type",
  "rDt" : "20200828093300",
  "info" : "기타정보"
} ]
//===============================================================
