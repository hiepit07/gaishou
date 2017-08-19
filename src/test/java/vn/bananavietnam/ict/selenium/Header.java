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
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

@RunWith(Parameterized.class)
public class Header {
	private static WebDriver driverIe;
	private static WebDriver driverFirefox;
	private static WebDriver driverChrome;
	private static WebDriver driverEdge;
	private String baseUrl;
	messageAlertHeader pages = new messageAlertHeader();

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
	public void testHeaderIe() throws Exception {
		String ieBrowser = "ie";
		for(int i = 0; i < languageTest.size(); i++){
			setMessage(languageTest.get(i), ieBrowser);
			
			//language page
			driverIe.get(baseUrl + "/ict/?language=" + pages.getLang());
			
			//login with role id = 1
			driverIe.findElement(By.id("j_username")).clear();
			driverIe.findElement(By.id("j_username")).sendKeys("U0000000001");
			driverIe.findElement(By.id("j_password")).clear();
			driverIe.findElement(By.id("j_password")).sendKeys("U0000000001");
			driverIe.findElement(By.id("btnLogin")).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "1_1_1_1_1", ieBrowser);
			//click button general setting
			driverIe.findElement(By.id("btnGeneralSetting")).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "2_1_1_1", ieBrowser);
			Thread.sleep(500);
			capture(pages.getUrlSaveImage() + "3_1_1_1", ieBrowser);
			Thread.sleep(500);
			capture(pages.getUrlSaveImage() + "4_1_1_1", ieBrowser);
			Thread.sleep(500);
			capture(pages.getUrlSaveImage() + "5_1_1_1", ieBrowser);
			Thread.sleep(500);
			capture(pages.getUrlSaveImage() + "6_1_1_1", ieBrowser);
			//change url in screen 0037
			driverIe.get(baseUrl + "/ict/0037/?language=" + pages.getLang());
			Thread.sleep(1000);
			//click button task management
			driverIe.findElement(By.id("btnTaskManagement")).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "3_1_1_2", ieBrowser);
			//click button trace
			driverIe.findElement(By.id("btnTrace")).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "4_1_1_2", ieBrowser);
			//click button task process
			driverIe.findElement(By.id("btnTaskProcess")).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "5_1_1_2", ieBrowser);
			//click button shipping number
			driverIe.findElement(By.id("btnShippingScreen")).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "6_1_1_2", ieBrowser);
			//click drop down change password
			Actions action = new Actions(driverIe);
		    action.moveToElement(driverIe.findElement(By.cssSelector("div.drop-menu"))
		    		.findElement(By.cssSelector("ul"))
		    		.findElement(By.cssSelector("li"))
		    		.findElement(By.cssSelector("a")))
		    		.moveToElement(driverIe.findElement(By.cssSelector("ul"))
		    				.findElement(By.cssSelector("li"))
		    				.findElement(By.cssSelector("a"))).click().build().perform();
		    Thread.sleep(2000);
			driverIe.findElement(By.linkText(pages.getChangePassword())).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "7_1_1_1", ieBrowser);
			//click drop down show guide
		    action.moveToElement(driverIe.findElement(By.cssSelector("div.drop-menu"))
		    		.findElement(By.cssSelector("ul"))
		    		.findElement(By.cssSelector("li"))
		    		.findElement(By.cssSelector("a")))
		    		.moveToElement(driverIe.findElement(By.cssSelector("ul"))
		    				.findElement(By.cssSelector("li"))
		    				.findElement(By.cssSelector("a"))).click().build().perform();
		    Thread.sleep(2000);
			driverIe.findElement(By.linkText(pages.getShowGuide())).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "8_1_1_1", ieBrowser);
			//click drop down logout
		    action.moveToElement(driverIe.findElement(By.cssSelector("div.drop-menu"))
		    		.findElement(By.cssSelector("ul"))
		    		.findElement(By.cssSelector("li"))
		    		.findElement(By.cssSelector("a")))
		    		.moveToElement(driverIe.findElement(By.cssSelector("ul"))
		    				.findElement(By.cssSelector("li"))
		    				.findElement(By.cssSelector("a"))).click().build().perform();
		    Thread.sleep(2000);
		    driverIe.findElement(By.linkText(pages.getLinkTextLogout())).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "9_1_1_1", ieBrowser);
			//message logout
		    assertEquals( pages.getMessageLogout(), driverIe.findElement(By.id("popup_message")).getText());
		    driverIe.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			driverIe.findElement(By.id("popup_ok")).click();
			
			// login with role id = 2
			driverIe.get(baseUrl + "/ict/?language=" + pages.getLang());
			driverIe.findElement(By.id("j_username")).clear();
			driverIe.findElement(By.id("j_username")).sendKeys("U0000000002");
			driverIe.findElement(By.id("j_password")).clear();
			driverIe.findElement(By.id("j_password")).sendKeys("U0000000002");
			driverIe.findElement(By.id("btnLogin")).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "1_1_1_1_2", ieBrowser);
			//click button task management
			capture(pages.getUrlSaveImage() + "2_1_2_1", ieBrowser);
			driverIe.findElement(By.id("btnTaskManagement")).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "3_1_2_1", ieBrowser);
			//click button trace
			driverIe.findElement(By.id("btnTrace")).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "4_1_2_1", ieBrowser);
			//click button task process
			driverIe.findElement(By.id("btnTaskProcess")).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "5_1_2_1", ieBrowser);
			//click button shipping number
			driverIe.findElement(By.id("btnShippingScreen")).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "6_1_2_1", ieBrowser);
			//click drop down change password
		    action.moveToElement(driverIe.findElement(By.cssSelector("div.drop-menu"))
		    		.findElement(By.cssSelector("ul"))
		    		.findElement(By.cssSelector("li"))
		    		.findElement(By.cssSelector("a")))
		    		.moveToElement(driverIe.findElement(By.cssSelector("ul"))
		    				.findElement(By.cssSelector("li"))
		    				.findElement(By.cssSelector("a"))).click().build().perform();
		    Thread.sleep(2000);
			driverIe.findElement(By.linkText(pages.getChangePassword())).click();
			Thread.sleep(1000);
			//click drop down show guide
		    action.moveToElement(driverIe.findElement(By.cssSelector("div.drop-menu"))
		    		.findElement(By.cssSelector("ul"))
		    		.findElement(By.cssSelector("li"))
		    		.findElement(By.cssSelector("a")))
		    		.moveToElement(driverIe.findElement(By.cssSelector("ul"))
		    				.findElement(By.cssSelector("li"))
		    				.findElement(By.cssSelector("a"))).click().build().perform();
		    Thread.sleep(2000);
			driverIe.findElement(By.linkText(pages.getShowGuide())).click();
			Thread.sleep(1000);
			//click drop down logout
		    action.moveToElement(driverIe.findElement(By.cssSelector("div.drop-menu"))
		    		.findElement(By.cssSelector("ul"))
		    		.findElement(By.cssSelector("li"))
		    		.findElement(By.cssSelector("a")))
		    		.moveToElement(driverIe.findElement(By.cssSelector("ul"))
		    				.findElement(By.cssSelector("li"))
		    				.findElement(By.cssSelector("a"))).click().build().perform();
		    Thread.sleep(2000);
		    driverIe.findElement(By.linkText(pages.getLinkTextLogout())).click();
			//message logout
		    assertEquals( pages.getMessageLogout(), driverIe.findElement(By.id("popup_message")).getText());
		    driverIe.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			driverIe.findElement(By.id("popup_ok")).click();
			
			//login with role id = 3
			driverIe.get(baseUrl + "/ict/?language=" + pages.getLang());
			driverIe.findElement(By.id("j_username")).clear();
			driverIe.findElement(By.id("j_username")).sendKeys("U0000000003");
			driverIe.findElement(By.id("j_password")).clear();
			driverIe.findElement(By.id("j_password")).sendKeys("U0000000003");
			driverIe.findElement(By.id("btnLogin")).click();
			Thread.sleep(1000);
			//click button task management
			driverIe.findElement(By.id("btnTaskManagement")).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "3_1_3_1", ieBrowser);
			//click button trace
			driverIe.findElement(By.id("btnTrace")).click();
			Thread.sleep(1000);
			//click button task process
			driverIe.findElement(By.id("btnTaskProcess")).click();
			Thread.sleep(1000);
			//click button shipping number
			driverIe.findElement(By.id("btnShippingScreen")).click();
			Thread.sleep(1000);
			//click drop down change password
		    action.moveToElement(driverIe.findElement(By.cssSelector("div.drop-menu"))
		    		.findElement(By.cssSelector("ul"))
		    		.findElement(By.cssSelector("li"))
		    		.findElement(By.cssSelector("a")))
		    		.moveToElement(driverIe.findElement(By.cssSelector("ul"))
		    				.findElement(By.cssSelector("li"))
		    				.findElement(By.cssSelector("a"))).click().build().perform();
		    Thread.sleep(2000);
			driverIe.findElement(By.linkText(pages.getChangePassword())).click();
			Thread.sleep(1000);
			//click drop down show guide
		    action.moveToElement(driverIe.findElement(By.cssSelector("div.drop-menu"))
		    		.findElement(By.cssSelector("ul"))
		    		.findElement(By.cssSelector("li"))
		    		.findElement(By.cssSelector("a")))
		    		.moveToElement(driverIe.findElement(By.cssSelector("ul"))
		    				.findElement(By.cssSelector("li"))
		    				.findElement(By.cssSelector("a"))).click().build().perform();
		    Thread.sleep(2000);
			driverIe.findElement(By.linkText(pages.getShowGuide())).click();
			Thread.sleep(1000);
			//click drop down logout
		    action.moveToElement(driverIe.findElement(By.cssSelector("div.drop-menu"))
		    		.findElement(By.cssSelector("ul"))
		    		.findElement(By.cssSelector("li"))
		    		.findElement(By.cssSelector("a")))
		    		.moveToElement(driverIe.findElement(By.cssSelector("ul"))
		    				.findElement(By.cssSelector("li"))
		    				.findElement(By.cssSelector("a"))).click().build().perform();
		    Thread.sleep(2000);
		    driverIe.findElement(By.linkText(pages.getLinkTextLogout())).click();
			//message logout
		    assertEquals( pages.getMessageLogout(), driverIe.findElement(By.id("popup_message")).getText());
		    driverIe.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			driverIe.findElement(By.id("popup_ok")).click();
			
			//login with role id = 4
			driverIe.get(baseUrl + "/ict/?language=" + pages.getLang());
			driverIe.findElement(By.id("j_username")).clear();
			driverIe.findElement(By.id("j_username")).sendKeys("U0000000005");
			driverIe.findElement(By.id("j_password")).clear();
			driverIe.findElement(By.id("j_password")).sendKeys("U0000000005");
			driverIe.findElement(By.id("btnLogin")).click();
			Thread.sleep(1000);
			//click button task management
			driverIe.findElement(By.id("btnTaskManagement")).click();
			Thread.sleep(1000);
			capture(pages.getUrlSaveImage() + "3_1_4_1", ieBrowser);
			//click button trace
			driverIe.findElement(By.id("btnTrace")).click();
			Thread.sleep(1000);
			//click button task process
			driverIe.findElement(By.id("btnTaskProcess")).click();
			Thread.sleep(1000);
			//click button shipping number
			driverIe.findElement(By.id("btnShippingScreen")).click();
			Thread.sleep(1000);
			//click drop down change password
		    action.moveToElement(driverIe.findElement(By.cssSelector("div.drop-menu"))
		    		.findElement(By.cssSelector("ul"))
		    		.findElement(By.cssSelector("li"))
		    		.findElement(By.cssSelector("a")))
		    		.moveToElement(driverIe.findElement(By.cssSelector("ul"))
		    				.findElement(By.cssSelector("li"))
		    				.findElement(By.cssSelector("a"))).click().build().perform();
		    Thread.sleep(2000);
			driverIe.findElement(By.linkText(pages.getChangePassword())).click();
			Thread.sleep(1000);
			//click drop down show guide
		    action.moveToElement(driverIe.findElement(By.cssSelector("div.drop-menu"))
		    		.findElement(By.cssSelector("ul"))
		    		.findElement(By.cssSelector("li"))
		    		.findElement(By.cssSelector("a")))
		    		.moveToElement(driverIe.findElement(By.cssSelector("ul"))
		    				.findElement(By.cssSelector("li"))
		    				.findElement(By.cssSelector("a"))).click().build().perform();
		    Thread.sleep(2000);
			driverIe.findElement(By.linkText(pages.getShowGuide())).click();
			Thread.sleep(1000);
			//click drop down logout
		    action.moveToElement(driverIe.findElement(By.cssSelector("div.drop-menu"))
		    		.findElement(By.cssSelector("ul"))
		    		.findElement(By.cssSelector("li"))
		    		.findElement(By.cssSelector("a")))
		    		.moveToElement(driverIe.findElement(By.cssSelector("ul"))
		    				.findElement(By.cssSelector("li"))
		    				.findElement(By.cssSelector("a"))).click().build().perform();
		    Thread.sleep(2000);
		    driverIe.findElement(By.linkText(pages.getLinkTextLogout())).click();
			//message logout
		    assertEquals( pages.getMessageLogout(), driverIe.findElement(By.id("popup_message")).getText());
		    driverIe.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			driverIe.findElement(By.id("popup_ok")).click();
		}
	}
	*/
	/**
	 * Test selenium in FireFox
	 * @throws Exception
	 */
	/*
	@Test
	public void testHeaderFireFox() throws Exception{
		String firefoxBrowser = "firefox";
		setMessage("vi", firefoxBrowser);
		
		//language page
		driverFirefox.get(baseUrl + "/ict/?language=" + pages.getLang());
		
		//login with role id = 1
		driverFirefox.findElement(By.id("j_username")).clear();
		driverFirefox.findElement(By.id("j_username")).sendKeys("U0000000001");
		driverFirefox.findElement(By.id("j_password")).clear();
		driverFirefox.findElement(By.id("j_password")).sendKeys("U0000000001");
		driverFirefox.findElement(By.id("btnLogin")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "1_1_1_1_1", firefoxBrowser);
		//click button general setting
		driverFirefox.findElement(By.id("btnGeneralSetting")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "2_1_1_1", firefoxBrowser);
		Thread.sleep(500);
		capture(pages.getUrlSaveImage() + "3_1_1_1", firefoxBrowser);
		Thread.sleep(500);
		capture(pages.getUrlSaveImage() + "4_1_1_1", firefoxBrowser);
		Thread.sleep(500);
		capture(pages.getUrlSaveImage() + "5_1_1_1", firefoxBrowser);
		Thread.sleep(500);
		capture(pages.getUrlSaveImage() + "6_1_1_1", firefoxBrowser);
		//change url in screen 0037
		driverFirefox.get(baseUrl + "/ict/0037/?language=" + pages.getLang());
		Thread.sleep(1000);
		//click button task management
		driverFirefox.findElement(By.id("btnTaskManagement")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "3_1_1_2", firefoxBrowser);
		//click button trace
		driverFirefox.findElement(By.id("btnTrace")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "4_1_1_2", firefoxBrowser);
		//click button task process
		driverFirefox.findElement(By.id("btnTaskProcess")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "5_1_1_2", firefoxBrowser);
		//click button shipping number
		driverFirefox.findElement(By.id("btnShippingScreen")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "6_1_1_2", firefoxBrowser);
		//click drop down change password
		Actions action = new Actions(driverFirefox);
	    action.moveToElement(driverFirefox.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverFirefox.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(2000);
	    driverFirefox.findElement(By.linkText(pages.getChangePassword())).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "7_1_1_1", firefoxBrowser);
		//click drop down show guide
	    action.moveToElement(driverFirefox.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverFirefox.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(2000);
	    driverFirefox.findElement(By.linkText(pages.getShowGuide())).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "8_1_1_1", firefoxBrowser);
		//click drop down logout
	    action.moveToElement(driverFirefox.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverFirefox.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(2000);
	    driverFirefox.findElement(By.linkText(pages.getLinkTextLogout())).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "9_1_1_1", firefoxBrowser);
		//message logout
	    assertEquals( pages.getMessageLogout(), driverFirefox.findElement(By.id("popup_message")).getText());
	    driverFirefox.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverFirefox.findElement(By.id("popup_ok")).click();
		
		// login with role id = 2
		driverFirefox.get(baseUrl + "/ict/?language=" + pages.getLang());
		driverFirefox.findElement(By.id("j_username")).clear();
		driverFirefox.findElement(By.id("j_username")).sendKeys("U0000000002");
		driverFirefox.findElement(By.id("j_password")).clear();
		driverFirefox.findElement(By.id("j_password")).sendKeys("U0000000002");
		driverFirefox.findElement(By.id("btnLogin")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "1_1_1_1_2", firefoxBrowser);
		//click button task management
		capture(pages.getUrlSaveImage() + "2_1_2_1", firefoxBrowser);
		driverFirefox.findElement(By.id("btnTaskManagement")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "3_1_2_1", firefoxBrowser);
		//click button trace
		driverFirefox.findElement(By.id("btnTrace")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "4_1_2_1", firefoxBrowser);
		//click button task process
		driverFirefox.findElement(By.id("btnTaskProcess")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "5_1_2_1", firefoxBrowser);
		//click button shipping number
		driverFirefox.findElement(By.id("btnShippingScreen")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "6_1_2_1", firefoxBrowser);
		//click drop down change password
	    action.moveToElement(driverFirefox.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverFirefox.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(2000);
	    driverFirefox.findElement(By.linkText(pages.getChangePassword())).click();
		Thread.sleep(1000);
		//click drop down show guide
	    action.moveToElement(driverFirefox.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverFirefox.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(2000);
	    driverFirefox.findElement(By.linkText(pages.getShowGuide())).click();
		Thread.sleep(1000);
		//click drop down logout
	    action.moveToElement(driverFirefox.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverFirefox.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(2000);
	    driverFirefox.findElement(By.linkText(pages.getLinkTextLogout())).click();
		//message logout
	    assertEquals( pages.getMessageLogout(), driverFirefox.findElement(By.id("popup_message")).getText());
	    driverFirefox.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverFirefox.findElement(By.id("popup_ok")).click();
		
		//login with role id = 3
	    driverFirefox.get(baseUrl + "/ict/?language=" + pages.getLang());
	    driverFirefox.findElement(By.id("j_username")).clear();
	    driverFirefox.findElement(By.id("j_username")).sendKeys("U0000000003");
	    driverFirefox.findElement(By.id("j_password")).clear();
	    driverFirefox.findElement(By.id("j_password")).sendKeys("U0000000003");
	    driverFirefox.findElement(By.id("btnLogin")).click();
		Thread.sleep(1000);
		//click button task management
		driverFirefox.findElement(By.id("btnTaskManagement")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "3_1_3_1", firefoxBrowser);
		//click button trace
		driverFirefox.findElement(By.id("btnTrace")).click();
		Thread.sleep(1000);
		//click button task process
		driverFirefox.findElement(By.id("btnTaskProcess")).click();
		Thread.sleep(1000);
		//click button shipping number
		driverFirefox.findElement(By.id("btnShippingScreen")).click();
		Thread.sleep(1000);
		//click drop down change password
	    action.moveToElement(driverFirefox.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverFirefox.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(2000);
	    driverFirefox.findElement(By.linkText(pages.getChangePassword())).click();
		Thread.sleep(1000);
		//click drop down show guide
	    action.moveToElement(driverFirefox.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverFirefox.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(2000);
	    driverFirefox.findElement(By.linkText(pages.getShowGuide())).click();
		Thread.sleep(1000);
		//click drop down logout
	    action.moveToElement(driverFirefox.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverFirefox.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(2000);
	    driverFirefox.findElement(By.linkText(pages.getLinkTextLogout())).click();
		//message logout
	    assertEquals( pages.getMessageLogout(), driverFirefox.findElement(By.id("popup_message")).getText());
	    driverFirefox.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverFirefox.findElement(By.id("popup_ok")).click();
		
		//login with role id = 4
	    driverFirefox.get(baseUrl + "/ict/?language=" + pages.getLang());
	    driverFirefox.findElement(By.id("j_username")).clear();
	    driverFirefox.findElement(By.id("j_username")).sendKeys("U0000000005");
	    driverFirefox.findElement(By.id("j_password")).clear();
	    driverFirefox.findElement(By.id("j_password")).sendKeys("U0000000005");
	    driverFirefox.findElement(By.id("btnLogin")).click();
		Thread.sleep(1000);
		//click button task management
		driverFirefox.findElement(By.id("btnTaskManagement")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "3_1_4_1", firefoxBrowser);
		//click button trace
		driverFirefox.findElement(By.id("btnTrace")).click();
		Thread.sleep(1000);
		//click button task process
		driverFirefox.findElement(By.id("btnTaskProcess")).click();
		Thread.sleep(1000);
		//click button shipping number
		driverFirefox.findElement(By.id("btnShippingScreen")).click();
		Thread.sleep(1000);
		//click drop down change password
	    action.moveToElement(driverFirefox.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverFirefox.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(2000);
	    driverFirefox.findElement(By.linkText(pages.getChangePassword())).click();
		Thread.sleep(1000);
		//click drop down show guide
	    action.moveToElement(driverFirefox.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverFirefox.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(2000);
	    driverFirefox.findElement(By.linkText(pages.getShowGuide())).click();
		Thread.sleep(1000);
		//click drop down logout
	    action.moveToElement(driverFirefox.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverFirefox.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(2000);
	    driverFirefox.findElement(By.linkText(pages.getLinkTextLogout())).click();
		//message logout
	    assertEquals( pages.getMessageLogout(), driverFirefox.findElement(By.id("popup_message")).getText());
	    driverFirefox.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverFirefox.findElement(By.id("popup_ok")).click();
	}
	*/
	
	/**
	 * Test selenium in Chrome
	 * @throws Exception
	 */
	/*
	@Test
	public void testHeaderChrome() throws Exception{
		String chromeBrowser = "chrome";
		setMessage("vi", chromeBrowser);
		
		//language page
		driverChrome.get(baseUrl + "/ict/?language=" + pages.getLang());
		
		//login with role id = 1
		driverChrome.findElement(By.id("j_username")).clear();
		driverChrome.findElement(By.id("j_username")).sendKeys("U0000000001");
		driverChrome.findElement(By.id("j_password")).clear();
		driverChrome.findElement(By.id("j_password")).sendKeys("U0000000001");
		driverChrome.findElement(By.id("btnLogin")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "1_1_1_1_1", chromeBrowser);
		//click button general setting
		driverChrome.findElement(By.id("btnGeneralSetting")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "2_1_1_1", chromeBrowser);
		Thread.sleep(500);
		capture(pages.getUrlSaveImage() + "3_1_1_1", chromeBrowser);
		Thread.sleep(500);
		capture(pages.getUrlSaveImage() + "4_1_1_1", chromeBrowser);
		Thread.sleep(500);
		capture(pages.getUrlSaveImage() + "5_1_1_1", chromeBrowser);
		Thread.sleep(500);
		capture(pages.getUrlSaveImage() + "6_1_1_1", chromeBrowser);
		//change url in screen 0037
		driverChrome.get(baseUrl + "/ict/0037/?language=" + pages.getLang());
		Thread.sleep(1000);
		//click button task management
		driverChrome.findElement(By.id("btnTaskManagement")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "3_1_1_2", chromeBrowser);
		//click button trace
		driverChrome.findElement(By.id("btnTrace")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "4_1_1_2", chromeBrowser);
		//click button task process
		driverChrome.findElement(By.id("btnTaskProcess")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "5_1_1_2", chromeBrowser);
		//click button shipping number
		driverChrome.findElement(By.id("btnShippingScreen")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "6_1_1_2", chromeBrowser);
		//click drop down change password
		Actions action = new Actions(driverChrome);
	    action.moveToElement(driverChrome.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverChrome.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverChrome.findElement(By.linkText(pages.getChangePassword())).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "7_1_1_1", chromeBrowser);
		//click drop down show guide
	    action.moveToElement(driverChrome.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverChrome.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverChrome.findElement(By.linkText(pages.getShowGuide())).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "8_1_1_1", chromeBrowser);
		//click drop down logout
	    action.moveToElement(driverChrome.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverChrome.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverChrome.findElement(By.linkText(pages.getLinkTextLogout())).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "9_1_1_1", chromeBrowser);
		//message logout
	    assertEquals( pages.getMessageLogout(), driverChrome.findElement(By.id("popup_message")).getText());
	    driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverChrome.findElement(By.id("popup_ok")).click();
		
		// login with role id = 2
	    driverChrome.get(baseUrl + "/ict/?language=" + pages.getLang());
	    driverChrome.findElement(By.id("j_username")).clear();
	    driverChrome.findElement(By.id("j_username")).sendKeys("U0000000002");
	    driverChrome.findElement(By.id("j_password")).clear();
	    driverChrome.findElement(By.id("j_password")).sendKeys("U0000000002");
	    driverChrome.findElement(By.id("btnLogin")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "1_1_1_1_2", chromeBrowser);
		//click button task management
		capture(pages.getUrlSaveImage() + "2_1_2_1", chromeBrowser);
		driverChrome.findElement(By.id("btnTaskManagement")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "3_1_2_1", chromeBrowser);
		//click button trace
		driverChrome.findElement(By.id("btnTrace")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "4_1_2_1", chromeBrowser);
		//click button task process
		driverChrome.findElement(By.id("btnTaskProcess")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "5_1_2_1", chromeBrowser);
		//click button shipping number
		driverChrome.findElement(By.id("btnShippingScreen")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "6_1_2_1", chromeBrowser);
		//click drop down change password
	    action.moveToElement(driverChrome.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverChrome.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverChrome.findElement(By.linkText(pages.getChangePassword())).click();
		Thread.sleep(1000);
		//click drop down show guide
	    action.moveToElement(driverChrome.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverChrome.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverChrome.findElement(By.linkText(pages.getShowGuide())).click();
		Thread.sleep(1000);
		//click drop down logout
	    action.moveToElement(driverChrome.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverChrome.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverChrome.findElement(By.linkText(pages.getLinkTextLogout())).click();
		//message logout
	    assertEquals( pages.getMessageLogout(), driverChrome.findElement(By.id("popup_message")).getText());
	    driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverChrome.findElement(By.id("popup_ok")).click();
		
		//login with role id = 3
	    driverChrome.get(baseUrl + "/ict/?language=" + pages.getLang());
	    driverChrome.findElement(By.id("j_username")).clear();
	    driverChrome.findElement(By.id("j_username")).sendKeys("U0000000003");
	    driverChrome.findElement(By.id("j_password")).clear();
	    driverChrome.findElement(By.id("j_password")).sendKeys("U0000000003");
	    driverChrome.findElement(By.id("btnLogin")).click();
		Thread.sleep(1000);
		//click button task management
		driverChrome.findElement(By.id("btnTaskManagement")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "3_1_3_1", chromeBrowser);
		//click button trace
		driverChrome.findElement(By.id("btnTrace")).click();
		Thread.sleep(1000);
		//click button task process
		driverChrome.findElement(By.id("btnTaskProcess")).click();
		Thread.sleep(1000);
		//click button shipping number
		driverChrome.findElement(By.id("btnShippingScreen")).click();
		Thread.sleep(1000);
		//click drop down change password
	    action.moveToElement(driverChrome.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverChrome.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverChrome.findElement(By.linkText(pages.getChangePassword())).click();
		Thread.sleep(1000);
		//click drop down show guide
	    action.moveToElement(driverChrome.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverChrome.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverChrome.findElement(By.linkText(pages.getShowGuide())).click();
		Thread.sleep(1000);
		//click drop down logout
	    action.moveToElement(driverChrome.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverChrome.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverChrome.findElement(By.linkText(pages.getLinkTextLogout())).click();
		//message logout
	    assertEquals( pages.getMessageLogout(), driverChrome.findElement(By.id("popup_message")).getText());
	    driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverChrome.findElement(By.id("popup_ok")).click();
		
		//login with role id = 4
	    driverChrome.get(baseUrl + "/ict/?language=" + pages.getLang());
	    driverChrome.findElement(By.id("j_username")).clear();
	    driverChrome.findElement(By.id("j_username")).sendKeys("U0000000005");
	    driverChrome.findElement(By.id("j_password")).clear();
	    driverChrome.findElement(By.id("j_password")).sendKeys("U0000000005");
	    driverChrome.findElement(By.id("btnLogin")).click();
		Thread.sleep(1000);
		//click button task management
		driverChrome.findElement(By.id("btnTaskManagement")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "3_1_4_1", chromeBrowser);
		//click button trace
		driverChrome.findElement(By.id("btnTrace")).click();
		Thread.sleep(1000);
		//click button task process
		driverChrome.findElement(By.id("btnTaskProcess")).click();
		Thread.sleep(1000);
		//click button shipping number
		driverChrome.findElement(By.id("btnShippingScreen")).click();
		Thread.sleep(1000);
		//click drop down change password
	    action.moveToElement(driverChrome.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverChrome.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverChrome.findElement(By.linkText(pages.getChangePassword())).click();
		Thread.sleep(1000);
		//click drop down show guide
	    action.moveToElement(driverChrome.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverChrome.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverChrome.findElement(By.linkText(pages.getShowGuide())).click();
		Thread.sleep(1000);
		//click drop down logout
	    action.moveToElement(driverChrome.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverChrome.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverChrome.findElement(By.linkText(pages.getLinkTextLogout())).click();
		//message logout
	    assertEquals( pages.getMessageLogout(), driverChrome.findElement(By.id("popup_message")).getText());
	    driverChrome.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverChrome.findElement(By.id("popup_ok")).click();
	}
	*/
	
	/**
	 * Test selenium in Chrome
	 * @throws Exception
	 */
	
	@Test
	public void testHeaderEdge() throws Exception{
		String chromeBrowser = "edge";
		setMessage("vi", chromeBrowser);
		
		//language page
		driverEdge.get(baseUrl + "/ict/?language=" + pages.getLang());
		
		//login with role id = 1
		driverEdge.findElement(By.id("j_username")).clear();
		driverEdge.findElement(By.id("j_username")).sendKeys("U0000000001");
		driverEdge.findElement(By.id("j_password")).clear();
		driverEdge.findElement(By.id("j_password")).sendKeys("U0000000001");
		driverEdge.findElement(By.id("btnLogin")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "1_1_1_1_1", chromeBrowser);
		//click button general setting
		driverEdge.findElement(By.id("btnGeneralSetting")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "2_1_1_1", chromeBrowser);
		Thread.sleep(500);
		capture(pages.getUrlSaveImage() + "3_1_1_1", chromeBrowser);
		Thread.sleep(500);
		capture(pages.getUrlSaveImage() + "4_1_1_1", chromeBrowser);
		Thread.sleep(500);
		capture(pages.getUrlSaveImage() + "5_1_1_1", chromeBrowser);
		Thread.sleep(500);
		capture(pages.getUrlSaveImage() + "6_1_1_1", chromeBrowser);
		//change url in screen 0037
		driverEdge.get(baseUrl + "/ict/0037/?language=" + pages.getLang());
		Thread.sleep(1000);
		//click button task management
		driverEdge.findElement(By.id("btnTaskManagement")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "3_1_1_2", chromeBrowser);
		//click button trace
		driverEdge.findElement(By.id("btnTrace")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "4_1_1_2", chromeBrowser);
		//click button task process
		driverEdge.findElement(By.id("btnTaskProcess")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "5_1_1_2", chromeBrowser);
		//click button shipping number
		driverEdge.findElement(By.id("btnShippingScreen")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "6_1_1_2", chromeBrowser);
		//click drop down change password
		Actions action = new Actions(driverEdge);
	    action.moveToElement(driverEdge.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverEdge.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverEdge.findElement(By.linkText(pages.getChangePassword())).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "7_1_1_1", chromeBrowser);
		//click drop down show guide
	    action.moveToElement(driverEdge.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverEdge.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverEdge.findElement(By.linkText(pages.getShowGuide())).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "8_1_1_1", chromeBrowser);
		//click drop down logout
	    action.moveToElement(driverEdge.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverEdge.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverEdge.findElement(By.linkText(pages.getLinkTextLogout())).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "9_1_1_1", chromeBrowser);
		//message logout
	    assertEquals( pages.getMessageLogout(), driverEdge.findElement(By.id("popup_message")).getText());
	    driverEdge.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverEdge.findElement(By.id("popup_ok")).click();
		
		// login with role id = 2
	    driverEdge.get(baseUrl + "/ict/?language=" + pages.getLang());
	    driverEdge.findElement(By.id("j_username")).clear();
	    driverEdge.findElement(By.id("j_username")).sendKeys("U0000000002");
	    driverEdge.findElement(By.id("j_password")).clear();
	    driverEdge.findElement(By.id("j_password")).sendKeys("U0000000002");
	    driverEdge.findElement(By.id("btnLogin")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "1_1_1_1_2", chromeBrowser);
		//click button task management
		capture(pages.getUrlSaveImage() + "2_1_2_1", chromeBrowser);
		driverEdge.findElement(By.id("btnTaskManagement")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "3_1_2_1", chromeBrowser);
		//click button trace
		driverEdge.findElement(By.id("btnTrace")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "4_1_2_1", chromeBrowser);
		//click button task process
		driverEdge.findElement(By.id("btnTaskProcess")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "5_1_2_1", chromeBrowser);
		//click button shipping number
		driverEdge.findElement(By.id("btnShippingScreen")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "6_1_2_1", chromeBrowser);
		//click drop down change password
	    action.moveToElement(driverEdge.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverEdge.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverEdge.findElement(By.linkText(pages.getChangePassword())).click();
		Thread.sleep(1000);
		//click drop down show guide
	    action.moveToElement(driverEdge.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverEdge.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverEdge.findElement(By.linkText(pages.getShowGuide())).click();
		Thread.sleep(1000);
		//click drop down logout
	    action.moveToElement(driverEdge.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverEdge.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverEdge.findElement(By.linkText(pages.getLinkTextLogout())).click();
		//message logout
	    assertEquals( pages.getMessageLogout(), driverEdge.findElement(By.id("popup_message")).getText());
	    driverEdge.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverEdge.findElement(By.id("popup_ok")).click();
		
		//login with role id = 3
	    driverEdge.get(baseUrl + "/ict/?language=" + pages.getLang());
	    driverEdge.findElement(By.id("j_username")).clear();
	    driverEdge.findElement(By.id("j_username")).sendKeys("U0000000003");
	    driverEdge.findElement(By.id("j_password")).clear();
	    driverEdge.findElement(By.id("j_password")).sendKeys("U0000000003");
	    driverEdge.findElement(By.id("btnLogin")).click();
		Thread.sleep(1000);
		//click button task management
		driverEdge.findElement(By.id("btnTaskManagement")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "3_1_3_1", chromeBrowser);
		//click button trace
		driverEdge.findElement(By.id("btnTrace")).click();
		Thread.sleep(1000);
		//click button task process
		driverEdge.findElement(By.id("btnTaskProcess")).click();
		Thread.sleep(1000);
		//click button shipping number
		driverEdge.findElement(By.id("btnShippingScreen")).click();
		Thread.sleep(1000);
		//click drop down change password
	    action.moveToElement(driverEdge.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverEdge.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverEdge.findElement(By.linkText(pages.getChangePassword())).click();
		Thread.sleep(1000);
		//click drop down show guide
	    action.moveToElement(driverEdge.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverEdge.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverEdge.findElement(By.linkText(pages.getShowGuide())).click();
		Thread.sleep(1000);
		//click drop down logout
	    action.moveToElement(driverEdge.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverEdge.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverEdge.findElement(By.linkText(pages.getLinkTextLogout())).click();
		//message logout
	    assertEquals( pages.getMessageLogout(), driverEdge.findElement(By.id("popup_message")).getText());
	    driverEdge.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverEdge.findElement(By.id("popup_ok")).click();
		
		//login with role id = 4
	    driverEdge.get(baseUrl + "/ict/?language=" + pages.getLang());
	    driverEdge.findElement(By.id("j_username")).clear();
	    driverEdge.findElement(By.id("j_username")).sendKeys("U0000000005");
	    driverEdge.findElement(By.id("j_password")).clear();
	    driverEdge.findElement(By.id("j_password")).sendKeys("U0000000005");
	    driverEdge.findElement(By.id("btnLogin")).click();
		Thread.sleep(1000);
		//click button task management
		driverEdge.findElement(By.id("btnTaskManagement")).click();
		Thread.sleep(1000);
		capture(pages.getUrlSaveImage() + "3_1_4_1", chromeBrowser);
		//click button trace
		driverEdge.findElement(By.id("btnTrace")).click();
		Thread.sleep(1000);
		//click button task process
		driverEdge.findElement(By.id("btnTaskProcess")).click();
		Thread.sleep(1000);
		//click button shipping number
		driverEdge.findElement(By.id("btnShippingScreen")).click();
		Thread.sleep(1000);
		//click drop down change password
	    action.moveToElement(driverEdge.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverEdge.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverEdge.findElement(By.linkText(pages.getChangePassword())).click();
		Thread.sleep(1000);
		//click drop down show guide
	    action.moveToElement(driverEdge.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverEdge.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverEdge.findElement(By.linkText(pages.getShowGuide())).click();
		Thread.sleep(1000);
		//click drop down logout
	    action.moveToElement(driverEdge.findElement(By.cssSelector("div.drop-menu"))
	    		.findElement(By.cssSelector("ul"))
	    		.findElement(By.cssSelector("li"))
	    		.findElement(By.cssSelector("a")))
	    		.moveToElement(driverEdge.findElement(By.cssSelector("ul"))
	    				.findElement(By.cssSelector("li"))
	    				.findElement(By.cssSelector("a"))).click().build().perform();
	    Thread.sleep(5000);
	    driverEdge.findElement(By.linkText(pages.getLinkTextLogout())).click();
		//message logout
	    assertEquals( pages.getMessageLogout(), driverEdge.findElement(By.id("popup_message")).getText());
	    driverEdge.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    driverEdge.findElement(By.id("popup_ok")).click();
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
			} else if(browser =="edge"){
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

	// set all text in header
	public void setMessage(String lang, String browser){
		if(lang == "jp"){
			pages.setLang(lang);
			//Url save image
			if(browser =="ie"){
				// url save image in browser internet explorer
				pages.setUrlSaveImage("selenium_test_images/header/IE/jp/");
			} else if(browser == "firefox"){
				// url save image in browser firefox
				pages.setUrlSaveImage("selenium_test_images/header/FIREFOX/jp/");
			} else if (browser == "chrome"){
				// url save image in browser chrome
				pages.setUrlSaveImage("selenium_test_images/header/CHROME/jp/");
			}  else if(browser =="edge"){
				pages.setUrlSaveImage("");
			}
			//message logout follow language
			pages.setMessageLogout("繝ｭ繧ｰ繧｢繧ｦ繝医＠縺ｾ縺吶�ゅｈ繧阪＠縺�縺ｧ縺吶°�ｼ�");
			// data no exit
			pages.setNoDataExit("繝�繝ｼ繧ｿ縺後≠繧翫∪縺帙ｓ縲�");
			//link text change password
			pages.setChangePassword("繝代せ繝ｯ繝ｼ繝牙､画峩");
			//link text show guide
			pages.setShowGuide("繧ｬ繧､繝芽｡ｨ遉ｺ");
			//link text log out
			pages.setLinkTextLogout("繝ｭ繧ｰ繧｢繧ｦ繝�");
		}else if(lang == "en"){
			pages.setLang(lang);
			//Url save image
			if(browser =="ie"){
				// url save image in browser internet explorer
				pages.setUrlSaveImage("selenium_test_images/header/IE/en/");
			} else if(browser == "firefox"){
				// url save image in browser firefox
				pages.setUrlSaveImage("selenium_test_images/header/FIREFOX/en/");
			} else if (browser == "chrome"){
				// url save image in browser chrome
				pages.setUrlSaveImage("selenium_test_images/header/CHROME/en/");
			} else if(browser =="edge"){
				pages.setUrlSaveImage("");
			}
			// message logout follow language
			pages.setMessageLogout("Are you sure you want to logout?");
			// data no exit
			pages.setNoDataExit("Data do not exist!");
			//link text change password
			pages.setChangePassword("Change password");
			//link text show guide
			pages.setShowGuide("Show guide");
			//link text log out
			pages.setLinkTextLogout("Log out");
		}else if (lang == "vi"){
			pages.setLang(lang);
			//Url save image
			if(browser =="ie"){
				// url save image in browser internet explorer
				pages.setUrlSaveImage("selenium_test_images/header/IE/vn/");
			} else if(browser == "firefox"){
				// url save image in browser firefox
				pages.setUrlSaveImage("selenium_test_images/header/FIREFOX/vn/");
			} else if (browser == "chrome"){
				// url save image in browser chrome
				pages.setUrlSaveImage("selenium_test_images/header/CHROME/vn/");
			} else if(browser =="edge"){
				pages.setUrlSaveImage("");
			}
			// message logout follow language
			pages.setMessageLogout("B蘯｡n cﾃｳ mu盻創 ﾄ惰ハg xu蘯･t khﾃｴng?");
			// data no exit
			pages.setNoDataExit("Khﾃｴng t盻渡 t蘯｡i thﾃｴng tin nﾃ�y!");
			//link text change password
			pages.setChangePassword("ﾄ雪ｻ品 m蘯ｭt kh蘯ｩu");
			//link text show guide
			pages.setShowGuide("Hi盻㌻ hﾆｰ盻嬾g d蘯ｫn");
			//link text log out
			pages.setLinkTextLogout("ﾄ斉ハg xu蘯･t");
		}
	}
	  
	  public class messageAlertHeader{
		  
		public String lang;
		public String urlSaveImage;
		public String messageLogout;
		public String linkTextLogout;
		public String noDataExit;
		public String changePassword;
		public String showGuide;

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

		public String getChangePassword() {
			return changePassword;
		}

		public void setChangePassword(String changePassword) {
			this.changePassword = changePassword;
		}

		public String getShowGuide() {
			return showGuide;
		}

		public void setShowGuide(String showGuide) {
			this.showGuide = showGuide;
		}
		
	  }
}
