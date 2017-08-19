/**
 * @author Khoa Le
 * Create new cultivation result, edit and delete existed cultivation result
 */

$(document).ready(function() {
	//--------------- Constants definition ---------------
	var NEXT_SCREEN_NAME = "0037";
	var CURRENT_SCREEN_NAME = "0035";
	//--------------- Variables definition ---------------
	// application path
	var rootPath = getContextPath();
	// all cultivation result data
	var filteredCultivationResultData = [];
	var cultivationResultData = [];
	var lastUpdateDateGlobal = "";
	// current task data
	var currentTaskData;
	// current Select All status
	var selectAllFlag = false;
	// variable to store selected block id
	var currentBlockIdString = "";
	// variable to store selected block name
	var currentBlockName = "";
	// variable to store current selected index on Work list
	var currentSelectedWorkListIndex = 0;
	// variable to store current mode
	var CURRENT_MODE = "";
	// variable to check if getting Task data is successful or not
	var isTaskLoadSuccessful = true;

	// display current date
	$("#txtCurrentDate").text(getCurrentDate() + " (" + DAY_OF_WEEK + ") ");
	// display farm, area, block name
	farmNameGlobal = decodeURIComponent(farmNameGlobal);
	selectedAreaNameGlobal = decodeURIComponent(selectedAreaNameGlobal);
	$("#lblFarmName").replaceWith("<div id='lblFarmName' class='width200 float-left overflow-ellipsis-label'>" + farmNameGlobal + "</div>");
	$("#cbbArea").val(selectedAreaIdGlobal);
	// set height at first time 
	setHeightTableFirstTime();
	// display all block
	drawBlockTable();
	// get all cultivation result data of product in block
	getAllCultivationResultData();
	// get data for popup's combobox
	initPopupComboboxData();

	// Area combobox change event process
	$("#cbbArea").bind("change", function() {
		// get and set area and kind data
		selectedAreaIdGlobal = $(this).find("option:selected").val();
		selectedAreaNameGlobal = $(this).find("option:selected").text();
		selectedKindIdGlobal = $(this).find("option:selected").attr("kindid");
		selectedKindNameGlobal = $(this).find("option:selected").attr("kindname");

		// reset variables
		cultivationResultData = [];
		filteredCultivationResultData = [];
		selectAllFlag = false;
		currentBlockIdString = "";
		currentSelectedWorkListIndex = 0;
		CURRENT_MODE = "";
		isTaskLoadSuccessful = true;

		// reset UI
		$("#btnDeselectAll").addClass("display-none");
		$("#btnSelectAll").removeClass("display-none");
		// loop through all checkboxes
		$(".blockCheckbox").each(function() {
			// change checkbox state to "uncheck"
			$(this).prop("checked", false);
		});
		// clear table
		$("#tblBody").find("tbody").remove();
		$("#tblBlockBody").find("tbody").remove();
		// disable button register
		$("#btnRegister").addClass("btn-disable").prop("disabled", true);

		// get new data based on selected area
		// display all block
		drawBlockTable();
		// get all cultivation result data of product in block
		getAllCultivationResultData();
	});

	// Select all button event process
	$("#btnSelectAll, #btnDeselectAll").bind("click", function() {
		if (!$(this).hasClass("btn-disable")) {
			// switch flag
			selectAllFlag = !selectAllFlag;
			// Hide, Unhide buttons
			if (selectAllFlag) {
				$("#btnDeselectAll").removeClass("display-none");
				$("#btnSelectAll").addClass("display-none");
			} else {
				$("#btnDeselectAll").addClass("display-none");
				$("#btnSelectAll").removeClass("display-none");
			}
			// reset variable
			currentBlockIdString = "";
			// loop through all checkboxes
			$(".blockCheckbox").each(function() {
				// generate id string
				var idString = $(this).attr("name") + ",";
				var lastUpdateDate = $(this).attr("lastUpdateDate") + ",";
				// select all state
				if (selectAllFlag) {
					// change checkbox state to "check"
					$(this).prop("checked", true);
					// store to variable
					currentBlockIdString += idString;
				} else {
					// change checkbox state to "uncheck"
					$(this).prop("checked", false);
					// remove from variable
					currentBlockIdString = currentBlockIdString.replace(idString, "");
				}
			});

			// draw cultivation result table
			if (currentBlockIdString != "") {
				drawCultivationResultTable(true);
				// enable button register
				$("#btnRegister").removeClass("btn-disable").prop("disabled", false);
			} else {
				// clear table
				$("#tblBody").find("tbody").remove();
				$("#tblBody").append("<tbody></tbody>");
				// fix height table
				$(".div-farmwork-right").height(calculateTableContentHeight());
				$("#divBody").height(calculateTableContentHeight() - 23);
				$("#tblBody").height(calculateTableContentHeight() - 24);
				// fix table header and body when scrolling only the table body
				fixTable();
				// disable button register
				$("#btnRegister").addClass("btn-disable").prop("disabled", true);
			}
		}
	});
	$("#btnSelectAll").click();
	// Block checkbox click event process
	$(document).on("click", ".blockCheckbox", function() {
		// generate id string
		var idString = $(this).attr("name") + ",";
		// check status of block
		if ($(this).is(":checked") && currentBlockIdString.indexOf(idString) < 0) {
			// store to variable
			currentBlockIdString += idString;
			// fix UI bug
			$(this).prop("checked", true);
		} else {
			// remove from variable
			currentBlockIdString = currentBlockIdString.replace(idString, "");
			// fix UI bug
			$(this).prop("checked", false);
		}

		// check number of selected product
		if (currentBlockIdString == "") {
			// disable button register
			$("#btnRegister").addClass("btn-disable").prop("disabled", true);
		} else {
			// enable button register
			$("#btnRegister").removeClass("btn-disable").prop("disabled", false);
		}

		// draw cultivation result table
		drawCultivationResultTable(false);
	});

	// Block link click event process
	$(document).on("click", ".blockLink", function(e) {
		e.preventDefault();
		// check authority of user
		if (!checkAuthority(NEXT_SCREEN_NAME)) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// get necessary parameters
			var blockId = $(this).attr("name");
			var blockName = $(this).html();

			// create parameter object
			var parameterObj = {
				"url": rootPath + '/' + NEXT_SCREEN_NAME + '/',
				"farmId": farmIdGlobal,
				"farmName": encodeURIComponent(farmNameGlobal),
				"areaId": selectedAreaIdGlobal,
				"areaName": encodeURIComponent(selectedAreaNameGlobal),
				"blockId": blockId,
				"blockName": encodeURIComponent(blockName)
			};
			// save to history
			savePageToHistory(parameterObj);

			// create form to submit
			$('<form>', {
				"id": "formChangePage",
				"html": '<input type="hidden" id="farmId" name="farmId" value="' + farmIdGlobal + '" />' +
						'<input type="hidden" id="farmName" name="farmName" value="' + encodeURIComponent(farmNameGlobal) + '" />' +
						'<input type="hidden" id="areaId" name="areaId" value="' + selectedAreaIdGlobal + '" />' +
						'<input type="hidden" id="areaName" name="areaName" value="' + encodeURIComponent(selectedAreaNameGlobal) + '" />' +
						'<input type="hidden" id="blockId" name="blockId" value="' + blockId + '" />' +
						'<input type="hidden" id="blockName" name="blockName" value="' + encodeURIComponent(blockName) + '" />',
				"method": 'POST',
				"action": rootPath + '/' + NEXT_SCREEN_NAME + '/'
			}).appendTo(document.body).submit();
		}
	});

	// View icon click event process
	$(document).on("click", ".view", function() {
		// set mode
		CURRENT_MODE = MODE_VIEW;
		// get selected index
		var selectedIndex = parseInt($(this).attr("name"));
		// reset block name
		currentBlockName = "";
		// initialize data when opening popup
		initPopupData(selectedIndex);
		// display operation detail popup
		if (isTaskLoadSuccessful) {
			showPopup($("#popupWrapper"));
		} else {
			// reset flag
			isTaskLoadSuccessful = true;
		}
	});

	// Delete icon click event process
	$(document).on("click", ".delete", function() {
		if (!checkRole(CURRENT_SCREEN_NAME, DELETABLE_FLAG)) {
			// display message
			jWarning(ROLE_DELET_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// set mode
			CURRENT_MODE = MODE_DELETE;
			// check if user selected a product or not
			if (currentBlockIdString == "") {
				// blank field(s)
				// display message
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// store selected index to variable
				currentSelectedWorkListIndex = parseInt($(this).attr("name"));
				// reset block name
				currentBlockName = "";
				// initialize data when opening popup
				initPopupData(0);
				// display operation detail popup
				if (isTaskLoadSuccessful) {
					showPopup($("#popupWrapper"));
				} else {
					// reset flag
					isTaskLoadSuccessful = true;
				}
			}
		}
	});

	// Back button event process
	$("#btnBack").bind("click", function() {
		goBackToPreviousPage();
	});

	// Add button event process
	$("#btnRegister").bind("click", function() {
		if (!$(this).hasClass("btn-disable")) {
			if (!checkRole(CURRENT_SCREEN_NAME, ADDABLE_FLAG)) {
				// display message
				jWarning(ROLE_ADD_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// set mode
				CURRENT_MODE = MODE_NEW;
				// check if user selected a product or not
				if (currentBlockIdString == "") {
					// blank field(s)
					// display message
					jWarning(VALIDATE_BLANK_FIELDS_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
				} else {
					// check if there is more than one checkbox selected
					// check if there is more than one block selected
					var blockIdArray = currentBlockIdString.split(",").filter(function(el) {return el.length != 0});
					if (blockIdArray.length > 1) {
						currentBlockName = "";
					} else {
						currentBlockName = $(".blockCheckbox:checked").val();
					}
					// initialize data when opening popup
					initPopupData(0);
					// display operation detail popup
					if (isTaskLoadSuccessful) {
						showPopup($("#popupWrapper"));
					} else {
						// reset flag
						isTaskLoadSuccessful = true;
					}
				}
			}
		}
	});

	// Working date change event process
	$("#txtWorkingDatePopup").bind("change", function() {
		// clear all date text fields
		$("#txtPlantDatePopup").val("");
		$("#txtFlowerDatePopup").val("");
		$("#txtBagDatePopup").val("");
		$("#txtHarvestDatePopup").val("");
		// get task combobox selected index
		var selectedTaskIndex = $("#cbbTaskPopup").prop("selectedIndex");
		// get task change point code
		var changePointCode = currentTaskData[selectedTaskIndex].changePointCode;
		// check change point code
		if (changePointCode == TASK_CHANGE_POINT_CODE_PLANTING) {
			$("#txtPlantDatePopup").val($(this).val());
		} else if (changePointCode == TASK_CHANGE_POINT_CODE_FLOWERING) {
			$("#txtFlowerDatePopup").val($(this).val());
		} else if (changePointCode == TASK_CHANGE_POINT_CODE_BAGGED) {
			$("#txtBagDatePopup").val($(this).val());
		} else if (changePointCode == TASK_CHANGE_POINT_CODE_HARVESTED) {
			$("#txtHarvestDatePopup").val($(this).val());
		}
	});

	// Process combobox change event process
	$("#cbbProcessPopup").bind("change", function() {
		// load Task combobox
		loadTaskCombobox();
		if (isTaskLoadSuccessful) {
			$("#cbbTaskPopup").trigger("change");
		}
	});

	// Task combobox change event process
	$("#cbbTaskPopup").bind("change", function() {
		// only fill product date in new mode
		if (CURRENT_MODE == MODE_NEW) {
			// clear all date text fields
			$("#txtPlantDatePopup").val("");
			$("#txtFlowerDatePopup").val("");
			$("#txtBagDatePopup").val("");
			$("#txtHarvestDatePopup").val("");
			// get selected index
			var selectedIndex = $(this).prop("selectedIndex");
			// set value to working content and precaution
			$("#txtWorkContentPopup").replaceWith("<textarea id='txtWorkContentPopup' class='txtarea-lv60 height60' rows='4' maxlength='250' disabled='disabled'>" + currentTaskData[selectedIndex].workingDetails + "</textarea>");
			$("#txtPrecautionPopup").replaceWith("<textarea id='txtPrecautionPopup' class='txtarea-lv60 height60' rows='4' maxlength='250' disabled='disabled'>" + currentTaskData[selectedIndex].note + "</textarea>");
			// get task change point code
			var changePointCode = currentTaskData[selectedIndex].changePointCode;
			// check change point code
			if (changePointCode == TASK_CHANGE_POINT_CODE_PLANTING) {
				$("#txtPlantDatePopup").val($("#txtWorkingDatePopup").val());
			} else if (changePointCode == TASK_CHANGE_POINT_CODE_FLOWERING) {
				$("#txtFlowerDatePopup").val($("#txtWorkingDatePopup").val());
			} else if (changePointCode == TASK_CHANGE_POINT_CODE_BAGGED) {
				$("#txtBagDatePopup").val($("#txtWorkingDatePopup").val());
			} else if (changePointCode == TASK_CHANGE_POINT_CODE_HARVESTED) {
				$("#txtHarvestDatePopup").val($("#txtWorkingDatePopup").val());
			}
		}
	});

	// Register button in popup click event process
	$("#btnRegisterPopup").bind("click", function() {
		// check mode
		if (CURRENT_MODE == MODE_DELETE) {
			// display confirmation message
			jQuestion_warning(DELETE_CONFIRM_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
				if (val) {
					// get selected item
					var selectedItem = filteredCultivationResultData[currentSelectedWorkListIndex];
					// create key object
					var cultivationResultKey = {
						farmId: farmIdGlobal,
						areaId: selectedAreaIdGlobal,
						blockId: selectedItem.blockId,
						lineId: DEFAULT_LINE_ID,
						columnId: DEFAULT_COLUMN_ID,
						previousRound: selectedItem.previousRound,
						workingDate: convertDataFrom(selectedItem.workingDateString),
						processId: selectedItem.processId,
						taskId: selectedItem.taskId,
						changePointCode: selectedItem.changePointCode,
						lastUpdateDateCultivation: selectedItem.lastUpdateDateCultivation,
						lastUpdateDateProduct: selectedItem.lastUpdateDateProduct
					};
					// make Ajax call to server to delete data
					$.ajax({
						type: "POST",
						url: "deleteData",
						contentType: "application/json",
						data: JSON.stringify(cultivationResultKey),
						async: false,
						success: function(returnedJsonData) {
							if (checkSessionTimeout(returnedJsonData) == 1) return;
							if (returnedJsonData != "") {
								// check returned value from server
								if (returnedJsonData == DELETE_RESULT_SUCCESSFUL) {
									// reset Product state and get new cultivation data
									resetScreenStateAndCultivationData();
									// hide block info popup
									hidePopup($("#popupWrapper"));
									// display message
									jInfo(DELETE_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
								} else if (returnedJsonData == DELETE_RESULT_FAILED_CULTIVATION) {
									// failed
									// display message
									jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
								} else if (returnedJsonData == DELETE_RESULT_FAILED_EXCEPTION) {
									// failed
									// display message
									jWarning(DELETE_RESULT_FAILED_MESSAGE.replace('$1', DELETE_RESULT_FAILED_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
								} else if (returnedJsonData == DELETE_RESULT_FAILED_PRODUCT) {
									// failed
									// display message
									jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
								} else if (returnedJsonData == DELETE_RESULT_FAILED_PRODUCT_UPDATE_DATE) {
									// failed
									// display message
									jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
								} else if (returnedJsonData == DELETE_RESULT_FAILED_CULTIVATION_UPDATE_DATE) {
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
			});
		} else if (CURRENT_MODE == MODE_NEW) {
			// check for user input
			if (checkInputBlankFields() != "") {
				// blank field(s)
				// display message
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// get task combobox selected index
				var selectedTaskIndex = $("#cbbTaskPopup").prop("selectedIndex");
				// get task change point code
				var changePointCode = currentTaskData[selectedTaskIndex].changePointCode;

				// create data object
				var cultivationResulData = {
					farmId: farmIdGlobal,
					areaId: selectedAreaIdGlobal,
					currentBlockIdString: currentBlockIdString,
					lastUpdateDateProduct: cultivationResultData.length == 0 ? lastUpdateDateGlobal : cultivationResultData[0].lastUpdateDateProduct,
					workingDate: convertDataFrom($("#txtWorkingDatePopup").val()),
					processId: $("#cbbProcessPopup").val(),
					taskId: $("#cbbTaskPopup").val(),
					statusId: $("#cbbStatusPopup").val(),
					note:  $.trim($("#txtNotePopup").val()),
					changePointCode: changePointCode
				};
				// make Ajax call to server to get data
				$.ajax({
					type: "POST",
					url: "insertData",
					contentType: "application/json",
					data: JSON.stringify(cultivationResulData),
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
								// reset Product state and get new cultivation data
								resetScreenStateAndCultivationData();
								// hide block info popup
								hidePopup($("#popupWrapper"));
								// display message
								jInfo(INSERT_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_DUPLICATED) {
								// duplicated user id
								// display message
								jWarning(INSERT_RESULT_DUPLICATED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_FAILED_PRODUCT) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_FAILED_CULTIVATION) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_FAILED_EXCEPTION) {
								// failed
								// display message
								jWarning(INSERT_RESULT_FAILED_MESSAGE.replace('$1', INSERT_RESULT_FAILED_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_FAILED_PRODUCT_UPDATE_DATE) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_FAILED_BLOCK_DELETE) {
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
		// hide block info popup
		hidePopup($("#popupWrapper"));
	});

	// Get data for popup's combobox process
	function initPopupComboboxData() {
		// process
		if (processData != "") {
			var optionStr = "";
			for (var i = 0; i < processData.length; i++) {
				optionStr += "<option value='" + processData[i].processId + "'>" + processData[i].processName + "</option>";
			}
			// append to combobox
			$("#cbbProcessPopup").append(optionStr);
		}
		// status
		if (statusData != "") {
			var optionStr = "";
			for (var i = 0; i < statusData.length; i++) {
				optionStr += "<option value='" + statusData[i].statusId + "'>" + statusData[i].statusName + "</option>";
			}
			// append to combobox
			$("#cbbStatusPopup").append(optionStr);
		}
	}

	// Draw block data
	function drawBlockTable() {
		// make Ajax call to server to get data
		$.ajax({
			url: "getBlockData",
			data: { "farmId": farmIdGlobal, "areaId": selectedAreaIdGlobal },
			type: "POST",
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				if (returnedJsonData != "") {
					// clear table
					$("#tblBlockBody").find("tbody").remove();
					// create table starts
					tableStringArray = [];

					// add tbody open tag
					tableStringArray.push("<tbody>");
					for (var i = 0; i < returnedJsonData.length; i++) {
						// row open tag
						tableStringArray.push("<tr>");
						// checkbox
						tableStringArray.push("<td class='align-center'><input type='checkbox' class='blockCheckbox' name='"
								+ returnedJsonData[i].blockId + "' value='" + returnedJsonData[i].blockName + "'></td>");
						// no.
						tableStringArray.push("<td class='align-center'>" + (i + 1) + "</td>");
						// block name
						tableStringArray.push("<td><a href='#' class='blockLink' name='" + returnedJsonData[i].blockId + "'>"
								+ returnedJsonData[i].blockName + "</a></td>");
						// row close tag
						tableStringArray.push("</tr>");
					}
					tableStringArray.push("<tr>");
					tableStringArray.push("<td colspan='3'></td>");
					tableStringArray.push("</tr>");
					// add tbody close tag
					tableStringArray.push("</tbody>");
					// append all created string to table
					$("#tblBlockBody").append(tableStringArray.join(''));

					// fix header size
					$("#divHeadBlock").width($("#divBodyBlock").width() - 17);

					// fix height table
					$(".div-farmwork").height(calculateTableContentHeight());
					$("#divBodyBlock").height(calculateTableContentHeight() - 50);
					$("#tblBlockBody").height(calculateTableContentHeight() - 51);

					// scroll to top of table
					$("#divBodyBlock").scrollTop(0).scrollLeft(0);
				} else {
					// disable button select all
					$("#btnSelectAll").addClass("btn-disable").prop("disabled", true);
					// display error message
					jWarning(SEARCH_RESULT_NO_DATA_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
				}
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	}

	// Get all cultivation result data
	function getAllCultivationResultData() {
		// make Ajax call to server to get data
		$.ajax({
			url: "getCultivationResultData",
			data: { "farmId": farmIdGlobal, "areaId": selectedAreaIdGlobal, "kindId": selectedKindIdGlobal },
			type: "POST",
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				// store data in global variable
				cultivationResultData = returnedJsonData;
				if (cultivationResultData.length == 0) {
					$.ajax({
						url: "getLastUpdateDateProduct",
						data: { "farmId": farmIdGlobal, "areaId": selectedAreaIdGlobal },
						type: "POST",
						async: false,
						success: function(returnedJsonData) {
							if (checkSessionTimeout(returnedJsonData) == 1) return;
							lastUpdateDateGlobal = returnedJsonData;
						},
						error: function(e) {
							// display error message
							jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
						}
					});
				}
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	}

	// Draw cultivation result data table based on selected ids
	function drawCultivationResultTable(isDrawAll) {
		if (!checkRole(CURRENT_SCREEN_NAME, REFERENCE_FLAG)) {
			// display message
			jWarning(ROLE_REFER_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			return;
		}

		// reset data array
		filteredCultivationResultData = [];
		// check isDrawAll variable
		if (!isDrawAll) {
			// get block id array
			var blockIdArray = currentBlockIdString.split(",").filter(function(el) {return el.length != 0});
			for (var k = 0; k < cultivationResultData.length; k++) {
				// get current data
				var currentItem = cultivationResultData[k];
				// loop through all selected product id
				for (var l = 0; l < blockIdArray.length; l++) {
					var currentId = blockIdArray[l];
					// compare id
					if (currentItem.blockId === currentId) {
						// push this item to array
						if ($.inArray(currentItem, filteredCultivationResultData) < 0) {
							filteredCultivationResultData.push(currentItem);
						}
					}
				}
			}
		} else {
			// draw all data
			filteredCultivationResultData = cultivationResultData;
		}
		// clear table
		$("#tblBody").find("tbody").remove();
		// create table starts
		var tableStringArray = [];

		// add open tag
		tableStringArray.push("<tbody>");
		for (var i = 0; i < filteredCultivationResultData.length; i++) {
			// get current data
			var currentItem = filteredCultivationResultData[i];
			// add open tag
			tableStringArray.push("<tr>");
			// no.
			tableStringArray.push("<td class='overflow-ellipsis align-center'>" + (i + 1) + "</td>");
			// block name
			tableStringArray.push("<td class='overflow-ellipsis'>" + currentItem.blockName + "</td>");
			// working date
			tableStringArray.push("<td class='overflow-ellipsis'>" + currentItem.workingDateString + "</td>");
			// process
			tableStringArray.push("<td class='overflow-ellipsis'>" + currentItem.processName + "</td>");
			// task
			tableStringArray.push("<td class='overflow-ellipsis'>" + currentItem.taskName + "</td>");
			// work content
			tableStringArray.push("<td class='overflow-ellipsis'>" + currentItem.workingContent + "</td>");
			// status
			tableStringArray.push("<td class='overflow-ellipsis'>" + currentItem.statusName + "</td>");
			// note
			var note = currentItem.note == null ? "" : currentItem.note;
			tableStringArray.push("<td class='overflow-ellipsis'>" + note + "</td>");
			// view icon
			tableStringArray.push("<td><img class='view cursor-pointer' name='" + i + "' src='"
					+ rootPath + "/resources/img/icon_ref.png?date=" + icon_refDate + "'></td>");
			// delete icon
			tableStringArray.push("<td><img class='delete cursor-pointer' name='" + i + "' src='"
					+ rootPath + "/resources/img/icon_del.png?date=" + icon_delDate + "'></td>");
			// add close tag
			tableStringArray.push("</tr>");
		}
		tableStringArray.push("<tr>");
		tableStringArray.push("<td colspan='10'></td>");
		tableStringArray.push("</tr>");
		// add close tag
		tableStringArray.push("</tbody>");

		// append all created string to table
		$("#tblBody").append(tableStringArray.join(''));

		// fix height table
		$(".div-farmwork-right").height(calculateTableContentHeight());
		$("#divBody").height(calculateTableContentHeight() - 23);
		$("#tblBody").height(calculateTableContentHeight() - 24);

		// fix table header and body when scrolling only the table body
		fixTable();

		// scroll to top of table
		$("#divBody").scrollTop(0).scrollLeft(0);
	}
	
	// set height at first time 
	function setHeightTableFirstTime(){

		// fix height table left
		$(".div-farmwork").height(calculateTableContentHeight());
		$("#divBodyBlock").height(calculateTableContentHeight() - 50);
		$("#tblBlockBody").height(calculateTableContentHeight() - 51);

		// fix height table right
		$(".div-farmwork-right").height(calculateTableContentHeight());
		$("#divBody").height(calculateTableContentHeight() - 23);
		$("#tblBody").height(calculateTableContentHeight() - 24);
	}
	
	// Fix height unregistered table
	function calculateTableContentHeight() {
		// get all the necessary height of controls
		var windowHeight = $(window).height();
		var headerHeight = $(".header").height();
		var headerGroupButtonHeight = $(".header-group-button").height();
		var titleHeight = $(".title").height();
		var searchConditionHeight = $(".tbl-box").height();
		var footerHeight = $(".footer").height();
		var estimatedSafeHeight = 125;
		// calculate table body height
		return windowHeight - headerHeight - headerGroupButtonHeight - titleHeight - 
			searchConditionHeight - footerHeight - estimatedSafeHeight;
	}

	// Initialize data when opening popup
	function initPopupData(selectedIndex) {
		// Farm name
		$("#txtFarmNamePopup").val(convertHtmlToText(farmNameGlobal));
		// Area name
		$("#txtAreaNamePopup").val(convertHtmlToText(selectedAreaNameGlobal));
		// Block name
		$("#txtBlockNamePopup").val(currentBlockName);
		// Kind name
		$("#txtKindNamePopup").val(convertHtmlToText(selectedKindNameGlobal));
		// initialize date picker
		$("#txtWorkingDatePopup").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		}).datepicker("setDate", new Date());
		$("#txtWorkingDatePopup").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		// clear all date text fields
		$("#txtPlantDatePopup").val("");
		$("#txtFlowerDatePopup").val("");
		$("#txtBagDatePopup").val("");
		$("#txtHarvestDatePopup").val("");
		// set values based on mode
		if (CURRENT_MODE == MODE_NEW) {
			// current date
			$("#txtWorkingDatePopup").prop("disabled", false);
			$("#txtWorkingDatePopup").datepicker("enable");
			// process
			$("#cbbProcessPopup").prop("disabled", false);
			$("#cbbProcessPopup option").eq(0).prop("selected", true);
			$("#cbbProcessPopup").trigger("change");
			// task
			$("#cbbTaskPopup").prop("disabled", false);
			$("#cbbTaskPopup option").eq(0).prop("selected", true);
			// status
			$("#cbbStatusPopup").prop("disabled", false);
			$("#cbbStatusPopup option").eq(0).prop("selected", true);
			// note
			$("#txtNotePopup").prop("disabled", false);
			$("#txtNotePopup").val("");
		} else if (CURRENT_MODE == MODE_VIEW || CURRENT_MODE == MODE_DELETE) {
			// get current item
			var currentItem = filteredCultivationResultData[selectedIndex];
			// block name
			$("#txtBlockNamePopup").val(convertHtmlToText(currentItem.blockName));
			// working date
			$("#txtWorkingDatePopup").prop("disabled", true);
			$("#txtWorkingDatePopup").datepicker("disable");
			$("#txtWorkingDatePopup").val(currentItem.workingDateString);
			// process
			$("#cbbProcessPopup").prop("disabled", true);
			$("#cbbProcessPopup").val(currentItem.processId);
			$("#cbbProcessPopup").trigger("change");
			// task
			$("#cbbTaskPopup").prop("disabled", true);
			$("#cbbTaskPopup").val(currentItem.taskId);
			// work content
			$("#txtWorkContentPopup").replaceWith("<textarea id='txtWorkContentPopup' class='txtarea-lv60 height60' rows='4' maxlength='250' disabled='disabled'>" + currentItem.workingContent + "</textarea>");
			// precaution
			$("#txtPrecautionPopup").replaceWith("<textarea id='txtPrecautionPopup' class='txtarea-lv60 height60' rows='4' maxlength='250' disabled='disabled'>" + currentItem.precaution + "</textarea>");
			// status
			$("#cbbStatusPopup").prop("disabled", true);
			$("#cbbStatusPopup").val(currentItem.statusId);
			// note
			$("#txtNotePopup").prop("disabled", true);
			$("#txtNotePopup").replaceWith("<textarea id='txtNotePopup' class='txtarea-lv60 height60' rows='4' maxlength='250' disabled='disabled'>" + currentItem.note + "</textarea>");
		}

		// hide button in View detail mode
		if (CURRENT_MODE == MODE_VIEW) {
			$("#btnRegisterPopup").addClass("display-none");
		}
		// show button in other modes
		else {
			$("#btnRegisterPopup").removeClass("display-none");
		}

		// change titles based on modes
		if (CURRENT_MODE == MODE_NEW) {
			$("#titlePopupNew").removeClass("display-none");
			$("#titlePopupDelete").addClass("display-none");
			$("#titlePopupView").addClass("display-none");
		} else if (CURRENT_MODE == MODE_VIEW) {
			$("#titlePopupNew").addClass("display-none");
			$("#titlePopupDelete").addClass("display-none");
			$("#titlePopupView").removeClass("display-none");
		} else if (CURRENT_MODE == MODE_DELETE) {
			$("#titlePopupNew").addClass("display-none");
			$("#titlePopupDelete").removeClass("display-none");
			$("#titlePopupView").addClass("display-none");
		}
	}

	// Load Task combobox data
	function loadTaskCombobox() {
		// get process id selected by user
		var selectedProcessId = $("#cbbProcessPopup").find("option:selected").val(); 
		selectedProcessId = selectedProcessId == undefined ? null : selectedProcessId;
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "getTaskData",
			data: { "farmId": farmIdGlobal, "kindId": selectedKindIdGlobal, "processId": selectedProcessId },
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				// switch flag
				isTaskLoadSuccessful = true;
				// create option string
				var optionStr = "";
				if (typeof returnedJsonData == "object") {
					if (returnedJsonData.length > 0) {
						// store task data to variable
						currentTaskData = returnedJsonData;
						for (var i = 0; i < currentTaskData.length; i++) {
							optionStr += "<option value='" + currentTaskData[i].taskId + "'>" + currentTaskData[i].taskName + "</option>";
						}
					} else {
						// switch flag
						isTaskLoadSuccessful = false;
						// display error message
						jWarning(MESSAGE_TASK_NULL_ERROR, DIALOG_TITLE, DIALOG_OK_BUTTON);
					}
				} else {
					// switch flag
					isTaskLoadSuccessful = false;
					// display error message
					jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
				}
				// set options to combobox
				$("#cbbTaskPopup").empty().append(optionStr);
			},
			error: function(e) {
				// switch flag
				isTaskLoadSuccessful = false;
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	}

	// Reset Product state and get new cultivation data
	function resetScreenStateAndCultivationData() {
		// get all cultivation result data of product in block
		getAllCultivationResultData();
		// draw cultivation result table
		drawCultivationResultTable(false);
		// loop through all images
		$(".blockCheckbox").each(function() {
			// generate id string
			var idString = $(this).attr("name") + ",";
			// select all state
			if (currentBlockIdString.indexOf(idString) >= 0) {
				// change checkbox state to "check"
				$(this).prop("checked", true);
			}
		});
	}
	
	// Check input: blank fields
	function checkInputBlankFields() {
		if ($("#txtWorkingDatePopup").val() == ""){
			return workingDatePopupBlank;
		} else if($("#cbbProcessPopup").val() == null){
			return cbbProcessPopupBlank;
		} else if($("#cbbTaskPopup").val() == null) {
			return cbbTaskPopupBlank;
		} else if($("#cbbStatusPopup").val() == null) {
			return cbbStatusPopupBlank;
		} else {
			return "";
		}
	}
});