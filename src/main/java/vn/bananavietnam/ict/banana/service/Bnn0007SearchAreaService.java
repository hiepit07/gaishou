package vn.bananavietnam.ict.banana.service;

import java.io.IOException;
import java.text.ParseException;
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

import vn.bananavietnam.ict.banana.component.Bnn0007BlockListDataObject;
import vn.bananavietnam.ict.banana.component.Bnn0007InserDataConditions;
import vn.bananavietnam.ict.banana.component.Bnn0007SearchAreaConditions;
import vn.bananavietnam.ict.banana.component.Bnn0007SearchAreaFormResult;
import vn.bananavietnam.ict.banana.component.Bnn0007SearchAreaResult;
import vn.bananavietnam.ict.banana.dao.Bnn0007SearchAreaDao;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.model.IvbMArea;
import vn.bananavietnam.ict.common.db.model.IvbMKind;
import vn.bananavietnam.ict.common.db.model.IvbMKindExample;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Nghia Nguyen
 */
@Service
public class Bnn0007SearchAreaService {

	private static Logger logger = Logger.getLogger(Bnn0007SearchAreaService.class);

	private Util util = new Util();
	
	@Autowired
	private Bnn0007SearchAreaDao bnn0007SearchAreaDao;

	@Autowired
	private UtilDao utilDao;

    // Variables definition
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
     * @param model
     */
    public void initData(Model model, String farmId) {
        // Get Farm data for combobox
    	model.addAttribute("farmIdClient", farmId);
    	
        List<UtilComponent> farmData = new ArrayList<UtilComponent>();
        //remove cache
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
        TransactionStatus status = txManager.getTransaction(def);
        
        try {
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_FARM_COMBOBOX);
        	// get All Data Of table IVB_M_FARM
			farmData = util.getFarmData(utilDao);
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.SQL_EXECUTION_FINISHED);
            convertSanitizeFarm(farmData);
            model.addAttribute("farmData", mapper.writeValueAsString(farmData));
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_DATA_COMBOBOX_FINISHED);
        } catch (Exception ex) {
            ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            farmData = new ArrayList<UtilComponent>();
            model.addAttribute("farmData", "''");
        }

        // Get Kind data for combobox
        List<IvbMKind> kindData = new ArrayList<IvbMKind>();
        IvbMKindExample kindExample = new IvbMKindExample();
        try {
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_KIND_COMBOBOX);
        	// set Sort by Kind Name
        	kindExample.setOrderByClause("KIND_NAME");
        	// get All Data Of table IVB_M_KIND
        	kindData = bnn0007SearchAreaDao.getIvbMKindMapper().selectByExample(kindExample);
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.SQL_EXECUTION_FINISHED);
        	// Add ModelAttribute is kindData = List<IvbMKind> kindData
        	convertSanitizeKind(kindData);
            model.addAttribute("kindData", mapper.writeValueAsString(kindData));
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_KIND_COMBOBOX_FINISHED);
        } catch (Exception ex) {
            ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            logger.error("Error message: " + ex.getMessage());
            kindData = new ArrayList<IvbMKind>();
            model.addAttribute("kindData", "''");
        }
        //release transaction
        txManager.commit(status);
    }

    /**
	 * Get data for Area Select Box by farm id 
	 * 
	 * @param farmId : data received from client
	 * @return Area data
	 */
	public List<UtilComponent> getAreaDataByFarmId(String farmId) {
		List<UtilComponent> areaData = new ArrayList<UtilComponent>();
		try {
			logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_AREA_COMBOBOX);
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
			areaData = util.getAreaDataByFarmId(utilDao, farmId);
			convertSanitizeUtilComponent(areaData);
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
     * Search area in DB based on search conditions received from client
     * 
     * @param searchConditions : data received from client
     * @return List of Area data
     */
	public List<Bnn0007SearchAreaResult> searchData(Bnn0007SearchAreaConditions searchConditions) {
		List<Bnn0007SearchAreaResult> bnn0007ResultList = new ArrayList<Bnn0007SearchAreaResult>();
        HashMap<String, Object> params = createSearchConditionParams(searchConditions);
        logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
    			+ LoggerMessage.AREA_SEARCH_STARTED);
    	// search starts
        try {
        	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
                bnn0007ResultList = bnn0007SearchAreaDao.getBnn0007SearchAreaMapper().searchData(params);
            	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.SQL_EXECUTION_FINISHED);
                if (bnn0007ResultList.size() > Constants.LIST_IS_EQUAL_TO_ZERO) {
                    // "real" total from search result
                    String searchDataTotalCounts = bnn0007SearchAreaDao.getBnn0007SearchAreaMapper().getSearchDataTotalCounts(params);
                    bnn0007ResultList.get(0).setSearchDataTotalCounts(searchDataTotalCounts);
                    // handle area input data
                    convertSanitize(bnn0007ResultList);
                    logger.info(LoggerMessage.AREA_SEARCH_RESULT_STRING.replace("$1", searchDataTotalCounts));
                } else {
                    logger.info(LoggerMessage.AREA_SEARCH_RESULT_0_STRING);
                }
                txManager.commit(status);
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
                Bnn0007SearchAreaResult tempObj = new Bnn0007SearchAreaResult();
                tempObj.setSearchDataTotalCounts("-1");
                bnn0007ResultList.add(tempObj);
                txManager.rollback(status);
            } catch (Exception ex) {
                ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                bnn0007ResultList = null;
                txManager.rollback(status);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            bnn0007ResultList = null;
        }
		
        return bnn0007ResultList;
	}

	/**
	 * Update Area's information to DB
     * 
     * @param areaData : data received from client 
	 * 
	 * @return String : update successfully: 1/update failed: -1
	 */
    public String updateData(String areaData1, Date lastUpdateDate) {
		// variable definition
		Bnn0007InserDataConditions areaData = null;
		try {
			// convert String to Object Bnn0007InserDataConditions
			areaData = new ObjectMapper().readValue(areaData1, Bnn0007InserDataConditions.class);
		} catch (IOException e) {
			e.printStackTrace();
        	logger.error(LoggerMessage.LOG_ERROR_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.EXCEPTION_ERROR_MESSAGE + e.getMessage());
			return Constants.UPDATE_RESULT_FAILED_EXCEPTION;
		}
        String returnValue = Constants.UPDATE_RESULT_SUCCESSFUL;
        // set areaId & farmId to check lastUpdateDate.
        Bnn0007SearchAreaConditions bnn0007SearchAreaConditions = new Bnn0007SearchAreaConditions();
        bnn0007SearchAreaConditions.setAreaId(areaData.getAreaId());
        bnn0007SearchAreaConditions.setFarmId(areaData.getFarmId());
        // update starts
        try {
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
    			+ LoggerMessage.AREA_UPDATE_STARTED);
        	// Check Farm Id
        	if (!checkFarmIdFormat(areaData)) {
            	// id is in wrong format
            	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.ERROR_FORMAT_FARM_ID);
                returnValue = Constants.VALIDATE_WRONG_FORMAT;
                return returnValue;
            }
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
                HashMap<String, Object> params = new HashMap<String, Object>();
                // Farm Id
                params.put("farmId", areaData.getFarmId());
                // Area id
                params.put("areaId", areaData.getAreaId());
                // Area name
                params.put("areaName", areaData.getAreaName());
                // Area Manager 
                params.put("areaManager", areaData.getAreaManager());
                // Kind Id
                params.put("kindId", areaData.getKindId());
                // Prospective Harvest Amount
                params.put("prospectiveHarvestAmount", areaData.getProspectiveHarvestAmount());
                // Estimated Days Flowering
                params.put("estimatedDaysFlowering", areaData.getEstimatedDaysFlowering());
                // Estimated Days Bagging
                params.put("estimatedDaysBagging", areaData.getEstimatedDaysBagging());
                // Estimated Days Harvest
                params.put("estimatedDaysHarvest", areaData.getEstimatedDaysHarvest());
                // Sugar Content
                params.put("sugarContent", areaData.getSugarContent());
                // Texture
                params.put("texture", areaData.getTexture());
                // note
                params.put("note", areaData.getNote());
                // update user id
                params.put("updateUserId", util.getUserInfo().getID());
                // last Update Date
                params.put("lastUpdateDate", areaData.getLastUpdateDate());

                int result = bnn0007SearchAreaDao.getBnn0007SearchAreaMapper().updateData(params);
                if (result <= Constants.RESULT_IS_EQUAL_TO_ZERO) {
                	// check last updated date
                    if (!checkLastUpdatedDateTime(bnn0007SearchAreaConditions, lastUpdateDate)) {
                    	// record has been updated
                    	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.CANT_UPDATE_DATA_UPDATE_DATE);
                        returnValue = Constants.UPDATE_RESULT_FAILED_UPDATE_DATE;
                        txManager.rollback(status);
                        return returnValue;
                    } else {
            			logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    			+ LoggerMessage.AREA_UPDATE_FAILED);
                        returnValue = Constants.UPDATE_RESULT_FAILED_CULTIVATION_AREA;
                        txManager.rollback(status);
                    }
                } else {
                	// successful
                	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                			+ LoggerMessage.AREA_UPDATE_FINISHED);
                	// register to DB
                    txManager.commit(status);
                }
            } catch (DuplicateKeyException ex) {
                ex.printStackTrace();
                // log to file
        		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.ERROR_DUPLICATE_AREA_NAME, ex);
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
	 * Insert new Area's information to DB
     * 
     * @param areaData : data received from client 
	 * @param ManagerData : data received from client 
	 * 
	 * @return String : Insert successfully: 1/update failed: -1
	 */
    public String insertData(String areaData1,  String managerData1) {
    	// variable definition
		Bnn0007InserDataConditions areaData = null;
		// convert String to Object Bnn0007InserDataConditions
		try {
			areaData = new ObjectMapper().readValue(areaData1, Bnn0007InserDataConditions.class);
		} catch (IOException e) {
			e.printStackTrace();
        	logger.error(LoggerMessage.LOG_ERROR_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.EXCEPTION_ERROR_MESSAGE + e.getMessage());
			return Constants.INSERT_RESULT_FAILED_EXCEPTION;
		}
		
		int result = 0;
		String areaId = "";
    	// Update Data 2 Table IVB_M_AREA & IVB_M_MANAGER
        // variable definition
        String returnValue = Constants.INSERT_RESULT_SUCCESSFUL;
        // update starts
        try {
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.AREA_INSERT_STARTED);
        	// check for user input
			if (!checkInputBlankFields(areaData)) {
				// blank field(s)
				logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.BLANK_FIELDS_ERROR_MESSAGE);
				returnValue = Constants.VALIDATE_BLANK_FIELDS;
				return returnValue;
			}
        	// Check Farm Id
        	if (!checkFarmIdFormat(areaData)) {
            	// id is in wrong format
            	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.ERROR_FORMAT_FARM_ID);
                returnValue = Constants.VALIDATE_WRONG_FORMAT;
                return returnValue;
            }
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
            	while(result <= Constants.RESULT_IS_EQUAL_TO_ZERO) {
            		// create HashMap to insert into params.
                	HashMap<String, Object> paramLastAreaId = new HashMap<String, Object>();
                    // farm Id
                    paramLastAreaId.put("farmId", areaData.getFarmId());
                    paramLastAreaId.put("areaDefault", Constants.DEFAULT_AREA);
                	// create Number of Id
                    String idNumberDefault = bnn0007SearchAreaDao.getBnn0007SearchAreaMapper().getLastAreaId(paramLastAreaId);
                	int idNumber = 0;
                	if (idNumberDefault != null) {
                		idNumber = Integer.parseInt(idNumberDefault.substring(1));
                	}
                	
                	// increase idNumber 1 unit.
                	if(idNumber < Constants.MAX_AREA_ID){
                		idNumber = idNumber + 1;
                        
                    	// set Area Id
        				areaId = Constants.BEGIN_AREA_ID + String.format("%0" + Constants.MAX_LENGHT_ID + "d", idNumber);

                        IvbMArea areaObj = new IvbMArea();
                        // Area id
                        areaObj.setAreaId(areaId);
                        // Area name
                        areaObj.setAreaName(areaData.getAreaName());
                        // Area Manager 
                        areaObj.setAreaManager(areaData.getAreaManager());
                        // Kind Id
                        areaObj.setKindId(areaData.getKindId());
                        // Sugar Content
                        areaObj.setSugarContent(areaData.getSugarContent());
                        // Texture
                        areaObj.setTexture(areaData.getTexture());
                        // Prospective Harvest Amount
                        areaObj.setProspectiveHarvestAmount(areaData.getProspectiveHarvestAmount());
                        // Estimated Days Flowering
                        areaObj.setEstimatedDaysFlowering(areaData.getEstimatedDaysFlowering());
                        // Estimated Days Bagging(
                        areaObj.setEstimatedDaysBagging(areaData.getEstimatedDaysBagging());
                        // Estimated Days Harvest
                        areaObj.setEstimatedDaysHarvest(areaData.getEstimatedDaysHarvest());
                        // Farm Id
                        areaObj.setFarmId(areaData.getFarmId());
                        // note
                        areaObj.setNote(areaData.getNote());
                        // create user id
                        areaObj.setCreateUserId(util.getUserInfo().getID());
                        // update user id
                        areaObj.setUpdateUserId(util.getUserInfo().getID());
                        // delete flag
                        areaObj.setDeleteFlag(areaData.getDeleteFlag());
                        
                        result = bnn0007SearchAreaDao.getIvbMAreaMapper().insert(areaObj);
                	} else {
                		break;
                	}
            	} 
                if (result > Constants.RESULT_IS_EQUAL_TO_ZERO) { // insert successfully
                	
						// Insert Data into IVB_M_BLOCK & IVB_T_PRODUCT 
						// by Farm id & Area Id & Number Of Block & Result
						try {
							// Create Map<String, Object> 
			            	Map<String, Object> params = new HashMap<String, Object>();
			                params.put("result", "");
			                params.put("farmId", areaData.getFarmId());
			                params.put("areaId", areaId);
			                params.put("usersId", util.getUserInfo().getID());
			                params.put("numberOfBlock", areaData.getNumberOfBlock());
			                params.put("cultivationStartDate", Constants.DEFAULT_CULTIVATION_START_DATE);
			                // call Store Procedure "CREATE_BLOCK_FROM_NUMBER_OF_BLOCK"
			                // to insert DB 
			                bnn0007SearchAreaDao.getBnn0007SearchAreaMapper().insertData(params);
			                // get insert result
			                String insertResult = (String) params.get("result");
			                // check result
			                if (insertResult.equals(Constants.INSERT_RESULT_SUCCESSFUL)) {
			                	// successful
                            	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                            			+ LoggerMessage.BLOCK_INSERT_FINISHED);
			                	// register to DB
			                    txManager.commit(status);
			                }
			            } catch (Exception ex) {
			                ex.printStackTrace();
				            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
			                returnValue = Constants.INSERT_RESULT_FAILED;
			                txManager.rollback(status);
			            }
                } else {
                	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                			+ LoggerMessage.AREA_INSERT_FAILED);
                    returnValue = Constants.INSERT_RESULT_FAILED_AREA;
                    txManager.rollback(status);
                }
            } catch (DuplicateKeyException ex) {
                ex.printStackTrace();
                // log to file
        		Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.ERROR_DUPLICATE_AREA_NAME, ex);
        		returnValue = Constants.INSERT_RESULT_DUPLICATED;
                txManager.rollback(status);
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
     * Update DELETE_FLAG = 1  of data's table IVB_M_AREA, IVB_M_MANAGER, IVB_M_BLOCK, IVB_M_PRODUCT
     * 
     * @param areaId : data received from client 
     * @param farmId : data received from client
     * 
     * @return String : delete successfully: 1/delete failed: -1
     */
    public String deleteData(String areaId, String farmId, String usersId, Date lastUpdateDate) {
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
				IvbMArea areaObj = new IvbMArea();
				// Set Data for Area id
				areaObj.setAreaId(areaId);
				// Set Data for Farm Id
				areaObj.setFarmId(farmId);
				areaObj.setDeleteFlag(Constants.DELETE_FLAG_ON);
				// if result > 0 then update Delete Flag = 1 of data in
				// IVB_M_BLOCK & IVB_T_PRODUCT
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("result", "");
				params.put("farmId", farmId);
				params.put("areaId", areaId);
				params.put("lastUpdateDate", lastUpdateDate);
				// CALL STORE PROCEDURE "CREATE_DATA_BLOCK_LINE_BANANA"
				bnn0007SearchAreaDao.getBnn0007SearchAreaMapper().deleteData(params);
				// get insert result
				String deleteResult = (String) params.get("result");
				// check result
				if (deleteResult == null || deleteResult.equals("") || deleteResult.equals(Constants.DELETE_RESULT_FAILED_CULTIVATION_AREA)) {
                    // error occurred
                    logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                    		+ LoggerMessage.BLOCK_DELETE_FAILED);
    				returnValue = Constants.DELETE_RESULT_FAILED_CULTIVATION_AREA; 
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
     * Create search conditions parameters from data received from client
     * 
     * @param searchConditions : data received from client
     * @return HashMap object
     */
    private HashMap<String, Object> createSearchConditionParams(Bnn0007SearchAreaConditions searchConditions) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        // Area id
        params.put("areaId", searchConditions.getAreaId().equals(Constants.SEARCH_CONDITION_NO_SELECT) ? Constants.SEARCH_CONDITION_NO_SELECT : searchConditions.getAreaId() );
        // Farm ID
        params.put("farmId", searchConditions.getFarmId().equals(Constants.SEARCH_CONDITION_NO_SELECT) ? Constants.SEARCH_CONDITION_NO_SELECT : searchConditions.getFarmId());
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
	private HashMap<String, Object> createGetSingleConditionParams(Bnn0007SearchAreaConditions searchConditions) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		// Farm Id
		params.put("farmId", searchConditions.getFarmId());
		// Area Id
		params.put("areaId", searchConditions.getAreaId());
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
	private void convertSanitizeKind(List<IvbMKind> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			IvbMKind currentData = inputData.get(i);
			// Farm name
			currentData.setKindName(util.convertSanitize(currentData.getKindName()));
		}
	}

	/**
	 * Get farm information based on farm's name and user's name
	 * @param searchConditions: data received from client
	 * @return Farm data
	 */
	public List<Bnn0007SearchAreaFormResult> getSingleData(Bnn0007SearchAreaConditions searchConditions) {
		
		// variable definition
		List<Bnn0007SearchAreaFormResult> bnn0007ResultList = new ArrayList<Bnn0007SearchAreaFormResult>();
		HashMap<String, Object> params = createGetSingleConditionParams(searchConditions);
		try {
			logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.AREA_SEARCH_STARTED);
			//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        try {
				// select starts
				bnn0007ResultList = bnn0007SearchAreaDao.getBnn0007SearchAreaMapper().searchSingleData(params);
				//release transaction
				txManager.commit(status);
				// if bnn0095ResultList > 0 then responses Logger message
				if (bnn0007ResultList.size() > Constants.LIST_IS_EQUAL_TO_ZERO) {
					// handle Access Authorization input data
					logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
		        			+ LoggerMessage.AREA_SEARCH_FINISHED);
				} else {
					logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
		        			+ LoggerMessage.AREA_SEARCH_FINISHED);
					logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
		        			+ LoggerMessage.AREA_SEARCH_RESULT_0_STRING);
				}
			} catch (OutOfMemoryError ex) {
				ex.printStackTrace();
				logger.error(LoggerMessage.LOG_ERROR_PREFIX + util.getUserInfo().getID() + ","
	        			+ LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE + ex.getMessage());
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
				Bnn0007SearchAreaFormResult tempObj = new Bnn0007SearchAreaFormResult();
				bnn0007ResultList.add(tempObj);
				txManager.rollback(status);
			} catch (Exception ex) {
				ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
				bnn0007ResultList = null;
				txManager.rollback(status);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
			bnn0007ResultList = null;
		}
		
		return bnn0007ResultList;
	}

	/**
     * Handle Area input data
     * 
     * @param inputData : search result data list
     */
    private void convertSanitize(List<Bnn0007SearchAreaResult> inputData) {
        for (int i = 0; i < inputData.size(); i++) {
        	Bnn0007SearchAreaResult currentData = inputData.get(i);
            // Area id
            currentData.setAreaId(util.convertSanitize(currentData.getAreaId()));
            // Farm name
            currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
            // Area Name
            currentData.setAreaName(util.convertSanitize(currentData.getAreaName()));
            // Kind Name
            currentData.setKindName(util.convertSanitize(currentData.getKindName()));
            // Note 
            currentData.setNote(util.convertSanitize(currentData.getNote()));
            // Texture 
            currentData.setTexture(util.convertSanitize(currentData.getTexture()));
            // Sugar Content 
            currentData.setSugarContent(util.convertSanitize(currentData.getSugarContent()));
        }
    }
    
    /**
     * Handle Area input data
     * 
     * @param inputData : search result data list
     */
    private void convertSanitizeUtilComponent(List<UtilComponent> inputData) {
        for (int i = 0; i < inputData.size(); i++) {
        	UtilComponent currentData = inputData.get(i);
            // Farm name
            currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
            // Area Name
            currentData.setAreaName(util.convertSanitize(currentData.getAreaName()));
        }
    }

 	/**
 	 * Check input: Farm's id format (F + xxx)
 	 * 
 	 * @param areaData : data received from client
 	 * @return boolean : correct format: true/wrong format: false
 	 */
 	private boolean checkFarmIdFormat(IvbMArea areaData) {
 		String farmId = areaData.getFarmId();
 		if (!farmId.matches("F\\d{3}")) {
 			return false;
 		} else {
 			return true;
 		}
 	}
 	
 	/**
	 * Create bnn0009Obj data
	 * @param farmId: data received from client
	 * @param areaId: data received from client
	 * @return bnn0009Obj data
	 */
	public Bnn0007BlockListDataObject bnn0009value(String farmId, String areaId) {
		Bnn0007BlockListDataObject bnn0009Obj = new Bnn0007BlockListDataObject();
		// farm id
		bnn0009Obj.setFarmId(farmId);
		// Farm Name
		bnn0009Obj.setAreaId(areaId);
		return bnn0009Obj;
	}

	/**
	 * Check input: blank fields
	 * @param farmData : data received from client
	 * @param managerData : data received from client
	 * @return boolean : all fields have value: true/blank fields exist: false
	 */
	private boolean checkInputBlankFields(Bnn0007InserDataConditions areaData) {
		if (areaData.getAreaName().equals("") || areaData.getTexture().equals("")) {
			return false;
		} else {
			return true;
		}
	}

 	/**
 	 * Check whether current record has been updated before or not
 	 * 
 	 * @param areaData
 	 * @return boolean : 2 dates are equal: true/2 dates are not equal: false 
 	 * @throws ParseException 
 	 */
 	private boolean checkLastUpdatedDateTime(Bnn0007SearchAreaConditions searchConditions, Date lastUpdateData) throws ParseException {
 		// get client last updated date time
		Date lastUpdatedDateTimeClient = lastUpdateData;
 		// get server last updated date time
 		List<Bnn0007SearchAreaFormResult> serverData = getSingleData(searchConditions);
 		if (serverData.size() == 0) {
 			return false;
 		} else {
 			Date lastUpdatedDateTimeServer = serverData.get(0).getLastUpdateDate();
 	 		// compare date
 	 		if (!lastUpdatedDateTimeClient.equals(lastUpdatedDateTimeServer)) {
 	 			return false;
 	 		}
 	 		return true;
 		}
 	}
    
	public IvbMKind getSingleDataKind(String kindId) {
	    IvbMKind ret = new IvbMKind();
	    try {
	      //remove cache
	        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        try {
		        //main process
		         ret = bnn0007SearchAreaDao.getIvbMKindMapper().selectByPrimaryKey(kindId);
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
}
