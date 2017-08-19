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
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Bnn0045SearchCultivationSeleniumErrorTest {
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
	private final String EDGE_VN_TEST = "10";
	private final String EDGE_JP_TEST = "11";
	private final String EDGE_EN_TEST = "12";
	private String[] testModeArray = {
		IE_VN_TEST, IE_JP_TEST, IE_EN_TEST
		/*FF_VN_TEST, FF_JP_TEST, FF_EN_TEST,*/
		/*CHR_VN_TEST, CHR_JP_TEST, CHR_EN_TEST,*/
		/*EDGE_VN_TEST, EDGE_JP_TEST, EDGE_EN_TEST*/
	};

	// define test mode image path here
	private final String IE_VN_IMG_PATH = "selenium_test_images/0045/Error/IE/vn/";
	private final String IE_JP_IMG_PATH = "selenium_test_images/0045/Error/IE/jp/";
	private final String IE_EN_IMG_PATH = "selenium_test_images/0045/Error/IE/en/";
	private final String FF_VN_IMG_PATH = "selenium_test_images/0045/Error/Firefox/vn/";
	private final String FF_JP_IMG_PATH = "selenium_test_images/0045/Error/Firefox/jp/";
	private final String FF_EN_IMG_PATH = "selenium_test_images/0045/Error/Firefox/en/";
	private final String CHR_VN_IMG_PATH = "selenium_test_images/0045/Error/Chrome/vn/";
	private final String CHR_JP_IMG_PATH = "selenium_test_images/0045/Error/Chrome/jp/";
	private final String CHR_EN_IMG_PATH = "selenium_test_images/0045/Error/Chrome/en/";
	private final String EDGE_VN_IMG_PATH = "selenium_test_images/0045/Error/Edge/vn/";
	private final String EDGE_JP_IMG_PATH = "selenium_test_images/0045/Error/Edge/jp/";
	private final String EDGE_EN_IMG_PATH = "selenium_test_images/0045/Error/Edge/en/";
	private String[] testImagePathArray = {
		IE_VN_IMG_PATH, IE_JP_IMG_PATH, IE_EN_IMG_PATH,
		FF_VN_IMG_PATH, FF_JP_IMG_PATH, FF_EN_IMG_PATH,
		CHR_VN_IMG_PATH, CHR_JP_IMG_PATH, CHR_EN_IMG_PATH,
		EDGE_VN_IMG_PATH, EDGE_JP_IMG_PATH, EDGE_EN_IMG_PATH
	};

	// define log evidence array here 
	ArrayList<Long> checkPointArray;
	ArrayList<String> logFileNameArray;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "webdriver/geckodriver.exe");
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");
		System.setProperty("webdriver.edge.driver", "webdriver/MicrosoftWebDriver.exe");

		// set test URL
		baseUrl = "http://172.16.0.126:8080";
	}

	@Test
	public void testBnn0045SearchCultivationSeleniumError() throws Exception {
		
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
			} else if (testMode.equals(EDGE_VN_TEST) || testMode.equals(EDGE_JP_TEST)
					|| testMode.equals(EDGE_EN_TEST)) {
				driver = new EdgeDriver();
			}

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			SeleniumCommon.disableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
			Thread.sleep(15000);
	  		SeleniumCommon.enableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");

			// get image path
			imagePath = testImagePathArray[i];

			// set page language
			loginBefore(testMode, baseUrl);

			driver.findElement(By.id("btnTaskProcess"));
			driver.findElement(By.id("btnTaskProcess")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));
			
			// start search Data Error
			// Error session
			SeleniumCommon.sessionTimeOut();
 			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_2_1_1_1.log");
			driver.findElement(By.id("btnSearch")).click();
 			capture(imagePath + "0045_2_1_1_1");

 			// set page language
 			loginBefore(testMode, baseUrl);

			driver.findElement(By.id("btnTaskProcess"));
			driver.findElement(By.id("btnTaskProcess")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));
 			
		    // Error connect internet
			SeleniumCommon.disableClientConnection();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_2_1_2_1.log");
 			driver.findElement(By.id("btnSearch")).click();
	  		driver.findElement(By.id("popup_ok"));
	  		capture(imagePath + "0045_2_1_2_1");
	  		driver.findElement(By.id("popup_ok")).click();
	  		SeleniumCommon.enableClientConnection();
	  		Thread.sleep(3000);
	  		driver.findElement(By.id("btnSearch")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));
	  		
	  		// Error connect DB
	  		SeleniumCommon.disableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
	  		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_2_1_3_1.log");
 			driver.findElement(By.id("btnSearch")).click();
 			driver.findElement(By.id("popup_ok"));
 			capture(imagePath + "0045_2_1_3_1");
 			driver.findElement(By.id("popup_ok")).click();
 			SeleniumCommon.enableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
 			
 			// button >,<,>>,<<
 			loginBefore(testMode, baseUrl);

			driver.findElement(By.id("btnTaskProcess"));
 			driver.findElement(By.id("btnTaskProcess")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));

 			SeleniumCommon.sessionTimeOut();
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_7_1_1_1.log");
		    driver.findElement(By.id("btnNext")).click();
		    capture(imagePath + "0045_7_1_1_1");

            // set page language
 			loginBefore(testMode, baseUrl);

			driver.findElement(By.id("btnTaskProcess"));
 			driver.findElement(By.id("btnTaskProcess")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));

			SeleniumCommon.disableClientConnection();
 			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_7_1_2_1.log");
 			driver.findElement(By.id("btnNext")).click();
	  		driver.findElement(By.id("popup_ok"));
	  		capture(imagePath + "0045_7_1_2_1");
	  		driver.findElement(By.id("popup_ok")).click();
	  		SeleniumCommon.enableClientConnection();
	  		Thread.sleep(3000);
	  		driver.findElement(By.id("btnSearch")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));

	  		SeleniumCommon.disableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
 			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_7_1_3_1.log");
 			driver.findElement(By.id("btnNext")).click();
 			driver.findElement(By.id("popup_ok"));
 			capture(imagePath + "0045_7_1_3_1");
 			driver.findElement(By.id("popup_ok")).click();
 			SeleniumCommon.enableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
 			
 			loginBefore(testMode, baseUrl);

			driver.findElement(By.id("btnTaskProcess"));
 			driver.findElement(By.id("btnTaskProcess")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));

 			SeleniumCommon.sessionTimeOut();
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_8_1_1_1.log");
		    driver.findElement(By.id("btnLast")).click();
		    capture(imagePath + "0045_8_1_1_1");

            // set page language
 			loginBefore(testMode, baseUrl);

			driver.findElement(By.id("btnTaskProcess"));
 			driver.findElement(By.id("btnTaskProcess")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));

			SeleniumCommon.disableClientConnection();
 			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_8_1_2_1.log");
 			driver.findElement(By.id("btnLast")).click();
	  		driver.findElement(By.id("popup_ok"));
	  		capture(imagePath + "0045_8_1_2_1");
	  		driver.findElement(By.id("popup_ok")).click();
	  		SeleniumCommon.enableClientConnection();
	  		Thread.sleep(3000);
	  		driver.findElement(By.id("btnSearch")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));

	  		SeleniumCommon.disableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
 			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_8_1_3_1.log");
 			driver.findElement(By.id("btnLast")).click();
 			driver.findElement(By.id("popup_ok"));
 			capture(imagePath + "0045_8_1_3_1");
 			driver.findElement(By.id("popup_ok")).click();
 			SeleniumCommon.enableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");

 			loginBefore(testMode, baseUrl);

			driver.findElement(By.id("btnTaskProcess"));
 			driver.findElement(By.id("btnTaskProcess")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));
 			driver.findElement(By.id("btnNext")).click();
			driver.findElement(By.cssSelector("span#btnPrevious[class$='page-number-pre']"));

 			SeleniumCommon.sessionTimeOut();
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_6_1_1_1.log");
		    driver.findElement(By.id("btnPrevious")).click();
	  		capture(imagePath + "0045_6_1_1_1");
            // set page language

 			loginBefore(testMode, baseUrl);

			driver.findElement(By.id("btnTaskProcess"));
 			driver.findElement(By.id("btnTaskProcess")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));
 			driver.findElement(By.id("btnNext")).click();
			driver.findElement(By.cssSelector("span#btnPrevious[class$='page-number-pre']"));

			SeleniumCommon.disableClientConnection();
 			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_6_1_2_1.log");
 			driver.findElement(By.id("btnPrevious")).click();
	  		driver.findElement(By.id("popup_ok"));
	  		capture(imagePath + "0045_6_1_2_1");
	  		driver.findElement(By.id("popup_ok")).click();
	  		SeleniumCommon.enableClientConnection();
	  		Thread.sleep(3000);

 			driver.findElement(By.id("btnSearch")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));
 			driver.findElement(By.id("btnNext")).click();
			driver.findElement(By.cssSelector("span#btnPrevious[class$='page-number-pre']"));

	  		SeleniumCommon.disableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
 			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_6_1_3_1.log");
 			driver.findElement(By.id("btnPrevious")).click();
 			driver.findElement(By.id("popup_ok"));
 			capture(imagePath + "0045_6_1_3_1");
 			driver.findElement(By.id("popup_ok")).click();
 			SeleniumCommon.enableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");

 			loginBefore(testMode, baseUrl);

			driver.findElement(By.id("btnTaskProcess"));
 			driver.findElement(By.id("btnTaskProcess")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));
 			driver.findElement(By.id("btnLast")).click();
			driver.findElement(By.cssSelector("span#btnFirst[class$='page-number-first']"));

 			SeleniumCommon.sessionTimeOut();
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_5_1_1_1.log");
		    driver.findElement(By.id("btnFirst")).click();
	  		capture(imagePath + "0045_5_1_1_1");
            // set page language

 			loginBefore(testMode, baseUrl);

			driver.findElement(By.id("btnTaskProcess"));
 			driver.findElement(By.id("btnTaskProcess")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));
 			driver.findElement(By.id("btnLast")).click();
			driver.findElement(By.cssSelector("span#btnFirst[class$='page-number-first']"));

			SeleniumCommon.disableClientConnection();
 			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_5_1_2_1.log");
 			driver.findElement(By.id("btnFirst")).click();
	  		driver.findElement(By.id("popup_ok"));
	  		capture(imagePath + "0045_5_1_2_1");
	  		driver.findElement(By.id("popup_ok")).click();
	  		SeleniumCommon.enableClientConnection();
	  		Thread.sleep(3000);

 			driver.findElement(By.id("btnSearch")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));
 			driver.findElement(By.id("btnLast")).click();
			driver.findElement(By.cssSelector("span#btnFirst[class$='page-number-first']"));

	  		SeleniumCommon.disableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
 			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_5_1_3_1.log");
 			driver.findElement(By.id("btnFirst")).click();
 			driver.findElement(By.id("popup_ok"));
 			capture(imagePath + "0045_5_1_3_1");
 			driver.findElement(By.id("popup_ok")).click();
 			SeleniumCommon.enableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
 			
 			loginBefore(testMode, baseUrl);

			driver.findElement(By.id("btnTaskProcess"));
 			driver.findElement(By.id("btnTaskProcess")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));
 			
 			SeleniumCommon.sessionTimeOut();
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_9_1_1_1.log");
		    driver.findElement(By.id("txtGoToPage")).sendKeys("2");
		    driver.findElement(By.id("btnGoToPage")).click();
		    capture(imagePath + "0045_9_1_1_1");

		    loginBefore(testMode, baseUrl);

			driver.findElement(By.id("btnTaskProcess"));
 			driver.findElement(By.id("btnTaskProcess")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));

			SeleniumCommon.disableClientConnection();
 			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_9_1_2_1.log");
		    driver.findElement(By.id("txtGoToPage")).sendKeys("2");
		    driver.findElement(By.id("btnGoToPage")).click();
	  		driver.findElement(By.id("popup_ok"));
	  		capture(imagePath + "0045_9_1_2_1");
	  		driver.findElement(By.id("popup_ok")).click();
	  		SeleniumCommon.enableClientConnection();
 			Thread.sleep(3000);

 			driver.findElement(By.id("btnSearch")).click();
 			driver.findElement(By.cssSelector("table.tbl-farm-table #row0"));

	  		SeleniumCommon.disableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
 			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0045_9_1_3_1.log");
		    driver.findElement(By.id("txtGoToPage")).sendKeys("2");
		    driver.findElement(By.id("btnGoToPage")).click();
 			driver.findElement(By.id("popup_ok"));
 			capture(imagePath + "0045_9_1_3_1");
 			driver.findElement(By.id("popup_ok")).click();
 			SeleniumCommon.enableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
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
	
	private void loginBefore(String testMode, String baseUrl){
		try{
			// open browser
			driver.get(baseUrl + "/ict/");
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
			driver.findElement(By.cssSelector("img.language"));
			// set page language
			if (testMode.equals(IE_VN_TEST) || testMode.equals(FF_VN_TEST)
					|| testMode.equals(CHR_VN_TEST) || testMode.equals(EDGE_VN_TEST)) {
				// Vietnamese
				driver.findElement(By.cssSelector("img.language[name|=vi]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Mật khẩu')]"));
			} else if (testMode.equals(IE_JP_TEST) || testMode.equals(FF_JP_TEST)
					|| testMode.equals(CHR_JP_TEST) || testMode.equals(EDGE_JP_TEST)) {
				// Japanese
				driver.findElement(By.cssSelector("img.language[name|=jp]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'パスワード')]"));
			} else if (testMode.equals(IE_EN_TEST) || testMode.equals(FF_EN_TEST)
					|| testMode.equals(CHR_EN_TEST) || testMode.equals(EDGE_EN_TEST)) {
				// English
				driver.findElement(By.cssSelector("img.language[name|=en]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Password')]"));
			}

			// login
			// start testing 
			// input user name and password
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000002");
			driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("U0000000002");
			((RemoteWebDriver) driver).executeScript("arguments[0].click()",driver.findElement(By.id("btnLogin")));
		} catch (Exception e){
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
