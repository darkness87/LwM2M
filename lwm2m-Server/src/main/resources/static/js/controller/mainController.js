$(document).ready(function() {

	getUsage();
	setInterval(function() {
		getUsage();
	}, 3000);

});
