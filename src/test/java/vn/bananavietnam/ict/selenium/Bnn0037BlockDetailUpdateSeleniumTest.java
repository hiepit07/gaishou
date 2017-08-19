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

public class Bnn0037BlockDetailUpdateSeleniumTest {
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
		IE_VN_TEST, IE_JP_TEST, IE_EN_TEST,
		FF_VN_TEST, FF_JP_TEST, FF_EN_TEST,
		CHR_VN_TEST, CHR_JP_TEST, CHR_EN_TEST,
		EDGE_VN_TEST, EDGE_JP_TEST, EDGE_EN_TEST
	};

	// define test mode image path here
	private final String IE_VN_IMG_PATH = "selenium_test_images/0037/Success/IE/vn/";
	private final String IE_JP_IMG_PATH = "selenium_test_images/0037/Success/IE/jp/";
	private final String IE_EN_IMG_PATH = "selenium_test_images/0037/Success/IE/en/";
	private final String FF_VN_IMG_PATH = "selenium_test_images/0037/Success/Firefox/vn/";
	private final String FF_JP_IMG_PATH = "selenium_test_images/0037/Success/Firefox/jp/";
	private final String FF_EN_IMG_PATH = "selenium_test_images/0037/Success/Firefox/en/";
	private final String CHR_VN_IMG_PATH = "selenium_test_images/0037/Success/Chrome/vn/";
	private final String CHR_JP_IMG_PATH = "selenium_test_images/0037/Success/Chrome/jp/";
	private final String CHR_EN_IMG_PATH = "selenium_test_images/0037/Success/Chrome/en/";
	private final String EDGE_VN_IMG_PATH = "selenium_test_images/0037/Success/Edge/vn/";
	private final String EDGE_JP_IMG_PATH = "selenium_test_images/0037/Success/Edge/jp/";
	private final String EDGE_EN_IMG_PATH = "selenium_test_images/0037/Success/Edge/en/";
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
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "webdriver/geckodriver.exe");
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");
		System.setProperty("webdriver.edge.driver", "webdriver/MicrosoftWebDriver.exe");

		// set test URL
		baseUrl = "http://localhost:8080";
	}

	@Test
	public void testBnn0037BlockUpdateSelenium() throws Exception {
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
			driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);

			// get image path
			imagePath = testImagePathArray[i];

			// set page language
			if (testMode.equals(IE_VN_TEST) || testMode.equals(FF_VN_TEST)
					|| testMode.equals(CHR_VN_TEST) || testMode.equals(EDGE_VN_TEST)) {
				// Vietnamese
				driver.get(baseUrl + "/ict/?language=vi");
			} else if (testMode.equals(IE_JP_TEST) || testMode.equals(FF_JP_TEST)
					|| testMode.equals(CHR_JP_TEST) || testMode.equals(EDGE_JP_TEST)) {
				// Japanese
				driver.get(baseUrl + "/ict/?language=jp");
			} else if (testMode.equals(IE_EN_TEST) || testMode.equals(FF_EN_TEST)
					|| testMode.equals(CHR_EN_TEST) || testMode.equals(EDGE_EN_TEST)) {
				// English
				driver.get(baseUrl + "/ict/?language=en");
			}

			// dump data to file
			SeleniumCommon.dumpDataToFile("BANANA_DB_KHOA", "E:/" + imagePath + "DATA_BEFORE", "IVB_T_CULTIVATION_RESULT");

			// start testing
			// input user name and password
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000002");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000002");
			// login
			driver.findElement(By.id("btnLogin")).click();
			// Thread.sleep(3000);
			// select first farm
			driver.findElement(By.id("F001")).click();
			// Thread.sleep(3000);
			// select first area
			driver.findElement(By.id("A001")).click();
			// Thread.sleep(3000);

			// select first block
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0037_1_1.log");
			driver.findElement(By.linkText("B000")).click();
			// Thread.sleep(3000);

			// take picture of Bnn0037 initialization state
			capture(imagePath + "0037_1_1");
			// Thread.sleep(3000);

			// select then deselect all items
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0037_2_1.log");
			driver.findElement(By.id("btnDeselectAll")).click();
			driver.findElement(By.id("btnSelectAll")).click();

			// take picture of Bnn0037 select all state
			capture(imagePath + "0037_2_1");
			// Thread.sleep(3000);

			driver.findElement(By.id("btnDeselectAll")).click();
			// select then deselect one checkbox at a time
			driver.findElement(By.name("L000:C000")).click();
			driver.findElement(By.name("L001:C000")).click();
			driver.findElement(By.name("L000:C000")).click();
			driver.findElement(By.name("L001:C000")).click();
			// go back to Bnn0035 from Bnn0037
			driver.findElement(By.id("btnBack")).click();
			// Thread.sleep(3000);
			// select first block to go to Bnn0037 again
			driver.findElement(By.linkText("B000")).click();
			// Thread.sleep(3000);
			// check on first item
			driver.findElement(By.id("btnDeselectAll")).click();
			driver.findElement(By.name("L000:C000")).click();

			// open View details popup
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0037_5_1.log");
			driver.findElement(By.name("0")).click();
			// Thread.sleep(3000);

			// take picture of Bnn0037 View details popup
			capture(imagePath + "0037_5_1");
			// Thread.sleep(3000);
			driver.findElement(By.id("btnCancelPopup")).click();

			// open Delete details popup
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0037_6_1.log");
			driver.findElement(By.xpath("(//img[@name='0'])[2]")).click();
			// Thread.sleep(3000);

			// take picture of Bnn0037 Delete details popup
			capture(imagePath + "0037_6_1");
			// Thread.sleep(3000);
			driver.findElement(By.id("btnCancelPopup")).click();

			// open Register details popup
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0037_7_1.log");
			driver.findElement(By.id("btnRegister")).click();
			// Thread.sleep(3000);

			// take picture of Bnn0037 Register details popup
			capture(imagePath + "0037_7_1");
			// Thread.sleep(3000);

			// change values on combobox and date picker
			new Select(driver.findElement(By.id("cbbProcessPopup"))).selectByIndex(1);
			// Thread.sleep(3000);
			new Select(driver.findElement(By.id("cbbTaskPopup"))).selectByIndex(1);
			// Thread.sleep(3000);
		    new Select(driver.findElement(By.id("cbbTaskPopup"))).selectByIndex(2);
		    // Thread.sleep(3000);
		    driver.findElement(By.cssSelector("img.ui-datepicker-trigger")).click();
		    driver.findElement(By.linkText("30")).click();
		    new Select(driver.findElement(By.id("cbbStatusPopup"))).selectByIndex(1);
		    // Thread.sleep(3000);
			// input text on Note textbox
			driver.findElement(By.id("txtNotePopup")).clear();
			driver.findElement(By.id("txtNotePopup")).sendKeys("test test test");

			// take picture of Bnn0037 before inserting
			capture(imagePath + "0037_11_1_1_0.1");
			// Thread.sleep(3000);

			// start insert
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0037_11_1_1.log");
			driver.findElement(By.id("btnRegisterPopup")).click();
			// Thread.sleep(3000);

			// take picture of Bnn0037 insert successfully
			capture(imagePath + "0037_11_1_1_0.2");
			// dump data to file
			SeleniumCommon.dumpDataToFile("BANANA_DB_KHOA", "E:/" + imagePath + "DATA_AFTER_INSERT", "IVB_T_CULTIVATION_RESULT");
			driver.findElement(By.id("popup_ok")).click();

			// open Delete details popup
			driver.findElement(By.xpath("(//img[@name='0'])[2]")).click();
			// Thread.sleep(3000);
			// start delete
			driver.findElement(By.id("btnRegisterPopup")).click();

			// take picture of Bnn0037 before deleting
			capture(imagePath + "0037_11_2_1");
			// Thread.sleep(3000);

			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + "0037_11_2_2.log");
			driver.findElement(By.id("popup_ok")).click();
			// Thread.sleep(3000);

			// take picture of Bnn0037 delete successfully
			capture(imagePath + "0037_11_2_2");
			// dump data to file
			SeleniumCommon.dumpDataToFile("BANANA_DB_KHOA", "E:/" + imagePath + "DATA_AFTER_DELETE", "IVB_T_CULTIVATION_RESULT");
			driver.findElement(By.id("popup_ok")).click();
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
