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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

@RunWith(Parameterized.class)
public class Bnn0075ShippingScreenErrorTest {
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
	public String imagePath = "selenium_test_images/0075/Error/";

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "webdriver/geckodriver.exe");
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");

		baseUrl = "http://localhost:8080";
	}

	public void common(String browser, String language) throws InterruptedException {

		checkPointArray = new ArrayList<Long>();
		logFileNameArray = new ArrayList<String>();

		if (browser == "IE") {
			 driver = new InternetExplorerDriver();
		} else if (browser == "Chrome") {
			driver = new ChromeDriver();
		} else if (browser == "FireFox") {
			driver = new FirefoxDriver();
		} /*else {
			driver = new EdgeDriver();
		}*/
		
		if (language == "vi") {
			driver.get(baseUrl + "/ict/?language=vi");
		} else if (language == "en") {
			driver.get(baseUrl + "/ict/?language=en");
		} else if (language == "jp") {
			driver.get(baseUrl + "/ict/?language=jp");
		}

		SeleniumCommon.enableClientConnection();
		// start testing
		// input user name and password
		driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("U0000000002");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("U0000000002");
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
		// Login
		driver.findElement(By.id("btnLogin")).click();
		Thread.sleep(3000);
		SeleniumCommon.disableClientConnection();
		Thread.sleep(3000);
		driver.findElement(By.id("btnShippingScreen")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + language + "/0075_1_1_1_1.1.log");
		capture(imagePath +browser+"/"+language+"/0075_1_1_1_1.1");
		driver.findElement(By.id("popup_ok")).click();
		Thread.sleep(3000);
		SeleniumCommon.enableClientConnection();
		Thread.sleep(3000);
		driver.findElement(By.id("btnShippingScreen")).click();
		Thread.sleep(3000);
		SeleniumCommon.disableClientConnection();
		Thread.sleep(3000);
		driver.findElement(By.id("btnSearch")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + language + "/0075_1_1_1_1.2.log");
		capture(imagePath +browser+"/"+language+"/0075_1_1_1_1.2");
		
		driver.findElement(By.id("popup_ok")).click();
		
		driver.findElement(By.id("shipping_number")).clear();
		driver.findElement(By.id("shipping_number")).sendKeys("00000001");
		
		driver.findElement(By.id("btnSearch")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + language + "/0075_1_1_1_1.3.log");
		capture(imagePath +browser+"/"+language+"/0075_1_1_1_1.3");
		
		driver.findElement(By.id("popup_ok")).click();
		
		driver.findElement(By.id("harvest_start_date")).sendKeys("30052017");
		
		driver.findElement(By.id("harvest_end_date")).sendKeys("30052017");
		
		driver.findElement(By.id("ship_start_date")).sendKeys("30052017");
		
		driver.findElement(By.id("ship_end_date")).sendKeys("30052017");
		
		driver.findElement(By.id("btnSearch")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + language + "/0075_1_1_1_1.4.log");
		capture(imagePath +browser+"/"+language+"/0075_1_1_1_1.4");
		
		driver.findElement(By.id("popup_ok")).click();

		Thread.sleep(3000);
		SeleniumCommon.enableClientConnection();
		Thread.sleep(3000);
		driver.findElement(By.id("shipping_number")).clear();
		driver.findElement(By.id("shipping_number")).sendKeys("");
		
		driver.findElement(By.id("harvest_start_date")).clear();
		driver.findElement(By.id("harvest_start_date")).sendKeys("");
		
		driver.findElement(By.id("harvest_end_date")).clear();
		driver.findElement(By.id("harvest_end_date")).sendKeys("");
		
		driver.findElement(By.id("ship_start_date")).clear();
		driver.findElement(By.id("ship_start_date")).sendKeys("");
		
		driver.findElement(By.id("ship_end_date")).clear();
		driver.findElement(By.id("ship_end_date")).sendKeys("");
		
		driver.findElement(By.id("btnSearch")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + language + "/0075_1_1_1_1.5.log");
		capture(imagePath +browser+"/"+language+"/0075_1_1_1_1.5");
		

		driver.findElement(By.cssSelector(".edit")).click();
		
		driver.findElement(By.id("shipDatePopup")).sendKeys("30052017");
		Thread.sleep(3000);
		SeleniumCommon.disableClientConnection();
		Thread.sleep(3000);
		driver.findElement(By.id("btnRegisterPopup")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + language + "/0075_3_1_1_1.log");
		capture(imagePath +browser+"/"+language+"/0075_3_1_1_1");
		
		driver.findElement(By.id("popup_ok")).click();
		Thread.sleep(3000);
		SeleniumCommon.enableClientConnection();
		Thread.sleep(3000);
		driver.findElement(By.id("btnRegisterPopup")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + language + "/0075_3_1_1_2.log");
		capture(imagePath +browser+"/"+language+"/0075_3_1_1_2");
		Thread.sleep(3000);
		SeleniumCommon.enableClientConnection();
		Thread.sleep(3000);
		driver.findElement(By.id("popup_ok")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("btnSearch")).click();
		driver.findElement(By.id("btnLast")).click(); 
		
		driver.findElement(By.cssSelector(".edit")).click();	
		Thread.sleep(3000);
		SeleniumCommon.disableClientConnection();
		Thread.sleep(3000);
		driver.findElement(By.id("btnRegisterPopup")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + language + "//0075_3_1_1_3.log");
		capture(imagePath +browser+"/"+language+"/0075_3_1_1_3");
		Thread.sleep(3000);
		SeleniumCommon.enableClientConnection();
		Thread.sleep(3000);
		driver.findElement(By.id("popup_ok")).click();
		driver.findElement(By.id("btnShippingScreen")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("btnSearch")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("btnLast")).click(); 
		driver.findElement(By.name("5")).click();
		
		driver.findElement(By.id("shipDatePopup")).sendKeys("30052017");
		Thread.sleep(3000);
		SeleniumCommon.disableClientConnection();
		Thread.sleep(3000);
		driver.findElement(By.id("btnRegisterPopup")).click();
		// store check point information
		checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
		// store log file name
		logFileNameArray.add(imagePath  + browser + "/" + language + "//0075_4_1_1_1.log");
		capture(imagePath +browser+"/"+language+"/0075_4_1_1_1");
		
		driver.findElement(By.id("popup_ok")).click();
		Thread.sleep(3000);
		SeleniumCommon.enableClientConnection();
		Thread.sleep(3000);
	}

	/*@Test
	 public void testBnn0075IeVi() throws Exception {
	 common("IE", "vi");
	 }*/
	@Test
	 public void testBnn0001BananaMenuIeEn() throws Exception {
	 common("IE", "vi");
	
	 }
	/* @Test
	 public void testBnn0001BananaMenuIeJp() throws Exception {
	 common("IE", "jp");
	
	 }
	 @Test
	 public void testBnn0001BananaMenuChromeVi() throws Exception {
	 common("Chrome", "en");
	
	 }*/
	 /* @Test
	 public void testBnn0001BananaMenuChromeEn() throws Exception {
	 common("Chrome", "en");
	
	 }*/
	// @Test
	// public void testBnn0001BananaMenuChromeJp() throws Exception {
	// common("Chrome", "jp");
	//
	/* @Test
	 public void testBnn0001BananaMenuFireFoxVi() throws Exception {
		 common("FireFox", "en");
	 }*/
	// @Test
	// public void testBnn0001BananaMenuFireFoxEn() throws Exception {
	// common("FireFox", "en");
	//
	// }
	/*@Test
	public void testBnn0001BananaMenuFireFoxJp() throws Exception {
		common("IE", "vi");

	}*/
	// @Test
	// public void testBnn0001BananaMenuedgeVi() throws Exception {
	// common("edge", "vi");
	//
	// }
	// @Test
	// public void testBnn0001BananaMenuedgeEn() throws Exception {
	// common("edge", "en");
	//
	// }
	// @Test
	// public void testBnn0001BananaMenuedgeJp() throws Exception {
	// common("edge", "jp");
	//
	// }

	@After
	public void tearDown() throws Exception {
		try {
			SeleniumCommon.startPrintingLog(checkPointArray, logFileNameArray);
			driver.quit();
		} catch (Exception ex) {
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

	public void alertScreenBnn0075(String lang) {
		if (lang == "jp") {
			pages.setLang(lang);
			// Shipping
			pages.setShipping("出荷");
		} else if (lang == "en") {
			pages.setLang(lang);
			// Shipping
			pages.setShipping("Shipping");
		} else if (lang == "vi") {
			pages.setLang(lang);
			// Shipping
			pages.setShipping("Xuất hàng");
		}
	}

	public class messageAlertBnn0075 {
		public String lang;
		public String urlSaveImage;
		// Shipping
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
