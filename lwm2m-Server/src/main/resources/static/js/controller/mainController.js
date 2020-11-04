$(document).ready(function () {

	getUsage();
	setInterval(function () {
		getUsage();
  }, 5000);

});
