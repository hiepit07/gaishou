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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.bananavietnam.ict.banana.component.Bnn0037ProductExtendObject;
import vn.bananavietnam.ict.banana.component.BnnBlockCultivationResult;
import vn.bananavietnam.ict.banana.service.Bnn0037BlockDetailUpdateService;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Khoa Le
 * Create new cultivation result, edit and delete existed cultivation result
 * Controller receives request from client and call service to process
 */
@Controller
@RequestMapping(value = "/0037")
public class Bnn0037BlockDetailUpdateController {

	@Autowired
	private Bnn0037BlockDetailUpdateService blockDetailUpdateService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String topPost(HttpServletRequest request, Model model, @ModelAttribute("farmId") String farmId,
			@ModelAttribute("farmName") String farmName, @ModelAttribute("areaId") String areaId,
			@ModelAttribute("areaName") String areaName, @ModelAttribute("blockId") String blockId,
			@ModelAttribute("blockName") String blockName) {
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0037BlockDetailUpdate.css", "blockUpdateCssDate",
    			"Bnn0037BlockDetailUpdate.js", "blockUpdateJsDate");
		// create initialization data
		if (blockDetailUpdateService.initData(model, farmId, farmName, areaId, areaName, blockId, blockName)
				.equals(Constants.INIT_RESULT_SUCCESSFUL)) {
			return "banana/Bnn0037BlockDetailUpdate";
		} else {
			// error page
			return "common/error";
		}
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String topGet(HttpServletRequest request, Model model, @ModelAttribute("farmId") String farmId,
			@ModelAttribute("farmName") String farmName, @ModelAttribute("areaId") String areaId,
			@ModelAttribute("areaName") String areaName, @ModelAttribute("blockId") String blockId,
			@ModelAttribute("blockName") String blockName) {
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0037BlockDetailUpdate.css", "blockUpdateCssDate",
    			"Bnn0037BlockDetailUpdate.js", "blockUpdateJsDate");
		// create initialization data
		if (blockDetailUpdateService.initData(model, farmId, farmName, areaId, areaName, blockId, blockName)
				.equals(Constants.INIT_RESULT_SUCCESSFUL)) {
			return "banana/Bnn0037BlockDetailUpdate";
		} else {
			// error page
			return "common/error";
		}
	}

	/**
     * Get product information based on ids
     * 
     * @param farmId : data received from client
     * @param areaId : data received from client
     * @param blockId : data received from client
     * @return product data
     */
	@RequestMapping(value = "/getProductData", method = RequestMethod.POST)
    public @ResponseBody List<Bnn0037ProductExtendObject> getProductData(@RequestParam(value = "farmId") String farmId,
    		@RequestParam(value = "areaId") String areaId, @RequestParam(value = "blockId") String blockId) {
        return blockDetailUpdateService.getProductData(farmId, areaId, blockId);
    }

	/**
     * Get cultivation result information based on ids
     * 
     * @param farmId : data received from client
     * @param areaId : data received from client
     * @param blockId : data received from client
     * @param kindId : data received from client
     * @return cultivation data
     */
	@RequestMapping(value = "/getCultivationResultData", method = RequestMethod.POST)
    public @ResponseBody List<BnnBlockCultivationResult> getCultivationResultData(@RequestParam(value = "farmId") String farmId,
    		@RequestParam(value = "areaId") String areaId, @RequestParam(value = "blockId") String blockId,
    		@RequestParam(value = "kindId") String kindId) {
        return blockDetailUpdateService.getCultivationResultData(farmId, areaId, blockId, kindId);
    }

	/**
     * Get task information based on ids
     * 
     * @param farmId : data received from client
     * @param kindId : data received from client
     * @param processId : data received from client
     * @return task data
     */
	@RequestMapping(value = "/getTaskData", method = RequestMethod.POST)
    public @ResponseBody List<IvbMTask> getTaskData(@RequestParam(value = "farmId") String farmId,
    		@RequestParam(value = "kindId") String kindId, @RequestParam(value = "processId") String processId) {
    	return blockDetailUpdateService.getTaskData(farmId, kindId, processId);
    }

	/**
     * Insert cultivation result's information to DB
     * 
     * @param cultivationResultData : data received from client
     * @return String : insert successfully: 1/insert failed: -1
     */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String insertData(@RequestBody BnnBlockCultivationResult cultivationResultData) {
        return blockDetailUpdateService.insertData(cultivationResultData);
    }

	/**
     * Delete cultivation result from DB
     * 
     * @param cultivationResultData : data received from client
     * @return String : delete successfully: 1/delete failed: -1
     */
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST)
    public @ResponseBody String deleteData(@RequestBody BnnBlockCultivationResult cultivationResultData) {
        return blockDetailUpdateService.deleteData(cultivationResultData);
    }
}