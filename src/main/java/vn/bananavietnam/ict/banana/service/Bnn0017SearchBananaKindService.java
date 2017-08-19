package vn.bananavietnam.ict.banana.service;

import java.util.ArrayList;
import java.util.Date;
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

import vn.bananavietnam.ict.banana.component.Bnn0017SearchBananaKindConditions;
import vn.bananavietnam.ict.banana.component.Bnn0017SearchBananaKindResult;
import vn.bananavietnam.ict.banana.dao.Bnn0017SearchBananaKindDao;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.db.model.IvbMKind;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Nghia Nguyen
 */
@Service
public class Bnn0017SearchBananaKindService {
	private static Logger logger = Logger.getLogger(Bnn0017SearchBananaKindService.class);

	private Util util = new Util();
	
	@Autowired
	private Bnn0017SearchBananaKindDao bnn0017SearchBananaKindDao;

    @Autowired
    private ApplicationContext appContext;
    
    public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	public List<Bnn0017SearchBananaKindResult> searchData(Bnn0017SearchBananaKindConditions searchConditions) {
		List<Bnn0017SearchBananaKindResult> bnn0017BananaKindList = new ArrayList<Bnn0017SearchBananaKindResult>();
		HashMap<String, Object> params = createSearchConditionParams(searchConditions);
		try {
		    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
		    try {
	             logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                        + LoggerMessage.KIND_SEARCH_STARTED);
	                // search starts
	                bnn0017BananaKindList = bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().searchData(params);
	                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + "," + LoggerMessage.SQL_EXECUTION_FINISHED);
	                if (bnn0017BananaKindList.size() > 0) {
	                    // "real" total from search result
	                    String searchDataTotalCounts = bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().getSearchDataTotalCounts(params);
	                    bnn0017BananaKindList.get(0).setSearchDataTotalCounts(searchDataTotalCounts);
	                    // handle Banana kind input data
	                    convertSanitize(bnn0017BananaKindList);
	                    logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                            + LoggerMessage.KIND_SEARCH_RESULT_STRING.replace("$1", searchDataTotalCounts));
	                } else {
	                    logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                            + LoggerMessage.KIND_SEARCH_RESULT_0_STRING);
	                }
	                txManager.commit(status);
	            } catch (OutOfMemoryError ex) {
	                ex.printStackTrace();
	                Bnn0017SearchBananaKindResult tempObj = new Bnn0017SearchBananaKindResult();
	                tempObj.setSearchDataTotalCounts("-1");
	                bnn0017BananaKindList.add(tempObj);
	                txManager.rollback(status);
	            	// log to file
	                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
	            } catch (Exception ex) {
	                ex.printStackTrace();
	                bnn0017BananaKindList = null;
	                txManager.rollback(status);
	            	// log to file
	                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            }
        } catch (Exception ex) {
            ex.printStackTrace();
        	// log to file
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            bnn0017BananaKindList = null;
        }
		 
	   return bnn0017BananaKindList;
	}

	/**
     * Update Banana kind information to DB
     * 
     * @param bananaKindData : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
    public String updateData(IvbMKind bananaKindData) {
        // variable definition
        String returnValue = Constants.UPDATE_RESULT_SUCCESSFUL;
        // update starts
        try {
            // check for info input
            if (!checkInputBlankFields(bananaKindData)) {
                // blank field(s)
            	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                        + LoggerMessage.BLANK_FIELDS_ERROR_MESSAGE);
                returnValue = Constants.VALIDATE_BLANK_FIELDS;
                return returnValue;
            }
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
            	
            	HashMap<String, Object> params = new HashMap<String, Object>();
            	// kind id
                params.put("kindId", bananaKindData.getKindId());
                // kind name
                params.put("kindName", bananaKindData.getKindName());
            	// ProspectiveHarvestAmount
                params.put("ProspectiveHarvestAmount", bananaKindData.getProspectiveHarvestAmount());
                // Estimated Days Flowering 20170203
                params.put("EstimatedDaysFlowering", bananaKindData.getEstimatedDaysFlowering());
                // Estimated Days Bagging 20170203
                params.put("EstimatedDaysBagging", bananaKindData.getEstimatedDaysBagging());
                // Estimated Days Harvest 20170203
                params.put("EstimatedDaysHarvest", bananaKindData.getEstimatedDaysHarvest());
                // update user id
                params.put("updateUserId", util.getUserInfo().getID());
                // delete flag
                params.put("deleteFlag", bananaKindData.getDeleteFlag());
                params.put("lastUpdateDate", bananaKindData.getLastUpdateDate());
                
               int result = bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().updateData(params);
                if (result > 0) { // update successfully
                    // register to DB
                    txManager.commit(status);
                } else {
                	 // check last updated date
                    if (!checkLastUpdatedDateTime(bananaKindData)) {
                    	// record has been updated
                    	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.CANT_UPDATE_DATA_UPDATE_DATE);
                        returnValue = Constants.UPDATE_RESULT_FAILED_UPDATE_DATE;
                        txManager.rollback(status);
                        return returnValue;
                    } else {                	
	                    returnValue = Constants.UPDATE_RESULT_FAILED_KIND;	                    
	                    logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
	                            + LoggerMessage.CANT_UPDATE_DATA);
	                    txManager.rollback(status);
	                    return returnValue;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            	// log to file
                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                returnValue = Constants.UPDATE_RESULT_FAILED_EXCEPTION;
                txManager.rollback(status);
            } 
        } catch (Exception ex) {
            ex.printStackTrace();
        	// log to file
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            returnValue = Constants.UPDATE_RESULT_FAILED_EXCEPTION;
        }
        return returnValue;
    }

    /**
     * Insert banana Kind's information to DB
     * 
     * @param bananaKindData : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
    public String insertData(IvbMKind bananaKindData) {
        // variable definition
        String returnValue = Constants.INSERT_RESULT_SUCCESSFUL;
        int result = 0;
        String kindId = "";
        // update starts
        try {
            // check for info input
            if (!checkInputBlankFields(bananaKindData)) {
                // blank field(s)
            	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                        + LoggerMessage.BLANK_FIELDS_ERROR_MESSAGE);
                returnValue = Constants.VALIDATE_BLANK_FIELDS;
                return returnValue;
            }
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
                while (result <= 0) {
                	HashMap<String, Object> params = new HashMap<String, Object>();
                	params.put("kindDefault", Constants.DEFAULT_KIND);
                	String idNumberStr = bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()
                            .getLastKindId(params);
                	int idNumber = 0;
                	if (idNumberStr != null) {
                		idNumber = Integer.parseInt(idNumberStr.substring(1));
                	}
                	idNumber = idNumber + 1 ;
                    kindId = Constants.BEGIN_KIND_ID + String.format("%0" + Constants.MAX_LENGHT_ID + "d", idNumber);
                	IvbMKind bananaKindObj = new IvbMKind();
                    // kind id
                    bananaKindObj.setKindId(kindId);
                    // kind name
                    bananaKindObj.setKindName(bananaKindData.getKindName());
                    // ProspectiveHarvestAmount
                    bananaKindObj.setProspectiveHarvestAmount(bananaKindData.getProspectiveHarvestAmount());
                    // Estimated Days Flowering 20170203
                    bananaKindObj.setEstimatedDaysFlowering(bananaKindData.getEstimatedDaysFlowering());
                    // Estimated Days Flowering 20170203
                    bananaKindObj.setEstimatedDaysBagging(bananaKindData.getEstimatedDaysBagging());
                    // Estimated Days Flowering 20170203
                    bananaKindObj.setEstimatedDaysHarvest(bananaKindData.getEstimatedDaysHarvest());
                    // note 20170203
                    //bananaKindObj.setNote(bananaKindData.getNote());
                    // create user id
                    bananaKindObj.setCreateUserId(util.getUserInfo().getID());
                    // update user id
                    bananaKindObj.setUpdateUserId(util.getUserInfo().getID());
                    // delete flag
                    bananaKindObj.setDeleteFlag(bananaKindData.getDeleteFlag());
                    result = bnn0017SearchBananaKindDao.getIvbMKindMapper().insert(bananaKindObj);
                    if (result > 0) { // insert successfully
                        // register to DB
                        txManager.commit(status);
                    } else {
                        returnValue = Constants.INSERT_RESULT_FAILED_KIND;
                        txManager.rollback(status);
                        logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                                + LoggerMessage.CANT_INSERT_DATA);
                    }
                }
            } catch (Exception ex) {
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
     * Delete user from DB
     * 
     * @param usersId : user's id to delete
     * @return String : delete successfully: 1/delete failed: -1
     */
    public String deleteData(IvbMKind bananaKindData) {
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
            	
            	HashMap<String, Object> params = new HashMap<String, Object>();
            	// kind id
                params.put("kindId", bananaKindData.getKindId());
                // update user id
                params.put("updateUserId", util.getUserInfo().getID());
                // delete flag
                params.put("deleteFlag", Constants.DELETE_FLAG_ON);
                params.put("lastUpdateDate", bananaKindData.getLastUpdateDate());
                
               int result = bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().updateData(params);

                if (result > 0) { // delete successfully
                    // register to DB
                    txManager.commit(status);
                } else {
                	
                	if (!checkLastUpdatedDateTime(bananaKindData)) {
                    	// record has been updated
                    	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.CANT_DELETE_DATA_UPDATE_DATE);
                        returnValue = Constants.DELETE_RESULT_FAILED_UPDATE_DATE;
                        txManager.rollback(status);
                        return returnValue;
                    } else {                	
	                    returnValue = Constants.DELETE_RESULT_FAILED_KIND;	                    
	                    logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
	                            + LoggerMessage.CANT_DELETE_DATA);
	                    txManager.rollback(status);
	                    return returnValue;
                    }
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
     * Create search conditions parameters from data received from client
     * 
     * @param searchConditions : data received from client
     * @return HashMap object
     */
    private HashMap<String, Object> createSearchConditionParams(Bnn0017SearchBananaKindConditions searchConditions) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        // Kind name
        params.put("kindName", searchConditions.getKindName().equals("") ? "" : "%" + searchConditions.getKindName() + "%");
        // From parameter
        params.put("fromRow", Integer.valueOf(searchConditions.getFromRow()));
        // Number of items in a page
        params.put("itemCount", Integer.valueOf(searchConditions.getItemCount()));
        return params;
    }
    
	public IvbMKind getSingleData(String kindId) {
	    IvbMKind ret = new IvbMKind();
	    try {
	      //remove cache
	        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        try {
		        //main process
		         ret = bnn0017SearchBananaKindDao.getIvbMKindMapper().selectByPrimaryKey(kindId);
		        //release transaction
		        txManager.commit(status);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        	// log to file
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            ret = null;
	            txManager.rollback(status);
	        }
        } catch (Exception ex) {
            ex.printStackTrace();
        	// log to file
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            ret = null;
        }
		return ret;
	}
	
	/**
     * Handle banana Kind input data
     * 
     * @param inputData : search result data list
     */
    private void convertSanitize(List<Bnn0017SearchBananaKindResult> inputData) {
        for (int i = 0; i < inputData.size(); i++) {
        	Bnn0017SearchBananaKindResult currentData = inputData.get(i);
            // Kind id
            currentData.setKindId(util.convertSanitize(currentData.getKindId()));
            // Kind name
            currentData.setKindName(util.convertSanitize(currentData.getKindName()));
        }
    }
    
    /**
     * Check input: blank fields
     * 
     * @param ivbMCultivationProcess:
     *            data received from client
     * @return boolean : all fields have value: true/blank fields exist: false
     */
    // 20170203
    private boolean checkInputBlankFields(IvbMKind bananaKindData) {
        if (bananaKindData.getKindName().equals("") || bananaKindData.getEstimatedDaysBagging() == null
                || bananaKindData.getEstimatedDaysFlowering() == null
                || bananaKindData.getEstimatedDaysHarvest() == null
                || bananaKindData.getProspectiveHarvestAmount() == null) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
 	 * Check whether current record has been updated before or not
 	 * 
 	 * @param bananaKindData
 	 * @return boolean : 2 dates are equal: true/2 dates are not equal: false 
 	 */
 	private boolean checkLastUpdatedDateTime(IvbMKind bananaKindData) {
 		// get client last updated date time
 		Date lastUpdatedDateTimeClient = bananaKindData.getLastUpdateDate();
 		// get server last updated date time
 		IvbMKind serverData = getSingleData(bananaKindData.getKindId());
 		Date lastUpdatedDateTimeServer = serverData.getLastUpdateDate();
 		// compare date
 		if (!lastUpdatedDateTimeClient.equals(lastUpdatedDateTimeServer)) {
 			return false;
 		}
 		return true;
 	}
}
