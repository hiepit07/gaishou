package vn.bananavietnam.ict.banana.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.util.Util;

/**
 * 
 * @author NghiaNguyen
 *
 */
@Service
public class Bnn0049AreaListService {

	private static Logger logger = Logger.getLogger(Bnn0049AreaListService.class);

	@Autowired
    private ApplicationContext appContext;
	
	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	private Util util = new Util();
	
	@Autowired
	private UtilDao utilDao;

	// Send array list data to client side
	ObjectMapper mapper = new ObjectMapper();

	/**
	 * Set data for Area, Fark text box on loading
	 * @param farmId : received from Bnn0047 screen
     * @param farmName : received from Bnn0047 screen
	 */
	public void initData(Model model, String farmId, String farmName) {
		//remove cache
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
        TransactionStatus status = txManager.getTransaction(def);
		// Get Farm data for combobox
		// if user Go to 0049 by Method GET from URL
        try {
        	if (farmId.equalsIgnoreCase("")) {
    			List<UtilComponent> farmDataUitl = new ArrayList<UtilComponent>();
    			farmDataUitl = util.getFarmData(utilDao);
    			convertSanitize(farmDataUitl);
    			// Get data for text box
    			try {
    				logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
    						+ LoggerMessage.GET_AREA_BY_FARM_ID + farmId);
    				List<UtilComponent> areaData = new ArrayList<UtilComponent>();
    				areaData = util.getAreaDataByFarmId(utilDao, farmDataUitl.get(0).getFarmId());
    				convertSanitize(areaData);
    				model.addAttribute("areaData", mapper.writeValueAsString(areaData));
    				model.addAttribute("farmName",farmDataUitl.get(0).getFarmName());
    				model.addAttribute("farmId", farmDataUitl.get(0).getFarmId());

    			} catch (Exception ex) {
    				ex.printStackTrace();
    				model.addAttribute("areaData", "");
    				model.addAttribute("farmName","");
    				model.addAttribute("farmId", "");
    	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
    			}
    		} else {// if user Go to 0049 by Method GET from 0047
    			// Get data for text box
    			try {
    				logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
    						+ LoggerMessage.GET_AREA_BY_FARM_ID + farmId);
    				List<UtilComponent> areaData = new ArrayList<UtilComponent>();
    				areaData = util.getAreaDataByFarmId(utilDao, farmId);
    				convertSanitize(areaData);
    				model.addAttribute("farmName", util.convertSanitize(farmName));
    				model.addAttribute("areaData", mapper.writeValueAsString(areaData));
    				model.addAttribute("farmId0047", farmId);
    			} catch (Exception ex) {
    				ex.printStackTrace();
    				model.addAttribute("areaData", "''");
    				model.addAttribute("farmName", "");
    				model.addAttribute("farmId", "");
    	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
    			}
    		}
    		//release transaction
    		txManager.commit(status);
        } catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("areaData", "-1");
			model.addAttribute("farmName","");
			model.addAttribute("farmId", "");
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
			txManager.rollback(status);
		}
	}
	
	/**
	 * Handle farm input data
	 * @param inputData: search result data list
	 */
	private void convertSanitize(List<UtilComponent> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			UtilComponent currentData = inputData.get(i);
			// Farm name
			currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
			// Area name
			currentData.setAreaName(util.convertSanitize(currentData.getAreaName()));
		}
	}
}
