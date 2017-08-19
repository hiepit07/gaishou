<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>Authentication Error</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css?date=20170421"/>
	<script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.js?date=20170421" type="text/javascript"></script>
</head>
<body>
	<h1>
		Please check your User ID or Password
	</h1>
	<input type="button" onclick="javascript: history.back();" value="Go back">
</body>
</html>