/**
 * @author HiepTL
 */
$(document).ready(function() {
	//--------------- Constants definition ---------------
	// number of items in one page in table
	var ITEM_IN_ONE_PAGE = ITEM_IN_ONE_PAGE_0087;
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
	// variable to store selected user id to show in popup
	var usersIdPopup = "";
	// variable to store current selected mode
	var currentMode = "";
	// variable to check whether user edits password in EDIT Mode
	var isPasswordChanged = false;
	var inputFilter = [];
	// display current date
	$("#txtCurrentDate").text(getCurrentDate() + " (" + DAY_OF_WEEK + ") ");
	var userNameEdit = "";
	var userIdEdit = "";
	var permissionNameEdit = "";
	var permissionIdEdit = "";
	var permissionIdPopup = 0;
	var lastUpDate = "";
	// 全選択】を押下する
	var isSelectAll = false;
	var isPermisionFarmManager = false; 
	var farmArray = [];
	var areaArray = [];
	// nameFarm selected
	var nameFarmSelected = "";
	var isDirty = false ;
	// search conditions 
	var searchConditions = null;
	// Search button event process
	$("#btnSearch").bind("click", function() {
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
	});
	// initialize combobox data
	initAffiliationComboboxData(false);
	
	// Reset variables process
	function resetVariables() {
		totalResultCount = 0;
		currentPage = 1;
		from = 0;
		isSelectAll = false;
		isPermisionFarmManager = false; 
		farmArray = [];
		areaArray = [];
	}
	
	// Reset variables in popup
	function resetVariablesInPopup() {
		isSelectAll = false;
		isPermisionFarmManager = false; 
		farmArray = [];
		areaArray = [];
		isDirty = false ;
	}
	
	
	// Reset search conditions process
	function resetSearchConditions() {
		$("#txtUsersName").val("");
		$("#cbbAffilName").val("");
	}
	
	// Get data for combobox process
	function initAffiliationComboboxData(isPopupMode) {
		// process
		if (affiliationData != "") {
			var optionStr = "<option value=''></option>";
			for (var i = 0; i < affiliationData.length; i++) {
				optionStr += "<option value='" + affiliationData[i].authorizationTypeId + "'>" + affiliationData[i].authorizationTypeName + "</option>";
			}
			// check whether to fill data in main screen or popup screen
			if (!isPopupMode) {
				$("#cbbAffilName").empty()
				$("#cbbAffilName").append(optionStr);
			} else {
				var optionStrPopup = "<option value=''></option>";
				for (var i = 0; i < affiliationData.length; i++) {
					optionStrPopup += "<option value='" + affiliationData[i].authorizationTypeId + "'>" + affiliationData[i].authorizationTypeName + "</option>";
				}
				$("#cbbAffilNamePopup").empty()
				$("#cbbAffilNamePopup").append(optionStrPopup);
			}
		}
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
	
	// Cancel button in popup click event process
	$("#btnCancelPopup").bind("click", function() {
		resetVariablesInPopup();
		// hide user info popup
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

	// Search conditions object creation process
	function createSearchConditions() {
		// get role string
		var searchOption = "";
		if ($("#partial").is(":checked")) {
			searchOption = "partial";
		}
		if ($("#forward").is(":checked")) {
			searchOption = "forward";
		}
		if ($("#backward").is(":checked")) {
			searchOption = "backward";
		}
		if ($("#all").is(":checked")) {
			searchOption = "all";
		}
		var authorityId = $("#cbbAffilName").find("option:selected").val();
		if (authorityId == null){
			authorityId = -2;
		}
		return {
			usersName: $.trim($("#txtUsersName").val()).replace(/\\/g, "\\\\").replace(/%/g, "\\%"),
			authorityId: authorityId == "" ? STATUS_NO_SELECT : authorityId,
			searchOption: searchOption,
			fromRow: from,
			itemCount: ITEM_IN_ONE_PAGE
		};
	}
	
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
	
	//search all
	$("#btnSearch").trigger("click");
	
	// Draw user data table based on search conditions
	function drawTable() {
		isDirty = false;
		// variables definition
		var returnValue = "";
		var dataObject = null;
		// get search conditions
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
						$("#tblBody").find("tbody").remove();
						// create table starts
						var tableStringArray = [];
						// add tbody open tag
						tableStringArray.push("<tbody>");
						for (var i = 0; i < returnedJsonData.length; i++) {
							numberical_order++;
							// row open tag
							tableStringArray.push("<tr id='row" + i + "'>");
							tableStringArray.push("<td class='align-center'>"+numberical_order+"</td>");
							// user name
							tableStringArray.push("<td class='overflow-ellipsis'><input id='userId' type='hidden' value='" + returnedJsonData[i].userId + "'>" + returnedJsonData[i].userName + "</td>");
							// typeName
							if (returnedJsonData[i].typeName == "null" || returnedJsonData[i].typeName == null ){
								returnedJsonData[i].typeName  = "";
							}
							tableStringArray.push("<td><input id='typeId' type='hidden' value='" + returnedJsonData[i].typeId + "'>" + returnedJsonData[i].typeName + "</td>");
							// update icon
							tableStringArray.push("<td><img class='edit cursor-pointer' name='" + i + "' src='" + rootPath + "/resources/img/icon_edit.png?date=" + icon_editDate + "'></td>");
							// delete icon
							tableStringArray.push("<td><img class='delete cursor-pointer' name='" + i + "' src='" + rootPath + "/resources/img/icon_del.png?date=" + icon_delDate + "'></td>");
							//Hidden farm id
							tableStringArray.push("<td style='display:none;'><input id='farmIDList' type='hidden' value='" + returnedJsonData[i].farmIdList +"'></td>");
							//Hidden Area Id
							tableStringArray.push("<td style='display:none;'><input id='farmIDList' type='hidden' value='" + returnedJsonData[i].areaIdList +"'></td>");
							//Hidden last update date
							tableStringArray.push("<td style='display:none;'><input id='txtLastUpdateDate' type='hidden' value='" + (returnedJsonData[i].lastUpdateDate == null ? "" : returnedJsonData[i].lastUpdateDate) +"'></td>");
							// row close tag
							tableStringArray.push("</tr>");
							if(i == (returnedJsonData.length -1)){
								checkMaxCount = true;
							}
						}
						// add tbody close tag
						tableStringArray.push("</tbody>");
						// append all created string to table
						$("#tblBody").append(tableStringArray.join(''));
						// show search result
						$(".cont-box").removeClass("display-none");
						// fix table height to fit page
						var tableHeight = calculateTableHeight();
						$("#divBody").height(tableHeight);
						// fix table header and body when scrolling only the table body
						fixTable();
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
	// Edit process info click event process
	$(document).on("click", ".edit", function() {
		$('#userNameEdit').attr("title", "");
		// make Ajax call to server to delete data
		// get id of selected 
		selectedRowIndex = $(this).attr("name");
		userId = $("#row" + selectedRowIndex).find("td").eq(1).find("input").val();
		userIdEdit = userId;
		permissionId = $("#row" + selectedRowIndex).find("td").eq(2).find("input").val();
		permissionIdEdit = permissionId;
		permissionIdPopup = permissionId;
		userNameEdit = $("#row" + selectedRowIndex).find("td").eq(1).text();
		$("#userNameEdit").text(userNameEdit);
		permissionNameEdit = $("#row" + selectedRowIndex).find("td").eq(2).find("input").val();
		lastUpDate = $("#row" + selectedRowIndex).find("#txtLastUpdateDate").val();
		var listFarmID = listFarmAreaID($("#row" + selectedRowIndex).find("td").eq(5).find("input").val());
		var listAreaID = listFarmAreaID($("#row" + selectedRowIndex).find("td").eq(6).find("input").val());
		if (permissionIdEdit == 3){
			isPermisionFarmManager = true ;
		}else{
			isPermisionFarmManager = false;
		}
		var divShowHide = document.getElementById("showhide");
		$("#areaShowHide").show();
		if (permissionIdPopup == 1 || permissionIdPopup == 2 || permissionIdPopup == "null" ){
			divShowHide.style.visibility = 'hidden';
		}else{
			divShowHide.style.visibility = 'visible';
			if (permissionIdPopup == 3){
				$("#areaShowHide").hide();
			}
		}
		$.ajax({
			url: "getDataForAffiliation",
			type: "POST",
			async: false,
			success: function(returnedJsonData) {
				if (checkSessionTimeout(returnedJsonData) == 1) return;
				
				if (returnedJsonData != "") {
					var numberFarm = 0 ;
					var numberArea = 0 ;
					// clear table
					$("#tblBodyPopup").find("tbody").remove();
					// create table starts
					var tableStringArray = [];
					var checkFarm = false;
					// add tbody open tag
					tableStringArray.push("<tbody>");
					farmArray = [];
					areaArray = [];
					for (var k = 0; k < returnedJsonData[0].arrDataFarm.length; k++) {
						numberFarm++;
						checkFarm = false;
						// row open tag
						tableStringArray.push("<tr>");
						tableStringArray.push("<td>"+numberFarm+"</td>");
						// farmName
						if (permissionNameEdit == 3){
							tableStringArray.push("<td class = 'farmCol overflow-ellipsis' ><input id='farmId' type='hidden' value='" + returnedJsonData[0].arrDataFarm[k].farmId + "'><a>" + returnedJsonData[0].arrDataFarm[k].farmName + "</td>");
						} else{
							tableStringArray.push("<td class = 'farmCol overflow-ellipsis' ><input id='farmId' type='hidden' value='" + returnedJsonData[0].arrDataFarm[k].farmId + "'><a>" + returnedJsonData[0].arrDataFarm[k].farmName + "</td>");
						}
						for (var i = 0; i < listFarmID.length; i++){
							if(listFarmID[i] == returnedJsonData[0].arrDataFarm[k].farmId){
								checkFarm = true;
								if (!contains(farmArray, returnedJsonData[0].arrDataFarm[k].farmId)){
									farmArray.push(returnedJsonData[0].arrDataFarm[k].farmId);
								}
								break;
							}
						}
						if(checkFarm){
							tableStringArray.push("<td class ='td_rd option' style= text-align:center;>" + "<input type= radio name=offline_payment_method value=Cheque checked> " + "</td>");
						} else {
							tableStringArray.push("<td class ='td_rd option' style= text-align:center;>" + "<input type= radio name=offline_payment_method value=Cheque> " + "</td>");
						}
					}
					// add tbody close tag
					tableStringArray.push("</tbody>");
					$("#tblBodyPopup").append(tableStringArray.join(''));
					// clear table
					$("#tblBodyRightPopup").find("tbody").remove();
					// create table starts
					var tableStringArrayArea = [];
					var checkArea = false;
					// add tbody open tag
					tableStringArrayArea.push("<tbody>");
					for (var i = 0; i < returnedJsonData.length; i++) {
						if (returnedJsonData[0].haveData != ""){
							// Set the real search total count
							totalResultCount = returnedJsonData[0].haveData;
							if (parseInt(totalResultCount) == -1) {
								// OutOfMemoryException, display error message
								jWarning(SEARCH_RESULT_OUT_OF_MEMORY_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else {
								checkArea = false;
								numberArea++;
								tableStringArrayArea.push("<tr>");
								tableStringArrayArea.push("<td>"+numberArea+"</td>");
								// process Id
								tableStringArrayArea.push("<td class = 'farmCol overflow-ellipsis' ><input id='farmId' type='hidden' value='" + returnedJsonData[i].farmId+ "'>" + returnedJsonData[i].farmName + "</td>");
								tableStringArrayArea.push("<td class = 'areaCol overflow-ellipsis' ><input id='areaId' type='hidden' value='" + returnedJsonData[i].areaId + "'>" + returnedJsonData[i].areaName + "</td>");
								if(returnedJsonData[i].usersName != null){
									tableStringArrayArea.push("<td class ='overflow-ellipsis'>" + returnedJsonData[i].usersName + "</td>");
								} else {
									tableStringArrayArea.push("<td class ='overflow-ellipsis'></td>");
								}
								// processName
								for (var a = 0; a < listAreaID.length; a++){
									if(listAreaID[a] == returnedJsonData[i].areaId){
										checkArea = true;
										if (!contains(areaArray, returnedJsonData[i].areaId)){
											areaArray.push(returnedJsonData[i].areaId);
										}
										break;
									}
								}
								tableStringArrayArea.push("<td class = 'optionArea' style= text-align:center;>" + "<input type = checkbox " 
										+ (checkArea ? "checked" : "" ) + "> </td>");
							}
						}
					}
					// add tbody close tag
					tableStringArrayArea.push("</tbody>");
					$("#tblBodyRightPopup").append(tableStringArrayArea.join(''));					
					// scroll to top of table
					$("#divBodyRightPopup").scrollTop(0).scrollLeft(0);
					$("#divBodyPopup").scrollTop(0).scrollLeft(0);
					initAffiliationComboboxData(true);
					$('#cbbAffilNamePopup').val(permissionNameEdit);
					showPopup($("#popupWrapper"));					
					
					var row = $('#tblBodyPopup .option :checked');
					var idFarm = row.parent().parent().find('.farmCol #farmId').val();
					nameFarmSelected = idFarm;
					inputFilter = [];
					inputFilter.push(idFarm);
					filterAreaByFarmId();
					// fix table header and body when scrolling only the table body
					fixTableRight();
					isDirty = false;
				} else {
					// Display error message
					jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
				}
			},
			error: function(e) {
				// display error message
				jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	});
	
	// Delete process info click event process
	$(document).on("click", ".delete", function() {
		// display confirmation message
		var curContext = $(this) ;
		jQuestion_confirm(DELETE_CONFIRM_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
			if (val) {
				// get row index
				selectedRowIndex = curContext.attr("name");
				// get id of selected 
				userId = $("#row" + selectedRowIndex).find("td").eq(1).find("input").val();
				permissionId = $("#row" + selectedRowIndex).find("td").eq(2).find("input").val();
				lastUpDate = $("#row" + selectedRowIndex).find("#txtLastUpdateDate").val();
				ivbMAffiliationKey  = {userId: userId,
									authorizationTypeId: permissionId,
									lastUpdateDate: lastUpDate};
				// make Ajax call to server to delete data
				$.ajax({
					url: "deleteData",
					contentType: "application/json",
					data: JSON.stringify(ivbMAffiliationKey),
					type: "POST",
					async: false,
					success: function(returnedJsonData) {
						if (checkSessionTimeout(returnedJsonData) == 1) return;
						if (returnedJsonData != "") {
							// check returned value from server
							if (returnedJsonData == DELETE_RESULT_SUCCESSFUL) {
								// search data again
								// reset variables
								numberical_order = 0;
								resetVariables();								
								resetSearchConditions();
								searchConditions = createSearchConditions();
								// draw table
								drawResult = drawTable();
								if (drawResult != "") {
									// update total data count UI
									setDataCounts();
								}
								// display message
								jInfo(DELETE_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION) {
								// failed
								// display message
								jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
							} else if (returnedJsonData == DELETE_RESULT_FAILED_MANAGER) {
								// failed
								// display message
								jWarning(DELETE_RESULT_FAILED_MANAGER_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
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
	// Cancel button in popup click event process
	$("#btn-cancel").bind("click", function() {
		isSelectAll = false; 
		// Show/Hide button Select All
		$("#unSelectAll").addClass("display-none");
		$("#selectAll").removeClass("display-none");
		resetVariablesInPopup();
		hidePopup($("#popupWrapper"));
	});
	
	// 入力されたデータを確定して呼び出し元へ遷移。
	$("#btn-submit").bind("click", function() {
		if (checkInputBeforeInsert() != "") {
			// blank field(s)
			// display message
			jWarning(VALIDATE_BLANK_FIELDS_MESSAGE.replace('$1', checkInputBeforeInsert()), DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			var dataObjectInsert = null;
			// get data for insert
			dataObjectInsert = createDataForInsert();
			$.ajax({
				url: "insertDataForAffiliation",
				contentType: "application/json",
				data : JSON.stringify(dataObjectInsert),
				type: "POST",
				success: function(returnedJsonData) {
					if (checkSessionTimeout(returnedJsonData) == 1) return;
					if (returnedJsonData != "") {
						// check returned value from server
						if (returnedJsonData == INSERT_RESULT_SUCCESSFUL) {
							// search data again
							// reset variables
							resetVariables();
							resetSearchConditions();
							searchConditions = createSearchConditions();
							numberical_order = 0;
							// draw table
							drawResult = drawTable();
							if (drawResult != "") {
								// update total data count UI
								setDataCounts();
							}
							// hide popup
							hidePopup($("#popupWrapper"));
							// display message
							jInfo(INSERT_RESULT_SUCCESSFUL_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
						} else if (returnedJsonData == INSERT_RESULT_FAILED_CULTIVATION_AFFILIATION) {
							// failed
							// display message
							jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
						} else if (returnedJsonData == INSERT_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE) {
							// failed
							// display message
							jWarning(TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
						} else if (returnedJsonData == INSERT_RESULT_FAILED_MANAGER) {
							// failed
							// display message
							jWarning(INSERT_RESULT_FAILED_MANAGER_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
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
	});
	
	$("#selectAll").bind("click", function() {
		var $this = $(this);
		$(".tbl-search-affiliation-detail-area-result tr input").prop('checked', true);
		isSelectAll = true; 
		// Show/Hide button Select All
		$("#selectAll").addClass("display-none");
		$("#unSelectAll").removeClass("display-none");
		var count = 0;
		areaArray = [];
		$('.optionArea input:checkbox').each(function (event) {
			var row = $(this);
			var idArea = row.parent().parent().find('.areaCol').find('#areaId').val();
			var table, tr;
			table = document.getElementById("tblBodyRightPopup");
			tr = table.getElementsByTagName("tr");
			if (tr[count].style.display != "none"){
				areaArray.push(idArea);
			}
			count++;
		});
		isDirty = true ;
	});
	
	$("#unSelectAll").bind("click", function() {
		unSelectAllAreaData();
	});
	
	
	// check item contain in array
	function contains(arr, obj) {
	    var i = arr.length;
	    while (i--) {
	       if (arr[i] == obj) {
	           return true;
	       }
	    }
	    return false;
	}
	
	$('#tblBodyPopup').on('click', '.option', function (event) {
		var row = $(this);
		var idFarm = row.parent().find('.farmCol #farmId').val();
		if (event.target.type == 'radio'){
			farmArray = [];
			farmArray.push(idFarm);
			if (nameFarmSelected != idFarm || isSelectAll){
				unSelectAllAreaData();
			}
			nameFarmSelected = idFarm;
			inputFilter = [];
			inputFilter.push(idFarm);
			filterAreaByFarmId();
			isDirty = true ;
			fixTableRightChangeOption();
		} 
		
	});

	$(document).on('click','.optionArea',function(event) {
		var row = $(this);
		var idArea = row.parent().find('.areaCol #areaId').val();
		if (event.target.type == 'checkbox'){
	    	if (!contains(areaArray, idArea)){
	    		areaArray.push(idArea);
	    	} else {
	    		removeItem(areaArray,idArea);
	    	}
		}
		isDirty = true ;
    });

	// remove item in array
	function removeItem(arr) {
	    var what, a = arguments, L = a.length, ax;
	    while (L > 1 && arr.length) {
	        what = a[--L];
	        while ((ax= arr.indexOf(what)) !== -1) {
	            arr.splice(ax, 1);
	        }
	    }
	    return arr;
	}
	
	//visible all data Area by Id
	function filterAreaByFarmId() {
		isDirty = true ;
		var table, tr, td, i;
		table = document.getElementById("tblBodyRightPopup");
		tr = table.getElementsByTagName("tr");
		var count = 1;
		for (i = 0; i < tr.length; i++) {
		    td = tr[i].getElementsByTagName("td")[1];
		    if (td) {
		      if (contains(inputFilter,$(td).find("input").val())|| inputFilter.length == 0) {
		    	  tr[i].style.display = "";
				  $(tr[i]).find('td:eq(0)').html(count++);
		      } else {
				  tr[i].style.display = "none";
			  }
		    }       
		}
	}
	
	//visible all data Area
	function visibleAllArea() {
		var table, tr, td, i;
		table = document.getElementById("tblBodyRightPopup");
		tr = table.getElementsByTagName("tr");
		var count = 1;
		for (i = 0; i < tr.length; i++) {
		    td = tr[i].getElementsByTagName("td")[1];
		    if (td) {
		    	  tr[i].style.display = "";
		    	  tr[i].style.display = "";
				  $(tr[i]).find('td:eq(0)').html(count++);
		    }       
		}
	}
	
	// change event permision
	$("#cbbAffilNamePopup").change(function () {
		// display confirmation message
		var curContext = this;
		if (isDirty == true){
			jQuestion_confirm(DELETE_CONFIRM_MESSAGE_WHEN_DIRTY, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
				if (val) {
					changePermision(curContext);
				}else{
					$('#cbbAffilNamePopup').val(permissionNameEdit);
				}
				fixTableRightChangeOption();
			});
		} else {
			changePermision(this);
		}
		fixTableRightChangeOption();
    });
	
	function changePermision(context){
		isDirty = false;
		permissionIdPopup = $(context).val();
		var divShowHide = document.getElementById("showhide");
		permissionNameEdit = $('#cbbAffilNamePopup').val();
		visibleAllArea();
		unSelectAllFarmData();
		unSelectAllAreaData();
		// 経営者、システム管理者が選択されるとき、農園一覧及びエリア一覧をクリアする。
		if ($(context).val() == 1 || $(context).val() == 2 || $(context).val() ==""){
			  // hide
			divShowHide.style.visibility = 'hidden';
			  // OR
			  return ;
		}else{
			divShowHide.style.visibility  = 'visible';
			// hide checkbox, visible radio button
			if ($(context).val() != 3){
				 $("#areaShowHide").show();
			} else {
				$("#areaShowHide").hide();
			}
			isPermisionFarmManager = false ;
			if ($(context).val() == 3){
				isPermisionFarmManager = true ;
				 $("#tblBodyPopup .farmCol").each(function() {
				     $(context).find("a").removeAttr("href");
				 });
			} else {
				 $("#tblBodyPopup .farmCol").each(function() {
				    $(context).find("a").attr("href", "#");
				 });
			}
			
			// scroll to top of table
			$("#divBodyRightPopup").scrollTop(0).scrollLeft(0);
			$("#divBodyPopup").scrollTop(0).scrollLeft(0);
		}
		
	}
	
	// unselect area data
	function unSelectAllAreaData (){
		$(".tbl-search-affiliation-detail-area-result tr input").prop('checked', false);
		isSelectAll = false; 
		// Show/Hide button Select All
		$("#unSelectAll").addClass("display-none");
		$("#selectAll").removeClass("display-none");
		areaArray = [];
	}
	// unselect farm data
	function unSelectAllFarmData (){
		$(".tbl-search-affiliation-detail-plant-result tr input").prop('checked', false);
		farmArray = [];
	}
	
	//Fix table header and body when scrolling only the table body
	function fixTableRightChangeOption() {
		// get height of table body and table container
		var tblBodyHeight = $("#tblBodyRightPopup").height();
		var divBodyHeight = $("#divBodyRightPopup").height();
		// if the table body is longer than the table container
		if (tblBodyHeight > divBodyHeight) {
			// add y scroll bar
			$("#divBodyRightPopup").css("overflow-y", "scroll");
			if (isMobile()) {
				// Mobile version
			} else {
				// ceiling the width of divBody to avoid misaligning the borders
				$("#divBodyRightPopup").width(Math.ceil($("#divBodyRightPopup").width()));
				// PC version
				// when the table body has many rows, scroll bar will appear
				// set the size of body to minus the scroll bar, scroll bar size: 17px
				$("#divHeadRightPopup").width($("#divBodyRightPopup").width() - 17);
				// set size when user resizes window
				$(window).resize(function() {
					$("#divHeadRightPopup").width($("#divBodyRightPopup").width() - 17);
				});
			}
		} else {
			// table body is shorter than the table container
			// hide y scroll bar
			$("#divBodyRightPopup").css("overflow-y", "hidden");
			
			if (isMobile()) {
				// Mobile version
			} else {
				// ceiling the width of divBody to avoid misaligning the borders
				$("#divBodyRightPopup").width(Math.ceil($("#divBodyRightPopup").width()));
				// PC version
				// when the table body has many rows, scroll bar will appear
				// set the size of body to minus the scroll bar, scroll bar size: 17px
				$("#divHeadRightPopup").width($("#divBodyRightPopup").width());
				// set size when user resizes window
				$(window).resize(function() {
					$("#divHeadRightPopup").width($("#divBodyRightPopup").width());
				});
			}
			
		}
	}
	
	//Fix table header and body when scrolling only the table body
	function fixTableRight() {
		// get height of table body and table container
		var tblBodyHeight = $("#tblBodyRightPopup").height();
		var divBodyHeight = $("#divBodyRightPopup").height();
		// if the table body is longer than the table container
		if (tblBodyHeight > divBodyHeight) {
			// add y scroll bar
			$("#divBodyRightPopup").css("overflow-y", "scroll");
			if (isMobile()) {
				// Mobile version
			} else {
				// ceiling the width of divBody to avoid misaligning the borders
				$("#divBodyRightPopup").width(Math.ceil($("#divBodyRightPopup").width()));
				// PC version
				// when the table body has many rows, scroll bar will appear
				// set the size of body to minus the scroll bar, scroll bar size: 17px
				$("#divHeadRightPopup").width($("#divBodyRightPopup").width() - 17);
				// set size when user resizes window
				$(window).resize(function() {
					$("#divHeadRightPopup").width($("#divBodyRightPopup").width() - 17);
				});
			}
		} else {
			// table body is shorter than the table container
			// hide y scroll bar
			$("#divBodyRightPopup").css("overflow-y", "hidden");
			
			if (isMobile()) {
				// Mobile version
			} else {
				// ceiling the width of divBody to avoid misaligning the borders
				$("#divBodyRightPopup").width(Math.ceil($("#divBodyRightPopup").width()));
				// PC version
				// when the table body has many rows, scroll bar will appear
				// set the size of body to minus the scroll bar, scroll bar size: 17px
				$("#divHeadRightPopup").width($("#divBodyRightPopup").width());
				// set size when user resizes window
				$(window).resize(function() {
					$("#divHeadRightPopup").width($("#divBodyRightPopup").width());
				});
			}
			
		}
		// fix table not repainting on Chrome
		$("#divBodyRightPopup").html($("#divBodyRightPopup").html());
		
		// get height of table body and table container
		var tblBodyHeight = $("#tblBodyPopup").height();
		var divBodyHeight = $("#divBodyPopup").height();
		// if the table body is longer than the table container
		if (tblBodyHeight > divBodyHeight) {
			// add y scroll bar
			$("#divBodyPopup").css("overflow-y", "scroll");
			if (isMobile()) {
				// Mobile version
			} else {
				// ceiling the width of divBody to avoid misaligning the borders
				$("#divBodyPopup").width(Math.ceil($("#divBodyPopup").width()));
				// PC version
				// when the table body has many rows, scroll bar will appear
				// set the size of body to minus the scroll bar, scroll bar size: 17px
				$("#divHeadPopup").width($("#divBodyPopup").width() - 17);
				// set size when user resizes window
				$(window).resize(function() {
					$("#divHeadPopup").width($("#divBodyPopup").width() - 17);
				});
			}
		} else {
			// table body is shorter than the table container
			// hide y scroll bar
			$("#divBodyPopup").css("overflow-y", "hidden");
			
		}
		// fix table not repainting on Chrome
		$("#divBodyRight").html($("#divBodyRight").html());
	}

	// create object insert send to server
	function createDataForInsert() {
		var insertData = [];
		permissionIdPopup = $("#cbbAffilNamePopup").val();
		// 経営者、システム管理者
		if (permissionIdPopup == 1 || permissionIdPopup == 2){
			var bnn0087InsertObj = {
					usersId: userIdEdit,
					authorizationTypeId: $("#cbbAffilNamePopup").val(),
					typeIdEdit : permissionIdEdit,
					farmId : DEFAULT_FARM_ID,
					areaId : DEFAULT_AREA_ID,
					deleteFlag: DELETE_FLAG_OFF,
					lastUpdateDate: lastUpDate
			}
			insertData.push(bnn0087InsertObj);
		} else {
			// 農園管理者の場合
			if (permissionIdPopup == 3){
				for (var i = 0; i < farmArray.length; i++) {
					var bnn0087InsertObj = {
							usersId: userIdEdit,
							authorizationTypeId: $("#cbbAffilNamePopup").val(),
							typeIdEdit : permissionIdEdit,
							farmId : farmArray[i],
							areaId : DEFAULT_AREA_ID,
							deleteFlag: DELETE_FLAG_OFF,
							lastUpdateDate: lastUpDate
					}
					insertData.push(bnn0087InsertObj);
				}
			} else {
				// エリア管理者の場合
				for (var i = 0; i < areaArray.length; i++) {
					var bnn0087InsertObj = {
							usersId: userIdEdit,
							authorizationTypeId: $("#cbbAffilNamePopup").val(),
							typeIdEdit : permissionIdEdit,
							farmId : farmArray[0],
							areaId : areaArray[i],
							deleteFlag: DELETE_FLAG_OFF,
							lastUpdateDate: lastUpDate
					}
					insertData.push(bnn0087InsertObj);
				}
			}
		}
		return insertData ;
	}
	
	// Check select: blank fields
	function checkInputBeforeInsert() {
		permissionIdPopup = $("#cbbAffilNamePopup").val();
		if (permissionIdPopup == "null" || permissionIdPopup == null || permissionIdPopup == ""){
			return cbbAffilNamePopupBlank;
		}
		if (permissionIdPopup == 4){
			// エリア管理者の場合、１つ以上エリア名の選択必須。
			if (areaArray.length == 0 || farmArray.length == 0){
				return areaListBlank;
			}
		}
		if (permissionIdPopup == 3){
			if (farmArray.length == 0){
				return plantListBlank;
			}
		}
		return "";
	}
	
	//split list farm or area
	function listFarmAreaID(listID){
		var objID = listID.split(",");
		var le = objID.length;
		return objID;
	}
	
	// Back button click event process
	$("#btnBack").bind("click", function() {
		goBackToPreviousPage();
	});
	
	//
	var myVar = setInterval(function(){
		if(checkMaxCount){
			checkMaxCount = false;
			clearInterval(myVar);
		}
		fixTable();
	}, 100);
});