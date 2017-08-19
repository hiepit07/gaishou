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

import vn.bananavietnam.ict.banana.component.Bnn0045SearchCultivationConditions;
import vn.bananavietnam.ict.banana.component.Bnn0045SearchCultivationInFormCbbResult;
import vn.bananavietnam.ict.banana.component.Bnn0045SearchCultivationResult;
import vn.bananavietnam.ict.banana.service.Bnn0045SearchCultivationService;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.util.Util;



/**
 * 
 * @author Hung Bui
 *
 */
@Controller
@RequestMapping(value = "/0045")
public class Bnn0045SearchCultivationController {

	@Autowired
    private Bnn0045SearchCultivationService bnn0045SearchCultivationService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String top(HttpServletRequest servletRequest, Locale locale, Model model) {
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(servletRequest, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(servletRequest, model, "Bnn0045SearchCultivation.css", "searchCultivationCssDate",
    			"Bnn0045SearchCultivation.js", "searchCultivationJsDate");
		return "banana/Bnn0045SearchCultivation";
	}
	
	/**
     * Search Cultivation in DB based on search conditions received from client
     * 
     * @param searchConditions : data received from client
     * @return List of Cultivation data
     */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Bnn0045SearchCultivationResult> searchData(@RequestBody Bnn0045SearchCultivationConditions searchConditions) {
        return bnn0045SearchCultivationService.searchData(searchConditions);
    }
	
	/**
	 * Get data for Process combobox by farm id, kind id 
	 * 
	 * @param farmId, kindId : data received from client
	 * @return Process data
	 */
	@RequestMapping(value = "/getProcessData", method = RequestMethod.POST)
    public @ResponseBody List<Bnn0045SearchCultivationInFormCbbResult> getProcessData() {
        return bnn0045SearchCultivationService.getProcessData();
    }
	
	/**
	 * Get data for Task combobox by farm id, kind id 
	 * 
	 * @param farmId, kindId : data received from client
	 * @return Task data
	 */
	@RequestMapping(value = "/getTaskData", method = RequestMethod.POST)
    public @ResponseBody List<Bnn0045SearchCultivationInFormCbbResult> getTaskData() {
        return bnn0045SearchCultivationService.getTaskData();
    }
	
	/**
	 * Get data for farm combobox by farm id, kind id 
	 * 
	 * @param farmId, kindId : data received from client
	 * @return farm data
	 */
	@RequestMapping(value = "/getFarmData", method = RequestMethod.POST)
    public @ResponseBody List<UtilComponent> getFarmData() {
        return bnn0045SearchCultivationService.getFarmData();
    }
	
	/**
	 * Get data for kind combobox by farm id, kind id 
	 * 
	 * @param farmId, kindId : data received from client
	 * @return kind data
	 */
	@RequestMapping(value = "/getKindData", method = RequestMethod.POST)
    public @ResponseBody List<Bnn0045SearchCultivationInFormCbbResult> getKindData(@RequestParam(value = "farmId") String farmId) {
        return bnn0045SearchCultivationService.getKindData(farmId);
    }

}