package OT;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import vn.bananavietnam.ict.common.cnst.Constants;

public class OT_0049_0035_0037_0045 {
	private static WebDriver driver;

	private List<Object[]> areaList = Arrays.asList(new Object[][] { 
		{"U0000000003","Biên Hòa",
			new String[] {"A001", "A002", "A003", "A004", "A005", "A006", "A007", "A008", "A009", "A010",
					 "A011", "A012", "A013", "A014", "A015", "A016", "A017", "A018", "A019", "A020", "A021"}
		}, 
		{"U0000000004", "Sài Gòn",
			new String[] {"A001", "A002", "A003", "A004", "A005"}
		},
	});

	private List<Object[]> blockList = Arrays.asList(new Object[][] { 
		{"Biên Hòa", "A001", "1"},
		{"Biên Hòa", "A002", "1"},
		{"Biên Hòa", "A003", "1"},
		{"Biên Hòa", "A004", "1"},
		{"Biên Hòa", "A005", "1"},
		{"Biên Hòa", "A006", "1"},
		{"Biên Hòa", "A007", "1"},
		{"Biên Hòa", "A008", "1"},
		{"Biên Hòa", "A009", "1"},
		{"Biên Hòa", "A010", "1"},
		{"Biên Hòa", "A011", "1"},
		{"Biên Hòa", "A012", "1"},
		{"Biên Hòa", "A013", "1"},
		{"Biên Hòa", "A014", "1"},
		{"Biên Hòa", "A015", "1"},
		{"Biên Hòa", "A016", "1"},
		{"Biên Hòa", "A017", "1"},
		{"Biên Hòa", "A018", "1"},
		{"Biên Hòa", "A019", "1"},
		{"Biên Hòa", "A020", "1"},
		{"Biên Hòa", "A021", "1"},
		{"Sài Gòn", "A001", "1"},
		{"Sài Gòn", "A002", "1"},
		{"Sài Gòn", "A003", "1"},
		{"Sài Gòn", "A004", "1"},
		{"Sài Gòn", "A005", "1"},
	});
	
	private List<Object[]> kindList = Arrays.asList(new Object[][] { 
		{"U0000000003", "Biên Hòa",
			new String[] {"LABA BANANA", "NAM MY BANANA"}
		},
		{"U0000000004", "Sài Gòn",
			new String[] {"LABA BANANA", "NAM MY BANANA"}
		}
	});
	/*private List<Object[]> blockList = Arrays.asList(new Object[][] { 
		{"Biên Hòa", "A001", "45"},
		{"Biên Hòa", "A002", "5"},
		{"Biên Hòa", "A003", "15"},
		{"Biên Hòa", "A004", "5"},
		{"Biên Hòa", "A005", "15"},
		{"Biên Hòa", "A006", "25"},
		{"Biên Hòa", "A007", "55"},
		{"Biên Hòa", "A008", "1"},
		{"Biên Hòa", "A009", "40"},
		{"Biên Hòa", "A010", "2"},
		{"Biên Hòa", "A011", "55"},
		{"Biên Hòa", "A012", "23"},
		{"Biên Hòa", "A013", "33"},
		{"Biên Hòa", "A014", "32"},
		{"Biên Hòa", "A015", "4"},
		{"Biên Hòa", "A016", "34"},
		{"Biên Hòa", "A017", "43"},
		{"Biên Hòa", "A018", "33"},
		{"Biên Hòa", "A019", "34"},
		{"Biên Hòa", "A020", "4"},
		{"Biên Hòa", "A021", "43"},
		{"Sài Gòn", "A001", "33"},
		{"Sài Gòn", "A002", "22"},
		{"Sài Gòn", "A003", "99"},
		{"Sài Gòn", "A004", "99"},
		{"Sài Gòn", "A005", "1"},
	});*/

	private List<Object[]> userList = Arrays.asList(new Object[][] { 
		{"U0000000003"}
	});

	private String imagePath = "selenium_test_images/OT/OT_0049_0035_0037_0045/";

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
				common0035(userList.get(i)[0].toString());
			}
		    
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("Can't open browser");
			assertEquals("1", "");
		}
	}

	public  void common0035(String userId) throws InterruptedException {
		for (int i = 0; i < areaList.size(); i++) {
			if (userId.equalsIgnoreCase(areaList.get(i)[0].toString())) {
				String[] areaListClick = (String[]) areaList.get(i)[2];
				if (areaListClick != null || areaListClick.length >= 0) {
		    		for (int j = 0; j < areaListClick.length; j++) {
						driver.findElement(By.id(areaListClick[j].toString())).click();
						for (int k = 0; k < blockList.size(); k++) {
		    				Integer blockListId = Integer.parseInt((String) blockList.get(k)[2]);		
		    				Thread.sleep(3000);
		    				OTCommon.scrollVSCapture(driver, imagePath + "3.クール切替/" + "0049_0035_" + i + "_" + j, "divBody", "document.getElementsByTagName('tr')[document.getElementsByTagName('tr').length-1]");

		    				if (areaList.get(i)[1].toString().equalsIgnoreCase(blockList.get(k)[0].toString())
		    						&& areaListClick[j].toString().equalsIgnoreCase(blockList.get(k)[1].toString())) {
		    					for (int t = 0; t < blockListId; t++) {
			    					String blockId = Constants.BEGIN_BLOCK_ID + String.format("%0" + Constants.MAX_LENGHT_ID + "d", t);
			    					driver.findElement(By.linkText(blockId)).click();
			    					OTCommon.scrollVSCapture(driver, imagePath + "3.クール切替/" + "0049_0035_0037" + i + "_" + j + "_" + blockId, "divBody", "document.getElementsByTagName('tr')[document.getElementsByTagName('tr').length-1]");
			    					Thread.sleep(3000);
			    					common0045(userId);
			    					driver.findElement(By.id("btnBack")).click();
			    					Thread.sleep(1000);
			    					driver.findElement(By.id("btnBack")).click();
			    				}
		    					break;
	    					} 
		    			}
						Thread.sleep(1000);
						driver.findElement(By.id("btnTaskManagement")).click();
		    		}
				} 
			}
			
		}
	}

	public  void common0045(String userId) throws InterruptedException {
		driver.findElement(By.id("btnTaskProcess")).click();
		for (int i = 0; i < kindList.size(); i++) {
			if (userId.equalsIgnoreCase(kindList.get(i)[0].toString())) {
				String[] kindListClick = (String[]) kindList.get(i)[2];
				if (kindListClick != null || kindListClick.length >= 0) {
					for (int j = 0; j < kindListClick.length; j++) {
						new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText(kindList.get(i)[1].toString());
						new Select(driver.findElement(By.id("cbbKindName"))).selectByVisibleText(kindListClick[j].toString());
						driver.findElement(By.id("btnSearch")).click();
						for (int t = Integer.parseInt(driver.findElement(By.id("lblCurrentPage")).getText()); t <= Integer.parseInt(driver.findElement(By.id("lblMaxPage")).getText()); ) {
    				    	t = Integer.parseInt(driver.findElement(By.id("lblCurrentPage")).getText());
    				    	List<WebElement> elementList = driver.findElements(By.xpath("//table[@id='tblBody']/tbody/tr"));

    				    	OTCommon.scrollVSCapture(driver, imagePath + "3.クール切替/" + "0035_0037_0045" + i + "_" + j + "_" + t, "divBody", "document.getElementsByTagName('tr')[document.getElementsByTagName('tr').length-1]");
    				    	
    					    if (t == Integer.parseInt(driver.findElement(By.id("lblMaxPage")).getText())) {
    					    	break;
    					    }
    					    driver.findElement(By.id("btnNext")).click();
    				    }
					}
				}
			}
		}
	}
	public static boolean capture(WebDriver driver, String fileName) {
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
