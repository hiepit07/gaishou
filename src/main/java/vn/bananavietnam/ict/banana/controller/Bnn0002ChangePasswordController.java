package vn.bananavietnam.ict.banana.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.bananavietnam.ict.banana.component.Bnn0002ChangePasswordDataObject;
import vn.bananavietnam.ict.banana.service.Bnn0002ChangePasswordService;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Khoa Le
 * Change current user's password
 * Controller receives request from client and call service to process
 */
@Controller
@RequestMapping(value = "/0002")
public class Bnn0002ChangePasswordController {

	@Autowired
	private Bnn0002ChangePasswordService changePasswordService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String top(HttpServletRequest request, Locale locale, Model model) {
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "", "",
    			"Bnn0002ChangePassword.js", "changePasswordJsDate");
		return "banana/Bnn0002ChangePassword";
	}

	/**
     * Update new password to DB
     * 
     * @param passwordData : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String searchData(@RequestBody Bnn0002ChangePasswordDataObject passwordData) {
        return changePasswordService.updateData(passwordData);
    }
}