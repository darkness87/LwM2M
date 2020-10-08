function getAllRegistrationsList() {
	var param = {};

	LWM2M_PROXY.invokeOpenAPI("getAllRegistrations", "json", param, function (result, _head, _params) {
		var view = $("#page-top");
		var listView = view.find("#lwm2mserverlist");
		listView.empty();

		if (result == null) {
			listView.html("<td colspan='8' style='text-align:center'>등록된 디바이스가 없습니다.</td>");
			return;
		}

		for (var i = 0; i < result.length; i++) {
			var item = result[i];
			var tmp = [];

			tmp.push("<tr onmouseover='this.style.background=\"#f8f9fc\"'; onclick='javascript:getObjectModel(\"" + item.endpoint + "\");' style='cursor: pointer;'>");
			tmp.push("	<td>" + item.endpoint + "</td>");
			tmp.push("	<td>" + item.id + "</td>");
			tmp.push("	<td>" + item.address + "</td>");
			tmp.push("	<td>" + item.port + "</td>");
			tmp.push("	<td>" + item.lwM2mVersion + "</td>");
			tmp.push("	<td>" + item.bindingMode + "</td>");
			tmp.push("	<td>" + item.lifeTimeInSec + "</td>");
			tmp.push("	<td>" + item.registrationDate + "</td>");
			tmp.push("	<td>" + item.lastUpdate + "</td>");
			tmp.push("</tr>");

			listView.append(tmp.join("\n"));
		}

	});
}

function getById(id) {
	var param = {};
	param["id"] = id;
	LWM2M_PROXY.invokeOpenAPI("getById", "json", param, function (result, _head, _params) {
		console.log(result);
	});
}

function getByEndpoint(endpoint) {
	var param = {};
	param["endpoint"] = endpoint;
	LWM2M_PROXY.invokeOpenAPI("getByEndpoint", "json", param, function (result, _head, _params) {
		console.log(result);
	});
}

function getObservationList(endpoint) {
	var param = {};
	param["endpoint"] = endpoint;

	LWM2M_PROXY.invokeOpenAPI("getObservationList", "json", param, function (result, _head, _params) {
		var view = $("#page-top");
		var listView = view.find("#clickdata");
		listView.empty();

		if (result == null || result == "") {
			listView.html("등록된 정보가 없습니다.");
			return;
		}

		listView.html("<div>" + result + "</div>");
	});
}

function getObjectModel(endpoint) {
	var param = {};
	param["endpoint"] = endpoint;

	LWM2M_PROXY.invokeOpenAPI("getObjectModel", "json", param, function (result, _head, _params) {
		var view = $("#page-top");
		var listView = view.find("#objectdiv");
		listView.empty();

		if (result == null || result == "") {
			listView.html("<div class='card shadow mb-4'><div class='card-header py-3'><h6 class='m-0 font-weight-bold text-primary'>등록된 디바이스가 없습니다.</h6></div></div>");
			return;
		}

		for (var i = 0; i < result.objectModels.length; i++) {
			var item = result.objectModels[i];
			var tmp = [];
			var observeUri = "/" + item.id + "/0";

			tmp.push("<div class='card shadow mb-4'>");
			tmp.push("	<div class='card-header py-3'>");
			tmp.push("		<h6 class='m-0 font-weight-bold text-primary'>" + item.id + " : " + item.name + "&nbsp&nbsp&nbsp<button class='btn btn-primary' type='button' onclick='javascript:sendCoapObserve(\"" + endpoint + "\",\"" + observeUri + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#'>Observe (" + observeUri + ") ▶</button>"
				+ "&nbsp&nbsp&nbsp<button class='btn btn-secondary' type='button' onclick='javascript:sendCoapObserveCancel(\"" + endpoint + "\",\"" + observeUri + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#'>■</button>" + "</h6>");
			tmp.push("	</div>");
			tmp.push("<div class='card-body'>");
			tmp.push("  <div class='table-responsive'>");
			tmp.push("    <table id='dataTable' width='100%' cellspacing='0'>");
			tmp.push("      <thead>");
			tmp.push("        <tr style='color: #000;'>");
			tmp.push("          <th>id</th>");
			tmp.push("          <th>resource name</th>");
			tmp.push("          <th>operations</th>");
			tmp.push("          <th>rangeEnumeration</th>");
			tmp.push("          <th>type</th>");
			tmp.push("          <th>units</th>");
			tmp.push("          <th width='20%'>data</th>");
			tmp.push("        </tr>");
			tmp.push("      </thead>");

			for (key in result.objectModels[i].resources) {
				var res = result.objectModels[i].resources[key];
				var uri = "/" + item.id + "/0" + "/" + res.id;
				var dataid = "tid" + item.id + "0" + res.id;

				tmp.push("      <tbody>");
				tmp.push("			<tr height='40'>");
				tmp.push("				<td>" + uri + "</td>");
				tmp.push("				<td>" + res.name + "</td>");

				if (res.operations == "R") {
					tmp.push("				<td>" + "<button class='btn btn-primary btn-sm' type='button' onclick='javascript:sendCoapRead(\"" + endpoint + "\",\"" + uri + "\",\"" + dataid + "\",\"" + res.type + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#'>Read</button>" + "</td>");
				} else if (res.operations == "RW") {
					tmp.push("				<td>" + "<button class='btn btn-primary btn-sm' type='button' onclick='javascript:sendCoapRead(\"" + endpoint + "\",\"" + uri + "\",\"" + dataid + "\",\"" + res.type + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#'>Read</button>"
						+ "&nbsp&nbsp<button class='btn btn-info btn-sm' type='button' onclick='javascript:viewWrite(\"" + endpoint + "\",\"" + uri + "\",\"" + dataid + "\",\"" + res.type + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#dataModal'>Write</button>" + "</td>");
				} else if (res.operations == "W") {
					tmp.push("				<td>" + "<button class='btn btn-info btn-sm' type='button' onclick='javascript:viewWrite(\"" + endpoint + "\",\"" + uri + "\",\"" + dataid + "\",\"" + res.type + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#dataModal'>Write</button>" + "</td>");
				} else if (res.operations == "E") {
					tmp.push("				<td>" + "<button class='btn btn-success btn-sm' type='button' onclick='javascript:sendCoapExec(\"" + endpoint + "\",\"" + uri + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#'>Exec</button>"
						+ "&nbsp&nbsp<button class='btn btn-secondary btn-sm' type='button' style='cursor: pointer;' data-toggle='modal' data-target='#'>※</button>" + "</td>");
				} else {
					tmp.push("				<td>" + res.operations + "</td>");
				}
				tmp.push("				<td>" + res.rangeEnumeration + "</td>");
				tmp.push("				<td>" + res.type + "</td>");
				tmp.push("				<td>" + res.units + "</td>");
				tmp.push("				<td id=\"" + dataid + "\" style='color: #4e73df; font-weight: bold;'>" + "" + "</td>");
				tmp.push("			</tr>");
				tmp.push("      </tbody>");

			}
			tmp.push("			</table>");
			tmp.push("  	</div>");
			tmp.push("	</div>");
			tmp.push("</div>");

			listView.append(tmp.join("\n"));
		}

	});
}

function sendCoapObserve(endpoint, uri) {
	var param = {};
	param["endpoint"] = endpoint;
	param["uri"] = uri;
	LWM2M_PROXY.invokeOpenAPI("coapObserve", null, param, function (result, _head, _params) {
		var data = eval(result);
		console.log(data);
		var view = $("#page-top");
		var dataView = null;
		for(var i = 0; i < data.length; i++){
			dataView = view.find("#" + data[i].tid);
			dataView.empty();
			dataView.html(data[i].value);
		}
	});
}

function sendCoapObserveCancel(endpoint, uri) {
	var param = {};
	param["endpoint"] = endpoint;
	param["uri"] = uri;
	LWM2M_PROXY.invokeOpenAPI("coapObserveCancel", null, param, function (result, _head, _params) {
		console.log(result);
	});
}

function sendCoapRead(endpoint, uri, dataid, type) {
	var param = {};
	param["endpoint"] = endpoint;
	param["uri"] = uri;
	param["type"] = type;
	LWM2M_PROXY.invokeOpenAPI("coapRead", null, param, function (result, _head, _params) {
		console.log(result);
		var view = $("#page-top");
		var dataView = view.find("#" + dataid);
		dataView.empty();

		if (result == null || result == "") {
			dataView.html("err");
			return;
		}

		dataView.html(result);
	});
}

function viewWrite(endpoint, uri, dataid, type) {
	var view = $("#page-top");
	var titleView = view.find("#titledata");
	var writeView = view.find("#detaildata");
	var footView = view.find("#footdata");
	titleView.empty();
	writeView.empty();
	footView.empty();

	titleView.html(endpoint + " : " + uri);
	writeView.html("<div>Value (" + type + ") : <input type='text' id='valuedata'/></div>");

	var data = null;

	footView.html("<button class='btn btn-info btn-sm' type='button' onclick='javascript:sendCoapWrite(\"" + endpoint + "\",\"" + uri + "\",\"" + dataid + "\",\"" + type + "\",\"" + data + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#dataModal'>Write</button>"
		+ "&nbsp&nbsp<button class='btn btn-secondary btn-sm' type='button' data-dismiss='modal'>닫기</button>");
}

function sendCoapWrite(endpoint, uri, dataid, type, data) {
	var param = {};
	param["endpoint"] = endpoint;
	param["uri"] = uri;
	param["type"] = type;

	var view = $("#page-top");
	data = view.find("#valuedata").val();
	param["data"] = data;

	LWM2M_PROXY.invokeOpenAPI("coapWrite", null, param, function (result, _head, _params) {
		console.log(result);

		var dataView = view.find("#" + dataid);
		dataView.empty();

		if (result == null || result == "") {
			dataView.html("err");
			return;
		}

		dataView.html(result);
	});
}

function sendCoapExec(endpoint, uri) {
	var param = {};
	param["endpoint"] = endpoint;
	param["uri"] = uri;

	var disableData = 10;

/* 	if (uri == '/1/0/4') {
		param["type"] = 'INTEGER';
		LWM2M_PROXY.invokeOpenAPI("coapRead", null, param, function (result, _head, _params) {
			console.log(result);
			disableData = result;
		});
	} */

	LWM2M_PROXY.invokeOpenAPI("coapExec", null, param, function (result, _head, _params) {
		console.log(result);

		if (result == 'ExecSuccess') {
			if (uri == '/1/0/4') {
				var view = $("#page-top");
				var listView = view.find("#objectdiv");
				listView.empty();
				listView.html("<div>" + endpoint + " Disable ...</div>");

				setTimeout(function () {
					getObjectModel(endpoint);
				}, disableData * 1000 + 1500);

			} else if (uri == '/1/0/8') {
				getObjectModel(endpoint);
				alert("Registration Update Trigger Success");
			} else {
				alert("구현체가 없습니다.");
			}
		} else {
			alert("Exec Fail...");
		}
	});
}

function sendCoapDisableRead(endpoint, uri, type) {
	var param = {};
	param["endpoint"] = endpoint;
	param["uri"] = uri;
	param["type"] = type;
	console.log(param);
	LWM2M_PROXY.invokeOpenAPI("coapRead", null, param, function (result, _head, _params) {
		console.log(result);
		return result;
	});
}
