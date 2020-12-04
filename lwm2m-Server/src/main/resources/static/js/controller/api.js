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

			tmp.push("<tr onmouseover='this.style.background=\"#f8f9fc\"'; onmouseout='this.style.background=\"#fff\"'; style='cursor: pointer;'>");
			tmp.push("	<td onclick='javascript:getObjectModel(\"" + item.endpoint + "\");'>" + item.endpoint + "</td>");
			tmp.push("	<td onclick='javascript:getObjectModel(\"" + item.endpoint + "\");'>" + item.id + "</td>");
			tmp.push("	<td onclick='javascript:getObjectModel(\"" + item.endpoint + "\");'>" + item.address + "</td>");
			tmp.push("	<td onclick='javascript:getObjectModel(\"" + item.endpoint + "\");'>" + item.port + "</td>");
			tmp.push("	<td onclick='javascript:getObjectModel(\"" + item.endpoint + "\");'>" + item.lwM2mVersion + "</td>");
			tmp.push("	<td onclick='javascript:getObjectModel(\"" + item.endpoint + "\");'>" + item.bindingMode + "</td>");
			tmp.push("	<td onclick='javascript:getObjectModel(\"" + item.endpoint + "\");'>" + item.lifeTimeInSec + "</td>");
			tmp.push("	<td onclick='javascript:getObjectModel(\"" + item.endpoint + "\");'>" + item.registrationDate + "</td>");
			tmp.push("	<td onclick='javascript:getObjectModel(\"" + item.endpoint + "\");'>" + item.lastUpdate + "</td>");
			tmp.push("	<td>" + "<button class='btn btn-secondary btn-sm' type='button' onclick='javascript:unRegistration(\"" + item.endpoint + "\",\"" + item.id + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#'>X</button>" + "</td>");
			tmp.push("</tr>");

			listView.append(tmp.join("\n"));
		}

	});
}

function unRegistration(endpoint,id) {
	console.log(endpoint,"/",id)
//	alert("구현중.../"+endpoint+"/"+id);
	var param = {};
	param["endpoint"] = endpoint;
//	param["id"] = id;
	// TODO 삭제가 되어야 한다.
	LWM2M_PROXY.invokeOpenAPI("unRegistration", null, param, function (result, _head, _params) {
		console.log(result);
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

function getObservationList() {
	$('#dataTable').DataTable().destroy();
	var param = {};

	LWM2M_PROXY.invokeOpenAPI("getObservationList", "json", param, function (result, _head, _params) {
		console.log(result);
		var view = $("#page-top");
		var listView = view.find("#observelist");
		var obCountView = view.find("#observeCount");
		listView.empty();
		obCountView.empty();

		if (result == null || result == "") {
			listView.html("<td colspan='8' style='text-align:center'>Observe가 없습니다.</td>");
			obCountView.html("Observe가 없습니다.");
			return;
		}

		for (var i = 0; i < result.length; i++) {
			var item = result[i];
			var tmp = [];

			var uri = "/" + item.path.objectId + "/" + item.path.objectInstanceId;

			if (item.path.resource == true) {
				uri = uri + "/" + item.path.resourceId;
			}

			tmp.push("<tr onmouseover='this.style.background=\"#f8f9fc\"'; onmouseout='this.style.background=\"#fff\"';>");
			tmp.push("	<td>" + item.id + "</td>");
			tmp.push("	<td>" + item.registrationId + "</td>");
			tmp.push("	<td>" + item.endpoint + "</td>");
			tmp.push("	<td>" + item.address + "</td>");
			tmp.push("	<td>" + uri + "</td>");
			tmp.push("	<td>" + item.contentFormat.mediaType + "</td>");
			tmp.push("	<td>" + "Observe 동작" + "</td>");
			tmp.push("	<td>" + "<button class='btn btn-secondary btn-sm' type='button' onclick='javascript:cancelRegistrationIdObservation(\"" + item.registrationId + "\",\"" + uri + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#'>■</button>" + "</td>");
			tmp.push("</tr>");

			listView.append(tmp.join("\n"));
		}

		obCountView.html(result.length + " Count");
		
		$('#dataTable').DataTable({
			"pageLength" : 12,
			"bPaginate" : true,
			"bLengthChange": false,
			"searching" : false,
			"bInfo" : true,
			"ordering" : false,
			"paging" : true,
			"pagingType" : "simple_numbers",
			"columnDefs" : [ {
				'targets' : [ 2, 3 ],
				'orderable' : false
			} ],
			"dom" : "Bfrtip",
			"select" : {
				style : 'single'
			}

		});

	});
}

function cancelObservations() {
	var param = {};

	LWM2M_PROXY.invokeOpenAPI("cancelObservations", "json", param, function (result, _head, _params) {
		var view = $("#page-top");
		var obCountView = view.find("#observeCancel");
		obCountView.empty();
		if (result == null || result == "") {
			obCountView.html("0개 취소되었습니다.");
			setTimeout(function() {
				getObservationList();
				var countView = view.find("#observeCancel");
				countView.empty();
				countView.html("전체 취소하기");
			}, 5000);
			return;
		}
		obCountView.html(result + "개 취소되었습니다.");

		setTimeout(function() {
			getObservationList();
			var countView = view.find("#observeCancel");
			countView.empty();
			countView.html("전체 취소하기");
		}, 5000);

	});
}

function cancelResourceObservation(endpoint,uri) {
	var param = {};
	param["endpoint"] = endpoint;
	param["uri"] = uri;

	LWM2M_PROXY.invokeOpenAPI("cancelResourceObservation", "json", param, function (result, _head, _params) {
		console.log(result);
	});
}

function cancelRegistrationIdObservation(id,uri) {
	var param = {};
	param["id"] = id;
	param["uri"] = uri;

	LWM2M_PROXY.invokeOpenAPI("cancelRegistrationIdObservation", "json", param, function (result, _head, _params) {
		console.log(result);
		
		setTimeout(function() {
			getObservationList();
			var countView = view.find("#observeCancel");
			countView.empty();
			countView.html("전체 취소하기");
		}, 5000);
	});
}

function getObjectModel(endpoint) {
	var param = {};
	param["endpoint"] = endpoint;

	LWM2M_PROXY.invokeOpenAPI("getObjectModel", "json", param, function (result, _head, _params) {
		console.log(result);
		var view = $("#page-top");
		var listView = view.find("#objectdiv");
		listView.empty();

		if (result == null || result == "") {
			listView.html("<div class='card shadow mb-4'><div class='card-header py-3'><h6 class='m-0 font-weight-bold text-primary'>등록된 디바이스가 없습니다.</h6></div></div>");
			return;
		}

		result.objectModels.sort(function (a, b) {
			return a.id < b.id ? -1 : a.id > b.id ? 1 : 0;
		});

		for (var i = 0; i < result.objectModels.length; i++) {
			var item = result.objectModels[i];
			var tmp = [];
			var observeUri = "/" + item.id + "/0";

			tmp.push("<div class='card shadow mb-4'>");
			tmp.push("	<div class='card-header py-3'>");
			tmp.push("		<h6 class='m-0 font-weight-bold text-primary'>" + item.id + " : " + item.name + "&nbsp&nbsp&nbsp<button class='btn btn-primary' type='button' onclick='javascript:sendCoapObserve(\"" + endpoint + "\",\"" + observeUri + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#'>Observe (" + observeUri + ") ▶</button>"
				+ "&nbsp&nbsp&nbsp<button class='btn btn-secondary' type='button' onclick='javascript:cancelResourceObservation(\"" + endpoint + "\",\"" + observeUri + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#'>■</button>"
				+ "<button style='float:right; outline: 0; border:0; background-color:#F8F9FC' href='#card" + item.id + "' data-toggle='collapse' role='button' aria-expanded='false' aria-controls='card" + item.id + " class='collapsed' '><h4>▼</h4></button></h6>");
			tmp.push("	</div>");
			tmp.push("<div class='card-body collapse' id='card" + item.id + "'>");
			tmp.push("  <div class='table-responsive'>");
			tmp.push("    <table id='dataTable" + item.id + "' width='100%' cellspacing='0'>");
			tmp.push("      <thead>");
			tmp.push("        <tr style='color: #000;'>");
			tmp.push("          <th>ID</th>");
			tmp.push("          <th>Resource Name</th>");
			tmp.push("          <th>Operations</th>");
			tmp.push("          <th>RangeEnumeration</th>");
			tmp.push("          <th>Type</th>");
			tmp.push("          <th>Units</th>");
			tmp.push("          <th width='20%'>Data</th>");
			tmp.push("        </tr>");
			tmp.push("      </thead>");

			for (key in result.objectModels[i].resources) {
				var res = result.objectModels[i].resources[key];
				var uri = "/" + item.id + "/0" + "/" + res.id;
				var dataid = "tid" + item.id + "0" + res.id;

				tmp.push("      <tbody>");
				tmp.push("			<tr height='40'>");
				tmp.push("				<td>" + uri + "</td>");
				tmp.push("				<td title='" + res.description + "'>" + res.name + "</td>");

				if (res.operations == "R") {
					tmp.push("				<td>" + "<button class='btn btn-secondary btn-sm' type='button' onclick='javascript:sendCoapObserve(\"" + endpoint + "\",\"" + uri + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#'>Observe</button>"
						+ "&nbsp&nbsp<button class='btn btn-primary btn-sm' type='button' onclick='javascript:sendCoapRead(\"" + endpoint + "\",\"" + uri + "\",\"" + dataid + "\",\"" + res.type + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#'>Read</button>" + "</td>");
				} else if (res.operations == "RW") {
					tmp.push("				<td>" + "<button class='btn btn-secondary btn-sm' type='button' onclick='javascript:sendCoapObserve(\"" + endpoint + "\",\"" + uri + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#'>Observe</button>"
						+ "&nbsp&nbsp<button class='btn btn-primary btn-sm' type='button' onclick='javascript:sendCoapRead(\"" + endpoint + "\",\"" + uri + "\",\"" + dataid + "\",\"" + res.type + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#'>Read</button>"
						+ "&nbsp&nbsp<button class='btn btn-info btn-sm' type='button' onclick='javascript:viewWrite(\"" + endpoint + "\",\"" + uri + "\",\"" + dataid + "\",\"" + res.type + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#dataModal'>Write</button>" + "</td>");
				} else if (res.operations == "W") {
					tmp.push("				<td>" + "<button class='btn btn-info btn-sm' type='button' onclick='javascript:viewWrite(\"" + endpoint + "\",\"" + uri + "\",\"" + dataid + "\",\"" + res.type + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#dataModal'>Write</button>" + "</td>");
				} else if (res.operations == "E") {
					tmp.push("				<td>" + "<button class='btn btn-success btn-sm' type='button' onclick='javascript:sendCoapExec(\"" + endpoint + "\",\"" + uri + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#'>Exec</button>"
						/*
						 * + "&nbsp&nbsp<button class='btn btn-secondary
						 * btn-sm' type='button' style='cursor: pointer;'
						 * data-toggle='modal' data-target='#'>※</button>"
						 */
						+ "</td>");
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
	var view = $("#page-top");
	param["contentType"] = view.find("#typeData").val();
	param["timeout"] = view.find("#timeOut").val();
	LWM2M_PROXY.invokeOpenAPI("coapObserve", null, param, function (result, _head, _params) {
		var data = eval(result);
		console.log(data);
		var dataView = null;
		for (var i = 0; i < data.length; i++) {
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
	var view = $("#page-top");
	param["contentType"] = view.find("#typeData").val();
	param["timeout"] = view.find("#timeOut").val();

	LWM2M_PROXY.invokeOpenAPI("coapRead", null, param, function (result, _head, _params) {
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

function viewWrite(endpoint, uri, dataid, type) {
	var view = $("#page-top");
	var titleView = view.find("#titledata");
	var writeView = view.find("#detaildata");
	var footView = view.find("#footdata");
	titleView.empty();
	writeView.empty();
	footView.empty();

	titleView.html(endpoint + " : " + uri);
	var data = null;
	
	if(type=="OPAQUE"&&uri=="/5/0/0"){
		writeView.html("<div>Value (" + type + ") : <input id='valuedata' type='file' file-model='resource.fileValue' ng-disabled='resource.stringValue'></div>");
		footView.html("<button class='btn btn-info btn-sm' type='button' onclick='javascript:sendCoapWriteFile(\"" + endpoint + "\",\"" + uri + "\",\"" + dataid + "\",\"" + type + "\",\"" + data + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#dataModal'>Write</button>"
				+ "&nbsp&nbsp<button class='btn btn-secondary btn-sm' type='button' data-dismiss='modal'>닫기</button>");
	}else if(type=="BOOLEAN"){
		writeView.html("<div>Value (" + type + ") : <select id='valuedata'><option value='TRUE'>TRUE</option><option value='FALSE'>FALSE</option></select></div>");
		footView.html("<button class='btn btn-info btn-sm' type='button' onclick='javascript:sendCoapWrite(\"" + endpoint + "\",\"" + uri + "\",\"" + dataid + "\",\"" + type + "\",\"" + data + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#dataModal'>Write</button>"
				+ "&nbsp&nbsp<button class='btn btn-secondary btn-sm' type='button' data-dismiss='modal'>닫기</button>");
	}else if(type=="TIME"){
		writeView.html("<div>Value (" + type + ") : <input type='date' id='dateinfo'><input type='time' id='timeinfo'></div>");
		footView.html("<button class='btn btn-info btn-sm' type='button' onclick='javascript:sendCoapWrite(\"" + endpoint + "\",\"" + uri + "\",\"" + dataid + "\",\"" + type + "\",\"" + data + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#dataModal'>Write</button>"
				+ "&nbsp&nbsp<button class='btn btn-secondary btn-sm' type='button' data-dismiss='modal'>닫기</button>");
	}else{
		writeView.html("<div>Value (" + type + ") : <input type='text' id='valuedata'/></div>");
		footView.html("<button class='btn btn-info btn-sm' type='button' onclick='javascript:sendCoapWrite(\"" + endpoint + "\",\"" + uri + "\",\"" + dataid + "\",\"" + type + "\",\"" + data + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#dataModal'>Write</button>"
				+ "&nbsp&nbsp<button class='btn btn-secondary btn-sm' type='button' data-dismiss='modal'>닫기</button>");
	}
}

// TODO
function fileinfo(){
	var input = document.getElementById('file_path').value;
	var data = document.selection.createRange().text.toString()
}

function sendCoapWrite(endpoint, uri, dataid, type, data) {
	var param = {};
	param["endpoint"] = endpoint;
	param["uri"] = uri;
	param["type"] = type;

	var view = $("#page-top");

	if(type=="TIME"){
		var date = view.find("#dateinfo").val();
		var time = view.find("#timeinfo").val();
		//alert(date+" "+time+":00")
		data = date+" "+time+":00";
	}else{
		data = view.find("#valuedata").val();
	}
	
	param["data"] = data;
	param["contentType"] = view.find("#typeData").val();
	param["timeout"] = view.find("#timeOut").val();

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

function sendCoapWriteFile(endpoint, uri, dataid, type, data) {
	var param = {};
	param["endpoint"] = endpoint;
	param["uri"] = uri;
	param["type"] = type;

	var view = $("#page-top");
	data = view.find("#valuedata").val();
	param["data"] = data;
	param["contentType"] = view.find("#typeData").val();
	param["timeout"] = view.find("#timeOut").val();
	
	fileCheck();

	LWM2M_PROXY.invokeOpenAPI("coapWriteFile", null, param, function (result, _head, _params) {
		console.log(result);
		var dataView = view.find("#" + dataid);
		dataView.empty();
		dataView.html(result);
	});
}

function sendCoapExec(endpoint, uri) {
	var param = {};
	param["endpoint"] = endpoint;
	param["uri"] = uri;
	var disableData = 10;
	var view = $("#page-top");
	param["contentType"] = view.find("#typeData").val();
	param["timeout"] = view.find("#timeOut").val();
	param["type"] = null;
	if (uri == '/1/0/4') {
		param["type"] = "INTEGER";
	}

	LWM2M_PROXY.invokeOpenAPI("coapExec", null, param, function (result, _head, _params) {
		console.log(result);

		if (result == 'ExecSuccess') {
			if (uri == '/1/0/8') {
				getObjectModel(endpoint);
				alert("Registration Update Trigger Success");
			} else {
				alert("구현체가 없습니다.");
			}
		} else if (result != 'ExecSuccess' && result != null) {
			if (uri == '/1/0/4') {
				var view = $("#page-top");
				var listView = view.find("#objectdiv");
				listView.empty();
				listView.html("<div>" + endpoint + " Disable ...</div>");
				disableData = result;
				setTimeout(function () {
					getObjectModel(endpoint);
				}, disableData * 1000 + 1500);
			} else {
				alert("데이터를 알 수 없습니다.");
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

function getRedisKeyList() {
	var view = $("#page-top");
	var param = {};
	param["typeData"] = view.find("#typeData").val();
	param["dateData"] = view.find("#dateData").val();

	LWM2M_PROXY.invokeOpenAPI("getRedisKeyList", "json", param, function (result, _head, _params) {
		
		var listView = view.find("#keylist");
		listView.empty();

		if (result == null || result == "") {
			listView.html("<tr><td colspan='3' style='text-align:center'>KEY 데이터가 없습니다.</td></tr>");
			return;
		}

		for (key in result) {
			var tmp = [];
			var no = key;
			var strArray = result[key].split('|');
			var keyData = strArray[0];
			var keyType = strArray[1];
			tmp.push("<tr onmouseover='this.style.background=\"#f8f9fc\"'; onmouseout='this.style.background=\"#fff\"'; onclick='javascript:getRedisKeyData(\"" + keyData + "\",\"" + keyType + "\");' style='cursor: pointer;' height='40'>");
			tmp.push("	<td>" + no + "</td>");
			tmp.push("	<td>" + keyData + "</td>");
			tmp.push("	<td>" + keyType + "</td>");
			tmp.push("</tr>");

			listView.append(tmp.join("\n"));
		}

	});
}

function getRedisKeyData(key,keyType) {
	var param = {};
	param["key"] = key;
	param["keyType"] = keyType;
	LWM2M_PROXY.invokeOpenAPI("getRedisKeyData", "json", param, function (result, _head, _params) {
		console.log(result);
		var view = $("#page-top");
		var dataView = view.find("#keydata");
		dataView.empty();

		if (result == null || result == "") {
			dataView.html("Data가 없습니다.");
			return;
		}

		dataView.html("KEY = " + key + "<br><br> DATA = <br>" + JSON.stringify(result, null, 2));
	});
}

// TODO
function sseTest(uri) {
	console.log("SSE TEST");
	var param = {};
	param["uri"] = uri;
	LWM2M_PROXY.invokeOpenAPI("sender", null, param, function (result, _head, _params) {
		console.log(result);
	});
}

function getUsage() {
	var param = {};
	LWM2M_PROXY.invokeOpenAPI("getUsage", "json", param, function (result, _head, _params) {
		myLineChart.data.labels.unshift(result.date);
		myLineChart.data.datasets[0].data.unshift(result.osCpu);
		myLineChart.data.datasets[1].data.unshift(result.osMemory);

		if (myLineChart.data.labels.length > 10 && myLineChart.data.datasets[0].data.length > 10) {
			myLineChart.data.labels.splice(10, myLineChart.data.labels.length - 10);
			myLineChart.data.datasets[0].data.splice(10, myLineChart.data.datasets[0].data.length - 10);
			myLineChart.data.datasets[0].data.splice(10, myLineChart.data.datasets[1].data.length - 10);
		}

		myLineChart.update();

		var view = $("#page-top");
		var listView = view.find("#usageInfo");
		listView.empty();
		var tmp = [];

        tmp.push("<h4 class='small font-weight-bold'>OS CPU 사용률 <span class='float-right'>"+result.osCpu+"%</span></h4>");
        tmp.push("<div class='progress mb-4'>");
        tmp.push("  <div class='progress-bar bg-danger' role='progressbar' style='width: "+result.osCpu+"%' aria-valuenow='"+result.osCpu+"' aria-valuemin='0' aria-valuemax='100'></div>");
        tmp.push("</div>");
        tmp.push("<h4 class='small font-weight-bold'>OS 메모리 사용률 <span class='float-right'>"+result.osMemory+"%</span></h4>");
        tmp.push("<div class='progress mb-4'>");
        tmp.push("  <div class='progress-bar bg-primary' role='progressbar' style='width: "+result.osMemory+"%' aria-valuenow='"+result.osMemory+"' aria-valuemin='0' aria-valuemax='100'></div>");
        tmp.push("</div>");
        tmp.push("<div class='row no-gutters align-items-center'>");
        tmp.push("  <div class='col mr-2'>");
        tmp.push("    <div class='text-xs font-weight-bold text-secondary text-uppercase mb-1'>jvmUsed</div>");
        tmp.push("    <div class='h5 mb-0 font-weight-bold text-gray-800'>"+result.jvmUsed+"</div>");
        tmp.push("  </div>");
        tmp.push("  <div class='col mr-2'>");
        tmp.push("    <div class='text-xs font-weight-bold text-secondary text-uppercase mb-1'>jvmFree</div>");
        tmp.push("    <div class='h5 mb-0 font-weight-bold text-gray-800'>"+result.jvmFree+"</div>");
        tmp.push("  </div>");
        tmp.push("  <div class='col mr-2'>");
        tmp.push("    <div class='text-xs font-weight-bold text-secondary text-uppercase mb-1'>jvmTotal</div>");
        tmp.push("    <div class='h5 mb-0 font-weight-bold text-gray-800'>"+result.jvmTotal+"</div>");
        tmp.push("  </div>");
        tmp.push("  <div class='col mr-2'>");
        tmp.push("    <div class='text-xs font-weight-bold text-secondary text-uppercase mb-1'>jvmMax</div>");
        tmp.push("    <div class='h5 mb-0 font-weight-bold text-gray-800'>"+result.jvmMax+"</div>");
        tmp.push("  </div>");
        tmp.push("  <div class='col-auto'>");
        tmp.push("    <i class='fas fa-clipboard-list fa-2x text-gray-300'></i>");
        tmp.push("  </div>");
        tmp.push("</div>");

        listView.append(tmp.join("\n"));
        
		var dataView = view.find("#regInfo");
		dataView.empty();
		var tmpl = [];

		tmpl.push("<div class='row no-gutters align-items-center'>");
		tmpl.push("  <div class='col mr-2'>");
		tmpl.push("    <div class='text-xs font-weight-bold text-secondary text-uppercase mb-1'>등록 장비수</div>");
		tmpl.push("    <div class='h5 mb-0 font-weight-bold text-gray-800'>"+result.registrationCount+"개</div>");
		tmpl.push("  </div>");
		tmpl.push("  <div class='col mr-2'>");
		tmpl.push("    <div class='text-xs font-weight-bold text-secondary text-uppercase mb-1'>Observe 동작수</div>");
		tmpl.push("    <div class='h5 mb-0 font-weight-bold text-gray-800'>"+result.observeCount+"개</div>");
		tmpl.push("  </div>");
		tmpl.push("  <div class='col-auto'>");
		tmpl.push("    <i class='fas fa-clipboard-list fa-2x text-gray-300'></i>");
		tmpl.push("  </div>");
		tmpl.push("</div>");

		dataView.append(tmpl.join("\n"));

	});
}

function getProperty(fileName) {
	console.log(fileName);
	var param = {};
	param["fileName"] = fileName+".properties";
	LWM2M_PROXY.invokeOpenAPI("getProperty", "json", param, function (result, _head, _params) {
		console.log(result);
		var view = $("#page-top");
		var listView = view.find("#"+fileName);
		listView.empty();

		if (result == null) {
			listView.html("<td colspan='2' style='text-align:center'>등록된 Properties 정보가 없습니다.</td>");
			return;
		}

		for (var i = 0; i < result.length; i++) {
			var item = result[i];
			var tmp = [];

			tmp.push("<tr>");
			tmp.push("	<td>" + item.key + "</td>");
			tmp.push("	<td><input type='text' value=\"" + item.value + "\"/></td>");
			tmp.push("</tr>");

			listView.append(tmp.join("\n"));
		}
		
		var buttonView = view.find("#"+fileName+"Button");
		buttonView.empty();
		buttonView.append("<button class='btn btn-secondary btn-sm' type='button' onclick='javascript:setProperty(\"" + fileName + "\");' style='cursor: pointer;' data-toggle='modal' data-target='#'>수정</button>")

	});
}

function setProperty(fileName) {
//	console.log("setProperty");
	var param = {};
	
	// 화면에서 key, value 값 가져오기
//	param["setData"] = [{key: "redis.ipAddr", value: "127.0.0.1"},{key: "redis.ipAddr", value: "127.0.0.1"}];
	
	var i, tr, temp;
	tr = new Array();
	temp = document.getElementById(fileName).getElementsByTagName('tr');
//	console.log(temp);
	for (i in temp) {
		if(i=="length"||i=="item"||i=="namedItem"){
			continue;
		}
		console.log("index : "+i);
		var data = new Object();
		
		if(temp[i].cells[1].getElementsByTagName('input')[0].value==null || temp[i].cells[1].getElementsByTagName('input')[0].value==""){
			data.key = temp[i].cells[0].innerText;
			data.value = "";
		}else{
			data.key = temp[i].cells[0].innerText;
			data.value = temp[i].cells[1].getElementsByTagName('input')[0].value;
		}
		
	    tr.push(data);
	}
	console.log(tr);
	
	param["fileName"] = fileName;
	param["setData"] = JSON.stringify(tr);
	
	LWM2M_PROXY.invokeOpenAPI("setProperty", "json", param, function (result, _head, _params) {
		console.log(result);
		if(result==0){
			alert("수정되었습니다.");
		}else{
			alert("실패하였습니다. 확인바랍니다.");
		}
	});
}

// TODO
function fileCheck() {
	// input file 태그.
	var file = document.getElementById('valuedata');
	// 파일 경로.
	var filePath = file.value;
	// 전체경로를 \ 나눔.
	var filePathSplit = filePath.split('\\');
	// 전체경로를 \로 나눈 길이.
	var filePathLength = filePathSplit.length;
	// 마지막 경로를 .으로 나눔.
	var fileNameSplit = filePathSplit[filePathLength - 1].split('.');
	// 파일명 : .으로 나눈 앞부분
	var fileName = fileNameSplit[0];
	// 파일 확장자 : .으로 나눈 뒷부분
	var fileExt = fileNameSplit[1];
	// 파일 크기
	var fileSize = file.files[0].size;

	console.log('파일 : ' + file);
	console.log('파일 경로 : ' + filePath);
	console.log('파일명 : ' + fileName);
	console.log('파일 확장자 : ' + fileExt);
	console.log('파일 크기 : ' + fileSize);
}

function getExternalIP() {
	var param = {};
	LWM2M_PROXY.invokeOpenAPI("getExternalIP", null, param, function (result, _head, _params) {
		console.log(result);
		var view = $("#page-top");
		var dataView = view.find("#serverInfo");
		dataView.empty();
		
		var tmpl = [];

		tmpl.push("<div class='row no-gutters align-items-center'>");
		tmpl.push("  <div class='col mr-2'>");
		tmpl.push("    <div class='text-xs font-weight-bold text-secondary text-uppercase mb-1'>서버 ExternalIP</div>");
		tmpl.push("    <div class='h5 mb-0 font-weight-bold text-gray-800'>"+result+"</div>");
		tmpl.push("  </div>");
		tmpl.push("</div>");

		dataView.append(tmpl.join("\n"));
	});
	
}

function getExternalIPLocation() {
	var param = {};
	LWM2M_PROXY.invokeOpenAPI("getExternalIPLocation", "json", param, function (result, _head, _params) {
		var data = eval(result);
		console.log(data);

		var view = $("#page-top");
		var dataView = view.find("#locationInfo");
		dataView.empty();
		
		var tmpl = [];

		tmpl.push("<div class='row no-gutters align-items-center'>");
		tmpl.push("  <div class='col mr-2'>");
		tmpl.push("    <div class='text-xs font-weight-bold text-secondary text-uppercase mb-1'>위도</div>");
		tmpl.push("    <div class='h5 mb-0 font-weight-bold text-gray-800'>"+data.lat+"</div>");
		tmpl.push("  </div>");
		tmpl.push("  <div class='col mr-2'>");
		tmpl.push("    <div class='text-xs font-weight-bold text-secondary text-uppercase mb-1'>경도</div>");
		tmpl.push("    <div class='h5 mb-0 font-weight-bold text-gray-800'>"+data.lon+"</div>");
		tmpl.push("  </div>");
		tmpl.push("</div>");

		dataView.append(tmpl.join("\n"));
	});
	
}

// TODO 지도 API 연동하기
function getLocation() {
	var param = {};
	LWM2M_PROXY.invokeOpenAPI("getLocation", "json", param, function (result, _head, _params) {
		console.log(result);

		var view = $("#page-top");
		var dataView = view.find("#locationMap");
		dataView.empty();
		var tmp = [];
		
		for (var i = 0; i < result.length; i++) {
			var item = result[i];

			tmp.push("<div>");
			tmp.push(item.endpoint+"/"+item.registrationId+"/"+item.address+"/"+item.country+"/"+item.regionName+"/"+item.city+"/위도:"+item.lat+"/경도:"+item.lon+"/ExternalIP:"+item.query);
			tmp.push("</div>");

		}

		dataView.append(tmp.join("\n"));
	});
	
}

function getSearchData(){
	var param = {};
	var view = $("#page-top");
	param["typeData"] = view.find("#typeData").val();

	if(view.find("#dateData").val()==null||view.find("#dateData").val()==""){
		alert("날짜를 선택해주세요.");
		myLineChart.data.labels=[];
		myLineChart.data.datasets[0].data=[];
		myLineChart.update();
		return;
	}else{
		param["dateData"] = view.find("#dateData").val();
	}
	myLineChart.data.labels=[];
	myLineChart.data.datasets[0].data=[];
	myLineChart.update();

	LWM2M_PROXY.invokeOpenAPI("getSearchData", "json", param, function (result, _head, _params) {
		/*result.sort(function (a, b) {
			return a.id < b.id ? -1 : a.id > b.id ? 1 : 0;
		});*/
		console.log(result);

		for (var i = 0; i < result.length; i++) {
			var item = result[i];
			myLineChart.data.labels.push(item.label);
			myLineChart.data.datasets[0].data.push(item.value);
			myLineChart.update();
		}

	});
}

function getFormatDate(date) {
	var year = date.getFullYear();
	var month = (1 + date.getMonth());
	month = month >= 10 ? month : '0' + month;
	var day = date.getDate();
	day = day >= 10 ? day : '0' + day;
	return year + '-' + month + '-' + day;
}