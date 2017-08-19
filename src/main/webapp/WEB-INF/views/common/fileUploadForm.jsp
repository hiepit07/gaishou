<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">	
		<div id="popupFileUpload" class="popup-content">		
			<form:form id="uploadForm" action="upload" method="POST" modelAttribute="myForm" enctype="multipart/form-data">	
				<div class="popup-title"><p><spring:message code="title_popup_upload" /></p></div>
				<div class="popup-content txt_width99">
					<div class="height60">
						<div class="margin-top10">
							<spring:message code="upload_form_select_file" /><br>
							<spring:message code="upload_form_file_type" />
						</div>
						<div>
							<input id="file" type="file" name="file">
 						</div>
 						<div class="group-button-lv3 margin-top10">
							<button id="btnUploadUploadForm" class="btn-large margin-right10" type="button"> 
								<spring:message code="button_upload" />
							</button>
							<button id="btnCancelUploadForm" class="btn-medium" type="button" name="button">
								<spring:message code="button_cancel" />
							</button>
						</div>
					</div>
				</div>
				<div id="upload"></div>
			</form:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>