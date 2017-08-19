package vn.bananavietnam.ict.banana.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import vn.bananavietnam.ict.banana.component.Bnn0091CultivationMasterDataObject;
import vn.bananavietnam.ict.banana.dao.Bnn0091CultivationMasterDao;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.db.model.IvbMCultivation;
import vn.bananavietnam.ict.common.db.model.IvbMCultivationExample;
import vn.bananavietnam.ict.common.db.model.IvbMFarm;
import vn.bananavietnam.ict.common.db.model.IvbMFarmExample;
import vn.bananavietnam.ict.common.db.model.IvbMKind;
import vn.bananavietnam.ict.common.db.model.IvbMKindExample;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.db.model.IvbMProcessExample;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Hieu Dao
 */

@Service
public class Bnn0091CultivationMasterService {

	private static Logger logger = Logger.getLogger(Bnn0091CultivationMasterService.class);

	private Util util = new Util();
	
	@Autowired
	private Bnn0091CultivationMasterDao bnn0091CultivationMasterDao;

	// Send array list data to client side
	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private ApplicationContext appContext;

	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	/**
	 * Create data for farm, kind, process, task combo box on loading
	 * Create data for task list on loading
	 * @param model
	 */
	public void initData(Model model) {

		// Get data for combo box
		List<IvbMFarm> farmData = new ArrayList<IvbMFarm>();
		List<IvbMKind> kindData = new ArrayList<IvbMKind>();
		List<IvbMProcess> processData = new ArrayList<IvbMProcess>();

		// Create example criteria
		IvbMFarmExample farmExample = new IvbMFarmExample();
		IvbMKindExample kindExample = new IvbMKindExample();
		IvbMProcessExample processExample = new IvbMProcessExample();
		IvbMFarmExample.Criteria farmCriteria = farmExample.createCriteria();
		IvbMKindExample.Criteria kindCriteria = kindExample.createCriteria();
		IvbMProcessExample.Criteria processCriteria = processExample.createCriteria();
		try {
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_DATA_COMBOBOX);
    		//remove cache
    		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
                farmExample.setOrderByClause("FARM_NAME");
            	kindExample.setOrderByClause("KIND_NAME");
            	processExample.setOrderByClause("PROCESS_NAME");
    			farmCriteria.andFarmIdNotEqualTo(Constants.DEFAULT_FARM);
    			farmCriteria.andDeleteFlagEqualTo(false);
    			kindCriteria.andKindIdNotEqualTo(Constants.DEFAULT_KIND);
    			kindCriteria.andDeleteFlagEqualTo(false);
    			processCriteria.andProcessIdNotEqualTo(Constants.DEFAULT_PROCESS);
    			processCriteria.andDeleteFlagEqualTo(false);
    			farmData = bnn0091CultivationMasterDao.getIvbMFarmMapper().selectByExample(farmExample);
    			kindData = bnn0091CultivationMasterDao.getIvbMKindMapper().selectByExample(kindExample);
    			processData = bnn0091CultivationMasterDao.getIvbMProcessMapper().selectByExample(processExample);
        		logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                		+ LoggerMessage.SQL_EXECUTION_FINISHED);
    			convertSanitizeFarm(farmData);
    			convertSanitizeKind(kindData);
    			convertSanitizeProcess(processData);
    			model.addAttribute("farmData", mapper.writeValueAsString(farmData));
    			model.addAttribute("kindData", mapper.writeValueAsString(kindData));
    			model.addAttribute("processData", mapper.writeValueAsString(processData));

    			//release transaction
    			txManager.commit(status);
            	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.GET_DATA_COMBOBOX_FINISHED);
            } catch (Exception ex) {
    			ex.printStackTrace();
                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                model.addAttribute("farmData", new ArrayList<IvbMFarm>());
    			model.addAttribute("kindData", new ArrayList<IvbMKind>());
    			model.addAttribute("processData", new ArrayList<IvbMProcess>());
    			txManager.rollback(status);
    		}
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            model.addAttribute("farmData", new ArrayList<IvbMFarm>());
			model.addAttribute("kindData", new ArrayList<IvbMKind>());
			model.addAttribute("processData", new ArrayList<IvbMProcess>());
		}
	}
	
	/**
	 * Get data task list
	 * @param cultivationData: data received from client
	 * @return List of task data
	 */
	@SuppressWarnings("rawtypes")
	public List<List> getTaskData(IvbMCultivation cultivationData) {

		// Get data for table list
		List<List> taskList = new ArrayList<List>();
		List<IvbMTask> unreList = new ArrayList<IvbMTask>();
		List<Bnn0091CultivationMasterDataObject> regiList = new ArrayList<Bnn0091CultivationMasterDataObject>();

		try {
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.GET_TASK_TABLE_LIST);
    		//remove cache
    		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
                unreList = bnn0091CultivationMasterDao.getBnn0091CultivationMasterMapper().getUnregisteredTask(cultivationData);
            	regiList = bnn0091CultivationMasterDao.getBnn0091CultivationMasterMapper().getRegisteredTask(cultivationData);
        		logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
                		+ LoggerMessage.SQL_EXECUTION_FINISHED);
            	convertSanitizeTask(unreList);
            	convertSanitizeBnn0091Object(regiList);
    			taskList.add(unreList);
    			taskList.add(regiList);
    			//release transaction
    			txManager.commit(status);

    			logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.GET_TASK_TABLE_LIST_FINISHED);
            }  catch (Exception ex) {
    			ex.printStackTrace();
                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
                taskList = new ArrayList<List>();
                txManager.rollback(status);
    		}
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            taskList = new ArrayList<List>();
		}
		return taskList;
	}

	/**
	 * Get task information based on task's id
	 * @param taskId : task's id received from client
	 * @return task data
	 */
	public IvbMTask getSingleData(String taskId) {
        IvbMTask bnn0091Object = new IvbMTask();
		//remove cache
		try	{
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
	        PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
	        TransactionStatus status = txManager.getTransaction(def);
	        try	{
		        bnn0091Object = bnn0091CultivationMasterDao.getIvbMTaskMapper().selectByPrimaryKey(taskId);
				convertSanitizeTask(bnn0091Object);
				//release transaction
				txManager.commit(status);
	        } catch (Exception ex) {
				ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
	            bnn0091Object = null;
	            txManager.rollback(status);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
            bnn0091Object = null;
		}

		return bnn0091Object;
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
		        ret = bnn0091CultivationMasterDao.getBnn0091CultivationMasterMapper().getUnregisteredProcessTotal(cultivationData);
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
	 * Insert cultivation's information to DB
	 * @param cultivationData : data received from client
	 * @return String : insert successfully: 1/insert failed: -1
	 */
	public String insertData(List<Bnn0091CultivationMasterDataObject> cultivationData) {
		// variable definition
		String returnValue = Constants.INSERT_RESULT_SUCCESSFUL;
		int result = 0;
		// insert starts
		try {
			logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
            		+ LoggerMessage.TASK_RIGISTER_STARTED);
			// transaction starts
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
			TransactionStatus status = txManager.getTransaction(def);
			try {
				IvbMCultivation cultivationObj = new IvbMCultivation();
				IvbMCultivationExample example = new IvbMCultivationExample();
				IvbMCultivationExample.Criteria criteria = example.createCriteria();
				criteria.andFarmIdEqualTo(cultivationData.get(0).getFarmId());
				criteria.andKindIdEqualTo(cultivationData.get(0).getKindId());
				criteria.andProcessIdEqualTo(cultivationData.get(0).getProcessId());
				if(cultivationData.get(0).getTaskId().equals("1")) {
					criteria.andLastUpdateDateLessThanOrEqualTo(cultivationData.get(0).getLastUpdateDate());
					logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                		+ LoggerMessage.TASK_UNRIGISTER_STARTED);
					result = bnn0091CultivationMasterDao.getIvbMCultivationMapper().deleteByExample(example);
					logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                		+ LoggerMessage.SQL_EXECUTION_FINISHED);
	                if(result > 0) {
						logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
		                		+ LoggerMessage.TASK_UNRIGISTER_FINISHED);
						if(cultivationData.size() > 1) {
							for(int i = 1; i < cultivationData.size(); i++) {
								// farm id
								cultivationObj.setFarmId(cultivationData.get(i).getFarmId());
								// kind id
								cultivationObj.setKindId(cultivationData.get(i).getKindId());
								// process id
								cultivationObj.setProcessId(cultivationData.get(i).getProcessId());
								// process order
								cultivationObj.setProcessOrder(0);
								// task id
								cultivationObj.setTaskId(cultivationData.get(i).getTaskId());
								// task order
								cultivationObj.setTaskOrder(i);
								// block flag
								if(cultivationData.get(i).getBlockWorkFlag()) {
									cultivationObj.setBlockWorkFlag(true);
								} else {
									cultivationObj.setBlockWorkFlag(false);
								}
								// create user id
								cultivationObj.setCreateUserId(util.getUserInfo().getID());
								// update user id
								cultivationObj.setUpdateUserId(util.getUserInfo().getID());
								// create date
								cultivationObj.setCreateDate(Calendar.getInstance().getTime());
								// update date
								cultivationObj.setLastUpdateDate(Calendar.getInstance().getTime());
								// delete flag
								cultivationObj.setDeleteFlag(false);
								
								result = bnn0091CultivationMasterDao.getIvbMCultivationMapper().insert(cultivationObj);
								logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
				                		+ LoggerMessage.SQL_EXECUTION_FINISHED);
								if(result <= 0) {
									logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
					                		+ LoggerMessage.TASK_RIGISTER_FAILED);
									break;
								}
							}
						}
					} else {
						// check last updated date
				        if (!checkLastUpdatedDateTime(cultivationData.get(0))) {
			            	// record has been updated
			            	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
			            			+ LoggerMessage.CANT_DELETE_DATA_UPDATE_DATE);
			                returnValue = Constants.INSERT_RESULT_FAILED_CULTIVATION_TASK_LAST_UPDATE_DATE_RESULT;
							txManager.rollback(status);
			                return returnValue;
				        } else {
							logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
			                		+ LoggerMessage.TASK_UNRIGISTER_FAILED);
			                returnValue = Constants.INSERT_RESULT_FAILED_CULTIVATION_TASK_RESULT;
							txManager.rollback(status);
			                return returnValue;
				        }
					}
				} else {
					for(int i = 1; i < cultivationData.size(); i++) {
						// farm id
						cultivationObj.setFarmId(cultivationData.get(i).getFarmId());
						// kind id
						cultivationObj.setKindId(cultivationData.get(i).getKindId());
						// process id
						cultivationObj.setProcessId(cultivationData.get(i).getProcessId());
						// process order
						cultivationObj.setProcessOrder(0);
						// task id
						cultivationObj.setTaskId(cultivationData.get(i).getTaskId());
						// task order
						cultivationObj.setTaskOrder(i);
						// block flag
						if(cultivationData.get(i).getBlockWorkFlag()) {
							cultivationObj.setBlockWorkFlag(true);
						} else {
							cultivationObj.setBlockWorkFlag(false);
						}
						// create user id
						cultivationObj.setCreateUserId(util.getUserInfo().getID());
						// update user id
						cultivationObj.setUpdateUserId(util.getUserInfo().getID());
						// create date
						cultivationObj.setCreateDate(Calendar.getInstance().getTime());
						// update date
						cultivationObj.setLastUpdateDate(Calendar.getInstance().getTime());
						// delete flag
						cultivationObj.setDeleteFlag(false);
						
						result = bnn0091CultivationMasterDao.getIvbMCultivationMapper().insert(cultivationObj);
						logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
		                		+ LoggerMessage.SQL_EXECUTION_FINISHED);
						if(result <= 0) {
							logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
			                		+ LoggerMessage.TASK_RIGISTER_FAILED);
							break;
						}
					}
				}
				// insert successfully
				if (result > 0) {
					// register to DB
					logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                		+ LoggerMessage.TASK_RIGISTER_FINISHED);
					txManager.commit(status);
				} else {
					logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
	                		+ LoggerMessage.TASK_RIGISTER_FAILED);
					returnValue = Constants.INSERT_RESULT_FAILED_CULTIVATION_TASK_RESULT;
					txManager.rollback(status);
				}
			} catch (DuplicateKeyException ex) {
				// check last updated date second time after insert
		        if (!checkLastUpdatedDateTime(cultivationData.get(0))) {
	            	// record has been updated
	                Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.CANT_DELETE_DATA_UPDATE_DATE, ex);
	                returnValue = Constants.INSERT_RESULT_FAILED_CULTIVATION_TASK_LAST_UPDATE_DATE_RESULT;
					txManager.rollback(status);
		        } else {
		        	ex.printStackTrace();
		            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
		        	returnValue = Constants.INSERT_RESULT_FAILED_EXCEPTION;
					txManager.rollback(status);
		        }
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
	 * Handle farm input data
	 * @param inputData: get farm result data list
	 */
	private void convertSanitizeFarm(List<IvbMFarm> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			IvbMFarm currentData = inputData.get(i);
			// Farm name
			currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
			// Farm location
			currentData.setFarmLocation(util.convertSanitize(currentData.getFarmName()));
			// Climate
			currentData.setClimate(util.convertSanitize(currentData.getClimate()));
			// Soil
			currentData.setSoil(util.convertSanitize(currentData.getSoil()));
			// Email address
			currentData.setEmailAddress(util.convertSanitize(currentData.getEmailAddress()));
			// Phone
			currentData.setPhone(util.convertSanitize(currentData.getPhone()));
			// Fax
			currentData.setFax(util.convertSanitize(currentData.getFax()));
			// Note
			currentData.setNote(util.convertSanitize(currentData.getNote()));
		}
	}

	/**
	 * Handle kind input data
	 * @param inputData: get kind result data list
	 */
	private void convertSanitizeKind(List<IvbMKind> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			IvbMKind currentData = inputData.get(i);
			// Kind name
			currentData.setKindName(util.convertSanitize(currentData.getKindName()));
		}
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
	 * Handle task input data
	 * @param inputData: get task result data list
	 */
	private void convertSanitizeTask(List<IvbMTask> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			IvbMTask currentData = inputData.get(i);
			// Task name
			currentData.setTaskName(util.convertSanitize(currentData.getTaskName()));
			// Working details
			currentData.setWorkingDetails(util.convertSanitize(currentData.getWorkingDetails()));
			// Note
			currentData.setNote(util.convertSanitize(currentData.getNote()));
		}
	}

	/**
	 * Handle task input data
	 * @param inputData: get task result data object
	 */
	private void convertSanitizeTask(IvbMTask inputData) {
		IvbMTask currentData = inputData;
		// Task name
		currentData.setTaskName(util.convertSanitize(currentData.getTaskName()));
		// Working details
		currentData.setWorkingDetails(util.convertSanitize(currentData.getWorkingDetails()));
		// Note
		currentData.setNote(util.convertSanitize(currentData.getNote()));
	}

	/**
	 * Handle task input data
	 * @param inputData: get bnn0091 object result data object
	 */
	private void convertSanitizeBnn0091Object(List<Bnn0091CultivationMasterDataObject> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			Bnn0091CultivationMasterDataObject currentData = inputData.get(i);
			// Farm name
			currentData.setFarmName(util.convertSanitize(currentData.getFarmName()));
			// Kind name
			currentData.setKindName(util.convertSanitize(currentData.getKindName()));
			// Task name
			currentData.setTaskName(util.convertSanitize(currentData.getTaskName()));
		}
	}

	/**
 	 * Check whether current record has been updated before or not
 	 * 
 	 * @param cultivationData
 	 * @return boolean : 2 dates are equal: true/2 dates are not equal: false 
 	 */
 	private boolean checkLastUpdatedDateTime(Bnn0091CultivationMasterDataObject cultivationData) {
 		boolean flagUpdate = true;
	 	// get client last updated date time
		Date lastUpdatedDateTimeClient = cultivationData.getLastUpdateDate();
		// get server last updated date time
		Date lastUpdatedDateTimeServer = bnn0091CultivationMasterDao.getBnn0091CultivationMasterMapper().getLastUpdateDate(cultivationData);
		// compare date
		if (!lastUpdatedDateTimeClient.equals(lastUpdatedDateTimeServer)) {
			flagUpdate = false;
			return flagUpdate;
		}
		return flagUpdate;
 	}
}