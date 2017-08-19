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

public class Bnn0023SearchUserPopupError {
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
		/*IE_VN_TEST, IE_JP_TEST, IE_EN_TEST,*/
		FF_VN_TEST/*, FF_JP_TEST, FF_EN_TEST,*/
		/*CHR_VN_TEST, CHR_JP_TEST, CHR_EN_TEST,*/
		/*EDGE_VN_TEST, EDGE_JP_TEST, EDGE_EN_TEST*/
	};

	// define test mode image path here
	private final String IE_VN_IMG_PATH = "selenium_test_images/0023/Error/IE/vn/";
	private final String IE_JP_IMG_PATH = "selenium_test_images/0023/Error/IE/jp/";
	private final String IE_EN_IMG_PATH = "selenium_test_images/0023/Error/IE/en/";
	private final String FF_VN_IMG_PATH = "selenium_test_images/0023/Error/Firefox/vn/";
	private final String FF_JP_IMG_PATH = "selenium_test_images/0023/Error/Firefox/jp/";
	private final String FF_EN_IMG_PATH = "selenium_test_images/0023/Error/Firefox/en/";
	private final String CHR_VN_IMG_PATH = "selenium_test_images/0023/Error/Chrome/vn/";
	private final String CHR_JP_IMG_PATH = "selenium_test_images/0023/Error/Chrome/jp/";
	private final String CHR_EN_IMG_PATH = "selenium_test_images/0023/Error/Chrome/en/";
	private final String EDGE_VN_IMG_PATH = "selenium_test_images/0023/Error/Edge/vn/";
	private final String EDGE_JP_IMG_PATH = "selenium_test_images/0023/Error/Edge/jp/";
	private final String EDGE_EN_IMG_PATH = "selenium_test_images/0023/Error/Edge/en/";
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
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "webdriver/geckodriver.exe");
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");
		System.setProperty("webdriver.edge.driver", "webdriver/MicrosoftWebDriver.exe");

		// set test URL
		baseUrl = "http://172.16.0.126:8080";
	}

	@Test
	public <V> void testBnn0023TraceabilityMasterSelenium() throws Exception {
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

			// get image path
			imagePath = testImagePathArray[i];
			driver.get(baseUrl + "/ict/");
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
			// set page language
			if (testMode.equals(IE_VN_TEST) || testMode.equals(FF_VN_TEST)
					|| testMode.equals(CHR_VN_TEST) || testMode.equals(EDGE_VN_TEST)) {
				// Vietnamese
				driver.findElement(By.cssSelector("img.language[name|=vi]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Mật khẩu')]"));
			} else if (testMode.equals(IE_JP_TEST) || testMode.equals(FF_JP_TEST)
					|| testMode.equals(CHR_JP_TEST) || testMode.equals(EDGE_JP_TEST)) {
				// Japanese
				driver.findElement(By.cssSelector("img.language[name|=en]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Password')]"));
			} else if (testMode.equals(IE_EN_TEST) || testMode.equals(FF_EN_TEST)
					|| testMode.equals(CHR_EN_TEST) || testMode.equals(EDGE_EN_TEST)) {
				// English
				driver.findElement(By.cssSelector("img.language[name|=jp]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'パスワード')]"));
			}

			// dump data to file
			SeleniumCommon.dumpDataToFile("BANANA_DB_HIEUDAO", "E:/" + imagePath + "DATA_BEFORE", "IVB_M_USERS");
			driver.findElement(By.id("j_username"));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000001");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000001");
			((RemoteWebDriver) driver).executeScript("arguments[0].click()",driver.findElement(By.id("btnLogin")));

			//Selenium Test Session Time Out

			driver.findElement(By.id("btnAreaScreen")).click();
			driver.findElement(By.cssSelector(".delete"));
			driver.findElement(By.id("btnRegister")).click();
			driver.findElement(By.cssSelector("div#popupWrapper[style*='display: block']"));
			driver.findElement(By.id("loadUserPopup")).click();
			SeleniumCommon.sessionTimeOut();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_1_1_2_1.log");
			driver.findElement(By.id("btnScUserPupSearch")).click();
			capture(imagePath + "/0023_1_1_2_1");
			driver.get(baseUrl + "/ict/");
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));

			// set page language
			if (testMode.equals(IE_VN_TEST) || testMode.equals(FF_VN_TEST)
					|| testMode.equals(CHR_VN_TEST) || testMode.equals(EDGE_VN_TEST)) {
				// Vietnamese
				driver.findElement(By.cssSelector("img.language[name|=vi]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Mật khẩu')]"));
			} else if (testMode.equals(IE_JP_TEST) || testMode.equals(FF_JP_TEST)
					|| testMode.equals(CHR_JP_TEST) || testMode.equals(EDGE_JP_TEST)) {
				// Japanese
				driver.findElement(By.cssSelector("img.language[name|=en]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Password')]"));
			} else if (testMode.equals(IE_EN_TEST) || testMode.equals(FF_EN_TEST)
					|| testMode.equals(CHR_EN_TEST) || testMode.equals(EDGE_EN_TEST)) {
				// English
				driver.findElement(By.cssSelector("img.language[name|=jp]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'パスワード')]"));
			}
			driver.findElement(By.id("j_username"));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000001");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000001");
			((RemoteWebDriver) driver).executeScript("arguments[0].click()",driver.findElement(By.id("btnLogin")));

			driver.findElement(By.id("btnAreaScreen")).click();
			driver.findElement(By.cssSelector(".delete"));
			driver.findElement(By.id("btnRegister")).click();
			driver.findElement(By.cssSelector("div#popupWrapper[style*='display: block']"));
			driver.findElement(By.id("loadUserPopup")).click();
			driver.findElement(By.id("btnScUserPupSearch")).click();
			driver.findElement(By.cssSelector(".tbl-user-popup-table .acRow:last-child"));
			SeleniumCommon.sessionTimeOut();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_4_1_1_1.log");
			driver.findElement(By.cssSelector("#btnScUserPupNext")).click();
			capture(imagePath + "/0023_4_1_1_1");
			driver.get(baseUrl + "/ict/");
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));

			// set page language
			if (testMode.equals(IE_VN_TEST) || testMode.equals(FF_VN_TEST)
					|| testMode.equals(CHR_VN_TEST) || testMode.equals(EDGE_VN_TEST)) {
				// Vietnamese
				driver.findElement(By.cssSelector("img.language[name|=vi]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Mật khẩu')]"));
			} else if (testMode.equals(IE_JP_TEST) || testMode.equals(FF_JP_TEST)
					|| testMode.equals(CHR_JP_TEST) || testMode.equals(EDGE_JP_TEST)) {
				// Japanese
				driver.findElement(By.cssSelector("img.language[name|=en]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Password')]"));
			} else if (testMode.equals(IE_EN_TEST) || testMode.equals(FF_EN_TEST)
					|| testMode.equals(CHR_EN_TEST) || testMode.equals(EDGE_EN_TEST)) {
				// English
				driver.findElement(By.cssSelector("img.language[name|=jp]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'パスワード')]"));
			}
			driver.findElement(By.id("j_username"));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000001");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000001");
			((RemoteWebDriver) driver).executeScript("arguments[0].click()",driver.findElement(By.id("btnLogin")));

			driver.findElement(By.id("btnAreaScreen")).click();
			driver.findElement(By.cssSelector(".delete"));
			driver.findElement(By.id("btnRegister")).click();
			driver.findElement(By.cssSelector("div#popupWrapper[style*='display: block']"));
			driver.findElement(By.id("loadUserPopup")).click();
			driver.findElement(By.id("btnScUserPupSearch")).click();
			driver.findElement(By.cssSelector(".tbl-user-popup-table .acRow:last-child"));
			SeleniumCommon.sessionTimeOut();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_5_1_1_1.log");
			driver.findElement(By.cssSelector("#btnScUserPupLast")).click();
			capture(imagePath + "/0023_5_1_1_1");
			driver.get(baseUrl + "/ict/");
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));

			// set page language
			if (testMode.equals(IE_VN_TEST) || testMode.equals(FF_VN_TEST)
					|| testMode.equals(CHR_VN_TEST) || testMode.equals(EDGE_VN_TEST)) {
				// Vietnamese
				driver.findElement(By.cssSelector("img.language[name|=vi]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Mật khẩu')]"));
			} else if (testMode.equals(IE_JP_TEST) || testMode.equals(FF_JP_TEST)
					|| testMode.equals(CHR_JP_TEST) || testMode.equals(EDGE_JP_TEST)) {
				// Japanese
				driver.findElement(By.cssSelector("img.language[name|=en]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Password')]"));
			} else if (testMode.equals(IE_EN_TEST) || testMode.equals(FF_EN_TEST)
					|| testMode.equals(CHR_EN_TEST) || testMode.equals(EDGE_EN_TEST)) {
				// English
				driver.findElement(By.cssSelector("img.language[name|=jp]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'パスワード')]"));
			}
			driver.findElement(By.id("j_username"));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000001");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000001");
			((RemoteWebDriver) driver).executeScript("arguments[0].click()",driver.findElement(By.id("btnLogin")));

			driver.findElement(By.id("btnAreaScreen")).click();
			driver.findElement(By.cssSelector(".delete"));
			driver.findElement(By.id("btnRegister")).click();
			driver.findElement(By.cssSelector("div#popupWrapper[style*='display: block']"));
			driver.findElement(By.id("loadUserPopup")).click();
			driver.findElement(By.id("btnScUserPupSearch")).click();
			driver.findElement(By.cssSelector("div[class$='cont-box-popup']"));
			driver.findElement(By.cssSelector("span#btnScUserPupNext[class$='page-number-next']"));
			driver.findElement(By.cssSelector("#btnScUserPupNext")).click();
			SeleniumCommon.sessionTimeOut();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_3_1_1_1.log");
			driver.findElement(By.cssSelector("#btnScUserPupPrevious")).click();
			capture(imagePath + "/0023_3_1_1_1");
			driver.get(baseUrl + "/ict/");
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));

			// set page language
			if (testMode.equals(IE_VN_TEST) || testMode.equals(FF_VN_TEST)
					|| testMode.equals(CHR_VN_TEST) || testMode.equals(EDGE_VN_TEST)) {
				// Vietnamese
				driver.findElement(By.cssSelector("img.language[name|=vi]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Mật khẩu')]"));
			} else if (testMode.equals(IE_JP_TEST) || testMode.equals(FF_JP_TEST)
					|| testMode.equals(CHR_JP_TEST) || testMode.equals(EDGE_JP_TEST)) {
				// Japanese
				driver.findElement(By.cssSelector("img.language[name|=en]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Password')]"));
			} else if (testMode.equals(IE_EN_TEST) || testMode.equals(FF_EN_TEST)
					|| testMode.equals(CHR_EN_TEST) || testMode.equals(EDGE_EN_TEST)) {
				// English
				driver.findElement(By.cssSelector("img.language[name|=jp]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'パスワード')]"));
			}
			driver.findElement(By.id("j_username"));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000001");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000001");
			((RemoteWebDriver) driver).executeScript("arguments[0].click()",driver.findElement(By.id("btnLogin")));
			
			driver.findElement(By.id("btnAreaScreen")).click();
			driver.findElement(By.cssSelector(".delete"));
			driver.findElement(By.id("btnRegister")).click();
			driver.findElement(By.cssSelector("div#popupWrapper[style*='display: block']"));
			driver.findElement(By.id("loadUserPopup")).click();
			driver.findElement(By.id("btnScUserPupSearch")).click();
			driver.findElement(By.cssSelector("div[class$='cont-box-popup']"));
			driver.findElement(By.cssSelector("span#btnScUserPupLast[class$='page-number-last']"));
			driver.findElement(By.cssSelector("#btnScUserPupLast")).click();
			SeleniumCommon.sessionTimeOut();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_2_1_1_1.log");
			driver.findElement(By.cssSelector("#btnScUserPupFirst")).click();
			capture(imagePath + "/0023_2_1_1_1");
			driver.get(baseUrl + "/ict/");
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));

			// set page language
			if (testMode.equals(IE_VN_TEST) || testMode.equals(FF_VN_TEST)
					|| testMode.equals(CHR_VN_TEST) || testMode.equals(EDGE_VN_TEST)) {
				// Vietnamese
				driver.findElement(By.cssSelector("img.language[name|=vi]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Mật khẩu')]"));
			} else if (testMode.equals(IE_JP_TEST) || testMode.equals(FF_JP_TEST)
					|| testMode.equals(CHR_JP_TEST) || testMode.equals(EDGE_JP_TEST)) {
				// Japanese
				driver.findElement(By.cssSelector("img.language[name|=en]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Password')]"));
			} else if (testMode.equals(IE_EN_TEST) || testMode.equals(FF_EN_TEST)
					|| testMode.equals(CHR_EN_TEST) || testMode.equals(EDGE_EN_TEST)) {
				// English
				driver.findElement(By.cssSelector("img.language[name|=jp]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'パスワード')]"));
			}
			driver.findElement(By.id("j_username"));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000001");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000001");
			((RemoteWebDriver) driver).executeScript("arguments[0].click()",driver.findElement(By.id("btnLogin")));

			driver.findElement(By.id("btnAreaScreen")).click();
			driver.findElement(By.cssSelector(".delete"));
			driver.findElement(By.id("btnRegister")).click();
			driver.findElement(By.cssSelector("div#popupWrapper[style*='display: block']"));
			driver.findElement(By.id("loadUserPopup")).click();
			driver.findElement(By.id("btnScUserPupSearch")).click();
			SeleniumCommon.sessionTimeOut();
			driver.findElement(By.id("txtScUserPupGoToPage")).clear();
			driver.findElement(By.id("txtScUserPupGoToPage")).sendKeys("2");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_6_1_1_1.log");
			driver.findElement(By.id("btnScUserPupGoToPage")).click();
			capture(imagePath + "/0023_6_1_1_1");

			//Selenium Test Disconnection

			driver.get(baseUrl + "/ict/");
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));

			// set page language
			if (testMode.equals(IE_VN_TEST) || testMode.equals(FF_VN_TEST)
					|| testMode.equals(CHR_VN_TEST) || testMode.equals(EDGE_VN_TEST)) {
				// Vietnamese
				driver.findElement(By.cssSelector("img.language[name|=vi]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Mật khẩu')]"));
			} else if (testMode.equals(IE_JP_TEST) || testMode.equals(FF_JP_TEST)
					|| testMode.equals(CHR_JP_TEST) || testMode.equals(EDGE_JP_TEST)) {
				// Japanese
				driver.findElement(By.cssSelector("img.language[name|=en]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Password')]"));
			} else if (testMode.equals(IE_EN_TEST) || testMode.equals(FF_EN_TEST)
					|| testMode.equals(CHR_EN_TEST) || testMode.equals(EDGE_EN_TEST)) {
				// English
				driver.findElement(By.cssSelector("img.language[name|=jp]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'パスワード')]"));
			}
			SeleniumCommon.disableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
			Thread.sleep(3000);
			SeleniumCommon.enableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
			driver.findElement(By.id("j_username"));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000001");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000001");
			((RemoteWebDriver) driver).executeScript("arguments[0].click()",driver.findElement(By.id("btnLogin")));

			driver.findElement(By.id("btnAreaScreen")).click();
			driver.findElement(By.cssSelector(".delete"));
			driver.findElement(By.id("btnRegister")).click();
			driver.findElement(By.cssSelector("div#popupWrapper[style*='display: block']"));
			driver.findElement(By.id("loadUserPopup")).click();
			SeleniumCommon.disableClientConnection();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_1_1_2_1.log");
			driver.findElement(By.id("btnScUserPupSearch")).click();
			driver.findElement(By.id("popup_ok"));
			capture(imagePath + "/0023_1_1_2_1");
			driver.findElement(By.id("popup_ok")).click();
			SeleniumCommon.enableClientConnection();
			Thread.sleep(3000);

			driver.findElement(By.id("btnScUserPupSearch")).click();
			driver.findElement(By.cssSelector(".tbl-user-popup-table .acRow:last-child"));
			SeleniumCommon.disableClientConnection();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_4_1_2_1.log");
			driver.findElement(By.cssSelector("#btnScUserPupNext")).click();
			driver.findElement(By.id("popup_ok"));
			capture(imagePath + "/0023_4_1_2_1");
			driver.findElement(By.id("popup_ok")).click();

			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_5_1_2_1.log");
			driver.findElement(By.cssSelector("#btnScUserPupLast")).click();
			driver.findElement(By.id("popup_ok"));
			capture(imagePath + "/0023_5_1_2_1");
			driver.findElement(By.id("popup_ok")).click();
			SeleniumCommon.enableClientConnection();
			Thread.sleep(3000);

			driver.findElement(By.cssSelector("#btnScUserPupNext")).click();
			driver.findElement(By.cssSelector("span#btnScUserPupPrevious[class$='page-number-pre']"));
			SeleniumCommon.disableClientConnection();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_3_1_2_1.log");
			driver.findElement(By.cssSelector("#btnScUserPupPrevious")).click();
			driver.findElement(By.id("popup_ok"));
			capture(imagePath + "/0023_3_1_2_1");
			driver.findElement(By.id("popup_ok")).click();

			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_2_1_2_1.log");
			driver.findElement(By.cssSelector("#btnScUserPupFirst")).click();
			driver.findElement(By.id("popup_ok"));
			capture(imagePath + "/0023_2_1_2_1");
			driver.findElement(By.id("popup_ok")).click();
			SeleniumCommon.enableClientConnection();
			Thread.sleep(3000);

			driver.findElement(By.id("btnScUserPupSearch")).click();
			driver.findElement(By.cssSelector(".tbl-user-popup-table .acRow:last-child"));
			SeleniumCommon.disableClientConnection();
			driver.findElement(By.id("txtScUserPupGoToPage")).clear();
			driver.findElement(By.id("txtScUserPupGoToPage")).sendKeys("2");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_6_1_2_1.log");
			driver.findElement(By.id("btnScUserPupGoToPage")).click();
			driver.findElement(By.id("popup_ok"));
			capture(imagePath + "/0023_6_1_2_1");
			driver.findElement(By.id("popup_ok")).click();
			SeleniumCommon.enableClientConnection();
			Thread.sleep(3000);
			SeleniumCommon.sessionTimeOut();
			driver.findElement(By.id("btnGeneralSetting")).click();
			
			//Selenium Test DB Server Connection

			driver.get(baseUrl + "/ict/");
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));

			// set page language
			if (testMode.equals(IE_VN_TEST) || testMode.equals(FF_VN_TEST)
					|| testMode.equals(CHR_VN_TEST) || testMode.equals(EDGE_VN_TEST)) {
				// Vietnamese
				driver.findElement(By.cssSelector("img.language[name|=vi]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Mật khẩu')]"));
			} else if (testMode.equals(IE_JP_TEST) || testMode.equals(FF_JP_TEST)
					|| testMode.equals(CHR_JP_TEST) || testMode.equals(EDGE_JP_TEST)) {
				// Japanese
				driver.findElement(By.cssSelector("img.language[name|=en]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'Password')]"));
			} else if (testMode.equals(IE_EN_TEST) || testMode.equals(FF_EN_TEST)
					|| testMode.equals(CHR_EN_TEST) || testMode.equals(EDGE_EN_TEST)) {
				// English
				driver.findElement(By.cssSelector("img.language[name|=jp]")).click();
				driver.findElement(By.xpath("//span[contains(text(), 'パスワード')]"));
			}
			SeleniumCommon.enableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
			driver.findElement(By.id("j_username"));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000001");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000001");
			((RemoteWebDriver) driver).executeScript("arguments[0].click()",driver.findElement(By.id("btnLogin")));
			
			driver.findElement(By.id("btnAreaScreen")).click();
			driver.findElement(By.cssSelector(".delete"));
			driver.findElement(By.id("btnRegister")).click();
			driver.findElement(By.cssSelector("div#popupWrapper[style*='display: block']"));
			driver.findElement(By.id("loadUserPopup")).click();
			SeleniumCommon.disableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_1_1_3_1.log");
			driver.findElement(By.id("btnScUserPupSearch")).click();
			driver.findElement(By.id("popup_ok"));
			capture(imagePath + "/0023_1_1_3_1");
			driver.findElement(By.id("popup_ok")).click();
			SeleniumCommon.enableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");

			driver.findElement(By.id("btnScUserPupSearch")).click();
			driver.findElement(By.cssSelector(".tbl-user-popup-table .acRow:last-child"));
			SeleniumCommon.disableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_4_1_3_1.log");
			driver.findElement(By.cssSelector("#btnScUserPupNext")).click();
			driver.findElement(By.id("popup_ok"));
			capture(imagePath + "/0023_4_1_3_1");
			driver.findElement(By.id("popup_ok")).click();
			SeleniumCommon.enableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");

			driver.findElement(By.id("btnScUserPupSearch")).click();
			driver.findElement(By.cssSelector(".tbl-user-popup-table .acRow:last-child"));
			SeleniumCommon.disableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_5_1_3_1.log");
			driver.findElement(By.cssSelector("#btnScUserPupLast")).click();
			driver.findElement(By.id("popup_ok"));
			capture(imagePath + "/0023_5_1_3_1");
			driver.findElement(By.id("popup_ok")).click();
			SeleniumCommon.enableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");

			driver.findElement(By.id("btnScUserPupSearch")).click();
			driver.findElement(By.cssSelector(".tbl-user-popup-table .acRow:last-child"));
			driver.findElement(By.cssSelector("#btnScUserPupNext")).click();
			driver.findElement(By.cssSelector(".tbl-user-popup-table .acRow:last-child"));
			SeleniumCommon.disableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_3_1_3_1.log");
			driver.findElement(By.cssSelector("#btnScUserPupPrevious")).click();
			driver.findElement(By.id("popup_ok"));
			capture(imagePath + "/0023_3_1_3_1");
			driver.findElement(By.id("popup_ok")).click();
			SeleniumCommon.enableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");

			driver.findElement(By.id("btnScUserPupSearch")).click();
			driver.findElement(By.cssSelector(".tbl-user-popup-table .acRow:last-child"));
			driver.findElement(By.cssSelector("#btnScUserPupLast")).click();
			driver.findElement(By.cssSelector(".tbl-user-popup-table .acRow:last-child"));
			SeleniumCommon.disableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_2_1_3_1.log");
			driver.findElement(By.cssSelector("#btnScUserPupFirst")).click();
			driver.findElement(By.id("popup_ok"));
			capture(imagePath + "/0023_2_1_3_1");
			driver.findElement(By.id("popup_ok")).click();
			SeleniumCommon.enableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");

			driver.findElement(By.id("btnScUserPupSearch")).click();
			driver.findElement(By.cssSelector(".tbl-user-popup-table .acRow:last-child"));
			SeleniumCommon.disableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
			driver.findElement(By.id("txtScUserPupGoToPage")).clear();
			driver.findElement(By.id("txtScUserPupGoToPage")).sendKeys("2");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0023_6_1_3_1.log");
			driver.findElement(By.id("btnScUserPupGoToPage")).click();
			driver.findElement(By.id("popup_ok"));
			capture(imagePath + "/0023_6_1_3_1");
			driver.findElement(By.id("popup_ok")).click();
			SeleniumCommon.enableDBServerConnection("BANANA_DB_HIEUDAO", "Hieu");
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
