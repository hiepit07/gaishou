package vn.bananavietnam.ict.selenium;

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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

@RunWith(Parameterized.class)
public class Bnn0007SearchArea {
	private static WebDriver driver;
	private String baseUrl;

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "U0000000001", "U0000000001" } });
	}

	@Parameter // first data value (0) is default
	public String fName;

	@Parameter(1)
	public String fPass;
	public String imagePath = "selenium_test_images/0007/success/";
	// define log evidence array here
	ArrayList<Long> checkPointArray;
	ArrayList<String> logFileNameArray;
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "webdriver/geckodriver.exe");
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");
		System.setProperty("webdriver.edge.driver", "webdriver/MicrosoftWebDriver.exe");

		baseUrl = "http://localhost:8080";
	}

	/*	@Test
	public void test0007SearchAreaWithIE11Jp()  throws Exception {
		driver = new InternetExplorerDriver();
		testBnn0007SearchArea(driver, "jp", "IE");
	}*/
	@Test
	public void test0007SearchAreaWithIE11Jp()  throws Exception {
		driver = new InternetExplorerDriver();
		testBnn0007SearchArea(driver, "vi", "IE");
	}

	/*@Test
	public void test0007SearchAreaWithIE11en()  throws Exception {
		driver = new InternetExplorerDriver();
		testBnn0007SearchArea(driver, "en", "IE");
	}

	@Test
	public void test0007SearchAreaWithIE11vi()  throws Exception {
		driver = new InternetExplorerDriver();
		testBnn0007SearchArea(driver, "jp", "IE");
	}*/

	/*@Test
	public void test0007SearchAreaWithFireFox11Jp()  throws Exception {
		driver = new FirefoxDriver();
		testBnn0007SearchArea(driver, "jp", "FireFox");
	}

	@Test
	public void test0007SearchAreaWithFireFox11en()  throws Exception {
		driver = new FirefoxDriver();
		testBnn0007SearchArea(driver, "en", "FireFox");
	}

	@Test
	public void test0007SearchAreaWithFireFox11vi()  throws Exception {
		driver = new FirefoxDriver();
		testBnn0007SearchArea(driver, "vi", "FireFox");
	}*/
	/*@Test
	public void test0007SearchAreaWithChrome11jp()  throws Exception {
		driver = new ChromeDriver();
		testBnn0007SearchArea(driver, "jp", "Chrome");
	}

	@Test
	public void test0007SearchAreaWithChrome11en()  throws Exception {
		driver = new ChromeDriver();
		testBnn0007SearchArea(driver, "en", "Chrome");
	}*/

	/*@Test
	public void test0007SearchAreaWithChrome11vi()  throws Exception {
		driver = new ChromeDriver();
		testBnn0007SearchArea(driver, "vi", "Chrome");
	}*/
	/*@Test
	public void test0007SearchAreaWithEdgeJp()  throws Exception {
		driver = new EdgeDriver();
		testBnn0007SearchArea(driver, "jp", "Edge");
	}

	@Test
	public void test0007SearchAreaWithEdgeEn()  throws Exception {
		driver = new EdgeDriver();
		testBnn0007SearchArea(driver, "en", "Edge");
	}

	@Test
	public void test0007SearchAreaWithEdgeVi()  throws Exception {
		driver = new EdgeDriver();
		testBnn0007SearchArea(driver, "vi", "Edge");
	}*/

	public void testBnn0007SearchArea(WebDriver driver, String lan, String browser) throws Exception {
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		checkPointArray = new ArrayList<Long>();
		logFileNameArray = new ArrayList<String>();
		SeleniumCommon.enableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		driver.get(baseUrl + "/ict/?language=" + lan);
		// input user name and password
		driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("U0000000001");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("U0000000001");
	    driver.findElement(By.id("btnLogin")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_1_1_1_1.1.log");
		driver.get(baseUrl + "/ict/0005/");
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_1_1_1_1.1");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_1_1_1_1.2.log");
	    
	    driver.findElement(By.cssSelector("span.goBnn0007")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_1_1_1_1.2");

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_1_1_1_2.1.log");
	    driver.findElement(By.id("btnGeneralSetting")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_1_1_1_2.1");

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_1_1_1_2.2.log");
		driver.get(baseUrl + "/ict/0007/");
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_1_1_1_2.2");

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_3_1_2_1.log");
	    new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("Đồng Nai");
	    driver.findElement(By.id("btnSearch")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_3_1_2_1");
	    driver.findElement(By.id("popup_ok")).click();

	    new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("Sài Gòn");
	    driver.findElement(By.id("btnSearch")).click();

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_12_1_1_1.log");
	    driver.findElement(By.id("btnRegister")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_12_1_1_1");
	    driver.findElement(By.id("txtAreaNamePopup")).clear();
	    driver.findElement(By.id("txtAreaNamePopup")).sendKeys("SG 010");
	    new Select(driver.findElement(By.id("cbbKindNamePopup"))).selectByVisibleText("ATAN BANANA");
	    driver.findElement(By.id("txtSugarContentPopup")).clear();
	    driver.findElement(By.id("txtSugarContentPopup")).sendKeys("1");
	    driver.findElement(By.id("txtTexturePopup")).clear();
	    driver.findElement(By.id("txtTexturePopup")).sendKeys("1");
	    driver.findElement(By.id("txtNotePopup")).clear();
	    driver.findElement(By.id("txtNotePopup")).sendKeys("1");
	    driver.findElement(By.id("txtNumberOfBlockPopup")).sendKeys("");

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_12_3_2_1.log");
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_12_3_2_1");
	    driver.findElement(By.id("popup_ok")).click();
	    driver.findElement(By.id("txtNumberOfBlockPopup")).clear();
	    driver.findElement(By.id("txtNumberOfBlockPopup")).sendKeys("1");
/*


		// dump data to file
		SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser+"/"+lan+"/"+"DATA_BEFORE_INSERT_AREA", "IVB_M_AREA");

		// dump data to file
		SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser+"/"+lan+"/"+ "DATA_BEFORE_INSERT_BLOCK", "IVB_M_BLOCK");

		// dump data to file
		SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser+"/"+lan+"/"+ "DATA_BEFORE_INSERT_PRODUCT", "IVB_T_PRODUCT");
	    */
		// store check point information 
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_12_3_1_1.log");
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_12_3_1_1");
	    driver.findElement(By.id("popup_ok")).click();
		// dump data to file
		SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser+"/"+lan+"/"+ "DATA_AFTER_INSERT_AREA", "IVB_M_AREA");

		// dump data to file
		SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser+"/"+lan+"/"+ "DATA_AFTER_INSERT_BLOCK", "IVB_M_BLOCK");

		// dump data to file
		SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath+ browser+"/"+lan+"/"+ "DATA_AFTER_INSERT_PRODUCT", "IVB_T_PRODUCT");
	    new Select(driver.findElement(By.id("cbbAreaName"))).selectByVisibleText("");
	    driver.findElement(By.id("btnSearch")).click();/*
	    driver.findElement(By.xpath("#row5 > td >.delete")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_5_3_1_1");
	    driver.findElement(By.id("popup_cancel")).click();*/
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_5_1_1_1.log");

		// dump data to file
		SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser+"/"+lan+"/"+"DATA_BEFORE_DELETE_AREA", "IVB_M_AREA");

		// dump data to file
		SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser+"/"+lan+"/"+ "DATA_BEFORE_DELETE_BLOCK", "IVB_M_BLOCK");

		// dump data to file
		SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser+"/"+lan+"/"+ "DATA_BEFORE_DELETE_PRODUCT", "IVB_T_PRODUCT");
	    
	    driver.findElement(By.cssSelector("#row5 > td >.delete")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_5_1_1_1");

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_5_2_1_1.log");
	    driver.findElement(By.id("popup_ok")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_5_2_1_1");

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_5_3_1_1.log");
	    driver.findElement(By.id("popup_ok")).click();

		// dump data to file
		SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser+"/"+lan+"/"+"DATA_AFTER_DELETE_AREA", "IVB_M_AREA");

		// dump data to file
		SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser+"/"+lan+"/"+ "DATA_AFTER_DELETE_BLOCK", "IVB_M_BLOCK");

		// dump data to file
		SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser+"/"+lan+"/"+ "DATA_AFTER_DELETE_PRODUCT", "IVB_T_PRODUCT");
	    
	    driver.findElement(By.id("btnGeneralSetting")).click();

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_13_1_1_1.1.log");
		driver.get(baseUrl + "/ict/0005/");
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_13_1_1_1.1");
	    driver.findElement(By.cssSelector("span.goBnn0007")).click();

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_13_1_1_1.2.log");
		Thread.sleep(1000);
	    driver.findElement(By.id("btnBack")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_13_1_1_1.2");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_14_1_1_1.1.log");
		
		driver.get(baseUrl + "/ict/0005/");
	    Thread.sleep(2000);
	    
	    driver.findElement(By.cssSelector("span.goBnn0007")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_14_1_1_1.1");

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_14_1_1_1.2.log");
	    driver.findElement(By.id("F001")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_14_1_1_1.2");

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_14_1_1_1.3.log");
	    driver.findElement(By.id("btnBack")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_14_1_1_1.3");
	    new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("Sài Gòn");

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_3_1_1_1.log");
	    driver.findElement(By.id("btnSearch")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_3_1_1_1");

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_4_1_1_1.log");
	    driver.findElement(By.cssSelector("#row0 > td > .edit")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_4_1_1_1");
	    driver.findElement(By.id("txtAreaNamePopup")).clear();
	    driver.findElement(By.id("txtAreaNamePopup")).sendKeys("BH 0066");
	    new Select(driver.findElement(By.id("cbbKindNamePopup"))).selectByVisibleText("NAM MY BANANA");
	    driver.findElement(By.id("txtTexturePopup")).clear();
	    driver.findElement(By.id("txtTexturePopup")).sendKeys("1");
	    driver.findElement(By.id("txtNotePopup")).clear();
	    driver.findElement(By.id("txtNotePopup")).sendKeys("1");

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_4_3_1_1.log");
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0007_4_3_1_1");
	    driver.findElement(By.id("popup_ok")).click();
		// dump data to file
		SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser+"/"+lan+"/"+ "DATA_AFTER_UPDATE", "IVB_M_AREA");
	    
		// click button >, >>, <, <<.
		Thread.sleep(1000);
		driver.get(baseUrl + "/ict/0007/?language=jp");

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_8_1_1_1.log");
		driver.findElement(By.id("btnNext")).click();
		capture(imagePath +browser+"/"+lan+"/0007_8_1_1_1");
		Thread.sleep(1000);
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_6_1_1_1.log");
		driver.findElement(By.id("btnPrevious")).click();
		capture(imagePath +browser+"/"+lan+"/0007_6_1_1_1");

		Thread.sleep(1000);
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_9_1_1_1.log");
		driver.findElement(By.id("btnLast")).click();
		capture(imagePath +browser+"/"+lan+"/0007_9_1_1_1");
		Thread.sleep(1000);
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_7_1_1_1.log");
		driver.findElement(By.id("btnFirst")).click();
		capture(imagePath +browser+"/"+lan+"/0007_7_1_1_1");
		Thread.sleep(1000);

		driver.findElement(By.id("txtGoToPage")).clear();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_10_3_1_1.log");
		driver.findElement(By.id("txtGoToPage")).sendKeys("2");
		Thread.sleep(1000);
		capture(imagePath +browser+"/"+lan+"/0007_10_3_1_1");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath +browser+"/"+lan+"/0007_11_1_1_1.log");
		driver.findElement(By.id("btnGoToPage")).click();
		Thread.sleep(1000);
		capture(imagePath +browser+"/"+lan+"/0007_11_1_1_1");
		driver.findElement(By.id("btnFirst")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		SeleniumCommon.startPrintingLog(checkPointArray, logFileNameArray);
	}

	// capture("selenium_test_images/Bnn0005SearchFarmInit");
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

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
