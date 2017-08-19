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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Bnn0047FarmListErrorTest {
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
		IE_VN_TEST,IE_JP_TEST, IE_EN_TEST,
		FF_VN_TEST, FF_JP_TEST, FF_EN_TEST,
		CHR_VN_TEST, CHR_JP_TEST,CHR_EN_TEST,
		EDGE_VN_TEST, EDGE_JP_TEST, EDGE_EN_TEST
	};

	// define test mode image path here
	private final String IE_VN_IMG_PATH = "selenium_test_images/0047/Error/IE/vn/";
	private final String IE_JP_IMG_PATH = "selenium_test_images/0047/Error/IE/jp/";
	private final String IE_EN_IMG_PATH = "selenium_test_images/0047/Error/IE/en/";
	private final String FF_VN_IMG_PATH = "selenium_test_images/0047/Error/Firefox/vn/";
	private final String FF_JP_IMG_PATH = "selenium_test_images/0047/Error/Firefox/jp/";
	private final String FF_EN_IMG_PATH = "selenium_test_images/0047/Error/Firefox/en/";
	private final String CHR_VN_IMG_PATH = "selenium_test_images/0047/Error/Chrome/vn/";
	private final String CHR_JP_IMG_PATH = "selenium_test_images/0047/Error/Chrome/jp/";
	private final String CHR_EN_IMG_PATH = "selenium_test_images/0047/Error/Chrome/en/";
	private final String EDGE_VN_IMG_PATH = "selenium_test_images/0047/Error/Edge/vn/";
	private final String EDGE_JP_IMG_PATH = "selenium_test_images/0047/Error/Edge/jp/";
	private final String EDGE_EN_IMG_PATH = "selenium_test_images/0047/Error/Edge/en/";
	private String[] testImagePathArray = {
		IE_VN_IMG_PATH, IE_JP_IMG_PATH, IE_EN_IMG_PATH,
		FF_VN_IMG_PATH, FF_JP_IMG_PATH, FF_EN_IMG_PATH,
		CHR_VN_IMG_PATH, CHR_JP_IMG_PATH, CHR_EN_IMG_PATH,
		EDGE_VN_IMG_PATH, EDGE_JP_IMG_PATH, EDGE_EN_IMG_PATH,
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
		baseUrl = "http://localhost:8080";
	}

	@Test
	public void testBnn0047FarmListSeleniumError() throws Exception {
		
		String testMode = "";
		String imagePath = "";
		String pageLanguage = "";
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
			driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);

			// get image path
			imagePath = testImagePathArray[i];

			// set page language
			if (testMode.equals(IE_VN_TEST) || testMode.equals(FF_VN_TEST)
					|| testMode.equals(CHR_VN_TEST) || testMode.equals(EDGE_VN_TEST)) {
				// Vietnamese
				pageLanguage = "/ict/?language=vi";
			} else if (testMode.equals(IE_JP_TEST) || testMode.equals(FF_JP_TEST)
					|| testMode.equals(CHR_JP_TEST) || testMode.equals(EDGE_JP_TEST)) {
				// Japanese
				pageLanguage = "/ict/?language=jp";
			} else if (testMode.equals(IE_EN_TEST) || testMode.equals(FF_EN_TEST)
					|| testMode.equals(CHR_EN_TEST) || testMode.equals(EDGE_EN_TEST)) {
				// English
				pageLanguage = "/ict/?language=en";
			}
			driver.get(baseUrl + pageLanguage);

			SeleniumCommon.enableDBServerConnection("BANANA_DB_HUNG_SELENIUM", "Hung");
			// login
			// start testing
			// input user name and password
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("U0000000002");
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("U0000000002");
		    driver.findElement(By.id("btnLogin")).click();
		    driver.findElement(By.id("btnTaskManagement")).click();
	   		
			// Error button Change
			// Sessions Error
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0047_3_1_1_1.log");
			SeleniumCommon.sessionTimeOut();
			driver.findElement(By.id("F001")).click();
			capture(imagePath + "0047_3_1_1_1");
			driver.get(baseUrl + pageLanguage);
  			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
 		    driver.findElement(By.id("j_username")).clear();
 		    driver.findElement(By.id("j_username")).sendKeys("U0000000002");
 		    driver.findElement(By.id("j_password")).clear();
 		    driver.findElement(By.id("j_password")).sendKeys("U0000000002");
 		    driver.findElement(By.id("btnLogin")).click();
 		    driver.findElement(By.id("btnTaskManagement")).click();
			
			// Connections internet Error
			SeleniumCommon.disableClientConnection();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0047_3_2_1_1.log");
 			driver.findElement(By.id("F001")).click();
	  		capture(imagePath + "0047_3_2_1_1");
	  		driver.findElement(By.id("popup_ok")).click();
	  		SeleniumCommon.enableClientConnection();
	  		
	  		driver.get(baseUrl + pageLanguage);
  			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
 		    driver.findElement(By.id("j_username")).clear();
 		    driver.findElement(By.id("j_username")).sendKeys("U0000000002");
 		    driver.findElement(By.id("j_password")).clear();
 		    driver.findElement(By.id("j_password")).sendKeys("U0000000002");
 		    driver.findElement(By.id("btnLogin")).click();
 		    driver.findElement(By.id("btnTaskManagement")).click();
			
	  		// Connections DB Error
			SeleniumCommon.disableDBServerConnection("BANANA_DB_HUNG_SELENIUM", "Hung");
	  		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0047_3_3_1_1.log");
 			driver.findElement(By.id("F001")).click();
	  		capture(imagePath + "0047_3_3_1_1");
	  		driver.findElement(By.id("popup_ok")).click();
 			SeleniumCommon.enableDBServerConnection("BANANA_DB_HUNG_SELENIUM", "Hung");

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

