<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0037BlockDetailUpdate.css?date=${blockUpdateCssDate}"/>
		<script>
			// get data for combobox
			var processData = ${processData};
			var statusData = ${statusData};
			// variable to store information passes from parents' screens
			var farmIdGlobal = ${farmIdGlobal};
			var farmNameGlobal = ${farmNameGlobal};
			var areaIdGlobal = ${areaIdGlobal};
			var areaNameGlobal = ${areaNameGlobal};
			var blockIdGlobal = ${blockIdGlobal};
			var blockNameGlobal = ${blockNameGlobal};
			var kindIdGlobal = ${kindIdGlobal};
			var kindNameGlobal = ${kindNameGlobal};
			// get line and column count
			var lineCount = ${lineCount};
			var columnCount = ${columnCount};
			var workingDatePopupBlank = '<spring:message code="date" />';
			var cbbProcessPopupBlank = '<spring:message code="column_process" />';
			var cbbTaskPopupBlank = '<spring:message code="column_process" />';
			var cbbStatusPopupBlank = '<spring:message code="status_name" />';
			var MESSAGE_TASK_NULL_ERROR = '<spring:message code="message_task_null_error" />';
		</script>
		<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0037BlockDetailUpdate.js?date=${blockUpdateJsDate}" type="text/javascript"></script>

		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="title_block_detail_result" /></h2>
			</div>
		</div>
		<div class="div-content">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
				<div class="clear"></div>
				<div class="tbl-only-row">
					<div class="text-title-lv3">
						<div class="float-left"><spring:message code="farm_name" />:&nbsp;</div> 
						<div id="lblFarmName" class="width200 float-left overflow-ellipsis-label"></div>
					</div>
					<div class="text-title-lv3">
						<div class="float-left"><spring:message code="area_name" />:&nbsp;</div> 
						<div id="lblAreaName" class="width200 float-left overflow-ellipsis-label"></div>
					</div>
					<div class="text-title-lv3">
						<div class="float-left"><spring:message code="block_name" />:&nbsp;</div> 
						<div id="lblBlockName" class="width200 float-left overflow-ellipsis-label"></div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="cont-box min-height210 margin-top10">
				<div class="div-content-lv2" style="width: 410px;">
					<div class="text-title"><p><spring:message code="product_list" /></p></div>
					<div class="clear"></div>
					<div class="div-farmwork min-height176 padding-left10">
						<button id="btnSelectAll" class="btn-small margin-top10" type="button">
							<spring:message code="select_all" />
						</button>
						<button id="btnDeselectAll" class="btn-small margin-top10 display-none" type="button">
							<spring:message code="deselect_all" />
						</button>
						<div class="clear"></div>
						<div class="tbl-select-area margin-top10">
							<div id="divHeadProduct">
								<table id="tblProductHeader" class="table-farmwork">
								</table>
							</div>
							<div id="divBodyProduct" class="min-height78 overflow-y-scroll">
								<table id="tblProductBody" class="table-farmwork">
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="div-content-lv2" style="width: 574px;">
					<div class="text-title"><p><spring:message code="work_list_product" /></p></div>
					<div class="clear"></div>
					<div class="div-farmwork-right">
						<div id="divHead">
							<table class="tbl-table">
								<colgroup>
									<col width="40">
									<col width="40">
							        <col width="40">
							        <col width="15%">
							        <col width="">
							        <col width="">
							        <col width="15%">
							        <col width="15%">
							        <col width="55">
							        <col width="40">
							        <col width="40">
								</colgroup>
								<tr>
									<th class=""><spring:message code="numberical_order" /></th>
									<th class="overflow-ellipsis">
										<spring:message code="column_line" />
									</th>
									<th class="overflow-ellipsis">
										<spring:message code="column_column" />
									</th>
									<th class="overflow-ellipsis">
										<spring:message code="column_date" />
									</th>
									<th class="overflow-ellipsis">
										<spring:message code="column_process" />
									</th>
									<th class="overflow-ellipsis">
										<spring:message code="column_task" />
									</th>
									<th class="overflow-ellipsis">
										<spring:message code="column_work_content" />
									</th>
									<th class="overflow-ellipsis">
										<spring:message code="column_status" />
									</th>
									<th class="overflow-ellipsis">
										<spring:message code="column_note" />
									</th>
									<th></th>
									<th></th>
								</tr>
							</table>
						</div>
						<div id="divBody" class="min-height153">
							<table id="tblBody" class="tbl-table">
								<colgroup>
									<col width="40">
									<col width="40">
							        <col width="40">
							        <col width="15%">
							        <col width="">
							        <col width="">
							        <col width="15%">
							        <col width="15%">
							        <col width="55">
							        <col width="40">
							        <col width="40">
								</colgroup>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">
			<div class="cont-footer">
				<button id="btnBack" class="btn-medium margin-right10" type="button"><spring:message code="button_back" /></button>
				<button id="btnRegister" class="btn-large margin-right10 btn-disable" disabled="disabled" type="button"><spring:message code="button_block_detail_register" /></button>
			</div>
		</div>

		<!-- Work detail popup screen -->
		<div id="overlay" class="web-popup-overlay"></div>
		<div id="popupWrapper" class="operationDetail-popup">
			<div id="popupTitle" class="popup-title">
				<p id="titlePopupNew"><spring:message code="title_popup_operation_detail_mode_new" /></p>
				<p id="titlePopupDelete"><spring:message code="title_popup_operation_detail_mode_delete" /></p>
				<p id="titlePopupView"><spring:message code="title_popup_operation_detail_mode_view" /></p>
			</div>
			<div class="popup-content">
				<div class="div-content-popup width565 margin-auto">
					<div class="div-content-lv2 margin-top2 margin-bottom0">		
						<div class="tbl-box">			
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="farm_name" /></span>
								</div>
								<div class="width-auto tbl-right">
									<input id="txtFarmNamePopup" class="txt-lv1" type="text" value="" name="" disabled="disabled">
								</div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="area_name" /></span>
								</div>
								<div class="width-auto tbl-right">
									<input id="txtAreaNamePopup" class="txt-lv1" type="text" value="" name="" disabled="disabled">
								</div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="block_name" /></span>
								</div>
								<div class="width-auto tbl-right">
									<input id="txtBlockNamePopup" class="txt-lv1" type="text" value="" name="" disabled="disabled">
								</div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="kind_name" /></span>
								</div>
								<div class="width-auto tbl-right">
									<input id="txtKindNamePopup" class="txt-lv1" type="text" value="" name="" disabled="disabled">
								</div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="date" /></span>
								</div>
								<div class="width-auto tbl-right">
									<input id="txtWorkingDatePopup" class="txt-lv1" type="text" value="" name="" size="10">
								</div>
							</div>
						</div>
					</div>
					<div class="div-content-lv2 margin-top2 margin-bottom0">		
						<div class="tbl-box">			
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="planting_date" /></span>
								</div>
								<div class="width-auto tbl-right">
									<input id="txtPlantDatePopup" class="txt-lv1" type="text" value="" name="" disabled="disabled">
								</div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="flowering_date" /></span>
								</div>
								<div class="width-auto tbl-right">
									<input id="txtFlowerDatePopup" class="txt-lv1" type="text" value="" name="" disabled="disabled">
								</div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="bag_closing_date" /></span>
								</div>
								<div class="width-auto tbl-right">
									<input id="txtBagDatePopup" class="txt-lv1" type="text" value="" name="" disabled="disabled">
								</div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="harvest_date" /></span>
								</div>
								<div class="width-auto tbl-right">
									<input id="txtHarvestDatePopup" class="txt-lv1" type="text" value="" name="" disabled="disabled">
								</div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="shipping_date" /></span>
								</div>
								<div class="width-auto tbl-right">
									<input id="txtShippingDatePopup" class="txt-lv1" type="text" value="" name="" disabled="disabled">
								</div>
							</div>
						</div>
					</div>
					<div class="clear"></div>
					<div class="div-content margin-top2">
						<div class="tbl-box">
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="column_process" /></span>
								</div>
								<div class="width-auto tbl-right">
									<select id="cbbProcessPopup" class="cbx-lv2"></select>
									<span class="tbl-left-text margin-left90"><spring:message code="column_task" /></span>
									<select id="cbbTaskPopup" class="cbx-lv2"></select>
								</div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="column_work_content" /></span>
								</div>
								<div class="tbl-right">
									<textarea id="txtWorkContentPopup" class="txtarea-lv60 height60" rows="4" maxlength="250" disabled="disabled"></textarea>
								</div>
							</div>
							<div class="clear height4"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini"><span class="tbl-left-text"><spring:message code="precaution" /></span></div>
								<div class="tbl-right">
									<textarea id="txtPrecautionPopup" class="txtarea-lv60 height60" rows="4" maxlength="250" disabled="disabled"></textarea>
								</div>
							</div>
							<div class="clear height4"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="status_name" /></span>
								</div>
								<div class="tbl-right">
									<select id="cbbStatusPopup" class="cbx-lv3">
									</select>
								</div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="column_note" /></span>
								</div>
								<div class="tbl-right">
									<textarea id="txtNotePopup" class="txtarea-lv60 height60" rows="4" maxlength="128"></textarea>
								</div>
							</div>
						</div>
					</div>
					<div class="clear height25"></div>
					<div class="group-button-lv2">
						<button id="btnCancelPopup" class="btn-medium" type="button"><spring:message code="button_back" /></button>
						<button id="btnRegisterPopup" class="btn-medium margin-right10" type="button"><spring:message code="button_confirm" /></button>
					</div>
				</div>
			</div>
		</div>

		<!-- Image upload form popup -->
		<div id="overlay2" class="web-popup-overlay2"></div>
		<div id="popupUploadFormWrapper" class="fileupload-popup"></div>
	</tiles:putAttribute>
</tiles:insertDefinition>