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
import org.springframework.web.bind.annotation.ResponseBody;

import vn.bananavietnam.ict.banana.component.Bnn0089SearchTaskConditions;
import vn.bananavietnam.ict.banana.component.Bnn0089SearchTaskResult;
import vn.bananavietnam.ict.banana.service.Bnn0089SearchTaskService;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.util.Util;

/**
 * 
 * @author NghiaNguyen
 *
 */
@Controller
@RequestMapping(value="/0089")
public class Bnn0089SearchTaskController {
	
	@Autowired
	private Bnn0089SearchTaskService bnn0089SearchTaskService;
	
	/**
     * Simply selects the home view to render by returning its name.
     */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String top (HttpServletRequest request, Locale locale, Model model) {
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0089SearchTask.css", "searchTaskCssDate",
    			"Bnn0089SearchTask.js", "searchTaskJsDate");
		return "banana/Bnn0089SearchTask";
	}
	
	/**
     * Search task in DB based on search conditions received from client
     * 
     * @param searchConditions : data received from client
     * @return List of Task data
     */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Bnn0089SearchTaskResult> searchData(@RequestBody Bnn0089SearchTaskConditions searchConditions) {
        return bnn0089SearchTaskService.searchData(searchConditions);
    }
	
	/**
     * Get task information based on search conditions received from client
     * 
     * @param searchConditions : data received from client
     * @return task data
     */
	@RequestMapping(value = "/getSingleData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody IvbMTask getSingleData(
			@RequestBody Bnn0089SearchTaskConditions searchConditions) {
		return bnn0089SearchTaskService.getSingleData(searchConditions);
	}
	
	/**
     * Update task's information to DB
     * 
     * @param taskData : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String updateData(@RequestBody IvbMTask taskData) {
        return bnn0089SearchTaskService.updateData(taskData);
    }
	
	/**
     * Insert task's information to DB
     * 
     * @param taskData : data received from client
     * @return String : insert successfully: 1/insert failed: -1
     */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String insertData(@RequestBody IvbMTask taskData) {
        return bnn0089SearchTaskService.insertData(taskData);
    }
	
	/**
     * Delete taskId from DB
     * 
     * @param taskId : taskId id to delete
     * @return String : delete successfully: 1/delete failed: -1
     */
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST)
   public @ResponseBody String deleteData(@RequestBody IvbMTask taskData) {
       return bnn0089SearchTaskService.deleteData(taskData);
   }
}
