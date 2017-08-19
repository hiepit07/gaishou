package OT;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.management.relation.RoleList;

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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import vn.bananavietnam.ict.common.cnst.Constants;

@RunWith(Parameterized.class)
public class OT_1Kurume {
	private static WebDriver driver;
	private String baseUrl;
	private String screen0035 = "0035";
	private String screen0037 = "0037";

	private List<Object[]> userList = Arrays.asList(new Object[][] { 
		//{"FManager1", "view"},
		{"AManager11", "task"},
		{"AManager12", "task"},
		//{"FManager2", "view"},
		{"AManager21", "view"},
		{"AManager22", "view"}
	});

	private List<Object[]> TaskList = Arrays.asList(new Object[][] {
		{"1月", 
			Arrays.asList(new Object[][] {
				// 植え前  開始
				{1, Arrays.asList(new Object[][] {
					{"土壌づくり", "・畝づくり", "植え前", screen0035}
				})},
				{2, Arrays.asList(new Object[][] {
					{"土壌づくり", "・穴掘り", "植え前", screen0035}
				})},
				{3, Arrays.asList(new Object[][] {
					{"土壌づくり", "・苗のTAG付け", "植え前", screen0035}
				})},
				{4, Arrays.asList(new Object[][] {
					{"土壌づくり", "・排水溝工事", "植え前", screen0035}
				})},
				{5, Arrays.asList(new Object[][] {
					{"土壌づくり", "・灌漑づくり", "植え前", screen0035}
				})},
				{6, Arrays.asList(new Object[][] {
				})},
				{7, Arrays.asList(new Object[][] {
				})},
				{8, Arrays.asList(new Object[][] {
					{"苗選択", "・新芽の選択", "植え前", screen0035}
				})},
				{9, Arrays.asList(new Object[][] {
					{"苗選択", "・苗床の植え", "植え前", screen0035}
				})},
				{10, Arrays.asList(new Object[][] {
					{"農園に苗植え", "・穴掘り", "植え前", screen0035}
				})},
				{11, Arrays.asList(new Object[][] {
					{"農園に苗植え", "・穴に肥料の入れ", "植え前", screen0035}
				})},
				{12, Arrays.asList(new Object[][] {
				})},
				{13, Arrays.asList(new Object[][] {
				})},
				{14, Arrays.asList(new Object[][] {
				})},
				// 植え前  終了
				//-------------------------
				// 育成中  開始
				{15, Arrays.asList(new Object[][] {
					{"農園に苗植え", "・穴に苗の入れ", "育成中", screen0035},
					{"肥料の撒き", "・苗のとき肥料撒き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 1週目～4週目", "育成中", screen0035},
				})},
				{16, Arrays.asList(new Object[][] {
					{"肥料の撒き", "・肥料の撒き　１５日：一株あたり", "育成中", screen0035},
					{"水やり", "・乾季の水やり 1週目～4週目", "育成中", screen0035}
				})},
				{17, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 1週目～4週目", "育成中", screen0035}
				})},
				{18, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 1週目～4週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{19, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 1週目～4週目", "育成中", screen0035}
				})},
				{20, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 1週目～4週目", "育成中", screen0035}
				})},
				{21, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 1週目～4週目", "育成中", screen0035}
				})},
				{22, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 1週目～4週目", "育成中", screen0035}
				})},
				{23, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 1週目～4週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{24, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 1週目～4週目", "育成中", screen0035}
				})},
				{25, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 1週目～4週目", "育成中", screen0035}
				})},
				{27, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 1週目～4週目", "育成中", screen0035}
				})},
				{26, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 1週目～4週目", "育成中", screen0035}
				})},
				{28, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 1週目～4週目", "育成中", screen0035}
				})}
			}) 
		},
		{"2月", 
			Arrays.asList(new Object[][] {
				{1, Arrays.asList(new Object[][] {
					{"肥料の撒き", "・肥料の撒き　１か月：一株あたり", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{2, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{3, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{4, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{5, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{6, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{7, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{8, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{9, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{10, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{11, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{12, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{13, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{14, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{15, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{16, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{17, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{18, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{19, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{20, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{21, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{22, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{23, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{24, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{25, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{26, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{27, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{28, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})}
			}) 
		},
		{"3月", 
			Arrays.asList(new Object[][] {
				{1, Arrays.asList(new Object[][] {
					{"肥料の撒き", "・肥料の撒き　２か月：一株あたり", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{2, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{3, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{4, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{5, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{6, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{7, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 5週目～9週目", "育成中", screen0035}
				})},
				{8, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{9, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{10, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{11, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{12, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{13, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{14, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{15, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{16, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{17, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{18, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{19, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{20, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{21, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{22, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{23, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{24, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{25, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{26, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{27, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{28, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})}
			}) 
		},
		{"4月", 
			Arrays.asList(new Object[][] {
				{1, Arrays.asList(new Object[][] {
					{"肥料の撒き", "・肥料の撒き　３か月：一株あたり", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{2, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{3, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{4, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{5, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{6, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{7, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{8, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{9, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{10, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{11, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{12, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{13, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{14, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{15, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{16, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{17, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{18, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{19, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{20, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{21, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{22, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{23, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{24, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{25, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{26, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{27, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{28, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})}
			}) 
		},
		{"5月", 
			Arrays.asList(new Object[][] {
				{1, Arrays.asList(new Object[][] {
					{"肥料の撒き", "・肥料の撒き　４か月：一株あたり", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{2, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{3, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{4, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{5, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{6, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{7, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{8, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{9, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{10, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{11, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{12, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{13, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{14, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{15, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{16, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{17, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{18, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{19, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{20, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{21, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 10週目～19週目", "育成中", screen0035}
				})},
				{22, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{23, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{24, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{25, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{26, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{27, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{28, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})}
			}) 
		},
		{"6月", 
			Arrays.asList(new Object[][] {
				{1, Arrays.asList(new Object[][] {
					{"肥料の撒き", "・肥料の撒き　５か月：一株あたり", "育成中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{2, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{3, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{4, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{5, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{6, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{7, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{8, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "育成中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{9, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "育成中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035},
					{"苗の管理", "・株の支え", "育成中", screen0037, "L003"}
				})},
				{10, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "育成中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{11, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{12, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{13, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				{14, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "育成中", screen0035}
				})},
				// 育成中  終了
				//-------------------------
				// 開花中  開始
				{15, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "開花中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "開花中", screen0035},
					{"苗の管理", "開花日登録", "開花中", screen0037, "L003"}
				})},
				{16, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "開花中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "開花中", screen0035}
				})},
				{17, Arrays.asList(new Object[][] {
					{"苗の管理", "花が咲いたら台芽を、一本除いて全て切り取る", "開花中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "開花中", screen0035},
					{"苗の管理", "花びらの取り除き", "開花中", screen0037, "L003"}
				})},
				{18, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "開花中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "開花中", screen0035},
					{"苗の管理", "雄しべの取り除き", "開花中", screen0037, "L003"}
				})},
				{19, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "開花中", screen0035},
					{"苗の管理", "・株の支え", "開花中", screen0037, "L003"}
				})},
				{20, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "開花中", screen0035}
				})},
				{21, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "開花中", screen0035}
				})},
				// 開花中  終了
				//-------------------------
				// 袋掛中  開始
				{22, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035},
					{"苗の管理", "房の覆い", "袋掛中", screen0037, "L003"}
				})},
				{23, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035},
					{"苗の管理", "・株の支え", "袋掛中", screen0037, "L003"}
				})},
				{24, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{25, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{26, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{27, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{28, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})}
			}) 
		},
		{"7月", 
			Arrays.asList(new Object[][] {
				{1, Arrays.asList(new Object[][] {
					{"肥料の撒き", "・肥料の撒き　６か月：一株あたり", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{2, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{3, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035},
					{"苗の管理", "・株の支え", "袋掛中", screen0037, "L003"}
				})},
				{4, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{5, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫の種類確認", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "罹患中", screen0035}
				})},
				{6, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫によって農薬適用　‐斑葉病", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{7, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{8, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{9, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035},
					{"苗の管理", "・株の支え", "袋掛中", screen0037, "L003"}
				})},
				{10, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{11, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫の種類確認", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "罹患中", screen0035}
				})},
				{12, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫によって農薬適用　‐炭疽病", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "罹患中", screen0035}
				})},
				{13, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{14, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{15, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{16, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035},
					{"苗の管理", "・株の支え", "袋掛中", screen0037, "L003"}
				})},
				{17, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{18, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫の種類確認", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "罹患中", screen0035}
				})},
				{19, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫によって農薬適用　‐バナナのパナマ病", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "罹患中", screen0035}
				})},
				{20, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{21, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{22, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{23, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035},
					{"苗の管理", "・株の支え", "袋掛中", screen0037, "L003"}
				})},
				{24, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{25, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫の種類確認", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "罹患中", screen0035}
				})},
				{26, Arrays.asList(new Object[][] {
					{"害虫の駆除", "害虫によって農薬適用　‐バンチートップ病", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "罹患中", screen0035}
				})},
				{27, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{28, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})}
			}) 
		},
		{"8月", 
			Arrays.asList(new Object[][] {
				{1, Arrays.asList(new Object[][] {
					{"肥料の撒き", "・肥料の撒き　７か月：一株あたり", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{2, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{3, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035},
					{"苗の管理", "・株の支え", "袋掛中", screen0037, "L003"}
				})},
				{4, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{5, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫の種類確認", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "罹患中", screen0035}
				})},
				{6, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫によって農薬適用　‐アブラムシ", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "罹患中", screen0035}
				})},
				{7, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{8, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{9, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035},
					{"苗の管理", "・株の支え", "袋掛中", screen0037, "L003"}
				})},
				{10, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{11, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫の種類確認", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "罹患中", screen0035}
				})},
				{12, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫によって農薬適用　‐根こぶ線虫病", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "罹患中", screen0035}
				})},
				{13, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035},
					{"苗の管理", "・病気の木を破壊する", "罹患中", screen0037, "L003:C002, L003:C004, L003:C006"}
				})},
				{14, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{15, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{16, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035},
					{"苗の管理", "・株の支え", "袋掛中", screen0037, "L003"}
				})},
				{17, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{18, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{19, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{20, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{21, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{22, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{23, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035},
					{"苗の管理", "・株の支え", "袋掛中", screen0037, "L003"}
				})},
				{24, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "袋掛中", screen0035},
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{25, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{26, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{27, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})},
				{28, Arrays.asList(new Object[][] {
					{"水やり", "・乾季の水やり 20週目～32週目", "袋掛中", screen0035}
				})}
			}) 
		},
		{"9月", 
			Arrays.asList(new Object[][] {
				{1, Arrays.asList(new Object[][] {
					{"肥料の撒き", "・肥料の撒き　８か月：一株あたり", "袋掛中", screen0035},
					{"水やり", "・雨季の水やり", "袋掛中", screen0035}
				})},
				{2, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "袋掛中", screen0035},
					{"水やり", "・雨季の水やり", "袋掛中", screen0035}
				})},
				{3, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "袋掛中", screen0035},
					{"水やり", "・雨季の水やり", "袋掛中", screen0035},
					{"苗の管理", "・株の支え", "袋掛中", screen0037, "L003"}
				})},
				{4, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "袋掛中", screen0035},
					{"水やり", "・雨季の水やり", "袋掛中", screen0035}
				})},
				{5, Arrays.asList(new Object[][] {
					{"水やり", "・雨季の水やり", "袋掛中", screen0035}
				})},
				{6, Arrays.asList(new Object[][] {
					{"水やり", "・雨季の水やり", "袋掛中", screen0035}
				})},
				{7, Arrays.asList(new Object[][] {
					{"水やり", "・雨季の水やり", "袋掛中", screen0035}
				})},
				{8, Arrays.asList(new Object[][] {
					{"苗の管理", "・不要な新芽の取り除き", "袋掛中", screen0035},
					{"水やり", "・雨季の水やり", "袋掛中", screen0035}
				})},
				{9, Arrays.asList(new Object[][] {
					{"苗の管理", "・毎月台芽の高さ３０ｃｍまで切り取り", "袋掛中", screen0035},
					{"水やり", "・雨季の水やり", "袋掛中", screen0035},
					{"苗の管理", "・株の支え", "袋掛中", screen0037, "L003"}
				})},
				{10, Arrays.asList(new Object[][] {
					{"害虫の駆除", "・害虫のサインチェック", "袋掛中", screen0035},
					{"水やり", "・雨季の水やり", "袋掛中", screen0035}
				})},
				{11, Arrays.asList(new Object[][] {
					{"水やり", "・雨季の水やり", "袋掛中", screen0035}
				})},
				{12, Arrays.asList(new Object[][] {
					{"水やり", "・雨季の水やり", "袋掛中", screen0035}
				})},
				{13, Arrays.asList(new Object[][] {
					{"水やり", "・雨季の水やり", "袋掛中", screen0035}
				})},
				{14, Arrays.asList(new Object[][] {
					{"水やり", "・雨季の水やり", "袋掛中", screen0035}
				})},
				// 袋掛中  終了
				//-------------------------
				// 収穫済  開始
				{15, Arrays.asList(new Object[][] {
					{"水洗い・選別", "・房の洗い流し", "収穫済", screen0035},
					{"水洗い・選別", "・バナナの傷／形のチェック", "収穫済", screen0035},
					{"収穫", "・房の切り取り", "収穫済", screen0037, "L003"},
					{"収穫", "・幹の上半分の切り倒し", "収穫済", screen0037, "L003"}
				})},
				{16, Arrays.asList(new Object[][] {
				})},
				{17, Arrays.asList(new Object[][] {
				})},
				{18, Arrays.asList(new Object[][] {
				})},
				{19, Arrays.asList(new Object[][] {
				})},
				// 収穫済  終了
				//-------------------------
				// 出荷済  開始
				{20, Arrays.asList(new Object[][] {
					{"計量・箱詰め", "・箱入れ", "出荷済", screen0035}
				})},
				{21, Arrays.asList(new Object[][] {
					{"輸出検査", "・輸出検査", "出荷済", screen0035},
					{"出港", "・港に運搬", "出荷済", screen0035}
				})}
				// 出荷済  終了
			}) 
		}
	});

	private String imagePath = "selenium_test_images/OT/1クール目/";

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "Admin", "Password" } });
	}
	@Parameter // first data value (0) is default
	public String fName;
	@Parameter(1)
	public String fPass;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");
		baseUrl = "http://172.16.0.175:8080";
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
			driver.get(baseUrl + "/farmjob/?language=vi");Thread.sleep(1000);

			

			boolean flag0037 = false;

			for(int month = 1; month <= 1 /*TaskList.size()*/; month++){
				List<Object[]> dayList = (List<Object[]>) TaskList.get(month-1)[1];
				for(int day = 1; day <= dayList.size(); day++){
					List<Object[]> taskList = (List<Object[]>) dayList.get(day-1)[1];
					for(int task = 1; task <= taskList.size(); task++){
						Object[] taskObject = taskList.get(task-1);
						for(int user = 0; user < userList.size(); user++){
							Object[] userObject = userList.get(user);

							System.err.println("Date   : " + day + "/" + month);
							System.err.println("User   : " + userObject[0].toString());
							System.err.println("Process: " + taskObject[0].toString());
							System.err.println("Task   : " + taskObject[1].toString());
							System.err.println("Status : " + taskObject[2].toString());
							System.err.println("Screen : " + taskObject[3].toString());
							driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));Thread.sleep(1000);

							driver.findElement(By.id("j_username")).clear();Thread.sleep(1000);
							driver.findElement(By.id("j_username")).sendKeys(userObject[0].toString());Thread.sleep(1000);
							driver.findElement(By.id("j_password")).clear();Thread.sleep(1000);
							driver.findElement(By.id("j_password")).sendKeys(userObject[0].toString());Thread.sleep(1000);
							((RemoteWebDriver) driver).executeScript("arguments[0].click()",driver.findElement(By.id("btnLogin")));Thread.sleep(1000);
							
							if(taskObject[3].toString().equals(screen0035)){
							    driver.findElement(By.id("btnRegister")).click();Thread.sleep(1000);
							    driver.findElement(By.cssSelector("img.ui-datepicker-trigger")).click();Thread.sleep(1000);
							    new Select(driver.findElement(By.cssSelector(".ui-datepicker-month"))).selectByValue(String.valueOf(month - 1));Thread.sleep(1000);
							    new Select(driver.findElement(By.cssSelector(".ui-datepicker-year"))).selectByVisibleText("2017");Thread.sleep(1000);
							    driver.findElement(By.linkText(String.valueOf(day))).click();Thread.sleep(1000);
							    new Select(driver.findElement(By.id("cbbProcessPopup"))).selectByVisibleText(taskObject[0].toString());Thread.sleep(1000);
							    System.err.println("Step1"+ day + "/" + month);
							    selectValue(driver, "cbbTaskPopup", taskObject[1].toString());Thread.sleep(1000);
							    System.err.println("Step2"+ day + "/" + month);
							    new Select(driver.findElement(By.id("cbbStatusPopup"))).selectByVisibleText(taskObject[2].toString());Thread.sleep(1000);
							    System.err.println("Step3"+ day + "/" + month);
							    driver.findElement(By.id("btnRegisterPopup")).click();Thread.sleep(1000);
							    driver.findElement(By.id("popup_ok")).click();Thread.sleep(1000);
							} else {
								
								if(taskObject[4].toString().length() == 4){
									System.err.println("0037" + day + "/" + month);
									if(!flag0037){
										driver.findElement(By.linkText("B001")).click();Thread.sleep(1000);
										flag0037 = true;
										System.err.println("Click Block" + day + "/" + month);
									}
	
									System.err.println("Add Task" + day + "/" + month);
								    driver.findElement(By.id("btnRegister")).click();Thread.sleep(1000);
								    driver.findElement(By.cssSelector("img.ui-datepicker-trigger")).click();Thread.sleep(1000);
								    new Select(driver.findElement(By.cssSelector(".ui-datepicker-month"))).selectByValue(String.valueOf(month - 1));Thread.sleep(1000);
								    new Select(driver.findElement(By.cssSelector(".ui-datepicker-year"))).selectByVisibleText("2017");Thread.sleep(1000);
								    driver.findElement(By.linkText(String.valueOf(day))).click();Thread.sleep(2000);
								    new Select(driver.findElement(By.id("cbbProcessPopup"))).selectByVisibleText(taskObject[0].toString());Thread.sleep(1000);
								    selectValue(driver, "cbbTaskPopup", taskObject[1].toString());Thread.sleep(1000);
								    new Select(driver.findElement(By.id("cbbStatusPopup"))).selectByVisibleText(taskObject[2].toString());Thread.sleep(1000);
								    driver.findElement(By.id("btnRegisterPopup")).click();Thread.sleep(1000);
								    driver.findElement(By.id("popup_ok")).click();Thread.sleep(1000);
								    System.err.println("End Add Task" + day + "/" + month);
								    
								    if(taskObject[1].toString().equals("・幹の上半分の切り倒し")){
								    	driver.findElement(By.id("btnShippingScreen")).click();Thread.sleep(1000);
								    	driver.findElement(By.cssSelector("a.edit")).click();Thread.sleep(1000);Thread.sleep(1000);
								    	String shippingDate = String.format("%0" + 2 + "d", day) + String.format("%0" + 2 + "d", month) + "2017";
								    	driver.findElement(By.id("shipDatePopup")).clear();
									    driver.findElement(By.id("shipDatePopup")).sendKeys(shippingDate);
								    	driver.findElement(By.id("btnRegisterPopup")).click();Thread.sleep(1000);
								    	driver.findElement(By.id("popup_ok")).click();Thread.sleep(1000);
								    	driver.findElement(By.id("btnTaskManagement")).click();Thread.sleep(1000);
								    	flag0037 = false;
								    } else if(task == taskList.size()){
										driver.findElement(By.id("btnBack")).click();Thread.sleep(1000);
										flag0037 = false;
									}
								} else if(taskObject[4].toString().length() > 4){
									if(!flag0037){
										driver.findElement(By.linkText("B001")).click();Thread.sleep(1000);
										flag0037 = true;
									}
								    driver.findElement(By.id("btnDeselectAll")).click();Thread.sleep(1000);
								    String[] treeArray = taskObject[4].toString().split(", ");
								    for(int treeNo = 0; treeNo < treeArray.length; treeNo++){
								    	driver.findElement(By.name(treeArray[treeNo])).click();Thread.sleep(1000);
								    }
	
								    driver.findElement(By.id("btnRegister")).click();Thread.sleep(1000);
								    driver.findElement(By.cssSelector("img.ui-datepicker-trigger")).click();Thread.sleep(1000);
								    new Select(driver.findElement(By.cssSelector(".ui-datepicker-month"))).selectByValue(String.valueOf(month - 1));Thread.sleep(1000);
								    new Select(driver.findElement(By.cssSelector(".ui-datepicker-year"))).selectByVisibleText("2017");Thread.sleep(1000);
								    driver.findElement(By.linkText(String.valueOf(day))).click();Thread.sleep(1000);
								    new Select(driver.findElement(By.id("cbbProcessPopup"))).selectByVisibleText(taskObject[0].toString());Thread.sleep(3000);
								    selectValue(driver, "cbbTaskPopup", taskObject[1].toString());Thread.sleep(1000);
								    new Select(driver.findElement(By.id("cbbStatusPopup"))).selectByVisibleText(taskObject[2].toString());Thread.sleep(3000);
								    driver.findElement(By.id("btnRegisterPopup")).click();Thread.sleep(1000);
								    driver.findElement(By.id("popup_ok")).click();Thread.sleep(1000);
	
								    if(taskObject[1].toString().equals("・病気の木を破壊する")){
								    	((RemoteWebDriver) driver).executeScript("arguments[0].click()",driver.findElement(By.id("linkLogout")));Thread.sleep(1000);
								    	driver.findElement(By.id("popup_ok")).click();Thread.sleep(1000);
								    	driver.get(baseUrl + "/maintenance/?language=vi");Thread.sleep(1000);
	
								    	driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));Thread.sleep(1000);
	
										driver.findElement(By.id("j_username")).clear();Thread.sleep(1000);
										driver.findElement(By.id("j_username")).sendKeys("SManager");Thread.sleep(1000);
										driver.findElement(By.id("j_password")).clear();Thread.sleep(1000);
										driver.findElement(By.id("j_password")).sendKeys("SManager");Thread.sleep(1000);
										((RemoteWebDriver) driver).executeScript("arguments[0].click()",driver.findElement(By.id("btnLogin")));
	
										driver.findElement(By.id("btnBlockScreen")).click();Thread.sleep(1000);
										driver.findElement(By.linkText("B001")).click();Thread.sleep(1000);
	
										for(int treeNo = 0; treeNo < treeArray.length; treeNo++){
									    	driver.findElement(By.name(treeArray[treeNo])).click();Thread.sleep(1000);
									    }
	
										driver.findElement(By.id("btnDisableProductPopup")).click();Thread.sleep(1000);
										driver.findElement(By.id("popup_ok")).click();Thread.sleep(1000);
										((RemoteWebDriver) driver).executeScript("arguments[0].click()",driver.findElement(By.id("linkLogout")));Thread.sleep(1000);
								    	driver.findElement(By.id("popup_ok")).click();Thread.sleep(1000);
								    	driver.get(baseUrl + "/farmjob/?language=vi");Thread.sleep(1000);
	
										driver.switchTo().frame(driver.findElement(By.id("wrapperFrame")));Thread.sleep(1000);
	
										driver.findElement(By.id("j_username")).clear();Thread.sleep(1000);
										driver.findElement(By.id("j_username")).sendKeys("AManager11");Thread.sleep(1000);
										driver.findElement(By.id("j_password")).clear();Thread.sleep(1000);
										driver.findElement(By.id("j_password")).sendKeys("AManager11");Thread.sleep(1000);
										((RemoteWebDriver) driver).executeScript("arguments[0].click()",driver.findElement(By.id("btnLogin")));Thread.sleep(1000);
										flag0037 = false;
								    } else if(task == taskList.size()){
										driver.findElement(By.id("btnBack")).click();Thread.sleep(1000);
										flag0037 = false;
									}
								} else {
									break;
								}
							}

							((RemoteWebDriver) driver).executeScript("arguments[0].click()",driver.findElement(By.id("linkLogout")));Thread.sleep(1000);
					    	driver.findElement(By.id("popup_ok")).click();Thread.sleep(1000);
						}
					}
				}
			}
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

	public static WebElement findElementSafe(WebDriver driver, By by) {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		try {
            return driver.findElement(by);
        }
        catch (NoSuchElementException ex) {
            return null;
        }
    }

	public static void selectValue(WebDriver driver, String comboboxId, String cmboboxText) {
		((RemoteWebDriver) driver).executeScript(
				"var selectOption = document.getElementById('" + comboboxId + "');"
			  + "var i = 0;"
			  + "for(i; i < selectOption.options.length; i++) {"
			  	+ "if (selectOption.options[i].text === '" + cmboboxText + "'){"
			  		+ "selectOption.selectedIndex = i;"
			  		+ "break;"
			  	+ "}"
	  		  + "}"
		);
	}
}
