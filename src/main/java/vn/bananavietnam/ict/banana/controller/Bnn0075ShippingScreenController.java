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

import vn.bananavietnam.ict.banana.component.Bnn0075SearchShippingScreenConditions;
import vn.bananavietnam.ict.banana.component.Bnn0075SearchShippingScreenResult;
import vn.bananavietnam.ict.banana.service.Bnn0075ShippingScreenService;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.util.Util;

/**
 * 
 * @author Dung Trinh
 *
 */
@Controller
@RequestMapping(value = "/0075")
public class Bnn0075ShippingScreenController {

	@Autowired
    private Bnn0075ShippingScreenService Bnn0075shippingScreenService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String top(HttpServletRequest request, Locale locale, Model model) {
		// create data for combobox
		Bnn0075shippingScreenService.initData(model);
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0075ShippingScreen.css", "shippingScreenCssDate",
    			"Bnn0075ShippingScreen.js", "shippingScreenJsDate");
		return "banana/Bnn0075shippingScreen";
	}

	/**
     * Search shipping in DB based on search conditions received from client
     * 
     * @param searchConditions : data received from client
     * @return List of shipping data
     */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Bnn0075SearchShippingScreenResult> searchData(@RequestBody Bnn0075SearchShippingScreenConditions searchConditions) {
        return Bnn0075shippingScreenService.searchData(searchConditions);
    }
	
	/**
     * Insert shipping information to DB
     * 
     * @param shippingControl : data received from client
     * @return String : insert successfully: 1/insert failed: -1
     */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String insertData(@RequestBody Bnn0075SearchShippingScreenResult shippingControl) {
        return Bnn0075shippingScreenService.insertData(shippingControl);
    }
	
	/**
     * update data shipping screen information to DB
     * 
     * @param shippingControl : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String updateData(@RequestBody Bnn0075SearchShippingScreenResult shippingControl) {
        return Bnn0075shippingScreenService.updateData(shippingControl);
    }
	
	/**
	 * Get data for Area combobox by farm id 
	 * 
	 * @param farmId : data received from client
	 * @return Area data
	 */
	@RequestMapping(value = "/getAreaDataByFarmId", method = RequestMethod.POST)
    public @ResponseBody List<UtilComponent> getAreaDataByFarmId(@RequestParam(value = "farmId") String farmId) {
		return Bnn0075shippingScreenService.getAreaDataByFarmId(farmId);
    }
}
