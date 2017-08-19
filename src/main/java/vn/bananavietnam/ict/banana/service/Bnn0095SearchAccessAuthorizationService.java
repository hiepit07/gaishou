package vn.bananavietnam.ict.banana.service;

import java.text.ParseException;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.bananavietnam.ict.banana.component.Bnn0095SearchAccessAuthorizationConditions;
import vn.bananavietnam.ict.banana.component.Bnn0095SearchAccessAuthorizationResult;
import vn.bananavietnam.ict.banana.dao.Bnn0095SearchAccessAuthorizationDao;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.db.model.IvbMAccessAuthorization;
import vn.bananavietnam.ict.common.util.Util;

/**
 * 	Bnn0095SearchAccessAuthorizationService
 * 
 *	@author NghiaNguyen
 *	@version 1.0.0
 * 	@since 1.0.0
 */
@Service
public class Bnn0095SearchAccessAuthorizationService {

	private static Logger logger = Logger.getLogger(Bnn0095SearchAccessAuthorizationService.class);

	private Util util = new Util();
	
	@Autowired
	private Bnn0095SearchAccessAuthorizationDao bnn0095SearchAccessAuthorizationDao;
    // Variables definition
	// send Array list data to client side
	ObjectMapper mapper = new ObjectMapper();
	
    @Autowired
    private ApplicationContext appContext;
    
    public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}
    /**
     * Search Access Authorization in DB based on search conditions received from client
     * 
     * @param searchConditions : data received from client
     * @return List of Access Authorization data
     */
	public List<Bnn0095SearchAccessAuthorizationResult> searchData(Bnn0095SearchAccessAuthorizationConditions searchConditions) {
		List<Bnn0095SearchAccessAuthorizationResult> bnn0095ResultList = new ArrayList<Bnn0095SearchAccessAuthorizationResult>();
        HashMap<String, Object> params = createSearchConditionParams(searchConditions);
        try {
        	 // search starts
 			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
        	try {
            	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.ACCESS_SEARCH_STARTED);
               
                bnn0095ResultList = bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper().searchData(params);
                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                		+ LoggerMessage.SQL_EXECUTION_FINISHED);
                if (bnn0095ResultList.size() > Constants.LIST_IS_EQUAL_TO_ZERO) {
                    // "real" total from search result
                    String searchDataTotalCounts = bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper().getSearchDataTotalCounts(params);
                    bnn0095ResultList.get(0).setSearchDataTotalCounts(searchDataTotalCounts);
                    // handle Access Authorization input data
                    convertSanitize(bnn0095ResultList);
                    logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                    		+ LoggerMessage.ACCESS_SEARCH_RESULT_STRING.replace("$1", searchDataTotalCounts));
                } else {
                	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                    		+ LoggerMessage.ACCESS_SEARCH_RESULT_0_STRING);
                }
                txManager.commit(status);
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
                Bnn0095SearchAccessAuthorizationResult tempObj = new Bnn0095SearchAccessAuthorizationResult();
                tempObj.setSearchDataTotalCounts("-1");
                txManager.rollback(status);
                bnn0095ResultList.add(tempObj);
            } catch (Exception ex) {
                ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                bnn0095ResultList = null;
                txManager.rollback(status);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            bnn0095ResultList = null;
        }
        return bnn0095ResultList;
	}

	/**
     * Update Access Authorization's information to DB
     * 
     * @param ivbMAccessAuthorization : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
    public String updateData(IvbMAccessAuthorization ivbMAccessAuthorization) {
        // variable definition
        String returnValue = Constants.UPDATE_RESULT_SUCCESSFUL;
        // update starts
        try {
        	// Check Screen Id
        	if (!checkScreenIdFormat(ivbMAccessAuthorization)) {
            	// id is in wrong format
        		logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.ERROR_MASSAGE_SCREEN_ID);
                returnValue = Constants.VALIDATE_WRONG_FORMAT;
                return returnValue;
            }
        	
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
            	
            	HashMap<String, Object> params = new HashMap<String, Object>();
                // Access Authority Id
                params.put("accessAuthorityId", ivbMAccessAuthorization.getAccessAuthorityId());
                // Screen Id
                params.put("screenId", ivbMAccessAuthorization.getScreenId());
                // Screen Display Enable Flag
                params.put("screenDisplayEnableFlag", ivbMAccessAuthorization.getScreenDisplayEnableFlag());
                // Add Able Flag
                params.put("addableFlag", ivbMAccessAuthorization.getAddableFlag());
                // Update able Flag
                params.put("updatableFlag", ivbMAccessAuthorization.getUpdatableFlag());
                // Delete Able Flag
                params.put("deletableFlag", ivbMAccessAuthorization.getDeletableFlag());
                // Reference Able Flag
                params.put("referenceFlag", ivbMAccessAuthorization.getReferenceFlag());
                // update user id
                params.put("updateUserId", util.getUserInfo().getID());
                // last Update Date
                params.put("lastUpdateDate", ivbMAccessAuthorization.getLastUpdateDate());
                // delete flag
                params.put("deleteFlag", ivbMAccessAuthorization.getDeleteFlag());

                int result = bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper().updateData(params);
                if (result > Constants.RESULT_IS_EQUAL_TO_ZERO) { // update successfully
					// register to DB
					txManager.commit(status);
				} else {
					// set accessAuthorityId & screenId to check lastUpdateDate.
		            Bnn0095SearchAccessAuthorizationConditions bnn0095SearchAccessAuthorizationConditions = new Bnn0095SearchAccessAuthorizationConditions();
		            bnn0095SearchAccessAuthorizationConditions.setAccessAuthorityId(ivbMAccessAuthorization.getAccessAuthorityId());
		            bnn0095SearchAccessAuthorizationConditions.setScreenId(ivbMAccessAuthorization.getScreenId());
		            // check last updated date
		            if (!checkLastUpdatedDateTime(bnn0095SearchAccessAuthorizationConditions, ivbMAccessAuthorization)) {
		            	// record has been updated
		            	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
		            			+ LoggerMessage.CANT_UPDATE_DATA_UPDATE_DATE);
		                returnValue = Constants.UPDATE_RESULT_FAILED_UPDATE_DATE;
                        txManager.rollback(status);
		                return returnValue;
		            } else {
						returnValue = Constants.UPDATE_RESULT_FAILED_ACCESS_AUTHORIZATION;
						txManager.rollback(status);
						logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
	                            + LoggerMessage.CANT_UPDATE_DATA);
		            }
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
     * Insert Access Authorization's information to DB
     * 
     * @param ivbMAccessAuthorization : data received from client
     * @return String : Insert successfully: 1/update failed: -1
     */
    public String insertData(IvbMAccessAuthorization ivbMAccessAuthorization) {
        // variable definition
        String returnValue = Constants.INSERT_RESULT_SUCCESSFUL;
        // update starts
        try {
        	// Check Screen Id
        	if (!checkScreenIdFormat(ivbMAccessAuthorization)) {
            	// id is in wrong format
        		logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.ERROR_MASSAGE_SCREEN_ID);
                returnValue = Constants.VALIDATE_WRONG_FORMAT;
                return returnValue;
            }
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
            	IvbMAccessAuthorization accessAuthorization = new IvbMAccessAuthorization();
                // Access Authority Id
            	accessAuthorization.setAccessAuthorityId(ivbMAccessAuthorization.getAccessAuthorityId());
            	// Screen Id
            	accessAuthorization.setScreenId(ivbMAccessAuthorization.getScreenId());
            	// Screen Display Enable Flag
            	accessAuthorization.setScreenDisplayEnableFlag(ivbMAccessAuthorization.getScreenDisplayEnableFlag());
            	// Add Able Flag
            	accessAuthorization.setAddableFlag(ivbMAccessAuthorization.getAddableFlag());
            	// Update able Flag
            	accessAuthorization.setUpdatableFlag(ivbMAccessAuthorization.getUpdatableFlag());
            	// Delete Able Flag
            	accessAuthorization.setDeletableFlag(ivbMAccessAuthorization.getDeletableFlag());
            	// Reference Able Flag
            	accessAuthorization.setReferenceFlag(ivbMAccessAuthorization.getReferenceFlag());
                // delete flag
            	accessAuthorization.setDeleteFlag(ivbMAccessAuthorization.getDeleteFlag());
                // create user id
            	accessAuthorization.setCreateUserId(util.getUserInfo().getID());
                // update user id
            	accessAuthorization.setUpdateUserId(util.getUserInfo().getID());

                int result = bnn0095SearchAccessAuthorizationDao.getIvbMAccessAuthorizationMapper().insert(accessAuthorization);
                if (result > Constants.RESULT_IS_EQUAL_TO_ZERO) { // update successfully
					// register to DB
					txManager.commit(status);
				} else {
					returnValue = Constants.INSERT_RESULT_FAILED_CULTIVATION_ACCESS_AUTHORIZATION;
					logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                            + LoggerMessage.CANT_INSERT_DATA);
					txManager.rollback(status);
				}
            } catch (DuplicateKeyException ex) {
                ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.DUPLICATE_KEY_EXCEPTION_ERROR_MESSAGE, ex);
                returnValue = Constants.INSERT_RESULT_DUPLICATED;
                txManager.rollback(status);
            }  catch (Exception ex) {
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
     * Delete Data Table IVB_M_ACCESS_AUTHORIZATION
     * 
     * @param accessAuthorityId
     * @param screenId
     * @return Delete successfully: 1/update failed: -1
     */
    public String deleteData(String accessAuthorityId, String screenId, Date lastUpdateDate) {
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
                // Access Authority Id
                params.put("accessAuthorityId", accessAuthorityId);
                // Screen Id
                params.put("screenId", screenId);
                // last Update Date
                params.put("lastUpdateDate", lastUpdateDate);
                // update user id
                params.put("updateUserId", util.getUserInfo().getID());
                // delete flag
                params.put("deleteFlag", Constants.DELETE_FLAG_ON);
                // check record is have deffenent lastUpdateDate
                // Set Data for screen Id
				int result = bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper().deleteData(params);
            	// if result > 0 then commit
                if (result > Constants.RESULT_IS_EQUAL_TO_ZERO) { // delete successfully
                	// register to DB
					txManager.commit(status);
                } else {
                	// check last updated date
                    if (!checkLastUpdatedDateTimeWhenDelete(accessAuthorityId, screenId, lastUpdateDate)) {
                    	// record has been updated
                    	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.CANT_DELETE_DATA_UPDATE_DATE);
                        returnValue = Constants.DELETE_RESULT_FAILED_UPDATE_DATE;
                        txManager.rollback(status);
                        return returnValue;
                    } else {
                    	returnValue = Constants.DELETE_RESULT_FAILED_CULTIVATION_ACCESS_AUTHORIZATION;
                        txManager.rollback(status);
                        logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                                + LoggerMessage.CANT_DELETE_DATA);
                    }
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
     * Create search conditions parameters from data received from client
     * 
     * @param searchConditions : data received from client
     * @return HashMap object
     */
    private HashMap<String, Object> createSearchConditionParams(Bnn0095SearchAccessAuthorizationConditions searchConditions) {
    	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
    			+ LoggerMessage.CREATE_SEARCH_CONDITIONS_STARTED);
    	HashMap<String, Object> params = new HashMap<String, Object>();
        // accessAuthorityId
        params.put("accessAuthorityId", searchConditions.getAccessAuthorityId().equals("") ? "" :  searchConditions.getAccessAuthorityId());
        // Screen id
        params.put("screenId", searchConditions.getScreenId().equals("") ? "" : "%" + searchConditions.getScreenId() + "%");

        params.put("screenDisplayEnableFlag", searchConditions.getScreenDisplayEnableFlag());
        params.put("addableFlag", searchConditions.getAddableFlag());
        params.put("updatableFlag", searchConditions.getUpdatableFlag());
        params.put("deletableFlag", searchConditions.getDeletableFlag());
        params.put("referenceFlag", searchConditions.getReferenceFlag());
        // From parameter
        params.put("fromRow", Integer.valueOf(searchConditions.getFromRow()));
        // Number of items in a page
        params.put("itemCount", Integer.valueOf(searchConditions.getItemCount()));

        return params;
    }

	/**
	 * Create get single conditions parameters from data received from client
	 * @param searchConditions: data received from client
	 * @return HashMap object
	 */
	private HashMap<String, Object> createGetSingleConditionParams(Bnn0095SearchAccessAuthorizationConditions searchConditions) {
		logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
    			+ LoggerMessage.CREATE_SEARCH_CONDITIONS_STARTED);
		HashMap<String, Object> params = new HashMap<String, Object>();
		// access Authority Id
		params.put("accessAuthorityId", searchConditions.getAccessAuthorityId().equals("") ? "" : searchConditions.getAccessAuthorityId());
		// screen Id
		params.put("screenId", searchConditions.getScreenId().equals("") ? "" : searchConditions.getScreenId());
		// delete Flag
		params.put("deleteFlag", Constants.DELETE_FLAG_OFF);
		return params;
	}
	
	/**
	 * Get Access Auhorization's information
	 * 
	 * @param searchConditions: data received from client
	 * @return Access Authorization data
	 */
	public List<Bnn0095SearchAccessAuthorizationResult> getSingleData(Bnn0095SearchAccessAuthorizationConditions searchConditions) {

		// variable definition
		List<Bnn0095SearchAccessAuthorizationResult> bnn0095ResultList = new ArrayList<Bnn0095SearchAccessAuthorizationResult>();
		HashMap<String, Object> params = createGetSingleConditionParams(searchConditions);
		try {
			//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
			try {
				logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
		    			+ LoggerMessage.ACCESS_SEARCH_STARTED);
				// select starts
				bnn0095ResultList = bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper().searchSingleData(params);
				logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	            		+ LoggerMessage.SQL_EXECUTION_FINISHED);
				// if bnn0095ResultList > 0 then responses Logger message
				if (bnn0095ResultList.size() > Constants.LIST_IS_EQUAL_TO_ZERO) {
	                // handle Access Authorization input data
	                convertSanitize(bnn0095ResultList);
	                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	    	    			+ LoggerMessage.ACCESS_SEARCH_FINISHED);
	            } else {
	            	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	    	    			+ LoggerMessage.ACCESS_SEARCH_FINISHED);
	            	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	    	    			+ LoggerMessage.ACCESS_SEARCH_RESULT_0_STRING);
	            }
				//release transaction
				txManager.commit(status);
			}  catch (Exception ex) {
				ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
				txManager.rollback(status);
				bnn0095ResultList = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
			bnn0095ResultList = null;
		}
		
		return bnn0095ResultList;
	}
	/**
     * Handle AccessAuthorization input data
     * 
     * @param inputData : search result data list
     */
    private void convertSanitize(List<Bnn0095SearchAccessAuthorizationResult> inputData) {
        for (int i = 0; i < inputData.size(); i++) {
        	Bnn0095SearchAccessAuthorizationResult currentData = inputData.get(i);
            // Screen id
            currentData.setScreenId(util.convertSanitize(currentData.getScreenId()));
        }
    }

    /**
 	 * Check input: screen id format (0 + xxx)
 	 * 
 	 * @param AccessAuthorizationData : data received from client
 	 * @return boolean : correct format: true/wrong format: false
 	 */
 	private boolean checkScreenIdFormat(IvbMAccessAuthorization AccessAuthorizationData) {
 		String screenId = AccessAuthorizationData.getScreenId();
 		if (!screenId.matches("0\\d{3}")) {
 			return false;
 		} else {
 			return true;
 		}
 	}

 	/**
 	 * Check whether current record has been updated before or not
 	 * 
 	 * @param accessAuthorizationData
 	 * @return boolean : 2 dates are equal: true/2 dates are not equal: false 
 	 */
 	private boolean checkLastUpdatedDateTime(Bnn0095SearchAccessAuthorizationConditions searchConditions, IvbMAccessAuthorization accessAuthorizationData) {
 		// get client last updated date time
 		Date lastUpdatedDateTimeClient = accessAuthorizationData.getLastUpdateDate();
 		// get server last updated date time
 		List<Bnn0095SearchAccessAuthorizationResult> serverData = getSingleData(searchConditions);
 		Date lastUpdatedDateTimeServer = serverData.get(0).getLastUpdateDate();
 		// compare date
 		if (!lastUpdatedDateTimeClient.equals(lastUpdatedDateTimeServer)) {
 			return false;
 		}
 		return true;
 	}
 
	/**
	 * Check whether current record has been updated before or not
	 * 
	 * @param area Data
	 * @return boolean : 2 dates are equal: true/2 dates are not equal: false 
	 * @throws ParseException 
	 */
	private boolean checkLastUpdatedDateTimeWhenDelete(String accessauthorizationId, String screenId, Date lastUpdateDate) throws ParseException {
		// set farmId & areaId to check lastUpdateDate.
		// set accessAuthorityId & screenId to check lastUpdateDate.
        Bnn0095SearchAccessAuthorizationConditions bnn0095SearchAccessAuthorizationConditions = new Bnn0095SearchAccessAuthorizationConditions();
        bnn0095SearchAccessAuthorizationConditions.setAccessAuthorityId(accessauthorizationId);
        bnn0095SearchAccessAuthorizationConditions.setScreenId(screenId);
        bnn0095SearchAccessAuthorizationConditions.setDeleteFlag("");
		// get client last updated date time
		Date lastUpdatedDateTimeClient = lastUpdateDate;
		// get server last updated date time
		List<Bnn0095SearchAccessAuthorizationResult> serverData = getSingleData(bnn0095SearchAccessAuthorizationConditions);
		Date lastUpdatedDateTimeServer = serverData.get(0).getLastUpdateDate();
		// compare date
		if (!lastUpdatedDateTimeClient.equals(lastUpdatedDateTimeServer)) {
			return false;
		}
		return true;
	}
}
