package vn.bananavietnam.ict.common.cnst;

public class LoggerMessage {

	/*************************************************
	 * COMMON LOG MESSAGES 
	 *************************************************/
	// logger INFO prefix // system autogen
	public static final String LOG_INFO_PREFIX = "";//"INFO,";
	// logger WARNING prefix // system autogen
	public static final String LOG_WARNING_PREFIX = "";//"WARNING,";
	// logger ERROR prefix // system autogen
	public static final String LOG_ERROR_PREFIX = "";//"ERROR,";
	// SQL execution finished
	public static final String SQL_EXECUTION_FINISHED = "SQL execution finished";
	// create search conditions parameters started
	public static final String CREATE_SEARCH_CONDITIONS_STARTED = "Create search conditions to send to DB based on user's input information";
	// exception error message
	public static final String EXCEPTION_ERROR_MESSAGE = "java.lang.Exception: ";
	// out of memory error message
	public static final String OOM_EXCEPTION_ERROR_MESSAGE = "java.lang.OutOfMemoryError: ";
	// duplicate key error message
	public static final String DUPLICATE_KEY_EXCEPTION_ERROR_MESSAGE = "DuplicateKeyException: ";
	// blank field(s) error message
	public static final String BLANK_FIELDS_ERROR_MESSAGE = "Error message: Blank fields";
	// format not correct error message
	public static final String FORMAT_NOT_CORRECT_ERROR_MESSAGE = "Error message: $1 is in wrong format";
	// insert warning message
	public static final String CANT_INSERT_DATA = "Can't insert data";
	// delete warning message
	public static final String CANT_DELETE_DATA = "Can't delete data";
	// update warning message
	public static final String CANT_UPDATE_DATA = "Can't update data";
	// delete warning message
	public static final String CANT_DELETE_DATA_ADMIN = "This user is the last admin";
	// insert warning message last update date
	public static final String CANT_INSERT_DATA_UPDATE_DATE = "Can't insert data. This data has been changed by another user.";
	// delete warning message last update date
	public static final String CANT_DELETE_DATA_UPDATE_DATE = "Can't delete data. This data has been changed by another user.";
	// update warning message last update date
	public static final String CANT_UPDATE_DATA_UPDATE_DATE = "Can't update data. This data has been changed by another user.";
	/*************************************************
	 * Bnn0002 LOG MESSAGES 
	 *************************************************/
	// new password and new password confirm do not match
	public static final String PASSWORD_NOT_MATCH_ERROR_MESSAGE = "Error message: Passwords do not match";
	// old password not correct
	public static final String PASSWORD_NOT_CORRECT = "Error message: Old password is not correct";


	/*************************************************
	 * Bnn0003 LOG MESSAGES 
	 *************************************************/
	// user searching started
	public static final String USER_SEARCH_STARTED = "User searching started";
	// search result string
	public static final String USER_SEARCH_RESULT_STRING = "User searching finished ==> $1 item(s)";
	// search result string 0 item
	public static final String USER_SEARCH_RESULT_0_STRING = "User searching finished ==> 0 item";

	/*************************************************
	 * Bnn0005 LOG MESSAGES 
	 *************************************************/
	// farm searching started
	public static final String FARM_SEARCH_STARTED = "Farm searching started";
	// search result string
	public static final String FARM_SEARCH_RESULT_STRING = "Farm searching finished ==> $1 item(s)";
	// search result string 0 item
	public static final String FARM_SEARCH_RESULT_0_STRING = "Farm searching finished ==> 0 item";
	// farm duplicate
	public static final String ERROR_DUPLICATE_FARM_NAME = "Error message: Farm's Name is Duplicate";
	/*************************************************
	 * Bnn0007 LOG MESSAGES 
	 *************************************************/
	// get Kind data combobox
	public static final String GET_KIND_COMBOBOX = "Get Kind data for combobox";
	// get Kind data combobox finished
	public static final String GET_KIND_COMBOBOX_FINISHED = "Get Kind data for combobox finished";
	// error Format FarmId
	public static final String ERROR_FORMAT_FARM_ID = "Error message: Farm's id is in wrong format";
	// error Format AreaId
	public static final String ERROR_FORMAT_AREA_ID = "Error message: Area's id is in wrong format";
	// area searching started
    public static final String AREA_SEARCH_STARTED = "Area searching started";
    // search result string
    public static final String AREA_SEARCH_FINISHED = "Area searching finished";
    // search result string
    public static final String AREA_SEARCH_RESULT_STRING = "Area searching finished ==> $1 item(s)";
    // search result string 0 item
    public static final String AREA_SEARCH_RESULT_0_STRING = "Area searching finished ==> 0 item";
	// update area started
	public static final String AREA_UPDATE_STARTED = "Area update started";
	// update area finished
	public static final String AREA_UPDATE_FINISHED = "Update Area succeeded, committing to DB";
	// update area failed
	public static final String AREA_UPDATE_FAILED = "Update Area data failed, rolling back";
	// insert area started
	public static final String AREA_INSERT_STARTED = "Area insert started";
	// insert area finished
	public static final String AREA_INSERT_FINISHED = "Insert Area succeeded, committing to DB";
	// insert area failed
	public static final String AREA_INSERT_FAILED = "Insert Area data failed, rolling back";
	
	/*************************************************
	 * Bnn0009 LOG MESSAGES 
	 *************************************************/
	// get farm data combobox
	public static final String GET_FARM_COMBOBOX = "Get Farm data for combobox";
	// block searching started
	public static final String BLOCK_SEARCH_STARTED = "Block searching started";
	// search result string
	public static final String BLOCK_SEARCH_RESULT_STRING = "Block searching finished ==> $1 item(s)";
	// search result string 0 item
	public static final String BLOCK_SEARCH_RESULT_0_STRING = "Block searching finished ==> 0 item";
	// get area by farm id started
	public static final String GET_AREA_BY_FARM_ID = "Get Area data by Farm id, Farm id: ";
	// get block by area id
	public static final String GET_BLOCK_BY_AREA_ID = "Get Block data by Area id, Area id: ";
	// update block started
	public static final String BLOCK_UPDATE_STARTED = "Block update started";
	// update block finished
	public static final String BLOCK_UPDATE_FINISHED = "Update Block succeeded, committing to DB";
	// update block failed
	public static final String BLOCK_UPDATE_FAILED = "Update Block data failed, rolling back";
	// insert block started
	public static final String BLOCK_INSERT_STARTED = "Block insert started";
	// insert block finished
	public static final String BLOCK_INSERT_FINISHED = "Insert Block succeeded, committing to DB";
	// insert block failed
	public static final String BLOCK_INSERT_FAILED = "Insert Block data failed, rolling back";
	// insert block duplicated key
	public static final String BLOCK_INSERT_DUPLICATE_KEY = "Insert Block data failed, primary key duplicated, rolling back";
	// insert block config filee not correct
	public static final String BLOCK_INSERT_CONFIG_NOT_CORRECT = "Insert Block data failed, data in config file is incorrect, rolling back";
	// delete block started
	public static final String BLOCK_DELETE_STARTED = "Block delete started";
	// delete block finished
	public static final String BLOCK_DELETE_FINISHED = "Delete Block succeeded, committing to DB";
	// delete block failed
	public static final String BLOCK_DELETE_FAILED = "Delete Block data failed, rolling back";
	// get block by farm id, area id, block id
	public static final String GET_BLOCK_BY_ALL_ID = "Get 1 Block data by Farm id, Area id, Block id, ids: ";
	// get product by farm id, area id, block id
	public static final String GET_PRODUCT_BY_ALL_ID = "Get Product data by Farm id, Area id, Block id, ids: ";
	// update product started
	public static final String PRODUCT_UPDATE_STARTED = "Product update started";
	// update product finished
	public static final String PRODUCT_UPDATE_FINISHED = "Update Product succeeded, committing to DB";
	// update product failed
	public static final String PRODUCT_UPDATE_FAILED = "Update Product data failed, rolling back";
	// update product failed last update date
	public static final String PRODUCT_UPDATE_FAILED_UPDATE_DATE = "Update Product data failed. This data has been changed by another user.";
	// get process data combobox
	public static final String ERROR_DUPLICATE_BLOCK_NAME = "Error message: Block's Name is Duplicate";
	/*************************************************
	 * Bnn0035 LOG MESSAGES 
	 *************************************************/
	// check if user goes to this screen by entering URL directly or not
	public static final String CHECK_SCREEN_URL_DATA = "Check ids to see if user entered URL directly or not";
	// get block by area id
	public static final String GET_BLOCK_BY_FARM_ID_AREA_ID = "Get Block data by Farm id, Area id, ids: ";
	// insert cultivation result failed
	public static final String CULTIVATION_RESULT_INSERT_FAILED = "Insert Cultivation result data failed, rolling back";
	// delete cultivation result failed
	public static final String CULTIVATION_RESULT_DELETE_FAILED = "Delete Cultivation result data failed, rolling back";
	// delete cultivation result failed last update date
	public static final String CULTIVATION_RESULT_DELETE_FAILED_UPDATE_DATE = "Delete Cultivation result data failed. This data has been changed by another user.";

	/*************************************************
	 * Bnn0037 LOG MESSAGES 
	 *************************************************/
	// get process data combobox
	public static final String GET_PROCESS_COMBOBOX = "Get Process data for combobox";
	// get process data combobox
	public static final String GET_TASK_COMBOBOX = "Get Task data for combobox";
	// get status data combobox
	public static final String GET_STATUS_COMBOBOX = "Get Status data for combobox";
	// get kind data
	public static final String GET_KIND_DATA = "Get Kind data";
	// get farm's line and column count
	public static final String GET_LINE_COLUMN_COUNT = "Get data for farm's line and column count";
	// get cultivation result by farm id, area id, block id, kind id
	public static final String GET_CULTIVATION_RESULT_BY_ALL_ID = "Get Cultivation result data by Farm id, Area id, Block id, Kind id, ids: ";
	// get cultivation result by farm id, area id, block id, kind id
	public static final String PRODUCT_RESULT_UPDATE_FAILED = "Update date = null in Product";
	/*************************************************
	 * Bnn0037 LOG MESSAGES 
	 *************************************************/
	// get process data combobox
	public static final String ERROR_DUPLICATE_AREA_NAME = "Error message: Area's Name is Duplicate";
	// get block by area id
	public static final String GET_KIND_BY_AREA_ID = "Get Kind data by Kind Id, Kind Id:  ";
	/*************************************************
     * Bnn0013 LOG MESSAGES 
     *************************************************/
    // user searching started
    public static final String PROCESS_SEARCH_STARTED = "Process searching started";
    // search result string
    public static final String PROCESS_SEARCH_RESULT_STRING = "Process searching finished ==> $1 item(s)";
    // search result string 0 item
    public static final String PROCESS_SEARCH_RESULT_0_STRING = "Process searching finished ==> 0 item";
    /*************************************************
     * Bnn0017 LOG MESSAGES 
     *************************************************/
    // user searching started
    public static final String KIND_SEARCH_STARTED = "Kind searching started";
    // search result string
    public static final String KIND_SEARCH_RESULT_STRING = "Kind searching finished ==> $1 item(s)";
    // search result string 0 item
    public static final String KIND_SEARCH_RESULT_0_STRING = "Kind searching finished ==> 0 item";
    /*************************************************
     * Bnn0075 LOG MESSAGES 
     *************************************************/
    // user searching started
    public static final String SHIPPING_SEARCH_STARTED = "Shipping searching started";
    // search result string
    public static final String SHIPPING_SEARCH_RESULT_STRING = "Shipping searching finished ==> $1 item(s)";
    // search result string 0 item
    public static final String SHIPPING_SEARCH_RESULT_0_STRING = "Shipping searching finished ==> 0 item";
    /*************************************************
     * Bnn0089 LOG MESSAGES 
     *************************************************/
    // user searching started
    public static final String TASK_SEARCH_STARTED = "Task searching started";
    // search result string
    public static final String TASK_SEARCH_RESULT_STRING = "Task searching finished ==> $1 item(s)";
    // search result string 0 item
    public static final String TASK_SEARCH_RESULT_0_STRING = "Task searching finished ==> 0 item";
	/*************************************************
	 * Bnn0061 LOG MESSAGES 
	 *************************************************/
	// get data combo box
	public static final String GET_DATA_COMBOBOX = "Get data for combobox";
	// get area data combobox
	public static final String GET_AREA_COMBOBOX = "Get Area data for combobox";
	// get block data combobox
	public static final String GET_BLOCK_COMBOBOX = "Get Block data for combobox";
	// get line data combobox
	public static final String GET_LINE_COMBOBOX = "Get Line data for combobox";
	// get column data combobox
	public static final String GET_COLUMN_COMBOBOX = "Get Column data for combobox";
	// get data combo box finished
	public static final String GET_DATA_COMBOBOX_FINISHED = "Get data for combobox finished";
	// cultivation searching started
	public static final String CULTIVATION_SEARCH_STARTED = "Cultivation searching started";
	// search result string
	public static final String CULTIVATION_SEARCH_RESULT_STRING = "Cultivation searching finished ==> $1 item(s)";
	// search result string 0 item
	public static final String CULTIVATION_SEARCH_RESULT_0_STRING = "Cultivation searching finished ==> 0 item";
    
    /*************************************************
	 * Bnn0087 LOG MESSAGES 
	 *************************************************/
	// delete affiliation failed
	public static final String AFFILIATION_DELETE_ERROR = "Can't delete this record because this user is area manager";
	// affiliation searching started
	public static final String AFFILIATION_SEARCH_STARTED = "Affiliation searching started";
	// affiliation search finished
	public static final String AFFILIATION_SEARCH_FINISHED = "Affiliation searching finished";
	// search result string
	public static final String AFFILIATION_SEARCH_RESULT_STRING = "Affiliation searching finished ==> $1 item(s)";
	// search result string 0 item
	public static final String AFFILIATION_SEARCH_RESULT_0_STRING = "Affiliation searching finished ==> 0 item";
	// affiliation insert warning message
	public static final String AFFILIATION_INSERT_DATA_ERROR = "Can't delete data before insert data";

    /*************************************************
	 * Bnn0091 LOG MESSAGES 
	 *************************************************/
	// get task data for table list started
	public static final String GET_TASK_TABLE_LIST = "Get task data for table list started";
	// get task data for table list finished
	public static final String GET_TASK_TABLE_LIST_FINISHED = "Get task data for table list finished";
	// register task started
	public static final String TASK_RIGISTER_STARTED = "Task register started";
	// register task finished
	public static final String TASK_RIGISTER_FINISHED = "Register Task succeeded, committing to DB";
	// register task failed
	public static final String TASK_RIGISTER_FAILED = "Register Task data failed, rolling back";
	// unregister task started
	public static final String TASK_UNRIGISTER_STARTED = "Task unregister started";
	// unregister task finished
	public static final String TASK_UNRIGISTER_FINISHED = "Unregister Task succeeded, committing to DB";
	// unregister task failed
	public static final String TASK_UNRIGISTER_FAILED = "Unregister Task data failed, rolling back";
    
    /*************************************************
	 * Bnn0092 LOG MESSAGES 
	 *************************************************/
	// get data text box
	public static final String GET_DATA_TEXTBOX = "Get data for textbox";
	// get data text box finished
	public static final String GET_DATA_TEXTBOX_FINISHED = "Get data for textbox finished";
	// get process data for table list started
	public static final String GET_PROCESS_TABLE_LIST = "Get process data for table list started";
	// get process data for table list finished
	public static final String GET_PROCESS_TABLE_LIST_FINISHED = "Get process data for table list finished";
	// register process started
	public static final String PROCESS_RIGISTER_STARTED = "Process register started";
	// register process finished
	public static final String PROCESS_RIGISTER_FINISHED = "Register Process succeeded, committing to DB";
	// register process failed
	public static final String PROCESS_RIGISTER_FAILED = "Register Process data failed, rolling back";
	// unregister process started
	public static final String PROCESS_UNRIGISTER_STARTED = "Process unregister started";
	// unregister process finished
	public static final String PROCESS_UNRIGISTER_FINISHED = "Unregister Process succeeded, committing to DB";
	// unregister process failed
	public static final String PROCESS_UNRIGISTER_FAILED = "Unregister Process data failed, rolling back";
	
	/*************************************************
	 * Bnn0095 LOG MESSAGES 
	 *************************************************/
	// Access searching started
    public static final String ACCESS_SEARCH_STARTED = "Access Authorization searching started";
    // Access searching started
    public static final String ACCESS_SEARCH_FINISHED = "Access Authorization searching finished";
    // search result string
    public static final String ACCESS_SEARCH_RESULT_STRING = "Access searching finished ==> $1 item(s)";
    // search result string 0 item
    public static final String ACCESS_SEARCH_RESULT_0_STRING = "Access searching finished ==> 0 item";
    
    public static final String ERROR_MASSAGE = "Error message: ";
    
    public static final String ERROR_MASSAGE_SCREEN_ID = "Error message: Screen's id is in wrong format";
    
    public static final String ERROR_MASSAGE_USERS_ID = "Error message: Could not get logged in user's id";
}
