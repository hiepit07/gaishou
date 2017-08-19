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

import vn.bananavietnam.ict.banana.component.Bnn0017SearchBananaKindConditions;
import vn.bananavietnam.ict.banana.component.Bnn0017SearchBananaKindResult;
import vn.bananavietnam.ict.banana.service.Bnn0017SearchBananaKindService;
import vn.bananavietnam.ict.common.db.model.IvbMKind;
import vn.bananavietnam.ict.common.util.Util;

/**
 * 
 * @author NghiaNguyen
 *
 */
@Controller
@RequestMapping(value = "/0017")
public class Bnn0017SearchBananaKindController {

	@Autowired
    private Bnn0017SearchBananaKindService bnn0017SearchBananaKindService;
	
	/**
     * Simply selects the home view to render by returning its name.
     */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String top(HttpServletRequest servletRequest, Locale locale, Model model) {

		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(servletRequest, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(servletRequest, model, "Bnn0017SearchBananaKind.css", "searchBananaKindCssDate",
    			"Bnn0017SearchBananaKind.js", "searchBananaKindJsDate");
		return "banana/Bnn0017SearchBananaKind";
	}
	
	/**
     * Search kind in DB based on search conditions received from client
     * 
     * @param searchConditions : data received from client
     * @return List of Kind data
     */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Bnn0017SearchBananaKindResult> searchData(@RequestBody Bnn0017SearchBananaKindConditions searchConditions) {
		return bnn0017SearchBananaKindService.searchData(searchConditions);
    }
	
	/**
     * Update kind's information to DB
     * 
     * @param bananaKindData : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String updateData(@RequestBody IvbMKind bananaKindData) {
        return bnn0017SearchBananaKindService.updateData(bananaKindData);
    }
	
	/**
     * Insert kind's information to DB
     * 
     * @param bananaKindData : data received from client
     * @return String : insert successfully: 1/insert failed: -1
     */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String insertData(@RequestBody IvbMKind bananaKindData) {
        return bnn0017SearchBananaKindService.insertData(bananaKindData);
    }
	
	/**
     * Get kind information based on kind's id
     * 
     * @param kindId : kindId's id received from client
     * @return kind data
     */
	@RequestMapping(value = "/getSingleData", method = RequestMethod.POST)
    public @ResponseBody IvbMKind getSingleData(@RequestParam(value = "kindId") String kindId) {
        return bnn0017SearchBananaKindService.getSingleData(kindId);
    }
	
	/**
     * Delete kindId from DB
     * 
     * @param kindId : kindId id to delete
     * @return String : delete successfully: 1/delete failed: -1
     */
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST)
    public @ResponseBody String deleteData(@RequestBody IvbMKind bananaKindData) {
        return bnn0017SearchBananaKindService.deleteData(bananaKindData);
    }
}
