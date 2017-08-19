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
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.bananavietnam.ict.banana.component.Bnn0087AffiliationDelete;
import vn.bananavietnam.ict.banana.component.Bnn0087IvbMManager;
import vn.bananavietnam.ict.banana.component.Bnn0087SearchAffiliationConditions;
import vn.bananavietnam.ict.banana.component.Bnn0087SearchAffiliationResult;
import vn.bananavietnam.ict.banana.component.Bnn0088SearchAreaResult;
import vn.bananavietnam.ict.banana.component.Bnn0088SearchFarmResult;
import vn.bananavietnam.ict.banana.dao.Bnn0087SearchAffiliationDao;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.db.model.IvbMAuthorization;
import vn.bananavietnam.ict.common.db.model.IvbMManager;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author HiepTL
 */
@Service
public class Bnn0087SearchAffiliationService {
    private static Logger logger = Logger.getLogger(Bnn0089SearchTaskService.class);
    
    private Util util = new Util();
    
    @Autowired
    private Bnn0087SearchAffiliationDao bnn0087SearchAffiliationDao;
    // Variables definition
    // send arraylist data to client side
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ApplicationContext appContext;

    public void initData(Model model) {
        // Get Farm data for combobox
        List<IvbMAuthorization> processData = new ArrayList<IvbMAuthorization>();
        try {
    		//remove cache
    		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
	            processData = bnn0087SearchAffiliationDao.getIvbMAuthorizationMapper().selectByExample(null);
	    		//release transaction
	    		txManager.commit(status);
	            model.addAttribute("affiliationData", mapper.writeValueAsString(processData));
            } catch (Exception ex) {
                ex.printStackTrace();
                txManager.rollback(status);
                processData = new ArrayList<IvbMAuthorization>();
            	// log to file
                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                model.addAttribute("affiliationData", "''");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            processData = new ArrayList<IvbMAuthorization>();
        	// log to file
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            model.addAttribute("affiliationData", "''");
        }
    }

    public List<Bnn0087SearchAffiliationResult> searchData(Bnn0087SearchAffiliationConditions searchConditions) {
        List<Bnn0087SearchAffiliationResult> bnn0087ResultList = new ArrayList<Bnn0087SearchAffiliationResult>();
        HashMap<String, Object> params = createSearchConditionParams(searchConditions);
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + "," + LoggerMessage.AFFILIATION_SEARCH_STARTED);
                // search starts
                bnn0087ResultList = bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper().searchData(params);
                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + "," + LoggerMessage.SQL_EXECUTION_FINISHED);
                if (bnn0087ResultList.size() > 0) {
                    // "real" total from search result
                    String searchDataTotalCounts = bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()
                            .getSearchDataTotalCounts(params);
                    bnn0087ResultList.get(0).setSearchDataTotalCounts(searchDataTotalCounts);
                    // handle user input data
                    convertSanitize(bnn0087ResultList);
                    logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + "," + LoggerMessage.AFFILIATION_SEARCH_RESULT_STRING.replace("$1", searchDataTotalCounts));
                } else {
                    logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + "," + LoggerMessage.AFFILIATION_SEARCH_RESULT_0_STRING);
                }
                txManager.commit(status);
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
                Bnn0087SearchAffiliationResult tempObj = new Bnn0087SearchAffiliationResult();
                tempObj.setSearchDataTotalCounts("-1");
                bnn0087ResultList.add(tempObj);
            	// log to file
                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
                txManager.rollback(status);
            } catch (Exception ex) {
                ex.printStackTrace();
                bnn0087ResultList = null;
            	// log to file
                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                txManager.rollback(status);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        	// log to file
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            bnn0087ResultList = null;
        }
       
        return bnn0087ResultList;
    }

    private void convertSanitize(List<Bnn0087SearchAffiliationResult> bnn0087ResultList) {
        for (int i = 0; i < bnn0087ResultList.size(); i++) {
            Bnn0087SearchAffiliationResult currentData = bnn0087ResultList.get(i);
            bnn0087ResultList.get(i).setTypeName(util.convertSanitize(currentData.getTypeName()));
            bnn0087ResultList.get(i).setUserName(util.convertSanitize(currentData.getUserName()));
        }

    }

    /**
     * Create search conditions parameters from data received from client
     * 
     * @param searchConditions
     *            : data received from client
     * @return HashMap object
     */
    private HashMap<String, Object> createSearchConditionParams(Bnn0087SearchAffiliationConditions searchConditions) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        String usersName = searchConditions.getUsersName();
        if (!usersName.equalsIgnoreCase("")) {
            usersName = "%" + searchConditions.getUsersName() + "%";
        } else {
            usersName = "";
        }
        // User name
        params.put("usersName", usersName);
        // authorityName
        params.put("authorityId", searchConditions.getAuthorityId());
        // From parameter
        params.put("fromRow", Integer.valueOf(searchConditions.getFromRow()));
        // Number of items in a page
        params.put("itemCount", Integer.valueOf(searchConditions.getItemCount()));

        return params;
    }

    /**
     * Delete Manager from DB
     * 
     * @param ivbMManagerKey
     *            : Manager to delete
     * @return String : delete successfully: 1/delete failed: -1
     */
    public String deleteData(Bnn0087AffiliationDelete bnn0087AffiliationDelete) {
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
                params.put("userId", bnn0087AffiliationDelete.getUserId());
                params.put("authorityId", bnn0087AffiliationDelete.getAuthorizationTypeId());
                params.put("lastUpdateDate", bnn0087AffiliationDelete.getLastUpdateDate());
                ArrayList<Bnn0088SearchAreaResult> resultCheckAreaManager = (ArrayList<Bnn0088SearchAreaResult>)bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper().checkAreaManager(params);
                if (resultCheckAreaManager.size() > 0) {
                    returnValue = Constants.DELETE_RESULT_FAILED_MANAGER;
                    txManager.rollback(status);
                    logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                            + LoggerMessage.AFFILIATION_DELETE_ERROR);
                } else {
                	if (bnn0087AffiliationDelete.getAuthorizationTypeId() != null && !bnn0087AffiliationDelete.getAuthorizationTypeId().equalsIgnoreCase("null")){
                		// check condition delete
                    	int result = bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper().deleteData(params);
                    	if (result > 0) {// delete successfully
                            // register to DB
                    		txManager.commit(status);
                    	} else {
                			// check last updated date
                	        if (!checkLastUpdatedDateTime(bnn0087AffiliationDelete)) {
                            	// record has been updated
                            	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                            			+ LoggerMessage.CANT_DELETE_DATA_UPDATE_DATE);
                                returnValue = Constants.DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE;
    							txManager.rollback(status);
                                return returnValue;
                            } else {
    	                		returnValue = Constants.DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION;
    	                        txManager.rollback(status);
    	                        logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
    	                                + LoggerMessage.CANT_DELETE_DATA);
                            }
                    	}
                	} else {
                		Date server = bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper().getLastUpdateDate(params);
                    	if (server != null){
                    		// record has been updated
                        	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                        			+ LoggerMessage.CANT_DELETE_DATA_UPDATE_DATE);
                            returnValue = Constants.DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE;
    						txManager.rollback(status);
                            return returnValue;
                    	} else {
                    		// register to DB
                    		txManager.commit(status);
                    	}
                	}
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                returnValue = Constants.DELETE_RESULT_FAILED_EXCEPTION;
                txManager.rollback(status);
            	// log to file
                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            returnValue = Constants.DELETE_RESULT_FAILED_EXCEPTION;
        	// log to file
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
        }
        return returnValue;
    }

    /**
	 * Handle farm input data
	 * @param inputData: search result data list
	 */
	private void convertSanitize(ArrayList<Bnn0088SearchFarmResult> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			Bnn0088SearchFarmResult currentData = inputData.get(i);
			// Farm name
			currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
		}
	}
	
	/**
	 * Handle farm input data
	 * @param inputData: search result data list
	 */
	private void convertSanitizeArea(List<Bnn0088SearchAreaResult> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			Bnn0088SearchAreaResult currentData = inputData.get(i);
			// Farm name
			currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
			// Farm name
			currentData.setAreaName(util.convertSanitize(currentData.getAreaName()));
			// Farm name
			currentData.setUsersName(util.convertSanitize(currentData.getUsersName()));			
		}
	}
    
    public List<Bnn0088SearchAreaResult> getDataForAffiliation() {
        List<Bnn0088SearchAreaResult> bnn0088ResultList = new ArrayList<Bnn0088SearchAreaResult>();
        HashMap<String, Object> params = new HashMap<String, Object>();
        try {
    		//remove cache
    		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
    	        // search starts
    	        bnn0088ResultList = bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper().selectDataArea(params);
    	        convertSanitizeArea(bnn0088ResultList);
    	        
    	        logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + "," + LoggerMessage.SQL_EXECUTION_FINISHED);
    	        // "real" total from search result
    	        ArrayList<Bnn0088SearchFarmResult> arrDataFarm = (ArrayList<Bnn0088SearchFarmResult>) bnn0087SearchAffiliationDao
    	                .getBnn0087SearchAffiliationMapper().selectDataFarm();
    	        
    	        convertSanitize(arrDataFarm);
    	        
    	        if (bnn0088ResultList.size() > 0) {
    	            bnn0088ResultList.get(0).setArrDataFarm(arrDataFarm);
    	            bnn0088ResultList.get(0).setHaveData(String.valueOf(bnn0088ResultList.size()));
    	        } else {
    	            Bnn0088SearchAreaResult temp = new Bnn0088SearchAreaResult();
    	            temp.setHaveData("");
    	            bnn0088ResultList.add(temp);
    	            bnn0088ResultList.get(0).setArrDataFarm(arrDataFarm);
    	        }
    			//release transaction
    			txManager.commit(status);
    	        // handle user input data
    	        logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + "," + LoggerMessage.AFFILIATION_SEARCH_FINISHED);
	        } catch (OutOfMemoryError ex) {
	            ex.printStackTrace();
	            Bnn0088SearchAreaResult tempObj = new Bnn0088SearchAreaResult();
	            tempObj.setHaveData("-1");
	            bnn0088ResultList.add(tempObj);
	        	// log to file
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
	            txManager.rollback(status);
	        }
        } catch (Exception ex) {
            ex.printStackTrace();
            bnn0088ResultList = null;
        	// log to file
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
        }
        return bnn0088ResultList;
    }

    public String insertDataForAffiliation(ArrayList<Bnn0087IvbMManager> ivbMManagerDataForInsert) {
        // variable definition
        String returnValue = Constants.DELETE_RESULT_SUCCESSFUL;
        int result = 0;
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
                IvbMManager ivbMManagerDelete = new IvbMManager();
                ivbMManagerDelete.setUsersId(ivbMManagerDataForInsert.get(0).getUsersId());
                ivbMManagerDelete.setAuthorizationTypeId(ivbMManagerDataForInsert.get(0).getTypeIdEdit());
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("userId", ivbMManagerDataForInsert.get(0).getUsersId());
                ArrayList<Bnn0088SearchAreaResult> resultCheckAreaManager = 
            		(ArrayList<Bnn0088SearchAreaResult>) bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper().checkAreaManager(params);
                if (resultCheckAreaManager.size() > 0 && ! isRepeatInsert(ivbMManagerDataForInsert, resultCheckAreaManager)) {
                    returnValue = Constants.INSERT_RESULT_FAILED_MANAGER;
                    txManager.rollback(status);
                    logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                            + LoggerMessage.AFFILIATION_DELETE_ERROR);
                } else {
                	// authorityName
                    String typeId = ivbMManagerDataForInsert.get(0).getTypeIdEdit();
                    if (typeId == null || typeId.equalsIgnoreCase("null")) {
                    	params.put("authorityId", typeId);
                    	Date server = bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper().getLastUpdateDate(params);
                    	if (server != null){
                    		// record has been updated
                        	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                        			+ LoggerMessage.CANT_INSERT_DATA_UPDATE_DATE);
                            returnValue = Constants.INSERT_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE;
							txManager.rollback(status);
                            return returnValue;
                    	} else {
                    		result = 1;
                    	}
                    } else {
                        params.put("authorityId", typeId);
                        params.put("lastUpdateDate", ivbMManagerDataForInsert.get(0).getLastUpdateDate());
                        result = bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper().deleteData(params);
                    }
                    if (result > 0) {
                        returnValue = Constants.INSERT_RESULT_SUCCESSFUL;
                        try {
                        	// register to DB
                        	for (int i = 0; i < ivbMManagerDataForInsert.size(); i++) {
                                IvbMManager ivbMManagerSub = ivbMManagerDataForInsert.get(i);
                                ivbMManagerSub.setCreateUserId(util.getUserInfo().getID());
                                // update user id
                                ivbMManagerSub.setUpdateUserId(util.getUserInfo().getID());
                                result = bnn0087SearchAffiliationDao.getIvbMManagerMapper().insert(ivbMManagerSub);
                                if (result <= 0) { // insert fail
                                    break;
                                }
                            }
                            if (result > 0) { // insert successfully
                                // register to DB
                                txManager.commit(status);
                            } else {
                                returnValue = Constants.INSERT_RESULT_FAILED_CULTIVATION_AFFILIATION;
                                txManager.rollback(status);
                                logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                                        + LoggerMessage.CANT_INSERT_DATA);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        	// log to file
                            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                            returnValue = Constants.INSERT_RESULT_FAILED_EXCEPTION;
                            txManager.rollback(status);
                        }
                    } else {
                    	Bnn0087AffiliationDelete bnn0087AffiliationDelete = new Bnn0087AffiliationDelete();
                    	bnn0087AffiliationDelete.setUserId(ivbMManagerDataForInsert.get(0).getUsersId());
                    	bnn0087AffiliationDelete.setAuthorizationTypeId(ivbMManagerDataForInsert.get(0).getAuthorizationTypeId());
                    	bnn0087AffiliationDelete.setLastUpdateDate(ivbMManagerDataForInsert.get(0).getLastUpdateDate());
            			// check last updated date
            	        if (!checkLastUpdatedDateTime(bnn0087AffiliationDelete)) {
                        	// record has been updated
                        	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                        			+ LoggerMessage.CANT_INSERT_DATA_UPDATE_DATE);
                            returnValue = Constants.INSERT_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE;
							txManager.rollback(status);
                            return returnValue;
                        } else {
	                        returnValue = Constants.INSERT_RESULT_FAILED_CULTIVATION_AFFILIATION;
	                        txManager.rollback(status);
	                        logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
	                                + LoggerMessage.AFFILIATION_INSERT_DATA_ERROR);
                        }
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

    public void setAppContext(ApplicationContext appContext) {
        this.appContext = appContext;
    }
    
    public boolean isRepeatInsert(ArrayList<Bnn0087IvbMManager> ivbMManagerDataForInsert, ArrayList<Bnn0088SearchAreaResult> resultCheckAreaManager ){
        int isRepeatInsertCount = 0;
        for (int i = 0; i < ivbMManagerDataForInsert.size(); i++) {
            IvbMManager ivbMManagerSub = ivbMManagerDataForInsert.get(i);
            for (int j = 0; j < resultCheckAreaManager.size(); j++) {
                Bnn0088SearchAreaResult resultCheckAreaManagerSub = resultCheckAreaManager.get(j);
                if (ivbMManagerSub.getFarmId().equalsIgnoreCase(resultCheckAreaManagerSub.getFarmId())
                        && ivbMManagerSub.getAreaId().equalsIgnoreCase(resultCheckAreaManagerSub.getAreaId())) {
                    isRepeatInsertCount ++;
                    if (isRepeatInsertCount == resultCheckAreaManager.size()) {
                        return true;
                    }
                } else {
                    // nothing
                }
            }
        }
        return false;
    }

	/**
 	 * Check whether current record has been updated before or not
 	 * 
 	 * @param bnn0087AffiliationDelete
 	 * @return boolean : 2 dates are equal: true/2 dates are not equal: false 
 	 */
	private boolean checkLastUpdatedDateTime(Bnn0087AffiliationDelete bnn0087AffiliationDelete) {
 		HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userId", bnn0087AffiliationDelete.getUserId());
        params.put("authorityId", bnn0087AffiliationDelete.getAuthorizationTypeId());
        params.put("lastUpdateDate", bnn0087AffiliationDelete.getLastUpdateDate());
		// get server last updated date time
		Date server = bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper().getLastUpdateDate(params);
		// compare date
		if (!bnn0087AffiliationDelete.getLastUpdateDate().equals(server)) {
			return false;
		}
		return true;
 	}
}
