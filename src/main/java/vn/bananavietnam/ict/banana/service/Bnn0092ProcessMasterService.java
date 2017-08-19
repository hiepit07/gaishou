package vn.bananavietnam.ict.banana.service;

import java.util.ArrayList;
import java.util.Date;
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

import vn.bananavietnam.ict.banana.component.Bnn0091CultivationMasterDataObject;
import vn.bananavietnam.ict.banana.component.Bnn0092ProcessMasterResult;
import vn.bananavietnam.ict.banana.dao.Bnn0092ProcessMasterDao;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.db.model.IvbMCultivation;
import vn.bananavietnam.ict.common.db.model.IvbMCultivationExample;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Hieu Dao
 */

@Service
public class Bnn0092ProcessMasterService {

	private static Logger logger = Logger.getLogger(Bnn0092ProcessMasterService.class);

	private Util util = new Util();

	@Autowired
	private Bnn0092ProcessMasterDao bnn0092ProcessMasterDao;

	// Send array list data to client side
	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private ApplicationContext appContext;

	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	/**
	 * Set data for farm, kind text box on loading
	 * @param cultivationData transfer from 0092
	 */
	public void getData(Model model, Bnn0091CultivationMasterDataObject cultivationData) {

		// Get data for text box
		Bnn0091CultivationMasterDataObject cultivationObject = new Bnn0091CultivationMasterDataObject();
		try {
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_DATA_TEXTBOX);
    		//remove cache
    		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
                cultivationObject.setFarmId(cultivationData.getFarmId());
    			cultivationObject.setFarmName(cultivationData.getFarmName());
    			cultivationObject.setKindId(cultivationData.getKindId());
    			cultivationObject.setKindName(cultivationData.getKindName());
    			convertSanitize(cultivationObject);
    			model.addAttribute("cultivationData", mapper.writeValueAsString(cultivationData));
    			//release transaction
    			txManager.commit(status);

    			logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.GET_DATA_TEXTBOX_FINISHED);
            } catch (Exception ex) {
    			ex.printStackTrace();
                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                model.addAttribute("cultivationData", new Bnn0091CultivationMasterDataObject());
                txManager.rollback(status);
    		}
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            model.addAttribute("cultivationData", new Bnn0091CultivationMasterDataObject());
		}
	}
	
	/**
	 * Get data process list
	 * @param cultivationData: data received from client
	 * @return List of process data
	 */
	public List<List<IvbMProcess>> initData(IvbMCultivation cultivationData) {

		// Get data for table list
		List<List<IvbMProcess>> processList = new ArrayList<List<IvbMProcess>>();
		List<IvbMProcess> unreList = new ArrayList<IvbMProcess>();
		List<IvbMProcess> regiList = new ArrayList<IvbMProcess>();

		try {
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_PROCESS_TABLE_LIST);
    		//remove cache
    		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
                unreList = bnn0092ProcessMasterDao.getBnn0092ProcessMasterMapper().getUnregisteredProcess(cultivationData);
            	regiList = bnn0092ProcessMasterDao.getBnn0092ProcessMasterMapper().getRegisteredProcess(cultivationData);
        		logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                		+ LoggerMessage.SQL_EXECUTION_FINISHED);
            	convertSanitizeProcess(unreList);
            	convertSanitizeProcess(regiList);
    			processList.add(unreList);
    			processList.add(regiList);
    			//release transaction
    			txManager.commit(status);

    			logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.GET_PROCESS_TABLE_LIST_FINISHED);
			} catch (Exception ex) {
				ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            processList = new ArrayList<List<IvbMProcess>>();
	            txManager.rollback(status);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            processList = new ArrayList<List<IvbMProcess>>();
		}
		return processList;
	}

	/**
	 * Select process detail in DB based on select conditions received from client
	 * @param cultivationData : data received from client
	 * @return List of process detail data
	 */
	public List<Bnn0092ProcessMasterResult> getProcessDetailData(IvbMCultivation cultivationData) {

		// variable definition
		List<Bnn0092ProcessMasterResult> processDetailData = new ArrayList<Bnn0092ProcessMasterResult>();
		try {
			logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.PROCESS_SEARCH_STARTED);
			//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
			try {
				// select starts
				processDetailData = bnn0092ProcessMasterDao.getBnn0092ProcessMasterMapper().getProcessDetailData(cultivationData);
				logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	            		+ LoggerMessage.SQL_EXECUTION_FINISHED);
				// check process data has data
				if (processDetailData.size() > 0) {
					// "real" total from search result
					String searchDataTotalCounts = bnn0092ProcessMasterDao.getBnn0092ProcessMasterMapper().getSearchDataTotalCounts(cultivationData);
					processDetailData.get(0).setSearchDataTotalCounts(searchDataTotalCounts);
					// handle user input data
					convertSanitize(processDetailData);
					logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                		+ LoggerMessage.PROCESS_SEARCH_RESULT_STRING.replace("$1", searchDataTotalCounts));
				} else {
					logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
		        			+ LoggerMessage.PROCESS_SEARCH_RESULT_0_STRING);
				}
				//release transaction
				txManager.commit(status);
			} catch (OutOfMemoryError ex) {
				ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
				Bnn0092ProcessMasterResult tempObj = new Bnn0092ProcessMasterResult();
				tempObj.setSearchDataTotalCounts("-1");
				processDetailData.add(tempObj);
				txManager.rollback(status);
			} catch (Exception ex) {
				ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
				processDetailData = null;
				txManager.rollback(status);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
			processDetailData = null;
		}
	        
		return processDetailData;
	}

	/**
	 * Get total process is unregistered on cultivation data
	 * @param cultivationData : data received from client
	 * @return total process is unregistered
	 */
	public String getUnregisteredProcessDataTotal(IvbMCultivation cultivationData) {
		String ret = "";
		try {
			//remove cache
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        try {
		        ret = bnn0092ProcessMasterDao.getBnn0091CultivationMasterMapper().getUnregisteredProcessTotal(cultivationData);
				//release transaction
				txManager.commit(status);
	        } catch (Exception ex) {
				ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            ret = null;
	            txManager.rollback(status);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            ret = null;
		}
		return ret;
	}

	/**
	 * Update cultivation's information to DB
	 * @param cultivationData : data received from client
	 * @return String : update successfully: 1/insert failed: -1
	 */
	public String updateData(List<IvbMCultivation> cultivationData) {

		// variable definition
		String returnValue = Constants.UPDATE_RESULT_SUCCESSFUL;
		IvbMCultivation cultivationObject = new IvbMCultivation();
		List<IvbMCultivation> cultivationList = new ArrayList<IvbMCultivation>();
		int result = 0;

		// update starts
		try {
			logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            		+ LoggerMessage.PROCESS_RIGISTER_STARTED);
			// transaction starts
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
			TransactionStatus status = txManager.getTransaction(def);
			try {
				cultivationList = cultivationData;
				for(int i = 0; i < cultivationList.size(); i++) {
					// variable definition example and create criteria
					IvbMCultivationExample example = new IvbMCultivationExample();
					IvbMCultivationExample.Criteria criteria = example.createCriteria();
					// farm id
					criteria.andFarmIdEqualTo(cultivationList.get(i).getFarmId());
					// kind id
					criteria.andKindIdEqualTo(cultivationList.get(i).getKindId());
					// process id
					criteria.andProcessIdEqualTo(cultivationList.get(i).getProcessId());
					// last update date
					criteria.andLastUpdateDateLessThanOrEqualTo(cultivationData.get(i).getLastUpdateDate());
					// process order
					cultivationObject.setProcessOrder(cultivationData.get(i).getProcessOrder());
					// update user id
					cultivationObject.setUpdateUserId(util.getUserInfo().getID());

					result = bnn0092ProcessMasterDao.getIvbMCultivationMapper().updateByExampleSelective(cultivationObject, example);
					logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
		            		+ LoggerMessage.SQL_EXECUTION_FINISHED);
					// check result signal data insert to database if <= 0 then end update data
					if(result <= 0) {
						logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
			            		+ LoggerMessage.PROCESS_RIGISTER_FAILED);
						break;
					}
				}
				// update successfully
				if (result > 0) {
						// register to DB
						logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
			            		+ LoggerMessage.PROCESS_RIGISTER_FINISHED);
						txManager.commit(status);
				} else {
					// check last updated date
			        if (!checkLastUpdatedDateTimeList(cultivationData)) {
		            	// record has been updated
		            	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
		            			+ LoggerMessage.CANT_UPDATE_DATA_UPDATE_DATE);
		                returnValue = Constants.UPDATE_RESULT_FAILED_CULTIVATION_PROCESS_LAST_UPDATE_DATE_RESULT;
						txManager.rollback(status);
		                return returnValue;
		            } else {
						logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
			            		+ LoggerMessage.PROCESS_RIGISTER_FAILED);
						returnValue = Constants.UPDATE_RESULT_FAILED_CULTIVATION_PROCESS_RESULT;
						txManager.rollback(status);
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
	 * Handle process input data
	 * @param inputData: get process result data list
	 */
	private void convertSanitizeProcess(List<IvbMProcess> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			IvbMProcess currentData = inputData.get(i);
			// Process name
			currentData.setProcessName(util.convertSanitize(currentData.getProcessName()));
		}
	}

	/**
	 * Handle user input data
	 * @param inputData : search result data list
	 */
	private void convertSanitize(List<Bnn0092ProcessMasterResult> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			Bnn0092ProcessMasterResult currentData = inputData.get(i);
			// Process Name
			currentData.setProcessName(util.convertSanitize(currentData.getProcessName()));
			// Task Name
			currentData.setTaskName(util.convertSanitize(currentData.getTaskName()));
			// Working Details
			currentData.setWorkingDetails(util.convertSanitize(currentData.getWorkingDetails()));
			// Note
			currentData.setNote(util.convertSanitize(currentData.getNote()));
		}
	}

	/**
	 * Handle cultivation input data
	 * @param inputData: get cultivation result data object
	 */
	private void convertSanitize(Bnn0091CultivationMasterDataObject inputData) {
		Bnn0091CultivationMasterDataObject currentData = inputData;
		// Farm name
		currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
		// Kind name
		currentData.setKindName(util.convertSanitize(currentData.getKindName()));
	}

	/**
 	 * Check whether current record has been updated before or not
 	 * 
 	 * @param cultivationData
 	 * @return boolean : 2 dates are equal: true/2 dates are not equal: false 
 	 */
 	private boolean checkLastUpdatedDateTimeList(List<IvbMCultivation> cultivationData) {
 		boolean flagUpdate = true;
 		for(int i = 1; i < cultivationData.size(); i++) {
		 	// get client last updated date time
			Date lastUpdatedDateTimeClient = cultivationData.get(i).getLastUpdateDate();
			// get server last updated date time
			Date lastUpdatedDateTimeServer = bnn0092ProcessMasterDao.getBnn0092ProcessMasterMapper().getLastUpdateDate(cultivationData.get(i));
			// compare date
			if (!lastUpdatedDateTimeClient.equals(lastUpdatedDateTimeServer)) {
				flagUpdate = false;
				break;
			}
 		}
		return flagUpdate;
 	}
}