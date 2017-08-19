<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
		
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0045SearchCultivation.css?date=${searchCultivationCssDate}"/>
	<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0045SearchCultivation.js?date=${searchCultivationJsDate}" type="text/javascript"></script>
	<script>
	</script>
		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="title_cultivation_search" /></h2>
			</div>
		</div>
		<div class="div-content">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
				<div class="clear"></div>
				<div class="tbl-only-row">
					<div class="tbl-line-title-process"><span class="tbl-left-text"><spring:message code="farm_name" /></span></div>
					<div class="tbl-line-item"><select id="cbbFarmName" class="cbx-lv2 cbx-h-lv2">
												</select></div>
					<div class="tbl-line-title-left-process"><span class="tbl-left-text"><spring:message code="kind_name" /></span></div>
					<div class="tbl-line-item"><select id="cbbKindName" class="cbx-lv2 cbx-h-lv2">
												</select></div>
				</div>
				<div class="clear"></div>
				<div class="margin-top5"></div>
				<div class="tbl-only-row">									
					<div class="tbl-line-title-process"><span class="tbl-left-text"><spring:message code="process_name" /></span></div>
					<div class="tbl-line-item"><select id="cbbProcessName" class="cbx-lv2 cbx-h-lv2">
												</select></div>
					<div class="tbl-line-title-left-process"><span class="tbl-left-text"><spring:message code="task_name" /></span></div>
					<div class="tbl-line-item"><select id="cbbTaskName" class="cbx-lv2 cbx-h-lv2">
					<option value='-2'></option>
												</select></div>
					<div class="tbl-line-title-right"><button id="btnSearch" class="btn-small" type="button"><spring:message code="button_search" /></button></div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="cont-box margin-top20 display-none">
				<p class="text-title"><spring:message code="search_result" />&nbsp;<span id="txtCounts"></span></p><br>
				<div class="text-title-lv2">
					<div class="float-left"><spring:message code="farm_name" />:&nbsp;</div> 
					<div id="txtfarmName" class="width200 float-left overflow-ellipsis-label"></div>
				</div>
				<div class="text-title-lv2"><p class="tbl-left-text"><spring:message code="kind_name" />:&nbsp;<span id="txtkindName"></span></p></div>
					<div class="div-zentai">
						<div id="divHead" class="div-header">
							<table class="tbl-table tbl-search-result">
								<col width="4%">
								<col width="8%">
								<col width="13%">
								<col width="25%">
								<col width="25%">
								<col width="">
								<tr>
									<th><spring:message code="numberical_order" /></th>
									<th><spring:message code="quarantine" /></th>
									<th><spring:message code="process_name" /></th>
									<th><spring:message code="task_name" /></th>
									<th><spring:message code="working_details" /></th>
									<th><spring:message code="precaution" /></th>
								</tr>
							</table>
						</div>
						<div id="divBody" class="div-body">
							<table id="tblBody" class="tbl-farm-table tbl-table">
								<col width="4%">
								<col width="8%">
								<col width="13%">
								<col width="25%">
								<col width="25%">
								<col width="">
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</table>
						</div>
					</div>
				<div class="tblPager pager float-right margin-top5">
					<div class="float-left padding-top2 margin-right20">
						<span id="btnFirst" class="page-number-first"></span>
						<span id="btnPrevious" class="page-number-pre"></span>
						<span id="lblCurrentPage" class="padding-left5 float-left">0</span>
						<span id="lblPageSeperator" class="float-left">/</span>
						<span id="lblMaxPage" class="padding-right5 float-left">0</span>
						<span id="btnNext" class="page-number-next"></span>
						<span id="btnLast" class="page-number-last"></span>
					</div>
					<div class="float-left">
						<input id="txtGoToPage" class="width40" type="text" maxlength="5"/>
						<input id="btnGoToPage" class="btn_pager" type="button" value="<spring:message code="button_pager" />" />
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
		<div id="popupSearchUser" class="userinfo-popup"></div>
	</tiles:putAttribute>
</tiles:insertDefinition>


