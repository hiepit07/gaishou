package vn.bananavietnam.ict.selenium;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
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


@RunWith(Parameterized.class)
public class Bnn0005SearchFarm {
	private static WebDriver driver;
	private String baseUrl;
	messageAlertBnn0005 pages = new messageAlertBnn0005();

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "U0000000001", "U0000000001" } });
	}

	@Parameter // first data value (0) is default
	public String fName;

	@Parameter(1)
	public String fPass;

	// define log evidence array here
	ArrayList<Long> checkPointArray;
	ArrayList<String> logFileNameArray;
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "webdriver/geckodriver.exe");
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");
		System.setProperty("webdriver.edge.driver", "webdriver/MicrosoftWebDriver.exe");
		
		//baseUrl = "http://172.16.0.17:8080";
		baseUrl = "http://localhost:8080";
	}

	public void common(String browser, String language) {
		checkPointArray = new ArrayList<Long>();
		logFileNameArray = new ArrayList<String>();
		
		try {
			setMessage(language);
			if (browser == "IE") {
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ignore");
				driver = new InternetExplorerDriver(ieCapabilities);
			} else if (browser == "Chrome") {
				driver = new ChromeDriver();
			} else if (browser == "FireFox") {
				driver = new FirefoxDriver();
			} else {
				driver = new EdgeDriver();
			}
			
			driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
			if (language == "vi") {
				driver.get(baseUrl + "/ict/?language=vi");
			} else if (language == "en") {
				driver.get(baseUrl + "/ict/?language=en");
			} else if (language == "jp") {
				driver.get(baseUrl + "/ict/?language=jp");
			}
			
			
			// start testing
			// Thread.sleep(3000);
			// input user name and password
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
			
			driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("U0000000001");
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("U0000000001");
		    //login page
		    driver.findElement(By.id("btnLogin")).click();
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_1_1_1_1.log");
		    //goto Bnn0005
		    driver.findElement(By.id("btnFarmScreen")).click();
		    
			// dump data to file
		    // Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/selenium_test_images/0005/Success/" + browser + "/" + language + "/DATA_BEFORE", "IVB_M_FARM");
			
		    // Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_1_1_1_1");
		    //search with farm name
		    new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("Biên Hòa");
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
		    logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_2_1_1_1.log");
		    driver.findElement(By.id("btnSearch")).click();
		    // Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_2_1_1_1");
		    //search blank
		    new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("");
		    driver.findElement(By.id("btnSearch")).click();
		    // Thread.sleep(1000);
		    //create new
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
		    logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_11_1_1_1.log");
		    
			// dump data to file
		    // Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/selenium_test_images/0005/Success/" + browser + "/" + language + "/DATA_BEFORE_INSERT", "IVB_M_FARM");
			
			// Thread.sleep(1000);
		    driver.findElement(By.id("btnNew")).click();
		    // Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_11_1_1_1");
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
			logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_11_2_1_1.log");
			// Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_11_2_1_1");
		    //click create
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    driver.findElement(By.id("txtFarmNamePopup")).clear();
		    driver.findElement(By.id("txtFarmNamePopup")).sendKeys("Ninh Thuận");
		    //click create
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    driver.findElement(By.id("txtAddressPopup")).clear();
		    driver.findElement(By.id("txtAddressPopup")).sendKeys("Phan Rang - Ninh Thuận");
		    //click create
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    driver.findElement(By.cssSelector("img.ui-datepicker-trigger")).click();
		    driver.findElement(By.linkText("30")).click();
		    // Thread.sleep(1000);
		    
		    driver.findElement(By.cssSelector("img.ui-datepicker-trigger")).click();
		    driver.findElement(By.id("txtTimeFromPopup")).sendKeys("01042017");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.xpath("//div[@id='popupWrapper']/div[2]/div/div/div[5]/div[2]/div[2]/img")).click();
		    driver.findElement(By.id("txtTimeToPopup")).sendKeys("30042017");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.xpath("//div[@id='popupWrapper']/div[2]/div/div/div[7]/div[2]/div/img")).click();
		    driver.findElement(By.id("txtOpenDatePopup")).sendKeys("30042017");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    
		    //click create
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    driver.findElement(By.id("txtSizeOfPlanPopup")).clear();
		    driver.findElement(By.id("txtSizeOfPlanPopup")).sendKeys("4200");
		    //click create
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    driver.findElement(By.id("txtLineOfPlanPopup")).clear();
		    driver.findElement(By.id("txtLineOfPlanPopup")).sendKeys("2");
		    //click create
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    driver.findElement(By.id("txtColumnOfPlanPopup")).clear();
		    driver.findElement(By.id("txtColumnOfPlanPopup")).sendKeys("48");
		    //click create
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    driver.findElement(By.id("txtClimatePopup")).clear();
		    driver.findElement(By.id("txtClimatePopup")).sendKeys("Hot");
		    //click create
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    driver.findElement(By.id("txtSoilPopup")).clear();
		    driver.findElement(By.id("txtSoilPopup")).sendKeys("feralit");
		    //click create
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    driver.findElement(By.id("txtEmailPopup")).clear();
		    driver.findElement(By.id("txtEmailPopup")).sendKeys("ninhthuan@gmail.com");
		    //click create
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    driver.findElement(By.id("txtPhonePopup")).clear();
		    driver.findElement(By.id("txtPhonePopup")).sendKeys("8585-8585");
		    
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
		    logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_11_3_2_1.log");
		    //click create
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_11_3_2_1");
		    driver.findElement(By.id("popup_ok")).click();
		    driver.findElement(By.id("txtFaxPopup")).clear();
		    driver.findElement(By.id("txtFaxPopup")).sendKeys("8585-8585");
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
		    logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_11_3_1_1.log");
		    //click create
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_11_3_1_1");
			// Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();

			
		    driver.findElement(By.id("btnNew")).click();
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
		    logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_11_4_1_1.log");
		    driver.findElement(By.id("btnCancelFarmPopup")).click();
		    
		 // dump data to file
		    // Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/selenium_test_images/0005/Success/" + browser + "/" + language + "/DATA_AFTER_INSERT", "IVB_M_FARM");
			// Thread.sleep(1000);
		    
		    // Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_11_4_1_1");
			
		    //edit
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
			logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_3_2_1_1.log");
			
			// dump data to file
			// Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/selenium_test_images/0005/Success/" + browser + "/" + language + "/DATA_BEFORE_UPDATE", "IVB_M_FARM");
			// Thread.sleep(1000);
		    driver.findElement(By.name("0")).click();
		    // Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_3_2_1_1");
			
		    driver.findElement(By.id("txtFarmNamePopup")).clear();
		    driver.findElement(By.id("txtFarmNamePopup")).sendKeys("");
		    //click edit
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    driver.findElement(By.id("txtFarmNamePopup")).clear();
		    driver.findElement(By.id("txtFarmNamePopup")).sendKeys("Biên Hòa");
		    driver.findElement(By.id("txtAddressPopup")).clear();
		    driver.findElement(By.id("txtAddressPopup")).sendKeys("");
		    //click edit
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    driver.findElement(By.id("txtAddressPopup")).clear();
		    driver.findElement(By.id("txtAddressPopup")).sendKeys("Đường Nguyễn Ái Quốc, phường Tân phong, thành phố, Đường Nguyễn Ái Quốc, KP7, P. Tân Phong, Tp Biên Hoà, tỉnh Đồng Nai, Tân Phong, Tp. Biên Hòa, Đồng Nai");

		    driver.findElement(By.id("txtTimeFromPopup")).clear();
		    driver.findElement(By.cssSelector("img.ui-datepicker-trigger")).click();
		    driver.findElement(By.id("txtTimeFromPopup")).sendKeys("01042017");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("txtTimeToPopup")).clear();
		    driver.findElement(By.xpath("//div[@id='popupWrapper']/div[2]/div/div/div[5]/div[2]/div[2]/img")).click();
		    driver.findElement(By.id("txtTimeToPopup")).sendKeys("30042017");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("txtOpenDatePopup")).clear();
		    driver.findElement(By.xpath("//div[@id='popupWrapper']/div[2]/div/div/div[7]/div[2]/div/img")).click();
		    driver.findElement(By.id("txtOpenDatePopup")).sendKeys("30042017");
		    // Thread.sleep(1000);
		    
		    //click edit
		    // Thread.sleep(1000);
		    driver.findElement(By.id("txtSizeOfPlanPopup")).clear();
		    driver.findElement(By.id("txtSizeOfPlanPopup")).sendKeys("3500");
		    //click edit
		    driver.findElement(By.id("btnRegisterFarmPopup")).click();
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_3_3_1_1");
			
			// dump data to file
			// Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/selenium_test_images/0005/Success/" + browser + "/" + language + "/DATA_AFTER_UPDATE", "IVB_M_FARM");
			// Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    //click cancel in popup
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnSearch")).click();
		    driver.findElement(By.name("0")).click();
		    // Thread.sleep(1000);
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
		    logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_3_4_1_1.log");
		    driver.findElement(By.id("btnCancelFarmPopup")).click();
		    // Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_3_4_1_1");

			// dump data to file
			// Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/selenium_test_images/0005/Success/" + browser + "/" + language + "/DATA_BEFORE_DELETE", "IVB_M_FARM");
			// Thread.sleep(1000);
		    //click delete
			driver.findElement(By.xpath("(//img[@name='0'])[2]")).click();
			// Thread.sleep(1000);
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
			logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_4_3_1_1.log");
		    driver.findElement(By.id("popup_cancel")).click();
		    // Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_4_3_1_1");
			
			// Thread.sleep(1000);
			driver.findElement(By.xpath("(//img[@name='15'])[2]")).click();
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
			logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_4_1_1_1.log");
			// Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_4_1_1_1");
			// Thread.sleep(1000);
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
			logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_4_2_1_1.log");
		    driver.findElement(By.id("popup_ok")).click();
		    // Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_4_2_1_1");
			// Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();

			// dump data to file
		    // Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/selenium_test_images/0005/Success/" + browser + "/" + language + "/DATA_AFTER_DELETE", "IVB_M_FARM");
			
		    // click button >, >>, <, <<. and take photos
			// Thread.sleep(1000);
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
			logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_7_1_1_1.log");
			driver.findElement(By.id("btnNext")).click();
			// Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_7_1_1_1");
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
			logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_7_1_2_1.log");
			// Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_7_1_2_1");
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
			logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_6_1_1_1.log");
			driver.findElement(By.id("btnPrevious")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_6_1_1_1");
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
		    logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_6_1_2_1.log");
		    // Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_6_1_2_1");
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
			logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_8_1_1_1.log");
			driver.findElement(By.id("btnLast")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_8_1_1_1");
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
		    logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_8_1_2_1.log");
		    // Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_8_1_2_1");
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
			logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_5_1_1_1.log");
		    driver.findElement(By.id("btnFirst")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_5_1_1_1");
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
		    logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_5_1_2_1.log");
		    // Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_5_1_2_1");
		    driver.findElement(By.id("txtGoToPage")).sendKeys("2");
		    // Thread.sleep(1000);
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
		    logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_10_1_1_1.log");
		    driver.findElement(By.id("btnGoToPage")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_10_1_1_1");
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
		    logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_10_1_2_1.log");
		    // Thread.sleep(1000);
			capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_10_1_2_1");
		    //click back
		    // store check point information
		 	// Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	// Thread.sleep(2000);
			logFileNameArray.add("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_12_1_1_1.log");
		    driver.findElement(By.id("btnBack")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0005/Success/" + browser + "/" + language + "/0005_12_1_1_1");
	
		} catch ( Exception ex ) {
			System.err.println("Can't open browser");
			assertEquals("1", "");
		}
	}

	@Test
	  public void testBnn0005IeVi() throws Exception {
	    common("IE", "vi");
	    
	  }
	@Test
	  public void testBnn0005IeEn() throws Exception {
	    common("IE", "en");
	    
	  }
	@Test
	  public void testBnn0005IeJp() throws Exception {
	    common("IE", "jp");
	    
	  }
	@Test
	  public void testBnn0005ChromeVi() throws Exception {
	    common("Chrome", "vi");
	    
	  }
	@Test
	  public void testBnn0005ChromeEn() throws Exception {
	    common("Chrome", "en");
	    
	  }
	@Test
	  public void testBnn0005ChromeJp() throws Exception {
	    common("Chrome", "jp");
	    
	  }
	@Test
	  public void testBnn0005FireFoxVi() throws Exception {
	    common("FireFox", "vi");
	    
	  }
	@Test
	  public void testBnn0005FireFoxEn() throws Exception {
	    common("FireFox", "en");
	    
	  }
	@Test
	  public void testBnn0005FireFoxJp() throws Exception {
	    common("FireFox", "jp");
	    
	  }
	
	@Test
	  public void testBnn0005EdgeVi() throws Exception {
	    common("edge", "vi");
	    
	  }
	@Test
	  public void testBnn0005EdgeEn() throws Exception {
	    common("edge", "en");
	    
	  }
	@Test
	  public void testBnn0005EdgeJp() throws Exception {
	    common("edge", "jp");
	    
	  }
	
	@After
	public void tearDown() throws Exception {
		try {	
			SeleniumCommon.startPrintingLog(checkPointArray, logFileNameArray);
			driver.quit();
		} catch ( Exception ex ) {
			System.err.println("Can't close browser");
		}
	}

	//take photos
	public static boolean capture(String fileName) {
		  
		  File file = new File(fileName + ".png");
		  String folder = FilenameUtils.getFullPathNoEndSeparator(file.getAbsolutePath());
		  File theDir = new File(folder);

		  // if the directory does not exist, create it
		  if (!theDir.exists()) {
		      System.out.println("creating directory: " + theDir.getName());
		      boolean result = false;
		      try{
		          theDir.mkdirs();
		          result = true;
		      } catch (SecurityException se) {
		          //handle it
		      }        
		      if(result) {    
		          System.out.println("DIR created");  
		      }
		  }
		  
		  
		  try {
		   File f = new File(fileName + ".png");
		   File oFile = ((TakesScreenshot) driver)
		     .getScreenshotAs(OutputType.FILE);
		   BufferedImage image = ImageIO.read(oFile);
		   ImageIO.write(image, "png", f);
		   return true;
		  } catch (Exception e) {
		   e.printStackTrace();
		   return false;
		  }
		 }
	
	// set all text in header
	public void setMessage(String lang){
		if(lang == "jp"){
			pages.setLang(lang);
			// data no exit
			pages.setNoDataExit("データがありません。");
			//alert delete
			pages.setAlertDelete("削除します。よろしいですか？");
		}else if(lang == "en"){
			pages.setLang(lang);
			// data no exit
			pages.setNoDataExit("Data do not exist!");
			//alert delete
			pages.setAlertDelete("Are you sure you want to delete this information?");
		}else if (lang == "vi"){
			pages.setLang(lang);
			// data no exit
			pages.setNoDataExit("Không tồn tại thông tin này!");
			//alert delete
			pages.setAlertDelete("Bạn có muốn xóa thông tin này không?");
		}
	}
	
	  public class messageAlertBnn0005{
		  
		public String lang;
		public String noDataExit;
		public String alertDelete;
		public String getLang() {
			return lang;
		}

		public void setLang(String lang) {
			this.lang = lang;
		}
		public String getNoDataExit() {
			return noDataExit;
		}

		public void setNoDataExit(String noDataExit) {
			this.noDataExit = noDataExit;
		}

		public String getAlertDelete() {
			return alertDelete;
		}

		public void setAlertDelete(String alertDelete) {
			this.alertDelete = alertDelete;
		}
		
	  }
}
