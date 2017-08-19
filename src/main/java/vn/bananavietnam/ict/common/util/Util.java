package vn.bananavietnam.ict.common.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
public class Util {
	
	/**
	 * Get information of logged in user
	 * 
	 * @return
	 */
	public MyUser getUserInfo() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		MyUser myUser = (MyUser) principal;
		return myUser;
	}

	/**
	 * Replace special characters
	 * 
	 * @param str
	 * @return
	 */
	public String convertSanitize(String str) {
		if (str == null) {
			return str;
		}
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll("'", "&#39;");
		str = str.replaceAll("%", "&#37;");
		return str;
	}

	/**
	 * Replace special characters
	 * 
	 * @param str
	 * @return
	 */
	public String convertUnsanitize(String str) {
		if (str == null) {
			return str;
		}
		str = str.replaceAll("&#39;", "'");
		str = str.replaceAll("&quot;", "\"");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&amp;", "&");
		str = str.replaceAll("&#37;", "%");
		return str;
	}
	
	/**
	 * Get farm id conform login rights
	 * @param utilDao
	 * @return farm data
	 */
	public List<UtilComponent> getFarmData(UtilDao utilDao) {
		String roleId = getUserInfo().getROLEID();
		String userId = getUserInfo().getID();
		HashMap<String, Object> params = new HashMap<String, Object>();
        // User id
        params.put("usersId", userId);
        if(roleId.equals(Constants.SYSTEM_MANAGER) || roleId.equals(Constants.CCO)){
        	return utilDao.getUtilMapper().selectFarmDataMaster();
        }else{
        	return utilDao.getUtilMapper().selectFarmData(params);
        }
	}
	
	/**
	 * Get area id conform login rights and farm id
	 * @param utilDao
	 * @param farmId: 
	 * @return area data
	 */
	public List<UtilComponent> getAreaDataByFarmId(UtilDao utilDao, String farmId){
		String roleId = getUserInfo().getROLEID();
		String userId = getUserInfo().getID();
		HashMap<String, Object> params = new HashMap<String, Object>();
        // User id
        params.put("usersId", userId);
        params.put("farmId", farmId);
        if(roleId.equals(Constants.SYSTEM_MANAGER) || roleId.equals(Constants.CCO)|| roleId.equals(Constants.FARM_MANAGER)){
        	return utilDao.getUtilMapper().selectAreaDataByFarmIdMaster(params);
        } else {
        	return utilDao.getUtilMapper().selectAreaDataByFarmId(params);
        }
	}

	/**
	 * Get last modified date of CSS and Javascript files in common folder, then set to client
	 * 
	 * @param request : servlet request
	 * @param model : jsp model
	 */
	public void setDateParamForCommonFiles(HttpServletRequest request, Model model) {
		// define folder path to common CSS files
		String pathCSS = request.getSession().getServletContext().getRealPath("/resources/css/");
		// define folder path to common Javascript files
		String pathJS = request.getSession().getServletContext().getRealPath("/resources/js/common/");
		// define folder path to common icon files
		String pathICon = request.getSession().getServletContext().getRealPath("/resources/img/");
		
		// set date for CSS common files
		model.addAttribute("resetDate", getFileLastModified(pathCSS + "reset.css"));
		model.addAttribute("layoutDate", getFileLastModified(pathCSS + "layout.css"));
		model.addAttribute("jqueryUiCssDate", getFileLastModified(pathCSS + "jquery-ui.min.css"));
		model.addAttribute("messageHandlerDate", getFileLastModified(pathCSS + "messageHandler.css"));
		// set date for Javascript common files
		model.addAttribute("jqueryDate", getFileLastModified(pathJS + "jquery-1.11.1.js"));
		model.addAttribute("jqueryUiDate", getFileLastModified(pathJS + "jquery-ui.min.js"));
		model.addAttribute("datepickerDate", getFileLastModified(pathJS + "jquery.ui.datepicker-jp.js"));
		model.addAttribute("inputmaskDate", getFileLastModified(pathJS + "inputmask.js"));
		model.addAttribute("jqueryInputmaskDate", getFileLastModified(pathJS + "jquery.inputmask.js"));
		model.addAttribute("inputMaskExtDate", getFileLastModified(pathJS + "inputmask.date.extensions.js"));
		model.addAttribute("calendarDate", getFileLastModified(pathJS + "load_calendar.js"));
		model.addAttribute("messageDate", getFileLastModified(pathJS + "load_message.js"));
		model.addAttribute("storageDate", getFileLastModified(pathJS + "jstorage.js"));
		model.addAttribute("commonDate", getFileLastModified(pathJS + "common.js"));
		
		// set date for icon common files
		model.addAttribute("holeDate", "\"" + getFileLastModified(pathICon + "hole.png") + "\"");
		model.addAttribute("hole_checkDate", "\"" + getFileLastModified(pathICon + "hole_check.png") + "\"");
		model.addAttribute("plantedDate", "\"" + getFileLastModified(pathICon + "planted.png") + "\"");
		model.addAttribute("planted_checkDate", "\"" + getFileLastModified(pathICon + "planted_check.png") + "\"");
		model.addAttribute("fosteringDate", "\"" + getFileLastModified(pathICon + "fostering.png") + "\"");
		model.addAttribute("fostering_checkDate", "\"" + getFileLastModified(pathICon + "fostering_check.png") + "\"");
		model.addAttribute("flowerDate", "\"" + getFileLastModified(pathICon + "flower.png") + "\"");
		model.addAttribute("flower_checkDate", "\"" + getFileLastModified(pathICon + "flower_check.png") + "\"");
		model.addAttribute("baggedDate", "\"" + getFileLastModified(pathICon + "bagged.png") + "\"");
		model.addAttribute("bagged_checkDate", "\"" + getFileLastModified(pathICon + "bagged_check.png") + "\"");
		model.addAttribute("cultivatedDate", "\"" + getFileLastModified(pathICon + "cultivated.png") + "\"");
		model.addAttribute("cultivated_checkDate", "\"" + getFileLastModified(pathICon + "cultivated_check.png") + "\"");
		model.addAttribute("disableDate", "\"" + getFileLastModified(pathICon + "disable.png") + "\"");
		model.addAttribute("disable_checkDate", "\"" + getFileLastModified(pathICon + "disable_check.png") + "\"");
		model.addAttribute("removedDate", "\"" + getFileLastModified(pathICon + "removed.png") + "\"");
		model.addAttribute("icon_editDate", "\"" + getFileLastModified(pathICon + "icon_edit.png") + "\"");
		model.addAttribute("icon_refDate", "\"" + getFileLastModified(pathICon + "icon_ref.png") + "\"");
		model.addAttribute("icon_delDate", "\"" + getFileLastModified(pathICon + "icon_del.png") + "\"");
		model.addAttribute("none_editDate", "\"" + getFileLastModified(pathICon + "none_edit.png") + "\"");
		
	}

	/**
	 * Get last modified date of CSS and Javascript files in banana folder, then set to client
	 * 
	 * @param request : servlet request
	 * @param model : jsp model
	 * @param cssFileName : name of CSS file
	 * @param cssVariableName : JSTL variable name of CSS file
	 * @param jsFileName : name of Javascript file
	 * @param jsVariableName : JSTL variable name of Javascript file
	 */
	public void setDataParamForScreenFiles(HttpServletRequest request, Model model, String cssFileName,
			String cssVariableName, String jsFileName, String jsVariableName) {
		// define folder path to common CSS files
		String pathCSS = request.getSession().getServletContext().getRealPath("/resources/css/banana/");
		// define folder path to common Javascript files
		String pathJS = request.getSession().getServletContext().getRealPath("/resources/js/banana/");

		// set date for CSS common files
		model.addAttribute(cssVariableName, getFileLastModified(pathCSS + cssFileName));
		// set date for Javascript common files
		model.addAttribute(jsVariableName, getFileLastModified(pathJS + jsFileName));
	}

	/**
	 * Get last modified date of a file 
	 * 
	 * @param filePath : real path to file
	 * @return date string in yyyyMMdd format
	 */
	public String getFileLastModified(String filePath) {
		File fileObj = new File(filePath);
		if (fileObj.exists()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			return sdf.format(fileObj.lastModified());
		} else {
			return null;
		}
	}

	/**
     * Write exception log to file
     * 
     * @param logger : log4j instance
     * @param userId : current logged in user id
     * @param exceptionType : type of exception (duplication, exception...)
     * @param ex : current exception
     */
    public static void writeExceptionLog(Logger logger, String userId, String exceptionType, Exception ex) {
    	// variable definition
        StringBuilder sb = new StringBuilder();
        // append exception message
        sb.append(ex.getMessage());
        sb.append("\n");
        // append exception stack trace
        for (StackTraceElement element : ex.getStackTrace()) {
              sb.append(element.toString());
              sb.append("\n");
        }
        // log to file
        logger.error(LoggerMessage.LOG_ERROR_PREFIX + userId + "," + exceptionType + Thread.currentThread().getStackTrace()[2].toString());
        logger.error(LoggerMessage.LOG_ERROR_PREFIX + userId + "," + exceptionType + sb);
    }
    public static void writeExceptionLog(Logger logger, String userId, String exceptionType, OutOfMemoryError ex) {
    	// variable definition
        StringBuilder sb = new StringBuilder();
        // append exception message
        sb.append(ex.getMessage());
        sb.append("\n");
        // append exception stack trace
        for (StackTraceElement element : ex.getStackTrace()) {
              sb.append(element.toString());
              sb.append("\n");
        }
        // log to file
        logger.error(LoggerMessage.LOG_ERROR_PREFIX + userId + "," + exceptionType + Thread.currentThread().getStackTrace()[2].toString());
        logger.error(LoggerMessage.LOG_ERROR_PREFIX + userId + "," + exceptionType + sb);
    }
}
