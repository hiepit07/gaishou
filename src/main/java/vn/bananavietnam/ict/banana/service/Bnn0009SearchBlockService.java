package vn.bananavietnam.ict.banana.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import vn.bananavietnam.ict.banana.component.Bnn0009SearchBlockConditions;
import vn.bananavietnam.ict.banana.component.Bnn0009SearchBlockProductAdjust;
import vn.bananavietnam.ict.banana.component.Bnn0009SearchBlockResult;
import vn.bananavietnam.ict.banana.dao.Bnn0009SearchBlockDao;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.model.IvbMBlock;
import vn.bananavietnam.ict.common.db.model.IvbMBlockExample;
import vn.bananavietnam.ict.common.db.model.IvbMBlockKey;
import vn.bananavietnam.ict.common.db.model.IvbMFarm;
import vn.bananavietnam.ict.common.db.model.IvbTProduct;
import vn.bananavietnam.ict.common.db.model.IvbTProductExample;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Khoa Le
 * Search block, create new block and edit block's info
 * Service connects to DB, processes requests and returns results to Controller
 */
@Service
public class Bnn0009SearchBlockService {

	private static Logger logger = Logger.getLogger(Bnn0009SearchBlockService.class);

	private Util util = new Util();
	
	@Autowired
	private Bnn0009SearchBlockDao bnn0009SearchBlockDao;

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
     * @param farmId : received from Bnn0007 screen
     * @param areaId : received from Bnn0007 screen
     */
    public void initData(Model model, String farmId, String areaId) {
        // get data for combobox
    	List<UtilComponent> farmData = new ArrayList<UtilComponent>();
    	if (farmId != null) {
			// save farmId & AreaId in JSP
			model.addAttribute("farmIdClient", farmId);
			model.addAttribute("areaIdClient", areaId);
			try {
	        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	        			+ LoggerMessage.GET_FARM_COMBOBOX);
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
		} else {
			model.addAttribute("farmIdClient", "");
			model.addAttribute("areaIdClient", "");
			try {
	        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	        			+ LoggerMessage.GET_FARM_COMBOBOX);
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
		            txManager.rollback(status);
		            model.addAttribute("farmData", "''");
		            
		        }
			} catch (Exception ex) {
	            ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            farmData = new ArrayList<UtilComponent>();
	            model.addAttribute("farmData", "''");
	            
	        }
		}
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

	/**
	 * Handle farm input data
	 * @param inputData: search result data list
	 */
	private void convertSanitizeArea(List<UtilComponent> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			UtilComponent currentData = inputData.get(i);
			// Farm name
			currentData.setAreaName(util.convertSanitize(currentData.getAreaName()));
		}
	}

	/**
	 * Handle farm input data
	 * @param inputData: search result data list
	 */
	private void convertSanitizeBlock(List<IvbMBlock> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			IvbMBlock currentData = inputData.get(i);
			IvbMBlockExample blockExample = new IvbMBlockExample();
			blockExample.setOrderByClause("BLOCK_NAME");
			// Farm name
			currentData.setBlockName(util.convertSanitize(currentData.getBlockName()));
		}
	}

    /**
     * Search block in DB based on search conditions received from client
     * 
     * @param searchConditions : data received from client
     * @return List of blocks data
     */
	public List<Bnn0009SearchBlockResult> searchData(Bnn0009SearchBlockConditions searchConditions) {
		List<Bnn0009SearchBlockResult> bnn0009ResultList = new ArrayList<Bnn0009SearchBlockResult>();
        HashMap<String, Object> params = createSearchConditionParams(searchConditions);
        try {
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.BLOCK_SEARCH_STARTED);
        	// search starts
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        try {
	            bnn0009ResultList = bnn0009SearchBlockDao.getBnn0009SearchBlockMapper().searchData(params);
	            logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	            		+ LoggerMessage.SQL_EXECUTION_FINISHED);
	            if (bnn0009ResultList.size() > 0) {
	                // "real" total from search result
	                String searchDataTotalCounts = bnn0009SearchBlockDao.getBnn0009SearchBlockMapper().getSearchDataTotalCounts(params);
	                bnn0009ResultList.get(0).setSearchDataTotalCounts(searchDataTotalCounts);
	                // handle user input data
	                convertSanitize(bnn0009ResultList);
	                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                		+ LoggerMessage.BLOCK_SEARCH_RESULT_STRING.replace("$1", searchDataTotalCounts));
	            } else {
	            	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                		+ LoggerMessage.BLOCK_SEARCH_RESULT_0_STRING);
	            }
	            txManager.commit(status);
	        } catch (OutOfMemoryError ex) {
	        	ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
	            Bnn0009SearchBlockResult tempObj = new Bnn0009SearchBlockResult();
	            tempObj.setSearchDataTotalCounts("-1");
	            bnn0009ResultList.add(tempObj);
	            txManager.rollback(status);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            bnn0009ResultList = null;
	            txManager.rollback(status);
	        }
        } catch (Exception ex) {
            ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            bnn0009ResultList = null;
        }
        
        return bnn0009ResultList;
	}

	/**
	 * Get data for Area combobox by farm id 
	 * 
	 * @param farmId : data received from client
	 * @return Area data
	 */
	public List<UtilComponent> getAreaDataByFarmId(String farmId) {
		List<UtilComponent> areaData = new ArrayList<UtilComponent>();
		try {
			logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
					+ LoggerMessage.GET_AREA_BY_FARM_ID + farmId);
			//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        areaData = util.getAreaDataByFarmId(utilDao, farmId);
			convertSanitizeArea(areaData);
			//release transaction
			txManager.commit(status);
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            areaData = new ArrayList<UtilComponent>();
		}
		
		return areaData;
	}

	/**
	 * Get data for Block combobox by area id 
	 * 
	 * @param areaId : data received from client
	 * @return Block data
	 */
	public List<IvbMBlock> getBlockDataByAreaId(String areaId, String farmId) {
		List<IvbMBlock> list = new ArrayList<IvbMBlock>();
		try {
			logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
					+ LoggerMessage.GET_BLOCK_BY_AREA_ID + areaId);
			//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        //main process
			IvbMBlockExample blockExample = new IvbMBlockExample();
			IvbMBlockExample.Criteria blockCriteria = blockExample.createCriteria();
			blockExample.setOrderByClause("BLOCK_NAME");
			blockCriteria.andFarmIdEqualTo(farmId);
			blockCriteria.andAreaIdEqualTo(areaId);
			blockCriteria.andDeleteFlagEqualTo(false);
			list = bnn0009SearchBlockDao.getIvbMBlockMapper().selectByExample(blockExample);
			convertSanitizeBlock(list);
			//release transaction
			txManager.commit(status);
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            list = new ArrayList<IvbMBlock>();
		}
		return list;
	}
 
	/**
     * Update block's information to DB
     * 
     * @param blockData : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
    public String updateData(IvbMBlock blockData) {
        // variable definition
        String returnValue = Constants.UPDATE_RESULT_SUCCESSFUL;
        // update starts 
        try {
            // check for user input
            if (!checkInputBlankFields(blockData)) {
            	// blank field(s)
            	logger.error(LoggerMessage.LOG_ERROR_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.BLANK_FIELDS_ERROR_MESSAGE);
                returnValue = Constants.VALIDATE_BLANK_FIELDS;
                return returnValue;
            }
            // transaction starts
            logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            		+ LoggerMessage.BLOCK_UPDATE_STARTED);
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
                HashMap<String, Object> params = new HashMap<String, Object>();
                // Farm Id
                params.put("farmId", blockData.getFarmId());
                // Area id
                params.put("areaId", blockData.getAreaId());
                // block Id
                params.put("blockId", blockData.getBlockId());
                // block name
                params.put("blockName", blockData.getBlockName());
                // note
                params.put("note", blockData.getNote());
                // update user id
                params.put("updateUserId", util.getUserInfo().getID());
                // last Update Date
                params.put("lastUpdateDate", blockData.getLastUpdateDate());
                // delete flag
                params.put("deleteFlag", blockData.getDeleteFlag());

                int result = bnn0009SearchBlockDao.getBnn0009SearchBlockMapper().updateData(params);
                if (result > 0) { // update successfully
                	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                			+ LoggerMessage.BLOCK_UPDATE_FINISHED);
                    // register to DB
                    txManager.commit(status);
                } else {
                    // check last updated date
                    if (!checkLastUpdatedDateTime(blockData)) {
                    	// record has been updated
                    	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.CANT_UPDATE_DATA_UPDATE_DATE);
                        returnValue = Constants.UPDATE_RESULT_FAILED_UPDATE_DATE;
                        txManager.rollback(status);
                        return returnValue;
                    } else {
                    	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.BLOCK_UPDATE_FAILED);
                        returnValue = Constants.UPDATE_RESULT_FAILED_CULTIVATION_BLOCK;
                        txManager.rollback(status);
                    }
                }
            } catch (DuplicateKeyException ex) {            	
                ex.printStackTrace();
                // log to file
        		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.ERROR_DUPLICATE_BLOCK_NAME, ex);
        		returnValue = Constants.UPDATE_RESULT_FAILED_DUPLLICATE;
                txManager.rollback(status);
            } catch (Exception ex) {
                ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                returnValue = Constants.UPDATE_RESULT_FAILED_EXCEPTION;
                txManager.rollback(status);
            } 
        } catch (Exception ex) {
            ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            returnValue = Constants.UPDATE_RESULT_FAILED_EXCEPTION;
        }
        return returnValue;
    }

	/**
     * Insert block's information to DB
     * 
     * @param blockData : data received from client
     * @return String : insert successfully: 1/insert failed: -1
     */
    public String insertData(IvbMBlock blockData) {
        // variable definition
        String returnValue = Constants.INSERT_RESULT_SUCCESSFUL;
        // insert starts
        try {
        	// check for user input
            if (!checkInputBlankFields(blockData)) {
            	// blank field(s)
            	logger.error(LoggerMessage.LOG_ERROR_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.BLANK_FIELDS_ERROR_MESSAGE);
                returnValue = Constants.VALIDATE_BLANK_FIELDS;
                return returnValue;
            }
            
        	// transaction starts
            logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            		+ LoggerMessage.BLOCK_INSERT_STARTED);
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
            	// create HashMap to insert into params.
            	HashMap<String, Object> paramLastBlockId = new HashMap<String, Object>();
                // farm Id
            	paramLastBlockId.put("farmId", blockData.getFarmId());
            	// area Id
            	paramLastBlockId.put("areaId", blockData.getAreaId());
            	// block default 
            	paramLastBlockId.put("blockDefault", Constants.DEFAULT_BLOCK); 
            	// create Number of Id
            	String idNumberStr = bnn0009SearchBlockDao.getBnn0009SearchBlockMapper()
                        .getLastBlockId(paramLastBlockId);
            	int idNumber = 0;
            	if (idNumberStr != null) {
            		idNumber = Integer.parseInt(idNumberStr.substring(1));
            	}
                idNumber = idNumber + 1;
            	
            	// set Area Id
				String blockId = Constants.BEGIN_BLOCK_ID + String.format("%0" + Constants.MAX_LENGHT_ID + "d", idNumber);
            	Map<String, Object> params = new HashMap<String, Object>();
                params.put("result", "");
                params.put("farmId", blockData.getFarmId());
                params.put("areaId", blockData.getAreaId());
                params.put("blockId", blockId);
                params.put("blockName", blockData.getBlockName());
                params.put("usersId", util.getUserInfo().getID());
                params.put("deleteFlag", blockData.getDeleteFlag());
                params.put("note", blockData.getNote());
                params.put("numberOfLines", getFarmLinesAndColumnsCount(blockData.getFarmId()).split(":")[0]);
                params.put("numberOfColumns", getFarmLinesAndColumnsCount(blockData.getFarmId()).split(":")[1]);
                params.put("cultivationStartDate", Constants.DEFAULT_CULTIVATION_START_DATE);
                bnn0009SearchBlockDao.getBnn0009SearchBlockMapper().insertData(params);
                // get insert result
                String insertResult = (String) params.get("result");
                // check result 
                if (insertResult == null || insertResult.equals("") || insertResult.equals(Constants.INSERT_RESULT_FAILED)) {
                	// error occurred
                	logger.info(LoggerMessage.LOG_ERROR_PREFIX + util.getUserInfo().getID() + ","
                			+ LoggerMessage.BLOCK_INSERT_FAILED);
                	returnValue = Constants.INSERT_RESULT_FAILED_BLOCK;
                	txManager.rollback(status);
                } else if (insertResult.equals(Constants.INSERT_RESULT_DUPLICATED_BLOCK)|| insertResult.equals(Constants.INSERT_RESULT_DUPLICATED)) {
                	// error occurred
                	logger.info(LoggerMessage.LOG_ERROR_PREFIX + util.getUserInfo().getID() + ","
                			+ LoggerMessage.ERROR_DUPLICATE_BLOCK_NAME);
                	returnValue = Constants.INSERT_RESULT_DUPLICATED_BLOCK;
                	txManager.rollback(status);
                } else if (insertResult.equals(Constants.INSERT_RESULT_SUCCESSFUL)) {
                	// successful
                	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                			+ LoggerMessage.BLOCK_INSERT_FINISHED);
                	returnValue = Constants.INSERT_RESULT_SUCCESSFUL;
                	// register to DB
                    txManager.commit(status);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            
                returnValue = Constants.INSERT_RESULT_FAILED_EXCEPTION;
                txManager.rollback(status);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            returnValue = Constants.INSERT_RESULT_FAILED_EXCEPTION;
        }
        return returnValue;
    }

	/**
     * Delete Block from DB
     * 
     * @param farmId : data received from client
     * @param areaId : data received from client
     * @param blockId : data received from client
     * @return String : delete successfully: 1/delete failed: -1
     */
    public String deleteData(String farmId, String areaId, String blockId, Date lastUpdateDate) {
    	// variable definition
        String returnValue = Constants.DELETE_RESULT_SUCCESSFUL;
        // delete starts
        try {
        	// transaction starts
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.BLOCK_DELETE_STARTED);
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
            	Map<String, Object> params = new HashMap<String, Object>();
                params.put("result", "");
                params.put("farmId", farmId);
                params.put("areaId", areaId);
                params.put("blockId", blockId);
                params.put("lastUpdateDate", lastUpdateDate);
                bnn0009SearchBlockDao.getBnn0009SearchBlockMapper().deleteData(params);
                // get delete result
                String deleteResult = (String) params.get("result");
                // check result
                if (deleteResult == null || deleteResult.equals("") || deleteResult.equals(Constants.DELETE_RESULT_FAILED)) {
                
                    // error occurred
                    logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                    		+ LoggerMessage.BLOCK_DELETE_FAILED);
                    returnValue = Constants.DELETE_RESULT_FAILED_CULTIVATION_BLOCK;
                    txManager.rollback(status);
                } else if (deleteResult.equals(Constants.DELETE_RESULT_FAILED_UPDATE_DATE)) {
					// check last updated date
					// record has been updated
                	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                			+ LoggerMessage.CANT_DELETE_DATA_UPDATE_DATE);
                    returnValue = Constants.DELETE_RESULT_FAILED_UPDATE_DATE;
                    txManager.rollback(status);
                    return returnValue;
				} else if (deleteResult.equals(Constants.DELETE_RESULT_SUCCESSFUL)) {
                	// successful
                	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                			+ LoggerMessage.BLOCK_DELETE_FINISHED);
                	// register to DB
                    txManager.commit(status);
                }
            } catch (Exception ex) { 
                ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                returnValue = Constants.DELETE_RESULT_FAILED_EXCEPTION;
                txManager.rollback(status);
            } 
        } catch (Exception ex) {
            ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            returnValue = Constants.DELETE_RESULT_FAILED_EXCEPTION;
        }
        return returnValue;
	}

    /**
     * Get block information based on ids 
     * 
     * @param farmId : data received from client
     * @param areaId : data received from client
     * @param blockId : data received from client
     * @return block data
     */
    public IvbMBlock getSingleData(String farmId, String areaId, String blockId) {
    	
    	IvbMBlock ret = new IvbMBlock();
    	try {
			logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
					+ LoggerMessage.GET_BLOCK_BY_ALL_ID + farmId + ", " + areaId + ", " + blockId);
			// remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
			PlatformTransactionManager txManager = (PlatformTransactionManager) appContext
					.getBean("transactionManager");
			TransactionStatus status = txManager.getTransaction(def);

			// main process
			IvbMBlockKey key = new IvbMBlockKey();
			key.setFarmId(farmId);
			key.setAreaId(areaId);
			key.setBlockId(blockId);
			ret = bnn0009SearchBlockDao.getIvbMBlockMapper().selectByPrimaryKey(key);
			txManager.commit(status);
    	} catch (Exception ex) {
            ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            ret = null;
        }
		return ret;
    }

    /**
	 * Get Number of Lines and Columns of specific farm
	 * 
	 * @param farmId : received from client
	 * @return Lines Count + ":" + Columns Count
	 */
	public String getFarmLinesAndColumnsCount(String farmId) {
		//remove cache
			//main process
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
			IvbMFarm farmData = bnn0009SearchBlockDao.getIvbMFarmMapper().selectByPrimaryKey(farmId);
			//release transaction
			txManager.commit(status);
			return farmData.getNumberOfLines() + ":" + farmData.getNumberOfColumns();
	}

    /**
     * Get product information based on ids
     * 
     * @param farmId : data received from client
     * @param areaId : data received from client
     * @param blockId : data received from client
     * @return product data
     */
    public List<IvbTProduct> getProductData(String farmId, String areaId, String blockId, Boolean previousRound) {
    	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
    			+ LoggerMessage.GET_PRODUCT_BY_ALL_ID + farmId + ", " + areaId + ", " + blockId);
        List<IvbTProduct> ret = new ArrayList<IvbTProduct>();
		//remove cache
    	try {
    		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
            	//main process
            	IvbTProductExample productExample = new IvbTProductExample();
            	IvbTProductExample.Criteria productCriteria = productExample.createCriteria();
            	productCriteria.andFarmIdEqualTo(farmId);
            	productCriteria.andAreaIdEqualTo(areaId);
            	productCriteria.andBlockIdEqualTo(blockId);
            	productCriteria.andPreviousRoundEqualTo(previousRound);
            	productCriteria.andLineIdNotEqualTo("9999");
            	productExample.setOrderByClause("COLUMN_ID, LINE_ID");
            	ret = bnn0009SearchBlockDao.getIvbTProductMapper().selectByExample(productExample);
        		//release transaction
        		txManager.commit(status);
            } catch(Exception ex) {
            	ex.printStackTrace();
            	ret = null;
                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                txManager.rollback(status);
            }
    	} catch(Exception ex) {
        	ex.printStackTrace();
        	ret = null;
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
        }
		return ret;
    }

    /**
     * Update product's information to DB
     * 
     * @param productData : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
    public String updateProductData(Bnn0009SearchBlockProductAdjust productData) {
        // variable definition
        String returnValue = Constants.UPDATE_RESULT_SUCCESSFUL;
        // update starts
        try {
            // check for user input
            if (productData.getIdString().equals("")) {
            	// blank field(s)
            	logger.error(LoggerMessage.LOG_ERROR_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.BLANK_FIELDS_ERROR_MESSAGE);
                returnValue = Constants.VALIDATE_BLANK_FIELDS;
                return returnValue;
            }
            // transaction starts
            logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            		+ LoggerMessage.PRODUCT_UPDATE_STARTED);
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
            	// get banana id array
            	String[] bananaIdArray = productData.getIdString().split(",");
            	// loop id array to update each banana
            	for (int i = 0; i < bananaIdArray.length; i++) {
            		String[] idArray = bananaIdArray[i].split(":");
            		// create banana object
            		IvbTProduct bananaObj = new IvbTProduct();
                    // farm id
                	bananaObj.setFarmId(idArray[0]);
                    // area id
                	bananaObj.setAreaId(idArray[1]);
                    // block id
                	bananaObj.setBlockId(idArray[2]);
                    // line id
                	bananaObj.setLineId(idArray[3]);
                	// column id
                	bananaObj.setColumnId(idArray[4]);
                	// delete flag
                	Boolean deleteFlag = Boolean.valueOf(idArray[5]);
                	bananaObj.setDeleteFlag(deleteFlag);
                	// previous round
                	bananaObj.setPreviousRound(Boolean.valueOf(productData.getPreviousRound()));
                    // update user id
                	bananaObj.setUpdateUserId(util.getUserInfo().getID());

                	int result = bnn0009SearchBlockDao.getIvbTProductMapper().updateByPrimaryKeySelective(bananaObj);
                	if (result <= 0) { // update failed
                		logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                				+ LoggerMessage.PRODUCT_UPDATE_FAILED);
                		returnValue = Constants.UPDATE_RESULT_FAILED_DUPLLICATE;
                        txManager.rollback(status);
                        break;
                    }
            	}
            	if (!returnValue.equals(Constants.UPDATE_RESULT_FAILED_DUPLLICATE)) {
            		// successful
            		logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            				+ LoggerMessage.PRODUCT_UPDATE_FINISHED);
                	// register to DB
                    txManager.commit(status);
            	}
            } catch (Exception ex) {
                ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                returnValue = Constants.UPDATE_RESULT_FAILED_EXCEPTION;
                txManager.rollback(status);
            } 
        } catch (Exception ex) {
            ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            returnValue = Constants.UPDATE_RESULT_FAILED_EXCEPTION;
        }
        return returnValue;
    }

	/**
     * Create search conditions parameters from data received from client
     * 
     * @param searchConditions : data received from client
     * @return HashMap object
     */
    private HashMap<String, Object> createSearchConditionParams(Bnn0009SearchBlockConditions searchConditions) {
    	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
    			+ LoggerMessage.CREATE_SEARCH_CONDITIONS_STARTED);
        HashMap<String, Object> params = new HashMap<String, Object>();
        // Farm id
        params.put("farmId", searchConditions.getFarmId().equals(Constants.SEARCH_CONDITION_NO_SELECT) ? Constants.SEARCH_CONDITION_NO_SELECT : searchConditions.getFarmId());
        // Area id
        params.put("areaId", searchConditions.getAreaId().equals(Constants.SEARCH_CONDITION_NO_SELECT) ? Constants.SEARCH_CONDITION_NO_SELECT : searchConditions.getAreaId());
        // Block id
        params.put("blockId", searchConditions.getBlockId().equals(Constants.SEARCH_CONDITION_NO_SELECT) ? Constants.SEARCH_CONDITION_NO_SELECT : searchConditions.getBlockId());
       // From parameter
        params.put("fromRow", Integer.valueOf(searchConditions.getFromRow()));
        // Number of items in a page
        params.put("itemCount", Integer.valueOf(searchConditions.getItemCount()));

        return params;
    }

    /**
     * Handle user input data
     * 
     * @param inputData : search result data list
     */
    private void convertSanitize(List<Bnn0009SearchBlockResult> inputData) {
        for (int i = 0; i < inputData.size(); i++) {
        	Bnn0009SearchBlockResult currentData = inputData.get(i);
            // Farm name
            currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
            // Area name
            currentData.setAreaName(util.convertSanitize(currentData.getAreaName()));
            // Block id
            currentData.setBlockId(util.convertSanitize(currentData.getBlockId()));
            // Block name
            currentData.setBlockName(util.convertSanitize(currentData.getBlockName()));
            // Note
            currentData.setNote(util.convertSanitize(currentData.getNote()));
        }
    }

    /**
     * Check input: blank fields
     * 
     * @param blockData : data received from client
     * @return boolean : all fields have value: true/blank fields exist: false
     */
 	private boolean checkInputBlankFields(IvbMBlock blockData) {
 		if (blockData.getFarmId().equals("") || blockData.getFarmId().equals(Constants.SEARCH_CONDITION_NO_SELECT)
 				|| blockData.getAreaId().equals("") || blockData.getAreaId().equals(Constants.SEARCH_CONDITION_NO_SELECT)
 				|| blockData.getBlockName().equals("")) {
 			return false;
 		} else {
 			return true;
 		}
 	}

 	/**
 	 * Check whether current record has been updated before or not
 	 * 
 	 * @param BlockData
 	 * @return boolean : 2 dates are equal: true/2 dates are not equal: false 
 	 */
 	private boolean checkLastUpdatedDateTime(IvbMBlock blockData) {
 		// get client last updated date time
 		Date lastUpdatedDateTimeClient = blockData.getLastUpdateDate();
 		// get server last updated date time
 		IvbMBlock serverData = getSingleData(blockData.getFarmId(), blockData.getAreaId(), blockData.getBlockId());
 		if (serverData == null) {
 			return false;
 		} else {
 	 		Date lastUpdatedDateTimeServer = serverData.getLastUpdateDate();
 	 		// compare date
 	 		if (!lastUpdatedDateTimeClient.equals(lastUpdatedDateTimeServer)) {
 	 			return false;
 	 		}
 	 		return true;
 		}
 	}
}