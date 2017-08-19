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

public class Bnn0003SearchUserSeleniumTest {
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
		CHR_VN_TEST, CHR_JP_TEST, CHR_EN_TEST,
	};

	// define test mode image path here
	private final String IE_VN_IMG_PATH = "selenium_test_images/0003/Success/IE/vn/";
	private final String IE_JP_IMG_PATH = "selenium_test_images/0003/Success/IE/jp/";
	private final String IE_EN_IMG_PATH = "selenium_test_images/0003/Success/IE/en/";
	private final String FF_VN_IMG_PATH = "selenium_test_images/0003/Success/Firefox/vn/";
	private final String FF_JP_IMG_PATH = "selenium_test_images/0003/Success/Firefox/jp/";
	private final String FF_EN_IMG_PATH = "selenium_test_images/0003/Success/Firefox/en/";
	private final String CHR_VN_IMG_PATH = "selenium_test_images/0003/Success/Chrome/vn/";
	private final String CHR_JP_IMG_PATH = "selenium_test_images/0003/Success/Chrome/jp/";
	private final String CHR_EN_IMG_PATH = "selenium_test_images/0003/Success/Chrome/en/";
	private String[] testImagePathArray = {
		IE_VN_IMG_PATH, IE_JP_IMG_PATH, IE_EN_IMG_PATH,
		FF_VN_IMG_PATH, FF_JP_IMG_PATH, FF_EN_IMG_PATH,
		CHR_VN_IMG_PATH, CHR_JP_IMG_PATH, CHR_EN_IMG_PATH,
	};

	// define log evidence array here
	ArrayList<Long> checkPointArray;
	ArrayList<String> logFileNameArray;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "webdriver/geckodriver.exe");
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");

		// set test URL
		baseUrl = "http://localhost:8080"; 
	}

	@Test
	public void testBnn000003SearchUserSelenium() throws Exception {
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

			// Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_HUNG_SELENIUM", "E:/" + imagePath + "DATA_BEFORE", "IVB_M_USERS");
			
			// start testing
			// Thread.sleep(3000);
			// input user name and password
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("U0000000001");
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("U0000000001");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnLogin")).click();
		    // Thread.sleep(3000);
		    
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_1_1_1_1.log");
		    
		    // Thread.sleep(1000);
            driver.findElement(By.id("btnUserScreen")).click();
		    // Thread.sleep(1000);
		    capture(imagePath + "0003_1_1_1_1");
		    // check text field
		    // Thread.sleep(1000);
		    driver.findElement(By.id("txtUsersId")).clear();
		    driver.findElement(By.id("txtUsersId")).sendKeys("11111111111");
		    // Thread.sleep(1000);
		    capture(imagePath + "0003_1_1_1_1_2");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("txtUsersName")).clear();
		    driver.findElement(By.id("txtUsersName")).sendKeys("1111111111111111111111111111111111111111");
		    // Thread.sleep(1000);
		    capture(imagePath + "0003_1_1_1_4");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("txtUsersId")).clear();
		    driver.findElement(By.id("txtUsersId")).sendKeys("");
		    driver.findElement(By.id("txtUsersName")).clear();
		    driver.findElement(By.id("txtUsersName")).sendKeys("");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnSearch")).click();
		    // check button >, >>, <, <<, Go
		    // Thread.sleep(3000);
		    
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_8_1_1_1.log");
			// Thread.sleep(1000);
		    
		    driver.findElement(By.id("btnLast")).click();
		    // Thread.sleep(1000);
		    capture(imagePath + "0003_8_1_1_1");
		    
		    // Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_5_1_1_1.log");
			// Thread.sleep(1000);
		    driver.findElement(By.id("btnFirst")).click();
		    // Thread.sleep(1000);
		    capture(imagePath + "0003_5_1_1_1");
		    
		    // Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_7_1_1_1.log");
		    
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnNext")).click();
		    // Thread.sleep(1000);
		    capture(imagePath + "0003_7_1_1_1");
		    
		    // Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_6_1_1_1.log");
		    
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnPrevious")).click();
		    // Thread.sleep(1000);
		    capture(imagePath + "0003_6_1_1_1");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("txtGoToPage")).clear();
		    driver.findElement(By.id("txtGoToPage")).sendKeys("2");
		    // Thread.sleep(1000);
		    capture(imagePath + "0003_9_3_1_1");
		    
		    // Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_10_1_1_1.log");
			
			// Thread.sleep(1000);
		    driver.findElement(By.id("btnGoToPage")).click();
		    // Thread.sleep(1000);
		    capture(imagePath + "0003_10_1_1_1");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("txtGoToPage")).clear();
		    driver.findElement(By.id("txtGoToPage")).sendKeys("1");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnGoToPage")).click();
		    // Thread.sleep(1000);
		    // search Data
		    driver.findElement(By.id("txtUsersName")).clear();
		    driver.findElement(By.id("txtUsersName")).sendKeys("H");
		    
		    // Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_2_1_1_1.log");
		    
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnSearch")).click();
		    // Thread.sleep(1000);
		    capture(imagePath + "0003_2_1_1_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		// Thread.sleep(1000);
	   		driver.findElement(By.id("txtUsersName")).clear();
	   		driver.findElement(By.id("txtUsersName")).sendKeys("H_data");
	   		
	   		// Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_2_1_3_1.log");
	   		
			// Thread.sleep(1000);
	   		driver.findElement(By.id("btnSearch")).click();
	   		// Thread.sleep(1000);
	   		driver.findElement(By.id("popup_ok")).click();
	   		capture(imagePath + "0003_2_1_3_1");
	   		// Thread.sleep(1000);
	   		driver.findElement(By.id("txtUsersName")).clear();
	   		driver.findElement(By.id("txtUsersName")).sendKeys("");
	   		// Thread.sleep(1000);
	   		driver.findElement(By.id("btnSearch")).click();
	   		// Thread.sleep(1000);
	   		
	   		/*// Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_11_1_1_1.log");
			// Thread.sleep(1000);*/
			
			// Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_HUNG_SELENIUM", "E:/" + imagePath + "DATA_BEFORE_INSERT", "IVB_M_USERS");
			
			
	   		driver.findElement(By.id("btnRegister")).click();
	   		// Thread.sleep(1000);
	   		capture(imagePath + "0003_11_1_1_1");
	   		// Thread.sleep(1000); 
	   		// random register one record
	   		String userName = "U";
	   		int randomNum = 0;
   			if(testMode.equals(IE_VN_TEST)) {
   				randomNum = 11000 + (int)(Math.random() * 12000);
		    }else if(testMode.equals(IE_EN_TEST)) {
		    	randomNum = 12001 + (int)(Math.random() * 13000);
		    }else if(testMode.equals(IE_JP_TEST)) {
		    	randomNum = 13001 + (int)(Math.random() * 14000);
		    }
		    else if(testMode.equals(CHR_VN_TEST)) {
   				randomNum = 15001 + (int)(Math.random() * 16000);
		    }else if(testMode.equals(CHR_EN_TEST)) {
		    	randomNum = 16001 + (int)(Math.random() * 17000);
		    }else if(testMode.equals(CHR_JP_TEST)) {
		    	randomNum = 17001 + (int)(Math.random() * 18000);
		    }
		    else if(testMode.equals(FF_VN_TEST)) {
   				randomNum = 18001 + (int)(Math.random() * 19000);
		    }else if(testMode.equals(FF_EN_TEST)) {
		    	randomNum = 19001 + (int)(Math.random() * 20000);
		    }else if(testMode.equals(FF_JP_TEST)) {
		    	randomNum = 20001 + (int)(Math.random() * 21000);
		    }
	   		userName = userName + String.valueOf(randomNum) + "00000";
	   		// Thread.sleep(1000);
	   		driver.findElement(By.id("txtUsersIdPopup")).clear();
	   		driver.findElement(By.id("txtUsersIdPopup")).sendKeys(userName);
	   		driver.findElement(By.id("txtUsersNamePopup")).clear();
	   		driver.findElement(By.id("txtUsersNamePopup")).sendKeys();
	   		driver.findElement(By.id("txtPasswordPopup")).clear();
	   		driver.findElement(By.id("txtPasswordPopup")).sendKeys();
	   		// Thread.sleep(1000);
	   		
	   		/*// Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_11_3_2_1.log");
			// Thread.sleep(1000);*/
	   		
	   		driver.findElement(By.id("btnRegisterPopup")).click();
	   		// Thread.sleep(1000);
	   		capture(imagePath + "0003_11_3_2_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		    driver.findElement(By.id("popup_ok")).click(); 
		    // Thread.sleep(1000);
		    driver.findElement(By.id("txtUsersIdPopup")).clear();
	   		driver.findElement(By.id("txtUsersIdPopup")).sendKeys("U0000000001");
	   		driver.findElement(By.id("txtUsersNamePopup")).clear();
	   		driver.findElement(By.id("txtUsersNamePopup")).sendKeys("U0000000001");
	   		driver.findElement(By.id("txtPasswordPopup")).clear();
	   		driver.findElement(By.id("txtPasswordPopup")).sendKeys("U0000000001");
	   		// Thread.sleep(1000);
	   		
	   		// Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_11_3_3_1.log");
			// Thread.sleep(1000);
	   		
	   		driver.findElement(By.id("btnRegisterPopup")).click();
	   		// Thread.sleep(1000);
	   		capture(imagePath + "0003_11_3_3_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		    driver.findElement(By.id("popup_ok")).click();
		    // Thread.sleep(1000);
	   		driver.findElement(By.id("txtUsersIdPopup")).clear();
	   		driver.findElement(By.id("txtUsersIdPopup")).sendKeys(userName);
	   		driver.findElement(By.id("txtUsersNamePopup")).clear();
	   		driver.findElement(By.id("txtUsersNamePopup")).sendKeys(userName);
	   		driver.findElement(By.id("txtPasswordPopup")).clear();
	   		driver.findElement(By.id("txtPasswordPopup")).sendKeys(userName);
	   		// Thread.sleep(1000);
	   		
	   		// Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_11_3_1_1.log");
			// Thread.sleep(1000);
	   		
	   		driver.findElement(By.id("btnRegisterPopup")).click();
	   		// Thread.sleep(1000);
	   		capture(imagePath + "0003_11_3_1_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		
	   		// Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_HUNG_SELENIUM", "E:/" + imagePath + "DATA_AFTER_INSERT", "IVB_M_USERS");
			
	   		
	   		driver.findElement(By.id("popup_ok")).click();
	   		// Thread.sleep(1000);
	   		driver.findElement(By.id("txtGoToPage")).clear();
		    driver.findElement(By.id("txtGoToPage")).sendKeys("4");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnGoToPage")).click();
		    // Thread.sleep(1000);
		    
		    // Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_3_1_1_1.log");
			// Thread.sleep(1000);
		    
			// Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_HUNG_SELENIUM", "E:/" + imagePath + "DATA_BEFORE_UPDATE", "IVB_M_USERS");
			
			
	   		//edit
	   		driver.findElement(By.cssSelector("#row0 .edit")).click();
	   		capture(imagePath + "0003_3_1_1_1");
	   		// Thread.sleep(1000);
	   		driver.findElement(By.id("txtUsersNamePopup")).clear();
	   		driver.findElement(By.id("txtUsersNamePopup")).sendKeys("");
	   		driver.findElement(By.id("txtPasswordPopup")).clear();
	   		driver.findElement(By.id("txtPasswordPopup")).sendKeys("");
	   		// Thread.sleep(1000);
	   		
	   		/*// Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_3_3_2_1.log");
			// Thread.sleep(1000);*/
	   		
	   		driver.findElement(By.id("btnRegisterPopup")).click();
	   		// Thread.sleep(1000);
	   		capture(imagePath + "0003_3_3_2_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		driver.findElement(By.id("popup_ok")).click();
	   		// Thread.sleep(1000);
	   		// click button confirm on popup
	   		driver.findElement(By.id("btnCancelPopup")).click();
	   		
	   		// Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_3_3_1_1.log");
			// Thread.sleep(1000);
	   		
	   		driver.findElement(By.cssSelector("#row0 .edit")).click();
	   		// Thread.sleep(1000);
	   		driver.findElement(By.id("txtUsersNamePopup")).clear();
	   		driver.findElement(By.id("txtUsersNamePopup")).sendKeys("UPDATE");
	   		// Thread.sleep(1000);
	   		driver.findElement(By.id("btnRegisterPopup")).click();
	   		// Thread.sleep(1000);
	   		capture(imagePath + "0003_3_3_1_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		// Thread.sleep(1000);
	   		driver.findElement(By.id("popup_ok")).click();
	   		// Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_HUNG_SELENIUM", "E:/" + imagePath + "DATA_AFTER_UPDATE", "IVB_M_USERS");
			
	   		// Thread.sleep(1000);
	   		driver.findElement(By.id("txtGoToPage")).clear();
		    driver.findElement(By.id("txtGoToPage")).sendKeys("4");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnGoToPage")).click();
	   		
		    // Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_4_1_1_1.log");
			// Thread.sleep(1000);
		    
			// Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_HUNG_SELENIUM", "E:/" + imagePath + "DATA_BEFORE_DELETE", "IVB_M_USERS");
			
			
	   		//delete
	   		// Thread.sleep(500);
	   		driver.findElement(By.cssSelector("#row0 .delete")).click();
	   		// Thread.sleep(1000);
	   		capture(imagePath + "0003_4_1_1_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		// Thread.sleep(1000);
	   		driver.findElement(By.id("popup_cancel")).click();
	   		capture(imagePath + "0003_4_3_1_1");
	   		
	   		/*// Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_4_2_1_1.log");
			// Thread.sleep(1000);*/
	   		
	   		driver.findElement(By.cssSelector("#row0 .delete")).click();
	   		// Thread.sleep(1000);
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		driver.findElement(By.id("popup_ok")).click();
	   		// Thread.sleep(1000);
	   		capture(imagePath + "0003_4_2_1_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		// Thread.sleep(1000);
	   		driver.findElement(By.id("popup_ok")).click();
	   		// Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_HUNG_SELENIUM", "E:/" + imagePath + "DATA_AFTER_DELETE", "IVB_M_USERS");
			

	   		// Thread.sleep(3000);
		    checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0003_12_1_1_1.log");
			// Thread.sleep(1000);
	   		
		    // back button
		    driver.findElement(By.id("btnBack")).click();
		    // Thread.sleep(1000);
		    capture(imagePath + "0003_12_1_1_1");
		    // Thread.sleep(1000);
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		SeleniumCommon.startPrintingLog(checkPointArray, logFileNameArray);
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
