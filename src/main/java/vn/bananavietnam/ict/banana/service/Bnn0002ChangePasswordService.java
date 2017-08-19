package vn.bananavietnam.ict.banana.service;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import vn.bananavietnam.ict.banana.component.Bnn0002ChangePasswordDataObject;
import vn.bananavietnam.ict.banana.dao.Bnn0002ChangePasswordDao;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.cnst.LoggerMessage;
import vn.bananavietnam.ict.common.db.model.IvbMUsers;
import vn.bananavietnam.ict.common.util.Util;

/**
 * @author Khoa Le
 * Change current user's password
 * Service connects to DB, processes requests and returns results to Controller 
 */
@Service
public class Bnn0002ChangePasswordService {

	private static Logger logger = Logger.getLogger(Bnn0002ChangePasswordService.class);

	private Util util = new Util();
	
	@Autowired
	private Bnn0002ChangePasswordDao bnn0002ChangePasswordDao;

    @Autowired
    private ApplicationContext appContext;

    public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

    /**
     * Update new password to DB
     * 
     * @param passwordData : data received from client
     * @return String : update successfully: 1/update failed: -1
     */
    public String updateData(Bnn0002ChangePasswordDataObject passwordData) {
        // variable definition
        String returnValue = Constants.UPDATE_RESULT_SUCCESSFUL;
        // get sing data
        IvbMUsers user = new IvbMUsers();
        user = getSingleData(util.getUserInfo().getID());
        // update starts
        try {
            // check for user input
            if (!checkInputBlankFields(passwordData)) {
            	// blank field(s)
            	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.BLANK_FIELDS_ERROR_MESSAGE);
                returnValue = Constants.VALIDATE_BLANK_FIELDS;
                return returnValue;
            }
            // check matching password
            if (!passwordData.getNewPassword().equals(passwordData.getNewPasswordConfirm())) {
            	// new password and new password confirm do not match
            	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.PASSWORD_NOT_MATCH_ERROR_MESSAGE);
                returnValue = Constants.VALIDATE_PASSWORD_NOT_MATCH;
                return returnValue;
            }
            // check correct password
            if (!checkInputPassword(passwordData)) {
            	// old password is not correct
            	logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
            			+ LoggerMessage.PASSWORD_NOT_CORRECT);
            	returnValue = Constants.VALIDATE_PASSWORD_NOT_CORRECT;
            	return returnValue;
            }
            // transaction starts
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            PlatformTransactionManager txManager = (PlatformTransactionManager) appContext.getBean("transactionManager");
            TransactionStatus status = txManager.getTransaction(def);
            try {
            	HashMap<String, Object> params = new HashMap<String, Object>();
            	
                // users id
                params.put("userId", util.getUserInfo().getID());
                // password
                params.put("password", passwordData.getNewPassword());
                // update user id
                params.put("updateUserId", util.getUserInfo().getID());
                // delete flag
                params.put("deleteFlag", passwordData.getDeleteFlag());
                //last update
                params.put("lastUpdateDate", user.getLastUpdateDate());
                
                int result = bnn0002ChangePasswordDao.getBnn0002ChangePasswordMapper().updateData(params);
                if (result > 0) { // update successfully
                    // register to DB
                    txManager.commit(status);
                } else {
                    returnValue = Constants.UPDATE_RESULT_FAILED_CULTIVATION_PASSWORD;
                    txManager.rollback(status);
                    logger.error(LoggerMessage.LOG_WARNING_PREFIX + util.getUserInfo().getID() + ","
                            + LoggerMessage.CANT_UPDATE_DATA);
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
     * Get user information based on user's id
     * 
     * @param usersId : user's id received from client
     * @return user data
     */
	public IvbMUsers getSingleData(String usersId) {
		return bnn0002ChangePasswordDao.getIvbMUsersMapper().selectByPrimaryKey(usersId);
	}

    /**
     * Check input: blank fields
     * 
     * @param passwordData : data received from client
     * @return boolean : all fields have value: true/blank fields exist: false
     */
 	private boolean checkInputBlankFields(Bnn0002ChangePasswordDataObject passwordData) {
 		if (passwordData.getOldPassword().equals("") || passwordData.getNewPassword().equals("")
 			|| passwordData.getNewPasswordConfirm().equals("")) {
 			return false;
 		} else {
 			return true;
 		}
 	}

 	/**
 	 * Check if current password is correct or not
 	 * 
 	 * @param passwordData : data received from client
 	 * @return boolean : current password is correct: true/current password is not correct: false
 	 */
 	private boolean checkInputPassword(Bnn0002ChangePasswordDataObject passwordData) {
 		String currentPassword = passwordData.getOldPassword();
 		// compare
 		HashMap<String, Object> params = new HashMap<String, Object>();
 		params.put("usersId", util.getUserInfo().getID());
 		params.put("password", currentPassword);
 		params.put("deleteFlag", Constants.DELETE_FLAG_OFF);
 		if (bnn0002ChangePasswordDao.getBnn0002ChangePasswordMapper().comparePassword(params) == null) {
 			return false;
 		} else {
 			return true;
 		}
 	}
}