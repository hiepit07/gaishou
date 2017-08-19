package vn.bananavietnam.ict.banana.service;

import java.util.ArrayList;
import java.util.Calendar;
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

import vn.bananavietnam.ict.banana.component.Bnn0005SearchFarmConditions;
import vn.bananavietnam.ict.banana.component.Bnn0005SearchFarmDataObject;
import vn.bananavietnam.ict.banana.component.Bnn0005SearchFarmResult;
import vn.bananavietnam.ict.banana.dao.Bnn0005SearchFarmDao;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.model.IvbMFarm;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Hieu Dao
 */

@Service
public class Bnn0005SearchFarmService {

	private static Logger logger = Logger.getLogger(Bnn0005SearchFarmService.class);
	
	// Variables definition
	private Util util = new Util();
	
	@Autowired
	private Bnn0005SearchFarmDao bnn0005SearchFarmDao;
	
	@Autowired
	private UtilDao utilDao;

	// Send array list data to client side
	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private ApplicationContext appContext;

	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	/**
	 * Create data for farm combo box on loading
	 * @param model
	 */
	public void initData(Model model) {
		 
		// get data for combo box
        List<UtilComponent> farmData = new ArrayList<UtilComponent>();
        try {
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_FARM_COMBOBOX);
        	//remove cache
    		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            
        	farmData = util.getFarmData(utilDao);
            convertSanitizeFarm(farmData);
            //release transaction
    		txManager.commit(status);
            model.addAttribute("farmData", mapper.writeValueAsString(farmData));
        } catch (Exception ex) {
            ex.printStackTrace();
            // log to file
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            model.addAttribute("farmData", "''");
        }
	}

	/**
	 * Search farm in DB based on search conditions received from client
	 * @param searchConditions: data received from client
	 * @return List of farm data
	 */
	public List<Bnn0005SearchFarmResult> searchData(Bnn0005SearchFarmConditions searchConditions) {

		List<Bnn0005SearchFarmResult> bnn0005ResultList = new ArrayList<Bnn0005SearchFarmResult>();
		HashMap<String, Object> params = createSearchConditionParams(searchConditions);
		try{
			//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
			try {
				logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	        			+ LoggerMessage.FARM_SEARCH_STARTED);
	
				// search starts
				bnn0005ResultList = bnn0005SearchFarmDao.getBnn0005SearchFarmMapper().searchData(params);
				logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	            		+ LoggerMessage.SQL_EXECUTION_FINISHED);
				if (bnn0005ResultList.size() > 0) {
	
					// "real" total from search result
					String searchDataTotalCounts = bnn0005SearchFarmDao.getBnn0005SearchFarmMapper().getSearchDataTotalCounts(params);
					bnn0005ResultList.get(0).setSearchDataTotalCounts(searchDataTotalCounts);
	
					// handle farm input data
					convertSanitize(bnn0005ResultList);
					logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                		+ LoggerMessage.FARM_SEARCH_RESULT_STRING.replace("$1", searchDataTotalCounts));
	            } else {
	                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                		+ LoggerMessage.FARM_SEARCH_RESULT_0_STRING);
	            }
				txManager.commit(status);
			} catch (OutOfMemoryError ex) {
				ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
				Bnn0005SearchFarmResult tempObj = new Bnn0005SearchFarmResult();
				tempObj.setSearchDataTotalCounts("-1");
				bnn0005ResultList.add(tempObj);
				txManager.rollback(status);
			} catch (Exception ex) {
				ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
				bnn0005ResultList = null;
				txManager.rollback(status);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            bnn0005ResultList = null;
		}

		return bnn0005ResultList;
	}

	/**
	 * Get farm information based on farm's name and user's name
	 * @param searchConditions: data received from client
	 * @return Farm data
	 */
	public Bnn0005SearchFarmDataObject getSingleData(String farmId) {
		Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
		try{
			//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        
			bnn0005Object = bnn0005SearchFarmDao.getBnn0005SearchFarmMapper().getSingleData(farmId);
			convertSanitize(bnn0005Object);
			
			//release transaction
			txManager.commit(status);
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            bnn0005Object = null;
		}
		
		return bnn0005Object;
	}

	/**
	 * Insert farm's information to DB
	 * @param farmData: data received from client
	 * @return String : insert successfully: 1/insert failed: -1
	 */
	public String insertData(Bnn0005SearchFarmDataObject farmData) {

		// variable definition
		String returnValue = Constants.INSERT_RESULT_SUCCESSFUL;
		String farmId = "";
		int result = 0;
		
		// insert starts
		try {
			// check for user input
			if (!checkInputBlankFields(farmData)) {
				// blank field(s)
				logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.BLANK_FIELDS_ERROR_MESSAGE);
				returnValue = Constants.VALIDATE_BLANK_FIELDS;
				return returnValue;
			} else {
				// transaction starts
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
				PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
				TransactionStatus status = txManager.getTransaction(def);
				try {					
					while(result <= 0) {
						// create HashMap to insert into params.
	                	HashMap<String, Object> paramLastFarmId = new HashMap<String, Object>();
	                	paramLastFarmId.put("farmDefault", Constants.DEFAULT_AREA);
						String idNumberStr = bnn0005SearchFarmDao.getBnn0005SearchFarmMapper()
	                            .getLastFarmId(paramLastFarmId);
	                	int idNumber = 0;
	                	if (idNumberStr != null) {
	                		idNumber = Integer.parseInt(idNumberStr.substring(1));
	                	}
						if(idNumber < Constants.MAX_FARM_ID){
							idNumber = idNumber + 1;
			
							farmId = Constants.BEGIN_FARM_ID + String.format("%0" + Constants.MAX_LENGHT_ID + "d", idNumber);
			
							// insert farm data
							IvbMFarm farmObj = new IvbMFarm();
							// farm id
							farmObj.setFarmId(farmId);
							// farm name
							farmObj.setFarmName(farmData.getFarmName());
							// line
							farmObj.setNumberOfLines(farmData.getNumberOfLines());
							// column
							farmObj.setNumberOfColumns(farmData.getNumberOfColumns());
							// open date
							farmObj.setOpenDate(farmData.getOpenDate());
							// time from
							farmObj.setTimeFrom(farmData.getTimeFrom());
							// time to
							farmObj.setTimeTo(farmData.getTimeTo());
							// address
							farmObj.setFarmLocation(farmData.getFarmLocation());
							// climate
							farmObj.setClimate(farmData.getClimate());
							// soil
							farmObj.setSoil(farmData.getSoil());
							// size of plan
							farmObj.setSizeOfPlan(farmData.getSizeOfPlan());
							// email
							farmObj.setEmailAddress(farmData.getEmailAddress());
							// phone
							farmObj.setPhone(farmData.getPhone());
							// fax
							farmObj.setFax(farmData.getFax());
							// note
							farmObj.setNote(farmData.getNote());
							// create user id
							farmObj.setCreateUserId(util.getUserInfo().getID());
							// create date
							farmObj.setCreateDate(Calendar.getInstance().getTime());
							// update user id
							farmObj.setUpdateUserId(util.getUserInfo().getID());
							// last update date
							farmObj.setLastUpdateDate(Calendar.getInstance().getTime());
							// delete flag
							farmObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);
			
							result = bnn0005SearchFarmDao.getIvbMFarmMapper().insert(farmObj);
						} else {
							break;
						}
					}
					if (result > 0) { // insert area successfully
						// register to DB
						txManager.commit(status);
					} else {
						returnValue = Constants.INSERT_RESULT_FAILED_CULTIVATION_FARM;
						txManager.rollback(status);
						logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
	                            + LoggerMessage.CANT_INSERT_DATA);
					}
				} catch (DuplicateKeyException ex) {
	                ex.printStackTrace();
	                // log to file
	        		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.ERROR_DUPLICATE_FARM_NAME, ex);
	        		returnValue = Constants.INSERT_RESULT_DUPLICATED;
	                txManager.rollback(status);
				} catch (Exception ex) {
					ex.printStackTrace();
		            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
					returnValue = Constants.INSERT_RESULT_FAILED_EXCEPTION;
					txManager.rollback(status);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
			returnValue = Constants.INSERT_RESULT_FAILED_EXCEPTION;
		}
		return returnValue;
	}

	/**
	 * Update farm's information to DB
	 * @param farmData: data received from client
	 * @return String : update successfully: 1/update failed: -1
	 */
	public String updateData(Bnn0005SearchFarmDataObject farmData) {

		// variable definition
		String returnValue = Constants.UPDATE_RESULT_SUCCESSFUL;
		int result = 0;

		// update starts
		try {
			// check for user input
			if (!checkInputBlankFields(farmData)) {
				// blank field(s)
				logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.BLANK_FIELDS_ERROR_MESSAGE);
				returnValue = Constants.VALIDATE_BLANK_FIELDS;
				return returnValue;
			}
			// transaction strats
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
			TransactionStatus status = txManager.getTransaction(def);
			try {
				HashMap<String, Object> params = new HashMap<String, Object>();
				
				// farm id
				params.put("farmId", farmData.getFarmId());
				// farm name
				params.put("farmName", farmData.getFarmName());
				// line
				params.put("numberOfLines", farmData.getNumberOfLines());
				// column
				params.put("numberOfColumns", farmData.getNumberOfColumns());
				// open date
				params.put("openDate", farmData.getOpenDate());
				// time from
				params.put("timeFrom", farmData.getTimeFrom());
				// time to
				params.put("timeTo", farmData.getTimeTo());
				// address
				params.put("farmLocation", farmData.getFarmLocation());
				// climate
				params.put("climate", farmData.getClimate());
				// soil
				params.put("soil", farmData.getSoil());
				// size of plan
				params.put("sizeOfPlan", farmData.getSizeOfPlan());
				// email
				params.put("emailAddress", farmData.getEmailAddress());
				// phone
				params.put("phone", farmData.getPhone());
				// fax
				params.put("fax", farmData.getFax());
				// note
				params.put("note", farmData.getNote());
				// update user id
				params.put("updateUserId", util.getUserInfo().getID());
				// delete flag
				params.put("deleteFlag", farmData.getDeleteFlag());

				params.put("lastUpdateDate", farmData.getLastUpdateDate());

				result = bnn0005SearchFarmDao.getBnn0005SearchFarmMapper().updateData(params);
				if (result > 0) { // update farm successfully
					// update to DB
					txManager.commit(status);
				} else {
					 // check last updated date
                    if (!checkLastUpdatedDateTime(farmData)) {
                    	// record has been updated
                    	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.CANT_UPDATE_DATA_UPDATE_DATE);
                        returnValue = Constants.UPDATE_RESULT_FAILED_UPDATE_DATE;
                        txManager.rollback(status);
                        return returnValue;
                    } else {                	
	                    returnValue = Constants.UPDATE_RESULT_FAILED_CULTIVATION_FARM;	                    
	                    logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
	                            + LoggerMessage.CANT_UPDATE_DATA);
	                    txManager.rollback(status);
	                    return returnValue;
                    }
				}
			} catch (DuplicateKeyException ex) {
                ex.printStackTrace();
                // log to file
        		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.ERROR_DUPLICATE_FARM_NAME, ex);
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
	 * Delete farm from DB
	 * @param farmId : farm's id to delete
	 * @return String : delete successfully: 1/delete failed: -1
	 */
	public String deleteData(String farmId, Date lastUpdateDate) {
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
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("result", "");
				params.put("farmId", farmId);
				params.put("lastUpdateDate", lastUpdateDate);
				// CALL STORE PROCEDURE "DELETE_FARM_AREA_BLOCK_PRODUCT"
				bnn0005SearchFarmDao.getBnn0005SearchFarmMapper().deleteData(params);
				// get insert result
				String deleteResult = (String) params.get("result");
				// check result
				if (deleteResult == null || deleteResult.equals("") || deleteResult.equals(Constants.DELETE_RESULT_FAILED)) {
					// error occurred
					logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                            + LoggerMessage.BLOCK_DELETE_FAILED);
					returnValue = Constants.DELETE_RESULT_FAILED_CULTIVATION_FARM;
					txManager.rollback(status);
				} else if (deleteResult.equals(Constants.DELETE_RESULT_FAILED_UPDATE_DATE)) {
					// check last updated date
					// record has been updated
                	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                			+ LoggerMessage.CANT_DELETE_DATA_UPDATE_DATE);
                    returnValue = Constants.DELETE_RESULT_FAILED_UPDATE_DATE;
                    txManager.rollback(status);
				} else if (deleteResult.equals(Constants.DELETE_RESULT_SUCCESSFUL)) {
					// successful
					logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                            + LoggerMessage.BLOCK_DELETE_FINISHED);
					returnValue = Constants.DELETE_RESULT_SUCCESSFUL;
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
	 * Get data for Farm combobox
	 * 
	 * @return Farm data
	 */
	public List<UtilComponent> getFarmName() {
		//remove cache
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
        TransactionStatus status = txManager.getTransaction(def);
		List<UtilComponent> ret = convertSanitizeFarmName(util.getFarmData(utilDao));
		//release transaction
		txManager.commit(status);
		return ret;
	}
	
	/**
	 * Create search conditions parameters from data received from client
	 * @param searchConditions: data received from client
	 * @return HashMap object
	 */
	private HashMap<String, Object> createSearchConditionParams(Bnn0005SearchFarmConditions searchConditions) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		// Farm Id
		params.put("farmId",searchConditions.getFarmId() == null ? "" : searchConditions.getFarmId());
		// From parameter
		params.put("fromRow", Integer.valueOf(searchConditions.getFromRow()));
		// Number of items in a page
		params.put("itemCount", Integer.valueOf(searchConditions.getItemCount()));
		return params;
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
		}
	}
	
	/**
	 * Handle farm input data
	 * @param inputData: search result data list
	 */
	private List<UtilComponent> convertSanitizeFarmName(List<UtilComponent> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			UtilComponent currentData = inputData.get(i);
			// Farm name
			currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
		}
		return inputData;
	}

	/**
	 * Handle farm input data
	 * @param inputData: search result data list
	 */
	private void convertSanitize(List<Bnn0005SearchFarmResult> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			Bnn0005SearchFarmResult currentData = inputData.get(i);
			// Farm name
			currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
			// User name
			currentData.setUsersName(util.convertSanitize(currentData.getUsersName()));
			// Address
			currentData.setAddress(util.convertSanitize(currentData.getAddress()));
			// Climate
			currentData.setClimate(util.convertSanitize(currentData.getClimate()));
			// Soil
			currentData.setSoil(util.convertSanitize(currentData.getSoil()));
			// Note
			currentData.setNote(util.convertSanitize(currentData.getNote()));
		}
	}

	/**
	 * Handle farm input data
	 * @param inputData: get signal result data object
	 */
	private void convertSanitize(Bnn0005SearchFarmDataObject inputData) {
		Bnn0005SearchFarmDataObject currentData = inputData;
		// Farm name
		currentData.setFarmName(util.convertUnsanitize(currentData.getFarmName()));
		// User name
		currentData.setUsersName(util.convertUnsanitize(currentData.getUsersName()));
		// Address
		currentData.setFarmLocation(util.convertUnsanitize(currentData.getFarmLocation()));
		// Climate
		currentData.setClimate(util.convertUnsanitize(currentData.getClimate()));
		// Soil
		currentData.setSoil(util.convertUnsanitize(currentData.getSoil()));
		// Note
		currentData.setNote(util.convertUnsanitize(currentData.getNote()));
	}

	/**
	 * Check input: blank fields
	 * @param farmData : data received from client
	 * @param managerData : data received from client
	 * @return boolean : all fields have value: true/blank fields exist: false
	 */
	private boolean checkInputBlankFields(Bnn0005SearchFarmDataObject farmData) {
		if (farmData.getFarmName().equalsIgnoreCase("") || farmData.getFarmLocation().equalsIgnoreCase("") ||
			farmData.getClimate().equalsIgnoreCase("") || farmData.getSoil().equalsIgnoreCase("")) {
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
 	private boolean checkLastUpdatedDateTime(Bnn0005SearchFarmDataObject bnn0005SearchFarmDataObject) {
 		// get client last updated date time
 		Date lastUpdatedDateTimeClient = bnn0005SearchFarmDataObject.getLastUpdateDate();
 		// get server last updated date time
 		Bnn0005SearchFarmDataObject serverData = getSingleData(bnn0005SearchFarmDataObject.getFarmId());
 		Date lastUpdatedDateTimeServer = serverData.getLastUpdateDate();
 		// compare date
 		if (!lastUpdatedDateTimeClient.equals(lastUpdatedDateTimeServer)) {
 			return false;
 		}
 		return true;
 	}
}