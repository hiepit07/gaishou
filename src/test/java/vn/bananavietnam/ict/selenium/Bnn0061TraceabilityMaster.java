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

public class Bnn0061TraceabilityMaster {
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
	private final String IE_VN_IMG_PATH = "selenium_test_images/0061/Success/IE/vn/";
	private final String IE_JP_IMG_PATH = "selenium_test_images/0061/Success/IE/jp/";
	private final String IE_EN_IMG_PATH = "selenium_test_images/0061/Success/IE/en/";
	private final String FF_VN_IMG_PATH = "selenium_test_images/0061/Success/Firefox/vn/";
	private final String FF_JP_IMG_PATH = "selenium_test_images/0061/Success/Firefox/jp/";
	private final String FF_EN_IMG_PATH = "selenium_test_images/0061/Success/Firefox/en/";
	private final String CHR_VN_IMG_PATH = "selenium_test_images/0061/Success/Chrome/vn/";
	private final String CHR_JP_IMG_PATH = "selenium_test_images/0061/Success/Chrome/jp/";
	private final String CHR_EN_IMG_PATH = "selenium_test_images/0061/Success/Chrome/en/";
	private final String EDGE_VN_IMG_PATH = "selenium_test_images/0061/Success/Edge/vn/";
	private final String EDGE_JP_IMG_PATH = "selenium_test_images/0061/Success/Edge/jp/";
	private final String EDGE_EN_IMG_PATH = "selenium_test_images/0061/Success/Edge/en/";
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
			SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "E:/" + imagePath + "DATA_BEFORE", "IVB_T_CULTIVATION_RESULT");

			// start testing
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000002");
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000002");
			driver.findElement(By.id("btnLogin")).click();

			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_1_1_1_1.log");
			driver.findElement(By.id("btnTrace")).click();
			capture(imagePath + "/0061_1_1_1_1");
			driver.findElement(By.id("cbbFarmName")).click();
			driver.findElement(By.id("cbbFarmName")).click();
			driver.findElement(By.cssSelector("#cbbFarmName > option[value|=F002]")).click();
			driver.findElement(By.id("cbbAreaName")).click();
			driver.findElement(By.id("cbbAreaName")).click();
			driver.findElement(By.cssSelector("#cbbAreaName > option[value|=A002]")).click();
			driver.findElement(By.id("cbbBlockName")).click();
			driver.findElement(By.id("cbbBlockName")).click();
			driver.findElement(By.cssSelector("#cbbBlockName > option[value|=B000]")).click();
			driver.findElement(By.id("cbbLineNumber")).click();
			driver.findElement(By.id("cbbLineNumber")).click();
			driver.findElement(By.cssSelector("#cbbLineNumber > option[value|=L001]")).click();
			driver.findElement(By.id("cbbColumnNumber")).click();
			driver.findElement(By.id("cbbColumnNumber")).click();
			driver.findElement(By.cssSelector("#cbbColumnNumber > option[value|=C001]")).click();
			driver.findElement(By.id("cbbSeason")).click();
			driver.findElement(By.id("cbbSeason")).click();
			driver.findElement(By.cssSelector("#cbbSeason > option[value|='0']")).click();
			driver.findElement(By.cssSelector("div#divCodeStart img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtCodeStart")).clear();
			driver.findElement(By.id("txtCodeStart")).sendKeys("01042017");
			driver.findElement(By.cssSelector("div#divCodeEnd img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtCodeEnd")).clear();
			driver.findElement(By.id("txtCodeEnd")).sendKeys("03042017");
			driver.findElement(By.cssSelector("div#divPlantStart img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtPlantStart")).clear();
			driver.findElement(By.id("txtPlantStart")).sendKeys("05042017");
			driver.findElement(By.cssSelector("div#divPlantEnd img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtPlantEnd")).clear();
			driver.findElement(By.id("txtPlantEnd")).sendKeys("07042017");
			driver.findElement(By.cssSelector("div#divFlowerStart img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtFlowerStart")).clear();
			driver.findElement(By.id("txtFlowerStart")).sendKeys("09042017");
			driver.findElement(By.cssSelector("div#divFlowerEnd img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtFlowerEnd")).clear();
			driver.findElement(By.id("txtFlowerEnd")).sendKeys("11042017");
			driver.findElement(By.cssSelector("div#divCloseStart img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtCloseStart")).clear();
			driver.findElement(By.id("txtCloseStart")).sendKeys("13042017");
			driver.findElement(By.cssSelector("div#divCloseEnd img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtCloseEnd")).clear();
			driver.findElement(By.id("txtCloseEnd")).sendKeys("15042017");
			driver.findElement(By.cssSelector("div#divHarvestStart img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtHarvestStart")).clear();
			driver.findElement(By.id("txtHarvestStart")).sendKeys("17042017");
			driver.findElement(By.cssSelector("div#divHarvestEnd img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtHarvestEnd")).clear();
			driver.findElement(By.id("txtHarvestEnd")).sendKeys("19042017");
			driver.findElement(By.cssSelector("div#divShipStart img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtShipStart")).clear();
			driver.findElement(By.id("txtShipStart")).sendKeys("21042017");
			driver.findElement(By.cssSelector("div#divShipEnd img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtShipEnd")).clear();
			driver.findElement(By.id("txtShipEnd")).sendKeys("23042017");
			driver.findElement(By.cssSelector("div#divWorkStart img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtWorkStart")).clear();
			driver.findElement(By.id("txtWorkStart")).sendKeys("25042017");
			driver.findElement(By.cssSelector("div#divWorkEnd img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtWorkEnd")).clear();
			driver.findElement(By.id("txtWorkEnd")).sendKeys("27042017");
			driver.findElement(By.id("cbbProcessName")).click();
			driver.findElement(By.id("cbbProcessName")).click();
			driver.findElement(By.cssSelector("#cbbProcessName > option[value|=P001]")).click();
			driver.findElement(By.id("cbbTaskName")).click();
			driver.findElement(By.id("cbbTaskName")).click();
			driver.findElement(By.cssSelector("#cbbTaskName > option[value|=T001]")).click();
			driver.findElement(By.id("cbbStatusName")).click();
			driver.findElement(By.id("cbbStatusName")).click();
			driver.findElement(By.cssSelector("#cbbStatusName > option[value|=S001]")).click();
			driver.findElement(By.id("cbbUnitWork")).click();
			driver.findElement(By.id("cbbUnitWork")).click();
			driver.findElement(By.cssSelector("#cbbUnitWork > option[value|='0']")).click();
			driver.findElement(By.id("txtNote")).clear();
			driver.findElement(By.id("txtNote")).sendKeys("cultivation");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_5_1_2_1.log");
			driver.findElement(By.id("btnAccess")).click();
			capture(imagePath + "/0061_5_1_2_1");
			driver.findElement(By.id("popup_ok")).click();
			driver.findElement(By.id("btnBack")).click();

			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_2_1_1_1.log");
			driver.findElement(By.id("btnTrace")).click();
			driver.findElement(By.id("cbbAreaName")).click();
			capture(imagePath + "/0061_2_1_1_1");
			driver.findElement(By.id("cbbAreaName")).click();
			driver.findElement(By.id("cbbFarmName")).click();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_2_1_1_2.log");
			driver.findElement(By.id("cbbFarmName")).click();
			driver.findElement(By.cssSelector("#cbbFarmName > option[value|=F002]")).click();
			driver.findElement(By.id("cbbAreaName")).click();
			capture(imagePath + "/0061_2_1_1_2");
			driver.findElement(By.id("cbbAreaName")).click();
			driver.findElement(By.id("cbbBlockName")).click();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_3_1_1_1.log");
			driver.findElement(By.id("cbbBlockName")).click();
			driver.findElement(By.cssSelector("#cbbBlockName > option[value|=B000]")).click();
			capture(imagePath + "/0061_3_1_1_1");
			driver.findElement(By.id("cbbAreaName")).click();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_3_1_1_2.log");
			driver.findElement(By.id("cbbAreaName")).click();
			driver.findElement(By.cssSelector("#cbbAreaName > option[value|=A002]")).click();
			driver.findElement(By.id("cbbBlockName")).click();
			capture(imagePath + "/0061_3_1_1_2");
			driver.findElement(By.id("cbbBlockName")).click();
			driver.findElement(By.id("cbbLineNumber")).click();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_4_1_1_1.log");
			driver.findElement(By.id("cbbLineNumber")).click();
			driver.findElement(By.cssSelector("#cbbLineNumber > option[value|=L001]")).click();
			driver.findElement(By.id("cbbColumnNumber")).click();
			driver.findElement(By.id("cbbColumnNumber")).click();
			driver.findElement(By.cssSelector("#cbbColumnNumber > option[value|=C001]")).click();
			capture(imagePath + "/0061_4_1_1_1");
			driver.findElement(By.id("cbbBlockName")).click();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_4_1_1_2.log");
			driver.findElement(By.id("cbbBlockName")).click();
			driver.findElement(By.cssSelector("#cbbBlockName > option[value|=B000]")).click();			    
			driver.findElement(By.id("cbbLineNumber")).click();
			driver.findElement(By.id("cbbLineNumber")).click();
			driver.findElement(By.id("cbbColumnNumber")).click();
			capture(imagePath + "/0061_4_1_1_2");
			driver.findElement(By.id("cbbColumnNumber")).click();
			driver.findElement(By.id("btnBack")).click();

			driver.findElement(By.id("btnTrace")).click();
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.id("cbbBlockName")).click();
			driver.findElement(By.id("cbbBlockName")).click();
			driver.findElement(By.cssSelector("#cbbBlockName > option[value|=B000]")).click();
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.id("cbbLineNumber")).click();
			driver.findElement(By.id("cbbLineNumber")).click();
			driver.findElement(By.cssSelector("#cbbLineNumber > option[value|=L001]")).click();
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.id("cbbColumnNumber")).click();
			driver.findElement(By.id("cbbColumnNumber")).click();
			driver.findElement(By.cssSelector("#cbbColumnNumber > option[value|=C001]")).click();
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.id("cbbSeason")).click();
			driver.findElement(By.id("cbbSeason")).click();
			driver.findElement(By.cssSelector("#cbbSeason > option[value|='0']")).click();
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_11_1.log");
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.id("txtGoToPage")).sendKeys("2");
			capture(imagePath + "/0061_11_1");
			driver.findElement(By.id("cbbBlockName")).click();
			driver.findElement(By.id("cbbBlockName")).click();
			driver.findElement(By.cssSelector("#cbbBlockName > option[value|='-2']")).click();
			driver.findElement(By.cssSelector("div#divCodeStart img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtCodeStart")).clear();
			driver.findElement(By.id("txtCodeStart")).sendKeys("01042017");
			driver.findElement(By.cssSelector("div#divCodeEnd img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtCodeEnd")).clear();
			driver.findElement(By.id("txtCodeEnd")).sendKeys("30042017");
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.cssSelector("div#divPlantEnd img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtPlantEnd")).clear();
			driver.findElement(By.id("txtPlantEnd")).sendKeys("25042017");
			driver.findElement(By.cssSelector("div#divPlantStart img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtPlantStart")).clear();
			driver.findElement(By.id("txtPlantStart")).sendKeys("01042017");
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.cssSelector("div#divFlowerStart img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtFlowerStart")).clear();
			driver.findElement(By.id("txtFlowerStart")).sendKeys("01042017");
			driver.findElement(By.cssSelector("div#divFlowerEnd img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtFlowerEnd")).clear();
			driver.findElement(By.id("txtFlowerEnd")).sendKeys("20042017");
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.cssSelector("div#divCloseEnd img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtCloseEnd")).clear();
			driver.findElement(By.id("txtCloseEnd")).sendKeys("15042017");
			driver.findElement(By.cssSelector("div#divCloseStart img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtCloseStart")).clear();
			driver.findElement(By.id("txtCloseStart")).sendKeys("01042017");
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.cssSelector("div#divHarvestStart img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtHarvestStart")).clear();
			driver.findElement(By.id("txtHarvestStart")).sendKeys("01042017");
			driver.findElement(By.cssSelector("div#divHarvestEnd img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtHarvestEnd")).clear();
			driver.findElement(By.id("txtHarvestEnd")).sendKeys("10042017");
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.cssSelector("div#divShipEnd img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtShipEnd")).clear();
			driver.findElement(By.id("txtShipEnd")).sendKeys("05042017");
			driver.findElement(By.cssSelector("div#divShipStart img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtShipStart")).clear();
			driver.findElement(By.id("txtShipStart")).sendKeys("01042017");
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.cssSelector("div#divWorkStart img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtWorkStart")).clear();
			driver.findElement(By.id("txtWorkStart")).sendKeys("01042017");
			driver.findElement(By.cssSelector("div#divWorkEnd img.ui-datepicker-trigger")).click();
			driver.findElement(By.id("txtWorkEnd")).clear();
			driver.findElement(By.id("txtWorkEnd")).sendKeys("30042017");
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.id("cbbProcessName")).click();
			driver.findElement(By.id("cbbProcessName")).click();
			driver.findElement(By.cssSelector("#cbbProcessName > option[value|=P011]")).click();
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.id("cbbTaskName")).click();
			driver.findElement(By.id("cbbTaskName")).click();
			driver.findElement(By.cssSelector("#cbbTaskName > option[value|=T048]")).click();
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.id("cbbStatusName")).click();
			driver.findElement(By.id("cbbStatusName")).click();
			driver.findElement(By.cssSelector("#cbbStatusName > option[value|=S003]")).click();
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.id("cbbUnitWork")).click();
			driver.findElement(By.id("cbbUnitWork")).click();
			driver.findElement(By.cssSelector("#cbbUnitWork > option[value|='0']")).click();
			driver.findElement(By.id("btnAccess")).click();
			driver.findElement(By.id("txtNote")).clear();
			driver.findElement(By.id("txtNote")).sendKeys("cultivation");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_5_1_1_1.log");
			driver.findElement(By.id("btnAccess")).click();
			capture(imagePath + "/0061_5_1_1_1");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_6_1vs2.log");
			driver.findElement(By.cssSelector(".view")).click();
			capture(imagePath + "/0061_6_1vs2");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_6_3.log");
			driver.findElement(By.id("btnCancelPopup")).click();
			capture(imagePath + "/0061_6_3");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_9_1_1vs2.log");
			driver.findElement(By.id("btnNext")).click();
			capture(imagePath + "/0061_9_1_1vs2");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_8_1_1vs2.log");
			driver.findElement(By.id("btnPrevious")).click();
			capture(imagePath + "/0061_8_1_1vs2");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_10_1_1vs2.log");
			driver.findElement(By.id("btnLast")).click();
			capture(imagePath + "/0061_10_1_1vs2");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_7_1_1vs2.log");
			driver.findElement(By.id("btnFirst")).click();
			capture(imagePath + "/0061_7_1_1vs2");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_11_2.log");
			driver.findElement(By.id("txtGoToPage")).clear();
			driver.findElement(By.id("txtGoToPage")).sendKeys("two");
			capture(imagePath + "/0061_11_2");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_11_3.log");
			driver.findElement(By.id("txtGoToPage")).sendKeys("2");
			capture(imagePath + "/0061_11_3");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_12_1_1vs2.log");
			driver.findElement(By.id("btnGoToPage")).click();
			capture(imagePath + "/0061_12_1_1vs2");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add(imagePath + "/0061_13_1.log");
			driver.findElement(By.id("btnBack")).click();
			capture(imagePath + "/0061_13_1");
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