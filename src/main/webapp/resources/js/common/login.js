
$(document).ready(function() {
	// get application url
	var rootPath = getContextPath();

	// focus to username text field
	$("#j_username").focus();

	// Login button event process
	$("#btnLogin").bind("click", function() {
		var username = $("#j_username").val();
		var password = $("#j_password").val();
		var login_ret = login(username, password, rootPath + "/j_spring_security_check");
		// check returned value
		if (login_ret == 1) { // wrong user name or password
			// show error message
			jWarning(WRONG_USERNAME_OR_PASSWORD_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else if (login_ret == 2) { // error
			// show error message
			jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else if (login_ret == 3) { // access denied
			// show error message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else { // success
			// clear storage
			$.jStorage.flush();
			// create parameter object
			var parameterObj = {
				"url": rootPath + login_ret
			};
			// save to history
			savePageToHistory(parameterObj);
			// go to returned url
			window.location = rootPath + login_ret;
		}
	});
	
	// keypress Enter in login
	$(document).bind('keypress', function(e){
		  if(e.which === 13) { // return
		     $('#btnLogin').trigger('click');
		  }
	});
	
	/**
	 * execute login to system
	 * @param username
	 * @param password
	 * @param login_url authenticating url
	 * @return login result
	 *   0: success
	 *   1: wrong username or password
	 *   2: connection error
	 *   -1: known error
	 */
	function login(username, password, login_url) {
		var login_ret = -1;
		$.ajax({
			type: "POST",
			url: login_url,
			async: false,
			data: {"j_username": username, "j_password": password},
			success: function(data) {
				if (checkLoginSuccess(data) == 1) {
					// wrong username or password
					login_ret = 1;
				} else if (checkLoginSuccess(data) == 3) {
					// access denied
					login_ret = 3;
				} else {
					// wrong username or password
					login_ret = data;
				}
			},
			error: function(e) {
				// connection error
				login_ret = 2;
			},
			complete: function(jqXHR,textStatus) {
				
			}
		});

		return login_ret;
	}
	function checkLoginSuccess(object) {
		if (object.constructor === "string".constructor) {
			if (object.indexOf("Authentication Error") > -1) {
				return 1;
			} else if (object.indexOf("accessdenied") > -1) {
				return 3;
			}
		}
		return object;
	}
});