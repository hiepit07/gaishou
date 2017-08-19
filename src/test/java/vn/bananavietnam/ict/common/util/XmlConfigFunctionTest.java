package vn.bananavietnam.ict.common.util;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import vn.bananavietnam.ict.common.component.XmlConfig;

public class XmlConfigFunctionTest {
	
	@Mock
	XmlConfigFunction xml = new XmlConfigFunction();
	
	@Mock
	XmlConfig xmlConfig = new XmlConfig();
	
	@Mock
	String filePath = new String();
	
	@Mock
	File xmlFile = new File("");
	
	@Test
	public void testSetFilePath() {
		xml.setFilePath("src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml");
		Assert.assertEquals("src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml", xml.getFilePath());
	}
	
	@Test
	public void testinitXmlConfig() {
		xml.setFilePath("src/test/java/vn/bananavietnam/ict/common/util/configright.xml");
		xml.initXmlConfig();
		
		xml.setFilePath("src/test/java/vn/bananavietnam/ict/common/util/configright.xml");
		xml.initXmlConfig();
	}
	
	@Test
	public void testinitXmlConfignull() {
		xml.setFilePath("src/test/java/vn/bananavietnam/ict/common/util/confignull.xml");
		xml.initXmlConfig();
		
		xml.setFilePath("src/test/java/vn/bananavietnam/ict/common/util/configright.xml");
		xml.initXmlConfig();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testgetScreenList() {
		xml.setFilePath("src/test/java/vn/bananavietnam/ict/common/util/configright.xml");
		Assert.assertEquals(new String[]{"0002","0035","0037","0045","0047","0049","0061","0075"}, 
				xml.getScreenList("farmjob"));
	}
}