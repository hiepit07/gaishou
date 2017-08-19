package vn.bananavietnam.ict.banana.controller;

import java.util.Date;
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

import vn.bananavietnam.ict.banana.component.Bnn0005SearchFarmConditions;
import vn.bananavietnam.ict.banana.component.Bnn0005SearchFarmDataObject;
import vn.bananavietnam.ict.banana.component.Bnn0005SearchFarmResult;
import vn.bananavietnam.ict.banana.service.Bnn0005SearchFarmService;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Hieu Dao
 */
@Controller
@RequestMapping(value = "/0005")
public class Bnn0005SearchFarmController {

	@Autowired
	private Bnn0005SearchFarmService SearchFarmService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String top(HttpServletRequest request, Locale locale, Model model) {
		SearchFarmService.initData(model);
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0005SearchFarm.css", "searchFarmCssDate",
    			"Bnn0005SearchFarm.js", "searchFarmJsDate");
		return "banana/Bnn0005SearchFarm";
	}

	/**
	 * Search farm in DB based on search conditions received from client
	 * @param searchConditions: data received from client
	 * @return List of farm data
	 */
	@RequestMapping(value = "/searchFarmData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Bnn0005SearchFarmResult> searchData(
			@RequestBody Bnn0005SearchFarmConditions searchConditions) {
		return SearchFarmService.searchData(searchConditions);
	}

	/**
	 * Get farm information based on farm's name and user's name
	 * @param searchConditions: data received from client
	 * @return Farm data
	 */
	@RequestMapping(value = "/getSingleData", method = RequestMethod.POST)
	public @ResponseBody Bnn0005SearchFarmDataObject getSingleData(@RequestParam(value = "farmId") String farmId) {
		return SearchFarmService.getSingleData(farmId);
	}

	/**
	 * Insert farm's information to DB
	 * @param farmData: data received from client
	 * @return String : insert successfully: 1/insert failed: -1
	 */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String insertData(@RequestBody Bnn0005SearchFarmDataObject farmData) {
		return SearchFarmService.insertData(farmData);
	}

	/**
	 * Update farm's information to DB
	 * @param farmData: data received from client
	 * @return String : update successfully: 1/update failed: -1
	 */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateData(@RequestBody Bnn0005SearchFarmDataObject farmData) {
		return SearchFarmService.updateData(farmData);
	}

	/**
	 * Delete farm from DB
	 * @param farmId : farm's id to delete
	 * @return String : delete successfully: 1/delete failed: -1
	 */
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST)
    public @ResponseBody String deleteData(@RequestParam(value = "farmId") String farmId, 
    									   @RequestParam(value = "lastUpdateDate") String lastUpdateDate) {
		Date lastUpdateDateClient = new Date(Long.valueOf(lastUpdateDate));
        return SearchFarmService.deleteData(farmId, lastUpdateDateClient);
    }
	
	/**
	 * Loading farm from DB when insert data
	 * @return Farm data
	 */
	@RequestMapping(value = "/getFarmName", method = RequestMethod.POST)
	public @ResponseBody List<UtilComponent> getFarmName() {
		return SearchFarmService.getFarmName();
    }
}
