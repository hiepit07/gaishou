<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="vn.bananavietnam.ict.common.cnst.Constants" %>
<!-- Constants -->
<script type="text/javascript">
	// logout confirm text based on languages
	var LOGOUT_MESSAGE = '<spring:message code="message_log_out_confirm" />';
	// dialog values based on languages
	var DIALOG_TITLE = '<spring:message code="dialog_title" />';
	var DIALOG_OK_BUTTON = '<spring:message code="dialog_ok_button" />';
	var DIALOG_YES_BUTTON = '<spring:message code="dialog_yes_button" />';
	var DIALOG_NO_BUTTON = '<spring:message code="dialog_no_button" />';
	// role name value based on languages
	var ROLE_SYSTEM_MANAGER = '<spring:message code="checkbox_authority_system_manager" />';
	var ROLE_BUSINESS_MANAGER = '<spring:message code="checkbox_authority_business_manager" />';
	var ROLE_FARM_MANAGER = '<spring:message code="checkbox_authority_farm_manager" />';
	var ROLE_AREA_MANAGER = '<spring:message code="checkbox_authority_area_manager" />';
	// enable/disable status value based on languages
	var STATUS_ENABLED = '<spring:message code="status_enabled" />';
	var STATUS_DISABLED = '<spring:message code="status_disabled" />';
	// done/none status value based on languages
	var STATUS_DONE = '<spring:message code="status_done" />';
	var STATUS_NONE = '<spring:message code="status_none" />';
	// check/uncheck status value based on languages
	var STATUS_CHECK = '<spring:message code="select_all" />';
	var STATUS_UNCHECK = '<spring:message code="deselect_all" />';
	// alert message based on languages
	var ACCESS_DENIED_MESSAGE = '<spring:message code="message_access_denied" />';
	var ROLE_ADD_DENIED_MESSAGE = '<spring:message code="message_role_add_denied" />';
	var ROLE_UPDAT_DENIED_MESSAGE = '<spring:message code="message_role_updat_denied" />';
	var ROLE_DELET_DENIED_MESSAGE = '<spring:message code="message_role_delet_denied" />';
	var ROLE_REFER_DENIED_MESSAGE = '<spring:message code="message_role_refer_denied" />';
	var ERROR_MESSAGE = '<spring:message code="message_error_generic" />';
	var ERROR_SELECT = '<spring:message code="message_error_select" />';
	var SEARCH_RESULT_OUT_OF_MEMORY_MESSAGE = '<spring:message code="message_search_result_out_of_memory" />';
	var SEARCH_RESULT_NO_DATA_MESSAGE = '<spring:message code="message_search_result_no_data" />';
	var VALIDATE_BLANK_FIELDS_MESSAGE = '<spring:message code="message_validate_blank_fields" />';
	var VALIDATE_BLANK_FIELDS_MESSAGE_SERVER = '<spring:message code="message_validate_blank_fields_server" />';
	var VALIDATE_NUMBER_FIELDS_MESSAGE = '<spring:message code="message_validate_number_fields" />';
	var VALIDATE_PASSWORD_NOT_MATCH_MESSAGE = '<spring:message code="message_validate_password_not_match" />';
	var VALIDATE_PASSWORD_NOT_CORRECT_MESSAGE = '<spring:message code="message_validate_password_not_correct" />';
	var VALIDATE_BLANK_LIST_DATA_MESSAGE = '<spring:message code="message_validate_blank_list_data" />';
	var UPDATE_RESULT_SUCCESSFUL_MESSAGE = '<spring:message code="message_update_result_successful" />';
	var UPDATE_RESULT_SUCCESSFUL_MESSAGE_SHIPPING = '<spring:message code="message_update_result_successful_shipping" />';
	var UPDATE_RESULT_FAILED_MESSAGE = '<spring:message code="message_update_result_failed" />';
	var INSERT_RESULT_SUCCESSFUL_MESSAGE = '<spring:message code="message_insert_result_successful" />';
	var INSERT_RESULT_DUPLICATED_MESSAGE = '<spring:message code="message_insert_result_duplicated" />';
	var INSERT_RESULT_DUPLICATED_BLOCK_MESSAGE = '<spring:message code="message_insert_block_duplicate" />';
	var INSERT_RESULT_CONFIG_NOT_CORRECT_MESSAGE = '<spring:message code="message_insert_result_config_not_correct" />';
	var INSERT_RESULT_FAILED_MESSAGE = '<spring:message code="message_insert_result_failed" />';
	var INSERT_RESULT_FAILED_MANAGER_MESSAGE = '<spring:message code="message_insert_result_failed_manager" />';	
	var DELETE_CONFIRM_MESSAGE = '<spring:message code="message_delete_confirm" />';
	var DELETE_RESULT_SUCCESSFUL_MESSAGE = '<spring:message code="message_delete_result_successful" />';
	var DELETE_RESULT_FAILED_MESSAGE = '<spring:message code="message_delete_result_failed" />';
	var DELETE_RESULT_FAILED_MANAGER_MESSAGE = '<spring:message code="message_delete_result_failed_manager" />';
	var TASK_RESULT_FAILED_LAST_UPDATE_DATE_MESSAGE = '<spring:message code="message_task_result_failed_last_update_date" />';
	var INSERT_FARM_DUPLICATE_MESSAGE = '<spring:message code="message_insert_farm_duplicate" />';
	var UPDATE_FARM_DUPLICATE_MESSAGE = '<spring:message code="message_update_farm_duplicate" />';
	var INSERT_AREA_DUPLICATE_MESSAGE = '<spring:message code="message_insert_area_duplicate" />';
	var UPDATE_AREA_DUPLICATE_MESSAGE = '<spring:message code="message_update_area_duplicate" />';
	var UPDATE_BLOCK_DUPLICATE_MESSAGE = '<spring:message code="message_update_block_duplicate" />';	
	var UPLOAD_IMAGE_WRONG_FILE_TYPED_MESSAGE = '<spring:message code="message_upload_wrong_file_type" />';
	var UPLOAD_IMAGE_RESULT_FAILED_MESSAGE = '<spring:message code="message_upload_result_failed" />';
	var VALIDATE_STARTDATE_LAGER_ENDDATE_MESSAGE = '<spring:message code="message_validate_startdate_lager_enddate_fields" />';
	var DELETE_CONFIRM_MESSAGE_WHEN_DIRTY = '<spring:message code="message_delete_confirm_when_change_data" />';
	var MESSAGE_FORMAT_FILED_IS_DOUBLE = '<spring:message code="message_format_filed_is_double" />';
	var MESSAGE_FORMAT_FILED_IS_SUGAR_COTENT = '<spring:message code="message_format_filed_is_sugar_cotent" />';
	var MESSAGE_FORMAT_FILED_IS_SIZE_OF_PLAN = '<spring:message code="message_format_filed_is_size_of_plan" />';
	var MESSAGE_FORMAT_EMAIL_IS_INVALID = '<spring:message code="message_format_email_is_invalid" />';
	var MESSAGE_SHIPPING_DATE_IS_LARGER_THAN_HARVEST_DATE = '<spring:message code="message_shipping_date_is_larger_than_harvest_date" />';
	var MESSAGE_BLOCK_NUMBER_IS_WRONG_NUMBER_FORMAT = ' <spring:message code="message_block_number_is_wrong_number_format" />';
	
	// day of week based on languages
	var DAY_OF_WEEK = "";
	var currentDay = new Date().getDay();
	if (currentDay == 0) { // Sunday
		DAY_OF_WEEK = '<spring:message code="week_day_sunday" />';
	} else if (currentDay == 1) { // Monday
		DAY_OF_WEEK = '<spring:message code="week_day_monday" />';
	} else if (currentDay == 2) { // Tuesday
		DAY_OF_WEEK = '<spring:message code="week_day_tuesday" />';
	} else if (currentDay == 3) { // Wednesday
		DAY_OF_WEEK = '<spring:message code="week_day_wednesday" />';
	} else if (currentDay == 4) { // Thursday
		DAY_OF_WEEK = '<spring:message code="week_day_thursday" />';
	} else if (currentDay == 5) { // Friday
		DAY_OF_WEEK = '<spring:message code="week_day_friday" />';
	} else if (currentDay == 6) { // Saturday
		DAY_OF_WEEK = '<spring:message code="week_day_saturday" />';
	}
	var STATUS_NONE = '<spring:message code="status_none"/>';
	var START_DATE_CODE = '<spring:message code="start_date_code"/>';
	var PLANTING_DATE = '<spring:message code="planting_date"/>';
	var FLOWERING_DATE = '<spring:message code="flowering_date"/>';
	var BAG_CLOSING_DATE = '<spring:message code="bag_closing_date"/>';
	var HARVEST_DATE = '<spring:message code="harvest_date"/>';
	var ARR_CHANGE_POINT = [STATUS_NONE,START_DATE_CODE,PLANTING_DATE,FLOWERING_DATE,BAG_CLOSING_DATE,HARVEST_DATE];
	LANGUAGE_CURRENT = "${pageContext.response.locale}";
	var SCREEN_LIST_ARRAY = "<security:authentication property='principal.SCREENID' />";
	var SCREEN_LIST_ROLE_ARRAY = "<security:authentication property='principal.AUTHORITIES' />";

	// default cultivation start date
	var DEFAULT_CULTIVATION_START_DATE = convertDataTo('<%=Constants.DEFAULT_CULTIVATION_START_DATE%>');
	// role type value
	var ROLE_TYPE_SYSTEM_MANAGER = '<%=Constants.ROLE_TYPE_SYSTEM_MANAGER%>';
	var ROLE_TYPE_BUSINESS_MANAGER = '<%=Constants.ROLE_TYPE_BUSINESS_MANAGER%>';
	var ROLE_TYPE_FARM_MANAGER = '<%=Constants.ROLE_TYPE_FARM_MANAGER%>';
	var ROLE_TYPE_AREA_MANAGER = '<%=Constants.ROLE_TYPE_AREA_MANAGER%>';
	// default Line Id
	var DEFAULT_LINE_ID = '<%=Constants.DEFAULT_LINE_ID%>';
	// default Column Id
	var DEFAULT_COLUMN_ID = '<%=Constants.DEFAULT_COLUMN_ID%>';
	// default farm Id
	var DEFAULT_FARM_ID = '<%=Constants.DEFAULT_FARM%>';
	// default area Id
	var DEFAULT_AREA_ID = '<%=Constants.DEFAULT_AREA%>';
	// task change point code
	var TASK_CHANGE_POINT_CODE_NONE = '<%=Constants.TASK_CHANGE_POINT_CODE_NONE%>';
	var TASK_CHANGE_POINT_CODE_CULTIVATION_START = '<%=Constants.TASK_CHANGE_POINT_CODE_CULTIVATION_START%>';
	var TASK_CHANGE_POINT_CODE_PLANTING = '<%=Constants.TASK_CHANGE_POINT_CODE_PLANTING%>';
	var TASK_CHANGE_POINT_CODE_FLOWERING = '<%=Constants.TASK_CHANGE_POINT_CODE_FLOWERING%>';
	var TASK_CHANGE_POINT_CODE_BAGGED = '<%=Constants.TASK_CHANGE_POINT_CODE_BAGGED%>';
	var TASK_CHANGE_POINT_CODE_HARVESTED = '<%=Constants.TASK_CHANGE_POINT_CODE_HARVESTED%>';
	// when user selected nothing
	var STATUS_NO_SELECT = '<%=Constants.SEARCH_CONDITION_NO_SELECT%>';
	// when blank fields exist
	var VALIDATE_BLANK_FIELDS = '<%=Constants.VALIDATE_BLANK_FIELDS%>';
	// when user's id is in wrong format
	var VALIDATE_WRONG_FORMAT = '<%=Constants.VALIDATE_WRONG_FORMAT%>';
	// when new password and new password confirm do not match
	var VALIDATE_PASSWORD_NOT_MATCH = '<%=Constants.VALIDATE_PASSWORD_NOT_MATCH%>';
	// when current password is not correct
	var VALIDATE_PASSWORD_NOT_CORRECT = '<%=Constants.VALIDATE_PASSWORD_NOT_CORRECT%>';
	// when updating to DB successfully
	var UPDATE_RESULT_SUCCESSFUL = '<%=Constants.UPDATE_RESULT_SUCCESSFUL%>';
	// when updating to DB failed
	var UPDATE_RESULT_FAILED = '<%=Constants.UPDATE_RESULT_FAILED%>';
	// when updating to DB failed at process
	var UPDATE_RESULT_FAILED_PROCESS = '<%=Constants.UPDATE_RESULT_FAILED_PROCESS%>';
	// when updating to DB failed at date
	var UPDATE_RESULT_FAILED_UPDATE_DATE = '<%=Constants.UPDATE_RESULT_FAILED_UPDATE_DATE%>';
	// when updating to DB failed at kind
	var UPDATE_RESULT_FAILED_KIND = '<%=Constants.UPDATE_RESULT_FAILED_KIND%>';
	// when updating to DB failed at Task
	var UPDATE_RESULT_FAILED_TASK = '<%=Constants.UPDATE_RESULT_FAILED_TASK%>';
	// when updating to DB failed at cultivation process result
	var UPDATE_RESULT_FAILED_CULTIVATION_PROCESS_RESULT = '<%=Constants.UPDATE_RESULT_FAILED_CULTIVATION_PROCESS_RESULT%>';
	// when updating to DB failed at cultivation process with last update is lastest result
	var UPDATE_RESULT_FAILED_CULTIVATION_PROCESS_LAST_UPDATE_DATE_RESULT = '<%=Constants.UPDATE_RESULT_FAILED_CULTIVATION_PROCESS_LAST_UPDATE_DATE_RESULT%>';
	// when updating to DB failed at password
	var UPDATE_RESULT_FAILED_CULTIVATION_PASSWORD = '<%=Constants.UPDATE_RESULT_FAILED_CULTIVATION_PASSWORD%>';
	// when updating to DB failed at user
	var UPDATE_RESULT_FAILED_CULTIVATION_USER = '<%=Constants.UPDATE_RESULT_FAILED_CULTIVATION_USER%>';
	// when updating to DB failed at farm
	var UPDATE_RESULT_FAILED_CULTIVATION_FARM = '<%=Constants.UPDATE_RESULT_FAILED_CULTIVATION_FARM%>';
	// when updating to DB failed at area
	var UPDATE_RESULT_FAILED_CULTIVATION_AREA = '<%=Constants.UPDATE_RESULT_FAILED_CULTIVATION_AREA%>';
	// when updating to DB failed at block
	var UPDATE_RESULT_FAILED_CULTIVATION_BLOCK = '<%=Constants.UPDATE_RESULT_FAILED_CULTIVATION_FARM%>';
	// when updating to DB failed at access authorization
	var UPDATE_RESULT_FAILED_ACCESS_AUTHORIZATION = '<%=Constants.UPDATE_RESULT_FAILED_ACCESS_AUTHORIZATION%>';
	// update update failed dupplicate
	var UPDATE_RESULT_FAILED_DUPLLICATE = '<%=Constants.UPDATE_RESULT_FAILED_DUPLLICATE%>'
	// when updating to DB failed at shipping
	var UPDATE_RESULT_FAILED_CULTIVATION_SHIPPING = '<%=Constants.UPDATE_RESULT_FAILED_CULTIVATION_SHIPPING%>';
	// when updating to DB failed at shipping
	var UPDATE_RESULT_FAILED_CULTIVATION_SHIPPING_UPDATE_DATE = '<%=Constants.UPDATE_RESULT_FAILED_CULTIVATION_SHIPPING_UPDATE_DATE%>';
	// when updating to DB failed at affilation
	var UPDATE_RESULT_FAILED_CULTIVATION_AFFILIATION = '<%=Constants.UPDATE_RESULT_FAILED_CULTIVATION_AFFILIATION%>';
	// when updating to DB failed at exception
	var UPDATE_RESULT_FAILED_EXCEPTION = '<%=Constants.UPDATE_RESULT_FAILED_EXCEPTION%>';
	// when inserting to DB successfully
	var INSERT_RESULT_SUCCESSFUL = '<%=Constants.INSERT_RESULT_SUCCESSFUL%>';
	// when inserting to DB with duplicated id
	var INSERT_RESULT_DUPLICATED = '<%=Constants.INSERT_RESULT_DUPLICATED%>';
	// when inserting to DB with duplicated block id
	var INSERT_RESULT_DUPLICATED_BLOCK = '<%=Constants.INSERT_RESULT_DUPLICATED_BLOCK%>';
	// when inserting to DB with wrong config
	var INSERT_RESULT_CONFIG_NOT_CORRECT = '<%=Constants.INSERT_RESULT_CONFIG_NOT_CORRECT%>';
	// when inserting to DB failed
	var INSERT_RESULT_FAILED = '<%=Constants.INSERT_RESULT_FAILED%>';
	// when inserting to DB failed at product
	var INSERT_RESULT_FAILED_PRODUCT = '<%=Constants.INSERT_RESULT_FAILED_PRODUCT%>';
	// when inserting to DB failed at cultivation
	var INSERT_RESULT_FAILED_CULTIVATION = '<%=Constants.INSERT_RESULT_FAILED_CULTIVATION%>';
	// when inserting to DB failed at process
	var INSERT_RESULT_FAILED_PROCESS = '<%=Constants.INSERT_RESULT_FAILED_PROCESS%>';
	// when inserting to DB failed at kind
	var INSERT_RESULT_FAILED_KIND = '<%=Constants.INSERT_RESULT_FAILED_KIND%>';
	// when inserting to DB failed at kind
	var INSERT_RESULT_FAILED_TASK = '<%=Constants.INSERT_RESULT_FAILED_TASK%>';
	// when inserting to DB failed at cultivation task result
	var INSERT_RESULT_FAILED_CULTIVATION_TASK_RESULT = '<%=Constants.INSERT_RESULT_FAILED_CULTIVATION_TASK_RESULT%>';
	// when inserting to DB failed at cultivation task with last update is lastest result
	var INSERT_RESULT_FAILED_CULTIVATION_TASK_LAST_UPDATE_DATE_RESULT = '<%=Constants.INSERT_RESULT_FAILED_CULTIVATION_TASK_LAST_UPDATE_DATE_RESULT%>';
	// when inserting to DB failed at password
	var INSERT_RESULT_FAILED_CULTIVATION_PASSWORD = '<%=Constants.INSERT_RESULT_FAILED_CULTIVATION_PASSWORD%>';
	// when inserting to DB failed at user
	var INSERT_RESULT_FAILED_CULTIVATION_USER = '<%=Constants.INSERT_RESULT_FAILED_CULTIVATION_USER%>';
	// when inserting to DB failed at farm
	var INSERT_RESULT_FAILED_CULTIVATION_FARM = '<%=Constants.INSERT_RESULT_FAILED_CULTIVATION_FARM%>';
	// when inserting to DB failed at area
	var INSERT_RESULT_FAILED_AREA = '<%=Constants.INSERT_RESULT_FAILED_AREA%>';
	// when inserting to DB failed at area
	var INSERT_RESULT_FAILED_BLOCK = '<%=Constants.INSERT_RESULT_FAILED_BLOCK%>';
	// when inserting to DB failed at area
	var INSERT_RESULT_FAILED_CULTIVATION_ACCESS_AUTHORIZATION = '<%=Constants.INSERT_RESULT_FAILED_CULTIVATION_ACCESS_AUTHORIZATION%>';
	// when inserting to DB failed at shipping
	var INSERT_RESULT_FAILED_CULTIVATION_SHIPPING = '<%=Constants.INSERT_RESULT_FAILED_CULTIVATION_SHIPPING%>';
	// when inserting to DB failed at shipping when record is had
	var INSERT_RESULT_FAILED_CULTIVATION_SHIPPING_RECORD_IS_HAD = '<%=Constants.INSERT_RESULT_FAILED_CULTIVATION_SHIPPING_RECORD_IS_HAD%>';
	// when inserting to DB failed at affiliation
	var INSERT_RESULT_FAILED_CULTIVATION_AFFILIATION = '<%=Constants.INSERT_RESULT_FAILED_CULTIVATION_AFFILIATION%>';
	// when inserting to DB failed at last update date affiliation
	var INSERT_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE = '<%=Constants.INSERT_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE%>';
	// when inserting to DB failed at exception
	var INSERT_RESULT_FAILED_EXCEPTION = '<%=Constants.INSERT_RESULT_FAILED_EXCEPTION%>';	
	// Insert info to DB failed at area manager
	var INSERT_RESULT_FAILED_MANAGER = '<%=Constants.INSERT_RESULT_FAILED_MANAGER%>';	
	// Insert info to DB failed at product update date
	var INSERT_RESULT_FAILED_PRODUCT_UPDATE_DATE = '<%=Constants.INSERT_RESULT_FAILED_PRODUCT_UPDATE_DATE%>';
	// Insert info to DB failed at block delete
	var INSERT_RESULT_FAILED_BLOCK_DELETE = '<%=Constants.INSERT_RESULT_FAILED_BLOCK_DELETE%>';
	// when deleting from DB successfully
	var DELETE_RESULT_SUCCESSFUL = '<%=Constants.DELETE_RESULT_SUCCESSFUL%>';
	// when deleting from DB failed
	var DELETE_RESULT_FAILED = '<%=Constants.DELETE_RESULT_FAILED%>';
	// when deleting to DB failed at process
	var DELETE_RESULT_FAILED_PROCESS = '<%=Constants.DELETE_RESULT_FAILED_PROCESS%>';
	// when deleting to DB failed at kind
	var DELETE_RESULT_FAILED_KIND = '<%=Constants.DELETE_RESULT_FAILED_KIND%>';
	// when deleting to DB failed at Task
	var DELETE_RESULT_FAILED_TASK = '<%=Constants.DELETE_RESULT_FAILED_TASK%>';
	// when updating to DB failed at passwrod
	var DELETE_RESULT_FAILED_CULTIVATION_PASSWORD = '<%=Constants.DELETE_RESULT_FAILED_CULTIVATION_PASSWORD%>';
	// when updating to DB failed at user
	var DELETE_RESULT_FAILED_CULTIVATION_USER = '<%=Constants.DELETE_RESULT_FAILED_CULTIVATION_USER%>';
	// when updating to DB failed at farm
	var DELETE_RESULT_FAILED_CULTIVATION_FARM = '<%=Constants.DELETE_RESULT_FAILED_CULTIVATION_FARM%>';
	// when updating to DB failed at area
	var DELETE_RESULT_FAILED_CULTIVATION_AREA = '<%=Constants.DELETE_RESULT_FAILED_CULTIVATION_AREA%>';
	// when updating to DB failed at block
	var DELETE_RESULT_FAILED_CULTIVATION_BLOCK = '<%=Constants.DELETE_RESULT_FAILED_CULTIVATION_BLOCK%>';
	// when updating to DB failed at access authorization
	var DELETE_RESULT_FAILED_CULTIVATION_ACCESS_AUTHORIZATION = '<%=Constants.DELETE_RESULT_FAILED_CULTIVATION_ACCESS_AUTHORIZATION%>';
	
	// when updating to DB failed at shipping
	var DELETE_RESULT_FAILED_CULTIVATION_SHIPPING = '<%=Constants.DELETE_RESULT_FAILED_CULTIVATION_SHIPPING%>';
	// when updating to DB failed at affiliation
	var DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION = '<%=Constants.DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION%>';
	// when updating to DB failed at last update date affiliation
	var DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE = '<%=Constants.DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE%>';
	// when deleting to DB failed at exception
	var DELETE_RESULT_FAILED_EXCEPTION = '<%=Constants.DELETE_RESULT_FAILED_EXCEPTION%>';	
	// when deleting to DB failed at exception
	var DELETE_RESULT_FAILED_CULTIVATION = '<%=Constants.DELETE_RESULT_FAILED_CULTIVATION%>';
	// when deleting to DB failed at exception
	var DELETE_RESULT_FAILED_PRODUCT = '<%=Constants.DELETE_RESULT_FAILED_PRODUCT%>';
	// when deleting to DB failed at exception
	var DELETE_RESULT_FAILED_PRODUCT_UPDATE_DATE = '<%=Constants.DELETE_RESULT_FAILED_PRODUCT_UPDATE_DATE%>';
	var DELETE_RESULT_FAILED_CULTIVATION_UPDATE_DATE = '<%=Constants.DELETE_RESULT_FAILED_CULTIVATION_UPDATE_DATE%>';
	// when deleting to DB failed at date
	var DELETE_RESULT_FAILED_UPDATE_DATE = '<%=Constants.DELETE_RESULT_FAILED_UPDATE_DATE%>';
	// Delete info to DB failed at area manager
	var DELETE_RESULT_FAILED_MANAGER = '<%=Constants.DELETE_RESULT_FAILED_MANAGER%>';
	// when uploading file to server successfully
	var UPLOAD_RESULT_SUCCESSFUL = '<%=Constants.UPLOAD_RESULT_SUCCESSFUL%>';
	// when uploading file to server failed
	var UPLOAD_RESULT_FAILED = '<%=Constants.UPLOAD_RESULT_FAILED%>';
	// when uploading to DB failed at exception
	var UPLOAD_RESULT_FAILED_EXCEPTION = '<%=Constants.UPLOAD_RESULT_FAILED_EXCEPTION%>';
	// when get data exception
	var ERROR_MESSAGE_EXCEPTION = '<%=Constants.ERROR_MESSAGE_EXCEPTION%>';
	// when get task data but not register yet 
	var ERROR_MESSAGE_TASK_NULL = '<%=Constants.ERROR_MESSAGE_TASK_NULL%>';
	// when get data exception at client
	var ERROR_MESSAGE_EXCEPTION_CLIENT = '<%=Constants.ERROR_MESSAGE_EXCEPTION_CLIENT%>';
	// when write file error
    var WRITE_FILE_EXCEPTION = '<%=Constants.WRITE_FILE_EXCEPTION%>';
	
	var SCREEN_DISPLAY_ENABLE_FLAG = '<%=Constants.SCREEN_DISPLAY_ENABLE_FLAG%>';
	var ADDABLE_FLAG = '<%=Constants.ADDABLE_FLAG%>';
	var UPDATABLE_FLAG = '<%=Constants.UPDATABLE_FLAG%>';
	var DELETABLE_FLAG = '<%=Constants.DELETABLE_FLAG%>';
	var REFERENCE_FLAG = '<%=Constants.REFERENCE_FLAG%>';
	
	var holeDate = ${holeDate};
	var hole_checkDate = ${hole_checkDate};
	var plantedDate = ${plantedDate};
	var planted_checkDate = ${planted_checkDate};
	var fosteringDate = ${fosteringDate};
	var fostering_checkDate = ${fostering_checkDate};
	var flowerDate = ${flowerDate};
	var flower_checkDate = ${flower_checkDate};			
	var baggedDate = ${baggedDate};
	var bagged_checkDate = ${bagged_checkDate};
	var cultivatedDate = ${cultivatedDate};
	var cultivated_checkDate = ${cultivated_checkDate};
	var disableDate = ${disableDate};
	var disable_checkDate = ${disable_checkDate};
	var removedDate = ${removedDate};
	var icon_editDate = ${icon_editDate};
	var icon_refDate = ${icon_refDate};
	var icon_delDate = ${icon_delDate};
	var none_editDate = ${none_editDate};
	
	$(document).ready(function() {
		var linkTaskManagement = "";
		var linkTrace = "0061";
		var linkTaskProcess = "0045";
		var linkShippingScreen = "0075";
		var strContextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));

		//do not change button in password change screen
		if (window.location.pathname.indexOf("/0002/") <= 0) {
			if (window.location.pathname.indexOf("/0001/") > 0 
					|| window.location.pathname.indexOf("/0003/") > 0
					|| window.location.pathname.indexOf("/0005/") > 0
					|| window.location.pathname.indexOf("/0007/") > 0
					|| window.location.pathname.indexOf("/0009/") > 0
					|| window.location.pathname.indexOf("/0017/") > 0
					|| window.location.pathname.indexOf("/0013/") > 0
					|| window.location.pathname.indexOf("/0089/") > 0
					|| window.location.pathname.indexOf("/0087/") > 0
					|| window.location.pathname.indexOf("/0091/") > 0
					|| window.location.pathname.indexOf("/0092/") > 0
					|| window.location.pathname.indexOf("/0095/") > 0) {
				$(".btnAdmin").show();
				$(".btnCommon").hide();
			} else {
				$(".btnAdmin").hide();
				$(".btnCommon").show();
			}
		}
	<security:authorize ifAllGranted="4">
		linkTaskManagement = "0035";
	</security:authorize>
		
	<security:authorize ifAllGranted="3">
		linkTaskManagement = "0049";
	</security:authorize>

	<security:authorize ifAnyGranted="1,2">		
		var linkGeneralSetting = "0001";
		linkTaskManagement = "0047";
		
		//Go to Search User page
		$('#btnGeneralSetting').click(function() {
			// check authority of user
			if (!checkAuthority(linkGeneralSetting)) {
				// display message
				jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// create parameter object
				var parameterObj = {
					"url": strContextPath + "/" + linkGeneralSetting + "/"
				};
				// save to history
				savePageToHistory(parameterObj);
				window.location = strContextPath + "/" + linkGeneralSetting + "/";
			}
		});	
	</security:authorize>
	
		//Go to Search User page
		$('#btnTaskManagement').click(function() {
			// check authority of user
			if (!checkAuthority(linkTaskManagement)) {
				// display message
				jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// create parameter object
				var parameterObj = {
					"url": strContextPath + "/" + linkTaskManagement + "/"
				};
				// save to history
				savePageToHistory(parameterObj);
				window.location = strContextPath + "/" + linkTaskManagement + "/";
			}
		});
		//Go to Search User page
		$('#btnTrace').click(function() {
			// check authority of user
			if (!checkAuthority(linkTrace)) {
				// display message
				jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// create parameter object
				var parameterObj = {
					"url": strContextPath + "/" + linkTrace + "/"
				};
				// save to history
				savePageToHistory(parameterObj);
				window.location = strContextPath + "/" + linkTrace + "/";
			}
		});
		//Go to Search User page
		$('#btnTaskProcess').click(function() {
			// check authority of user
			if (!checkAuthority(linkTaskProcess)) {
				// display message
				jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// create parameter object
				var parameterObj = {
					"url": strContextPath + "/" + linkTaskProcess + "/"
				};
				// save to history
				savePageToHistory(parameterObj);
				window.location = strContextPath + "/" + linkTaskProcess + "/";
			}
		});
		//Go to Search User page
		$('#btnShippingScreen').click(function() {
			// check authority of user
			if (!checkAuthority(linkShippingScreen)) {
				// display message
				jWarning(ACCESS_DENIED_MESSAGE, DIALOG_TITLE, DIALOG_OK_BUTTON);
			} else {
				// create parameter object
				var parameterObj = {
					"url": strContextPath + "/" + linkShippingScreen + "/"
				};
				// save to history
				savePageToHistory(parameterObj);
				window.location = strContextPath + "/" + linkShippingScreen + "/";
			}
		});
	})
</script>

<div class="header">
	<div class="header-bg">
		<div class="header-cont">
			<div class="header-left"><img class="logo" src="${pageContext.request.contextPath}/resources/img/img_hv_logo.png" alt="VNB"></div>
			<div class="header-left header-title"><h1>SYSTEM CERES</h1></div>
			<div class="header-right">
				<div class="person-g drop-menu">
					<ul>
						<li>
							<a id="person-username" href="#">
								<security:authentication property="principal.USERFULLNAME" /> ▼
							</a>
							<ul>
								<li><a href="0002" id="linkChangePassword">
										<spring:message code="menu_change_password" />
									</a>
								</li>
								<li><a href="#"><spring:message code="menu_display_guide" /></a></li>
								<li><a href="${pageContext.request.contextPath}/login" id="linkLogout">
										<spring:message code="menu_log_out" />
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="header-group-button">
	<div class="header-group-button-cont">
		<div class="group-button-lv5">
			<security:authorize ifAnyGranted="1,2">
   				<button id='btnGeneralSetting' class="btn-large-lv2 btnAdmin" value="" style="display: none;"><spring:message code="button_general_settings" /></button>
			</security:authorize>
			<button id='btnTaskManagement' class="btn-large-lv2 btnCommon" value="" style="display: none;"><spring:message code="button_task_management" /></button>
			<button id='btnTrace' class="btn-large-lv2 btnCommon" value="" style="display: none;"><spring:message code="button_trace" /></button>
			<button id='btnTaskProcess' class="btn-large-lv2 btnCommon" value="" style="display: none;"><spring:message code="button_task_process" /></button>
			<button id='btnShippingScreen' class="btn-large-lv2 btnCommon" value="" style="display: none;"><spring:message code="title_shipping_screen" /></button>
		</div>
	</div>
</div>