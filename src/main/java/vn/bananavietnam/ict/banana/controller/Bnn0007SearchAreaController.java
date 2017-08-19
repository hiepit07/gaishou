package vn.bananavietnam.ict.banana.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.bananavietnam.ict.banana.component.Bnn0007SearchAreaConditions;
import vn.bananavietnam.ict.banana.component.Bnn0007SearchAreaFormResult;
import vn.bananavietnam.ict.banana.component.Bnn0007SearchAreaResult;
import vn.bananavietnam.ict.banana.service.Bnn0007SearchAreaService;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.model.IvbMKind;
import vn.bananavietnam.ict.common.util.Util;

/**
 * 
 * 	@author NghiaNguyen
 *	@version 1.0.0
 * 	@since 1.0.0
 * 
 */
@Controller
@RequestMapping(value = "/0007")
public class Bnn0007SearchAreaController {

	@Autowired
    private Bnn0007SearchAreaService bnn0007SearchAreaService;

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @param locale locale
	 * @param model modelAtriibute
	 * @return JSP
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String topPost(HttpServletRequest request, Locale locale, Model model, @ModelAttribute("farmId") String farmId) {
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0007SearchArea.css", "Bnn0007SearchAreaCss",
    			"Bnn0007SearchArea.js", "Bnn0007SearchAreaJsDate");
    	util.setDataParamForScreenFiles(request, model, "Bnn0023SearchUserPopup.css", "Bnn0023SearchUserPopupCss",
    			"Bnn0023SearchUserPopup.js", "Bnn0023SearchUserPopupJsDate");
		// create data for combobox
		bnn0007SearchAreaService.initData(model, farmId);
		return "banana/Bnn0007SearchArea";
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String topGet(HttpServletRequest request, Locale locale, Model model, @ModelAttribute("farmId") String farmId) {
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0007SearchArea.css", "Bnn0007SearchAreaCss",
    			"Bnn0007SearchArea.js", "Bnn0007SearchAreaJsDate");
    	util.setDataParamForScreenFiles(request, model, "Bnn0023SearchUserPopup.css", "Bnn0023SearchUserPopupCss",
    			"Bnn0023SearchUserPopup.js", "Bnn0023SearchUserPopupJsDate");
		// create data for combobox
		bnn0007SearchAreaService.initData(model, farmId);
		return "banana/Bnn0007SearchArea";
	}

	/**
	 * Search Data From IVB_M_Area By searchConditions
	 * 
	 * @param searchConditions
	 * @return List<Bnn0007SearchAreaResult>
	 */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Bnn0007SearchAreaResult> searchData(@RequestBody Bnn0007SearchAreaConditions searchConditions) {
        return bnn0007SearchAreaService.searchData(searchConditions);
    }
	
	/**
	 * Search Area Data By Farm Id selected From select box
	 * 
	 * @param farmId
	 * @return List<IvbMArea>
	 */
	@RequestMapping(value = "/getAreaDataByFarmId", method = RequestMethod.POST)
    public @ResponseBody List<UtilComponent> getAreaDataByFarmId(@RequestParam(value = "farmId") String farmId) {
        return bnn0007SearchAreaService.getAreaDataByFarmId(farmId);
    }
	
	/**
	 * Update Data Of table IVB_M_Area & table IVB_M_Manager
	 * 
	 * @param areaData Object of Bnn0007InserDataConditions from client
	 * @param oldManagerData Object of IvbMManager from client
	 * @param newManagerData Object of IvbMManager from client
	 * 
	 * @return String : update successfully: 1/update failed: -1
	 */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST)
    public @ResponseBody String updateData(	@RequestParam("areaData") String areaData,
    										@RequestParam(value = "lastUpdateDate") String lastUpdateDate) {
		Date lastUpdateDateClient = new Date(Long.valueOf(lastUpdateDate));
		return bnn0007SearchAreaService.updateData(areaData, lastUpdateDateClient);
    }

	/**
	 * Update Data Of table IVB_M_Area & table IVB_M_Manager & table IVB_M_BLOCK
	 * & Table IVB_T_PRODUCT
	 * 
	 * @param areaData
	 * @param managerData
	 * @return String : Insert successfully: 1/update failed: -1
	 */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST)
    public @ResponseBody String insertData(	@RequestParam("areaData") String areaData,
											@RequestParam("managerData") String managerData) {
		return bnn0007SearchAreaService.insertData(areaData, managerData);
    }

	/**
	 * Search Single Data From IVB_M_Area by farmId & areaId
	 * 
	 * @param searchConditions
	 * @return List<Bnn0007SearchAreaFormResult>
	 */
	@RequestMapping(value = "/getSingleData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Bnn0007SearchAreaFormResult> getSingleData(
			@RequestBody Bnn0007SearchAreaConditions searchConditions) {
		return bnn0007SearchAreaService.getSingleData(searchConditions);
	}

	/**
	 * Delete Data Of table IVB_M_Area by farmId & areaId
	 * 
	 * @param areaId
	 * @param farmId
	 * @return Delete successfully: 1/update failed: -1
	 */
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST)
    public @ResponseBody String deleteData(@RequestParam(value = "areaId") String areaId,
    										@RequestParam(value = "farmId") String farmId,
    										@RequestParam(value = "usersId") String usersId,
    										@RequestParam(value = "lastUpdateDate") String lastUpdateDate) {
		Date lastUpdateDateClient = new Date(Long.valueOf(lastUpdateDate));
        return bnn0007SearchAreaService.deleteData(areaId, farmId, usersId, lastUpdateDateClient);
    }
	
	/**
     * Get kind information based on kind's id
     * 
     * @param kindId : kindId's id received from client
     * @return kind data
     */
	@RequestMapping(value = "/getSingleDataKind", method = RequestMethod.POST)
    public @ResponseBody IvbMKind getSingleDataKind(@RequestParam(value = "kindId") String kindId) {
        return bnn0007SearchAreaService.getSingleDataKind(kindId);
    }
}
