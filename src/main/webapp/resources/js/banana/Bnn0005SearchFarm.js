/**
 * @author Hieu Dao
 */

$(document).ready(function() {

	//--------------- Constants definition ---------------
	// number of items in one page in table
	var ITEM_IN_ONE_PAGE = ITEM_IN_ONE_PAGE_0005;
	var NEXT_SCREEN_NAME = "0007";
	//--------------- Variables definition ---------------
	// Application path
	var rootPath = getContextPath();
	// Search result total data count
	var totalResultCount = 0;
	// Variables for handling pager
	var currentPage = 1;
	var maxPage = 0;
	var from = 0;
	// Variable to store selected row index
	var selectedRowIndex = "";
	// Variable to store current selected mode
	var currentMode = "";
	// numberical_order
	var numberical_order = 0;
	// search conditions 
	var searchConditions = null;
	//Max count
	var checkMaxCount = false;
	// variable to store last updated date time when user opens edit popup
	var currentLastUpdatedTimeString = "";
	// Display current date
	$("#txtCurrentDate").text(getCurrentDate() + " (" + DAY_OF_WEEK + ") ");

	// Initialize combo box data
	initData();

	// Search button event process
	$("#btnSearch").bind("click", function() {
		numberical_order = 0;
		// Reset variables
		resetVariables();
		// search conditions
		searchConditions = createFarmSearchConditions();
		// Draw table
		drawResult = drawFarmTable();
		if (drawResult != "") {
			// Update total data count UI
			setDataCounts();
		}
	});

	// Clear user name button event process
	$("#btnClear").bind("click", function() {
		$("#txtUsersNamePopup").val("");
	});

	// Register farm click event process
	$("#btnNew").bind("click", function() {

		// Clear all current text of pop up controls
		clearPopupControl();

		// Change state of controls in pop up based on mode
		setPopupControlState(MODE_NEW);

		// Display farm info pop up
		showPopup($("#popupWrapper"));

		// Fix farm info pop up height
		fixUserPoupHeight();
	});

	// Call search user pop up
	$(document).on("click", "#loadUserPopup", function() {

		// Check current mode
		var mode = false;
		if (currentMode == MODE_NEW){
			mode = true;
		}
		// Set Config Variables
		var config = {
			"listUserId": 'txtUsersIdPopup',
			"listUserIdNew": 'txtNewUsersIdPopup',
			"listUserName": 'txtUsersNamePopup',
			"modeAction": mode,
			"modeSelect": true
		};
		getSearchUser(config);
	});

	// Update farm info click event process
	$(document).on("click", ".edit", function() {
		// variables definition
		farmId = "";
		// get row index
		selectedRowIndex = $(this).attr("name");
		// get id of selected farm
		farmId = $("#row" + selectedRowIndex).find("td").eq(1).find("input").val();

		// make Ajax call to server to get data
		$.ajax({
			url: "getSingleData",
			data: { "farmId": farmId },
			type: "POST",
			async: false,
			success: function(returnedJsonData) {

				// Check returned value from server
				if (checkSessionTimeout(returnedJsonData) == 1) return;

				// Read data from Json for Form
				if (returnedJsonData != "") {

					// Clear all current text of pop up controls
					clearPopupControl();

					// Farm id
					$("#hddFarmIdPopup").val(returnedJsonData.farmId);
					// Farm name
					$("#txtFarmNamePopup").val(returnedJsonData.farmName);
					// User id
					$("#txtUsersIdPopup").val(returnedJsonData.usersId);
					// User New id
					$("#txtNewUsersIdPopup").val(returnedJsonData.usersId);
					// User name
					$("#txtUsersNamePopup").val(returnedJsonData.usersName);
					// Address
					$("#txtAddressPopup").val(returnedJsonData.farmLocation);
					// Open date
					$("#txtOpenDatePopup").val(convertDataTo(returnedJsonData.dateOpen));
					// Time from
					$("#txtTimeFromPopup").val(convertDataTo(returnedJsonData.fromTime));
					// Time to
					$("#txtTimeToPopup").val(convertDataTo(returnedJsonData.toTime));
					// Size of plan
					$("#txtSizeOfPlanPopup").val(returnedJsonData.sizeOfPlan);
					// Line of plan
					$("#txtLineOfPlanPopup").val(returnedJsonData.numberOfLines);
					// Column of plan
					$("#txtColumnOfPlanPopup").val(returnedJsonData.numberOfColumns);
					// E-mail
					$("#txtEmailPopup").val(returnedJsonData.emailAddress);
					// Phone
					$("#txtPhonePopup").val(returnedJsonData.phone);
					// Fax
					$("#txtFaxPopup").val(returnedJsonData.fax);
					// Climate
					$("#txtClimatePopup").val(returnedJsonData.climate);
					// Soil
					$("#txtSoilPopup").val(returnedJsonData.soil);
					// Note
					$("#txtNotePopup").val(returnedJsonData.note);
					// last updated date time
					currentLastUpdatedTimeString = returnedJsonData.lastUpdateDate;
					// Status
					if (returnedJsonData.deleteFlag == DELETE_FLAG_OFF) {
						$("#chbDeletePopup").prop("checked", false);
					} else if (returnedJsonData.deleteFlag == DELETE_FLAG_ON) {
						$("#chbDeletePopup").prop("checked", true);
					}

					// Change state of controls in pop up based on mode
					setPopupControlState(MODE_EDIT);

					// Display farm info pop up
					showPopup($("#popupWrapper"));
				} else {
					jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
				}				
			},
			error: function(e) {
				// Display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	});

	// Register button in pop up click event process
	$("#btnRegisterFarmPopup").bind("click", function() {
		var email = $("#txtEmailPopup").val();
		
		// Variables definition
		var dataFarmObject = null;

		// Check current mode
		if (currentMode == MODE_NEW) {

			// Check for user input
			if (checkInputBlankFields() != "") {
				// Blank field(s)
				// Display message
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if(!checkInputDateUpdate()){
				// id is in wrong format
				// display message
				jWarning(VALIDATE_STARTDATE_LAGER_ENDDATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if(!checkDoubleField($("#txtSizeOfPlanPopup").val(),6,4,1)){
				jWarning(MESSAGE_FORMAT_FILED_IS_SIZE_OF_PLAN, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (checkInputNumberFields() != "") {
				jWarning(VALIDATE_NUMBER_FIELDS_MESSAGE.replace('$1', checkInputNumberFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if(!validateEmail(email)){
				// email is in wrong format
				// display message
				jWarning(MESSAGE_FORMAT_EMAIL_IS_INVALID, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (checkPhoneFaxFields() != "") {
				jWarning(VALIDATE_NUMBER_FIELDS_MESSAGE.replace('$1', checkPhoneFaxFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else{

				// Insert new farm to DB
				// Get farm input data
				dataFarmObject = createFarmDataObject();

				// Make Ajax call to server to get data
				$.ajax({
					type: "POST",
					url: "insertData",
					contentType: "application/json",
					data: JSON.stringify(dataFarmObject),
					async: false,
					success: function(returnedJsonData) {

						// Check returned value from server
						if (checkSessionTimeout(returnedJsonData) == 1) return;

						// Read data from Json for Form
						if (returnedJsonData != "") {

							// Check returned value from server
							if (returnedJsonData == INSERT_RESULT_SUCCESSFUL) {

								// Reset variables
								resetVariables();
								// reset search conditions
								resetSearchConditions();
								searchConditions = createFarmSearchConditions();
								// Draw table
								numberical_order = 0;
								// Initialize combo box data
								loadFarmNameCombobox();
								// create table
								drawResult = drawFarmTable();
								if (drawResult != "") {
									// Update total data count UI
									setDataCounts();
								}
								// Display message
								jInfo(INSERT_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
								// Hide pop up
								hidePopup($("#popupWrapper"));

							} else if (returnedJsonData == INSERT_RESULT_FAILED_CULTIVATION_FARM) {
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
								jWarning(INSERT_FARM_DUPLICATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							}
						}
					},
					error: function(e) {

						// Display error message
						jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
					}
				});
			}
		} else if (currentMode == MODE_EDIT) {

			// Check for user input
			if (checkInputBlankFields() != "") {
				// Blank field(s)
				// Display message
				jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBlankFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if(!checkInputDateUpdate()){
				// id is in wrong format
				// display message
				jWarning(VALIDATE_STARTDATE_LAGER_ENDDATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if(!checkDoubleField($("#txtSizeOfPlanPopup").val(),6,4,1)){
				jWarning(MESSAGE_FORMAT_FILED_IS_SIZE_OF_PLAN, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (checkInputNumberFields() != "") {
				jWarning(VALIDATE_NUMBER_FIELDS_MESSAGE.replace('$1', checkInputNumberFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if(!validateEmail(email)){
				// email is in wrong format
				// display message
				jWarning(MESSAGE_FORMAT_EMAIL_IS_INVALID, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else if (checkPhoneFaxFields() != "") {
				jWarning(VALIDATE_NUMBER_FIELDS_MESSAGE.replace('$1', checkPhoneFaxFields()), DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// Update user in DB
				// Get user input data
				dataFarmObject = updateFarmDataObject();

				// Make Ajax call to server to get data
				$.ajax({
					type: "POST",
					url: "updateData",
					contentType: "application/json",
					data: JSON.stringify(dataFarmObject),
					async: false,
					success: function(returnedJsonData) {

						// Check returned value from server
						if (checkSessionTimeout(returnedJsonData) == 1) return;

						// Read data from Json for Form
						if (returnedJsonData != "") {

							// Check returned value from server
							if (returnedJsonData == UPDATE_RESULT_SUCCESSFUL) {

								// Reset variables
								resetVariables();
								// reset search conditions
								resetSearchConditions();
								searchConditions = createFarmSearchConditions();
								// Initialize combo box data
								loadFarmNameCombobox();
								// Draw table
								numberical_order = 0;
								drawResult = drawFarmTable();
								if (drawResult != "") {
									// Update total data count UI
									setDataCounts();
								}
								// Display message
								jInfo(UPDATE_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
								// Hide pop up
								hidePopup($("#popupWrapper"));
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_UPDATE_DATE) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_CULTIVATION_FARM) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_EXCEPTION) {
								// failed
								// display message
								jWarning(UPDATE_RESULT_FAILED_MESSAGE.replace('$1', UPDATE_RESULT_FAILED_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == UPDATE_RESULT_FAILED_DUPLLICATE) {
								// failed
								// display message
								jWarning(UPDATE_FARM_DUPLICATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							}
						}
					},
					error: function(e) {

						// Display error message
						jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
					}
				});
			}
		}
	});

	// Delete farm click event process
	$(document).on("click", ".delete", function() {
		var lastUpdateDate = $(this).attr("lastUpdateDate");
		// get row index
		selectedRowIndex = $(this).attr("name");
		// display confirmation message
		jQuestion_warning(DELETE_CONFIRM_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
			if (val) {

				// get id of selected user
				farmId = $("#row" + selectedRowIndex).find("td").eq(1).find("input").val();
				
				// make Ajax call to server to get data
				$.ajax({
					url: "deleteData",
					data: { "farmId": farmId,
							"lastUpdateDate": lastUpdateDate
					},
					type: "POST",
					async: false,
					success: function(returnedJsonData) {

						// Check returned value from server
						if (checkSessionTimeout(returnedJsonData) == 1) return;

						// Read data from Json for Form
						if (returnedJsonData != "") {

							// check returned value from server
							if (returnedJsonData == DELETE_RESULT_SUCCESSFUL) {

								// search data again
								// reset variables
								resetVariables();
								// reset search conditions
								resetSearchConditions();
								searchConditions = createFarmSearchConditions();
								// Initialize combo box data
								loadFarmNameCombobox();
								// draw table
								numberical_order = 0;
								drawResult = drawFarmTable();
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
							} else if (returnedJsonData == DELETE_RESULT_FAILED_CULTIVATION_FARM) {
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

	// Get next link click event
	$(document).on("click", ".goBnn0007", function() {
		// check authority of user
		if (!checkAuthority(NEXT_SCREEN_NAME)) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			var farmId = $(this).parent().find("input").val();

			// create parameter object
			var parameterObj = {
				"url": rootPath + '/' + NEXT_SCREEN_NAME + '/',
				"farmId": farmId
			};
			// save to history
			savePageToHistory(parameterObj);

			$('<form>', {
				"id": "formTransfer",
				"html": '<input type="hidden" id="farmId" name="farmId" value="' + farmId + '" />',
				"method": 'POST',
				"action": rootPath + '/' + NEXT_SCREEN_NAME + '/'
			}).appendTo(document.body).submit();
		}

	});
	
	// Back button click event process
	$("#btnBack").bind("click", function() {
		goBackToPreviousPage();
	});

	// Cancel button in farm pop up click event process
	$("#btnCancelFarmPopup").bind("click", function() {
		// Hide farm info pop up
		hidePopup($("#popupWrapper"));
	});

	
	$(document).on('keypress', '#txtSizeOfPlanPopup', function(e) {
		  if (e.charCode != 0 && (e.charCode < 46 || e.charCode > 57 || e.charCode == 47)){
		   e.preventDefault();
		  }
		  if(this.value == 0){
			  $(this).val("");
		  }
	});
	
	$(document).on('keypress', '#txtColumnOfPlanPopup', function(e) {
		  if (e.charCode != 0 && (e.charCode < 48 || e.charCode > 57)){
		   e.preventDefault();
		  }
		  if(this.value == 0){
			  $(this).val("");
		  }
	});
	
	$(document).on('keypress', '#txtLineOfPlanPopup', function(e) {
		  if (e.charCode != 0 && (e.charCode < 48 || e.charCode > 57)){
		   e.preventDefault();
		  }
		  if(this.value == 0){
			  $(this).val("");
		  }
	});
	
	$(document).on('change', '#txtSizeOfPlanPopup', function(e) {
		if ($('#txtSizeOfPlanPopup').filter(function() {
	        	return parseInt(this.value, 0) !== 0;
	    	}).length === 0) {
			$(this).val("");
		}
	});
	
	$(document).on('change', '#txtColumnOfPlanPopup', function(e) {
		if ($('#txtColumnOfPlanPopup').filter(function() {
	        	return parseInt(this.value, 0) !== 0;
	    	}).length === 0) {
			$(this).val("");
		}
	});
	
	$(document).on('change', '#txtLineOfPlanPopup', function(e) {
		if ($('#txtLineOfPlanPopup').filter(function() {
	        	return parseInt(this.value, 0) !== 0;
	    	}).length === 0) {
			$(this).val("");
		}
	});

	// call Paging event
	function pagingEvent() {
		$("#btnScUserPupFirst").unbind("click");
		$("#btnScUserPupPrevious").unbind("click");
		$("#btnScUserPupNext").unbind("click");
		$("#btnScUserPupLast").unbind("click");
		$("#txtScUserPupGoToPage").unbind("keyup");
		$("#txtScUserPupGoToPage").unbind("keydown");
		$("#btnScUserPupGoToPage").unbind("click");

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
	}
	
	// Get data for combo box and task table process
	function initData() {

		//load combobox farm name
		initFarmNameComboboxData(false);

		// initialize date picker
		$("#txtOpenDatePopup").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		}).datepicker("setDate", new Date());
		$("#txtOpenDatePopup").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#txtTimeFromPopup").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		}).datepicker("setDate", new Date());
		$("#txtTimeFromPopup").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		$("#txtTimeToPopup").datepicker({
			changeMonth: true,
			changeYear: true,
			showOn: "button",
		    buttonImage: rootPath + "/resources/img/calendar.gif",
		    buttonImageOnly: true,
		    dateFormat: 'dd/mm/yy',
		    defaultDate: new Date()
		}).datepicker("setDate", new Date());
		$("#txtTimeToPopup").inputmask("dd/mm/yyyy", { 
			placeholder: "__/__/____",
	        onincomplete: function() {
	            $(this).val('');
	        }
	    });
		
		pagingEvent();
	}
	// Get data for combobox process
	function initFarmNameComboboxData(isPopupMode) {
		// farm
		if (farmData != "") {
			var farmStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
			for (var i = 0; i < farmData.length; i++) {
				farmStr += "<option value='" + farmData[i].farmId + "'>" + farmData[i].farmName + "</option>";
			}
			$("#cbbFarmName").empty();
			$("#cbbFarmName").append(farmStr);
			$("#cbbFarmName").prop("disabled", false);
		} else {
			// create option string
			var farmStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
			// set options to combobox
			$("#cbbFarmName").empty();
			$("#cbbFarmName").append(farmStr);
			$("#cbbFarmName").prop("disabled", true);
			// display error message
			jInfo(SEARCH_RESULT_NO_DATA_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		}
	}
	// Load Farm name combobox data
	function loadFarmNameCombobox() {

		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "getFarmName",
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				// create option string
				var farmStr = "";
				if (returnedJsonData != "") {
					farmData = returnedJsonData;
					var farmStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
					for (var i = 0; i < returnedJsonData.length; i++) {
						farmStr += "<option value='" + returnedJsonData[i].farmId + "'><span>" + returnedJsonData[i].farmName + "</span></option>";
					}
					// set options to combobox
					$("#cbbFarmName").empty();
					$("#cbbFarmName").append(farmStr);
					$("#cbbFarmName").prop("disabled", false);
				} else {
					// create option string
					var farmStr = "<option value='" + STATUS_NO_SELECT + "'></option>";
					// set options to combobox
					$("#cbbFarmName").empty();
					$("#cbbFarmName").append(farmStr);
					$("#cbbFarmName").prop("disabled", true);
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
	
	//search all
	$("#btnSearch").trigger("click");

	// Draw farm data table based on search conditions
	function drawFarmTable() {
		// Variables definition
		var address = "";
		var climate = "";
		var soil = "";
		var openDate = "";
		var timeFrom = "";
		var timeTo = "";
		var sizePlan = 0;
		// Get search conditions
		searchConditions.fromRow = from;
		// Make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: "searchFarmData",
			contentType: "application/json",
			data: JSON.stringify(searchConditions),
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
						$(".tbl-farm-table").find("tbody").remove();
						// Create table starts
						var tableStringArray = [];
						// Add tbody open tag
						tableStringArray.push("<tbody>");
						for (var i = 0; i < returnedJsonData.length; i++) {
							
							address = returnedJsonData[i].address;
							climate = returnedJsonData[i].climate;
							soil = returnedJsonData[i].soil;
							openDate = convertDataTo(returnedJsonData[i].openDate);
							timeFrom = convertDataTo(returnedJsonData[i].timeFrom);
							timeTo = convertDataTo(returnedJsonData[i].timeTo);
							sizePlan = returnedJsonData[i].sizeOfPlan;
							numberical_order++;
							// Row open tag
							tableStringArray.push("<tr id='row" + i + "'>");
							// No.
							tableStringArray.push("<td class='align-center'>"+numberical_order+"</td>");
							// Farm name
							tableStringArray.push("<td class='overflow-ellipsis'><input id='hddFarmName' type='hidden' value='" + returnedJsonData[i].farmId + "'><span class='goBnn0007'>" + returnedJsonData[i].farmName + "</span></td>");
							// Number of Lines
							tableStringArray.push("<td class='align-right'>" + returnedJsonData[i].lineOfPlan + "</td>");
							// Number of Columns
							tableStringArray.push("<td class='align-right'>" + returnedJsonData[i].columnOfPlan + "</td>");
							// Address
							tableStringArray.push("<td class='overflow-ellipsis'><span>" + ((address != null) ? address : "") + "</span></td>");
							// Climate
							tableStringArray.push("<td class='overflow-ellipsis'><span>" + ((climate != null) ? climate : "") + "</span></td>");
							// Soil
							tableStringArray.push("<td class='overflow-ellipsis'><span>" + ((soil != null) ? soil : "") + "</span></td>");
							// Open Date
							tableStringArray.push("<td class='align-right'>" + ((openDate != null) ? openDate : "") + "</td>");
							// Time Form
							tableStringArray.push("<td class='align-right'>" + ((timeFrom != null) ? timeFrom : "") + "</td>");
							// Time To
							tableStringArray.push("<td class='align-right'>" + ((timeTo != null) ? timeTo : "") + "</td>");
							// Size plan
							var sizePlanMark = new String(sizePlan);
							while(sizePlanMark != (sizePlanMark = sizePlanMark.replace(/^(-?\d+)(\d{3})/, "$1,$2"))); //add ","
							tableStringArray.push("<td class='align-right'>" + ((sizePlan != null) ? sizePlanMark : "") + "</td>");
							// Update icon
							tableStringArray.push("<td><img class='edit cursor-pointer' name='" + i + "' src='" + rootPath + "/resources/img/icon_edit.png?date=" + icon_editDate + "'></td>");
							// Delete icon
							tableStringArray.push("<td><img class='delete cursor-pointer' lastUpdateDate = '"+ returnedJsonData[i].lastUpdateDate +"' name='" + i + "' src='" + rootPath + "/resources/img/icon_del.png?date=" + icon_delDate + "'></td>");
							// Row close tag
							tableStringArray.push("</tr>");
							if(i == (returnedJsonData.length -1)){
								checkMaxCount = true;
							}
						}
						// Add tbody close tag
						tableStringArray.push("</tbody>");
						// Append all created string to table
						$(".tbl-farm-table").append(tableStringArray.join(''));
						// Show search result
						$(".cont-box").removeClass("display-none");
						// Fix table height to fit page
						var tableHeight = calculateTableHeight();
						$("#divBody").height(tableHeight);
						// Fix table header and body when scrolling only the table body
						fixTable();
						// Update total data count UI
						setDataCounts();
						setPagerStatus();
						// scroll to top of table
						$("#divBody").scrollTop(0).scrollLeft(0);
					}
				} else {
					// Display error message
					jWarning(SEARCH_RESULT_NO_DATA_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
					// Update pager
					$("#lblCurrentPage").text("0");
					$("#lblMaxPage").text("0");
					// Clear table
					$(".tbl-farm-table").find("tbody").remove();
					fixTable();
					totalResultCount = 0;
					// Update total data count UI
					setDataCounts();
					setPagerStatus();
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

	//
	var myVar = setInterval(function(){
		if(checkMaxCount){
			checkMaxCount = false;
			clearInterval(myVar);
		}
		fixTable();
	}, 100);

	// Fix pop up height
	function fixUserPoupHeight() {
		var titleHeight = $(".farminfo-popup #popupTitle").height();
		var bodyHeight = $(".farminfo-popup .popup-content").height();
		return titleHeight + bodyHeight;
	}

	// Search farm conditions object creation process
	function createFarmSearchConditions() {
		// variables definition
		faId = $("#cbbFarmName").find("option:selected").val();
		return {
			farmId: faId,
			fromRow: from,
			itemCount: ITEM_IN_ONE_PAGE
		};
	}

	// Get Single conditions object creation process
	function createGetSingleConditions(farmId) {
		return {
			farmId: farmId
		};
	}

	// Farm data object creation process, to use in Inserting farm
	function createFarmDataObject() {
		return {
			farmName: $.trim($("#txtFarmNamePopup").val()),
			usersId: $.trim($("#txtUsersIdPopup").val()),
			usersIdNew: $.trim($("#txtNewUsersIdPopup").val()),
			farmLocation: $.trim($("#txtAddressPopup").val()),
			timeFrom: convertDataFrom($("#txtTimeFromPopup").val()),
			timeTo: convertDataFrom($("#txtTimeToPopup").val()),
			openDate: convertDataFrom($("#txtOpenDatePopup").val()),
			sizeOfPlan: $.trim($("#txtSizeOfPlanPopup").val()),
			numberOfLines: $.trim($("#txtLineOfPlanPopup").val()),
			numberOfColumns: $.trim($("#txtColumnOfPlanPopup").val()),
			climate: $.trim($("#txtClimatePopup").val()),
			soil: $.trim($("#txtSoilPopup").val()),
			emailAddress: $.trim($("#txtEmailPopup").val()),
			phone: $.trim($("#txtPhonePopup").val()),
			fax: $.trim($("#txtFaxPopup").val()),
			note: $.trim($("#txtNotePopup").val()),
			deleteFlag: DELETE_FLAG_OFF
		};
	}

	// Farm data object creation process, to use in Updating farm
	function updateFarmDataObject() {
		return {
			farmId: $.trim($("#hddFarmIdPopup").val()),
			farmName: $.trim($("#txtFarmNamePopup").val()),
			usersId: $.trim($("#txtUsersIdPopup").val()),
			usersIdNew: $.trim($("#txtNewUsersIdPopup").val()),
			farmLocation: $.trim($("#txtAddressPopup").val()),
			timeFrom: convertDataFrom($("#txtTimeFromPopup").val()),
			timeTo: convertDataFrom($("#txtTimeToPopup").val()),
			openDate: convertDataFrom($("#txtOpenDatePopup").val()),
			sizeOfPlan: $.trim($("#txtSizeOfPlanPopup").val()),
			numberOfLines: $.trim($("#txtLineOfPlanPopup").val()),
			numberOfColumns: $.trim($("#txtColumnOfPlanPopup").val()),
			soil: $.trim($("#txtSoilPopup").val()),
			note: $.trim($("#txtNotePopup").val()),
			emailAddress: $.trim($("#txtEmailPopup").val()),
			phone: $.trim($("#txtPhonePopup").val()),
			fax: $.trim($("#txtFaxPopup").val()),
			climate: $.trim($("#txtClimatePopup").val()),
			deleteFlag: DELETE_FLAG_OFF,
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
			var drawResult = drawFarmTable();
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
	}

	// Clear all current text of pop up controls
	function clearPopupControl() {

		// farm id
		$("#hddFarmIdPopup").val("")
		// farm name
		$("#txtFarmNamePopup").val("");
		// user id
		$("#txtUsersIdPopup").val("");
		// new user id
		$("#txtNewUsersIdPopup").val("");
		// user name
		$("#txtUsersNamePopup").val("");
		// address
		$("#txtAddressPopup").val("");
		// open date
		$("#txtOpenDatePopup").val("");
		// time from
		$("#txtTimeFromPopup").val("");
		// time to
		$("#txtTimeToPopup").val("");
		// size of plan
		$("#txtSizeOfPlanPopup").val("");
		// line of plan
		$("#txtLineOfPlanPopup").val("");
		// column of plan
		$("#txtColumnOfPlanPopup").val("");
		// e-mail
		$("#txtEmailPopup").val("");
		// phone
		$("#txtPhonePopup").val("");
		// fax
		$("#txtFaxPopup").val("");
		// climate
		$("#txtClimatePopup").val("");
		// soil
		$("#txtSoilPopup").val("");
		// note
		$("#txtNotePopup").val("");
		// status
		$("#chbDeletePopup").prop("checked", false);
	}

	// Change state of controls in pop up based on mode
	function setPopupControlState(mode) {
		if (mode == MODE_NEW) {
			$("#update").addClass("display-none");
			$("#addNew").removeClass("display-none");
			// reset numberical Order
			numberical_order = 0;
			// set current mode
			currentMode = MODE_NEW;

			// farm name
			$("#txtFarmNamePopup").prop("disabled", false);
			// user name
			$("#txtUsersNamePopup").prop("disabled", false);
			// address
			$("#txtAddressPopup").prop("disabled", false);
			// open date
			$("#txtOpenDatePopup").prop("disabled", false);
			// time from
			$("#txtTimeFromPopup").prop("disabled", false);
			// time to
			$("#txtTimeToPopup").prop("disabled", false);
			// size of plan
			$("#txtSizeOfPlanPopup").prop("disabled", false);
			// line of plan
			$("#txtLineOfPlanPopup").prop("disabled", false);
			// column of plan
			$("#txtColumnOfPlanPopup").prop("disabled", false);
			// e-mail
			$("#txtEmailPopup").prop("disabled", false);
			// phone
			$("#txtPhonePopup").prop("disabled", false);
			// fax
			$("#txtFaxPopup").prop("disabled", false);
			// climate
			$("#txtClimatePopup").prop("disabled", false);
			// soil
			$("#txtSoilPopup").prop("disabled", false);
			// note
			$("#txtNotePopup").prop("disabled", false);
			// status
			$("#chbDeletePopup").prop("disabled", false);

			// calendar
			$(".ui-datepicker-trigger").show();
			//search user botton
			$("#loadUserPopup").show();
			//search clear
			$("#btnClear").show();
			// register button
			$("#btnRegisterFarmPopup").show();

		} else if (mode == MODE_EDIT) {
			$("#addNew").addClass("display-none");
			$("#update").removeClass("display-none");
			// set current mode
			currentMode = MODE_EDIT;

			// farm name
			$("#txtFarmNamePopup").prop("disabled", false);
			// user name
			$("#txtUsersNamePopup").prop("disabled", false);
			// address
			$("#txtAddressPopup").prop("disabled", false);
			// open date
			$("#txtOpenDatePopup").prop("disabled", false);
			// time from
			$("#txtTimeFromPopup").prop("disabled", false);
			// time to
			$("#txtTimeToPopup").prop("disabled", false);
			// size of plan
			$("#txtSizeOfPlanPopup").prop("disabled", false);
			// line of plan
			$("#txtLineOfPlanPopup").prop("disabled", true);
			// column of plan
			$("#txtColumnOfPlanPopup").prop("disabled", true);
			// e-mail
			$("#txtEmailPopup").prop("disabled", false);
			// phone
			$("#txtPhonePopup").prop("disabled", false);
			// fax
			$("#txtFaxPopup").prop("disabled", false);
			// climate
			$("#txtClimatePopup").prop("disabled", false);
			// soil
			$("#txtSoilPopup").prop("disabled", false);
			// note
			$("#txtNotePopup").prop("disabled", false);
			// status
			$("#chbDeletePopup").prop("disabled", false);

			// calendar
			$(".ui-datepicker-trigger").show();
			//search user botton
			$("#loadUserPopup").show();
			//search clear
			$("#btnClear").show();
			// register button
			$("#btnRegisterFarmPopup").show();

		}
	}

	// Check input: blank fields
	function checkInputBlankFields() {
		if ($.trim($("#txtFarmNamePopup").val()) == "" ){
			return farmNamePopupBlank;
		} else if ($.trim($("#txtAddressPopup").val()) == ""){
			return addressPopupBlank;
		} else if($("#txtTimeFromPopup").val() == "" || $("#txtTimeToPopup").val() == ""){
			return timePopupBlank;
		} else if($("#txtOpenDatePopup").val() == ""){
			return openDatePopupBlank;
		} else if($("#txtSizeOfPlanPopup").val() == ""){
			return sizeOfPlanPopupBlank;
		} else if($("#txtLineOfPlanPopup").val() == ""){
			return lineOfPlanPopupBlank;
		} else if($("#txtColumnOfPlanPopup").val() == ""){
			return columnOfPlanPopupBlank;
		} else if($.trim($("#txtClimatePopup").val()) == ""){
			return climatePopupBlank;
		} else if($.trim($("#txtSoilPopup").val()) == ""){
			return soilPopupBlank;
		} else if($.trim($("#txtEmailPopup").val()) == ""){
			return emailPopupBlank;
		} else if($("#txtPhonePopup").val() == ""){
			return phonePopupBlank;
		} else if($("#txtFaxPopup").val() == ""){
			return faxPopupBlank;
		} else {
			return "";
		}
	}
	
	// Check input: number fields
	function checkInputNumberFields(){
		var errorCount = 0;
		if (isNaN($("#txtSizeOfPlanPopup").val())) {
			return sizeOfPlanPopupBlank;
		} else if (isNaN($("#txtLineOfPlanPopup").val())) {
			return lineOfPlanPopupBlank;
		} else if (isNaN($("#txtColumnOfPlanPopup").val())) {
			return columnOfPlanPopupBlank;
		} else {
			return ""
		}
		return errorStr;
	}

	// Input: number's format (Number)
	function inputNumberFormat() {
		// Allow: backspace, delete, tab, escape, enter
		if ($.inArray(event.keyCode, [ 46, 8, 9, 27, 13 ]) !== -1 ||
				// Allow: Ctrl+A, Command+A
				(event.keyCode == 65 && (event.ctrlKey === true || event.metaKey === true)) ||
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
	}
	
	// numberphone, fax
	$(document).on('keypress', '#txtPhonePopup, #txtFaxPopup', function(e) {
		  if (e.charCode != 0 && e.charCode != 43 && e.charCode != 45 && e.charCode != 40 && e.charCode != 41 && (e.charCode < 48 || e.charCode > 57)){
			  e.preventDefault();
		  }
	});
	
	// Check input: Phone, Fax fields
	function checkPhoneFaxFields(){
		var errorCount = 0;
		if (isNaN($("#txtPhonePopup").val().replace(/\+|\-|\(|\)/gi, ""))) {
			return phonePopupBlank;
		} else if (isNaN($("#txtFaxPopup").val().replace(/\+|\-|\(|\)/gi, ""))) {
			return faxPopupBlank;
		} else {
			return ""
		}
		return errorStr;
	}
	
	// check email
	function validateEmail(email) {
		  var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		  return re.test(email);
	}
	
	//check start date < end date
	function checkInputDateUpdate(){
		var checkBoolean = true;
		if(convertDataFrom($("#txtTimeFromPopup").val()) > convertDataFrom($("#txtTimeToPopup").val())){
			checkBoolean = false;
		}
		return checkBoolean;
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

})