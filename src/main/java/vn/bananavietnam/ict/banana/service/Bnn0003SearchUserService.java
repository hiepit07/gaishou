package vn.bananavietnam.ict.banana.service;

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

import vn.bananavietnam.ict.banana.component.Bnn0003SearchUserConditions;
import vn.bananavietnam.ict.banana.component.Bnn0003SearchUserDataObject;
import vn.bananavietnam.ict.banana.component.Bnn0003SearchUserResult;
import vn.bananavietnam.ict.banana.dao.Bnn0003SearchUserDao;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.db.model.IvbMUsers;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Khoa Le
 * Search user, create new user and edit user's info
 * Service connects to DB, processes requests and returns results to Controller
 */
@Service
public class Bnn0003SearchUserService {

	private static Logger logger = Logger.getLogger(Bnn0003SearchUserService.class);

	private Util util = new Util();
	
	@Autowired
	private Bnn0003SearchUserDao bnn0003SearchUserDao;

     @Autowired
    private ApplicationContext appContext;

	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	/**
     * Search user in DB based on search conditions received from client
     * 
     * @param searchConditions : data received from client
     * @return List of users data
     */
	public List<Bnn0003SearchUserResult> searchData(Bnn0003SearchUserConditions searchConditions) {
		List<Bnn0003SearchUserResult> bnn0003ResultList = new ArrayList<Bnn0003SearchUserResult>();
        HashMap<String, Object> params = createSearchConditionParams(searchConditions);
        try {
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.USER_SEARCH_STARTED);
            // search starts
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
	            bnn0003ResultList = bnn0003SearchUserDao.getBnn0003SearchUserMapper().searchData(params);
	            logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + "," 
	            		+ LoggerMessage.SQL_EXECUTION_FINISHED);
	            if (bnn0003ResultList.size() > 0) {
	                // "real" total from search result
	                String searchDataTotalCounts = bnn0003SearchUserDao.getBnn0003SearchUserMapper().getSearchDataTotalCounts(params);
	                bnn0003ResultList.get(0).setSearchDataTotalCounts(searchDataTotalCounts);
	                // handle user input data
	                convertSanitize(bnn0003ResultList);
	                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                		+ LoggerMessage.USER_SEARCH_RESULT_STRING.replace("$1", searchDataTotalCounts));
	            } else {
	                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                		+ LoggerMessage.USER_SEARCH_RESULT_0_STRING);
	            }
	            txManager.commit(status);
	        } catch (OutOfMemoryError ex) {
	            ex.printStackTrace();
	            txManager.rollback(status);
	            // log to file
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
	            Bnn0003SearchUserResult tempObj = new Bnn0003SearchUserResult();
	            tempObj.setSearchDataTotalCounts("-1");
	            bnn0003ResultList.add(tempObj);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            txManager.rollback(status);
	            // log to file
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            bnn0003ResultList = null;
	        }
        } catch (Exception ex) {
            ex.printStackTrace();
            // log to file
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            bnn0003ResultList = null;
        }
        return bnn0003ResultList;
	}

	/**
     * Update user's information to DB
     * 
     * @param userData : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
    public String updateData(Bnn0003SearchUserDataObject userData) {
        // variable definition
        String returnValue = Constants.UPDATE_RESULT_SUCCESSFUL;
        // update starts
        try {
            // check for user input
            if (!checkInputBlankFields(userData)) {
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
            	HashMap<String, Object> params = new HashMap<String, Object>();
            	// User id
                params.put("userId", userData.getUsersId());
                // User name
                params.put("usersName", userData.getUsersName());
                // password
                // check if user has changed the password
                if (userData.isPasswordChanged()) {
                	params.put("password", userData.getPassword());
                	//userObj.setPassword(userData.getPassword());
                }
                // update user id
                params.put("updateUserId", util.getUserInfo().getID());
                // delete flag
                params.put("deleteFlag", userData.getDeleteFlag());
                params.put("lastUpdateDate", userData.getLastUpdateDate());
                int result = bnn0003SearchUserDao.getBnn0003SearchUserMapper().updateData(params);
                if (result > 0) { // update successfully
                    // register to DB
                    txManager.commit(status);
                } else {
                	 // check last updated date
                    if (!checkLastUpdatedDateTime(userData)) {
                    	// record has been updated
                    	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.CANT_UPDATE_DATA_UPDATE_DATE);
                        returnValue = Constants.UPDATE_RESULT_FAILED_UPDATE_DATE;
                        txManager.rollback(status);
                        return returnValue;
                    } else {                	
	                    returnValue = Constants.UPDATE_RESULT_FAILED_CULTIVATION_USER;	                    
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
     * Insert user's information to DB
     * 
     * @param userData : data received from client
     * @return String : insert successfully: 1/insert failed: -1
     */
    public String insertData(IvbMUsers userData) {
        // variable definition
        String returnValue = Constants.INSERT_RESULT_SUCCESSFUL;
        // insert starts
        try {
        	// check for user input
            if (!checkInputBlankFields(userData)) {
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
                IvbMUsers userObj = new IvbMUsers();
                // users id
                userObj.setUsersId(userData.getUsersId());
                // users name
                userObj.setUsersName(userData.getUsersName());
                // password
                userObj.setPassword(userData.getPassword());
                // create user id
                userObj.setCreateUserId(util.getUserInfo().getID());
                // update user id
                userObj.setUpdateUserId(util.getUserInfo().getID());
                // delete flag
                userObj.setDeleteFlag(userData.getDeleteFlag());

                int result = bnn0003SearchUserDao.getIvbMUsersMapper().insert(userObj);
                if (result > 0) { // insert successfully
                    // register to DB
                    txManager.commit(status);
                } else {
                    returnValue = Constants.INSERT_RESULT_FAILED_CULTIVATION_USER;
                    logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                            + LoggerMessage.CANT_INSERT_DATA);
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
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);;
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
    public String deleteData(IvbMUsers userData) {
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
            	params.put("userId", userData.getUsersId());
                // User name
                params.put("usersName", userData.getUsersName());
                // update user id
                params.put("updateUserId", util.getUserInfo().getID());
                // delete flag
                params.put("deleteFlag", Constants.DELETE_FLAG_ON);
                params.put("lastUpdateDate", userData.getLastUpdateDate());
                int result = bnn0003SearchUserDao.getBnn0003SearchUserMapper().updateData(params);
            	if (result > 0) { // delete successfully
                    // register to DB
                    txManager.commit(status);
                } else {
                	if (!checkLastUpdatedDateTime(userData)) {
                    	// record has been updated
                    	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.CANT_DELETE_DATA_UPDATE_DATE);
                        returnValue = Constants.DELETE_RESULT_FAILED_UPDATE_DATE;
                        txManager.rollback(status);
                        return returnValue;
                    } else {                	
	                    returnValue = Constants.DELETE_RESULT_FAILED_CULTIVATION_USER;	                    
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
     * Get user information based on user's id
     * 
     * @param usersId : user's id received from client
     * @return user data
     */
	public IvbMUsers getSingleData(String usersId) {
		IvbMUsers returnValue = new IvbMUsers();
		try {
        	// transaction starts
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
            	returnValue = bnn0003SearchUserDao.getIvbMUsersMapper().selectByPrimaryKey(usersId);
            	txManager.commit(status);
            } catch (Exception ex) {
                ex.printStackTrace();
                // log to file
                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                returnValue = null;
                txManager.rollback(status);
            }
		} catch (Exception ex) {
            ex.printStackTrace();
            // log to file
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            returnValue = null;
        }		
		return returnValue;
	}

	/**
     * Create search conditions parameters from data received from client
     * 
     * @param searchConditions : data received from client
     * @return HashMap object
     */
    private HashMap<String, Object> createSearchConditionParams(Bnn0003SearchUserConditions searchConditions) {
    	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
    			+ LoggerMessage.CREATE_SEARCH_CONDITIONS_STARTED);
        HashMap<String, Object> params = new HashMap<String, Object>();
        // User id
        params.put("usersId", searchConditions.getUsersId().equals("") ? "" : "%" + searchConditions.getUsersId() + "%");
        // User name
        params.put("usersName", searchConditions.getUsersName().equals("") ? "" : "%" + searchConditions.getUsersName() + "%");
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
    private void convertSanitize(List<Bnn0003SearchUserResult> inputData) {
        for (int i = 0; i < inputData.size(); i++) {
        	Bnn0003SearchUserResult currentData = inputData.get(i);
            // Users id
            currentData.setUsersId(util.convertSanitize(currentData.getUsersId()));
            // User name
            currentData.setUsersName(util.convertSanitize(currentData.getUsersName()));
        }
    }

    /**
     * Check input: blank fields
     * 
     * @param userData : data received from client
     * @return boolean : all fields have value: true/blank fields exist: false
     */
 	private boolean checkInputBlankFields(IvbMUsers userData) {
 		if (userData.getUsersId().equals("") || userData.getUsersName().equals("")
 			|| userData.getPassword().equals("")) {
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
 	private boolean checkLastUpdatedDateTime(IvbMUsers userData) {
 		// get client last updated date time
 		Date lastUpdatedDateTimeClient = userData.getLastUpdateDate();
 		// get server last updated date time
 		IvbMUsers serverData = getSingleData(userData.getUsersId());
 		Date lastUpdatedDateTimeServer = serverData.getLastUpdateDate();
 		// compare date
 		if (!lastUpdatedDateTimeClient.equals(lastUpdatedDateTimeServer)) {
 			return false;
 		}
 		return true;
 	}
}