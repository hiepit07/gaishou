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

import vn.bananavietnam.ict.banana.component.Bnn0095SearchAccessAuthorizationConditions;
import vn.bananavietnam.ict.banana.component.Bnn0095SearchAccessAuthorizationResult;
import vn.bananavietnam.ict.banana.service.Bnn0095SearchAccessAuthorizationService;
import vn.bananavietnam.ict.common.db.model.IvbMAccessAuthorization;
import vn.bananavietnam.ict.common.util.Util;

/**
 * 	Bnn0095SearchAccessAuthorizationController
 * 
 *	@author NghiaNguyen
 *	@version 1.0.0
 * 	@since 1.0.0
 */
@Controller
@RequestMapping (value ="/0095")
public class Bnn0095SearchAccessAuthorizationController {
	
	
	@Autowired
	private Bnn0095SearchAccessAuthorizationService Bnn0095SearchAccessAuthorizationService;
	
	/**
	 * 
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @param locale locale
	 * @param model modelAtriibute
	 * @return JSP
	 */
	@RequestMapping(value ="/", method = RequestMethod.GET)
	public String top (HttpServletRequest request, Locale locale, Model model) {
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0095SearchAccessAuthorization.css", "Bnn0095SearchAccessAuthorizationCss",
    			"Bnn0095SearchAccessAuthorization.js", "Bnn0095SearchAccessAuthorizationJsDate");
		return "banana/Bnn0095SearchAccessAuthorizarion";
	}

	/**
	 * Search Data From IVB_M_Access_Authorization By searchConditions
	 * 
	 * @param searchConditions
	 * @return List<Bnn0095SearchAccessAuthorizationResult>
	 */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Bnn0095SearchAccessAuthorizationResult> searchData(@RequestBody Bnn0095SearchAccessAuthorizationConditions searchConditions) {
        return Bnn0095SearchAccessAuthorizationService.searchData(searchConditions);
    }
	
	/**
	 * Search Single Data From IVB_M_Access_Authorization by Id
	 * 
	 * @param searchConditions
	 * @return List<Bnn0095SearchAccessAuthorizationResult>
	 */
	@RequestMapping(value = "/getSingleData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Bnn0095SearchAccessAuthorizationResult> getSingleData(
			@RequestBody Bnn0095SearchAccessAuthorizationConditions searchConditions) {
		return Bnn0095SearchAccessAuthorizationService.getSingleData(searchConditions);
	}

	/**
	 * Update Data Of table IVB_M_Access_Authorization 
	 * 
	 * @param accessAuthorizationData
	 * @return String : update successfully: 1/update failed: -1 
	 */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String updateData(@RequestBody IvbMAccessAuthorization accessAuthorizationData) {
        return Bnn0095SearchAccessAuthorizationService.updateData(accessAuthorizationData);
    }

	/**
	 * Insert New Data into table IVB_M_Access_Authorization 
	 * 
	 * @param accessAuthorizationData
	 * @return String : Insert successfully: 1/update failed: -1
	 */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String insertData(@RequestBody IvbMAccessAuthorization accessAuthorizationData) {
        return Bnn0095SearchAccessAuthorizationService.insertData(accessAuthorizationData);
    }
	
	/**
	 * Delete Data Of table IVB_M_Access_Authorization by accessAuthorityId & screenId
	 * 
	 * @param accessAuthorityId
	 * @param screenId
	 * @return Delete successfully: 1/update failed: -1
	 */
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST)
	public @ResponseBody String deleteData(@RequestParam("accessAuthorityId") String accessAuthorityId,
   										@RequestParam("screenId") String screenId,
   										@RequestParam("lastUpdateDate") String lastUpdateDate) {
		
		Date lastUpdateDateClient = new Date(Long.valueOf(lastUpdateDate));
		return Bnn0095SearchAccessAuthorizationService.deleteData(accessAuthorityId, screenId, lastUpdateDateClient);
   }
}
