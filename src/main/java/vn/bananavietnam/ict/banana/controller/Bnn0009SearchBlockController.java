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

import vn.bananavietnam.ict.banana.component.Bnn0009SearchBlockConditions;
import vn.bananavietnam.ict.banana.component.Bnn0009SearchBlockProductAdjust;
import vn.bananavietnam.ict.banana.component.Bnn0009SearchBlockResult;
import vn.bananavietnam.ict.banana.service.Bnn0009SearchBlockService;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.model.IvbMBlock;
import vn.bananavietnam.ict.common.db.model.IvbTProduct;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Khoa Le
 * Search block, create new block and edit block's info
 * Controller receives request from client and call service to process
 */
@Controller
@RequestMapping(value = "/0009")
public class Bnn0009SearchBlockController {

	@Autowired
	private Bnn0009SearchBlockService searchBlockService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String topPost(HttpServletRequest request, Locale locale, Model model, @ModelAttribute("farmId") String farmId,
			@ModelAttribute("areaId") String areaId) {
		// create data for comboboxUtil util = new Util();
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0009SearchBlock.css", "Bnn0009SearchBlockCss",
    			"Bnn0009SearchBlock.js", "Bnn0009SearchBlockJsDate");
		// check variables to avoid entering link directly
		searchBlockService.initData(model, farmId, areaId);
		return "banana/Bnn0009SearchBlock";
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String topGet(HttpServletRequest request, Locale locale, Model model, @ModelAttribute("farmId") String farmId,
			@ModelAttribute("areaId") String areaId) {
		// create data for combobox
		Util util = new Util();
    	util.setDateParamForCommonFiles(request, model);
    	// set date for screen files
    	util.setDataParamForScreenFiles(request, model, "Bnn0009SearchBlock.css", "Bnn0009SearchBlockCss",
    			"Bnn0009SearchBlock.js", "Bnn0009SearchBlockJsDate");
		// check variables to avoid entering link directly
		searchBlockService.initData(model, farmId, areaId);
		return "banana/Bnn0009SearchBlock";
	}

	/**
     * Search block in DB based on search conditions received from client
     * 
     * @param searchConditions : data received from client
     * @return List of blocks data
     */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Bnn0009SearchBlockResult> searchData(@RequestBody Bnn0009SearchBlockConditions searchConditions) {
        return searchBlockService.searchData(searchConditions);
    }

	/**
	 * Get data for Area combobox by farm id 
	 * 
	 * @param farmId : data received from client
	 * @return Area data
	 */
	@RequestMapping(value = "/getAreaDataByFarmId", method = RequestMethod.POST)
    public @ResponseBody List<UtilComponent> getAreaDataByFarmId(@RequestParam(value = "farmId") String farmId) {
        return searchBlockService.getAreaDataByFarmId(farmId);
    }

	/**
	 * Get data for Block combobox by area id 
	 * 
	 * @param areaId : data received from client
	 * @return Block data
	 */
	@RequestMapping(value = "/getBlockDataByAreaId", method = RequestMethod.POST)
    public @ResponseBody List<IvbMBlock> getBlockDataByAreaId(	@RequestParam(value = "farmId") String farmId,
    															@RequestParam(value = "areaId") String areaId) {
        return searchBlockService.getBlockDataByAreaId(areaId, farmId);
    }

	/**
     * Update block's information to DB
     * 
     * @param blockData : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
	@RequestMapping(value = "/updateData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String updateData(@RequestBody IvbMBlock blockData) {
        return searchBlockService.updateData(blockData);
    }

	/**
     * Insert block's information to DB
     * 
     * @param blockData : data received from client
     * @return String : insert successfully: 1/insert failed: -1
     */
	@RequestMapping(value = "/insertData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String insertData(@RequestBody IvbMBlock blockData) {
        return searchBlockService.insertData(blockData);
    }

	/**
     * Delete Block from DB
     * 
     * @param farmId : data received from client
     * @param areaId : data received from client
     * @param blockId : data received from client
     * @return String : delete successfully: 1/delete failed: -1
     */
	
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST)
    public @ResponseBody String deleteData(@RequestParam(value = "farmId") String farmId,
    		@RequestParam(value = "areaId") String areaId, @RequestParam(value = "blockId") String blockId, 
    		@RequestParam(value = "lastUpdateDate") String lastUpdateDate ) {
		Date lastUpdateDateClient = new Date(Long.valueOf(lastUpdateDate));
        return searchBlockService.deleteData(farmId, areaId, blockId, lastUpdateDateClient);
    }

	/**
     * Get block information based on ids
     * 
     * @param farmId : data received from client
     * @param areaId : data received from client
     * @param blockId : data received from client
     * @return block data
     */
	@RequestMapping(value = "/getSingleData", method = RequestMethod.POST)
    public @ResponseBody IvbMBlock getSingleData(@RequestParam(value = "farmId") String farmId,
    		@RequestParam(value = "areaId") String areaId, @RequestParam(value = "blockId") String blockId) {
        return searchBlockService.getSingleData(farmId, areaId, blockId);
    }

	/**
	 * Get Number of Lines and Columns of specific farm
	 * 
	 * @param farmId : received from client
	 * @return Lines Count + ":" + Columns Count
	 */
	@RequestMapping(value = "/getFarmLinesAndColumnsCount", method = RequestMethod.POST)
    public @ResponseBody String getFarmLinesAndColumnsCount(@RequestParam(value = "farmId") String farmId) {
        return searchBlockService.getFarmLinesAndColumnsCount(farmId);
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
    public @ResponseBody List<IvbTProduct> getProductData(@RequestParam(value = "farmId") String farmId,
    		@RequestParam(value = "areaId") String areaId, @RequestParam(value = "blockId") String blockId,
    		@RequestParam(value = "previousRound") Boolean previousRound) {
        return searchBlockService.getProductData(farmId, areaId, blockId, previousRound);
    }

	/**
     * Update product's information to DB
     * 
     * @param productData : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
	@RequestMapping(value = "/updateProductData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String updateProductData(@RequestBody Bnn0009SearchBlockProductAdjust productData) {
        return searchBlockService.updateProductData(productData);
    }
}