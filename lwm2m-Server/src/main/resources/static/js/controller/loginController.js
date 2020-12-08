$(document).ready(function() {

});

function login() {
	var view = $("#page-top");
	var id = view.find("#inputId").val();
	var pw = view.find("#inputPassword").val();

	if (id == null || id == "") {
		alert("아이디를 입력해 주세요.");
		return;
	} else if (pw == null || pw == "") {
		alert("패스워드를 입력해 주세요.");
		return;
	}

	var param = {};
	param["id"] = id;
	param["pw"] = pw;
	LWM2M_PROXY.invokeOpenAPI("login", "json", param, function(result, _head, _params) {
		if (result == 1) {
			console.log("login Fail");
			alert("일치하지 않습니다. 아이디와 패스워드를 확인해 주세요.");
		} else if (result == 0) {
			console.log("login Success");
			location.href = "index.html";
		}

	});

}

function enterkey() {
	if (window.event.keyCode == 13) {
		var view = $("#page-top");
		var id = view.find("#inputId").val();
		var pw = view.find("#inputPassword").val();

		if (id == null || id == "") {
			alert("아이디를 입력해 주세요.");
		} else if (pw == null || pw == "") {
			alert("패스워드를 입력해 주세요.");
		} else {
			login();
		}
	}
}
