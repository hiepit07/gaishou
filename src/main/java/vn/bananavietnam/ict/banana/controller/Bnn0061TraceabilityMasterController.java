package vn.bananavietnam.ict.banana.controller;

import java.sql.Date;
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
import org.springframework.web.context.request.WebRequest;

import vn.bananavietnam.ict.banana.component.Bnn0061TraceabilityMasterDataConditions;
import vn.bananavietnam.ict.banana.component.Bnn0061TraceabilityMasterDataObject;
import vn.bananavietnam.ict.banana.component.Bnn0075SearchShippingScreenResult;
import vn.bananavietnam.ict.banana.service.Bnn0061TraceabilityMasterService;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.model.IvbMBlock;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.db.model.IvbMStatus;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.db.model.IvbTProduct;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Hieu Dao
 */

@Controller
@RequestMapping(value = "/0061")
public class Bnn0061TraceabilityMasterController {
	@Autowired
	private Bnn0061TraceabilityMasterService bnn0061TraceabilityMasterService = new Bnn0061TraceabilityMasterService();
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String topPost(WebRequest request, HttpServletRequest servletRequest, Model model, @ModelAttribute("shipNumber") String shipNumber,
			@ModelAttribute("farmId") String farmId, @ModelAttribute("areaId") String areaId,
			@ModelAttribute("harvestDate") Date harvestDate, @ModelAttribute("shipDate") Date shipDate) {
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(servletRequest, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(servletRequest, model, "Bnn0061TraceabilityMaster.css", "traceabilityMasterCssDate",
    			"Bnn0061TraceabilityMaster.js", "traceabilityMasterJsDate");
		Bnn0075SearchShippingScreenResult bnn0075Data = new Bnn0075SearchShippingScreenResult();
		// Shipping number
		bnn0075Data.setShippingNumber(shipNumber);
		// Farm id
		bnn0075Data.setFarmId(farmId);
		// Area id
		bnn0075Data.setAreaId(areaId);
		// Harvest Date
		bnn0075Data.setHarvestDate(harvestDate);
		// Shipping Date
		bnn0075Data.setShipDate(shipDate);
		// Create data page
		bnn0061TraceabilityMasterService.initData(model, bnn0075Data);
		return "banana/Bnn0061TraceabilityMaster";
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String topGet(WebRequest request, HttpServletRequest servletRequest, Model model) {
		// set date for common files
		Util util = new Util();
    	util.setDateParamForCommonFiles(servletRequest, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(servletRequest, model, "Bnn0061TraceabilityMaster.css", "traceabilityMasterCssDate",
    			"Bnn0061TraceabilityMaster.js", "traceabilityMasterJsDate");
		Bnn0075SearchShippingScreenResult bnn0075Data = new Bnn0075SearchShippingScreenResult();
		// Create data page
		bnn0061TraceabilityMasterService.initData(model, bnn0075Data);
		return "banana/Bnn0061TraceabilityMaster";
	}

	/**
	 * Get area in DB based on farm id received from client
	 * @param farmId: data received from client
	 * @return List of area data
	 */
	@RequestMapping(value = "/getAreaData", method = RequestMethod.POST)
	public @ResponseBody List<UtilComponent> getAreaData(@RequestParam(value = "farmId") String farmId) {
		return bnn0061TraceabilityMasterService.getAreaData(farmId);
	}

	/**
	 * Get block in DB based on block data received from client
	 * @param blockData: data received from client
	 * @return List of block data
	 */
	@RequestMapping(value = "/getBlockData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<IvbMBlock> getBlockData(@RequestBody IvbMBlock blockData) {
		return bnn0061TraceabilityMasterService.getBlockData(blockData);
	}

	/**
	 * Get line in DB based on product data received from client
	 * @param productData: data received from client
	 * @return List of line data
	 */
	@RequestMapping(value = "/getLineData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> getLineData(@RequestBody IvbTProduct productData) {
		return bnn0061TraceabilityMasterService.getLineData(productData);
	}

	/**
	 * Get column in DB based on product data received from client
	 * @param productData: data received from client
	 * @return List of column data
	 */
	@RequestMapping(value = "/getColumnData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> getColumnData(@RequestBody IvbTProduct productData) {
		return bnn0061TraceabilityMasterService.getColumnData(productData);
	}
	
	/**
	 * Get status in DB based on product data received from client
	 * @param farmId: data received from client
	 * @return List of status data
	 */
	@RequestMapping(value = "/getStatusData", method = RequestMethod.POST)
	public @ResponseBody List<IvbMStatus> getStatusData(@RequestParam(value = "farmId") String farmId) {
		return bnn0061TraceabilityMasterService.getStatusData(farmId);
	}
	
	/**
	 * Get task in DB based on product data received from client
	 * @param farmId: data received from client
	 * @param processId: data received from client
	 * @return List of status data
	 */
	@RequestMapping(value = "/getTaskData", method = RequestMethod.POST)
	public @ResponseBody List<IvbMTask> getTaskData(@RequestParam(value = "farmId") String farmId, 
			@RequestParam(value = "processId") String processId) {
		return bnn0061TraceabilityMasterService.getTaskData(farmId, processId);
	}
	
	/**
	 * Get task in DB based on product data received from client
	 * @param farmId: data received from client
	 * @param processId: data received from client
	 * @return List of status data
	 */
	@RequestMapping(value = "/getProcessData", method = RequestMethod.POST)
	public @ResponseBody List<IvbMProcess> getProcessData(@RequestParam(value = "farmId") String farmId) {
		return bnn0061TraceabilityMasterService.getProcessData(farmId);
	}
	
	/**
	 * Search cultivation in DB based on bnn0061 object received from client
	 * @param searchConditions: data received from client
	 * @return List of cultivation data
	 */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Bnn0061TraceabilityMasterDataObject> searchData(@RequestBody Bnn0061TraceabilityMasterDataConditions searchConditions) {
		return bnn0061TraceabilityMasterService.searchData(searchConditions);
	}

	/**
	 * Get cultivation information based on farm, area, block, line, column, process, task, status id
	 * @param searchConditions: data received from client
	 * @return cultivation data
	 */
	@RequestMapping(value = "/getSingleData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Bnn0061TraceabilityMasterDataObject getSingleData(@RequestBody Bnn0061TraceabilityMasterDataConditions searchConditions) {
		return bnn0061TraceabilityMasterService.getSingleData(searchConditions);
	}
}