package vn.bananavietnam.ict.common.component;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>FilePathTest</code> contains tests for the class <code>{@link FilePath}</code>.
 *
 * @generatedBy CodePro at 17/05/04 16:46
 * @author pc30
 * @version $Revision: 1.0 $
 */
public class FilePathTest {
	/**
	 * Run the String getConfigPath() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 17/05/04 16:46
	 */
	@Test
	public void testGetConfigPath_1()
		throws Exception {
		FilePath fixture = new FilePath();
		fixture.setConfigPath("");

		String result = fixture.getConfigPath();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the void setConfigPath(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 17/05/04 16:46
	 */
	@Test
	public void testSetConfigPath_1()
		throws Exception {
		FilePath fixture = new FilePath();
		fixture.setConfigPath("");
		String configPath = "";

		fixture.setConfigPath(configPath);

		// add additional test code here
	}
}