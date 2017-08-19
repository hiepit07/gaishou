/**
 * @author Nghia Nguyen
 */
$(document).ready(function() {
	//--------------- Constants definition ---------------
	// number of items in one page in table
	var ITEM_IN_ONE_PAGE = ITEM_IN_ONE_PAGE_0089;
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
	// variable to store selected Area id to show in popup
	var areaIdPopup = "";
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

	// Register Task click event process
	$("#btnRegister").bind("click", function() {
		// clear all current text of popup controls
		clearPopupControl(); 
		// change state of controls in popup based on mode
		setPopupControlState(MODE_NEW);
		// display Area info popup
		showPopup($("#popupWrapper"));
		initComboboxData() ;
	});

	// Delete Task click event process
	$(document).on("click", ".delete", function() {
		// display confirmation message
		var curContext = $(this) ;
		// get row index
		selectedRowIndex = curContext.attr("name");
		jQuestion_warning(DELETE_CONFIRM_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
			if (val) {
				
				// get id of selected Task
				taskIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).find("input").val();
				var lastUpdate = $("#row" + selectedRowIndex).find("td").eq(2).find("input").val();
				taskData = {taskId : taskIdPopup,
						  lastUpdateDate: lastUpdate};
				// make Ajax call to server to get data
				$.ajax({
					url: "deleteData",
					contentType: "application/json",
					data: JSON.stringify(taskData),
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
								$("#txtTaskId").val("");
								$("#txtTaskName").val("");
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
							} else if (returnedJsonData == DELETE_RESULT_FAILED_TASK) {
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
	// Edit Area info click event process
	$(document).on("click", ".edit", function() {
		initComboboxData() ;
		// get row index
		selectedRowIndex = $(this).attr("name");
		// get id of selected Task
		taskIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).find("input").val();
		// Get search conditions
		ivbMCultivationProcessKey  = {taskId : taskIdPopup};
		// make Ajax call to server to get data
		$.ajax({
			url: "getSingleData",
			contentType: "application/json",
			data: JSON.stringify(ivbMCultivationProcessKey),
			type: "POST",
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				if (returnedJsonData != "") {
					// clear all current text of popup controls
					clearPopupControl();
					// Task id
					$("#txtTaskIdPopup").val(returnedJsonData.taskId);
					// Task name
					$("#txtTaskNamePopup").val(returnedJsonData.taskName);
					$("#txtWorkingDetailsPopup").val(returnedJsonData.workingDetails);
					$("#txtUsersNamePopup").prop("disabled",true);
					// Process Name
					var optionStr = "<option value='" + returnedJsonData.processId + "'>" + returnedJsonData.processId + "</option>";
					$("#cbbProcessNamePopup").empty().append(optionStr);
					if (returnedJsonData.quarantineHandlingFlag == DELETE_FLAG_OFF) {
						$("#chbQuarantineHandlingFlagPopup").prop("checked", false);
					} else if (returnedJsonData.quarantineHandlingFlag == DELETE_FLAG_ON) {
						$("#chbQuarantineHandlingFlagPopup").prop("checked", true);
					}
					// status
					if (returnedJsonData.deleteFlag == DELETE_FLAG_OFF) {
						$("#chbDeletePopup").prop("checked", false);
					} else if (returnedJsonData.deleteFlag == DELETE_FLAG_ON) {
						$("#chbDeletePopup").prop("checked", true);
					}
					// last updated date time
					currentLastUpdatedTimeString = returnedJsonData.lastUpdateDate;
					// note
					$("#txtNotePopup").val(returnedJsonData.note);
					// change point
					$("#cbbChangePointCode").val(returnedJsonData.changePointCode);
					
					// change state of controls in popup based on mode
					setPopupControlState(MODE_EDIT);
					
					// display Task info popup
					showPopup($("#popupWrapper"));
				} else if (returnedJsonData == VALIDATE_BLANK_FIELDS) {
					// blank field(s)
					// display message
					jWarning(VALIDATE_BLANK_FIELDS_MESSAGE_SERVER, DIALOG_TITLE, DIALOG_OK_BUTTON);
				} else {
					// display error message
					jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
				}
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	});

	// Back button click event process
	$("#btnBack").bind("click", function() {
		goBackToPreviousPage();
	});
	
	// Register button in popup click event process
	$("#btnRegisterPopup").bind("click", function() {
		var dataObject = null;
		// check current mode
		if (currentMode == MODE_NEW) {
			// check for user input
			if (checkInputBlankFieldsStar() != "") {
				// blank field(s)
				// display message
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFieldsStar()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// insert new Task to DB
				// get Task input data
				dataTaskObject = createTaskDataObject();
				// make Ajax call to server to get data
				$.ajax({
					type: "POST",
					url: "insertData",
					contentType: "application/json",
					data: JSON.stringify(dataTaskObject),
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
								$("#txtTaskId").val("");
								$("#txtTaskName").val("");
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
							} else if (returnedJsonData == INSERT_RESULT_FAILED_TASK) {
								// failed
								// display message
								jWarning(INSERT_RESULT_FAILED_MESSAGE.replace('$1', INSERT_RESULT_FAILED_TASK), DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_FAILED_EXCEPTION) {
								// failed
								// display message
								jWarning(INSERT_RESULT_FAILED_MESSAGE.replace('$1', INSERT_RESULT_FAILED_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == VALIDATE_BLANK_FIELDS) {
								// blank field(s)
								// display message
								jWarning(VALIDATE_BLANK_FIELDS_MESSAGE_SERVER, DIALOG_TITLE, DIALOG_OK_BUTTON);
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
			if (checkInputBlankFieldsStar() != "") {
				// blank field(s)
				// display message
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFieldsStar()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// update existing Task in DB
				// get user input data
				dataTaskObject = createTaskDataObject();
				// make Ajax call to server to get data
				$.ajax({
					type: "POST",
					url: "updateData",
					contentType: "application/json",
					data: JSON.stringify(dataTaskObject),
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
								$("#txtTaskId").val("");
								$("#txtTaskName").val("");
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
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_EXCEPTION) {
								// failed
								// display message
								jWarning(UPDATE_RESULT_FAILED_MESSAGE.replace('$1', UPDATE_RESULT_FAILED_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_TASK) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
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
		// hide Task info popup
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
	
	// Draw Task data table based on search conditions
	function drawTable() {
		// variables definition
		var returnValue = "";
		var dataObject = null;
		// get search conditions
		//dataObject = createSearchConditions();
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
							// reference icon
							tableStringArray.push("<td class='align-center'>"+numberical_order+"</td>");
							// task Name
							tableStringArray.push("<td class='overflow-ellipsis'><input id='taskId' type='hidden' value='" + returnedJsonData[i].taskId + "'>" + returnedJsonData[i].taskName + "</td>");
							// lastUpdateDate
							tableStringArray.push("<td style='display:none;'><input type='hidden' value='" + returnedJsonData[i].lastUpdateDate + "' ></td>");
							// working Details
							var enterChar = "\n";
							tableStringArray.push("<td>" + returnedJsonData[i].workingDetails.replace(/\n/g, '<br>') + "</td>");
							// quarantine Handling Flag
							var quarantineHandlingFlag = (returnedJsonData[i].quarantineHandlingFlag == DELETE_FLAG_OFF) ? STATUS_DISABLED : STATUS_ENABLED;
							tableStringArray.push("<td class='align-center'>" + quarantineHandlingFlag + "</td>");
							// change point code
							var changePointCode = arrChangePoint[returnedJsonData[i].changePointCode];
							if (changePointCode == null){
								changePointCode = "";
							}
							tableStringArray.push("<td>" + changePointCode + "</td>");
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
						// scroll to top of table
						var tableHeight = calculateTableHeight();
						$("#divBody").height(tableHeight);
						fixTable();
						// update total data count UI
						// fix table height to fit page
						var tableHeight = calculateTableHeight();
						$("#divBody").height(tableHeight);
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
		return {
			taskId: $.trim($("#txtTaskId").val()),
			taskName: $.trim($("#txtTaskName").val()),
			fromRow: from,
			itemCount: ITEM_IN_ONE_PAGE
		};
	}

	// Area data object creation process, to use in Updating and Inserting user
	function createTaskDataObject() {
		return {
			taskId: $.trim($("#txtTaskIdPopup").val()),
			taskName: $.trim($("#txtTaskNamePopup").val()),
			workingDetails: $.trim($("#txtWorkingDetailsPopup").val()),
			deleteFlag: DELETE_FLAG_OFF,
			quarantineHandlingFlag: $("#chbQuarantineHandlingFlagPopup").is(":checked") ? FLAG_ON : FLAG_OFF,
			changePointCode	: $('#cbbChangePointCode').val(),
			note: $.trim($("#txtNotePopup").val()),
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
		// cbbProcessNamePopup
		$("#cbbProcessNamePopup").val(STATUS_NO_SELECT);
		$("#cbbProcessNamePopup").prop("disabled", false);
		// Task Id
		$("#txtTaskIdPopup").val("");
		// Task Name
		$("#txtTaskNamePopup").val("");
		// txtWorkingDetailsPopup
		$("#txtWorkingDetailsPopup").val("");
		// Quarantine Handling Flag
		$("#chbQuarantineHandlingFlagPopup").prop("checked", false);
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
			// initialize combobox data
			// Task Id
			$("#txtTaskIdPopup").prop("disabled", false);
			// Task Name
			$("#txtTaskNamePopup").prop("disabled", false);
			// Process Name
			$("#cbbProcessNamePopup").prop("disabled", false);
			// Working Details
			$("#txtWorkingDetailsPopup").prop("disabled", false);
			// status
			$("#chbDeletePopup").prop("disabled", false);
			// Quarantine Handling Flag
			$("#chbQuarantineHandlingFlagPopup").prop("disabled", false);
			// note
			$("#txtNotePopup").prop("disabled", false);
			// Show Farm Popup button
			$("#btnSearchPopup").show();
			// hide button show Farm popup 
			$(".area-info-popup #btnSearchPopup").show();
			// hide clear text Farm popup
			$(".area-info-popup #btnClearPopup").show();
			// Show Farm Clear button
			$("#btnClearPopup").show();
			// register button
			$("#btnRegisterPopup").show();
		} else if (mode == MODE_EDIT) {
			$("#addNew").addClass("display-none");
			$("#update").removeClass("display-none");
			// set current mode
			currentMode = MODE_EDIT;
			// Task Id
			$("#txtTaskIdPopup").prop("disabled", true);
			// Task Name
			$("#txtTaskNamePopup").prop("disabled", false);
			// Process Name
			$("#cbbProcessNamePopup").prop("disabled", true);
			// Working Details
			$("#txtWorkingDetailsPopup").prop("disabled", false);
			// status
			$("#chbDeletePopup").prop("disabled", false);
			// Quarantine Handling Flag
			$("#chbQuarantineHandlingFlagPopup").prop("disabled", false);
			// note
			$("#txtNotePopup").prop("disabled", false);
			// Show Farm Popup button
			$("#btnSearchPopup").show();
			// register button
			$("#btnRegisterPopup").show();
		} else if (mode == MODE_VIEW) {
			// set current mode
			currentMode = MODE_VIEW;
			// Task Id
			$("#txtTaskIdPopup").prop("disabled", true);
			// Task Name
			$("#txtTaskNamePopup").prop("disabled", true);
			// Process Name
			$("#cbbProcessNamePopup").prop("disabled", true);
			// Working Details
			$("#txtWorkingDetailsPopup").prop("disabled", true);
			// status
			$("#chbDeletePopup").prop("disabled", true);
			// Quarantine Handling Flag
			$("#chbQuarantineHandlingFlagPopup").prop("disabled", true);
			// note
			$("#txtNotePopup").prop("disabled", true);
			// Show Farm Popup button
			$("#btnSearchPopup").hide();
			// Show Farm Clear button
			$("#btnClearPopup").hide();
			// register button
			$("#btnRegisterPopup").hide();
		}
	}
	
	// Get data for combobox process
	function initComboboxData() {
		if (arrChangePoint != "") {
			var optionStrPopup = "";
			for (var i = 0; i < arrChangePoint.length; i++) {
				optionStrPopup += "<option value='" + i  + "'>" + arrChangePoint[i] + "</option>";
			}
			$("#cbbChangePointCode").empty()
			$("#cbbChangePointCode").append(optionStrPopup);
		}
	}
	
	// Check input: blank fields
	function checkInputBlankFieldsStar() {
		if ($.trim($("#txtTaskNamePopup").val()) == "" ){
			return taskName;
		} else if ($.trim($("#txtWorkingDetailsPopup").val()) == ""){
			return workingDetails;
		} else {
			return "";
		}
	}
});