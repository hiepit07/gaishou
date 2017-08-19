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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

@RunWith(Parameterized.class)
public class Bnn0049AreaList {
	private static WebDriver driver;
	private String baseUrl;

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "U0000000001", "U0000000001" } });
	}

	@Parameter // first data value (0) is default
	public String fName;
	public String imagePath = "selenium_test_images/0049/Success/";
	@Parameter(1)
	public String fPass;
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
	public void test0049AreaListWithIE11Jp()  throws Exception {
		driver = new InternetExplorerDriver();
		testBnn0047SearchBlock(driver, "jp", "IE");
	}

	@Test
	public void test0049AreaListWithIE11en()  throws Exception {
		driver = new InternetExplorerDriver();
		testBnn0047SearchBlock(driver, "en", "IE");
	}

	@Test
	public void test0049AreaListWithIE11vi()  throws Exception {
		driver = new InternetExplorerDriver();
		testBnn0047SearchBlock(driver, "vi", "IE");
	}

	@Test
	public void test0049AreaListWithFireFox11Jp()  throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		testBnn0047SearchBlock(driver, "jp", "FireFox");
	}

	@Test
	public void test0049AreaListWithFireFox11en()  throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		testBnn0047SearchBlock(driver, "en", "FireFox");
	}

	@Test
	public void test0049AreaListWithFireFox11vi()  throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		testBnn0047SearchBlock(driver, "vi", "FireFox");
	}
	@Test
	public void test0049AreaListWithChrome11jp()  throws Exception {
		driver = new ChromeDriver();
		testBnn0047SearchBlock(driver, "jp", "Chrome");
	}

	@Test
	public void test0049AreaListWithChrome11en()  throws Exception {
		driver = new ChromeDriver();
		testBnn0047SearchBlock(driver, "en", "Chrome");
	}

	@Test
	public void test0049AreaListWithChrome11vi()  throws Exception {
		driver = new ChromeDriver();
		testBnn0047SearchBlock(driver, "vi", "Chrome");
	}
	/*@Test
	public void test0047FarmListWithEdgeJp()  throws Exception {
		driver = new EdgeDriver();
		testBnn0047SearchBlock(driver, "jp", "Edge");
	}

	@Test
	public void test0047FarmListWithEdgeEn()  throws Exception {
		driver = new EdgeDriver();
		testBnn0047SearchBlock(driver, "en", "Edge");
	}

	@Test
	public void test0047FarmListWithEdgeVi()  throws Exception {
		driver = new EdgeDriver();
		testBnn0047SearchBlock(driver, "vi", "Edge");
	}*/

	public void testBnn0047SearchBlock(WebDriver driver, String lan, String browser) throws Exception {
		driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
		checkPointArray = new ArrayList<Long>();
		logFileNameArray = new ArrayList<String>();
		driver.get(baseUrl + "/ict/?language=" + lan);
		// input user name and password
		driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));

	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("U0000000001");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("U0000000001");
	    driver.findElement(By.id("btnLogin")).click();
	    // Thread.sleep(1000);
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
	    // dump data to file
	 	SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + lan + "/" +"DATA_FARM", "IVB_M_FARM");
	 	// dump data to file
	 	SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + lan + "/" +"DATA_AREA", "IVB_M_AREA");
	 	// dump data to file
	 	SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + lan + "/" +"DATA_MANAGER", "IVB_M_MANAGER");	 
	    
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0049_role_type_1.1.log");
	    driver.get(baseUrl + "/ict/0047/");
	    // Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0049_role_type_1.1-1");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0049_role_type_1.1-2.log");
	    driver.findElement(By.id("F001")).click();
	    // Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0049_role_type_1.1-2");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0049_role_type_1.2.log");
	    driver.get(baseUrl + "/ict/0049/");
	    // Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0049_role_type_1.2");
	    driver.get(baseUrl + "/ict/?language=" + lan);
	    driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("U0000000002");
	   
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("U0000000002");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0049_role_type_2.1-1.log");
	    driver.findElement(By.id("btnLogin")).click();
	    // Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0049_role_type_2.1-1");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0049_role_type_2.1-2.log");
	    driver.findElement(By.id("F001")).click();
	    // Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0049_role_type_2.1-2");
	    driver.get(baseUrl + "/ict/?language=" + lan);
	    driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("U0000000003");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("U0000000003");
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0049_role_type_3.log");
	    driver.findElement(By.id("btnLogin")).click();
	    // Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0049_role_type_3");
	    driver.get(baseUrl + "/ict/?language=" + lan);
	    driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("U0000000005");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("U0000000005");
	    driver.findElement(By.id("btnLogin")).click();
	    // Thread.sleep(1000);
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + lan + "/0049_role_type_4.log");
	    driver.get(baseUrl + "/ict/0049/");
	    // Thread.sleep(1000);
	    capture(imagePath +browser+"/"+lan+"/0049_role_type_4");
	    
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

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
