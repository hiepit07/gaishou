<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0001BananaMenu.css?date=${bananaMenuCssDate}"/>
		<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0001BananaMenu.js?date=${bananaMenuJsDate}" type="text/javascript"></script>

		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="title_banana_menu" /></h2>
			</div>
		</div>

		<div class="div-conten-menu">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class='div-menu'><button id='btnUserScreen' class='btn-menu' type='button'><spring:message code="title_popup_user_information" /></button></div>
					<div class='div-menu'><button id='btnFarmScreen' class='btn-menu' type='button'><spring:message code="title_farm_information" /></button></div>
				</div>
				<div class="tbl-row">
					<div class='div-menu'><button id='btnAreaScreen' class='btn-menu' type='button'><spring:message code="area_info" /></button></div>
					<div class='div-menu'><button id='btnBlockScreen' class='btn-menu' type='button'><spring:message code="title_popup_block_information_search" /></button></div>
				</div>
				<div class="tbl-row">
					<div class='div-menu'><button id='btnBananaKindScreen' class='btn-menu' type='button'><spring:message code="bananakind_info" /></button></div>
					<div class='div-menu'><button id='btnProcessScreen' class='btn-menu' type='button'><spring:message code="process_info" /></button></div>
				</div>
				<div class="tbl-row">
					<div class='div-menu'><button id='btnTaskScreen' class='btn-menu' type='button'><spring:message code="task_info" /></button></div>
					<div class='div-menu'><button id='btnManageScreen' class='btn-menu' type='button'><spring:message code="manage_info" /></button></div>
				</div>
				<div class="tbl-row">
					<div class='div-menu'><button id='btnCultivationScreen' class='btn-menu' type='button'><spring:message code="cultivation_info" /></button></div>
					<div class='div-menu'><button id='btnAuthorityScreen' class='btn-menu' type='button'><spring:message code="authority_info" /></button></div>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>