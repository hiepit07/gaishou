package vn.bananavietnam.ict.selenium;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

@RunWith(Parameterized.class)
public class Login {
	private static WebDriver driverIe;
	private static WebDriver driverFirefox;
	private static WebDriver driverChrome;
	private static WebDriver driverEdge;
	private String baseUrl;
	messageAlertLogin pages = new messageAlertLogin();

	/**
	 * Test in 3 language
	 *   jp: Japanese
	 *   en: English
	 *   vi: Vietnamese
	 */
	private List<String> languageTest = new ArrayList<String>(Arrays.asList("jp", "en", "vi"));
	
	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "U0000000001", "U0000000001" } });
	}

	@Parameter // first data value (0) is default
	public String fName;

	@Parameter(1)
	public String fPass;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "webdriver/geckodriver.exe");
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");
		
		 DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		ieCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ignore");
		
//		driverFirefox = new FirefoxDriver();
//		driverIe = new InternetExplorerDriver(ieCapabilities);
//		driverChrome = new ChromeDriver();
		driverEdge = new EdgeDriver();
		baseUrl = "http://172.16.0.17:8080";
//		driverIe.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		driverFirefox.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		driverChrome.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driverEdge.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * Test selenium in internet Explorer
	 * @throws Exception
	 */
	/*
	@Test
	public void testLoginIe() throws Exception {
		String ieBrowser = "ie";
		for(int i = 0; i < languageTest.size(); i++){
			setMessage(languageTest.get(i), ieBrowser);
			
			//language page
			driverIe.get(baseUrl + "/ict/?language=" + pages.getLang());
			
		    ///Test text language
		    //take a photo
			Thread.sleep(1000);
		    capture(pages.getUrlSaveImage() + "0000_3_1_1_1", ieBrowser);
		    
			//Click button login without username and without password
		    driverIe.findElement(By.id("btnLogin")).click();
		    driverIe.findElement(By.cssSelector("input[type=\"button\"]")).click();
		    
		    Thread.sleep(1000);
		    //take a photo
		    capture(pages.getUrlSaveImage() + "0000_LoginNoUserAndPass", ieBrowser);
		    
		    //Click button login with username and without password
		    driverIe.findElement(By.id("j_username")).clear();
		    driverIe.findElement(By.id("j_username")).sendKeys("U0000000001");
		    driverIe.findElement(By.id("btnLogin")).click();
		    driverIe.findElement(By.cssSelector("input[type=\"button\"]")).click();
		    
		    Thread.sleep(1000);
		    //take a photo
		    capture(pages.getUrlSaveImage() + "0000_LoginNoPass", ieBrowser);
		    
		    //click button login without username and with password
		    driverIe.findElement(By.id("j_username")).clear();
		    driverIe.findElement(By.id("j_username")).sendKeys("");
		    driverIe.findElement(By.id("j_password")).sendKeys("U0000000001");
		    driverIe.findElement(By.id("btnLogin")).click();
		    driverIe.findElement(By.cssSelector("input[type=\"button\"]")).click();
		    
		    Thread.sleep(1000);
		    //take a photo
		    capture(pages.getUrlSaveImage() + "0000_LoginNoUser", ieBrowser);
		    
		    // click button login with username and with password, role id = 1
		    driverIe.findElement(By.id("j_username")).clear();
		    driverIe.findElement(By.id("j_username")).sendKeys("U0000000001");
		    driverIe.findElement(By.id("j_password")).clear();
		    driverIe.findElement(By.id("j_password")).sendKeys("U0000000001");
		    driverIe.findElement(By.id("btnLogin")).click();
		    
		    Thread.sleep(1000);
		    //take a photo
		    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_1", ieBrowser);
		    
		    //Logout, return screen login
		    Actions action = new Actions(driverIe);
		    action.moveToElement(driverIe.findElement(By.cssSelector("div.drop-menu"))
		    		.findElement(By.cssSelector("ul"))
		    		.findElement(By.cssSelector("li"))
		    		.findElement(By.cssSelector("a")))
		    		.moveToElement(driverIe.findElement(By.cssSelector("ul"))
		    				.findElement(By.cssSelector("li"))
		    				.findElement(By.cssSelector("a"))).click().build().perform();
		    Thread.sleep(5000);
		    
		    //click link text logout
		    driverIe.findElement(By.linkText(pages.getLinkTextLogout())).click();
		    
		    Thread.sleep(1000);
		    
		    //message logout
		    assertEquals( pages.getMessageLogout(), driverIe.findElement(By.id("popup_message")).getText());
		    driverIe.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			driverIe.findElement(By.id("popup_ok")).click();
		    
		    //click button login with username and with password, role id = 2
		    driverIe.get(baseUrl + "/ict/?language=" + pages.getLang());
		  	
		    driverIe.findElement(By.id("j_username")).clear();
		    driverIe.findElement(By.id("j_username")).sendKeys("U0000000002");
		    driverIe.findElement(By.id("j_password")).clear();
		    driverIe.findElement(By.id("j_password")).sendKeys("U0000000002");
		    driverIe.findElement(By.id("btnLogin")).click();
		    
		    Thread.sleep(1000);
		    //take a photo
		    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_2", ieBrowser);
		    
		    //logout, return screen login
		    action.moveToElement(driverIe.findElement(By.cssSelector("div.drop-menu"))
		    		.findElement(By.cssSelector("ul"))
		    		.findElement(By.cssSelector("li"))
		    		.findElement(By.cssSelector("a")))
		    		.moveToElement(driverIe.findElement(By.cssSelector("ul"))
		    				.findElement(By.cssSelector("li"))
		    				.findElement(By.cssSelector("a"))).click().build().perform();
		    Thread.sleep(5000);

		    //click link text logout
		    driverIe.findElement(By.linkText(pages.getLinkTextLogout())).click();
		    
		    Thread.sleep(1000);
		    
		    //message logout
		    assertEquals( pages.getMessageLogout(), driverIe.findElement(By.id("popup_message")).getText());
		    driverIe.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			driverIe.findElement(By.id("popup_ok")).click();
		    
		    // click button login with username and with password, role id = 3
		    driverIe.get(baseUrl + "/ict/?language=" + pages.getLang());
		  	
		    driverIe.findElement(By.id("j_username")).clear();
		    driverIe.findElement(By.id("j_username")).sendKeys("U0000000003");
		    driverIe.findElement(By.id("j_password")).clear();
		    driverIe.findElement(By.id("j_password")).sendKeys("U0000000003");
		    driverIe.findElement(By.id("btnLogin")).click();
		    
			Thread.sleep(1000); 
//			assertEquals(pages.getNoDataExit(), driverIe.findElement(By.id("popup_message")).getText());
//			driverIe.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//			driverIe.findElement(By.id("popup_ok")).click();

		    Thread.sleep(1000);
		    ///take a photo
		    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_3", ieBrowser);
		    
		    //logout, return screen login
		    action.moveToElement(driverIe.findElement(By.cssSelector("div.drop-menu"))
		    		.findElement(By.cssSelector("ul"))
		    		.findElement(By.cssSelector("li"))
		    		.findElement(By.cssSelector("a")))
		    		.moveToElement(driverIe.findElement(By.cssSelector("ul"))
		    				.findElement(By.cssSelector("li"))
		    				.findElement(By.cssSelector("a"))).click().build().perform();
		    Thread.sleep(5000); 
		    
		    //click link text logout
		    driverIe.findElement(By.linkText(pages.getLinkTextLogout())).click();

		    Thread.sleep(1000);
		    
		    //message logout
		    assertEquals( pages.getMessageLogout(), driverIe.findElement(By.id("popup_message")).getText());
		    driverIe.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			driverIe.findElement(By.id("popup_ok")).click();
		    
		    //click button login with username and with password, role id = 4
		    driverIe.get(baseUrl + "/ict/?language=" + pages.getLang());
		  	
		    driverIe.findElement(By.id("j_username")).clear();
		    driverIe.findElement(By.id("j_username")).sendKeys("U0000000005");
		    driverIe.findElement(By.id("j_password")).clear();
		    driverIe.findElement(By.id("j_password")).sendKeys("U0000000005");
		    driverIe.findElement(By.id("btnLogin")).click();
		    
		    Thread.sleep(1000);
		    ///take a photo
		    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_4", ieBrowser);
		}
	}
	*/
	
	/**
	 * Test selenium in FireFox
	 * @throws Exception
	 */
	/*
	@Test
	public void testLoginFireFox() throws Exception{
		String firefoxBrowser = "firefox";
		setMessage("vi", firefoxBrowser);
		
		//language page
		driverFirefox.get(baseUrl + "/ict/?language=" + pages.getLang());
		
	    ///Test text language
	    //take a photo
		Thread.sleep(1000);
	    capture(pages.getUrlSaveImage() + "0000_3_1_1_1", firefoxBrowser);
	    
		//Click button login without username and without password
	    driverFirefox.findElement(By.id("btnLogin")).click();
	    driverFirefox.findElement(By.cssSelector("input[type=\"button\"]")).click();
	    
	    Thread.sleep(1000);
	    //take a photo
	    capture(pages.getUrlSaveImage() + "0000_LoginNoUserAndPass", firefoxBrowser);
	    
	    //Click button login with username and without password
	    driverFirefox.findElement(By.id("j_username")).clear();
	    driverFirefox.findElement(By.id("j_username")).sendKeys("U0000000001");
	    Thread.sleep(1000);
	    driverFirefox.findElement(By.id("btnLogin")).click();
	    Thread.sleep(1000);
	    driverFirefox.findElement(By.cssSelector("input[type=\"button\"]")).click();
	    
	    Thread.sleep(1000);
	    //take a photo
	    capture(pages.getUrlSaveImage() + "0000_LoginNoPass", firefoxBrowser);
	    
	    //click button login without username and with password
	    driverFirefox.findElement(By.id("j_username")).clear();
	    driverFirefox.findElement(By.id("j_username")).sendKeys("");
	    driverFirefox.findElement(By.id("j_password")).sendKeys("U0000000001");
	    Thread.sleep(1000);
	    driverFirefox.findElement(By.id("btnLogin")).click();
	    Thread.sleep(1000);
	    driverFirefox.findElement(By.cssSelector("input[type=\"button\"]")).click();
	    
	    Thread.sleep(1000);
	    //take a photo
	    capture(pages.getUrlSaveImage() + "0000_LoginNoUser", firefoxBrowser);
	    
	    // click button login with username and with password, role id = 1
	    driverFirefox.findElement(By.id("j_username")).clear();
	    driverFirefox.findElement(By.id("j_username")).sendKeys("U0000000001");
	    driverFirefox.findElement(By.id("j_password")).clear();
	    driverFirefox.findElement(By.id("j_password")).sendKeys("U0000000001");
	    Thread.sleep(1000);
	    driverFirefox.findElement(By.id("btnLogin")).click();
	    
	    Thread.sleep(1000);
	    //take a photo
	    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_1", firefoxBrowser);
	    
	    //Logout, return screen login
	    Actions action = new Actions(driverFirefox);
	    action.moveToElement(driverFirefox.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverFirefox.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    
	    //click link text logout
	    driverFirefox.findElement(By.linkText(pages.getLinkTextLogout())).click();
	    
	    Thread.sleep(10000);
	    
	    //message logout
//	    assertEquals( pages.getMessageLogout(), closeAlertAndGetItsTextFirefox());
	    assertEquals( pages.getMessageLogout(), driverFirefox.findElement(By.id("popup_message")).getText());
		driverFirefox.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driverFirefox.findElement(By.id("popup_ok")).click();
	    //click button login with username and with password, role id = 2
	    driverFirefox.get(baseUrl + "/ict/?language=" + pages.getLang());
	  	
	    driverFirefox.findElement(By.id("j_username")).clear();
	    driverFirefox.findElement(By.id("j_username")).sendKeys("U0000000002");
	    driverFirefox.findElement(By.id("j_password")).clear();
	    driverFirefox.findElement(By.id("j_password")).sendKeys("U0000000002");
	    Thread.sleep(1000);
	    driverFirefox.findElement(By.id("btnLogin")).click();
	    
	    Thread.sleep(1000);
	    //take a photo
	    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_2", firefoxBrowser);
	    
	    //logout, return screen login
	    action.moveToElement(driverFirefox.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverFirefox.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);

	    //click link text logout
	    driverFirefox.findElement(By.linkText(pages.getLinkTextLogout())).click();
	    
	    Thread.sleep(1000);
	    
	    //message logout
	    //assertEquals( pages.getMessageLogout(), closeAlertAndGetItsTextFirefox());
	    assertEquals( pages.getMessageLogout(), driverFirefox.findElement(By.id("popup_message")).getText());
		driverFirefox.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driverFirefox.findElement(By.id("popup_ok")).click();
	    
	    // click button login with username and with password, role id = 3
	    driverFirefox.get(baseUrl + "/ict/?language=" + pages.getLang());
	  	
	    driverFirefox.findElement(By.id("j_username")).clear();
	    driverFirefox.findElement(By.id("j_username")).sendKeys("U0000000003");
	    driverFirefox.findElement(By.id("j_password")).clear();
	    driverFirefox.findElement(By.id("j_password")).sendKeys("U0000000003");
	    Thread.sleep(1000);
	    driverFirefox.findElement(By.id("btnLogin")).click();
	    
//		Thread.sleep(1000); 
//		assertEquals(pages.getNoDataExit(), driverFirefox.findElement(By.id("popup_message")).getText());
//		driverFirefox.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//		driverFirefox.findElement(By.id("popup_ok")).click();

	    Thread.sleep(1000);
	    ///take a photo
	    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_3", firefoxBrowser);
	    
	    //logout, return screen login
	    action.moveToElement(driverFirefox.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverFirefox.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000); 
	    
	    //click link text logout
	    driverFirefox.findElement(By.linkText(pages.getLinkTextLogout())).click();

	    Thread.sleep(1000);
	    
	    //message logout
	    //assertEquals( pages.getMessageLogout(), closeAlertAndGetItsTextFirefox());
	    assertEquals( pages.getMessageLogout(), driverFirefox.findElement(By.id("popup_message")).getText());
		driverFirefox.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driverFirefox.findElement(By.id("popup_ok")).click();
	    
	    //click button login with username and with password, role id = 4
	    driverFirefox.get(baseUrl + "/ict/?language=" + pages.getLang());
	  	
	    driverFirefox.findElement(By.id("j_username")).clear();
	    driverFirefox.findElement(By.id("j_username")).sendKeys("U0000000005");
	    driverFirefox.findElement(By.id("j_password")).clear();
	    driverFirefox.findElement(By.id("j_password")).sendKeys("U0000000005");
	    Thread.sleep(1000);
	    driverFirefox.findElement(By.id("btnLogin")).click();
	    
	    Thread.sleep(1000);
	    ///take a photo
	    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_4", firefoxBrowser);
	}
	*/
	
	/**
	 * Test selenium in Chrome
	 * @throws Exception
	 */
	/*
	@Test
	public void testLoginChrome() throws Exception{
		String chromeBrowser = "chrome";
		setMessage("vi", chromeBrowser);
		
		//language page
		driverChrome.get(baseUrl + "/ict/?language=" + pages.getLang());
		
	    ///Test text language
	    //take a photo
		Thread.sleep(1000);
	    capture(pages.getUrlSaveImage() + "0000_3_1_1_1", chromeBrowser);
	    
		//Click button login without username and without password
	    driverChrome.findElement(By.id("btnLogin")).click();
	    driverChrome.findElement(By.cssSelector("input[type=\"button\"]")).click();
	    
	    Thread.sleep(1000);
	    //take a photo
	    capture(pages.getUrlSaveImage() + "0000_LoginNoUserAndPass", chromeBrowser);
	    
	    //Click button login with username and without password
	    driverChrome.findElement(By.id("j_username")).clear();
	    driverChrome.findElement(By.id("j_username")).sendKeys("U0000000001");
	    driverChrome.findElement(By.id("btnLogin")).click();
	    driverChrome.findElement(By.cssSelector("input[type=\"button\"]")).click();
	    
	    Thread.sleep(1000);
	    //take a photo
	    capture(pages.getUrlSaveImage() + "0000_LoginNoPass", chromeBrowser);
	    
	    //click button login without username and with password
	    driverChrome.findElement(By.id("j_username")).clear();
	    driverChrome.findElement(By.id("j_username")).sendKeys("");
	    driverChrome.findElement(By.id("j_password")).sendKeys("U0000000001");
	    driverChrome.findElement(By.id("btnLogin")).click();
	    driverChrome.findElement(By.cssSelector("input[type=\"button\"]")).click();
	    
	    Thread.sleep(1000);
	    //take a photo
	    capture(pages.getUrlSaveImage() + "0000_LoginNoUser", chromeBrowser);
	    
	    // click button login with username and with password, role id = 1
	    driverChrome.findElement(By.id("j_username")).clear();
	    driverChrome.findElement(By.id("j_username")).sendKeys("U0000000001");
	    driverChrome.findElement(By.id("j_password")).clear();
	    driverChrome.findElement(By.id("j_password")).sendKeys("U0000000001");
	    driverChrome.findElement(By.id("btnLogin")).click();
	    
	    Thread.sleep(1000);
	    //take a photo
	    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_1", chromeBrowser);
	    
	    //Logout, return screen login
	    Actions action = new Actions(driverChrome);
	    action.moveToElement(driverChrome.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverChrome.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    
	    //click link text logout
	    driverChrome.findElement(By.linkText(pages.getLinkTextLogout())).click();
	    
	    Thread.sleep(1000);
	    
	    //message logout
	    //assertEquals( pages.getMessageLogout(), closeAlertAndGetItsTextChrome());
	    assertEquals("Bạn có muốn đăng xuất không?", driverChrome.findElement(By.id("popup_message")).getText());
	    driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverChrome.findElement(By.id("popup_ok")).click();
	    
	    //click button login with username and with password, role id = 2
	    driverChrome.get(baseUrl + "/ict/?language=" + pages.getLang());
	  	
	    driverChrome.findElement(By.id("j_username")).clear();
	    driverChrome.findElement(By.id("j_username")).sendKeys("U0000000002");
	    driverChrome.findElement(By.id("j_password")).clear();
	    driverChrome.findElement(By.id("j_password")).sendKeys("U0000000002");
	    driverChrome.findElement(By.id("btnLogin")).click();
	    
	    Thread.sleep(1000);
	    //take a photo
	    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_2", chromeBrowser);
	    
	    //logout, return screen login
	    action.moveToElement(driverChrome.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverChrome.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);

	    //click link text logout
	    driverChrome.findElement(By.linkText(pages.getLinkTextLogout())).click();
	    
	    Thread.sleep(1000);
	    
	    //message logout
	    //assertEquals( pages.getMessageLogout(), closeAlertAndGetItsTextChrome());
	    assertEquals("Bạn có muốn đăng xuất không?", driverChrome.findElement(By.id("popup_message")).getText());
	    driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverChrome.findElement(By.id("popup_ok")).click();
	    
	    // click button login with username and with password, role id = 3
	    driverChrome.get(baseUrl + "/ict/?language=" + pages.getLang());
	  	
	    driverChrome.findElement(By.id("j_username")).clear();
	    driverChrome.findElement(By.id("j_username")).sendKeys("U0000000003");
	    driverChrome.findElement(By.id("j_password")).clear();
	    driverChrome.findElement(By.id("j_password")).sendKeys("U0000000003");
	    driverChrome.findElement(By.id("btnLogin")).click();
	    
//		Thread.sleep(1000); 
//		assertEquals(pages.getNoDataExit(), driverChrome.findElement(By.id("popup_message")).getText());
//		driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//		driverChrome.findElement(By.id("popup_ok")).click();

	    Thread.sleep(1000);
	    ///take a photo
	    //language ja
	    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_3", chromeBrowser);
	    
	    //logout, return screen login
	    action.moveToElement(driverChrome.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverChrome.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000); 
	    
	    //click link text logout
	    driverChrome.findElement(By.linkText(pages.getLinkTextLogout())).click();

	    Thread.sleep(1000);
	    
	    //message logout
	    //assertEquals( pages.getMessageLogout(), closeAlertAndGetItsTextChrome());
	    assertEquals("Bạn có muốn đăng xuất không?", driverChrome.findElement(By.id("popup_message")).getText());
	    driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverChrome.findElement(By.id("popup_ok")).click();
	    
	    //click button login with username and with password, role id = 4
	    driverChrome.get(baseUrl + "/ict/?language=" + pages.getLang());
	  	
	    driverChrome.findElement(By.id("j_username")).clear();
	    driverChrome.findElement(By.id("j_username")).sendKeys("U0000000005");
	    driverChrome.findElement(By.id("j_password")).clear();
	    driverChrome.findElement(By.id("j_password")).sendKeys("U0000000005");
	    driverChrome.findElement(By.id("btnLogin")).click();
	    
	    Thread.sleep(1000);
	    ///take a photo
	    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_4", chromeBrowser);
	}
	*/
	
	/**
	 * Test selenium in Chrome
	 * @throws Exception
	 */
	@Test
	public void testLoginEdge() throws Exception{
		String chromeBrowser = "edge";
		setMessage("vi", chromeBrowser);
		
		//language page
		driverEdge.get(baseUrl + "/ict/?language=" + pages.getLang());
		
	    ///Test text language
	    //take a photo
		Thread.sleep(1000);
	    capture(pages.getUrlSaveImage() + "0000_3_1_1_1", chromeBrowser);
	    
		//Click button login without username and without password
	    driverEdge.findElement(By.id("btnLogin")).click();
	    driverEdge.findElement(By.cssSelector("input[type=\"button\"]")).click();
	    
	    Thread.sleep(1000);
	    //take a photo
	    capture(pages.getUrlSaveImage() + "0000_LoginNoUserAndPass", chromeBrowser);
	    
	    //Click button login with username and without password
	    driverEdge.findElement(By.id("j_username")).clear();
	    driverEdge.findElement(By.id("j_username")).sendKeys("U0000000001");
	    driverEdge.findElement(By.id("btnLogin")).click();
	    driverEdge.findElement(By.cssSelector("input[type=\"button\"]")).click();
	    
	    Thread.sleep(1000);
	    //take a photo
	    capture(pages.getUrlSaveImage() + "0000_LoginNoPass", chromeBrowser);
	    
	    //click button login without username and with password
	    driverEdge.findElement(By.id("j_username")).clear();
	    driverEdge.findElement(By.id("j_username")).sendKeys("");
	    driverEdge.findElement(By.id("j_password")).sendKeys("U0000000001");
	    driverEdge.findElement(By.id("btnLogin")).click();
	    driverEdge.findElement(By.cssSelector("input[type=\"button\"]")).click();
	    
	    Thread.sleep(1000);
	    //take a photo
	    capture(pages.getUrlSaveImage() + "0000_LoginNoUser", chromeBrowser);
	    
	    // click button login with username and with password, role id = 1
	    driverEdge.findElement(By.id("j_username")).clear();
	    driverEdge.findElement(By.id("j_username")).sendKeys("U0000000001");
	    driverEdge.findElement(By.id("j_password")).clear();
	    driverEdge.findElement(By.id("j_password")).sendKeys("U0000000001");
	    driverEdge.findElement(By.id("btnLogin")).click();
	    
	    Thread.sleep(1000);
	    //take a photo
	    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_1", chromeBrowser);
	    
	    //Logout, return screen login
	    Actions action = new Actions(driverEdge);
	    action.moveToElement(driverEdge.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverEdge.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    
	    //click link text logout
	    driverEdge.findElement(By.linkText(pages.getLinkTextLogout())).click();
	    
	    Thread.sleep(1000);
	    
	    //message logout
	    //assertEquals( pages.getMessageLogout(), closeAlertAndGetItsTextChrome());
	    assertEquals("Bạn có muốn đăng xuất không?", driverEdge.findElement(By.id("popup_message")).getText());
	    driverEdge.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverEdge.findElement(By.id("popup_ok")).click();
	    
	    //click button login with username and with password, role id = 2
	    driverEdge.get(baseUrl + "/ict/?language=" + pages.getLang());
	  	
	    driverEdge.findElement(By.id("j_username")).clear();
	    driverEdge.findElement(By.id("j_username")).sendKeys("U0000000002");
	    driverEdge.findElement(By.id("j_password")).clear();
	    driverEdge.findElement(By.id("j_password")).sendKeys("U0000000002");
	    driverEdge.findElement(By.id("btnLogin")).click();
	    
	    Thread.sleep(1000);
	    //take a photo
	    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_2", chromeBrowser);
	    
	    //logout, return screen login
	    action.moveToElement(driverEdge.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverEdge.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);

	    //click link text logout
	    driverEdge.findElement(By.linkText(pages.getLinkTextLogout())).click();
	    
	    Thread.sleep(1000);
	    
	    //message logout
	    //assertEquals( pages.getMessageLogout(), closeAlertAndGetItsTextChrome());
	    assertEquals("Bạn có muốn đăng xuất không?", driverEdge.findElement(By.id("popup_message")).getText());
	    driverEdge.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverEdge.findElement(By.id("popup_ok")).click();
	    
	    // click button login with username and with password, role id = 3
	    driverEdge.get(baseUrl + "/ict/?language=" + pages.getLang());
	  	
	    driverEdge.findElement(By.id("j_username")).clear();
	    driverEdge.findElement(By.id("j_username")).sendKeys("U0000000003");
	    driverEdge.findElement(By.id("j_password")).clear();
	    driverEdge.findElement(By.id("j_password")).sendKeys("U0000000003");
	    driverEdge.findElement(By.id("btnLogin")).click();
	    
//		Thread.sleep(1000); 
//		assertEquals(pages.getNoDataExit(), driverChrome.findElement(By.id("popup_message")).getText());
//		driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//		driverChrome.findElement(By.id("popup_ok")).click();

	    Thread.sleep(1000);
	    ///take a photo
	    //language ja
	    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_3", chromeBrowser);
	    
	    //logout, return screen login
	    action.moveToElement(driverEdge.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverEdge.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000); 
	    
	    //click link text logout
	    driverEdge.findElement(By.linkText(pages.getLinkTextLogout())).click();

	    Thread.sleep(1000);
	    
	    //message logout
	    //assertEquals( pages.getMessageLogout(), closeAlertAndGetItsTextChrome());
	    assertEquals("Bạn có muốn đăng xuất không?", driverEdge.findElement(By.id("popup_message")).getText());
	    driverEdge.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverEdge.findElement(By.id("popup_ok")).click();
	    
	    //click button login with username and with password, role id = 4
	    driverEdge.get(baseUrl + "/ict/?language=" + pages.getLang());
	  	
	    driverEdge.findElement(By.id("j_username")).clear();
	    driverEdge.findElement(By.id("j_username")).sendKeys("U0000000005");
	    driverEdge.findElement(By.id("j_password")).clear();
	    driverEdge.findElement(By.id("j_password")).sendKeys("U0000000005");
	    driverEdge.findElement(By.id("btnLogin")).click();
	    
	    Thread.sleep(1000);
	    ///take a photo
	    capture(pages.getUrlSaveImage() + "0000_2_1_1_1_4", chromeBrowser);
	}
	//quit when run successful
	@After
	public void tearDown() throws Exception {
		driverIe.quit();
		driverFirefox.quit();
		driverChrome.quit();
		driverEdge.quit();
	}

	//take photos
	public static boolean capture(String fileName, String browser) {
		try {
			File f = new File(fileName + ".png");
			File oFile = null;
			if(browser == "ie"){
				oFile = ((TakesScreenshot) driverIe).getScreenshotAs(OutputType.FILE);
			} else if(browser == "firefox"){
				oFile = ((TakesScreenshot) driverFirefox).getScreenshotAs(OutputType.FILE);
			} else if(browser == "chrome"){
				oFile = ((TakesScreenshot) driverChrome).getScreenshotAs(OutputType.FILE);
			} else if(browser == "edge"){
				oFile = ((TakesScreenshot) driverEdge).getScreenshotAs(OutputType.FILE);
			}
			BufferedImage image = ImageIO.read(oFile);
			ImageIO.write(image, "png", f);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// set all text in screen login
	public void setMessage(String lang, String browser){
		if(lang == "jp"){
			pages.setLang(lang);
			//Url save image
			if(browser =="ie"){
				// url save image in browser internet explorer
				pages.setUrlSaveImage("selenium_test_images/0000/IE/jp/");
			} else if(browser == "firefox"){
				// url save image in browser firefox
				pages.setUrlSaveImage("selenium_test_images/0000/FIREFOX/jp/");
			} else if (browser == "chrome"){
				// url save image in browser chrome
				pages.setUrlSaveImage("selenium_test_images/0000/CHROME/jp/");
			} else if (browser == "edge"){
				// url save image in browser edge
				pages.setUrlSaveImage("");
			}
			// message logout follow language
			pages.setMessageLogout("ログアウトします。よろしいですか？");
			// data no exit
			pages.setNoDataExit("データがありません。");
			//link text log out
			pages.setLinkTextLogout("ログアウト");
		}else if(lang == "en"){
			pages.setLang(lang);
			//Url save image
			if(browser =="ie"){
				// url save image in browser internet explorer
				pages.setUrlSaveImage("selenium_test_images/0000/IE/en/");
			} else if(browser == "firefox"){
				// url save image in browser firefox
				pages.setUrlSaveImage("selenium_test_images/0000/FIREFOX/en/");
			} else if (browser == "chrome"){
				// url save image in browser chrome
				pages.setUrlSaveImage("selenium_test_images/0000/CHROME/en/");
			} else if (browser == "edge"){
				// url save image in browser edge
				pages.setUrlSaveImage("");
			}
			// message logout follow language
			pages.setMessageLogout("Are you sure you want to logout?");
			// data no exit
			pages.setNoDataExit("Data do not exist!");
			//link text log out
			pages.setLinkTextLogout("Log out");
		}else if (lang == "vi"){
			pages.setLang(lang);
			//Url save image
			if(browser =="ie"){
				// url save image in browser internet explorer
				pages.setUrlSaveImage("selenium_test_images/0000/IE/vn/");
			} else if(browser == "firefox"){
				// url save image in browser firefox
				pages.setUrlSaveImage("selenium_test_images/0000/FIREFOX/vn/");
			} else if (browser == "chrome"){
				// url save image in browser chrome
				pages.setUrlSaveImage("selenium_test_images/0000/CHROME/vn/");
			} else if (browser == "edge"){
				// url save image in browser edge
				pages.setUrlSaveImage("");
			}
			// message logout follow language
			pages.setMessageLogout("Bạn có muốn đăng xuất không?");
			// data no exit
			pages.setNoDataExit("Không tồn tại thông tin này!");
			//link text log out
			pages.setLinkTextLogout("Đăng xuất");
		}
	}
	  
	  public class messageAlertLogin{
		  
		public String lang;
		public String urlSaveImage;
		public String messageLogout;
		public String linkTextLogout;
		public String noDataExit;

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

		public String getMessageLogout() {
			return messageLogout;
		}

		public void setMessageLogout(String messageLogout) {
			this.messageLogout = messageLogout;
		}

		public String getLinkTextLogout() {
			return linkTextLogout;
		}

		public void setLinkTextLogout(String linkTextLogout) {
			this.linkTextLogout = linkTextLogout;
		}

		public String getNoDataExit() {
			return noDataExit;
		}

		public void setNoDataExit(String noDataExit) {
			this.noDataExit = noDataExit;
		}
		
	  }
}
