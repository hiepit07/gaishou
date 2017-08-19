<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
		
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0007SearchArea.css?date=${Bnn0007SearchAreaCss}"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0023SearchUserPopup.css?date=${Bnn0023SearchUserPopupCss}"/>
	<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0023SearchUserPopup.js?date=${Bnn0023SearchUserPopupJsDate}" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0007SearchArea.js?date=${Bnn0007SearchAreaJsDate}" type="text/javascript"></script>
	<script>
		var farmData = ${farmData};
		var kindData = ${kindData};
		var NUMBER_OF_BLOCK = '<spring:message code="number_of_block" />';
		var AREA_NAME = '<spring:message code="area_name" />';
		var KIND_NAME = '<spring:message code="kind_name" />';
		var PROSPECTIVE_HARVEST_AMOUNT = '<spring:message code="prospective_Harvest_Amount" />';
		var ESTIMATED_DAYS_FLOWERING = '<spring:message code="estimated_Days_Flowering" />';
		var ESTIMATED_DAYS_BAGGING = '<spring:message code="estimated_Days_Bagging" />';
		var ESTIMATED_DAYS_HARVEST = '<spring:message code="estimated_Days_Harvest" />';
		var SUGAR_CONTENT = '<spring:message code="sugar_content" />';
		var TEXTURE = '<spring:message code="texture" />';
	</script>
	
	
		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="area_information_search" /></h2>
			</div>
		</div>
		<div class="div-content">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
				<div class="clear height20"></div>
				<div class="tbl-only-row">
					<div class="tbl-line-title"><span class="tbl-left-text"><spring:message code="farm_name" /></span></div>
					<div class="tbl-line-item"><select class="cbx-lv3 cbx-h-lv4" id="cbbFarmName">
												</select></div>
					<div class="tbl-line-title"><span class="tbl-left-text"><spring:message code="area_name" /></span></div>
					<div class="tbl-line-item"><select class="cbx-lv3 cbx-h-lv4" id="cbbAreaName">
												</select></div>
					<div class="tbl-line-item float-right">
						<button class="btn-small" type="button" id = "btnSearch"><spring:message code="button_search" /></button>
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="cont-box margin-top20 display-none">
				<p class="text-title"><spring:message code="search_result" />&nbsp;<span id="txtCounts"></span></p>
				<div class="text-title"><div class="float-left"><spring:message code="farm_name" />:&nbsp;</div>
										<div id="farmNamelbl" class="width200 float-left overflow-ellipsis-label"></div></div>
				<div class="div-zentai">
					<div id="divHead" class="div-header">
						<table class="tbl-table tbl-search-result">
							<col width="40px">
							<col width="8%">
							<col width="8%">
							<col width="8%">
							<col width="8%">
							<col width="8%">
							<col width="10%">
							<col width="12%">
							<col width="12%">
							<col width="">
							<col width="40">
							<col width="40">
							<tr>
								<th rowspan="2"><spring:message code="numberical_order" /></th>
								<th rowspan="2"><spring:message code="area_name" /></th>
								<th rowspan="2"><spring:message code="area_manager" /></th>
								<th rowspan="2"><spring:message code="kind_name" /></th>
								<th rowspan="2"><spring:message code="sugar_content_brix" /></th>
								<th rowspan="2"><spring:message code="texture" /></th>
								<th rowspan="2"><spring:message code="prospective_Harvest_Amount" /></th>
								<th colspan="3"><spring:message code="estimated_Days" /></th>
								<th rowspan="2"></th>
								<th rowspan="2"></th>
							</tr>
							<tr>
								<th class="overflow-ellipsis"><spring:message code="flowering" /></th>
								<th class="overflow-ellipsis"><spring:message code="bagging" /></th>
								<th class="overflow-ellipsis"><spring:message code="harvest" /></th>
							</tr>
						</table>
					</div>
					<div id="divBody" class="div-body height400">
						<table id="tblBody" class="tbl-table">
							<col width="40">
							<col width="8%">
							<col width="8%">
							<col width="8%">
							<col width="8%">
							<col width="8%">
							<col width="10%">
							<col width="12%">
							<col width="12%">
							<col width="">
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
					<div class="float-left btnPage">
						<input id="txtGoToPage" class="width40" type="text" maxlength="5"/>
						<input id="btnGoToPage" class="btn_pager" type="button" value="<spring:message code="button_pager" />" />
					</div>
				</div>
			</div>
		</div>
		<input type = "hidden" id = "farmIdClient" value = "${farmIdClient}" /> 
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
				<p id = "update"><spring:message code="title_popup_area_information_edit" /></p>
				<p id = "addNew"><spring:message code="title_popup_area_information_add" /></p>
			</div>
			<div class="height20"></div>
			<div class="popup-content">
				<div class="div-content-popup">
					<div class="tbl-box width670 margin-auto">
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="farm_name" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right">
								<input id="txtFarmNamePopup" class="txt-lv3" type="text" value="" name="" maxlength="16" disabled="disabled" />
								<input id="txtFarmIdPopup" class="txt-lv3" type="hidden" value="" name="" />
							</div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text overflow-ellipsis-label"><spring:message code="area_name" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right">
								<input id="txtAreaNamePopup" class="txt-lv3" type="text" value="" name="" maxlength="16" />
								<input id="txtAreaIdPopup" class="txt-lv3" type="hidden" value="" name="" />
							</div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="area_manager" /></span></div>
							<div class="tbl-right">							
								<span class="float-left margin-right10"><spring:message code="user_id" /></span>
								<input id="txtUsersIdPopup"  class="txt-lv0 txt-lvt-btn txt-lvs-btn float-left margin-right10" type="text" value="" name="" readonly="readonly">
								<input id="txtNewUsersIdPopup" type="hidden" value="" name="">
								<input id="txtUsersNamePopup" type="hidden" value="" name="" readonly="readonly">
								<button id="loadUserPopup" class="btn-search-clear margin-right10 float-left" type="button"><spring:message code="button_search" /></button>
								<div class="float-left"><spring:message code="user_name" />:&nbsp;</div>
								<div id="textUsersNamePopup" class="width10pc float-left overflow-ellipsis-label"></div>
							</div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text overflow-ellipsis-label"><spring:message code="number_of_block" />(<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><input id="txtNumberOfBlockPopup" class="txt-lv1 align-right" type="text" value="" name="" maxlength="2" /></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text overflow-ellipsis-label"><spring:message code="kind_name" />(<span class="color-red">*</span>)</span></div>
							<div class="tbl-right">
								<select id="cbbKindNamePopup" class="cbx-lv3">
								</select>
							</div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left width10"></div>
							<div class="tbl-left width40"><span class="tbl-left-text overflow-ellipsis-label"><spring:message code="prospective_Harvest_Amount" />(<span class="color-red">*</span>)</span></div>
							<div class="tbl-right width50"><input id="txtProspectiveHarvestAmountPopup" class="txt-lv1 align-right" type="text" value="" name="" maxlength="8" /></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left width10"></div>
							<div class="tbl-left width40"><span class="tbl-left-text overflow-ellipsis-label"><spring:message code="estimated_Days_Flowering" />(<span class="color-red">*</span>)</span></div>
							<div class="tbl-right width50"><input id="txtEstimatedDaysFloweringPopup" class="txt-lv1 align-right" type="text" value="" name="" maxlength="3"/></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left width10"></div>
							<div class="tbl-left width40"><span class="tbl-left-text overflow-ellipsis-label"><spring:message code="estimated_Days_Bagging" />(<span class="color-red">*</span>)</span></div>
							<div class="tbl-right width50"><input id="txtEstimatedDaysBaggingPopup" class="txt-lv1 align-right" type="text" value="" name="" maxlength="3"/></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left width10"></div>
							<div class="tbl-left width40"><span class="tbl-left-text overflow-ellipsis-label"><spring:message code="estimated_Days_Harvest" />(<span class="color-red">*</span>)</span></div>
							<div class="tbl-right width50"><input id="txtEstimatedDaysHarvestPopup" class="txt-lv1 align-right" type="text" value="" name="" maxlength="3"/></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text overflow-ellipsis-label"><spring:message code="sugar_content" />(<span class="color-red">*</span>)</span></div>
							<div class="tbl-right">
								<input id="txtSugarContentPopup" class="txt-lv1 float-left align-right" type="text" value="" name="" maxlength="4"/>
								<span class="float-left margin-left10"><spring:message code="brix" /></span>
							</div>
						</div>
						<div class="clear height10"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text overflow-ellipsis-label"><spring:message code="texture" />(<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><textarea id="txtTexturePopup" class="txtarea-lv90 " rows="4" maxlength="128"></textarea></div>
						</div>
						<div class="clear height10"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text overflow-ellipsis-label"><spring:message code="note" /></span></div>
							<div class="tbl-right"><textarea id="txtNotePopup" class="txtarea-lv90" rows="4" maxlength="128"></textarea></div>
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
		<div id="popupSearchUser" class="userinfo-popup"></div>
	</tiles:putAttribute>
</tiles:insertDefinition>


