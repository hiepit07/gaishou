package vn.bananavietnam.ict.banana.controller;

import java.util.ArrayList;
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

import vn.bananavietnam.ict.banana.component.Bnn0087AffiliationDelete;
import vn.bananavietnam.ict.banana.component.Bnn0087IvbMManager;
import vn.bananavietnam.ict.banana.component.Bnn0087SearchAffiliationConditions;
import vn.bananavietnam.ict.banana.component.Bnn0087SearchAffiliationResult;
import vn.bananavietnam.ict.banana.component.Bnn0088SearchAreaResult;
import vn.bananavietnam.ict.banana.service.Bnn0087SearchAffiliationService;
import vn.bananavietnam.ict.common.util.Util;

@Controller
@RequestMapping(value = "/0087")
public class Bnn0087SearchAffiliationController {
    @Autowired
    private Bnn0087SearchAffiliationService bnn0087SearchAffiliationService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String top(HttpServletRequest request, Locale locale, Model model) {
        bnn0087SearchAffiliationService.initData(model);
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0087SearchAffiliation.css", "searchAffiliationCssDate",
    			"Bnn0087SearchAffiliation.js", "searchAffiliationJsDate");
        return "banana/Bnn0087SearchAffiliation";
    }

    @RequestMapping(value = "/searchData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Bnn0087SearchAffiliationResult> searchData(
            @RequestBody Bnn0087SearchAffiliationConditions searchConditions) {
        return bnn0087SearchAffiliationService.searchData(searchConditions);
    }
    
    @RequestMapping(value = "/deleteData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String deleteData(@RequestBody Bnn0087AffiliationDelete bnn0087AffiliationDelete) {
        return bnn0087SearchAffiliationService.deleteData(bnn0087AffiliationDelete);
    }
    
    @RequestMapping(value = "/getDataForAffiliation", method = RequestMethod.POST)
    public @ResponseBody List<Bnn0088SearchAreaResult> getDataForAffiliation() {
        return bnn0087SearchAffiliationService.getDataForAffiliation();
    }    
    
    @RequestMapping(value = "/insertDataForAffiliation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String insertDataForAffiliation( @RequestBody ArrayList<Bnn0087IvbMManager> dataInsert) {
        return bnn0087SearchAffiliationService.insertDataForAffiliation(dataInsert);
    }
}
