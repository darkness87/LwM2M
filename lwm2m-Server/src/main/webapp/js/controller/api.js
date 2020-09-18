function getAllRegistrationsList() {
	var param = {};

	LWM2M_PROXY.invokeOpenAPI("getAllRegistrations", "json", param, function(result, _head, _params) {

		var view = $("#page-top");
		var listView = view.find("#lwm2mserverlist");
		listView.empty();

		console.log(result);

		if (result == null) {
			listView.html("<td colspan='8' style='text-align:center'>등록된 디바이스가 없습니다.</td>");
			return;
		}

		for (var i=0; i<result.length; i++) {
			var item = result[i];
			var tmp = [];

			tmp.push("<tr onclick='javascript:getObjectModel(\""+item.endpoint+"\");' style='cursor: pointer;'>");
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

function getObservationList(endpoint) {

	console.log(endpoint);

	var param = {};
	param["endpoint"] = endpoint;

	LWM2M_PROXY.invokeOpenAPI("getObservationList", "json", param, function(result, _head, _params) {

		var view = $("#page-top");

		var listView = view.find("#clickdata");
		listView.empty();

		console.log(result);

		if (result == null || result == "") {
			listView.html("등록된 정보가 없습니다.");
			return;
		}

		listView.html("<div>"+result+"</div>");

	});
}

function getObjectModel(endpoint) {

	console.log(endpoint);

	var param = {};
	param["endpoint"] = endpoint;

	LWM2M_PROXY.invokeOpenAPI("getObjectModel", "json", param, function(result, _head, _params) {

		var view = $("#page-top");

		var listView = view.find("#clicktabledata");
		listView.empty();

		console.log(result);

		if (result == null || result == "") {
			listView.html("<td colspan='11' style='text-align:center'>등록된 디바이스가 없습니다.</td>");
			return;
		}

 		for (var i=0; i<result.objectModels.length; i++) {
			var item = result.objectModels[i];
			var tmp = [];

			if(item.id==null || typeof item.id=="undefined"){
				continue;
			}

			var count = Object.keys(result.objectModels[i].resources).length;

			tmp.push("<tr>");
			tmp.push("<td rowspan='"+count+"'>" + item.id + "</td>");
			tmp.push("<td rowspan='"+count+"'>" + item.name + "</td>");
			tmp.push("<td rowspan='"+count+"'>" + item.omaObject + "</td>");
			tmp.push("<td rowspan='"+count+"'>" + item.urn + "</td>");
			tmp.push("<td rowspan='"+count+"'>" + item.version + "</td>");
			// item.lwM2mVersion, item.mandatory , item.multiple

			for(var r=0; r<count; r++){

				var res = result.objectModels[i].resources[r];

				console.log(i,r);

				if(typeof(res.id)=="undefined"){
					console.log("없음");
					continue;
				}
				if(Object.keys(res).length==0){
					console.log("없음");
					continue;
				}
				// TODO 수정중이라 주석 남김  // 데이터 index가 object 4번에 4번이 없는데 배열 순서가 5로 바로 넘어감 그래서 4번을 찾지못하고 오류

				if(r!=0){
					tmp.push("<tr>");
				}
				tmp.push("<td>" + res.id + "</td>");
				tmp.push("<td>" + res.name + "</td>");
				tmp.push("<td>" + res.operations + "</td>");
				tmp.push("<td>" + res.rangeEnumeration + "</td>");
				tmp.push("<td>" + res.type + "</td>");
				tmp.push("<td>" + res.units + "</td>");
				tmp.push("</tr>");

			}

			listView.append(tmp.join("\n"));
		}

	});
}