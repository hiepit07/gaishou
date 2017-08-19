/**
 * @author Khoa Le
 */
//number of items in one page in table
var ITEM_IN_ONE_PAGE_0003 = 20;
var ITEM_IN_ONE_PAGE_0005 = 20;
var ITEM_IN_ONE_PAGE_0007 = 20;
var ITEM_IN_ONE_PAGE_0009 = 20;
var ITEM_IN_ONE_PAGE_0013 = 20;
var ITEM_IN_ONE_PAGE_0017 = 20;
var ITEM_IN_ONE_PAGE_0045 = 20;
var ITEM_IN_ONE_PAGE_0075 = 20;
var ITEM_IN_ONE_PAGE_0087 = 20;
var ITEM_IN_ONE_PAGE_0089 = 20;
var ITEM_IN_ONE_PAGE_0095 = 20;

//--------------- Constants definition ---------------
// language abbreviations definition
var LANGUAGE_ENGLISH = "en";
var LANGUAGE_JAPANESE = "jp";
var LANGUAGE_VIETNAMESE = "vi";
var LANGUAGE_CURRENT = "jp";
// popup mode
var MODE_VIEW = "view";
var MODE_EDIT = "edit";
var MODE_NEW = "new";
var MODE_DELETE = "delete";
// popup action mode
var MODE_EDIT_POPUP = false;
var MODE_NEW_POPUP = true;
// select mode
var MODE_SINGEL = false;
var MODE_MULTI = true;
// action mode
var ACTION_GET = "get";
var ACTION_BACK = "back";
var ACTION_GETALL = "getall";
var ACTION_BACKALL = "backall";
var ACTION_UP = "up";
var ACTION_DOWN = "down";
var ACTION_FIRST = "first";
var ACTION_LAST = "last";
var AC_ROW = ".acRow";
var SL_ROW = ".slRow";
// flag status
var FLAG_ON = true;
var FLAG_OFF = false;
// delete flag status
var DELETE_FLAG_ON = "1001";
var DELETE_FLAG_OFF = "1000";
var DELETE_FLAG_ON = true;
var DELETE_FLAG_OFF = false;
var PREVIOUS_PAGE_DATA = "previousPageData";
// Prevent the backspace key from navigating back.
$(document).unbind("keydown").bind("keydown", function (event) {
    if (event.keyCode === 8) {
        var doPrevent = true;
        var types = ["text", "password", "file", "search", "email", "number", "date", "color", "datetime", "datetime-local", "month", "range", "search", "tel", "time", "url", "week"];
        var d = $(event.srcElement || event.target);
        var disabled = d.prop("readonly") || d.prop("disabled");
        if (!disabled) {
            if (d[0].isContentEditable) {
                doPrevent = false;
            } else if (d.is("input")) {
                var type = d.attr("type");
                if (type) {
                    type = type.toLowerCase();
                }
                if (types.indexOf(type) > -1) {
                    doPrevent = false;
                }
            } else if (d.is("textarea")) {
                doPrevent = false;
            }
        }
        if (doPrevent) {
            event.preventDefault();
            return false;
        }
    }
});

$(document).ready( function() {
    $("input[type='text'], textarea").attr('spellcheck',false);
});

// Click event processing for language changing
$(document).on("click", ".language", function() {
	// get selected locale
	var selectedLocale = $(this).attr("name");
	// change url
	window.location = "?language=" + selectedLocale;
});

// calculate scroll bar width
function getScrollbarWidth() {
    var outer = document.createElement("div");
    outer.style.visibility = "hidden";
    outer.style.width = "100px";
    outer.style.msOverflowStyle = "scrollbar"; // needed for WinJS apps

    document.body.appendChild(outer);

    var widthNoScroll = outer.offsetWidth;
    // force scrollbars
    outer.style.overflow = "scroll";

    // add innerdiv
    var inner = document.createElement("div");
    inner.style.width = "100%";
    outer.appendChild(inner);        

    var widthWithScroll = inner.offsetWidth;

    // remove divs
    outer.parentNode.removeChild(outer);

    return widthNoScroll - widthWithScroll;
}

// Fix table header and body when scrolling only the table body
function fixTable() {
	// current browser croll bar width
	var scrollBarWidth = getScrollbarWidth();
	// get height of table body and table container
	var tblBodyHeight = $("#tblBody").height();
	var divBodyHeight = $("#divBody").height();
	
	var tblBodyWidth = Math.ceil($("#divBody").width());

	// if the table body is longer than the table container
	if (tblBodyHeight > divBodyHeight) {
		// add y scroll bar
		$("#divBody").css("overflow-y", "scroll");
		if (isMobile()) {
			// Mobile version
		} else {
			// PC version
			// when the table body has many rows, scroll bar will appear
			// set the size of body to minus the scroll bar, scroll bar size: 17px
			$("#divHead").width($("#divBody").width() - scrollBarWidth);
			$("#divFooter").width($("#divBody").width() - scrollBarWidth);
			// set size when user resizes window
			$(window).resize(function() {
				$("#divHead").width($("#divBody").width() - scrollBarWidth);
				$("#divFooter").width($("#divBody").width() - scrollBarWidth);
			});
		}
	} else {
		// table body is shorter than the table container
		// hide y scroll bar
		$("#divBody").css("overflow-y", "hidden");
		// when the table body has few rows, scroll bar will disappear
		// set the size of body to plus the scroll bar, scroll bar size: 17px
		$("#divHead").width($("#divBody").width());
		$("#divFooter").width($("#divBody").width() + scrollBarWidth);
		// set size when user resizes window
		$(window).resize(function() {
			$("#divHead").width($("#divBody").width());
			$("#divFooter").width($("#divBody").width() + scrollBarWidth);
		});
	}
	// fix table not repainting on Chrome
	$("#divBody").html($("#divBody").html());
	
	if (tblBodyWidth != Math.ceil($("#divBody").width())) {
		fixTable();
	}
}

// calculate table height to fit page
function calculateTableHeight() {
	// get all the necessary height of controls
	var windowHeight = $(window).height();
	var headerHeight = $(".header").height();
	var headerGroupButtonHeight = $(".header-group-button").height();
	var titleHeight = $(".title").height();
	var searchConditionHeight = $(".tbl-box").height();
	var searchTableBodyTopMargin = $(".text-title").height() + $("#divHead").height();
	var pagerHeight = $(".tblPager").height();
	var footerHeight = $(".footer").height();
	var estimatedSafeHeight = 60;
	// when in jsp not paper
	if (pagerHeight == null) {
		pagerHeight = 0;
		estimatedSafeHeight = 91;
	}
	// calculate table body height
	return windowHeight - headerHeight - headerGroupButtonHeight - titleHeight - searchConditionHeight
				- searchTableBodyTopMargin - pagerHeight - footerHeight - estimatedSafeHeight;
}

// Check session is timeout
function checkSessionTimeout(object) {
	if (object.constructor === "string".constructor) {
		if (object.indexOf("Empty content.") > -1) {
			// return to login page
			window.top.location = getContextPath() + "/login";
			return 1;
		}
	}
	return 0;
}

// Check current language function
function checkCurrentLanguage() {
	var currentUrl = window.location.href;
	if (currentUrl.indexOf("?language=" + LANGUAGE_ENGLISH) > 0) {
		return LANGUAGE_ENGLISH;
	} else if (currentUrl.indexOf("?language=" + LANGUAGE_VIETNAMESE) > 0) {
		return LANGUAGE_VIETNAMESE;
	} else {
		return LANGUAGE_JAPANESE;
	}
}

// Display dialog
function showPopup(element) {
	$("#overlay").show();
	element.css("top", Math.max(0, (($(window).height() - element.outerHeight()) / 2) + 
            $(window).scrollTop()) + "px");
	element.css("left", Math.max(0, (($(window).width() - element.outerWidth()) / 2) + 
            $(window).scrollLeft()) + "px");
	element.show();
}
// Hide dialog
function hidePopup(element) {
	element.hide();
	$("#overlay").hide();
}

// Display dialog level 2
function showPopup2(element) {
	$("#overlay2").show();
	element.css("top", Math.max(0, (($(window).height() - element.outerHeight()) / 2) + 
            $(window).scrollTop()) + "px");
	element.css("left", Math.max(0, (($(window).width() - element.outerWidth()) / 2) + 
            $(window).scrollLeft()) + "px");
	element.show();
}
// Hide dialog level 2
function hidePopup2(element) {
	element.hide();
	$("#overlay2").hide();
}

// Check for OS function
function isMobile() {
	if (/iPad|iPhone|Android|webOS|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
		// Mobile
		return true;
	} else {
		// PC
		return false;
	}
}

// Get current date
function getCurrentDate() {
	var currentdate = new Date();
	return currentdate.getDate() + "/" + (currentdate.getMonth() + 1)  + "/" + currentdate.getFullYear();
}

// Get current time
function getCurrentTime() {
	var currentdate = new Date();
	return currentdate.getHours() + ":" + currentdate.getMinutes() + ":"  + currentdate.getSeconds();
}

// Get uppermost path of application
function getContextPath() {
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
}
// Get Path Domain 
function getContextPathDomain() {
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 3));
}

//convert date from dd/mm/yyyy to yyyy-mm-dd
function convertDataFrom(dateStr) {
	if(dateStr == '' || dateStr == null){
		return '';
	}
    var parts = dateStr.split("/");
    var sqlDate = parts[2] + "-" + parts[1] + "-" + parts[0];
    return sqlDate;
}

//convert date from yyyy-mm-dd to dd/mm/yyyy
function convertDataTo(dateStr) {
	if(dateStr == '' || dateStr == null){
		return '';
	}
    var parts = dateStr.split("-");
    var sqlDate = parts[2] + "/" + parts[1] + "/" + parts[0];
    return sqlDate;
}

// convert from HTML tags to normal texts
function convertHtmlToText(value) {
    var d = document.createElement("div");
    d.innerHTML = value;
    return d.innerText;
}

// change password link click event process
$(document).on("click", "#linkChangePassword", function(e) {
	e.preventDefault();
	// get current screen name
	var screenName = $(this).attr("href");
	// check authority of user
	if (!checkAuthority(screenName)) {
		// display message
		jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
	} else {
		// create parameter object
		var parameterObj = {
			"url": getContextPath() + "/" + screenName + "/"
		};
		// save to history
		savePageToHistory(parameterObj);
		window.location = getContextPath() + "/" + screenName + "/";
	}
});

// logout link click event process
$(document).on("click", "#linkLogout", function(e) {
	e.preventDefault();
	// get current link
	var link = $(this).attr("href");
	jQuestion_confirm(LOGOUT_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
		if (val) {
			window.top.location = link;
		}
	});
});

// ellipsis hover event process
$(document).on("mouseenter", ".overflow-ellipsis, .overflow-ellipsis-label", function() {
	if (checkOverflow(this) && !$(this).attr("title")) {
		$(this).attr("title", $(this).text().trim());
	}
});

// check if text is longer than container
function checkOverflow(el) {
   var curOverflow = el.style.overflow;
   if (!curOverflow || curOverflow === "visible") {
	   el.style.overflow = "hidden";
   }
   var isOverflowing = el.clientWidth < el.scrollWidth;
   el.style.overflow = curOverflow;

   return isOverflowing;
}

// save current url to storage
function savePageToHistory(parameterObj) {
	// get saved storage first
	var domianPath = getContextPathDomain();
	var savedArray = $.jStorage.get(domianPath + PREVIOUS_PAGE_DATA, []);
	// store new value to array
	savedArray.push(parameterObj);
	// save to storage
	$.jStorage.set(domianPath + PREVIOUS_PAGE_DATA, savedArray);
}

// get previous url in storage
function goBackToPreviousPage() {
	// get saved storage first
	var domianPath = getContextPathDomain();
	var savedArray = $.jStorage.get(domianPath + PREVIOUS_PAGE_DATA, []);
	// check size of array
	if (savedArray.length > 1) {
		// remove last element (current page)
		savedArray.pop();
		// save to storage
		$.jStorage.set(domianPath + PREVIOUS_PAGE_DATA, savedArray);
		// get previous page object
		var previousPageObj = savedArray[savedArray.length - 1];
		// count the number of keys in object
		var size = Object.keys(previousPageObj).length;
		// check the size
		if (size > 1) {
			// page with initialization parameter
			// loop through object to create form
			var formInputString = "";
			for (var key in previousPageObj) {
				if (previousPageObj.hasOwnProperty(key)) {
					formInputString += '<input type="hidden" id="' + key + '" name="'
										+ key + '" value="' + previousPageObj[key] + '" />';
				}
			}
			// create form starts
			$('<form>', {
				"id": "formChangePage",
				"html": formInputString,
				"method": 'POST',
				"action": previousPageObj["url"]
			}).appendTo(document.body).submit();
		} else {
			// normal page with no initialization parameter
			window.location = previousPageObj["url"];
		}
	} else {
		// last page, back to login page
		jQuestion_confirm(LOGOUT_MESSAGE, DIALOG_TITLE, DIALOG_YES_BUTTON, DIALOG_NO_BUTTON, function(val) {
			if (val) {
				window.top.location = getContextPath() + "/login";
			}
		});
	}
}

// function to check authority of user before navigating to next screen
function checkAuthority(nextScreenName) {
	// get authorized screen list
	var screenList = decodeEntities(SCREEN_LIST_ARRAY).replace(/ /g, "").replace("[", "").replace("]", "").split(",");
	var roleList = decodeEntities(SCREEN_LIST_ROLE_ARRAY).replace(/ /g, "").replace("[", "").replace("]", "").split(",");
	
	var screenRole = screenList.indexOf(nextScreenName);
	
	if (screenRole < 0) {
		return false;
	} else {
		if (roleList[screenRole].charAt(SCREEN_DISPLAY_ENABLE_FLAG) == '1')
			return true;
		return false;
	}
}

// function to check role of user on screen
function checkRole(screenName, role) {
	// get authorized screen list
	var screenList = decodeEntities(SCREEN_LIST_ARRAY).replace(/ /g, "").replace("[", "").replace("]", "").split(",");
	var roleList = decodeEntities(SCREEN_LIST_ROLE_ARRAY).replace(/ /g, "").replace("[", "").replace("]", "").split(",");
	
	var screenRole = screenList.indexOf(screenName);
	
	if (screenRole < 0) {
		return false;
	} else {		
		if (roleList[screenRole].charAt(role) == '1')
			return true;
		return false;
	}
}

// function to decode HTML special characters to normal characters
var decodeEntities = (function() {
	// this prevents any overhead from creating the object each time
	var element = document.createElement('div');

	function decodeHTMLEntities (str) {
		if(str && typeof str === 'string') {
			// strip script/html tags
		    str = str.replace(/<script[^>]*>([\S\s]*?)<\/script>/gmi, '');
		    str = str.replace(/<\/?\w(?:[^"'>]|"[^"]*"|'[^']*')*>/gmi, '');
		    element.innerHTML = str;
		    str = element.textContent;
		    element.textContent = '';
		}
		return str;
	}

	return decodeHTMLEntities;
})();