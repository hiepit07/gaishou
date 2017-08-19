package vn.bananavietnam.ict.banana.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
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

import vn.bananavietnam.ict.banana.component.Bnn0075SearchShippingScreenConditions;
import vn.bananavietnam.ict.banana.component.Bnn0075SearchShippingScreenResult;
import vn.bananavietnam.ict.banana.dao.Bnn0075shippingScreenDao;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.component.FilePath;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.model.IvbTShippingControl;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Dung Trinh
 */
@Service
public class Bnn0075ShippingScreenService {
	private static Logger logger = Logger.getLogger(Bnn0075ShippingScreenService.class);
	
	private Util util = new Util();
	
	@Autowired
	private Bnn0075shippingScreenDao Bnn0075shippingScreenDao;
	
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
    public void initData(Model model) {
        // Get Farm data for combobox
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
            farmData = new ArrayList<UtilComponent>();
            model.addAttribute("farmData", "''");
        }  
    }
    
    /**
     * Search shipping in DB based on search conditions received from client
     * 
     * @param searchConditions : data received from client
     * @return List of shipping data
     */
	public List<Bnn0075SearchShippingScreenResult> searchData(Bnn0075SearchShippingScreenConditions searchConditions) {
		List<Bnn0075SearchShippingScreenResult> bnn0075ResultList = new ArrayList<Bnn0075SearchShippingScreenResult>();
        HashMap<String, Object> params = createSearchConditionParams(searchConditions);
        try {
        	//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        
	        try {
	        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                    + LoggerMessage.SHIPPING_SEARCH_STARTED);
	            // search starts
	            bnn0075ResultList = Bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper().searchData(params);
	            logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + "," + LoggerMessage.SQL_EXECUTION_FINISHED);
	            if (bnn0075ResultList.size() > Constants.RESULT_IS_EQUAL_TO_ZERO) {
	                // "real" total from search result
	                String searchDataTotalCounts = "";
	                searchDataTotalCounts = Bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper().getSearchDataTotalCounts(params);
	                bnn0075ResultList.get(Constants.RESULT_IS_EQUAL_TO_ZERO).setSearchDataTotalCounts(searchDataTotalCounts);
	                // handle area input data
	                convertSanitize(bnn0075ResultList);
	                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                		+ LoggerMessage.SHIPPING_SEARCH_RESULT_STRING.replace("$1", searchDataTotalCounts));
	            } else {
	            	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                		+ LoggerMessage.SHIPPING_SEARCH_RESULT_0_STRING);
	            }
	            txManager.commit(status);
	        } catch (OutOfMemoryError ex) {
	            ex.printStackTrace();
	        	// log to file
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
	            Bnn0075SearchShippingScreenResult tempObj = new Bnn0075SearchShippingScreenResult();
	            tempObj.setSearchDataTotalCounts("-1");
	            bnn0075ResultList.add(tempObj);
	            txManager.rollback(status);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        	// log to file
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            bnn0075ResultList = null;
	            txManager.rollback(status);
	        }
        } catch (Exception ex) {
            ex.printStackTrace();
        	// log to file
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            bnn0075ResultList = null;
        }
        return bnn0075ResultList;
	}
	
    /**
     * Insert shipping information to DB
     * 
     * @param shippingControl : data received from client
     * @return String : insert successfully: 1/insert failed: -1
     */
    public String insertData(Bnn0075SearchShippingScreenResult shippingControl) {
        // variable definition
        String returnValue = Constants.INSERT_RESULT_SUCCESSFUL;
        // shipping number
        String shippingNumber = "";
        int result = 0;
        // update starts
        try {
        	// check for user input
			if (!checkInputBlankFields(shippingControl)) {
				// blank field(s)
				logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.BLANK_FIELDS_ERROR_MESSAGE);
				returnValue = Constants.VALIDATE_BLANK_FIELDS;
				return returnValue;
			} else {
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	            TransactionStatus status = txManager.getTransaction(def);
	            try {
					String strShippingNumber = "";
					int intShippingNumber = 0;
					
		            String filePath = ((FilePath)appContext.getBean("last_shipping_number_path")).getLastShippingNumberPath();
					File file = new File(filePath);
					if (!file.exists()) {
						file.createNewFile();
					}
					RandomAccessFile in = null;
					try {
						in = new RandomAccessFile(file, "rw");
						FileLock lock = in.getChannel().lock();
						try {
							while (in.read() != -1) {
								strShippingNumber += in.readLine();
							}
							try {
								intShippingNumber = Integer.parseInt(strShippingNumber.trim());
							} catch (NumberFormatException ex) {
								String shippingNumberStr = Bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper().getLastShippingNumber();
								if (shippingNumberStr != null) {
									intShippingNumber = Integer.parseInt(shippingNumberStr);
								}
							}
							
							while(result <= 0) {
								try {
									if(intShippingNumber == Constants.MAX_SHIPPING_NUMBER){
										intShippingNumber = 0;
									}
									//last shipping number + 1
									intShippingNumber = intShippingNumber + 1;
									// format shipping number
									shippingNumber = String.format("%0" + Constants.MAX_LENGHT_SHIPNUMBER_ID + "d", intShippingNumber);
									
									IvbTShippingControl shipObj = new IvbTShippingControl();
									//shipping number
									shipObj.setShippingNumber(shippingNumber);
									//farm id
									shipObj.setFarmId(shippingControl.getFarmId());
									//area id
									shipObj.setAreaId(shippingControl.getAreaId());
									//harvest date
									shipObj.setHarvestDate(shippingControl.getHarvestDate()); 
									// shipping date 
									shipObj.setShippingDate(shippingControl.getShippingDate()); 
									// create user id
									shipObj.setCreateUserId(util.getUserInfo().getID());
									// update user id
									shipObj.setUpdateUserId(util.getUserInfo().getID());
									// delete flag
									shipObj.setDeleteFlag(shippingControl.getDeleteFlag());
									/******** NGHIA STRAT 2017/05/18 ********/
									// create hashMap to select data 
									HashMap<String, Object> params = new HashMap<String, Object>();
									// farmId
									params.put("farmId", shippingControl.getFarmId());
									// areaId
									params.put("areaId", shippingControl.getAreaId());
									 
									String DATE_FORMAT_NOW = "yyyy-MM-dd";
									SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW); 
									String harvestDateStr = sdf.format(shippingControl.getHarvestDate());
									// harvest date
									params.put("harvestDate", harvestDateStr);
									// create List Shipping Control
									List<Bnn0075SearchShippingScreenResult> listIvbShippingControl = new ArrayList<Bnn0075SearchShippingScreenResult>();
									listIvbShippingControl = Bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper().getLastUpdateDate(params);
									if (listIvbShippingControl.size() != Constants.RESULT_IS_EQUAL_TO_ZERO) {
										returnValue = Constants.INSERT_RESULT_DUPLICATED; 
										logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
												+ LoggerMessage.CANT_INSERT_DATA);
										lock.release();
										in.close();
										return returnValue;
									} else {
										/******** NGHIA END 2017/05/18 ********/
										result = Bnn0075shippingScreenDao.getIvbTShippingControlMapper().insert(shipObj);
										if (result > Constants.RESULT_IS_EQUAL_TO_ZERO) { // insert successfully IvbTShippingControl table
											result = 0;
											
											// create hashMap to select data
											HashMap<String, Object> paramsProduct = new HashMap<String, Object>();
											// farmId
											paramsProduct.put("farmId", shippingControl.getFarmId());
											// areaId
											paramsProduct.put("areaId", shippingControl.getAreaId());
											// harvestDate
											paramsProduct.put("harvestDate", shippingControl.getHarvestDate());
											// lastUpdateDate
											paramsProduct.put("lastUpdateDate", shippingControl.getLastUpdateDateProduct());
											// shippingDate
											paramsProduct.put("shippingDate", shippingControl.getShippingDate());
											// shippingDate
											paramsProduct.put("updateUserId", util.getUserInfo().getID());
											
											//return result
											result = Bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper().updateProductData(paramsProduct);
											if (result > Constants.RESULT_IS_EQUAL_TO_ZERO) { //update successfully IvbTProduct table
												returnValue = returnValue +","+ shippingNumber;
												// register to DB
												txManager.commit(status);
												in.setLength(0);
												in.seek(0);
												in.writeBytes(shippingNumber);
											} else { //update failed IvbTProduct table
												returnValue = Constants.INSERT_RESULT_FAILED_CULTIVATION_SHIPPING;
												txManager.rollback(status);
												logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
														+ LoggerMessage.CANT_INSERT_DATA);
												// not need to try again
												lock.release();
												in.close();
												return returnValue;
											}
										} else {// insert failed IvbTShippingControl table
											returnValue = Constants.INSERT_RESULT_FAILED_CULTIVATION_SHIPPING;
											txManager.rollback(status);
											logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
													+ LoggerMessage.CANT_INSERT_DATA);
										}
									}
								} catch (DuplicateKeyException ex) {
									// loop if duplicate key
									result = 0;
								}	            		
							}
						} finally {
							lock.release();
						}
					} catch (FileNotFoundException ex) {
						// Can't read file
						ex.printStackTrace();
						Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
						returnValue = Constants.WRITE_FILE_EXCEPTION;
					} catch (IOException ex) {
						// Can't read file
						ex.printStackTrace();
						Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
						returnValue = Constants.WRITE_FILE_EXCEPTION;
					} finally {
						try {
							if (in != null) {
								in.close();
							}
						} catch (IOException ex) {
							ex.printStackTrace();
							Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
						}
					}
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            	// log to file
	                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	                returnValue = Constants.INSERT_RESULT_FAILED_EXCEPTION;
	                txManager.rollback(status);
	            }
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
     * update data shipping screen information to DB
     * 
     * @param shippingControl : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
    public String updateData(Bnn0075SearchShippingScreenResult shippingControl) {
        // variable definition
        String returnValue = Constants.UPDATE_RESULT_SUCCESSFUL;
    	//result update
    	int result = Constants.RESULT_IS_EQUAL_TO_ZERO;
        // update starts
        try {
        	// check for user input
			if (!checkInputBlankFields(shippingControl)) {
				// blank field(s)
				logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.BLANK_FIELDS_ERROR_MESSAGE);
				returnValue = Constants.VALIDATE_BLANK_FIELDS;
				return returnValue;
			} else {
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	            TransactionStatus status = txManager.getTransaction(def);
	            try {
	            	HashMap<String, Object> params = new HashMap<String, Object>();
	            	
	            	///Condition update for IvbTShippingControl table
	            	//Shipping Number Update
	            	params.put("shippingNumber", shippingControl.getShippingNumber());
	            	///Data update for IvbTShippingControl table
	            	//shipping date
	            	params.put("shippingDate", shippingControl.getShippingDate());
	                // update user id
	                params.put("updateUserId", util.getUserInfo().getID());
	                
	                // delete flag
	                params.put("deleteFlag", shippingControl.getDeleteFlag());
	                params.put("lastUpdateDate", shippingControl.getLastUpdateDate());
	                result = Bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper().updateData(params);
	                if (result > Constants.RESULT_IS_EQUAL_TO_ZERO) { // update successfully IvbTShippingControl table
	                	// create hashMap to select data
		                HashMap<String, Object> paramsProduct = new HashMap<String, Object>();
		                // farmId
		                paramsProduct.put("farmId", shippingControl.getFarmId());
		                // areaId
		                paramsProduct.put("areaId", shippingControl.getAreaId());
		                // harvestDate
		                paramsProduct.put("harvestDate", shippingControl.getHarvestDate());
		                // lastUpdateDate
		                paramsProduct.put("lastUpdateDate", shippingControl.getLastUpdateDateProduct());
		                // shippingDate
		                paramsProduct.put("shippingDate", shippingControl.getShippingDate());
		                // shippingDate
		                paramsProduct.put("updateUserId", util.getUserInfo().getID());
		                
	                	//return result
	                	result = Bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper().updateProductData(paramsProduct);
	                	if (result > Constants.RESULT_IS_EQUAL_TO_ZERO) { //update successfully IvbTProduct table
	                		// register to DB
	                		txManager.commit(status);
	                	} else { //update failed IvbTProduct table
	                        
	                        if (!checkLastUpdatedDateTime(shippingControl, true)) {
		                    	// record has been updated
		                    	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
		                    			+ LoggerMessage.CANT_UPDATE_DATA_UPDATE_DATE);
		                        returnValue = Constants.UPDATE_RESULT_FAILED_CULTIVATION_SHIPPING_UPDATE_DATE;
		                        txManager.rollback(status);
		                        return returnValue;
		                    } else {                	
			                    returnValue = Constants.UPDATE_RESULT_FAILED_CULTIVATION_SHIPPING;	                    
			                    logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
			                            + LoggerMessage.CANT_UPDATE_DATA);
			                    txManager.rollback(status);
			                    return returnValue;
		                    }
	                	}
	                } else {
	                	// check last updated date
	                    if (!checkLastUpdatedDateTime(shippingControl, false)) {
	                    	// record has been updated
	                    	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
	                    			+ LoggerMessage.CANT_UPDATE_DATA_UPDATE_DATE);
	                        returnValue = Constants.UPDATE_RESULT_FAILED_UPDATE_DATE;
	                        txManager.rollback(status);
	                        return returnValue;
	                    } else {                	
		                    returnValue = Constants.UPDATE_RESULT_FAILED_CULTIVATION_SHIPPING;	                    
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
	 * Get data for Area combobox by farm id 
	 * 
	 * @param farmId : data received from client
	 * @return Area data
	 */
	public List<UtilComponent> getAreaDataByFarmId(String farmId) {
		//remove cache
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
        TransactionStatus status = txManager.getTransaction(def);

        List<UtilComponent> ret = util.getAreaDataByFarmId(utilDao, farmId);
		//release transaction
		txManager.commit(status);
		convertSanitizeArea(ret);
		return ret;
	}
	
	
    /**
     * Get user information based on Shipping number
     * 
     * @param shippingNumber : 
     * @return shipping data
     */
	public IvbTShippingControl getSingleData(String shippingNumber) {
		IvbTShippingControl shipping = new IvbTShippingControl();
		try{
			//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        
	        shipping = Bnn0075shippingScreenDao.getIvbTShippingControlMapper().selectByPrimaryKey(shippingNumber);
			
			//release transaction
			txManager.commit(status);
		} catch (Exception ex) {
			ex.printStackTrace();
			// log to file
			Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            shipping = null;
		}
		
		return shipping;
	}
	
	/**
     * Create search conditions parameters from data received from client
     * 
     * @param searchConditions : data received from client
     * @return HashMap object
     */
    private HashMap<String, Object> createSearchConditionParams(Bnn0075SearchShippingScreenConditions searchConditions) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        // Area id
        params.put("areaId", searchConditions.getAreaId() == null ? "" : searchConditions.getAreaId());
        // Farm name
        params.put("farmId", searchConditions.getFarmId() == null ? "" : searchConditions.getFarmId());
        // Harvest start date
        params.put("shippingNumber", searchConditions.getShippingNumber() == "" ? "" : "%" + searchConditions.getShippingNumber() + "%");
        // Harvest start date
        params.put("harvestStartDate", searchConditions.getHarvestStartDate());
        // Harvest end date
        params.put("harvestEndDate", searchConditions.getHarvestEndDate());
        // Ship start date
        params.put("shipStartDate", searchConditions.getShipStartDate());
        // Ship end date
        params.put("shipEndDate", searchConditions.getShipEndDate());
        // From parameter
        params.put("fromRow", Integer.valueOf(searchConditions.getFromRow()));
        // Number of items in a page
        params.put("itemCount", Integer.valueOf(searchConditions.getItemCount()));
        return params;
    }

    /**
     * @param inputData : search result data list
     */
    private void convertSanitize(List<Bnn0075SearchShippingScreenResult> inputData) {
        for (int i = 0; i < inputData.size(); i++) {
        	Bnn0075SearchShippingScreenResult currentData = inputData.get(i);
            // Area id
            currentData.setAreaId(util.convertSanitize(currentData.getAreaId()));
            // Farm name
            currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
            // Area Name
            currentData.setAreaName(util.convertSanitize(currentData.getAreaName()));
        }
    }
    
	/**
	 * Handle farm input data
	 * @param inputData: get farm result data list
	 */
	private void convertSanitizeFarm(List<UtilComponent> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			UtilComponent currentData = inputData.get(i);
			// Farm name
			currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
			// Area name
			currentData.setAreaName(util.convertSanitize(currentData.getAreaName()));
		}
	}

	/**
	 * Handle area input data
	 * @param inputData: get area result data list
	 */
	private void convertSanitizeArea(List<UtilComponent> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			UtilComponent currentData = inputData.get(i);
			// Farm name
			currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
			// Area name
			currentData.setAreaName(util.convertSanitize(currentData.getAreaName()));
		}
	}
    
	/**
	 * Check input: blank fields
	 * @param shippingControl : data received from client
	 * @return boolean : all fields have value: true/blank fields exist: false
	 */
	private boolean checkInputBlankFields(Bnn0075SearchShippingScreenResult shippingControl) {
		if (shippingControl.getAreaId().equals("") || shippingControl.getFarmId().equals("") || shippingControl.getHarvestDate() == null) {
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
 	private boolean checkLastUpdatedDateTime(Bnn0075SearchShippingScreenResult shippingControl, Boolean product) {
 		// get client last updated date time
 		Date lastUpdatedDateTimeClient = null;
 		Date lastUpdatedDateTimeServer = null;
 		// get server last updated date time
 		if (product) {
 			lastUpdatedDateTimeClient = shippingControl.getLastUpdateDateProduct();
 		// create hashMap to select data
            HashMap<String, Object> paramsProduct = new HashMap<String, Object>();
            // farmId
            paramsProduct.put("farmId", shippingControl.getFarmId());
            // areaId
            paramsProduct.put("areaId", shippingControl.getAreaId());
 			
            Bnn0075SearchShippingScreenResult serverData = Bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper().getLastUpdateDateProduct(paramsProduct);
            lastUpdatedDateTimeServer = serverData.getLastUpdateDateProduct();
 		} else {
 			lastUpdatedDateTimeClient = shippingControl.getLastUpdateDate();
 			IvbTShippingControl serverData = getSingleData(shippingControl.getShippingNumber());
 	 		lastUpdatedDateTimeServer = serverData.getLastUpdateDate();
 		}
 		
 		// compare date
 		if (!lastUpdatedDateTimeClient.equals(lastUpdatedDateTimeServer)) {
 			return false;
 		}
 		return true;
 	}
}
