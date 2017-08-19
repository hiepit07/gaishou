$(document).ready(function() {
	//--------------- Constants definition ---------------
	// number of items in one page in table
	var ITEM_IN_ONE_PAGE = ITEM_IN_ONE_PAGE_0045;
	// when Area selected nothing
	// when process selected nothing
	var STATUS_NO_SELECT = "-2";
	// when updating to DB successfully
	var UPDATE_RESULT_SUCCESSFUL = "1";
	// when updating to DB failed
	var UPDATE_RESULT_FAILED = "-1";
	// when inserting to DB successfully
	var INSERT_RESULT_SUCCESSFUL = "1";
	// when inserting to DB failed
	var INSERT_RESULT_FAILED = "-1";
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
	// variable to store current selected mode
	var currentMode = "";
	// search conditions 
	var searchConditions = null;
	// display current date
	$("#txtCurrentDate").text(getCurrentDate() + " (" + DAY_OF_WEEK + ") ");
	// load farm, kind, process, task name combobox
	loadDataCombobox(false);

	// Search button event process
	$("#btnSearch").bind("click", function() {
		numberical_order = 0;
		$("#txtfarmName").text($("#cbbFarmName").find("option:selected").text());
		$("#txtkindName").text($("#cbbKindName").find("option:selected").text());
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
	
	// Farm combo box change event process
	$("#cbbFarmName").bind("change", function() {
		loadKinkData(false);
	});
	
	//search all
	$("#btnSearch").trigger("click");
	
	// Draw info data table based on search conditions
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
						$(".tbl-farm-table").find("tbody").remove();
						// create table starts
						var tableStringArray = [];
						// add tbody open tag
						tableStringArray.push("<tbody>");
						for (var i = 0; i < returnedJsonData.length; i++) {
							numberical_order++;
							// row open tag
							if(returnedJsonData[i].quarantimehandlingflag == "1") {
								tableStringArray.push("<tr class='yellow' id='row" + i + "'>");
								tableStringArray.push("<td class='align-center'>"+numberical_order+"</td>");
								//quarantine_handling_flag
								tableStringArray.push("<td class='align-center'>" + '●' + "</td>");
								
								tableStringArray.push("<td>" + returnedJsonData[i].processName + "</td>");
								// task Name
								tableStringArray.push("<td>" + returnedJsonData[i].taskName + "</td>");
								//workingdetails
								var enterChar = "\n";
								tableStringArray.push("<td class='overflow-ellipsis-detail'>" + returnedJsonData[i].workingdetails.replace(/\n/g, '<br>') + "</td>");
								//note
								tableStringArray.push("<td class='overflow-ellipsis-note'>" + returnedJsonData[i].note.replace(/\n/g, '<br>') + "</td>");
								// row close tag
								tableStringArray.push("</tr>");
							} else {
								tableStringArray.push("<tr id='row" + i + "'>");
								tableStringArray.push("<td class='align-center'>"+numberical_order+"</td>");
								//quarantine_handling_flag
								tableStringArray.push("<td class='align-center'>" + '' + "</td>");
								
								tableStringArray.push("<td>" + returnedJsonData[i].processName + "</td>");
								// task Name
								tableStringArray.push("<td>" + returnedJsonData[i].taskName + "</td>");
								//workingdetails
								var enterChar = "\n";
								tableStringArray.push("<td class='overflow-ellipsis-detail'>" + returnedJsonData[i].workingdetails.replace(/\n/g, '<br>') + "</td>");
								//note
								tableStringArray.push("<td class='overflow-ellipsis-note'>" + returnedJsonData[i].note.replace(/\n/g, '<br>') + "</td>");
								// row close tag
								tableStringArray.push("</tr>");
							}
							
						}
						// add tbody close tag
						tableStringArray.push("</tbody>");
						// append all created string to table
						$(".tbl-farm-table").append(tableStringArray.join(''));
						// show search result
						$(".cont-box").removeClass("display-none");
						// fix table header and body when scrolling only the table body
						// Fix table height to fit page
						var tableHeight = calculateTableHeight();
						$("#divBody").height(tableHeight - 20);
						fixTable();
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
					$(".tbl-farm-table").find("tbody").remove();
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
	
	// Load farm, kind, process, task name combobox data
	function loadDataCombobox(isPopupMode) {
		$.ajax({
			type: "POST",
			url: "getFarmData",
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				// create option string
				var optionStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
				if (returnedJsonData != "" && returnedJsonData.length > 0) {
					for (var i = 0; i < returnedJsonData.length; i++) {
						optionStr += "<option value='" + returnedJsonData[i].farmId + "'>" + returnedJsonData[i].farmName + "</option>";
					}
				}
				if (!isPopupMode) {
					// set options to combobox
					$("#cbbFarmName").empty().append(optionStr);
					// enable combobox
					$("#cbbFarmName").prop("disabled", false);
				}
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
		loadKinkData(isPopupMode);
		$.ajax({
			type: "POST",
			url: "getProcessData",
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				// create option string
				var optionStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
				if (returnedJsonData != "" && returnedJsonData.length > 0) {
					for (var i = 0; i < returnedJsonData.length; i++) {
						optionStr += "<option value='" + returnedJsonData[i].processId + "'>" + returnedJsonData[i].processName + "</option>";
					}
				}
				if (!isPopupMode) {
					// set options to combobox
					$("#cbbProcessName").empty().append(optionStr);
					// enable combobox
					$("#cbbProcessName").prop("disabled", false);
				}
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
		$.ajax({
			type: "POST",
			url: "getTaskData",
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				// create option string
				var optionStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
				if (returnedJsonData != "" && returnedJsonData.length > 0) {
					for (var i = 0; i < returnedJsonData.length; i++) {
						optionStr += "<option value='" + returnedJsonData[i].taskId + "'>" + returnedJsonData[i].taskName + "</option>";
					}
				}
				if (!isPopupMode) {
					// set options to combobox
					$("#cbbTaskName").empty().append(optionStr);
					// enable combobox
					$("#cbbTaskName").prop("disabled", false);
				}
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	}
	
	function loadKinkData(isPopupMode) {
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "getKindData",
			data: { "farmId": $("#cbbFarmName").val() },
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				// create option string
				var optionStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
				if (returnedJsonData != "" && returnedJsonData.length > 0) {
					for (var i = 0; i < returnedJsonData.length; i++) {
						optionStr += "<option value='" + returnedJsonData[i].kindId + "'>" + returnedJsonData[i].kindName + "</option>";
					}
				}
				if (!isPopupMode) {
					// set options to combobox
					$("#cbbKindName").empty().append(optionStr);
					// enable combobox
					$("#cbbKindName").prop("disabled", false);
				}
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	}
	
	// Search conditions object creation cultivation
	function createSearchConditions() {
		// FarmId
		var farmId = $("#cbbFarmName").val();
		// kindId
		var kindId = $("#cbbKindName").val();
		// processId
		var processId = $("#cbbProcessName").val();
		// taskId
		var taskId = $("#cbbTaskName").val();
		return {
			farmId: farmId,
			kindId: kindId,
			processId: processId,
			taskId: taskId,
			fromRow: from,
			itemCount: ITEM_IN_ONE_PAGE
		};
		
	}
	// Reset variables process
	function resetVariables() {
		totalResultCount = 0;
		currentPage = 1;
		from = 0;
	}
	// Update total data count process
	function setDataCounts() {
		$("#txtCounts").text(totalResultCount.toString().replace(/^(-?\d+)(\d{3})/, "$1, $2"));
	}
	
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

	// Back button event process
	$("#btnBack").bind("click", function() {
		goBackToPreviousPage();
	});

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
});