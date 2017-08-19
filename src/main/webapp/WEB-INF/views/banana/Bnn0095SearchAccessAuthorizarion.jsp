<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<script type="text/javascript">
	var SCREENID = '<spring:message code="screen_id" />';
	var ACCESSAUTHORITYID = '<spring:message code="access_authority_id" />';
</script>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0095SearchAccessAuthorization.css?date=${Bnn0095SearchAccessAuthorizationCss}"/>
	<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0095SearchAccessAuthorization.js?date=${Bnn0095SearchAccessAuthorizationJsDate}" type="text/javascript"></script>
		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="access_authority_search" /></h2>
			</div>
		</div>
		<div class="div-content">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
				<div class="clear"></div>
				<div class="tbl-only-row">
					<div class="tbl-left"><span class="tbl-left-text"><spring:message code="access_authority_id" /></span></div>
					<div class="tbl-right">
						<input id="txtAccessAuthorityId" class="txt-lv1" type="text" value="" name="" maxlength="1" />
					</div>
				</div>
				<div class="clear"></div>
				<div class="tbl-only-row">
					<div class="tbl-left"><span class="tbl-left-text"><spring:message code="screen_id" /></span></div>
					<div class="tbl-right">
						<input id="txtScreenId" class="txt-lv1" type="text" value="" name="" maxlength="4" />
					</div>
				</div>
				<div class="clear"></div>
				<div class="tbl-only-row">
					<div class="float-left width-auto">
						<span class="tbl-left-text margin-right10"><spring:message code="screen_display_enable_flag" /></span>
						<select id="cbbScreenDisplayEnableFlag" class="cbx-lv0">
							<option value="-2"></option>
							<option value="1"><spring:message code="status_enabled" /></option>
							<option value="0"><spring:message code="status_disabled" /></option>
						</select>
						
						<span class="margin-left10 tbl-left-text margin-right10"><spring:message code="addable_flag" /></span>
						<select id="cbbAddableFlag" class="cbx-lv0">
							<option value="-2"></option>
							<option value="1"><spring:message code="status_enabled" /></option>
							<option value="0"><spring:message code="status_disabled" /></option>
						</select>
						
						<span class="margin-left10 tbl-left-text margin-right10"><spring:message code="updateable_flag" /></span>
						<select id="cbbUpdatableFlag" class="cbx-lv0">
							<option value="-2"></option>
							<option value="1"><spring:message code="status_enabled" /></option>
							<option value="0"><spring:message code="status_disabled" /></option>
						</select>
						
						<span class="margin-left10 tbl-left-text margin-right10"><spring:message code="deleteable_flag" /></span>
						<select id="cbbDeleteableFlag" class="cbx-lv0">
							<option value="-2"></option>
							<option value="1"><spring:message code="status_enabled" /></option>
							<option value="0"><spring:message code="status_disabled" /></option>
						</select>
						
						<span class="margin-left10 tbl-left-text margin-right10"><spring:message code="referenceable_flag" /></span>
						<select id="cbbReferenceableFlag" class="cbx-lv0">
							<option value="-2"></option>
							<option value="1"><spring:message code="status_enabled" /></option>
							<option value="0"><spring:message code="status_disabled" /></option>
						</select>
					</div>					
					<button id="btnSearch" class="btn-small float-right" type="button"><spring:message code="button_search" /></button>
				</div>
			</div>
			<div class="clear"></div>
			<div class="cont-box display-none">
				<p class="text-title"><spring:message code="search_result" />&nbsp;<span id="txtCounts"></span></p>
				<div class="div-zentai">
					<div id="divHead" class="div-header">
						<table class="tbl-table tbl-search-result">
							<col width="40">
							<col width="125">
							<col width="">
							<col width="125">
							<col width="125">
							<col width="125">
							<col width="125">
							<col width="125">
							<col width="40">
							<col width="40">
							<tr>
								<th><spring:message code="numberical_order" /></th>
								<th><spring:message code="access_authority_id" /></th>
								<th><spring:message code="screen_id" /></th>
								<th><spring:message code="screen_display_enable_flag" /></th>
								<th><spring:message code="addable_flag" /></th>
								<th><spring:message code="updateable_flag" /></th>
								<th><spring:message code="deleteable_flag" /></th>
								<th><spring:message code="referenceable_flag" /></th>
								<th></th>
								<th></th>
							</tr>
						</table>
					</div>
					<div id="divBody" class="div-body height400">
						<table id="tblBody" class="tbl-table">
							<col width="40">
							<col width="125">
							<col width="">
							<col width="125">
							<col width="125">
							<col width="125">
							<col width="125">
							<col width="125">
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
				<p id = "update"><spring:message code="title_popup_access_edit" /></p>
				<p id = "addNew"><spring:message code="title_popup_access_add" /></p>
			</div>
			<div class="height20"></div>
			<div class="popup-content width600 margin-auto">
				<div class="div-content-popup">
					<div class="tbl-box">
						<div class="tbl-row">
							<div class="tbl-left width170"><span class="tbl-left-text"><spring:message code="access_authority_id" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><input id="txtAccessAuthorityIdPopup" class="txt-lv3" type="text" maxlength="1" value="" name=""></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left width170"><span class="tbl-left-text"><spring:message code="screen_id" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><input id="txtScreenIdPopup" class="txt-lv3" type="text" maxlength="4" value="" name=""></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<table style="margin-left: 100px;">
								<col width="18%">
								<col width="10%">
								<col width="20%">
								<col width="10%">
								<col width="20%">
								<col>
								<tr>
									<td><span class="tbl-left-text align-right"><spring:message code="screen_display_enable_flag" /></span></td>
									<td><input id="chbScreenDisplayEnableFlagPopup" class="" type="checkbox" value="" name="flag"></td>
									<td><span class="tbl-left-text margin-left10 margin-right10 align-right"><spring:message code="addable_flag" /></span></td>
									<td><input id="chbAddableFlagPopup" class="" type="checkbox" value="" name="flag"></td>
									<td><span class="tbl-left-text margin-left10 margin-right10 align-right"><spring:message code="updateable_flag" /></span></td>
									<td><input id="chbUpdatableFlagPopup" class="" type="checkbox" value="" name="flag"></td>
								</tr>
								<tr>
									<td><span class="tbl-left-text align-right"><spring:message code="deleteable_flag" /></span></td>
									<td><input id="chbDeletableFlagPopup" class="" type="checkbox" value="" name="flag"></td>
									<td><span class="tbl-left-text margin-left10 margin-right10 align-right"><spring:message code="referenceable_flag" /></span></td>
									<td><input id="chbRefernceFlagPopup" class="" type="checkbox" value="" name="flag"></td>
									<td></td>
									<td></td>
								</tr>
							</table>
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


