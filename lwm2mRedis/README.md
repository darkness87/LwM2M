## LwM2MRedis.jar

@author skchae@cnuglobal.com
@version 0.1_20200818
@since 2020.08.10

* 해당 라이브러리는 Redis을 연동하기 위하여 Jedis 라이브러리 기반으로 제작되었다.
* 라이브러리의 원할한 사용을 위하여 아래와 같이 추가 라이브러리가 포함된다. (라이브러리가 추가 및 변경될 수 있다.)

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
  