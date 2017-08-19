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

public class Bnn0002ChangePasswordSeleniumErrorTest1 {
	// define log evidence array here
	ArrayList<Long> checkPointArray;
	ArrayList<String> logFileNameArray;
	private WebDriver driver;
	private String baseUrl;
	SeleniumCommon seleniumCommon = new SeleniumCommon();
	private String[] testImagePathArray = SeleniumCommon.setImagePath("0002", "Error");
	@Before 
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "webdriver/geckodriver.exe");
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");
		System.setProperty("webdriver.edge.driver", "webdriver/MicrosoftWebDriver.exe");
		// set test URL
				baseUrl = "http://localhost:8080/ict/";
	}
	@Test
	public void testBnn0002ChangePasswordSeleniumError() throws Exception {
		
		String testMode = "";
		String imagePath = "";
		String pageLanguage = "";

		// loop through all modes
		for (int i = 0; i < seleniumCommon.testModeArray.length; i++) {
			// get test mode
			testMode = seleniumCommon.testModeArray[i];

			// setup driver
			if (testMode.equals(seleniumCommon.IE_VN_TEST) || testMode.equals(seleniumCommon.IE_JP_TEST)
					|| testMode.equals(seleniumCommon.IE_EN_TEST)) {
				// IE setup
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ignore");
				driver = new InternetExplorerDriver(ieCapabilities);
			} else if (testMode.equals(seleniumCommon.FF_VN_TEST) || testMode.equals(seleniumCommon.FF_JP_TEST)
					|| testMode.equals(seleniumCommon.FF_EN_TEST)) {
				// Firefox setup
				driver = new FirefoxDriver();
			} else if (testMode.equals(seleniumCommon.CHR_VN_TEST) || testMode.equals(seleniumCommon.CHR_JP_TEST)
					|| testMode.equals(seleniumCommon.CHR_EN_TEST)) {
				//
				driver = new ChromeDriver();
			} else if (testMode.equals(seleniumCommon.EDGE_VN_TEST) || testMode.equals(seleniumCommon.EDGE_JP_TEST)
					|| testMode.equals(seleniumCommon.EDGE_EN_TEST)) {
				driver = new EdgeDriver();
			}
			driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);

			// get image path
			imagePath = testImagePathArray[i];

			// set page language
			if (testMode.equals(seleniumCommon.IE_VN_TEST) || testMode.equals(seleniumCommon.FF_VN_TEST)
					|| testMode.equals(seleniumCommon.CHR_VN_TEST) || testMode.equals(seleniumCommon.EDGE_VN_TEST)) {
				// Vietnamese
				pageLanguage = "?language=vi";
			} else if (testMode.equals(seleniumCommon.IE_JP_TEST) || testMode.equals(seleniumCommon.FF_JP_TEST)
					|| testMode.equals(seleniumCommon.CHR_JP_TEST) || testMode.equals(seleniumCommon.EDGE_JP_TEST)) {
				// Japanese
				pageLanguage = "?language=jp";
			} else if (testMode.equals(seleniumCommon.IE_EN_TEST) || testMode.equals(seleniumCommon.FF_EN_TEST)
					|| testMode.equals(seleniumCommon.CHR_EN_TEST) || testMode.equals(seleniumCommon.EDGE_EN_TEST)) {
				// English
				pageLanguage = "?language=en";
			}
			driver.get(baseUrl + pageLanguage);

			SeleniumCommon.enableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
			// login
			// start testing
			// input user name and password
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("U0000000001");
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("U0000000001");
		    driver.findElement(By.id("btnLogin")).click();
		    WebElement tmpElement= driver.findElement(By.id("linkChangePassword"));
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", tmpElement);
	   		
			// Error button Change
			// Sessions Error
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0002_2_2_1_1.log");
			SeleniumCommon.sessionTimeOut();
	   		driver.findElement(By.id("txtOldPassword")).clear();
	   		driver.findElement(By.id("txtOldPassword")).sendKeys("U0000000001");
	   		driver.findElement(By.id("txtNewPassword")).clear();
	   		driver.findElement(By.id("txtNewPassword")).sendKeys("U0000000001");
	   		driver.findElement(By.id("txtNewPasswordConfirm")).clear();
	   		driver.findElement(By.id("txtNewPasswordConfirm")).sendKeys("U0000000001");
	   		driver.findElement(By.id("btnChange")).click();
			capture(imagePath + "0002_2_2_1_1");
			driver.get(baseUrl + pageLanguage);
  			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
 		    driver.findElement(By.id("j_username")).clear();
 		    driver.findElement(By.id("j_username")).sendKeys("U0000000001");
 		    driver.findElement(By.id("j_password")).clear();
 		    driver.findElement(By.id("j_password")).sendKeys("U0000000001");
 		    driver.findElement(By.id("btnLogin")).click();
		    WebElement tmpElement2= driver.findElement(By.id("linkChangePassword"));
			JavascriptExecutor executor2 = (JavascriptExecutor)driver;
			executor2.executeScript("arguments[0].click();", tmpElement2);
			
			// Connections internet Error
			SeleniumCommon.disableClientConnection();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0002_2_3_1_1.log");
 			driver.findElement(By.id("txtOldPassword")).clear();
	   		driver.findElement(By.id("txtOldPassword")).sendKeys("U0000000001");
	   		driver.findElement(By.id("txtNewPassword")).clear();
	   		driver.findElement(By.id("txtNewPassword")).sendKeys("U0000000001");
	   		driver.findElement(By.id("txtNewPasswordConfirm")).clear();
	   		driver.findElement(By.id("txtNewPasswordConfirm")).sendKeys("U0000000001");
	   		driver.findElement(By.id("btnChange")).click();
	  		capture(imagePath + "0002_2_3_1_1");
	  		driver.findElement(By.id("popup_ok")).click();
	  		SeleniumCommon.enableClientConnection();
	  		driver.get(baseUrl + pageLanguage);
  			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
 		    driver.findElement(By.id("j_username")).clear();
 		    driver.findElement(By.id("j_username")).sendKeys("U0000000001");
 		    driver.findElement(By.id("j_password")).clear();
 		    driver.findElement(By.id("j_password")).sendKeys("U0000000001");
 		    driver.findElement(By.id("btnLogin")).click();
		    WebElement tmpElement3= driver.findElement(By.id("linkChangePassword"));
			JavascriptExecutor executor3 = (JavascriptExecutor)driver;
			executor3.executeScript("arguments[0].click();", tmpElement3);
			
			
	  		// Connections DB Error
			SeleniumCommon.disableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
	  		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
 			// store log file name
 			logFileNameArray.add(imagePath + "0002_2_4_1_1.log");
 			driver.findElement(By.id("txtOldPassword")).clear();
	   		driver.findElement(By.id("txtOldPassword")).sendKeys("U0000000001");
	   		driver.findElement(By.id("txtNewPassword")).clear();
	   		driver.findElement(By.id("txtNewPassword")).sendKeys("U0000000001");
	   		driver.findElement(By.id("txtNewPasswordConfirm")).clear();
	   		driver.findElement(By.id("txtNewPasswordConfirm")).sendKeys("U0000000001");
	   		driver.findElement(By.id("btnChange")).click();
	  		capture(imagePath + "0002_2_4_1_1");
	  		driver.findElement(By.id("popup_ok")).click();
 			SeleniumCommon.enableDBServerConnection("BANANA_DB_NGHIA", "Nghia");

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

