<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0013CultivationProcess.css?date=${cultivationProcessCssDate}"/>
		<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0013CultivationProcess.js?date=${cultivationProcessJsDate}" type="text/javascript"></script>
		
		<script>
			var processName = '<spring:message code="process_name" />';
		</script>
		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="title_cultivation_process_search" /></h2>
			</div>
		</div>
		<div class="div-content">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
				<div class="clear height20"></div>
				<div class="tbl-only-row">
					<div class="tbl-left"><span class="tbl-left-text"><spring:message code="process_name" /></span></div>
					<div class="tbl-line-item"><input id="txtProcessName" class="txt-lv4" type="text" value="" name="" maxlength="50"></div>
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
							<col width="40px">
							<col width="">
							<col width="40px">
							<col width="40px">
							<tr>
								<th><spring:message code="numberical_order" /></th>
								<th><spring:message code="process_name" /></th>
								<th></th>
								<th></th>
							</tr>
						</table>
					</div>
					<div id="divBody" class="div-body height400">
						<table id="tblBody" class="tbl-table">
							<col width="40px">
							<col width="">
							<col width="40px">
							<col width="40px">
							<tr>
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
		<div id="popupWrapper" class="cultiProcess-popup">
			<div id="popupTitle" class="popup-title">
				<p id = "update"><spring:message code="title_cultivation_process_edit" /></p>
				<p id = "addNew"><spring:message code="title_cultivation_process_add" /></p>
			</div>
			<div class="popup-content">
				<div class="div-content-popup">
					<div class="tbl-box width500 margin-auto">
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="process_name" />(<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><input id="hddProcessIdPopup" type="hidden" value="" name=""><input id="txtProcessNamePopup" class="txt-lv4" type="text" value="" name="" maxlength="50"></div>
						</div>
						<div class="clear height10"></div>
						<div class="tbl-row">
							<div class="tbl-left"></div>
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
	</tiles:putAttribute>
</tiles:insertDefinition>


