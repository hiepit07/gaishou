package vn.bananavietnam.ict.common.component;

import java.util.Map;
import org.easymock.EasyMock;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>XmlConfigTest</code> contains tests for the class <code>{@link XmlConfig}</code>.
 *
 * @generatedBy CodePro at 17/05/04 17:14
 * @author pc30
 * @version $Revision: 1.0 $
 */
public class XmlConfigTest {
	/**
	 * Run the XmlConfig() constructor test.
	 *
	 * @generatedBy CodePro at 17/05/04 17:14
	 */
	@Test
	public void testXmlConfig_1()
		throws Exception {
		XmlConfig result = new XmlConfig();
		assertNotNull(result);
		// add additional test code here
	}

	/**
	 * Run the long getLastModified() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 17/05/04 17:14
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetLastModified_1()
		throws Exception {
		XmlConfig fixture = new XmlConfig();
		fixture.setLastModified(1L);
		fixture.mode = EasyMock.createNiceMock(Map.class);

		long result = fixture.getLastModified();

		// add additional test code here
		assertEquals(1L, result);
	}

	/**
	 * Run the String[] getScreenList(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 17/05/04 17:14
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetScreenList_1()
		throws Exception {
		XmlConfig fixture = new XmlConfig();
		fixture.setLastModified(1L);
		fixture.mode = EasyMock.createNiceMock(Map.class);
		String name = "";

		String[] result = fixture.getScreenList(name);

		// add additional test code here
		assertNotNull(result);
		assertEquals(0, result.length);
	}

	/**
	 * Run the String[] getScreenList(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 17/05/04 17:14
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetScreenList_2()
		throws Exception {
		XmlConfig fixture = new XmlConfig();
		fixture.setLastModified(1L);
		fixture.mode = EasyMock.createNiceMock(Map.class);
		String name = "";

		String[] result = fixture.getScreenList(name);

		// add additional test code here
		assertNotNull(result);
		assertEquals(0, result.length);
	}

	/**
	 * Run the void setLastModified(long) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 17/05/04 17:14
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testSetLastModified_1()
		throws Exception {
		XmlConfig fixture = new XmlConfig();
		fixture.setLastModified(1L);
		fixture.mode = EasyMock.createNiceMock(Map.class);
		long lastModified = 1L;

		fixture.setLastModified(lastModified);

		// add additional test code here
	}
}