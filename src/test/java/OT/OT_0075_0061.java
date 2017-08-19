package OT;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public class OT_0075_0061 {
	private static WebDriver driver;

	private List<Object[]> FarmsList = Arrays.asList(new Object[][] { 
		{"U0000000003","Biên Hòa",
			new String[] {"BH 001", "BH 002", "BH 003", "BH 004", "BH 005", "BH 006", "BH 007", "BH 008", "BH 009", "BH 010",
					 "BH 011", "BH 012", "BH 013", "BH 014", "BH 015", "BH 016", "BH 017", "BH 018", "BH 019", "BH 020", "BH 021"}
		}, 
		{"U0000000004", "Sài Gòn",
			new String[] {"SG 001", "SG 002", "SG 003", "SG 004", "SG 005"}
		},
	});

	private List<Object[]> userList = Arrays.asList(new Object[][] { 
		{"U0000000003"}, 
		{"U0000000004"}
	});
	private List<Object[]> ShippingList = Arrays.asList(new Object[][] { 
		{"Biên Hòa","BH 001","00000001", "1"},
		{"Biên Hòa","BH 002","00000003", "0"},
		{"Biên Hòa","BH 002","00000005", "1"},
		{"Biên Hòa","BH 004","00000008", "0"},
		{"Biên Hòa","BH 004","00000009", "1"},
		{"Biên Hòa","BH 021","00000013", "0"},
		{"Biên Hòa","BH 021","00000014", "0"},
		{"Biên Hòa","BH 021","00000015", "0"},
		{"Biên Hòa","BH 021","00000016", "0"},
		{"Biên Hòa","BH 021","00000017", "0"},
		{"Biên Hòa","BH 021","00000018", "0"},
		{"Biên Hòa","BH 021","00000019", "0"},
		{"Biên Hòa","BH 021","00000020", "0"},
		{"Biên Hòa","BH 021","00000021", "0"},
		{"Biên Hòa","BH 021","00000022", "0"},
		{"Biên Hòa","BH 021","00000023", "0"},
		{"Biên Hòa","BH 021","00000025", "0"},
		{"Biên Hòa","BH 021","00000026", "0"},
		{"Biên Hòa","BH 021","00000027", "0"},
		{"Biên Hòa","BH 021","00000029", "0"},
		{"Biên Hòa","BH 021","00000032", "0"},
		{"Biên Hòa","BH 021","00000034", "0"},
		{"Biên Hòa","BH 021","00000035", "0"},
		{"Biên Hòa","BH 021","00000036", "0"},
		{"Sài Gòn","SG 001","00000002", "1"},
		
	});

	private String imagePath = "selenium_test_images/OT/OT_0075_0061/";

	@Before
	public void setUp() {
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");
	}
	@Test
	public void test0075() {
		try {
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ignore");
			driver = new InternetExplorerDriver(ieCapabilities);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			for (int i = 0; i < userList.size(); i++) {
				driver.get("http://localhost:8080" + "/ict/?language=jp");
				driver.findElement(By.id("wrapperFrame"));
				// start testing
				driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));

				driver.findElement(By.id("j_username")).clear();
				driver.findElement(By.id("j_username")).sendKeys(userList.get(i)[0].toString());
				driver.findElement(By.id("j_password")).clear();
				driver.findElement(By.id("j_password")).sendKeys(userList.get(i)[0].toString());
				driver.findElement(By.id("btnLogin")).click();
				common(userList.get(i)[0].toString());
			}
		    
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("Can't open browser");
			assertEquals("1", "");
		}
	}

	public  void common(String userId) {
		
		//▼▼▼--農作物削除（穴）　を調整--▼▼▼
		//0075 ブロック情報マスタ画面
	    driver.findElement(By.id("btnShippingScreen")).click();
	    for (int i = 0; i < FarmsList.size(); i++) {
	    	if (userId.equalsIgnoreCase(FarmsList.get(i)[0].toString())) {
	    		new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText(FarmsList.get(i)[1].toString());
		    	String[] areaList = (String[]) FarmsList.get(i)[2];
		    	if (areaList != null || areaList.length >= 0) {
		    		for (int j = 0; j < areaList.length; j++) {
		    			for (int k = 0; k < ShippingList.size(); k++) {
			    			new Select(driver.findElement(By.id("cbbAreaName"))).selectByVisibleText(areaList[j].toString());
		    				if (FarmsList.get(i)[1].toString().equalsIgnoreCase(ShippingList.get(k)[0].toString())
		    						&& areaList[j].equalsIgnoreCase(ShippingList.get(k)[1].toString())) {
		    					driver.findElement(By.id("btnSearch")).click();
		    					driver.findElement(By.id(ShippingList.get(k)[2].toString())).click();
			    				if (ShippingList.get(k)[3].toString().equalsIgnoreCase("0")) {
			    					driver.findElement(By.id("popup_ok")).click();
				    				driver.findElement(By.id("btnShippingScreen")).click();
			    				} else {
			    				    for (int t = Integer.parseInt(driver.findElement(By.id("lblCurrentPage")).getText()); t <= Integer.parseInt(driver.findElement(By.id("lblMaxPage")).getText()); ) {
			    				    	t = Integer.parseInt(driver.findElement(By.id("lblCurrentPage")).getText());
			    				    	List<WebElement> elementList = driver.findElements(By.xpath("//table[@id='tblBody']/tbody/tr"));

			    				    	OTCommon.scrollVSCapture(driver, imagePath + "3.クール切替/" + "0075_0061_" + i + "_" + j + "_" + t, "divBody", "document.getElementsByTagName('tr')[document.getElementsByTagName('tr').length-1]");
			    				    	
			    					    if (t == Integer.parseInt(driver.findElement(By.id("lblMaxPage")).getText())) {
			    					    	break;
			    					    }
			    					    driver.findElement(By.id("btnNext")).click();
			    				    }
			    					 
				    				driver.findElement(By.id("btnShippingScreen")).click();	
			    				}
		    				}
		    			}
			    	}
		    	} else {
		    		driver.findElement(By.id("btnSearch")).click();
		    		driver.findElement(By.id("popup_ok")).click();
		    	}
			}
	    }
	}
	@After
	public void tearDown() throws Exception {
		try {
			driver.quit();
		} catch (Exception ex) {
			System.err.println("Can't close browser");
		}
	}	

	public static WebElement findElementSafe(WebDriver driver, By by) {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		try {
            return driver.findElement(by);
        }
        catch (NoSuchElementException ex) {
            return null;
        }
    }
}
