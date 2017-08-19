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
import org.openqa.selenium.support.ui.Select;

@RunWith(Parameterized.class)
public class Bnn0075ShippingScreen {
	private static WebDriver driver;
	private String baseUrl;
	messageAlertBnn0075 pages = new messageAlertBnn0075();

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "U0000000001", "U0000000001" } });
	}
	
	// define log evidence array here
	ArrayList<Long> checkPointArray;
	ArrayList<String> logFileNameArray;

	@Parameter // first data value (0) is default
	public String fName;

	@Parameter(1)
	public String fPass;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "webdriver/geckodriver.exe");
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");

		baseUrl = "http://172.16.0.17:8080";
	}
	
	public void common(String browser, String language) {
		
		checkPointArray = new ArrayList<Long>();
		logFileNameArray = new ArrayList<String>();
		
		try {
			alertScreenBnn0075(language);
			if (browser == "IE") {
				 driver = new InternetExplorerDriver();
			} else if (browser == "Chrome") {
				driver = new ChromeDriver();
			} else if (browser == "FireFox") {
				driver = new FirefoxDriver();
			} else {
				driver = new EdgeDriver();
			}
			
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			if (language == "vi") {
				driver.get(baseUrl + "/ict/?language=vi");
			} else if (language == "en") {
				driver.get(baseUrl + "/ict/?language=en");
			} else if (language == "jp") {
				driver.get(baseUrl + "/ict/?language=jp");
			}
			
			// start testing
			Thread.sleep(3000);
			// input user name and password
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
			
			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("U0000000001");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("U0000000001");
			//Login
			driver.findElement(By.id("btnLogin")).click();
			
			//Go to screen 0075
			Thread.sleep(1000);
			//driver.findElement(By.id("btnShippingScreen")).click();
			driver.get(baseUrl + "/ict/0075/?language=" + pages.getLang());
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			
			// dump data to file
		    Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_DUNG_SELENIUM", "E:/selenium_test_images/0075/Success/" + browser + "/" + language + "/DATA_BEFORE", "IVB_T_SHIPPING_CONTROL");

		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_1_1_1_1.log");
			//take a photo
			Thread.sleep(1000);
			capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_1_1_1_1");
			//change combobox farm
			Thread.sleep(1000);
			new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("Sài Gòn");
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_2_1_1_1.log");
		    //click search
			Thread.sleep(1000);
		    driver.findElement(By.id("btnSearch")).click();
		    //take a photo
			Thread.sleep(1000);
			capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_2_1_1_1");
		    //change search
		    new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("Biên Hòa");
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_3_1_1_1.log");
		    Thread.sleep(1000);
		    driver.findElement(By.id("btnSearch")).click();
		    //take a photo
			Thread.sleep(1000);
			capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_3_1_1_1");
		    //search with shippingNumber -- result no data
			driver.findElement(By.id("shipping_number")).clear();
			driver.findElement(By.id("shipping_number")).sendKeys("100000000");
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_3_1_2_1.log");
			Thread.sleep(1000);
			driver.findElement(By.id("btnSearch")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("popup_ok")).click();
		    //take a photo
			Thread.sleep(1000);
			capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_3_1_2_1");
		    //search with shippingNumber -- result data ok
			driver.findElement(By.id("shipping_number")).clear();
			Thread.sleep(1000);
			driver.findElement(By.id("shipping_number")).sendKeys("00000001");
			Thread.sleep(1000);
			driver.findElement(By.id("btnSearch")).click();
			Thread.sleep(1000);
		    //search no shippingNumber
			driver.findElement(By.id("shipping_number")).clear();
			Thread.sleep(1000);
			driver.findElement(By.id("shipping_number")).sendKeys("");
			Thread.sleep(1000);
			driver.findElement(By.id("btnSearch")).click();
		    //search with harvest start date.
			Thread.sleep(1000);
			driver.findElement(By.cssSelector("img.ui-datepicker-trigger")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("harvest_start_date")).sendKeys("01042017");
			Thread.sleep(1000);
			driver.findElement(By.id("btnSearch")).click();
			
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[7]/img")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("harvest_end_date")).sendKeys("11052017");
			Thread.sleep(1000);
		    //search data ok
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[7]/img")).click();
			driver.findElement(By.id("harvest_end_date")).sendKeys("30052017");
			Thread.sleep(1000);
			driver.findElement(By.id("btnSearch")).click();
		    //search with shipping start date -- result no data
			driver.findElement(By.xpath("//div[5]/div[5]/img")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("ship_start_date")).sendKeys("01042017");
			Thread.sleep(1000);
			driver.findElement(By.id("btnSearch")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[5]/div[7]/img")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("ship_end_date")).sendKeys("21032017");
			Thread.sleep(1000);
			driver.findElement(By.id("btnSearch")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("popup_ok")).click();
		    //search data ok
			Thread.sleep(1000);
			driver.findElement(By.id("harvest_start_date")).clear();
			Thread.sleep(1000);
			driver.findElement(By.id("harvest_end_date")).clear();
			Thread.sleep(1000);
			driver.findElement(By.id("ship_start_date")).clear();
			Thread.sleep(1000);
			driver.findElement(By.id("ship_end_date")).clear();
			Thread.sleep(1000);
			driver.findElement(By.id("btnSearch")).click();
		    ///select data
		    new Select(driver.findElement(By.id("cbbAreaName"))).selectByVisibleText("BH 001");
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_9_1_1_1.log");
		    // click button >, >>, <, <<. and take photos
			driver.findElement(By.id("btnNext")).click();
			Thread.sleep(1000);
			capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_9_1_1_1");
			
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_10_1_1_1.log");
			driver.findElement(By.id("btnLast")).click();
		    Thread.sleep(1000);
		    capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_10_1_1_1");
		    
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_8_1_1_1.log");
		    driver.findElement(By.id("btnPrevious")).click();
		    Thread.sleep(1000);
		    capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_8_1_1_1");
		    
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_7_1_1_1.log");
		    driver.findElement(By.id("btnFirst")).click();
		    Thread.sleep(1000);
		    capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_7_1_1_1");
		    
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_12_1_1_1.log");
		    driver.findElement(By.id("txtGoToPage")).sendKeys("2");
		    Thread.sleep(1000);
		    capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_12_1_1_1");
		    driver.findElement(By.id("btnFirst")).click();
		    Thread.sleep(1000);
		    //goto popup screen bnn0075 and click back
		    driver.findElement(By.linkText(pages.getShipping())).click();
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_5_4_1_1.log");
		    driver.findElement(By.id("btnCancelPopup")).click();
		    //take a photo
			Thread.sleep(1000);
			capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_5_4_1_1");
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_5_1_1_1.log");
		    
			
			// dump data to file
		    Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_DUNG_SELENIUM", "E:/selenium_test_images/0075/Success/" + browser + "/" + language + "/DATA_BEFORE_INSERT", "IVB_T_SHIPPING_CONTROL");
			Thread.sleep(2000);
			
		 	//goto popup screen bnn0075
			driver.findElement(By.linkText(pages.getShipping())).click();
		    //take a photo
			Thread.sleep(1000);
			capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_5_1_1_1");
		    //insert when blank field (blank shippingNumber and blank shipping date)
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_5_2_1_1.log");
			driver.findElement(By.id("btnRegisterPopup")).click();
			driver.findElement(By.id("popup_ok")).click();
		    //take a photo
			Thread.sleep(1000);
			capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_5_2_1_1");
		    //create shipping number
			Thread.sleep(1000);
			driver.findElement(By.id("btnNumberAssignmentPopup")).click();
		    //insert when shipping date blank
			Thread.sleep(1000);
			driver.findElement(By.id("btnRegisterPopup")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("popup_ok")).click();
			Thread.sleep(1000);
			driver.findElement(By.id("shipDatePopup")).sendKeys("30052017");
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_5_3_1_1.log");
		    //insert ok
			driver.findElement(By.id("btnRegisterPopup")).click();
			//take a photo
			Thread.sleep(1000);
			capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_5_3_1_1");
			driver.findElement(By.id("popup_ok")).click();
			
			// dump data to file
		    Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_DUNG_SELENIUM", "E:/selenium_test_images/0075/Success/" + browser + "/" + language + "/DATA_AFTER_INSERT", "IVB_T_SHIPPING_CONTROL");
			Thread.sleep(2000);
			
//			//cancel tamj vaayj
//			driver.findElement(By.id("btnCancelPopup")).click();
		    //take a photo
			Thread.sleep(1000);
			capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_5_3_1_1");
			
			// dump data to file
		    Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_DUNG_SELENIUM", "E:/selenium_test_images/0075/Success/" + browser + "/" + language + "/DATA_BEFORE_UPDATE", "IVB_T_SHIPPING_CONTROL");
			Thread.sleep(2000);
			
		    //update successful
			driver.findElement(By.name("0")).click();
		    //take a photo
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_4_1_1_1.log");
			Thread.sleep(1000);
			capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_4_1_1_1");
			driver.findElement(By.id("shipDatePopup")).sendKeys("30052017");
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_4_2_1_1.log");
		    //take a photo
			Thread.sleep(1000);
			capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_4_2_1_1");
			Thread.sleep(1000);
			driver.findElement(By.id("btnRegisterPopup")).click();
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_4_3_1_1.log");
		    //take a photo
			Thread.sleep(1000);
			capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_4_3_1_1");
			driver.findElement(By.id("popup_ok")).click();
			
			// dump data to file
		    Thread.sleep(1000);
			SeleniumCommon.dumpDataToFile("BANANA_DB_DUNG_SELENIUM", "E:/selenium_test_images/0075/Success/" + browser + "/" + language + "/DATA_AFTER_UPDATE", "IVB_T_SHIPPING_CONTROL");
			Thread.sleep(2000);
			
		    //click shippingNumber in table, go to screen bnn061
			Thread.sleep(1000);
			driver.findElement(By.id("btnSearch")).click();
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_6_1_1_1.log");
			Thread.sleep(1000);
			driver.findElement(By.id("00000001")).click();
//			driver.findElement(By.id("popup_ok")).click();
		    //take a photo
			Thread.sleep(1000);
			capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_6_1_1_1");
		    //click back bnn0075
		    // store check point information
		 	Thread.sleep(2000);
		 	checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		    // store log file name
		 	Thread.sleep(2000);
		 	logFileNameArray.add("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_13_1_1_1.log");
			driver.findElement(By.id("btnBack")).click();
		    //take a photo
		    Thread.sleep(1000);
		    capture("selenium_test_images/0075/Success/" + browser + "/" + language + "/0075_13_1_1_1");
	
		} catch ( Exception ex ) {
			System.err.println("Can't open browser");
			assertEquals("1", "");
		}
	}
	
//	@Test
//	  public void testBnn0075IeVi() throws Exception {
//	    common("IE", "vi");
//	  }
//	@Test
//	  public void testBnn0001BananaMenuIeEn() throws Exception {
//	    common("IE", "en");
//	    
//	  }
//	@Test
//	  public void testBnn0001BananaMenuIeJp() throws Exception {
//	    common("IE", "jp");
//	    
//	  }
//	@Test
//	  public void testBnn0001BananaMenuChromeVi() throws Exception {
//	    common("Chrome", "vi");
//	    
//	  }
//	@Test
//	  public void testBnn0001BananaMenuChromeEn() throws Exception {
//	    common("Chrome", "en");
//	    
//	  }
//	@Test
//	  public void testBnn0001BananaMenuChromeJp() throws Exception {
//	    common("Chrome", "jp");
//	    
//	  }
//	@Test
//	  public void testBnn0001BananaMenuFireFoxVi() throws Exception {
//	    common("FireFox", "vi");
//	    
//	  }
//	@Test
//	  public void testBnn0001BananaMenuFireFoxEn() throws Exception {
//	    common("FireFox", "en");
//	    
//	  }
	@Test
	  public void testBnn0001BananaMenuFireFoxJp() throws Exception {
	    common("FireFox", "jp");
	    
	  }
//	@Test
//	  public void testBnn0001BananaMenuedgeVi() throws Exception {
//	    common("edge", "vi");
//	    
//	  }
//	@Test
//	  public void testBnn0001BananaMenuedgeEn() throws Exception {
//	    common("edge", "en");
//	    
//	  }
//	@Test
//	  public void testBnn0001BananaMenuedgeJp() throws Exception {
//	    common("edge", "jp");
//	    
//	  }

	@After
	public void tearDown() throws Exception {
		try {
			SeleniumCommon.startPrintingLog(checkPointArray, logFileNameArray);
			driver.quit();
		} catch ( Exception ex ) {
			System.err.println("Can't close browser");
		}
	}

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

	public void alertScreenBnn0075(String lang){
		if(lang == "jp"){
			pages.setLang(lang);
			//Shipping
			pages.setShipping("出荷");
		} else if(lang == "en"){
			pages.setLang(lang);
			//Shipping
			pages.setShipping("Shipping");
		} else if (lang == "vi"){
			pages.setLang(lang);
			//Shipping
			pages.setShipping("Xuất hàng");
		}
	}
	
	public class messageAlertBnn0075{
		public String lang;
		public String urlSaveImage;
		//Shipping
		public String shipping;
		
		public String getLang() {
			return lang;
		}
		public void setLang(String lang) {
			this.lang = lang;
		}
		public String getUrlSaveImage() {
			return urlSaveImage;
		}
		public void setUrlSaveImage(String urlSaveImage) {
			this.urlSaveImage = urlSaveImage;
		}
		public String getShipping() {
			return shipping;
		}
		public void setShipping(String shipping) {
			this.shipping = shipping;
		}
	}
}
