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

public class Bnn0092ProcessMaster {
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
		FF_VN_TEST, FF_JP_TEST, FF_EN_TEST,
		CHR_VN_TEST, CHR_JP_TEST, CHR_EN_TEST,
		EDGE_VN_TEST, EDGE_JP_TEST, EDGE_EN_TEST
	};

	// define test mode image path here
	private final String IE_VN_IMG_PATH = "selenium_test_images/0092/Success/IE/vn/";
	private final String IE_JP_IMG_PATH = "selenium_test_images/0092/Success/IE/jp/";
	private final String IE_EN_IMG_PATH = "selenium_test_images/0092/Success/IE/en/";
	private final String FF_VN_IMG_PATH = "selenium_test_images/0092/Success/Firefox/vn/";
	private final String FF_JP_IMG_PATH = "selenium_test_images/0092/Success/Firefox/jp/";
	private final String FF_EN_IMG_PATH = "selenium_test_images/0092/Success/Firefox/en/";
	private final String CHR_VN_IMG_PATH = "selenium_test_images/0092/Success/Chrome/vn/";
	private final String CHR_JP_IMG_PATH = "selenium_test_images/0092/Success/Chrome/jp/";
	private final String CHR_EN_IMG_PATH = "selenium_test_images/0092/Success/Chrome/en/";
	private final String EDGE_VN_IMG_PATH = "selenium_test_images/0092/Success/Edge/vn/";
	private final String EDGE_JP_IMG_PATH = "selenium_test_images/0092/Success/Edge/jp/";
	private final String EDGE_EN_IMG_PATH = "selenium_test_images/0092/Success/Edge/en/";
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
	public void testBnn0061TraceabilityMasterSelenium() throws Exception {
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
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + "DATA_BEFORE", "IVB_M_CULTIVATION");

			// start testing
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000001");
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000001");
			driver.findElement(By.id("btnLogin")).click();

			// start testing
			driver.findElement(By.id("btnCultivationScreen")).click();
			driver.findElement(By.cssSelector("#cbbFarmName > option:last-child"));
			driver.findElement(By.cssSelector("#cbbKindName > option:last-child"));
			driver.findElement(By.cssSelector("table.tbl-task-un .acRow:first-child"));
			driver.findElement(By.id("cbbKindName")).click();
			driver.findElement(By.cssSelector("#cbbKindName > option[value|=K002]"));
			driver.findElement(By.cssSelector("#cbbKindName > option[value|=K002]")).click();
			driver.findElement(By.id("cbbKindName")).click();
			driver.findElement(By.cssSelector("table.tbl-task-re .acRow:first-child"));
			driver.findElement(By.id("btnNext")).click();
			driver.findElement(By.cssSelector("table.tbl-process-un .acRow:first-child"));
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0092_1_1_1.log");
			driver.findElement(By.cssSelector("table.tbl-process-un .acRow:first-child")).click();
			driver.findElement(By.cssSelector("button#btn_get[class$='btn-get']"));
			driver.findElement(By.id("btn_get")).click();
			driver.findElement(By.cssSelector("table.tbl-process-re .acRow:first-child"));
			capture(imagePath + "/0092_1_1_1_1");
			driver.findElement(By.id("btn_getall")).click();
			driver.findElement(By.cssSelector("button#btn_getall[class$='btn-getall-disable']"));
			capture(imagePath + "/0092_1_1_1_2");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0092_2_1_1.log");
			driver.findElement(By.cssSelector("table.tbl-process-re .acRow:first-child")).click();
			driver.findElement(By.id("btn_back")).click();
			driver.findElement(By.cssSelector("table.tbl-process-un .acRow:first-child"));
			capture(imagePath + "/0092_1_2_1_1");
			driver.findElement(By.id("btn_backall")).click();
			driver.findElement(By.cssSelector("button#btn_backall[class$='btn-backall-disable']"));
			capture(imagePath + "/0092_1_2_1_2");
			driver.findElement(By.id("btn_getall")).click();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0092_1_3_1.log");
			driver.findElement(By.cssSelector("table.tbl-process-re .acRow:nth-child(2)")).click();
			driver.findElement(By.id("btn_up")).click();
			capture(imagePath + "/0092_1_3_1_1");
			driver.findElement(By.id("btn_down")).click();
			capture(imagePath + "/0092_1_3_1_2");
			driver.findElement(By.id("btn_first")).click();
			capture(imagePath + "/0092_1_3_1_3");
			driver.findElement(By.id("btn_last")).click();
			capture(imagePath + "/0092_1_3_1_4");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0092_3_1_1_1.log");
			driver.findElement(By.id("btnBack")).click();
			driver.findElement(By.id("popup_cancel"));
			capture(imagePath + "/0092_3_1_1_1");
			driver.findElement(By.id("popup_cancel")).click();
			driver.findElement(By.id("btnBack")).click();
			driver.findElement(By.id("popup_ok"));
			driver.findElement(By.id("popup_ok")).click();
			driver.findElement(By.cssSelector("#cbbFarmName > option:last-child"));
			driver.findElement(By.cssSelector("#cbbKindName > option:last-child"));
			driver.findElement(By.cssSelector("table.tbl-task-un .acRow:first-child"));
			driver.findElement(By.id("cbbKindName")).click();
			driver.findElement(By.cssSelector("#cbbKindName > option[value|=K002]"));
			driver.findElement(By.cssSelector("#cbbKindName > option[value|=K002]")).click();
			driver.findElement(By.id("cbbKindName")).click();
			driver.findElement(By.cssSelector("table.tbl-task-re .acRow:first-child"));
			driver.findElement(By.id("btnNext")).click();
			driver.findElement(By.cssSelector("table.tbl-process-un .acRow:first-child"));
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0092_3_1_1_2.log");
			driver.findElement(By.id("btnBack")).click();
			driver.findElement(By.id("popup_cancel"));
			capture(imagePath + "/0092_3_1_1_2");
			driver.findElement(By.id("popup_cancel")).click();
			driver.findElement(By.id("btnBack")).click();
			driver.findElement(By.id("popup_ok"));
			driver.findElement(By.id("popup_ok")).click();
			driver.findElement(By.cssSelector("#cbbFarmName > option:last-child"));
			driver.findElement(By.cssSelector("#cbbKindName > option:last-child"));
			driver.findElement(By.cssSelector("table.tbl-task-un .acRow:first-child"));
			driver.findElement(By.id("cbbKindName")).click();
			driver.findElement(By.cssSelector("#cbbKindName > option[value|=K002]"));
			driver.findElement(By.cssSelector("#cbbKindName > option[value|=K002]")).click();
			driver.findElement(By.id("cbbKindName")).click();
			driver.findElement(By.cssSelector("table.tbl-task-re .acRow:first-child"));
			driver.findElement(By.id("btnNext")).click();
			driver.findElement(By.cssSelector("table.tbl-process-un .acRow:first-child"));
			driver.findElement(By.id("btn_getall")).click();
			driver.findElement(By.cssSelector("table.tbl-process-re .acRow:first-child"));
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0092_4.log");
			driver.findElement(By.id("btnRegister")).click();
			driver.findElement(By.id("popup_ok"));
			capture(imagePath + "/0092_4");
			driver.findElement(By.id("popup_ok")).click();
			// reset
			driver.findElement(By.id("btn_backall")).click();
			driver.findElement(By.cssSelector("table.tbl-process-un .acRow:first-child"));
			driver.findElement(By.id("btnRegister")).click();
			driver.findElement(By.id("popup_ok"));
			driver.findElement(By.id("popup_ok")).click();
			// dump data to file
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + "DATA_AFTER_UPDATE", "IVB_M_CULTIVATION");
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