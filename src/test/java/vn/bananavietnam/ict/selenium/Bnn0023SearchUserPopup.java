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
public class Bnn0023SearchUserPopup {
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
	
	// define log evidence array here
	ArrayList<Long> checkPointArray;
	ArrayList<String> logFileNameArray;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "webdriver/geckodriver.exe");
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");
		System.setProperty("webdriver.edge.driver", "webdriver/MicrosoftWebDriver.exe");
		
		baseUrl = "http://localhost:8080";
	}
	public void common(String browser, String language) {
		try {
			checkPointArray = new ArrayList<Long>();
			logFileNameArray = new ArrayList<String>();
			if (browser == "IE") {
				 driver = new InternetExplorerDriver(); 			 
			} else if (browser == "Chrome") {
				driver = new ChromeDriver();
			} else if (browser == "FireFox") {
				driver = new FirefoxDriver();
			} else {
				driver = new EdgeDriver();
			}

			driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
			driver.get(baseUrl + "/ict/");
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));

			// Thread.sleep(3000);

			if (language == "vi") {
				driver.findElement(By.cssSelector("img.language[name|=vi]")).click();
			} else if (language == "en") {
				driver.findElement(By.cssSelector("img.language[name|=en]")).click();
			} else if (language == "jp") {
				driver.findElement(By.cssSelector("img.language[name|=jp]")).click();
			}
		    // Thread.sleep(3000);

			// dump data to file
			SeleniumCommon.dumpDataToFile("BANANA_DB_HIEU", "selenium_test_images/0023/Success/" + browser + "/" + language + "DATA_BEFORE", "IVB_M_USERS");

		    driver.findElement(By.id("j_username")).clear();
		    // Thread.sleep(500);
		    driver.findElement(By.id("j_username")).sendKeys("U0000000001");
		    // Thread.sleep(500);
		    driver.findElement(By.id("j_password")).clear();
		    // Thread.sleep(500);
		    driver.findElement(By.id("j_password")).sendKeys("U0000000001");
		    // Thread.sleep(3000);
		    driver.findElement(By.id("btnLogin")).click();
			
		    // Thread.sleep(3000);
		    
		    // into 0007 display
		    driver.findElement(By.id("btnAreaScreen")).click();
		    new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText("Biên Hòa");
		    driver.findElement(By.id("btnSearch")).click();
		    // Thread.sleep(1000);
		    // load 0023SearchUserPopup display
		    driver.findElement(By.name("0")).click();
		    // Thread.sleep(1000);
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_1_1_1_1vs0023_10_1_1_1.log");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("loadUserPopup")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_1_1_1_1");
		   // Thread.sleep(1000);
		    // click button back
	   		driver.findElement(By.id("btnScUserPupBack")).click();
	   		// Thread.sleep(1000);
	   		capture("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_10_1_1_1");
	   		// Thread.sleep(1000);
	   		// reload 0023SearchUserPopup display
		    driver.findElement(By.id("loadUserPopup")).click();
		    // click button >, >>, <, <<, Go
		    // Thread.sleep(1000);
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_5_1_1_1.log");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnScUserPupNext")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_5_1_1_1");
		    // Thread.sleep(500);
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_6_1_1_1.log");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnScUserPupLast")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_6_1_1_1");
		    // Thread.sleep(500);
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_4_1_1_1.log");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnScUserPupPrevious")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_4_1_1_1");
		    // Thread.sleep(500);
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_3_1_1_1.log");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnScUserPupFirst")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_3_1_1_1");
		    // Thread.sleep(500);  
		    driver.findElement(By.id("txtScUserPupGoToPage")).clear();
		    driver.findElement(By.id("txtScUserPupGoToPage")).sendKeys("2");
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_7_3_1_1");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_8_1_1.log");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnScUserPupGoToPage")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_8_1_1_1");
		    driver.findElement(By.id("txtScUserPupGoToPage")).clear();
		    driver.findElement(By.id("txtScUserPupGoToPage")).sendKeys("1");
		    driver.findElement(By.id("btnScUserPupGoToPage")).click();
		    // check text field
		    // Thread.sleep(500);
		    driver.findElement(By.id("txtScUserPupUsersId")).clear();
		    driver.findElement(By.id("txtScUserPupUsersId")).sendKeys("11111111111");
		    // Thread.sleep(500);
		    driver.findElement(By.id("txtScUserPupUsersName")).clear();
		    driver.findElement(By.id("txtScUserPupUsersName")).sendKeys("1111111111111111111111111111111111111111");
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_1_1_1_2");
		    // Thread.sleep(500);
		    // search Data
	   		// result 0 
	   		driver.findElement(By.id("txtScUserPupUsersId")).clear();
	   		driver.findElement(By.id("txtScUserPupUsersId")).sendKeys("U0000000004");
	   		driver.findElement(By.id("txtScUserPupUsersName")).clear();
	   		driver.findElement(By.id("txtScUserPupUsersName")).sendKeys("U0000000005");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_2_1_2_1.log");
		    // Thread.sleep(1000);
	   		driver.findElement(By.id("btnScUserPupSearch")).click();
	   		// Thread.sleep(1000); 
	   		capture("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_2_1_2_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		driver.findElement(By.id("popup_ok")).click();
	   		//check result record is 1 ?
		    driver.findElement(By.id("txtScUserPupUsersId")).clear();
		    driver.findElement(By.id("txtScUserPupUsersName")).clear();
		    driver.findElement(By.id("txtScUserPupUsersName")).sendKeys("U0000000005");
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_2_1_1_1.log");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnScUserPupSearch")).click();
		    // Thread.sleep(1000);
	   		assertEquals("1", driver.findElement(By.id("txtScUserPupCounts")).getText());
	   		// Thread.sleep(1000);
	   		capture("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_2_1_1_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		// get Data
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_9_1_2_1.log");
		    // Thread.sleep(1000);
	   		driver.findElement(By.id("btnScUserPupSelect")).click();
	   		// Thread.sleep(1000);
	   		capture("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_9_1_2_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		driver.findElement(By.id("popup_ok")).click();

			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			logFileNameArray.add("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_9_1_1_1.log");
		    // Thread.sleep(1000);
	   		driver.findElement(By.xpath("(//tr[@id='row0']/td[3])[2]")).click();
	   		// Thread.sleep(1000);
	   		capture("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_9_1_1_1");
	   		// Thread.sleep(500);
	   		driver.findElement(By.id("btnScUserPupSelect")).click();
	   		// Thread.sleep(1000);
	   		capture("selenium_test_images/0023/Success/" + browser + "/" + language + "/0023_9_1_1_1_1");
		} catch ( Exception ex ) {
			System.err.println("Can't open browser");
			assertEquals("1", "");
		}	
	}
	
	@Test
	  public void testBnn0023SearchUserPopupIeVi() throws Exception {
	    common("IE", "vi");
	    
	  }
	@Test
	  public void testBnn0023SearchUserPopupIeEn() throws Exception {
	    common("IE", "en");
	    
	  }
	@Test
	  public void testBnn0023SearchUserPopupIeJp() throws Exception {
	    common("IE", "jp");
	    
	  }
	@Test
	  public void testBnn0023SearchUserPopupChromeVi() throws Exception {
	    common("Chrome", "vi");
	    
	  }
	@Test
	  public void testBnn0023SearchUserPopupChromeEn() throws Exception {
	    common("Chrome", "en");
	    
	  }
	@Test
	  public void testBnn0023SearchUserPopupChromeJp() throws Exception {
	    common("Chrome", "jp");
	    
	  }
	@Test
	  public void testBnn0023SearchUserPopupFireFoxVi() throws Exception {
	    common("FireFox", "vi");
	    
	  }
	@Test
	  public void testBnn0023SearchUserPopupFireFoxEn() throws Exception {
	    common("FireFox", "en");
	    
	  }
	@Test
	  public void testBnn0023SearchUserPopupFireFoxJp() throws Exception {
	    common("FireFox", "jp");
	    
	  }
	@Test
	  public void testBnn0023SearchUserPopupedgeVi() throws Exception {
	    common("edge", "vi");
	    
	  }
	@Test
	  public void testBnn0023SearchUserPopupedgeEn() throws Exception {
	    common("edge", "en");
	    
	  }
	@Test
	  public void testBnn0023SearchUserPopupedgeJp() throws Exception {
	    common("edge", "jp");
	    
	  }
	  
	@After
	public void tearDown() throws Exception {
		driver.quit();
		SeleniumCommon.startPrintingLog(checkPointArray, logFileNameArray);
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
}
