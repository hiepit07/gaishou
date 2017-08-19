package vn.bananavietnam.ict.banana.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import vn.bananavietnam.ict.banana.component.Bnn0023SearchUserPopupDataObject;
import vn.bananavietnam.ict.banana.dao.Bnn0023SearchUserPopupDao;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Hieu Dao
 */

@Service
public class Bnn0023SearchUserPopupService {

	private static Logger logger = Logger.getLogger(Bnn0023SearchUserPopupService.class);

    private Util util = new Util();
	
	@Autowired
	private Bnn0023SearchUserPopupDao bnn0023Dao;

    @Autowired
    private ApplicationContext appContext;

	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	/**
	 * Search farm in DB based on search conditions received from client
	 * @param searchConditions : data received from client
	 * @return List of users data
	 */
	public List<Bnn0023SearchUserPopupDataObject> searchData(Bnn0023SearchUserPopupDataObject userData) {
		List<Bnn0023SearchUserPopupDataObject> bnn0023ResultList = new ArrayList<Bnn0023SearchUserPopupDataObject>();
		try{
        	logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
        			+ LoggerMessage.USER_SEARCH_STARTED);
    		//remove cache
    		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
			try {
	        	if(!userData.getUserId().equals("")) {
	        		userData.setUserId("%" + userData.getUserId() + "%");
	        	}
	        	if(!userData.getUserName().equals("")) {
	        		userData.setUserName("%" + userData.getUserName() + "%");
	        	}
	    		//main process
				// search starts
	        	bnn0023ResultList = bnn0023Dao.getBnn0023SearchUserPopupServiceMapper().searchData(userData);
	            logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	            		+ LoggerMessage.SQL_EXECUTION_FINISHED);
				if (bnn0023ResultList.size() > 0) {
					// "real" total from search result
					String searchDataTotalCounts = bnn0023Dao.getBnn0023SearchUserPopupServiceMapper().getSearchDataTotalCounts(userData);
					bnn0023ResultList.get(0).setSearchDataTotalCounts(searchDataTotalCounts);
					// handle user input data
					convertSanitize(bnn0023ResultList);
	                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                		+ LoggerMessage.USER_SEARCH_RESULT_STRING.replace("$1", searchDataTotalCounts));
				} else {
	                logger.info(LoggerMessage.LOG_INFO_PREFIX + util.getUserInfo().getID() + ","
	                		+ LoggerMessage.USER_SEARCH_RESULT_0_STRING);
				}
				//release transaction
				txManager.commit(status);
			} catch (OutOfMemoryError ex) {
				ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.OOM_EXCEPTION_ERROR_MESSAGE, ex);
				Bnn0023SearchUserPopupDataObject tempObj = new Bnn0023SearchUserPopupDataObject();
				tempObj.setSearchDataTotalCounts("-1");
				bnn0023ResultList.add(tempObj);
				txManager.rollback(status);
			} catch (Exception ex) {
				ex.printStackTrace();
	            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
				bnn0023ResultList = null;
				txManager.rollback(status);
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
            Util.writeExceptionLog(logger, util.getUserInfo().getID(), LoggerMessage.EXCEPTION_ERROR_MESSAGE, ex);
			bnn0023ResultList = null;
		}
		return bnn0023ResultList;
	}

	/**
	 * Handle user input data
	 * @param inputData : search result data list
	 */
	private void convertSanitize(List<Bnn0023SearchUserPopupDataObject> inputData) {
		for (int i = 0; i < inputData.size(); i++) {
			Bnn0023SearchUserPopupDataObject currentData = inputData.get(i);
			// User name
			currentData.setUserName(util.convertSanitize(currentData.getUserName()));
		}
	}
}