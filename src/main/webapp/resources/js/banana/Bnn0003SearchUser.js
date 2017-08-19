/**
 * @author Khoa Le
 * Search user, create new user and edit user's info
 */

$(document).ready(function() {
	//--------------- Constants definition ---------------
	// number of items in one page in table
	var ITEM_IN_ONE_PAGE = ITEM_IN_ONE_PAGE_0003;
	//--------------- Variables definition ---------------
	// application path
	var rootPath = getContextPath();
	// search result total data count
	var totalResultCount = 0;
	// variables for handling pager
	var currentPage = 1;
	// numberical_order
	var numberical_order = 0;
	var maxPage = 0;
	var from = 0;
	// variable to store selected row index
	var selectedRowIndex = "";
	// variable to store selected user id to show in popup
	var usersIdPopup = "";
	// variable to store current selected mode
	var currentMode = "";
	// variable to check whether user edits password in EDIT Mode
	var isPasswordChanged = false;
	// search conditions 
	var searchConditions = null;
	// variable to store last updated date time when user opens edit popup
	var currentLastUpdatedTimeString = "";
	// display current date
	$("#txtCurrentDate").text(getCurrentDate() + " (" + DAY_OF_WEEK + ") ");

	// Search button event process
	$("#btnSearch").bind("click", function() {
		numberical_order = 0;
		// reset variables
		resetVariables();
		// search conditions
		searchConditions = createSearchConditions();
		// draw table
		drawResult = drawTable();
		if (drawResult != "") {
			// update total data count UI
			setDataCounts();
		}
	});

	// Register user click event process
	$("#btnRegister").bind("click", function() {
		// clear all current text of popup controls
		clearPopupControl(); 
		// change state of controls in popup based on mode
		setPopupControlState(MODE_NEW);
		// display user info popup
		showPopup($("#popupWrapper"));
	});

	// Back button click event process
	$("#btnBack").bind("click", function() {
		goBackToPreviousPage();
	});

	// Edit user info click event process
	$(document).on("click", ".edit", function() {
		// get row index
		selectedRowIndex = $(this).attr("name");
		// get id of selected user
		usersIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).text();
		// make Ajax call to server to get data
		$.ajax({
			url: "getSingleData",
			data: { "usersId": usersIdPopup },
			type: "POST",
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				if (returnedJsonData != "") {
					// clear all current text of popup controls
					clearPopupControl();

					// users id
					$("#txtUsersIdPopup").val(returnedJsonData.usersId);
					// users name
					$("#txtUsersNamePopup").val(returnedJsonData.usersName);
					// password
					$("#txtPasswordPopup").val(returnedJsonData.password);
					// status
					if (returnedJsonData.deleteFlag == DELETE_FLAG_OFF) {
						$("#chbDeletePopup").prop("checked", false);
					} else if (returnedJsonData.deleteFlag == DELETE_FLAG_ON) {
						$("#chbDeletePopup").prop("checked", true);
					}
					// last updated date time
					currentLastUpdatedTimeString = returnedJsonData.lastUpdateDate;

					// change state of controls in popup based on mode
					setPopupControlState(MODE_EDIT);
					
					// display user info popup
					showPopup($("#popupWrapper"));
				} else {
					jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
				}
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	});

	// Delete user click event process
	$(document).on("click", ".delete", function() {
		// get row index
		selectedRowIndex = $(this).attr("name");
		// display confirmation message
		jQuestion_warning(DELETE_CONFIRM_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
			if (val) {
				
				// get id of selected user
				usersIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).text();
				var lastUpdate = $("#row" + selectedRowIndex).find("td").eq(2).find("input").val();
				userData  = {usersId : usersIdPopup,
							lastUpdateDate: lastUpdate};
				// make Ajax call to server to delete data
				$.ajax({
					url: "deleteData",
					contentType: "application/json",
					data: JSON.stringify(userData),
					type: "POST",
					async: false,
					success: function(returnedJsonData) {
						if (checkSessionTimeout(returnedJsonData) == 1) return;
						if (returnedJsonData != "") {
							// check returned value from server
							if (returnedJsonData == DELETE_RESULT_SUCCESSFUL) {
								// search data again
								// reset variables
								resetVariables();
								// reset search conditions
								resetSearchConditions();
								searchConditions = createSearchConditions();
								// draw table
								numberical_order = 0;
								drawResult = drawTable();
								if (drawResult != "") {
									// update total data count UI
									setDataCounts();
								}
								// display message
								jInfo(DELETE_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == DELETE_RESULT_FAILED_UPDATE_DATE) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == DELETE_RESULT_FAILED_CULTIVATION_USER) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == DELETE_RESULT_FAILED_EXCEPTION) {
								// failed
								// display message
								jWarning(DELETE_RESULT_FAILED_MESSAGE.replace('$1', DELETE_RESULT_FAILED_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
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
	});

	// Changed event when user edits password in EDIT Mode
	$("#txtPasswordPopup").bind("input propertychange paste", function() {
	    // switch flag
		isPasswordChanged = true;
	});

	// Register button in popup click event process
	$("#btnRegisterPopup").bind("click", function() {
		var dataObject = null;
		// check current mode
		if (currentMode == MODE_NEW) {
			// check for user input
			if (checkInputBlankFields() != "") {
				// blank field(s)
				// display message
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// insert new user to DB
				// get user input data
				dataObject = createUserDataObject();
				// make Ajax call to server to get data
				$.ajax({
					type: "POST",
					url: "insertData",
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
							} else if (returnedJsonData == INSERT_RESULT_SUCCESSFUL) {
								// search data again
								// reset variables
								resetVariables();
								// reset search conditions
								resetSearchConditions();
								searchConditions = createSearchConditions();
								// draw table
								numberical_order = 0;
								drawResult = drawTable();
								if (drawResult != "") {
									// update total data count UI
									setDataCounts();
								}
								// hide popup
								hidePopup($("#popupWrapper"));
								// display message
								jInfo(INSERT_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_DUPLICATED) {
								// duplicated user id
								// display message
								jWarning(INSERT_RESULT_DUPLICATED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_FAILED_CULTIVATION_USER) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_FAILED_EXCEPTION) {
								// failed
								// display message
								jWarning(INSERT_RESULT_FAILED_MESSAGE.replace('$1', INSERT_RESULT_FAILED_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
							}
						}
					},
					error: function(e) {
						// display error message
						jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
					}
				});
			}
		} else if (currentMode == MODE_EDIT) {
			// check for user input
			if (checkInputBlankFields() != "") {
				// blank field(s)
				// display message
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// update existing user in DB
				// get user input data
				dataObject = createUserDataObject();
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
							} else if (returnedJsonData == UPDATE_RESULT_SUCCESSFUL) {
								// update the corresponding user data in search table
								// make Ajax call to server to get data
								$.ajax({
									url: "getSingleData",
									data: { "usersId": usersIdPopup },
									type: "POST",
									async: false,
									success: function(returnedJsonData) {
										if (checkSessionTimeout(returnedJsonData) == 1) return;
										if (returnedJsonData != "") {
											// users name
											$("#row" + selectedRowIndex).find("td").eq(2).text(returnedJsonData.usersName);
										}
									},
									complete: function(jqXHR, textStatus) {
										resetVariables();
										// reset search conditions
										resetSearchConditions();
										searchConditions = createSearchConditions();
										// draw table
										numberical_order = 0;
										drawResult = drawTable();
										if (drawResult != "") {
											// update total data count UI
											setDataCounts();
										}
										// hide popup
										hidePopup($("#popupWrapper"));
										// display message
										jInfo(UPDATE_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
									}
								});
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_CULTIVATION_USER) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_UPDATE_DATE) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
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
		}
	});

	// Cancel button in popup click event process
	$("#btnCancelPopup").bind("click", function() {
		// hide user info popup
		hidePopup($("#popupWrapper"));
	});

	// Page First button click event process
	$("#btnFirst").bind("click", function() {
		if (parseInt(totalResultCount) > 0) {
			if (currentPage > 1) {
				var newPage = 1;
				numberical_order = 0;
				goToPage(newPage);
			}
		}
	});

	// Page Previous button click event process
	$("#btnPrevious").bind("click", function() {
		if (parseInt(totalResultCount) > 0) {
			if (currentPage > 1) {
				var newPage = currentPage - 1;
				numberical_order = (newPage -1) * ITEM_IN_ONE_PAGE;
				goToPage(newPage);
			}
		}
	});

	// Page Next button click event process
	$("#btnNext").bind("click", function() {
		if (parseInt(totalResultCount) > 0) {
			if (currentPage < maxPage) {
				var newPage = currentPage + 1;
				numberical_order = (newPage -1) * ITEM_IN_ONE_PAGE;
				goToPage(newPage);
			}
		}
	});

	// Page Last button click event process
	$("#btnLast").bind("click", function() {
		if (parseInt(totalResultCount) > 0) {
			if (currentPage < maxPage) {
				var newPage = maxPage;
				numberical_order = (newPage -1) * ITEM_IN_ONE_PAGE;
				goToPage(newPage);
			}
		}
	});

	// Check Page input
	$("#txtGoToPage").bind("keyup", function() {
		var value = parseInt($(this).val());
		if (value > 0) {
			$("#btnGoToPage").prop("disabled", false);
			$("#btnGoToPage").css("cursor", "pointer");
		}
		if (isNaN(value) || $(this).val() == "" || (currentPage == maxPage && (maxPage == 1 || maxPage == 0))) {
			$("#btnGoToPage").prop("disabled", true);
			$("#btnGoToPage").css("cursor", "default");
		}
	});
	$("#txtGoToPage").bind("keydown", function(event) {
		// Allow: backspace, delete, tab, escape, enter
		if ($.inArray(event.keyCode, [ 46, 8, 9, 27, 13 ]) !== -1
				||
				// Allow: Ctrl+A, Command+A
				(event.keyCode == 65 && (event.ctrlKey === true || event.metaKey === true))
				||
				// Allow: home, end, left,
				// right, down, up
				(event.keyCode >= 35 && event.keyCode <= 40)) {
			// let it happen, don't do anything
			return;
		}
		// Ensure that it is a number and stop
		// the keypress
		if ((event.shiftKey || (event.keyCode < 48 || event.keyCode > 57))
				&& (event.keyCode < 96 || event.keyCode > 105)) {
			event.preventDefault();
		}
	});

	// Page Go button click event process
	$("#btnGoToPage").bind("click", function() {
		var newPage = parseInt($("#txtGoToPage").val());
		if (newPage >= 1 && newPage <= maxPage && maxPage > 0) {
			currentPage = newPage;
			numberical_order = ((newPage-1) * ITEM_IN_ONE_PAGE);
			goToPage(currentPage);
		} else if (newPage >= 1 && newPage > maxPage && maxPage > 0) {
			newPage = maxPage;
			numberical_order = ((newPage-1) * ITEM_IN_ONE_PAGE);
			goToPage(newPage);
		} else if (newPage == 0 && maxPage > 0) {
			newPage = 1;
			numberical_order = 0;
			goToPage(newPage);
		}
	});

	//search all
	$("#btnSearch").trigger("click");
	
	// Draw user data table based on search conditions
	function drawTable() {
		// variables definition
		var returnValue = "";
		searchConditions.fromRow = from;
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "searchData",
			contentType: "application/json",
			data: JSON.stringify(searchConditions),
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				if (returnedJsonData != "") {
					// set the real search total count
					totalResultCount = returnedJsonData[0].searchDataTotalCounts;
					if (parseInt(totalResultCount) == -1) {
						// OutOfMemoryException, display error message
						jWarning(SEARCH_RESULT_OUT_OF_MEMORY_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
					} else {
						// calculate max page
						var calculatedResultOdd = parseInt(totalResultCount) % parseInt(ITEM_IN_ONE_PAGE);
						var calculatedResult = Math.floor(parseInt(totalResultCount) / parseInt(ITEM_IN_ONE_PAGE));
						maxPage = (calculatedResultOdd == 0) ? calculatedResult : (calculatedResult + 1);
						// update pager
						$("#lblCurrentPage").text(currentPage);
						$("#lblMaxPage").text(maxPage);
						// clear table
						$("#tblBody").find("tbody").remove();
						// create table starts
						var tableStringArray = [];
						// add tbody open tag
						tableStringArray.push("<tbody>");
						for (var i = 0; i < returnedJsonData.length; i++) {
							numberical_order++;
							// row open tag
							tableStringArray.push("<tr id='row" + i + "'>");
							tableStringArray.push("<td class='align-center'>"+numberical_order+"</td>");
							// users id
							tableStringArray.push("<td>" + returnedJsonData[i].usersId + "</td>");
							
							// lastUpdateDate
							tableStringArray.push("<td style='display:none;'><input type='hidden' value='" + returnedJsonData[i].lastUpdateDate + "' ></td>");
							// users name
							tableStringArray.push("<td>" + returnedJsonData[i].usersName + "</td>");
							// update icon
							tableStringArray.push("<td><img class='edit cursor-pointer' name='" + i + "' src='" + rootPath + "/resources/img/icon_edit.png?date=" + icon_editDate + "'></td>");
							// delete icon
							tableStringArray.push("<td><img class='delete cursor-pointer' name='" + i + "' src='" + rootPath + "/resources/img/icon_del.png?date=" + icon_delDate + "'></td>");
							// row close tag
							tableStringArray.push("</tr>");
						}
						// add tbody close tag
						tableStringArray.push("</tbody>");
						// append all created string to table
						$("#tblBody").append(tableStringArray.join(''));
						// show search result
						$(".cont-box").removeClass("display-none");
						// fix table header and body when scrolling only the table body
						fixTable();
						// fix table height to fit page
						var tableHeight = calculateTableHeight();
						$("#divBody").height(tableHeight);
						// update total data count UI
						setDataCounts();
					    setPagerStatus();
					    // scroll to top of table
						$("#divBody").scrollTop(0).scrollLeft(0);
					}
				} else {
					// display error message
					jWarning(SEARCH_RESULT_NO_DATA_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
					// update pager
					$("#lblCurrentPage").text("0");
					$("#lblMaxPage").text("0");
					// clear table
					$("#tblBody").find("tbody").remove();
					fixTable();
					totalResultCount = 0;
					// update total data count UI
					setDataCounts();
					setPagerStatus();
				}
				returnValue = totalResultCount;
			},
			error: function(e) {
				// set return value
				returnValue = "";
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
		return returnValue;
	}

	// Search conditions object creation process
	function createSearchConditions() {
		// get role string
		var roleString = "";
		if ($("#chbSysAdmin").is(":checked")) {
			roleString += $("#chbSysAdmin").val() + ",";
		}
		if ($("#chbMana").is(":checked")) {
			roleString += $("#chbMana").val() + ",";
		}
		if ($("#chbFarmMana").is(":checked")) {
			roleString += $("#chbFarmMana").val() + ",";
		}
		if ($("#chbAreaMana").is(":checked")) {
			roleString += $("#chbAreaMana").val() + ",";
		}
		return {
			usersId: $.trim($("#txtUsersId").val()).replace(/\\/g, "\\\\").replace(/%/g, "\\%"),
			usersName: $.trim($("#txtUsersName").val()).replace(/\\/g, "\\\\").replace(/%/g, "\\%"),
			usersRoleTypeString: roleString == "" ? STATUS_NO_SELECT : roleString,
			lastUpdateDate: currentLastUpdatedTimeString,
			fromRow: from,
			itemCount: ITEM_IN_ONE_PAGE
		};
	}

	// User data object creation process, to use in Updating and Inserting user
	function createUserDataObject() {
		if (currentMode == MODE_EDIT) {
			return {
				usersId: $.trim($("#txtUsersIdPopup").val()),
				usersName: $.trim($("#txtUsersNamePopup").val()),
				password: $("#txtPasswordPopup").val(),
				passwordChanged: isPasswordChanged,
				roleType: $.trim($("input[name=typePopup]:checked").val()),
				deleteFlag: DELETE_FLAG_OFF,
				note: $.trim($("#txtNotePopup").val()),
				lastUpdateDate: currentLastUpdatedTimeString
			};
		} else {
			return {
				usersId: $.trim($("#txtUsersIdPopup").val()),
				usersName: $.trim($("#txtUsersNamePopup").val()),
				password: $("#txtPasswordPopup").val(),
				roleType: $.trim($("input[name=typePopup]:checked").val()),
				deleteFlag: DELETE_FLAG_OFF,
				note: $.trim($("#txtNotePopup").val())
			};
		}
	}

	// Get data of search result based on page number
	function goToPage(newPage) {
		setTimeout(function() {
			// newPage is 0-index based
			newPage = parseInt(newPage) - 1;
			from = parseInt(ITEM_IN_ONE_PAGE) * newPage;
			// draw new data
			var drawResult = drawTable();
			if (drawResult != "") {
				currentPage = newPage + 1;
				// update pager
				$("#lblCurrentPage").text(currentPage);
				$("#lblMaxPage").text(maxPage);
				setPagerStatus();
			}
			// update total data count UI
			setDataCounts();
		}, 10);
	}

	// Update UI of pager based on search result
	function setPagerStatus() {
		$("#txtGoToPage").val("");
		if (parseInt(totalResultCount) > 0) {
			if (parseInt(maxPage) == 1) {
				$("#btnFirst").addClass("page-number-first_dis");
				$("#btnPrevious").addClass("page-number-pre_dis");
				$("#btnNext").addClass("page-number-next_dis");
				$("#btnLast").addClass("page-number-last_dis");
				$("#txtGoToPage").prop("readonly", true);
			} else {
				if (currentPage == 1) {
					$("#btnFirst").addClass("page-number-first_dis");
					$("#btnPrevious").addClass("page-number-pre_dis");
					$("#btnNext").removeClass("page-number-next_dis");
					$("#btnLast").removeClass("page-number-last_dis");
				}
				if (currentPage > 1 && currentPage < maxPage) {
					$("#btnFirst").removeClass("page-number-first_dis");
					$("#btnPrevious").removeClass("page-number-pre_dis");
					$("#btnNext").removeClass("page-number-next_dis");
					$("#btnLast").removeClass("page-number-last_dis");
				}
				if (currentPage == maxPage) {
					$("#btnFirst").removeClass("page-number-first_dis");
					$("#btnPrevious").removeClass("page-number-pre_dis");
					$("#btnNext").addClass("page-number-next_dis");
					$("#btnLast").addClass("page-number-last_dis");
				}
				$("#txtGoToPage").prop("readonly", false);
			}
		} else {
			$("#btnFirst").addClass("page-number-first_dis");
			$("#btnPrevious").addClass("page-number-pre_dis");
			$("#btnNext").addClass("page-number-next_dis");
			$("#btnLast").addClass("page-number-last_dis");
			$("#txtGoToPage").prop("readonly", true);
		}
		$("#txtGoToPage").val("");
		$("#btnGoToPage").prop("disabled", true);
		$("#btnGoToPage").css("cursor", "default");
	}

	// Update total data count process
	function setDataCounts() {
		$("#txtCounts").text(totalResultCount.toString().replace(/^(-?\d+)(\d{3})/, "$1,$2"));
	}

	// Reset variables process
	function resetVariables() {
		totalResultCount = 0;
		currentPage = 1;
		from = 0;
	}

	// Reset search conditions process
	function resetSearchConditions() {
		$("#txtUsersId").val("");
		$("#txtUsersName").val("");
		$("#cbbStatus").val(STATUS_NO_SELECT);
	}
	
	// Clear all current text of popup controls
	function clearPopupControl() {
		// users id
		$("#txtUsersIdPopup").val("");
		// users name
		$("#txtUsersNamePopup").val("");
		// password
		$("#txtPasswordPopup").val("");
		// status
		$("#chbDeletePopup").prop("checked", false);
	}

	// Change state of controls in popup based on mode
	function setPopupControlState(mode) {
		if (mode == MODE_NEW) {
			$("#update").addClass("display-none");
			$("#addNew").removeClass("display-none");
			// reset numberical Order
			numberical_order = 0;
			// set current mode
			currentMode = MODE_NEW;
			// users id
			$("#txtUsersIdPopup").prop("disabled", false);
			// users name
			$("#txtUsersNamePopup").prop("disabled", false);
			// password
			$("#txtPasswordPopup").prop("disabled", false);
			// status
			$("#chbDeletePopup").prop("disabled", false);
			// register button
			$("#btnRegisterPopup").show();
		} else if (mode == MODE_EDIT) {
			$("#addNew").addClass("display-none");
			$("#update").removeClass("display-none");
			// set current mode
			currentMode = MODE_EDIT;
			// reset flag
			isPasswordChanged = false;
			// users id
			$("#txtUsersIdPopup").prop("disabled", true);
			// users name
			$("#txtUsersNamePopup").prop("disabled", false);
			// password
			$("#txtPasswordPopup").prop("disabled", false);
			// status
			$("#chbDeletePopup").prop("disabled", false);
			// register button
			$("#btnRegisterPopup").show();
		} else if (mode == MODE_VIEW) {
			// set current mode
			currentMode = MODE_VIEW;
			// users id
			$("#txtUsersIdPopup").prop("disabled", true);
			// users name
			$("#txtUsersNamePopup").prop("disabled", true);
			// password
			$("#txtPasswordPopup").prop("disabled", true);
			// status
			$("#chbDeletePopup").prop("disabled", true);
			// register button
			$("#btnRegisterPopup").hide();
		}
	}

	// Check input: blank fields
	function checkInputBlankFields() {
		if ($.trim($("#txtUsersIdPopup").val()) == ""){
			return usersIdPopupBlank;
		} else if($.trim($("#txtUsersNamePopup").val()) == ""){
			return usersNamePopupBlank;
		} else if($("#txtPasswordPopup").val() == "") {
			return passwordPopupBlank;
		} else {
			return "";
		}
	}
});