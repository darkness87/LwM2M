var LWM2M_PROXY = {
	xhrPool: [],
	// 공통
	duplicateAPI: {},
	loadingbarCode: ["getAllRegistrations", "getObservationList", "awakeDevice", "getObjectModel"],
	abortCode: ["equipment", "fault"],
	alertCode: { "0000x": true, "00001x": true },		//alertCode -> boolean형은 사용할지 여부
	confirmCode: { "0000x": true, "00001x": true },	//confirmCode -> boolean형은 사용할지 여부

	// 메인&로그인
	login : "login.do",
	getAllRegistrations: "/getAllRegistrations.do",
	getById: "/getById.do",
	getByEndpoint: "/getByEndpoint.do",
	getObservationList: "/getObservationList.do",
	cancelObservations: "/cancelObservations.do",
	awakeDevice: "/awakeDevice.do",
	getObjectModel: "/getObjectModel.do",
	coapObserve: "/coapObserve.do",
	coapObserveCancel: "/coapObserveCancel.do",
	coapRead: "/coapRead.do",
	coapWrite: "/coapWrite.do",
	coapExec: "/coapExec.do",
	getRedisKeyList: "getRedisKeyList.do",
	getRedisKeyData: "getRedisKeyData.do",
	coapWriteFile : "coapWriteFile.do",
	getUsage : "getUsage.do",
	getExternalIP : "getExternalIP.do",
	getLocation : "getLocation.do",
	getProperty : "getProperty.do",
	sender : "sender",
	cancelResourceObservation : "cancelResourceObservation.do",
	cancelRegistrationIdObservation : "cancelRegistrationIdObservation.do",
	unRegistration : "unRegistration.do",
	getSearchData : "getSearchData.do",
	getExternalIPLocation : "getExternalIPLocation.do",
	setProperty : "setProperty.do",

	prefixUrl: "",

	errorSessionExpired: "/error/sessionExpired",

	// 메뉴

	// 유저

	// 관리자

	invokeDownloadOpenAPI: function (apiCode) {
		var url = LWM2M_PROXY.prefixUrl + LWM2M_PROXY[apiCode];
		window.open(url, "_self");
	},
	invokeOpenAPI: function (apiCode, dataType, params, successCallBack, errorCallBack) {
		var url = LWM2M_PROXY[apiCode];
		var ajaxAsync = true;
		var successCallBack2 = null;
		var errorCallBack2 = null;

		if (LWM2M_PROXY.loadingbarCode.indexOf(apiCode) > -1
			&& $("#contentsBody").find(".table").find("tbody").length > 0
			&& $("#contentsBody").find(".table").find("tbody").children().length == 0) {
			PAGE_CONTROLLER.getTopicPageAppend("loadingbar", "loadingbar", $("#contentsBody").find(".table").find("tbody"));
		} else if (LWM2M_PROXY.loadingbarCode.indexOf(apiCode) > -1
			&& $("#contentsBody").find(".table").find("tbody").length > 1) {
			$("#contentsBody").find(".table").find("tbody").each(function (_i, e) {
				var view = $(e);

				if (view.children().length == 0) {
					PAGE_CONTROLLER.getTopicPageAppend("loadingbar", "loadingbar", view);
				}
			})
		}

		if (LWM2M_PROXY[apiCode] == undefined || LWM2M_PROXY[apiCode] == "") {
			// alert(SQUARE_RES.get("msg_invalid_apicode"));
			alert("유효하지 않은 API코드 : " + apiCode + " --> " + LWM2M_PROXY[apiCode]);
			return;
		}

		//메뉴 클릭하여 그룹의 컨텐츠 처음 호출되는 ajax 콜인 경우, 이미 요청된 ajax가 존재한다면 해당 ajax을 abort 하도록 처리함.
		if (LWM2M_PROXY.abortCode.indexOf(apiCode) > -1) {
			LWM2M_PROXY.abortOpenApi();
		}

		if (dataType == "json") {
			params["dataType"] = "json";
			var d = new Date();
			var t = new Date(d.getFullYear(), d.getMonth(), d.getDay(), d.getHours(), d.getMinutes(), d.getMilliseconds());
			var time = Date.parse(t.toISOString()) + ((1000 * 60 * 60 * 24) - 1);
			params["timestamp"] = time;
			params["apiCode"] = apiCode;
			FINAL_TIME_STAMP = time;
		} else if (dataType == "html") {
			params["dataType"] = "html"
		}

		if (LWM2M_PROXY["duplicateAPI"][apiCode] != undefined) {
			if (apiCode != "getInputMemberCount" && apiCode != "getSquareMemberCount") {
				if (apiCode != "getWorkfeedContentsList" && apiCode != "searchResult") {
					// systemMessage(SQUARE_RES.get("msg_try_again_later"));
					//alert("잠시후 다시 사용해주세요");
				}

				return;
			}
		}

		LWM2M_PROXY["duplicateAPI"][apiCode] = true;

		// successCallBack이 없을 경우
		if (successCallBack == undefined || successCallBack == null) {
			successCallBack2 = function (response, status, request) {
				LOG("::::::::::SUCCESS START");
				LOG(response);
				LOG(status);
				LOG(request);
				LOG("::::::::::SUCCESS END ");
			};
		} else {
			successCallBack2 = successCallBack;
		}

		// errorCallBack이 없을 경우
		if (errorCallBack == undefined || errorCallBack == null) {
			errorCallBack2 = function (request, status, error) {
				if (request && request.responseJSON) {	// 서버 500에러 일때 처리
					alert(request.responseJSON.message);
				}
				LOG("::::::::::ERROR START");
				LOG(request);
				LOG(status);
				LOG(error);
				LOG("::::::::::ERROR END ");
			};
		} else {
			errorCallBack2 = errorCallBack;
		}

		if (LWM2M_CONST.AJAX_SYNC_MODE == params["ajaxMode"]) {
			ajaxAsync = false;
		}

		$.ajax({
			type: "POST",
			async: ajaxAsync,
			url: url,
			dataType: dataType,
			timeout: TIME_OUT,
			cache: false,
			data: params,
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",

			error: function (_request, status, _error) {
				delete LWM2M_PROXY["duplicateAPI"][apiCode];

				if (LWM2M_PROXY.loadingbarCode.indexOf(apiCode) > -1) {
					$("#contentsBody").find("#loadingbar").remove();
				}

				if (status == "parsererror") {
					alert("session expired");
				}

				errorCallBack2(_request, status, _error);
			},

			success: function (response, status, request) {
				delete LWM2M_PROXY["duplicateAPI"][apiCode];

				if (LWM2M_PROXY.loadingbarCode.indexOf(apiCode) > -1) {
					$("#contentsBody").find("#loadingbar").remove();
				}

				if (dataType == "json") {
					LOG(":::::::::Response:::::::::");
					LOG(response);
					if (response.responseHead == null || response.responseHead == undefined) {	// AMI api 콜이 아닌 경우
						successCallBack2(response, params, status, request);
					} else if (LWM2M_PROXY.isAlertCode(response.responseHead.resultCode) == true) {		// exception 정의 코드 유무 확인 (LWM2M_PROXY.alertCode)
						alert(response.responseHead.resultMessage);
					} else if (LWM2M_PROXY.isConfirmCode(response.responseHead.resultCode) == true) {		// exception 정의 코드 유무 확인 (LWM2M_PROXY.confirmCode)
						if (confirm(response.responseHead.resultMessage)) {
							// if (LWM2M_PROXY.PIMS_DETECT_FOUND == response.responseHead.resultCode) {
							// } else if (LWM2M_PROXY.OVER_LIMITED_MEMBERS == response.responseHead.resultCode) {
							// } else if (LWM2M_PROXY.SOME_FILES_NOT_EXISIS == response.responseHead.resultCode) {
							// }
						}
					} else if (response.responseHead.resultCode != "0") {
						systemMessage(response.responseHead.resultMessage);

						if (response.responseHead.resultCode == "20") {
							location.href = "/ami/logout";
						}

						errorCallBack2();
					} else if (response.responseData != null && response.responseData != undefined) {	//json형식이면서 Data가 있을때
						successCallBack2(response.responseData, response.responseHead, params, status, request);
					} else {	//json형식이면서 Data가 없을경우 (즐겨찾기 등록 성공 여부 등등)
						successCallBack2(response, params, status, request);
					}
				} else {
					if (request.getResponseHeader("content-type").indexOf("json") > -1 && JSON.parse(response).responseHead.resultCode != 0) {
						systemMessage(JSON.parse(response).responseHead.resultMessage);

						if (JSON.parse(response).responseHead.resultCode == "20") {
							location.href = "/ami/logout";
						}

						errorCallBack2();
					} else {
						successCallBack2(response, params, status, request);
					}
				}
			},

			beforeSend: function (request, _settings) {
				LWM2M_PROXY.xhrPool.push(request);
			},

			complete: function (request, _status) {
				LWM2M_PROXY.xhrPool = $.grep(LWM2M_PROXY.xhrPool, function (xhr) {
					return xhr != request;
				});

				if (request.getResponseHeader("Status") == "302") {
					location.href = request.getResponseHeader("Location");
				}
			}
		});
	},
	abortOpenApi: function () {
		$.each(LWM2M_PROXY.xhrPool, function (_idx, jqXHR) {
			jqXHR.abort();
		});
	},
	isAlertCode: function (resultCode) {
		if (LWM2M_PROXY["alertCode"].hasOwnProperty(resultCode)) {
			if (LWM2M_PROXY["alertCode"][resultCode]) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	},
	isConfirmCode: function (resultCode) {
		if (LWM2M_PROXY["confirmCode"].hasOwnProperty(resultCode)) {
			if (LWM2M_PROXY["confirmCode"][resultCode]) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
};