package vn.bananavietnam.ict.banana.service;

import java.util.ArrayList;
import java.util.Calendar;
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

import vn.bananavietnam.ict.banana.component.Bnn0013SearchProcessConditions;
import vn.bananavietnam.ict.banana.component.Bnn0013SearchProcessResult;
import vn.bananavietnam.ict.banana.dao.Bnn0013SearchProcessDao;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author HiepTL
 */
@Service
public class Bnn0013SearchProcessService {
    private static Logger logger = Logger.getLogger(Bnn0013SearchProcessService.class);

    private Util util = new Util();
    
    @Autowired
    private Bnn0013SearchProcessDao bnn0013CultivationProcessDao;

    // Variables definition
    // send arraylist data to client side
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ApplicationContext appContext;

    public void setAppContext(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    /**
     * Search process in DB based on search conditions received from client
     * 
     * @param searchConditions:
     *            data received from client
     * @return List of Process data
     */
    public List<Bnn0013SearchProcessResult> searchData(Bnn0013SearchProcessConditions searchConditions) {
        List<Bnn0013SearchProcessResult> bnn0013ResultList = new ArrayList<Bnn0013SearchProcessResult>();
        HashMap<String, Object> params = createSearchConditionParams(searchConditions);
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                        + LoggerMessage.PROCESS_SEARCH_STARTED);
                // search starts
                bnn0013ResultList = bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper().searchData(params);
                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + "," + LoggerMessage.SQL_EXECUTION_FINISHED);
                if (bnn0013ResultList.size() > 0) {
                    // "real" total from search result
                    String searchDataTotalCounts = bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()
                            .getSearchDataTotalCounts(params);
                    bnn0013ResultList.get(0).setSearchDataTotalCounts(searchDataTotalCounts);
                    // handle input data
                    convertSanitize(bnn0013ResultList);
                    logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                            + LoggerMessage.PROCESS_SEARCH_RESULT_STRING.replace("$1", searchDataTotalCounts));
                } else {
                    logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                            + LoggerMessage.PROCESS_SEARCH_RESULT_0_STRING);
                }
                txManager.commit(status);
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
                Bnn0013SearchProcessResult tempObj = new Bnn0013SearchProcessResult();
                tempObj.setSearchDataTotalCounts("-1");
                bnn0013ResultList.add(tempObj);
                txManager.rollback(status);
                // log to file
                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
            } catch (Exception ex) {
                ex.printStackTrace();
                bnn0013ResultList = null;
                txManager.rollback(status);
                // log to file
                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // log to file
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            bnn0013ResultList = null;
        }
        return bnn0013ResultList;
    }

    /**
     * Create search conditions parameters from data received from client
     * 
     * @param searchConditions
     *            : data received from client
     * @return HashMap object
     */
    private HashMap<String, Object> createSearchConditionParams(Bnn0013SearchProcessConditions searchConditions) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        // Process name
        params.put("processName",
                searchConditions.getProcessName().equals("") ? "" : "%" + searchConditions.getProcessName() + "%");
        /*
         * // Process status if (searchConditions.getStatus().equals(Constants.
         * SEARCH_CONDITION_NO_SELECT)) { params.put("status",
         * Constants.SEARCH_CONDITION_NO_SELECT); } else { params.put("status",
         * searchConditions.getStatus()); }
         */
        // From parameter
        params.put("fromRow", Integer.valueOf(searchConditions.getFromRow()));
        // Number of items in a page
        params.put("itemCount", Integer.valueOf(searchConditions.getItemCount()));

        return params;
    }

    /**
     * Handle process input data
     * 
     * @param inputData:
     *            search result data list
     */
    private void convertSanitize(List<Bnn0013SearchProcessResult> inputData) {
        for (int i = 0; i < inputData.size(); i++) {
            Bnn0013SearchProcessResult currentData = inputData.get(i);
            // Process id
            currentData.setProcessId(util.convertSanitize(currentData.getProcessId()));
            // Process name
            currentData.setProcessName((util.convertSanitize(currentData.getProcessName())));
        }
    }

    /**
     * Get process information based on CultivationProcess
     * 
     * @param ivbMCultivationProcessKey
     *            : ivbMCultivationProcessKey received from client
     * @return IvbMCultivationProcess data
     */
    // 20170203
    public IvbMProcess getSingleData(Bnn0013SearchProcessConditions bnn0013ProcessConditions) {
        IvbMProcess ret = new IvbMProcess();
        try {
	        //remove cache
	        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        try {
	            //main process
	            ret = bnn0013CultivationProcessDao.getIvbMProcessMapper()
	                    .selectByPrimaryKey(bnn0013ProcessConditions.getProcessId());
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
     * Update information to DB
     * 
     * @param ivbMCultivationProcess:
     *            data received from client
     * @return String : update successfully: 1/update failed: -1
     */
    // 20170203
    public String updateData(IvbMProcess ivbMCultivationProcess) {
        // variable definition
        String returnValue = Constants.UPDATE_RESULT_SUCCESSFUL;
        // update starts
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
                // check for info input
                if (!checkInputBlankFields(ivbMCultivationProcess)) {
                    // blank field(s)
                	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                            + LoggerMessage.BLANK_FIELDS_ERROR_MESSAGE);
                    returnValue = Constants.VALIDATE_BLANK_FIELDS;
                    return returnValue;
                }
                HashMap<String, Object> params = new HashMap<String, Object>();
                // Process Id
                params.put("processId", ivbMCultivationProcess.getProcessId());
                // Process Name
                params.put("processName", ivbMCultivationProcess.getProcessName());
                // update user id
                params.put("updateUserId", util.getUserInfo().getID());
                // delete flag
                params.put("deleteFlag", ivbMCultivationProcess.getDeleteFlag());
                params.put("lastUpdateDate", ivbMCultivationProcess.getLastUpdateDate());

                int result = bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper().updateData(params);
                if (result > 0) { // update successfully
                    // register to DB
                    txManager.commit(status);
                } else {
                    
                    if (!checkLastUpdatedDateTime(ivbMCultivationProcess)) {
                    	// record has been updated
                    	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.CANT_UPDATE_DATA_UPDATE_DATE);
                        returnValue = Constants.UPDATE_RESULT_FAILED_UPDATE_DATE;
                        txManager.rollback(status);
                        return returnValue;
                    } else {                	
	                    returnValue = Constants.UPDATE_RESULT_FAILED_PROCESS;	                    
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
     * Insert process's information to DB
     * 
     * @param ivbMCultivationProcess
     *            : data received from client
     * @return String : insert successfully: 1/insert failed: -1
     */
    // 20170203
    public String insertData(IvbMProcess ivbMCultivationProcess) {
        // variable definition
        String returnValue = Constants.INSERT_RESULT_SUCCESSFUL;
        int result = 0;
        String processId = "";
        // update starts
        try {
            // check for info input
            if (!checkInputBlankFields(ivbMCultivationProcess)) {
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
                	String idNumberStr = bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()
                            .getLastProcessId();
                	int idNumber = 0;
                	if (idNumberStr != null) {
                		idNumber = Integer.parseInt(idNumberStr.substring(1));
                	}
                    idNumber = idNumber + 1;

                    processId = Constants.BEGIN_PROCESS_ID + String.format("%0" + Constants.MAX_LENGHT_ID + "d", idNumber);
                    IvbMProcess objInsert = new IvbMProcess();
                    // Process Id
                    objInsert.setProcessId(processId);
                    // Process Name
                    objInsert.setProcessName(ivbMCultivationProcess.getProcessName());
                    // create user id
                    objInsert.setCreateUserId(util.getUserInfo().getID());
                    // create date
                    objInsert.setCreateDate(Calendar.getInstance().getTime());
                    // update user id
                    objInsert.setUpdateUserId(util.getUserInfo().getID());
                    // last update date
                    objInsert.setLastUpdateDate(Calendar.getInstance().getTime());
                    // delete flag
                    objInsert.setDeleteFlag(ivbMCultivationProcess.getDeleteFlag());

                    result = bnn0013CultivationProcessDao.getIvbMProcessMapper().insert(objInsert);
                    if (result > 0) { // insert successfully
                        // register to DB
                        txManager.commit(status);
                    } else {
                        returnValue = Constants.INSERT_RESULT_FAILED_PROCESS;
                        logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                                + LoggerMessage.CANT_INSERT_DATA);
                        txManager.rollback(status);
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
     * Delete process from DB
     * 
     * @param ivbMCultivationProcessKey
     *            : CultivationProcess to delete
     * @return String : delete successfully: 1/delete failed: -1
     */
    // 20170203
    public String deleteData(IvbMProcess ivbMCultivationProcessKey) {
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
                // Process Id
                params.put("processId", ivbMCultivationProcessKey.getProcessId());
                // Process Name
                //params.put("processName", ivbMCultivationProcessKey.getProcessName());
                // update user id
                params.put("updateUserId", util.getUserInfo().getID());
                // delete flag
                params.put("deleteFlag", Constants.DELETE_FLAG_ON);
                params.put("lastUpdateDate", ivbMCultivationProcessKey.getLastUpdateDate());

                int result = bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper().updateData(params);
            	
            	
                if (result > 0) { // delete successfully
                    // register to DB
                    txManager.commit(status);
                } else {
                	
                	if (!checkLastUpdatedDateTime(ivbMCultivationProcessKey)) {
                    	// record has been updated
                    	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.CANT_DELETE_DATA_UPDATE_DATE);
                        returnValue = Constants.DELETE_RESULT_FAILED_UPDATE_DATE;
                        txManager.rollback(status);
                        return returnValue;
                    } else {                	
	                    returnValue = Constants.DELETE_RESULT_FAILED_PROCESS;	                    
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
     * Check input: blank fields
     * 
     * @param ivbMCultivationProcess:
     *            data received from client
     * @return boolean : all fields have value: true/blank fields exist: false
     */
    // 20170203
    private boolean checkInputBlankFields(IvbMProcess ivbMCultivationProcess) {
        if (ivbMCultivationProcess.getProcessName().equals("")) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
 	 * Check whether current record has been updated before or not
 	 * 
 	 * @param ivbMCultivationProcess
 	 * @return boolean : 2 dates are equal: true/2 dates are not equal: false 
 	 */
 	private boolean checkLastUpdatedDateTime(IvbMProcess ivbMCultivationProcess) {
 		// get client last updated date time
 		Date lastUpdatedDateTimeClient = ivbMCultivationProcess.getLastUpdateDate();
 		// get server last updated date time
 		Bnn0013SearchProcessConditions bnn0013SearchProcessConditions = new Bnn0013SearchProcessConditions();
 		bnn0013SearchProcessConditions.setProcessId(ivbMCultivationProcess.getProcessId());
 		IvbMProcess serverData = getSingleData(bnn0013SearchProcessConditions);
 		Date lastUpdatedDateTimeServer = serverData.getLastUpdateDate();
 		// compare date
 		if (!lastUpdatedDateTimeClient.equals(lastUpdatedDateTimeServer)) {
 			return false;
 		}
 		return true;
 	}
}