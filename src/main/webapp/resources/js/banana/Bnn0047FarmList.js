/**
 * 	@author Nghia Nguyen
 * 
 * 	@version 1.0.0
 * 	@since 1.0.0
 */
$(document).ready(function() {
	//--------------- Constants definition ---------------
	var NEXT_SCREEN_NAME = "0049";
	//--------------- Variables definition ---------------
	var rootPath = getContextPath();
	var numberical_order = 0;
	initFarmNameComboboxData();
	// display current date
	$("#txtCurrentDate").text(getCurrentDate() + " (" + DAY_OF_WEEK + ") ");

	// Get data for combobox farm
	function initFarmNameComboboxData() {
		// farm
		if (farmData != "") {
			// clear table
			$("#tblBody").find("tbody").remove();
			// create table starts
			var tableStringArray = [];
			// add tbody open tag
			tableStringArray.push("<tbody>");
			for (var i = 0; i < farmData.length; i++) {
				numberical_order++;
				// row open tag
				tableStringArray.push("<tr id='row" + farmData[i].farmId + "'>");
				// numberical_order
				tableStringArray.push("<td class='align-center'>"+numberical_order+"</td>");
				// Farm Id + Farm Name
				tableStringArray.push("<td class='align-left overflow-ellipsis'><a id = '"+ farmData[i].farmId +"' class='farmId cursor-pointer'>" + farmData[i].farmName + "</a></td>");
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
		    $("#tblBody").scrollTop(0);
			$("#tblBody").scrollLeft(0);
		} else {
			jWarning(SEARCH_RESULT_NO_DATA_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		}
	}
	
	// Back button click event process
	$("#btnBack").bind("click", function() {
		goBackToPreviousPage();
	});
	
	$(".farmId").bind("click", function() {
		if (!checkAuthority(NEXT_SCREEN_NAME)) {
			// display message
			jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
		} else {
			// farm Id
			var farmId = $(this).attr('id');
			var farmName = $(this).html();
	
			// create parameter object
			var parameterObj = {
				"url": rootPath + '/' + NEXT_SCREEN_NAME + '/',
				"farmId": farmId,
				"farmName": encodeURIComponent(farmName)
			};
			// save to history
			savePageToHistory(parameterObj);
	
			$('<form>', {
				"id": "formTransfer",
				"html": '<input type="hidden" id="farmId" name="farmId" value="' + farmId + '" />' +
						'<input type="hidden" id="farmName" name="farmName" value="' + encodeURIComponent(farmName) + '" />',
				"method": 'POST',
				"action": rootPath + '/' + NEXT_SCREEN_NAME + '/'
			}).appendTo(document.body).submit();
		}
	});
});