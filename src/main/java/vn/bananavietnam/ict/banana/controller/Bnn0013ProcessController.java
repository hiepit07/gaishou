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

import vn.bananavietnam.ict.banana.component.Bnn0013SearchProcessConditions;
import vn.bananavietnam.ict.banana.component.Bnn0013SearchProcessResult;
import vn.bananavietnam.ict.banana.service.Bnn0013SearchProcessService;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author HiepTL
 */
@Controller
@RequestMapping(value = "/0013")
public class Bnn0013ProcessController {

	@Autowired
	private Bnn0013SearchProcessService searchCultivationProcessService;
	
	/**
     * Simply selects the home view to render by returning its name.
     */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String top(HttpServletRequest servletRequest, Locale locale, Model model) {
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(servletRequest, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(servletRequest, model, "Bnn0013CultivationProcess.css", "cultivationProcessCssDate",
    			"Bnn0013CultivationProcess.js", "cultivationProcessJsDate");
		return "banana/Bnn0013CultivationProcess";
	}
	
	/**
     * Search process in DB based on search conditions received from client
     * 
     * @param searchConditions : data received from client
     * @return List of process data
     */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Bnn0013SearchProcessResult> searchData(
			@RequestBody Bnn0013SearchProcessConditions searchConditions) {
		return searchCultivationProcessService.searchData(searchConditions);
	}
	
	/**
     * Search process in DB based on search conditions received from client
     * 
     * @param bnn0013ProcessConditions : data received from client
     * @return  process data
     */
	@RequestMapping(value = "/getSingleData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody IvbMProcess getSingleData(@RequestBody Bnn0013SearchProcessConditions bnn0013ProcessConditions) {
        return searchCultivationProcessService.getSingleData(bnn0013ProcessConditions);
    }
	
	/**
     * Update proces's information to DB
     * 
     * @param ivbMCultivationProcess : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String updateData(@RequestBody IvbMProcess ivbMCultivationProcess) {
        return searchCultivationProcessService.updateData(ivbMCultivationProcess);
    }
	
	/**
     * Insert process's information to DB
     * 
     * @param ivbMCultivationProcess : data received from client
     * @return String : insert successfully: 1/insert failed: -1
     */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String insertData(@RequestBody IvbMProcess ivbMCultivationProcess) {
        return searchCultivationProcessService.insertData(ivbMCultivationProcess);
    }
	
	/**
     * Delete process from DB
     * 
     * @param ivbMCultivationProcessKey : data received from client
     * @return String : delete successfully: 1/delete failed: -1
     */
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST)
    public @ResponseBody String deleteData(@RequestBody IvbMProcess ivbMCultivationProcessKey) {
        return searchCultivationProcessService.deleteData(ivbMCultivationProcessKey);
    }
}
