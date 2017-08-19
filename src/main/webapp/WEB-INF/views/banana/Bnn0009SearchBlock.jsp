<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0009SearchBlock.css?date=${Bnn0009SearchBlockCss}"/>
		<script>
			var PRODUCT_CHANGE_MESSAGE = '<spring:message code="message_back_confirm_when_change_product_line_col" />';
			// get data for combobox
			var farmData = ${farmData};
			var BLOCK_NAME = '<spring:message code="block_name" />';
		</script>
		<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0009SearchBlock.js?date=${Bnn0009SearchBlockJsDate}" type="text/javascript"></script>

		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="title_block_information_search" /></h2>
			</div>
		</div>
		<div class="div-content">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
				<div class="clear height10"></div>
				
				<div class="tbl-only-row">
					<div class="tbl-line-title"><span class="tbl-left-text"><spring:message code="farm_name" /></span></div>
					<div class="tbl-line-item"><select id="cbbFarmName" class="cbx-lv2"></select></div>
					<div class="tbl-line-title"><span class="tbl-left-text"><spring:message code="area_name" /></span></div>
					<div class="tbl-line-item"><select id="cbbAreaName" class="cbx-lv2" disabled="disabled"></select></div>
					<div class="tbl-line-title"><span class="tbl-left-text"><spring:message code="block_name" /></span></div>
					<div class="tbl-line-item"><select id="cbbBlockId" class="cbx-lv2" disabled="disabled"></select></div>
					<div class="tbl-line-item float-right">
						<button id="btnSearch" class="btn-medium" type="button"><spring:message code="button_search" /></button>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
			<div class="cont-box margin-top20 display-none">
				<p class="text-title"><spring:message code="search_result" />&nbsp;<span id="txtCounts"></span></p>
				<div class="text-title-lv4"><div class="float-left"><spring:message code="farm_name" />:&nbsp;</div>
											<div id="farmNamelbl" class="width145 float-left overflow-ellipsis-label"></div></div>
				<div class="text-title-lv4"><div class="float-left"><spring:message code="area_name" />:&nbsp;</div>
											<div id="areaNamelbl" class="width145 float-left overflow-ellipsis-label"></div></div>
				<div class="div-zentai">
					<div id="divHead">
						<table class="tbl-table tbl-search-result">
							<col width="40">
							<col width="20%">
							<col width="15%">
							<col width="">
							<col width="40">
							<col width="40">
							<tr>
								<th><spring:message code="numberical_order" /></th>
								<th><spring:message code="block_name" /></th>
								<th><spring:message code="last_season" /></th>
								<th><spring:message code="Remarks" /></th>
								<th></th>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
								<th></th>
							</tr>
						</table>
					</div>
					<div id="divBody" class="height400">
						<table id="tblBody" class="tbl-table">
							<col width="40">
							<col width="20%">
							<col width="15%">
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
		<div class="footer">
			<div>
				<div class="group-button">
					<button id="btnBack" class="btn-medium" type="button"><spring:message code="button_back" /></button>
					<button id="btnRegister" class="btn-medium margin-right10" type="button"><spring:message code="button_add" /></button>
				</div>
			</div>
		</div>
		<input type = "hidden" id="farmIdClient" value="${farmIdClient}" />
		<input type = "hidden" id="areaIdClient" value="${areaIdClient}" />
		<input type = "hidden" id="reference" value="<spring:message code="reference" />" />
		<!-- Block info popup screen -->
		<div id="overlay" class="web-popup-overlay"></div>
		<div id="popupWrapper" class="blockinfo-popup">
			<div id="popupTitle" class="popup-title">
				<p id = "updateBlock"><spring:message code="title_popup_block_information_search_edit" /></p>
				<p id = "addNewBlock"><spring:message code="title_popup_block_information_search_add" /></p>
			</div>
			<div class="popup-content">
				<div class="div-content-popup">
					<div class="tbl-box width500 margin-auto">
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="farm_name" /> (<span class="color-red">*</span>)</span></div>
							<input id="txtFarmIdPopup" class="txt-lv4" type="hidden" value="" name="" maxlength="4">
							<div class="tbl-right"><input id="txtFarmNamePopup" class="txt-lv4" type="text" value="" name="" maxlength="16" disabled="disabled"></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="area_name" /> (<span class="color-red">*</span>)</span></div>
							<input id="txtAreaIdPopup" class="txt-lv4" type="hidden" value="" name="" maxlength="4">
							<div class="tbl-right"><input id="txtAreaNamePopup" class="txt-lv4" type="text" value="" name="" maxlength="16" disabled="disabled"></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="block_name" /> (<span class="color-red">*</span>)</span></div>
							<input id="txtBlockIdPopup" class="txt-lv4" type="hidden" value="" name="" maxlength="4">
							<div class="tbl-right"><input id="txtBlockNamePopup" class="txt-lv4" type="text" value="" name="" maxlength="16"></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="note" /></span></div>
							<div class="tbl-right"><textarea id="txtNotePopup" class="txtarea-lv90 height60" rows="4" maxlength="128"></textarea></div>
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

		<!-- Product adjustment popup screen -->
		<div id="popupProductWrapper" class="productAdjust-popup">
			<div id="popupTitle" class="popup-title"><p><spring:message code="title_popup_product_adjustment" /></p></div>
			<div class="popup-content div-content-lv2" style="width: 412px;">
				<div class="clear heigth10"></div>
				<div>
					<button id="selectAll" class="btn-small margin-top20" type="button"><spring:message code="select_all" /></button>
					<button id="unSelectAll" class="btn-small margin-top20 display-none" type="button"><spring:message code="deselect_all" /></button>
				</div>
				<div class="clear"></div>
				<div class="div-content-popup ">
					<div class="height400">
						<div class="tbl-select-area">
							<div id="divHeadProduct">
								<table id="tblProductHeader" class="table-farmwork">
								</table>
							</div>
							<div id="divBodyProduct" class="height350 overflow-y-scroll">
								<table id="tblProductBody" class="table-farmwork">
								</table>
							</div>
						</div>
					</div>
					<div class="clear"></div>
					<div class="group-button-0009 margin-top20">
						<button id="btnCancelProductPopup" class="btn-medium" type="button"><spring:message code="button_back" /></button>
						<button id="btnDisableProductPopup" class="btn-large margin-right10" type="button"><spring:message code="button_confirm" /></button>
						
					</div>
				</div>
			</div>
			<div class="div-content-lv2" style="width: 412px;">
					
					<div class="div-block-guide height480">
						<div class="block-guide-center float-left margin-left20">
							<div class="text-title align-left"><p><spring:message code="user_guide" /></p></div>
							<div class="clear"></div>
							<table class="margin-top21">
								<tr>
									<td style="vertical-align: middle"><div class="div-cr-block-guide padding-right5 float-left">L1</div></td>
									<td><spring:message code="column_line" /></td>
								</tr>
								<tr>
									<td style="vertical-align: middle"><div class="div-cr-block-guide padding-right5 float-left">C1</div></td>
									<td><spring:message code="column_column" /></td>
								</tr>
								<tr>
									<td style="vertical-align: middle"><div class="div-cr-block-guide width15 padding-right5 float-left" style="background-color: darkKhaki;"></div></td>
									<td><spring:message code="rib" /></td>
								</tr>
							</table>
						</div>
						<div class="clear"></div>
						<div class="block-guide-center">
							<table class="table-block-guide width90pc margin-top20">
								<colgroup>
									<col width="30%">
									<col width="">
									<col width="30%">
								</colgroup>
								<tr>
									<th><spring:message code="first" /></th>
									<th><spring:message code="choose" /></th>
									<th><spring:message code="button_confirm" /></th>
								</tr>
								<tr>
									<td><div><img class="block-guide-fostering" alt="" src="${pageContext.request.contextPath}/resources/img/fostering.png"></div><spring:message code="status_enabled" /></td>
									<td><div><img class="block-guide-fostering" alt="" src="${pageContext.request.contextPath}/resources/img/fostering.png"></div><spring:message code="not_choose" /></td>
									<td><div><img class="block-guide-fostering" alt="" src="${pageContext.request.contextPath}/resources/img/fostering.png"></div><spring:message code="stay_active" /></td>
								</tr>
								<tr>
									<td><div><img class="block-guide-fostering" alt="" src="${pageContext.request.contextPath}/resources/img/fostering.png"></div><spring:message code="status_enabled" /></td>
									<td><div><img class="block-guide-fostering_check" alt="" src="${pageContext.request.contextPath}/resources/img/fostering_check.png"></div><spring:message code="choose" /></td>
									<td><div><img class="block-guide-disable" alt="" src="${pageContext.request.contextPath}/resources/img/disable.png"></div><spring:message code="status_disabled" /></td>
								</tr>
								<tr>
									<td><div><img class="block-guide-disable" alt="" src="${pageContext.request.contextPath}/resources/img/disable.png"></div><spring:message code="status_disabled" /></td>
									<td><div><img class="block-guide-disable" alt="" src="${pageContext.request.contextPath}/resources/img/disable.png"></div><spring:message code="not_choose" /></td>
									<td><div><img class="block-guide-disable" alt="" src="${pageContext.request.contextPath}/resources/img/disable.png"></div><spring:message code="still_disable" /></td>
								</tr>
								<tr>
									<td><div><img class="block-guide-disable" alt="" src="${pageContext.request.contextPath}/resources/img/disable.png"></div><spring:message code="status_disabled" /></td>
									<td><div><img class="block-guide-disable_check" alt="" src="${pageContext.request.contextPath}/resources/img/disable_check.png"></div><spring:message code="choose" /></td>
									<td><div><img class="block-guide-fostering" alt="" src="${pageContext.request.contextPath}/resources/img/fostering.png"></div><spring:message code="effective" /></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
		</div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>


