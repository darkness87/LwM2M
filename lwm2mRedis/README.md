===== LwM2MRedis.jar =====

@author skchae@cnuglobal.com
@version 0.1_20200818
@since 2020.08.10

* 해당 라이브러리는 Redis을 연동하기 위하여 Jedis 라이브러리 기반으로 제작되었다.

* 라이브러리의 원할한 사용을 위하여 아래와 같이 추가 라이브러리가 포함된다. (라이브러리가 추가되어 변경될 수 있다.)

junit-3.8.1.jar
jedis-3.3.0.jar
commons-pool2-2.6.2.jar
slf4j-api-1.7.30.jar
slf4j-simple-1.7.30.jar
jackson-databind-2.11.0.jar
jackson-annotations-2.11.0.jar
jackson-core-2.11.0.jar

=========================

* API 클래스

- RedisConnect
  => Redis 연결을 위해 사용
  => 실질적으로 사용자가 사용하지 않으나 필요할 경우가 있을 경우 사용할 수 있다.
  
- ObjectData
  => Object 데이터를 get/set 하여 할용할 수 있도록 한다.
  => List 된 Object 정보도 get/set

  setObjectData(String, Object)
  getObjectData(String)
  setObjectListData(String, List<Object>)
  getObjectListData(String)

- LpData
  => Metering Data 정보를 조회 (미사용 예정 - ObjectData로 통일)
  
- InfoData
  => 전체 KEY 조회, KEY 검색, KEY값 유무, KEY데이터 보관일 설정, 상태 등을 확인할 수 있다.
  
  getAllKey()
  getSearchKey(String)
  getBooleanKey(String)
  setExpireKey(String, int)
  setPersistKey(String)
  setDelKey(String)
  getClientList()
  getRedisInfo()
  
  