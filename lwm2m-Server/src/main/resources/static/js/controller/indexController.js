$(document).ready(function () {
  $('#dataTable').DataTable({
    "pageLength": 10,
    //    "displayLength": 10,
    "searching": false,
    "lengthChange": false,
    "bInfo": false,
    "ordering": false,
    //    "order": [ [ 0, "asc" ], [ 1, "desc"] ],
    "paging": true,
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

  // 디바이스 등록리스트
  getAllRegistrationsList();

  setInterval(function () {
    getAllRegistrationsList();
  }, 5000);

});
