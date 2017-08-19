package vn.bananavietnam.ict.selenium;


import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public class Bnn0045SearchCultivationSeleniumTest {
	private WebDriver driver;
	private String baseUrl;

	// define test mode here
	private final String IE_VN_TEST = "1";
	private final String IE_JP_TEST = "2";
	private final String IE_EN_TEST = "3";
	private final String FF_VN_TEST = "4";
	private final String FF_JP_TEST = "5";
	private final String FF_EN_TEST = "6";
	private final String CHR_VN_TEST = "7";
	private final String CHR_JP_TEST = "8";
	private final String CHR_EN_TEST = "9";
	private String[] testModeArray = {
		IE_VN_TEST, IE_JP_TEST, IE_EN_TEST,
		FF_VN_TEST, FF_JP_TEST, FF_EN_TEST,
		CHR_VN_TEST, CHR_JP_TEST, CHR_EN_TEST
	};

	// define test mode image path here
	private final String IE_VN_IMG_PATH = "selenium_test_images/0045/Success/IE/vn/";
	private final String IE_JP_IMG_PATH = "selenium_test_images/0045/Success/IE/jp/";
	private final String IE_EN_IMG_PATH = "selenium_test_images/0045/Success/IE/en/";
	private final String FF_VN_IMG_PATH = "selenium_test_images/0045/Success/Firefox/vn/";
	private final String FF_JP_IMG_PATH = "selenium_test_images/0045/Success/Firefox/jp/";
	private final String FF_EN_IMG_PATH = "selenium_test_images/0045/Success/Firefox/en/";
	private final String CHR_VN_IMG_PATH = "selenium_test_images/0045/Success/Chrome/vn/";
	private final String CHR_JP_IMG_PATH = "selenium_test_images/0045/Success/Chrome/jp/";
	private final String CHR_EN_IMG_PATH = "selenium_test_images/0045/Success/Chrome/en/";
	private String[] testImagePathArray = {
		IE_VN_IMG_PATH, IE_JP_IMG_PATH, IE_EN_IMG_PATH,
		FF_VN_IMG_PATH, FF_JP_IMG_PATH, FF_EN_IMG_PATH,
		CHR_VN_IMG_PATH, CHR_JP_IMG_PATH, CHR_EN_IMG_PATH,
	};

	// define log evidence array here 
	ArrayList<Long> checkPointArray;
	ArrayList<String> logFileNameArray;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "webdriver/geckodriver.exe");
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");

		// set test URL
		baseUrl = "http://localhost:8080";
	}

	@Test
	public void testBnn0045SearchCultivationSelenium() throws Exception {
		
		String testMode = "";
		String imagePath = "";
		checkPointArray = new ArrayList<Long>();
		logFileNameArray = new ArrayList<String>();

		// loop through all modes
		for (int i = 0; i < testModeArray.length; i++) {
			// get test mode
			testMode = testModeArray[i];

			// setup driver
			if (testMode.equals(IE_VN_TEST) || testMode.equals(IE_JP_TEST)
					|| testMode.equals(IE_EN_TEST)) {
				// IE setup
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ignore");
				driver = new InternetExplorerDriver(ieCapabilities);
			} else if (testMode.equals(FF_VN_TEST) || testMode.equals(FF_JP_TEST)
					|| testMode.equals(FF_EN_TEST)) {
				// Firefox setup
				driver = new FirefoxDriver();
			} else if (testMode.equals(CHR_VN_TEST) || testMode.equals(CHR_JP_TEST)
					|| testMode.equals(CHR_EN_TEST)) {
				//
				driver = new ChromeDriver();
			}
			driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
			// get image path
			imagePath = testImagePathArray[i];

			// set page language
			if (testMode.equals(IE_VN_TEST) || testMode.equals(FF_VN_TEST)
					|| testMode.equals(CHR_VN_TEST)) {
				// Vietnamese
				driver.get(baseUrl + "/ict/?language=vi");
			} else if (testMode.equals(IE_JP_TEST) || testMode.equals(FF_JP_TEST)
					|| testMode.equals(CHR_JP_TEST)) {
				// Japanese
				driver.get(baseUrl + "/ict/?language=jp");
			} else if (testMode.equals(IE_EN_TEST) || testMode.equals(FF_EN_TEST)
					|| testMode.equals(CHR_EN_TEST)) {
				// English
				driver.get(baseUrl + "/ict/?language=en");
			}

			// dump data to file
			SeleniumCommon.dumpDataToFile("BANANA_DB_HUNG_SELENIUM", "E:/" + imagePath + "DATA_BEFORE", "IVB_M_CULTIVATION");

			// start testing
			// Thread.sleep(3000);
			// input user name and password
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("U0000000002");
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("U0000000002");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnLogin")).click();
		    
		    // Thread.sleep(3000);
 			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_2_1_1_1.log");
		    // Thread.sleep(1000);
			driver.findElement(By.id("btnTaskProcess")).click();
		    // Thread.sleep(1000);
		    capture(imagePath + "0045_1_1_1_1");
		    
		    // Thread.sleep(3000);
 			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_2_1_1_1.log");
 			// Thread.sleep(1000);
 			driver.findElement(By.id("btnSearch")).click();
	  		// Thread.sleep(1000);
	  		capture(imagePath + "0045_2_1_1_1");
	  		// Thread.sleep(1000);
	  		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	  		
	  		// Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_5_1_1_1.log");
 			// Thread.sleep(1000);
		    driver.findElement(By.id("btnNext")).click();
		    // Thread.sleep(1000);
		    capture(imagePath + "0045_5_1_1_1");
		    
		    // Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_6_1_1_1.log");
 			// Thread.sleep(1000);
		    driver.findElement(By.id("btnLast")).click();
		    // Thread.sleep(1000);
		    capture(imagePath + "0045_6_1_1_1");
		    // Thread.sleep(3000);
		    
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_4_1_1_1.log");
 			// Thread.sleep(1000);
		    driver.findElement(By.id("btnPrevious")).click();
		    // Thread.sleep(1000);
		    capture(imagePath + "0045_4_1_1_1");
		    // Thread.sleep(3000);
		    
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_3_1_1_1.log");
 			// Thread.sleep(1000);
		    driver.findElement(By.id("btnFirst")).click();
		    // Thread.sleep(1000);
		    capture(imagePath + "0045_3_1_1_1");
		    // Thread.sleep(1000);
	  		//result >0
	  		new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("Sài Gòn");
	  		// Thread.sleep(3000);
	  		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_2_1_1_5.log");
 			// Thread.sleep(1000);
	 	    driver.findElement(By.id("btnSearch")).click();
	 	    // Thread.sleep(1000);
	 	    capture(imagePath + "0045_2_1_1_5");
	   		
	   		// Thread.sleep(1000);
	   		//result =1
	   		new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("Sài Gòn");
	   		// Thread.sleep(500);
	   		new Select(driver.findElement(By.id("cbbTaskName"))).selectByVisibleText("・畝づくり");
	   		// Thread.sleep(3000);
	   		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_2_1_3_1.log");
 			// Thread.sleep(1000);
	 	    driver.findElement(By.id("btnSearch")).click();
	 	    // Thread.sleep(1000);
	   		capture(imagePath + "0045_2_1_3_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		
	   		// Thread.sleep(1000);
	   		//result =0
	   		new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("Sài Gòn 1");
	   		// Thread.sleep(500);
	   		new Select(driver.findElement(By.id("cbbTaskName"))).selectByVisibleText("・畝づくり");
	   		// Thread.sleep(3000);
	   		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0045_2_1_2_1.log");
			// Thread.sleep(1000);
	 	    driver.findElement(By.id("btnSearch")).click();
	 	   	// Thread.sleep(1000);
	   		capture(imagePath + "0045_2_1_2_1");
	   		// Thread.sleep(1000);
	   		driver.findElement(By.id("popup_ok")).click();
		    // Thread.sleep(1000);
		    
		    new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("");
	   		// Thread.sleep(500);
	   		new Select(driver.findElement(By.id("cbbTaskName"))).selectByVisibleText("");
	   		// Thread.sleep(1000);
	 	    driver.findElement(By.id("btnSearch")).click();
	 	    // Thread.sleep(1000);

		    driver.findElement(By.id("txtGoToPage")).clear();
		    driver.findElement(By.id("txtGoToPage")).sendKeys("2");
		    // Thread.sleep(1000);
		    capture(imagePath + "0045_7_3_1_1");
		    // Thread.sleep(3000);
		    
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_8_1_1_1.log");
		    driver.findElement(By.id("btnGoToPage")).click();
		    // Thread.sleep(1000);
		    capture(imagePath + "0045_8_1_1_1");
		    
		    // Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_9_1_1_1.log");
		    // Thread.sleep(1000);
		    driver.findElement(By.cssSelector("button.btn-medium")).click();
		    // Thread.sleep(1000);
		    capture(imagePath + "0045_9_1_1_1");
		    // Thread.sleep(1000);
		}
		
	}

	@After
	public void tearDown(){
		SeleniumCommon.startPrintingLog(checkPointArray, logFileNameArray);
		try {
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private boolean capture(String fileName) {
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

	
}
