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

public class OT_ShokiSettei {
	private static WebDriver driver;

	private List<Object[]> UsersList = Arrays.asList(new Object[][] { 
		{"SManager", "System Manager", "SManager", "1", "9999", "9999"}, 
		{"BManager", "Business Manager", "BManager", "2", "9999", "9999"},
		{"FManager1", "Farm Manager 1", "FManager1", "3", "農園_1", "9999"},
		{"FManager2", "Farm Manager 2", "FManager2", "3", "農園_2", "9999"},
		{"AManager11", "Area Manager 1_1", "AManager11", "4", "農園_1", "エリア_1-1"},
		{"AManager12", "Area Manager 1_2", "AManager12", "4", "農園_1", "エリア_1-2"},
		{"AManager21", "Area Manager 2_1", "AManager21", "4", "農園_2", "エリア_2-1"},
		{"AManager22", "Area Manager 2_2", "AManager22", "4", "農園_2", "エリア_2-2"}
	});

	private List<Object[]> RoleList = Arrays.asList(new Object[][] {
		{"3", "0049", "1", "1", "1", "1", "1"},
		{"3", "0035", "1", "1", "1", "1", "1"},
		{"3", "0037", "1", "1", "1", "1", "1"},
		{"3", "0061", "1", "1", "1", "1", "1"},
		{"3", "0045", "1", "1", "1", "1", "1"},
		{"3", "0075", "1", "1", "1", "1", "1"},
		{"4", "0035", "1", "1", "1", "1", "1"},
		{"4", "0037", "1", "1", "1", "1", "1"},
		{"4", "0061", "1", "1", "1", "1", "1"},
		{"4", "0045", "1", "1", "1", "1", "1"},
		{"4", "0075", "1", "1", "1", "1", "1"}
	});

	private List<Object[]> KindList = Arrays.asList(new Object[][] {
		{"品種_1", "39.5", "5", "15", "1"},
		{"品種_2", "37.5", "4", "10", "1"}
	});

	private List<Object[]> FarmList = Arrays.asList(new Object[][] {
		{"農園_1", "Đăk Lăk", "01012017", "01122018", "01012017", "3000", "3", "50", "Humid tropics", "Feralit", "farm01@gmail.com", "999-999-999", "999-999-999"},
		{"農園_2", "Lâm Đồng", "01012017", "01122018", "01012017", "3000", "3", "50", "Humid tropics", "Feralit", "farm02@gmail.com", "888-888-888", "888-888-888"}
	});

	private List<Object[]> AreaList = Arrays.asList(new Object[][] {
		{"農園_1", "エリア_1-1", "5", "品種_1", "24.5", "Cuống to, ngắn"},
		{"農園_1", "エリア_1-2", "5", "品種_2", "26", "Tù đầu"},
		{"農園_2", "エリア_2-1", "5", "品種_1", "25.5", "Cuống nhỏ, dài"},
		{"農園_2", "エリア_2-2", "4", "品種_2", "24", "Nhọn đầu"}
	});

	private List<Object[]>  BlockList = Arrays.asList(new Object[][] {
		{"農園_2", "エリア_2-2", "ブロック_2-2-5"}
	});
	
	private List<Object[]>  TreeKillList = Arrays.asList(new Object[][] {
		{"農園_1", "エリア_1-1", 
			new String[] {"B001", "B002", "B003", "B004", "B005"}, 
			new String[] {"L001", "L002"}
		},
		{"農園_1", "エリア_1-2", 
			new String[] {"B001", "B002", "B003", "B004", "B005"},
			new String[] {"L001", "L002"}
		},
		{"農園_2", "エリア_2-1", 
			new String[] {"B001", "B002", "B003", "B004", "B005"},
			new String[] {"L001", "L002"}
		},
		{"農園_2", "エリア_2-2", 
			new String[] {"B001", "B002", "B003", "B004", "ブロック_2-2-5"},
			new String[] {"L001", "L002"}
		}
	});
	
	private List<Object[]> ProcessList = Arrays.asList(new Object[][] {
		{"土壌づくり",
			Arrays.asList(new Object[][] {
				{"・畝づくり", "・畝幅 2-2.5m\n・方位 西－東", "", "0", "0", "育成開始日"},
				{"・穴掘り", "・サイズ 幅30cm深さ40cm\n・株間 2m\n・植える前肥料入れ時点 2週間\n　　‐石灰 0.3-0.5kg\n　　‐リン酸 0.3-0.5kg\n　　‐牛糞・鶏糞 2kg\n　　‐有機質肥料 2kg", "", "0", "1", ""},
				{"・苗のTAG付け", "・TAG名", "", "0", "0", ""},
				{"・排水溝工事", "・排水溝の幅 XXm\n・排水溝の奥 YYm\n・排水溝の深さ HHm\n", "", "0", "0", ""},
				{"・灌漑づくり", " 未／済み", "", "0", "0", ""},
			})
		},
		{"苗選択", 
			Arrays.asList(new Object[][] {
				{"・新芽の選択", "・新芽の高さ 30-40cm\n・新芽の葉 3-5枚", "病気がない新芽、ほぼ同じサイズの新芽", "0", "0", ""},
				{"・苗床の植え", "・苗床の準備\n　　‐ビニールハウス作り\n・植え方\n　　‐穴開け\n　　‐苗入れ\n　　‐水やり\n・苗の面倒：植え後2日間\n　　‐肥料入れ\n　　‐農薬スプレー Daconil", "最初の段階で雨が直接に入れないこと\n日当たりを70-80%カット\n\n\n\n\n早朝または夕方に作業を行う", "0", "0", ""}
			}) 
		},
		{"農園に苗植え", 
			Arrays.asList(new Object[][] {
				{"・穴掘り", "・準備しておく穴に苗の根より穴を掘る ", "", "0", "0", ""},
				{"・穴に肥料の入れ", "肥料_名前 ", "夕方ごろ作業を実施する　地表から深さ10cmスペース", "0", "0", ""},
				{"・穴に苗の入れ", "方向", "苗を新たな土に真っ直ぐに立てて植える", "0", "0", "植え日"},
				{"・植え替え", "死んだ木の確認 5-10日後", "", "0", "0", "植え日"}
			})
		},
		{"水やり", 
			Arrays.asList(new Object[][] {
				{"・乾季の水やり 1週目～4週目", "・1週目～4週目 5L／株", "", "0", "0", ""},
				{"・乾季の水やり 5週目～9週目", "・5週目～9週目 8－10L／株", "", "0", "0", ""},
				{"・乾季の水やり 10週目～19週目", "・10週目～19週目 12L／株", "", "0", "0", ""},
				{"・乾季の水やり 20週目～32週目", "・22週目～32週目 16－12L／株", "", "0", "0", ""},
				{"・雨季の水やり", "・水はけのチェック ", "", "0", "0", ""}
			}) 
		},
		{"肥料の撒き", 
			Arrays.asList(new Object[][] {
				{"・苗のとき肥料撒き", "・液肥を根に直接やる ", "", "0", "1", ""},
				{"・肥料の撒き　１５日：一株あたり", "　‐尿素 50g", "木のとき：木から0.5m～0.6m間隔に穴を掘って肥料を埋める", "0", "1", ""},
				{"・肥料の撒き　１か月：一株あたり", "　‐尿素 45g\n　‐KCｌ 45g\n　‐Ca(NO3)2 10g\n　‐ZnSO4 20g\n　‐MgSO4 20g", "", "0", "1", ""},
				{"・肥料の撒き　２か月：一株あたり", "　‐尿素 50g\n　‐KCｌ 50g\n　‐Ca(NO3)2 10g\n　‐MgSO4 20g", "", "0", "1", ""},
				{"・肥料の撒き　３か月：一株あたり", "　‐尿素 50g\n　‐KCｌ 60g\n　‐Ca(NO3)2 10g", "", "0", "1", ""},
				{"・肥料の撒き　４か月：一株あたり", "　‐尿素 50g\n　‐KCｌ 60g\n　‐Ca(NO3)2 10g", "", "0", "1", ""},
				{"・肥料の撒き　５か月：一株あたり", "　‐尿素 50g\n　‐DAP 300g\n　‐KCｌ 60g\n　‐K2SO4 10g\n　‐Ca(NO3)2 10g", "", "0", "1", ""},
				{"・肥料の撒き　６か月：一株あたり", "　‐尿素 50g\n　‐KCｌ 100g\n　‐K2SO4 30g\n　‐Ca(NO3)2 100g\n", "", "0", "1", ""},
				{"・肥料の撒き　７か月：一株あたり", "　‐尿素 50g\n　‐KCｌ 100g\n　‐K2SO4 40g\n　‐Ca(NO3)2 100g", "", "0", "1", ""},
				{"・肥料の撒き　８か月：一株あたり", "　‐尿素 30g\n　‐KCｌ 60g\n　‐K2SO4 50g\n　‐Ca(NO3)2 50g", "", "0", "1", ""}
			})
		},
		{"苗の管理", 
			Arrays.asList(new Object[][] {
				{"・不要な新芽の取り除き", "", "", "0", "0", ""},
				{"・毎月台芽の高さ３０ｃｍまで切り取り", "", "", "0", "0", ""},
				{"・花が咲いたら台芽を、一本除いて全て切り取る", "", "", "0", "0", ""},
				{"・開花日登録", "", "", "1", "0", "開花日"},
				{"・房の覆い", "", "", "1", "0", "袋がけ日"},
				{"・花びらの取り除き", "", "", "1", "0", ""},
				{"・雄しべの取り除き", "", "", "1", "0", ""},
				{"・株の支え", "", "", "1", "0", ""},
				{"・病気の木を破壊する", "", "", "1", "0", ""}
			})
		},
		{"害虫の駆除", 
			Arrays.asList(new Object[][] {
				{"・害虫のサインチェック", "", "", "0", "0", ""},
				{"・害虫の種類確認", "", "", "0", "0", ""},
				{"・害虫によって農薬適用　‐斑葉病", "Mineral oil\nCarbenzim\nTilt\nCoc 85\nAntracol\nAnvil", "", "0", "1", ""},
				{"・害虫によって農薬適用　‐炭疽病", "Talent\nDaconil\nCarbenzim", "", "0", "1", ""},
				{"・害虫によって農薬適用　‐バナナのパナマ病", "Trichoderma spp\nBordeaux", "", "0", "1", ""},
				{"・害虫によって農薬適用　‐バンチートップ病", "Viben-C", "", "0", "1", ""},
				{"・害虫によって農薬適用　‐アブラムシ", "", "Bini", "0", "1", ""},
				{"・害虫によって農薬適用　‐根こぶ線虫病", "Etocap", "", "0", "1", ""}
			})
		},
		{"収穫", 
			Arrays.asList(new Object[][] {
				{"・房の切り取り", "", "", "1", "0", "収穫日"},
				{"・幹の上半分の切り倒し", "", "", "1", "0", ""}
			})
		},
		{"水洗い・選別", 
			Arrays.asList(new Object[][] {
				{"・房の洗い流し", "", "", "0", "0", ""},
				{"・バナナの傷／形のチェック", "", "", "0", "0", ""}
			})
		},
		{"計量・箱詰め", 
			Arrays.asList(new Object[][] {
				{"・箱入れ", "", "", "0", "0", ""}
			}) 
		},
		{"輸出検査", 
			Arrays.asList(new Object[][] {
				{"・輸出検査", "", "", "0", "0", ""}
			}) 
		},
		{"出港", 
			Arrays.asList(new Object[][] {
				{"・港に運搬", "", "", "0", "0", ""}
			}) 
		}
	});

	private String imagePath = "selenium_test_images/OT/0初期設定/";

	@Before
	public void setUp() {
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testShokiSettei() {
		try {
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ignore");
			driver = new InternetExplorerDriver(ieCapabilities);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(OTCommon.getBaseurl() + "/ict/?language=jp");

			driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));

			driver.findElement(By.id("j_username")).clear();
			driver.findElement(By.id("j_username")).sendKeys("Admin");
			driver.findElement(By.id("j_password")).clear();
			driver.findElement(By.id("j_password")).sendKeys("Password");
			driver.findElement(By.id("btnLogin")).click();

			//▼▼▼--1.ユーザ登録＆権限設定--▼▼▼
			//0003 ユーザー情報マスタ画面
		    driver.findElement(By.id("btnUserScreen")).click();
		    driver.findElements(By.xpath("//img[contains(@class,'edit')]"));
		    driver.findElements(By.xpath("//img[contains(@class,'delete')]"));
			// Thread.sleep(1000);
		    for (int i = 0; i < UsersList.size(); i++) {
		    	driver.findElement(By.id("btnRegister")).click();
			    driver.findElement(By.id("txtUsersIdPopup")).clear();
			    driver.findElement(By.id("txtUsersIdPopup")).sendKeys(UsersList.get(i)[0].toString());
			    driver.findElement(By.id("txtUsersNamePopup")).clear();
			    driver.findElement(By.id("txtUsersNamePopup")).sendKeys(UsersList.get(i)[1].toString());
			    driver.findElement(By.id("txtPasswordPopup")).clear();
			    driver.findElement(By.id("txtPasswordPopup")).sendKeys(UsersList.get(i)[2].toString());
			    driver.findElement(By.id("btnRegisterPopup")).click();
			    driver.findElement(By.id("popup_ok"));
			    driver.findElement(By.id("popup_ok")).click();
		    }
		    OTCommon.capture(driver, imagePath + "1.ユーザ登録＆権限設定/" + "0003");

		    driver.findElement(By.id("btnGeneralSetting")).click();

			//0087 所属情報マスタ画面
		    driver.findElement(By.id("btnManageScreen")).click();
		    driver.findElements(By.xpath("//img[contains(@class,'edit')]"));
		    driver.findElements(By.xpath("//img[contains(@class,'delete')]"));
		    // Thread.sleep(1000);
		    for (int i = 0; i < 2; i++) {
		    	String xPathSelector = "//td/input[@value='" + UsersList.get(i)[0].toString() + "']/parent::td/parent::tr/td/img[contains(@class, 'edit')]";
		    	driver.findElement(By.xpath(xPathSelector)).click();
		    	if (UsersList.get(i)[3].toString().equalsIgnoreCase("1")) {
		    		new Select(driver.findElement(By.id("cbbAffilNamePopup"))).selectByVisibleText("Quản lý hệ thống");
		    	} else {
		    		new Select(driver.findElement(By.id("cbbAffilNamePopup"))).selectByVisibleText("Giám đốc kinh doanh");
		    	}
		    	driver.findElement(By.id("btn-submit")).click();
		    	driver.findElement(By.id("popup_ok"));
			    driver.findElement(By.id("popup_ok")).click();
		    }
		    OTCommon.capture(driver, imagePath + "1.ユーザ登録＆権限設定/" + "0087");

		    driver.findElement(By.id("btnGeneralSetting")).click();
		    
			//0095 アクセス権限マスタ画面
		    driver.findElement(By.id("btnAuthorityScreen")).click();
		    driver.findElements(By.xpath("//img[contains(@class,'edit')]"));
		    driver.findElements(By.xpath("//img[contains(@class,'delete')]"));
		    // Thread.sleep(1000);
		    for (int i = 0; i < RoleList.size(); i++) {
		    	driver.findElement(By.id("btnRegister")).click();
			    driver.findElement(By.id("txtAccessAuthorityIdPopup")).clear();
			    driver.findElement(By.id("txtAccessAuthorityIdPopup")).sendKeys(RoleList.get(i)[0].toString());
			    driver.findElement(By.id("txtScreenIdPopup")).clear();
			    driver.findElement(By.id("txtScreenIdPopup")).sendKeys(RoleList.get(i)[1].toString());

			    if (RoleList.get(i)[2].toString() == "1") {
			    	driver.findElement(By.id("chbScreenDisplayEnableFlagPopup")).click();
			    }
			    if (RoleList.get(i)[3].toString() == "1") {
			    	driver.findElement(By.id("chbDeletableFlagPopup")).click();
			    }
			    if (RoleList.get(i)[4].toString() == "1") {
			    	driver.findElement(By.id("chbAddableFlagPopup")).click();
			    }
			    if (RoleList.get(i)[5].toString() == "1") {
			    	driver.findElement(By.id("chbUpdatableFlagPopup")).click();
			    }
			    if (RoleList.get(i)[6].toString() == "1") {
			    	driver.findElement(By.id("chbRefernceFlagPopup")).click();
			    }
			    driver.findElement(By.id("btnRegisterPopup")).click();
			    driver.findElement(By.id("popup_ok"));
			    driver.findElement(By.id("popup_ok")).click();
		    }		    
		    driver.findElement(By.id("btnLast")).click();
		    OTCommon.capture(driver, imagePath + "1.ユーザ登録＆権限設定/" + "0095");
		    //▲▲▲--1.ユーザ登録＆権限設定--▲▲▲

		    driver.findElement(By.id("btnGeneralSetting")).click();

		    //▼▼▼--2.品種の登録--▼▼▼
			//0017 品種マスタ画面
		    driver.findElement(By.id("btnBananaKindScreen")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok"));
		    driver.findElement(By.id("popup_ok")).click();
		    for (int i = 0; i < KindList.size(); i++) {
		    	driver.findElement(By.id("btnRegister")).click();
			    driver.findElement(By.id("txtKindNamePopup")).clear();
			    driver.findElement(By.id("txtKindNamePopup")).sendKeys(KindList.get(i)[0].toString());
			    driver.findElement(By.id("txtProspectiveHarvestAmountPopup")).clear();
			    driver.findElement(By.id("txtProspectiveHarvestAmountPopup")).sendKeys(KindList.get(i)[1].toString());
			    driver.findElement(By.id("txtEstimatedDaysFloweringPopup")).clear();
			    driver.findElement(By.id("txtEstimatedDaysFloweringPopup")).sendKeys(KindList.get(i)[2].toString());
			    driver.findElement(By.id("txtEstimatedDaysBaggingPopup")).clear();
			    driver.findElement(By.id("txtEstimatedDaysBaggingPopup")).sendKeys(KindList.get(i)[3].toString());
			    driver.findElement(By.id("txtEstimatedDaysHarvestPopup")).clear();
			    driver.findElement(By.id("txtEstimatedDaysHarvestPopup")).sendKeys(KindList.get(i)[4].toString());
			    driver.findElement(By.id("btnRegisterPopup")).click();
			    driver.findElement(By.id("popup_ok"));
			    driver.findElement(By.id("popup_ok")).click();
		    }
		    OTCommon.capture(driver, imagePath + "2.品種の登録/" + "0017");
		    //▲▲▲--2.品種の登録--▲▲▲

		    driver.findElement(By.id("btnGeneralSetting")).click();

			//▼▼▼--3.フィールド設定--▼▼▼	
			//0005 農園情報マスタ画面
		    driver.findElement(By.id("btnFarmScreen")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok"));
		    driver.findElement(By.id("popup_ok")).click();
		    for (int i = 0; i < FarmList.size(); i++) {
		    	driver.findElement(By.id("btnNew")).click();
			    driver.findElement(By.id("txtFarmNamePopup")).clear();
			    driver.findElement(By.id("txtFarmNamePopup")).sendKeys(FarmList.get(i)[0].toString());
			    driver.findElement(By.id("txtAddressPopup")).clear();
			    driver.findElement(By.id("txtAddressPopup")).sendKeys(FarmList.get(i)[1].toString());
				driver.findElement(By.id("txtTimeFromPopup")).clear();
				driver.findElement(By.id("txtTimeFromPopup")).sendKeys(FarmList.get(i)[2].toString());
				driver.findElement(By.id("txtTimeToPopup")).clear();
				driver.findElement(By.id("txtTimeToPopup")).sendKeys(FarmList.get(i)[3].toString());
				driver.findElement(By.id("txtOpenDatePopup")).clear();
				driver.findElement(By.id("txtOpenDatePopup")).sendKeys(FarmList.get(i)[4].toString());
			    driver.findElement(By.id("txtSizeOfPlanPopup")).clear();
			    driver.findElement(By.id("txtSizeOfPlanPopup")).sendKeys(FarmList.get(i)[5].toString());
			    driver.findElement(By.id("txtLineOfPlanPopup")).clear();
			    driver.findElement(By.id("txtLineOfPlanPopup")).sendKeys(FarmList.get(i)[6].toString());
			    driver.findElement(By.id("txtColumnOfPlanPopup")).clear();
			    driver.findElement(By.id("txtColumnOfPlanPopup")).sendKeys(FarmList.get(i)[7].toString());
			    driver.findElement(By.id("txtClimatePopup")).clear();
			    driver.findElement(By.id("txtClimatePopup")).sendKeys(FarmList.get(i)[8].toString());
			    driver.findElement(By.id("txtSoilPopup")).clear();
			    driver.findElement(By.id("txtSoilPopup")).sendKeys(FarmList.get(i)[9].toString());
			    driver.findElement(By.id("txtEmailPopup")).clear();
			    driver.findElement(By.id("txtEmailPopup")).sendKeys(FarmList.get(i)[10].toString());
			    driver.findElement(By.id("txtFaxPopup")).click();
			    driver.findElement(By.id("txtPhonePopup")).clear();
			    driver.findElement(By.id("txtPhonePopup")).sendKeys(FarmList.get(i)[11].toString());
			    driver.findElement(By.id("txtFaxPopup")).clear();
			    driver.findElement(By.id("txtFaxPopup")).sendKeys(FarmList.get(i)[12].toString());
			    driver.findElement(By.id("btnRegisterFarmPopup")).click();
			    driver.findElement(By.id("popup_ok"));
			    driver.findElement(By.id("popup_ok")).click();
		    }
		    OTCommon.capture(driver, imagePath + "3.フィールド設定/" + "0005");

		    driver.findElement(By.id("btnGeneralSetting")).click();
			//0007 エリア情報マスタ画面
		    driver.findElement(By.id("btnAreaScreen")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok"));
		    driver.findElement(By.id("popup_ok")).click();
		    for (int i = 0; i < AreaList.size(); i++) {
		    	new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText(AreaList.get(i)[0].toString());
		    	driver.findElement(By.id("btnSearch")).click();
		    	// Thread.sleep(1000);
		    	if (findElementSafe(driver, By.id("popup_ok")) != null) {
		    		driver.findElement(By.id("popup_ok")).click();
		    	}
		    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		    	driver.findElement(By.id("btnRegister")).click();
			    driver.findElement(By.id("txtAreaNamePopup")).clear();
			    driver.findElement(By.id("txtAreaNamePopup")).sendKeys(AreaList.get(i)[1].toString());
			    driver.findElement(By.id("txtNumberOfBlockPopup")).clear();
			    driver.findElement(By.id("txtNumberOfBlockPopup")).sendKeys(AreaList.get(i)[2].toString());
			    new Select(driver.findElement(By.id("cbbKindNamePopup"))).selectByVisibleText(AreaList.get(i)[3].toString());
			    driver.findElement(By.id("txtSugarContentPopup")).clear();
			    driver.findElement(By.id("txtSugarContentPopup")).sendKeys(AreaList.get(i)[4].toString());
			    driver.findElement(By.id("txtTexturePopup")).clear();
			    driver.findElement(By.id("txtTexturePopup")).sendKeys(AreaList.get(i)[5].toString());
			    driver.findElement(By.id("btnRegisterPopup")).click();
			    driver.findElement(By.id("popup_ok"));
			    driver.findElement(By.id("popup_ok")).click();
			    OTCommon.capture(driver, imagePath + "3.フィールド設定/" + "0007_" + i);
		    }
		    driver.findElement(By.id("btnGeneralSetting")).click();

			//0009 ブロック情報マスタ画面
		    driver.findElement(By.id("btnBlockScreen")).click();
		    driver.findElements(By.xpath("//img[contains(@class,'edit')]"));
		    driver.findElements(By.xpath("//img[contains(@class,'delete')]"));
		    // Thread.sleep(1000);
	    	for (int i = 0; i < BlockList.size(); i++) {
		    	new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText(BlockList.get(i)[0].toString());
		    	new Select(driver.findElement(By.id("cbbAreaName"))).selectByVisibleText(BlockList.get(i)[1].toString());
		    	driver.findElement(By.id("btnSearch")).click();

		    	driver.findElement(By.id("btnRegister")).click();
			    driver.findElement(By.id("txtBlockNamePopup")).clear();
			    driver.findElement(By.id("txtBlockNamePopup")).sendKeys("ブロック_2-2-5");
			    driver.findElement(By.id("btnRegisterPopup")).click();
			    driver.findElement(By.id("popup_ok"));
			    driver.findElement(By.id("popup_ok")).click();
			    OTCommon.capture(driver, imagePath + "3.フィールド設定/" + "0009_" + i);
		    }
 
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
			    	OTCommon.scrollVSCapture(driver, imagePath + "3.フィールド設定/" + "21_" + i + "_" + b, "divBodyProduct", "document.getElementsByClassName('rowImgProduct')[document.getElementsByClassName('rowImgProduct').length-1]");

				    driver.findElement(By.id("btnCancelProductPopup")).click();
		    	}
		    }
			//▲▲▲--3.フィールド設定--▲▲▲

		    driver.findElement(By.id("btnGeneralSetting")).click();

		    //▼▼▼--4.ユーザとフィールドの紐付け--▼▼▼
			//0087 所属情報マスタ画面
		    driver.findElement(By.id("btnManageScreen")).click();
		    driver.findElements(By.xpath("//img[contains(@class,'edit')]"));
		    driver.findElements(By.xpath("//img[contains(@class,'delete')]"));
		    // Thread.sleep(1000);
		    for (int i = 2; i < UsersList.size(); i++) {
		    	String xPathSelector = "//td/input[@value='" + UsersList.get(i)[0].toString() + "']/parent::td/parent::tr/td/img[contains(@class, 'edit')]";
		    	driver.findElement(By.xpath(xPathSelector)).click();
		    	if (UsersList.get(i)[3].toString().equalsIgnoreCase("3")) {
		    		new Select(driver.findElement(By.id("cbbAffilNamePopup"))).selectByVisibleText("Quản lý nông trại");
		    		xPathSelector = "//td/a[text()='" + UsersList.get(i)[4].toString() + "']/parent::td/parent::tr/td/input[contains(@type, 'radio')]";
		    		driver.findElement(By.xpath(xPathSelector)).click();
		    	} else {
		    		new Select(driver.findElement(By.id("cbbAffilNamePopup"))).selectByVisibleText("Quản lý lô");
		    		xPathSelector = "//td/a[text()='" + UsersList.get(i)[4].toString() + "']/parent::td/parent::tr/td/input[contains(@type, 'radio')]";
		    		driver.findElement(By.xpath(xPathSelector)).click();
		    		xPathSelector = "//td[text()='" + UsersList.get(i)[5].toString() + "']/parent::tr/td/input[contains(@type, 'checkbox')]";
		    		driver.findElement(By.xpath(xPathSelector)).click();
		    	}
		    	driver.findElement(By.id("btn-submit")).click();
		    	driver.findElement(By.id("popup_ok"));
			    driver.findElement(By.id("popup_ok")).click();
		    }
		    OTCommon.capture(driver, imagePath + "4.ユーザとフィールドの紐付け/" + "0087");

		    driver.findElement(By.id("btnGeneralSetting")).click();
			//0007 エリア情報マスタ画面
			driver.findElement(By.id("btnAreaScreen")).click();
			driver.findElements(By.xpath("//img[contains(@class,'edit')]"));
		    driver.findElements(By.xpath("//img[contains(@class,'delete')]"));
			for (int i = 0; i < UsersList.size(); i++) {
				if (UsersList.get(i)[3].toString().equalsIgnoreCase("4")) {
					new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText(UsersList.get(i)[4].toString());
				    driver.findElement(By.id("btnSearch")).click();
				    Thread.sleep(1000);
			    	String xPathSelector = "//td/a[text()='" + UsersList.get(i)[5].toString() + "']/parent::td/parent::tr/td/img[contains(@class, 'edit')]";
			    	driver.findElement(By.xpath(xPathSelector)).click();

					driver.findElement(By.xpath("//div[@id='popupWrapper'][contains(@style, 'display: block')]"));

			    	driver.findElement(By.id("loadUserPopup")).click();

			    	xPathSelector = "//td[text()='" + UsersList.get(i)[0].toString() + "']/parent::tr";
			    	driver.findElement(By.xpath(xPathSelector)).click();

			    	driver.findElement(By.id("btnScUserPupSelect")).click();
			    	OTCommon.capture(driver, imagePath + "4.ユーザとフィールドの紐付け/" + "0007_" + i);
				    driver.findElement(By.id("btnRegisterPopup")).click();
				    driver.findElement(By.id("popup_ok"));
				    driver.findElement(By.id("popup_ok")).click();
				}
		    }

			//▲▲▲--4.ユーザとフィールドの紐付け--▲▲▲
			driver.findElement(By.id("btnGeneralSetting")).click();
			
			//▼▼▼--5.作業項目の設定--▼▼▼
			//0013 プロセスマスタ画面
		    driver.findElement(By.id("btnProcessScreen")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok"));
		    driver.findElement(By.id("popup_ok")).click();

		    for (int i = 0; i < ProcessList.size(); i++) {
		    	driver.findElement(By.id("btnRegister")).click();
			    driver.findElement(By.id("txtProcessNamePopup")).clear();
			    driver.findElement(By.id("txtProcessNamePopup")).sendKeys(ProcessList.get(i)[0].toString());
			    driver.findElement(By.id("btnRegisterPopup")).click();
			    driver.findElement(By.id("popup_ok"));
			    driver.findElement(By.id("popup_ok")).click();
		    }
		    OTCommon.capture(driver, imagePath + "5.作業項目の設定/" + "0013");

		    driver.findElement(By.id("btnGeneralSetting")).click();
			//0089 タスクマスタ画面
		    driver.findElement(By.id("btnTaskScreen")).click();
		    // Thread.sleep(1000);
		    driver.findElement(By.id("popup_ok"));
		    driver.findElement(By.id("popup_ok")).click();
		    for (int i = 0; i < ProcessList.size(); i++) {
		    	for (int j = 0; j < ((List<Object[]>) ProcessList.get(i)[1]).size(); j++) {
		    		Object[] TaskList = ((List<Object[]>) ProcessList.get(i)[1]).get(j);

		    		driver.findElement(By.id("btnRegister")).click();
				    driver.findElement(By.id("txtTaskNamePopup")).clear();
				    driver.findElement(By.id("txtTaskNamePopup")).sendKeys(TaskList[0].toString());
				    driver.findElement(By.id("txtWorkingDetailsPopup")).clear();
				    if (TaskList[1].toString().equalsIgnoreCase("")) {
				    	driver.findElement(By.id("txtWorkingDetailsPopup")).sendKeys(TaskList[0].toString());
				    } else {
				    	driver.findElement(By.id("txtWorkingDetailsPopup")).sendKeys(TaskList[1].toString());
				    }

				    if (!TaskList[2].toString().equalsIgnoreCase("")) {
				    	driver.findElement(By.id("txtNotePopup")).sendKeys(TaskList[2].toString());
				    }

				    if (TaskList[4].toString().equalsIgnoreCase("1")) {
				    	driver.findElement(By.id("chbQuarantineHandlingFlagPopup")).click();
				    }

				    if (TaskList[5].toString().equalsIgnoreCase("")) {
				    	new Select(driver.findElement(By.id("cbbChangePointCode"))).selectByVisibleText("なし");
				    } else {
				    	new Select(driver.findElement(By.id("cbbChangePointCode"))).selectByVisibleText(TaskList[5].toString());
				    }
				    driver.findElement(By.id("btnRegisterPopup")).click();
				    driver.findElement(By.id("popup_ok"));
				    driver.findElement(By.id("popup_ok")).click();
		    	}
		    }
		    Thread.sleep(1000);
		    for (int i = Integer.parseInt(driver.findElement(By.id("lblCurrentPage")).getText()); i <= Integer.parseInt(driver.findElement(By.id("lblMaxPage")).getText()); ) {
		    	i = Integer.parseInt(driver.findElement(By.id("lblCurrentPage")).getText());
		    	List<WebElement> elementList = driver.findElements(By.xpath("//table[@id='tblBody']/tbody/tr"));

		    	OTCommon.scrollVSCapture(driver, imagePath + "5.作業項目の設定/" + "0089_" + i, "divBody", "document.getElementById('" + elementList.get(elementList.size()-1).getAttribute("id") + "')");
		    	
			    if (i == Integer.parseInt(driver.findElement(By.id("lblMaxPage")).getText())) {
			    	break;
			    }
			    driver.findElement(By.id("btnNext")).click();
		    }
		    
		    driver.findElement(By.id("btnGeneralSetting")).click();

		    //0091 栽培マスタ画面
		    driver.findElement(By.id("btnCultivationScreen")).click();
		    // Thread.sleep(1000);
		    for (int l = 0; l < AreaList.size(); l++) {
		    	new Select(driver.findElement(By.id("cbbFarmName"))).selectByVisibleText(AreaList.get(l)[0].toString());
		    	new Select(driver.findElement(By.id("cbbKindName"))).selectByVisibleText(AreaList.get(l)[3].toString());
			    for (int i = 0; i < ProcessList.size(); i++) {
			    	new Select(driver.findElement(By.id("cbbProcessName"))).selectByVisibleText(ProcessList.get(i)[0].toString());
			    	for (int j = 0; j < ((List<Object[]>) ProcessList.get(i)[1]).size(); j++) {
			    		Object[] TaskList = ((List<Object[]>) ProcessList.get(i)[1]).get(j);
			    		String xPathSelector = "//table[contains(@id,'tblBody')]/tbody/tr/td[text()='" + TaskList[0].toString() + "']";

			    		if (TaskList[0].toString().equalsIgnoreCase("・穴掘り")) {
				    		List<WebElement> elementList = driver.findElements(By.xpath(xPathSelector));
				    		WebElement needElement = null; 
				    		for (int k = 0; k < elementList.size(); k++) {
					    		elementList.get(k).click();
					    		if (driver.findElement(By.id("trFlag")).getText().equalsIgnoreCase("検疫対応 なし")) {
					    			if (ProcessList.get(i)[0].toString().equalsIgnoreCase("農園に苗植え")){
					    				needElement = elementList.get(k);
					    				elementList.get(k).click();
					    			}
					    		} else if (driver.findElement(By.id("trFlag")).getText().equalsIgnoreCase("検疫対応 あり")) {				    			
					    			if (ProcessList.get(i)[0].toString().equalsIgnoreCase("土壌づくり")){
					    				needElement = elementList.get(k);
					    				elementList.get(k).click();
					    			}
					    		}
					    	}
				    		needElement.click();
				    		driver.findElement(By.id("btn_get")).click();
			    		} else {
			    			driver.findElement(By.xpath(xPathSelector)).click();
			    			driver.findElement(By.id("btn_get")).click();
			    		}

			    		xPathSelector = "//table[contains(@id,'tblBodyRight')]/tbody/tr/td[text()='" + TaskList[0].toString() + "']/parent::tr/td/input[contains(@type,'checkbox')]";
			    		if (TaskList[3].toString().equalsIgnoreCase("0")) {
			    			driver.findElement(By.xpath(xPathSelector)).click();
			    		}
			    	}			    	
			    	driver.findElement(By.id("btnRegister")).click();			    	
			    	driver.findElement(By.id("popup_ok"));
				    driver.findElement(By.id("popup_ok")).click();
				    OTCommon.capture(driver, imagePath + "5.作業項目の設定/" + "0091_" + l + "_" + i);
			    }

			    //0092 栽培マスタ画面
		    	driver.findElement(By.id("btnNext")).click();
		    	// Thread.sleep(1000);
		    	for (int i = 0; i < ProcessList.size(); i++) {
		    		String xPathSelector = "//td[text()='" + ProcessList.get(i)[0].toString() + "']";
		    		driver.findElement(By.xpath(xPathSelector)).click();
		    		driver.findElement(By.id("btn_get")).click();
		    	}		    	
		    	driver.findElement(By.id("btnRegister")).click();
		    	driver.findElement(By.id("popup_ok"));
			    driver.findElement(By.id("popup_ok")).click();
			    OTCommon.capture(driver, imagePath + "5.作業項目の設定/" + "0092_" + l);
			    driver.findElement(By.id("btnBack")).click();
		    }
			//▲▲▲--5.作業項目の設定--▲▲▲
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
