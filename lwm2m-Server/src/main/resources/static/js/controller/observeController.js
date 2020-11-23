$(document).ready(function() {
	$('#dataTable').DataTable({
		"pageLength" : 12,
		"displayLength" : 10,
		"searching" : false,
		"lengthChange" : false,
		"bInfo" : false,
		"ordering" : false,
		//    "order": [ [ 2, "asc" ], [ 4, "asc"] ],
		"paging" : true,
		"pagingType" : "simple_numbers",
		"scrollCollapse" : true,
		"columnDefs" : [ {
			'targets' : [ 2, 3 ],
			'orderable' : false
		} ],
		"dom" : "Bfrtip",
		"select" : {
			style : 'single'
		}
	});

	// Observe 리스트
	getObservationList();

	setInterval(function() {
		getObservationList();
		var view = $("#page-top");
		var obCountView = view.find("#observeCancel");
		obCountView.empty();
		obCountView.html("전체 취소하기");
	}, 5000);

});
