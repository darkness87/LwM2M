var LWM2M_CONST = {
	LIST_SIZE: 20,
	PAGE_ACTION_TYPE_LISTJS: "listJS",
	EXPIRED_RECENTLY_DATE: 15,
	// Ajax SyncMode
	AJAX_ASYNC_MODE: "0",	// Ajax 비동기 방식
	AJAX_SYNC_MODE: "1"	// Ajax 동기 방식
}

//init
var DEBUG_MODE = false;
var TIME_OUT = 15000;

//log
function LOG(msg) {
	if (DEBUG_MODE && window.console) {
		console.log(msg);
	}
}