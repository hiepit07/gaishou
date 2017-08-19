/**
 * @author Khoa Le
 * Create new cultivation result, edit and delete existed cultivation result
 */

$(document).ready(function() {
	//--------------- Constants definition ---------------
	// max font size allowed in Header
	var MAX_FONT_SIZE = 15;
	var CURRENT_SCREEN_NAME = "0037";
	//--------------- Variables definition ---------------
	// application path
	var rootPath = getContextPath();
	// all cultivation result data
	var cultivationResultData = [];
	// current cultivation result data
	var filteredCultivationResultData = [];
	// current task data
	var currentTaskData;
	// current Select All status
	var selectAllFlag = false;
	// variable to store selected product id
	var currentProductIdString = "";
	var currentProductLastUpdateDateString = "";
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
	areaNameGlobal = decodeURIComponent(areaNameGlobal);
	blockNameGlobal = decodeURIComponent(blockNameGlobal);
	$("#lblFarmName").replaceWith("<div id='lblFarmName' class='width200 float-left overflow-ellipsis-label'>" + farmNameGlobal + "</div>");
	$("#lblAreaName").replaceWith("<div id='lblAreaName' class='width200 float-left overflow-ellipsis-label'>" + areaNameGlobal + "</div>");
	$("#lblBlockName").replaceWith("<div id='lblBlockName' class='width200 float-left overflow-ellipsis-label'>" + blockNameGlobal + "</div>");
	// set height at first time 
	setHeightTableFirstTime();
	// display all product in block
	drawProductTable();
	// get all cultivation result data of product in block
	getAllCultivationResultData();
	// get data for popup's combobox
	initPopupComboboxData();

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
			currentProductIdString = "";
			// loop through all images
			$(".productImg").each(function() {
				// check for clickable images
				if (!$(this).hasClass("productRemoved")) {
					// generate id string
					var idString = $(this).attr("name") + ",";
					var lastUpdateDate = $(this).attr("lastUpdateDate") + ",";
					// select all state
					if (selectAllFlag) {
						if ($(this).attr("src").indexOf("disable") == -1) {
							// change image type to "check"
							if ($(this).attr("src").indexOf("_check") == -1) {
								if ($(this).attr("src").indexOf("disable") >= 0) {
									$(this).attr("src", rootPath + "/resources/img/disable_check.png?date=" + disable_checkDate);
								} else if ($(this).attr("src").indexOf("hole") >= 0) {
									$(this).attr("src", rootPath + "/resources/img/hole_check.png?date=" + hole_checkDate);
								} else if ($(this).attr("src").indexOf("planted") >= 0) {
									$(this).attr("src", rootPath + "/resources/img/planted_check.png?date=" + planted_checkDate);
								} else if ($(this).attr("src").indexOf("fostering") >= 0) {
									$(this).attr("src", rootPath + "/resources/img/fostering_check.png?date=" + fostering_checkDate);
								} else if ($(this).attr("src").indexOf("flower") >= 0) {
									$(this).attr("src", rootPath + "/resources/img/flower_check.png?date=" + flower_checkDate);
								} else if ($(this).attr("src").indexOf("bagged") >= 0) {
									$(this).attr("src", rootPath + "/resources/img/bagged_check.png?date=" + bagged_checkDate);
								} else if ($(this).attr("src").indexOf("cultivated") >= 0) {
									$(this).attr("src", rootPath + "/resources/img/cultivated_check.png?date=" + cultivated_checkDate);
								}
							}
							// store to variable
							currentProductIdString += idString;
						} else {
							$(this).attr("src", $(this).attr("src").replace("disable_check", "disable"));
						}
					} else {
						// change image type to "uncheck"
						if ($(this).attr("src").indexOf("disable") >= 0) {
							$(this).attr("src", rootPath + "/resources/img/disable.png?date=" + disableDate);
						} else if ($(this).attr("src").indexOf("hole") >= 0) {
							$(this).attr("src", rootPath + "/resources/img/hole.png?date=" + holeDate);
						} else if ($(this).attr("src").indexOf("planted") >= 0) {
							$(this).attr("src", rootPath + "/resources/img/planted.png?date=" + plantedDate);
						} else if ($(this).attr("src").indexOf("fostering") >= 0) {
							$(this).attr("src", rootPath + "/resources/img/fostering.png?date=" + fosteringDate);
						} else if ($(this).attr("src").indexOf("flower") >= 0) {
							$(this).attr("src", rootPath + "/resources/img/flower.png?date=" + flowerDate);
						} else if ($(this).attr("src").indexOf("bagged") >= 0) {
							$(this).attr("src", rootPath + "/resources/img/bagged.png?date=" + baggedDate);
						} else if ($(this).attr("src").indexOf("cultivated") >= 0) {
							$(this).attr("src", rootPath + "/resources/img/cultivated.png?date=" + cultivatedDate);
						}
						// remove from variable
						currentProductIdString = currentProductIdString.replace(idString, "");
					}
				}
			});

			// draw cultivation result table
			if (currentProductIdString != "") {
				drawCultivationResultTable(false);
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
	// Product adjustment image click event process
	$(document).on("click", ".productImg", function() {
		// check for clickable images
		if (!$(this).hasClass("productRemoved")) {
			// generate id string
			var idString = $(this).attr("name") + ",";
			var lastUpdateDate = $(this).attr("lastUpdateDate") + ",";
			// check status of product
			if ($(this).attr("src").indexOf("_check") == -1) {
				// change image type to "check"
				if ($(this).attr("src").indexOf("disable") >= 0) {
					$(this).attr("src", rootPath + "/resources/img/disable_check.png?date=" + disable_checkDate);
				} else if ($(this).attr("src").indexOf("hole") >= 0) {
					$(this).attr("src", rootPath + "/resources/img/hole_check.png?date=" + hole_checkDate);
				} else if ($(this).attr("src").indexOf("planted") >= 0) {
					$(this).attr("src", rootPath + "/resources/img/planted_check.png?date=" + planted_checkDate);
				} else if ($(this).attr("src").indexOf("fostering") >= 0) {
					$(this).attr("src", rootPath + "/resources/img/fostering_check.png?date=" + fostering_checkDate);
				} else if ($(this).attr("src").indexOf("flower") >= 0) {
					$(this).attr("src", rootPath + "/resources/img/flower_check.png?date=" + flower_checkDate);
				} else if ($(this).attr("src").indexOf("bagged") >= 0) {
					$(this).attr("src", rootPath + "/resources/img/bagged_check.png?date=" + bagged_checkDate);
				} else if ($(this).attr("src").indexOf("cultivated") >= 0) {
					$(this).attr("src", rootPath + "/resources/img/cultivated_check.png?date=" + cultivated_checkDate);
				}
				// store to variable
				currentProductIdString += idString;
			} else {
				// change image type to "uncheck"
				if ($(this).attr("src").indexOf("disable") >= 0) {
					$(this).attr("src", rootPath + "/resources/img/disable.png?date=" + disableDate);
				} else if ($(this).attr("src").indexOf("hole") >= 0) {
					$(this).attr("src", rootPath + "/resources/img/hole.png?date=" + holeDate);
				} else if ($(this).attr("src").indexOf("planted") >= 0) {
					$(this).attr("src", rootPath + "/resources/img/planted.png?date=" + plantedDate);
				} else if ($(this).attr("src").indexOf("fostering") >= 0) {
					$(this).attr("src", rootPath + "/resources/img/fostering.png?date=" + fosteringDate);
				} else if ($(this).attr("src").indexOf("flower") >= 0) {
					$(this).attr("src", rootPath + "/resources/img/flower.png?date=" + flowerDate);
				} else if ($(this).attr("src").indexOf("bagged") >= 0) {
					$(this).attr("src", rootPath + "/resources/img/bagged.png?date=" + baggedDate);
				} else if ($(this).attr("src").indexOf("cultivated") >= 0) {
					$(this).attr("src", rootPath + "/resources/img/cultivated.png?date=" + cultivatedDate);
				}
				// remove from variable
				currentProductIdString = currentProductIdString.replace(idString, "");
			}

			var disable_check = false;
			$(".productImg").each(function() {			
				if (!$(this).hasClass("productRemoved")) {
					if ($(this).attr("src").indexOf("disable_check") !== -1) {
						disable_check = true;
					}
				}
			});
			// check number of selected product
			if (currentProductIdString == "" || disable_check) {
				// disable button register
				$("#btnRegister").addClass("btn-disable").prop("disabled", true);				
			} else {
				// enable button register
				$("#btnRegister").removeClass("btn-disable").prop("disabled", false);				
			}
			
			if (disable_check) {
				$("#btnDeselectAll").addClass("btn-disable").prop("disabled", true);
				$("#btnSelectAll").addClass("btn-disable").prop("disabled", true);
			} else {
				$("#btnDeselectAll").removeClass("btn-disable").prop("disabled", false);
				$("#btnSelectAll").removeClass("btn-disable").prop("disabled", false);
			}
			// draw cultivation result table
			drawCultivationResultTable(false);
		}
	});

	// View icon click event process
	$(document).on("click", ".view", function() {
		// set mode
		CURRENT_MODE = MODE_VIEW;
		// get selected index
		var selectedIndex = parseInt($(this).attr("name"));
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
			if (currentProductIdString == "") {
				// blank field(s)
				// display message
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// store selected index to variable
				currentSelectedWorkListIndex = parseInt($(this).attr("name"));
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
				if (currentProductIdString == "") {
					// blank field(s)
					// display message
					jWarning(VALIDATE_BLANK_FIELDS_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
				} else {
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
		// refill old values to their places
		if (CURRENT_MODE == MODE_VIEW || CURRENT_MODE == MODE_DELETE) {
			// get current item
			var currentItem = filteredCultivationResultData[0];
			// planting date
			$("#txtPlantDatePopup").val(currentItem.plantingDate);
			// flowering date
			$("#txtFlowerDatePopup").val(currentItem.floweringDate);
			// bag closing date
			$("#txtBagDatePopup").val(currentItem.bagClosingDate);
			// harvest date
			$("#txtHarvestDatePopup").val(currentItem.harvestDate);
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
						areaId: areaIdGlobal,
						blockId: blockIdGlobal,
						lineId: selectedItem.lineId,
						columnId: selectedItem.columnId,
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
									// display all product in block
									drawProductTable();
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
				
				currentProductLastUpdateDateString = "";				
				var productIdArray = currentProductIdString.split(",").filter(function(el) {return el.length != 0});
				for (var intProductID = 0; intProductID < productIdArray.length; intProductID++) {
					currentProductLastUpdateDateString += $("img[name='" + productIdArray[intProductID] + "']").attr("lastupdatedate") + ",";
				}
				
				// create data object
				var cultivationResulData = {
					farmId: farmIdGlobal,
					areaId: areaIdGlobal,
					blockId: blockIdGlobal,
					currentProductIdString: currentProductIdString,
					currentLastUpdateDateString: currentProductLastUpdateDateString,
					workingDate: convertDataFrom($("#txtWorkingDatePopup").val()),
					processId: $("#cbbProcessPopup").val(),
					taskId: $("#cbbTaskPopup").val(),
					statusId: $("#cbbStatusPopup").val(),
					note: $.trim($("#txtNotePopup").val()),
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
								// display all product in block
								drawProductTable();
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
							} else if (returnedJsonData == INSERT_RESULT_FAILED_EXCEPTION) {
								// failed
								// display message
								jWarning(INSERT_RESULT_FAILED_MESSAGE.replace('$1', INSERT_RESULT_FAILED_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_FAILED_CULTIVATION) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_FAILED_PRODUCT) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_FAILED_PRODUCT_UPDATE_DATE) {
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

	// Draw product data in selected block
	function drawProductTable() {
		// make Ajax call to server to get data
		$.ajax({
			url: "getProductData",
			data: { "farmId": farmIdGlobal, "areaId": areaIdGlobal, "blockId": blockIdGlobal },
			type: "POST",
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				if (returnedJsonData != "") {
					// get Line per Block and Column per Block
					var linePerBlock = parseInt(lineCount);
					var colPerBlock = parseInt(columnCount);
					// calculate total product in Block
					var totalProductInBlock = linePerBlock * colPerBlock;

					/* ------------------------------------------
					 * ------------------HEADER------------------*/
					// clear table
					$("#tblProductHeader").empty();
					// create table starts
					var tableStringArray = [];

					// calculate colgroup width
					var colWidthThreshold = 90; // 95% of table width
					var firstColThreshold = 10; // 5% of table width
					var oneColWidth = colWidthThreshold / (3 * linePerBlock); // one Line header "colspan" number is 3
					// calculate image width
					var parentWidth = $(".div-farmwork").width();
					var imageWidth = parentWidth * (oneColWidth * 1.5) / 100;

					// create colgroup
					// add open tag
					tableStringArray.push("<colgroup>");
					tableStringArray.push("<col width='" + firstColThreshold + "%'>");
					tableStringArray.push("<col width='" + oneColWidth + "%'>");
					for (var i = 0; i < linePerBlock; i++) {
						tableStringArray.push("<col width='" + oneColWidth + "%'>");
						if (i != linePerBlock - 1) {
							tableStringArray.push("<col width='" + oneColWidth + "%'>");
							tableStringArray.push("<col width='" + oneColWidth + "%'>");
						} else {
							tableStringArray.push("<col width='" + oneColWidth + "%'>");
						}
					}
					// add close tag
					tableStringArray.push("</colgroup>");

					// add open tag
					tableStringArray.push("<tbody>");
					tableStringArray.push("<tr>");
					tableStringArray.push("<th class='border-top-none border-bottom-none border-left-none'></th>");
					for (var i = 0; i < linePerBlock; i++) {
						tableStringArray.push("<th colspan='3' class='line_column_color' style='font-size: "
								+ (Math.floor(imageWidth) <= MAX_FONT_SIZE ? Math.floor(imageWidth)
								: MAX_FONT_SIZE) + "px;'>L" + (i + 1) + "</th>");
					}
					// add close tag
					tableStringArray.push("</tr>");

					// add open tag
					tableStringArray.push("<tr>");
					tableStringArray.push("<td class='border-top-none border-bottom-none border-left-none' colspan='2'></td>");
					for (var i = 0; i < linePerBlock; i++) {
						tableStringArray.push("<td class='border-top-none border-bottom-none'></td>");
						if (i != linePerBlock - 1) {
							tableStringArray.push("<td class='border-top-none border-bottom-none' colspan='2'></td>");
						} else {
							tableStringArray.push("<td class='border-top-none border-bottom-none border-right-none'></td>");
						}
					}
					// add close tag
					tableStringArray.push("</tr>");

					// add close tag
					tableStringArray.push("</tbody>");
					// append all created string to table
					$("#tblProductHeader").append(tableStringArray.join(''));

					/* ----------------------------------------
					 * ------------------BODY------------------*/
					// clear table
					$("#tblProductBody").empty();
					// create table starts
					tableStringArray = [];

					// create colgroup
					// add open tag
					tableStringArray.push("<colgroup>");
					tableStringArray.push("<col width='" + firstColThreshold + "%'>");
					tableStringArray.push("<col width='" + oneColWidth + "%'>");
					for (var i = 0; i < linePerBlock; i++) {
						tableStringArray.push("<col width='" + oneColWidth + "%'>");
						if (i != linePerBlock - 1) {
							tableStringArray.push("<col width='" + oneColWidth + "%'>");
							tableStringArray.push("<col width='" + oneColWidth + "%'>");
						} else {
							tableStringArray.push("<col width='" + oneColWidth + "%'>");
						}
					}
					// add close tag
					tableStringArray.push("</colgroup>");

					var indexInArray = 0;
					var loopCount = 1;
					// add open tag
					tableStringArray.push("<tbody>");
					while (loopCount <= colPerBlock) {
						// add open tag
						tableStringArray.push("<tr>");
						tableStringArray.push("<td class='line_column_color' style='font-size: "
								+ (Math.floor(imageWidth) <= MAX_FONT_SIZE ? Math.floor(imageWidth)
								: MAX_FONT_SIZE) + "px;'>C" + loopCount + "</td>");
						for (var i = 0; i < linePerBlock; i++) {
							// get current product
							var currentProduct = returnedJsonData[indexInArray];
							// check if current product is physically removed from DB or not
							var currentLine = i + 1;
							var currentColumn = loopCount;
							// compare
							if (currentProduct == null || (parseInt(currentProduct.lineId.replace(/[^\d.]/g, '')) != currentLine
									|| parseInt(currentProduct.columnId.replace(/[^\d.]/g, '')) != currentColumn)) {
								// product is removed from DB
								tableStringArray.push("<td colspan='3'><img style='width: "
										+ imageWidth + "px; max-width: 32px;' class='productImg productRemoved' src='"
										+ rootPath + "/resources/img/removed.png?date=" + removedDate + "'></td>");
							} else {
								// product is still in DB
								// check if current product is deleted or not
								if (currentProduct.deleteFlag == DELETE_FLAG_ON) {
									tableStringArray.push("<td colspan='3'><img style='width: "
											+ imageWidth + "px; max-width: 32px;' class='productImg' name='"
											+ (currentProduct.lineId + ":" + currentProduct.columnId)
											+ "' src='" + rootPath + "/resources/img/disable.png?date=" + disableDate + "'></td>");
									indexInArray++;
								} else {
									var tableString = "";
									
									// first case: cultivation start date is default date
									if (currentProduct.cultivationStartDateString == DEFAULT_CULTIVATION_START_DATE) {
										tableString = "<td colspan='3'><img style='width: "
												+ imageWidth + "px; max-width: 32px;' class='productImg' name='"
												+ (currentProduct.lineId + ":" + currentProduct.columnId)
												+ "' lastUpdateDate='" + currentProduct.lastUpdateDate + "' src='" + rootPath + "/resources/img/hole.png?date=" + holeDate + "'></td>";
									}
									// second case: planting date is null
									if (currentProduct.cultivationStartDateString != DEFAULT_CULTIVATION_START_DATE && currentProduct.plantingDateString == null) {
										tableString = "<td colspan='3'><img style='width: "
												+ imageWidth + "px; max-width: 32px;' class='productImg' name='"
												+ (currentProduct.lineId + ":" + currentProduct.columnId)
												+ "' lastUpdateDate='" + currentProduct.lastUpdateDate + "' src='" + rootPath + "/resources/img/planted.png?date=" + plantedDate + "'></td>";
									}
									// third case: only planting date
									if (currentProduct.plantingDateString != null) {
										tableString = "<td colspan='3'><img style='width: "
												+ imageWidth + "px; max-width: 32px;' class='productImg' name='"
												+ (currentProduct.lineId + ":" + currentProduct.columnId)
												+ "' lastUpdateDate='" + currentProduct.lastUpdateDate + "' src='" + rootPath + "/resources/img/fostering.png?date=" + fosteringDate + "'></td>";
									}
									// fourth case: flowering date
									if (currentProduct.floweringDateString != null) {
										tableString = "<td colspan='3'><img style='width: "
												+ imageWidth + "px; max-width: 32px;' class='productImg' name='"
												+ (currentProduct.lineId + ":" + currentProduct.columnId)
												+ "' lastUpdateDate='" + currentProduct.lastUpdateDate + "' src='" + rootPath + "/resources/img/flower.png?date=" + flowerDate + "'></td>";
									}
									// fifth case: bag closing date
									if (currentProduct.bagClosingDateString != null) {
										tableString = "<td colspan='3'><img style='width: "
												+ imageWidth + "px; max-width: 32px;' class='productImg' name='"
												+ (currentProduct.lineId + ":" + currentProduct.columnId)
												+ "' lastUpdateDate='" + currentProduct.lastUpdateDate + "' src='" + rootPath + "/resources/img/bagged.png?date=" + baggedDate + "'></td>";
									}
									// sixth case: harvest date
									if (currentProduct.harvestDateString != null) {
										tableString = "<td colspan='3'><img style='width: "
												+ imageWidth + "px; max-width: 32px;' class='productImg' name='"
												+ (currentProduct.lineId + ":" + currentProduct.columnId)
												+ "' lastUpdateDate='" + currentProduct.lastUpdateDate + "' src='" + rootPath + "/resources/img/cultivated.png?date=" + cultivatedDate + "'></td>";
									}									
									tableStringArray.push(tableString);									
									indexInArray++;
								}
							}
						}
						// add close tag
						tableStringArray.push("</tr>");

						// last row check
						if (loopCount != colPerBlock) {
							// add open tag
							tableStringArray.push("<tr>");
							tableStringArray.push("<td class='border-left-none' colspan='2'></td>");
							for (var i = 0; i < linePerBlock; i++) {
								tableStringArray.push("<td></td>");
								if (i != linePerBlock - 1) {
									tableStringArray.push("<td colspan='2'></td>");
								} else {
									tableStringArray.push("<td class='border-top-none border-right-none'></td>");
								}
							}
							// add close tag
							tableStringArray.push("</tr>");
						}

						// increase loop count
						loopCount++;
					}
					tableStringArray.push("<tr>");
					tableStringArray.push("<td></td>");
					tableStringArray.push("</tr>");
					// add close tag
					tableStringArray.push("</tbody>");
					// append all created string to table
					$("#tblProductBody").append(tableStringArray.join(''));

					// fix header size
					$("#divHeadProduct").width($("#divBodyProduct").width() - 17);

					// fix height table left
					$(".div-farmwork").height(calculateTableContentHeight());
					$("#divBodyProduct").height(calculateTableContentHeight() - 98);
					$("#tblProductBody").height(calculateTableContentHeight() - 99);

					// scroll to top of table
					$("#divBodyProduct").scrollTop(0).scrollLeft(0);
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
			data: { "farmId": farmIdGlobal, "areaId": areaIdGlobal,
				"blockId": blockIdGlobal, "kindId": kindIdGlobal },
			type: "POST",
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				// store data in global variable
				cultivationResultData = returnedJsonData;
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
			// get product id array
			var productIdArray = currentProductIdString.split(",").filter(function(el) {return el.length != 0});
			for (var k = 0; k < cultivationResultData.length; k++) {
				// get current data
				var currentItem = cultivationResultData[k];
				// loop through all selected product id
				for (var l = 0; l < productIdArray.length; l++) {
					var currentId = productIdArray[l];
					// compare id
					if (currentItem.lineId === currentId.split(":")[0]
						&& currentItem.columnId === currentId.split(":")[1]) {
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
			// line
			var lineId = currentItem.lineId.substring(1);
			lineId = parseInt(lineId);
			tableStringArray.push("<td class='overflow-ellipsis'>L" + lineId + "</td>");
			// column
			var columnId = currentItem.columnId.substring(1);
			columnId = parseInt(columnId);
			tableStringArray.push("<td class='overflow-ellipsis'>C" + columnId + "</td>");
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
		tableStringArray.push("<td colspan='11'></td>");
		tableStringArray.push("</tr>");
		// add close tag
		tableStringArray.push("</tbody>");

		// append all created string to table
		$("#tblBody").append(tableStringArray.join(''));

		// fix height table right
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
		$("#divBodyProduct").height(calculateTableContentHeight() - 98);
		$("#tblProductBody").height(calculateTableContentHeight() - 99);

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
		$("#txtAreaNamePopup").val(convertHtmlToText(areaNameGlobal));
		// Block name
		$("#txtBlockNamePopup").val(convertHtmlToText(blockNameGlobal));
		// Kind name
		$("#txtKindNamePopup").val(convertHtmlToText(kindNameGlobal));
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
		// set values based on mode
		if (CURRENT_MODE == MODE_NEW) {
			// current date
			$("#txtWorkingDatePopup").prop("disabled", false);
			$("#txtWorkingDatePopup").datepicker("enable");
			// planting date
			$("#txtPlantDatePopup").val("");
			// flowering date
			$("#txtFlowerDatePopup").val("");
			// bag closing date
			$("#txtBagDatePopup").val("");
			// harvest date
			$("#txtHarvestDatePopup").val("");
			// shipping date
			$("#txtShippingDatePopup").val("");
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
			// working date
			$("#txtWorkingDatePopup").prop("disabled", true);
			$("#txtWorkingDatePopup").datepicker("disable");
			$("#txtWorkingDatePopup").val(currentItem.workingDateString);
			// planting date
			$("#txtPlantDatePopup").val(currentItem.plantingDate);
			// flowering date
			$("#txtFlowerDatePopup").val(currentItem.floweringDate);
			// bag closing date
			$("#txtBagDatePopup").val(currentItem.bagClosingDate);
			// harvest date
			$("#txtHarvestDatePopup").val(currentItem.harvestDate);
			// shipping date
			$("#txtShippingDatePopup").val(currentItem.shippingDate);
			// process
			$("#cbbProcessPopup").prop("disabled", true);
			$("#cbbProcessPopup").val(currentItem.processId);
			$("#cbbProcessPopup").trigger("change");
			// task
			$("#cbbTaskPopup").prop("disabled", true);
			$("#cbbTaskPopup").val(currentItem.taskId);
			// work content
			$("#txtWorkContentPopup").val(convertHtmlToText(currentItem.workingContent));
			// precaution
			$("#txtPrecautionPopup").val(convertHtmlToText(currentItem.precaution));
			// status
			$("#cbbStatusPopup").prop("disabled", true);
			$("#cbbStatusPopup").val(currentItem.statusId);
			// note
			$("#txtNotePopup").prop("disabled", true);
			$("#txtNotePopup").val(convertHtmlToText(currentItem.note));
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
			data: { "farmId": farmIdGlobal, "kindId": kindIdGlobal, "processId": selectedProcessId },
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
		$(".productImg").each(function() {
			// check for clickable images
			if (!$(this).hasClass("productRemoved")) {
				// generate id string
				var idString = $(this).attr("name") + ",";
				// select all state
				if (currentProductIdString.indexOf(idString) >= 0) {
					// change image type to "check"
					if ($(this).attr("src").indexOf("disable") >= 0) {
						$(this).attr("src", rootPath + "/resources/img/disable_check.png?date=" + disable_checkDate);
					} else if ($(this).attr("src").indexOf("hole") >= 0) {
						$(this).attr("src", rootPath + "/resources/img/hole_check.png?date=" + hole_checkDate);
					} else if ($(this).attr("src").indexOf("planted") >= 0) {
						$(this).attr("src", rootPath + "/resources/img/planted_check.png?date=" + planted_checkDate);
					} else if ($(this).attr("src").indexOf("fostering") >= 0) {
						$(this).attr("src", rootPath + "/resources/img/fostering_check.png?date=" + fostering_checkDate);
					} else if ($(this).attr("src").indexOf("flower") >= 0) {
						$(this).attr("src", rootPath + "/resources/img/flower_check.png?date=" + flower_checkDate);
					} else if ($(this).attr("src").indexOf("bagged") >= 0) {
						$(this).attr("src", rootPath + "/resources/img/bagged_check.png?date=" + bagged_checkDate);
					} else if ($(this).attr("src").indexOf("cultivated") >= 0) {
						$(this).attr("src", rootPath + "/resources/img/cultivated_check.png?date=" + cultivated_checkDate);
					}
				}
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