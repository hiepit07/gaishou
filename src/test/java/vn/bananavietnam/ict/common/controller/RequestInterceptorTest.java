package vn.bananavietnam.ict.common.controller;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.component.FilePath;
import vn.bananavietnam.ict.common.util.Util;
import vn.bananavietnam.ict.common.util.XmlConfigFunction;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class RequestInterceptorTest {
	@InjectMocks
	RequestInterceptor Rqi;
	
	@Mock
    private ApplicationContext appContext;
	
	@Mock
	private XmlConfigFunction xmlConfigFunction;
	
	@Mock
	private Util util = new Util();
	
	@Test
    public void testPreHandle1() throws Exception { 
		MockitoAnnotations.initMocks(this);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Object handler = Mockito.mock(Object.class);
		
		//mockito for xmlConfigFunction.setFilePath(((FilePath)appContext.getBean("xml_path")).getConfigPath());
		FilePath bean = Mockito.mock(FilePath.class);
		when(appContext.getBean(anyString())).thenReturn(bean);
		when(bean.getConfigPath()).thenReturn("path");
		
		//mockito for request.getRequestURL().toString();
		StringBuffer url = new StringBuffer("172.16.0.234:8080/ict/0045/");
		when(request.getRequestURL()).thenReturn(url);
		
		boolean ret = Rqi.preHandle(request, response, handler);
		assertFalse(ret);
	}
	
	@Test
    public void testPreHandle2() throws Exception { 
		MockitoAnnotations.initMocks(this);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Object handler = Mockito.mock(Object.class);
		
		//mockito for xmlConfigFunction.setFilePath(((FilePath)appContext.getBean("xml_path")).getConfigPath());
		FilePath bean = Mockito.mock(FilePath.class);
		when(appContext.getBean(anyString())).thenReturn(bean);
		when(bean.getConfigPath()).thenReturn("path");
		
		//mockito for request.getRequestURL().toString();
		StringBuffer url = new StringBuffer("172.16.0.234:8080/ict/00/");
		when(request.getRequestURL()).thenReturn(url);
		
		boolean ret = Rqi.preHandle(request, response, handler);
		assertTrue(ret);
	}
	
	@Test
    public void testPreHandle3() throws Exception { 
		MockitoAnnotations.initMocks(this);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Object handler = Mockito.mock(Object.class);
		
		//mockito for xmlConfigFunction.setFilePath(((FilePath)appContext.getBean("xml_path")).getConfigPath());
		FilePath bean = Mockito.mock(FilePath.class);
		when(appContext.getBean(anyString())).thenReturn(bean);
		when(bean.getConfigPath()).thenReturn("path");
		
		//mockito for request.getRequestURL().toString();
		StringBuffer url = new StringBuffer("172.16.0.234:8080/ict/0045/");
		when(request.getRequestURL()).thenReturn(url);
		
		//mockito for String contextPath = request.getContextPath().replace("/", "");
		when(request.getContextPath()).thenReturn("test");
		
		//mokito for String[] screenList = xmlConfigFunction.getScreenList(contextPath.toLowerCase());
		String[] screenList = new String[]{"0045", "0013"};
		when(xmlConfigFunction.getScreenList(anyString())).thenReturn(screenList);
		
		//mokito for util.getUserInfo().getSCREENID().contains(screenId)
		MyUser user = Mockito.mock(MyUser.class);
		ArrayList<String> arrStr = new ArrayList<String>();
		arrStr.add("0045");
		arrStr.add("0013");
		when(user.getSCREENID()).thenReturn(arrStr);
		when(util.getUserInfo()).thenReturn(user);
		
		//mokito for response.sendRedirect(request.getContextPath() + "/accessdenied");
				
		boolean ret = Rqi.preHandle(request, response, handler);
		assertTrue(ret);
	}
	
	@Test
    public void testPreHandle4() throws Exception { 
		MockitoAnnotations.initMocks(this);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Object handler = Mockito.mock(Object.class);
		
		//mockito for xmlConfigFunction.setFilePath(((FilePath)appContext.getBean("xml_path")).getConfigPath());
		FilePath bean = Mockito.mock(FilePath.class);
		when(appContext.getBean(anyString())).thenReturn(bean);
		when(bean.getConfigPath()).thenReturn("path");
		
		//mockito for request.getRequestURL().toString();
		StringBuffer url = new StringBuffer("172.16.0.234:8080/ict/0045/");
		when(request.getRequestURL()).thenReturn(url);
		
		//mockito for String contextPath = request.getContextPath().replace("/", "");
		when(request.getContextPath()).thenReturn("test");
		
		//mokito for String[] screenList = xmlConfigFunction.getScreenList(contextPath.toLowerCase());
		String[] screenList = new String[]{""};
		when(xmlConfigFunction.getScreenList(anyString())).thenReturn(screenList);
		
		//mokito for util.getUserInfo().getSCREENID().contains(screenId)
		MyUser user = Mockito.mock(MyUser.class);
		ArrayList<String> arrStr = new ArrayList<String>();
		arrStr.add("0045");
		arrStr.add("0013");
		when(user.getSCREENID()).thenReturn(arrStr);
		when(util.getUserInfo()).thenReturn(user);
		
		//mokito for response.sendRedirect(request.getContextPath() + "/accessdenied");
				
		boolean ret = Rqi.preHandle(request, response, handler);
		assertTrue(ret);
	}
	
	@Test
    public void testPreHandle5() throws Exception { 
		MockitoAnnotations.initMocks(this);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Object handler = Mockito.mock(Object.class);
		
		//mockito for xmlConfigFunction.setFilePath(((FilePath)appContext.getBean("xml_path")).getConfigPath());
		FilePath bean = Mockito.mock(FilePath.class);
		when(appContext.getBean(anyString())).thenReturn(bean);
		when(bean.getConfigPath()).thenReturn("path");
		
		//mockito for request.getRequestURL().toString();
		StringBuffer url = new StringBuffer("172.16.0.234:8080/ict/0045/");
		when(request.getRequestURL()).thenReturn(url);
		
		//mockito for String contextPath = request.getContextPath().replace("/", "");
		when(request.getContextPath()).thenReturn("test");
		
		//mokito for String[] screenList = xmlConfigFunction.getScreenList(contextPath.toLowerCase());
		String[] screenList = new String[]{"0045", "0013"};
		when(xmlConfigFunction.getScreenList(anyString())).thenReturn(screenList);
		
		//mokito for util.getUserInfo().getSCREENID().contains(screenId)
		MyUser user = Mockito.mock(MyUser.class);
		ArrayList<String> arrStr = new ArrayList<String>();
		arrStr.add("");
		when(user.getSCREENID()).thenReturn(arrStr);
		when(util.getUserInfo()).thenReturn(user);
		
		//mokito for response.sendRedirect(request.getContextPath() + "/accessdenied");
				
		boolean ret = Rqi.preHandle(request, response, handler);
		assertTrue(ret);
	}
    
	@Test
    public void testPreHandle6() throws Exception { 
		MockitoAnnotations.initMocks(this);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Object handler = Mockito.mock(Object.class);
		
		//mockito for xmlConfigFunction.setFilePath(((FilePath)appContext.getBean("xml_path")).getConfigPath());
		FilePath bean = Mockito.mock(FilePath.class);
		when(appContext.getBean(anyString())).thenReturn(bean);
		when(bean.getConfigPath()).thenReturn("path");
		
		//mockito for request.getRequestURL().toString();
		StringBuffer url = new StringBuffer("172.16.0.234:8080/ict/0045/");
		when(request.getRequestURL()).thenReturn(url);
		
		//mockito for String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");
		when(request.getHeader(anyString())).thenReturn("test");
		
		boolean ret = Rqi.preHandle(request, response, handler);
		assertFalse(ret);
	}
	
	@Test
    public void testPreHandle7() throws Exception { 
		MockitoAnnotations.initMocks(this);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		Object handler = Mockito.mock(Object.class);
		
		//mockito for xmlConfigFunction.setFilePath(((FilePath)appContext.getBean("xml_path")).getConfigPath());
		FilePath bean = Mockito.mock(FilePath.class);
		when(appContext.getBean(anyString())).thenReturn(bean);
		when(bean.getConfigPath()).thenReturn("path");
		
		//mockito for request.getRequestURL().toString();
		StringBuffer url = new StringBuffer("172.16.0.234:8080/ict/0045/");
		when(request.getRequestURL()).thenReturn(url);
		
		//mockito for String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");
		when(request.getHeader(anyString())).thenReturn("XMLHttpRequest");
		
		//mockito for resp.getWriter().write("login:-1,j_username:-1,j_password:-1");
		PrintWriter writer = Mockito.mock(PrintWriter.class);
		when(response.getWriter()).thenReturn(writer);
		
		boolean ret = Rqi.preHandle(request, response, handler);
		assertFalse(ret);
	}
	
	@Test
    public void testPostHandle() throws Exception { 
		MockitoAnnotations.initMocks(this);
		HttpServletRequest arg0 = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse arg1 = Mockito.mock(HttpServletResponse.class);
		Object arg2 = Mockito.mock(Object.class);
		ModelAndView arg3 = Mockito.mock(ModelAndView.class);
		
		//mockito for postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
		Rqi.postHandle(arg0, arg1, arg2, arg3);
	}
	
	@Test
    public void testAfterCompletion() throws Exception { 
		MockitoAnnotations.initMocks(this);
		HttpServletRequest arg0 = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse arg1 = Mockito.mock(HttpServletResponse.class);
		Object arg2 = Mockito.mock(Object.class);
		Exception arg3 = Mockito.mock(Exception.class);
		
		//mockito for void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
		Rqi.afterCompletion(arg0, arg1, arg2, arg3);
	}
}
