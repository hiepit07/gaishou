package vn.bananavietnam.ict.common.aop;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * The class <code>BeanNameGeneratorTest</code> contains tests for the class <code>{@link BeanNameGeneratorTest}</code>.
 *
 * @generatedBy CodePro at 17/05/04 15:46
 * @author pc30
 * @version $Revision: 1.0 $
 */
public class BeanNameGeneratorTest {
	

	/**
	 * Run the String buildDefaultBeanName(BeanDefinition definition) method test.
	 *
	 */
	@Test
	public void testBuildDefaultBeanName() throws Exception {
		BeanNameGenerator bng = new BeanNameGenerator();
		BeanDefinition definition = Mockito.mock(BeanDefinition.class);
		when(definition.getBeanClassName()).thenReturn("server_setting");
		bng.buildDefaultBeanName(definition);
	}

}