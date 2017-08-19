package vn.bananavietnam.ict.banana.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.bananavietnam.ict.banana.service.Bnn0049AreaListService;
import vn.bananavietnam.ict.common.util.Util;

/**
 * 
 * @author NghiaNguyen
 *
 */
@Controller
@RequestMapping(value = "/0049")
public class Bnn0049AreaListController {

	@Autowired
	private Bnn0049AreaListService bnn0049AreaListService;
	/**
	 * Simply selects the home view to render by returning its name.
	 * Take Bnn0049AreaData from 0047 page
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String topPost(HttpServletRequest request, Model model, @ModelAttribute("farmId") String farmId,
			@ModelAttribute("farmName") String farmName) {
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0049FarmList.css", "Bnn0049AreaListCss",
    			"Bnn0049AreaList.js", "Bnn0049AreaListJsDate");
		// get data form Bnn0047FarmListDataObject page transfered
		bnn0049AreaListService.initData(model, farmId, farmName);

		return "banana/Bnn0049AreaList";
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String topGet(HttpServletRequest request, Model model, @ModelAttribute("farmId") String farmId,
			@ModelAttribute("farmName") String farmName) {
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0049FarmList.css", "Bnn0049AreaListCss",
    			"Bnn0049AreaList.js", "Bnn0049AreaListJsDate");
		// get data form Bnn0047FarmListDataObject page transfered
		bnn0049AreaListService.initData(model, farmId, farmName);

		return "banana/Bnn0049AreaList";
	}
}
