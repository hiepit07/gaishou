<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page language="java" import="vn.bananavietnam.ict.common.util.Util" %>

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
		<%
			// define folder path to common CSS files
			String pathCSS = request.getSession().getServletContext().getRealPath("/resources/css/");
			// define folder path to common Javascript files
			String pathJS = request.getSession().getServletContext().getRealPath("/resources/js/common/");
			String resetDate = new Util().getFileLastModified(pathCSS + "reset.css");
			String layoutDate = new Util().getFileLastModified(pathCSS + "layout.css");
			String jQueryCssDate = new Util().getFileLastModified(pathCSS + "jquery-ui.min.css");
			String messageDate = new Util().getFileLastModified(pathCSS + "messageHandler.css");
			String jQueryDate = new Util().getFileLastModified(pathJS + "jquery-1.11.1.js");
			String loadmessageDate = new Util().getFileLastModified(pathJS + "load_message.js");
			String jstorageDate = new Util().getFileLastModified(pathJS + "jstorage.js");
			String commonDate = new Util().getFileLastModified(pathJS + "common.js");
			String loginDate = new Util().getFileLastModified(pathJS + "login.js");
        %>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reset.css?date=<%=resetDate%>"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/layout.css?date=<%=layoutDate%>"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css?date=<%=jQueryCssDate%>"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/messageHandler.css?date=<%=messageDate%>"/>

		<script src="${pageContext.request.contextPath}/resources/js/common/jquery-1.11.1.js?date=<%=jQueryDate%>" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common/load_message.js?date=<%=loadmessageDate%>" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common/jstorage.js?date=<%=jstorageDate%>" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common/common.js?date=<%=commonDate%>" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common/login.js?date=<%=loginDate%>" type="text/javascript"></script>
	</head>
	<body class="login-body">
		<!-- Constants -->
		<script type="text/javascript">
			// wrong username or password text based on languages
			var WRONG_USERNAME_OR_PASSWORD_MESSAGE = '<spring:message code="message_wrong_username_or_password" />';
			// access denied
			var ACCESS_DENIED_MESSAGE = '<spring:message code="message_access_denied" />';
			// dialog values based on languages
			var DIALOG_TITLE = '<spring:message code="dialog_title" />';
			var DIALOG_OK_BUTTON = '<spring:message code="dialog_ok_button" />';
			// alert message based on languages
			var ERROR_MESSAGE = '<spring:message code="message_error_generic" />';
		</script>
		<form action="j_spring_security_check" method="POST">
			<div class="login-content">
				<div class="login-logo"><img src="${pageContext.request.contextPath}/resources/img/img_hv_logo.png" alt="" width="200"></div>
				<div class="login-box">
					<div class="tbl-box">
						<div class="tbl-row align-center header-title"><p>SYSTEM CERES</p>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"></div>
							<div class="tbl-right">
								<div class="flag-g">
									<img class="language cursor-pointer" name="jp" src="${pageContext.request.contextPath}/resources/img/jp.png" alt="日本語">
									<img class="language cursor-pointer" name="en" src="${pageContext.request.contextPath}/resources/img/en.png" alt="English">
									<img class="language cursor-pointer" name="vi" src="${pageContext.request.contextPath}/resources/img/vi.png" alt="Tiếng Việt">
								</div>
							</div>
						</div>
						<div class="clear height5"></div>
						<div class="clear height5"></div>
						<input type="hidden" id="msgContentpopup" name="messages" 
							class="MSG_WARNING" value="ユーザー名またはパスワードが間違っている!" title="警告"/>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="user_id" /></span></div>
							<div class="tbl-right"><input id="j_username" name="j_username" class="txt-lv3" type="text" value="" maxlength="11"></div>
						</div>
						<div class="clear height5"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="password" /></span></div>
							<div class="tbl-right"><input id="j_password" name="j_password" class="txt-lv3" type="password" value="" maxlength="11"></div>
						</div>
						<div class="clear"></div>
						<!-- <div class="error-msg-log margin-top10"><span id="alertmsg">ユーザーIDが存在しません。</span></div> -->
						<div class="btn-login-group"><input id="btnLogin" class="btn-login" value="<spring:message code="button_login" />" type="button"/></div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>


