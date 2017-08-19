/**
 * @author Khoa Le
 * Change current user's password
 */

$(document).ready(function() {
	// display current date
	$("#txtCurrentDate").text(getCurrentDate() + " (" + DAY_OF_WEEK + ") ");

	// Change button event process
	$("#btnChange").bind("click", function() {
		var dataObject = null;
		// check for user input
		if (checkInputBlankFields() != "") {
			// blank field(s)
			// display message
			jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else if (!comparePassword()) {
			// new password and new password confirm do not match
			// display message
			jWarning(VALIDATE_PASSWORD_NOT_MATCH_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// update new password to DB
			// get password input data
			dataObject = createPasswordDataObject();
			// make Ajax call to server to get data
			$.ajax({
				type: "POST",
				url: "updateData",
				contentType: "application/json",
				data: JSON.stringify(dataObject),
				async: false,
				success: function(returnedJsonData) {
					if (checkSessionTimeout(returnedJsonData) == 1) return;
					if (returnedJsonData != "") {
						// check returned value from server
						if (returnedJsonData == VALIDATE_BLANK_FIELDS) {
							// blank field(s)
							// display message
							jWarning(VALIDATE_BLANK_FIELDS_MESSAGE_SERVER, DIALOG_TITLE, DIALOG_OK_BUTTON);
						} else if (returnedJsonData == VALIDATE_PASSWORD_NOT_MATCH) {
							// new password and new password confirm do not match
							// display message
							jWarning(VALIDATE_PASSWORD_NOT_MATCH_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
						} else if (returnedJsonData == VALIDATE_PASSWORD_NOT_CORRECT) {
							// old password is not correct
							// display message
							jWarning(VALIDATE_PASSWORD_NOT_CORRECT_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
						} else if (returnedJsonData == UPDATE_RESULT_SUCCESSFUL) {
							// changed password successfully
							// display message
							jInfo(UPDATE_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
						} else if (returnedJsonData == UPDATE_RESULT_FAILED_CULTIVATION_PASSWORD) {
							// failed
							// display message
							jWarning(UPDATE_RESULT_SQL_FAILED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
						} else if (returnedJsonData == UPDATE_RESULT_FAILED_EXCEPTION) {
							// failed
							// display message
							jWarning(UPDATE_RESULT_FAILED_MESSAGE.replace('$1', UPDATE_RESULT_FAILED_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
						}
					}
				},
				error: function(e) {
					// display error message
					jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
				}
			});
		}
	});

	// Compare new password and new password confirm process
	function comparePassword() {
		var newPassword = $("#txtNewPassword").val();
		var newPasswordConfirm = $("#txtNewPasswordConfirm").val();
		// compare
		if (newPassword == newPasswordConfirm) {
			return true;
		} else {
			return false;
		}
	}

	// Password data object creation process
	function createPasswordDataObject() {
		return {
			oldPassword: $("#txtOldPassword").val(),
			newPassword: $("#txtNewPassword").val(),
			newPasswordConfirm: $("#txtNewPasswordConfirm").val(),
			deleteFlag: DELETE_FLAG_OFF
		};
	}

	// Check input: blank fields
	function checkInputBlankFields() {
		if ($("#txtOldPassword").val() == "" ){
			return oldPasswordBlank;
		} else if ($("#txtNewPassword").val() == ""){
			return newPasswordBlank;
		} else if ($("#txtNewPasswordConfirm").val() == "") {
			return newPasswordConfirmBlank;
		} else {
			return "";
		}
	}

	// Back button click event process
	$("#btnBack").bind("click", function() {
		goBackToPreviousPage();
	});
});