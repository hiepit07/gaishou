package vn.bananavietnam.ict.banana.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fasterxml.jackson.databind.ObjectMapper;
import vn.bananavietnam.ict.banana.component.Bnn0045SearchCultivationConditions;
import vn.bananavietnam.ict.banana.component.Bnn0045SearchCultivationInFormCbbResult;
import vn.bananavietnam.ict.banana.component.Bnn0045SearchCultivationResult;
import vn.bananavietnam.ict.banana.dao.Bnn0045SearchCultivationDao;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Hung Bui
 */
@Service
public class Bnn0045SearchCultivationService {

	private static Logger logger = Logger.getLogger(Bnn0045SearchCultivationService.class);

	private Util util = new Util();

	@Autowired
    private ApplicationContext appContext;
	
	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}
	
	@Autowired
	private Bnn0045SearchCultivationDao bnn0045SearchCultivationDao;
	
	@Autowired
	private UtilDao utilDao;
	
	// send array list data to client side
	ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Get data for farm combobox by farm id, kind id 
	 * 
	 * @param farmId, kindId : data received from client
	 * @return Farm data
	 */
	public List<UtilComponent> getFarmData() {
		// Get Farm data for combobox
		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		try {			
			//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
			
	        farmData = util.getFarmData(utilDao);
	        convertSanitizeFarm(farmData);
			//release transaction
			txManager.commit(status);
		} catch (Exception ex) {
			ex.printStackTrace();
			farmData = new ArrayList<UtilComponent>();
			// log to file
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
		}
		return farmData;
	}
	
	/**
	 * Get data for kind combobox by farm id, kind id 
	 * 
	 * @param farmId, kindId : data received from client
	 * @return kind data
	 */
	public List<Bnn0045SearchCultivationInFormCbbResult> getKindData(String farmId) {
		// Get Kind data for combobox
		List<Bnn0045SearchCultivationInFormCbbResult> kindData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
		// Get Farm data for combobox
		List<UtilComponent> areaData = new ArrayList<UtilComponent>();
		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		ArrayList<String> listAreaID = new ArrayList<String>();
		ArrayList<String> listFarmID = new ArrayList<String>();
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			
			//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        
	        farmData = util.getFarmData(utilDao);
	        if (farmId.equalsIgnoreCase("-2")) {
	        	areaData = util.getAreaDataByFarmId(utilDao, "-2");
	        } else {
	        	areaData = util.getAreaDataByFarmId(utilDao, farmId);
	        }
	        if (farmData.size() > Constants.LIST_IS_EQUAL_TO_ZERO && areaData.size() > Constants.LIST_IS_EQUAL_TO_ZERO) {
	        	for (int i = 0; i < areaData.size(); i++) {
                	listAreaID.add(areaData.get(i).getAreaId());
                }	        	
	        	if (farmId.equalsIgnoreCase("-2")) {
        			for (int j = 0; j < farmData.size(); j++) {
                    	listFarmID.add(farmData.get(j).getFarmId());
                    }
	        	} else {
	        		listFarmID.add(farmId);
	        	}	        	
                params.put("listAreaID", listAreaID);
                params.put("listFarmID", listFarmID);
                kindData = bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper().getDataKindCbb(params);
                convertSanitizeKind(kindData);
            } else {
            	kindData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
            	logger.error(LoggerMessage.LOG_ERROR_PREFIX + util.getUserInfo().getID() + ","
                        + LoggerMessage.CULTIVATION_SEARCH_RESULT_0_STRING);
            }

			//release transaction
			txManager.commit(status);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			kindData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
			// log to file
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
		}
		return kindData;
	}
    
    /**
     * Search cultivation in DB based on search conditions received from client
     * 
     * @param searchConditions : data received from client
     * @return List of cultivation data
     */
	public List<Bnn0045SearchCultivationResult> searchData(Bnn0045SearchCultivationConditions searchConditions) {
		List<Bnn0045SearchCultivationResult> bnn0045ResultList = new ArrayList<Bnn0045SearchCultivationResult>();
        HashMap<String, Object> params = createSearchConditionParams(searchConditions);
        try{
        	//remove cache
    		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
	        try {
	        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                    + LoggerMessage.CULTIVATION_SEARCH_STARTED);
	    		
	            // search starts
	        	bnn0045ResultList = bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper().searchData(params);
	        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + "," + LoggerMessage.SQL_EXECUTION_FINISHED);
	            if (bnn0045ResultList.size() > Constants.LIST_IS_EQUAL_TO_ZERO) {
	                // "real" total from search result
	                String searchDataTotalCounts = bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper().getSearchDataTotalCounts(params);
	                bnn0045ResultList.get(0).setSearchDataTotalCounts(searchDataTotalCounts);
	                // handle user input data
	                convertSanitize(bnn0045ResultList);
	                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                        + LoggerMessage.CULTIVATION_SEARCH_RESULT_STRING.replace("$1", searchDataTotalCounts));
	            } else {
	            	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                        + LoggerMessage.CULTIVATION_SEARCH_RESULT_0_STRING);
	            }
	    		//release transaction
	    		txManager.commit(status);
	        } catch (OutOfMemoryError ex) {
	        	ex.printStackTrace();
	            Bnn0045SearchCultivationResult tempObj = new Bnn0045SearchCultivationResult();
	            tempObj.setSearchDataTotalCounts("-1");
	            bnn0045ResultList.add(tempObj);
	            txManager.rollback(status);
	        	// log to file
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            bnn0045ResultList = null;
	            txManager.rollback(status);
	        	// log to file
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	        }
        } catch (Exception ex) {
			ex.printStackTrace();
			// log to file
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            bnn0045ResultList = null;
		}
        
        return bnn0045ResultList;
	}
	
	/**
     * Create search conditions parameters from data received from client
     * 
     * @param searchConditions : data received from client
     * @return HashMap object
     */    
    private HashMap<String, Object> createSearchConditionParams(Bnn0045SearchCultivationConditions searchConditions) {
    	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
    			+ LoggerMessage.CREATE_SEARCH_CONDITIONS_STARTED);
    	HashMap<String, Object> params = new HashMap<String, Object>();
        // Farm id
    	List<String> farmId = new ArrayList<String>();
    	if (searchConditions.getFarmId() == null || searchConditions.getFarmId().equalsIgnoreCase("-2")) {
    		List<UtilComponent> listFarm = new ArrayList<UtilComponent>();
    		listFarm = getFarmData();
    		if (listFarm.size() > 0) {
    			for (int i = 0; i < listFarm.size(); i++) {
        			farmId.add(listFarm.get(i).getFarmId());
        		}
    		} else {
    			farmId.add("");
    		}    		
    	} else {
    		farmId.add(searchConditions.getFarmId());
    	}    	
    	params.put("farmId", farmId);
    	// processId
        params.put("processId", searchConditions.getProcessId() == null ? "" : searchConditions.getProcessId());
        // kind Id
        List<String> kindId = new ArrayList<String>();
    	if (searchConditions.getKindId() == null || searchConditions.getKindId().equalsIgnoreCase("-2")) {
    		List<Bnn0045SearchCultivationInFormCbbResult> listKind = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();  		
    		if (searchConditions.getFarmId() == null || searchConditions.getFarmId().equalsIgnoreCase("-2")) {
    			listKind = getKindData("-2");
        	} else {
        		listKind = getKindData(searchConditions.getFarmId());
        	}
    		if (listKind.size() > 0) {
	    		for (int i = 0; i < listKind.size(); i++) {
	    			kindId.add(listKind.get(i).getKindId());
	    		}
	    	} else {
	    		kindId.add("");
			} 
    	} else {
    		kindId.add(searchConditions.getKindId());
    	}        
        params.put("kindId", kindId);
        // task Id
        params.put("taskId", searchConditions.getTaskId() == null ? "" : searchConditions.getTaskId());
        // From parameter
        params.put("fromRow", Integer.valueOf(searchConditions.getFromRow()));
        // Number of items in a page
        params.put("itemCount", Integer.valueOf(searchConditions.getItemCount()));

        return params;
    }
    
    /**
     * Handle Cultivation input data
     * 
     * @param inputData : search result data list
     */
    private void convertSanitize(List<Bnn0045SearchCultivationResult> inputData) {
        for (int i = 0; i < inputData.size(); i++) {
        	Bnn0045SearchCultivationResult currentData = inputData.get(i);
            // Farm name
            currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
            // Process Name
            currentData.setProcessName(util.convertSanitize(currentData.getProcessName()));
            // Kind Name
            currentData.setKindName(util.convertSanitize(currentData.getKindName()));
            // Task Name
            currentData.setTaskName(util.convertSanitize(currentData.getTaskName()));
            // Workingdetails
            currentData.setWorkingdetails(util.convertSanitize(currentData.getWorkingdetails()));
            // Note
            currentData.setNote(util.convertSanitize(currentData.getNote()));  
        }
    }
    
    /**
	 * Get data for Process combobox by farm id, kind id 
	 * 
	 * @param farmId, kindId : data received from client
	 * @return Process data
	 */
	public List<Bnn0045SearchCultivationInFormCbbResult> getProcessData() {
		// Get Farm data for combobox
		List<Bnn0045SearchCultivationInFormCbbResult> processData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
			
	        processData = bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper().getDataProcessCbb(params);
	        convertSanitizeProcess(processData);
			//release transaction
			txManager.commit(status);
		} catch (Exception ex) {
			ex.printStackTrace();
			processData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
			// log to file
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
		}
		return processData;
	}
	
	/**
	 * Get data for Task combobox by farm id, kind id 
	 * 
	 * @param farmId, kindId : data received from client
	 * @return Task data
	 */
	public List<Bnn0045SearchCultivationInFormCbbResult> getTaskData() {
		// Get Farm data for combobox
		List<Bnn0045SearchCultivationInFormCbbResult> taskData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        
			taskData = bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper().getDataTaskCbb(params);
			convertSanitizeTask(taskData);
			//release transaction
			txManager.commit(status);
		} catch (Exception ex) {
			ex.printStackTrace();
			taskData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
			// log to file
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
		}
		return taskData;
	}
	
	/**
	 * Handle farm input data
	 * @param inputData: get farm result data list
	 */
	private void convertSanitizeFarm(List<UtilComponent> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			UtilComponent currentData = inputData.get(i);
			// Farm name
			currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
		}
	}
	
	/**
	 * Handle kind input data
	 * @param inputData: get kind result data list
	 */
	private void convertSanitizeKind(List<Bnn0045SearchCultivationInFormCbbResult> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			Bnn0045SearchCultivationInFormCbbResult currentData = inputData.get(i);
			// Kind name
			currentData.setKindName(util.convertSanitize(currentData.getKindName()));
		}
	}
	
	/**
	 * Handle farm input data
	 * @param inputData: get farm result data list
	 */
	private void convertSanitizeProcess(List<Bnn0045SearchCultivationInFormCbbResult> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			Bnn0045SearchCultivationInFormCbbResult currentData = inputData.get(i);
			// Process name
			currentData.setProcessName(util.convertSanitize(currentData.getProcessName()));
		}
	}
	
	/**
	 * Handle farm input data
	 * @param inputData: get farm result data list
	 */
	private void convertSanitizeTask(List<Bnn0045SearchCultivationInFormCbbResult> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			Bnn0045SearchCultivationInFormCbbResult currentData = inputData.get(i);
			// Task name
			currentData.setTaskName(util.convertSanitize(currentData.getTaskName()));
		}
	}
 
}