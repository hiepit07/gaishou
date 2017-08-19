package OT;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import vn.bananavietnam.ict.selenium.javaRobot;

public class OTCommon {
	private static final String baseUrl = "http://172.16.0.130:8080";
	private static final String BANANA_DB_IP = "172.16.0.175";
	private static final String BANANA_DB_ROOT = "root";
	private static final String BANANA_DB_ROOT_PASSWORD = "B@nana2016";
	private static final String MY_SQL_SHORTCUT_FILE = "mysql.exe.lnk";

	/**
	 * call UPDATE_PRODUCT_PREVIOUS_ROUND_BY_FARM_AND_AREA
	 */
	public static boolean callStoreUPDATE(String databaseName, String usersId, String farmId, String areaId) {	
		// start dumping data
	    try {
	    	String commandString = MY_SQL_SHORTCUT_FILE + " -u " + BANANA_DB_ROOT + " -p" + BANANA_DB_ROOT_PASSWORD
	    			+ " -h " + BANANA_DB_IP + " " + databaseName + " -e "
	    			+ "\"SELECT FARM_ID into @farmId FROM IVB_M_FARM WHERE FARM_NAME = '"+farmId+"';SELECT A.AREA_ID into @areaId FROM IVB_M_AREA A JOIN IVB_M_FARM F ON F.FARM_ID = A.FARM_ID WHERE F.FARM_NAME = '"+farmId+"' AND A.AREA_NAME = '"+areaId+"';CALL UPDATE_PRODUCT_PREVIOUS_ROUND_BY_FARM_AND_AREA('"+usersId+"', @farmId, @areaId, @resultHistory, @resultCode);\"";
	    	ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "/wait", "cmd.exe", "/c", commandString);
	    	Process p = pb.start();
	    	Thread.sleep(3000);
	    	/*javaRobot javaRobot = new javaRobot(); */
	    	
	    	System.out.println("Data dumping started: " + commandString);
	    	System.out.println("Result code: " + p.waitFor());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Data dumping failed");
			return false;
		}
	    return true;
	}
	
	/**3
	 * call EXPORT_IVB_T_CULTIVATION_RESULT_DATA_CSV
	 */
	public static boolean callStoreEXPORT(String databaseName, String exportPatch) {	
		// start dumping data
	    try {
	    	String commandString = MY_SQL_SHORTCUT_FILE + " -u " + BANANA_DB_ROOT + " -p" + BANANA_DB_ROOT_PASSWORD
	    			+ " -h " + BANANA_DB_IP + " " + databaseName + " -e "
	    			+ "\"CALL EXPORT_IVB_T_CULTIVATION_RESULT_DATA_CSV('" + exportPatch + "', @resultHistory, @resultCode);\"";
	    	ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "/wait", "cmd.exe", "/c", commandString);
	    	Process p = pb.start();
	    	System.out.println("Data dumping started: " + commandString);
	    	System.out.println("Result code: " + p.waitFor());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Data dumping failed");
			return false;
		}
	    return true;
	}
	
	/**
	 * scroll and capture
	 */
	public static void scrollVSCapture(WebDriver driver, String imagePath, String tableDiv, String lastElem) {	
		String script = "";		    	
    	script = "var divBody = document.getElementById('" + tableDiv + "'); var docViewTop = divBody.scrollTop; var docViewBottom = docViewTop + divBody.offsetHeight; var elem = " + lastElem + "; var elemTop = elem.offsetTop; var elemBottom = elemTop + elem.offsetHeight; return ((elemBottom <= docViewBottom) && (elemTop >= docViewTop));";
 
	    boolean visible = (Boolean) ((RemoteWebDriver) driver).executeScript(script);
    	int scrollIndex = 0;
	    capture(driver, imagePath + "_" + scrollIndex);
	    while (!visible) {
	    	scrollIndex++;
	    	((RemoteWebDriver) driver).executeScript("var divBody = document.getElementById('" + tableDiv + "'); divBody.scrollTop = divBody.scrollTop + divBody.offsetHeight;");
	    	visible = (Boolean) ((RemoteWebDriver) driver).executeScript(script);
	    	capture(driver, imagePath + "_" + scrollIndex);
	    }
	}
	
	public static boolean capture(WebDriver driver, String fileName) {
		File file = new File(fileName + ".png");
		String folder = FilenameUtils.getFullPathNoEndSeparator(file.getAbsolutePath());
		File theDir = new File(folder);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			boolean result = false;
			try {
				theDir.mkdirs();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

		try {
			File f = new File(fileName + ".png");
			File oFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			BufferedImage image = ImageIO.read(oFile);
			ImageIO.write(image, "png", f);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String getBaseurl() {
		return baseUrl;
	}
}
