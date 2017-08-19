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

import vn.bananavietnam.ict.banana.component.Bnn0047FarmListDataObject;
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
public class Bnn0047FarmListService {

	private static Logger logger = Logger.getLogger(Bnn0047FarmListService.class);
	
	private Util util = new Util();

	@Autowired
    private ApplicationContext appContext;

	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	@Autowired
	private UtilDao utilDao;
	// send arraylist data to client side
	ObjectMapper mapper = new ObjectMapper();

	/**
	 * Create data for farm combo box on loading.
	 * 
	 * @param model
	 */
	public void initData(Model model) {
		// Get Farm data for combobox
		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		try {
			//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        try {
				//main process
				farmData = util.getFarmData(utilDao);
				convertSanitizeFarm(farmData);		
				model.addAttribute("farmData", mapper.writeValueAsString(farmData));
				//release transaction
				txManager.commit(status);
			} catch (Exception ex) {
				ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
				farmData = new ArrayList<UtilComponent>();
				model.addAttribute("farmData", "''");
				txManager.rollback(status);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
			farmData = new ArrayList<UtilComponent>();
			model.addAttribute("farmData", "''");
		}
		
	}

	/**
	 * Create bnn0049Obj data
	 * @param farmId: data received from client
	 * @param farmName: data received from client
	 * @return bnn0049Obj data
	 */
	public Bnn0047FarmListDataObject bnn0049value(String farmId, String farmName) {
		Bnn0047FarmListDataObject bnn0049Obj = new Bnn0047FarmListDataObject();
		// farm id
		bnn0049Obj.setFarmId(farmId);
		// Farm Name
		bnn0049Obj.setFarmName(farmName);
		return bnn0049Obj;
	}
	
	/**
	 * Handle farm input data
	 * @param inputData: search result data list
	 */
	private void convertSanitizeFarm(List<UtilComponent> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			UtilComponent currentData = inputData.get(i);
			// Farm name
			currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
			// Farm name
			currentData.setAreaName(util.convertSanitize(currentData.getAreaName()));
		}
	}

}
