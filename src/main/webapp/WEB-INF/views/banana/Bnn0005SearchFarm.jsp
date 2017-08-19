<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0005SearchFarm.css?date=${searchFarmCssDate}"/>
		<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0005SearchFarm.js?date=${searchFarmJsDate}" type="text/javascript"></script>
		<script>
			var farmData = ${farmData};
			var farmNamePopupBlank = '<spring:message code="farm_name" />';
			var addressPopupBlank = '<spring:message code="farm_address" />';
			var timePopupBlank = '<spring:message code="farm_time" />';
			var openDatePopupBlank = '<spring:message code="farm_open" />';
			var sizeOfPlanPopupBlank = '<spring:message code="farm_size" />';
			var lineOfPlanPopupBlank = '<spring:message code="number_lines" />';
			var columnOfPlanPopupBlank = '<spring:message code="number_columns" />';
			var climatePopupBlank = '<spring:message code="climate" />';
			var soilPopupBlank = '<spring:message code="soil" />';
			var emailPopupBlank = '<spring:message code="farm_mail" />';
			var phonePopupBlank = '<spring:message code="farm_phone" />';
			var faxPopupBlank = '<spring:message code="farm_fax" />';
		</script>
		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="title_farm_information_search" /></h2>
			</div>
		</div>
		<div class="div-content">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
				<div class="clear height20"></div>
				<div class="tbl-only-row">												
					<div class="tbl-left"><span class="tbl-left-text"><spring:message code="farm_name" /></span></div>
					<div class="tbl-line-item"><select class="cbx-lv3 cbx-h-lv4" id="cbbFarmName">
											</select></div>												
					<div class="tbl-line-item float-right">
						<button class="btn-small" type="button" id = "btnSearch"><spring:message code="button_search" /></button>
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="cont-box display-none">
				<p class="text-title"><spring:message code="search_result" />&nbsp;<span id="txtCounts"></span></p>
				<div class="div-zentai">
					<div id="divHead" class="div-header">
						<table class="tbl-table tbl-search-result">
							<colgroup>
								<col width="35px">
								<col width="9%">
								<col width="60px">
								<col width="60px">
								<col width="">
								<col width="8%">
								<col width="8%">
								<col width="9%">
								<col width="9%">
								<col width="9%">
								<col width="6%">
								<col width="40px">
								<col width="40px">
							</colgroup>
							<tr>
								<th rowspan="2"><spring:message code="numberical_order" /></th>
								<th rowspan="2"><spring:message code="farm_name" /></th>
								<th rowspan="2"><spring:message code="number_lines" /></th>
								<th rowspan="2"><spring:message code="number_columns" /></th>
								<th rowspan="2"><spring:message code="farm_address" /></th>
								<th rowspan="2"><spring:message code="climate" /></th>
								<th rowspan="2"><spring:message code="soil" /></th>
								<th rowspan="2"><spring:message code="farm_open" /></th>
								<th colspan="2"><spring:message code="farm_time" /></th>
								<th rowspan="2"><spring:message code="farm_size_ha" /></th>
								<th rowspan="2"></th>
								<th rowspan="2"></th>
							</tr>
							<tr>
								<th><spring:message code="from" /></th>
								<th><spring:message code="to" /></th>
							</tr>
						</table>
					</div>
					<div id="divBody" class="div-body height400">
						<table id="tblBody" class="tbl-farm-table tbl-table">
							<colgroup>
								<col width="35px">
								<col width="9%">
								<col width="60px">
								<col width="60px">
								<col width="">
								<col width="8%">
								<col width="8%">
								<col width="9%">
								<col width="9%">
								<col width="9%">
								<col width="6%">
								<col width="40px">
								<col width="40px">
							</colgroup>
							<tr>
								<td>100</td>
								<td>ホーチミン</td>
								<td>100</td>
								<td>100</td>
								<td></td>
								<td>モンスーン</td>
								<td>火山灰質</td>
								<td>30/12/2020</td>
								<td>30/12/2020</td>
								<td>30/12/2020</td>
								<td>1500</td>
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
					<button id="btnNew" class="btn-medium margin-right10" type="button"><spring:message code="button_new" /></button>
				</div>
			</div>
		</div>

		<!-- Farm info popup screen -->
		<div id="overlay" class="web-popup-overlay"></div>
		<div id="popupWrapper" class="farminfo-popup">
			<div id="popupTitle" class="popup-title">
				<p id = "update"><spring:message code="title_farm_information_edit" /></p>
				<p id = "addNew"><spring:message code="title_farm_information_add" /></p>
			</div>
			<div class="popup-content">
				<div class="div-content-popup">
					<div class="tbl-box width565 margin-auto">
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="farm_name" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right">	<input id="hddFarmIdPopup" type="hidden" value="" name="">
													<input id="txtFarmNamePopup" class="txt-lv4" type="text" value="" name="" maxlength="256"></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="farm_address" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><input id="txtAddressPopup" class="txt-lv4" type="text" value="" name="" maxlength="256"></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="farm_time" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right">
								<div class="float-left">
									<input id="txtTimeFromPopup" class="txt-lv1" type="text" value="" name="">
								</div>
								<span class="float-left">&nbsp;&nbsp;～&nbsp;&nbsp;</span>
								<div class="float-left">
									<input id="txtTimeToPopup" class="txt-lv1" type="text" value="" name="">
								</div>
							</div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="farm_open" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right">
								<div class="float-left">
									<input id="txtOpenDatePopup" class="txt-lv1" type="text" value="" name="" >
								</div>
							</div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="farm_size" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><input class="align-right" id="txtSizeOfPlanPopup" class="txt-lv2 txt-lvt txt-lvs" type="text" maxlength="6" value="" name="">&nbsp;ha</div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="number_lines" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><input class="align-right" id="txtLineOfPlanPopup" class="txt-lv2 txt-lvt txt-lvs" type="text" maxlength="1" value="" name=""></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="number_columns" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><input class="align-right" id="txtColumnOfPlanPopup" class="txt-lv2 txt-lvt txt-lvs" type="text" maxlength="2" value="" name=""></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="climate" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><textarea id="txtClimatePopup" class="txtarea-lv90 margin-bottom3" rows="2" maxlength="256"></textarea></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="soil" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><textarea id="txtSoilPopup" class="txtarea-lv90 margin-bottom3" rows="2" maxlength="256"></textarea></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="farm_mail" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><input id="txtEmailPopup" class="txt-lv4" type="text" value="" name="" maxlength="256"></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="farm_phone" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><input id="txtPhonePopup" class="txt-lv3" type="text" maxlength="12" value="" name=""></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="farm_fax" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><input id="txtFaxPopup" class="txt-lv3" type="text" maxlength="12" value="" name=""></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="note" /></span></div>
							<div class="tbl-right"><textarea id="txtNotePopup" class="txtarea-lv90" rows="2" maxlength="128"></textarea></div>
						</div>
						<div class="clear height10"></div>
						<div class="tbl-row">
							<div class="tbl-left"></div>
							<div class="tbl-right"><span class="color-red">*</span>: <spring:message code="necessary_items_note" /></div>
						</div>
						<div class="clear height10"></div>
						<div class="group-button-popup">
							<button id="btnCancelFarmPopup" class="btn-medium" type="button"><spring:message code="button_back" /></button>
							<button id="btnRegisterFarmPopup" class="btn-medium margin-right10" type="button"><spring:message code="button_confirm" /></button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- User info popup screen -->
		<div id="overlaySearchUser" class="web-popup-overlay"></div>
		<div id="popupSearchUser" class="userinfo-popup"></div>
	</tiles:putAttribute>
</tiles:insertDefinition>