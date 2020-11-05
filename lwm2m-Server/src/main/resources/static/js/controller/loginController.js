$(document).ready(function () {
	
});

function login(){
	var view = $("#page-top");
	var id = view.find("#inputEmail").val();
	var pw = view.find("#inputPassword").val();

	console.log("login Success");
	console.log(id+"/"+pw);
}