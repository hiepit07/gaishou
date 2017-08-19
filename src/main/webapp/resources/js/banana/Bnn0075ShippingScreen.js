/**
 * @author Dung Trinh
 */
$(document).ready(function() {
	//--------------- Constants definition ---------------
	var CURRENT_SCREEN_NAME = "0075";
	// number of items in one page in table
	var ITEM_IN_ONE_PAGE = ITEM_IN_ONE_PAGE_0075;
	var NEXT_SCREEN_NAME = "0061";
	// when Cultivation selected nothing
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
	// variable to store current selected mode
	var currentMode = "";
	// numberical_order
	var numberical_order = 0;
	//check shipping number exist
	var checkShippingNumber = true;
	// search conditions 
	var searchConditions = null;
	//load combobox farm name
	initFarmNameComboboxData(false);
	// Display current date
	$("#txtCurrentDate").text(getCurrentDate() + " (" + DAY_OF_WEEK + ") ");
	//load combobox area name
	
	// Farm name combobox change event process
	$("#cbbFarmName, #cbbFarmNamePopup").bind("change", function() {
		// load Area name combobox
		loadAreaNameCombobox();
	});
	
	// Load shipping name combobox data
	function loadAreaNameCombobox() {
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
					var optionStr = "";
					if (returnedJsonData != "") {
						for (var i = 0; i < returnedJsonData.length; i++) {
							optionStr += "<option value='" + returnedJsonData[i].areaId + "'>" + returnedJsonData[i].areaName + "</option>";
						}
					}
					// set options to combobox
					$("#cbbAreaName").empty().append(optionStr);
					// enable combobox
					$("#cbbAreaName").prop("disabled", false);
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
	
	// Get data for combobox process
	function initFarmNameComboboxData(isPopupMode) {
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
				loadAreaNameCombobox();
			} else {
				var optionStrPopup = "<option value=''></option>";
				for (var i = 0; i < farmData.length; i++) {
					optionStrPopup += "<option value='" + farmData[i].farmId + "'>" + farmData[i].farmName + "</option>";
				}
				$("#cbbFarmNamePopup").empty()
				$("#cbbFarmNamePopup").append(optionStrPopup);
			}
		}
		
		$("#ship_start_date").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#ship_start_date").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#harvest_start_date").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#harvest_start_date").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#harvest_end_date").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#harvest_end_date").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#ship_end_date").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#ship_end_date").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#shipDatePopup").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		});
		$("#shipDatePopup").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
	}
	// Search button event process
	$("#btnSearch").bind("click", function() {
		if (!checkRole(CURRENT_SCREEN_NAME, REFERENCE_FLAG)) {
			// display message
			jWarning(ROLE_REFER_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
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
		}
	});
	
	// Edit block info click event process
	$(document).on("click", ".edit", function(e) {
		
		if (!checkRole(CURRENT_SCREEN_NAME, UPDATABLE_FLAG)) {
			// display message
			jWarning(ROLE_UPDAT_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			e.preventDefault();
			// get row index
			selectedRowIndex = $(this).attr("name");
			/// get ids of selected block
			// get shipping number edit
			var shippingNumber = $("#row" + selectedRowIndex).find("td").eq(1).attr("value"),
			// get farm id edit
			farmId = $("#row" + selectedRowIndex).find("td").eq(2).attr("value"),
			// get farm name edit
			farmName = $("#row" + selectedRowIndex).find("td").eq(2).text();
			// get area id edit
			areaId = $("#row" + selectedRowIndex).find("td").eq(3).attr("value"),
			// get area name edit
			areaName = $("#row" + selectedRowIndex).find("td").eq(3).text();
			// get harvest date edit
			harvestDate = $("#row" + selectedRowIndex).find("td").eq(4).attr("value");
			// get shipping date edit
			shippingDate = $("#row" + selectedRowIndex).find("td").eq(5).attr("value");
			// get last update date
			var lastUpdateDate = $("#row" + selectedRowIndex).find("td").eq(5).attr("name");
			var lastUpdateDateProduct = $("#row" + selectedRowIndex).find("td").eq(5).attr("lastUpdateDateProduct");
			
			/// set data for popup
			// set shipping number for shippingNumberPopup
			$("#shippingNumberPopup").val(shippingNumber);
			// set farm id for farmIdPopup
			var optionNameStr = "<option value='" + farmId + "'>" + htmlEntities(farmName) + "</option>";
			$("#cbbFarmNamePopup").empty().append(optionNameStr);
			// set area id for arearIdPopup
			var optionAreaStr = "<option value='" + areaId + "'>" + htmlEntities(areaName) + "</option>";
			$("#cbbAreaNamePopup").empty().append(optionAreaStr);
			// set harvest date for harvestDatePopup
			$("#harvestDatePopup").val(convertDataTo(harvestDate));
			// set shipping date for shippingDatePopup
			$("#shipDatePopup").val(convertDataTo(shippingDate));
			$("#shipDatePopup").attr("name", lastUpdateDate);
			$("#shipDatePopup").attr("lastUpdateDateProduct", lastUpdateDateProduct);
			// change state of controls in popup based on mode
			setPopupControlState(MODE_EDIT);
			// display user info popup
			showPopup($("#popupWrapper"));
		}
	});
	
	// Cancel button in popup click event process
	$("#btnCancelPopup").bind("click", function() {
		// hide user info popup
		hidePopup($("#popupWrapper"));
	});
	
	// Register button in popup click event process
	$("#btnRegisterPopup").bind("click", function() {
		var dataObject = null;
		if (!checkRole(CURRENT_SCREEN_NAME, ADDABLE_FLAG)) {
			// display message
			jWarning(ROLE_ADD_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			if (currentMode == MODE_EDIT) {
				if (checkInputBlankFields() != "") {
					// id is in wrong format
					// display message
					jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
				} else if(!checkInputDateUpdate()){
					// id is in wrong format
					// display message
					jWarning(MESSAGE_SHIPPING_DATE_IS_LARGER_THAN_HARVEST_DATE, DIALOG_TITLE, DIALOG_OK_BUTTON);
				}else {
					// update existing user in DB
					// get user input data
					var dataShipObject = createShippingScreenDataObject();
					var checkExist = "insertData";
					if(checkShippingNumber){
						checkExist = "updateData";
					}
					// make Ajax call to server to get data
					$.ajax({
						type: "POST",
						url: checkExist,
						data: JSON.stringify(dataShipObject),
						contentType: "application/json",
						async: false,
						success: function(returnedJsonData) {
							if (checkSessionTimeout(returnedJsonData) == 1) return;
							if (returnedJsonData != "") {
								var insertSuccessfull = returnedJsonData.split(",");
								// check returned value from server
								if (insertSuccessfull[0] == UPDATE_RESULT_SUCCESSFUL || insertSuccessfull[0] == INSERT_RESULT_SUCCESSFUL) {
									// update the corresponding user data in search table
									// make Ajax call to server to get data
									// reset Data 
									clearPopupControl()
									// reset variables
									resetVariables();
									resetSearchConditions();
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
									//message successful
									if(insertSuccessfull[1] != null && insertSuccessfull[1]!= '' && insertSuccessfull[1] != undefined && insertSuccessfull[1] != 'undefined'){
										jInfo(UPDATE_RESULT_SUCCESSFUL_MESSAGE_SHIPPING.replace('$1', insertSuccessfull[1]), DIALOG_TITLE, DIALOG_OK_BUTTON);
									} else {
										jInfo(UPDATE_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
									}

								} else if (returnedJsonData == INSERT_RESULT_FAILED_CULTIVATION_SHIPPING) {
									// failed
									// display message
									jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
								} else if (returnedJsonData == INSERT_RESULT_FAILED_EXCEPTION) {
									// failed
									// display message
									jWarning(INSERT_RESULT_FAILED_MESSAGE.replace('$1', INSERT_RESULT_FAILED_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
								} else if (returnedJsonData == INSERT_RESULT_DUPLICATED) {
									// failed
									// display message
									jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
								} else if (returnedJsonData == UPDATE_RESULT_FAILED_CULTIVATION_SHIPPING) {
									// failed
									// display message
									jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
								} else if (returnedJsonData == UPDATE_RESULT_FAILED_EXCEPTION) {
									// failed
									// display message
									jWarning(UPDATE_RESULT_FAILED_MESSAGE.replace('$1', UPDATE_RESULT_FAILED_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
								} else if (returnedJsonData == UPDATE_RESULT_FAILED_UPDATE_DATE) {
									// failed
									// display message
									jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
								} else if (returnedJsonData == UPDATE_RESULT_FAILED_CULTIVATION_SHIPPING_UPDATE_DATE) {
									// failed
									// display message
									jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
								} else if (returnedJsonData == WRITE_FILE_EXCEPTION) {
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
		}
	});
	
	//Change Page Bnn0061
	$(document).on("click", ".shippingNumber", function(e) {
		e.preventDefault();
		// check authority of user
		if (!checkAuthority(NEXT_SCREEN_NAME)) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			var shipNumber = $(this).attr("id");
			var rowID = $(this).parent().parent().attr("id");
			var farmId = $($("#" + rowID).find("td").eq(2)).attr("value");
			var areaId = $($("#" + rowID).find("td").eq(3)).attr("value");
			var harvestDate = $($("#" + rowID).find("td").eq(4)).attr("value");
			var shipDate = $($("#" + rowID).find("td").eq(5)).attr("value");

			// create parameter object
			var parameterObj = {
				"url": rootPath + '/' + NEXT_SCREEN_NAME + '/',
				"shipNumber": shipNumber,
				"farmId": farmId,
				"areaId": areaId,
				"harvestDate": harvestDate,
				"shipDate": shipDate
			};
			// save to history
			savePageToHistory(parameterObj);

			$('<form>', {
				"id": "formTransfer",
				"html": '<input type="hidden" id="shipNumber" name="shipNumber" value="' + shipNumber + '" />' +
						'<input type="hidden" id="farmId" name="farmId" value="' + farmId + '" />' +
						'<input type="hidden" id="areaId" name="areaId" value="' + areaId + '" />' +
						'<input type="hidden" id="harvestDate" name="harvestDate" value="' + harvestDate + '" />' +
						'<input type="hidden" id="shipDate" name="shipDate" value="' + shipDate + '" />',
				"method": 'POST',
				"action": rootPath + '/' + NEXT_SCREEN_NAME + '/'
			}).appendTo(document.body).submit();
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
	
	// Back button click event process
	$("#btnBack").bind("click", function() {
		goBackToPreviousPage();
	});
	
	// Change state of controls in popup based on mode
	function setPopupControlState(mode) {
		if (mode == MODE_EDIT) {
			// set current mode
			currentMode = MODE_EDIT;
			// Shipping Number
			checkShippingNumber = true;//Shipping number exist
			if($("#shippingNumberPopup").val() == ""){
				checkShippingNumber = false;//Shipping number not exist
				$("#popupTitle").find('p').html(registerPopup);
			} else {
				$("#popupTitle").find('p').html(editPopup);
			}
			$("#shippingNumberPopup").prop("disabled", true);
			// Farm Name
			$("#cbbFarmNamePopup").prop("disabled", true);
			// Area Name
			$("#cbbAreaNamePopup").prop("disabled", true);
			// Harvest Date
			$("#harvestDatePopup").prop("disabled", true);
			// Shipping Date
			$("#shipDatePopup").prop("disabled", false);
			// Number Assignment Popup button
			$("#btnNumberAssignmentPopup").show();
			$("#btnNumberAssignmentPopup").prop("disabled", checkShippingNumber);
			if(checkShippingNumber){
				// disable button NumberAssignmentPopup
				$("#btnNumberAssignmentPopup").addClass("btn-disable");
			}else{
				$("#btnNumberAssignmentPopup").removeClass("btn-disable");
			}
			// Registration button
			$("#btnRegisterPopup").show();
			// Cancel button
			$("#btnCancel").show();
		}
	}
	
	// Clear all current text of popup controls
	function clearPopupControl() {
		// Shipping Number
		$("#shippingNumberPopup").val("");
		// Farm Id
		$("#cbbFarmNamePopup").val(STATUS_NO_SELECT);
		$("#cbbFarmNamePopup").prop("disabled", false);
		// Area Name
		$("#cbbAreaNamePopup").val(STATUS_NO_SELECT);
		$("#cbbAreaNamePopup").prop("disabled", false);
		// Farm Name
		$("#harvestDatePopup").val("");
		// Farm Name
		$("#shipDatePopup").val("");
	}
	
	// Shipping data object creation process, to use in Updating and Inserting user
	function createShippingScreenDataObject() {
		// FarmId
		var farmId = $("#cbbFarmNamePopup").find("option:selected").val();
		farmId = (farmId == STATUS_NO_SELECT) ? STATUS_NO_SELECT : farmId;
		// AreaId
		var areaId = (farmId == STATUS_NO_SELECT || $("#cbbAreaNamePopup").find("option:selected").val() == STATUS_NO_SELECT) ?
				STATUS_NO_SELECT : $("#cbbAreaNamePopup").find("option:selected").val();
		return {
			shippingNumber: $.trim($("#shippingNumberPopup").val()),
			farmId: farmId,
			areaId: areaId,
			harvestDate: convertDataFrom($("#harvestDatePopup").val()),
			shippingDate: convertDataFrom(($("#shipDatePopup").val() == null ? '': $("#shipDatePopup").val())),
			lastUpdateDate: $("#shipDatePopup").attr("name"),
			lastUpdateDateProduct: $("#shipDatePopup").attr("lastUpdateDateProduct"),			
			deleteFlag: DELETE_FLAG_OFF
		};
	}
	
	// Reset variables process
	function resetVariables() {
		totalResultCount = 0;
		currentPage = 1;
		from = 0;
	}
	
	//search all
	$("#btnSearch").trigger("click");

	// Draw Shipping data table based on search conditions
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
							tableStringArray.push("<td class='align-center'>"+ numberical_order +"</td>");
							// Shipping Number
							tableStringArray.push("<td class='align-center' value="+ returnedJsonData[i].shippingNumber +" ><a href='' id = '"+ returnedJsonData[i].shippingNumber +"' class='shippingNumber cursor-pointer' value = '"+ returnedJsonData[i].shippingNumber +"'>" + returnedJsonData[i].shippingNumber + "</a></td>");
							// Farm Name
							tableStringArray.push("<td class='overflow-ellipsis' value="+ returnedJsonData[i].farmId +">" + returnedJsonData[i].farmName + "</td>");
							// Area Id
							tableStringArray.push("<td class='overflow-ellipsis' value="+ returnedJsonData[i].areaId +">" + returnedJsonData[i].areaName + "</td>");
							// Harvest Date
							if(returnedJsonData[i].harvestDate == null){
								returnedJsonData[i].harvestDate = '';
							}
							tableStringArray.push("<td class = 'align-right' value="+ returnedJsonData[i].harvestDate +">" + convertDataTo(returnedJsonData[i].harvestDate) + "</td>");
							// Ship Date
							if(returnedJsonData[i].shipDate == null){
								returnedJsonData[i].shipDate = '';
							}
							tableStringArray.push("<td class = 'align-right' name='" + returnedJsonData[i].lastUpdateDate + "' lastUpdateDateProduct='" + returnedJsonData[i].lastUpdateDateProduct + "' value="+ returnedJsonData[i].shipDate +">" + convertDataTo(returnedJsonData[i].shipDate) + "</td>");
							
							if(returnedJsonData[i].shippingNumber != ''){
								//shipping register
								tableStringArray.push("<td></td>");
								// update icon
								tableStringArray.push("<td><img class='edit cursor-pointer' name='" + i + "' src='" + rootPath + "/resources/img/icon_edit.png?date=" + icon_editDate + "'></td>");
							} else {
								//shipping register
								tableStringArray.push("<td class = 'align-center' ><a href='' class='edit cursor-pointer' name='" + i + "'>" + shippingRegister +"</td>");
								// update icon
								tableStringArray.push("<td><img src='" + rootPath + "/resources/img/none_edit.png?date=" + none_editDate + "'></td>");
							}
							// row close tag
							tableStringArray.push("</tr>");
						}
						// add tbody close tag
						tableStringArray.push("</tbody>");
						// append all created string to table
						$("#tblBody").append(tableStringArray.join(''));
						// show search result
						$(".body-delete").removeClass("display-none");
						// fix table header and body when scrolling only the table body
						// Fix table height to fit page
						var tableHeight = calculateTableHeight();
						$("#divBody").height(tableHeight - 102);
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
	
	// Update total data count process
	function setDataCounts() {
		$("#txtCounts").text(totalResultCount.toString().replace(/^(-?\d+)(\d{3})/, "$1,$2"));
	}
	
	function resetSearchConditions() {
		$("#harvest_start_date").val("");
		$("#harvest_end_date").val("");
		$("#ship_start_date").val("");
		$("#ship_end_date").val("");
		$("#shipping_number").val("");
	}
	
	// Search conditions object creation process
	function createSearchConditions() {
		// FarmId
		var farmId = $("#cbbFarmName").find("option:selected").val();
		farmId = (farmId == STATUS_NO_SELECT) ? STATUS_NO_SELECT : farmId;
		// AreaId
		var areaId = (farmId == STATUS_NO_SELECT || $("#cbbAreaName").find("option:selected").val() == STATUS_NO_SELECT) ?
				STATUS_NO_SELECT : $("#cbbAreaName").find("option:selected").val();
		var shippingNumber = $.trim($("#shipping_number").val());
		var harvestStartDate = convertDataFrom($("#harvest_start_date").val());
		var harvestEndDate = convertDataFrom($("#harvest_end_date").val());
		var shipStartDate = convertDataFrom($("#ship_start_date").val());
		var shipEndDate = convertDataFrom($("#ship_end_date").val());
		return {
			areaId: areaId,
			farmId: farmId,
			farmName: "",
			areaName: "",
			shippingNumber: shippingNumber,
			harvestStartDate: harvestStartDate,
			harvestEndDate: harvestEndDate,
			shipStartDate: shipStartDate,
			shipEndDate: shipEndDate,
			fromRow: from,
			itemCount: ITEM_IN_ONE_PAGE
		};
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
	
	//get single conditions
	function createGetSingleConditions(farmId,areaId) {
		return {
			farmId: farmId,
			areaId: areaId
		};
	}
	
	//Check input: blank fields
	function checkInputBlankFields(){
		var checkFields = createShippingScreenDataObject();
		if (checkFields.shippingDate==""){
			return shipDatePopupBlank;
		} else {
			return "";
		}
	}
	//check start date < end date
	function checkInputDateSearch() {
		var checkDate = createSearchConditions();
		var checkBoolean = true;
		if(checkDate.harvestStartDate != "" && checkDate.harvestEndDate != ""){
			if(checkDate.harvestStartDate > checkDate.harvestEndDate) {
				checkBoolean = false;
			}
		}
		if(checkDate.shipStartDate != "" && checkDate.shipEndDate != ""){
			if(checkDate.shipStartDate > checkDate.shipEndDate){
				checkBoolean = false;
			}
		}
		return checkBoolean;
	}
	
	//check shipping date > harvset date
	function checkInputDateUpdate(){
		var checkFields = createShippingScreenDataObject();
		var checkBoolean = true;
		if(checkFields.harvestDate > checkFields.shippingDate){
			checkBoolean = false;
		}
		return checkBoolean;
	}
	function htmlEntities(str) {
	    return String(str).replace(/&/g, "&amp;")
	    					.replace(/</g, "&lt;")
	    					.replace(/>/g, "&gt;")
	    					.replace(/"/g, "&quot;")
	    					.replace(/'/g, "&#39;")
	    					.replace(/%/g, "&#37;");
	}
});

