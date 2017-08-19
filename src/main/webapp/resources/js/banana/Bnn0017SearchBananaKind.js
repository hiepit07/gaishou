/**
 * @author Khoa Le
 */

$(document).ready(function() {
	//--------------- Constants definition ---------------
	// number of items in one page in table
	var ITEM_IN_ONE_PAGE = ITEM_IN_ONE_PAGE_0017;
	
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
	// variables to store selected kind id to show in popup
	var kindIdPopup = "";
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

	// Register kind click event process
	$("#btnRegister").bind("click", function() {
		// clear all current text of popup controls
		clearPopupControl(); 
		// change state of controls in popup based on mode
		setPopupControlState(MODE_NEW);
		// display kind info popup
		showPopup($("#popupWrapper"));
	});
	
	// Edit kind info click event process
	$(document).on("click", ".edit", function() {
		// get row index
		selectedRowIndex = $(this).attr("name");
		// get id of selected kind
		kindIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).find("input").val();
		// make Ajax call to server to get data
		$.ajax({
			url: "getSingleData",
			data: { "kindId": kindIdPopup },
			type: "POST",
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				if (returnedJsonData != "") {
					// clear all current text of popup controls
					clearPopupControl();
					// kind id
					$("#txtKindIdPopup").val(returnedJsonData.kindId);
					// kind name
					$("#txtKindNamePopup").val(returnedJsonData.kindName);
					// prospective_Harvest_Amount
					$("#txtProspectiveHarvestAmountPopup").val(returnedJsonData.prospectiveHarvestAmount);
					// estimated Days Flowering
					$("#txtEstimatedDaysFloweringPopup").val(returnedJsonData.estimatedDaysFlowering);
					// estimated Days Bagging
					$("#txtEstimatedDaysBaggingPopup").val(returnedJsonData.estimatedDaysBagging);
					// estimated Days Harvest
					$("#txtEstimatedDaysHarvestPopup").val(returnedJsonData.estimatedDaysHarvest);
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
					// change state of controls in popup based on mode
					setPopupControlState(MODE_EDIT);
					
					// display kind info popup
					showPopup($("#popupWrapper"));
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
	
	// Delete Banana Kind click event process
	$(document).on("click", ".delete", function() {
		var curContext = $(this) ;
		// get row index
		selectedRowIndex = curContext.attr("name");
		// display confirmation message
		jQuestion_warning(DELETE_CONFIRM_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
			if (val) {
				
				// get id of selected Banana Kind
				kindIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).find("input").val();
				var lastUpdate = $("#row" + selectedRowIndex).find("td").eq(2).find("input").val();
				bananaKindData  = {kindId : kindIdPopup,
						  			lastUpdateDate: lastUpdate};
				// make Ajax call to server to delete data
				$.ajax({
					url: "deleteData",
					contentType: "application/json",
					data: JSON.stringify(bananaKindData),
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
								$("#txtKindName").val("");
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
							} else if (returnedJsonData == DELETE_RESULT_FAILED_KIND) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == DELETE_RESULT_FAILED_UPDATE_DATE) {
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
			} 
			else if (!checkInputBlankFields()) {
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (checkInputNumberFields() != "") {
				jWarning(VALIDATE_NUMBER_FIELDS_MESSAGE.replace('$1', checkInputNumberFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (!checkDoubleField($("#txtProspectiveHarvestAmountPopup").val())) {
				jWarning(MESSAGE_FORMAT_FILED_IS_DOUBLE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// insert new kind to DB
				// get kind input data
				dataObject = createBananaKindObject();
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
							if (returnedJsonData == INSERT_RESULT_SUCCESSFUL) {
								// search data again
								// reset variables
								resetVariables();
								$("#txtKindName").val("");
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
							} else if (returnedJsonData == INSERT_RESULT_FAILED_KIND) {
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
			if (checkInputBlankFieldsStar() != "") {
				// blank field(s)
				// display message
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFieldsStar()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} 
			else if (!checkInputBlankFields()) {
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (checkInputNumberFields() != "") {
				jWarning(VALIDATE_NUMBER_FIELDS_MESSAGE.replace('$1', checkInputNumberFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (!checkDoubleField($("#txtProspectiveHarvestAmountPopup").val())) {
				jWarning(MESSAGE_FORMAT_FILED_IS_DOUBLE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// update existing kind in DB
				// get kind input data
				dataObject = createBananaKindObject();
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
							if (returnedJsonData == UPDATE_RESULT_SUCCESSFUL) {
								// search data again
								// reset variables
								resetVariables();
								$("#txtKindName").val("");
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
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_KIND) {
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
	// Clear all current text of popup controls
	function clearPopupControl() {
		// kind id
		$("#txtKindIdPopup").val("");
		// kind name
		$("#txtKindNamePopup").val("");
		// Estimated Days Flowering
		$("#txtEstimatedDaysFloweringPopup").val("");
		// Estimated Days Bagging
		$("#txtEstimatedDaysBaggingPopup").val("");
		// Estimated Days Harvest
		$("#txtEstimatedDaysHarvestPopup").val("");
		// note
		$("#txtNotePopup").val("");
		$("#txtProspectiveHarvestAmountPopup").val("");
	}
	// Cancel button in popup click event process
	$("#btnCancelPopup").bind("click", function() {
		// hide kind info popup
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
	
	// Draw kind data table based on search conditions
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
							tableStringArray.push("<td class='align-center'>"+numberical_order+"</td>");
							// kind name
							tableStringArray.push("<td class='overflow-ellipsis'><input id='kindId' type='hidden' value='" + returnedJsonData[i].kindId + "'>" + returnedJsonData[i].kindName + "</td>");
							// lastUpdateDate
							tableStringArray.push("<td style='display:none;'><input type='hidden' value='" + returnedJsonData[i].lastUpdateDate + "' ></td>");
							// estimated Days Flowering
							tableStringArray.push("<td class='overflow-ellipsis align-right' >" + returnedJsonData[i].prospectiveHarvestAmount + "</td>");
							// estimated Days Flowering
							tableStringArray.push("<td class='overflow-ellipsis align-right'>" +  returnedJsonData[i].estimatedDaysFlowering + "</td>");
							// estimated Days Bagging
							tableStringArray.push("<td class='overflow-ellipsis align-right'>" +  returnedJsonData[i].estimatedDaysBagging + "</td>");
							// estimated Days Harvest
							tableStringArray.push("<td class='overflow-ellipsis align-right'>" +  returnedJsonData[i].estimatedDaysHarvest + "</td>");
							// Update icon
							tableStringArray.push("<td><img class='edit cursor-pointer align-center' name='" + i + "' src='" + rootPath + "/resources/img/icon_edit.png?date=" + icon_editDate + "'></td>");
							// delete icon
							tableStringArray.push("<td><img class='delete cursor-pointer align-center' name='" + i + "' src='" + rootPath + "/resources/img/icon_del.png?date=" + icon_delDate + "'></td>");
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
	
	// Search conditions object creation process
	function createSearchConditions() {
		return {
			kindName: $.trim($("#txtKindName").val()).replace(/\\/g, "\\\\").replace(/%/g, "\\%"),
			//deleteFlag: $("#delete_flag_sl").val(),
			fromRow: from,
			itemCount: ITEM_IN_ONE_PAGE
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
	
	// kind data object creation process, to use in Updating and Inserting kind
	function createBananaKindObject() {
		return {
			kindId: $.trim($("#txtKindIdPopup").val()),
			kindName: $.trim($("#txtKindNamePopup").val()),
			// prospective_Harvest_Amount
			prospectiveHarvestAmount : $.trim($("#txtProspectiveHarvestAmountPopup").val()),
			expectedAmountHarvest: $.trim($("#txtExpectedAmountHarvestPopup").val()),
			// txtEstimatedDaysFloweringPopup
			estimatedDaysFlowering : $.trim($("#txtEstimatedDaysFloweringPopup").val()),
			// txtEstimatedDaysBaggingPopup
			estimatedDaysBagging: $.trim($("#txtEstimatedDaysBaggingPopup").val()),
			// txtEstimatedDaysHarvestPopup
			estimatedDaysHarvest: $.trim($("#txtEstimatedDaysHarvestPopup").val()),
			deleteFlag: DELETE_FLAG_OFF,
			note: $.trim($("#txtNotePopup").val()),
			lastUpdateDate: currentLastUpdatedTimeString
		};
	}
	// Reset variables process
	function resetVariables() {
		totalResultCount = 0;
		currentPage = 1;
		from = 0;
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
			// kind id
			$("#txtKindIdPopup").prop("disabled", false);
			$("#txtProspectiveHarvestAmountPopup").prop("disabled", false);
			// kind name
			$("#txtKindNamePopup").prop("disabled", false);
			// txtEstimatedDaysFloweringPopup
			$("#txtEstimatedDaysFloweringPopup").prop("disabled", false);
			// txtEstimatedDaysBaggingPopup
			$("#txtEstimatedDaysBaggingPopup").prop("disabled", false);
			// txtEstimatedDaysHarvestPopup
			$("#txtEstimatedDaysHarvestPopup").prop("disabled", false);
			// status
			$("#chbDeletePopup").prop("disabled", false);
			// note
			$("#txtNotePopup").prop("disabled", false);
			// register button
			$("#btnRegisterPopup").show();
		} else if (mode == MODE_EDIT) {
			$("#addNew").addClass("display-none");
			$("#update").removeClass("display-none");
			// set current mode
			currentMode = MODE_EDIT;
			// kind id
			$("#txtKindIdPopup").prop("disabled", true);
			// kind name
			$("#txtKindNamePopup").prop("disabled", false);
			// txtEstimatedDaysFloweringPopup
			$("#txtEstimatedDaysFloweringPopup").prop("disabled", false);
			// txtEstimatedDaysBaggingPopup
			$("#txtEstimatedDaysBaggingPopup").prop("disabled", false);
			// txtEstimatedDaysHarvestPopup
			$("#txtEstimatedDaysHarvestPopup").prop("disabled", false);
			// status
			$("#chbDeletePopup").prop("disabled", false);
			// note
			$("#txtNotePopup").prop("disabled", false);
			// register button
			$("#btnRegisterPopup").show();
		} else if (mode == MODE_VIEW) {
			// set current mode
			currentMode = MODE_VIEW;
			// kind id
			$("#txtKindIdPopup").prop("disabled", true);
			// kind name
			$("#txtKindNamePopup").prop("disabled", true);
			// txtEstimatedDaysFloweringPopup
			$("#txtEstimatedDaysFloweringPopup").prop("disabled", true);
			// txtEstimatedDaysBaggingPopup
			$("#txtEstimatedDaysBaggingPopup").prop("disabled", true);
			// txtEstimatedDaysHarvestPopup
			$("#txtEstimatedDaysHarvestPopup").prop("disabled", true);
			// status
			$("#chbDeletePopup").prop("disabled", true);
			// note
			$("#txtNotePopup").prop("disabled", true);
			// register button
			$("#btnRegisterPopup").hide();
		}
	}
	
	// Check input: blank fields
	function checkInputBlankFields() {
		if ($.trim($("#txtKindNamePopup").val()) == "" || $("#txtEstimatedDaysFloweringPopup").val() == ""
			|| $("#txtEstimatedDaysBaggingPopup").val() == ""|| $("#txtEstimatedDaysHarvestPopup").val() == ""
			|| $("#txtProspectiveHarvestAmountPopup").val() == ""	) {
			return false;
		} else {
			return true;
		}
	}

	// check input Area Id
	function checkKindIdFormat() {
		var areaId = $("#txtKindNamePopup").val();
		if (!/^K\d{3}$/.test(areaId)) {
			return false;
		} else {
			return true;
		}
	}

	// check data is double value ?
	function checkDoubleField(str) {
		if (str.length > 8) {
			return false;
		} else {
			if (str.indexOf(".") == -1) {
				if (str.length <= 4) {
					return true;
				} else {
					return false;
				}
			} else {
				var docs =  str.indexOf(".");
				var lastDocs = str.lastIndexOf(".");
				var firstStr = str.substring(0, docs);
				var lastStr = str.substring(docs + 1, str.length);
				if (firstStr.length > 0 && firstStr.length <= 4) {
					if (lastStr.length > 0 && lastStr.length <= 3 && docs == lastDocs) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
	}
	
	// check number day ProspectiveHarvestAmount, Flowering, EstimatedDaysBagging, EstimatedDaysHarvest on popup
	$(document).on('keypress', '#txtProspectiveHarvestAmountPopup', function(e) {
		  if (e.charCode != 0 && e.charCode != 46 && (e.charCode < 48 || e.charCode > 57)){
		   e.preventDefault();
		  }
		  if(this.value == 0){
			  $(this).val("");
		  }
	});
	$(document).on('change', '#txtProspectiveHarvestAmountPopup', function(e) {
		if ($('#txtProspectiveHarvestAmountPopup').filter(function() {
	        	return parseInt(this.value, 0) !== 0;
	    	}).length === 0) {
			$(this).val("");
		}
	});
	$(document).on('keypress', '#txtEstimatedDaysFloweringPopup', function(e) {
		  if (e.charCode != 0 && (e.charCode < 48 || e.charCode > 57)){
		   e.preventDefault();
		  }
		  if(this.value == 0){
			  $(this).val("");
		  }
	});
	$(document).on('change', '#txtEstimatedDaysFloweringPopup', function(e) {
		if ($('#txtEstimatedDaysFloweringPopup').filter(function() {
	        	return parseInt(this.value, 0) !== 0;
	    	}).length === 0) {
			$(this).val("");
		}
	});
	$(document).on('keypress', '#txtEstimatedDaysBaggingPopup', function(e) {
		  if (e.charCode != 0 && (e.charCode < 48 || e.charCode > 57)){
		   e.preventDefault();
		  }
		  if(this.value == 0){
			  $(this).val("");
		  }
	});
	$(document).on('change', '#txtEstimatedDaysBaggingPopup', function(e) {
		if ($('#txtEstimatedDaysBaggingPopup').filter(function() {
	        	return parseInt(this.value, 0) !== 0;
	    	}).length === 0) {
			$(this).val("");
		}
	});
	$(document).on('keypress', '#txtEstimatedDaysHarvestPopup', function(e) {
		  if (e.charCode != 0 && (e.charCode < 48 || e.charCode > 57)){
		   e.preventDefault();
		  }
		  if(this.value == 0){
			  $(this).val("");
		  }
	});
	$(document).on('change', '#txtEstimatedDaysHarvestPopup', function(e) {
		if ($('#txtEstimatedDaysHarvestPopup').filter(function() {
	        	return parseInt(this.value, 0) !== 0;
	    	}).length === 0) {
			$(this).val("");
		}
	});
	
	// Check input: blank fields
	function checkInputBlankFieldsStar() {
		if ($.trim($("#txtKindNamePopup").val()) == "" ){
			return kindName;
		} else if ($("#txtProspectiveHarvestAmountPopup").val() == ""){
			return prospectiveHarvestAmount;
		} else if ($("#txtEstimatedDaysFloweringPopup").val() == "") {
			return estimatedDaysFlowering;
		}else if ($("#txtEstimatedDaysBaggingPopup").val() == ""){
			return estimatedDaysBagging;
		} else if ($("#txtEstimatedDaysHarvestPopup").val() == "") {
			return estimatedDaysHarvest;
		} else {
			return "";
		}
	}

	function checkInputNumberFields(){
		var errorCount = 0;
		if (isNaN($("#txtProspectiveHarvestAmountPopup").val())) {
			return prospectiveHarvestAmount;
		} else if (isNaN($("#txtEstimatedDaysFloweringPopup").val())) {
			return estimatedDaysFlowering;
		} else if (isNaN($("#txtEstimatedDaysBaggingPopup").val())) {
			return estimatedDaysBagging;
		} else if (isNaN($("#txtEstimatedDaysHarvestPopup").val())) {
			return estimatedDaysHarvest;
		} else {
			return ""
		}
		return errorStr;
	}
});