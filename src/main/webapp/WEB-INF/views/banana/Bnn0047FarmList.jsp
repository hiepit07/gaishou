<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0047FarmList.css?date=${Bnn0047FarmListCss}"/>
	<script type="text/javascript">
		var farmData = ${farmData};
	</script>
	<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0047FarmList.js?date=${Bnn0047FarmListJsDate}" type="text/javascript"></script>
		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="farm_list" /></h2>
			</div>
		</div>
		<div class="div-content">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
				
			</div>
			<div class="clear"></div>
			
			<div class="cont-box margin-top20 display-none">
				<p class="text-title"></p>
				<div class="div-zentai">
					<div id="divHead" class="div-header">
						<table class="tbl-table tbl-search-result">
							<col width="40">
							<col width="">
							<tr>
								<th><spring:message code="numberical_order" /></th>
								<th><spring:message code="farm_name" /></th>
							</tr>
						</table>
					</div>
					<div id="divBody" class="div-body">
						<table id="tblBody" class="tbl-table">
							<col width="40">
							<col width="">
							<tr>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">
			<div>
				<div class="group-button">
					<button id="btnBack" class="btn-medium" type="button"><spring:message code="button_back" /></button>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>


