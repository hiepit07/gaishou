package vn.bananavietnam.ict.selenium;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

@RunWith(Parameterized.class)
public class IvbTProduct {
	private static final String BANANA_DB_IP = "172.16.0.175";
	private static final String BANANA_DB_ROOT = "root";
	private static final String BANANA_DB_ROOT_PASSWORD = "B@nana2016";
	private static final String MY_SQL_SHORTCUT_FILE = "mysql.exe.lnk";

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "U0000000001", "U0000000001" } });
	}

	@Parameter // first data value (0) is default
	public String fName;

	@Parameter(1)
	public String fPass;

	public void common() throws Exception {

		// dump data to file
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case1/PRODUCT_DATA_BEFORE", "IVB_T_PRODUCT");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case1/CULTIVATION_RESULT_DATA_BEFORE", "IVB_T_CULTIVATION_RESULT");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case1/CULTIVATION_RESULT_SUPPLEMENTARY_DATA_BEFORE", "IVB_T_CULTIVATION_RESULT_SUPPLEMENTARY");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case1/BLOCK_HARVEST_DATA_BEFORE", "IVB_T_BLOCK_HARVEST");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case1/BLOCK_HARVEST_SCHEDULES_AMOUNT_DATA_BEFORE", "IVB_T_BLOCK_HARVEST_SCHEDULES_AMOUNT");
	    Thread.sleep(5000);
		callStoredProcedure("BANANA_DB_HIEU", "U0000000001", "F001", "A001");
	    Thread.sleep(5000);
	    SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case1/PRODUCT_DATA_AFTER", "IVB_T_PRODUCT");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case1/CULTIVATION_RESULT_DATA_AFTER", "IVB_T_CULTIVATION_RESULT");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case1/CULTIVATION_RESULT_SUPPLEMENTARY_DATA_AFTER", "IVB_T_CULTIVATION_RESULT_SUPPLEMENTARY");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case1/BLOCK_HARVEST_DATA_AFTER", "IVB_T_BLOCK_HARVEST");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case1/BLOCK_HARVEST_SCHEDULES_AMOUNT_DATA_AFTER", "IVB_T_BLOCK_HARVEST_SCHEDULES_AMOUNT");
	    Thread.sleep(5000);
		
	    // dump data to file
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case2/PRODUCT_DATA_BEFORE", "IVB_T_PRODUCT");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case2/CULTIVATION_RESULT_DATA_BEFORE", "IVB_T_CULTIVATION_RESULT");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case2/CULTIVATION_RESULT_SUPPLEMENTARY_DATA_BEFORE", "IVB_T_CULTIVATION_RESULT_SUPPLEMENTARY");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case2/BLOCK_HARVEST_DATA_BEFORE", "IVB_T_BLOCK_HARVEST");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case2/BLOCK_HARVEST_SCHEDULES_AMOUNT_DATA_BEFORE", "IVB_T_BLOCK_HARVEST_SCHEDULES_AMOUNT");
	    Thread.sleep(5000);
		callStoredProcedure("BANANA_DB_HIEU", "U0000000001", "F001", "A001");
	    Thread.sleep(5000);
	    SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case2/PRODUCT_DATA_AFTER", "IVB_T_PRODUCT");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case2/CULTIVATION_RESULT_DATA_AFTER", "IVB_T_CULTIVATION_RESULT");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case2/CULTIVATION_RESULT_SUPPLEMENTARY_DATA_AFTER", "IVB_T_CULTIVATION_RESULT_SUPPLEMENTARY");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case2/BLOCK_HARVEST_DATA_AFTER", "IVB_T_BLOCK_HARVEST");
	    Thread.sleep(5000);
		SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/IvbTProduct/Success/Case2/BLOCK_HARVEST_SCHEDULES_AMOUNT_DATA_AFTER", "IVB_T_BLOCK_HARVEST_SCHEDULES_AMOUNT");
	    Thread.sleep(5000);
	}

	@Test
	public void testBnn0061BananaMenuIeVi() throws Exception {
		common();
	}

	/**
	 * call stored procedure
	 * 
	 * @param databaseName : name of database
	 * @param userId : user id
	 * @param farmId : farm id
	 * @param areaId : area id
	 * @return true/false : operation successful/failed
	 */
	public static boolean callStoredProcedure(String databaseName, String userId, String farmId, String areaId) {
		// start dumping data
	    try {
	    	String commandString = MY_SQL_SHORTCUT_FILE + " -u " + BANANA_DB_ROOT + " -p"
	    			+ BANANA_DB_ROOT_PASSWORD + " -h " + BANANA_DB_IP + " " + databaseName + " -e "
	    			+ "\"CALL `UPDATE_PRODUCT_PREVIOUS_ROUND_BY_FARM_AND_AREA`('" + userId + "', '" + farmId + "', '" + areaId + "', @result)\"";
	    	ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "start", "/wait", "cmd.exe", "/c", commandString);
	    	Process p = pb.start();
	    	System.out.println("Call stored procedure started: " + commandString);
	    	System.out.println("Result code: " + p.waitFor());
		    Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Call stored procedure failed");
			return false;
		}
	    return true;
	}
}