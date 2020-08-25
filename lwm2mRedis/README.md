## LwM2MRedis.jar

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
  |junit-3.8.1.jar||
  |jedis-3.3.0.jar||
  |commons-pool2-2.6.2.jar||
  |slf4j-api-1.7.30.jar||
  |slf4j-simple-1.7.30.jar||
  |jackson-databind-2.11.0.jar||
  |jackson-annotations-2.11.0.jar||
  |jackson-core-2.11.0.jar||


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

- ObjectData
  => Object 데이터를 get/set 하여 할용할 수 있도록 한다.
  => List 된 Object 정보도 get/set

  ```java
  ObjectData objectData = new ObjectData();

  // 해당 키 정보로 Object 값 SET
  objectData.setObjectData(String key, Object object);
  
  // 해당 키 정보로 Object 값 GET
  objectData.getObjectData(String key);

  // 해당 키 정보로 List Object 값 SET
  objectData.setObjectListData(String key, List<Object> object)
  
  // 해당 키 정보로 List Object 값 GET
  objectData.getObjectListData(String key)

  ```  
  
  ```java

	// 사용 예시
	List<Object> list = new ArrayList<Object>();
		
	LpLoadProfileVo LoadProfileVo = new LpLoadProfileVo();
	
	for(int i=0;i<96;i++) {
		
		LoadProfileVo.setAid("1234567890");
		.
		.
		.
		.
		.
			
		list.add(LoadProfileVo);
			
		}
		
		objectData.setObjectListData(key, list);
  ``` 

- InfoData
  => 전체 KEY 조회, KEY 검색, KEY값 유무, KEY데이터 보관일(초) 설정, KEY 삭제, 접속 Client확인, 상태 등을 확인할 수 있다.

  ```java
  InfoData infoData = new InfoData();
  
  // 전체 KEY 조회 , return Set<String>
  infoData.getAllKey();

  // 검색 KEY 조회, return Set<String>
  infoData.getSearchKey(String key);

  // KEY 값 유무, return true or false
  infoData.getBooleanKey(String key);
  
  // KEY Expire 초 세팅 (해당 초 만큼 데이터 유지)
  infoData.setExpireKey(String key, int sec);
  
  // KEY Expire 해제
  infoData.setPersistKey(String key);
  
  // KEY 삭제
  infoData.setDelKey(String key);
  
  // 현재 접속 Client 리스트
  infoData.getClientList();
  
  // Radis 정보 출력
  infoData.getRedisInfo();
  
  ```  
  
- LpData
  => Metering Data 정보를 조회 (미사용 예정 - ObjectData로 통일)
  ```java
  LpData lpData = new LpData();
  
  ```  
  

## Sample

- RedisConnect 를 이용한 sample

  ```java

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
		
		
		// 임의값 KEY 지정하여 임의 데이터 넣기
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
