<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0003SearchUser.css?date=${searchUserCssDate}"/>
		<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0003SearchUser.js?date=${searchUserJsDate}" type="text/javascript"></script>
		
		<script>
			var usersIdPopupBlank = '<spring:message code="user_id" />';
			var usersNamePopupBlank = '<spring:message code="user_name" />';
			var passwordPopupBlank = '<spring:message code="password" />';
		</script>
		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="title_user_information_search" /></h2>
			</div>
		</div>
		<div class="div-content">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
				<div class="clear"></div>
				<div class="tbl-only-row">
					<div class="tbl-left"><span class="tbl-left-text"><spring:message code="user_id" /></span></div>
					<div class="tbl-right"><input id="txtUsersId" class="txt-lv3" type="text" value="" name="" maxlength="11"></div>
				</div>
				<div class="clear"></div>
				<div class="tbl-only-row">
					<div class="tbl-left"><span class="tbl-left-text"><spring:message code="user_name" /></span></div>
					<div class="tbl-right width-auto"><input id="txtUsersName" class="txt-lv4" type="text" value="" name="" maxlength="40"></div>
					<button id="btnSearch" class="btn-small float-right margin-right280" type="button"><spring:message code="button_search" /></button>
				</div>
				
			</div>
			<div class="clear"></div>
			<div class="cont-box margin-top20 display-none">
				<p class="text-title"><spring:message code="search_result" />&nbsp;<span id="txtCounts"></span></p>
				<div class="div-zentai">
					<div id="divHead">
						<table class="tbl-table tbl-search-result">
							<col width="40">
							<col width="15%">
							<col width="">
							<col width="40">
							<col width="40">
							<tr>
								<th><spring:message code="numberical_order" /></th>
								<th><spring:message code="user_id" /></th>
								<th><spring:message code="user_name" /></th>
								<th></th>
								<th></th>
							</tr>
						</table>
					</div>
					<div id="divBody" class="height400">
						<table id="tblBody" class="tbl-table">
							<col width="40">
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
					<button id="btnRegister" class="btn-medium margin-right10" type="button"><spring:message code="button_add" /></button>
				</div>
			</div>
		</div>

		<!-- User info popup screen -->
		<div id="overlay" class="web-popup-overlay"></div>
		<div id="popupWrapper" class="userinfo-popup">
			<div id="popupTitle" class="popup-title">
				<p id = "update"><spring:message code="title_popup_user_information_edit" /></p>
				<p id = "addNew"><spring:message code="title_popup_user_information_add" /></p>
			</div>
			<div class="popup-content width500 margin-auto">
				<div class="div-content-popup">
					<div class="tbl-box">
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="user_id" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><input id="txtUsersIdPopup" class="txt-lv3" type="text" value="" name="" maxlength="11"></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="user_name" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><input id="txtUsersNamePopup" class="txt-lv4" type="text" value="" name="" maxlength="40"></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-left"><span class="tbl-left-text"><spring:message code="password" /> (<span class="color-red">*</span>)</span></div>
							<div class="tbl-right"><input id="txtPasswordPopup" class="txt-lv4" type="password" value="" name="" maxlength="11"></div>
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


