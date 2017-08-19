package vn.bananavietnam.ict.banana.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Hieu Dao
 */
@Controller
@RequestMapping(value = "/0001")
public class Bnn0001BananaMenuController {

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String top(HttpServletRequest request, Locale locale, Model model) {
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0001BananaMenu.css", "bananaMenuCssDate",
    			"Bnn0001BananaMenu.js", "bananaMenuJsDate");
		return "banana/Bnn0001BananaMenu";
	}
}
