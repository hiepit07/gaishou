<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0002ChangePassword.js?date=${changePasswordJsDate}" type="text/javascript"></script>
		
		<script>
			var oldPasswordBlank = '<spring:message code="old_password" />';
			var newPasswordBlank = '<spring:message code="new_password" />';
			var newPasswordConfirmBlank = '<spring:message code="new_password_confirm" />';
		</script>
		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="menu_change_password" /></h2>
			</div>
		</div>
		<div class="div-content">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
				<div class="clear"></div>
				<div class="width600 margin-auto">
					<div class="tbl-row">
						<div class="tbl-left width180"><span class="tbl-left-text"><spring:message code="old_password" /> (<span class="color-red">*</span>)</span></div>
						<div class="tbl-right"><input id="txtOldPassword" class="txt-lv4" type="password" value="" name="" maxlength="40"></div>
					</div>
					<div class="clear"></div>
					<div class="tbl-row">
						<div class="tbl-left width180"><span class="tbl-left-text"><spring:message code="new_password" /> (<span class="color-red">*</span>)</span></div>
						<div class="tbl-right"><input id="txtNewPassword" class="txt-lv4" type="password" value="" name="" maxlength="40">
						</div>
					</div>
					<div class="clear"></div>
					<div class="tbl-row">
						<div class="tbl-left width180"><span class="tbl-left-text"><spring:message code="new_password_confirm" /> (<span class="color-red">*</span>)</span></div>
						<div class="tbl-right"><input id="txtNewPasswordConfirm" class="txt-lv4" type="password" value="" name="" maxlength="40">
						</div>
					</div>
					<div class="clear height10"></div>
					<div class="tbl-row">
						<div class="tbl-left width180"></div>
						<div class="tbl-right"><span class="color-red">*</span>: <spring:message code="necessary_items_note" /></div>
					</div>
					<div class="clear"></div>
				</div>
				<div class="group-button margin-top20 align-center">
					<button id="btnChange" class="btn-medium margin-right10" type="button"><spring:message code="button_change" /></button>
					<button class="btn-medium" type="button" onclick="javascript: history.back();"><spring:message code="button_back" /></button>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>