<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="body">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/banana/Bnn0091CultivationMaster.css?date=${cultivationMasterCssDate}"/>
		<script src="${pageContext.request.contextPath}/resources/js/banana/Bnn0091CultivationMaster.js?date=${cultivationMasterJsDate}" type="text/javascript"></script>
		<script>
			var TASK_CHANGE_MESSAGE = '<spring:message code="task_change_message" />';
			var PROCESS_UNREGISTERED_MESSAGE = '<spring:message code="process_unregistered_message" />';
			var farmData = ${farmData};
			var kindData = ${kindData};
			var processData = ${processData};
		</script>
		<div class="title">
			<div class="title-cont">
				<h2><spring:message code="title_cultivation_master" /></h2>
			</div>
		</div>
		<div class="div-content">
			<div class="tbl-box">
				<div class="tbl-row">
					<div class="float-right"><span id="txtCurrentDate"></span></div>
				</div>
				<div class="clear"></div>
				<div class="tbl-only-row">
					<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="farm_name" /></span></div>
					<div class="tbl-line-item"><select id="cbbFarmName" class="cbx-lv2 cbx-h-lv2">
												</select></div>
					<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="kind_name" /></span></div>
					<div class="tbl-line-item"><select id="cbbKindName" class="cbx-lv2 cbx-h-lv2">
												</select></div>
					<div class="tbl-line-title width110"><span class="tbl-left-text"><spring:message code="process_name" /></span></div>
					<div class="tbl-line-item"><select id="cbbProcessName" class="cbx-lv2 cbx-h-lv2">
												</select></div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="cont-box margin-top20">
				<div class="div-content-table-lv4">
					<div class="text-title height25 "><span><spring:message code="title_task_unregistered_list" /></span></div>
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
									<th><spring:message code="task_name" /></th>
								</tr>
							</table>
						</div>
						<div id="divBody" class="div-Body min-height200">
							<table id="tblBody" class="tbl-table tbl-table-list tbl-task-un height100pc">
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
					<div class="text-title-lv2 width305"><span><spring:message code="title_task_registered_list" /></span></div>
					<div><button id="btnSelectAll" class="btn-small float-right" type="button"><spring:message code="select_all" /></button></div>
					<div class="clear"></div>
					<div class="areaDetail-zentai">
						<div id="divHeadRight" class="div-header">
							<table class="tbl-table">
								<colgroup>
									<col width="40">
									<col width="">
									<col width="65">
								</colgroup>
								<tr>
									<th><spring:message code="numberical_order" /></th>
									<th><spring:message code="task_name" /></th>
									<th><spring:message code="block_flag" /></th>
								</tr>
							</table>
						</div>
						<div id="divBodyRight" class="div-Body min-height200">
							<table id="tblBodyRight" class="tbl-table tbl-table-list tbl-task-re height100pc">
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
				<div class="marginTo10">
					<div class="text-title-lv2"><p class="text-title"><spring:message code="title_cultivation_task_detail" /></p></div>	
					<div class="clear"></div>
					<div class="areaDetail-zentai height117">
						<div id="tblTaskTable" class="table-box height116" style="overflow-y: auto">
							<table id="tblDetail" class="tbl-table tbl-task-de height100pc">
								<colgroup>
									<col width="135">
									<col width="">
								</colgroup>
								<tr id="trName">
									<td><spring:message code="task_name" /></td>
									<td class="overflow-ellipsis"></td>
								</tr>
								<tr id="trContent">
									<td><spring:message code="column_work_content" /></td>
									<td></td>
								</tr>
								<tr id="trNote">
									<td><spring:message code="precaution" /></td>
									<td></td>
								</tr>
								<tr id="trFlag">
									<td><spring:message code="quarantine_handling" /></td>
									<td></td>
								</tr>
								<tr id="trPoint">
									<td><spring:message code="change_point" /></td>
									<td></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">
			<div>
				<div class="group-button">
					<button id="btnBack" class="btn-medium margin-right10" type="button"><spring:message code="button_back" /></button>
					<button id="btnRegister" class="btn-medium margin-right10" type="button"><spring:message code="button_confirm" /></button>
					<button id="btnNext" class="btn-medium" type="button"><spring:message code="button_next" /></button>
				</div>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>