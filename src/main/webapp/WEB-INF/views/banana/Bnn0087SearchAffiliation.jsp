<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0087SearchAffiliation.css?date=${searchAffiliationCssDate}"/>
	<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0087SearchAffiliation.js?date=${searchAffiliationJsDate}" type="text/javascript"></script>
	<script>
		var affiliationData = ${affiliationData};
		var cbbAffilNamePopupBlank = '<spring:message code="permision_name" />';
		var plantListBlank = '<spring:message code="plant_list" />';
		var areaListBlank = '<spring:message code="area_list" />';
	</script>
		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="title_managed" /></h2>
			</div>
		</div>
		<div class="clear"></div>
		<div class="div-content">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
				<div class="clear"></div>
				<div class="tbl-only-row">
					<div class="tbl-left"><span class="tbl-left-text"><spring:message code="user_name" /></span></div>
					<div class="tbl-line-item"><input id="txtUsersName" type="text" maxlength="40" ></div>
					
					<div class="tbl-line-title"><span class="tbl-left-text"><spring:message code="permision_name" /></span></div>
					<div class="tbl-line-item">
						<select id ="cbbAffilName" class="cbx-lv2 cbx-h-lv2">
						</select>
					</div>
						
					<div class="tbl-line-item float-right">
						<button class="btn-small" type="button" id = "btnSearch"><spring:message code="button_search" /></button>
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="cont-box margin-top20 display-none">
				<p class="text-title"><spring:message code="search_result" />&nbsp;<span id="txtCounts"></span></p>
					<div class="div-zentai">
					<div id="divHead">
					<table class="tbl-table tbl-search-result">
						<colgroup>
							<col width="5%">
							<col width="35%">
							<col width="50%">
							<col width="5%">
							<col width="5%">
						</colgroup>
						<tr>
							<th><spring:message code="numberical_order" /></th>
							<th><spring:message code="user_name" /></th>
							<th><spring:message code="permision_name" /></th>
							<th></th>
							<th></th>
						</tr>
					</table>
				</div>
				<div id="divBody" class="div-body">
					<table id="tblBody" class="tbl-table">
						<colgroup>
							<col width="5%">
							<col width="35%">
							<col width="50%">
							<col width="5%">
							<col width="5%">
						</colgroup>
						<tr>
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
		<div class="footer">
			<div>
				<div class="group-button">
					<button id="btnBack" class="btn-medium" type="button"><spring:message code="button_back" /></button>
				</div>
			</div>
		</div>
		<div id="overlay" class="web-popup-overlay"></div>
		<div id="popupWrapper" class="affiliationDetail">
			<div id="popupTitle" class="popup-title"><p><spring:message code="title_affiliation" /></p></div>
				<div class="popup_content">
				<div class="div-content-popup">
						<div class="tbl-box margin-top20">
							<div class="text-title-lv3"><div class="tbl-left-text float-left"><spring:message code="user_name" />:&nbsp;</div>
										<div id="userNameEdit" class="width200 float-left overflow-ellipsis-label"></div></div>
							<div class="text-title-lv3">
									<div class="tbl-line-title"><span class="tbl-left-text"><spring:message code="permision_name" /></span></div>
									<div class="tbl-line-item">	<select  id="cbbAffilNamePopup"  class="cbx-lv2 cbx-h-lv2">
																</select></div>
							</div>
							<div id="showhide" >
							<div class="clear"></div>
							<div class="div-content-lv2 margin-top10">
								<div class="text-title height25 margin-bottom5 "><p><spring:message code="plant_list" /></p></div>
								<div class="clear"></div>
									<div class="areaDetail-zentai">
										<div id="divHeadPopup" class="div-header">
											<table class="tbl-table">
											<colgroup>
												<col width="40">
												<col width="">
												<col width="65">
											</colgroup>
											<tr>
												<th><spring:message code="numberical_order" /></th>
												<th><spring:message code="farm_name" /></th>
												<th><spring:message code="managed_object" /></th>
											</tr>
										</table>
										</div>
										<div id="divBodyPopup" class="div-Body height464px">
											<table id="tblBodyPopup" class="tbl-table tbl-search-affiliation-detail-plant-result">
												<colgroup>
													<col width="40">
													<col width="">
													<col width="65">
												</colgroup>
												<tr>
													<td></td>
													<td></td>
													<td></td>
												</tr>
											</table>
										</div>
									</div>
							</div>
							<div id="areaShowHide" class="div-content-lv2 margin-top10">
								<div class="text-title-lv2"><p><spring:message code="area_list" /></p></div>
								<div>
									<button id = "selectAll" class="btn-small width67 float-right margin-right17 margin-bottom5" type="button"><spring:message code="select_all" /></button>
									<button id = "unSelectAll" class="btn-small width67 float-right margin-right17 margin-bottom5 display-none" type="button"><spring:message code="deselect_all" /></button>
								</div>
								<div class="clear"></div>
								<div class="areaDetail-zentai">
									<div id="divHeadRightPopup" class="div-header">
										<table class="tbl-table">
											<colgroup>
												<col width="40">
												<col width="">
												<col width="25%">
												<col width="25%">
												<col width="65">
											</colgroup>
											<tr>
												<th><spring:message code="numberical_order" /></th>
												<th><spring:message code="farm_name" /></th>
												<th><spring:message code="area_name" /></th>
												<th><spring:message code="area_manager_name" /></th>
												<th><spring:message code="managed_object" /></th>
											</tr>
										</table>
									</div>
									<div id="divBodyRightPopup" class="div-Body border-bottom height464px">
										<table id="tblBodyRightPopup" class="tbl-table tbl-search-affiliation-detail-area-result">
											<colgroup>
												<col width="40">
												<col width="">
												<col width="25%">
												<col width="25%">
												<col width="65">
											</colgroup>
											<tr>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
							</div>
						</div>
					</div>
					<div class="clear height25"></div>
					<div class="group-button-lv2">
						<button id="btn-cancel" class="btn-medium" type="button"><spring:message code="button_back" /></button>
						<button id="btn-submit" class="btn-medium" type="button"><spring:message code="button_confirm" /></button>
					</div>
				</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>