/**
 * @author Khoa Le
 * Search block, create new block and edit block's info
 */

$(document).ready(function() {
	//--------------- Constants definition ---------------
	// number of items in one page in table
	var ITEM_IN_ONE_PAGE = ITEM_IN_ONE_PAGE_0009;
	// max font size allowed in Header
	var MAX_FONT_SIZE = 15;
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
	// variable to store number of lines and columns count
	var lineCount = "";
	var columnCount = "";
	// variable to store selected farm id, area id, block id, previous round to show in popup
	var farmIdPopup = "";
	var areaIdPopup = "";
	var blockIdPopup = "";
	var previousRound = "";
	var rowName = "";
	// variable to store current selected mode
	var currentMode = "";
	// numberical_order
	var numberical_order = 0;
	// variable to store selected product id
	var currentProductIdString = "";
	var countChangeCbb = 0;
	//dirty flag
	var dirty = 0;
	// search data
	var searchData = null;
	// variable to store last updated date time when user opens edit popup
	var currentLastUpdatedTimeString = "";
	$("#btnDisableProductPopup").prop("disabled", true);
	$("#btnDisableProductPopup").addClass("btn-disable");
	// display current date
	$("#txtCurrentDate").text(getCurrentDate() + " (" + DAY_OF_WEEK + ") ");

	// initialize combobox data
	initFarmNameComboboxData(false);

	// Farm name combobox change event process
	$("#cbbFarmName").bind("change", function() {
		// load Area name combobox
		countChangeCbb ++;
		loadAreaNameCombobox(false);
	});

	// Area name combobox change event process
	$("#cbbAreaName").bind("change", function() {
		// load Block id combobox
		loadBlockIdCombobox();
	});
	
	// Search button event process
	$("#btnSearch").bind("click", function() {
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

	// Register block click event process  with previous round = 0
	$("#btnRegister").bind("click", function() {
		$("#txtFarmIdPopup").val(searchData.farmId);
		$("#txtFarmNamePopup").val($("#farmNamelbl").text());
		$("#txtAreaIdPopup").val(searchData.areaId);
		$("#txtAreaNamePopup").val($("#areaNamelbl").text());
		// clear all current text of popup controls
		clearPopupControl(); 
		// change state of controls in popup based on mode
		setPopupControlState(MODE_NEW);
		
		// display block info popup
		showPopup($("#popupWrapper"));
	});

	// Product adjustment click event process
	$(document).on("click", ".adjust", function() {
		// get row index
		selectedRowIndex = $(this).attr("name");
		// get ids of selected block
		farmIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).attr("name");
		areaIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).attr("id");
		blockIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).attr("block_id");
		//blockIdPopup = blockIdPopup.substring(0,4);
		//reset dirty flag
		dirty = 0;
		$("#btnDisableProductPopup").prop("disabled", true);
		$("#btnDisableProductPopup").addClass("btn-disable");
		previousRound = FLAG_ON;
		// get number of lines and columns of farm first
		// make Ajax call to server to get data
		$.ajax({
			url: "getFarmLinesAndColumnsCount",
			data: { "farmId": farmIdPopup },
			type: "POST",
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				if (returnedJsonData != "") {
					var tempArr = returnedJsonData.split(":");
					// set lines and columns count
					lineCount = tempArr[0];
					columnCount = tempArr[1];
					// get product data based on ids
					drawProductTable();
					// Show/Hide button Select All
					$("#unSelectAll").addClass("display-none");
					$("#selectAll").removeClass("display-none");
				}
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	});
	
	// Product adjustment click event process with previous round = 0
	$(document).on("click", ".blockName", function() {
		// get row index
		selectedRowIndex = $(this).attr("name");
		// get ids of selected block
		farmIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).attr("name");
		areaIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).attr("id");
		blockIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).attr("block_id");
		//blockIdPopup = blockIdPopup.substring(0,4);
		//reset dirty flag
		dirty = 0;
		$("#btnDisableProductPopup").prop("disabled", true);
		$("#btnDisableProductPopup").addClass("btn-disable");
		previousRound = FLAG_OFF;
		// get number of lines and columns of farm first
		// make Ajax call to server to get data
		$.ajax({
			url: "getFarmLinesAndColumnsCount",
			data: { "farmId": farmIdPopup },
			type: "POST",
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				if (returnedJsonData != "") {
					var tempArr = returnedJsonData.split(":");
					// set lines and columns count
					lineCount = tempArr[0];
					columnCount = tempArr[1];
					// get product data based on ids
					drawProductTable();
					$("#unSelectAll").addClass("display-none");
					$("#selectAll").removeClass("display-none");
				}
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
		return false;
	});

	function getBody(element) {
	    var divider = 2;
	    var originalTable = element.clone();
	    var tds = $(originalTable).children('td').length;
	    return tds;
	}

	// select All when click #selectAll in popup
	$(document).on("click", "#selectAll", function(){
		$(".rowImgProduct").each(function(){
			var countTdInTr = getBody($(this));
			for (i = 0; i < countTdInTr; i++) {
				var idString = "";
				if ($($(this).find("td").eq(i).find("img")).hasClass("productFostering")) {
					$($(this).find("td").eq(i).find("img")).attr("src", rootPath + "/resources/img/fostering_check.png?date=" + fostering_checkDate);
					// store to variable
					idString = farmIdPopup + ":" + areaIdPopup + ":" + blockIdPopup + ":" + $($(this).find("td").eq(i).find("img")).attr("name") + ":"+ FLAG_ON +",";
					currentProductIdString += idString;
				} else if ($($(this).find("td").eq(i).find("img")).hasClass("productDisable")) {
					$($(this).find("td").eq(i).find("img")).attr("src", rootPath + "/resources/img/disable_check.png?date=" + disable_checkDate);
					// store to variable
					idString = farmIdPopup + ":" + areaIdPopup + ":" + blockIdPopup + ":" + $($(this).find("td").eq(i).find("img")).attr("name") + ":"+ FLAG_OFF +",";
					currentProductIdString += idString;
				}
			}
		});
		//set dirty
		dirty = 1;
		$("#btnDisableProductPopup").prop("disabled", false);
		$("#btnDisableProductPopup").removeClass("btn-disable");
		$("#selectAll").addClass("display-none");
		$("#unSelectAll").removeClass("display-none");
	})

	// Back button click event process
	$("#btnBack").bind("click", function() {
		goBackToPreviousPage();
	});

	// Un select All 
	$(document).on("click", "#unSelectAll", function(){
		$(".rowImgProduct").each(function(){
			var countTdInTr = getBody($(this));
			for (i = 0; i < countTdInTr; i++) {
				if ($($(this).find("td").eq(i).find("img")).hasClass("productFostering")) {
					$($(this).find("td").eq(i).find("img")).attr("src", rootPath + "/resources/img/fostering.png?date=" + fosteringDate);
				} else if ($($(this).find("td").eq(i).find("img")).hasClass("productDisable")) {
					$($(this).find("td").eq(i).find("img")).attr("src", rootPath + "/resources/img/disable.png?date=" + disableDate);
				}
			}
		});
		//reset id list
		currentProductIdString = "";
		//set dirty
		dirty = 0;
		$("#btnDisableProductPopup").prop("disabled", true);
		$("#btnDisableProductPopup").addClass("btn-disable");
		$("#selectAll").removeClass("display-none");
		$("#unSelectAll").addClass("display-none");
	})

	// Edit block info click event process
	$(document).on("click", ".edit", function() {
		// get row index
		selectedRowIndex = $(this).attr("name");
		// save Name Of row
		rowName = selectedRowIndex;
		// get ids of selected block
		farmIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).attr("name");
		areaIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).attr("id");
		blockIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).attr("block_id");
		// create data object
		var dataObject = {
			farmId: farmIdPopup,
			areaId: areaIdPopup,
			blockId: blockIdPopup,
			blockName: "",
			fromRow: 0,
			itemCount: ITEM_IN_ONE_PAGE
		};
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "getSingleData",
			data: { "farmId": farmIdPopup, "areaId": areaIdPopup, "blockId": blockIdPopup },
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				if (returnedJsonData != "") {
					// clear all current text of popup controls
					clearPopupControl();
					$("#txtFarmIdPopup").val(returnedJsonData.farmId);
					$("#txtFarmNamePopup").val($("#cbbFarmName option:selected").text());
					$("#txtAreaIdPopup").val(returnedJsonData.areaId);
					$("#txtAreaNamePopup").val($("#areaNamelbl").text());
					// block id
					$("#txtBlockIdPopup").val(returnedJsonData.blockId);
					// block name
					$("#txtBlockNamePopup").val(returnedJsonData.blockName);
					// note
					$("#txtNotePopup").val(returnedJsonData.note);
					// last updated date time
					currentLastUpdatedTimeString = returnedJsonData.lastUpdateDate;
					// change state of controls in popup based on mode
					setPopupControlState(MODE_EDIT);
					// display user info popup
					showPopup($("#popupWrapper"));
				}
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	});

	// View block info click event process
	$(document).on("click", ".view", function() {
		// get row index
		selectedRowIndex = $(this).attr("name");
		// get ids of selected block
		farmIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).attr("name");
		areaIdPopup = $("#row" + selectedRowIndex).find("td").eq(2).attr("name");
		blockIdPopup = $("#row" + selectedRowIndex).find("td").eq(3).text();
		// create data object
		var dataObject = {
			farmId: farmIdPopup,
			areaId: areaIdPopup,
			blockId: blockIdPopup,
			blockName: "",
			fromRow: 0,
			itemCount: ITEM_IN_ONE_PAGE
		};
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "searchData",
			contentType: "application/json",
			data: JSON.stringify(dataObject),
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				if (returnedJsonData != "") {
					// clear all current text of popup controls
					clearPopupControl();

					// farm name
					var optionStr = "<option value='" + returnedJsonData[0].farmId + "'>" + returnedJsonData[0].farmName + "</option>";
					$("#cbbFarmNamePopup").empty().append(optionStr);
					// area name
					optionStr = "<option value='" + returnedJsonData[0].areaId + "'>" + returnedJsonData[0].areaName + "</option>";
					$("#cbbAreaNamePopup").empty().append(optionStr);
					// block id
					$("#txtBlockIdPopup").val(returnedJsonData[0].blockId);
					// block name
					$("#txtBlockNamePopup").val(returnedJsonData[0].blockName);
					// status
					if (returnedJsonData[0].deleteFlag == DELETE_FLAG_OFF) {
						$("#chbDeletePopup").prop("checked", false);
					} else if (returnedJsonData[0].deleteFlag == DELETE_FLAG_ON) {
						$("#chbDeletePopup").prop("checked", true);
					}
					// note
					$("#txtNotePopup").val(returnedJsonData[0].note);

					// last updated date time
					currentLastUpdatedTimeString = returnedJsonData.lastUpdateDate;
					// change state of controls in popup based on mode
					setPopupControlState(MODE_VIEW);
				}
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			},
			complete: function(jqXHR, textStatus) {
				// display user info popup
				showPopup($("#popupWrapper"));
			}
		});
	});

	// Delete user click event process
	$(document).on("click", ".delete", function() {
		selectedRowIndex = $(this).attr("name");
		var lastUpdateDate = $(this).attr("lastUpdateDate");
		// display confirmation message
		jQuestion_warning(DELETE_CONFIRM_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
			if (val) {
				// get row index
				
				// get ids of selected block
				farmIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).attr("name");
				areaIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).attr("id");
				blockIdPopup = $("#row" + selectedRowIndex).find("td").eq(1).attr("block_id");
				blockIdPopup = blockIdPopup.substring(0,4);
				// make Ajax call to server to delete data
				$.ajax({
					url: "deleteData",
					data: { "farmId": farmIdPopup, "areaId": areaIdPopup, "blockId": blockIdPopup, "lastUpdateDate" : lastUpdateDate },
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
								// reset search conditions
								//resetSearchConditions();
								// draw table
								numberical_order = 0;
								searchData.blockId = STATUS_NO_SELECT;
								loadBlockIdCombobox();
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
							} else if (returnedJsonData == DELETE_RESULT_FAILED_CULTIVATION_BLOCK) {
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

	// Product adjustment image click event process
	$(document).on("click", ".productImg", function() {
		//set dirty
		dirty = 1;
		$("#btnDisableProductPopup").prop("disabled", false);
		$("#btnDisableProductPopup").removeClass("btn-disable");
		// check for clickable images
		if (!$(this).hasClass("productRemoved")) {
			// generate id string
			var idString = "";
			// check status of product
			if ($(this).attr("src").indexOf("_check") == -1) {
				// change image type to "check"
				$(this).attr("src", $(this).attr("src").replace("disable", "disable_check")
						.replace("fostering", "fostering_check"));
				if ($(this).attr("src").indexOf("fostering_check") >= 0) {
					idString = farmIdPopup + ":" + areaIdPopup + ":" + blockIdPopup + ":" + $(this).attr("name") + ":" + FLAG_ON + ",";
				} else if ($(this).attr("src").indexOf("disable_check") >= 0) {
					idString = farmIdPopup + ":" + areaIdPopup + ":" + blockIdPopup + ":" + $(this).attr("name") + ":" + FLAG_OFF + ",";
				}
				// store to variable
				currentProductIdString += idString;
			} else {
				//id for removing from current selected id list
				var idStringRemove = "";
				// change image type to "uncheck"
				$(this).attr("src", $(this).attr("src").replace("disable_check", "disable")
						.replace("fostering_check", "fostering"));
				if ($(this).attr("src").indexOf("fostering") >= 0) {
					idString = farmIdPopup + ":" + areaIdPopup + ":" + blockIdPopup + ":" + $(this).attr("name") + ":" + FLAG_ON + ",";
				} else if ($(this).attr("src").indexOf("disable") >= 0) {
					idString = farmIdPopup + ":" + areaIdPopup + ":" + blockIdPopup + ":" + $(this).attr("name") + ":" + FLAG_OFF + ",";
				}
				// remove from variable
				currentProductIdString = currentProductIdString.replace(idString, "");
			}
		}
		if (currentProductIdString == "") {
			// Disable btn Comfirm
			$("#btnDisableProductPopup").addClass("btn-disable").prop("disabled", true);
			dirty = 0;
		} else {
			// Enable btn Comfirm
			$("#btnDisableProductPopup").removeClass("btn-disable").prop("disabled", false);
		}
	});

	// Register button in popup click event process
	$("#btnRegisterPopup").bind("click", function() {
		var dataObject = null;
		// check current mode
		if (currentMode == MODE_NEW) {
			// check for user input
			if (checkInputBlankFields() != "") {
				// blank field(s)
				// display message
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// insert new block to DB
				// get block input data
				dataObject = createBlockDataObject();
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
							if (returnedJsonData == VALIDATE_BLANK_FIELDS) {
								// blank field(s)
								// display message
								jWarning(VALIDATE_BLANK_FIELDS_MESSAGE_SERVER, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_SUCCESSFUL) {
								// search data again
								// reset variables
								resetVariables();
								// reset search conditions
								//resetSearchConditions();
								// draw table
								numberical_order = 0;
								searchData.blockId = STATUS_NO_SELECT;
								loadBlockIdCombobox();
								drawResult = drawTable();
								if (drawResult != "") {
									// update total data count UI
									setDataCounts();
								}
								// hide popup
								hidePopup($("#popupWrapper"));
								// display message
								jInfo(INSERT_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_DUPLICATED_BLOCK) {
								// duplicated user id
								// display message
								jWarning(INSERT_RESULT_DUPLICATED_BLOCK_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_CONFIG_NOT_CORRECT) {
								// config file contains wrong data
								// display message
								jWarning(INSERT_RESULT_CONFIG_NOT_CORRECT_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == INSERT_RESULT_FAILED_BLOCK) {
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
			if (checkInputBlankFields() != "") {
				// blank field(s)
				// display message
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// update existing block in DB
				// get block input data
				dataObject = createBlockDataObject();
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
							if (returnedJsonData == VALIDATE_BLANK_FIELDS) {
								// blank field(s)
								// display message
								jWarning(VALIDATE_BLANK_FIELDS_MESSAGE_SERVER, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == UPDATE_RESULT_SUCCESSFUL) {
								// update the corresponding block data in search table
								// make Ajax call to server to get data
								$.ajax({
									url: "getSingleData",
									data: { "farmId": farmIdPopup, "areaId": areaIdPopup, "blockId": blockIdPopup },
									type: "POST",
									async: false,
									success: function(returnedJsonData) {
										if (checkSessionTimeout(returnedJsonData) == 1) return;
										if (returnedJsonData != "") {
											// search data again
											// reset variables
											resetVariables();
											// reset search conditions
											//resetSearchConditions();
											// draw table
											numberical_order = 0;
											searchData.blockId = STATUS_NO_SELECT;
											loadBlockIdCombobox();
											drawResult = drawTable();
											if (drawResult != "") {
												// update total data count UI
												setDataCounts();
											}
											// last updated date time
											currentLastUpdatedTimeString = returnedJsonData.lastUpdateDate;
										} 
									},
									complete: function(jqXHR, textStatus) {
										// hide popup
										hidePopup($("#popupWrapper"));
										// display message
										jInfo(UPDATE_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
									}
								});
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_UPDATE_DATE) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_DUPLLICATE) {
								// failed
								// display message
								jWarning(UPDATE_BLOCK_DUPLICATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_CULTIVATION_BLOCK) {
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

	$("#btnDisableProductPopup").bind("click", function() {
		// check if user selected a product or not
		if (currentProductIdString == "") {
			// blank field(s)
			// display message
			jWarning(VALIDATE_BLANK_FIELDS_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// update selected product process
			updateProductData(previousRound);
			$("#selectAll").removeClass("display-none");
			$("#unSelectAll").addClass("display-none");
			hidePopup($("#popupProductWrapper"));
		}
	});

	// Cancel button in popup click event process
	$("#btnCancelPopup").bind("click", function() {
		// hide block info popup
		hidePopup($("#popupWrapper"));
	});

	$("#btnCancelProductPopup").bind("click", function() {
		if (dirty == 1) {
			jQuestion_warning(PRODUCT_CHANGE_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
				if (val) {
					// hide product adjust popup
					hidePopup($("#popupProductWrapper"));
					currentProductIdString = "";
					dirty = 0;
				} else {
					//do nothing
				}
			});
		} else {
			// hide product adjust popup
			hidePopup($("#popupProductWrapper"));
		}
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

	// Get data for combobox process
	function initFarmNameComboboxData(isPopupMode) {
		// farm
		numberical_order = 0;
		var farmIdClient = $("#farmIdClient").val();
		var farmDefault = "";
		if (farmData != "") {
			var optionStr = "";
			for (var i = 0; i < farmData.length; i++) {
				farmDefault = farmData[0].farmId;
				// if farmIdClient is empty 
				if (farmIdClient == "") {
					optionStr += "<option value='" + farmData[i].farmId + "'>" + farmData[i].farmName + "</option>";
				} else {
					// if farmIdClient = farmId of FarmData then selected at farmIDClient
					if (farmData[i].farmId == farmIdClient) {
						optionStr += "<option value='" + farmData[i].farmId + "' selected = 'selected'>" + farmData[i].farmName + "</option>";
					}  else {
						optionStr += "<option value='" + farmData[i].farmId + "'>" + farmData[i].farmName + "</option>";
					}
				}
			} 
			// check whether to fill data in main screen or popup screen
			if (!isPopupMode) {
				$("#cbbFarmName").append(optionStr);
				loadAreaNameCombobox(false);
			} else {
				$("#cbbFarmNamePopup").append(optionStr);
				loadAreaNameCombobox(true);
			}
			$("#btnRegister").prop("disabled", false).removeClass("btn-disable");
		} else {
			var optionStr = "<option value='-2'></option>";
			$("#cbbFarmName").append(optionStr);
			$("#cbbAreaName").prop("disabled", true);
			$("#btnRegister").prop("disabled", true).addClass("btn-disable");
		}
	}

	// Load Area name combobox data
	function loadAreaNameCombobox(isPopupMode) {
		// get farm id selected by user
		numberical_order = 0;
		var areaIdClient = $("#areaIdClient").val();
		var farmIdClient = $("#farmIdClient").val();
		var selectedFarmId = "";
		if (!isPopupMode) {
			// if farmIdClient != "" then set selectedFarmId = farmIdClient
			if (farmIdClient != "") {
				selectedFarmId = farmIdClient;
			} else {
				selectedFarmId = $("#cbbFarmName").find("option:selected").val(); 
			}
		} else {
			selectedFarmId = $("#cbbFarmNamePopup").find("option:selected").val();
		}
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
					var optionStr = "";
					if (returnedJsonData != "") {
						for (var i = 0; i < returnedJsonData.length; i++) {
							if (areaIdClient == "") {
								optionStr += "<option value='" + returnedJsonData[i].areaId + "'>" + returnedJsonData[i].areaName + "</option>";
							} else {
								if (returnedJsonData[i].areaId == areaIdClient) {
									optionStr += "<option value='" + returnedJsonData[i].areaId + "' selected = 'selected'>" + returnedJsonData[i].areaName + "</option>";
								} else {
									optionStr += "<option value='" + returnedJsonData[i].areaId + "'>" + returnedJsonData[i].areaName + "</option>";
								}
							}
						}
						$("#btnRegister").prop("disabled", false).removeClass("btn-disable");
					} else {
						$("#btnRegister").prop("disabled", true).addClass("btn-disable");
					}
					if (!isPopupMode) {
						// set options to combobox
						$("#cbbAreaName").empty().append(optionStr);
						// enable combobox
						 
						if (returnedJsonData != "") {
							loadBlockIdCombobox();
							$("#cbbAreaName").prop("disabled", false);
							$("#cbbBlockId").prop("disabled", false);
						} else {
							$("#cbbAreaName").prop("disabled", true);
							$("#cbbBlockId").empty().prop("disabled", true);
						}
					} else {
						// set options to combobox
						$("#cbbAreaNamePopup").empty().append(optionStr);
						// enable combobox
						if (returnedJsonData != "") {
							$("#cbbAreaNamePopup").prop("disabled", false);
						} else {
							$("#cbbAreaNamePopup").prop("disabled", true);
						}
					}
					// if have event change cbbFarmName then drawTable.
					if (countChangeCbb == 0) {
						$("#farmIdClient").val("");
						$("#areaIdClient").val("");
					}
				},
				error: function(e) {
					// display error message
					jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
				}
			});
		} else {
			if (!isPopupMode) {
				// clear combobox
				$("#cbbAreaName").empty();
				$("#cbbBlockId").empty();
				// disable combobox
				$("#cbbAreaName").prop("disabled", true);
				$("#cbbBlockId").prop("disabled", true);
			} else {
				// clear combobox
				$("#cbbAreaNamePopup").empty();
				// disable combobox
				$("#cbbAreaNamePopup").prop("disabled", true);
				$("#cbbBlockId").prop("disabled", true);
			}
		}
	}

	// Load Block id combobox data
	function loadBlockIdCombobox() {
		// get area id selected by user
		var selectedAreaId = $("#cbbAreaName").find("option:selected").val();
		selectedAreaId = selectedAreaId == undefined ? '-2' : selectedAreaId;
		// get farm id selected by user
		var selectedFarmId = $("#cbbFarmName").find("option:selected").val(); 
		if (selectedAreaId != STATUS_NO_SELECT) {
			// make Ajax call to server to get data
			$.ajax({
				type: "POST",
				url: "getBlockDataByAreaId",
				data: { "areaId": selectedAreaId,
						"farmId": selectedFarmId
					  },
				async: false,
				success: function(returnedJsonData) {
					if (checkSessionTimeout(returnedJsonData) == 1) return;
					// create option string
					var optionStr = "<option id ='" + STATUS_NO_SELECT + "' value='" + STATUS_NO_SELECT + "'></option>";
					if (returnedJsonData != "") {
						for (var i = 0; i < returnedJsonData.length; i++) {
							optionStr += "<option id = '"+ returnedJsonData[i].blockName +"' value='" + returnedJsonData[i].blockId + "'>" + returnedJsonData[i].blockName + "</option>";
						}
					}
					// set options to combobox
					$("#cbbBlockId").empty().append(optionStr);
					// enable combobox
					$("#cbbBlockId").prop("disabled", false);
				},
				error: function(e) {
					// display error message
					jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
				}
			});
		} else {
			// clear combobox
			// disable combobox
			$("#cbbBlockId").empty().prop("disabled", true);
		}
	}

	// Draw block data table based on search conditions
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
						$("#tblBody").find("tbody").remove();
						// create table starts
						var tableStringArray = [];
						// add tbody open tag
						tableStringArray.push("<tbody>");
						$("#farmNamelbl").html(returnedJsonData[0].farmName);
						$("#areaNamelbl").html(returnedJsonData[0].areaName);
						for (var i = 0; i < returnedJsonData.length; i++) {
							numberical_order++;
							// row open tag
							tableStringArray.push("<tr id='row" + i + "'>");
							tableStringArray.push("<td class='align-center'>"+numberical_order+"</td>");
							// Block name & Previous Round = 0
							tableStringArray.push("<td class='overflow-ellipsis' name='" + returnedJsonData[i].farmId + "' id = '" + returnedJsonData[i].areaId + "'block_id = '" + returnedJsonData[i].blockId + "'><a  class='blockName' name='" + i + "' href='#'>" + returnedJsonData[i].blockName + "</a></td>");
							// Previous Round = 1 & product adjust icon
							tableStringArray.push("<td class='align-center'><a class='adjust cursor-pointer' name='" + i + "' href='#'>" + $("#reference").val() + "</a></td>");
							// Note
							tableStringArray.push("<td class='overflow-ellipsis align-left'>" + returnedJsonData[i].note + "</td>");
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
						fixTable();
						// fix table height to fit page
						var tableHeight = calculateTableHeight();
						$("#divBody").height(tableHeight);
						// update total data count UI
						setDataCounts();
					    setPagerStatus();
					    // scroll to top of table
						$("#divBody").scrollTop(0).scrollLeft(0);
					}
				} else {
					$("#farmNamelbl").text($("#cbbFarmName").find("option:selected").text());
					$("#areaNamelbl").text($("#cbbAreaName").find("option:selected").text());
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
				$("#farmNamelbl").text($("#cbbFarmName").find("option:selected").text());
				$("#areaNamelbl").text($("#cbbAreaName").find("option:selected").text());
				// set return value
				returnValue = "";
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
		return returnValue;
	}

	// Draw product data in selected block
	function drawProductTable() {
		// make Ajax call to server to get data
		$.ajax({
			url: "getProductData",
			data: { "farmId": farmIdPopup, "areaId": areaIdPopup, "blockId": blockIdPopup, "previousRound": previousRound },
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
					var colWidthThreshold = 90; // 90% of table width
					var firstColThreshold = 10; // 10% of table width
					var oneColWidth = colWidthThreshold / (3 * linePerBlock); // one Line header "colspan" number is 3
					// calculate image width
					var popupWidth = $("#popupProductWrapper").width() / 2;
					var imageWidth = popupWidth * (oneColWidth * 1.5) / 100;

					$('.block-guide-fostering').attr('src', $('.block-guide-fostering').attr('src') + "?date=" + fosteringDate);
					$('.block-guide-fostering_check').attr('src', $('.block-guide-fostering_check').attr('src') + "?date=" + fostering_checkDate);
					$('.block-guide-disable').attr('src', $('.block-guide-disable').attr('src') + "?date=" + disableDate);
					$('.block-guide-disable_check').attr('src', $('.block-guide-disable_check').attr('src') + "?date=" + disable_checkDate);
					
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
						tableStringArray.push("<td class='border-top-none border-bottom-none darkKhaki'></td>");
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
						tableStringArray.push("<tr class='rowImgProduct'>");
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
											+ imageWidth + "px; max-width: 32px;' class='productImg productDisable' name='"
											+ (currentProduct.lineId + ":" + currentProduct.columnId)
											+ "' src='" + rootPath + "/resources/img/disable.png?date=" + disableDate + "'></td>");
									indexInArray++;
								} else {
									tableStringArray.push("<td colspan='3'><img style='width: "
											+ imageWidth + "px; max-width: 32px;' class='productImg productFostering' name='"
											+ (currentProduct.lineId + ":" + currentProduct.columnId)
											+ "' src='" + rootPath + "/resources/img/fostering.png?date=" + fosteringDate + "'></td>");
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
							tableStringArray.push("<td class='border-left-none' colspan='2' ></td>");
							for (var i = 0; i < linePerBlock; i++) {
								tableStringArray.push("<td  class = 'darkKhaki'></td>");
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
					// add close tag
					tableStringArray.push("</tbody>");
					// append all created string to table
					$("#tblProductBody").append(tableStringArray.join(''));

					// display user info popup
					showPopup($("#popupProductWrapper"));
					// fix header size
					$("#divHeadProduct").width($("#divBodyProduct").width() - 17);
					// scroll to top of table
					$("#divBodyProduct").scrollTop(0).scrollLeft(0);
				} else if (returnedJsonData == null) {
					// failed
					// display error message
					jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
				} else {
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

	// Update selected product process
	function updateProductData(previousRound) {
		// get product data
		var dataObject = {
			idString: currentProductIdString,
			previousRound: previousRound,
		}
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "updateProductData",
			contentType: "application/json",
			data: JSON.stringify(dataObject),
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				if (returnedJsonData != "") {
					// check returned value from server
					if (returnedJsonData == VALIDATE_BLANK_FIELDS) {
						// blank field(s)
						// display message
						jWarning(VALIDATE_BLANK_FIELDS_MESSAGE_SERVER, DIALOG_TITLE, DIALOG_OK_BUTTON);
					} else if (returnedJsonData == UPDATE_RESULT_SUCCESSFUL) {
						// reload product table
						drawProductTable();
						// display message
						jInfo(UPDATE_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
					} else if (returnedJsonData == UPDATE_RESULT_FAILED_DUPLLICATE) {
						// failed
						// display message
						jWarning(UPDATE_RESULT_FAILED_MESSAGE.replace('$1', UPDATE_RESULT_FAILED_DUPLLICATE), DIALOG_TITLE, DIALOG_OK_BUTTON);
					} else if (returnedJsonData == UPDATE_RESULT_FAILED_EXCEPTION) {
						// failed
						// display message
						jWarning(UPDATE_RESULT_FAILED_MESSAGE.replace('$1', UPDATE_RESULT_FAILED_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
					}
				}
				// reset variable
				currentProductIdString = "";
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	}

	// Search conditions object creation process
	function createSearchConditions() {
		var farmId = $("#cbbFarmName").find("option:selected").val();
		var areaId = (farmId == STATUS_NO_SELECT || $("#cbbAreaName").find("option:selected").val() == STATUS_NO_SELECT) ?
				STATUS_NO_SELECT : $("#cbbAreaName").find("option:selected").val();
		var blockId = (areaId == STATUS_NO_SELECT || $("#cbbBlockId").find("option:selected").val() == STATUS_NO_SELECT) ?
				STATUS_NO_SELECT : $("#cbbBlockId").find("option:selected").val();

		if (blockId == undefined) {
			blockId = STATUS_NO_SELECT;
		}
		if (farmId == undefined) {
			farmId = STATUS_NO_SELECT;
		}
		if (areaId == undefined) {
			areaId = STATUS_NO_SELECT;
		}
		// farmId Client 
		var farmIdClient = $("#farmIdClient").val();
		var areaIdClient = $("#areaIdClient").val();
		if (farmIdClient != "" && areaIdClient != "") {
			farmId = farmIdClient;
			areaId = areaIdClient;
			blockId = STATUS_NO_SELECT;
		}
		return {
			farmId: farmId,
			areaId: areaId,
			blockId: blockId,
			fromRow: from,
			itemCount: ITEM_IN_ONE_PAGE
		};
	}

	// User data object creation process, to use in Updating and Inserting block
	function createBlockDataObject() {
		return {
			farmId: $.trim($("#txtFarmIdPopup").val()),
			areaId: $.trim($("#txtAreaIdPopup").val()),
			blockId: $.trim($("#txtBlockIdPopup").val()),
			blockName: $.trim($("#txtBlockNamePopup").val()),
			deleteFlag:  DELETE_FLAG_OFF,
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

	// Reset search conditions process
	function resetSearchConditions() {
		$("#cbbFarmName").val(STATUS_NO_SELECT);
		// clear combobox
		$("#cbbAreaName").empty();
		$("#cbbBlockId").empty();
		// disable combobox
		$("#cbbAreaName").prop("disabled", true);
		$("#cbbBlockId").prop("disabled", true);
		$("#txtBlockName").val("");
	}

	// Clear all current text of popup controls
	function clearPopupControl() {
		// farm name
		$("#cbbFarmNamePopup").empty();
		// area name
		$("#cbbAreaNamePopup").empty();
		// block id
		$("#txtBlockIdPopup").val("");
		// block name
		$("#txtBlockNamePopup").val("");
		// status
		$("#chbDeletePopup").prop("checked", false);
		// note
		$("#txtNotePopup").val("");
	}

	// Change state of controls in popup based on mode
	function setPopupControlState(mode) {
		if (mode == MODE_NEW) {
			// reset numberical Order
			numberical_order = 0;
			$("#updateBlock").addClass("display-none");
			$("#addNewBlock").removeClass("display-none");
			// set current mode
			currentMode = MODE_NEW;
			// farm name
			$("#cbbFarmNamePopup").prop("disabled", false);
			//initFarmNameComboboxData(true);
			// area name
			$("#cbbAreaNamePopup").prop("disabled", false);
			// block id
			$("#txtBlockIdPopup").prop("disabled", false);
			// block name
			$("#txtBlockNamePopup").prop("disabled", false);
			// status
			$("#chbDeletePopup").prop("disabled", false);
			// note
			$("#txtNotePopup").prop("disabled", false);
			// register button
			$("#btnRegisterPopup").show();
		} else if (mode == MODE_EDIT) {
			$("#addNewBlock").addClass("display-none");
			$("#updateBlock").removeClass("display-none");
			// set current mode
			currentMode = MODE_EDIT;
			// farm name
			$("#cbbFarmNamePopup").prop("disabled", true);
			// area name
			$("#cbbAreaNamePopup").prop("disabled", true);
			// block id
			$("#txtBlockIdPopup").prop("disabled", true);
			// block name
			$("#txtBlockNamePopup").prop("disabled", false);
			// status
			$("#chbDeletePopup").prop("disabled", false);
			// note
			$("#txtNotePopup").prop("disabled", false);
			// register button
			$("#btnRegisterPopup").show();
		} else if (mode == MODE_VIEW) {
			// set current mode
			currentMode = MODE_VIEW;
			// farm name
			$("#cbbFarmNamePopup").prop("disabled", true);
			// area name
			$("#cbbAreaNamePopup").prop("disabled", true);
			// block id
			$("#txtBlockIdPopup").prop("disabled", true);
			// block name
			$("#txtBlockNamePopup").prop("disabled", true);
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
		if ($.trim($("#txtBlockNamePopup").val()) == "") {
			return BLOCK_NAME;
		} else {
			return "";
		}
	}
});