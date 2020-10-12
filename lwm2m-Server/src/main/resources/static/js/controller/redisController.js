$(document).ready(function () {
  $('#keyTable').DataTable({
    "pageLength": 10,
    "displayLength": 10,
    "searching": false,
    "lengthChange": false,
    "bInfo": false,
    "ordering": false,
    "paging": true,
    "columnDefs": [
      {
        'targets': [0],
        'orderable': false
      }
    ],
    "dom": "Bfrtip",
    "select": {
      style: 'single'
    }
  });

  // Redis Key 리스트
  getRedisKeyList();

});
