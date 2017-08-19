package vn.bananavietnam.ict.common.aop;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * The class <code>LogSessionIdFilterTest</code> contains tests for the class <code>{@link LogSessionIdFilter}</code>.
 *
 * @generatedBy CodePro at 17/05/04 15:46
 * @author pc30
 * @version $Revision: 1.0 $
 */
public class LogSessionIdFilterTest {
	/**
	 * Run the void destroy() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 17/05/04 15:46
	 */
	@Test
	public void testDestroy_1()
		throws Exception {
		LogSessionIdFilter fixture = new LogSessionIdFilter();

		fixture.destroy();

		// add additional test code here
	}

	/**
	 * Run the void doFilter(ServletRequest,ServletResponse,FilterChain) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 17/05/04 15:46
	 */
	@Test
	public void testDoFilter_1()
		throws Exception {
		LogSessionIdFilter fixture = new LogSessionIdFilter();
		ServletRequest request = new MockHttpServletRequest();
		ServletResponse response = new MockHttpServletResponse();
		FilterChain chain = new MockFilterChain();

		fixture.doFilter(request, response, chain);

		// add additional test code here
	}

	/**
	 * Run the void doFilter(ServletRequest,ServletResponse,FilterChain) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 17/05/04 15:46
	 */
	@Test
	public void testDoFilter_2()
		throws Exception {
		LogSessionIdFilter fixture = new LogSessionIdFilter();
		ServletRequest request = new MockHttpServletRequest();
		ServletResponse response = new MockHttpServletResponse();
		FilterChain chain = new MockFilterChain();

		fixture.doFilter(request, response, chain);

		// add additional test code here
	}

	/**
	 * Run the void doFilter(ServletRequest,ServletResponse,FilterChain) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 17/05/04 15:46
	 */
	@Test
	public void testDoFilter_3()
		throws Exception {
		LogSessionIdFilter fixture = new LogSessionIdFilter();
		ServletRequest request = new MockHttpServletRequest();
		ServletResponse response = new MockHttpServletResponse();
		FilterChain chain = new MockFilterChain();

		fixture.doFilter(request, response, chain);

		// add additional test code here
	}

	/**
	 * Run the void doFilter(ServletRequest,ServletResponse,FilterChain) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 17/05/04 15:46
	 */
	@Test
	public void testDoFilter_4()
		throws Exception {
		LogSessionIdFilter fixture = new LogSessionIdFilter();
		ServletRequest request = new MockHttpServletRequest();
		ServletResponse response = new MockHttpServletResponse();
		FilterChain chain = new MockFilterChain();

		fixture.doFilter(request, response, chain);

		// add additional test code here
	}

	/**
	 * Run the void doFilter(ServletRequest,ServletResponse,FilterChain) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 17/05/04 15:46
	 */
	@Test
	public void testDoFilter_5()
		throws Exception {
		LogSessionIdFilter fixture = new LogSessionIdFilter();
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		ServletResponse response = new MockHttpServletResponse();
		FilterChain chain = new MockFilterChain();

		fixture.doFilter(request, response, chain);

		// add additional test code here
	}
	
	/**
	 * Run the void init(FilterConfig) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 17/05/04 15:46
	 */
	@Test
	public void testInit_1()
		throws Exception {
		LogSessionIdFilter fixture = new LogSessionIdFilter();
		FilterConfig arg0 = new MockFilterConfig();

		fixture.init(arg0);

		// add additional test code here
	}
}