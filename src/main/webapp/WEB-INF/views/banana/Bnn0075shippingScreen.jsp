<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0075ShippingScreen.css?date=${shippingScreenCssDate}"/>
		<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0075ShippingScreen.js?date=${shippingScreenJsDate}" type="text/javascript"></script>
		<script>
			var farmData = ${farmData};
			var shippingRegister = '<spring:message code="title_shipping_screen" />';
			var registerPopup = '<spring:message code="title_shipping_register_popup" />';
			var editPopup = '<spring:message code="title_shipping_edit_popup" />';
			var shippingNumberPopupBlank = '<spring:message code="lot_number" />';
			var shipDatePopupBlank = '<spring:message code="ship_date" />';
		</script>

		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="title_shipping_screen" /></h2>
			</div>
		</div>
		<div class="div-content">
			<div class="tbl-row">
				<div class="float-right"><span id="txtCurrentDate"></span></div>
			</div>
			<div class="clear"></div>
			<div class="cont-box margin-top20">
				<div class="tbl-row">
					<div class="tbl-left-mini width110" ><span class="tbl-left-text"><spring:message code="lot_number" /></span></div>
					<div class="tbl-right width170">	<input class="cal_text txt-lv1" type="text" value="" name="" size="10" id ="shipping_number" maxlength = "8"></div>
				</div>
				<div class="clear"></div>
				<div class="tbl-row">
					<div class="tbl-left-mini width110"><span class="tbl-left-text"><spring:message code="farm_name" /></span></div>
					<div class="tbl-right width170">	<select class="txt-lv2" id ="cbbFarmName">
														</select></div>					
					<div class="tbl-left-mini width125"><span class="tbl-left-text"><spring:message code="harvest_date" /></span></div>
					<div class="tbl-left-mini width67"><span class="tbl-left-text"><spring:message code="start_date" /></span></div>
					<div class="tbl-right width170">	<input class="cal_text txt-lv1" type="text" value="" name="" size="10" id ="harvest_start_date">
														</div>
					
					<div class="tbl-left-mini width90"><span class="tbl-left-text"><spring:message code="end_date" /></span></div>
					<div class="tbl-right width170">	<input class="cal_text txt-lv1" type="text" value="" name="" size="10" id ="harvest_end_date">
														</div>
				</div>
				<div class="clear"></div>
				<div class="tbl-row">
					<div class="tbl-left-mini width110"><span class="tbl-left-text"><spring:message code="area_name" /></span></div>
					<div class="tbl-right width170">	<select class="txt-lv2" id ="cbbAreaName">
														</select></div>
					<div class="tbl-left-mini width125"><span class="tbl-left-text"><spring:message code="ship_date" /></span></div>
					<div class="tbl-left-mini width67"><span class="tbl-left-text"><spring:message code="start_date" /></span></div>
					<div class="tbl-right width170">	<input class="cal_text txt-lv1" type="text" value="" name="" size="10" id ="ship_start_date">
														</div>
					
					<div class="tbl-left-mini width90"><span class="tbl-left-text"><spring:message code="end_date" /></span></div>
					<div class="tbl-right width170">	<input class="cal_text txt-lv1" type="text" value="" name="" size="10" id ="ship_end_date">
														</div>
														
					<div><button class="btn-small float-right margin-right17" type="button" id="btnSearch"><spring:message code="button_search" /></button></div>
				</div>
				<div class="clear"></div>
				<div class="body-delete cont-box margin-top5 display-none">
					<div class="clear"></div>
					<div class="tbl-row">
						<div class=""><span class="tbl-left-text"><spring:message code="search_result" /></span>&nbsp;<span id="txtCounts"></span></div>
					</div>
					<div class="table-box">
						<div class="areaDetail-zentai">
							<div  id="divHead" class="div-header" style="height: 24px! important">
								<table class="tbl-table tbl-search-result">
									<colgroup>
										<col width="40px">
										<col width="10%">
										<col width="20%">
										<col width="20%">
										<col width="15%">
										<col width="15%">
										<col width="">
										<col width="37px">
									</colgroup>
									<tr>
										<th><spring:message code="numberical_order" /></th>
										<th><spring:message code="lot_number" /></th>
										<th><spring:message code="farm_name" /></th>
										<th><spring:message code="area_name" /></th>
										<th><spring:message code="harvest_date" /></th>
										<th><spring:message code="ship_date" /></th>
										<th><spring:message code="shipping_register" /></th>
										<th></th>
									</tr>
								</table>
							</div>
							
							<div id="divBody" class="div-body">
								<table class="tbl-table tbl-shipping-screen-select" id="tblBody">
									<colgroup>
										<col width="40px">
										<col width="10%">
										<col width="20%">
										<col width="20%">
										<col width="15%">
										<col width="15%">
										<col width="">
										<col width="37px">
									</colgroup>
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

							<div class="tblPager pager float-right margin-top5">
								<div class="float-left padding-top2 margin-right20">
									<span id="btnFirst" class="page-number-first"></span>
									<span id="btnPrevious" class="page-number-pre"></span>
									<span id="lblCurrentPage" class="padding-left5 float-left">1</span>
									<span id="lblPageSeperator" class="float-left">/</span>
									<span id="lblMaxPage" class="padding-right5 float-left">1</span>
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
				</div>
			</div>
		</div>
		<div class="footer">
			<div>
				<div class="group-button">
<%-- 					<button id="btnRegister" class="btn-medium margin-right10" type="button"><spring:message code="button_add" /></button> --%>
					<button id="btnBack" class="btn-medium" type="button"><spring:message code="button_back" /></button>
				</div>
			</div>
		</div>
		<!-- User info popup screen -->
		<div id="overlay" class="web-popup-overlay"></div>
		<div class="clear"></div>
		<div id="popupWrapper" class="shipping-info-popup">
			<div id="popupTitle" class="popup-title"><p><spring:message code="title_shipping_screen" /></p></div>
			<div class="popup-content width350 margin-auto">
				<div class="div-content-popup">
					<div class="div-content">
						<div class="tbl-box-popup">
							<div class="tbl-row">
								<div class="tbl-left" ><span class="tbl-left-text"><spring:message code="lot_number" /></span></div>
								<div class="tbl-right">	<input id ="shippingNumberPopup" class="cal_text txt-lv1" type="text" value="2102" name="" size="10" readonly></div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left"><span class="tbl-left-text"><spring:message code="farm_name" />(<span class="color-red">*</span>)</span></div>
								<div class="tbl-right"><select class="txt-lv2" id ="cbbFarmNamePopup">
															</select></div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left"><span class="tbl-left-text"><spring:message code="area_name" />(<span class="color-red">*</span>)</span></div>
								<div class="tbl-right"><select class="txt-lv2" id ="cbbAreaNamePopup">
															</select></div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left"><span class="tbl-left-text"><spring:message code="harvest_date" />(<span class="color-red">*</span>)</span></div>
								<div class="tbl-right"> <input class="cal_text txt-lv1" id="harvestDatePopup" type="text" value="" name="" size="10" readonly>
														</div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left"><span class="tbl-left-text"><spring:message code="ship_date" />(<span class="color-red">*</span>)</span></div>
								<div class="tbl-right"> <input class="cal_text txt-lv1" id="shipDatePopup" type="text" value="" name="" size="10">
														</div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
							<div class="tbl-left"></div>
							<div class="tbl-right"><span class="color-red">*</span>: <spring:message code="necessary_items_note" /></div>
						</div>
						</div>
					</div>
					<div class="group-button-popup">
						<button id="btnCancelPopup" class="btn-medium" type="button"><spring:message code="button_back" /></button>
						<button id ="btnRegisterPopup" class="btn-medium margin-right10" type="button"><spring:message code="button_confirm" /></button>
					</div>
				</div>
			</div>
		</div>
		<div id="popupSearchUser" class="userinfo-popup"></div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>