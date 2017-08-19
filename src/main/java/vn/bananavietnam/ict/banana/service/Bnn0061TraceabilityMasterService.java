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
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.bananavietnam.ict.banana.component.Bnn0061TraceabilityMasterDataConditions;
import vn.bananavietnam.ict.banana.component.Bnn0061TraceabilityMasterDataObject;
import vn.bananavietnam.ict.banana.component.Bnn0075SearchShippingScreenResult;
import vn.bananavietnam.ict.banana.dao.Bnn0061TraceabilityMasterDao;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.model.IvbMBlock;
import vn.bananavietnam.ict.common.db.model.IvbMBlockExample;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.db.model.IvbMStatus;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.db.model.IvbTProduct;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Hieu Dao
 */

@Service
public class Bnn0061TraceabilityMasterService {

	private static Logger logger = Logger.getLogger(Bnn0061TraceabilityMasterService.class);
	
	private Util util = new Util();
	
	@Autowired
	private Bnn0061TraceabilityMasterDao bnn0061TraceabilityMasterDao;

	@Autowired
	private UtilDao utilDao;

	@Autowired
    private ApplicationContext appContext;

	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	// Send array list data to client side
	ObjectMapper mapper = new ObjectMapper();

	/**
	 * Create data for farm, kind, process, task combo box on loading
	 * Create data for task list on loading
	 * @param model
	 */
	public void initData(Model model, Bnn0075SearchShippingScreenResult bnn0075Data) {

		// Get data for combo box
		Bnn0075SearchShippingScreenResult bnn0075Values = new Bnn0075SearchShippingScreenResult();
		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		List<IvbMProcess> processData = new ArrayList<IvbMProcess>();
		List<IvbMTask> taskData = new ArrayList<IvbMTask>();
		List<IvbMStatus> statusData = new ArrayList<IvbMStatus>();		
		HashMap<String, Object> params;
		try {
	    	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	    			+ LoggerMessage.GET_DATA_COMBOBOX);
    		//remove cache
    		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
			try {
		        bnn0075Values = bnn0075Data;
				farmData = util.getFarmData(utilDao);

				logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
		        		+ LoggerMessage.SQL_EXECUTION_FINISHED);
				convertSanitizeFarm(farmData);
				

				if(bnn0075Values.getFarmId() == null){
					model.addAttribute("bnn0075Data", mapper.writeValueAsString("0"));					
					if (farmData.size() > 0) {
						params = new HashMap<String, Object>();
						params.put("farmId", farmData.get(0).getFarmId());
						params.put("processId", "-2");				
						processData = bnn0061TraceabilityMasterDao.getBnn0061TraceabilityMasterMapper().getProcessList(params);
						taskData = bnn0061TraceabilityMasterDao.getBnn0061TraceabilityMasterMapper().getTaskList(params);
						statusData = bnn0061TraceabilityMasterDao.getBnn0061TraceabilityMasterMapper().getStatusList(params);
					}
				} else {
					model.addAttribute("bnn0075Data", mapper.writeValueAsString(bnn0075Values));					
					params = new HashMap<String, Object>();
					params.put("farmId", bnn0075Values.getFarmId());
					params.put("processId", "-2");				
					processData = bnn0061TraceabilityMasterDao.getBnn0061TraceabilityMasterMapper().getProcessList(params);
					taskData = bnn0061TraceabilityMasterDao.getBnn0061TraceabilityMasterMapper().getTaskList(params);
					statusData = bnn0061TraceabilityMasterDao.getBnn0061TraceabilityMasterMapper().getStatusList(params);
				}
				
				convertSanitizeProcess(processData);
				convertSanitizeTask(taskData);
				convertSanitizeStatus(statusData);
				
				model.addAttribute("farmData", mapper.writeValueAsString(farmData));
				model.addAttribute("processData", mapper.writeValueAsString(processData));
				model.addAttribute("taskData", mapper.writeValueAsString(taskData));
				model.addAttribute("statusData", mapper.writeValueAsString(statusData));

				//release transaction
				txManager.commit(status);
		
				logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
		    			+ LoggerMessage.GET_DATA_COMBOBOX_FINISHED);
			} catch (Exception ex) {
				ex.printStackTrace();
				Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);

				model.addAttribute("bnn0075Data", "null");
		        model.addAttribute("farmData", new ArrayList<UtilComponent>());
				model.addAttribute("processData", new ArrayList<IvbMProcess>());
				model.addAttribute("taskData", new ArrayList<IvbMTask>());
				model.addAttribute("statusData", new ArrayList<IvbMStatus>());
				txManager.rollback(status);
			}
		}  catch (Exception ex) {
			ex.printStackTrace();
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);

			model.addAttribute("bnn0075Data", "null");
	        model.addAttribute("farmData", new ArrayList<UtilComponent>());
			model.addAttribute("processData", new ArrayList<IvbMProcess>());
			model.addAttribute("taskData", new ArrayList<IvbMTask>());
			model.addAttribute("statusData", new ArrayList<IvbMStatus>());
		}
	}
	
	/**
	 * Get data area combo box
	 * @param farmId: data received from client
	 * @return List of area data
	 */
	public List<UtilComponent> getAreaData(String farmId) {

		// Get data for combo box
		List<UtilComponent> areaList = new ArrayList<UtilComponent>();

		try {
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_AREA_COMBOBOX);
    		//remove cache
    		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);

            areaList = util.getAreaDataByFarmId(utilDao, farmId);
    		logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            		+ LoggerMessage.SQL_EXECUTION_FINISHED);
    		convertSanitizeArea(areaList);

    		//release transaction
    		txManager.commit(status);
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_DATA_COMBOBOX_FINISHED);
		} catch (Exception ex) {
			ex.printStackTrace();
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            areaList = new ArrayList<UtilComponent>();
		}
		return areaList;
	}

	/**
	 * Get data block combo box
	 * @param blockId: data received from client
	 * @return List of block data
	 */
	public List<IvbMBlock> getBlockData(IvbMBlock blockData) {

		// Get data for combo box
		List<IvbMBlock> blockList = new ArrayList<IvbMBlock>();

		// Create example criteria
		IvbMBlockExample blockExample = new IvbMBlockExample();
		IvbMBlockExample.Criteria blockCriteria = blockExample.createCriteria();

		try {
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_BLOCK_COMBOBOX);
    		//remove cache
    		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);

            blockExample.isDistinct();
        	blockExample.setOrderByClause("BLOCK_NAME");
			blockCriteria.andFarmIdEqualTo(blockData.getFarmId());
			blockCriteria.andAreaIdEqualTo(blockData.getAreaId());
			blockCriteria.andDeleteFlagEqualTo(false);

			blockList = bnn0061TraceabilityMasterDao.getIvbMBlockMapper().selectByExample(blockExample);
    		logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            		+ LoggerMessage.SQL_EXECUTION_FINISHED);
			convertSanitizeBlock(blockList);

			//release transaction
			txManager.commit(status);

			logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_DATA_COMBOBOX_FINISHED);
		} catch (Exception ex) {
			ex.printStackTrace();
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            blockList = new ArrayList<IvbMBlock>();
		}
		return blockList;
	}
	
	/**
	 * Get data line combo box
	 * @param productData: data received from client
	 * @return List of line data
	 */
	public List<String> getLineData(IvbTProduct productData) {

		// Get data for combo box
		List<String> lineList = new ArrayList<String>();

		try {
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_LINE_COMBOBOX);
    		//remove cache
    		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);

            lineList = bnn0061TraceabilityMasterDao.getBnn0061TraceabilityMasterMapper().getLineIdList(productData);
    		logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            		+ LoggerMessage.SQL_EXECUTION_FINISHED);

    		//release transaction
    		txManager.commit(status);

    		logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_DATA_COMBOBOX_FINISHED);
		} catch (Exception ex) {
			ex.printStackTrace();
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            lineList = new ArrayList<String>();
		}
		return lineList;
	}
	
	/**
	 * Get data column combo box
	 * @param productData: data received from client
	 * @return List of column data
	 */
	public List<String> getColumnData(IvbTProduct productData) {

		// Get data for combo box
		List<String> columnList = new ArrayList<String>();

		try {
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_COLUMN_COMBOBOX);
    		//remove cache
    		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);

            columnList = bnn0061TraceabilityMasterDao.getBnn0061TraceabilityMasterMapper().getColumnIdList(productData);
    		logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            		+ LoggerMessage.SQL_EXECUTION_FINISHED);

    		//release transaction
    		txManager.commit(status);

    		logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_DATA_COMBOBOX_FINISHED);
		} catch (Exception ex) {
			ex.printStackTrace();
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            columnList = new ArrayList<String>();
		}
		return columnList;
	}
	
	/**
	 * Get data status combo box
	 * @param farmId: data received from client
	 * @return List of status data
	 */
	public List<IvbMStatus> getStatusData(String farmId) {
		List<IvbMStatus> statusData = new ArrayList<IvbMStatus>();
	
		try {
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            
            try {
            	HashMap<String, Object> params = new HashMap<String, Object>();
        		params.put("farmId", farmId);
        		statusData = bnn0061TraceabilityMasterDao.getBnn0061TraceabilityMasterMapper().getStatusList(params);
        		convertSanitizeStatus(statusData);
        		txManager.commit(status);
            } catch (Exception ex) {
            	txManager.rollback(status);
    			ex.printStackTrace();
    			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
    		}
		} catch (Exception ex) {
			ex.printStackTrace();
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
		}
		return statusData;
	}
	
	/**
	 * Get data task combo box
	 * @param farmId: data received from client
	 * @param processId: data received from client
	 * @return List of task data
	 */
	public List<IvbMTask> getTaskData(String farmId, String processId) {
		List<IvbMTask> taskData = new ArrayList<IvbMTask>();
	
		try {
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            
            try {
            	HashMap<String, Object> params = new HashMap<String, Object>();
        		params.put("farmId", farmId);
        		params.put("processId", processId);
        		taskData = bnn0061TraceabilityMasterDao.getBnn0061TraceabilityMasterMapper().getTaskList(params);
        		convertSanitizeTask(taskData);
        		txManager.commit(status);
            } catch (Exception ex) {
            	txManager.rollback(status);
    			ex.printStackTrace();
    			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
    		}
		} catch (Exception ex) {
			ex.printStackTrace();
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
		}
		return taskData;
	}
	
	/**
	 * Get data process combo box
	 * @param farmId: data received from client
	 * @return List of process data
	 */
	public List<IvbMProcess> getProcessData(String farmId) {
		List<IvbMProcess> processData = new ArrayList<IvbMProcess>();
	
		try {
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            
            try {
            	HashMap<String, Object> params = new HashMap<String, Object>();
        		params.put("farmId", farmId);
        		processData = bnn0061TraceabilityMasterDao.getBnn0061TraceabilityMasterMapper().getProcessList(params);
        		convertSanitizeProcess(processData);
        		txManager.commit(status);
            } catch (Exception ex) {
            	txManager.rollback(status);
    			ex.printStackTrace();
    			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
    		}
		} catch (Exception ex) {
			ex.printStackTrace();
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
		}
		return processData;
	}
	
	/**
	 * Search cultivation result in DB based on search conditions received from client
	 * @param searchConditions: data received from client
	 * @return List of cultivation result data
	 */
	public List<Bnn0061TraceabilityMasterDataObject> searchData(Bnn0061TraceabilityMasterDataConditions searchConditions) {

		List<Bnn0061TraceabilityMasterDataObject> bnn0061ResultList = new ArrayList<Bnn0061TraceabilityMasterDataObject>();
		try{
			logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.CULTIVATION_SEARCH_STARTED);
			//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
			try {
				// search starts
				bnn0061ResultList = bnn0061TraceabilityMasterDao.getBnn0061TraceabilityMasterMapper().searchData(searchConditions);
				logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	            		+ LoggerMessage.SQL_EXECUTION_FINISHED);
				if (bnn0061ResultList.size() > 0) {
	
					// "real" total from search result
					String searchDataTotalCounts = bnn0061TraceabilityMasterDao.getBnn0061TraceabilityMasterMapper().getSearchDataTotalCounts(searchConditions);
					bnn0061ResultList.get(0).setSearchDataTotalCounts(searchDataTotalCounts);
	
					// handle farm input data
					convertSanitize(bnn0061ResultList);
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
				Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
				Bnn0061TraceabilityMasterDataObject tempObj = new Bnn0061TraceabilityMasterDataObject();
				tempObj.setSearchDataTotalCounts("-1");
				bnn0061ResultList.add(tempObj);
				txManager.rollback(status);
			} catch (Exception ex) {
				ex.printStackTrace();
				Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            bnn0061ResultList = null;
				txManager.rollback(status);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            bnn0061ResultList = null;
		}
		return bnn0061ResultList;
	}

	/**
	 * Get cultivation information based on farm, area, block, line, column, process, task, status id
	 * @param searchConditions: data received from client
	 * @return cultivation data
	 */
	public Bnn0061TraceabilityMasterDataObject getSingleData(Bnn0061TraceabilityMasterDataConditions searchConditions) {
		Bnn0061TraceabilityMasterDataObject bnn0061Object = new Bnn0061TraceabilityMasterDataObject();
		//remove cache
		try {
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        try {
		        bnn0061Object = bnn0061TraceabilityMasterDao.getBnn0061TraceabilityMasterMapper().getSingleData(searchConditions);
				convertSanitize(bnn0061Object);
				//release transaction
				txManager.commit(status);
	        } catch (Exception ex) {
				ex.printStackTrace();
				Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            bnn0061Object = null;
	            txManager.rollback(status);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            bnn0061Object = null;
		}
		return bnn0061Object;
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
			// Area name
			currentData.setAreaName(util.convertSanitize(currentData.getAreaName()));
		}
	}

	/**
	 * Handle area input data
	 * @param inputData: get area result data list
	 */
	private void convertSanitizeArea(List<UtilComponent> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			UtilComponent currentData = inputData.get(i);
			// Farm name
			currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
			// Area name
			currentData.setAreaName(util.convertSanitize(currentData.getAreaName()));
		}
	}

	/**
	 * Handle block input data
	 * @param inputData: get block result data list
	 */
	private void convertSanitizeBlock(List<IvbMBlock> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			IvbMBlock currentData = inputData.get(i);
			// Block name
			currentData.setBlockName(util.convertSanitize(currentData.getBlockName()));
			// Note
			currentData.setNote(util.convertSanitize(currentData.getNote()));
		}
	}

	/**
	 * Handle process input data
	 * @param inputData: get process result data list
	 */
	private void convertSanitizeProcess(List<IvbMProcess> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			IvbMProcess currentData = inputData.get(i);
			// Process name
			currentData.setProcessName(util.convertSanitize(currentData.getProcessName()));
		}
	}

	/**
	 * Handle task input data
	 * @param inputData: get task result data list
	 */
	private void convertSanitizeTask(List<IvbMTask> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			IvbMTask currentData = inputData.get(i);
			// Task name
			currentData.setTaskName(util.convertSanitize(currentData.getTaskName()));
			// Working details
			currentData.setWorkingDetails(util.convertSanitize(currentData.getWorkingDetails()));
			// Note
			currentData.setNote(util.convertSanitize(currentData.getNote()));
		}
	}

	/**
	 * Handle status input data
	 * @param inputData: get status result data list
	 */
	private void convertSanitizeStatus(List<IvbMStatus> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			IvbMStatus currentData = inputData.get(i);
			// Status name
			currentData.setStatusName(util.convertSanitize(currentData.getStatusName()));
		}
	}

	/**
	 * Handle bnn0061 input data
	 * @param inputData: get cultivation result data list
	 */
	private void convertSanitize(List<Bnn0061TraceabilityMasterDataObject> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			Bnn0061TraceabilityMasterDataObject currentData = inputData.get(i);
			// Farm name
			currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
			// Area name
			currentData.setAreaName(util.convertSanitize(currentData.getAreaName()));
			// Block name
			currentData.setBlockName(util.convertSanitize(currentData.getBlockName()));
			// Kind name
			currentData.setKindName(util.convertSanitize(currentData.getKindName()));
			// Process name
			currentData.setProcessName(util.convertSanitize(currentData.getProcessName()));
			// Task name
			currentData.setTaskName(util.convertSanitize(currentData.getTaskName()));
			// Status name
			currentData.setStatusName(util.convertSanitize(currentData.getStatusName()));
			// Working detail
			currentData.setWorkingDetails(util.convertSanitize(currentData.getWorkingDetails()));
			// Task note
			currentData.setTaskNote(util.convertSanitize(currentData.getTaskNote()));
			// Cultivation note
			currentData.setCultivationNote(util.convertSanitize(currentData.getCultivationNote()));
		}
	}

	/**
	 * Handle bnn0061 input data
	 * @param inputData: get cultivation result data
	 */
	private void convertSanitize(Bnn0061TraceabilityMasterDataObject inputData) {
		Bnn0061TraceabilityMasterDataObject currentData = inputData;
		// Farm name
		currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
		// Area name
		currentData.setAreaName(util.convertSanitize(currentData.getAreaName()));
		// Block name
		currentData.setBlockName(util.convertSanitize(currentData.getBlockName()));
		// Kind name
		currentData.setKindName(util.convertSanitize(currentData.getKindName()));
		// Process name
		currentData.setProcessName(util.convertSanitize(currentData.getProcessName()));
		// Task name
		currentData.setTaskName(util.convertSanitize(currentData.getTaskName()));
		// Status name
		currentData.setStatusName(util.convertSanitize(currentData.getStatusName()));
		// Working detail
		currentData.setWorkingDetails(util.convertSanitize(currentData.getWorkingDetails()));
		// Task note
		currentData.setTaskNote(util.convertSanitize(currentData.getTaskNote()));
		// Cultivation note
		currentData.setCultivationNote(util.convertSanitize(currentData.getCultivationNote()));
	}
}