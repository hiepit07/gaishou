<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0092ProcessMaster.css?date=${processMasterCssDate}"/>
		<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0092ProcessMaster.js?date=${processMasterJsDate}" type="text/javascript"></script>
		<script>
			var PROCESS_CHANGE_MESSAGE = '<spring:message code="process_change_message" />';
			var PROCESS_UNREGISTERED_MESSAGE = '<spring:message code="process_unregistered_message" />';
			var cultivationData = ${cultivationData};
		</script>
		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="title_process_master" /></h2>
			</div>
		</div>
		<div class="div-content">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
				<div class="clear"></div>
				<div class="tbl-only-row">
					<div class="tbl-line-title width125"><span class="tbl-left-text"><spring:message code="farm_name" /></span></div>
					<div class="tbl-line-item">	<input id="txtFarmName" class="txt-lv2" type="text" value="" name="" maxlength="4" readonly="readonly">
												<input id="txtHideFarmId" type="hidden" value="" name=""></div>
					<div class="tbl-line-title width125"><span class="tbl-left-text margin-left20"><spring:message code="kind_name" /></span></div>
					<div class="tbl-line-item">	<input id="txtKindName" class="txt-lv2" type="text" value="" name="" maxlength="4" readonly="readonly">
												<input id="txtHideKindId" type="hidden" value="" name=""></div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="cont-box margin-top20">
				<div class="div-content-table-lv4">
					<div class="text-title height25 "><span><spring:message code="title_process_unregistered_list" /></span></div>
					<div class="clear"></div>
					<div class="areaDetail-zentai">
						<div id="divHead" class="div-header">
							<table class="tbl-table">
								<colgroup>
									<col width="40">
									<col width="">
								</colgroup>
								<tr>
									<th><spring:message code="numberical_order" /></th>
									<th><spring:message code="process_name" /></th>
								</tr>
							</table>
						</div>
						<div id="divBody" class="div-Body min-height225">
							<table id="tblBody" class="tbl-table tbl-table-list tbl-process-un height100pc">
								<colgroup>
									<col width="40">
									<col width="">
								</colgroup>
								<tr>
									<td></td>
									<td></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div class="div-content-button-lv4">
					<div class="marginauto margin-top75"><button id="btn_get" class="btn-get" type="button" value="get" ></button></div>
					<div class="clear"></div>
					<div class="marginauto margin-top25"><button id="btn_getall" class="btn-getall" type="button" value="getAll" ></button></div>
					<div class="clear"></div>
					<div class="marginauto margin-top25"><button id="btn_back" class="btn-back" type="button" value="back" ></button></div>
					<div class="clear"></div>
					<div class="marginauto margin-top25"><button id="btn_backall" class="btn-backall" type="button" value="backAll" ></button></div>
				</div>
				<div class="div-content-table-lv4">
					<div class="text-title-lv2 height25"><span><spring:message code="title_process_registered_list" /></span></div>
					<div class="clear"></div>
					<div class="areaDetail-zentai">
						<div id="divHeadRight" class="div-header">
							<table class="tbl-table">
								<colgroup>
									<col width="40">
									<col width="">
								</colgroup>
								<tr>
									<th><spring:message code="numberical_order" /></th>
									<th><spring:message code="process_name" /></th>
								</tr>
							</table>
						</div>
						<div id="divBodyRight" class="div-Body min-height225">
							<table id="tblBodyRight" class="tbl-table tbl-table-list tbl-process-re height100pc">
								<colgroup>
									<col width="40">
									<col width="">
								</colgroup>
								<tr>
									<td></td>
									<td></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div class="div-content-button-lv4">
					<div class="margin-top75"><button id="btn_up" class="btn-up" type="button" value="up" ></button></div>
					<div class="clear"></div>
					<div class="margin-top25"><button id="btn_first" class="btn-first" type="button" value="first" ></button></div>
					<div class="clear"></div>
					<div class="margin-top25"><button id="btn_last" class="btn-last" type="button" value="last" ></button></div>
					<div class="clear"></div>
					<div class="margin-top25"><button id="btn_down" class="btn-down" type="button" value="down" ></button></div>
				</div>
				<div class="clear"></div>
				<div class="div-content margin-top10 margin-bottom0">
					<div class="text-title-lv2"><p class="text-title"><span><spring:message code="title_cultivation_process_detail" /></span></p></div>
					<div class="clear"></div>
					<div class="areaDetail-zentai">
						<div id="divHeadBottom" class="div-header">
							<table class="tbl-table">
								<colgroup>
									<col width="40">
									<col width="15%">
									<col width="15%">
									<col width="15%">
									<col width="">
									<col width="100">
									<col width="125">
								</colgroup>
								<tr>
									<th><spring:message code="numberical_order" /></th>
									<th><spring:message code="process_name" /></th>
									<th><spring:message code="task_name" /></th>
									<th><spring:message code="working_details" /></th>
									<th><spring:message code="precaution" /></th>
									<th><spring:message code="quarantine_handling" /></th>
									<th><spring:message code="change_point" /></th>
								</tr>
							</table>
						</div>
						<div id="divBodyBottom" class="div-Body height92">
							<table id="tblBodyBottom" class="tbl-table tbl-process-de height100pc">
								<colgroup>
									<col width="40">
									<col width="15%">
									<col width="15%">
									<col width="15%">
									<col width="">
									<col width="100">
									<col width="125">
								</colgroup>
								<tr>
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
				</div>
			</div>
			<div class="footer">
				<div>
					<div class="group-button">
						<button id="btnBack" class="btn-medium margin-right10" type="button"><spring:message code="button_back" /></button>
						<button id="btnRegister" class="btn-medium" type="button"><spring:message code="button_confirm" /></button>
					</div>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>