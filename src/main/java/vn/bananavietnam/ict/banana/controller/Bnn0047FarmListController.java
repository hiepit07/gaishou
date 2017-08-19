package vn.bananavietnam.ict.banana.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.bananavietnam.ict.banana.service.Bnn0047FarmListService;
import vn.bananavietnam.ict.common.util.Util;

@Controller
@RequestMapping(value = "/0047")
public class Bnn0047FarmListController {
	
	@Autowired
	private Bnn0047FarmListService Bnn0047FarmListService;
	
	/**
	 * 
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @param locale locale
	 * @param model modelAtriibute
	 * @return JSP
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String top(HttpServletRequest request, Locale locale, Model model) {
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0047FarmList.css", "Bnn0047FarmListCss",
    			"Bnn0047FarmList.js", "Bnn0047FarmListJsDate");
		Bnn0047FarmListService.initData(model);
		return "banana/Bnn0047FarmList";
	}
}