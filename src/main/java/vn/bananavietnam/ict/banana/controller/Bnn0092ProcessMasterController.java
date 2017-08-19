package vn.bananavietnam.ict.banana.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import vn.bananavietnam.ict.banana.component.Bnn0091CultivationMasterDataObject;
import vn.bananavietnam.ict.banana.component.Bnn0092ProcessMasterResult;
import vn.bananavietnam.ict.banana.service.Bnn0092ProcessMasterService;
import vn.bananavietnam.ict.common.db.model.IvbMCultivation;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Hieu Dao
 */

@Controller
@RequestMapping(value = "/0092")
public class Bnn0092ProcessMasterController {

	@Autowired
	private Bnn0092ProcessMasterService bnn0092ProcessMasterService;

	/**
	 * Simply selects the home view to render by returning its name.
	 * Take data from 0091 page
	 * @param farmId, farmName, kindId, kindName : received from 0091 page
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String topPost(WebRequest request, HttpServletRequest servletRequest, Model model, @ModelAttribute("farmId") String farmId,
			@ModelAttribute("farmName") String farmName, @ModelAttribute("kindId") String kindId,
			@ModelAttribute("kindName") String kindName) {
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(servletRequest, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(servletRequest, model, "Bnn0092ProcessMaster.css", "processMasterCssDate",
    			"Bnn0092ProcessMaster.js", "processMasterJsDate");
		Bnn0091CultivationMasterDataObject cultivationObject = new Bnn0091CultivationMasterDataObject();
		// farm id
		cultivationObject.setFarmId(farmId);
		// farm name
		cultivationObject.setFarmName(farmName);
		// kind id
		cultivationObject.setKindId(kindId);
		// kind name
		cultivationObject.setKindName(kindName);
		// get data form Bnn0091CultivationMaster page transfered
		bnn0092ProcessMasterService.getData(model, cultivationObject);
		return "banana/Bnn0092ProcessMaster";
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String topGet(WebRequest request, HttpServletRequest servletRequest, Model model, @ModelAttribute("farmId") String farmId,
			@ModelAttribute("farmName") String farmName, @ModelAttribute("kindId") String kindId,
			@ModelAttribute("kindName") String kindName) {
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(servletRequest, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(servletRequest, model, "Bnn0061TraceabilityMaster.css", "traceabilityMasterCssDate",
    			"Bnn0061TraceabilityMaster.js", "traceabilityMasterJsDate");
		Bnn0091CultivationMasterDataObject cultivationObject = new Bnn0091CultivationMasterDataObject();
		// farm id
		cultivationObject.setFarmId(farmId);
		// farm name
		cultivationObject.setFarmName(farmName);
		// kind id
		cultivationObject.setKindId(kindId);
		// kind name
		cultivationObject.setKindName(kindName);
		// get data form Bnn0091CultivationMaster page transfered
		bnn0092ProcessMasterService.getData(model, cultivationObject);
		return "banana/Bnn0092ProcessMaster";
	}

	/**
     * Get process list based on farm and kind's id
     * @param cultivationData : farm and kind's id received from client
     * @return process list
     */
	@RequestMapping(value = "/initData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<List<IvbMProcess>> initData(@RequestBody IvbMCultivation cultivationData) {
		return bnn0092ProcessMasterService.initData(cultivationData);
	}

	/**
     * Get process detail based on farm and kind and process's id
     * @param searchConditions : data received from client
     * @return process list
     */
	@RequestMapping(value = "/getProcessDetailData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Bnn0092ProcessMasterResult> getProcessDetailData(@RequestBody IvbMCultivation cultivationData) {
		return bnn0092ProcessMasterService.getProcessDetailData(cultivationData);
	}

	/**
	 * Get total process is unregistered on cultivation data
	 * @param cultivationData : data received from client
	 * @return total process is unregistered
	 */
	@RequestMapping(value = "/getProcessDataTotal", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getProcessDataTotal(@RequestBody IvbMCultivation cultivationData) {
		return bnn0092ProcessMasterService.getUnregisteredProcessDataTotal(cultivationData);
	}

	/**
     * Update cultivation's information to DB
     * @param cultivationData : data received from client
     * @return String : update successfully: 1/insert failed: -1
     */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateData(@RequestBody List<IvbMCultivation> cultivationData) {
		return bnn0092ProcessMasterService.updateData(cultivationData);
	}
}