/**
 * @author Hieu Dao
 */

$(document).ready(function() {
	//--------------- Constants definition ---------------
	var CURRENT_SCREEN_NAME = "0061";
	// number of items in one page in table
	var ITEM_IN_ONE_PAGE = 20;
	//--------------- Variables definition ---------------
	// Application path
	var rootPath = getContextPath();
	// search result total data count
	var totalResultCount = 0;
	// Variables for handling pager
	var currentPage = 1;
	var maxPage = 0;
	var from = 0;
	// numberical order
	var numberical_order = 0;
	// search data
	var searchData = null;
	
	// Display current date
	$("#txtCurrentDate").text(getCurrentDate() + " (" + DAY_OF_WEEK + ") ");
	
	// Initialize combo box data
	initData();
	loadAreaData();
	loadBlockData();
	loadLineData();
	loadColumnData();
	
	// Set pager status
	setPagerStatus();

	// Farm combo box change event process
	$("#cbbFarmName").bind("change", function() {
		// load Area, Block, Line, Column, Status data combo box
		loadAreaData();
		loadBlockData();
		loadLineData();
		loadColumnData();
		loadStatusData();
		loadTaskData();
		loadProcessData();
	});

	// Area combo box change event process
	$("#cbbAreaName").bind("change", function() {
		// load Block, Line, Column data combo box
		loadBlockData();
		loadLineData();
		loadColumnData();
	});

	// Block combo box change event process
	$("#cbbBlockName").bind("change", function() {
		// load Line, Column data combo box
		loadLineData();
		loadColumnData();
	});
	
	// Process combo box change event process
	$("#cbbProcessName").bind("change", function() {
		// load Task data combo box
		loadTaskData();
	});

	// Search button event process
	$("#btnAccess").bind("click", function() {
		if (!checkRole(CURRENT_SCREEN_NAME, REFERENCE_FLAG)) {
			// display message
			jWarning(ROLE_REFER_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			numberical_order = 0;
			// Set farm, area Name
			$("#spFarmName").text($("#cbbFarmName").find("option:selected").text());
			$("#spAreaName").text($("#cbbAreaName").find("option:selected").text());
			// Reset variables
			resetVariables();
			// Get search conditions
			searchData = createSearchConditions();
			// Draw table
			drawResult = drawTable();
			if (drawResult != "") {
				// update total data count UI
				setDataCounts();
			}
		}
	});
	
	if(bnn0075Data == null || (bnn0075Data != null && bnn0075Data != "0")) {
		// auto search at first time loaded page
	    $("#btnAccess").trigger("click");
	}
	
	// View button event process
	$(document).on("click", ".view", function() {
		// variables definition
		var cultivationObject = {};
		// get row index
		selectedRowIndex = $(this).attr("name");
		// Create object
		cultivationObject = {	
				farmId: $("#cbbFarmName").find("option:selected").val(),
				areaId: $("#cbbAreaName").find("option:selected").val(),
				blockId: $("#row" + selectedRowIndex).find("td").eq(1).find("#hddBlockId").val(),
				lineId: $($("#row" + selectedRowIndex).find("td").eq(2).find("span")).attr('line'),
				columnId: $($("#row" + selectedRowIndex).find("td").eq(3).find("span")).attr('column'),
				previousRoundCbb: $("#row" + selectedRowIndex).find("td").eq(4).attr("name"),
				workingDateStart: convertDataFrom($("#row" + selectedRowIndex).find("td").eq(4).text()),
				workingDateEnd: convertDataFrom($("#row" + selectedRowIndex).find("td").eq(4).text()),
				processId: $("#row" + selectedRowIndex).find("td").eq(5).find("#hddProcessId").val(),
				taskId: $("#row" + selectedRowIndex).find("td").eq(6).find("#hddTaskId").val(),
				statusId: $("#row" + selectedRowIndex).find("td").eq(7).find("#hddStatusId").val()

		}

		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "getSingleData",
			contentType: "application/json",
			data: JSON.stringify(cultivationObject),
			async: false,
			success: function(returnedJsonData) {

				// Check returned value from server
				if (checkSessionTimeout(returnedJsonData) == 1) return;

				// Read data from Json for Form
				if (returnedJsonData != "") {
					// Farm name
					$("#txtFarmNamePopup").replaceWith("<input id='txtFarmNamePopup' class='txt-lv1' type='text' value='"+ returnedJsonData.farmName +"' name='' disabled='disabled'>");
					// Area name
					$("#txtAreaNamePopup").replaceWith("<input id='txtAreaNamePopup' class='txt-lv1' type='text' value='"+ returnedJsonData.areaName +"' name='' disabled='disabled'>");
					// Block name
					$("#txtBlockNamePopup").replaceWith("<input id='txtBlockNamePopup' class='txt-lv1' type='text' value='"+ returnedJsonData.blockName +"' name='' disabled='disabled'>");
					// Kind name
					$("#txtKindNamePopup").replaceWith("<input id='txtKindNamePopup' class='txt-lv1' type='text' value='"+ returnedJsonData.kindName +"' name='' disabled='disabled'>");
					// Date
					$("#txtWorkingDatePopup").val(convertDataTo(returnedJsonData.workingDate));
					// Process name
					$("#txtProcessNamePopup").replaceWith("<input id='txtProcessNamePopup' class='txt-lv1 width145' type='text' value='"+ returnedJsonData.processName +"' name='' disabled='disabled'>");
					// Planting date
					$("#txtPlantDatePopup").val(convertDataTo(returnedJsonData.plantingDate));
					// Flowering date
					$("#txtFlowerDatePopup").val(convertDataTo(returnedJsonData.floweringDate));
					// Bag closing date
					$("#txtBagDatePopup").val(convertDataTo(returnedJsonData.bagClosingDate));
					// Harvest date
					$("#txtHarvestDatePopup").val(convertDataTo(returnedJsonData.harvestDate));
					// Shipping date
					$("#txtShippingDatePopup").val(convertDataTo(returnedJsonData.shippingDate));
					// Task name
					$("#txtTaskPopup").replaceWith("<input id='txtTaskPopup' class='txt-lv1 width145' type='text' value='"+ returnedJsonData.taskName +"' name='' disabled='disabled'>");
					// Content
					$("#txtWorkContentPopup").replaceWith("<textarea id='txtWorkContentPopup' class='txtarea-lv60 height60' rows='4' maxlength='255' disabled='disabled' spellcheck=''false'>" + returnedJsonData.workingDetails + "</textarea>");
					// Precaution
					$("#txtPrecautionPopup").replaceWith("<textarea id='txtPrecautionPopup' class='txtarea-lv60 height60' rows='4' maxlength='255' disabled='disabled' spellcheck=''false'>" + returnedJsonData.taskNote + "</textarea>");
					// Status name
					$("#txtStatusPopup").replaceWith("<input id='txtStatusPopup' class='txt-lv1 width145' type='text' value='" + returnedJsonData.statusName + "' name='' disabled='disabled'>");
					// Note
					$("#txtNotePopup").replaceWith("<textarea id='txtNotePopup' class='txtarea-lv60 height60' rows='4' maxlength='255' disabled='disabled' spellcheck='false'>" + returnedJsonData.cultivationNote + "</textarea>");
					// display operation detail popup
					showPopup($("#popupWrapper"));
				} else {
					// Display error message
					jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
				}
			},
			error: function(e) {
				// Display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	});
	
	// Cancel button in popup click event process
	$(document).on("click", "#btnCancelPopup", function() {
		// Clear all current text of pop up controls
		clearPopupControl();
		// hide operation detail popup
		hidePopup($("#popupWrapper"));
	});
	
	// Back button click event process
	$("#btnBack").bind("click", function() {
		goBackToPreviousPage();
	});

	// call Paging event
	function pagingEvent() {
		$("#btnFirst").unbind("click");
		$("#btnPrevious").unbind("click");
		$("#btnNext").unbind("click");
		$("#btnLast").unbind("click");
		$("#txtGoToPage").unbind("keyup");
		$("#txtGoToPage").unbind("keydown");
		$("#btnGoToPage").unbind("click");

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
			if (isNaN(value) || $(this).val() == "" || parseInt($(this).val(), 10) == 0 || (currentPage == maxPage && (maxPage == 1 || maxPage == 0))) {
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
					// Right, down, up
					(event.keyCode >= 35 && event.keyCode <= 40)) {
				// Let it happen, don't do anything
				return;
			}
			// Ensure that it is a number and stop
			// The keypress
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
	}

	// Get data for combo box
	function initData() {

		// set height cultivation detail table on first load
		$("#divBody").height(calculateBnn0061TableHeight());
		
		// farm name combo box
		if (farmData.length > 0) {
			var farmStr = "";
			for (var i = 0; i < farmData.length; i++) {
				farmStr += "<option value='" + farmData[i].farmId + "'>" + farmData[i].farmName + "</option>";
			}
			$("#cbbFarmName").append(farmStr);
			if(bnn0075Data.farmId != null) {
				$("#cbbFarmName").val(bnn0075Data.farmId);
			}
		}

		// process name combo box
		if (processData.length > 0) {
			var processStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
			for (var i = 0; i < processData.length; i++) {
				processStr += "<option value='" + processData[i].processId + "'>" + processData[i].processName + "</option>";
			}
			$("#cbbProcessName").append(processStr);
		}

		// task name combo box
		if (taskData.length > 0) {
			var taskStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
			for (var i = 0; i < taskData.length; i++) {
				taskStr += "<option value='" + taskData[i].taskId + "'>" + taskData[i].taskName + "</option>";
			}
			$("#cbbTaskName").append(taskStr);
		}

		// status name combo box
		if (statusData.length > 0) {
			var statusStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
			for (var i = 0; i < statusData.length; i++) {
				statusStr += "<option value='" + statusData[i].statusId + "'>" + statusData[i].statusName + "</option>";
			}
			$("#cbbStatusName").append(statusStr);
		}
		
		// set harvestDate, shipDate default
		if(bnn0075Data != null && bnn0075Data != "0") {
			$("#txtHarvestStart").val(convertDataTo(bnn0075Data.harvestDate));
			$("#txtHarvestEnd").val(convertDataTo(bnn0075Data.harvestDate));
			$("#txtShipStart").val(convertDataTo(bnn0075Data.shipDate));
			$("#txtShipEnd").val(convertDataTo(bnn0075Data.shipDate));
		}
		
		$("#txtCodeStart").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#txtCodeStart").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#txtCodeEnd").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#txtCodeEnd").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#txtPlantStart").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#txtPlantStart").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#txtPlantEnd").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#txtPlantEnd").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#txtFlowerStart").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#txtFlowerStart").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#txtFlowerEnd").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#txtFlowerEnd").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#txtCloseStart").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#txtCloseStart").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#txtCloseEnd").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#txtCloseEnd").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#txtHarvestStart").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#txtHarvestStart").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#txtHarvestEnd").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#txtHarvestEnd").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#txtShipStart").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#txtShipStart").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#txtShipEnd").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#txtShipEnd").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#txtWorkStart").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#txtWorkStart").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#txtWorkEnd").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#txtWorkEnd").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
	}

	function loadAreaData(){

		// get Area selected by Farm id
		var selectedFarmId = "";
		var optionStr = "";
		selectedFarmId = $("#cbbFarmName").find("option:selected").val();
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "getAreaData",
			data: { "farmId": selectedFarmId },
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				// create option string
				if (returnedJsonData != "") {
					for (var i = 0; i < returnedJsonData.length; i++) {
						optionStr += "<option value='" + returnedJsonData[i].areaId + "'>" + returnedJsonData[i].areaName + "</option>";
					}
				}
				// set options to combo box
				$("#cbbAreaName").empty().append(optionStr);
				if(bnn0075Data.farmId != null) {
					$("#cbbAreaName").val(bnn0075Data.areaId);
				}
			},
			error: function(e) {
				// set options to combo box
				$("#cbbAreaName").empty().append(optionStr);
			}
		});
	}

	function loadBlockData(){

		// get block selected by block object include farm, area id
		var selectedFarmId ="";
		var selectedAreaId ="";
		var blockObject = "";
		var optionStr = "";
		selectedFarmId = $("#cbbFarmName").find("option:selected").val();
		selectedAreaId = $("#cbbAreaName").find("option:selected").val();
		blockObject = {
				farmId: selectedFarmId,
				areaId: selectedAreaId
		}
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "getBlockData",
			contentType: "application/json",
			data: JSON.stringify(blockObject),
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				// create option string
				optionStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
				if (returnedJsonData != "") {
					for (var i = 0; i < returnedJsonData.length; i++) {
						optionStr += "<option value='" + returnedJsonData[i].blockId + "'>" + returnedJsonData[i].blockName + "</option>";
					}
				}
				// set options to combo box
				$("#cbbBlockName").empty().append(optionStr);
			},
			error: function(e) {
				// set options to combo box
				$("#cbbBlockName").empty().append(optionStr);
			}
		});
	}

	function loadLineData(){

		// get line selected by product object include farm, area, block id
		var selectedFarmId ="";
		var selectedAreaId ="";
		var selectedBlockId ="";
		var productObject = "";
		var optionStr = "";
		selectedFarmId = $("#cbbFarmName").find("option:selected").val();
		selectedAreaId = $("#cbbAreaName").find("option:selected").val();
		selectedBlockId = $("#cbbBlockName").find("option:selected").val();
		productObject = {
				farmId: selectedFarmId,
				areaId: selectedAreaId,
				blockId: selectedBlockId
		}
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "getLineData",
			contentType: "application/json",
			data: JSON.stringify(productObject),
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				// create option string
				optionStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
				if (returnedJsonData != "") {
					for (var i = 0; i < returnedJsonData.length; i++) {
						var lineId = returnedJsonData[i].substring(1);						
						lineId = parseInt(lineId) == 999 ? returnedJsonData[i] : "L" + parseInt(lineId) ;						
						optionStr += "<option value='" + returnedJsonData[i] + "'>" + lineId + "</option>";
					}
				}
				// set options to combo box
				$("#cbbLineNumber").empty().append(optionStr);
			},
			error: function(e) {
				// set options to combo box
				$("#cbbLineNumber").empty().append(optionStr);
			}
		});
	}

	function loadColumnData(){

		// get column selected by product object include farm, area, block id
		var selectedFarmId ="";
		var selectedAreaId ="";
		var selectedBlockId ="";
		var productObject = "";
		var optionStr = ""
		selectedFarmId = $("#cbbFarmName").find("option:selected").val();
		selectedAreaId = $("#cbbAreaName").find("option:selected").val();
		selectedBlockId = $("#cbbBlockName").find("option:selected").val();
		productObject = {
				farmId: selectedFarmId,
				areaId: selectedAreaId,
				blockId: selectedBlockId
		}
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "getColumnData",
			contentType: "application/json",
			data: JSON.stringify(productObject),
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				// create option string
				optionStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
				if (returnedJsonData != "") {
					for (var i = 0; i < returnedJsonData.length; i++) {
						var columnId = returnedJsonData[i].substring(1);
						columnId = parseInt(columnId) == 999 ? returnedJsonData[i] : "C" + parseInt(columnId) ;
						optionStr += "<option value='" + returnedJsonData[i] + "'>" + columnId + "</option>";
					}
				}
				// set options to combo box
				$("#cbbColumnNumber").empty().append(optionStr);
			},
			error: function(e) {
				// set options to combo box
				$("#cbbColumnNumber").empty().append(optionStr);
			}
		});
	}

	// Load status data with farm
	function loadStatusData() {		
		var selectedFarmId = "";
		var optionStr = ""
		selectedFarmId = $("#cbbFarmName").val();
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "getStatusData",
			data: { "farmId": selectedFarmId },
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				// create option string
				optionStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
				if (returnedJsonData != "") {
					for (var i = 0; i < returnedJsonData.length; i++) {
						optionStr += "<option value='" + returnedJsonData[i].statusId + "'>" + returnedJsonData[i].statusName + "</option>";
					}
				}
				// set options to combo box
				$("#cbbStatusName").empty().append(optionStr);
			},
			error: function(e) {
				// set options to combo box
				$("#cbbStatusName").empty().append(optionStr);
			}
		});
	}
	
	// load task data with process and farm
	function loadTaskData() {
		var selectedFarmId = "";
		var selectedProcessId = "";
		var optionStr = ""
		selectedFarmId = $("#cbbFarmName").val();
		selectedProcessId = $("#cbbProcessName").val();
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "getTaskData",
			data: { "farmId": selectedFarmId,
					"processId": selectedProcessId
			},
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				// create option string
				optionStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
				if (returnedJsonData != "") {
					for (var i = 0; i < returnedJsonData.length; i++) {
						optionStr += "<option value='" + returnedJsonData[i].taskId + "'>" + returnedJsonData[i].taskName + "</option>";
					}
				}
				// set options to combo box
				$("#cbbTaskName").empty().append(optionStr);
			},
			error: function(e) {
				// set options to combo box
				$("#cbbTaskName").empty().append(optionStr);
			}
		});
	}
	
	// load task data with process and farm
	function loadProcessData() {
		var selectedFarmId = "";
		var optionStr = ""
		selectedFarmId = $("#cbbFarmName").val();
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "getProcessData",
			data: { "farmId": selectedFarmId
			},
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				// create option string
				optionStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
				if (returnedJsonData != "") {
					for (var i = 0; i < returnedJsonData.length; i++) {
						optionStr += "<option value='" + returnedJsonData[i].processId + "'>" + returnedJsonData[i].processName + "</option>";
					}
				}
				// set options to combo box
				$("#cbbProcessName").empty().append(optionStr);
			},
			error: function(e) {
				// set options to combo box
				$("#cbbProcessName").empty().append(optionStr);
			}
		});
	}
	
	// Update total data count process
	function setDataCounts() {
		$("#txtCounts").text(totalResultCount.toString().replace(/^(-?\d+)(\d{3})/, "$1,$2"));
	}

	// Draw data table based on access
	function drawTable() {
		// Variables definition
		var tableHeight = calculateBnn0061TableHeight();
		// Set page number for search data
		searchData.fromRow = from;
		// Make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "searchData",
			contentType: "application/json",
			data: JSON.stringify(searchData),
			async: false,
			success: function(returnedJsonData) {

				// Check returned value from server
				if (checkSessionTimeout(returnedJsonData) == 1) return;

				// Read data from Json for Form
				if (returnedJsonData != "") {

					// Set the real search total count
					totalResultCount = returnedJsonData[0].searchDataTotalCounts;
					if (parseInt(totalResultCount) == -1) {
						// OutOfMemoryException, display error message
						jWarning(SEARCH_RESULT_OUT_OF_MEMORY_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
					} else {
						// Calculate max page
						var calculatedResultOdd = parseInt(totalResultCount) % parseInt(ITEM_IN_ONE_PAGE);
						var calculatedResult = Math.floor(parseInt(totalResultCount) / parseInt(ITEM_IN_ONE_PAGE));
						maxPage = (calculatedResultOdd == 0) ? calculatedResult : (calculatedResult + 1);
						// Update pager
						$("#lblCurrentPage").text(currentPage);
						$("#lblMaxPage").text(maxPage);
						// Clear table
						$(".tbl-search-traceability-result").find("tbody").remove();
						// Create table starts
						var tableStringArray = [];
						// Add tbody open tag
						tableStringArray.push("<tbody>");
						for (var i = 0; i < returnedJsonData.length; i++) {
							workingDate = convertDataTo(returnedJsonData[i].workingDate);
							cultivationNote = returnedJsonData[i].cultivationNote;
							numberical_order++;
							// Row open tag
							tableStringArray.push("<tr id='row" + i + "'>");
							// No.
							tableStringArray.push("<td class='align-center'>" + numberical_order + "</td>");
							// Block name
							tableStringArray.push("<td class='overflow-ellipsis'><input id='hddBlockId' type='hidden' value='" + returnedJsonData[i].blockId + "'/><span>" + returnedJsonData[i].blockName + "</span></td>");
							// Line number
							var lineId = returnedJsonData[i].lineId.substring(1);
							lineId = parseInt(lineId) == 999 ? returnedJsonData[i].lineId : "L" + parseInt(lineId) ;
							tableStringArray.push("<td class='overflow-ellipsis'><span line='" + returnedJsonData[i].lineId + "'>" + lineId + "</span></td>");
							// Column number
							var columnId = returnedJsonData[i].columnId.substring(1);
							columnId = parseInt(columnId) == 999 ? returnedJsonData[i].columnId : "C" + parseInt(columnId);
							tableStringArray.push("<td class='overflow-ellipsis'><span column='" + returnedJsonData[i].columnId + "'>" + columnId + "</span></td>");
							// Working Date
							tableStringArray.push("<td class='align-right' name='" + (returnedJsonData[i].previousRound) + "' >" + ((workingDate != null) ? workingDate : "") + "</td>");
							// Process Name
							tableStringArray.push("<td class='overflow-ellipsis'><input id='hddProcessId' type='hidden' value='" + returnedJsonData[i].processId + "'/><span>" + returnedJsonData[i].processName + "</span></td>");
							// Task Name
							tableStringArray.push("<td class='overflow-ellipsis'><input id='hddTaskId' type='hidden' value='" + returnedJsonData[i].taskId + "'/><span>" + returnedJsonData[i].taskName + "</span></td>");
							// Status Name
							tableStringArray.push("<td class='overflow-ellipsis'><input id='hddStatusId' type='hidden' value='" + returnedJsonData[i].statusId + "'/><span>" + returnedJsonData[i].statusName + "</span></td>");
							// Note
							tableStringArray.push("<td class='overflow-ellipsis'><span>" + ((cultivationNote != null) ? cultivationNote : "") + "</span></td>");
							// Supplement document 1
							tableStringArray.push("<td class='align-center'><span class='image'></span></td>");
							// Supplement document 2
							tableStringArray.push("<td class='align-center'><span class='image'></span></td>");
							// Supplement document 3
							tableStringArray.push("<td class='align-center'><span class='image'></span></td>");
							// Supplement document 3
							tableStringArray.push("<td class='align-center'><img class='view cursor-pointer' name='" + i + "' src='" + rootPath + "/resources/img/icon_ref.png?date=" + icon_refDate + "'></td>");
							
							// Row close tag
							tableStringArray.push("</tr>");
						}
						// Add tbody close tag
						tableStringArray.push("</tbody>");
						// Append all created string to table
						$(".tbl-search-traceability-result").append(tableStringArray.join(''));
						// fix table header and body when scrolling only the table body
						$("#divBody").height(tableHeight);
						// Fix table header and body when scrolling only the table body
						fixTable();
						setDataCounts();
						setPagerStatus();
						pagingEvent();
						// scroll to top of table
						$("#divBody").scrollTop(0).scrollLeft(0);
					}
				} else {
					// Display error message
					jInfo(SEARCH_RESULT_NO_DATA_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
					// Update pager
					$("#lblCurrentPage").text("0");
					$("#lblMaxPage").text("0");
					// Clear table
					$(".tbl-search-traceability-result").find("tbody").remove();
					fixTable();
					totalResultCount = 0;
					setDataCounts();
					setPagerStatus();
					pagingEvent();
				}
				returnValue = totalResultCount;
			},
			error: function(e) {
				// Set return value
				returnValue = "";
				// Display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
		return returnValue;
	}
	
	// Fix height unregistered table
	function calculateBnn0061TableHeight() {
		// get all the necessary height of controls
		var windowHeight = $(window).height();
		var headerHeight = $(".header").height();
		var headerGroupButtonHeight = $(".header-group-button").height();
		var titleHeight = $(".title").height();
		var searchConditionHeight = $($(".div-boder")[0]).height() + $($(".div-boder")[1]).height();
		var searchTableBodyTopMargin = ($(".text-title-lv4").height() * 3) + $(".div-header").height();
		var pagerHeight = $(".tblPager").height();
		var footerHeight = $(".footer").height();
		var estimatedSafeHeight = 100;
		
		// calculate table body height
		return windowHeight - headerHeight - headerGroupButtonHeight - titleHeight - searchConditionHeight
					- searchTableBodyTopMargin - pagerHeight - footerHeight - estimatedSafeHeight;
	}

	// Search conditions object creation process
	function createSearchConditions() {
		// variables definition
		var faId = $("#cbbFarmName").val();
		var arId = $("#cbbAreaName").val();
		var blId = $("#cbbBlockName").val();
		var lnId = $("#cbbLineNumber").val();
		var clId = $("#cbbColumnNumber").val();
		var prev = $("#cbbSeason").val();
		var ctSt = ($("#txtCodeStart").val() != "") ? convertDataFrom($("#txtCodeStart").val()) : $("#txtCodeStart").val();
		var ctEn = ($("#txtCodeEnd").val() != "") ? convertDataFrom($("#txtCodeEnd").val()) : $("#txtCodeEnd").val();
		var plSt = ($("#txtPlantStart").val() != "") ? convertDataFrom($("#txtPlantStart").val()) : $("#txtPlantStart").val();
		var plEn = ($("#txtPlantEnd").val() != "") ? convertDataFrom($("#txtPlantEnd").val()) : $("#txtPlantEnd").val();
		var flSt = ($("#txtFlowerStart").val() != "") ? convertDataFrom($("#txtFlowerStart").val()) : $("#txtFlowerStart").val();
		var flEn = ($("#txtFlowerEnd").val() != "") ? convertDataFrom($("#txtFlowerEnd").val()) : $("#txtFlowerEnd").val();
		var bcSt = ($("#txtCloseStart").val() != "") ? convertDataFrom($("#txtCloseStart").val()) : $("#txtCloseStart").val();
		var bcEn = ($("#txtCloseEnd").val() != "") ? convertDataFrom($("#txtCloseEnd").val()) : $("#txtCloseEnd").val();
		var hvSt = ($("#txtHarvestStart").val() != "") ? convertDataFrom($("#txtHarvestStart").val()) : $("#txtHarvestStart").val();
		var hvEn = ($("#txtHarvestEnd").val() != "") ? convertDataFrom($("#txtHarvestEnd").val()) : $("#txtHarvestEnd").val();
		var spSt = ($("#txtShipStart").val() != "") ? convertDataFrom($("#txtShipStart").val()) : $("#txtShipStart").val();
		var spEn = ($("#txtShipEnd").val() != "") ? convertDataFrom($("#txtShipEnd").val()) : $("#txtShipEnd").val();
		var wkSt = ($("#txtWorkStart").val() != "") ? convertDataFrom($("#txtWorkStart").val()) : $("#txtWorkStart").val();
		var wkEn = ($("#txtWorkEnd").val() != "") ? convertDataFrom($("#txtWorkEnd").val()) : $("#txtWorkEnd").val();
		var pcId = $("#cbbProcessName").find("option:selected").val();
		var taId = $("#cbbTaskName").find("option:selected").val();
		var stId = $("#cbbStatusName").find("option:selected").val();
		var blwk = $("#cbbUnitWork").find("option:selected").val();
		var not = "%" + $.trim($("#txtNote").val()).replace(/\\/g, "\\\\").replace(/%/g, "\\%") + "%";
		return {
			farmId: faId,
			areaId: arId,
			blockId: blId,
			lineId: lnId,
			columnId: clId,
			previousRoundCbb: prev,
			cultivationStartDateStart: ctSt,
			cultivationStartDateEnd: ctEn,
			plantingDateStart: plSt,
			plantingDateEnd: plEn,
			floweringDateStart: flSt,
			floweringDateEnd: flEn,
			bagClosingDateStart: bcSt,
			bagClosingDateEnd: bcEn,
			harvestDateStart: hvSt,
			harvestDateEnd: hvEn,
			shippingDateStart: spSt,
			shippingDateEnd: spEn,
			workingDateStart: wkSt,
			workingDateEnd: wkEn,
			processId: pcId,
			taskId: taId,
			statusId: stId,
			blockWorkFlagCbb: blwk,
			cultivationNote: not,
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

	// Reset variables process
	function resetVariables() {
		totalResultCount = 0;
		currentPage = 1;
		from = 0;
	}

	// Clear all current text of pop up controls
	function clearPopupControl() {
		// Farm name
		$("#txtFarmNamePopup").val("");
		// Area name
		$("#txtAreaNamePopup").val("");
		// Block name
		$("#txtBlockNamePopup").val("");
		// Kind name
		$("#txtKindNamePopup").val("");
		// Date
		$("#txtWorkingDatePopup").val("");
		// Process name
		$("#txtProcessNamePopup").val("");
		// Planting date
		$("#txtPlantDatePopup").val("");
		// Flowering date
		$("#txtFlowerDatePopup").val("");
		// Bag closing date
		$("#txtBagDatePopup").val("");
		// Harvest date
		$("#txtHarvestDatePopup").val("");
		// Shipping date
		$("#txtShippingDatePopup").val("");
		// Task name
		$("#txtTaskPopup").val("");
		// Content
		$("#txtWorkContentPopup").val("");
		// Precaution
		$("#txtPrecautionPopup").val("");
		// Status name
		$("#txtStatusPopup").val("");
		// Note
		$("#txtNotePopup").val("");
	}
});