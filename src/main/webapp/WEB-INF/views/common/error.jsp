<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<!-- キャッシュ対応 -->
		<meta http-equiv="cache-control" content="max-age=0" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
		<meta http-equiv="pragma" content="no-cache" />
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<title>System Ceres</title>

		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reset.css?date=${resetDate}">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/layout.css?date=${layoutDate}">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css?date=${jqueryUiCssDate}">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/messageHandler.css?date=${messageHandlerDate}"/>
		<script src="${pageContext.request.contextPath}/resources/js/common/jquery-1.11.1.js?date=${jqueryDate}" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common/load_message.js?date=${messageDate}" type="text/javascript"></script>
	</head>
	<body>
		<div class="header">
			<div class="header-bg">
				<div class="header-cont">
					<div class="header-left"><img class="logo" src="${pageContext.request.contextPath}/resources/img/img_hv_logo.png" alt="VNB"></div>
					<div class="header-left header-title"><h1>SYSTEM CERES</h1></div>
					<div class="header-right">
						<div class="person-g drop-menu">
							<ul>
								<li>
									<a id="person-username" href="#">
										<security:authentication property="principal.USERFULLNAME" /> ▼
									</a>
									<ul>
										<li><a href="#"><spring:message code="menu_display_guide" /></a></li>
										<li><a href="${pageContext.request.contextPath}/login" id="linkLogout">
												<spring:message code="menu_log_out" />
											</a>
										</li>
									</ul>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="header-group-button">
			<div class="header-group-button-cont">
				<div class="group-button-lv5">
					<button id='btnTaskManagement' class="btn-large-lv2 btnCommon btn-disable" value="" disabled="disabled"><spring:message code="button_task_management" /></button>
					<button id='btnTrace' class="btn-large-lv2 btnCommon btn-disable" value="" disabled="disabled"><spring:message code="button_trace" /></button>
					<button id='btnTaskProcess' class="btn-large-lv2 btnCommon btn-disable" value="" disabled="disabled"><spring:message code="button_task_process" /></button>
					<button id='btnShippingScreen' class="btn-large-lv2 btnCommon btn-disable" value="" disabled="disabled"><spring:message code="title_shipping_screen" /></button>
				</div>
			</div>
		</div>
		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="error" /></h2>
			</div>
		</div>
		<script>
			// logout confirm text based on languages
			var LOGOUT_MESSAGE = '<spring:message code="message_log_out_confirm" />';
			// dialog values based on languages
			var DIALOG_TITLE = '<spring:message code="dialog_title" />';
			var DIALOG_YES_BUTTON = '<spring:message code="dialog_yes_button" />';
			var DIALOG_NO_BUTTON = '<spring:message code="dialog_no_button" />';
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
			// link click event
			$(document).on("click", ".span-link", function() {
				// get current link
				var link = $(this).attr("name");
				window.top.location = link;
			});
		</script>
		<div class="div-content align-center">
			<h1 class="header-title" style="color: red"><spring:message code="message_error1" /></h1>
			<h1 class="header-title" style="color: red"><spring:message code="message_error2" /></h1>
			<h1 class="header-title" style="color: red"><spring:message code="message_error3" /></h1>
			<span class="span-link" name="${pageContext.request.contextPath}/login"><spring:message code="login_back" /></span>
		</div>
	</body>
</html>