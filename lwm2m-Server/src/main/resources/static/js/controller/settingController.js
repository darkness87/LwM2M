$(document).ready(function() {

	getProperty("RedisConfig");
	// 동시 실행하면 하나가 동작 안함 - setTimeout 시간 지연으로 실행
	setTimeout(function() {
		getProperty("Californium");
	}, 10);
	
});
