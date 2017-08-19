package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.component.Bnn0095SearchAccessAuthorizationConditions;
import vn.bananavietnam.ict.banana.component.Bnn0095SearchAccessAuthorizationResult;
import vn.bananavietnam.ict.banana.dao.Bnn0095SearchAccessAuthorizationDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0095SearchAccessAuthorizationMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.mapper.IvbMAccessAuthorizationMapper;
import vn.bananavietnam.ict.common.db.model.IvbMAccessAuthorization;
import vn.bananavietnam.ict.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0095SearchAccessAuthorizationServiceTest {
	
	@Autowired
    private ApplicationContext appContext;

	@InjectMocks
	private Bnn0095SearchAccessAuthorizationService bnn0095SearchAccessAuthorizationService;

	@Mock
	Bnn0095SearchAccessAuthorizationDao bnn0095SearchAccessAuthorizationDao;
	
	@Mock
	Util util;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
	
	@SuppressWarnings("unchecked")
	@Test
	public void searchDataHaveResult() throws SQLException {
		
		Bnn0095SearchAccessAuthorizationConditions searchConditions = new Bnn0095SearchAccessAuthorizationConditions();
		// access authorization Id
		searchConditions.setAccessAuthorityId("1");
		// screen Id
		searchConditions.setScreenId("0095");
		// screenDisplayEnableFlag
		searchConditions.setScreenDisplayEnableFlag(Constants.COMPARE_WITH_FLAG_OFF);
		// addableFlag
		searchConditions.setAddableFlag(Constants.COMPARE_WITH_FLAG_OFF);
		// updatableFlag
		searchConditions.setUpdatableFlag(Constants.COMPARE_WITH_FLAG_OFF);
		// deletableFlag
		searchConditions.setDeletableFlag(Constants.COMPARE_WITH_FLAG_OFF);
		// referenceFlag
		searchConditions.setReferenceFlag(Constants.COMPARE_WITH_FLAG_OFF);
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");
		
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		when(util.getUserInfo()).thenReturn(u);
		
		List<Bnn0095SearchAccessAuthorizationResult> expe = new ArrayList<Bnn0095SearchAccessAuthorizationResult>();
		Bnn0095SearchAccessAuthorizationResult temp = new Bnn0095SearchAccessAuthorizationResult();
		expe.add(temp);
		
		Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);		
		when(tmp.searchData(any(HashMap.class))).thenReturn(expe);
		when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("1");
		
		// get search result
		List<Bnn0095SearchAccessAuthorizationResult> bnn0001ResultList = bnn0095SearchAccessAuthorizationService.searchData(searchConditions);
		// start testing
		Assert.assertEquals(1, bnn0001ResultList.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void searchDataNoResult() throws SQLException {
		Bnn0095SearchAccessAuthorizationConditions searchConditions = new Bnn0095SearchAccessAuthorizationConditions();
		// access authorization Id
		searchConditions.setAccessAuthorityId("");
		// screen Id
		searchConditions.setScreenId("");
		// screenDisplayEnableFlag
		searchConditions.setScreenDisplayEnableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// addableFlag
		searchConditions.setAddableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// updatableFlag
		searchConditions.setUpdatableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// deletableFlag
		searchConditions.setDeletableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// referenceFlag
		searchConditions.setReferenceFlag(Constants.COMPARE_WITH_FLAG_ON);
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");
		
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		when(util.getUserInfo()).thenReturn(u);
		
		List<Bnn0095SearchAccessAuthorizationResult> expe = new ArrayList<Bnn0095SearchAccessAuthorizationResult>();
		
		Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);		
		when(tmp.searchData(any(HashMap.class))).thenReturn(expe);
		when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
		
		// get search result
		List<Bnn0095SearchAccessAuthorizationResult> bnn0001ResultList = bnn0095SearchAccessAuthorizationService.searchData(searchConditions);
		// start testing
		Assert.assertEquals(0, bnn0001ResultList.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataOutOfMemoryException() throws SQLException {
		Bnn0095SearchAccessAuthorizationConditions searchConditions = new Bnn0095SearchAccessAuthorizationConditions();
		// access authorization Id
		searchConditions.setAccessAuthorityId("1");
		// screen Id
		searchConditions.setScreenId("0095");
		// screenDisplayEnableFlag
		searchConditions.setScreenDisplayEnableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// addableFlag
		searchConditions.setAddableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// updatableFlag
		searchConditions.setUpdatableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// deletableFlag
		searchConditions.setDeletableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// referenceFlag
		searchConditions.setReferenceFlag(Constants.COMPARE_WITH_FLAG_ON);
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");
		
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		when(util.getUserInfo()).thenReturn(u);
		
		// Mock setup
		Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);
		doThrow(new OutOfMemoryError()).when(tmp).searchData(any(HashMap.class));
		
		// get search result
		List<Bnn0095SearchAccessAuthorizationResult> bnn0095ResultList = bnn0095SearchAccessAuthorizationService.searchData(searchConditions);
		// start testing
		Assert.assertEquals(1, bnn0095ResultList.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataException() throws SQLException {
		Bnn0095SearchAccessAuthorizationConditions searchConditions = new Bnn0095SearchAccessAuthorizationConditions();
		// access authorization Id
		searchConditions.setAccessAuthorityId("1");
		// screen Id
		searchConditions.setScreenId("0095");
		// screenDisplayEnableFlag
		searchConditions.setScreenDisplayEnableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// addableFlag
		searchConditions.setAddableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// updatableFlag
		searchConditions.setUpdatableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// deletableFlag
		searchConditions.setDeletableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// referenceFlag
		searchConditions.setReferenceFlag(Constants.COMPARE_WITH_FLAG_ON);
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");
		
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		when(util.getUserInfo()).thenReturn(u);
		
		// Mock setup
		Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));
		
		// get search result
		List<Bnn0095SearchAccessAuthorizationResult> bnn0013ResultList = bnn0095SearchAccessAuthorizationService.searchData(searchConditions);
		// start testing
		Assert.assertEquals(null, bnn0013ResultList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataException1() throws SQLException {
		Bnn0095SearchAccessAuthorizationConditions searchConditions = new Bnn0095SearchAccessAuthorizationConditions();
		// access authorization Id
		searchConditions.setAccessAuthorityId("1");
		// screen Id
		searchConditions.setScreenId("0095");
		// screenDisplayEnableFlag
		searchConditions.setScreenDisplayEnableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// addableFlag
		searchConditions.setAddableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// updatableFlag
		searchConditions.setUpdatableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// deletableFlag
		searchConditions.setDeletableFlag(Constants.COMPARE_WITH_FLAG_ON);
		// referenceFlag
		searchConditions.setReferenceFlag(Constants.COMPARE_WITH_FLAG_ON);
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		when(util.getUserInfo()).thenReturn(u);
		
		// Mock setup
		Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));
		
		// get search result
		List<Bnn0095SearchAccessAuthorizationResult> bnn0013ResultList = bnn0095SearchAccessAuthorizationService.searchData(searchConditions);
		// start testing
		Assert.assertEquals(null, bnn0013ResultList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchGetSingleData() throws SQLException {
		Bnn0095SearchAccessAuthorizationConditions searchConditions = new Bnn0095SearchAccessAuthorizationConditions();
		// access authorization Id
		searchConditions.setAccessAuthorityId("1");
		// screen Id
		searchConditions.setScreenId("0095");
		
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		when(util.getUserInfo()).thenReturn(u);
		
		List<Bnn0095SearchAccessAuthorizationResult> expe = new ArrayList<Bnn0095SearchAccessAuthorizationResult>();
		Bnn0095SearchAccessAuthorizationResult temp = new Bnn0095SearchAccessAuthorizationResult();
		expe.add(temp);
		
		Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);		
		when(tmp.searchSingleData(any(HashMap.class))).thenReturn(expe);
		
		// get search result
		List<Bnn0095SearchAccessAuthorizationResult> bnn0095ResultList = bnn0095SearchAccessAuthorizationService.getSingleData(searchConditions);
		// start testing
		Assert.assertEquals(1, bnn0095ResultList.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchGetSingleNoData() throws SQLException {
		Bnn0095SearchAccessAuthorizationConditions searchConditions = new Bnn0095SearchAccessAuthorizationConditions();
		// access authorization Id
		searchConditions.setAccessAuthorityId("1");
		// screen Id
		searchConditions.setScreenId("0061");
		
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		when(util.getUserInfo()).thenReturn(u);
		
		List<Bnn0095SearchAccessAuthorizationResult> expe = new ArrayList<Bnn0095SearchAccessAuthorizationResult>();

		Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);		
		when(tmp.searchSingleData(any(HashMap.class))).thenReturn(expe);		
		
		// get search result
		List<Bnn0095SearchAccessAuthorizationResult> bnn0095ResultList = bnn0095SearchAccessAuthorizationService.getSingleData(searchConditions);
		// start testing
		Assert.assertEquals(0, bnn0095ResultList.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchGetSingleDataException() throws SQLException {
		Bnn0095SearchAccessAuthorizationConditions searchConditions = new Bnn0095SearchAccessAuthorizationConditions();
		// access authorization Id
		searchConditions.setAccessAuthorityId("");
		// screen Id
		searchConditions.setScreenId("");
		
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		when(util.getUserInfo()).thenReturn(u);
		
		// Mock setup
		Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).searchSingleData(any(HashMap.class));
		
		// get search result
		List<Bnn0095SearchAccessAuthorizationResult> bnn0095ResultList = bnn0095SearchAccessAuthorizationService.getSingleData(searchConditions);
		// start testing
		Assert.assertEquals(null, bnn0095ResultList);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchGetSingleDataException1() throws SQLException {
		Bnn0095SearchAccessAuthorizationConditions searchConditions = new Bnn0095SearchAccessAuthorizationConditions();
		// access authorization Id
		searchConditions.setAccessAuthorityId("");
		// screen Id
		searchConditions.setScreenId("");
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		when(util.getUserInfo()).thenReturn(u);
		
		// Mock setup
		Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).searchSingleData(any(HashMap.class));
		
		// get search result
		List<Bnn0095SearchAccessAuthorizationResult> bnn0095ResultList = bnn0095SearchAccessAuthorizationService.getSingleData(searchConditions);
		// start testing
		Assert.assertEquals(null, bnn0095ResultList);
	}
	
	@Test
	public void testUpdateDataUserIDNull(){
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();
		// Process Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// Process Name
		ivbMAccessAuthorization.setScreenId("0095");
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		
        MyUser u = Mockito.mock(MyUser.class);
        when(util.getUserInfo()).thenReturn(u);
        when(u.getID()).thenReturn(null);
        
		IvbMAccessAuthorizationMapper tmp = Mockito.mock(IvbMAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getIvbMAccessAuthorizationMapper()).thenReturn(tmp);
		when(tmp.updateByPrimaryKeySelective(any(IvbMAccessAuthorization.class))).thenReturn(1);
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		String result = bnn0095SearchAccessAuthorizationService.updateData(ivbMAccessAuthorization);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateData() throws SQLException {
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();
		// Process Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// Process Name
		ivbMAccessAuthorization.setScreenId("0095");
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
		
        Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		String result = bnn0095SearchAccessAuthorizationService.updateData(ivbMAccessAuthorization);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_SUCCESSFUL, result);
	}
	
	@Test
	public void testUpdateDataFail() throws SQLException {
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();
		// Process Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// Process Name
		ivbMAccessAuthorization.setScreenId("0095");
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
		
		IvbMAccessAuthorizationMapper tmp = Mockito.mock(IvbMAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getIvbMAccessAuthorizationMapper()).thenReturn(tmp);
		when(tmp.updateByPrimaryKeySelective(any(IvbMAccessAuthorization.class))).thenReturn(0);
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		String result = bnn0095SearchAccessAuthorizationService.updateData(ivbMAccessAuthorization);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}
	
	@Test
	public void testUpdateDataWithWrongScreenId() throws SQLException {
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();
		// Process Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// Process Name
		ivbMAccessAuthorization.setScreenId("1095");
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
		
		String result = bnn0095SearchAccessAuthorizationService.updateData(ivbMAccessAuthorization);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_WRONG_FORMAT, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataRollBack() throws SQLException, ParseException {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		when(util.getUserInfo()).thenReturn(u);
		
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		java.util.Date dateLast = format.parse("2017/05/17 13:25:25");
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();
		// Process Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// Process Name
		ivbMAccessAuthorization.setScreenId("0099");
		// last Update Date
		ivbMAccessAuthorization.setLastUpdateDate(date);
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		Bnn0095SearchAccessAuthorizationConditions searchConditions = new Bnn0095SearchAccessAuthorizationConditions();
		// access authorization Id
		searchConditions.setAccessAuthorityId("1");
		// screen Id
		searchConditions.setScreenId("0095");
		
		
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		
		List<Bnn0095SearchAccessAuthorizationResult> expe = new ArrayList<Bnn0095SearchAccessAuthorizationResult>();
		Bnn0095SearchAccessAuthorizationResult temp = new Bnn0095SearchAccessAuthorizationResult();
		temp.setLastUpdateDate(dateLast);
		expe.add(temp);
		
		Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);		
		when(tmp.searchSingleData(any(HashMap.class))).thenReturn(expe);

		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(0);
		
		String result = bnn0095SearchAccessAuthorizationService.updateData(ivbMAccessAuthorization);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_ACCESS_AUTHORIZATION, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataUpdateDate() throws SQLException, ParseException {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		when(util.getUserInfo()).thenReturn(u);
		
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		java.util.Date dateLast = format.parse("2017/05/18 13:25:25");
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();
		// Process Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// Process Name
		ivbMAccessAuthorization.setScreenId("0099");
		// last Update Date
		ivbMAccessAuthorization.setLastUpdateDate(date);
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		Bnn0095SearchAccessAuthorizationConditions searchConditions = new Bnn0095SearchAccessAuthorizationConditions();
		// access authorization Id
		searchConditions.setAccessAuthorityId("1");
		// screen Id
		searchConditions.setScreenId("0095");
		
		
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		
		List<Bnn0095SearchAccessAuthorizationResult> list = new ArrayList<Bnn0095SearchAccessAuthorizationResult>();
		Bnn0095SearchAccessAuthorizationResult temp = new Bnn0095SearchAccessAuthorizationResult();
		// access authorization Id
		searchConditions.setAccessAuthorityId("1");
		// screen Id
		searchConditions.setScreenId("0095");
		temp.setLastUpdateDate(dateLast);
		list.add(temp);
		
		Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);		
		when(tmp.searchSingleData(any(HashMap.class))).thenReturn(list);
		
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(0);
		
		String result = bnn0095SearchAccessAuthorizationService.updateData(ivbMAccessAuthorization);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataSuceess() throws SQLException, ParseException {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		when(util.getUserInfo()).thenReturn(u);
		
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		java.util.Date dateLast = format.parse("2017/05/18 13:25:25");
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();
		// Process Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// Process Name
		ivbMAccessAuthorization.setScreenId("0099");
		// last Update Date
		ivbMAccessAuthorization.setLastUpdateDate(date);
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		Bnn0095SearchAccessAuthorizationConditions searchConditions = new Bnn0095SearchAccessAuthorizationConditions();
		// access authorization Id
		searchConditions.setAccessAuthorityId("1");
		// screen Id
		searchConditions.setScreenId("0099");
		
		
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		
		List<Bnn0095SearchAccessAuthorizationResult> expe = new ArrayList<Bnn0095SearchAccessAuthorizationResult>();
		Bnn0095SearchAccessAuthorizationResult temp = new Bnn0095SearchAccessAuthorizationResult();
		temp.setLastUpdateDate(dateLast);
		expe.add(temp);
		
		Bnn0095SearchAccessAuthorizationMapper tmp1 = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp1);		
		when(tmp1.searchSingleData(any(HashMap.class))).thenReturn(expe);
		
        when(util.getUserInfo()).thenReturn(u);
		
        Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		
		String result = bnn0095SearchAccessAuthorizationService.updateData(ivbMAccessAuthorization);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_SUCCESSFUL, result);
	}
	@Test
	@Transactional
	public void testUpdateDataException() throws SQLException {
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();
		// Process Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// Process Name
		ivbMAccessAuthorization.setScreenId("0095");
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
		
		IvbMAccessAuthorizationMapper tmp = Mockito.mock(IvbMAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getIvbMAccessAuthorizationMapper()).thenReturn(tmp);
		when(((IvbMAccessAuthorizationMapper) tmp).updateByPrimaryKeySelective(ivbMAccessAuthorization)).thenReturn(0);
		
		String result = bnn0095SearchAccessAuthorizationService.updateData(ivbMAccessAuthorization);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}
	
	@Test
	public void testUpdateDataOutOfMemoryException() throws SQLException {
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();// access authorization Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// screen Id
		ivbMAccessAuthorization.setScreenId("0095");
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
		
		// Mock setup
        when(bnn0095SearchAccessAuthorizationDao.getIvbMAccessAuthorizationMapper()).thenReturn(null);
		
        // update user
        bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
 		String result = bnn0095SearchAccessAuthorizationService.updateData(ivbMAccessAuthorization);
		// start testing
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}
	
	@Test
	public void testInsertDataUserIDnull() throws SQLException {
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();// access authorization Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// screen Id
		ivbMAccessAuthorization.setScreenId("0100");
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		// screenDisplayEnableFlag
		ivbMAccessAuthorization.setScreenDisplayEnableFlag(Constants.DELETE_FLAG_OFF);
		// addableFlag
		ivbMAccessAuthorization.setAddableFlag(Constants.DELETE_FLAG_OFF);
		// updatableFlag
		ivbMAccessAuthorization.setUpdatableFlag(Constants.DELETE_FLAG_OFF);
		// deletableFlag
		ivbMAccessAuthorization.setDeletableFlag(Constants.DELETE_FLAG_OFF);
		// referenceFlag
		ivbMAccessAuthorization.setReferenceFlag(Constants.DELETE_FLAG_OFF);
		
        MyUser u = Mockito.mock(MyUser.class);
       
        when(util.getUserInfo()).thenReturn(u);
		when(u.getID()).thenReturn(null);
        
		IvbMAccessAuthorizationMapper tmp = Mockito.mock(IvbMAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getIvbMAccessAuthorizationMapper()).thenReturn(tmp);		
		when(tmp.insert(any(IvbMAccessAuthorization.class))).thenReturn(1);
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		String result = bnn0095SearchAccessAuthorizationService.insertData(ivbMAccessAuthorization);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
	}
	
	
	@Test
	public void testInsertData() throws SQLException {
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();// access authorization Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// screen Id
		ivbMAccessAuthorization.setScreenId("0100");
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		// screenDisplayEnableFlag
		ivbMAccessAuthorization.setScreenDisplayEnableFlag(Constants.DELETE_FLAG_OFF);
		// addableFlag
		ivbMAccessAuthorization.setAddableFlag(Constants.DELETE_FLAG_OFF);
		// updatableFlag
		ivbMAccessAuthorization.setUpdatableFlag(Constants.DELETE_FLAG_OFF);
		// deletableFlag
		ivbMAccessAuthorization.setDeletableFlag(Constants.DELETE_FLAG_OFF);
		// referenceFlag
		ivbMAccessAuthorization.setReferenceFlag(Constants.DELETE_FLAG_OFF);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
		
		IvbMAccessAuthorizationMapper tmp = Mockito.mock(IvbMAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getIvbMAccessAuthorizationMapper()).thenReturn(tmp);		
		when(tmp.insert(any(IvbMAccessAuthorization.class))).thenReturn(1);
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		String result = bnn0095SearchAccessAuthorizationService.insertData(ivbMAccessAuthorization);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
	}
	
	@Test
	public void testInsertInnerException() throws SQLException {
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();// access authorization Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// screen Id
		ivbMAccessAuthorization.setScreenId("0100");
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
		
		IvbMAccessAuthorizationMapper tmp = Mockito.mock(IvbMAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getIvbMAccessAuthorizationMapper()).thenReturn(tmp);		
		doThrow(new RuntimeException()).when(tmp).insert(any(IvbMAccessAuthorization.class));
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		String result = bnn0095SearchAccessAuthorizationService.insertData(ivbMAccessAuthorization);
		
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}
	
	@Test
	public void testInsertInnerDuplicateKeyException() throws SQLException {
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();// access authorization Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// screen Id
		ivbMAccessAuthorization.setScreenId("0100");
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
		
		IvbMAccessAuthorizationMapper tmp = Mockito.mock(IvbMAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getIvbMAccessAuthorizationMapper()).thenReturn(tmp);		
		doThrow(new DuplicateKeyException(null)).when(tmp).insert(any(IvbMAccessAuthorization.class));
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		String result = bnn0095SearchAccessAuthorizationService.insertData(ivbMAccessAuthorization);
		
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_DUPLICATED, result);
	}
	
	@Test
	public void testInsertDataFormatScreenId() throws SQLException {
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();// access authorization Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// screen Id
		ivbMAccessAuthorization.setScreenId("1100");
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
		
		String result = bnn0095SearchAccessAuthorizationService.insertData(ivbMAccessAuthorization);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_WRONG_FORMAT, result);
	}
	@Test
	public void testInsertDataRollBack() throws SQLException, ParseException {
		
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();// access authorization Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// screen Id
		ivbMAccessAuthorization.setScreenId("0100");
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		// screenDisplayEnableFlag
		ivbMAccessAuthorization.setScreenDisplayEnableFlag(Constants.DELETE_FLAG_OFF);
		// addableFlag
		ivbMAccessAuthorization.setAddableFlag(Constants.DELETE_FLAG_OFF);
		// updatableFlag
		ivbMAccessAuthorization.setUpdatableFlag(Constants.DELETE_FLAG_OFF);
		// deletableFlag
		ivbMAccessAuthorization.setDeletableFlag(Constants.DELETE_FLAG_OFF);
		// referenceFlag
		ivbMAccessAuthorization.setReferenceFlag(Constants.DELETE_FLAG_OFF);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
		
		IvbMAccessAuthorizationMapper tmp = Mockito.mock(IvbMAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getIvbMAccessAuthorizationMapper()).thenReturn(tmp);
		when(tmp.insert(ivbMAccessAuthorization)).thenReturn(0);
		
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		String result = bnn0095SearchAccessAuthorizationService.insertData(ivbMAccessAuthorization);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_ACCESS_AUTHORIZATION, result);
	}
	
	@Test
	public void testInsertOuterExcetion() throws SQLException {
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();// access authorization Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// screen Id
		ivbMAccessAuthorization.setScreenId("0100");
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(true);
		// screenDisplayEnableFlag
		ivbMAccessAuthorization.setScreenDisplayEnableFlag(Constants.DELETE_FLAG_OFF);
		// addableFlag
		ivbMAccessAuthorization.setAddableFlag(Constants.DELETE_FLAG_OFF);
		// updatableFlag
		ivbMAccessAuthorization.setUpdatableFlag(Constants.DELETE_FLAG_OFF);
		// deletableFlag
		ivbMAccessAuthorization.setDeletableFlag(Constants.DELETE_FLAG_OFF);
		// referenceFlag
		ivbMAccessAuthorization.setReferenceFlag(Constants.DELETE_FLAG_OFF);
		// Delete Flag
		ivbMAccessAuthorization.setDeleteFlag(false);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
		
		// mock setup
		IvbMAccessAuthorizationMapper tmp = Mockito.mock(IvbMAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getIvbMAccessAuthorizationMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).insert(ivbMAccessAuthorization);
		
		String result = bnn0095SearchAccessAuthorizationService.insertData(ivbMAccessAuthorization);
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteData() throws SQLException {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenIdMock = new ArrayList<String>();
		screenIdMock.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenIdMock);
		when(util.getUserInfo()).thenReturn(u);
		
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();// access authorization Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// screen Id
		ivbMAccessAuthorization.setScreenId("0095");
		String accessAuthorityId = ivbMAccessAuthorization.getAccessAuthorityId();
		String screenId = ivbMAccessAuthorization.getScreenId();
		
		Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);		
		when(tmp.deleteData(any(HashMap.class))).thenReturn(1);
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		Date lastUpdateDate = null;
		String result = bnn0095SearchAccessAuthorizationService.deleteData(accessAuthorityId, screenId, lastUpdateDate);
		Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteDataRollback() throws SQLException, ParseException {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenIdMock = new ArrayList<String>();
		screenIdMock.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenIdMock);
		when(util.getUserInfo()).thenReturn(u);

		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		java.util.Date dateLast = format.parse("2017/05/17 13:25:25");
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();// access authorization Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// screen Id
		ivbMAccessAuthorization.setScreenId("0100");
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		
		String accessAuthorityId = ivbMAccessAuthorization.getAccessAuthorityId();
		String screenId = ivbMAccessAuthorization.getScreenId();
		List<Bnn0095SearchAccessAuthorizationResult> expe = new ArrayList<Bnn0095SearchAccessAuthorizationResult>();
		Bnn0095SearchAccessAuthorizationResult temp = new Bnn0095SearchAccessAuthorizationResult();
		temp.setLastUpdateDate(dateLast);
		expe.add(temp);
		
		Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);		
		when(tmp.searchSingleData(any(HashMap.class))).thenReturn(expe);
		
		when(tmp.deleteData(any(HashMap.class))).thenReturn(Constants.RESULT_IS_EQUAL_TO_ZERO);
		
		String result = bnn0095SearchAccessAuthorizationService.deleteData(accessAuthorityId, screenId, date);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_ACCESS_AUTHORIZATION, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteDataFailedUpdateDate() throws SQLException, ParseException {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenIdMock = new ArrayList<String>();
		screenIdMock.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenIdMock);
		when(util.getUserInfo()).thenReturn(u);

		String string = "2017/05/18 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		java.util.Date dateLast = format.parse("2017/05/19 13:25:25");
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();// access authorization Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// screen Id
		ivbMAccessAuthorization.setScreenId("0100");
		bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		
		String accessAuthorityId = ivbMAccessAuthorization.getAccessAuthorityId();
		String screenId = ivbMAccessAuthorization.getScreenId();
		List<Bnn0095SearchAccessAuthorizationResult> expe = new ArrayList<Bnn0095SearchAccessAuthorizationResult>();
		Bnn0095SearchAccessAuthorizationResult temp = new Bnn0095SearchAccessAuthorizationResult();
		temp.setLastUpdateDate(dateLast);
		expe.add(temp);
		
		Bnn0095SearchAccessAuthorizationMapper tmp = Mockito.mock(Bnn0095SearchAccessAuthorizationMapper.class);
		when(bnn0095SearchAccessAuthorizationDao.getBnn0095SearchAccessAuthorizationMapper()).thenReturn(tmp);		
		when(tmp.searchSingleData(any(HashMap.class))).thenReturn(expe);
		
		when(tmp.deleteData(any(HashMap.class))).thenReturn(Constants.RESULT_IS_EQUAL_TO_ZERO);
		
		String result = bnn0095SearchAccessAuthorizationService.deleteData(accessAuthorityId, screenId, date);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_UPDATE_DATE, result);
	}

	@Test
	@Transactional
	public void testDeleteDataInnerException() throws SQLException {
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();// access authorization Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// screen Id
		ivbMAccessAuthorization.setScreenId("0100");
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenIdMock = new ArrayList<String>();
		screenIdMock.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenIdMock);
		when(util.getUserInfo()).thenReturn(u);
		
		String accessAuthorityId = ivbMAccessAuthorization.getAccessAuthorityId();
		String screenId = ivbMAccessAuthorization.getScreenId();

		// Mock setup
        when(bnn0095SearchAccessAuthorizationDao.getIvbMAccessAuthorizationMapper()).thenReturn(null);

		// delete user
        bnn0095SearchAccessAuthorizationService.setAppContext(appContext);
		Date lastUpdateDate = null;
		String result = bnn0095SearchAccessAuthorizationService.deleteData(accessAuthorityId, screenId, lastUpdateDate);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}
	@Test
	@Transactional
	public void testDeleteDataOuterException() throws SQLException {
		IvbMAccessAuthorization ivbMAccessAuthorization = new IvbMAccessAuthorization();// access authorization Id
		ivbMAccessAuthorization.setAccessAuthorityId("1");
		// screen Id
		ivbMAccessAuthorization.setScreenId("0100");
		ivbMAccessAuthorization.setDeletableFlag(true);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenIdMock = new ArrayList<String>();
		screenIdMock.add("0095");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenIdMock);
		when(util.getUserInfo()).thenReturn(u);
		
		String accessAuthorityId = ivbMAccessAuthorization.getAccessAuthorityId();
		String screenId = ivbMAccessAuthorization.getScreenId();

		// Mock setup
		IvbMAccessAuthorizationMapper tmp = Mockito.mock(IvbMAccessAuthorizationMapper.class);
        when(bnn0095SearchAccessAuthorizationDao.getIvbMAccessAuthorizationMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).updateByPrimaryKeySelective(ivbMAccessAuthorization);

		// delete user
		Date lastUpdateDate = null;
		String result = bnn0095SearchAccessAuthorizationService.deleteData(accessAuthorityId, screenId, lastUpdateDate);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}
}
