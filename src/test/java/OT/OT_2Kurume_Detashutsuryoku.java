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

public class OT_2Kurume_Detashutsuryoku {
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

	@Before
	public void setUp() {
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");
	}

	/**
	 * 
	 */
	@Test
	public void testKurume_Detashutsuryoku() {
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
				OTCommon.callStoreEXPORT("BANANA_DB_OT", "/var/lib/mysql-files/");
			}			
			//▲▲▲--トレーサビリティ出力機能--▲▲▲
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
