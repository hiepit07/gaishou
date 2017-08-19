package OT;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class OT_Store_Proceduce {
	private static WebDriver driver;
	private static final String BANANA_DB_IP = "172.16.0.175";
	private static final String BANANA_DB_ROOT = "root";
	private static final String BANANA_DB_ROOT_PASSWORD = "B@nana2016";
	private static final String MY_SQL_SHORTCUT_FILE = "mysql.exe.lnk";

	private List<Object[]> userList = Arrays.asList(new Object[][] { 
		{"U0000000003"}
	});

	private String imagePath = "selenium_test_images/OT/OT_0049_0035_0037_0045/";

	@Before
	public void setUp() {
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");
	}
	@Test
	public void testStoreProceduce() {
		
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
