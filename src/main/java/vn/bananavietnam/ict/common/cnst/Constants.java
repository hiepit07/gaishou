package vn.bananavietnam.ict.common.cnst;

public class Constants {

	// Constants definition
	// Upload path
	public static final String UPLOAD_PATH = "C:/Banana Data/images/";

	// Role type value
	public static final String ROLE_TYPE_SYSTEM_MANAGER = "1";
	public static final String ROLE_TYPE_BUSINESS_MANAGER = "2";
	public static final String ROLE_TYPE_FARM_MANAGER = "3";
	public static final String ROLE_TYPE_AREA_MANAGER = "4";

	// Delete flag value
	public static final Boolean DELETE_FLAG_ON = true;
	public static final Boolean DELETE_FLAG_OFF = false;

	// Compare with flag value
	public static final String COMPARE_WITH_FLAG_ON = "true";
	public static final String COMPARE_WITH_FLAG_OFF = "false";

	// Flag value
	public static final Boolean FLAG_ON = true;
	public static final Boolean FLAG_OFF = false;

	// Cultivation Result Previous Round
	public static final Boolean PREVIOUS_ROUND = true;
	public static final Boolean CURRENT_ROUND = false;

	// Authorization Type Id
	public static final String AUTHORIZATION_TYPE_ID = "1";
	// Begin Farm Id
	public static final String BEGIN_FARM_ID = "F";
	// Begin Area Id
	public static final String BEGIN_AREA_ID = "A";
	// Begin Block Id
	public static final String BEGIN_BLOCK_ID = "B";
	// Begin Kind Id
	public static final String BEGIN_KIND_ID = "K";
	// Begin Process Id
    public static final String BEGIN_PROCESS_ID = "P";
    // Begin Task Id
    public static final String BEGIN_TASK_ID = "T";
    // Max farm Id
  	public static final int MAX_FARM_ID = 999;
  	
  	// Max Area Id
   	public static final int MAX_AREA_ID = 999;
   	// Max Kind Id
   	public static final int MAX_KIND_ID = 999;
	// Default Farm
	public static final String DEFAULT_FARM = "9999";
	// Default Area
	public static final String DEFAULT_AREA = "9999";
	// Default Area
	public static final String DEFAULT_BLOCK = "9999";
	// Default Kind
	public static final String DEFAULT_KIND = "9999";
	// Default Line Id
	public static final String DEFAULT_LINE_ID = "9999";
	// Default Column Id
	public static final String DEFAULT_COLUMN_ID = "9999";
	// Default Column Id
	public static final String DEFAULT_PROCESS = "9999";
	// Max Length Id
	public static final int MAX_LENGHT_ID = 3;
	// Task change point code
	public static final String TASK_CHANGE_POINT_CODE_NONE = "0";
	public static final String TASK_CHANGE_POINT_CODE_CULTIVATION_START = "1";
	public static final String TASK_CHANGE_POINT_CODE_PLANTING = "2";
	public static final String TASK_CHANGE_POINT_CODE_FLOWERING = "3";
	public static final String TASK_CHANGE_POINT_CODE_BAGGED = "4";
	public static final String TASK_CHANGE_POINT_CODE_HARVESTED = "5";

	// List/Result is equal to 0
	public static final Integer LIST_IS_EQUAL_TO_ZERO = 0;
	public static final Integer RESULT_IS_EQUAL_TO_ZERO = 0;
	// Constant used when searching data: Nothing is searched
    public static final String SEARCH_CONDITION_NO_SELECT = "-2";
    // When blank fields exist
 	public static final String VALIDATE_BLANK_FIELDS = "3000";
 	// When field is in wrong format
 	public static final String VALIDATE_WRONG_FORMAT = "3001";
 	// When new password and new password confirm do not match
 	public static final String VALIDATE_PASSWORD_NOT_MATCH = "3002";
 	// When current password is not correct
  	public static final String VALIDATE_PASSWORD_NOT_CORRECT = "3003";
    // Update info to DB successfully
    public static final String UPDATE_RESULT_SUCCESSFUL = "4000";
    // Update info to DB failed
    public static final String UPDATE_RESULT_FAILED = "4001";
    // Update info to DB failed
    public static final String UPDATE_RESULT_FAILED_DUPLLICATE = "4002";
    // Update info to DB failed at process
    public static final String UPDATE_RESULT_FAILED_PROCESS = "4013";
    // Update info to DB failed at kind
    public static final String UPDATE_RESULT_FAILED_KIND = "4017";
    // Update info to DB failed at Task
    public static final String UPDATE_RESULT_FAILED_TASK = "4089";
    // Update info to DB failed at cultivation process result
    public static final String UPDATE_RESULT_FAILED_CULTIVATION_PROCESS_RESULT = "4920";
    // Update info to DB failed at cultivation process with last update date is latest result
    public static final String UPDATE_RESULT_FAILED_CULTIVATION_PROCESS_LAST_UPDATE_DATE_RESULT = "4921";
    // Update info to DB failed at password
    public static final String UPDATE_RESULT_FAILED_CULTIVATION_PASSWORD = "4002";
    // Update info to DB failed at user
    public static final String UPDATE_RESULT_FAILED_CULTIVATION_USER = "4003";
    // Update info to DB failed at farm
    public static final String UPDATE_RESULT_FAILED_CULTIVATION_FARM = "4005";
    // Update info to DB failed at AREA
    public static final String UPDATE_RESULT_FAILED_CULTIVATION_AREA = "4007";
    // Update info to DB failed at AREA
    public static final String UPDATE_RESULT_FAILED_CULTIVATION_BLOCK = "4009";
    // Update info to DB failed at shipping
    public static final String UPDATE_RESULT_FAILED_CULTIVATION_SHIPPING = "4075";
    // Update info to DB failed at affiliation
    public static final String UPDATE_RESULT_FAILED_CULTIVATION_SHIPPING_UPDATE_DATE = "4751";
    // Update info to DB failed at affiliation
    public static final String UPDATE_RESULT_FAILED_CULTIVATION_AFFILIATION = "4087";
    // Update info to DB failed at affiliation
    public static final String UPDATE_RESULT_FAILED_ACCESS_AUTHORIZATION = "4095";
    // Update info to DB failed at exception
    public static final String UPDATE_RESULT_FAILED_EXCEPTION = "4006";
    // Update info to DB failed at exception
    public static final String UPDATE_RESULT_FAILED_UPDATE_DATE = "4131";
    // Insert info to DB successfully
    public static final String INSERT_RESULT_SUCCESSFUL = "5000";
    // Insert info to DB with duplicated id
    public static final String INSERT_RESULT_DUPLICATED = "5001";
    // Insert info to DB with duplicated block id
    public static final String INSERT_RESULT_DUPLICATED_BLOCK = "5109";
    // Insert info to DB with wrong config
    // this case will not happen, in the past we read data from XML but not anymore
    public static final String INSERT_RESULT_CONFIG_NOT_CORRECT = "5002";
    // Insert info to DB failed
    public static final String INSERT_RESULT_FAILED = "5003";
    // Insert info to DB failed at product
    public static final String INSERT_RESULT_FAILED_PRODUCT = "5004";
    // Insert info to DB failed at cultivation
    public static final String INSERT_RESULT_FAILED_CULTIVATION = "5005";
    // Insert info to DB failed at Area
    public static final String INSERT_RESULT_FAILED_AREA = "5007";
    // Insert info to DB failed at Block
    public static final String INSERT_RESULT_FAILED_BLOCK = "5009";
    // Insert info to DB failed at process
    public static final String INSERT_RESULT_FAILED_PROCESS = "5013";
    // Insert info to DB failed at kind
    public static final String INSERT_RESULT_FAILED_KIND = "5017";
    // Insert info to DB failed at task
    public static final String INSERT_RESULT_FAILED_TASK = "5089";
    // Insert info to DB failed at cultivation task result
    public static final String INSERT_RESULT_FAILED_CULTIVATION_TASK_RESULT = "5910";
    // Insert info to DB failed at cultivation task with last update date is latest result
    public static final String INSERT_RESULT_FAILED_CULTIVATION_TASK_LAST_UPDATE_DATE_RESULT = "5911";
    // Insert info to DB failed at password
    public static final String INSERT_RESULT_FAILED_CULTIVATION_PASSWORD = "5021";
    // Insert info to DB failed at user
    public static final String INSERT_RESULT_FAILED_CULTIVATION_USER = "5031";
    // Insert info to DB failed at farm
    public static final String INSERT_RESULT_FAILED_CULTIVATION_FARM = "5051";
    // Insert info to DB failed at shipping
    public static final String INSERT_RESULT_FAILED_CULTIVATION_SHIPPING = "5075";
    // Insert info to DB failed at shipping when record is had
    public static final String INSERT_RESULT_FAILED_CULTIVATION_SHIPPING_RECORD_IS_HAD = "5175";
    // Insert info to DB failed at affiliation
    public static final String INSERT_RESULT_FAILED_CULTIVATION_AFFILIATION = "5870";
    // Insert info to DB failed at last update date affiliation
    public static final String INSERT_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE = "5871";
    // Insert info to DB failed at access authorization
    public static final String INSERT_RESULT_FAILED_CULTIVATION_ACCESS_AUTHORIZATION = "5095";
    // Insert info to DB failed at exception
    public static final String INSERT_RESULT_FAILED_EXCEPTION = "5006";
    // Insert info to DB failed at area manager
    public static final String INSERT_RESULT_FAILED_MANAGER = "5872";
    // Insert info to DB failed at product update date
    public static final String INSERT_RESULT_FAILED_PRODUCT_UPDATE_DATE = "5372";
    // Insert info to DB failed at block delete
    public static final String INSERT_RESULT_FAILED_BLOCK_DELETE = "5373";
    // Delete info to DB successfully
    public static final String DELETE_RESULT_SUCCESSFUL = "6000";
    // Delete info to DB failed
    public static final String DELETE_RESULT_FAILED = "6001";
    // Delete info to DB failed at process
    public static final String DELETE_RESULT_FAILED_PROCESS = "6013";
    // Delete info to DB failed at kind
    public static final String DELETE_RESULT_FAILED_KIND = "6017";
    // Delete info to DB failed at Task
    public static final String DELETE_RESULT_FAILED_TASK = "6089";
    // Delete info to DB failed at password
    public static final String DELETE_RESULT_FAILED_CULTIVATION_PASSWORD = "6002";
    // Delete info to DB failed at user
    public static final String DELETE_RESULT_FAILED_CULTIVATION_USER = "6003";
    // Delete info to DB failed at farm
    public static final String DELETE_RESULT_FAILED_CULTIVATION_FARM = "6005";
    // Delete info to DB failed at area
    public static final String DELETE_RESULT_FAILED_CULTIVATION_AREA = "6007";
    // Delete info to DB failed at block
    public static final String DELETE_RESULT_FAILED_CULTIVATION_BLOCK = "6009";
    // Delete info to DB failed at shipping
    public static final String DELETE_RESULT_FAILED_CULTIVATION_SHIPPING = "6075";
    // Delete info to DB failed at affiliation
    public static final String DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION = "6870";
    // Delete info to DB failed at last update date affiliation
    public static final String DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE = "6871";
    // Delete info to DB failed at access authorization
    public static final String DELETE_RESULT_FAILED_CULTIVATION_ACCESS_AUTHORIZATION = "6095";
    // Delete info to DB failed at exception
    public static final String DELETE_RESULT_FAILED_EXCEPTION = "6006";
    // Update info to DB failed at exception
    public static final String DELETE_RESULT_FAILED_UPDATE_DATE = "6131";
	// Delete info to DB failed at exception
    public static final String DELETE_RESULT_FAILED_CULTIVATION = "6371";
    // Delete info to DB failed at exception
    public static final String DELETE_RESULT_FAILED_PRODUCT = "6372";
    // Delete info to DB failed at exception
    public static final String DELETE_RESULT_FAILED_PRODUCT_UPDATE_DATE = "6373";
    // Delete info to DB failed at exception
    public static final String DELETE_RESULT_FAILED_CULTIVATION_UPDATE_DATE = "6374";
    // Delete info to DB failed at area manager
    public static final String DELETE_RESULT_FAILED_MANAGER = "6872";
    // Upload file to server successfully
    public static final String UPLOAD_RESULT_SUCCESSFUL = "7000";
    // Upload file to server failed
    public static final String UPLOAD_RESULT_FAILED = "7001";
    // Upload info to DB failed at exception
    public static final String UPLOAD_RESULT_FAILED_EXCEPTION = "7006";
    // Init screen successfully
    public static final String INIT_RESULT_SUCCESSFUL = "8000";
    // Init screen failed
    public static final String INIT_RESULT_FAILED = "8001";
    // when get data exception
    public static final String ERROR_MESSAGE_EXCEPTION = "8351";
    // when get task data but not register yet 
    public static final String ERROR_MESSAGE_TASK_NULL = "8352";
    // when get data exception at client
    public static final String ERROR_MESSAGE_EXCEPTION_CLIENT = "8353";
    // when write file error
    public static final String WRITE_FILE_EXCEPTION = "9001";

    public static final String SYSTEM_MANAGER = "1";
    public static final String CCO = "2";
    public static final String FARM_MANAGER = "3";
    public static final String AREA_MANAGER = "4";
	//Max length In Shipping Number Id
	public static final int MAX_LENGHT_SHIPNUMBER_ID = 8;
	//Max shipping number
	public static final Integer MAX_SHIPPING_NUMBER = 99999999;
	// default starting cultivation date
	public static final String DEFAULT_CULTIVATION_START_DATE = "1900-01-01";
	
	public static final int SCREEN_DISPLAY_ENABLE_FLAG = 0;
	public static final int ADDABLE_FLAG = 1;
	public static final int UPDATABLE_FLAG = 2;
	public static final int DELETABLE_FLAG = 3;
	public static final int REFERENCE_FLAG = 4;
}