package vn.bananavietnam.ict.common.auth;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.util.XmlConfigFunction;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class MySuccessHandlerTest {
	@InjectMocks
	MySuccessHandler hander = new MySuccessHandler () {
		public void changePageOnAuth(Set<String> roles, HttpServletResponse response, String[] appScreenList, ArrayList<String> authScreenList) throws IOException, ServletException {
			return;
		};
	};
	
	@InjectMocks
	MySuccessHandler handerchangePageOnAuth = new MySuccessHandler();
	
	private String configFisrt = "D:/030_20161004_VNB_ICT/30_SourceManagement/banana/config.xml";
	
	@Mock
	ResultSet rsMock;

	@Mock
	Authentication authenticationMock;
	
	@Mock
	HttpServletRequest requestMock;
	
	@Mock
	HttpServletResponse responseMock;
	
	@Mock
	XmlConfigFunction xmlConfigFunction = new XmlConfigFunction();
	
	@Mock
	AuthorityUtils author;
	
	@Mock
	List<GrantedAuthority> authorities1 = new ArrayList<GrantedAuthority>();
	
	@Mock
	File xmlFile = new File("D:/030_20161004_VNB_ICT/30_SourceManagement/banana/config.xml");
	
	MockMvc mockMvc;
	@Mock
	File Fileright = new File("C:\\GamesnewOrder.txt"); 
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0035");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMyUserWhileTrue() throws Exception{

		
		String[] appScreenList = "0001,0047,0049,0035,0000".toString().split(",");
		requestMock = Mockito.mock(HttpServletRequest.class);
		when(requestMock.getContextPath()).thenReturn(configFisrt);
		when(xmlConfigFunction.getScreenList(any(String.class))).thenReturn(appScreenList);

		responseMock = Mockito.mock(HttpServletResponse.class);
		authenticationMock = Mockito.mock(Authentication.class);
		Collection<GrantedAuthority> userAuthorities = Mockito.mock(Collection.class);
		GrantedAuthority authority =  Mockito.mock(GrantedAuthority.class);
		when(authority.getAuthority()).thenReturn(Constants.SYSTEM_MANAGER);
		userAuthorities.add(authority);
		
		MyUser u = Mockito.mock(MyUser.class);
		ArrayList<String> t = new ArrayList<String>();
		when(u.getSCREENID()).thenReturn(t);
		when(authenticationMock.getPrincipal()).thenReturn(u);
		
		hander.onAuthenticationSuccess(requestMock, responseMock, authenticationMock);
	}
	
	@Test
	public void testChangePageOnAuthSYSTEM_MANAGER0001() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0001".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0001");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.SYSTEM_MANAGER);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}
	
	@Test
	public void testChangePageOnAuthSYSTEM_MANAGER0001_2() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0001".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0000");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.SYSTEM_MANAGER);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}
	
	@Test
	public void testChangePageOnAuthSYSTEM_MANAGER0047() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0047".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0047");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.SYSTEM_MANAGER);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}
	
	@Test
	public void testChangePageOnAuthSYSTEM_MANAGER0047_1() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0047".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0000");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.SYSTEM_MANAGER);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}
	
	@Test
	public void testChangePageOnAuthSYSTEM_MANAGERNull() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0000".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0000");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.SYSTEM_MANAGER);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}
	
	@Test
	public void testChangePageOnAuthCCO0047() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0047".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0047");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.CCO);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}

	@Test
	public void testChangePageOnAuthCCO0047_1() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0047".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0000");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.CCO);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}

	@Test
	public void testChangePageOnAuthCCO0001() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0001".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0001");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.CCO);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}
	
	@Test
	public void testChangePageOnAuthCCO0001_1() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0001".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0000");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.CCO);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}
	
	@Test
	public void testChangePageOnAuthCCONull() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0000".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0000");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.CCO);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}
	
	@Test
	public void testChangePageOnAuthFARM_MANAGER() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0000".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0000");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.FARM_MANAGER);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}
	
	@Test
	public void testChangePageOnAuthFARM_MANAGER_1() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0049".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0000");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.FARM_MANAGER);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}
	
	@Test
	public void testChangePageOnAuthFARM_MANAGER_2() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0049".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0049");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.FARM_MANAGER);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}

	@Test
	public void testChangePageOnAuthAREA_MANAGER() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0035".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0035");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.AREA_MANAGER);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}
	
	@Test
	public void testChangePageOnAuthAREA_MANAGER_1() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0035".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0000");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.AREA_MANAGER);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}
	
	@Test
	public void testChangePageOnAuthAREA_MANAGER_2() throws Exception{
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		
		String[] appScreenList = "0000".toString().split(",");
		ArrayList<String> authScreenList = new ArrayList<String>();
		authScreenList.add("0000");
		Set<String> roles = new HashSet<String>();
		roles.add(Constants.AREA_MANAGER);
		responseMock = Mockito.mock(HttpServletResponse.class);
		PrintWriter printWriter = Mockito.mock(PrintWriter.class);
		when(responseMock.getWriter()).thenReturn(printWriter);
		handerchangePageOnAuth.changePageOnAuth(roles, responseMock, appScreenList, authScreenList);
	}

}