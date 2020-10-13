$(document).ready(function () {
  $('#dataTable').DataTable({
    "pageLength": 10,
    //    "displayLength": 10,
    "searching": false,
    "lengthChange": false,
    "bInfo": false,
    "ordering": false,
    //    "order": [ [ 0, "asc" ], [ 1, "desc"] ],
    "paging": false,
    "columnDefs": [
      {
        'targets': [2, 3],
        'orderable': false
      }
    ],
    /*     "buttons": [
          {
            extend: "excel",
            text: "Excel",
            filename: "LwM2M_Registration_List",
            title:"LwM2M",
            exportOptions: { orthogonal: "export" }
          }
        ], */
    "dom": "Bfrtip",
    "select": {
      style: 'single'
    }
  });

  // Observe 리스트
  getObservationList();

  setInterval(function () {
    getObservationList();
    var view = $("#page-top");
    var obCountView = view.find("#observeCancel");
    obCountView.empty();
    obCountView.html("전체 취소하기");
  }, 5000);

});
