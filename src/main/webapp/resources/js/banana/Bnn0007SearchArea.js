/**
 * @author Nghia Nguyen
 */
$(document).ready(function() {
	//--------------- Constants definition ---------------
	// number of items in one page in table
	var ITEM_IN_ONE_PAGE = ITEM_IN_ONE_PAGE_0007;
	var NEXT_SCREEN_NAME = "0009";
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
	// flag check click btnSeacrh
	var searchFlag = false;
	//max number of block
	var numberOfBlock = 78;
	// variable to store last updated date time when user opens edit popup
	var currentLastUpdatedTimeString = "";
	// search data
	var searchData = null;
	// display current date
	$("#txtCurrentDate").text(getCurrentDate() + " (" + DAY_OF_WEEK + ") ");
	// initialize combobox data
	initFarmNameComboboxData(false);
	// set read only
	// Search button event process
	$("#btnSearch").bind("click", function() {
		searchFlag = true;
		numberical_order = 0;
		// reset variables
		resetVariables();
		// Get search conditions
		searchData = createSearchConditions();
		// draw table
		drawResult = drawTable();
		if (drawResult != "") {
			// update total data count UI
			setDataCounts();
		}
	});

	// auto search at first time loaded page
    $("#btnSearch").trigger("click");

	// Back button click event process
	$("#btnBack").bind("click", function() {
		goBackToPreviousPage();
	});
	
	// Farm name combobox change event process
	$("#cbbFarmName").bind("change", function() {
		// load Area name combobox
		loadAreaNameCombobox(false);
		searchFlag = false;
	});

	// Farm name combobox change event process
	$("#cbbKindNamePopup").bind("change", function() {
		// load Area name combobox
		loadKindNameCombobox();
	});

	// Load Area name combobox data
	function loadAreaNameCombobox(isPopupMode) {
		var farmIdClient = $("#farmIdClient").val();
		// get Area id selected by Farm
		var selectedFarmId = "";
		selectedFarmId = $("#cbbFarmName").find("option:selected").val();
		if (selectedFarmId != STATUS_NO_SELECT) {
			// make Ajax call to server to get data
			$.ajax({
				type: "POST",
				url: "getAreaDataByFarmId",
				data: { "farmId": selectedFarmId },
				async: false,
				success: function(returnedJsonData) {
					if (checkSessionTimeout(returnedJsonData) == 1) return;
					// create option string
					var optionStr = "<option value = '"+ STATUS_NO_SELECT +"'></option>";
					if (returnedJsonData != "") {
						for (var i = 0; i < returnedJsonData.length; i++) {
							optionStr += "<option value='" + returnedJsonData[i].areaId + "'>" + returnedJsonData[i].areaName + "</option>";
						}
						// set options to combobox
						$("#cbbAreaName").empty().append(optionStr);
						// enable combobox
						$("#cbbAreaName").prop("disabled", false);
					} else {
						// clear combobox
						$("#cbbAreaName").empty();
						// disable combobox
						$("#cbbAreaName").prop("disabled", true);
					}
				},
				error: function(e) {
					// display error message
					jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
				}
			});
		} else {
			// clear combobox
			$("#cbbAreaName").empty();
			// disable combobox
			$("#cbbAreaName").prop("disabled", true);
		}
	}

	// Load Area name combobox data
	function loadKindNameCombobox() {
		// get Kind id selected by Kind
		selectedKindId = $("#cbbKindNamePopup").find("option:selected").val(); 
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "getSingleDataKind",
			data: { "kindId": selectedKindId },
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				$("#txtProspectiveHarvestAmountPopup").val(returnedJsonData.prospectiveHarvestAmount);
				$("#txtEstimatedDaysFloweringPopup").val(returnedJsonData.estimatedDaysFlowering);
				$("#txtEstimatedDaysBaggingPopup").val(returnedJsonData.estimatedDaysBagging);
				$("#txtEstimatedDaysHarvestPopup").val(returnedJsonData.estimatedDaysHarvest);
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	}

	// Register Area click event process
	$("#btnRegister").bind("click", function() {
		// clear all current text of popup controls
		clearPopupControl(); 
		// change state of controls in popup based on mode
		setPopupControlState(MODE_NEW);
		$("#txtFarmNamePopup").val(searchFarmNameStr);
		// display Area info popup
		showPopup($("#popupWrapper"));
	});

	// Delete Area click event process
	$(document).on("click", ".delete", function() {
		// get row index
		selectedRowIndex = $(this).attr("name");
		var lastUpdateDate = $(this).attr("lastUpdateDate");
		// display confirmation message
		jQuestion_warning(DELETE_CONFIRM_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
			if (val) {
				// get id of selected Area
				var areaIdPopup = $($("#row" + selectedRowIndex).find("td").eq(1)).attr("id");
				var farmIdPopup = $($("#row" + selectedRowIndex).find("td").eq(1)).attr("name");
				var userIdPopup = $($("#row" + selectedRowIndex).find("td").eq(2)).attr("id");
				// Get search conditions
				var dataObject = createGetSingleConditions(farmIdPopup,areaIdPopup);
				
				// make Ajax call to server to get data
				$.ajax({
					url: "deleteData",
					data: { "areaId": areaIdPopup, 
							"farmId": farmIdPopup,
							"usersId" : userIdPopup == undefined ? "" : userIdPopup,
							"lastUpdateDate" : lastUpdateDate
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
								// draw table
								numberical_order = 0;
								loadAreaNameCombobox(false);
								searchData.areaId = STATUS_NO_SELECT;
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
							} else if (returnedJsonData == DELETE_RESULT_FAILED_CULTIVATION_AREA) {
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
		// get row index
		selectedRowIndex = $(this).attr("name");
		// get id of selected Area
		var areaIdPopup = $($("#row" + selectedRowIndex).find("td").eq(1)).attr("id");
		var farmIdPopup = $($("#row" + selectedRowIndex).find("td").eq(1)).attr("name");
		// Get search conditions
		var dataObject = createGetSingleConditions(farmIdPopup,areaIdPopup);
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
					// farm Id 
					$("#txtFarmIdPopup").val(returnedJsonData[0].farmId);
					// farm Name
					$("#txtFarmNamePopup").val(returnedJsonData[0].farmName);
					// Area id
					$("#txtAreaIdPopup").val(returnedJsonData[0].areaId);
					// Area name
					$("#txtAreaNamePopup").val(returnedJsonData[0].areaName);
					
					if (returnedJsonData[0].usersName != null) {
						$("#textUsersNamePopup").text(returnedJsonData[0].usersName);
					}
					
					if (returnedJsonData[0].usersId != null) {
						$("#txtUsersIdPopup").val(returnedJsonData[0].usersId);
						$("#txtNewUsersIdPopup").val(returnedJsonData[0].usersId);
					} 
					
					$("#txtUsersNamePopup").prop("disabled",true);
					
					// number Of Block
					$("#txtNumberOfBlockPopup").val(returnedJsonData[0].numberOfBlock);
					// Farm Name
					var optionStr = "<option value='" + returnedJsonData[0].farmId + "'>" + returnedJsonData[0].farmName + "</option>";
					$("#cbbFarmNamePopup").empty().append(optionStr);
					// Kind Name
					if (kindData != "") {
						var optionStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
						for (var i = 0; i < kindData.length; i++) {
							if (kindData[i].deleteFlag == 0) {
								if (kindData[i].kindId == returnedJsonData[0].kindId) {
									optionStr += "<option value='" + kindData[i].kindId + "' selected = 'selected'>" + kindData[i].kindName + "</option>";
								} else {
									optionStr += "<option value='" + kindData[i].kindId + "'>" + kindData[i].kindName + "</option>";
								}
							}
						}
						// check whether to fill data in main screen or popup screen
						$("#cbbKindNamePopup").empty().append(optionStr);
					}
					// Texture
					$("#txtTexturePopup").val(returnedJsonData[0].texture);
					// Prospective Harvest Amount
					$("#txtProspectiveHarvestAmountPopup").val(returnedJsonData[0].prospectiveHarvestAmount);
					// Estimated Days Flowering
					$("#txtEstimatedDaysFloweringPopup").val(returnedJsonData[0].estimatedDaysFlowering);
					// Estimated Days Bagging
					$("#txtEstimatedDaysBaggingPopup").val(returnedJsonData[0].estimatedDaysBagging);
					// Estimated Days Harvest
					$("#txtEstimatedDaysHarvestPopup").val(returnedJsonData[0].estimatedDaysHarvest);
					// Sugar Content
					$("#txtSugarContentPopup").val(returnedJsonData[0].sugarContent);
					// status
					if (returnedJsonData[0].deleteFlag == DELETE_FLAG_OFF) {
						$("#chbDeletePopup").prop("checked", false);
					} else if (returnedJsonData[0].deleteFlag == DELETE_FLAG_ON) {
						$("#chbDeletePopup").prop("checked", true);
					}
					// note
					$("#txtNotePopup").val(returnedJsonData[0].note);
					// last updated date time
					currentLastUpdatedTimeString = returnedJsonData[0].lastUpdateDate;
					// change state of controls in popup based on mode
					setPopupControlState(MODE_EDIT);
					// display Area info popup
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

	// Call search user pop up
	$(document).on("click", "#loadUserPopup", function() {
		if (!checkAuthority(NEXT_SCREEN_NAME)) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// Check current mode
			var mode = false;
	        if (currentMode == MODE_NEW){
	        	mode = true;
	        }
	        // Set Config Variables
			var config = {
		        "listUserId": 'txtUsersIdPopup',
		        "listUserName": 'textUsersNamePopup',
		        "farmid": $("#txtFarmIdPopup").val(),
				"userRole": ROLE_TYPE_AREA_MANAGER,
		        "modeAction": mode,
	        	"modeSelect": false,
		    };
			hidePopup($("#popupWrapper"));
			$("#overlay").show();
			getSearchUser(config);
		}
	});

	$(document).on("click", "#btnScUserPupBack", function() {
		showPopup($("#popupWrapper"));
	});

	// Call search user pop up
	$(document).on("click", "#loadUser", function() {

		// Check current mode
		var mode = false;
        if (currentMode == MODE_NEW){
        	mode = true;
        }
        // Set Config Variables
		var config = {
	        "listUserId": 'txtUsersId',
	        "listUserName": 'txtUsersName',
	        "farmid": null,
			"userRole": null,
	        "modeAction": mode,
        	"modeSelect": false,
	    };
		getSearchUser(config);
	});

	$('#txtNumberOfBlockPopup').focusout(function(e) {
		if ($("#txtNumberOfBlockPopup").val() == 0) {
			$("#txtNumberOfBlockPopup").val("");
		}
	});
	$(document).on('keypress', '#txtNumberOfBlockPopup', function(e) {
		if (e.charCode != 0 && (e.charCode < 48 || e.charCode > 57)){
		  e.preventDefault();
		}
		if ($("#txtNumberOfBlockPopup").val() == 0) {
			$("#txtNumberOfBlockPopup").val("");
		}
	});

	$(document).on('keypress', '#txtSugarContentPopup', function(e) {
		if (e.charCode != 0  && (e.charCode < 46 || e.charCode > 57 || e.charCode == 47)){
			e.preventDefault();
		}
		if ($("#txtSugarContentPopup").val() == 0) {
			$("#txtSugarContentPopup").val("");
		}
	});
	
	$('#txtSugarContentPopup').focusout(function(e) {
		if ($("#txtSugarContentPopup").val() == 0) {
			$("#txtSugarContentPopup").val("");
		}
	});
	
	$(document).on('keypress', '#txtProspectiveHarvestAmountPopup', function(e) {
		if (e.charCode != 0 && (e.charCode < 46 || e.charCode > 57 || e.charCode == 47)){
			e.preventDefault();
		}
		if ($("#txtProspectiveHarvestAmountPopup").val() == 0) {
			$("#txtProspectiveHarvestAmountPopup").val("");
		}
	});
	
	$('#txtProspectiveHarvestAmountPopup').focusout(function(e) {
		if ($("#txtProspectiveHarvestAmountPopup").val() == 0) {
			$("#txtProspectiveHarvestAmountPopup").val("");
		}
	});
	
	$(document).on('keypress', '#txtEstimatedDaysFloweringPopup', function(e) {
		if (e.charCode != 0 && (e.charCode < 46 || e.charCode > 57 || e.charCode == 47)){
			e.preventDefault();
		}
		if ($("#txtEstimatedDaysFloweringPopup").val() == 0) {
			$("#txtEstimatedDaysFloweringPopup").val("");
		}
	});
	
	$('#txtEstimatedDaysFloweringPopup').focusout(function(e) {
		if ($("#txtEstimatedDaysFloweringPopup").val() == 0) {
			$("#txtEstimatedDaysFloweringPopup").val("");
		}
	});
	
	$(document).on('keypress', '#txtEstimatedDaysBaggingPopup', function(e) {
		if (e.charCode != 0 && (e.charCode < 46 || e.charCode > 57 || e.charCode == 47)){
			e.preventDefault();
		}
		if ($("#txtEstimatedDaysBaggingPopup").val() == 0) {
			$("#txtEstimatedDaysBaggingPopup").val("");
		}
	});
	
	$('#txtEstimatedDaysBaggingPopup').focusout(function(e) {
		if ($("#txtEstimatedDaysBaggingPopup").val() == 0) {
			$("#txtEstimatedDaysBaggingPopup").val("");
		}
	});
	
	$(document).on('keypress', '#txtEstimatedDaysHarvestPopup', function(e) {
		if (e.charCode != 0 && (e.charCode < 46 || e.charCode > 57 || e.charCode == 47)){
			e.preventDefault();
		}
		if ($("#txtEstimatedDaysHarvestPopup").val() == 0) {
			$("#txtEstimatedDaysHarvestPopup").val("");
		}
	});
	
	$('#txtEstimatedDaysHarvestPopup').focusout(function(e) {
		if ($("#txtEstimatedDaysHarvestPopup").val() == 0) {
			$("#txtEstimatedDaysHarvestPopup").val("");
		}
	});
	
	$("#txtNumberOfBlockPopup").blur(function(){
		var number = parseInt($(this).val());
		if(number > numberOfBlock){
			$("#txtNumberOfBlockPopup").val(numberOfBlock);
		}
	});
	
	// Register button in popup click event process
	$("#btnRegisterPopup").bind("click", function() {
		var dataObject = null;
		var DIFFERENT_ZERO = "";
		// check current mode
		if (currentMode == MODE_NEW) {
			if (checkInputBlankFields() != "") {
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (checkInputNumberFields() != "") {
				jWarning(VALIDATE_NUMBER_FIELDS_MESSAGE.replace('$1', checkInputNumberFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (!checkNumberFormat($("#txtNumberOfBlockPopup").val())) {
				jWarning(MESSAGE_BLOCK_NUMBER_IS_WRONG_NUMBER_FORMAT, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (!checkDoubleField($("#txtProspectiveHarvestAmountPopup").val(),8,4,3)) {
				jWarning(MESSAGE_FORMAT_FILED_IS_DOUBLE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (!checkDoubleField($("#txtSugarContentPopup").val(),4,2,1)) {
				jWarning(MESSAGE_FORMAT_FILED_IS_SUGAR_COTENT, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// insert new user to DB
				// get Area input data
				var dataAreaObject = createAreaDataObject();
				var dataManagerObject = createManagerDataObject();
				// make ajax call to server to get data
				$.ajax({
					type: "POST",
					url: "insertData",
					data: {	'areaData': JSON.stringify(dataAreaObject), 
							'managerData' : JSON.stringify(dataManagerObject)},
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
								// draw table
								numberical_order = 0;
								loadAreaNameCombobox(false);
								searchData.areaId = STATUS_NO_SELECT;
								drawResult = drawTable();
								if (drawResult != "") {
									// update total data count UI
									setDataCounts();
								}
								searchFlag = false;
								// hide popup
								hidePopup($("#popupWrapper"));
								// display message
								jInfo(INSERT_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_DUPLICATED) {
								// failed
								// display message
								jWarning(INSERT_AREA_DUPLICATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_FAILED_AREA) {
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
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (checkInputNumberFields() != "") {
				jWarning(VALIDATE_NUMBER_FIELDS_MESSAGE.replace('$1', checkInputNumberFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (!checkDoubleField($("#txtProspectiveHarvestAmountPopup").val(),8,4,3)) {
				jWarning(MESSAGE_FORMAT_FILED_IS_DOUBLE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (!checkDoubleField($("#txtSugarContentPopup").val(),4,2,1)) {
				jWarning(MESSAGE_FORMAT_FILED_IS_SUGAR_COTENT, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// update existing user in DB
				// get area input data
				var dataAreaObject = createAreaDataObject();
				var newDataManagerObject = createManagerDataObject()
				var oldDataManagerObject = createNewManagerDataObject()
				// make Ajax call to server to get data
				$.ajax({
					type: "POST",
					url: "updateData",
					data: {	'areaData': JSON.stringify(dataAreaObject),
							"lastUpdateDate": currentLastUpdatedTimeString},
					async: false,
					success: function(returnedJsonData) {
						if (checkSessionTimeout(returnedJsonData) == 1) return;
						if (returnedJsonData != "") {
							// check returned value from server
							if (returnedJsonData == UPDATE_RESULT_SUCCESSFUL) {
								// update the corresponding user data in search table
								// make Ajax call to server to get data
								var areaIdPopup = $("#txtAreaIdPopup").val();
								var farmIdPopup = $("#txtFarmIdPopup").val();
								var dataObject = createGetSingleConditions(farmIdPopup,areaIdPopup);
								$.ajax({
									url: "getSingleData",
									contentType: "application/json",
									data: JSON.stringify(dataObject),
									type: "POST",
									async: false,
									success: function(returnedJsonData) {
										if (checkSessionTimeout(returnedJsonData) == 1) return;
										if (returnedJsonData != "") {
											// users name
											$("#row" + selectedRowIndex).find("td").eq(1).html("<a href='#' class='areaId' name='" + returnedJsonData[0].areaId + "' id = '"+ returnedJsonData[0].farmId +"'>" + returnedJsonData[0].areaName) + "</a>";
											if (returnedJsonData[0].usersName != null) {
												$("#row" + selectedRowIndex).find("td").eq(2).text(returnedJsonData[0].usersName);
											}
											
											$("#row" + selectedRowIndex).find("td").eq(3).text(returnedJsonData[0].kindName);
											$($("#row" + selectedRowIndex).find("td").eq(3)).attr("title", returnedJsonData[0].kindName);
											var sugarContent = returnedJsonData[0].sugarContent;
											if (returnedJsonData[0].sugarContent == '') {
												sugarContent = "";
											} else {
												sugarContent = returnedJsonData[0].sugarContent;
											}
											$("#row" + selectedRowIndex).find("td").eq(4).text(sugarContent);
											$("#row" + selectedRowIndex).find("td").eq(5).text(returnedJsonData[0].texture);
											$("#row" + selectedRowIndex).find("td").eq(6).text(returnedJsonData[0].prospectiveHarvestAmount);
											$("#row" + selectedRowIndex).find("td").eq(7).text(returnedJsonData[0].estimatedDaysFlowering);
											$("#row" + selectedRowIndex).find("td").eq(8).text(returnedJsonData[0].estimatedDaysBagging);
											$("#row" + selectedRowIndex).find("td").eq(9).text(returnedJsonData[0].estimatedDaysHarvest);
											// last updated date time
											currentLastUpdatedTimeString = returnedJsonData[0].lastUpdateDate;
											$("#divBody").scrollTop(0);
											$("#divBody").scrollLeft(0);
										} 
									},
									complete: function(jqXHR, textStatus) {
										// Reset Data 
										clearPopupControl()
										// reset variables
										resetVariables();
										// draw table
										numberical_order = 0;
										loadAreaNameCombobox(false);
										searchData.areaId = STATUS_NO_SELECT;
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
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_DUPLLICATE) {
								// failed
								// display message
								jWarning(UPDATE_AREA_DUPLICATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_UPDATE_DATE) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_CULTIVATION_AREA) {
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
		// hide area info popup
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

	// Draw Area data table based on search conditions
	function drawTable() {
		// variables definition
		var returnValue = "";
		// Set page number for search data
		searchData.fromRow = from;
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "searchData",
			contentType: "application/json",
			data: JSON.stringify(searchData),
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
						if (searchFlag == true) {
							$("#farmNamelbl").text($("#cbbFarmName option:selected").text());
						} else {
							$("#farmNamelbl").text(searchFarmNameStr);
						}
						for (var i = 0; i < returnedJsonData.length; i++) {
							numberical_order++;
							// row open tag
							tableStringArray.push("<tr id='row" + i + "' name='row" + i + "'>");
							tableStringArray.push("<td class='align-center'>"+numberical_order+"</td>");
							// area name
							tableStringArray.push("<td class = 'overflow-ellipsis' name = '"+ returnedJsonData[i].farmId +"' id='"+returnedJsonData[i].areaId+"'><a class='areaId' name='" + returnedJsonData[i].areaId + "' id='"+ returnedJsonData[i].farmId +"'>" + returnedJsonData[i].areaName + "</a></td>");
							if (returnedJsonData[i].usersName == null) {
								returnedJsonData[i].usersName = "";
							}
							// area Manager
							tableStringArray.push("<td class = 'overflow-ellipsis' id = '" + returnedJsonData[i].areaManager + "'>" + returnedJsonData[i].usersName + "</td>");
							
							// kind Id
							if (returnedJsonData[i].kindName == null) {
								returnedJsonData[i].kindName = "";
							}
							tableStringArray.push("<td  class = 'overflow-ellipsis'>" + returnedJsonData[i].kindName + "</td>");
							// Sugar Content
							var sugarContent = returnedJsonData[i].sugarContent;
							if (returnedJsonData[i].sugarContent == '') {
								sugarContent = "";
							} else {
								sugarContent = returnedJsonData[i].sugarContent;
							}
							
							tableStringArray.push("<td  class = 'align-right overflow-ellipsis'>" + sugarContent + "</td>");
							// farm name
							if (returnedJsonData[i].sugarContent == null) {
								returnedJsonData[i].texture = "";
							} 
							tableStringArray.push("<td  class = 'overflow-ellipsis'>" + returnedJsonData[i].texture + "</td>");
							
							// prospective Harvest Amount
							if (returnedJsonData[i].prospectiveHarvestAmount == null) {
								returnedJsonData[i].prospectiveHarvestAmount = "0.000";
							}
							tableStringArray.push("<td class = 'align-right overflow-ellipsis'>" + returnedJsonData[i].prospectiveHarvestAmount + "</td>");
							// estimated Days Flowering
							tableStringArray.push("<td class = 'align-right overflow-ellipsis'>" + returnedJsonData[i].estimatedDaysFlowering + "</td>");
							// estimated Days Bagging
							tableStringArray.push("<td class = 'align-right overflow-ellipsis'>" + returnedJsonData[i].estimatedDaysBagging + "</td>");
							// estimated Days Harvest
							tableStringArray.push("<td class = 'align-right overflow-ellipsis'>" + returnedJsonData[i].estimatedDaysHarvest + "</td>");
							// update icon
							tableStringArray.push("<td><img class='edit cursor-pointer' lastUpdateDate = '"+returnedJsonData[i].lastUpdateDate+"' name='" + i + "' src='" + rootPath + "/resources/img/icon_edit.png?date=" + icon_editDate + "'></td>");
							// delete icon
							tableStringArray.push("<td><img class='delete cursor-pointer' lastUpdateDate = '"+returnedJsonData[i].lastUpdateDate+"' name='" + i + "' src='" + rootPath + "/resources/img/icon_del.png?date=" + icon_delDate + "'></td>");
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
						
						fixTable();
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
					var farmId = $("#cbbFarmName option:selected").text();
					$("#farmNamelbl").text(farmId);
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
		// FarmId
		var farmId = $("#cbbFarmName").find("option:selected").val();
		farmId = (farmId == STATUS_NO_SELECT) ? STATUS_NO_SELECT : farmId;
		// AreaId
		var areaId = (farmId == STATUS_NO_SELECT || $("#cbbAreaName").find("option:selected").val() == STATUS_NO_SELECT) ?
				STATUS_NO_SELECT : $("#cbbAreaName").find("option:selected").val();
		if (areaId == undefined) {
			areaId = STATUS_NO_SELECT;
		}
		return {
			areaId: areaId,
			farmId: farmId,
			fromRow: from,
			itemCount: ITEM_IN_ONE_PAGE,
		};
	}

	// Manager data object creation process, to use in Inserting farm
	function createManagerDataObject() {
		return {
			farmId: searchData.farmId,
			areaId: $.trim($("#txtAreaIdPopup").val()),
			usersId: $.trim($("#txtUsersIdPopup").val()),
		};
	}

	function createGetSingleConditions(farmId,areaId) {
		return {
			farmId: farmId,
			areaId: areaId
		};
	}

	// Manager New data object creation process, to use in Inserting farm
	function createNewManagerDataObject() {
		return {
			farmId: $.trim($("#txtFarmIdPopup").val()),
			areaId: $.trim($("#txtAreaIdPopup").val()),
			usersId: $.trim($("#txtNewUsersIdPopup").val()),
			authorizationTypeId: "1"
		};
	}

	// Area data object creation process, to use in Updating and Inserting user
	function createAreaDataObject() {
		return {
			areaId: $.trim($("#txtAreaIdPopup").val()),
			areaName: $.trim($("#txtAreaNamePopup").val()),
			farmId: searchData.farmId,
			areaManager: $.trim($("#txtUsersIdPopup").val()),
			numberOfBlock : $.trim($("#txtNumberOfBlockPopup").val()),
			kindId: $("#cbbKindNamePopup").val(),
			prospectiveHarvestAmount: $.trim($("#txtProspectiveHarvestAmountPopup").val()),
			estimatedDaysFlowering: $.trim($("#txtEstimatedDaysFloweringPopup").val()),
			estimatedDaysBagging: $.trim($("#txtEstimatedDaysBaggingPopup").val()),
			estimatedDaysHarvest: $.trim($("#txtEstimatedDaysHarvestPopup").val()),
			sugarContent: $.trim($("#txtSugarContentPopup").val()),
			texture: $.trim($("#txtTexturePopup").val()),
			deleteFlag: DELETE_FLAG_OFF,
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
		// Farm Id
		$("#cbbFarmNamePopup").val(STATUS_NO_SELECT);
		$("#cbbFarmNamePopup").prop("disabled", false);
		// Area Id
		$("#txtAreaIdPopup").val("");
		$("#txtNumberOfBlockPopup").val("");
		$("#textUsersNamePopup").text("");
		$("#textUsersNamePopup").attr("title", "");
		$("#txtUsersIdPopup").val("");
		// Area Name
		$("#txtAreaNamePopup").val("");
		// Kind Name
		$("#cbbKindNamePopup").val(STATUS_NO_SELECT);
		$("#cbbKindNamePopup").prop("disabled", false);
		// sugar content
		$("#txtSugarContentPopup").val("");
		// Texture
		$("#txtTexturePopup").val("");
		// Prospective Harvest Amount
		$("#txtProspectiveHarvestAmountPopup").val("");
		// Estimated Days Flowering
		$("#txtEstimatedDaysFloweringPopup").val("");
		// Estimated Days Bagging
		$("#txtEstimatedDaysBaggingPopup").val("");
		// Estimated Days Harvest
		$("#txtEstimatedDaysHarvestPopup").val("");
		// status
		$("#chbDeletePopup").prop("checked", false);
		// note
		$("#txtNotePopup").val("");
		$("#txtUsersNamePopup").val("");
		$("#txtUsersIdPopup").val("");
		$("#txtNewUsersIdPopup").val("");
		$("#txtUsersNamePopup").prop("disabled",false);
	}

	var searchFarmNameStr = "";
	var searchFarmIdStr = "";
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
			initFarmNameComboboxData(true);
			// Area Id
			$("#txtAreaIdPopup").prop("disabled", false);
			// Area Name
			$("#txtAreaNamePopup").prop("disabled", false);
			// Number Of Block
			$("#txtNumberOfBlockPopup").prop("disabled", false);
			$("#cbbFarmNamePopup").val(STATUS_NO_SELECT);
			if (searchFlag == true) {
				searchFarmNameStr = $("#cbbFarmName option:selected").text();
				searchFarmIdStr = $("#cbbFarmName option:selected").val();
			}
			$("#txtFarmNamePopup").prop("disabled", false);
			$("#txtFarmNamePopup").val(searchFarmNameStr);
			$("#txtFarmNamePopup").prop("disabled", true);
			$("#txtFarmIdPopup").val(searchFarmIdStr);
			$("#textUsersNamePopup").text("");
			
			// Kind Name
			$("#cbbKindNamePopup").prop("disabled", false);
			$("#cbbKindNamePopup").val(STATUS_NO_SELECT);
			// sugar content
			$("#txtSugarContentPopup").prop("disabled", false);
			// Texture
			$("#txtTexturePopup").prop("disabled", false);
			// Prospective Harvest Amount
			$("#txtProspectiveHarvestAmountPopup").prop("disabled", false);
			// Estimated Days Flowering
			$("#txtEstimatedDaysFloweringPopup").prop("disabled", false);
			// Estimated Days Bagging
			$("#txtEstimatedDaysBaggingPopup").prop("disabled", false);
			// Estimated Days Harvest
			$("#txtEstimatedDaysHarvestPopup").prop("disabled", false);
			// status
			$("#chbDeletePopup").prop("disabled", false);
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
			// Area Id
			$("#txtAreaIdPopup").prop("disabled", true);
			// Area Name
			$("#txtAreaNamePopup").prop("disabled", false);
			// Number Of Block
			$("#txtNumberOfBlockPopup").prop("disabled", true);
			// Farm Name
			$("#cbbFarmNamePopup").prop("disabled", true);
			
			// Kind Name
			$("#cbbKindNamePopup").prop("disabled", false);
			// sugar content
			$("#txtSugarContentPopup").prop("disabled", false);
			// Texture
			$("#txtTexturePopup").prop("disabled", false);
			// Prospective Harvest Amount
			$("#txtProspectiveHarvestAmountPopup").prop("disabled", false);
			// Estimated Days Flowering
			$("#txtEstimatedDaysFloweringPopup").prop("disabled", false);
			// Estimated Days Bagging
			$("#txtEstimatedDaysBaggingPopup").prop("disabled", false);
			// Estimated Days Harvest
			$("#txtEstimatedDaysHarvestPopup").prop("disabled", false);
			// status
			$("#chbDeletePopup").prop("disabled", false);
			// note
			$("#txtNotePopup").prop("disabled", false);
			// Show Farm Popup button
			$("#btnSearchPopup").show();
			// hide button show Farm popup 
			$(".area-info-popup #btnSearchPopup").hide();
			// hide clear text Farm popup
			$(".area-info-popup #btnClearPopup").hide();
			// register button
			$("#btnRegisterPopup").show();
		} else if (mode == MODE_VIEW) {
			// set current mode
			currentMode = MODE_VIEW;
			// Area Id
			$("#txtAreaIdPopup").prop("disabled", true);
			// Area Name
			$("#txtAreaNamePopup").prop("disabled", true);
			// Farm Name
			$("#cbbFarmNamePopup").prop("disabled", true);
			// Kind Name
			$("#cbbKindNamePopup").prop("disabled", true);
			// sugar content
			$("#txtSugarContentPopup").prop("disabled", true);
			// Texture
			$("#txtTexturePopup").prop("disabled", true);
			// Prospective Harvest Amount
			$("#txtProspectiveHarvestAmountPopup").prop("disabled", true);
			// Estimated Days Flowering
			$("#txtEstimatedDaysFloweringPopup").prop("disabled", true);
			// Estimated Days Bagging
			$("#txtEstimatedDaysBaggingPopup").prop("disabled", true);
			// Estimated Days Harvest
			$("#txtEstimatedDaysHarvestPopup").prop("disabled", true);
			// status
			$("#chbDeletePopup").prop("disabled", true);
			// note
			$("#txtNotePopup").prop("disabled", true);
			// Show Farm Popup button
			$("#btnSearchPopup").hide();
			// hide button show Farm popup 
			$(".area-info-popup #btnSearchPopup").hide();
			// hide clear text Farm popup
			$(".area-info-popup #btnClearPopup").hide();
			// Show Farm Clear button
			$("#btnClearPopup").hide();
			// register button
			$("#btnRegisterPopup").hide();
		}
	}

	// check input FarmID 
	function checkFarmIdFormat() {
		var farmId = $("#txtFarmIdEdit").val();
		if (!/^F\d{3}$/.test(farmId)) {
			return false;
		} else {
			return true;
		}
	}

	// check input Area Id
	function checkAreaIdFormat() {
		var areaId = $("#txtAreaIdPopup").val();
		if (!/^A\d{3}$/.test(areaId)) {
			return false;
		} else {
			return true;
		}
	}

	function checkNumberFormat(number) {
		if (!/^\d+$/.test(number) || number == "0") {
			return false;
		} else if (parseInt(number) >= 1 && parseInt(number) <= 78) {
			return true;
		}
	}
	// Get data for combobox process
	function initFarmNameComboboxData(isPopupMode) {
		var farmIdClient = $("#farmIdClient").val();
		// farm
		if (farmData != "") {
			var optionStr = "";
			for (var i = 0; i < farmData.length; i++) {
				optionStr += "<option value='" + farmData[i].farmId + "'>" + farmData[i].farmName + "</option>";
			}
			// check whether to fill data in main screen or popup screen
			if (!isPopupMode) {
				$("#cbbFarmName").empty()
				$("#cbbFarmName").append(optionStr);
				if (farmIdClient != "") {
					$("#cbbFarmName").val(farmIdClient);
				}
				loadAreaNameCombobox(false)
			} else {
				var optionStrPopup = "<option value=''></option>";
				for (var i = 0; i < farmData.length; i++) {
					optionStrPopup += "<option value='" + farmData[i].farmId + "'>" + farmData[i].farmName + "</option>";
				}
				$("#cbbFarmNamePopup").empty()
				$("#cbbFarmNamePopup").append(optionStrPopup);
			}
			$("#btnRegister").prop("disabled", false).removeClass("btn-disable");
		} else {
			var optionStr = "<option value='-2'></option>";
			$("#cbbFarmName").append(optionStr);
			$("#btnRegister").prop("disabled", true).addClass("btn-disable");
			$("#cbbAreaName").prop("disabled", true);
		}

		// Kind 
		if (kindData != "") {
			var optionStr = "<option value='"+ STATUS_NO_SELECT + "'></option>";
			var optionStrPopup = "<option value='"+ STATUS_NO_SELECT + "'></option>";
			for (var i = 0; i < kindData.length; i++) {
				if (kindData[i].deleteFlag == 0) {
					optionStrPopup += "<option value='" + kindData[i].kindId + "'>" + kindData[i].kindName + "</option>";
				}
			}
			$("#cbbKindNamePopup").prop("disabled", false);
			$("#cbbKindNamePopup").empty()
			$("#cbbKindNamePopup").append(optionStrPopup);
		} else {
			$("#cbbKindNamePopup").prop("disabled", true);
		}
	}

	// change page
	// Delete Area click event process
	$(document).on("click", ".areaId", function() {
		
		if (!checkAuthority(NEXT_SCREEN_NAME)) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// farm Id
			var farmId = $(this).attr('id');
			var areaId = $(this).attr('name');

			// create parameter object
			var parameterObj = {
				"url": rootPath + '/' + NEXT_SCREEN_NAME + '/',
				"farmId": farmId,
				"areaId": areaId
			};
			// save to history
			savePageToHistory(parameterObj);
			
			$('<form>', {
				"id": "formTransfer",
				"html": '<input type="hidden" id="farmId" name="farmId" value="' + farmId + '" />' +
						'<input type="hidden" id="areaId" name="areaId" value="' + areaId + '" />',
				"method": 'POST',
				"action": rootPath + '/' + NEXT_SCREEN_NAME + '/'
			}).appendTo(document.body).submit();
		}
	});

	// Check input: blank fields
	function checkInputBlankFields() {
		var errorCount = 0;
		if ($.trim($("#txtAreaNamePopup").val()) == "") {
			return AREA_NAME;
		} else if ($("#txtNumberOfBlockPopup").val() == "") {
			return NUMBER_OF_BLOCK;
		} else if ($("#cbbKindNamePopup").val() == STATUS_NO_SELECT) {
			return KIND_NAME;
		} else if ($("#txtProspectiveHarvestAmountPopup").val() == "") {
			return PROSPECTIVE_HARVEST_AMOUNT;
		} else if ($("#txtEstimatedDaysFloweringPopup").val() == "") {
			return ESTIMATED_DAYS_FLOWERING;
		} else if ($("#txtEstimatedDaysBaggingPopup").val() == "") {
			return ESTIMATED_DAYS_BAGGING;
		} else if ($("#txtEstimatedDaysHarvestPopup").val() == "") {
			return ESTIMATED_DAYS_HARVEST;
		} else if ($("#txtSugarContentPopup").val() == "") {
			return SUGAR_CONTENT;
		} else if ($.trim($("#txtTexturePopup").val()) == "") {
			return TEXTURE;
		} else {
			return ""
		}
		return errorStr;
	}
	
	function checkInputNumberFields(){
		var errorCount = 0;
		if (isNaN($("#txtNumberOfBlockPopup").val())) {
			return NUMBER_OF_BLOCK;
		} else if (isNaN($("#txtProspectiveHarvestAmountPopup").val())) {
			return PROSPECTIVE_HARVEST_AMOUNT;
		} else if (isNaN($("#txtEstimatedDaysFloweringPopup").val())) {
			return ESTIMATED_DAYS_FLOWERING;
		} else if (isNaN($("#txtEstimatedDaysBaggingPopup").val())) {
			return ESTIMATED_DAYS_BAGGING;
		} else if (isNaN($("#txtEstimatedDaysHarvestPopup").val())) {
			return ESTIMATED_DAYS_HARVEST;
		} else if (isNaN($("#txtSugarContentPopup").val())) {
			return SUGAR_CONTENT;
		} else {
			return ""
		}
		return errorStr;
	}

	// check data is double value ?
	function checkDoubleField(str, maxlength, firstLength, LastLength) {
		if (str.length > 8) {
			return false;
		} else {
			if (str.indexOf(".") == -1) {
				if (str.length <= firstLength) {
					return true;
				} else {
					return false;
				}
			} else {
				var docs =  str.indexOf(".");
				var lastDocs = str.lastIndexOf(".");
				var firstStr = str.substring(0, docs);
				var lastStr = str.substring(docs + 1, str.length);
				if (firstStr.length > 0 && firstStr.length <= firstLength) {
					if (lastStr.length > 0 && lastStr.length <= LastLength && docs == lastDocs) {
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
});