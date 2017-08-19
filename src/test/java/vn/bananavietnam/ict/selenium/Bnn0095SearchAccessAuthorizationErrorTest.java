package vn.bananavietnam.ict.selenium;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
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
public class Bnn0095SearchAccessAuthorizationErrorTest {
	private static WebDriver driver;
	private String baseUrl;

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "U0000000001", "U0000000001" } });
	}

	@Parameter // first data value (0) is default
	public String fName;
	public String imagePath = "selenium_test_images/0095/Error/";
	@Parameter(1)
	public String fPass;
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
		checkPointArray = new ArrayList<Long>();
		logFileNameArray = new ArrayList<String>();
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
			
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			if (language == "vi") {
				driver.get(baseUrl + "/ict/?language=vi");
			} else if (language == "en") {
				driver.get(baseUrl + "/ict/?language=en");
			} else if (language == "jp") {
				driver.get(baseUrl + "/ict/?language=jp");
			}
			// input user name and password
			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("U0000000001");
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("U0000000001");	 
		    driver.findElement(By.id("btnLogin")).click();
		    // go to 0095 display
		    Thread.sleep(3000);
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			Thread.sleep(3000);	
		    Thread.sleep(500);
		    driver.get(baseUrl + "/ict/0095/");
		    Thread.sleep(3000);
			// start client connection ----------------------------
			SeleniumCommon.enableClientConnection();
			Thread.sleep(3000);
			driver.get(baseUrl + "/ict/0095/");
		    Thread.sleep(1000);
		    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath  + browser + "/" + language + "/0095_1_1_1_1.log");
	   		driver.findElement(By.id("popup_ok")).click();
	   		Thread.sleep(500);
	   		capture(imagePath  + browser + "/" + language + "/0095_1_1_1_1");
		    Thread.sleep(1000);
	   		driver.findElement(By.id("txtAccessAuthorityId")).clear();
	   	    driver.findElement(By.id("txtAccessAuthorityId")).sendKeys("1");
	   	    driver.findElement(By.id("txtScreenId")).clear();
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath  + browser + "/" + language + "/0095_1_1_1_1_2.log");
	   	    driver.findElement(By.id("txtScreenId")).sendKeys("1111");
	   	    Thread.sleep(500);
	   	    capture(imagePath  + browser + "/" + language + "/0095_1_1_1_1_2");
	   	    Thread.sleep(1000);
	   	    driver.findElement(By.id("txtAccessAuthorityId")).clear();
		    driver.findElement(By.id("txtAccessAuthorityId")).sendKeys("");
		    driver.findElement(By.id("txtScreenId")).clear();
		    driver.findElement(By.id("txtScreenId")).sendKeys("");
		    
		    if (language == "jp") {
		    	driver.findElement(By.id("cbbScreenDisplayEnableFlag")).click();
		    	Thread.sleep(500);
		    	new Select(driver.findElement(By.id("cbbScreenDisplayEnableFlag"))).selectByVisibleText("無効");
		    	driver.findElement(By.id("cbbAddableFlag")).click();
			    Thread.sleep(500);
			    new Select(driver.findElement(By.id("cbbAddableFlag"))).selectByVisibleText("無効");
			    driver.findElement(By.id("cbbUpdatableFlag")).click();
			    Thread.sleep(500);
			    new Select(driver.findElement(By.id("cbbUpdatableFlag"))).selectByVisibleText("無効");
			    driver.findElement(By.id("cbbDeleteableFlag")).click();
			    Thread.sleep(500);
			    new Select(driver.findElement(By.id("cbbDeleteableFlag"))).selectByVisibleText("無効");
			    driver.findElement(By.id("cbbReferenceableFlag")).click();
			    Thread.sleep(500);
			    new Select(driver.findElement(By.id("cbbReferenceableFlag"))).selectByVisibleText("無効");
		    } else if (language == "en") {
		    	driver.findElement(By.id("cbbScreenDisplayEnableFlag")).click();
		    	Thread.sleep(500);
		    	new Select(driver.findElement(By.id("cbbScreenDisplayEnableFlag"))).selectByVisibleText("Disabled");
		    	driver.findElement(By.id("cbbAddableFlag")).click();
			    Thread.sleep(500);
			    new Select(driver.findElement(By.id("cbbAddableFlag"))).selectByVisibleText("Disabled");
			    driver.findElement(By.id("cbbUpdatableFlag")).click();
			    Thread.sleep(500);
			    new Select(driver.findElement(By.id("cbbUpdatableFlag"))).selectByVisibleText("Disabled");
			    driver.findElement(By.id("cbbDeleteableFlag")).click();
			    Thread.sleep(500);
			    new Select(driver.findElement(By.id("cbbDeleteableFlag"))).selectByVisibleText("Disabled");
			    driver.findElement(By.id("cbbReferenceableFlag")).click();
			    Thread.sleep(500);
			    new Select(driver.findElement(By.id("cbbReferenceableFlag"))).selectByVisibleText("Disabled");
		    } else if (language == "vi") {
		    	Thread.sleep(2000);
		    	new Select(driver.findElement(By.id("cbbScreenDisplayEnableFlag"))).selectByVisibleText("Vô hiệu");
			    Thread.sleep(2000);
			    new Select(driver.findElement(By.id("cbbAddableFlag"))).selectByVisibleText("Vô hiệu");
			    Thread.sleep(2000);
			    new Select(driver.findElement(By.id("cbbUpdatableFlag"))).selectByVisibleText("Vô hiệu");
			    Thread.sleep(2000);
			    new Select(driver.findElement(By.id("cbbDeleteableFlag"))).selectByVisibleText("Vô hiệu");
			    Thread.sleep(2000);
			    new Select(driver.findElement(By.id("cbbReferenceableFlag"))).selectByVisibleText("Vô hiệu");
		    }
		    
		    
			
			Thread.sleep(3000);
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			Thread.sleep(3000);	
		    driver.findElement(By.id("btnSearch")).click();
		    driver.findElement(By.id("popup_ok")).click();
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + browser + "/" + language + "/0095_2_1_1_1.log");
			capture(imagePath  + browser + "/" + language + "/0095_2_1_1_1");
	   		Thread.sleep(500);
	   		Thread.sleep(3000);
			// start client connection ----------------------------
			SeleniumCommon.enableClientConnection();
			Thread.sleep(3000);
			driver.findElement(By.id("btnSearch")).click();
		    Thread.sleep(1000);
		    
		    // check button >, >>, <, <<, Go
		    Thread.sleep(3000);
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			Thread.sleep(3000);	
		    Thread.sleep(500);
		    driver.findElement(By.id("btnNext")).click();
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath + browser + "/" + language + "/0095_7_1_1_1.log");
			Thread.sleep(500);
		    capture(imagePath  + browser + "/" + language + "/0095_7_1_1_1");
		    driver.findElement(By.id("popup_ok")).click();
		    Thread.sleep(3000);
			// start client connection ----------------------------
			SeleniumCommon.enableClientConnection();
			Thread.sleep(3000);
		    
		    Thread.sleep(500);
		    driver.findElement(By.id("btnNext")).click();
		    Thread.sleep(500);
			Thread.sleep(3000);
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			Thread.sleep(3000);	
		    driver.findElement(By.id("btnLast")).click();
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath  + browser + "/" + language + "/0095_8_1_1_1.log");
		    Thread.sleep(500);
		    capture(imagePath  + browser + "/" + language + "/0095_8_1_1_1");
		    driver.findElement(By.id("popup_ok")).click();
		    Thread.sleep(500);
		    Thread.sleep(3000);
			// start client connection ----------------------------
			SeleniumCommon.enableClientConnection();
			Thread.sleep(3000);
			driver.findElement(By.id("btnLast")).click();
			
			Thread.sleep(3000);
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			Thread.sleep(3000);	
		    driver.findElement(By.id("btnPrevious")).click();
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath  + browser + "/" + language + "/0095_6_1_1_1.log");
		    Thread.sleep(500);
		    capture(imagePath  + browser + "/" + language + "/0095_6_1_1_1");
		    driver.findElement(By.id("popup_ok")).click();
		    Thread.sleep(3000);
			// start client connection ----------------------------
			SeleniumCommon.enableClientConnection();
			Thread.sleep(3000);
		    driver.findElement(By.id("btnPrevious")).click();
		    
		    Thread.sleep(3000);
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			Thread.sleep(3000);	
		    driver.findElement(By.id("btnFirst")).click();
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath  + browser + "/" + language + "/0095_5_1_1_1.log");
		    Thread.sleep(500);
		    capture(imagePath  + browser + "/" + language + "/0095_5_1_1_1");
		    Thread.sleep(500);
		    driver.findElement(By.id("popup_ok")).click();
		    Thread.sleep(3000);
			// start client connection ----------------------------
			SeleniumCommon.enableClientConnection();
			Thread.sleep(3000);
			driver.findElement(By.id("btnFirst")).click();
			
		    driver.findElement(By.id("txtGoToPage")).clear();
		    
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath  + browser + "/" + language + "/0095_9_3_1_1.log");
		    driver.findElement(By.id("txtGoToPage")).sendKeys("2");
		    Thread.sleep(500);
		    capture(imagePath  + browser + "/" + language + "/0095_9_3_1_1");
			Thread.sleep(3000);
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			Thread.sleep(3000);	
		    driver.findElement(By.id("btnGoToPage")).click();

			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath  + browser + "/" + language + "/0095_10_1_1_1.log");
		    Thread.sleep(500);
		    capture(imagePath  + browser + "/" + language + "/0095_10_1_1_1");
		    driver.findElement(By.id("popup_ok")).click();
		    Thread.sleep(3000);
			// start client connection ----------------------------
			SeleniumCommon.enableClientConnection();
			Thread.sleep(3000);
			
		    Thread.sleep(500);
		    driver.findElement(By.id("txtGoToPage")).clear();
		    driver.findElement(By.id("txtGoToPage")).sendKeys("3");
		    Thread.sleep(500);
		    driver.findElement(By.id("btnGoToPage")).click();
		    Thread.sleep(500);
		    driver.findElement(By.id("txtGoToPage")).clear();
		    driver.findElement(By.id("txtGoToPage")).sendKeys("1");
		    Thread.sleep(500);
		    driver.findElement(By.id("btnGoToPage")).click();
		    Thread.sleep(500);
		    
		    // search Data
		    Thread.sleep(1000);
		    driver.findElement(By.id("txtAccessAuthorityId")).clear();
		    driver.findElement(By.id("txtAccessAuthorityId")).sendKeys("3");
		    driver.findElement(By.id("txtScreenId")).clear();
		    driver.findElement(By.id("txtScreenId")).sendKeys("0104");
		    Thread.sleep(1000);
		    // pic
		    driver.findElement(By.id("btnSearch")).click();
		    Thread.sleep(1000);
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath  + browser + "/" + language + "/0095_2_1_2_1.log");
		    assertEquals("1", driver.findElement(By.id("txtCounts")).getText());
	   		Thread.sleep(1000);
		    capture(imagePath  + browser + "/" + language + "/0095_2_1_2_1");
	
		    Thread.sleep(1000); 
		    driver.findElement(By.id("txtAccessAuthorityId")).clear();
		    driver.findElement(By.id("txtAccessAuthorityId")).sendKeys("3");
		    driver.findElement(By.id("txtScreenId")).clear();
		    driver.findElement(By.id("txtScreenId")).sendKeys("0007");
		    Thread.sleep(500);
		    Thread.sleep(3000);
			// stop client connection ----------------------------
			SeleniumCommon.disableClientConnection();
			Thread.sleep(3000);	
		    // pic
		    driver.findElement(By.id("btnSearch")).click();
		    driver.findElement(By.id("popup_ok")).click();
		    
		    if(language == "vi") {
		    	assertEquals("Không tồn tại thông tin này!", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "en") {
		    	assertEquals("Data do not exist!", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "jp") {
		    	assertEquals("データがありません。", driver.findElement(By.id("popup_message")).getText());
		    }
	 	    //check result record is 0 ?
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath  + browser + "/" + language + "/0095_2_1_3_1.log");
	   		assertEquals("0", driver.findElement(By.id("txtCounts")).getText());
	   		Thread.sleep(1000);
		    capture(imagePath  + browser + "/" + language + "/0095_2_1_3_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		    driver.findElement(By.id("popup_ok")).click();
		    
	   		// click button register
		    // register successfully
		    Thread.sleep(1000);
	   	    driver.findElement(By.id("txtAccessAuthorityId")).clear();
		    driver.findElement(By.id("txtAccessAuthorityId")).sendKeys("");
		    driver.findElement(By.id("txtScreenId")).clear();
		    driver.findElement(By.id("txtScreenId")).sendKeys("");
		    Thread.sleep(500);
		    driver.findElement(By.id("btnSearch")).click();
	   		Thread.sleep(500);
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath  + browser + "/" + language + "/0095_11_1_1_1.log");
	   		driver.findElement(By.id("btnRegister")).click();
	   		Thread.sleep(500); 
	   		capture(imagePath  + browser + "/" + language + "/0095_11_1_1_1");
	   		Thread.sleep(1000); 
	   		// random register one record
	   		String screenName = "0";
	   		int randomNum = 0;	   		 
	   		if (browser == "IE") {
	   			if(language == "vi") {
	   				randomNum = ThreadLocalRandom.current().nextInt(400, 450);
			    }else if(language == "en") {
			    	randomNum = ThreadLocalRandom.current().nextInt(451, 500);
			    }else if(language == "jp") {
			    	randomNum = ThreadLocalRandom.current().nextInt(501, 550);
			    }			 
			} else if (browser == "Chrome") {
				if(language == "vi") {
					randomNum = ThreadLocalRandom.current().nextInt(551, 600);
			    }else if(language == "en") {
			    	randomNum = ThreadLocalRandom.current().nextInt(601, 650);
			    }else if(language == "jp") {
			    	randomNum = ThreadLocalRandom.current().nextInt(651, 700);
			    }
			} else if (browser == "FireFox") {
				if(language == "vi") {
					randomNum = ThreadLocalRandom.current().nextInt(701, 730);
			    }else if(language == "en") {
			    	randomNum = ThreadLocalRandom.current().nextInt(731, 760);
			    }else if(language == "jp") {
			    	randomNum = ThreadLocalRandom.current().nextInt(761, 790);
			    }
			} else {
				if(language == "vi") {
	   				randomNum = ThreadLocalRandom.current().nextInt(791, 820);
			    }else if(language == "en") {
			    	randomNum = ThreadLocalRandom.current().nextInt(821, 850);
			    }else if(language == "jp") {
			    	randomNum = ThreadLocalRandom.current().nextInt(851, 880);
			    }
			}
	   		screenName = screenName + String.valueOf(randomNum);
	   		System.err.println(screenName);
	   		Thread.sleep(500);
	   		driver.findElement(By.id("chbScreenDisplayEnableFlagPopup")).click();
	   		Thread.sleep(500);
		    driver.findElement(By.id("chbDeletableFlagPopup")).click();
		    Thread.sleep(500);
		    driver.findElement(By.id("chbAddableFlagPopup")).click();
		    Thread.sleep(500);
		    driver.findElement(By.id("chbRefernceFlagPopup")).click();
		    Thread.sleep(500);
		    driver.findElement(By.id("chbUpdatableFlagPopup")).click();
		    Thread.sleep(500);
		    driver.findElement(By.id("btnRegisterPopup")).click();
		    Thread.sleep(1000);
		    if(language == "vi") {
		    	assertEquals("Vui lòng nhập đầy đủ thông tin cần thiết!", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "en") {
		    	assertEquals("Please input all necessary information!", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "jp") {
		    	assertEquals("必要な情報を入力してください。", driver.findElement(By.id("popup_message")).getText());
		    }
		    Thread.sleep(1000);
		    capture(imagePath  + browser + "/" + language + "/0095_11_3_2_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		Thread.sleep(500);
		    driver.findElement(By.id("popup_ok")).click();
		    Thread.sleep(1000);
		    driver.findElement(By.id("txtAccessAuthorityIdPopup")).clear();
		    driver.findElement(By.id("txtAccessAuthorityIdPopup")).sendKeys("a");
		    driver.findElement(By.id("txtScreenIdPopup")).clear();
		    driver.findElement(By.id("txtScreenIdPopup")).sendKeys("9999");
		    Thread.sleep(500);
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath  + browser + "/" + language + "/0095_11_3_3_1.log");
		    driver.findElement(By.id("btnRegisterPopup")).click();
		    Thread.sleep(1000);
	   		if(language == "vi") {
		    	assertEquals("Vui lòng nhập đầy đủ thông tin cần thiết!", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "en") {
		    	assertEquals("Please input all necessary information!", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "jp") {
		    	assertEquals("必要な情報を入力してください。", driver.findElement(By.id("popup_message")).getText());
		    }
	   		Thread.sleep(1000);
	   		capture(imagePath  + browser + "/" + language + "/0095_11_3_3_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		Thread.sleep(500);
		    driver.findElement(By.id("popup_ok")).click();
		    
		    Thread.sleep(1000);
		    driver.findElement(By.id("txtAccessAuthorityIdPopup")).clear();
		    driver.findElement(By.id("txtAccessAuthorityIdPopup")).sendKeys("4");
		    driver.findElement(By.id("txtScreenIdPopup")).clear();
		    driver.findElement(By.id("txtScreenIdPopup")).sendKeys(screenName);
		    Thread.sleep(500);
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath  + browser + "/" + language + "/0095_11_3_1_1.log");
			// dump data to file
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + language + "/" +"DATA_BEFORE_INSERT", "IVB_M_ACCESS_AUTHORIZATION");
			
		    driver.findElement(By.id("btnRegisterPopup")).click();
		    Thread.sleep(1000); 
		    if(language == "vi") {
		    	assertEquals("Đăng ký thành công!", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "en") {
		    	assertEquals("Register successfully!", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "jp") {
		    	assertEquals("登録が成功しました。", driver.findElement(By.id("popup_message")).getText());
		    }

			// dump data to file
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + language + "/" +"DATA_AFTER_INSERT", "IVB_M_ACCESS_AUTHORIZATION");
		    Thread.sleep(1000);
	   		capture(imagePath  + browser + "/" + language + "/0095_11_3_1_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		Thread.sleep(500);
		    driver.findElement(By.id("popup_ok")).click();
	   		Thread.sleep(500);
	   		driver.findElement(By.id("txtGoToPage")).clear();
		    driver.findElement(By.id("txtGoToPage")).sendKeys("2");
		    Thread.sleep(500);
		    driver.findElement(By.id("btnGoToPage")).click();
		    Thread.sleep(1000);
		    
	   		//edit
	   		// blank on text field
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add("selenium_test_images/0049/success/" + browser + "/" + language + "/0095_3_1_1_1.log");
			driver.findElement(By.id("txtGoToPage")).clear();
		    driver.findElement(By.id("txtGoToPage")).sendKeys("3");
		    Thread.sleep(500);
		    driver.findElement(By.id("btnGoToPage")).click();
	   		driver.findElement(By.cssSelector("#row18 > td > .edit")).click();
	   		capture(imagePath  + browser + "/" + language + "/0095_3_1_1_1");
	   		Thread.sleep(2000);
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add("selenium_test_images/0049/success/" + browser + "/" + language + "/0095_3_4_1_1.log");
	   		driver.findElement(By.cssSelector("#btnCancelPopup")).click();
	   		Thread.sleep(500);
	   		capture(imagePath  + browser + "/" + language + "/0095_3_4_1_1");
	   		driver.findElement(By.id("txtGoToPage")).clear();
		    driver.findElement(By.id("txtGoToPage")).sendKeys("3");
		    Thread.sleep(500);
		    driver.findElement(By.id("btnGoToPage")).click();
	   		Thread.sleep(500);
	   		driver.findElement(By.cssSelector("#row18 > td > .edit")).click();
	   		Thread.sleep(500);
	   		driver.findElement(By.id("chbScreenDisplayEnableFlagPopup")).click();
	   		Thread.sleep(500);
		    driver.findElement(By.id("chbDeletableFlagPopup")).click();
		    Thread.sleep(1000);

			// dump data to file
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + language + "/" +"DATA_BEFORE_UPDATE", "IVB_M_ACCESS_AUTHORIZATION");
		    driver.findElement(By.id("btnRegisterPopup")).click();
		    Thread.sleep(1000); 
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint()); 
			// store log file name
			logFileNameArray.add("selenium_test_images/0049/success/" + browser + "/" + language + "/0095_3_3_1_1.log");
	   		if(language == "vi") {
		    	assertEquals("Cập nhật thông tin thành công!", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "en") {
		    	assertEquals("Update information successfully!", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "jp") {
		    	assertEquals("情報の更新が成功しました。", driver.findElement(By.id("popup_message")).getText());
		    }

			// dump data to file
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + language + "/" +"DATA_AFTER_UPDATE", "IVB_M_ACCESS_AUTHORIZATION");
		    Thread.sleep(1000);
		    capture(imagePath  + browser + "/" + language + "/0095_3_3_1_1");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		Thread.sleep(500);
		    driver.findElement(By.id("popup_ok")).click();
		    driver.findElement(By.id("txtGoToPage")).clear();
		    driver.findElement(By.id("txtGoToPage")).sendKeys("3");
		    Thread.sleep(500);
		    driver.findElement(By.id("btnGoToPage")).click();
		    Thread.sleep(1000);
		    
		    driver.findElement(By.cssSelector("#row18 > td > .edit")).click();
		    driver.findElement(By.id("chbScreenDisplayEnableFlagPopup")).click();
		    driver.findElement(By.id("chbDeletableFlagPopup")).click();
		    Thread.sleep(1000);

			// dump data to file
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + language + "/" +"DATA_BEFORE_UPDATE", "IVB_M_ACCESS_AUTHORIZATION");
		    driver.findElement(By.id("btnRegisterPopup")).click();
		    Thread.sleep(1000); 
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add("selenium_test_images/0049/success/" + browser + "/" + language + "/0095_3_3_1_2.log");
	   		if(language == "vi") {
		    	assertEquals("Cập nhật thông tin thành công!", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "en") {
		    	assertEquals("Update information successfully!", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "jp") {
		    	assertEquals("情報の更新が成功しました。", driver.findElement(By.id("popup_message")).getText());
		    }
			// dump data to file
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + language + "/" + "DATA_AFTER_UPDATE", "IVB_M_ACCESS_AUTHORIZATION");
		    Thread.sleep(1000);
		    capture(imagePath  + browser + "/" + language + "/0095_3_3_1_2");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		Thread.sleep(500);
		    driver.findElement(By.id("popup_ok")).click();
		    Thread.sleep(1000);
		    
	   		//delete
		    driver.findElement(By.id("txtGoToPage")).clear();
		    driver.findElement(By.id("txtGoToPage")).sendKeys("3");
		    Thread.sleep(500);
		    driver.findElement(By.id("btnGoToPage")).click();
		    Thread.sleep(1000);
		    driver.findElement(By.cssSelector("#row18 > td > .delete")).click();
		    Thread.sleep(1000); 
	   		if(language == "vi") {
		    	assertEquals("Bạn có muốn xóa thông tin này không?", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "en") {
		    	assertEquals("Are you sure you want to delete this information?", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "jp") {
		    	assertEquals("削除します。よろしいですか？", driver.findElement(By.id("popup_message")).getText());
		    }
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add("selenium_test_images/0049/success/" + browser + "/" + language + "/0095_4_1_1_1.log");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		Thread.sleep(1000);
	   		capture(imagePath  + browser + "/" + language + "/0095_4_1_1_1");
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add("selenium_test_images/0049/success/" + browser + "/" + language + "/0095_4_3_1_1.log");
		    driver.findElement(By.id("popup_cancel")).click();
		    Thread.sleep(1000);
		    capture(imagePath  + browser + "/" + language + "/0095_4_3_1_1");
		    driver.findElement(By.id("txtGoToPage")).clear();
		    driver.findElement(By.id("txtGoToPage")).sendKeys("3");
		    Thread.sleep(500);
		    driver.findElement(By.id("btnGoToPage")).click();
		    Thread.sleep(1000);
			// dump data to file
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + language + "/" +"DATA_BEFORE_DELETE", "IVB_M_ACCESS_AUTHORIZATION");
		    driver.findElement(By.cssSelector("#row18 .delete")).click();
		    Thread.sleep(1000); 
	   		if(language == "vi") {
		    	assertEquals("Bạn có muốn xóa thông tin này không?", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "en") {
		    	assertEquals("Are you sure you want to delete this information?", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "jp") {
		    	assertEquals("削除します。よろしいですか？", driver.findElement(By.id("popup_message")).getText());
		    }
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok")).click();
		    Thread.sleep(1000);
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath  + browser + "/" + language + "/0095_4_2_1_1.log");
	   		if(language == "vi") {
		    	assertEquals("Xóa thông tin thành công!", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "en") {
		    	assertEquals("Delete successfully!", driver.findElement(By.id("popup_message")).getText());
		    }else if(language == "jp") {
		    	assertEquals("削除が成功しました。", driver.findElement(By.id("popup_message")).getText());
		    }

			// dump data to file
			SeleniumCommon.dumpDataToFile("BANANA_DB_NGHIA", "E:/" + imagePath + browser + "/" + language + "/" +"DATA_AFTER_DELETE", "IVB_M_ACCESS_AUTHORIZATION");
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath  + browser + "/" + language + "/0095_4_2_1_1.log");
	   		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	   		Thread.sleep(1000);
	   		capture(imagePath  + browser + "/" + language + "/0095_4_2_1_1");
	   		Thread.sleep(1000);
	   		driver.findElement(By.id("popup_ok")).click();
	   		Thread.sleep(500);
			// store check point information
			checkPointArray.add(SeleniumCommon.createCurrentCheckPoint());
			// store log file name
			logFileNameArray.add(imagePath  + browser + "/" + language + "/0095_12_1_1_1.log");
	   		driver.findElement(By.id("btnBack")).click();
	   		Thread.sleep(500);
	   		capture(imagePath  + browser + "/" + language + "/0095_12_1_1_1");
	   		Thread.sleep(500);
		} catch ( Exception ex ) {
			System.err.println("Can't open browser");
			assertEquals("1", "");
		} 
	  }
	
	  @Test
	  public void testBnn0095SearchAccessAuthorizationIeVi() throws Exception {
	    common("IE", "jp");
	  }
	  @Test
	  public void testBnn0095SearchAccessAuthorizationIeEn() throws Exception {
	    common("IE", "en");
	  }
	  @Test
	  public void testBnn0095SearchAccessAuthorizationIeJp() throws Exception {
	    common("IE", "vi");
	  }
	/*@Test
	  public void testBnn0095SearchAccessAuthorizationChromeVi() throws Exception {
	    common("Chrome", "vi");
	    
	  }
	@Test
	  public void testBnn0095SearchAccessAuthorizationChromeEn() throws Exception {
	    common("Chrome", "en");
	    
	  }
	@Test
	  public void testBnn0095SearchAccessAuthorizationChromeJp() throws Exception {
	    common("Chrome", "jp");
	    
	  }*/
	/*@Test
	  public void testBnn0095SearchAccessAuthorizationFireFoxVi() throws Exception {
	    common("FireFox", "vi");
	    
	  }
	@Test
	  public void testBnn0095SearchAccessAuthorizationFireFoxEn() throws Exception {
	    common("FireFox", "en");
	    
	  }
	@Test
	  public void testBnn0095SearchAccessAuthorizationFireFoxJp() throws Exception {
	    common("FireFox", "jp");
	    
	  }*/
	/*	@Test
	  public void testBnn0095SearchAccessAuthorizationedgeVi() throws Exception {
	    common("edge", "vi");
	    
	  }
	@Test
	  public void testBnn0095SearchAccessAuthorizationedgeEn() throws Exception {
	    common("edge", "en");
	    
	  }
	@Test
	  public void testBnn0095SearchAccessAuthorizationedgeJp() throws Exception {
	    common("edge", "jp");
	    
	  }*/


	@After
	public void tearDown() throws Exception {
		try {	
			driver.quit();
			SeleniumCommon.startPrintingLog(checkPointArray, logFileNameArray);
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
