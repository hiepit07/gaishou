<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0017SearchBananaKind.css?date=${searchBananaKindCssDate}"/>
		<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0017SearchBananaKind.js?date=${searchBananaKindJsDate}" type="text/javascript"></script>
		
		<script>
			var kindName = '<spring:message code="kind_name" />';
			var prospectiveHarvestAmount = '<spring:message code="prospective_Harvest_Amount" />';
			var estimatedDaysFlowering = '<spring:message code="estimated_Days_Flowering" />';
			var estimatedDaysBagging = '<spring:message code="estimated_Days_Bagging" />';
			var estimatedDaysHarvest = '<spring:message code="estimated_Days_Harvest" />';
		</script>
		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="kind_information_search" /></h2>
			</div>
		</div>
		<div class="div-content">			
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
				<div class="clear height20"></div>
				<div class="tbl-only-row">
					<div class="tbl-left"><span class="tbl-left-text"><spring:message code="kind_name" /></span></div>
					<div class="tbl-line-item"><input id="txtKindName" class="txt-lv4" type="text" value="" name="" maxlength="50"></div>
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
							<col width="18%">
							<col width="18%">
							<col width="18%">
							<col width="18%">
							<col width="40px">
							<col width="40px">
							<tr>
								<th><spring:message code="numberical_order" /></th>
								<th><spring:message code="kind_name" /></th>
								<th><spring:message code="prospective_Harvest_Amount" /></th>
								<th><spring:message code="estimated_Days_Flowering" /></th>
								<th><spring:message code="estimated_Days_Bagging" /></th>
								<th><spring:message code="estimated_Days_Harvest" /></th>
								<th></th>
								<th></th>
							</tr>
						</table>
					</div>
					<div id="divBody" class="div-body height400">
						<table id="tblBody" class="tbl-table">
							<col width="40px">
							<col width="">
							<col width="18%">
							<col width="18%">
							<col width="18%">
							<col width="18%">
							<col width="40px">
							<col width="40px">
							<tr>
								<td></td>
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
		<div id="popupWrapper" class="userinfo-popup">
			<div id="popupTitle" class="popup-title">
				<p id = "update"><spring:message code="kind_information_edit" /></p>
				<p id = "addNew"><spring:message code="kind_information_add" /></p>
			</div>
			<div class="popup-content">
				<div class="div-content-popup">
					<div class="tbl-box width450 margin-auto">
						<div class="tbl-row">
							<div class="tbl-left width200"><span class="tbl-left-text"><spring:message code="kind_name" />(<span class="color-red">*</span>)</span></div>
							<div class="tbl-right width250"><input id="txtKindIdPopup" type="hidden"><input id="txtKindNamePopup" class="txt-lv3" type="text" value="" name="" maxlength="50"></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left width200"><span class="tbl-left-text"><spring:message code="prospective_Harvest_Amount" />(<span class="color-red">*</span>)</span></div>
							<div class="tbl-right width250"><input id="txtProspectiveHarvestAmountPopup" class="txt-lv3 align-right" type="text" value="" name="" maxlength="8">&nbsp;<spring:message code="kg" /></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left width200"><span class="tbl-left-text"><spring:message code="estimated_Days_Flowering" />(<span class="color-red">*</span>)</span></div>
							<div class="tbl-right width250"><input id="txtEstimatedDaysFloweringPopup" class="txt-lv3 align-right" type="text" value="" name="" maxlength="4"></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left width200"><span class="tbl-left-text"><spring:message code="estimated_Days_Bagging" />(<span class="color-red">*</span>)</span></div>
							<div class="tbl-right width250"><input id="txtEstimatedDaysBaggingPopup" class="txt-lv3 align-right" type="text" value="" name="" maxlength="4"></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left width200"><span class="tbl-left-text"><spring:message code="estimated_Days_Harvest" />(<span class="color-red">*</span>)</span></div>
							<div class="tbl-right width250"><input id="txtEstimatedDaysHarvestPopup" class="txt-lv3 align-right" type="text" value="" name="" maxlength="4"></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left width200"></div>
							<div class="tbl-right width250"><span class="color-red">*</span>: <spring:message code="necessary_items_note" /></div>
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


