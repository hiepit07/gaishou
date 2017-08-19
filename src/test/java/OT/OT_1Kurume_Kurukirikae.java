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

public class OT_1Kurume_Kurukirikae {
	private static WebDriver driver;
	
	private List<Object[]>  TreeKillList = Arrays.asList(new Object[][] {
		{"農園_1", "エリア_1-1", 
			new String[] {"B001", "B002", "B003", "B004", "B005"}, 
			new String[] {"L001", "L003"}
		},
		{"農園_1", "エリア_1-2", 
			new String[] {"B001", "B002", "B003", "B004", "B005"},
			new String[] {"L001", "L003"}
		},
		{"農園_2", "エリア_2-1", 
			new String[] {"B001", "B002", "B003", "B004", "B005"},
			new String[] {"L001", "L003"}
		},
		{"農園_2", "エリア_2-2", 
			new String[] {"B001", "B002", "B003", "B004", "ブロック_2-2-5"},
			new String[] {"L001", "L003"}
		}
	});
	
	private List<Object[]> AreaList = Arrays.asList(new Object[][] {
		{"農園_1", "エリア_1-1"},
		{"農園_1", "エリア_1-2"},
		{"農園_2", "エリア_2-1"},
		{"農園_2", "エリア_2-2"}
	});

	private String imagePath = "selenium_test_images/OT/1クール目/";

	@Before
	public void setUp() {
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");
	}

	/**
	 * 
	 */
	@Test
	public void testKurume_Kurukirikae() {
		try {
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ignore");
			driver = new InternetExplorerDriver(ieCapabilities);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(OTCommon.getBaseurl() + "/ict/?language=jp");

			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));

			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("SManager");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("SManager");
			driver.findElement(By.id("btnLogin")).click();
			
			//▼▼▼--トレーサビリティ出力機能--▼▼▼
			for (int i = 0; i < AreaList.size(); i++) {
				OTCommon.callStoreUPDATE("BANANA_DB_NGHIA", "SManager", AreaList.get(i)[0].toString(), AreaList.get(i)[1].toString());
			}			
			//▲▲▲--トレーサビリティ出力機能--▲▲▲
			
			
			/*//▼▼▼--農作物削除（穴）　を調整--▼▼▼
			//0009 ブロック情報マスタ画面
		    driver.findElement(By.id("btnBlockScreen")).click();
		    driver.findElements(By.xpath("//img[contains(@class,'edit')]"));
		    driver.findElements(By.xpath("//img[contains(@class,'delete')]"));
 
		    for (int i = 0; i < TreeKillList.size(); i++) {
		    	new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText(TreeKillList.get(i)[0].toString());
		    	new Select(driver.findElement(By.id("cbbAreaName"))).selectByVisibleText(TreeKillList.get(i)[1].toString());
		    	
		    	String[] BlockList = (String[]) TreeKillList.get(i)[2];
		    	for (int b = 0; b < BlockList.length; b++) {
			    	new Select(driver.findElement(By.id("cbbBlockId"))).selectByVisibleText(BlockList[b]);
			    	driver.findElement(By.id("btnSearch")).click();
			    	
			    	String xPathSelector = "//td/a[text()='" + BlockList[b] + "']";
			    	driver.findElement(By.xpath(xPathSelector));
			    	driver.findElement(By.xpath(xPathSelector)).click();
	
			    	driver.findElement(By.cssSelector("div#popupProductWrapper[style*='display: block']"));
			    	Thread.sleep(1000);
			    	String[] TreeList = (String[]) TreeKillList.get(i)[3];
			    	if (TreeList.length > 0) {
				    	for (int j = 0; j < TreeList.length; j++) {
				    		xPathSelector = "//img[contains(@name, '" + TreeList[j] + "')]";
				    		List<WebElement> links = driver.findElements(By.xpath(xPathSelector));
				    		for (WebElement myElement : links){
				    			myElement.click();
				    		}					    	
					    }
				    	driver.findElement(By.cssSelector("#btnDisableProductPopup:not([disabled])")).click();
				    	driver.findElement(By.id("popup_ok"));
					    driver.findElement(By.id("popup_ok")).click();
			    	} else {
			    		driver.findElement(By.id("btnCancelProductPopup")).click();
			    	}
			    	Thread.sleep(1000);
			    	xPathSelector = "//td/a[text()='" + BlockList[b] + "']";
			    	driver.findElement(By.xpath(xPathSelector)).click();
			    	Thread.sleep(1000);
			    	
			    	OTCommon.scrollVSCapture(driver, imagePath + "3.クール切替/" + "21_" + i + "_" + b, "divBodyProduct", "document.getElementsByClassName('rowImgProduct')[document.getElementsByClassName('rowImgProduct').length-1]");

				    driver.findElement(By.id("btnCancelProductPopup")).click();
		    	}
		    }*/
			//▲▲▲--農作物削除（穴）　を調整--▲▲▲
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("Can't open browser");
			assertEquals("1", "");
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
