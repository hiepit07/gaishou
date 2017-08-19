/**
 * @author Hieu Dao
 */

//--------------- Global variables definition ---------------
var unreTable = [];
var rtedTable = [];

$(document).ready(function() {
	//--------------- Constants definition ---------------
	var NEXT_SCREEN_NAME = "0091";
	//--------------- Variables definition ---------------
	// application path
	var rootPath = getContextPath();
	// search result total data count
	var totalResultCount = 0;
	// variable to store action selected mode
	var actionMode = "";

	// Display current date
	$("#txtCurrentDate").text(getCurrentDate() + " (" + DAY_OF_WEEK + ") ");

	// Initialize combo box data
	resetPage();

	// Hover user row
	$(document).on("mouseover", ".acRow", function() {

		// Single hover
		$(".acRow").removeClass("hvRow");
		// Multi hover
		$(this).toggleClass("hvRow");
	});

	// Hover out task row
	$(document).on("mouseout", ".acRow", function() {
		$(".acRow").removeClass("hvRow");
	})

	// Select user row
	function unBindClickRow() {
		$(".acRow").unbind("click");
		$(".acRow").bind("click", function() {
			// get data from page to set for variables
			var id = $(this).find("td").eq(1).find("#txtProcessId").val();
	
			// check table has class 'tbl-process-re' then add class crRow to tr
			if($(this).parent().parent().hasClass("tbl-process-un")) {
				// Single select
				if($(this).hasClass("slRow")) {
					$(".tbl-process-un .acRow").removeClass("slRow");
					$(this).removeClass("slRow");
				} else {
					$(".tbl-process-un .acRow").removeClass("slRow");
					$(this).addClass("slRow");
				}
			}
	
			// check table has class 'tbl-process-re' then add class crRow to tr
			if($(this).parent().parent().hasClass("tbl-process-re")) {
				// Single select
				if($(this).hasClass("slRow")) {
					$(".tbl-process-re .acRow").removeClass("slRow");
					$(this).removeClass("slRow");
				} else {
					$(".tbl-process-re .acRow").removeClass("slRow");
					$(this).addClass("slRow");
				}
				
				// Single select
				if($(this).hasClass("crRow")) {
					$(".acRow").removeClass("crRow");
					$(this).removeClass("crRow");
				} else {
					$(".acRow").removeClass("crRow");
					$(this).addClass("crRow");
				}
			}
	
			// set status action button
			setActionStatus();
			// select process detail
			processDetail(id);
		});
	}

	// Get button click event
	$(document).on("click", "#btn_get",function() {
		actionGetBack(SL_ROW, ACTION_GET);
	});

	// Back button click event
	$(document).on("click", "#btn_back",function() {
		actionGetBack(SL_ROW, ACTION_BACK);
	});

	// Get All button click event
	$(document).on("click", "#btn_getall",function() {
		actionGetBack(AC_ROW, ACTION_GETALL);
	});

	// Back All button click event
	$(document).on("click", "#btn_backall",function() {
		actionGetBack(AC_ROW, ACTION_BACKALL);
	});

	// Up button click event
	$(document).on("click", "#btn_up",function() {
		moveRow(ACTION_UP);
	});

	// Down button click event
	$(document).on("click", "#btn_down",function() {
		moveRow(ACTION_DOWN);
	});

	// First button click event
	$(document).on("click", "#btn_first",function() {
		jumpRow(ACTION_FIRST);
	});

	// Last button click event
	$(document).on("click", "#btn_last",function() {
		jumpRow(ACTION_LAST);
	});

	// Register button click event process
	$("#btnRegister").bind("click", function() {

		// variables definition
		var unTable = $(".tbl-process-un .acRow");
		var reTable = $(".tbl-process-re .acRow");
		var cultivationObject = null;
		var cultivationList = [];
		var regiFlag = false;

		// get data from page to set for variables
		var faId = $("#txtHideFarmId").val();
		var kiId = $("#txtHideKindId").val();
		
		// check Process data is change
		regiFlag = checkProcessChange();
		
		if(regiFlag) {
			for(var i = 0; i < reTable.length; i ++) {
				cultivationObject = {
					farmId: faId,
					kindId: kiId,
					processId: $(reTable[i]).find('#txtProcessId').val(),
					processOrder: i + 1,
					lastUpdateDate: $(reTable[i]).find('#txtLastUpdateDate').val()
				}
				cultivationList.push(cultivationObject);
			}
			for(var i = 0; i < unTable.length; i ++) {
				cultivationObject = {
					farmId: faId,
					kindId: kiId,
					processId: $(unTable[i]).find('#txtProcessId').val(),
					processOrder: 0,
					lastUpdateDate: $(unTable[i]).find('#txtLastUpdateDate').val()
				}
				cultivationList.push(cultivationObject);
			}
			// make Ajax call to server to get data
			$.ajax({
				type: "POST",
				url: "updateData",
				contentType: "application/json",
				data: JSON.stringify(cultivationList),
				async: false,
				success: function(returnedJsonData) {

					// Check returned value from server
					if (checkSessionTimeout(returnedJsonData) == 1) return;

					// Read data from Json for Form
					if (returnedJsonData != "") {

						// Check returned value from server
						if (returnedJsonData == UPDATE_RESULT_SUCCESSFUL) {

							// Reset page
							resetPage();

							// Display message
							jInfo(UPDATE_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
						} else if (returnedJsonData == UPDATE_RESULT_FAILED_CULTIVATION_PROCESS_RESULT) {
							// Failed
							// Display message
							jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
						} else if (returnedJsonData == UPDATE_RESULT_FAILED_CULTIVATION_PROCESS_LAST_UPDATE_DATE_RESULT) {
							// Failed
							// Display message
							jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
						} else if (returnedJsonData == UPDATE_RESULT_FAILED_EXCEPTION) {
							// Failed
							// Display message
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
	
	// Back button click event process
	$("#btnBack").bind("click", function() {

		// variables definition
		var cultivationObject = null;
		var regiFlag = false;

		// check Process data is change
		regiFlag = checkProcessChange();
		
		if(regiFlag) {
			jQuestion_warning(PROCESS_CHANGE_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
				if (val) {
					goBackToPreviousPage();
				}
			});
		} else {

			// get data from page to set for variables
			var faId = $("#txtHideFarmId").val();
			var kiId = $("#txtHideKindId").val();
			// create cultivation data
			cultivationObject = {
				farmId: faId,
				kindId: kiId,
			}
			// make Ajax call to server to get data
			$.ajax({
				type: "POST",
				url: "getProcessDataTotal",
				contentType: "application/json",
				data: JSON.stringify(cultivationObject),
				async: false,
				success: function(returnedJsonData) {

					// Check returned value from server
					if (checkSessionTimeout(returnedJsonData) == 1) return;

					// Read data from Json for Form
					if (returnedJsonData > 0) {
						jQuestion_warning(PROCESS_UNREGISTERED_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
							if (val) {
								goBackToPreviousPage();
							}
						});
					} else {
						goBackToPreviousPage();
					}
				}, 
				error: function(e) {
					// display error message
					jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
				}
			});
		}
	});

	// Get data for text box and process table process
	function initData() {

		// set height cultivation detail table on first load
		$("#divBody").height(calculateBnn0092TableBodyHeight());
		$("#divBodyRight").height(calculateBnn0092TableBodyHeight());

		// get farm, kind from cultivation data set to text box
		if (cultivationData != "") {
			$("#txtFarmName").val(decodeURIComponent(cultivationData.farmName));
			$("#txtHideFarmId").val(cultivationData.farmId);
			$("#txtKindName").val(decodeURIComponent(cultivationData.kindName));
			$("#txtHideKindId").val(cultivationData.kindId);
		}

		// create data object
		dataObject = {
			farmId: cultivationData.farmId,
			kindId: cultivationData.kindId,
		}

		// check Farm and Kind data is not value
		if(	$("#txtHideFarmId").val() == "" || $("#txtHideKindId").val() == "" ||
			$("#txtHideFarmId").val() == null || $("#txtHideKindId").val() == null){
			// go to Bnn0091
			// create parameter object
			var parameterObj = {
				"url": rootPath + '/' + NEXT_SCREEN_NAME + '/'
			};
			// save to history
			savePageToHistory(parameterObj);
			window.location = rootPath + '/' + NEXT_SCREEN_NAME + '/';
		} else {
			// make Ajax call to server to get data
			$.ajax({
				type: "POST",
				url: "initData",
				contentType: "application/json",
				data: JSON.stringify(dataObject),
				async: false,
				success: function(returnedJsonData) {

					// Check returned value from server
					if (checkSessionTimeout(returnedJsonData) == 1) return;

					// Read data from Json for Form
					if (returnedJsonData.length > 0 && (returnedJsonData[0].length > 0 || returnedJsonData[1].length > 0)) {
						unreTable = returnedJsonData[0];
						rtedTable = returnedJsonData[1];
						// draw two process table list
						drawTable();
					} else {
						// display error message
						jInfo(SEARCH_RESULT_NO_DATA_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
					}
				},
				error: function(e) {
					// display error message
					jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
				}
			});
		}
	}
	
	// check Process data is change
	function checkProcessChange() {
		// variables definition
		var reTable = $(".tbl-process-re .acRow");
		var regiFlag = false;

		if(rtedTable.length != reTable.length) {
			regiFlag = true;
		} else if (rtedTable.length == reTable.length) {
			for(var i = 0; i < reTable.length; i ++) {
				var processIdOld = rtedTable[i].processId;
				var processIdNew = $(reTable[i]).find('#txtProcessId').val();
				if(	processIdOld != processIdNew) {
					regiFlag = true;
				}

				if(regiFlag) {
					break;
				}
			}
		}
		return regiFlag;
	}
	
	// set btnRegister button status
	function setButtonRegisterStatus() {
		var btnRegister = $("#btnRegister");
		var regiFlag = false;

		// check Task data is change
		regiFlag = checkProcessChange();
		if(regiFlag) {
			btnRegister.removeClass("btn-disable");
			btnRegister.prop("disabled", false);
		} else {
			btnRegister.addClass("btn-disable");
			btnRegister.prop("disabled", true);
		}
	}

	// View process info select event process
	function processDetail(id) {

		// variable definition
		var dataObject = {};

		// create process object
		dataObject = {
			farmId: cultivationData.farmId,
			kindId: cultivationData.kindId,
			processId: id
		}

		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "getProcessDetailData",
			contentType: "application/json",
			data: JSON.stringify(dataObject),
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
						// clear table
						$("#tblBodyBottom").find("tbody").remove();
						// create table starts
						var tableStringArray = [];
						// add tbody open tag
						tableStringArray.push("<tbody>");
						for (var i = 0; i < returnedJsonData.length; i++) {
							// row open tag
							tableStringArray.push("<tr id='row" + i + "'>");
							// task order
							tableStringArray.push("<td>" + (i + 1) + "</td>");
							// process name
							tableStringArray.push("<td class='overflow-ellipsis'><span>" + returnedJsonData[i].processName + "</span></td>");
							// task name
							tableStringArray.push("<td class='overflow-ellipsis'><span>" + returnedJsonData[i].taskName + "</span></td>");
							// working details
							if(returnedJsonData[i].workingDetails != null){
								tableStringArray.push("<td>" + returnedJsonData[i].workingDetails.replace(/\n/g, '<br>') + "</td>");
							} else {
								tableStringArray.push("<td></td>");
							}
							// note
							if(returnedJsonData[i].note != null){
								tableStringArray.push("<td>" + returnedJsonData[i].note.replace(/\n/g, '<br>') + "</td>");
							} else {
								tableStringArray.push("<td></td>");
							}
							// quarantine handling flag
							if(returnedJsonData[i].quarantineHandlingFlag == FLAG_ON){
								tableStringArray.push("<td>" + STATUS_DONE + "</td>");
							} else if(returnedJsonData[i].quarantineHandlingFlag == FLAG_OFF) {
								tableStringArray.push("<td>" + STATUS_NONE + "</td>");
							}
							// change point code
							var changePointCode = ARR_CHANGE_POINT[returnedJsonData[i].changePointCode];
							if (changePointCode == null){
								changePointCode = "";
							}
							tableStringArray.push("<td>" + changePointCode + "</td>");
							// row close tag
							tableStringArray.push("</tr>");
						}
						// add tbody close tag
						tableStringArray.push("</tbody>");
						// append all created string to table
						$("#tblBodyBottom").append(tableStringArray.join(''));
						// fix table header and body when scrolling only the table body
						fixTableBottom();
						// scroll to top of table
						$("#tblBodyBottom").scrollTop(0).scrollLeft(0);
					}
				} else {
					// display error message
					jWarning(SEARCH_RESULT_NO_DATA_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
					// clear table
					$("#tblBodyBottom").find("tbody").remove();
					totalResultCount = 0;
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

	// Save selected data of process table based
	function actionGetBack(rowTypev, actionv) {
		// variables definition
		var crntRow = null;
		var leftNo = 1;
		var rightNo = 1;
		var crntId = "";
		var crntName = "";
		var newLeftRow = "";
		var newRightRow = "";
		var rowType = "";
		var action = "";

		rowType = rowTypev;
		action = actionv;

		// repair table 
		$(rowType).each(function() {
			crntRow = $(this);
			crntId = crntRow.find("td").eq(1).find("#txtProcessId").val();
			crntName = crntRow.find("td").eq(1).text();
			crntDate = crntRow.find("td").eq(1).find("#txtLastUpdateDate").val();
			if((action != ACTION_BACK && action != ACTION_BACKALL) && crntRow.parent().parent().hasClass("tbl-process-un")) {
				crntRow.remove();
				newRightRow = "<tr id='row' class='acRow'>";
				newRightRow = newRightRow + "<td></td>";
				newRightRow = newRightRow + "<td>" + escapeHtml(crntName) + "<input id='txtProcessId' type='hidden' value='" + crntId + "'/>" +
																			"<input id='txtLastUpdateDate' type='hidden' value='" + crntDate + "'/>" + "</td>";
				newRightRow = newRightRow + "</tr>";
				// append all created string to table
				$($(".tbl-process-re tr")[$(".tbl-process-re tr").length-1]).before(newRightRow);
			} else if((action != ACTION_GET && action != ACTION_GETALL) && crntRow.parent().parent().hasClass("tbl-process-re")) {
				crntRow.remove();
				newLeftRow = "<tr id='row' class='acRow'>";
				newLeftRow = newLeftRow + "<td></td>";
				newLeftRow = newLeftRow + "<td>" + escapeHtml(crntName) + 	"<input id='txtProcessId' type='hidden' value='" + crntId + "'/>" +
																			"<input id='txtLastUpdateDate' type='hidden' value='" + crntDate + "'/>" + "</td>";
				newLeftRow = newLeftRow + "</tr>";
				// append all created string to table
				$($(".tbl-process-un tr")[$(".tbl-process-un tr").length-1]).before(newLeftRow);
			}
		});

		fixTable();
		fixTableRight();

		// repair row no
		$(".acRow").each(function() {
			crntRow = $(this);
			if(crntRow.parent().parent().hasClass("tbl-process-un")) {
				if(leftNo <= $(".tbl-process-un .acRow").length) {
					crntRow.find("td").eq(0).text(leftNo);
					leftNo++;
				}
			} else if(crntRow.parent().parent().hasClass("tbl-process-re")) {
				if(rightNo <= $(".tbl-process-re .acRow").length) {
					crntRow.find("td").eq(0).text(rightNo);
					rightNo++;
				}
			}
		});

		// set status action button
		setActionStatus();
		// set Register button is disable
		setButtonRegisterStatus();
	}

	// Move up/down row
	function moveRow(action) {
		// variable definition
		if($(".tbl-process-re tr").hasClass("crRow")){
			var crntRow = $(".crRow");
			var newRow = null;
			var newProcess = {};
			var oldProcess = {};
		
			if(action == ACTION_UP) {// if action up
				newRow = $($(".tbl-process-re .acRow")[($(".tbl-process-re .crRow").index() - 1)]);
			} else if(action == ACTION_DOWN) {// if action down
				newRow = $($(".tbl-process-re .acRow")[($(".tbl-process-re .crRow").index() + 1)]);
			}
			
			// 1.check current row index > 0 and action up
			// 2.check current row index is not last row and action down
			// then get row data to process object and update table data
			if(($(".crRow").index() > 0 && action == ACTION_UP) || 
				($(".crRow").index() < ($(".tbl-process-re .acRow").length - 1) && action == ACTION_DOWN)) {
				// create new process is current process
				newProcess = {
						no : crntRow.index(),
						id : crntRow.find("td").eq(1).find("#txtProcessId").val(),
						name : crntRow.find("td").eq(1).text(),
						date : crntRow.find("td").eq(1).find("#txtLastUpdateDate").val()
				};
				// create old process
				oldProcess = {
						no : newRow.index(),
						id : newRow.find("td").eq(1).find("#txtProcessId").val(),
						name : newRow.find("td").eq(1).text(),
						date : newRow.find("td").eq(1).find("#txtLastUpdateDate").val()
				};
				crntRow.find("td").eq(1).text(oldProcess.name);
				crntRow.find("td").eq(1).append("<input id='txtProcessId' type='hidden' value='" + oldProcess.id + "'/>");
				crntRow.find("td").eq(1).append("<input id='txtLastUpdateDate' type='hidden' value='" + oldProcess.date + "'/>");
				newRow.find("td").eq(1).text(newProcess.name);
				newRow.find("td").eq(1).append("<input id='txtProcessId' type='hidden' value='" + newProcess.id + "'/>");
				newRow.find("td").eq(1).append("<input id='txtLastUpdateDate' type='hidden' value='" + newProcess.date + "'/>");
				newRow.addClass("slRow crRow");
				$($(".tbl-process-re .acRow")[newProcess.no]).removeClass("slRow crRow");
			} else {
				return false;
			}
		}

		// set status action button
		setActionStatus();
		// set Register button is disable
		setButtonRegisterStatus();
	}

	// Jump fist/last row
	function jumpRow(action) {
		// variable definition
		var crntRow = $(".crRow");
		var count = 0;
		var no = "";

		if($(".tbl-process-re .acRow").length > 2){
			// remove current row selectd
			$(".crRow").remove();
		}
		
		// if action move first
		if(action == ACTION_FIRST) {
			$($(".tbl-process-re .acRow")[0]).before(crntRow);
		} else if(action == ACTION_LAST) {// else action move last
			$($(".tbl-process-re .acRow")[$(".tbl-process-re .acRow").length - 1]).after(crntRow);
		}
		
		
		// update table data
		$(".tbl-process-re .acRow").each(function() {
			no = $(this).find("td").eq(0).text()
			if((no != "" && no != (count + 1) && action == ACTION_FIRST) ||
				(no != "" && action == ACTION_LAST)) {
			$(this).find("td").eq(0).text(count + 1);
				count++;
			} else {
				return false;
			}
		});

		// set status action button
		setActionStatus();
		// set Register button is disable
		setButtonRegisterStatus();
	}

	// Draw process data table based
	function drawTable() {

		// draw left table
		// clear table left
		$("#tblBody").find("tbody").remove();
		// create table starts
		var tableStringArray = [];
		var tableHeight = calculateBnn0092TableBodyHeight();
		// add tbody open tag
		tableStringArray.push("<tbody>");
		for (var i = 0; i < unreTable.length; i++) {
			// row open tag
			tableStringArray.push("<tr id='row' class='acRow'>");
			// no.
			tableStringArray.push("<td>" + (i + 1) + "</td>");
			// process name
			tableStringArray.push("<td>" + unreTable[i].processName + 	"<input id='txtProcessId' type='hidden' value='" + unreTable[i].processId + "'/>" +
																		"<input id='txtLastUpdateDate' type='hidden' value='" + unreTable[i].lastUpdateDate + "'/>" + "</td>");
			// row close tag
			tableStringArray.push("</tr>");
		}
		// row empty
		tableStringArray.push("<tr>");
		tableStringArray.push("<td></td>");
		tableStringArray.push("<td></td>");
		tableStringArray.push("</tr>");
		// add tbody close tag
		tableStringArray.push("</tbody>");
		// append all created string to table
		$("#tblBody").append(tableStringArray.join(''));
		// fix table header and body when scrolling only the table body
		$("#divBody").height(tableHeight);
		fixTable();
		// scroll to top of table
		$("#divBody").scrollTop(0).scrollLeft(0);

		//-------------------------
		// draw right table
		// clear table right
		$("#tblBodyRight").find("tbody").remove();
		// create table starts
		var tableRightStringArray = [];
		// add tbody open tag
		tableRightStringArray.push("<tbody>");
		for (var i = 0; i < rtedTable.length; i++) {
			// row open tag
			tableRightStringArray.push("<tr id='row' class='acRow'>");
			// no.
			tableRightStringArray.push("<td>" + (i + 1) + "</td>");
			// process name
			tableRightStringArray.push("<td>" + rtedTable[i].processName + 	"<input id='txtProcessId' type='hidden' value='" + rtedTable[i].processId + "'/>" +
																			"<input id='txtLastUpdateDate' type='hidden' value='" + rtedTable[i].lastUpdateDate + "'/>" + "</td>");
			// row close tag
			tableRightStringArray.push("</tr>");
		}
		// row empty
		tableRightStringArray.push("<tr>");
		tableRightStringArray.push("<td></td>");
		tableRightStringArray.push("<td></td>");
		tableRightStringArray.push("</tr>");
		// add tbody close tag
		tableRightStringArray.push("</tbody>");
		// append all created string to table
		$("#tblBodyRight").append(tableRightStringArray.join(''));
		// fix table header and body when scrolling only the table body
		$("#divBodyRight").height(tableHeight);
		fixTableRight();
		// scroll to top of table
		$("#divBodyRight").scrollTop(0).scrollLeft(0);

		// set status action button
		setActionStatus();
	}
	
	// Fix height unregistered table
	function calculateBnn0092TableBodyHeight() {
		// get all the necessary height of controls
		var windowHeight = $(window).height();
		var headerHeight = $(".header").height();
		var headerGroupButtonHeight = $(".header-group-button").height();
		var titleHeight = $(".title").height();
		var searchConditionHeight = $(".tbl-box").height();
		var searchTableBodyTopMargin = ($(".text-title-lv2").height()*2) + $(".div-header").height();
		var titleTableBottomHeight = $("#divHeadBottom").height();
		var tableBottomHeight = $("#divBodyBottom").height();
		var footerHeight = $(".footer").height();
		var estimatedSafeHeight = 120;
		// calculate table body height
		return windowHeight - headerHeight - headerGroupButtonHeight - titleHeight - searchConditionHeight
		- searchTableBodyTopMargin - titleTableBottomHeight - tableBottomHeight - footerHeight - estimatedSafeHeight;
	}

	//Fix right table header and body when scrolling only the table body
	function fixTableRight() {
		// current browser croll bar width
		var scrollBarWidth = getScrollbarWidth();
		if (/Edge/.test(navigator.userAgent)) {
			scrollBarWidth = 12;
		}

		// get height of table body and table container
		var tblBodyHeight = $("#tblBodyRight").height();
		var divBodyHeight = $("#divBodyRight").height();
		// if the table body is longer than the table container
		if (tblBodyHeight > divBodyHeight) {
			// add y scroll bar
			$("#divBodyRight").css("overflow-y", "scroll");
			if (isMobile()) {
				// Mobile version
			} else {
				// ceiling the width of divBody to avoid misaligning the borders
				$("#divBodyRight").width(Math.ceil($("#divBodyRight").width()));
				// PC version
				// when the table body has many rows, scroll bar will appear
				// set the size of body to minus the scroll bar, scroll bar size: scrollBarWidth
				$("#divHeadRight").width($("#divBodyRight").width() - scrollBarWidth);
				$("#divFooterRight").width($("#divBodyRight").width() - scrollBarWidth);
				// set size when user resizes window
				$(window).resize(function() {
					$("#divHeadRight").width($("#divBodyRight").width() - scrollBarWidth);
					$("#divFooterRight").width($("#divBodyRight").width() - scrollBarWidth);
				});
			}
		} else {
			// table body is shorter than the table container
			// hide y scroll bar
			$("#divBodyRight").css("overflow-y", "hidden");
			// ceiling the width of divBody to avoid misaligning the borders
			$("#divBodyRight").width(Math.ceil($("#divBodyRight").width()));
			// when the table body has few rows, scroll bar will disappear
			// set the size of body to plus the scroll bar, scroll bar size: scrollBarWidth
			$("#divHeadRight").width($("#divBodyRight").width());
			$("#divFooterRight").width($("#divBodyRight").width() + scrollBarWidth);
			// set size when user resizes window
			$(window).resize(function() {
				$("#divHeadRight").width($("#divBodyRight").width());
				$("#divFooterRight").width($("#divBodyRight").width() + scrollBarWidth);
			});
		}
		// fix table not repainting on Chrome
		$("#divBodyRight").html($("#divBodyRight").html());
	}

	//Fix bottom table header and body when scrolling only the table body
	function fixTableBottom() {
		// current browser croll bar width
		var scrollBarWidth = getScrollbarWidth();
		if (/Edge/.test(navigator.userAgent)) {
			scrollBarWidth = 12;
		}

		// get height of table body and table container
		var tblBodyHeight = $("#tblBodyBottom").height();
		var divBodyHeight = $("#divBodyBottom").height();
		// if the table body is longer than the table container
		if (tblBodyHeight > divBodyHeight) {
			// add y scroll bar
			$("#divBodyBottom").css("overflow-y", "scroll");
			if (isMobile()) {
				// Mobile version
			} else {
				// ceiling the width of divBody to avoid misaligning the borders
				$("#divBodyBottom").width(Math.ceil($("#divBodyBottom").width()));
				// PC version
				// when the table body has many rows, scroll bar will appear
				// set the size of body to minus the scroll bar, scroll bar size: scrollBarWidth
				$("#divHeadBottom").width($("#divBodyBottom").width() - scrollBarWidth);
				$("#divFooterBottom").width($("#divBodyBottom").width() - scrollBarWidth);
				// set size when user resizes window
				$(window).resize(function() {
					$("#divHeadBottom").width($("#divBodyBottom").width() - scrollBarWidth);
					$("#divFooterBottom").width($("#divBodyBottom").width() - scrollBarWidth);
				});
			}
		} else {
			// table body is shorter than the table container
			// hide y scroll bar
			$("#divBodyBottom").css("overflow-y", "hidden");
			// when the table body has few rows, scroll bar will disappear
			// set the size of body to plus the scroll bar, scroll bar size: scrollBarWidth
			$("#divHeadBottom").width($("#divBodyBottom").width());
			$("#divFooterBottom").width($("#divBodyBottom").width() + scrollBarWidth);
			// set size when user resizes window
			$(window).resize(function() {
				$("#divHeadBottom").width($("#divBodyBottom").width());
				$("#divFooterBottom").width($("#divBodyBottom").width() + scrollBarWidth);
			});
		}
		// fix table not repainting on Chrome
		$("#divBodyBottom").html($("#divBodyBottom").html());
	}

	

	// Reset page
	function resetPage() {
		// process list
		initData();
		// Set action status
		setActionStatus();
		// select row
		unBindClickRow();
		// set Register button is disable
		setButtonRegisterStatus();
	}

	// Set action status
	function setActionStatus() {
		var rowUnTable = $(".tbl-process-un tr");
		var rowReTable = $(".tbl-process-re tr");
		var acRowUnTable = $(".tbl-process-un .acRow");
		var acRowReTable = $(".tbl-process-re .acRow");
		var slRowReTable = $(".tbl-process-re .slRow");
		var crRowReTable = $(".tbl-process-re .crRow");
		var btnGet = $("#btn_get");
		var btnGetAll = $("#btn_getall");
		var btnBackAll = $("#btn_backall");
		var btnBack = $("#btn_back");
		var btnUp = $("#btn_up");
		var btnFirst = $("#btn_first");
		var btnLast = $("#btn_last");
		var btnDown = $("#btn_down");

		if(rowUnTable.hasClass("acRow")) {
			btnGetAll.prop("disabled", false);
			btnGetAll.removeClass("btn-getall-disable");
			if(acRowUnTable.hasClass("slRow")) {
				btnGet.prop("disabled", false);
				btnGet.removeClass("btn-get-disable");
			} else {
				btnGet.prop("disabled", true);
				btnGet.addClass("btn-get-disable");
			}
		} else {
			btnGet.prop("disabled", true);
			btnGetAll.prop("disabled", true);
			btnGet.addClass("btn-get-disable");
			btnGetAll.addClass("btn-getall-disable");
		}

		if(rowReTable.hasClass("acRow")) {
			btnBackAll.prop("disabled", false);
			btnBackAll.removeClass("btn-backall-disable");
			if(acRowReTable.hasClass("slRow")) {
				btnBack.prop("disabled", false);
				btnBack.removeClass("btn-back-disable");
			} else {
				btnBack.prop("disabled", true);
				btnBack.addClass("btn-back-disable");
			}

			if(slRowReTable.hasClass("crRow")) {
				if(crRowReTable.find("td").eq(0).text() == 1 && acRowReTable.length > 1) {
					btnUp.prop("disabled", true);
					btnFirst.prop("disabled", true);
					btnLast.prop("disabled", false);
					btnDown.prop("disabled", false);
					btnUp.addClass("btn-up-disable");
					btnFirst.addClass("btn-first-disable");
					btnLast.removeClass("btn-last-disable");
					btnDown.removeClass("btn-down-disable");
				} else if(crRowReTable.find("td").eq(0).text() == acRowReTable.length && acRowReTable.length > 1) {
					btnUp.prop("disabled", false);
					btnFirst.prop("disabled", false);
					btnLast.prop("disabled", true);
					btnDown.prop("disabled", true);
					btnUp.removeClass("btn-up-disable");
					btnFirst.removeClass("btn-first-disable");
					btnLast.addClass("btn-last-disable");
					btnDown.addClass("btn-down-disable");
				} else if(crRowReTable.find("td").eq(0).text() > 1 && crRowReTable.find("td").eq(0).text() < acRowReTable.length) {
					btnUp.prop("disabled", false);
					btnFirst.prop("disabled", false);
					btnLast.prop("disabled", false);
					btnDown.prop("disabled", false);
					btnUp.removeClass("btn-up-disable");
					btnFirst.removeClass("btn-first-disable");
					btnLast.removeClass("btn-last-disable");
					btnDown.removeClass("btn-down-disable");
				}
			} else {

				btnUp.prop("disabled", true);
				btnFirst.prop("disabled", true);
				btnLast.prop("disabled", true);
				btnDown.prop("disabled", true);
				btnUp.addClass("btn-up-disable");
				btnFirst.addClass("btn-first-disable");
				btnLast.addClass("btn-last-disable");
				btnDown.addClass("btn-down-disable");
			}
		} else {
			btnBack.prop("disabled", true);
			btnBackAll.prop("disabled", true);
			btnUp.prop("disabled", true);
			btnFirst.prop("disabled", true);
			btnLast.prop("disabled", true);
			btnDown.prop("disabled", true);
			btnBack.addClass("btn-back-disable");
			btnBackAll.addClass("btn-backall-disable");
			btnUp.addClass("btn-up-disable");
			btnFirst.addClass("btn-first-disable");
			btnLast.addClass("btn-last-disable");
			btnDown.addClass("btn-down-disable");
		}

		// select row
		unBindClickRow();
	}
	
	function escapeHtml(text) {
	  return text
	      .replace(/&/g, "&amp;")
	      .replace(/</g, "&lt;")
	      .replace(/>/g, "&gt;")
	      .replace(/"/g, "&quot;")
	      .replace(/'/g, "&#039;");
	}
});