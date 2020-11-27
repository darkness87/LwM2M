$(document).ready(function () {
	$('#dataTable').DataTable().destroy();
	$('#dataTable').DataTable({
		"searching" : false,
		"lengthChange" : false,
		"bInfo" : false,
		"ordering" : false,
		"paging" : false,
		"columnDefs" : [ {
			'targets' : [ 2, 3 ],
			'orderable' : false
		} ],
		"dom" : "Bfrtip",
		"select" : {
			style : 'single'
		}
	});
	
	// 디바이스 등록리스트
	getAllRegistrationsList();

	setInterval(function() {
		getAllRegistrationsList();
	}, 5000);

	//  $("#valuedata").change(function() { $("#file_path").val(this.files && this.files.length ? this.files[0].name : this.value.replace(/^C:\\fakepath\\/i, '')); })

});
