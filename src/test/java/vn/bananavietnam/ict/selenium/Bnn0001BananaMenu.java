package vn.bananavietnam.ict.selenium;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
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

@RunWith(Parameterized.class)
public class Bnn0001BananaMenu {
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
			if (language == "vi") {
				driver.get(baseUrl + "/ict/?language=vi");
			} else if (language == "en") {
				driver.get(baseUrl + "/ict/?language=en");
			} else if (language == "jp") {
				driver.get(baseUrl + "/ict/?language=jp");
			}

			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("U0000000001");
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("U0000000001");	 
		    driver.findElement(By.id("btnLogin")).click();
		    
		    // go to Menu display
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0001/Success/" + browser + "/" + language + "/0001_1_1_1_1");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnUserScreen")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0001/Success/" + browser + "/" + language + "/0001_2_1_1_1");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnBack")).click();
		    driver.findElement(By.id("btnFarmScreen")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0001/Success/" + browser + "/" + language + "/0001_2_1_1_2");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnBack")).click();
		    driver.findElement(By.id("btnAreaScreen")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0001/Success/" + browser + "/" + language + "/0001_2_1_1_3");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnBack")).click();
		    driver.findElement(By.id("btnBlockScreen")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0001/Success/" + browser + "/" + language + "/0001_2_1_1_4");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnBack")).click();
		    driver.findElement(By.id("btnBananaKindScreen")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0001/Success/" + browser + "/" + language + "/0001_2_1_1_5");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnBack")).click();
		    driver.findElement(By.id("btnProcessScreen")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0001/Success/" + browser + "/" + language + "/0001_2_1_1_6");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnBack")).click();
		    driver.findElement(By.id("btnTaskScreen")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0001/Success/" + browser + "/" + language + "/0001_2_1_1_7");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnBack")).click();
		    driver.findElement(By.id("btnManageScreen")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0001/Success/" + browser + "/" + language + "/0001_2_1_1_8");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnBack")).click();
		    driver.findElement(By.id("btnCultivationScreen")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0001/Success/" + browser + "/" + language + "/0001_2_1_1_9");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnBack")).click();
		    driver.findElement(By.id("btnAuthorityScreen")).click();
		    // Thread.sleep(1000);
		    capture("selenium_test_images/0001/Success/" + browser + "/" + language + "/0001_2_1_1_10");
		    // Thread.sleep(1000);
		    driver.findElement(By.id("btnBack")).click();
		} catch ( Exception ex ) {
			System.err.println("Can't open browser");
			assertEquals("1", "");
		}
	}
	
	@Test
	  public void testBnn0001BananaMenuIeVi() throws Exception {
	    common("IE", "vi");
	    
	  }
	@Test
	  public void testBnn0001BananaMenuIeEn() throws Exception {
	    common("IE", "en");
	    
	  }
	@Test
	  public void testBnn0001BananaMenuIeJp() throws Exception {
	    common("IE", "jp");
	    
	  }
	@Test
	  public void testBnn0001BananaMenuChromeVi() throws Exception {
	    common("Chrome", "vi");
	    
	  }
	@Test
	  public void testBnn0001BananaMenuChromeEn() throws Exception {
	    common("Chrome", "en");
	    
	  }
	@Test
	  public void testBnn0001BananaMenuChromeJp() throws Exception {
	    common("Chrome", "jp");
	    
	  }
	@Test
	  public void testBnn0001BananaMenuFireFoxVi() throws Exception {
	    common("FireFox", "vi");
	    
	  }
	@Test
	  public void testBnn0001BananaMenuFireFoxEn() throws Exception {
	    common("FireFox", "en");
	    
	  }
	@Test
	  public void testBnn0001BananaMenuFireFoxJp() throws Exception {
	    common("FireFox", "jp");
	    
	  }
	@Test
	  public void testBnn0001BananaMenuedgeVi() throws Exception {
	    common("edge", "vi");
	    
	  }
	@Test
	  public void testBnn0001BananaMenuedgeEn() throws Exception {
	    common("edge", "en");
	    
	  }
	@Test
	  public void testBnn0001BananaMenuedgeJp() throws Exception {
	    common("edge", "jp");
	    
	  }


	@After
	public void tearDown() throws Exception {
		try {	
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
}
