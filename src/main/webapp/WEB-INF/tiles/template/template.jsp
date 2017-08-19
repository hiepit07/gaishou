<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

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
		<script src="${pageContext.request.contextPath}/resources/js/common/jquery-ui.min.js?date=${jqueryUiDate}" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common/jquery.ui.datepicker-jp.js?date=${datepickerDate}" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common/inputmask.js?date=${inputmaskDate}" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common/jquery.inputmask.js?date=${jqueryInputmaskDate}" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common/inputmask.date.extensions.js?date=${inputMaskExtDate}" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common/load_calendar.js?date=${calendarDate}" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common/load_message.js?date=${messageDate}" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common/jstorage.js?date=${storageDate}" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common/common.js?date=${commonDate}" type="text/javascript"></script>
		
	</head>
	<body>
		<tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="body" />
	</body>
</html>