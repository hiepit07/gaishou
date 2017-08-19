<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0061TraceabilityMaster.css?date=${traceabilityMasterCssDate}"/>
		<script>
			var IMAGE_LANGUAGE = '<spring:message code="image" />';
			var farmData = ${farmData}; 
			var processData = ${processData};
			var taskData = ${taskData};
			var statusData = ${statusData};
			var bnn0075Data = ${bnn0075Data};
		</script>
		<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0061TraceabilityMaster.js?date=${traceabilityMasterJsDate}" type="text/javascript"></script>
		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="title_traceability_master" /></h2>
			</div>
		</div>
		<div class="clear"></div>
		<div class="div-content">
			<div class="tbl-row">
				<div class="float-right"><span id="txtCurrentDate"></span></div>
			</div>
			<div class="clear"></div>
			<div class="cont-box">
				<p><spring:message code="title_crop_infomation" /></p>
				<div class="div-boder width930 height147 float-left">
					<div class="tbl-box margin-top10 margin-bottom10">
						<div class="tbl-only-row">
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="farm_name" /></span></div>
							<div class="tbl-line-item margin-bottom3">	<select id="cbbFarmName" class="cbx-lv2 cbx-h-lv2">
														</select>
							</div>
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="area_name" /></span></div>
							<div class="tbl-line-item">	<select id="cbbAreaName" class="cbx-lv2 cbx-h-lv2">
														</select></div>
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="block_name" /></span></div>
							<div class="tbl-line-item">	<select id="cbbBlockName" class="cbx-lv2 cbx-h-lv2">
														</select></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-only-row">
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="line_id" /></span></div>
							<div class="tbl-line-item margin-bottom3">	<select id="cbbLineNumber" class="cbx-lv2 cbx-h-lv2">
														</select>
							</div>
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="column_id" /></span></div>
							<div class="tbl-line-item"><select id="cbbColumnNumber" class="cbx-lv2 cbx-h-lv2">
														</select></div>
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="season" /></span></div>
							<div class="tbl-line-item">	<select id="cbbSeason" class="cbx-lv2 cbx-h-lv2">
															<option value="-2"></option>
															<option value="0"><spring:message code="current_season" /></option>
															<option value="1"><spring:message code="last_season" /></option>
														</select></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="start_date_code" /></span></div>
							<div class="tbl-right width350">
								<div class="tbl-left-mini width64"><span class="tbl-left-text"><spring:message code="start_date" /></span></div>
								<div id="divCodeStart" class="float-left">
									<input id="txtCodeStart" class="cal_text width63" type="text" value="" name="" maxlength="10">
								</div>
								<div class="tbl-left-mini align-center width85"><span><spring:message code="end_date" /></span></div>
								<div id="divCodeEnd">
									<input id="txtCodeEnd" class="cal_text width63" type="text" value="" name="" maxlength="10">
								</div>
							</div>
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="planting_date" /></span></div>
							<div class="tbl-right width350">
								<div class="tbl-left-mini width64"><span class="tbl-left-text"><spring:message code="start_date" /></span></div>
								<div id="divPlantStart" class="float-left">
									<input id="txtPlantStart" class="cal_text width63" type="text" value="" name="" maxlength="10">
								</div>
								<div class="tbl-left-mini align-center width85"><span><spring:message code="end_date" /></span></div>
								<div id="divPlantEnd">
									<input id="txtPlantEnd" class="cal_text width63" type="text" value="" name="" maxlength="10">
								</div>
							</div>
						</div>
						<div class="clear"></div>
						<div class="tbl-row">
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="flowering_date" /></span></div>
							<div class="tbl-right width350">
								<div class="tbl-left-mini width64"><span class="tbl-left-text"><spring:message code="start_date" /></span></div>
								<div id="divFlowerStart" class="float-left">
									<input id="txtFlowerStart" class="cal_text width63" type="text" value="" name="" maxlength="10">
								</div>
								<div class="tbl-left-mini align-center width85"><span><spring:message code="end_date" /></span></div>
								<div id="divFlowerEnd">
									<input id="txtFlowerEnd" class="cal_text width63" type="text" value="" name="" maxlength="10">
								</div>
							</div>
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="bag_closing_date" /></span></div>
							<div class="tbl-right width350">
								<div class="tbl-left-mini width64"><span class="tbl-left-text"><spring:message code="start_date" /></span></div>
								<div id="divCloseStart" class="float-left">
									<input id="txtCloseStart" class="cal_text width63" type="text" value="" name="" maxlength="10">
								</div>
								<div class="tbl-left-mini align-center width85"><span><spring:message code="end_date" /></span></div>
								<div id="divCloseEnd">
									<input id="txtCloseEnd" class="cal_text width63" type="text" value="" name="" maxlength="10">
								</div>
							</div>
						</div>
						<div class="clear"></div><div class="tbl-row">
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="harvest_date" /></span></div>
							<div class="tbl-right width350">
								<div class="tbl-left-mini width64"><span class="tbl-left-text"><spring:message code="start_date" /></span></div>
								<div id="divHarvestStart" class="float-left">
									<input id="txtHarvestStart" class="cal_text width63" type="text" value="" name="" maxlength="10">
								</div>
								<div class="tbl-left-mini align-center width85"><span><spring:message code="end_date" /></span></div>
								<div id="divHarvestEnd">
									<input id="txtHarvestEnd" class="cal_text width63" type="text" value="" name="" maxlength="10">
								</div>
							</div>
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="shipping_date" /></span></div>
							<div class="tbl-right width350">
								<div class="tbl-left-mini width64"><span class="tbl-left-text"><spring:message code="start_date" /></span></div>
								<div id="divShipStart" class="float-left">
									<input id="txtShipStart" class="cal_text width63" type="text" value="" name="" maxlength="10">
								</div>
								<div class="tbl-left-mini align-center width85"><span><spring:message code="end_date" /></span></div>
								<div id="divShipEnd">
									<input id="txtShipEnd" class="cal_text width63" type="text" value="" name="" maxlength="10">
								</div>
							</div>
						</div>
						<div class="clear"></div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="cont-box margin-top20">
				<p><spring:message code="title_cultivation_work" /></p>
				<div class="div-boder width930 height118 float-left">
					<div class="tbl-box margin-top10 margin-bottom10">
						<div class="tbl-row">
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="column_date" /></span></div>
							<div class="tbl-right width350">
								<div class="tbl-left-mini width64"><span class="tbl-left-text"><spring:message code="start_date" /></span></div>
								<div id="divWorkStart" class="float-left">
									<input id="txtWorkStart" class="cal_text width63" type="text" value="" name="" maxlength="10">
								</div>
								<div class="tbl-left-mini align-center width85"><span><spring:message code="end_date" /></span></div>
								<div id="divWorkEnd">
									<input id="txtWorkEnd" class="cal_text width63" type="text" value="" name="" maxlength="10">
								</div>
							</div>
						</div>
						<div class="clear"></div>
						<div class="tbl-only-row">
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="process_name" /></span></div>
							<div class="tbl-line-item margin-bottom3">	<select id="cbbProcessName" class="cbx-lv2 cbx-h-lv2">
														</select>
							</div>
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="task_name" /></span></div>
							<div class="tbl-line-item"><select id="cbbTaskName" class="cbx-lv2 cbx-h-lv2">
														</select></div>
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="status_name" /></span></div>
							<div class="tbl-line-item">	<select id="cbbStatusName" class="cbx-lv2 cbx-h-lv2">
														</select></div>
						</div>
						<div class="clear"></div>
						<div class="tbl-only-row">
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="unit_work" /></span></div>
							<div class="tbl-line-item margin-bottom3">	<select id="cbbUnitWork" class="cbx-lv2 cbx-h-lv2">
															<option value="-2"></option>
															<option value="0"><spring:message code="banana_unit_work" /></option>
															<option value="1"><spring:message code="block_unit_work" /></option>
														</select>
							</div>
						</div>
						<div class="clear"></div>
						<div class="tbl-only-row">
							<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="column_note" /></span></div>
							<div class="tbl-line-item">
								<input id="txtNote" class="txt-lv4" value="" name="" type="text" maxlength="255">
							</div>
						</div>
						<div class="clear"></div>
					</div>
				</div>
				<button id="btnAccess" class="btn-small margin-top95 margin-left5 width85" type="button"><spring:message code="access" /></button>
			</div>
			<div class="clear"></div>
			<div class="cont-box margin-top20">
				<div class="text-title-lv4"><spring:message code="search_result" />&nbsp;<span id="txtCounts"></span></div>
				<div class="text-title-lv4"><div class="float-left"><spring:message code="name_of_farm" />:&nbsp;</div>
											<div id="spFarmName" class="width145 float-left overflow-ellipsis-label"></div></div>
				<div class="text-title-lv4"><div class="float-left"><spring:message code="name_of_area" />:&nbsp;</div>
											<div id="spAreaName" class="width145 float-left overflow-ellipsis-label"></div></div>
				<div class="clear"></div>
				<div class="div-zentai">
					<div id="divHead" class="div-header">
						<table class="tbl-table tbl-search-result">
							<colgroup>
								<col width="35">
								<col width="10%">
								<col width="50">
								<col width="50">
								<col width="80">
								<col width="10%">
								<col width="15%">
								<col width="10%">
								<col width="">
								<col width="180px">
								<col width="41px">
							</colgroup>
							<tr>
								<th><spring:message code="numberical_order" /></th>
								<th><spring:message code="block_name" /></th>
								<th><spring:message code="column_line" /></th>
								<th><spring:message code="column_column" /></th>
								<th><spring:message code="column_date" /></th>
								<th><spring:message code="column_process" /></th>
								<th><spring:message code="column_task" /></th>
								<th><spring:message code="column_status" /></th>
								<th><spring:message code="column_note" /></th>
								<th rowspan="3"><spring:message code="supplement_document" /></th>
								<th><spring:message code="work_detail" /></th>
							</tr>
						</table>
					</div>
					<div id="divBody" class="div-body min-height61 border-bottom">
						<table id="tblBody" class="tbl-table tbl-search-traceability-result">
							<col width="35">
							<col width="10%">
							<col width="50">
							<col width="50">
							<col width="80">
							<col width="10%">
							<col width="15%">
							<col width="10%">
							<col width="">
							<col width="60">
							<col width="60">
							<col width="60">
							<col width="41">
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
					<button id="btnBack" class="btn-medium margin-right10" type="button"><spring:message code="button_back" /></button>
				</div>
			</div>
		</div>

		<!-- Cultivation detail popup screen -->
		<div id="overlay" class="web-popup-overlay"></div>
		<div id="popupWrapper" class="operationDetail-popup">
			<div id="popupTitle" class="popup-title">
				<p id="titlePopup"><spring:message code="title_traceability_master_popup" /></p>
			</div>
			<div class="popup-content">
				<div class="div-content-popup">
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
									<input id="txtWorkingDatePopup" class="txt-lv1" type="text" value="" name="" maxlength="10" disabled="disabled">
								</div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="column_process" /></span>
								</div>
								<div class="width-auto tbl-right">
									<input id="txtProcessNamePopup" class="txt-lv1 width145" type="text" value="" name="" maxlength="10" disabled="disabled">
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
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="column_task" /></span>
								</div>
								<div class="width-auto tbl-right">
									<input id="txtTaskPopup" class="txt-lv1 width145" type="text" value="" name="" maxlength="10" disabled="disabled">
								</div>
							</div>
						</div>
					</div>
					<div class="clear"></div>
					<div class="div-content margin-top2">
						<div class="tbl-box">
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="column_work_content" /></span>
								</div>
								<div class="tbl-right">
									<textarea id="txtWorkContentPopup" class="txtarea-lv60 height60" rows="4" maxlength="255" disabled="disabled"></textarea>
								</div>
							</div>
							<div class="clear height4"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini"><span class="tbl-left-text"><spring:message code="precaution" /></span></div>
								<div class="tbl-right">
									<textarea id="txtPrecautionPopup" class="txtarea-lv60 height60" rows="4" maxlength="255" disabled="disabled"></textarea>
								</div>
							</div>
							<div class="clear height4"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="status_name" /></span>
								</div>
								<div class="width-auto tbl-right">
									<input id="txtStatusPopup" class="txt-lv2" type="text" value="" name="" maxlength="10" disabled="disabled">
								</div>
							</div>
							<div class="clear"></div>
							<div class="tbl-row">
								<div class="tbl-left-mini">
									<span class="tbl-left-text"><spring:message code="column_note" /></span>
								</div>
								<div class="tbl-right">
									<textarea id="txtNotePopup" class="txtarea-lv60 height60" rows="4" maxlength="255" disabled="disabled"></textarea>
								</div>
							</div>
						</div>
					</div>
					<div class="clear height25"></div>
					<div class="group-button width-auto">
						<button id="btnCancelPopup" class="btn-medium" type="button"><spring:message code="button_back" /></button>
					</div>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>