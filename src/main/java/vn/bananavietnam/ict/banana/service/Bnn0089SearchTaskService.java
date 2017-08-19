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

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.bananavietnam.ict.banana.component.Bnn0089SearchTaskConditions;
import vn.bananavietnam.ict.banana.component.Bnn0089SearchTaskResult;
import vn.bananavietnam.ict.banana.dao.Bnn0089SearchTaskDao;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Nghia Nguyen
 */
@Service
public class Bnn0089SearchTaskService {

	private static Logger logger = Logger.getLogger(Bnn0089SearchTaskService.class);

	private Util util = new Util();
	
	@Autowired
	private Bnn0089SearchTaskDao bnn0089SearchTaskDao;
    // Variables definition
	// send arraylist data to client side
	ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ApplicationContext appContext;
    
    public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

    /**
     * Search area in DB based on search conditions received from client
     * 
     * @param searchConditions : data received from client
     * @return List of users data
     */
	public List<Bnn0089SearchTaskResult> searchData(Bnn0089SearchTaskConditions searchConditions) {
		List<Bnn0089SearchTaskResult> bnn0089ResultList = new ArrayList<Bnn0089SearchTaskResult>();
        HashMap<String, Object> params = createSearchConditionParams(searchConditions);
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                        + LoggerMessage.TASK_SEARCH_STARTED);
                // search starts
                bnn0089ResultList = bnn0089SearchTaskDao.getBnn0089SearchTaskMapper().searchData(params);
                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + "," + LoggerMessage.SQL_EXECUTION_FINISHED);
                if (bnn0089ResultList.size() > 0) {
                    // "real" total from search result
                    String searchDataTotalCounts = bnn0089SearchTaskDao.getBnn0089SearchTaskMapper().getSearchDataTotalCounts(params);
                    bnn0089ResultList.get(0).setSearchDataTotalCounts(searchDataTotalCounts);
                    // handle user input data
                    convertSanitize(bnn0089ResultList);
                    logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                            + LoggerMessage.TASK_SEARCH_RESULT_STRING.replace("$1", searchDataTotalCounts));
                } else {
                    logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                            + LoggerMessage.TASK_SEARCH_RESULT_0_STRING);
                }
                txManager.commit(status);
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
                Bnn0089SearchTaskResult tempObj = new Bnn0089SearchTaskResult();
                tempObj.setSearchDataTotalCounts("-1");
                bnn0089ResultList.add(tempObj);
            	// log to file
                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
                txManager.rollback(status);
            } catch (Exception ex) {
                ex.printStackTrace();
                bnn0089ResultList = null;
            	// log to file
                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                txManager.rollback(status);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        	// log to file
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            bnn0089ResultList = null;
        }
       
        return bnn0089ResultList;
	}

	/**
     * Update task's information to DB
     * 
     * @param areaData : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
    public String updateData(IvbMTask taskData) {
        // variable definition
        String returnValue = Constants.UPDATE_RESULT_SUCCESSFUL;
        // update starts
        try {
        	// check Task Id
            // check for info input
            if (!checkInputBlankFields(taskData)) {
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
            	// Task id
                params.put("taskId", taskData.getTaskId());
                // Task name
                params.put("taskName", taskData.getTaskName());
            	// Working Details
                params.put("workingDetails", taskData.getWorkingDetails());
                // Quarantine Handling Flag
                params.put("quarantineHandlingFlag", taskData.getQuarantineHandlingFlag());
                // note
                params.put("note", taskData.getNote());
                // update user id
                params.put("updateUserId", util.getUserInfo().getID());
                // delete flag
                params.put("deleteFlag", taskData.getDeleteFlag());
                params.put("lastUpdateDate", taskData.getLastUpdateDate());
            	
                //change point code
                params.put("changePointCode", taskData.getChangePointCode());

                int result = bnn0089SearchTaskDao.getBnn0089SearchTaskMapper().updateData(params);
                if (result > 0) { // update successfully
					// register to DB
					txManager.commit(status);
				} else {
					 // check last updated date
                    if (!checkLastUpdatedDateTime(taskData)) {
                    	// record has been updated
                    	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.CANT_UPDATE_DATA_UPDATE_DATE);
                        returnValue = Constants.UPDATE_RESULT_FAILED_UPDATE_DATE;
                        txManager.rollback(status);
                        return returnValue;
                    } else {                	
	                    returnValue = Constants.UPDATE_RESULT_FAILED_TASK;	                    
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
     * Insert area's information to DB
     * 
     * @param areaData : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
    public String insertData(IvbMTask taskData) {
        // variable definition
        String returnValue = Constants.INSERT_RESULT_SUCCESSFUL;
        int result = 0;
        String taskId = "";
        // update starts
        try {
        	// check Task Id
            if (!checkInputBlankFields(taskData)) {
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
                	String idNumberStr = bnn0089SearchTaskDao.getBnn0089SearchTaskMapper()
                            .getLastTaskId();
                	int idNumber = 0;
                	if (idNumberStr != null) {
                		idNumber = Integer.parseInt(idNumberStr.substring(1));
                	}
                    idNumber = idNumber + 1;

                    taskId = Constants.BEGIN_TASK_ID + String.format("%0" + Constants.MAX_LENGHT_ID + "d", idNumber);
                	IvbMTask taskObj = new IvbMTask();
                    // Task name
                    taskObj.setTaskName(taskData.getTaskName());
                    // Task Id
                    taskObj.setTaskId(taskId);
                    // Working Details
                    taskObj.setWorkingDetails(taskData.getWorkingDetails());
                    // Quarantine Handling Flag
                    taskObj.setQuarantineHandlingFlag(taskData.getQuarantineHandlingFlag());
                    // note
                    taskObj.setNote(taskData.getNote());
                    // delete flag
                    taskObj.setDeleteFlag(taskData.getDeleteFlag());
                    //change point code
                    taskObj.setChangePointCode(taskData.getChangePointCode());
                    // create user id
                    taskObj.setCreateUserId(util.getUserInfo().getID());
                    // update user id
                    taskObj.setUpdateUserId(util.getUserInfo().getID());
    
                    result = bnn0089SearchTaskDao.getIvbMTaskMapper().insert(taskObj);
                    if (result > 0) { // update successfully
    					// register to DB
    					txManager.commit(status);
    				} else {
    					returnValue = Constants.INSERT_RESULT_FAILED_TASK;
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
     * Delet Data Table IVB_M_TASK
     * @param processId
     * @param taskId
     * @return
     */
    public String deleteData(IvbMTask taskData) {
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
            	// Task id
                params.put("taskId", taskData.getTaskId());
                // update user id
                params.put("updateUserId", util.getUserInfo().getID());
                // delete flag
                params.put("deleteFlag", Constants.DELETE_FLAG_ON);
                params.put("lastUpdateDate", taskData.getLastUpdateDate());
            	
                //change point code
                //params.put("changePointCode", taskData.getChangePointCode());

                int result = bnn0089SearchTaskDao.getBnn0089SearchTaskMapper().updateData(params);
            	
                if (result > 0) { // delete successfully
						txManager.commit(status);
                } else {
                	if (!checkLastUpdatedDateTime(taskData)) {
                    	// record has been updated
                    	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.CANT_DELETE_DATA_UPDATE_DATE);
                        returnValue = Constants.DELETE_RESULT_FAILED_UPDATE_DATE;
                        txManager.rollback(status);
                        return returnValue;
                    } else {                	
	                    returnValue = Constants.DELETE_RESULT_FAILED_TASK;	                    
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
    private HashMap<String, Object> createSearchConditionParams(Bnn0089SearchTaskConditions searchConditions) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        // Task name
        params.put("taskName", searchConditions.getTaskName().equals("") ? "" : "%" + searchConditions.getTaskName() + "%");
        // From parameter
        params.put("fromRow", Integer.valueOf(searchConditions.getFromRow()));
        // Number of items in a page
        params.put("itemCount", Integer.valueOf(searchConditions.getItemCount()));

        return params;
    }

	/**
	 * Get farm information based on farm's name and user's name
	 * @param searchConditions: data received from client
	 * @return Process data
	 */
    
	public IvbMTask getSingleData(Bnn0089SearchTaskConditions searchConditions) {
		IvbMTask ret = new IvbMTask();
        try {
	        //remove cache
	        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        try {
	            //main process
	            ret = bnn0089SearchTaskDao.getIvbMTaskMapper()
	                    .selectByPrimaryKey(searchConditions.getTaskId());
	            //release transaction
	            txManager.commit(status);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        	// log to file
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            ret = null;
	            txManager.rollback(status);
	        }
        }  catch (Exception ex) {
            ex.printStackTrace();
        	// log to file
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            ret = null;
        }
		return ret;
    }
	
	
	/**
     * Handle Task input data
     * 
     * @param inputData : search result data list
     */
    private void convertSanitize(List<Bnn0089SearchTaskResult> inputData) {
        for (int i = 0; i < inputData.size(); i++) {
        	Bnn0089SearchTaskResult currentData = inputData.get(i);
            // Task id
            currentData.setWorkingDetails(util.convertSanitize(currentData.getWorkingDetails()));
            // Task Name
            currentData.setTaskName(util.convertSanitize(currentData.getTaskName()));
        }
    }
    /**
     * Check input: blank fields
     * 
     * @param taskData:
     *            data received from client
     * @return boolean : all fields have value: true/blank fields exist: false
     */
    private boolean checkInputBlankFields(IvbMTask taskData) {
        if (taskData.getTaskName().equals("") || taskData.getWorkingDetails().equals("")) {
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
    private boolean checkLastUpdatedDateTime(IvbMTask taskData) {
 		// get client last updated date time
 		Date lastUpdatedDateTimeClient = taskData.getLastUpdateDate();
 		// get server last updated date time
 		Bnn0089SearchTaskConditions bnn0089SearchTaskConditions = new Bnn0089SearchTaskConditions();
 		bnn0089SearchTaskConditions.setTaskId(taskData.getTaskId());
 		IvbMTask serverData = getSingleData(bnn0089SearchTaskConditions);
 		Date lastUpdatedDateTimeServer = serverData.getLastUpdateDate();
 		// compare date
 		if (!lastUpdatedDateTimeClient.equals(lastUpdatedDateTimeServer)) {
 			return false;
 		}
 		return true;
 	}

}
