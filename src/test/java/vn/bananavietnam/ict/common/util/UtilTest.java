package vn.bananavietnam.ict.common.util;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;

import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.mapper.UtilMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class UtilTest {
	private Util util = new Util();
	
	@Mock
	Util utilMock = new Util();
	
	@Mock
	UtilDao utilDao;

	@Mock
	MyUser user;

	@Test
	public void testconvertUnsanitizeNull() {		
		Assert.assertEquals(null, util.convertUnsanitize(null));
	}

	@Test
	public void testconvertUnsanitize() {		
		Assert.assertEquals("'\"><&%", util.convertUnsanitize("&#39;&quot;&gt;&lt;&amp;&#37;"));
	}
	
	@Test
	public void testconvertSanitizeNull() {		
		Assert.assertEquals(null, util.convertSanitize(null));
	}

	@Test
	public void testconvertSanitize() {		
		Assert.assertEquals("&#39;&quot;&gt;&lt;&amp;&#37;", util.convertSanitize("'\"><&%"));
	}
	
	@Test
	public void testgetUserInfo() throws Exception {
      MockitoAnnotations.initMocks(this);

      List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
      authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
      ArrayList<String> screenId = new ArrayList<String>();
      screenId.add("Util");
      MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
      Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
      SecurityContextHolder.getContext().setAuthentication(authToken);
      
      Assert.assertEquals(u , util.getUserInfo());
	}
	
	@Test
	public void testgetFarmData1() throws SQLException {
		  MockitoAnnotations.initMocks(this);
		
		  List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		  authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		  ArrayList<String> screenId = new ArrayList<String>();
		  screenId.add("Util");
		  MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		  Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
		  SecurityContextHolder.getContext().setAuthentication(authToken);
		  
		List<UtilComponent> farmResult = new ArrayList<UtilComponent>();
		farmResult.add(new UtilComponent());

		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
    	when(utilDao.getUtilMapper()).thenReturn(tmp);
        when(tmp.selectFarmDataMaster()).thenReturn(farmResult);
        
		Assert.assertEquals( 1, util.getFarmData(utilDao).size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testgetFarmData2() throws Exception {
		  MockitoAnnotations.initMocks(this);

		  List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		  authorities.add(new SimpleGrantedAuthority(Constants.CCO));
		  ArrayList<String> screenId = new ArrayList<String>();
		  screenId.add("Util");
		  MyUser u = new MyUser("U0000000002", "U0000000002", true, true, true, true, authorities, "0", "U0000000002", "U0000000002", screenId);
		  when(user.getROLEID()).thenReturn("2");
		  u = user;
		  Authentication authToken = new UsernamePasswordAuthenticationToken (u, "U0000000002", authorities);
		  SecurityContextHolder.getContext().setAuthentication(authToken);
		  
		List<UtilComponent> farmResult = new ArrayList<UtilComponent>();
		farmResult.add(new UtilComponent());

		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
    	when(utilDao.getUtilMapper()).thenReturn(tmp);
        when(tmp.selectFarmData(any(HashMap.class))).thenReturn(farmResult);
        
		Assert.assertEquals( 0, util.getFarmData(utilDao).size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testgetFarmData3() throws Exception {
		  MockitoAnnotations.initMocks(this);

		  List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		  authorities.add(new SimpleGrantedAuthority(Constants.CCO));
		  ArrayList<String> screenId = new ArrayList<String>();
		  screenId.add("Util");
		  MyUser u = new MyUser("U0000000002", "U0000000002", true, true, true, true, authorities, "0", "U0000000002", "U0000000002", screenId);
		  when(user.getROLEID()).thenReturn("3");
		  u = user;
		  Authentication authToken = new UsernamePasswordAuthenticationToken (u, "U0000000002", authorities);
		  SecurityContextHolder.getContext().setAuthentication(authToken);
		  
		List<UtilComponent> farmResult = new ArrayList<UtilComponent>();
		farmResult.add(new UtilComponent());

		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
    	when(utilDao.getUtilMapper()).thenReturn(tmp);
        when(tmp.selectFarmData(any(HashMap.class))).thenReturn(farmResult);
        
		Assert.assertEquals( 1, util.getFarmData(utilDao).size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testgetAreaDataByFarmId1() throws SQLException {
		String farmId = "F001";
		  MockitoAnnotations.initMocks(this);
		
		  List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		  authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		  ArrayList<String> screenId = new ArrayList<String>();
		  screenId.add("Util");
		  MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		  Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
		  SecurityContextHolder.getContext().setAuthentication(authToken);
		  
		List<UtilComponent> farmResult = new ArrayList<UtilComponent>();
		farmResult.add(new UtilComponent());

		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
    	when(utilDao.getUtilMapper()).thenReturn(tmp);
        when(tmp.selectAreaDataByFarmIdMaster(any(HashMap.class))).thenReturn(farmResult);
        
		Assert.assertEquals( 1, util.getAreaDataByFarmId(utilDao, farmId).size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testgetAreaDataByFarmId2() throws SQLException {
		String farmId = "F001";
		  MockitoAnnotations.initMocks(this);
		
		  List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		  authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		  ArrayList<String> screenId = new ArrayList<String>();
		  screenId.add("Util");
		  MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		  when(user.getROLEID()).thenReturn("2");
		  u = user;
		  Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
		  SecurityContextHolder.getContext().setAuthentication(authToken);
		  
		List<UtilComponent> farmResult = new ArrayList<UtilComponent>();
		farmResult.add(new UtilComponent());

		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
    	when(utilDao.getUtilMapper()).thenReturn(tmp);
        when(tmp.selectAreaDataByFarmIdMaster(any(HashMap.class))).thenReturn(farmResult);
        
		Assert.assertEquals( 1, util.getAreaDataByFarmId(utilDao, farmId).size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testgetAreaDataByFarmId3() throws SQLException {
		String farmId = "F001";
		  MockitoAnnotations.initMocks(this);
		
		  List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		  authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		  ArrayList<String> screenId = new ArrayList<String>();
		  screenId.add("Util");
		  MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		  when(user.getROLEID()).thenReturn("3");
		  u = user;
		  Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
		  SecurityContextHolder.getContext().setAuthentication(authToken);
		  
		List<UtilComponent> farmResult = new ArrayList<UtilComponent>();
		farmResult.add(new UtilComponent());

		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
    	when(utilDao.getUtilMapper()).thenReturn(tmp);
        when(tmp.selectAreaDataByFarmIdMaster(any(HashMap.class))).thenReturn(farmResult);
        
		Assert.assertEquals( 1, util.getAreaDataByFarmId(utilDao, farmId).size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testgetAreaDataByFarmId4() throws SQLException {
		String farmId = "F001";
		  MockitoAnnotations.initMocks(this);
		
		  List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		  authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		  ArrayList<String> screenId = new ArrayList<String>();
		  screenId.add("Util");
		  MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		  when(user.getROLEID()).thenReturn("4");
		  u = user;
		  Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
		  SecurityContextHolder.getContext().setAuthentication(authToken);
		  
		List<UtilComponent> farmResult = new ArrayList<UtilComponent>();
		farmResult.add(new UtilComponent());

		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
    	when(utilDao.getUtilMapper()).thenReturn(tmp);
        when(tmp.selectAreaDataByFarmId(any(HashMap.class))).thenReturn(farmResult);
        
		Assert.assertEquals( 1, util.getAreaDataByFarmId(utilDao, farmId).size());
	}
	
	@Test
	public void testgetFileLastModified() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String servlet_context_test = "src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml";
		Method method = util.getClass().getDeclaredMethod("getFileLastModified", servlet_context_test.getClass());
		method.setAccessible(true);
		String ret = (String) method.invoke(util, servlet_context_test);
		Assert.assertNotNull(ret);
	}
	
	@Test
	public void testgetFileLastModifiednull() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String servlet_context_test = "";
		Method method = util.getClass().getDeclaredMethod("getFileLastModified", servlet_context_test.getClass());
		method.setAccessible(true);
		String ret = (String) method.invoke(util, servlet_context_test);
		Assert.assertNull(ret);
	}
	
	@Test
	public void testsetDataParamForScreenFiles() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Model model = Mockito.mock(Model.class);
		
		HttpSession session = Mockito.mock(HttpSession.class);
		ServletContext serv = Mockito.mock(ServletContext.class);
		when(request.getSession()).thenReturn(session);		
		when(session.getServletContext()).thenReturn(serv);
		when(serv.getRealPath(anyString())).thenReturn("abc");
		request.getSession().getServletContext().getRealPath("/resources/css/banana/");
		utilMock.setDataParamForScreenFiles(request, model, "", "", "", "");
	}
	
	@Test
	public void testsetDateParamForCommonFiles() {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Model model = Mockito.mock(Model.class);
		
		HttpSession session = Mockito.mock(HttpSession.class);
		ServletContext serv = Mockito.mock(ServletContext.class);
		when(request.getSession()).thenReturn(session);		
		when(session.getServletContext()).thenReturn(serv);
		when(serv.getRealPath(anyString())).thenReturn("abc");
		request.getSession().getServletContext().getRealPath("/resources/css/banana/");
		utilMock.setDateParamForCommonFiles(request, model);
	}
}
