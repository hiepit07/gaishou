 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
		
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0089SearchTask.css?date=${searchTaskCssDate}"/>
	<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0089SearchTask.js?date=${searchTaskJsDate}" type="text/javascript"></script>
	  	<script type="text/javascript" >
        	var status_none = "<spring:message code="status_none"/>";
        	var start_date_code = "<spring:message code="start_date_code"/>";
        	var planting_date = "<spring:message code="planting_date"/>";
        	var flowering_date = "<spring:message code="flowering_date"/>";
        	var bag_closing_date = "<spring:message code="bag_closing_date"/>";
        	var harvest_date = "<spring:message code="harvest_date"/>";
        	var arrChangePoint = [status_none,start_date_code,planting_date,flowering_date,bag_closing_date,harvest_date];
    	</script>
    	<script>
			var taskName = '<spring:message code="task_name" />';
			var workingDetails = '<spring:message code="working_details" />';
		</script>
		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="task_information_search" /></h2>
			</div>
		</div>
		<div class="div-content">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
				<div class="clear height20"></div>
				<div class="tbl-only-row">
					<div class="tbl-left"><span class="tbl-left-text"><spring:message code="task_name" /></span></div>
					<div class="tbl-line-item"><input id="txtTaskName" class="txt-lv4" type="text" value="" name="" maxlength="50"></div>
					<div class="tbl-line-item float-right">
						<button class="btn-small" type="button" id = "btnSearch"><spring:message code="button_search" /></button>
					</div>
				</div>
			</div>			
			<div class="clear"></div>
			<div class="cont-box margin-top20 display-none">
				<p class="text-title"><spring:message code="search_result" />&nbsp;<span id="txtCounts"></span></p>
				<div class="div-zentai">
					<div id="divHead" class="div-header">
						<table class="tbl-table tbl-search-result">
							<col width="40">
							<col width="15%">
							<col width="">
							<col width="10%">
							<col width="20%">
							<col width="40">
							<col width="40">
							<tr>
								<th><spring:message code="numberical_order" /></th>
								<th><spring:message code="task_name" /></th>
								<th><spring:message code="working_details" /></th>
								<th><spring:message code="quarantine_handling_flag" /></th>
								<th><spring:message code="change_point_code" /></th>
								<th></th>
								<th></th>
							</tr>
						</table>
					</div>
					<div id="divBody" class="div-body height400">
						<table id="tblBody" class="tbl-table">
							<col width="40">
							<col width="15%">
							<col width="">
							<col width="10%">
							<col width="20%">
							<col width="40">
							<col width="40">
							<tr>
								<td></td>
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
					<button id="btnRegister" class="btn-medium margin-right10" type="button"><spring:message code="button_new" /></button>
				</div>
			</div>
		</div>

		<!-- User info popup screen -->
		<div id="overlay" class="web-popup-overlay"></div>
		<div id="popupWrapper" class="area-info-popup">
			<div id="popupTitle" class="popup-title">
				<p id = "update"><spring:message code="title_popup_task_information_edit" /></p>
				<p id = "addNew"><spring:message code="title_popup_task_information_add" /></p>
			</div>
			<div class="height20"></div>
			<div class="popup-content">
				<div class="div-content-popup">
					<div class="tbl-box width670 margin-auto">
						<div class="tbl-row">
							<div class="tbl-left width200"><span class="tbl-left-text"><spring:message code="task_name" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><input id="txtTaskIdPopup" type="hidden"><input id="txtTaskNamePopup" class="txt-lv3" type="text" value="" name="" maxlength="80"></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left width200"><span class="tbl-left-text"><spring:message code="working_details" />(<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><textarea id="txtWorkingDetailsPopup" class="txtarea-lv90" rows="3" maxlength="128"></textarea></div>
						</div>
						<div class="clear height10"></div>
						<div class="tbl-row">
							<div class="tbl-left width200"><span class="tbl-left-text"><spring:message code="precaution" /></span></div>
							<div class="tbl-right"><textarea id="txtNotePopup" class="txtarea-lv90" rows="3" maxlength="128"></textarea></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left width200"><span class="tbl-left-text"><spring:message code="quarantine_handling_flag" /></span></div>
							<div class="tbl-right">
								<input id="chbQuarantineHandlingFlagPopup" class="" type="checkbox" value="" name="flag">
							</div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
								<div class="tbl-left width200"><span class="tbl-left-text"><spring:message code="change_point_code" /></span> </div>
								<div class="tbl-right">	<select id="cbbChangePointCode" class="cbx-lv2 cbx-h-lv2"></select></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left width200"></div>
							<div class="tbl-right"><span class="color-red">*</span>: <spring:message code="necessary_items_note" /></div>
						</div>
						<div class="clear"></div>
						<div class="group-button-popup margin-top20">
							<button id="btnCancelPopup" class="btn-medium" type="button"><spring:message code="button_back" /></button>
							<button id="btnRegisterPopup" class="btn-medium margin-right10" type="button"><spring:message code="button_confirm" /></button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="popupSearchUser" class="userinfo-popup"></div>
	</tiles:putAttribute>
</tiles:insertDefinition>


