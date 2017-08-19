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
public class Bnn0007SearchAreaErrorTesst {
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
	public String imagePath = "selenium_test_images/0007/Error/";
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
		driver.get(baseUrl + "/ict/?language=" + lan);
		Thread.sleep(3000);
		SeleniumCommon.enableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
		// input user name and password
		driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("U0000000001");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("U0000000001");
		Thread.sleep(3000);
		SeleniumCommon.enableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
		driver.findElement(By.id("btnLogin")).click();
		
		driver.get(baseUrl + "/ict/0005/");
		// stop server connection ----------------------------
		Thread.sleep(3000);
		SeleniumCommon.disableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0007_1_1_1_1.1.log");
		driver.get(baseUrl + "/ict/0007/");
		capture(imagePath +browser+"/"+lan+"/0007_1_1_1_1.1");
		Thread.sleep(3000);
		SeleniumCommon.enableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
	    driver.findElement(By.id("btnGeneralSetting")).click();
		// stop server connection ----------------------------
	    Thread.sleep(3000);
		SeleniumCommon.disableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
		driver.get(baseUrl + "/ict/0007/");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0007_1_1_1_1.2.log");
		capture(imagePath +browser+"/"+lan+"/0007_1_1_1_1.2");
	    driver.findElement(By.id("popup_ok")).click();
	    Thread.sleep(3000);
		SeleniumCommon.enableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);

		driver.findElement(By.id("btnGeneralSetting")).click();
		driver.get(baseUrl + "/ict/0007/");

	    driver.findElement(By.id("btnRegister")).click();
		// stop server connection ----------------------------
	    Thread.sleep(3000);
		SeleniumCommon.disableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
	    driver.findElement(By.id("btnRegisterPopup")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0007_10_1_2_1.log");
		capture(imagePath +browser+"/"+lan+"/0007_10_1_2_1");
	    driver.findElement(By.id("popup_ok")).click();
		
	    driver.findElement(By.id("txtAreaNamePopup")).clear();
	    driver.findElement(By.id("txtAreaNamePopup")).sendKeys("BH 023");
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    driver.findElement(By.id("popup_ok")).click();
	    driver.findElement(By.id("txtNumberOfBlockPopup")).clear();
	    driver.findElement(By.id("txtNumberOfBlockPopup")).sendKeys("1");
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    driver.findElement(By.id("popup_ok")).click();
	    new Select(driver.findElement(By.id("cbbKindNamePopup"))).selectByVisibleText("ATAN BANANA");
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    driver.findElement(By.id("popup_ok")).click();
	    driver.findElement(By.id("txtSugarContentPopup")).clear();
	    driver.findElement(By.id("txtSugarContentPopup")).sendKeys("1");
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    driver.findElement(By.id("popup_ok")).click();
	    driver.findElement(By.id("txtTexturePopup")).clear();
	    driver.findElement(By.id("txtTexturePopup")).sendKeys("1");
	    driver.findElement(By.id("btnRegisterPopup")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0007_10_1_2_2.log");
		capture(imagePath +browser+"/"+lan+"/0007_10_1_2_2");
	    driver.findElement(By.id("popup_ok")).click();
	    Thread.sleep(3000);
		SeleniumCommon.enableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    driver.findElement(By.id("popup_ok")).click();

	    driver.findElement(By.name("0")).click();
	    driver.findElement(By.id("txtAreaNamePopup")).clear();
	    driver.findElement(By.id("txtAreaNamePopup")).sendKeys("BH 023");
		// stop server connection ----------------------------
	    Thread.sleep(3000);
		SeleniumCommon.disableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
	    driver.findElement(By.id("btnRegisterPopup")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0007_3_1_2_1.log");
		capture(imagePath +browser+"/"+lan+"/0007_3_1_2_1");
	    driver.findElement(By.id("popup_ok")).click();
	    Thread.sleep(3000);
		SeleniumCommon.enableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
	    new Select(driver.findElement(By.id("cbbKindNamePopup"))).selectByVisibleText("DWARF RED BANANA");
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    driver.findElement(By.id("popup_ok")).click();
	    driver.findElement(By.id("btnLast")).click();
	    driver.findElement(By.xpath("(//img[@name='0'])[2]")).click();
		// stop server connection ----------------------------
	    Thread.sleep(3000);
		SeleniumCommon.disableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
	    driver.findElement(By.id("popup_ok")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0007_4_1_2_1.log");
		capture(imagePath +browser+"/"+lan+"/0007_4_1_2_1");
	    driver.findElement(By.id("popup_ok")).click();
	    Thread.sleep(3000);
	    new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("Biên Hòa");
		// stop server connection ----------------------------
	    Thread.sleep(3000);
		SeleniumCommon.disableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
	    driver.findElement(By.id("btnSearch")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0007_1_1_2_1.log");
		capture(imagePath +browser+"/"+lan+"/0007_1_1_2_1");
	    driver.findElement(By.id("popup_ok")).click();	
	    Thread.sleep(3000);
		SeleniumCommon.enableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
	    new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("Sài Gòn");
	    new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("Biên Hòa");
	    Thread.sleep(2000);
	    new Select(driver.findElement(By.id("cbbAreaName"))).selectByVisibleText("BH 023");
		// stop server connection ----------------------------
	    Thread.sleep(3000);
		SeleniumCommon.disableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
	    driver.findElement(By.id("btnSearch")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0007_1_1_2_1.2.log");
		capture(imagePath +browser+"/"+lan+"/0007_1_1_2_1.2");
	    driver.findElement(By.id("popup_ok")).click();
	    Thread.sleep(3000);
		SeleniumCommon.enableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
	    driver.findElement(By.id("btnSearch")).click();
	    Thread.sleep(3000);
		SeleniumCommon.enableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
	    driver.findElement(By.xpath("(//img[@name='0'])[2]")).click();
	    Thread.sleep(3000);
	    driver.findElement(By.id("popup_ok")).click();
	    Thread.sleep(3000);
	    driver.findElement(By.id("popup_ok")).click();
		// stop server connection ----------------------------
	    Thread.sleep(3000);
		SeleniumCommon.disableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
	    driver.findElement(By.id("F001")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0007_11_1_2_1.log");
		capture(imagePath +browser+"/"+lan+"/0007_11_1_2_1");
	    driver.findElement(By.id("popup_ok")).click();
	    Thread.sleep(3000);
		SeleniumCommon.enableDBServerConnection("BANANA_DB_NGHIA", "Nghia");
		Thread.sleep(3000);
	    driver.findElement(By.id("btnBack")).click();
	    driver.findElement(By.id("F001")).click();
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
