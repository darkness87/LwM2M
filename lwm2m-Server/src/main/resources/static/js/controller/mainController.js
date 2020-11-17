$(document).ready(function() {

	getUsage();

	getExternalIP();

	getExternalIPLocation();

	setInterval(function() {
		getUsage();
	}, 3000);

});
