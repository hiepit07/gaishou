<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div id="popup_searchUser">
	<div id="popupTitle" class="popup-title"><p><spring:message code="title_user_information_search" /></p></div>
	<div id="searchUser" class="popup-content">
		<div class="div-content-popup height390">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="tbl-left"><span class="tbl-left-text"><spring:message code="user_id" /></span></div>
					<div class="tbl-right"><input id="txtScUserPupUsersId" class="txt-lv3" type="text" value="" name="" maxlength="11"></div>
				</div>
				<div class="clear"></div>
				<div class="tbl-row">
					<div class="tbl-left"><span class="tbl-left-text"><spring:message code="user_name" /></span></div>
					<div class="tbl-right width60pc"><input id="txtScUserPupUsersName" class="txt-lv4" type="text" value="" name="" maxlength="40"></div>
					<div><button id="btnScUserPupSearch" class="btn-small float-right margin-right17" type="button"><spring:message code="button_search" /></button></div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="cont-box cont-box-popup display-none">
				<p class="text-title"><spring:message code="search_result" />&nbsp;<span id="txtScUserPupCounts"></span></p>
				<div class="div-zentai">
					<div id="divHead" class="div-header">
						<table class="tbl-table tbl-search-result">
							<colgroup>
								<col width="40px">
								<col width="125">
								<col width="">
							</colgroup>
							<tr>
								<th><spring:message code="numberical_order" /></th>
								<th><spring:message code="user_id" /></th>
								<th><spring:message code="user_name" /></th>
							</tr>
						</table>
					</div>
					<div id="divBody" class="div-body">
						<table id="tblBody" class="tbl-user-popup-table tbl-table">
							<colgroup>
								<col width="40px">
								<col width="125">
								<col width="">
							</colgroup>
							<tr>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="tblPager pager float-right margin-top5">
					<div class="float-left padding-top2 margin-right20">
						<span id="btnScUserPupFirst" class="page-number-first"></span>
						<span id="btnScUserPupPrevious" class="page-number-pre"></span>
						<span id="lblScUserPupCurrentPage" class="padding-left5 float-left">0</span>
						<span id="lblScUserPupPageSeperator" class="float-left">/</span>
						<span id="lblScUserPupMaxPage" class="padding-right5 float-left">0</span>
						<span id="btnScUserPupNext" class="page-number-next"></span>
						<span id="btnScUserPupLast" class="page-number-last"></span>
					</div>
					<div class="float-left width125">
						<input id="txtScUserPupGoToPage" class="width40" type="text" maxlength="5"/>
						<input id="btnScUserPupGoToPage" class="btn_pager" type="button" value="<spring:message code="button_pager" />" />
					</div>
				</div>
			</div>
		</div>
		<div class="clear height20"></div>
		<div class="group-button-lv2">
			<button id="btnScUserPupBack" class="btn-medium" type="button"><spring:message code="button_back" /></button>
			<button id="btnScUserPupSelect" class="btn-medium margin-right10" type="button"><spring:message code="button_select" /></button>
		</div>
	</div>
</div>