/**
 * 	@author Nghia Nguyen
 * 
 * 	@version 1.0.0
 * 	@since 1.0.0
 */
$(document).ready(function() {
	//--------------- Constants definition ---------------
	// number of items in one page in table
	var ITEM_IN_ONE_PAGE = ITEM_IN_ONE_PAGE_0095;
	// when Area selected nothing
	
	//--------------- Variables definition ---------------
	// application path
	var rootPath = getContextPath();
	// search result total data count
	var totalResultCount = 0;
	// variables for handling pager
	var currentPage = 1;
	var maxPage = 0;
	var from = 0;
	// variable to store selected row index
	var selectedRowIndex = "";
	// variable to store current selected mode
	var currentMode = "";
	// numberical_order
	var numberical_order = 0;
	// search conditions 
	var searchConditions = null;
	// variable to store last updated date time when user opens edit popup
	var currentLastUpdatedTimeString = "";
	// display current date
	$("#txtCurrentDate").text(getCurrentDate() + " (" + DAY_OF_WEEK + ") ");
	// initialize combobox data
	// set read only
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

	// Back button click event process
	$("#btnBack").bind("click", function() {
		goBackToPreviousPage();
	});

	// Register Access Authorization click event process
	$("#btnRegister").bind("click", function() {
		// clear all current text of popup controls
		clearPopupControl(); 
		// change state of controls in popup based on mode
		setPopupControlState(MODE_NEW);
		// display Area info popup
		showPopup($("#popupWrapper"));
	});

	// Delete Access Authorization click event process
	$(document).on("click", ".delete", function() {
		// get row index
		selectedRowIndex = $(this).attr("name");
		var lastUpdateDate = $(this).attr("lastUpdateDate");
		
		// display confirmation message
		jQuestion_warning(DELETE_CONFIRM_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
			if (val) {
				
				accessAuthorityId = $("#row" + selectedRowIndex).find("td").eq(1).text();
				screenId = $("#row" + selectedRowIndex).find("td").eq(2).text();
				// make Ajax call to server to get data
				$.ajax({
					url: "deleteData",
					data: { "accessAuthorityId": accessAuthorityId, 
							"screenId": screenId,
							"lastUpdateDate": lastUpdateDate
						},
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
							} else if (returnedJsonData == DELETE_RESULT_FAILED_CULTIVATION_ACCESS_AUTHORIZATION) {
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
	// Edit Access Authorization info click event process
	$(document).on("click", ".edit", function() {
		// get row index
		selectedRowIndex = $(this).attr("name");
		accessAuthorityId = $("#row" + selectedRowIndex).find("td").eq(1).text();
		screenId = $("#row" + selectedRowIndex).find("td").eq(2).text();
		// Get search conditions
		dataObject = createGetSingleConditions(accessAuthorityId, screenId);
		// make Ajax call to server to get data
		$.ajax({
			url: "getSingleData",
			contentType: "application/json",
			data: JSON.stringify(dataObject),
			type: "POST",
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				if (returnedJsonData != "") {
					// clear all current text of popup controls
					clearPopupControl();
					// accessAuthorityId
					$("#txtAccessAuthorityIdPopup").val(returnedJsonData[0].accessAuthorityId);
					// Screen id
					$("#txtScreenIdPopup").val(returnedJsonData[0].screenId);
					// screenDisplayEnableFlag
					if (returnedJsonData[0].screenDisplayEnableFlag == DELETE_FLAG_ON) {
						$("#chbScreenDisplayEnableFlagPopup").prop("checked", true);
					} else if (returnedJsonData[0].screenDisplayEnableFlag == DELETE_FLAG_OFF) {
						$("#chbScreenDisplayEnableFlagPopup").prop("checked", false);
					}
					// addableFlag
					if (returnedJsonData[0].addableFlag == DELETE_FLAG_ON) {
						$("#chbAddableFlagPopup").prop("checked", true);
					} else if (returnedJsonData[0].addableFlag == DELETE_FLAG_OFF) {
						$("#chbAddableFlagPopup").prop("checked", false);
					}
					// updatableFlag
					if (returnedJsonData[0].updatableFlag == DELETE_FLAG_ON) {
						$("#chbUpdatableFlagPopup").prop("checked", true);
					} else if (returnedJsonData[0].updatableFlag == DELETE_FLAG_OFF) {
						$("#chbUpdatableFlagPopup").prop("checked", false);
					}
					// deletableFlag
					if (returnedJsonData[0].deletableFlag == DELETE_FLAG_ON) {
						$("#chbDeletableFlagPopup").prop("checked", true);
					} else if (returnedJsonData[0].deletableFlag == DELETE_FLAG_OFF) {
						$("#chbDeletableFlagPopup").prop("checked", false);
					}
					// referenceFlag
					if (returnedJsonData[0].referenceFlag == DELETE_FLAG_ON) {
						$("#chbRefernceFlagPopup").prop("checked", true);
					} else if (returnedJsonData[0].referenceFlag == DELETE_FLAG_OFF) {
						$("#chbRefernceFlagPopup").prop("checked", false);
					}
					// status
					if (returnedJsonData[0].deleteFlag == DELETE_FLAG_ON) {
						$("#chbDeletePopup").prop("checked", true);
					} else if (returnedJsonData[0].deleteFlag == DELETE_FLAG_OFF) {
						$("#chbDeletePopup").prop("checked", false);
					}

					// last updated date time
					currentLastUpdatedTimeString = returnedJsonData[0].lastUpdateDate;
					setPopupControlState(MODE_EDIT);
					
					// display user info popup
					showPopup($("#popupWrapper"));
				}
				else {
					jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
				}
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	});
	
	// Register button in popup click event process
	$("#btnRegisterPopup").bind("click", function() {
		var dataObject = null;
		// check current mode
		if (currentMode == MODE_NEW) {
			 if (checkInputBlankFields() != "") {
				// blank field(s)
				// display message
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			 } else if (checkInputNumberFields() != "") {
					jWarning(VALIDATE_NUMBER_FIELDS_MESSAGE.replace('$1', checkInputNumberFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			 } else {
				// insert new Access Authorization to DB
				// get Task input data
				dataAccessAuthorizationObject = createAccessAuthorizationDataObject();
				// make Ajax call to server to get data
				$.ajax({
					type: "POST",
					url: "insertData",
					contentType: "application/json",
					data: JSON.stringify(dataAccessAuthorizationObject),
					async: false,
					success: function(returnedJsonData) {
						if (checkSessionTimeout(returnedJsonData) == 1) return;
						if (returnedJsonData != "") {
							// check returned value from server
							if (returnedJsonData == INSERT_RESULT_SUCCESSFUL) {
								// Reset Data 
								clearPopupControl()
								// reset variables
								resetVariables();
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
								jWarning(INSERT_RESULT_DUPLICATED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_FAILED_CULTIVATION_ACCESS_AUTHORIZATION) {
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
			if (checkInputBlankFields() != "") {
				// blank field(s)
				// display message
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (checkInputNumberFields() != "") {
					jWarning(VALIDATE_NUMBER_FIELDS_MESSAGE.replace('$1', checkInputNumberFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// update existing user in DB
				// get Access Authorization input data
				dataAccessAuthorizationObject = createAccessAuthorizationDataObject();
				// make Ajax call to server to get data
				$.ajax({
					type: "POST",
					url: "updateData",
					contentType: "application/json",
					data: JSON.stringify(dataAccessAuthorizationObject),
					async: false,
					success: function(returnedJsonData) {
						if (checkSessionTimeout(returnedJsonData) == 1) return;
						if (returnedJsonData != "") {
							// check returned value from server
							if (returnedJsonData == UPDATE_RESULT_SUCCESSFUL) {
								// Reset Data 
								clearPopupControl()
								// reset variables
								resetVariables();
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
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_ACCESS_AUTHORIZATION) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_EXCEPTION) {
								// failed
								// display message
								jWarning(UPDATE_RESULT_FAILED_MESSAGE.replace('$1', UPDATE_RESULT_FAILED_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_UPDATE_DATE) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
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
		// hide Access Authorization info popup
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
		if (isNaN(value) || $(this).val() == "" || parseInt($(this).val()) == 0 || (currentPage == maxPage && (maxPage == 1 || maxPage == 0))) {
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
	
	// Draw Access Authorization data table based on search conditions
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
					// 検索結果件数設定
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
							// accessAuthorityId
							tableStringArray.push("<td class='align-center'>"+numberical_order+"</td>");
							tableStringArray.push("<td class='align-right'>" + returnedJsonData[i].accessAuthorityId + "</td>");
							// screenId
							tableStringArray.push("<td class='align-right'>" + returnedJsonData[i].screenId + "</td>");
							// screenDisplayEnableFlag
							var screenDisplayEnableFlag = (returnedJsonData[i].screenDisplayEnableFlag == DELETE_FLAG_OFF) ? STATUS_DISABLED : STATUS_ENABLED;
							tableStringArray.push("<td>" + screenDisplayEnableFlag + "</td>");
							// addableFlag
							var addableFlag = (returnedJsonData[i].addableFlag == DELETE_FLAG_OFF) ? STATUS_DISABLED : STATUS_ENABLED;
							tableStringArray.push("<td>" + addableFlag + "</td>");
							// updatableFlag
							var updatableFlag = (returnedJsonData[i].updatableFlag == DELETE_FLAG_OFF) ? STATUS_DISABLED : STATUS_ENABLED;
							tableStringArray.push("<td>" + updatableFlag + "</td>");
							// deletableFlag
							var deletableFlag = (returnedJsonData[i].deletableFlag == DELETE_FLAG_OFF) ? STATUS_DISABLED : STATUS_ENABLED;
							tableStringArray.push("<td>" + deletableFlag + "</td>");
							// referenceFlag
							var referenceFlag = (returnedJsonData[i].referenceFlag == DELETE_FLAG_OFF) ? STATUS_DISABLED : STATUS_ENABLED;
							tableStringArray.push("<td>" + referenceFlag + "</td>");
							// update icon
							tableStringArray.push("<td><img class='edit cursor-pointer' lastUpdateDate = '"+returnedJsonData[i].lastUpdateDate+"' name='" + i + "' src='" + rootPath + "/resources/img/icon_edit.png?date=" + icon_editDate + "'></td>");
							//if(returnedJsonData[i].accessAuthorityId != "1") {
							// delete icon
							tableStringArray.push("<td><img class='delete cursor-pointer' lastUpdateDate = '"+returnedJsonData[i].lastUpdateDate+"' name='" + i + "' src='" + rootPath + "/resources/img/icon_del.png?date=" + icon_delDate + "'></td>");
							//} else {
								// delete icon
								//tableStringArray.push("<td></td>");
							//}
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
						// scroll to top of table
						var tableHeight = calculateTableHeight();
						$("#divBody").height(tableHeight);
						fixTable();												
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

	function resetSearchConditions() {
		$("#txtAccessAuthorityId").val("");
		$("#txtScreenId").val("");
		$("#cbbScreenDisplayEnableFlag").val("-2");
		$("#cbbAddableFlag").val("-2");
		$("#cbbUpdatableFlag").val("-2");
		$("#cbbDeleteableFlag").val("-2");
		$("#cbbReferenceableFlag").val("-2");
	}
	
	// Search conditions object creation process
	function createSearchConditions() {
		var screenDisplayEnableFlag = $("#cbbScreenDisplayEnableFlag").find("option:selected").val();
		// Add able Flag
		var addableFlag = $("#cbbAddableFlag").find("option:selected").val();
		// Update able Flag
		var updatableFlag = $("#cbbUpdatableFlag").find("option:selected").val();
		// Update able Flag
		var deletableFlag = $("#cbbDeleteableFlag").find("option:selected").val();
		// Reference able Flag
		var referenceFlag = $("#cbbReferenceableFlag").find("option:selected").val();
		return {
			accessAuthorityId: $.trim($("#txtAccessAuthorityId").val()),
			screenId: $.trim($("#txtScreenId").val()),
			screenDisplayEnableFlag: screenDisplayEnableFlag,
			addableFlag: addableFlag,
			updatableFlag: updatableFlag,
			deletableFlag: deletableFlag,
			referenceFlag: referenceFlag,
			fromRow: from,
			itemCount: ITEM_IN_ONE_PAGE
		};
	}
	
	function createGetSingleConditions(accessAuthorityId,screenId) {
		return {
			accessAuthorityId: accessAuthorityId,
			screenId: screenId
		};
	}

	// Area data object creation process, to use in Updating and Inserting user
	function createAccessAuthorizationDataObject() {
		return {
			accessAuthorityId: $.trim($("#txtAccessAuthorityIdPopup").val()),
			screenId: $.trim($("#txtScreenIdPopup").val()),
			screenDisplayEnableFlag: $("#chbScreenDisplayEnableFlagPopup").is(":checked") ? FLAG_ON : FLAG_OFF,
			addableFlag: $("#chbAddableFlagPopup").is(":checked") ? FLAG_ON : FLAG_OFF,
			updatableFlag: $("#chbUpdatableFlagPopup").is(":checked") ? FLAG_ON : FLAG_OFF,
			deletableFlag: $("#chbDeletableFlagPopup").is(":checked") ? FLAG_ON : FLAG_OFF,
			referenceFlag: $("#chbRefernceFlagPopup").is(":checked") ? FLAG_ON : FLAG_OFF,
			deleteFlag: false,
			lastUpdateDate: currentLastUpdatedTimeString
		};
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

	// Update UI of pager based on search result ２００５テーブルナビゲーションコントロールの状態変更
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

	// Clear all current text of popup controls
	function clearPopupControl() {
		// accessAuthorityId
		$("#txtAccessAuthorityIdPopup").val("");
		// screenId
		$("#txtScreenIdPopup").val("");
		// chbScreenDisplayEnableFlagPopup
		$("#chbScreenDisplayEnableFlagPopup").prop("checked", false);
		// add able Flag
		$("#chbAddableFlagPopup").prop("checked", false);
		// updatableFlag
		$("#chbUpdatableFlagPopup").prop("checked", false);
		// delete able Flag
		$("#chbDeletableFlagPopup").prop("checked", false);
		// referenceFlag
		$("#chbRefernceFlagPopup").prop("checked", false);
		// status
		$("#chbDeletePopup").prop("checked", false);
		// note
		$("#txtNotePopup").val("");
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
			// accessAuthorityId
			$("#txtAccessAuthorityIdPopup").prop("disabled", false);
			// screenId
			$("#txtScreenIdPopup").prop("disabled", false);
			// status
			$("#chbDeletePopup").prop("disabled", false);
			// screen Display Enable Flag
			$("#chbScreenDisplayEnableFlagPopup").prop("disabled", false);
			// add able Flag
			$("#chbAddableFlagPopup").prop("disabled", false);
			// updatableFlag
			$("#chbUpdatableFlagPopup").prop("disabled", false);
			// deletable Flag
			$("#chbDeletableFlagPopup").prop("disabled", false);
			// referenceFlag
			$("#chbRefernceFlagPopup").prop("disabled", false);
			// hide button show Farm popup 
			$(".area-info-popup #btnSearchPopup").show();
			// hide clear text Farm popup
			$(".area-info-popup #btnClearPopup").show();
			// register button
			$("#btnRegisterPopup").show();
		} else if (mode == MODE_EDIT) {
			$("#addNew").addClass("display-none");
			$("#update").removeClass("display-none");
			// set current mode
			currentMode = MODE_EDIT;
			// accessAuthorityId
			$("#txtAccessAuthorityIdPopup").prop("disabled", true);
			// screenId
			$("#txtScreenIdPopup").prop("disabled", true);
			// status
			$("#chbDeletePopup").prop("disabled", false);
			// screen Display Enable Flag
			$("#chbScreenDisplayEnableFlagPopup").prop("disabled", false);
			// add able Flag
			$("#chbAddableFlagPopup").prop("disabled", false);
			// updatableFlag
			$("#chbUpdatableFlagPopup").prop("disabled", false);
			// deletable Flag
			$("#chbDeletableFlagPopup").prop("disabled", false);
			// referenceFlag
			$("#chbRefernceFlagPopup").prop("disabled", false);
			// Show Farm Popup button
			$("#btnSearchPopup").show();
			// register button
			$("#btnRegisterPopup").show();
		} else if (mode == MODE_VIEW) {
			// set current mode
			currentMode = MODE_VIEW;
			// accessAuthorityId
			$("#txtAccessAuthorityIdPopup").prop("disabled", true);
			// screenId
			$("#txtScreenIdPopup").prop("disabled", true);
			// status
			$("#chbDeletePopup").prop("disabled", true);
			// screen Display Enable Flag
			$("#chbScreenDisplayEnableFlagPopup").prop("disabled", true);
			// add able Flag
			$("#chbAddableFlagPopup").prop("disabled", true);
			// updatableFlag
			$("#chbUpdatableFlagPopup").prop("disabled", true);
			// deletable Flag
			$("#chbDeletableFlagPopup").prop("disabled", true);
			// referenceFlag
			$("#chbRefernceFlagPopup").prop("disabled", true);
			// Show Farm Popup button
			$("#btnSearchPopup").hide();
			// Show Farm Clear button
			$("#btnClearPopup").hide();
			// register button
			$("#btnRegisterPopup").hide();
		}
	}

	// Check input: blank fields
	function checkInputBlankFields() {
		if ($("#txtAccessAuthorityIdPopup").val() == "") {
			return ACCESSAUTHORITYID;
		} else if ($("#txtScreenIdPopup").val() == "") {
			return SCREENID;
		} else {
			return "";
		}
	}

	function checkInputNumberFields(){
		var errorCount = 0;
		if (isNaN($("#txtAccessAuthorityIdPopup").val())) {
			return ACCESSAUTHORITYID;
		} else if (isNaN($("#txtScreenIdPopup").val())) {
			return SCREENID;
		} else {
			return ""
		}
		return errorStr;
	}

	//
	$(document).on('keypress', '#txtAccessAuthorityId, #txtScreenId, #txtAccessAuthorityIdPopup, #txtScreenIdPopup', function(e) {
		  if (e.charCode != 0 && (e.charCode < 48 || e.charCode > 57)){
		   e.preventDefault();
		  }
	});
});