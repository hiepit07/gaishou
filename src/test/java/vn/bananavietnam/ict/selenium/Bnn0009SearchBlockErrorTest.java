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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

@RunWith(Parameterized.class)
public class Bnn0009SearchBlockErrorTest {
	private static WebDriver driver;
	private String baseUrl;

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "U0000000001", "U0000000001" } });
	}

	@Parameter // first data value (0) is default
	public String fName;
	public String imagePath = "selenium_test_images/0009/Error/   ";
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
	@Test
	public void test0009SearchBlockWithIE11Jp()  throws Exception {
		driver = new InternetExplorerDriver();
		testBnn0009SearchBlock(driver, "vi", "IE");
	}
	/*@Test
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

	@Test
	public void test0009SearchBlockWithFireFox11Jp()  throws Exception {
		driver = new FirefoxDriver();
		testBnn0009SearchBlock(driver, "jp", "FireFox");
	}

	@Test
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
		//Thread.sleep(3000);
		SeleniumCommon.enableClientConnection();
		//Thread.sleep(3000);
		// input user name and password
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("U0000000001");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("U0000000001");
		driver.findElement(By.id("btnLogin")).click();
		
	    driver.findElement(By.id("btnAreaScreen")).click();

		// stop server connection ----------------------------
		//Thread.sleep(3000);
		SeleniumCommon.disableClientConnection();
		//Thread.sleep(3000);
		driver.findElement(By.id("F001")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath + browser + "/" + lan + "/0009_1_1_1_1.log");
		capture(imagePath + browser + "/" + lan + "/0009_1_1_1_1");
	    driver.findElement(By.id("popup_ok")).click();
	    driver.findElement(By.id("btnGeneralSetting")).click();
	    driver.findElement(By.id("btnBlockScreen")).click();

		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath + browser + "/" + lan + "/0009_1_1_1_2.log");
		capture(imagePath + browser + "/" + lan + "/0009_1_1_1_2");
	    driver.findElement(By.id("popup_ok")).click();
		//Thread.sleep(3000);
		SeleniumCommon.enableClientConnection();
		//Thread.sleep(3000);
	    driver.findElement(By.id("btnGeneralSetting")).click();
		driver.findElement(By.id("btnBlockScreen")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath + browser + "/" + lan + "/0009_1_1_1_2.log");
		capture(imagePath + browser + "/" + lan + "/0009_1_1_1_2");
	    driver.findElement(By.id("btnRegister")).click();
	    //Thread.sleep(3000);
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    // store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath + browser + "/" + lan + "/0009_3_1_1_1.log");
		capture(imagePath + browser + "/" + lan + "/0009_3_1_1_1");

	    driver.findElement(By.id("popup_ok")).click();
	    driver.findElement(By.id("txtBlockNamePopup")).clear();
	    driver.findElement(By.id("txtBlockNamePopup")).sendKeys("B101");
		// stop server connection ----------------------------
		//Thread.sleep(3000);
		SeleniumCommon.disableClientConnection();
		//Thread.sleep(3000);
	    driver.findElement(By.id("btnRegisterPopup")).click();

	    // store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath + browser + "/" + lan + "/0009_3_1_1_2.log");
		capture(imagePath + browser + "/" + lan + "/0009_3_1_1_2");
	    driver.findElement(By.id("popup_ok")).click();
		//Thread.sleep(3000);
		SeleniumCommon.enableClientConnection();
		//Thread.sleep(3000);
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    // store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath + browser + "/" + lan + "/0009_3_1_1_3.log");
		capture(imagePath + browser + "/" + lan + "/0009_3_1_1_3");
	    driver.findElement(By.id("popup_ok")).click();
	    new Select(driver.findElement(By.id("cbbBlockId"))).selectByVisibleText("B101");

	    driver.findElement(By.id("B101")).click();
	    driver.findElement(By.id("btnSearch")).click();
	    driver.findElement(By.id("txtBlockNamePopup")).clear();
	    driver.findElement(By.id("txtBlockNamePopup")).sendKeys("");
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    // store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath + browser + "/" + lan + "/0009_4_1_1_1.log");
		capture(imagePath + browser + "/" + lan + "/0009_4_1_1_1");
	    driver.findElement(By.id("popup_ok")).click();
	    driver.findElement(By.id("txtBlockNamePopup")).clear();
	    driver.findElement(By.id("txtBlockNamePopup")).sendKeys("B101ABC");
		// stop server connection ----------------------------
		//Thread.sleep(3000);
		SeleniumCommon.disableClientConnection();
		//Thread.sleep(3000);
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    // store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath + browser + "/" + lan + "/0009_4_1_1_2.log");
		capture(imagePath + browser + "/" + lan + "/0009_4_1_1_2");
	    driver.findElement(By.id("popup_ok")).click();
		//Thread.sleep(3000);
		SeleniumCommon.enableClientConnection();
		//Thread.sleep(3000);
	    driver.findElement(By.id("btnRegisterPopup")).click();
	    // store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath + browser + "/" + lan + "/0009_4_1_1_3.log");
		capture(imagePath + browser + "/" + lan + "/0009_4_1_1_3");
	    driver.findElement(By.id("popup_ok")).click();
	    new Select(driver.findElement(By.id("cbbBlockId"))).selectByVisibleText("B101ABC");
	    driver.findElement(By.id("B101ABC")).click();
	    driver.findElement(By.id("btnSearch")).click();
	    driver.findElement(By.xpath("(//img[@name='0'])[2]")).click();
		// stop server connection ----------------------------
		//Thread.sleep(3000);
		SeleniumCommon.disableClientConnection();
		//Thread.sleep(3000);
	    driver.findElement(By.id("popup_ok")).click();

	    // store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath + browser + "/" + lan + "/0009_5_1_1_1.log");
		capture(imagePath + browser + "/" + lan + "/0009_5_1_1_1");
	    driver.findElement(By.id("popup_ok")).click();
	    driver.findElement(By.xpath("(//img[@name='0'])[2]")).click();
		//Thread.sleep(3000);
		SeleniumCommon.enableClientConnection();
		//Thread.sleep(3000);
	    driver.findElement(By.id("popup_ok")).click();
	    // store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath + browser + "/" + lan + "/0009_5_1_1_2.log");
		capture(imagePath + browser + "/" + lan + "/0009_5_1_1_2");
	    driver.findElement(By.id("popup_ok")).click();
		// stop server connection ----------------------------
		//Thread.sleep(3000);
		SeleniumCommon.disableClientConnection();
		//Thread.sleep(3000);
	    driver.findElement(By.linkText("B000")).click();
	    // store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath + browser + "/" + lan + "//0009_6_1_1_1.log");
		capture(imagePath + browser + "/" + lan + "/0009_6_1_1_1");
	    driver.findElement(By.id("popup_ok")).click();
		//Thread.sleep(3000);
		SeleniumCommon.enableClientConnection();
		//Thread.sleep(3000);
	    driver.findElement(By.linkText("B000")).click();
	    Thread.sleep(3000);
	    driver.findElement(By.id("selectAll")).click();
	    driver.findElement(By.id("unSelectAll")).click();
	    driver.findElement(By.name("L000:C000")).click();
		// stop server connection ----------------------------
		//Thread.sleep(3000);
		SeleniumCommon.disableClientConnection();
		//Thread.sleep(3000);
	    driver.findElement(By.id("btnDisableProductPopup")).click();
	    // store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath + browser + "/" + lan + "//0009_7_1_1_1.log");
		capture(imagePath + browser + "/" + lan + "/0009_7_1_1_1");
	    driver.findElement(By.id("popup_ok")).click();

		//Thread.sleep(3000);
		SeleniumCommon.enableClientConnection();
		//Thread.sleep(3000);
	    driver.findElement(By.linkText("B000")).click();
	    //Thread.sleep(3000);
	    driver.findElement(By.name("L000:C000")).click();
	    driver.findElement(By.id("btnDisableProductPopup")).click();
	    driver.findElement(By.id("popup_ok")).click();
	    // store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath + browser + "/" + lan + "//0009_8_1_1_1.log");
		capture(imagePath + browser + "/" + lan + "/0009_8_1_1_1");
	    driver.findElement(By.id("popup_ok")).click();
	    driver.findElement(By.id("btnSearch")).click();
	    driver.findElement(By.id("txtGoToPage")).clear();
		// stop server connection ----------------------------
		//Thread.sleep(3000);
		SeleniumCommon.disableClientConnection();
		//Thread.sleep(3000);
	    driver.findElement(By.id("txtGoToPage")).sendKeys("1");
	    driver.findElement(By.id("btnGoToPage")).click();
	    // store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath + browser + "/" + lan + "//0009_9_1_1_1.log");
		capture(imagePath + browser + "/" + lan + "/0009_9_1_1_1");
	    driver.findElement(By.id("popup_ok")).click();
	    driver.findElement(By.id("linkLogout")).click();
	    driver.findElement(By.id("popup_ok")).click();
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
