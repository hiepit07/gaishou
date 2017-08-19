	package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;
import vn.bananavietnam.ict.common.db.model.IvbMArea;
import vn.bananavietnam.ict.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0049AreaListServiceTest {

	@Autowired
	private ApplicationContext appContext;

	@InjectMocks
	private Bnn0049AreaListService bnn0049AreaListService_Mock;

	@Mock
	ObjectMapper mapper = new ObjectMapper();
    PlatformTransactionManager txManager;
    
    @Mock
	Util util;

	@Mock
	UtilDao utilDao;
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0047");
        MyUser u = new MyUser("A0001", "A0001", true, true, true, true, authorities, "1", "A0001", "A0001", screenId);
        Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0001", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

	public void createUserLogin() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0049");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
	}

	@Test
	public void initDataTestFarmNull() throws Exception {
		bnn0049AreaListService_Mock.setAppContext(appContext);
		createUserLogin();
		Model model = Mockito.mock(Model.class);
		List<IvbMArea> areaData = new ArrayList<IvbMArea>();
		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
        when(tmp.selectByExample(null)).thenReturn(areaData);
		
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        
        when(util.getUserInfo()).thenReturn(u);
        
        List<UtilComponent> farmutil = new ArrayList<UtilComponent>();
        UtilComponent temp = new UtilComponent();
        temp.setFarmId("aaaa");
        farmutil.add(temp);
        when(util.getFarmData(any(UtilDao.class))).thenReturn(farmutil);
        
        List<UtilComponent> area = new ArrayList<UtilComponent>();
        temp = new UtilComponent();
        temp.setFarmId("aaaa");
        temp.setFarmName("aaaa");
        area.add(temp);
        when(util.getAreaDataByFarmId(any(UtilDao.class), any(String.class))).thenReturn(area);
        
        bnn0049AreaListService_Mock.initData(model, "", "");        
	}
	
	@Test
	public void initDataTestFarmNullException() throws Exception {
		bnn0049AreaListService_Mock.setAppContext(appContext);
		createUserLogin();	
		Model model = Mockito.mock(Model.class);
		List<IvbMArea> areaData = new ArrayList<IvbMArea>();
		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
        when(tmp.selectByExample(null)).thenReturn(areaData);
		
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
        
        bnn0049AreaListService_Mock.initData(model, "", "");
	}	
	
	@Test
	public void initDataTest()  throws Exception {
		bnn0049AreaListService_Mock.setAppContext(appContext);
		createUserLogin();		
		Model model = Mockito.mock(Model.class);
		List<IvbMArea> areaData = new ArrayList<IvbMArea>();
		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
        when(tmp.selectByExample(null)).thenReturn(areaData);
		
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
        
        bnn0049AreaListService_Mock.initData(model, "F001", "F001");
	}
	
	@Test
	public void initDataTestException() throws Exception {
		bnn0049AreaListService_Mock.setAppContext(appContext);
		createUserLogin();
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
		
		Model model = Mockito.mock(Model.class);
		List<IvbMArea> farmData = new ArrayList<IvbMArea>();
		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
        when(tmp.selectByExample(null)).thenReturn(farmData);
		
        when(mapper.writeValueAsString(farmData)).thenReturn("OKL");
        doThrow(new RuntimeException()).when(model).addAttribute(any(String.class), eq("OKL"));
        
        bnn0049AreaListService_Mock.initData(model, "F001", "F001");
	}

	@Test
	public void initDataTestOuterException() throws Exception {
		bnn0049AreaListService_Mock.setAppContext(appContext);
		createUserLogin();
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
		
		Model model = Mockito.mock(Model.class);
		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		UtilComponent utilComponent = new UtilComponent();
		utilComponent.setFarmId("");
		farmData.add(utilComponent);
		when(util.getFarmData(any(UtilDao.class))).thenReturn(null);
        
        bnn0049AreaListService_Mock.initData(model, "", "F001");
	}
}
