/**
 * @author Hieu Dao
 */
var countqwe = 1;
// Load search user popup
function getSearchUser(config) {
	// variables definition
	var rootPath = getContextPath();

	// Định nghĩa object defaults, object này chứa các giá trị mặc định cho tham số truyền vào
	var defaults = {
		"listUserId": "",
		"listUserName": "",
		"farmid": null,
		"userRole": null,
		"modeAction": false,
		"modeSelect": false,
	};
	// Biến options là merge giữa defaults và config user truyền vào, tham số nào không truyền
	// thì lấy giá trị của defaults
	var options = $.extend(defaults, config);

	// Lấy các giá trị options do user truyền vào, giá trị nào không được truyền thì sẽ lấy từ
	// object defaults ở trên.
	txtUserId = options['listUserId'];
	txtUserName = options['listUserName'];
	txtFarmIdPopup = options['farmid'];
	txtUserRolePopup = options['userRole'];
	modeAction = options['modeAction'];
	modeSelect = options['modeSelect'];

	// Gọi hàm load file Bnn0024SearchUserPopup.jsp vào div #popupSearchUser
	$("#popupSearchUser").load(rootPath + "/0023/ #popup_searchUser", function(response, status, xhr) {
		if ($("#searchUser").length > 0) {
			ShowPopupSearchUserCodeDialog();
			initData0023();
		} else {
			jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION), DIALOG_TITLE, DIALOG_OK_BUTTON);
		}
	});

	// Gọi return this để sau khi gọi hàm này thì #popupSearchUser có thể tiếp tục được sử dụng
	return this;
}

// Popup search user
function ShowPopupSearchUserCodeDialog()
{
	 element = $("#popupSearchUser");
	 // Call user popup overlay
	 $("#overlaySearchUser").show();
	 $("#popupSearchUser").fadeIn(300);

	 // Get the window height and width
	 var winH = $(window).height();
	 var winW = $(window).width();

	 // Set position for popup
	 var w = (winW - element.width()) / 2;
	 element.css("top", Math.max(0, (($(window).height() - element.outerHeight()) / 2) + 
	            $(window).scrollTop()) + "px");
	 element.css("left", Math.max(0, (($(window).width() - element.outerWidth()) / 2) + 
	            $(window).scrollLeft()) + "px");
}

function initData0023() {
	//--------------- Constants definition ---------------
	// Number of items in one page in table
	var ITEM_IN_ONE_PAGE = 20;

	//--------------- Variables definition ---------------
	// application path
	var rootPath = getContextPath();
	// search result total data count
	var totalResultCount = 0;
	// variables for handling pager
	var currentPage = 1;
	var maxPage = 0;
	var from = 0;
	// numberical_order
	var numberical_order = 0;
	// variables definition
	var userId = "";
	var userName = "";
	var userObject = {};
	var arraySelected = [];

	// Search button event process
	$(document).on("click", "#btnScUserPupSearch", function() {
		numberical_order = 0;
		setTimeout(function() {
			// reset variables
			resetVariables();
			// draw table
			drawResult = drawUserTable();
			if (drawResult != "") {
				// update total data count UI
				setDataCounts();
				unBindClickRow();
				unBindSelectUser();
				pagingEvent()
			}
		}, 10);
	});
	
	// auto search on first time loading page
	$("#btnScUserPupSearch").trigger("click");

	// Hover user row
	$(document).on("mouseover", ".acRow", function() {  

		// Single hover
		$(".acRow").removeClass("hvRow");
		// Multi hover
		$(this).toggleClass("hvRow");
	});

	// Select user row
	function unBindClickRow() {
		$(".acRow").unbind("click");
		$(".acRow").bind("click", function() {

			//variables definition
			var id = $(this).find("td").eq(1).text();
			var name = $(this).find("td").eq(2).text();

			// Check search user popup mode
			if(modeSelect == MODE_SINGEL) {
				
				// Single select
				if($(this).hasClass("slRow")) {
					$(".acRow").removeClass("slRow");
					$(this).removeClass("slRow");
				} else {
					$(".acRow").removeClass("slRow");
					$(this).addClass("slRow");
				}
			}

			// Multi Single select
			if(modeSelect == MODE_MULTI) {
				$(this).toggleClass("slRow");
			}

			// Add object selected to list
			saveSelectedData(id, name)
		});
	}

	// Object user
	function userInfo(id, user) {
		this.userId = id;
		this.userName = user;
	}

	 // Save selected data of search result based on page number
	function saveSelectedData(id, name) {

		// variables definition
		var add = true;
		var count = 0

		// Check id's exsit in array selected
		$(arraySelected).each(function() {
			if(id == this.userId) {
				add = false
				arraySelected.splice(count,1);
				return false;
			}
			count++;
		});

		if(modeSelect == MODE_SINGEL) { // If mode select's singel
			if(add == true) {
				arraySelected = [];
				userObject = new userInfo(id, name)
				arraySelected.push(userObject);
			} else {
				arraySelected = [];
			}
		} else if(modeSelect == MODE_MULTI) { // If mode select's multi
			if(add == true) {
				userObject = new userInfo(id, name)
				arraySelected.push(userObject);
			}
		}
	}

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
		$("#btnScUserPupFirst").bind("click", function() {
			if (parseInt(totalResultCount) > 0) {
				if (currentPage > 1) {
					var newPage = 1;
					numberical_order = 0;
					goToPage(newPage);
				}
			}
		});
	
		// Page Previous button click event process
		$("#btnScUserPupPrevious").bind("click", function() {
			if (parseInt(totalResultCount) > 0) {
				if (currentPage > 1) {
					var newPage = currentPage - 1;
					numberical_order = (newPage -1) * ITEM_IN_ONE_PAGE;
					goToPage(newPage);
				}
			}
		});
	
		// Page Next button click event process
		$("#btnScUserPupNext").bind("click", function() {
			if (parseInt(totalResultCount) > 0) {
				if (currentPage < maxPage) {
					var newPage = currentPage + 1;
					numberical_order = (newPage -1) * ITEM_IN_ONE_PAGE;
					goToPage(newPage);
				}
			}
		});
	
		// Page Last button click event process
		$("#btnScUserPupLast").bind("click", function() {
			if (parseInt(totalResultCount) > 0) {
				if (currentPage < maxPage) {
					var newPage = maxPage;
					numberical_order = (newPage -1) * ITEM_IN_ONE_PAGE;
					goToPage(newPage);
				}
			}
		});
	
		// Check Page input
		$("#txtScUserPupGoToPage").bind("keyup", function() {
			var value = parseInt($(this).val());
			if (value > 0) {
				$("#btnScUserPupGoToPage").prop("disabled", false);
				$("#btnScUserPupGoToPage").css("cursor", "pointer");
			}
			if (isNaN(value) || $(this).val() == "" || parseInt($(this).val(), 10) == 0 || (currentPage == maxPage && (maxPage == 1 || maxPage == 0))) {
				$("#btnScUserPupGoToPage").prop("disabled", true);
				$("#btnScUserPupGoToPage").css("cursor", "default");
			}
		});
	
		$("#txtScUserPupGoToPage").bind("keydown", function(event) {
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
		$("#btnScUserPupGoToPage").bind("click", function() {
			var newPage = parseInt($("#txtScUserPupGoToPage").val());
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

	// Get user form popup
	function unBindSelectUser(){
		$("#btnScUserPupSelect").unbind("click");
		$("#btnScUserPupSelect").bind("click", function() {

			// Variables definition
			var userIdArray = [];
			var userNameArray = [];
			// If array selected's not null
			if(Object.keys(arraySelected).length > 0) {

				// Read selected array value set to id and name array
				$(arraySelected).each(function() {
					userIdArray.push(this.userId);
					userNameArray.push(this.userName);
				});

				//Get user when mode's edit
				if (userNameArray != null) {
					if(modeAction == MODE_EDIT_POPUP) { 
						$("#" + txtUserId).val(userIdArray);
						$("#" + txtUserName).attr("title", userNameArray);
						if (userNameArray[0].length > 10) {
							userNameArray[0] = userNameArray[0].substring(0,10) + "...";
						}
						$("#" + txtUserName).text(userNameArray);
					} else { //Get user when mode's new
						$("#" + txtUserId).val(userIdArray);
						$("#" + txtUserName).attr("title", userNameArray);
						if (userNameArray[0].length > 10) {
							userNameArray[0] = userNameArray[0].substring(0,10) + "...";
						}
						$("#" + txtUserName).text(userNameArray);
						
					}
				}
				// reset variables
				resetVariables();
				HidePopupSearchUserCodeDialog();
				showPopup($("#popupWrapper"));
			} else {
				// display error message
				jWarning(ERROR_SELECT, DIALOG_TITLE, DIALOG_OK_BUTTON);
			}
		});
	}

	//Close search user popup
	$(document).on("click", "#btnScUserPupBack", function() {
		HidePopupSearchUserCodeDialog();
		showPopup($("#popupWrapper"));
	});

	// Draw user data table based on search conditions
	function drawUserTable() {
		// variables definition
		var returnValue = "";
		var dataObject = null;
		// get search conditions
		dataObject = createSearchConditions();
		// make Ajax call to server to get data
		$.ajax({
			type: "POST",
			url: rootPath + "/0023/searchData",
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
						// calculate max page
						var calculatedResultOdd = parseInt(totalResultCount) % parseInt(ITEM_IN_ONE_PAGE);
						var calculatedResult = Math.floor(parseInt(totalResultCount) / parseInt(ITEM_IN_ONE_PAGE));
						maxPage = (calculatedResultOdd == 0) ? calculatedResult : (calculatedResult + 1);
						// update pager
						$("#lblScUserPupCurrentPage").text(currentPage);
						$("#lblScUserPupMaxPage").text(maxPage);
						// clear table
						$(".tbl-user-popup-table").find("tbody").remove();
						// create table starts
						var tableStringArray = [];
						// add tbody open tag
						tableStringArray.push("<tbody>");
						for (var i = 0; i < returnedJsonData.length; i++) {
							numberical_order++;
							// row open tag
							tableStringArray.push("<tr id='row" + i + "' class='acRow '>");
							tableStringArray.push("<td class='align-center'>"+numberical_order+"</td>");
							// users id
							tableStringArray.push("<td>" + returnedJsonData[i].userId + "</td>");
							// users name
							tableStringArray.push("<td>" + returnedJsonData[i].userName + "</td>");
							// row close tag
							tableStringArray.push("</tr>");
						}
						// add tbody close tag
						tableStringArray.push("</tbody>");
						// append all created string to table
						$(".tbl-user-popup-table").append(tableStringArray.join(''));
						// show search result
						$("#popup_searchUser .cont-box").removeClass("display-none");
						// fix table header and body when scrolling only the table body
						fixUserPoupTable();
						// Fix table height to fit page
						var tableHeight = calculateUserTableHeight();
						$("#popup_searchUser #divBody").height(tableHeight);
						// update total data count UI
						setDataCounts();
						setPagerStatus();
						$("#btnScUserPupSelect").prop("disabled", false);
						$("#btnScUserPupSelect").removeClass("btn-disable");
						$("#popup_searchUser #divBody").scrollTop(0).scrollLeft(0);
					}
					if(arraySelected.length > 0) {
						$(".acRow").each(function() {
							for(var i = 0; i < arraySelected.length; i++) {
								if($(this).find("td").eq(0).text() == arraySelected[i].userId) {
									$(this).addClass("slRow");
								}
							}
						});
					}
				} else {
					// display error message
					jWarning(SEARCH_RESULT_NO_DATA_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
					// update pager
					$("#lblScUserPupCurrentPage").text("0");
					$("#lblScUserPupMaxPage").text("0");
					// clear table
					$(".tbl-user-popup-table").find("tbody").remove();
					totalResultCount = 0;
					// update total data count UI
					setDataCounts();
					setPagerStatus();
					$("#btnScUserPupSelect").prop("disabled", true);
					$("#btnScUserPupSelect").addClass("btn-disable");
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

	// Fix table header and body when scrolling only the table body
	function fixUserPoupTable() {
		// get height of table body and table container
		var tblBodyHeight = $("#popup_searchUser #tblBody").height();
		var divBodyHeight = $("#popup_searchUser #divBody").height();
		// if the table body is longer than the table container
		if (tblBodyHeight > divBodyHeight) {
			// add y scroll bar
			$("#popup_searchUser #divBody").css("overflow-y", "scroll");
			if (isMobile()) {
				// Mobile version
			} else {
				// PC version
				// when the table body has many rows, scroll bar will appear
				// set the size of body to minus the scroll bar, scroll bar size: 17px	 
				$("#popup_searchUser #divHead").width($("#popup_searchUser #divBody").width() - 17);
				$("#popup_searchUser #divFooter").width($("#popup_searchUser #divBody").width() - 17);
				// set size when user resizes window
				$(window).resize(function() {
					$("#popup_searchUser #divHead").width($("#popup_searchUser #divBody").width() - 17);
					$("#popup_searchUser #divFooter").width($("#popup_searchUser #divBody").width() - 17);
				});
			}
		} else {
			// table body is shorter than the table container
			// hide y scroll bar
			$("#popup_searchUser #divBody").css("overflow-y", "hidden");
			// when the table body has few rows, scroll bar will disappear
			// set the size of body to plus the scroll bar, scroll bar size: 17px
			$("#popup_searchUser #divHead").width($("#popup_searchUser #divBody").width());
			$("#popup_searchUser #divFooter").width($("#popup_searchUser #divBody").width() + 17);
			// set size when user resizes window
			$(window).resize(function() {
				$("#popup_searchUser #divHead").width($("#popup_searchUser #divBody").width());
				$("#popup_searchUser #divFooter").width($("#popup_searchUser #divBody").width() + 17);
			});
		}
	}

	// calculate table height to fit page
	function calculateUserTableHeight() {
		// get all the necessary height of controls
		var windowHeight = $(window).height();
		var topHeight = $("#popupSearchUser").css('top');
		var bottomHeight = $("#popupSearchUser").css('margin-bottom');
		var titleHeight = $("#popup_searchUser #popupTitle").height();
		var searchConditionHeight = $("#popup_searchUser .tbl-box").height();
		var searchTableBodyTopMargin = $("#popup_searchUser .text-title").height() + $("#popup_searchUser #divHead").height();
		var pagerHeight = $("#popup_searchUser .tblPager").height();
		var footerHeight = $("#popup_searchUser .group-button-lv2").height();
		var estimatedSafeHeight = 40;
		// calculate table body height
		return windowHeight - topHeight - bottomHeight - titleHeight - searchConditionHeight - searchTableBodyTopMargin 
		- pagerHeight - footerHeight - estimatedSafeHeight;
	}

	// Search conditions object creation process
	function createSearchConditions() {
		var uId =  $.trim($("#txtScUserPupUsersId").val());
		var uName =  $.trim($("#txtScUserPupUsersName").val());
		return {
			userId: uId,
			userName: uName,
			farmId:	txtFarmIdPopup,
			authorizationTypeId: txtUserRolePopup,
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
			var drawResult = drawUserTable();
			if (drawResult != "") {
				currentPage = newPage + 1;
				// update pager
				$("#lblScUserPupCurrentPage").text(currentPage);
				$("#lblScUserPupMaxPage").text(maxPage);
				setPagerStatus();
				unBindClickRow();
				unBindSelectUser();
			}
			// update total data count UI
			setDataCounts();
		}, 10);
	}

	// Update UI of pager based on search result
	function setPagerStatus() {
		$("#txtScUserPupGoToPage").val("");
		if (parseInt(totalResultCount) > 0) {
			if (parseInt(maxPage) == 1) {
				$("#btnScUserPupFirst").addClass("page-number-first_dis");
				$("#btnScUserPupPrevious").addClass("page-number-pre_dis");
				$("#btnScUserPupNext").addClass("page-number-next_dis");
				$("#btnScUserPupLast").addClass("page-number-last_dis");
				$("#txtScUserPupGoToPage").prop("readonly", true);
			} else {
				if (currentPage == 1) {
					$("#btnScUserPupFirst").addClass("page-number-first_dis");
					$("#btnScUserPupPrevious").addClass("page-number-pre_dis");
					$("#btnScUserPupNext").removeClass("page-number-next_dis");
					$("#btnScUserPupLast").removeClass("page-number-last_dis");
				}
				if (currentPage > 1 && currentPage < maxPage) {
					$("#btnScUserPupFirst").removeClass("page-number-first_dis");
					$("#btnScUserPupPrevious").removeClass("page-number-pre_dis");
					$("#btnScUserPupNext").removeClass("page-number-next_dis");
					$("#btnScUserPupLast").removeClass("page-number-last_dis");
				}
				if (currentPage == maxPage) {
					$("#btnScUserPupFirst").removeClass("page-number-first_dis");
					$("#btnScUserPupPrevious").removeClass("page-number-pre_dis");
					$("#btnScUserPupNext").addClass("page-number-next_dis");
					$("#btnScUserPupLast").addClass("page-number-last_dis");
				}
				$("#txtScUserPupGoToPage").prop("readonly", false);
			}
		} else {
			$("#btnScUserPupFirst").addClass("page-number-first_dis");
			$("#btnScUserPupPrevious").addClass("page-number-pre_dis");
			$("#btnScUserPupNext").addClass("page-number-next_dis");
			$("#btnScUserPupLast").addClass("page-number-last_dis");
			$("#txtScUserPupGoToPage").prop("readonly", true);
		}
		$("#txtScUserPupGoToPage").val("");
		$("#btnScUserPupGoToPage").prop("disabled", true);
		$("#btnScUserPupGoToPage").css("cursor", "default");
	}

	// Update total data count process
	function setDataCounts() {
		$("#txtScUserPupCounts").text(totalResultCount.toString().replace(/^(-?\d+)(\d{3})/, "$1,$2"));
	}

	// Reset variables process
	function resetVariables() {
		totalResultCount = 0;
		arraySelected = [];
		currentPage = 1;
		from = 0;
	}

	// Close search user popup function
	function HidePopupSearchUserCodeDialog() {
		$("#overlaySearchUser").hide();
		$("#popupSearchUser").hide();
	}
}