package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.bananavietnam.ict.banana.component.Bnn0007InserDataConditions;
import vn.bananavietnam.ict.banana.component.Bnn0007SearchAreaConditions;
import vn.bananavietnam.ict.banana.component.Bnn0007SearchAreaFormResult;
import vn.bananavietnam.ict.banana.component.Bnn0007SearchAreaResult;
import vn.bananavietnam.ict.banana.dao.Bnn0007SearchAreaDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0007SearchAreaMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMManagerMapper;
import vn.bananavietnam.ict.common.db.mapper.UtilMapper;
import vn.bananavietnam.ict.common.db.model.IvbMArea;
import vn.bananavietnam.ict.common.db.model.IvbMKind;
import vn.bananavietnam.ict.common.db.model.IvbMKindExample;
import vn.bananavietnam.ict.common.db.model.IvbMManager;
import vn.bananavietnam.ict.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0007SearchAreaServiceTest {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private Bnn0007SearchAreaService bnn0007SearchAreaService;

	@InjectMocks
	private Bnn0007SearchAreaService bnn0007SearchAreaService_Mock;

	@Mock
	Bnn0007SearchAreaDao bnn0007SearchAreaDao;

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
		screenId.add("0007");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		when(util.getUserInfo()).thenReturn(u);
		Authentication authToken = new UsernamePasswordAuthenticationToken(u, "A0007", authorities);
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

	public void createUserLogin() throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0009");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        when(util.getUserInfo()).thenReturn(u);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void searchDataHaveResult()  {
		Bnn0007SearchAreaConditions searchConditions = new Bnn0007SearchAreaConditions();
		searchConditions.setFarmId("F001");
		searchConditions.setAreaId("A001");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");
		List<Bnn0007SearchAreaResult> list = new ArrayList<Bnn0007SearchAreaResult>();
		list.add(new Bnn0007SearchAreaResult());
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.searchData(any(HashMap.class))).thenReturn(list);
		when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("1");

		bnn0007SearchAreaService_Mock.setAppContext(appContext);

		// get search result
		List<Bnn0007SearchAreaResult> bnn0007ResultList = bnn0007SearchAreaService_Mock.searchData(searchConditions);
		// start testing
		Assert.assertEquals(1, bnn0007ResultList.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void searchDatNoResultWithoutFarmIdAndAreaId()  {
		Bnn0007SearchAreaConditions searchConditions = new Bnn0007SearchAreaConditions();
		searchConditions.setFarmId(Constants.SEARCH_CONDITION_NO_SELECT);
		searchConditions.setAreaId(Constants.SEARCH_CONDITION_NO_SELECT);
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");
		// get search result
		List<Bnn0007SearchAreaResult> list = new ArrayList<Bnn0007SearchAreaResult>();
		Bnn0007SearchAreaResult bnn0007SearchAreaResult = new Bnn0007SearchAreaResult();
		bnn0007SearchAreaResult.setFarmId("");
		bnn0007SearchAreaResult.setAreaId("");
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.searchData(any(HashMap.class))).thenReturn(list);
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		List<Bnn0007SearchAreaResult> bnn0007ResultList = bnn0007SearchAreaService_Mock.searchData(searchConditions);
		Assert.assertEquals(0, bnn0007ResultList.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void searchDatNoResult()  {
		Bnn0007SearchAreaConditions searchConditions = new Bnn0007SearchAreaConditions();
		searchConditions.setFarmId(Constants.SEARCH_CONDITION_NO_SELECT);
		searchConditions.setAreaId("A789");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");
		// get search result
		List<Bnn0007SearchAreaResult> list = new ArrayList<Bnn0007SearchAreaResult>();
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.searchData(any(HashMap.class))).thenReturn(list);
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		List<Bnn0007SearchAreaResult> bnn0007ResultList = bnn0007SearchAreaService_Mock.searchData(searchConditions);
		// start testing
		Assert.assertEquals(0, bnn0007ResultList.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataOutOfMemoryException() throws IllegalStateException {
		Bnn0007SearchAreaConditions searchConditions = new Bnn0007SearchAreaConditions();
		searchConditions.setFarmId("F123");
		searchConditions.setAreaId("A789");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");
		// Mock setup
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		doThrow(new OutOfMemoryError()).when(tmp).searchData(any(HashMap.class));

		bnn0007SearchAreaService_Mock.setAppContext(appContext);

		// get search result
		List<Bnn0007SearchAreaResult> bnn0007ResultList = bnn0007SearchAreaService_Mock.searchData(searchConditions);
		// start testing
		Assert.assertEquals("-1", bnn0007ResultList.get(0).getSearchDataTotalCounts());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataException() {
		Bnn0007SearchAreaConditions searchConditions = new Bnn0007SearchAreaConditions();
		searchConditions.setFarmId("F123");
		searchConditions.setAreaId("A789");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");
		// Mock setup
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));

		bnn0007SearchAreaService_Mock.setAppContext(appContext);

		// get search result
		List<Bnn0007SearchAreaResult> bnn0007ResultList = bnn0007SearchAreaService_Mock.searchData(searchConditions);
		// start testing
		Assert.assertEquals(null, bnn0007ResultList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataOuterException() {
		Bnn0007SearchAreaConditions searchConditions = new Bnn0007SearchAreaConditions();
		searchConditions.setFarmId("F123");
		searchConditions.setAreaId("A789");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");
		// Mock setup
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));

		// get search result
		List<Bnn0007SearchAreaResult> bnn0007ResultList = bnn0007SearchAreaService_Mock.searchData(searchConditions);
		// start testing
		Assert.assertEquals(null, bnn0007ResultList);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchGetSingleData() throws Exception {
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		createUserLogin();
		Bnn0007SearchAreaConditions searchConditions = new Bnn0007SearchAreaConditions();
		searchConditions.setFarmId(Constants.SEARCH_CONDITION_NO_SELECT);
		searchConditions.setAreaId("A001");
		searchConditions.setFarmId("F001");
		// get search result
		List<Bnn0007SearchAreaFormResult> list = new ArrayList<Bnn0007SearchAreaFormResult>();
		list.add(new Bnn0007SearchAreaFormResult());

		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.searchSingleData(any(HashMap.class))).thenReturn(list);
		List<Bnn0007SearchAreaFormResult> bnn0007ResultList = bnn0007SearchAreaService_Mock
				.getSingleData(searchConditions);
		// start testing
		Assert.assertEquals(1, bnn0007ResultList.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchGetSingleNoData() throws Exception {
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		createUserLogin();
		Bnn0007SearchAreaConditions searchConditions = new Bnn0007SearchAreaConditions();
		searchConditions.setFarmId(Constants.SEARCH_CONDITION_NO_SELECT);
		searchConditions.setAreaId("A001");
		searchConditions.setFarmId("F987");
		// get search result
		List<Bnn0007SearchAreaFormResult> list = new ArrayList<Bnn0007SearchAreaFormResult>();

		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.searchSingleData(any(HashMap.class))).thenReturn(list);
		List<Bnn0007SearchAreaFormResult> bnn0007ResultList = bnn0007SearchAreaService.getSingleData(searchConditions);
		// start testing
		Assert.assertEquals(0, bnn0007ResultList.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchGetSingleOutOfMemoryException()  throws Exception {
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		createUserLogin();
		Bnn0007SearchAreaConditions searchConditions = new Bnn0007SearchAreaConditions();
		searchConditions.setFarmId(Constants.SEARCH_CONDITION_NO_SELECT);
		searchConditions.setAreaId("A001");
		searchConditions.setFarmId("F987");
		// Mock setup
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		doThrow(new OutOfMemoryError()).when(tmp).searchSingleData(any(HashMap.class));

		// get search result
		List<Bnn0007SearchAreaFormResult> bnn0007ResultList = bnn0007SearchAreaService_Mock
				.getSingleData(searchConditions);
		// start testing
		Assert.assertEquals(1, bnn0007ResultList.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchGetSingleOuterException()  throws Exception {
		createUserLogin();
		Bnn0007SearchAreaConditions searchConditions = new Bnn0007SearchAreaConditions();
		searchConditions.setFarmId(Constants.SEARCH_CONDITION_NO_SELECT);
		searchConditions.setAreaId("A001");
		searchConditions.setFarmId("F987");
		// Mock setup
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		doThrow(new OutOfMemoryError()).when(tmp).searchSingleData(any(HashMap.class));

		// get search result
		List<Bnn0007SearchAreaFormResult> bnn0007ResultList = bnn0007SearchAreaService_Mock
				.getSingleData(searchConditions);
		// start testing
		Assert.assertEquals(null, bnn0007ResultList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchGetSingleDataException()  throws Exception {
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		createUserLogin();
		Bnn0007SearchAreaConditions searchConditions = new Bnn0007SearchAreaConditions();
		searchConditions.setFarmId(Constants.SEARCH_CONDITION_NO_SELECT);
		searchConditions.setAreaId("A001");
		searchConditions.setFarmId("F987");
		// Mock setup
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).searchSingleData(any(HashMap.class));

		// get search result
		List<Bnn0007SearchAreaFormResult> bnn0007ResultList = bnn0007SearchAreaService_Mock
				.getSingleData(searchConditions);
		// start testing
		Assert.assertEquals(null, bnn0007ResultList);
	}

	@Test
	public void testInsertDataRollBack()  {
		// CREATE OBEJCT MANAGER
		IvbMManager managerObj = null;

		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"A033\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";
		IvbMManagerMapper tmp = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmp);
		when(tmp.insert(managerObj)).thenReturn(0);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}

	@Test
	public void testInsertDataFaildAreaName()  {
		// CREATE OBEJCT MANAGER
		IvbMManager managerObj = null;

		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";
		IvbMManagerMapper tmp = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmp);
		when(tmp.insert(managerObj)).thenReturn(0);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}

	@Test
	public void testInsertDataFaildTextture()  {
		// CREATE OBEJCT MANAGER
		IvbMManager managerObj = null;

		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"A033\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"\",\"texture\":\"\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";
		IvbMManagerMapper tmp = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmp);
		when(tmp.insert(managerObj)).thenReturn(0);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}

	@Test
	public void testInsertDataResultIsqualtoOneWhenInsertArea()  {
		// CREATE oBJECT AREA
		Bnn0007InserDataConditions areaObj = new Bnn0007InserDataConditions();

		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"A032\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";
		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmp);
		when(tmp.insert(areaObj)).thenReturn(0);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInsertDataSuccess()  {
		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"A031\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";

		Bnn0007SearchAreaMapper tmp0007 = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp0007);
		when(tmp0007.getLastAreaId(any(HashMap.class))).thenReturn("A001");

		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", Constants.INSERT_RESULT_SUCCESSFUL);
				return null;
			}
		}).when(tmp0007).insertData(any(HashMap.class));

		List<Bnn0007SearchAreaResult> listAreaName = new ArrayList<Bnn0007SearchAreaResult>();
		Bnn0007SearchAreaResult bnn0007SearchAreaResult = new Bnn0007SearchAreaResult();
		bnn0007SearchAreaResult.setAreaName("A001");
		listAreaName.add(bnn0007SearchAreaResult);
		when(tmp0007.searchAreaNameByFarmId(any(HashMap.class))).thenReturn(listAreaName);

		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmp);
		when(tmp.insert(any(Bnn0007InserDataConditions.class))).thenReturn(1);

		IvbMManagerMapper tmpManager = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmpManager);
		when(tmpManager.insert(any(IvbMManager.class))).thenReturn(1);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInsertDataListAreaIsZeroAndResultFalse()  {
		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"A031\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";

		Bnn0007SearchAreaMapper tmp0007 = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp0007);
		when(tmp0007.getLastAreaId(any(HashMap.class))).thenReturn("A001");

		List<Bnn0007SearchAreaResult> listAreaName = new ArrayList<Bnn0007SearchAreaResult>();
		when(tmp0007.searchAreaNameByFarmId(any(HashMap.class))).thenReturn(listAreaName);

		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmp);
		when(tmp.insert(any(Bnn0007InserDataConditions.class))).thenReturn(1);

		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", Constants.INSERT_RESULT_FAILED);
				return null;
			}
		}).when(tmp0007).insertData(any(HashMap.class));

		IvbMManagerMapper tmpManager = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmpManager);
		when(tmpManager.insert(any(IvbMManager.class))).thenReturn(1);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInsertDataExcep()  {
		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"A031\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";

		Bnn0007SearchAreaMapper tmp0007 = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp0007);
		when(tmp0007.getLastAreaId(any(HashMap.class))).thenReturn("A001");
		doThrow(new RuntimeException()).when(tmp0007).insertData(any(HashMap.class));

		List<Bnn0007SearchAreaResult> listAreaName = new ArrayList<Bnn0007SearchAreaResult>();
		Bnn0007SearchAreaResult bnn0007SearchAreaResult = new Bnn0007SearchAreaResult();
		bnn0007SearchAreaResult.setAreaName("A001");
		listAreaName.add(bnn0007SearchAreaResult);

		when(tmp0007.searchAreaNameByFarmId(any(HashMap.class))).thenReturn(listAreaName);

		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmp);
		when(tmp.insert(any(Bnn0007InserDataConditions.class))).thenReturn(1);

		IvbMManagerMapper tmpManager = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmpManager);
		when(tmpManager.insert(any(IvbMManager.class))).thenReturn(1);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInsertDataSuccess2()  {
		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"A031\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";

		Bnn0007SearchAreaMapper tmp0007 = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp0007);
		when(tmp0007.getLastAreaId(any(HashMap.class))).thenReturn(Constants.DEFAULT_AREA);

		List<Bnn0007SearchAreaResult> listAreaName = new ArrayList<Bnn0007SearchAreaResult>();
		Bnn0007SearchAreaResult bnn0007SearchAreaResult = new Bnn0007SearchAreaResult();
		bnn0007SearchAreaResult.setAreaName("A031");
		listAreaName.add(bnn0007SearchAreaResult);
		bnn0007SearchAreaResult = new Bnn0007SearchAreaResult();
		bnn0007SearchAreaResult.setAreaName("A001");
		listAreaName.add(bnn0007SearchAreaResult);

		when(tmp0007.searchAreaNameByFarmId(any(HashMap.class))).thenReturn(listAreaName);

		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmp);
		when(tmp.insert(any(Bnn0007InserDataConditions.class))).thenReturn(1);

		IvbMManagerMapper tmpManager = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmpManager);
		when(tmpManager.insert(any(IvbMManager.class))).thenReturn(1);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_AREA, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInsertDataJsonParseException()  {
		String areaObj1 = "{\"areaId:\"\",\"areaName\":\"A031\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";

		Bnn0007SearchAreaMapper tmp0007 = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp0007);

		when(tmp0007.getLastAreaId(any(HashMap.class))).thenReturn("A001");

		List<Bnn0007SearchAreaResult> listAreaName = new ArrayList<Bnn0007SearchAreaResult>();
		Bnn0007SearchAreaResult bnn0007SearchAreaResult = new Bnn0007SearchAreaResult();
		bnn0007SearchAreaResult.setAreaName("A031");
		listAreaName.add(bnn0007SearchAreaResult);
		when(tmp0007.searchAreaNameByFarmId(any(HashMap.class))).thenReturn(listAreaName);

		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmp);
		when(tmp.insert(any(Bnn0007InserDataConditions.class))).thenReturn(1);

		IvbMManagerMapper tmpManager = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmpManager);
		when(tmpManager.insert(any(IvbMManager.class))).thenReturn(1);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInsertDataJsonMappingException()  {
		String areaObj1 = "{\"dsd\":\"A031\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";

		Bnn0007SearchAreaMapper tmp0007 = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp0007);

		when(tmp0007.getLastAreaId(any(HashMap.class))).thenReturn("A001");

		List<Bnn0007SearchAreaResult> listAreaName = new ArrayList<Bnn0007SearchAreaResult>();
		Bnn0007SearchAreaResult bnn0007SearchAreaResult = new Bnn0007SearchAreaResult();
		bnn0007SearchAreaResult.setAreaName("A001");
		listAreaName.add(bnn0007SearchAreaResult);
		when(tmp0007.searchAreaNameByFarmId(any(HashMap.class))).thenReturn(listAreaName);

		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmp);
		when(tmp.insert(any(Bnn0007InserDataConditions.class))).thenReturn(1);

		IvbMManagerMapper tmpManager = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmpManager);
		when(tmpManager.insert(any(IvbMManager.class))).thenReturn(1);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInsertDataInnerException()  {

		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"A031\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";

		Bnn0007SearchAreaMapper tmp0007 = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp0007);

		when(tmp0007.getLastAreaId(any(HashMap.class))).thenReturn("A999");

		List<Bnn0007SearchAreaResult> listAreaName = new ArrayList<Bnn0007SearchAreaResult>();
		Bnn0007SearchAreaResult bnn0007SearchAreaResult = new Bnn0007SearchAreaResult();
		bnn0007SearchAreaResult.setAreaName("A001");
		listAreaName.add(bnn0007SearchAreaResult);
		when(tmp0007.searchAreaNameByFarmId(any(HashMap.class))).thenReturn(listAreaName);

		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmp);
		when(tmp.insert(any(Bnn0007InserDataConditions.class))).thenReturn(0);

		IvbMManagerMapper tmpManager = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmpManager);
		when(tmpManager.insert(any(IvbMManager.class))).thenReturn(1);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_AREA, result);
	}

	@Test
	public void testInsertDataWrongFormatFarmId()  {

		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"A031\",\"farmId\":\"L027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"L027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";

		String result = bnn0007SearchAreaService.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_WRONG_FORMAT, result);
	}

	@Test
	public void testInsertDataJsonMapExcepytion() throws JsonParseException, JsonMappingException, IOException {

		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"A031\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";

		Bnn0007InserDataConditions areaData = null;

		ObjectMapper tmp = Mockito.mock(ObjectMapper.class);
		when(tmp.readValue(areaObj1, Bnn0007InserDataConditions.class)).thenReturn(areaData);
		doThrow(new JsonMappingException(areaObj1)).when(tmp).readValue(areaObj1, Bnn0007InserDataConditions.class);

		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInsertDataDuplicateName()  {
		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"A001\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";

		List<Bnn0007SearchAreaResult> listAreaName = new ArrayList<Bnn0007SearchAreaResult>();
		Bnn0007SearchAreaResult bnn0007SearchAreaResult = new Bnn0007SearchAreaResult();
		bnn0007SearchAreaResult.setAreaName("A001");
		listAreaName.add(bnn0007SearchAreaResult);

		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.getLastAreaId(any(HashMap.class))).thenReturn("A001");
		when(tmp.searchAreaNameByFarmId(any(HashMap.class))).thenReturn(listAreaName);
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		IvbMAreaMapper tmpArea = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmpArea);
        doThrow(new DuplicateKeyException("")).when(tmpArea).insert(any(IvbMArea.class));
		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_DUPLICATED, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInsertDataAreaIdNull()  {
		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"A001\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";

		List<Bnn0007SearchAreaResult> listAreaName = new ArrayList<Bnn0007SearchAreaResult>();
		Bnn0007SearchAreaResult bnn0007SearchAreaResult = new Bnn0007SearchAreaResult();
		bnn0007SearchAreaResult.setAreaName("A001");
		listAreaName.add(bnn0007SearchAreaResult);

		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.getLastAreaId(any(HashMap.class))).thenReturn(null);
		when(tmp.searchAreaNameByFarmId(any(HashMap.class))).thenReturn(listAreaName);
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_DUPLICATED, result);
	}

	@Test
	public void testInsertDataResultIsqualtoOneWhenInsertManager()  {
		// CREATE oBJECT AREA
		Bnn0007InserDataConditions areaObj = new Bnn0007InserDataConditions();
		// Area id
		areaObj.setAreaId("A008");
		// Area name
		areaObj.setAreaName("A008");
		// Area Manager
		areaObj.setAreaManager("KAKA");
		// Kind Id
		areaObj.setKindId("K001");
		// Sugar Content
		areaObj.setSugarContent("1");
		// Texture
		areaObj.setTexture("1");
		// Prospective Harvest Amount
		areaObj.setProspectiveHarvestAmount(0.000);
		// Estimated Days Flowering
		areaObj.setEstimatedDaysFlowering(3);
		// Estimated Days Bagging(
		areaObj.setEstimatedDaysBagging(3);
		// Estimated Days Harvest
		areaObj.setEstimatedDaysHarvest(3);
		// Farm Id
		areaObj.setFarmId("F003");
		// note
		areaObj.setNote("");
		// create user id
		areaObj.setCreateUserId((new Util()).getUserInfo().getID());
		// update user id
		areaObj.setUpdateUserId((new Util()).getUserInfo().getID());
		// delete flag
		areaObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);
		// CREATE OBEJCT MANAGER
		IvbMManager managerObj = new IvbMManager();
		// user id
		managerObj.setUsersId("A0001");
		// farm id
		managerObj.setFarmId("F003");
		// Area id
		managerObj.setAreaId("A008");
		// get Data from DB to set params
		managerObj.setAuthorizationTypeId("1");
		// create user id
		managerObj.setCreateUserId((new Util()).getUserInfo().getID());
		// create date
		managerObj.setCreateDate(Calendar.getInstance().getTime());
		// update user id
		managerObj.setUpdateUserId((new Util()).getUserInfo().getID());
		// last update date
		managerObj.setLastUpdateDate(Calendar.getInstance().getTime());
		// delete flag
		managerObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);

		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"A030\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";

		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(null);
		when(tmp.insert(areaObj)).thenReturn(10);

		IvbMManagerMapper tmp1 = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(null);
		when(tmp1.insert(managerObj)).thenReturn(0);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		// start testing, could not get logged in user's id in test mode
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);

	}

	@Test
	@Transactional
	public void testInsertOuterExcetion()  {
		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"A030\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";
		// mock setup
		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).insert(any(IvbMArea.class));

		bnn0007SearchAreaService_Mock.setAppContext(appContext);

		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}

	@Test
	public void Bnn0009ValueTest() {
		bnn0007SearchAreaService.bnn0009value("F027", "A026");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateData()  {
		String areaObj1 = "{\"areaId\":\"A028\",\"areaName\":\"A032\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";

		List<Bnn0007SearchAreaResult> listAreaName = new ArrayList<Bnn0007SearchAreaResult>();
		Bnn0007SearchAreaResult bnn0007SearchAreaResult = new Bnn0007SearchAreaResult();
		bnn0007SearchAreaResult.setAreaName("A001");
		listAreaName.add(bnn0007SearchAreaResult);

		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.searchAreaNameByFarmId(any(HashMap.class))).thenReturn(listAreaName);

		IvbMAreaMapper tmpArea = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmpArea);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);

		IvbMManagerMapper tmpManager = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmpManager);
		when(tmpManager.insert(any(IvbMManager.class))).thenReturn(1);
		when(tmpManager.deleteByPrimaryKey(any(IvbMManager.class))).thenReturn(1);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.updateData(areaObj1, lastUpdateDate);
		Assert.assertEquals(Constants.UPDATE_RESULT_SUCCESSFUL, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateDataListAreaIsZero()  {
		String areaObj1 = "{\"areaId\":\"A028\",\"areaName\":\"A032\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";

		List<Bnn0007SearchAreaResult> listAreaName = new ArrayList<Bnn0007SearchAreaResult>();

		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.searchAreaNameByFarmId(any(HashMap.class))).thenReturn(listAreaName);

		IvbMAreaMapper tmpArea = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmpArea);
		when(tmpArea.updateByPrimaryKeySelective(any(IvbMArea.class))).thenReturn(1);

		IvbMManagerMapper tmpManager = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmpManager);
		when(tmpManager.insert(any(IvbMManager.class))).thenReturn(1);
		when(tmpManager.deleteByPrimaryKey(any(IvbMManager.class))).thenReturn(1);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.updateData(areaObj1, lastUpdateDate);
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateDataUserJsonParseException() {
		String areaObj1 = "{\"areaId:\"A028\",\"areaName\":\"A032\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";

		List<Bnn0007SearchAreaResult> listAreaName = new ArrayList<Bnn0007SearchAreaResult>();
		Bnn0007SearchAreaResult bnn0007SearchAreaResult = new Bnn0007SearchAreaResult();
		bnn0007SearchAreaResult.setAreaName("A001");
		listAreaName.add(bnn0007SearchAreaResult);

		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.searchAreaNameByFarmId(any(HashMap.class))).thenReturn(listAreaName);

		IvbMAreaMapper tmpArea = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmpArea);
		when(tmpArea.updateByPrimaryKeySelective(any(IvbMArea.class))).thenReturn(1);

		IvbMManagerMapper tmpManager = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmpManager);
		when(tmpManager.insert(any(IvbMManager.class))).thenReturn(1);

		when(tmpManager.deleteByPrimaryKey(any(IvbMManager.class))).thenReturn(1);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.updateData(areaObj1, lastUpdateDate);
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateDataUserJsonMappingException() {
		String areaObj1 = "{\"areaNEAD\":\"A032\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";

		List<Bnn0007SearchAreaResult> listAreaName = new ArrayList<Bnn0007SearchAreaResult>();
		Bnn0007SearchAreaResult bnn0007SearchAreaResult = new Bnn0007SearchAreaResult();
		bnn0007SearchAreaResult.setAreaName("A001");
		listAreaName.add(bnn0007SearchAreaResult);

		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.searchAreaNameByFarmId(any(HashMap.class))).thenReturn(listAreaName);

		IvbMAreaMapper tmpArea = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmpArea);
		when(tmpArea.updateByPrimaryKeySelective(any(IvbMArea.class))).thenReturn(1);

		IvbMManagerMapper tmpManager = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmpManager);
		when(tmpManager.insert(any(IvbMManager.class))).thenReturn(1);

		when(tmpManager.deleteByPrimaryKey(any(IvbMManager.class))).thenReturn(1);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.updateData(areaObj1, lastUpdateDate);
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateDataRollBack()  {
		String areaObj1 = "{\"areaId\":\"A028\",\"areaName\":\"A032\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";

		List<Bnn0007SearchAreaResult> listAreaName = new ArrayList<Bnn0007SearchAreaResult>();
		Bnn0007SearchAreaResult bnn0007SearchAreaResult = new Bnn0007SearchAreaResult();
		bnn0007SearchAreaResult.setAreaName("A001");
		listAreaName.add(bnn0007SearchAreaResult);

		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.searchAreaNameByFarmId(any(HashMap.class))).thenReturn(listAreaName);

		IvbMAreaMapper tmpArea = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmpArea);
		when(tmpArea.updateByPrimaryKeySelective(any(IvbMArea.class))).thenReturn(-1);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.updateData(areaObj1, lastUpdateDate);
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_UPDATE_DATE, result);
	}

	@Test
	public void testUpdateInnerException()  {
		String areaObj1 = "{\"areaId\":\"A028\",\"areaName\":\"A045\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";

		IvbMManagerMapper tmpManager = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmpManager);
		when(tmpManager.updateByPrimaryKeySelective(any(IvbMManager.class))).thenReturn(0);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.updateData(areaObj1, lastUpdateDate);
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateDuplicateName()  {
		String areaObj1 = "{\"areaId\":\"A001\",\"areaName\":\"A001\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";

		List<Bnn0007SearchAreaResult> listAreaName = new ArrayList<Bnn0007SearchAreaResult>();
		Bnn0007SearchAreaResult bnn0007SearchAreaResult = new Bnn0007SearchAreaResult();
		bnn0007SearchAreaResult.setAreaName("A001");
		listAreaName.add(bnn0007SearchAreaResult);

		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
        doThrow(new DuplicateKeyException("")).when(tmp).updateData(any(HashMap.class));

		IvbMAreaMapper tmpArea = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmpArea);
		when(tmpArea.updateByPrimaryKeySelective(any(IvbMArea.class))).thenReturn(1);

		IvbMManagerMapper tmpManager = Mockito.mock(IvbMManagerMapper.class);
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(tmpManager);
		when(tmpManager.insert(any(IvbMManager.class))).thenReturn(1);

		when(tmpManager.deleteByPrimaryKey(any(IvbMManager.class))).thenReturn(1);

		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.updateData(areaObj1, lastUpdateDate);
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_DUPLLICATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateByPrimaryKeySelective() throws Exception {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		String areaObj1 = "{\"areaId\":\"A020\",\"areaName\":\"A045\",\"farmId\":\"F001\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		createUserLogin();
		Bnn0007SearchAreaConditions searchConditions = new Bnn0007SearchAreaConditions();
		searchConditions.setFarmId(Constants.SEARCH_CONDITION_NO_SELECT);
		searchConditions.setAreaId("A001");
		searchConditions.setFarmId("F001");
		// get search result
		List<Bnn0007SearchAreaFormResult> list = new ArrayList<Bnn0007SearchAreaFormResult>();
		Bnn0007SearchAreaFormResult  bnn0007SearchAreaFormResult = new Bnn0007SearchAreaFormResult();
		bnn0007SearchAreaFormResult.setLastUpdateDate(date);
		list.add(bnn0007SearchAreaFormResult);

		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.searchSingleData(any(HashMap.class))).thenReturn(list);
		
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(-1);

		String result = bnn0007SearchAreaService_Mock.updateData(areaObj1, format.parse("2017/07/17 13:25:25"));
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataError() throws Exception {
		String dateTestStr = "2017/05/18 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(dateTestStr);
		String areaObj1 = "{\"areaId\":\"A020\",\"areaName\":\"A045\",\"farmId\":\"F001\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		createUserLogin();
		Bnn0007SearchAreaConditions searchConditions = new Bnn0007SearchAreaConditions();
		searchConditions.setFarmId(Constants.SEARCH_CONDITION_NO_SELECT);
		searchConditions.setAreaId("A001");
		searchConditions.setFarmId("F001");
		// get search result
		List<Bnn0007SearchAreaFormResult> list = new ArrayList<Bnn0007SearchAreaFormResult>();
		Bnn0007SearchAreaFormResult  bnn0007SearchAreaFormResult = new Bnn0007SearchAreaFormResult();
		bnn0007SearchAreaFormResult.setLastUpdateDate(date);
		list.add(bnn0007SearchAreaFormResult);

		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.searchSingleData(any(HashMap.class))).thenReturn(list);
		
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(-1);

		String result = bnn0007SearchAreaService_Mock.updateData(areaObj1, date);
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_CULTIVATION_AREA, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataFailedUpdateDate1() {
		String areaObj1 = "{\"areaId\":\"A028\",\"areaName\":\"A045\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\",\"lastUpdateDate\":\"2\"}";

		bnn0007SearchAreaService.setAppContext(appContext);

		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(0);

		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.updateData(areaObj1, lastUpdateDate);
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateException() {
		String areaObj1 = "{\"areaId\":\"A028\",\"areaName\":\"A045\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";

		bnn0007SearchAreaService_Mock.setAppContext(appContext);

		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		when(tmp.searchAreaNameByFarmId(any(HashMap.class))).thenReturn(null);

		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.updateData(areaObj1, lastUpdateDate);
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_UPDATE_DATE, result);
	}

	@Test
	@Transactional
	public void testUpdateDataWrongFormatId() {
		String areaObj1 = "{\"areaId\":\"A028\",\"areaName\":\"A032\",\"farmId\":\"A027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";

		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService.updateData(areaObj1, lastUpdateDate);
		Assert.assertEquals(Constants.VALIDATE_WRONG_FORMAT, result);
	}

	@Test
	@Transactional
	public void testDeleteDataException() {
		// Mock setup
		when(bnn0007SearchAreaDao.getIvbMManagerMapper()).thenReturn(null);

		// delete user
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.deleteData("A024", "F027", "A0001", lastUpdateDate);
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}

	@Test
	@Transactional
	public void testDeleteDataOuterException() {
		// delete user
		bnn0007SearchAreaService_Mock.setAppContext(null);
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.deleteData("A015", "F027", "A0001", lastUpdateDate);
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataResultAnyInt()  {
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", 0);
				return null;
			}
		}).when(tmp).deleteData(any(HashMap.class));
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		// delete block
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.deleteData("A015", "F027", "A0001", lastUpdateDate);
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataResultAnyString()  {
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", "Error");
				return null;
			}
		}).when(tmp).deleteData(any(HashMap.class));
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		// delete block
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.deleteData("A015", "F027", "A0001", lastUpdateDate);
		Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataResultFailedUpdateDate()  {
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", Constants.DELETE_RESULT_FAILED_UPDATE_DATE);
				return null;
			}
		}).when(tmp).deleteData(any(HashMap.class));
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		// delete block
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.deleteData("A015", "F027", "A0001", lastUpdateDate);
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataNull()  {
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", null);
				return null;
			}
		}).when(tmp).deleteData(any(HashMap.class));
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		// delete block
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.deleteData("A015", "F027", "A0001", lastUpdateDate);
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_AREA, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataEmpty()  {
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", "");
				return null;
			}
		}).when(tmp).deleteData(any(HashMap.class));
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		// delete block
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.deleteData("A015", "F027", "A0001", lastUpdateDate);
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_AREA, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataRollBack()  {
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", Constants.DELETE_RESULT_FAILED_CULTIVATION_AREA);
				return null;
			}
		}).when(tmp).deleteData(any(HashMap.class));
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		// delete block
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.deleteData("A015", "F027", "A0001", lastUpdateDate);
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_AREA, result);
	}

	@Test
	public void testGetAreaDataByFarmId() throws Exception {
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		createUserLogin();
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		List<UtilComponent> areaData = new ArrayList<UtilComponent>();
		UtilComponent utilComponent = new UtilComponent();
		utilComponent.setFarmName("F001");
		utilComponent.setAreaName("A001");
		areaData.add(utilComponent);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		when(util.getAreaDataByFarmId(any(UtilDao.class),any(String.class))).thenReturn(areaData);
		bnn0007SearchAreaService_Mock.getAreaDataByFarmId("F001");
	}

	@Test
	public void testGetAreaDataByFarmIdException() throws Exception {
		createUserLogin();
		when(utilDao.getUtilMapper()).thenReturn(null);
		bnn0007SearchAreaService_Mock.getAreaDataByFarmId("F001");
	}

	@Test
	public void initData() throws Exception {
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		createUserLogin();
		Model model = Mockito.mock(Model.class);
		// Mock setup
		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		UtilComponent utilComponent = new UtilComponent();
		utilComponent.setFarmName("F001");
		farmData.add(utilComponent);
		when(util.getFarmData(any(UtilDao.class))).thenReturn(farmData);

		// Mock setup
		List<IvbMKind> kindData = new ArrayList<IvbMKind>();
		IvbMKind ivbMKind = new IvbMKind();
		ivbMKind.setKindName("K002");
		kindData.add(ivbMKind);
		IvbMKindMapper tmpKind = Mockito.mock(IvbMKindMapper.class);
		when(bnn0007SearchAreaDao.getIvbMKindMapper()).thenReturn(tmpKind);
		when(tmpKind.selectByExample(null)).thenReturn(kindData);
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		bnn0007SearchAreaService_Mock.initData(model, "F001");
	}

	@Test
	public void initDataException() throws Exception {
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		createUserLogin();
		Model model = Mockito.mock(Model.class);

		doThrow(new RuntimeException()).when(util).getFarmData(any(UtilDao.class));

		// Mock setup
		IvbMKindMapper tmpKind = Mockito.mock(IvbMKindMapper.class);
		when(bnn0007SearchAreaDao.getIvbMKindMapper()).thenReturn(tmpKind);
		doThrow(new RuntimeException()).when(tmpKind).selectByExample(null);
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		bnn0007SearchAreaService_Mock.initData(model, "F001");
	}

	@Test
	public void initDataExceptionKind()  throws Exception {
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		createUserLogin();
		Model model = Mockito.mock(Model.class);

		// Mock setup
		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		UtilComponent utilComponent = new UtilComponent();
		utilComponent.setFarmName("F001");
		farmData.add(utilComponent);
		when(util.getFarmData(any(UtilDao.class))).thenReturn(farmData);
		// Mock setup
		IvbMKindExample kindExample = null;
		IvbMKindMapper tmpKind = Mockito.mock(IvbMKindMapper.class);
		when(bnn0007SearchAreaDao.getIvbMKindMapper()).thenReturn(tmpKind);
		doThrow(new RuntimeException()).when(tmpKind).selectByExample(kindExample);
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		bnn0007SearchAreaService_Mock.initData(model, "F001");
	}

	@Test
	public void initDataConvertiszieKind() throws Exception {
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		createUserLogin();
		Model model = Mockito.mock(Model.class);

		// Mock setup
		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		UtilComponent utilComponent = new UtilComponent();
		utilComponent.setFarmName("F001");
		farmData.add(utilComponent);
		when(util.getFarmData(any(UtilDao.class))).thenReturn(farmData);
		List<IvbMKind> kindData = new ArrayList<IvbMKind>();
		IvbMKind ivbMKind = new IvbMKind();
		ivbMKind.setKindName(null);
		kindData.add(ivbMKind);
		// Mock setup
		IvbMKindMapper tmpKind = Mockito.mock(IvbMKindMapper.class);
		when(bnn0007SearchAreaDao.getIvbMKindMapper()).thenReturn(tmpKind);
		when(tmpKind.selectByExample(any(IvbMKindExample.class))).thenReturn(kindData); 
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		bnn0007SearchAreaService_Mock.initData(model, "F001");
	}

	@Test
	public void initDataConvertiszieKindException() throws Exception {
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		createUserLogin();
		Model model = Mockito.mock(Model.class);

		// Mock setup
		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		UtilComponent utilComponent = new UtilComponent();
		utilComponent.setFarmName("F001");
		farmData.add(utilComponent);
		when(util.getFarmData(any(UtilDao.class))).thenReturn(farmData);
		List<IvbMKind> kindData = new ArrayList<IvbMKind>();
		IvbMKind ivbMKind = new IvbMKind();
		ivbMKind.setKindName(null);
		kindData.add(ivbMKind);
		// Mock setup
		IvbMKindMapper tmpKind = Mockito.mock(IvbMKindMapper.class);
		when(bnn0007SearchAreaDao.getIvbMKindMapper()).thenReturn(tmpKind);
		when(tmpKind.selectByExample(any(IvbMKindExample.class))).thenReturn(null);
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		bnn0007SearchAreaService_Mock.initData(model, "F001");
	}

	@Test
	@Transactional
	public void testInsertDataRollBack1() {

		String areaObj1 = "{\"areaId\":\"\",\"areaName\":\"A045\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj1 = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";

		bnn0007SearchAreaService_Mock.setAppContext(appContext);

		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
		when(bnn0007SearchAreaDao.getIvbMAreaMapper()).thenReturn(tmp);
		when(tmp.insert(any(IvbMArea.class))).thenReturn(0);

		String result = bnn0007SearchAreaService_Mock.insertData(areaObj1, managerObj1);
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);

	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataSuccess()  {
		Bnn0007SearchAreaMapper tmp = Mockito.mock(Bnn0007SearchAreaMapper.class);
		when(bnn0007SearchAreaDao.getBnn0007SearchAreaMapper()).thenReturn(tmp);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", Constants.DELETE_RESULT_SUCCESSFUL);
				return null;
			}
		}).when(tmp).deleteData(any(HashMap.class));
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		
		// delete block
		Date lastUpdateDate = null;
		String result = bnn0007SearchAreaService_Mock.deleteData("A015", "F027", "A0001", lastUpdateDate);
		Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
	}

	@Test
	public void TestGetSingleDataKindSucess() {
		IvbMKind ivbMKind = new IvbMKind();
		
		IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
		when(bnn0007SearchAreaDao.getIvbMKindMapper()).thenReturn(tmp);
		when(tmp.selectByPrimaryKey(any(String.class))).thenReturn(ivbMKind);
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		IvbMKind result = bnn0007SearchAreaService_Mock.getSingleDataKind("K001");
		Assert.assertEquals(ivbMKind, result);
	}

	@Test
	public void TestGetSingleDataKindRollBack() {
		IvbMKind ivbMKind = new IvbMKind();
		
		IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
		when(bnn0007SearchAreaDao.getIvbMKindMapper()).thenReturn(null);
		when(tmp.selectByPrimaryKey(any(String.class))).thenReturn(ivbMKind);
		bnn0007SearchAreaService_Mock.setAppContext(appContext);
		IvbMKind result = bnn0007SearchAreaService_Mock.getSingleDataKind("K001");
		Assert.assertEquals(null, result);
	}
	@Test
	public void TestGetSingleDataKindOuterException() {
		IvbMKind result = bnn0007SearchAreaService_Mock.getSingleDataKind("K001");
		Assert.assertEquals(null, result);
	}
}
