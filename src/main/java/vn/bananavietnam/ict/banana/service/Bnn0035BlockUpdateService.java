package vn.bananavietnam.ict.banana.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.bananavietnam.ict.banana.component.Bnn0035AreaExtendObject;
import vn.bananavietnam.ict.banana.component.BnnBlockCultivationResult;
import vn.bananavietnam.ict.banana.dao.Bnn0035BlockUpdateDao;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.model.IvbMArea;
import vn.bananavietnam.ict.common.db.model.IvbMAreaKey;
import vn.bananavietnam.ict.common.db.model.IvbMBlock;
import vn.bananavietnam.ict.common.db.model.IvbMBlockExample;
import vn.bananavietnam.ict.common.db.model.IvbMKind;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.db.model.IvbMStatus;
import vn.bananavietnam.ict.common.db.model.IvbMStatusExample;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.db.model.IvbTCultivationResult;
import vn.bananavietnam.ict.common.db.model.IvbTProduct;
import vn.bananavietnam.ict.common.db.model.IvbTProductKey;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Khoa Le
 * Create new cultivation result, edit and delete existed cultivation result
 * Service connects to DB, processes requests and returns results to Controller
 */
@Service
public class Bnn0035BlockUpdateService {

	private static Logger logger = Logger.getLogger(Bnn0035BlockUpdateService.class);

	private Util util = new Util();

	@Autowired
	private Bnn0035BlockUpdateDao bnn0035BlockUpdateDao;

	@Autowired
	private UtilDao utilDao;

	// send arraylist data to client side
	ObjectMapper mapper = new ObjectMapper();

	@Autowired
    private ApplicationContext appContext;

	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	/**
     * Create data for combobox on loading
     * 
     * @param model : jsp model
     * @param farmId : received from Bnn0035BlockUpdate screen
     * @param farmName : received from Bnn0035BlockUpdate screen
     * @param areaId : received from Bnn0035BlockUpdate screen
     * @param areaName : received from Bnn0035BlockUpdate screen
     * @return init result: "8000": success/"8001": failed
     */
    public String initData(Model model, String farmId, String farmName, String areaId, String areaName) {
    	// variables definition
    	boolean isAreaSelectable = false;
    	List<IvbMProcess> processData = new ArrayList<IvbMProcess>();
    	List<IvbMStatus> statusData = new ArrayList<IvbMStatus>();
    	List<Bnn0035AreaExtendObject> areaItemsArray = new ArrayList<Bnn0035AreaExtendObject>();
    	String kindId = "";
    	String kindName = "";

		// remove cache
    	try {
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
			// main process
	    	try {
	    		// check for id data to see if user goes to this screen by entering URL directly
	    		logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	        			+ LoggerMessage.CHECK_SCREEN_URL_DATA);
	    		// check variables to avoid entering link directly
	    		if (farmId.equals("") || farmName.equals("") || areaId.equals("") || areaName.equals("")) {
	    			// user has permission to select area
	    			isAreaSelectable = true;
	    			// get farm data
	    			List<UtilComponent> farmDataUtil = util.getFarmData(utilDao);
	    			farmId = farmDataUtil.get(0).getFarmId();
	    			farmName = util.convertSanitize(farmDataUtil.get(0).getFarmName());
	    			// get area data
	    			List<UtilComponent> areaData = util.getAreaDataByFarmId(utilDao, farmId);
	    			areaId = areaData.get(0).getAreaId();
	    			areaName = util.convertSanitize(areaData.get(0).getAreaName());
	    		}

	        	// get Kind data for joining with Cultivation table
	            logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	        			+ LoggerMessage.GET_KIND_DATA);
	        	// get kind id
	        	IvbMAreaKey areaKey = new IvbMAreaKey();
	        	areaKey.setFarmId(farmId);
	        	areaKey.setAreaId(areaId);
	        	IvbMArea areaData = bnn0035BlockUpdateDao.getIvbMAreaMapper().selectByPrimaryKey(areaKey);
	        	kindId = areaData.getKindId();
	        	// get kind name
	        	IvbMKind kindData = bnn0035BlockUpdateDao.getIvbMKindMapper().selectByPrimaryKey(kindId);
	        	kindName = util.convertSanitize(kindData.getKindName());
	
	        	// get Process data
	        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	        			+ LoggerMessage.GET_PROCESS_COMBOBOX);
	        	processData = getProcessData(farmId, kindId);
	
	        	// get Status data
	        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	        			+ LoggerMessage.GET_STATUS_COMBOBOX);
	        	IvbMStatusExample statusExample = new IvbMStatusExample();
	        	IvbMStatusExample.Criteria statusCriteria = statusExample.createCriteria();
	        	statusCriteria.andFarmIdEqualTo(farmId);
	        	statusCriteria.andDeleteFlagEqualTo(Constants.DELETE_FLAG_OFF);
	    		statusData = bnn0035BlockUpdateDao.getIvbMStatusMapper().selectByExample(statusExample);
	    		// handle user input data
	    		for (int j = 0; j < statusData.size(); j++) {
	    			IvbMStatus currentItem = statusData.get(j);
	        		currentItem.setStatusId(util.convertSanitize(currentItem.getStatusId()));
	        		currentItem.setStatusName(util.convertSanitize(currentItem.getStatusName()));
	        	}
	        	model.addAttribute("processData", mapper.writeValueAsString(processData));
	            model.addAttribute("statusData", mapper.writeValueAsString(statusData));
	
		        // get data for area combobox
		        // create params
		 		HashMap<String, Object> params = new HashMap<String, Object>();
		        // Farm id
		        params.put("farmId", farmId);
		        // Delete flag
		        params.put("deleteFlag", Constants.DELETE_FLAG_OFF);
		
		        // check role
				String roleId = util.getUserInfo().getROLEID();
		        if (roleId.equals(Constants.SYSTEM_MANAGER) || roleId.equals(Constants.CCO) || roleId.equals(Constants.FARM_MANAGER)) {
		        	areaItemsArray = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().getAreaAndKindDataFromMaster(params);
		        } else {
		        	// only get area belong to area_manager
		        	params.put("usersId", util.getUserInfo().getID());
		        	areaItemsArray = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().getAreaAndKindDataFromAffiliation(params);
		        }
		
				for (int k = 0; k < areaItemsArray.size(); k++) {
					Bnn0035AreaExtendObject currentItem = areaItemsArray.get(k);
		    		currentItem.setAreaName(util.convertSanitize(currentItem.getAreaName()));
		    		currentItem.setKindName(util.convertSanitize(currentItem.getKindName()));
		    	}
		
		        // set information received from parents' screens to client
		    	model.addAttribute("farmIdGlobal", "'" + farmId + "'");
		    	model.addAttribute("farmNameGlobal", "\"" + farmName + "\"");
		    	model.addAttribute("selectedAreaIdGlobal", "'" + areaId + "'");
		    	model.addAttribute("selectedAreaNameGlobal", "\"" + areaName + "\"");
		    	model.addAttribute("selectedKindIdGlobal", "'" + kindId + "'");
		    	model.addAttribute("selectedKindNameGlobal", "'" + kindName + "'");
		    	model.addAttribute("isAreaSelectable", isAreaSelectable);
		    	model.addAttribute("areaItemsArray", areaItemsArray);

		    	// release transaction
				txManager.commit(status);
	    	} catch (Exception ex) {
	    		ex.printStackTrace();
	    		// roll back transaction
	    		txManager.rollback(status);
	    		// log to file
	    		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            // error page
				return Constants.INIT_RESULT_FAILED;
	    	}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		// log to file
    		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            // error page
			return Constants.INIT_RESULT_FAILED;
    	}

    	return Constants.INIT_RESULT_SUCCESSFUL;
    }

    /**
     * Get Block information based on ids
     * 
     * @param farmId : data received from client
     * @param areaId : data received from client
     * @return block data
     */
    public List<IvbMBlock> getBlockData(String farmId, String areaId) {
    	List<IvbMBlock> blockResultList = new ArrayList<IvbMBlock>();

    	try {
			// remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
			// main process
	        logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
					+ LoggerMessage.GET_BLOCK_BY_FARM_ID_AREA_ID + farmId + ", " + areaId);
	        try {
				IvbMBlockExample blockExample = new IvbMBlockExample();
				IvbMBlockExample.Criteria blockCriteria = blockExample.createCriteria();
				blockCriteria.andFarmIdEqualTo(farmId);
				blockCriteria.andAreaIdEqualTo(areaId);
				blockCriteria.andDeleteFlagEqualTo(Constants.DELETE_FLAG_OFF);
				blockExample.setOrderByClause("BLOCK_NAME");
				// search starts
				blockResultList = bnn0035BlockUpdateDao.getIvbMBlockMapper().selectByExample(blockExample);
				// handle user input data
				for (int i = 0; i < blockResultList.size(); i++) {
					IvbMBlock currentItem = blockResultList.get(i);
		    		currentItem.setBlockId(util.convertSanitize(currentItem.getBlockId()));
		    		currentItem.setBlockName(util.convertSanitize(currentItem.getBlockName()));
		    	}
				// release transaction
				txManager.commit(status);
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        	// roll back transaction
	    		txManager.rollback(status);
	    		// log to file
	    		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            blockResultList = new ArrayList<IvbMBlock>();
	        }
    	} catch (Exception ex) {
			ex.printStackTrace();
			// log to file
    		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            blockResultList = new ArrayList<IvbMBlock>();
		}

		return blockResultList;
    }

    /**
     * Get cultivation result information based on ids
     * 
     * @param farmId : data received from client
     * @param areaId : data received from client
     * @param kindId : data received from client
     * @return cultivation data
     */
	public List<BnnBlockCultivationResult> getCultivationResultData(String farmId, String areaId, String kindId) {
		List<BnnBlockCultivationResult> cultivationResultList = new ArrayList<BnnBlockCultivationResult>();

		try {
			// remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        // main process
	        logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
					+ LoggerMessage.GET_CULTIVATION_RESULT_BY_ALL_ID + farmId + ", " + areaId + ", " + kindId);
			try {
		        // create params
				HashMap<String, Object> params = new HashMap<String, Object>();
		        // Farm id
		        params.put("farmId", farmId);
		        // Area id
		        params.put("areaId", areaId);
		        // Kind id
		        params.put("kindId", kindId);
		        // Delete flag
		        params.put("deleteFlag", Constants.DELETE_FLAG_OFF);
		        // Block work flag
		        params.put("blockWorkFlag", Constants.FLAG_ON);
		        // Previous round flag
		        params.put("previousRound", Constants.CURRENT_ROUND);
		        // search starts
		        cultivationResultList = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().getCultivationResultData(params);
				// handle user input data
		        convertSanitize(cultivationResultList);
				// release transaction
				txManager.commit(status);
			} catch (Exception ex) {
	        	ex.printStackTrace();
	        	// roll back transaction
	    		txManager.rollback(status);
	    		// log to file
	    		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            cultivationResultList = new ArrayList<BnnBlockCultivationResult>();
	        }
		} catch (Exception ex) {
			ex.printStackTrace();
			// log to file
    		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            cultivationResultList = new ArrayList<BnnBlockCultivationResult>();
		}

		return cultivationResultList;
	}

	/**
     * Get process information based on ids
     * 
     * @param farmId : data received from client
     * @param kindId : data received from client
     * @return process data
     */
    public List<IvbMProcess> getProcessData(String farmId, String kindId) {
    	List<IvbMProcess> processResultList = new ArrayList<IvbMProcess>();

    	try {
			// remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        // main process
	        try {
		    	// create params
		    	HashMap<String, Object> params = new HashMap<String, Object>();
		        // Farm id
		        params.put("farmId", farmId);
		        // Kind id
		        params.put("kindId", kindId);
		        // Delete flag
		        params.put("deleteFlag", Constants.DELETE_FLAG_OFF);
		        // Block work flag
		        params.put("blockWorkFlag", Constants.FLAG_ON);
		        // search starts
		        processResultList = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().getProcessData(params);
	 			// handle user input data
		        for (int i = 0; i < processResultList.size(); i++) {
	        		IvbMProcess currentItem = processResultList.get(i);
	        		currentItem.setProcessId(util.convertSanitize(currentItem.getProcessId()));
	        		currentItem.setProcessName(util.convertSanitize(currentItem.getProcessName()));
	        	}
	 			// release transaction
	 			txManager.commit(status);
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        	// roll back transaction
	    		txManager.rollback(status);
	    		// log to file
	    		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            processResultList = new ArrayList<IvbMProcess>();
	        }
    	} catch (Exception ex) {
			ex.printStackTrace();
			// log to file
    		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            processResultList = new ArrayList<IvbMProcess>();
		}

    	return processResultList;
    }

	/**
     * Get task information based on ids
     * 
     * @param farmId : data received from client
     * @param kindId : data received from client
     * @param processId : data received from client
     * @return task data
     */
    public List<IvbMTask> getTaskData(String farmId, String kindId, String processId) {
    	List<IvbMTask> taskResultList = new ArrayList<IvbMTask>();

    	try {
			// remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        // main process
	        logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	    			+ LoggerMessage.GET_TASK_COMBOBOX + farmId + ", " + kindId + ", " + processId);
	        try {
		    	// create params
		    	HashMap<String, Object> params = new HashMap<String, Object>();
		        // Farm id
		        params.put("farmId", farmId);
		        // Kind id
		        params.put("kindId", kindId);
		        // Process id
		        params.put("processId", processId);
		        // Delete flag
		        params.put("deleteFlag", Constants.DELETE_FLAG_OFF);
		        // Block work flag
		        params.put("blockWorkFlag", Constants.FLAG_ON);
		        taskResultList = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().getTaskData(params);
		    	// handle user input data
				for (int i = 0; i < taskResultList.size(); i++) {
					IvbMTask currentItem = taskResultList.get(i);
		    		currentItem.setTaskId(util.convertSanitize(currentItem.getTaskId()));
		    		currentItem.setTaskName(util.convertSanitize(currentItem.getTaskName()));
		    	}
				// release transaction
				txManager.commit(status);
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        	// roll back transaction
	    		txManager.rollback(status);
	    		// log to file
	    		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            taskResultList = null;
	        }
    	} catch (Exception ex) {
			ex.printStackTrace();
			// log to file
    		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            taskResultList = null;
		}

		return taskResultList;
    }

    /**
     * Insert cultivation result's information to DB
     * 
     * @param cultivationResultData : data received from client
     * @return String : insert successfully: 1/insert failed: -1
     */
    public String insertData(BnnBlockCultivationResult cultivationResultData) {
    	// variable definition
        String returnValue = Constants.INSERT_RESULT_SUCCESSFUL;
        // insert starts
        try {
        	// check for user input
            if (!checkInputBlankFields(cultivationResultData)) {
            	// blank field(s)
            	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.BLANK_FIELDS_ERROR_MESSAGE);
                returnValue = Constants.VALIDATE_BLANK_FIELDS;
                return returnValue;
            }
        	// transaction starts
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
            	// variable definition
            	boolean isTransactionSuccess = true;
            	// get block id array
            	String[] blockIdArray = cultivationResultData.getCurrentBlockIdString().split(",");
            	Date lastUpdateDateProduct = cultivationResultData.getLastUpdateDateProduct();
            	// loop through array to insert all cultivation result
            	for (int i = 0; i < blockIdArray.length; i++) {
            		// get block id
            		String blockId = blockIdArray[i];

            		HashMap<String, Object> params;
                	int updateBlockDate = 0;
            		
            		// Check change point code to see if products data need to be updated
            		String changePointCode = cultivationResultData.getChangePointCode();
            		if (!changePointCode.equals(Constants.TASK_CHANGE_POINT_CODE_NONE)) {
            			// create params
            	    	params = new HashMap<String, Object>();
            	        // Farm id
            	        params.put("farmId", cultivationResultData.getFarmId());
            	        // Area id
            	        params.put("areaId", cultivationResultData.getAreaId());
            	        // Block id
            	        params.put("blockId", blockId);
            	        // Previous round
            	        params.put("previousRound", Constants.CURRENT_ROUND);
            	        // deleteFlag
            	        params.put("deleteFlag", null);
            	       
            	        List<IvbTProductKey> productList = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().getProductData(params);
            	        if (productList.size() > 0) {
            	        	// create params
                	    	params = new HashMap<String, Object>();
                	        // Farm id
                	        params.put("farmId", cultivationResultData.getFarmId());
                	        // Area id
                	        params.put("areaId", cultivationResultData.getAreaId());
                	        // Block id
                	        params.put("blockId", blockId);
                	        // Previous round
                	        params.put("previousRound", Constants.CURRENT_ROUND);
                	        // lastUpdateDate
                	        params.put("lastUpdateDate", lastUpdateDateProduct);
                	        params.put("deleteFlag", false);
                	        params.put("updateUserId", util.getUserInfo().getID());
                	        if (changePointCode.equals(Constants.TASK_CHANGE_POINT_CODE_CULTIVATION_START)) {
	                	        // default cultivation start date
	                	        params.put("defaultCultivationStartDate", cultivationResultData.getWorkingDate());
	                	    } else if (changePointCode.equals(Constants.TASK_CHANGE_POINT_CODE_PLANTING)) {
	                			params.put("plantingDate", cultivationResultData.getWorkingDate());
	                		} else if (changePointCode.equals(Constants.TASK_CHANGE_POINT_CODE_FLOWERING)) {
	                			params.put("floweringDate", cultivationResultData.getWorkingDate());
	                		} else if (changePointCode.equals(Constants.TASK_CHANGE_POINT_CODE_BAGGED)) {
	                			params.put("bagDate", cultivationResultData.getWorkingDate());
	                		} else if (changePointCode.equals(Constants.TASK_CHANGE_POINT_CODE_HARVESTED)) {
	                			params.put("harvestDate", cultivationResultData.getWorkingDate());
	                		} else {
	                			logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
	                        			+ LoggerMessage.PRODUCT_UPDATE_FAILED);
	                			// switch flag
	                        	isTransactionSuccess = false;
	                        	// set return value and break out of loop
	                        	returnValue = Constants.INSERT_RESULT_FAILED_PRODUCT;
	                        	break;
	                		}
            	        	updateBlockDate = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().updateProductDate(params);
            	        	if (updateBlockDate == 0) { // update failed
	                			IvbTProduct productData = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().getLastUpdateDateProduct(params);
	                			if (productData != null && lastUpdateDateProduct.compareTo(productData.getLastUpdateDate()) <= 0 ) {
	                				logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
	                            			+ LoggerMessage.PRODUCT_UPDATE_FAILED_UPDATE_DATE);
	                                returnValue = Constants.INSERT_RESULT_FAILED_PRODUCT_UPDATE_DATE;
	                			} else {
	                				logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
	                            			+ LoggerMessage.PRODUCT_UPDATE_FAILED);
	                            	// switch flag
	                            	isTransactionSuccess = false;
	                            	// set return value and break out of loop
	                            	returnValue = Constants.INSERT_RESULT_FAILED_PRODUCT;
	                			}
	                        	break;
	                        }
            	        } else {
            	        	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                        			+ LoggerMessage.PRODUCT_UPDATE_FAILED);
                        	// switch flag
                        	isTransactionSuccess = false;
                        	// set return value and break out of loop
                        	returnValue = Constants.INSERT_RESULT_FAILED_BLOCK_DELETE;
            	        	break;
            	        }
            		}

            		// start inserting
            		IvbTCultivationResult resultObj = new IvbTCultivationResult();
                	// Farm id
                	resultObj.setFarmId(cultivationResultData.getFarmId());
                	// Area id
                	resultObj.setAreaId(cultivationResultData.getAreaId());
                	// Block id
                	resultObj.setBlockId(blockId);
                	// Line id
                	resultObj.setLineId(Constants.DEFAULT_LINE_ID);
                	// Column id
                	resultObj.setColumnId(Constants.DEFAULT_COLUMN_ID);
                	// Previous round
                	resultObj.setPreviousRound(Constants.CURRENT_ROUND);
                	// Working date
                	resultObj.setWorkingDate(cultivationResultData.getWorkingDate());
                	// Block work flag
                	resultObj.setBlockWorkFlag(Constants.FLAG_ON);
                	// Process id
                	resultObj.setProcessId(cultivationResultData.getProcessId());
                	// Task id
                	resultObj.setTaskId(cultivationResultData.getTaskId());
                	// Status id
                	resultObj.setStatusId(cultivationResultData.getStatusId());
                	// Note
                	resultObj.setNote(cultivationResultData.getNote());
                	// Create user id
                	resultObj.setCreateUserId(util.getUserInfo().getID());
                	// Update user id
                	resultObj.setUpdateUserId(util.getUserInfo().getID());
                	// Delete flag
                	resultObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);

                    int result = bnn0035BlockUpdateDao.getIvbTCultivationResultMapper().insert(resultObj);
                    if (result == 0) { // insert failed
                    	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.CULTIVATION_RESULT_INSERT_FAILED);
                    	// switch flag
                    	isTransactionSuccess = false;
                    	// set return value and break out of loop
                    	returnValue = Constants.INSERT_RESULT_FAILED_CULTIVATION;
                    	break;
                    }
            	}
            	// commit or roll back transaction based on result
            	if (isTransactionSuccess) {
            		// register to DB
                    txManager.commit(status);
            	} else {
            		// roll back transaction
            		txManager.rollback(status);
            	}
            } catch (DuplicateKeyException ex) {
                ex.printStackTrace();
                // log to file
        		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.DUPLICATE_KEY_EXCEPTION_ERROR_MESSAGE, ex);
                returnValue = Constants.INSERT_RESULT_DUPLICATED;
                txManager.rollback(status);
            }  catch (Exception ex) {
                ex.printStackTrace();
                // log to file
        		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                returnValue = Constants.INSERT_RESULT_FAILED_EXCEPTION;
                txManager.rollback(status);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // log to file
    		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            returnValue = Constants.INSERT_RESULT_FAILED_EXCEPTION;
        }
        return returnValue;
	}

    /**
     * Delete cultivation result from DB
     * 
     * @param cultivationResultData : data received from client
     * @return String : delete successfully: 1/delete failed: -1
     */
	public String deleteData(BnnBlockCultivationResult cultivationResultData) {
		// variable definition
        String returnValue = Constants.DELETE_RESULT_SUCCESSFUL;
        // delete starts
        try {
        	// transaction starts
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
            	HashMap<String, Object> params;
            	int updateBlockDate = 0;
            	// Check change point code to see if products data need to be updated
        		String changePointCode = cultivationResultData.getChangePointCode();
        		if (!changePointCode.equals(Constants.TASK_CHANGE_POINT_CODE_NONE)) {
        			// create params
        	    	params = new HashMap<String, Object>();
        	        // Farm id
        	        params.put("farmId", cultivationResultData.getFarmId());
        	        // Area id
        	        params.put("areaId", cultivationResultData.getAreaId());
        	        // Block id
        	        params.put("blockId", cultivationResultData.getBlockId());
        	        // Previous round
        	        params.put("previousRound", Constants.CURRENT_ROUND);
        	        
        	        params.put("lastUpdateDate", cultivationResultData.getLastUpdateDateProduct());
        	        
        	        // deleteFlag
        	        params.put("deleteFlag", false);
        	        List<IvbTProductKey> productList = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().getProductData(params);
        	        
        	        for (int i = 0; i < productList.size(); i++) {
        	        	// create params
            	    	params = new HashMap<String, Object>();
            	        // Farm id
            	        params.put("farmId", cultivationResultData.getFarmId());
            	        // Area id
            	        params.put("areaId", cultivationResultData.getAreaId());
            	        // Block id
            	        params.put("blockId", cultivationResultData.getBlockId());
            	        // Previous round
            	        params.put("previousRound", Constants.CURRENT_ROUND);            	        
            	        params.put("lastUpdateDate", cultivationResultData.getLastUpdateDateProduct());
        	        	params.put("lineId", productList.get(i).getLineId());
        	        	params.put("columnId", productList.get(i).getColumnId());
        	        	params.put("updateUserId", util.getUserInfo().getID());
        	        	// check change point code
                		if (changePointCode.equals(Constants.TASK_CHANGE_POINT_CODE_CULTIVATION_START)) {
                	        // default cultivation start date
                	        params.put("defaultCultivationStartDate", Constants.DEFAULT_CULTIVATION_START_DATE);
                	        updateBlockDate = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().updateCultivationStartDate(params);
                		} else if (changePointCode.equals(Constants.TASK_CHANGE_POINT_CODE_PLANTING)) {
                			params.put("plantingDate", null);
                			updateBlockDate = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().updateProductPlantingDate(params);
                		} else if (changePointCode.equals(Constants.TASK_CHANGE_POINT_CODE_FLOWERING)) {
                			params.put("floweringDate", null);
                			updateBlockDate = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().updateProductFloweringDate(params);
                		} else if (changePointCode.equals(Constants.TASK_CHANGE_POINT_CODE_BAGGED)) {
                			params.put("bagDate", null);
                			updateBlockDate = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().updateProductBaggedDate(params);
                		} else if (changePointCode.equals(Constants.TASK_CHANGE_POINT_CODE_HARVESTED)) {
                			params.put("harvestDate", null);
                			updateBlockDate = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().updateProductHarvestedDate(params);
                		}
                		
                		if (updateBlockDate <= 0) { // update fail
                			IvbTProduct productData = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().getLastUpdateDateProduct(params);
                			if (productData != null && !checkLastUpdatedDateProduct(cultivationResultData, productData)) {
                				logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                            			+ LoggerMessage.PRODUCT_UPDATE_FAILED_UPDATE_DATE);
                                returnValue = Constants.DELETE_RESULT_FAILED_PRODUCT_UPDATE_DATE;
                			} else {
                				logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                            			+ LoggerMessage.PRODUCT_RESULT_UPDATE_FAILED);
                                returnValue = Constants.DELETE_RESULT_FAILED_PRODUCT;
                			}
                            txManager.rollback(status);
                            return returnValue;
                		}
        	        }
        		}
        		params = new HashMap<String, Object>();
        		// Farm id
    	        params.put("farmId", cultivationResultData.getFarmId());
    	        // Area id
    	        params.put("areaId", cultivationResultData.getAreaId());
    	        // Block id
    	        params.put("blockId", cultivationResultData.getBlockId());
    	        // Line id
    	        params.put("lineId", cultivationResultData.getLineId());
    	        // Column id
    	        params.put("columnId", cultivationResultData.getColumnId());
    	        // Previous round
    	        params.put("previousRound", Constants.CURRENT_ROUND);
    	        // delete flag
    	        params.put("processId", cultivationResultData.getProcessId());
    	        // delete flag
    	        params.put("taskId", cultivationResultData.getTaskId());
    	        // delete flag
    	        params.put("deleteFlag", Constants.DELETE_FLAG_ON);
    	        // working date
    	        params.put("workingDate", new SimpleDateFormat("yyyy-MM-dd").format(cultivationResultData.getWorkingDate()));
    	        // LAST_UPDATE_DATE
    	        params.put("lastUpdateDate", cultivationResultData.getLastUpdateDateCultivation());

                int result = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().updateData(params);
        		
                if (result > 0) { // delete successfully
                    // register to DB
                    txManager.commit(status);
                } else {
                	IvbTCultivationResult cultivationResult = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().getLastUpdateDateCultivation(params);
        			if (cultivationResult != null && !checkLastUpdatedDateCultivation(cultivationResultData, cultivationResult)) {
        				logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.CULTIVATION_RESULT_DELETE_FAILED_UPDATE_DATE);
                        returnValue = Constants.DELETE_RESULT_FAILED_CULTIVATION_UPDATE_DATE;
        			} else {
        				logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.CULTIVATION_RESULT_DELETE_FAILED);
                        returnValue = Constants.DELETE_RESULT_FAILED_CULTIVATION;                            
        			}
        			txManager.rollback(status);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                // log to file
        		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                returnValue = Constants.DELETE_RESULT_FAILED_EXCEPTION;
                txManager.rollback(status);
            } 
        } catch (Exception ex) {
            ex.printStackTrace();
            // log to file
    		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            returnValue = Constants.DELETE_RESULT_FAILED_EXCEPTION;
        }
        return returnValue;
	}

	/**
     * Handle user input data
     * 
     * @param inputData : search result data list
     */
    private void convertSanitize(List<BnnBlockCultivationResult> inputData) {
        for (int i = 0; i < inputData.size(); i++) {
        	BnnBlockCultivationResult currentData = inputData.get(i);
        	// block name
        	currentData.setBlockName(util.convertSanitize(currentData.getBlockName()));
        	// process name
        	currentData.setProcessName(util.convertSanitize(currentData.getProcessName()));
        	// task name
        	currentData.setTaskName(util.convertSanitize(currentData.getTaskName()));
            // working content
            currentData.setWorkingContent(util.convertSanitize(currentData.getWorkingContent()));
            // precaution
            currentData.setPrecaution(util.convertSanitize(currentData.getPrecaution()));
            // status name
            currentData.setStatusName(util.convertSanitize(currentData.getStatusName()));
            // memo
            currentData.setNote(util.convertSanitize(currentData.getNote()));
        }
    }

    /**
     * Check input: blank fields
     * 
     * @param cultivationResultData : data received from client
     * @return boolean : all fields have value: true/blank fields exist: false
     */
 	private boolean checkInputBlankFields(BnnBlockCultivationResult cultivationResultData) {
 		if (cultivationResultData.getWorkingDate() == null || cultivationResultData.getProcessId() == null
 			|| cultivationResultData.getTaskId() == null || cultivationResultData.getStatusId() == null) {
 			return false;
 		} else {
 			return true;
 		}
 	}
 	
 	/**
 	 * Check whether current record has been updated before or not
 	 * 
 	 * @param userData
 	 * @return boolean : 2 dates are equal: true/2 dates are not equal: false 
 	 */
 	private boolean checkLastUpdatedDateProduct(BnnBlockCultivationResult cultivationClient, IvbTProduct ivbTProduct) {
 		// get client last updated date time
 		Date lastUpdatedDateTimeClient = cultivationClient.getLastUpdateDateProduct();
 		// get server last updated date time
 		Date lastUpdatedDateTimeServer = ivbTProduct.getLastUpdateDate();
 		// compare date
 		if (!lastUpdatedDateTimeClient.equals(lastUpdatedDateTimeServer)) {
 			return false;
 		}
 		return true;
 	}
 	
 	/**
 	 * Check whether current record has been updated before or not
 	 * 
 	 * @param userData
 	 * @return boolean : 2 dates are equal: true/2 dates are not equal: false 
 	 */
 	private boolean checkLastUpdatedDateCultivation(BnnBlockCultivationResult cultivationClient, IvbTCultivationResult cultivationResult) {
 		// get client last updated date time
 		Date lastUpdatedDateTimeClient = cultivationClient.getLastUpdateDateCultivation();
 		// get server last updated date time
 		Date lastUpdatedDateTimeServer = cultivationResult.getLastUpdateDate();
 		// compare date
 		if (!lastUpdatedDateTimeClient.equals(lastUpdatedDateTimeServer)) {
 			return false;
 		}
 		return true;
 	}
 	
 	/**
     * Get last update date int product table
     * 
     * @param farmId : data received from client
     * @param kindId : data received from client
     * @return date
     */
    public Date getLastUpdateDateProduct(String farmId, String areaId) {
    	IvbTProduct product = new IvbTProduct();

    	try {
			// remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        try {
		    	// create params
		    	HashMap<String, Object> params = new HashMap<String, Object>();
		        // Farm id
		        params.put("farmId", farmId);
		        // Kind id
		        params.put("areaId", areaId);
		        params.put("blockId", null);
		        params.put("previousRound", Constants.CURRENT_ROUND);
		        
		        product = bnn0035BlockUpdateDao.getBnn0035BlockUpdateMapper().getLastUpdateDateProduct(params);
		    	
				txManager.commit(status);
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        	// roll back transaction
	    		txManager.rollback(status);
	    		// log to file
	    		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	    		return new Date();
	        }
    	} catch (Exception ex) {
			ex.printStackTrace();
			// log to file
    		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
    		return new Date();
		}

		return product.getLastUpdateDate();
    }
}