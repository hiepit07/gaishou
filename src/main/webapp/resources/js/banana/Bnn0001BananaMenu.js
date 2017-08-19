/**
 * @author Hieu Dao
 */

$(document).ready(function() {

	// Application path
	var rootPath = getContextPath();

	// Go to Search User page
	$('#btnUserScreen').click(function() {
		if (!checkAuthority("0003")) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// create parameter object
			var parameterObj = {
				"url": rootPath + "/0003/"
			};
			// save to history
			savePageToHistory(parameterObj);
			window.location = rootPath + "/0003/";
		}
	});

	// Go to Search Farm page
	$('#btnFarmScreen').click(function() {
		if (!checkAuthority("0005")) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// create parameter object
			var parameterObj = {
				"url": rootPath + "/0005/"
			};
			// save to history
			savePageToHistory(parameterObj);
			window.location = rootPath + "/0005/";
		}
	});


	// Go to Search Area page
	$('#btnAreaScreen').click(function() {
		if (!checkAuthority("0007")) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// create parameter object
			var parameterObj = {
				"url": rootPath + "/0007/"
			};
			// save to history
			savePageToHistory(parameterObj);
			window.location = rootPath + "/0007/";
		}
	});


	// Go to Search Block page
	$('#btnBlockScreen').click(function() {
		if (!checkAuthority("0009")) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// create parameter object
			var parameterObj = {
				"url": rootPath + "/0009/"
			};
			// save to history
			savePageToHistory(parameterObj);
			window.location = rootPath + "/0009/";
		}
	});


	// Go to Search Line page
	$('#btnProcessScreen').click(function() {
		if (!checkAuthority("0013")) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// create parameter object
			var parameterObj = {
				"url": rootPath + "/0013/"
			};
			// save to history
			savePageToHistory(parameterObj);
			window.location = rootPath + "/0013/";
		}
	});

	// Go to Search Banana page
	$('#btnTaskScreen').click(function() {
		if (!checkAuthority("0089")) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// create parameter object
			var parameterObj = {
				"url": rootPath + "/0089/"
			};
			// save to history
			savePageToHistory(parameterObj);
			window.location = rootPath + "/0089/";
		}
	});

	// Go to Search Banana Kind page
	$('#btnBananaKindScreen').click(function() {
		if (!checkAuthority("0017")) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// create parameter object
			var parameterObj = {
				"url": rootPath + "/0017/"
			};
			// save to history
			savePageToHistory(parameterObj);
			window.location = rootPath + "/0017/";
		}
	});


	// Go to Search Manage page
	$('#btnManageScreen').click(function() {
		if (!checkAuthority("0087")) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// create parameter object
			var parameterObj = {
				"url": rootPath + "/0087/"
			};
			// save to history
			savePageToHistory(parameterObj);
			window.location = rootPath + "/0087/";
		}
	});

	// Go to Search Cultivation page
	$('#btnCultivationScreen').click(function() {
		if (!checkAuthority("0091")) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// create parameter object
			var parameterObj = {
				"url": rootPath + "/0091/"
			};
			// save to history
			savePageToHistory(parameterObj);
			window.location = rootPath + "/0091/";
		}
	});


	// Go to Search Authority Kind page
	$('#btnAuthorityScreen').click(function() {
		if (!checkAuthority("0095")) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// create parameter object
			var parameterObj = {
				"url": rootPath + "/0095/"
			};
			// save to history
			savePageToHistory(parameterObj);
			window.location = rootPath + "/0095/";
		}
	});
})