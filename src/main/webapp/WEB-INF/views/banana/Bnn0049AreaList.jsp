<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0049FarmList.css?date=${Bnn0049AreaListCss}"/>
	<script type="text/javascript">
		var areaData = ${areaData};
	</script>
	<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0049AreaList.js?date=${Bnn0049AreaListJsDate}" type="text/javascript"></script>
	<div class="title">
			<div class="title-cont">
				<h2><spring:message code="area_list" /></h2>
			</div>
		</div>
		<div class="div-content">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="cont-box margin-top20 width600">
				<div class="text-title-lv2">
					<div class="float-left"><spring:message code="farm_name" />:&nbsp;</div> 
					<div id="txtFarmName" class="width350 float-left overflow-ellipsis-label">${farmName}</div>
					<input type="hidden" id="farmId0047" value = "${farmId0047}" />
				</div>
				<div class="div-zentai">
					<div id="divHead" class="div-header">
						<table class="tbl-table tbl-search-result ">
							<col width="40">
							<col width="">
							<tr>
								<th><spring:message code="numberical_order" /></th>
								<th><spring:message code="area_name" /></th>
							</tr>
						</table>
					</div>
				</div>
				<div id="divBody" class="div-body height400 display_block">
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
		<input type = "hidden" id = "farmId" value = "${farmId}" />
		<input type = "hidden" id = "farmName" value = "${farmName}" />
		<div class="footer">
			<div>
				<div class="group-button">
					<button id="btnBack" class="btn-medium float-left margin-left20" type="button"><spring:message code="button_back" /></button>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>


