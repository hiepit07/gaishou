package vn.bananavietnam.ict.banana.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.bananavietnam.ict.banana.component.Bnn0003SearchUserConditions;
import vn.bananavietnam.ict.banana.component.Bnn0003SearchUserDataObject;
import vn.bananavietnam.ict.banana.component.Bnn0003SearchUserResult;
import vn.bananavietnam.ict.banana.service.Bnn0003SearchUserService;
import vn.bananavietnam.ict.common.db.model.IvbMUsers;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Khoa Le
 * Search user, create new user and edit user's info
 * Controller receives request from client and call service to process
 */
@Controller
@RequestMapping(value = "/0003")
public class Bnn0003SearchUserController {

	@Autowired
    private Bnn0003SearchUserService searchUserService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String top(HttpServletRequest request, Locale locale, Model model) {
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0003SearchUser.css", "searchUserCssDate",
    			"Bnn0003SearchUser.js", "searchUserJsDate");
		return "banana/Bnn0003SearchUser";
	}

	/**
     * Search user in DB based on search conditions received from client
     * 
     * @param searchConditions : data received from client
     * @return List of users data
     */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Bnn0003SearchUserResult> searchData(@RequestBody Bnn0003SearchUserConditions searchConditions) {
        return searchUserService.searchData(searchConditions);
    }

	/**
     * Update user's information to DB
     * 
     * @param userData : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String updateData(@RequestBody Bnn0003SearchUserDataObject userData) {
        return searchUserService.updateData(userData);
    }

	/**
     * Insert user's information to DB
     * 
     * @param userData : data received from client
     * @return String : insert successfully: 1/insert failed: -1
     */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String insertData(@RequestBody IvbMUsers userData) {
        return searchUserService.insertData(userData);
    }

	/**
     * Delete user from DB
     * 
     * @param usersId : user's id to delete
     * @return String : delete successfully: 1/delete failed: -1
     */
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST)
    public @ResponseBody String deleteData(@RequestBody IvbMUsers userData) {
        return searchUserService.deleteData(userData);
    }

	/**
     * Get user information based on user's id
     * 
     * @param usersId : user's id received from client
     * @return user data
     */
	@RequestMapping(value = "/getSingleData", method = RequestMethod.POST)
    public @ResponseBody IvbMUsers getSingleData(@RequestParam(value = "usersId") String usersId) {
        return searchUserService.getSingleData(usersId);
    }
}