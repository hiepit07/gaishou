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
import org.openqa.selenium.support.ui.Select;

public class Bnn0035BlockUpdateSeleniumErrorTest {
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
		IE_VN_TEST/*, IE_JP_TEST, IE_EN_TEST,
		FF_VN_TEST, FF_JP_TEST, FF_EN_TEST,
		CHR_VN_TEST, CHR_JP_TEST, CHR_EN_TEST,
		EDGE_VN_TEST, EDGE_JP_TEST, EDGE_EN_TEST*/
	};

	// define test mode image path here
	private final String IE_VN_IMG_PATH = "selenium_test_images/0035/Error/IE/vn/";
	private final String IE_JP_IMG_PATH = "selenium_test_images/0035/Error/IE/jp/";
	private final String IE_EN_IMG_PATH = "selenium_test_images/0035/Error/IE/en/";
	private final String FF_VN_IMG_PATH = "selenium_test_images/0035/Error/Firefox/vn/";
	private final String FF_JP_IMG_PATH = "selenium_test_images/0035/Error/Firefox/jp/";
	private final String FF_EN_IMG_PATH = "selenium_test_images/0035/Error/Firefox/en/";
	private final String CHR_VN_IMG_PATH = "selenium_test_images/0035/Error/Chrome/vn/";
	private final String CHR_JP_IMG_PATH = "selenium_test_images/0035/Error/Chrome/jp/";
	private final String CHR_EN_IMG_PATH = "selenium_test_images/0035/Error/Chrome/en/";
	private final String EDGE_VN_IMG_PATH = "selenium_test_images/0035/Error/Edge/vn/";
	private final String EDGE_JP_IMG_PATH = "selenium_test_images/0035/Error/Edge/jp/";
	private final String EDGE_EN_IMG_PATH = "selenium_test_images/0035/Error/Edge/en/";
	private String[] testImagePathArray = {
		IE_VN_IMG_PATH/*, IE_JP_IMG_PATH, IE_EN_IMG_PATH,
		FF_VN_IMG_PATH, FF_JP_IMG_PATH, FF_EN_IMG_PATH,
		CHR_VN_IMG_PATH, CHR_JP_IMG_PATH, CHR_EN_IMG_PATH,
		EDGE_VN_IMG_PATH, EDGE_JP_IMG_PATH, EDGE_EN_IMG_PATH,*/
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
		baseUrl = "http://172.16.0.133:8080";
	}

	@Test
	public void testBnn0035BlockUpdateSeleniumError() throws Exception {
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
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

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

			// start testing
			// delete all area ----------------------------
			SeleniumCommon.executeSqlFile("BANANA_DB_KHOA", "delete_area.sql");
			// input user name and password
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000005");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000005");
			// login
			driver.findElement(By.id("btnLogin")).click();
			//Thread.sleep(3000);
			// take picture of Bnn0035 initialization state with no area error
			capture(imagePath + "0035_2_1_1_1");
			//Thread.sleep(3000);
			// insert all area ----------------------------
			SeleniumCommon.executeSqlFile("BANANA_DB_KHOA", "insert_area.sql");

			// login again
			driver.get(baseUrl + pageLanguage);
			// input user name and password
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000002");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000002");
			// login
			driver.findElement(By.id("btnLogin")).click();
			//Thread.sleep(3000);
			// select first farm
			driver.findElement(By.id("F001")).click();
			//Thread.sleep(3000);
			// select second area
			driver.findElement(By.id("A002")).click();
			//Thread.sleep(3000);
			// take picture of Bnn0035 initialization state with no block error
			capture(imagePath + "0035_2_1_2_1");
			//Thread.sleep(3000);

			// login again
			driver.get(baseUrl + pageLanguage);
			// input user name and password
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000002");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000002");
			// login
			driver.findElement(By.id("btnLogin")).click();
			//Thread.sleep(3000);
			// select first farm
			driver.findElement(By.id("F001")).click();
			//Thread.sleep(3000);
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			//Thread.sleep(3000);
			// select first area
			driver.findElement(By.id("A001")).click();
			//Thread.sleep(3000);
			// take picture of Bnn0035 initialization state with client connection error
			capture(imagePath + "0035_2_1_4_1");
			//Thread.sleep(3000);
			// start client connection ----------------------------
			SeleniumCommon.enableClientConnection();
			//Thread.sleep(3000);

			// login again
			driver.get(baseUrl + pageLanguage);
			// input user name and password
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000002");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000002");
			// login
			driver.findElement(By.id("btnLogin")).click();
			//Thread.sleep(3000);
			// select first farm
			driver.findElement(By.id("F001")).click();
			//Thread.sleep(3000);
			// stop server connection ----------------------------
			SeleniumCommon.disableDBServerConnection("BANANA_DB_KHOA", "Khoa");
			//Thread.sleep(3000);
			// select first area
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0035_2_1_5_1.log");
			driver.findElement(By.id("A001")).click();
			//Thread.sleep(3000);
			// take picture of Bnn0035 initialization state with server connection error
			capture(imagePath + "0035_2_1_5_1");
			//Thread.sleep(3000);
			// start server connection ----------------------------
			SeleniumCommon.enableDBServerConnection("BANANA_DB_KHOA", "Khoa");
			//Thread.sleep(3000);

			// login again
			driver.get(baseUrl + pageLanguage);
			// input user name and password
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000002");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000002");
			// login
			driver.findElement(By.id("btnLogin")).click();
			//Thread.sleep(3000);
			// select first farm
			driver.findElement(By.id("F001")).click();
			//Thread.sleep(3000);
			// select first area
			driver.findElement(By.id("A001")).click();
			//Thread.sleep(3000);
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			//Thread.sleep(3000);
			// click on block link to go to Bnn0037
			driver.findElement(By.linkText("B001")).click();
			//Thread.sleep(3000);
			// take picture of clicking on block link when there is no internet connection
			capture(imagePath + "0035_3_1_2_1");
			//Thread.sleep(3000);
			// start client connection ----------------------------
			SeleniumCommon.enableClientConnection();
			//Thread.sleep(3000);

			// login again
			driver.get(baseUrl + pageLanguage);
			// input user name and password
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000002");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000002");
			// login
			driver.findElement(By.id("btnLogin")).click();
			//Thread.sleep(3000);
			// select first farm
			driver.findElement(By.id("F001")).click();
			//Thread.sleep(3000);
			// delete all process ----------------------------
			SeleniumCommon.executeSqlFile("BANANA_DB_KHOA", "delete_process.sql");
			// select first area
			driver.findElement(By.id("A001")).click();
			//Thread.sleep(3000);
			// ---------------------------- open View details popup ----------------------------
			driver.findElement(By.name("0")).click();
			//Thread.sleep(3000);
			// take picture of opening View details popup with no process error
			capture(imagePath + "0035_4_1_1_1");
			//Thread.sleep(3000);
			driver.findElement(By.id("popup_ok")).click();
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			//Thread.sleep(3000);
			// open View details popup
			driver.findElement(By.name("0")).click();
			//Thread.sleep(3000);
			// take picture of opening View details popup when there is no internet connection
			capture(imagePath + "0035_4_1_3_1");
			//Thread.sleep(3000);
			driver.findElement(By.id("popup_ok")).click();
			// start client connection ----------------------------
			SeleniumCommon.enableClientConnection();
			//Thread.sleep(3000);
			// stop server connection ----------------------------
			SeleniumCommon.disableDBServerConnection("BANANA_DB_KHOA", "Khoa");
			//Thread.sleep(3000);
			// open View details popup
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0035_4_1_4_1.log");
			driver.findElement(By.name("0")).click();
			//Thread.sleep(3000);
			// take picture of opening View details popup with server connection error
			capture(imagePath + "0035_4_1_4_1");
			//Thread.sleep(3000);
			driver.findElement(By.id("popup_ok")).click();
			// start server connection ----------------------------
			SeleniumCommon.enableDBServerConnection("BANANA_DB_KHOA", "Khoa");
			//Thread.sleep(3000);
			// ---------------------------- open Delete details popup ----------------------------
			driver.findElement(By.xpath("(//img[@name='0'])[2]")).click();
			//Thread.sleep(3000);
			// take picture of opening Delete details popup with no process error
			capture(imagePath + "0035_5_1_1_1");
			//Thread.sleep(3000);
			driver.findElement(By.id("popup_ok")).click();
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			//Thread.sleep(3000);
			// open Delete details popup
			driver.findElement(By.xpath("(//img[@name='0'])[2]")).click();
			//Thread.sleep(3000);
			// take picture of opening Delete details popup when there is no internet connection
			capture(imagePath + "0035_5_1_3_1");
			//Thread.sleep(3000);
			driver.findElement(By.id("popup_ok")).click();
			// start client connection ----------------------------
			SeleniumCommon.enableClientConnection();
			//Thread.sleep(3000);
			// stop server connection ----------------------------
			SeleniumCommon.disableDBServerConnection("BANANA_DB_KHOA", "Khoa");
			//Thread.sleep(3000);
			// open Delete details popup
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0035_5_1_4_1.log");
			driver.findElement(By.xpath("(//img[@name='0'])[2]")).click();
			//Thread.sleep(3000);
			// take picture of opening Delete details popup with server connection error
			capture(imagePath + "0035_5_1_4_1");
			//Thread.sleep(3000);
			driver.findElement(By.id("popup_ok")).click();
			// start server connection ----------------------------
			SeleniumCommon.enableDBServerConnection("BANANA_DB_KHOA", "Khoa");
			//Thread.sleep(3000);
			// ---------------------------- open Register details popup ----------------------------
			driver.findElement(By.id("btnRegister")).click();
			//Thread.sleep(3000);
			// take picture of opening Register details popup with no process error
			capture(imagePath + "0035_6_1_1_1_");
			//Thread.sleep(3000);
			driver.findElement(By.id("popup_ok")).click();
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			//Thread.sleep(3000);
			// open Register details popup
			driver.findElement(By.id("btnRegister")).click();
			//Thread.sleep(3000);
			// take picture of opening Register details popup when there is no internet connection
			capture(imagePath + "0035_6_1_3_1");
			//Thread.sleep(3000);
			driver.findElement(By.id("popup_ok")).click();
			// start client connection ----------------------------
			SeleniumCommon.enableClientConnection();
			//Thread.sleep(3000);
			// stop server connection ----------------------------
			SeleniumCommon.disableDBServerConnection("BANANA_DB_KHOA", "Khoa");
			//Thread.sleep(3000);
			// open Register details popup
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0035_6_1_4_1.log");
			driver.findElement(By.id("btnRegister")).click();
			//Thread.sleep(3000);
			// take picture of opening Register details popup with server connection error
			capture(imagePath + "0035_6_1_4_1");
			//Thread.sleep(3000);
			driver.findElement(By.id("popup_ok")).click();
			// start server connection ----------------------------
			SeleniumCommon.enableDBServerConnection("BANANA_DB_KHOA", "Khoa");
			//Thread.sleep(3000);
			// insert all process ----------------------------
			SeleniumCommon.executeSqlFile("BANANA_DB_KHOA", "insert_process.sql");

			// login again
			driver.get(baseUrl + pageLanguage);
			// input user name and password
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000002");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000002");
			// login
			driver.findElement(By.id("btnLogin")).click();
			//Thread.sleep(3000);
			// select first farm
			driver.findElement(By.id("F001")).click();
			//Thread.sleep(3000);
			// select first area
			driver.findElement(By.id("A001")).click();
			//Thread.sleep(3000);
			// ---------------------------- open Register details popup ----------------------------
			driver.findElement(By.id("btnRegister")).click();
			//Thread.sleep(3000);
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			//Thread.sleep(3000);
			// change values on combobox
			new Select(driver.findElement(By.id("cbbProcessPopup"))).selectByIndex(1);
			//Thread.sleep(3000);
			// take picture of changing values on combobox when there is no internet connection
			capture(imagePath + "0035_7_1_2_1");
			//Thread.sleep(3000);
			driver.findElement(By.id("popup_ok")).click();
			// start client connection ----------------------------
			SeleniumCommon.enableClientConnection();
			//Thread.sleep(3000);
			// stop server connection ----------------------------
			SeleniumCommon.disableDBServerConnection("BANANA_DB_KHOA", "Khoa");
			//Thread.sleep(3000);
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0035_7_1_3_1.log");
			new Select(driver.findElement(By.id("cbbProcessPopup"))).selectByIndex(2);
			//Thread.sleep(3000);
			// take picture of changing values on combobox with server connection error
			capture(imagePath + "0035_7_1_3_1");
			//Thread.sleep(3000);
			driver.findElement(By.id("popup_ok")).click();
			driver.findElement(By.id("btnCancelPopup")).click();
			// start server connection ----------------------------
			SeleniumCommon.enableDBServerConnection("BANANA_DB_KHOA", "Khoa");
			//Thread.sleep(3000);

			// ---------------------------- open Delete details popup ----------------------------
			driver.findElement(By.xpath("(//img[@name='0'])[2]")).click();
			//Thread.sleep(3000);
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			//Thread.sleep(3000);
			// start delete
			driver.findElement(By.id("btnRegisterPopup")).click();
			driver.findElement(By.id("popup_ok")).click();
			//Thread.sleep(3000);
			// take picture of deleting when there is no internet connection
			capture(imagePath + "0035_8_1_3_1");
			//Thread.sleep(3000);
			driver.findElement(By.id("popup_ok")).click();
			// start client connection ----------------------------
			SeleniumCommon.enableClientConnection();
			//Thread.sleep(3000);
			// stop server connection ----------------------------
			SeleniumCommon.disableDBServerConnection("BANANA_DB_KHOA", "Khoa");
			//Thread.sleep(3000);
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0035_8_1_1_1.log");
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0035_8_1_4_1.log");
			// start delete
			driver.findElement(By.id("btnRegisterPopup")).click();
			driver.findElement(By.id("popup_ok")).click();
			Thread.sleep(5000);
			// take picture of deleting with server connection error
			capture(imagePath + "0035_8_1_1_1");
			//Thread.sleep(3000);
			capture(imagePath + "0035_8_1_4_1");
			//Thread.sleep(3000);
			driver.findElement(By.id("popup_ok")).click();
			driver.findElement(By.id("btnCancelPopup")).click();
			// start server connection ----------------------------
			SeleniumCommon.enableDBServerConnection("BANANA_DB_KHOA", "Khoa");
			//Thread.sleep(3000);

			// ---------------------------- open Register details popup ----------------------------
			driver.findElement(By.id("btnRegister")).click();
			//Thread.sleep(3000);
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			//Thread.sleep(3000);
			// start insert
			driver.findElement(By.id("btnRegisterPopup")).click();
			//Thread.sleep(3000);
			// take picture of inserting when there is no internet connection
			capture(imagePath + "0035_9_1_3_1");
			//Thread.sleep(3000);
			driver.findElement(By.id("popup_ok")).click();
			// start client connection ----------------------------
			SeleniumCommon.enableClientConnection();
			//Thread.sleep(3000);
			// stop server connection ----------------------------
			SeleniumCommon.disableDBServerConnection("BANANA_DB_KHOA", "Khoa");
			//Thread.sleep(3000);
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0035_9_1_1_1.log");
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0035_9_1_4_1.log");
			// start insert
			driver.findElement(By.id("btnRegisterPopup")).click();
			Thread.sleep(5000);
			// take picture of inserting with server connection error
			capture(imagePath + "0035_9_1_1_1");
			//Thread.sleep(3000);
			capture(imagePath + "0035_9_1_4_1");
			//Thread.sleep(3000);
			driver.findElement(By.id("popup_ok")).click();
			driver.findElement(By.id("btnCancelPopup")).click();
			// start server connection ----------------------------
			SeleniumCommon.enableDBServerConnection("BANANA_DB_KHOA", "Khoa");
			//Thread.sleep(3000);
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
