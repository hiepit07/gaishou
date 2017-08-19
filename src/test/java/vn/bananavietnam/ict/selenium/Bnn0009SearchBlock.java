package vn.bananavietnam.ict.selenium;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

@RunWith(Parameterized.class)
public class Bnn0009SearchBlock {
	private static WebDriver driver;
	private String baseUrl;

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "U0000000001", "U0000000001" } });
	}

	@Parameter // first data value (0) is default
	public String fName;
	public String imagePath = "selenium_test_images/0009/success/   ";
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
		//driver = new InternetExplorerDriver();

		//driverFireFox = new FirefoxDriver();
		
		//driverChrome = new ChromeDriver();
		baseUrl = "http://localhost:8080";
		
	}
	/*@Test
	public void test0009SearchBlockWithIE11Jp()  throws Exception {
		driver = new InternetExplorerDriver();
		testBnn0009SearchBlock(driver, "jp", "IE");
	}*/
/*	@Test
	public void test0047FarmListWithEdgeJp()  throws Exception {
		driver = new InternetExplorerDriver();
		testBnn0009SearchBlock(driver, "jp", "IE");
	}

	@Test
	public void test0009SearchBlockWithIE11en()  throws Exception {
		driver = new InternetExplorerDriver();
		testBnn0009SearchBlock(driver, "en", "IE");
	}

	@Test
	public void test0009SearchBlockWithIE11vi()  throws Exception {
		driver = new InternetExplorerDriver();
		testBnn0009SearchBlock(driver, "vi", "IE");
	}
*/
	@Test
	public void test0009SearchBlockWithFireFox11Jp()  throws Exception {
		driver = new FirefoxDriver();
		testBnn0009SearchBlock(driver, "jp", "FireFox");
	}

/*	@Test
	public void test0009SearchBlockWithFireFox11en()  throws Exception {
		driver = new FirefoxDriver();
		testBnn0009SearchBlock(driver, "en", "FireFox");
	}

	@Test
	public void test0009SearchBlockWithFireFox11vi()  throws Exception {
		driver = new FirefoxDriver();
		testBnn0009SearchBlock(driver, "vi", "FireFox");
	}
	@Test
	public void test0009SearchBlockWithChrome11jp()  throws Exception {
		driver = new ChromeDriver();
		testBnn0009SearchBlock(driver, "jp", "Chrome");
	}

	@Test
	public void test0009SearchBlockWithChrome11en()  throws Exception {
		driver = new ChromeDriver();
		testBnn0009SearchBlock(driver, "en", "Chrome");
	}

	@Test
	public void test0009SearchBlockWithChrome11vi()  throws Exception {
		driver = new ChromeDriver();
		testBnn0009SearchBlock(driver, "vi", "Chrome");
	}*/
	/*@Test
	public void test0009SearchBlockWithEdgeJp()  throws Exception {
		driver = new EdgeDriver();
		testBnn0009SearchBlock(driver, "jp", "Edge");
	}

	@Test
	public void test0009SearchBlockWithEdgeEn()  throws Exception {
		driver = new EdgeDriver();
		testBnn0009SearchBlock(driver, "en", "Edge");
	}

	@Test
	public void test0009SearchBlockWithEdgeVi()  throws Exception {
		driver = new EdgeDriver();
		testBnn0009SearchBlock(driver, "vi", "Edge");
	}*/

	public void testBnn0009SearchBlock(WebDriver driver, String lan, String browser) throws Exception {
		checkPointArray = new ArrayList<Long>();
		logFileNameArray = new ArrayList<String>();
		driver.get(baseUrl + "/ict/?language=" + lan);
		// input user name and password
		driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("U0000000001");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("U0000000001");
	    driver.findElement(By.id("btnLogin")).click();
	    Thread.sleep(1000);
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_1_1_1_1.1.log");
		driver.get(baseUrl + "/ict/0007/");
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0009_1_1_1_1.1");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_1_1_1_1.2.log");
	    driver.findElement(By.id("F001")).click();
	    Thread.sleep(2000);
	    capture(imagePath +browser+"/"+lan+"/0009_1_1_1_1.2");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_1_1_1_2.1.log");
	    driver.findElement(By.id("btnGeneralSetting")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0009_1_1_1_2.1");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_1_1_1_2.2.log");
		driver.get(baseUrl + "/ict/0009/");
	    Thread.sleep(2000);
	    capture(imagePath +browser+"/"+lan+"/0009_1_1_1_2.2");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_2_1_1_1.1.log");
	    
	    new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("Biên Hòa");
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0009_2_1_1_1.1");
	    new Select(driver.findElement(By.id("cbbAreaName"))).selectByVisibleText("BH 002");
	    driver.findElement(By.id("btnSearch")).click();
	    Thread.sleep(1000);
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_13_1_1_1.1.log");
	    driver.findElement(By.id("btnRegister")).click();
	    Thread.sleep(1000);
		capture(imagePath +browser+"/"+lan+"/0009_13_1_1_1.1");
	    driver.findElement(By.id("txtBlockNamePopup")).clear();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_13_2_1_1.log");
	    driver.findElement(By.id("txtBlockNamePopup")).sendKeys("B7");
	    Thread.sleep(1000);
		capture(imagePath +browser+"/"+lan+"/0009_13_2_1_1");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_13_1_1_1.2.log");
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    Thread.sleep(1000);
		capture(imagePath +browser+"/"+lan+"/0009_13_1_1_1.2");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_4_1_1_1.1.log");
	    driver.findElement(By.id("popup_ok")).click();
	    // dump data to file
	 	SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + lan + "/" +"DATA_BEFORE_INSERT_BLOCK", "IVB_M_BLOCK");
	 	SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + lan + "/" +"DATA_BEFORE_INSERT_PRODUCT", "IVB_T_PRODUCT");
	 	Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0009_4_1_1_1.1");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_5_1_1_1.log");
	    driver.findElement(By.id("btnSearch")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0009_5_1_1_1");
	    driver.findElement(By.cssSelector("img[name=\"5\"]")).click();
	    driver.findElement(By.id("txtBlockNamePopup")).clear();
	    driver.findElement(By.id("txtBlockNamePopup")).sendKeys("B777");
	    driver.findElement(By.id("txtNotePopup")).clear();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_5_3_1_1.1.log");
	    driver.findElement(By.id("txtNotePopup")).sendKeys("OK");
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0009_5_3_1_1.1");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_5_3_1_1.2.log");
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0009_5_3_1_1.2");
	    driver.findElement(By.id("popup_ok")).click();
	    // dump data to file
	 	SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + lan + "/" +"DATA_BEFORE_UPDATE_BLOCK", "IVB_M_BLOCK");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_6_1_1_1.log");
	    driver.findElement(By.xpath("(//img[@name='5'])[2]")).click();
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0009_6_1_1_1");
	    driver.findElement(By.id("popup_ok")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_13_3_1_1.1.log");
	    driver.findElement(By.id("popup_ok")).click();
	    // dump data to file
	 	SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + lan + "/" +"DATA_BEFORE_DELETE_BLOCK", "IVB_M_BLOCK");
	 // dump data to file
	 	 	SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + lan + "/" +"DATA_BEFORE_DELETE_PRODUCT", "IVB_M_BLOCK");
	 	Thread.sleep(1000);
		capture(imagePath +browser+"/"+lan+"/0009_13_3_1_1.1");
	    new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("Biên Hòa");
	    new Select(driver.findElement(By.id("cbbAreaName"))).selectByVisibleText("BH 002");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_15_3_1_1.log");
	    driver.findElement(By.id("btnSearch")).click();
	    Thread.sleep(1000);
		capture(imagePath +browser+"/"+lan+"/0009_15_3_1_1");
	    driver.findElement(By.linkText("B000")).click();
	    driver.findElement(By.id("selectAll")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_18_1_1_1.log");
	    driver.findElement(By.id("unSelectAll")).click();
	    Thread.sleep(1000);
		capture(imagePath +browser+"/"+lan+"/0009_18_1_1_1");
	   
	    driver.findElement(By.id("selectAll")).click();
	    driver.findElement(By.id("btnDisableProductPopup")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_20_1_1_1.log");
	    
	    driver.findElement(By.id("popup_ok")).click();
	    Thread.sleep(1000);
		capture(imagePath +browser+"/"+lan+"/0009_20_1_1_1");
		driver.findElement(By.linkText("B000")).click();
		 driver.findElement(By.id("selectAll")).click();
	    driver.findElement(By.id("btnCancelProductPopup")).click();
	   
	    driver.findElement(By.id("popup_ok")).click();
	    /*driver.findElement(By.cssSelector("td.align-center > a[name=\"0\"]")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_19_1_1_1.log");
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0009_19_1_1_1");
	    driver.findElement(By.id("btnCancelProductPopup")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_6_1_1_1.log");
	    driver.findElement(By.id("popup_ok")).click();*/
	    
	    Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0009_6_1_1_1");
	    new Select(driver.findElement(By.id("cbbAreaName"))).selectByVisibleText("BH 001");
	    driver.findElement(By.id("btnSearch")).click();
		// click button >, >>, <, <<.
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_9_1_1_1.log");
		driver.findElement(By.id("btnNext")).click();
		capture(imagePath +browser+"/"+lan+"/0009_9_1_1_1");
		Thread.sleep(1000);
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_7_1_1_1.log");
		driver.findElement(By.id("btnPrevious")).click();
		capture(imagePath +browser+"/"+lan+"/0009_7_1_1_1");

		Thread.sleep(1000);
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_10_1_1_1.log");
		driver.findElement(By.id("btnLast")).click();
		capture(imagePath +browser+"/"+lan+"/0009_10_1_1_1");
		Thread.sleep(1000);
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_8_1_1_1.log");
		driver.findElement(By.id("btnFirst")).click();
		capture(imagePath +browser+"/"+lan+"/0009_8_1_1_1");
		
	    driver.findElement(By.id("txtGoToPage")).clear();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_11_1_1_1.1.log");
	    driver.findElement(By.id("txtGoToPage")).sendKeys("1");
	    Thread.sleep(1000);
		capture(imagePath +browser+"/"+lan+"/0009_11_1_1_1.1");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_11_1_1_1.2.log");
	    driver.findElement(By.id("btnGoToPage")).click();
	    Thread.sleep(1000);
		capture(imagePath +browser+"/"+lan+"/0009_11_1_1_1.2");
	    driver.findElement(By.id("txtGoToPage")).clear();
	    driver.findElement(By.id("txtGoToPage")).sendKeys("2");
	    driver.findElement(By.id("btnGoToPage")).click();
	    driver.findElement(By.id("txtGoToPage")).clear();
	    driver.findElement(By.id("txtGoToPage")).sendKeys("3");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_14_1_1_1.1.log");
		Thread.sleep(1000);
	    driver.findElement(By.id("btnGoToPage")).click();
	    Thread.sleep(1000);
		capture(imagePath +browser+"/"+lan+"/0009_14_1_1_1.1");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0009_14_1_1_1.2.log");
	    driver.findElement(By.id("btnBack")).click();
	    Thread.sleep(1000);
		capture(imagePath +browser+"/"+lan+"/0009_14_1_1_1.2");
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
