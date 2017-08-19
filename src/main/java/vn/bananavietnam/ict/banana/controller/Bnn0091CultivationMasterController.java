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

import vn.bananavietnam.ict.banana.component.Bnn0091CultivationMasterDataObject;
import vn.bananavietnam.ict.banana.service.Bnn0091CultivationMasterService;
import vn.bananavietnam.ict.common.db.model.IvbMCultivation;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Hieu Dao
 */

@Controller
@RequestMapping(value = "/0091")
public class Bnn0091CultivationMasterController {

	@Autowired
	private Bnn0091CultivationMasterService Bnn0091CultivationMasterService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String top(HttpServletRequest servletRequest, Locale locale, Model model) {
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(servletRequest, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(servletRequest, model, "Bnn0091CultivationMaster.css", "cultivationMasterCssDate",
    			"Bnn0091CultivationMaster.js", "cultivationMasterJsDate");
		// Create data page
		Bnn0091CultivationMasterService.initData(model);
		return "banana/Bnn0091CultivationMaster";
	}

	/**
	 * Get task in DB based on cultivation received from client
	 * @param farmId: data received from client
	 * @return List of area data
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getTaskData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<List> getTaskData(@RequestBody IvbMCultivation cultivationData) {
		return Bnn0091CultivationMasterService.getTaskData(cultivationData);
	}

	/**
     * Get task information based on task's id
     * @param taskId : task's id received from client
     * @return task data
     */
	@RequestMapping(value = "/getSingleData", method = RequestMethod.POST)
	public @ResponseBody IvbMTask getSingleData(@RequestParam(value = "taskId") String taskId) {
		return Bnn0091CultivationMasterService.getSingleData(taskId);
	}

	/**
	 * Get total process is unregistered on cultivation data
	 * @param cultivationData : data received from client
	 * @return total process is unregistered
	 */
	@RequestMapping(value = "/getProcessDataTotal", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getProcessDataTotal(@RequestBody IvbMCultivation cultivationData) {
		return Bnn0091CultivationMasterService.getUnregisteredProcessDataTotal(cultivationData);
	}

	/**
     * Insert cultivation's information to DB
     * @param cultivationData : data received from client
     * @return String : insert successfully: 1/insert failed: -1
     */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String insertData(@RequestBody List<Bnn0091CultivationMasterDataObject> cultivationData) {
		return Bnn0091CultivationMasterService.insertData(cultivationData);
	}
}