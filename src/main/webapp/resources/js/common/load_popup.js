//****************************************************
//show and hide popup
//****************************************************

//Popup Cultivation
function ShowCultivationCodeDialog()
{
	$("#overlay").show();
	$("#popupCultivation").fadeIn(300);

	//Set width, height for popup
	document.getElementById('popupCultivation').style.width = "950px";
	document.getElementById('popupCultivation').style.height= "740px";

	//Set position for popup
	$("#popupCultivation").css('top', "150px");
	$("#popupCultivation").css('left', "20px");

	$("#overlay").unbind("click");
	$("html").addClass("body-scroll");
	$("#popupCultivation").addClass("popup-scroll");
}
function HideCultivationCodeDialog()
{
	$("#overlay").hide();
	$("#popupCultivation").fadeOut(300);
	$("html").removeClass("body-scroll");
	$("#popupCultivation").removeClass("popup-scroll");
}

//Popup SearchUser
function ShowSearchUserDialog()
{
	$("#overlay").show();
	$("#popupSearchUser").fadeIn(300);

	//Set width, height for popup
	document.getElementById('popupSearchUser').style.width = "950px";
	document.getElementById('popupSearchUser').style.height= "740px";

	//Set position for popup
	$("#popupSearchUser").css('top', "100px");
	$("#popupSearchUser").css('left', "20px");

	$("#overlay").unbind("click");
	$("html").addClass("body-scroll");
	$("#popupSearchUser").addClass("popup-scroll");
	
	// Handle width Table Scroll
	var tblBodyHeight = $("#tblBody").height();
	var divBodyHeight = $("#divBody").height();
	if (tblBodyHeight > divBodyHeight) {
		$("#divBody").css("overflow-y","scroll");
		if (isMobile()){
			
		} else {
			$("#divHead").width($("#divBody").width()-17);
			$("#divFooter").width($("#divBody").width()-17);
			$(window).resize(function() {
				$("#divHead").width($("#divBody").width()-17);
				$("#divFooter").width($("#divBody").width()-17);
			});
		}
		
	}else {
		$("#divBody").css("overflow-y","hidden");
	}
}
function HideSearchUserDialog()
{
	$("#overlay").hide();
	$("#popupSearchUser").fadeOut(300);
	$("html").removeClass("body-scroll");
	$("#popupSearchUser").removeClass("popup-scroll");
}