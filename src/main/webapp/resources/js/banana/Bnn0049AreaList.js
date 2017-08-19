/**
 * 	@author Nghia Nguyen
 * 
 * 	@version 1.0.0
 * 	@since 1.0.0
 */
$(document).ready(function() {
	//--------------- Constants definition ---------------
	var NEXT_SCREEN_NAME = "0035";
	//--------------- Variables definition ---------------
	// application path
	var rootPath = getContextPath();
	var numberical_order = 0;
	initFarmNameComboboxData();
	// display current date
	$("#txtCurrentDate").text(getCurrentDate() + " (" + DAY_OF_WEEK + ") ");
	var farmId0047 = $("#farmId0047").val();
	if (farmId0047 != "") {
		$("#txtFarmName").replaceWith("<div id='txtFarmName' class='width350 float-left overflow-ellipsis-label'>" + decodeURIComponent($("#txtFarmName").text()) + "</div>");
	} else {
		$("#txtFarmName").text(decodeURIComponent($("#txtFarmName").text()));
	}
	
	// Get data for combobox process
	function initFarmNameComboboxData() {
		// area
		if (areaData == "-1") {
			jWarning(ERROR_MESSAGE.replace('$1', ERROR_MESSAGE_EXCEPTION_CLIENT), DIALOG_TITLE, DIALOG_OK_BUTTON);
			var tableHeight = calculateTableHeight();
			$("#divBody").height(tableHeight);
			fixTable();
			// scroll to top of table
		    $("#divBody").scrollTop(0).scrollLeft(0);
		} else if (areaData != "") {
			// create table starts
			var tableStringArray = [];
			// add tbody open tag
			// clear table
			$("#tblBody").find("tbody").remove();
			tableStringArray.push("<tbody>");
			for (var i = 0; i < areaData.length; i++) {
				numberical_order++;
				// row open tag
				tableStringArray.push("<tr id='row" + i + "'>");
				tableStringArray.push("<td class='align-center'>"+numberical_order+"</td>");
				tableStringArray.push("<td class='align-left'><a class='areaId cursor-pointer' id = '"+ areaData[i].areaId + "'>" + areaData[i].areaName + "</a></td>");
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
			var tableHeight = calculateTableHeight();
			$("#divBody").height(tableHeight);
			fixTable();
			// scroll to top of table
		    $("#divBody").scrollTop(0).scrollLeft(0);
		} else {
			jWarning(SEARCH_RESULT_NO_DATA_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			var tableHeight = calculateTableHeight();
			$("#divBody").height(tableHeight);
			fixTable();
			// scroll to top of table
		    $("#divBody").scrollTop(0).scrollLeft(0);
		}
	}
	
	// Back button click event process
	$("#btnBack").bind("click", function() {
		goBackToPreviousPage();
	});

	function convertUnicode(str) {
		var data = [];
		data = ["&lt;","&lt;","%26lt%3B"];
		replaceData = [];
		replaceData = ['<',">"];
		for (var i = 0; i < data.length; i++) {
			if (str.indexOf(data[i]) != -1) {
				str = str.replace('/'+data[i]+'/g', replaceData[i]);
			}
		}
		
	}
	$(document).on("click", ".areaId", function() {
		if (!checkAuthority(NEXT_SCREEN_NAME)) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// farm Id
			var farmId = $("#farmId").val();
			var farmName = $("#txtFarmName").html();
			var areaId = $(this).attr("id");
			var areaName = $(this).html();
	
			// create parameter object
			var parameterObj = {
				"url": rootPath + '/' + NEXT_SCREEN_NAME + '/',
				"farmId": farmId,
				"farmName": encodeURIComponent(farmName),
				"areaId": areaId,
				"areaName": encodeURIComponent(areaName)
			};
			// save to history
			savePageToHistory(parameterObj);
	
			$('<form>', {
				"id": "formTransfer",
				"html": '<input type="hidden" id="farmId" name="farmId" value="' + farmId + '" />' +
						'<input type="hidden" id="areaId" name="areaId" value="' + areaId + '" />' +
						'<input type="hidden" id="areaName" name="areaName" value="' + encodeURIComponent(areaName) + '" />' +
						'<input type="hidden" id="farmName" name="farmName" value="' + encodeURIComponent(farmName) + '" />',
				"method": 'POST',
				"action": rootPath + '/' + NEXT_SCREEN_NAME + '/'
			}).appendTo(document.body).submit();
		}
	});
});