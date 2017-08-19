/**
 * @author Hieu Dao
 */

//--------------- Global variables definition ---------------
var checkFlag = false;
var unreTable = [];
var rtedTable = [];
var farmDefault = "";
var kindDefault = "";
var processDefault = "";

$(document).ready(function() {
	//--------------- Constants definition ---------------
	var NEXT_SCREEN_NAME = "0092";
	//--------------- Variables definition ---------------
	// Application path
	var rootPath = getContextPath();
	// search result total data count
	var totalResultCount = 0;
	// variable to store action selected mode
	var actionMode = "";

	// Display current date
	$("#txtCurrentDate").text(getCurrentDate() + " (" + DAY_OF_WEEK + ") ");

	// Initialize combo box data
	initData();

	// task name table
	resetPage();

	// Farm combo box change event process
	$("#cbbFarmName").bind("change", function() {
		// variables definition
		var regiFlag = false;

		// check Task data is change
		regiFlag = checkTaskChange();
		
		if(regiFlag) {
			jQuestion_warning(TASK_CHANGE_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
				if (val) {
					// load Task table list data
					resetPage();
				} else {
					$("#cbbFarmName").val(farmDefault);
				}
			});
		} else {
			// load Task table list data
			resetPage();
		}
	});

	// Kind combo box change event process
	$("#cbbKindName").bind("change", function() {
		// variables definition
		var regiFlag = false;

		// check Task data is change
		regiFlag = checkTaskChange();
		
		if(regiFlag) {
			jQuestion_warning(TASK_CHANGE_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
				if (val) {
					// load Task table list data
					resetPage();
				} else {
					$("#cbbKindName").val(kindDefault);
				}
			});
		} else {
			// load Task table list data
			resetPage();
		}
	});

	// Process combo box change event process
	$("#cbbProcessName").bind("change", function() {
		// variables definition
		var regiFlag = false;

		// check Task data is change
		regiFlag = checkTaskChange();
		
		if(regiFlag) {
			jQuestion_warning(TASK_CHANGE_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
				if (val) {
					// load Task table list data
					resetPage();
				} else {
					$("#cbbProcessName").val(processDefault);
				}
			});
		} else {
			// load Task table list data
			resetPage();
		}
	});

	// Hover task row
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

			// check flag is false(only select row)
			if(checkFlag != true) {
				// get data from page to set for variables
				var id = $(this).find("td").eq(1).find("#txtTaskId").val();
	
				// check table has class 'tbl-task-re' then add class crRow to tr
				if($(this).parent().parent().hasClass("tbl-task-un")) {
					// Single select
					if($(this).hasClass("slRow")) {
						$(".tbl-task-un .acRow").removeClass("slRow");
						$(this).removeClass("slRow");
					} else {
						$(".tbl-task-un .acRow").removeClass("slRow");
						$(this).addClass("slRow");
					}
				}
	
				// check table has class 'tbl-task-re' then add class crRow to tr
				if($(this).parent().parent().hasClass("tbl-task-re")) {
					// Single select
					if($(this).hasClass("slRow")) {
						$(".tbl-task-re .acRow").removeClass("slRow");
						$(this).removeClass("slRow");
					} else {
						$(".tbl-task-re .acRow").removeClass("slRow");
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
				// select task detail
				taskDetail(id);
			}
	
			// set flag is false after click check box 
			checkFlag = false;
		});
	}

	// Checkbox click event
	function unBindCheckEvent() {
		$(".acRow input").unbind("click");
		$(".acRow input").bind("click", function() {
			// set btnSelectAll button status
			setButtonStatus();
			// set flag is true when click check box
			checkFlag = true;
		});
	}

	// Select all button click event
	$(document).on("click", "#btnSelectAll",function() {
		$(this).toggleClass("checkall");
		if($(this).hasClass("checkall")) {
			$(this).text(STATUS_UNCHECK);
			$(".tbl-task-re tr").each(function(){
				$(this).find('td:eq(2) .blockCheck').prop('checked', true);
			});
		} else {
			$(this).text(STATUS_CHECK);
			$(".tbl-task-re tr").each(function(){
				$(this).find('td:eq(2) .blockCheck').prop('checked', false);
			});
		}
		// set btnSelectAll button status
		setButtonStatus();
	});

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
		var unTable = $(".tbl-task-un .acRow");
		var reTable = $(".tbl-task-re .acRow");
		var cultivationObject = null;
		var cultivationList = [];
		var regiFlag = false;

		// get data from page to set for variables
		var faId = $("#cbbFarmName").find("option:selected").val();
		var kiId = $("#cbbKindName").find("option:selected").val();
		var prId = $("#cbbProcessName").find("option:selected").val();
		var tsId = "0";
		if(rtedTable.length > 0) {
			tsId = "1";
		}

		cultivationObject = {
			farmId: faId,
			kindId: kiId,
			processId: prId,
			taskId: tsId,
			lastUpdateDate: (rtedTable.length > 0) ? rtedTable[rtedTable.length - 1].lastUpdateDate : null
		}
		cultivationList.push(cultivationObject);

		// check Task data is change
		regiFlag = checkTaskChange();
		
		if(regiFlag) {
			for(var i = 0; i < reTable.length; i ++) {
				cultivationObject = {
					farmId: faId,
					kindId: kiId,
					processId: prId,
					taskId: $(reTable[i]).find('#txtTaskId').val(),
					blockWorkFlag: $(reTable[i]).find('td:eq(2) .blockCheck').is(':checked')
				}
				cultivationList.push(cultivationObject);
			}
			// make Ajax call to server to get data
			$.ajax({
				type: "POST",
				url: "insertData",
				contentType: "application/json",
				data: JSON.stringify(cultivationList),
				async: false,
				success: function(returnedJsonData) {

					// Check returned value from server
					if (checkSessionTimeout(returnedJsonData) == 1) return;

					// Read data from Json for Form
					if (returnedJsonData != "") {

						// Check returned value from server
						if (returnedJsonData == INSERT_RESULT_SUCCESSFUL) {

							// Reset page
							resetPage();

							// Display message
							jInfo(INSERT_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
						} else if (returnedJsonData == INSERT_RESULT_FAILED_CULTIVATION_TASK_RESULT) {
							// Failed
							// Display message
							jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
						} else if (returnedJsonData == INSERT_RESULT_FAILED_CULTIVATION_TASK_LAST_UPDATE_DATE_RESULT) {
							// Failed
							// Display message
							jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
						} else if (returnedJsonData == INSERT_RESULT_FAILED_EXCEPTION) {
							// Failed
							// Display message
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
	});
	
	// Back button click event process
	$("#btnBack").bind("click", function() {

		// variables definition
		var cultivationObject = null;
		var regiFlag = false;

		// check Task data is change
		regiFlag = checkTaskChange();
		
		if(regiFlag) {
			jQuestion_warning(TASK_CHANGE_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
				if (val) {
					goBackToPreviousPage();
				}
			});
		} else {

			// get data from page to set for variables
			var faId = $("#cbbFarmName").find("option:selected").val();
			var kiId = $("#cbbKindName").find("option:selected").val();
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

	// Get next button click event
	$("#btnNext").bind("click", function() {
		// get authorized screen list
		if (!checkAuthority(NEXT_SCREEN_NAME)) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// variables definition
			var farmId = $("#cbbFarmName").find("option:selected").val();
			var farmName = $("#cbbFarmName").find("option:selected").text();
			var kindId = $("#cbbKindName").find("option:selected").val();
			var kindName = $("#cbbKindName").find("option:selected").text();
			var regiFlag = false;
	
			// check Task data is change
			regiFlag = checkTaskChange();
			
			if(regiFlag) {
				jQuestion_warning(TASK_CHANGE_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
					if (val) {
						// create parameter object
						var parameterObj = {
							"url": rootPath + '/' + NEXT_SCREEN_NAME + '/',
							"farmId": farmId,
							"farmName": encodeURIComponent(farmName),
							"kindId": kindId,
							"kindName": encodeURIComponent(kindName)
						};
						// save to history
						savePageToHistory(parameterObj);
	
						$('<form>', {
							"id": "formTransfer",
							"html": '<input type="hidden" id="farmId" name="farmId" value="' + farmId + '" />' +
									'<input type="hidden" id="farmName" name="farmName" value="' + encodeURIComponent(farmName) + '" />' +
									'<input type="hidden" id="kindId" name="kindId" value="' + kindId + '" />' +
									'<input type="hidden" id="kindName" name="kindName" value="' + encodeURIComponent(kindName) + '" />',
							"method": 'POST',
							"action": rootPath + '/' + NEXT_SCREEN_NAME + '/'
						}).appendTo(document.body).submit();
					}
				});
			} else {
				// create parameter object
				var parameterObj = {
					"url": rootPath + '/' + NEXT_SCREEN_NAME + '/',
					"farmId": farmId,
					"farmName": encodeURIComponent(farmName),
					"kindId": kindId,
					"kindName": encodeURIComponent(kindName)
				};
				// save to history
				savePageToHistory(parameterObj);
	
				$('<form>', {
					"id": "formTransfer",
					"html": '<input type="hidden" id="farmId" name="farmId" value="' + farmId + '" />' +
							'<input type="hidden" id="farmName" name="farmName" value="' + encodeURIComponent(farmName) + '" />' +
							'<input type="hidden" id="kindId" name="kindId" value="' + kindId + '" />' +
							'<input type="hidden" id="kindName" name="kindName" value="' + encodeURIComponent(kindName) + '" />',
					"method": 'POST',
					"action": rootPath + '/' + NEXT_SCREEN_NAME + '/'
				}).appendTo(document.body).submit();
			}
		}
	});

	// Get data for combo box and task table process
	function initData() {

		// set height cultivation detail table on first load
		$("#divBody").height(calculateBnn0091TableHeight());
		$("#divBodyRight").height(calculateBnn0091TableHeight());

		// farm name combo box
		if (farmData != "") {
			var farmStr = "";
			for (var i = 0; i < farmData.length; i++) {
				farmStr += "<option value='" + farmData[i].farmId + "'>" + farmData[i].farmName + "</option>";
			}
			$("#cbbFarmName").append(farmStr);
		}

		// kind name combo box
		if (kindData != "") {
			var kindStr = "";
			for (var i = 0; i < kindData.length; i++) {
				kindStr += "<option value='" + kindData[i].kindId + "'>" + kindData[i].kindName + "</option>";
			}
			$("#cbbKindName").append(kindStr);
		}

		// process name combo box
		if (processData != "") {
			var processStr = "";
			for (var i = 0; i < processData.length; i++) {
				processStr += "<option value='" + processData[i].processId + "'>" + processData[i].processName + "</option>";
			}
			$("#cbbProcessName").append(processStr);
		}
	}
	
	// Load task table list data
	function loadTaskData() {
		var dataObject = null;
		// get data from page to set for variables
		var faId = $("#cbbFarmName").find("option:selected").val();
		var kiId = $("#cbbKindName").find("option:selected").val();
		var prId = $("#cbbProcessName").find("option:selected").val();
		// create dataObject
		dataObject = {
			farmId: faId,
			kindId: kiId,
			processId: prId
		}
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "getTaskData",
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
					farmDefault = faId;
					kindDefault = kiId;
					processDefault = prId;
					// draw two task table list
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
	
	// check Task data is change
	function checkTaskChange() {
		// variables definition
		var reTable = $(".tbl-task-re .acRow");
		var regiFlag = false;

		if(rtedTable.length != reTable.length) {
			regiFlag = true;
		} else if (rtedTable.length == reTable.length) {
			for(var i = 0; i < reTable.length; i ++) {
				var taskIdOld = rtedTable[i].taskId;
				var taskFlagOld = rtedTable[i].blockFlag;
				var taskIdNew = $(reTable[i]).find('#txtTaskId').val();
				var taskFlagNew = $(reTable[i]).find('td:eq(2) .blockCheck').is(':checked');
				if(	taskIdOld != taskIdNew || taskFlagOld != taskFlagNew) {
					regiFlag = true;
				}

				if(regiFlag) {
					break;
				}
			}
		}
		return regiFlag;
	}
	
	// set btnSelectAll button status
	function setButtonStatus() {
		// variables definition
		var reTable = $(".tbl-task-re .blockCheck");
		var btnAll = $("#btnSelectAll");
		var checkFlag = false;
		var size = $(".tbl-task-re .acRow").length;
		btnAll.removeClass("btn-disable");
		if(size > 0) {
			for(var i = 0; i < (reTable.length - 1); i ++) {
				var statusFlagFirst = $(reTable[i]).is(':checked');
				var statusFlagSecond = $(reTable[(i+1)]).is(':checked');
				if(statusFlagFirst != statusFlagSecond) {
					checkFlag = true;
				}
	
				if(checkFlag) {
					btnAll.text(STATUS_CHECK);
					btnAll.removeClass("checkall");
					break;
				}
			}
	
			if(!checkFlag) {
				if($(reTable[0]).is(':checked')) {
					btnAll.removeClass("btn-disable");
					btnAll.prop("disabled", false);
					btnAll.text(STATUS_UNCHECK);
					btnAll.addClass("checkall");
				} else {
					btnAll.removeClass("btn-disable");
					btnAll.prop("disabled", false);
					btnAll.text(STATUS_CHECK);
					btnAll.removeClass("checkall");
				}
			}
		} else {
			btnAll.text(STATUS_CHECK);
			btnAll.addClass("btn-disable");
			btnAll.prop("disabled", true);
		}

		// set Register button is disable
		setButtonRegisterStatus();
	}
	
	// set btnRegister button status
	function setButtonRegisterStatus() {
		var btnRegister = $("#btnRegister");
		var regiFlag = false;

		// check Task data is change
		regiFlag = checkTaskChange();
		if(regiFlag) {
			btnRegister.removeClass("btn-disable");
			btnRegister.prop("disabled", false);
		} else {
			btnRegister.addClass("btn-disable");
			btnRegister.prop("disabled", true);
		}
	}
	
	// View task info select event process
	function taskDetail(id) {
		// make Ajax call to server to get data
		$.ajax({
			url: "getSingleData",
			data: {"taskId": id},
			type: "POST",
			async: false,
			success: function(returnedJsonData) {

				// check returned value from server
				if (checkSessionTimeout(returnedJsonData) == 1) return;

				// read data from Json for Form
				if (returnedJsonData != "") {
					// task name
					$('#tblDetail #trName').find('td').eq(1).html(returnedJsonData.taskName.replace(/\n/g, '<br>'));
					// working details
					$('#tblDetail #trContent').find('td').eq(1).html(returnedJsonData.workingDetails.replace(/\n/g, '<br>'));
					// note
					if(returnedJsonData.note != null){
						$('#tblDetail #trNote').find('td').eq(1).html(returnedJsonData.note.replace(/\n/g, '<br>'));
					} else {
						$('#tblDetail #trNote').find('td').eq(1).text("");
					}
					// quarantineHandling flag
					if(returnedJsonData.quarantineHandlingFlag == FLAG_ON){
						$('#tblDetail #trFlag').find('td').eq(1).text(STATUS_DONE);
					} else if(returnedJsonData.quarantineHandlingFlag == FLAG_OFF) {
						$('#tblDetail #trFlag').find('td').eq(1).text(STATUS_NONE);
					}
					// change point code
					var changePointCode = ARR_CHANGE_POINT[returnedJsonData.changePointCode];
					if (changePointCode == null){
						changePointCode = "";
					}
					$('#tblDetail #trPoint').find('td').eq(1).text(changePointCode);
				} else {
					// display error message
					jInfo(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
				}
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	}

	// Save selected data of task table based
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
			crntId = crntRow.find("td").eq(1).find("#txtTaskId").val();
			crntName = crntRow.find("td").eq(1).text();
			if((action != ACTION_BACK && action != ACTION_BACKALL) && crntRow.parent().parent().hasClass("tbl-task-un")) {
				crntRow.remove();
				newRightRow = "<tr id='row' class='acRow'>";
				newRightRow = newRightRow + "<td></td>";
				newRightRow = newRightRow + "<td>" + escapeHtml(crntName) + "<input id='txtTaskId' type='hidden' value='" + crntId + "'/></td>";
				newRightRow = newRightRow + "<td><input class='blockCheck' type='checkbox'></td></tr>";
				newRightRow = newRightRow + "</tr>";
				// append all created string to table
				$($(".tbl-task-re tr")[$(".tbl-task-re tr").length-1]).before(newRightRow);
			} else if((action != ACTION_GET && action != ACTION_GETALL) && crntRow.parent().parent().hasClass("tbl-task-re")) {
				crntRow.remove();
				newLeftRow = "<tr id='row' class='acRow'>";
				newLeftRow = newLeftRow + "<td></td>";
				newLeftRow = newLeftRow + "<td>" + escapeHtml(crntName) + "<input id='txtTaskId' type='hidden' value='" + crntId + "'/></td>";
				newLeftRow = newLeftRow + "</tr>";
				// append all created string to table
				$($(".tbl-task-un tr")[$(".tbl-task-un tr").length-1]).before(newLeftRow);
			}
		});

		fixTable();
		fixTableRight();

		// repair row no
		$(".acRow").each(function() {
			crntRow = $(this);
			if(crntRow.parent().parent().hasClass("tbl-task-un")) {
				if(leftNo <= $(".tbl-task-un .acRow").length) {
					crntRow.find("td").eq(0).text(leftNo);
					leftNo++;
				}
			} else if(crntRow.parent().parent().hasClass("tbl-task-re")) {
				if(rightNo <= $(".tbl-task-re .acRow").length) {
					crntRow.find("td").eq(0).text(rightNo);
					rightNo++;
				}
			}
		});

		// set status action button
		setActionStatus();
		// set btnSelectAll button status
		setButtonStatus();
		// set Register button is disable
		setButtonRegisterStatus();
	}

	// Move up/down row
	function moveRow(action) {
		// variable definition
		if($(".tbl-task-re tr").hasClass("crRow")){
			var crntRow = $(".crRow");
			var newRow = null;
			var newTask = {};
			var oldTask = {};
		
			if(action == ACTION_UP) {// if action up
				newRow = $($(".tbl-task-re .acRow")[($(".tbl-task-re .crRow").index() - 1)]);
			} else if(action == ACTION_DOWN) {// if action down
				newRow = $($(".tbl-task-re .acRow")[($(".tbl-task-re .crRow").index() + 1)]);
			}
			
			// 1.check current row index > 0 and action up
			// 2.check current row index is not last row and action down
			// then get row data to process object and update table data
			if(($(".crRow").index() > 0 && action == ACTION_UP) || 
				($(".crRow").index() < ($(".tbl-task-re .acRow").length - 1) && action == ACTION_DOWN)) {
				// create new task is current task
				newTask = {
						no : crntRow.index(),
						id : crntRow.find("td").eq(1).find("#txtTaskId").val(),
						name : crntRow.find("td").eq(1).text(),
						flag: crntRow.find('td:eq(2) .blockCheck').is(':checked')
				};
				// create old task
				oldTask = {
						no : newRow.index(),
						id : newRow.find("td").eq(1).find("#txtTaskId").val(),
						name : newRow.find("td").eq(1).text(),
						flag: newRow.find('td:eq(2) .blockCheck').is(':checked')
				};
				crntRow.find("td").eq(1).text(oldTask.name);
				crntRow.find("td").eq(1).append("<input id='txtTaskId' type='hidden' value='" + oldTask.id + "'/>");
				crntRow.find('td:eq(2) .blockCheck').prop('checked', oldTask.flag);
				newRow.find("td").eq(1).text(newTask.name);
				newRow.find("td").eq(1).append("<input id='txtTaskId' type='hidden' value='" + newTask.id + "'/>");
				newRow.find('td:eq(2) .blockCheck').prop('checked', newTask.flag);
				newRow.addClass("slRow crRow");
				$($(".tbl-task-re .acRow")[newTask.no]).removeClass("slRow crRow");
			} else {
				return false;
			}
		}

		// set status action button
		setActionStatus();
		// set btnSelectAll button status
		setButtonStatus();
		// set Register button is disable
		setButtonRegisterStatus();
	}

	// Jump fist/last row
	function jumpRow(action) {
		// variable definition
		var crntRow = $(".crRow");
		var count = 0;
		var no = "";

		if($(".tbl-task-re .acRow").length > 2){
			// remove current row selectd
			$(".crRow").remove();
		}
		
		// if action move first
		if(action == ACTION_FIRST) {
			$($(".tbl-task-re .acRow")[0]).before(crntRow);
		} else if(action == ACTION_LAST) {// else action move last
			$($(".tbl-task-re .acRow")[$(".tbl-task-re .acRow").length - 1]).after(crntRow);
		}
		
		
		// update table data
		$(".tbl-task-re .acRow").each(function() {
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
		// set btnSelectAll button status
		setButtonStatus();
		// set Register button is disable
		setButtonRegisterStatus();
	}

	// Draw task data table based
	function drawTable() {

		// draw left table
		// clear table left
		$("#tblBody").find("tbody").remove();
		// create table starts
		var tableStringArray = [];
		var tableHeight = calculateBnn0091TableHeight();
		// add tbody open tag
		tableStringArray.push("<tbody>");
		for (var i = 0; i < unreTable.length; i++) {
			// row open tag
			tableStringArray.push("<tr id='row' class='acRow'>");
			// no.
			tableStringArray.push("<td>" + (i + 1) + "</td>");
			// task name
			tableStringArray.push("<td>" + unreTable[i].taskName + "<input id='txtTaskId' type='hidden' value='" + unreTable[i].taskId + "'/></td>");
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
			// task name
			tableRightStringArray.push("<td>" + rtedTable[i].taskName + "<input id='txtTaskId' type='hidden' value='" + rtedTable[i].taskId + "'/></td>");
			// block work flag
			if(rtedTable[i].blockFlag == 1) {
				tableRightStringArray.push("<td><input class='blockCheck' type='checkbox' checked></td>");
			} else {
				tableRightStringArray.push("<td><input class='blockCheck' type='checkbox'></td>");
			}
			// row close tag
			tableRightStringArray.push("</tr>");
		}
		// row empty
		tableRightStringArray.push("<tr>");
		tableRightStringArray.push("<td></td>");
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
	}
	
	// Fix height unregistered table
	function calculateBnn0091TableHeight() {
		// get all the necessary height of controls
		var windowHeight = $(window).height();
		var headerHeight = $(".header").height();
		var headerGroupButtonHeight = $(".header-group-button").height();
		var titleHeight = $(".title").height();
		var searchConditionHeight = $(".tbl-box").height();
		var searchTableBodyTopMargin = $(".text-title-lv2").height() + $("#btnSelectAll").height() + $(".div-header").height();
		var tableBottomHeight = $("#tblTaskTable").height();
		var footerHeight = $(".footer").height();
		var estimatedSafeHeight = 120;
		// calculate table body height
		return windowHeight - headerHeight - headerGroupButtonHeight - titleHeight - searchConditionHeight
					- searchTableBodyTopMargin - tableBottomHeight - footerHeight - estimatedSafeHeight;
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
		//$("#divBodyRight").html($("#divBodyRight").html());
	}

	// Reset page
	function resetPage() {
		// task list
		loadTaskData();
		// Set action status
		setActionStatus();
		// set btnSelectAll button status
		setButtonStatus();
		// select row
		unBindClickRow();
		// checkbox click event
		unBindCheckEvent();
		// set Register button is disable
		setButtonRegisterStatus();
	}

	// Set action status
	function setActionStatus() {
		var rowUnTable = $(".tbl-task-un tr");
		var rowReTable = $(".tbl-task-re tr");
		var acRowUnTable = $(".tbl-task-un .acRow");
		var acRowReTable = $(".tbl-task-re .acRow");
		var slRowReTable = $(".tbl-task-re .slRow");
		var crRowReTable = $(".tbl-task-re .crRow");
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
		// checkbox click event
		unBindCheckEvent();
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