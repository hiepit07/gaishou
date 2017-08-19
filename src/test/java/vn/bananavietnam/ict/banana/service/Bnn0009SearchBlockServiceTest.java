package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.bananavietnam.ict.banana.component.Bnn0009SearchBlockConditions;
import vn.bananavietnam.ict.banana.component.Bnn0009SearchBlockProductAdjust;
import vn.bananavietnam.ict.banana.component.Bnn0009SearchBlockResult;
import vn.bananavietnam.ict.banana.dao.Bnn0009SearchBlockDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0009SearchBlockMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.mapper.IvbMBlockMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTProductMapper;
import vn.bananavietnam.ict.common.db.mapper.UtilMapper;
import vn.bananavietnam.ict.common.db.model.IvbMBlock;
import vn.bananavietnam.ict.common.db.model.IvbMBlockExample;
import vn.bananavietnam.ict.common.db.model.IvbMBlockKey;
import vn.bananavietnam.ict.common.db.model.IvbMFarm;
import vn.bananavietnam.ict.common.db.model.IvbTProduct;
import vn.bananavietnam.ict.common.db.model.IvbTProductExample;
import vn.bananavietnam.ict.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0009SearchBlockServiceTest {

	@Autowired
	private ApplicationContext appContext;

	@InjectMocks
	private Bnn0009SearchBlockService bnn0009SearchBlockService;

	@Mock
	ObjectMapper mapper = new ObjectMapper();
	PlatformTransactionManager txManager;

	@Mock
	Util util;
	@Mock
	Bnn0009SearchBlockDao dao;
	@Mock
	UtilDao utilDao;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0009");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
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

	@Test
	public void initDataTest() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);

		createUserLogin();

		String farmId = "F027";
		String areaId = "A000";
		Model model = Mockito.mock(Model.class);
		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		UtilComponent object = new UtilComponent();
		object.setFarmName("F001");
		farmData.add(object);
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		when(tmp.selectFarmDataMaster()).thenReturn(farmData);

		//doThrow(new RuntimeException()).when(mapper).writeValueAsString(farmData);
		when(mapper.writeValueAsString(farmData)).thenReturn("OKL");
		
		bnn0009SearchBlockService.initData(model, farmId, areaId);
	}

	@Test
	public void initDataTestWithAreaObjectNull() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String farmId = null;
		String areaId = "A000";
		
		Model model = Mockito.mock(Model.class);
		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		UtilComponent utilComponent = new UtilComponent();
		utilComponent.setFarmName("F001");
		farmData.add(utilComponent);
		when(util.getFarmData(any(UtilDao.class))).thenReturn(farmData);
		// doThrow(new
		// RuntimeException()).when(mapper).writeValueAsString(farmData);
		when(mapper.writeValueAsString(farmData)).thenReturn("OKL");

		bnn0009SearchBlockService.initData(model, farmId, areaId);
	}

	@Test
	public void initDataTestThrowRuntimeException() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		
		createUserLogin();
		String farmId = "F001";
		String areaId = "A000";
		
		Model model = Mockito.mock(Model.class);
		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		/*when(tmp.getFarmData(any(UtilDao.class))).thenReturn(farmData);
		doThrow(new RuntimeException()).when(tmp).getFarmData(any(UtilDao.class));*/
		
		when(mapper.writeValueAsString(farmData)).thenReturn("OKL");
		doThrow(new RuntimeException()).when(model).addAttribute(any(String.class), eq("OKL"));
		bnn0009SearchBlockService.setAppContext(appContext);
		bnn0009SearchBlockService.initData(model, farmId, areaId);
	}

	@Test
	public void initDataTestThrowOuterException() throws Exception {
		
		createUserLogin();
		String farmId = "F001";
		String areaId = "A000";
		
		Model model = Mockito.mock(Model.class);
		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		/*when(tmp.getFarmData(any(UtilDao.class))).thenReturn(farmData);
		doThrow(new RuntimeException()).when(tmp).getFarmData(any(UtilDao.class));*/
		
		when(mapper.writeValueAsString(farmData)).thenReturn("OKL");
		doThrow(new RuntimeException()).when(model).addAttribute(any(String.class), eq("OKL"));
		bnn0009SearchBlockService.initData(model, farmId, areaId);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void initDataTestWithAreaObjectNullThrowRuntimeException() throws Exception {

		bnn0009SearchBlockService.setAppContext(appContext);
		
		createUserLogin();
		String farmId = null;
		String areaId = "A000";
		Model model = Mockito.mock(Model.class);
		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		when(tmp.selectFarmData(any(HashMap.class))).thenReturn(farmData);

		when(mapper.writeValueAsString(farmData)).thenReturn("OKL");
		doThrow(new RuntimeException()).when(model).addAttribute(any(String.class), eq("OKL"));
		bnn0009SearchBlockService.setAppContext(appContext);
		bnn0009SearchBlockService.initData(model, farmId, areaId);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void initDataTestWithAreaObjectNullThrowOuterException() throws Exception {
		createUserLogin();
		String farmId = null;
		String areaId = "A000";
		Model model = Mockito.mock(Model.class);
		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		when(tmp.selectFarmData(any(HashMap.class))).thenReturn(farmData);

		when(mapper.writeValueAsString(farmData)).thenReturn("OKL");
		doThrow(new RuntimeException()).when(model).addAttribute(any(String.class), eq("OKL"));
		bnn0009SearchBlockService.initData(model, farmId, areaId);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataNoResult() throws Exception {
		createUserLogin();
		Bnn0009SearchBlockConditions searchConditions = new Bnn0009SearchBlockConditions();
		// farm id
		searchConditions.setFarmId("F002");
		// area id
		searchConditions.setAreaId("A001");
		// block id
		searchConditions.setBlockId("B001");
		// block name
		searchConditions.setBlockName("B1");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");

		List<Bnn0009SearchBlockResult> bnn0009ResultListReturn = new ArrayList<Bnn0009SearchBlockResult>();

		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0009ResultListReturn);
		when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
		// get search result
		bnn0009SearchBlockService.setAppContext(appContext);
		List<Bnn0009SearchBlockResult> bnn0009ResultList = bnn0009SearchBlockService.searchData(searchConditions);
		// start testing
		Assert.assertEquals(0, bnn0009ResultList.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataOutOfMemoryException() throws Exception {
		createUserLogin();
		Bnn0009SearchBlockConditions searchConditions = new Bnn0009SearchBlockConditions();
		// farm id
		searchConditions.setFarmId("F001");
		// area id
		searchConditions.setAreaId("A001");
		// block id
		searchConditions.setBlockId("B001");
		// block name
		searchConditions.setBlockName("B1");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");

		// Mock setup
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		doThrow(new OutOfMemoryError()).when(tmp).searchData(any(HashMap.class));
		bnn0009SearchBlockService.setAppContext(appContext);
		// get search result
		List<Bnn0009SearchBlockResult> bnn0009ResultList = bnn0009SearchBlockService.searchData(searchConditions);
		// start testing
		Assert.assertEquals("-1", bnn0009ResultList.get(0).getSearchDataTotalCounts());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataOuterException() throws Exception {
		createUserLogin();
		Bnn0009SearchBlockConditions searchConditions = new Bnn0009SearchBlockConditions();
		// farm id
		searchConditions.setFarmId("F001");
		// area id
		searchConditions.setAreaId("A001");
		// block id
		searchConditions.setBlockId("B001");
		// block name
		searchConditions.setBlockName("B1");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");

		// Mock setup
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		doThrow(new OutOfMemoryError()).when(tmp).searchData(any(HashMap.class));
		// get search result
		List<Bnn0009SearchBlockResult> bnn0009ResultList = bnn0009SearchBlockService.searchData(searchConditions);
		// start testing
		Assert.assertEquals(null, bnn0009ResultList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataException() throws Exception {
		createUserLogin();
		Bnn0009SearchBlockConditions searchConditions = new Bnn0009SearchBlockConditions();
		// farm id
		searchConditions.setFarmId("F001");
		// area id
		searchConditions.setAreaId("A001");
		// block id
		searchConditions.setBlockId("B001");
		// block name
		searchConditions.setBlockName("B1");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");

		// Mock setup
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));
		bnn0009SearchBlockService.setAppContext(appContext);
		// get search result
		List<Bnn0009SearchBlockResult> bnn0009ResultList = bnn0009SearchBlockService.searchData(searchConditions);
		// start testing
		Assert.assertEquals(null, bnn0009ResultList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchData() throws Exception {
		createUserLogin();
		Bnn0009SearchBlockConditions searchConditions = new Bnn0009SearchBlockConditions();
		// farm id
		searchConditions.setFarmId("F001");
		// area id
		searchConditions.setAreaId("A001");
		// block id
		searchConditions.setBlockId("B000");
		// block name
		searchConditions.setBlockName("");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");
		
		List<Bnn0009SearchBlockResult> bnn0009ResultListReturn = new ArrayList<Bnn0009SearchBlockResult>();
		bnn0009ResultListReturn.add(new Bnn0009SearchBlockResult());

		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0009ResultListReturn);
		when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("1");
		// get search result
		bnn0009SearchBlockService.setAppContext(appContext);
		List<Bnn0009SearchBlockResult> bnn0009ResultList = bnn0009SearchBlockService.searchData(searchConditions);
		// start testing
		Assert.assertEquals(1, bnn0009ResultList.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataBlank() throws Exception {
		createUserLogin();
		Bnn0009SearchBlockConditions searchConditions = new Bnn0009SearchBlockConditions();
		// farm id
		searchConditions.setFarmId(Constants.SEARCH_CONDITION_NO_SELECT);
		// area id
		searchConditions.setAreaId(Constants.SEARCH_CONDITION_NO_SELECT);
		// block id
		searchConditions.setBlockId(Constants.SEARCH_CONDITION_NO_SELECT);
		// block name
		searchConditions.setBlockName("");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");

		List<Bnn0009SearchBlockResult> bnn0009ResultListReturn = new ArrayList<Bnn0009SearchBlockResult>();
		bnn0009ResultListReturn.add(new Bnn0009SearchBlockResult());
		bnn0009ResultListReturn.add(new Bnn0009SearchBlockResult());
		bnn0009ResultListReturn.add(new Bnn0009SearchBlockResult());
		bnn0009ResultListReturn.add(new Bnn0009SearchBlockResult());
		bnn0009ResultListReturn.add(new Bnn0009SearchBlockResult());

		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0009ResultListReturn);
		when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("5");
		// get search result
		bnn0009SearchBlockService.setAppContext(appContext);
		List<Bnn0009SearchBlockResult> bnn0009ResultList = bnn0009SearchBlockService.searchData(searchConditions);
		// start testing
		Assert.assertEquals(5, bnn0009ResultList.size());
	}

	@Test
	public void testGetBlockDataByAreaId() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String areaId = "A001";
		List<IvbMBlock> result = new ArrayList<IvbMBlock>();
		IvbMBlock temp = new IvbMBlock();
		result.add(temp);

		IvbMBlockMapper tmp = Mockito.mock(IvbMBlockMapper.class);
		when(dao.getIvbMBlockMapper()).thenReturn(tmp);
		when(tmp.selectByExample(any(IvbMBlockExample.class))).thenReturn(result);

		// get block data
		List<IvbMBlock> blockData = bnn0009SearchBlockService.getBlockDataByAreaId(areaId, "F008881");
		// start testing
		Assert.assertEquals(1, blockData.size());
	}

	@Test
	public void testGetBlockDataByAreaIdException() throws Exception {
		createUserLogin();
		String areaId = "A001";
		List<IvbMBlock> result = new ArrayList<IvbMBlock>();
		IvbMBlock temp = new IvbMBlock();
		result.add(temp);

		IvbMBlockMapper tmp = Mockito.mock(IvbMBlockMapper.class);
		when(dao.getIvbMBlockMapper()).thenReturn(tmp);
		when(tmp.selectByExample(any(IvbMBlockExample.class))).thenReturn(result);

		// get block data
		List<IvbMBlock> blockData = bnn0009SearchBlockService.getBlockDataByAreaId(areaId, "F008881");
		// start testing
		Assert.assertEquals(0, blockData.size());
	}

	@Test
	public void testGetAreaDataByFarmId()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();

		List<UtilComponent> farmData = new ArrayList<UtilComponent>();
		UtilComponent utilComponent = new UtilComponent();
		utilComponent.setFarmName("F001");
		utilComponent.setAreaId("A001");
		utilComponent.setAreaName("A001");
		farmData.add(utilComponent);
		when(util.getAreaDataByFarmId(utilDao, "F001")).thenReturn(farmData);
		// get block data
		List<UtilComponent> blockData = bnn0009SearchBlockService.getAreaDataByFarmId("F001");
		// start testing
		Assert.assertEquals(1, blockData.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetAreaDataByFarmIdException()  throws Exception {
		createUserLogin();
		List<UtilComponent> result = new ArrayList<UtilComponent>();
		UtilComponent temp = new UtilComponent();
		temp.setFarmId("F001");
		temp.setAreaId("A001");
		temp.setAreaName("A001");
		result.add(temp);

		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		when(tmp.selectAreaDataByFarmId(any(HashMap.class))).thenReturn(result);
		// get block data
		List<UtilComponent> blockData = bnn0009SearchBlockService.getAreaDataByFarmId("F001");
		// start testing
		Assert.assertEquals(0, blockData.size());
	}

	@Test
	public void testUpdateDataBlankFields()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("");
		// update block
		String result = bnn0009SearchBlockService.updateData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}

	@Test
	public void testUpdateDataBlankFields1() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId(Constants.SEARCH_CONDITION_NO_SELECT);
		// update block
		String result = bnn0009SearchBlockService.updateData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}

	@Test
	public void testUpdateDataBlankFields2() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("F001");
		// area id
		blockData.setAreaId("");
		// update block
		String result = bnn0009SearchBlockService.updateData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}

	@Test
	public void testUpdateDataBlankFields3() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("F001");
		// area id
		blockData.setAreaId(Constants.SEARCH_CONDITION_NO_SELECT);
		// update block
		String result = bnn0009SearchBlockService.updateData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}

	@Test
	public void testUpdateDataBlankFields4()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("F001");
		// area id
		blockData.setAreaId("A001");
		// block id
		blockData.setBlockName("");
		// update block
		String result = bnn0009SearchBlockService.updateData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}

	@Test
	public void testUpdateDataBlankFields5() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("F001");
		// area id
		blockData.setAreaId("A001");
		// block id
		blockData.setBlockId("B000");
		// block name
		blockData.setBlockName("");
		// update block
		String result = bnn0009SearchBlockService.updateData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataRollback() throws Exception {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("F001");
		// area id
		blockData.setAreaId("A001");
		// block id
		blockData.setBlockId("B000");
		// block name
		blockData.setBlockName("B3");
		// lastUpdateDate
		blockData.setLastUpdateDate(date);
		createUserLogin();
		IvbMBlock result = new IvbMBlock();
		result.setBlockId("0088");
		result.setLastUpdateDate(date);

		IvbMBlockMapper tmp1 = Mockito.mock(IvbMBlockMapper.class);
		when(dao.getIvbMBlockMapper()).thenReturn(tmp1);
		when(tmp1.selectByPrimaryKey(any(IvbMBlockKey.class))).thenReturn(result);
		// Mock setup
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(0);

		// update block
		bnn0009SearchBlockService.setAppContext(appContext);
		String result1 = bnn0009SearchBlockService.updateData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_CULTIVATION_BLOCK, result1);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataRollbackUpdateDate() throws Exception {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		java.util.Date dateLast = format.parse("2017/05/18 13:25:25");
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("F001");
		// area id
		blockData.setAreaId("A001");
		// block id
		blockData.setBlockId("B000");
		// block name
		blockData.setBlockName("B3");
		// lastUpdateDate
		blockData.setLastUpdateDate(date);
		createUserLogin();
		IvbMBlock result = new IvbMBlock();
		result.setBlockId("0088");
		result.setLastUpdateDate(dateLast);

		IvbMBlockMapper tmp1 = Mockito.mock(IvbMBlockMapper.class);
		when(dao.getIvbMBlockMapper()).thenReturn(tmp1);
		when(tmp1.selectByPrimaryKey(any(IvbMBlockKey.class))).thenReturn(result);
		// Mock setup
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(0);

		// update block
		bnn0009SearchBlockService.setAppContext(appContext);
		String result1 = bnn0009SearchBlockService.updateData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_UPDATE_DATE, result1);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataSuccess() throws Exception {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		java.util.Date dateLast = format.parse("2017/05/18 13:25:25");
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("F001");
		// area id
		blockData.setAreaId("A001");
		// block id
		blockData.setBlockId("B000");
		// block name
		blockData.setBlockName("B3");
		// lastUpdateDate
		blockData.setLastUpdateDate(date);
		createUserLogin();
		IvbMBlock result = new IvbMBlock();
		result.setBlockId("0088");
		result.setLastUpdateDate(dateLast);

		IvbMBlockMapper tmp1 = Mockito.mock(IvbMBlockMapper.class);
		when(dao.getIvbMBlockMapper()).thenReturn(tmp1);
		when(tmp1.selectByPrimaryKey(any(IvbMBlockKey.class))).thenReturn(result);
		// Mock setup
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);

		// update block
		bnn0009SearchBlockService.setAppContext(appContext);
		String result1 = bnn0009SearchBlockService.updateData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_SUCCESSFUL, result1);
	}
	@Test
	public void testUpdateDataInnerException() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("F001");
		// area id
		blockData.setAreaId("A001");
		// block id
		blockData.setBlockId("B000");
		// block name
		blockData.setBlockName("B3");

		// Mock setup
		when(dao.getIvbMBlockMapper()).thenReturn(null);

		// update block
		bnn0009SearchBlockService.setAppContext(appContext);
		String result = bnn0009SearchBlockService.updateData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}

	@Test
	public void testUpdateDataOuterException() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = null;
		// update block
		String result = bnn0009SearchBlockService.updateData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateDataDuplicateBlockName() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("F001");
		// area id
		blockData.setAreaId("A001");
		// block id
		blockData.setBlockId("B000");
		// block name
		blockData.setBlockName("B3");
		Bnn0009SearchBlockResult bnn0009SearchBlockResult = new Bnn0009SearchBlockResult();
		bnn0009SearchBlockResult.setBlockName("B3");
		bnn0009SearchBlockResult.setBlockId("B001");
		List<Bnn0009SearchBlockResult> listBlocklName = new ArrayList<Bnn0009SearchBlockResult>();
		listBlocklName.add(bnn0009SearchBlockResult);
		Bnn0009SearchBlockMapper tmpListName = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmpListName);
		when(tmpListName.searchBlockNameByFarmIdAndAreaId(any(HashMap.class))).thenReturn(listBlocklName);
        doThrow(new DuplicateKeyException("")).when(tmpListName).updateData(any(HashMap.class));

		bnn0009SearchBlockService.setAppContext(appContext);
		// update block
		String result = bnn0009SearchBlockService.updateData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_DUPLLICATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateData() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("F001");
		// area id
		blockData.setAreaId("A001");
		// block id
		blockData.setBlockId("B000");
		// block name
		blockData.setBlockName("B3");
		// delete flag
		blockData.setDeleteFlag(Constants.DELETE_FLAG_OFF);
		// note
		blockData.setNote("note");
		List<Bnn0009SearchBlockResult> listBlocklName = new ArrayList<Bnn0009SearchBlockResult>();
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.searchBlockNameByFarmIdAndAreaId(any(Map.class))).thenReturn(listBlocklName);

		IvbMBlockMapper ivbMBlockMapper = Mockito.mock(IvbMBlockMapper.class);
		when(dao.getIvbMBlockMapper()).thenReturn(ivbMBlockMapper);
		when(ivbMBlockMapper.updateByPrimaryKeySelective(any(IvbMBlock.class))).thenReturn(1);
		bnn0009SearchBlockService.setAppContext(appContext);
		// update block
		String result = bnn0009SearchBlockService.updateData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateDataFailed() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("F001");
		// area id
		blockData.setAreaId("A001");
		// block id
		blockData.setBlockId("B000");
		// block name
		blockData.setBlockName("B3");
		// delete flag
		blockData.setDeleteFlag(Constants.DELETE_FLAG_OFF);
		// note
		blockData.setNote("note");
		List<Bnn0009SearchBlockResult> listBlocklName = new ArrayList<Bnn0009SearchBlockResult>();
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		Bnn0009SearchBlockResult blockDataResult = new Bnn0009SearchBlockResult();
		// block id
		blockDataResult.setBlockId("B000");
		// block name
		blockDataResult.setBlockName("B3");
		listBlocklName.add(blockDataResult);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.searchBlockNameByFarmIdAndAreaId(any(Map.class))).thenReturn(listBlocklName);

		IvbMBlockMapper ivbMBlockMapper = Mockito.mock(IvbMBlockMapper.class);
		when(dao.getIvbMBlockMapper()).thenReturn(ivbMBlockMapper);
		when(ivbMBlockMapper.updateByPrimaryKeySelective(any(IvbMBlock.class))).thenReturn(1);
		bnn0009SearchBlockService.setAppContext(appContext);
		// update block
		String result = bnn0009SearchBlockService.updateData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateDataRollBack() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("F001");
		// area id
		blockData.setAreaId("A001");
		// block id
		blockData.setBlockId("B000");
		// block name
		blockData.setBlockName("B3");
		Bnn0009SearchBlockResult bnn0009SearchBlockResult = new Bnn0009SearchBlockResult();
		bnn0009SearchBlockResult.setBlockName("B1");
		List<Bnn0009SearchBlockResult> listBlocklName = new ArrayList<Bnn0009SearchBlockResult>();
		listBlocklName.add(bnn0009SearchBlockResult);
		Bnn0009SearchBlockMapper tmpListName = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmpListName);
		when(tmpListName.searchBlockNameByFarmIdAndAreaId(any(HashMap.class))).thenReturn(listBlocklName);

		IvbMBlockMapper tmp = Mockito.mock(IvbMBlockMapper.class);
		when(dao.getIvbMBlockMapper()).thenReturn(tmp);
		when(tmp.updateByPrimaryKeySelective(any(IvbMBlock.class))).thenReturn(0);
		bnn0009SearchBlockService.setAppContext(appContext);
		// update block
		String result = bnn0009SearchBlockService.updateData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_UPDATE_DATE, result);
	}

	@Test
	public void testInsertDataBlankFields() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("");

		// insert block
		String result = bnn0009SearchBlockService.insertData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}

	@Test
	@Transactional
	public void testInsertDataRollback()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("F001");
		// area id
		blockData.setAreaId("A00001");
		// block id
		blockData.setBlockId("B000");
		// block name
		blockData.setBlockName("B3");
		// delete flag
		blockData.setDeleteFlag(Constants.DELETE_FLAG_OFF);
		// note
		blockData.setNote("note");

		// insert block
		String result = bnn0009SearchBlockService.insertData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataDuplicateBlockName() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.getLastBlockId(any(HashMap.class))).thenReturn("B010");

		IvbMBlock blockData = new IvbMBlock();
		blockData.setFarmId("F001");
		blockData.setAreaId("A001");
		blockData.setBlockName("B87654");

		IvbMFarm farmData = new IvbMFarm();
		farmData.setFarmId("F001");
		farmData.setNumberOfLines(2);
		farmData.setNumberOfColumns(3);
		IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
		when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
		when(tmpFarm.selectByPrimaryKey("F001")).thenReturn(farmData);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", Constants.INSERT_RESULT_DUPLICATED);
				return null;
			}
		}).when(tmp).insertData(any(HashMap.class));

		bnn0009SearchBlockService.setAppContext(appContext);
		// update block
		String result = bnn0009SearchBlockService.insertData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataSuccessFull()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.getLastBlockId(any(HashMap.class))).thenReturn("B010");

		IvbMBlock blockData = new IvbMBlock();
		blockData.setFarmId("F001");
		blockData.setAreaId("A001");
		blockData.setBlockName("B87654");

		IvbMFarm farmData = new IvbMFarm();
		farmData.setFarmId("F001");
		farmData.setNumberOfLines(2);
		farmData.setNumberOfColumns(3);
		IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
		when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
		when(tmpFarm.selectByPrimaryKey("F001")).thenReturn(farmData);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", Constants.INSERT_RESULT_SUCCESSFUL);
				return null;
			}
		}).when(tmp).insertData(any(HashMap.class));

		bnn0009SearchBlockService.setAppContext(appContext);
		// update block
		String result = bnn0009SearchBlockService.insertData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataDUPLICATED_BLOCK()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.getLastBlockId(any(HashMap.class))).thenReturn("B010");

		IvbMBlock blockData = new IvbMBlock();
		blockData.setFarmId("F001");
		blockData.setAreaId("A001");
		blockData.setBlockName("B87654");

		IvbMFarm farmData = new IvbMFarm();
		farmData.setFarmId("F001");
		farmData.setNumberOfLines(2);
		farmData.setNumberOfColumns(3);
		IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
		when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
		when(tmpFarm.selectByPrimaryKey("F001")).thenReturn(farmData);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", Constants.INSERT_RESULT_DUPLICATED_BLOCK);
				return null;
			}
		}).when(tmp).insertData(any(HashMap.class));

		bnn0009SearchBlockService.setAppContext(appContext);
		// update block
		String result = bnn0009SearchBlockService.insertData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_DUPLICATED_BLOCK, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataSuccessNoneFull() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.getLastBlockId(any(HashMap.class))).thenReturn(null);

		IvbMBlock blockData = new IvbMBlock();
		blockData.setFarmId("F001");
		blockData.setAreaId("A001");
		blockData.setBlockName("B87654");

		List<Bnn0009SearchBlockResult> listBlocklName = new ArrayList<Bnn0009SearchBlockResult>();
		Bnn0009SearchBlockResult bnn0009SearchBlockResult = new Bnn0009SearchBlockResult();
		bnn0009SearchBlockResult.setBlockName("B1");
		listBlocklName.add(bnn0009SearchBlockResult);
		when(tmp.searchBlockNameByFarmIdAndAreaId(any(HashMap.class))).thenReturn(listBlocklName);

		IvbMFarm farmData = new IvbMFarm();
		farmData.setFarmId("F001");
		farmData.setNumberOfLines(2);
		farmData.setNumberOfColumns(3);
		IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
		when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
		when(tmpFarm.selectByPrimaryKey("F001")).thenReturn(farmData);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", "Error");
				return null;
			}
		}).when(tmp).insertData(any(HashMap.class));

		bnn0009SearchBlockService.setAppContext(appContext);
		// update block
		String result = bnn0009SearchBlockService.insertData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataFail() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.getLastBlockId(any(HashMap.class))).thenReturn("B010");

		IvbMBlock blockData = new IvbMBlock();
		blockData.setFarmId("F001");
		blockData.setAreaId("A001");
		blockData.setBlockName("B87654");

		IvbMFarm farmData = new IvbMFarm();
		farmData.setFarmId("F001");
		farmData.setNumberOfLines(2);
		farmData.setNumberOfColumns(3);
		IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
		when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
		when(tmpFarm.selectByPrimaryKey("F001")).thenReturn(farmData);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", Constants.INSERT_RESULT_FAILED);
				return null;
			}
		}).when(tmp).insertData(any(HashMap.class));

		bnn0009SearchBlockService.setAppContext(appContext);
		// update block
		String result = bnn0009SearchBlockService.insertData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_BLOCK, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataWithInsertResultNull() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.getLastBlockId(any(HashMap.class))).thenReturn("B010");

		IvbMBlock blockData = new IvbMBlock();
		blockData.setFarmId("F001");
		blockData.setAreaId("A001");
		blockData.setBlockName("B87654");

		IvbMFarm farmData = new IvbMFarm();
		farmData.setFarmId("F001");
		farmData.setNumberOfLines(2);
		farmData.setNumberOfColumns(3);
		IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
		when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
		when(tmpFarm.selectByPrimaryKey("F001")).thenReturn(farmData);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", null);
				return null;
			}
		}).when(tmp).insertData(any(HashMap.class));

		bnn0009SearchBlockService.setAppContext(appContext);
		// update block
		String result = bnn0009SearchBlockService.insertData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_BLOCK, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataWithInsertResultEmpty() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.getLastBlockId(any(HashMap.class))).thenReturn("B010");

		IvbMBlock blockData = new IvbMBlock();
		blockData.setFarmId("F001");
		blockData.setAreaId("A001");
		blockData.setBlockName("B87654");

		IvbMFarm farmData = new IvbMFarm();
		farmData.setFarmId("F001");
		farmData.setNumberOfLines(2);
		farmData.setNumberOfColumns(3);
		IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
		when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
		when(tmpFarm.selectByPrimaryKey("F001")).thenReturn(farmData);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", "");
				return null;
			}
		}).when(tmp).insertData(any(HashMap.class));

		bnn0009SearchBlockService.setAppContext(appContext);
		// update block
		String result = bnn0009SearchBlockService.insertData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_BLOCK, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataDuplicateName()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.getLastBlockId(any(HashMap.class))).thenReturn("B010");

		IvbMBlock blockData = new IvbMBlock();
		blockData.setFarmId("F001");
		blockData.setAreaId("A001");
		blockData.setBlockName("B87654");

		IvbMFarm farmData = new IvbMFarm();
		farmData.setFarmId("F001");
		farmData.setNumberOfLines(2);
		farmData.setNumberOfColumns(3);
		IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
		when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
		when(tmpFarm.selectByPrimaryKey("F001")).thenReturn(farmData);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", "");
				return null;
			}
		}).when(tmp).insertData(any(HashMap.class));

		bnn0009SearchBlockService.setAppContext(appContext);
		// update block
		String result = bnn0009SearchBlockService.insertData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_BLOCK, result);
	}

	@Test
	@Transactional
	public void testInsertDataInnerException()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("F001");
		// area id
		blockData.setAreaId("A001");
		// block id
		blockData.setBlockId("B003");
		// block name
		blockData.setBlockName("B3");
		// delete flag
		blockData.setDeleteFlag(Constants.DELETE_FLAG_OFF);
		// note
		blockData.setNote("note");

		// Mock setup
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(null);

		// insert block
		bnn0009SearchBlockService.setAppContext(appContext);
		String result = bnn0009SearchBlockService.insertData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}

	@Test
	@Transactional
	public void testInsertDataOuterException()  throws Exception {
		createUserLogin();
		IvbMBlock blockData = new IvbMBlock();
		// farm id
		blockData.setFarmId("F001");
		// area id
		blockData.setAreaId("A001");
		// block id
		blockData.setBlockId("B003");
		// block name
		blockData.setBlockName("B3");
		// delete flag
		blockData.setDeleteFlag(Constants.DELETE_FLAG_OFF);
		// note
		blockData.setNote("note");
		// Mock setup
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(null);
		// insert block
		String result = bnn0009SearchBlockService.insertData(blockData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataRollback()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String farmId = "F001";
		String areaId = "A000001";
		String blockId = "B000";
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", Constants.DELETE_RESULT_FAILED);
				return null;
			}
		}).when(tmp).deleteData(any(HashMap.class));
		bnn0009SearchBlockService.setAppContext(appContext);
		// delete block
		Date lastUpdateDate = null;
		String result = bnn0009SearchBlockService.deleteData(farmId, areaId, blockId, lastUpdateDate);
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_BLOCK, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataFailedUpdateDate()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String farmId = "F001";
		String areaId = "A000001";
		String blockId = "B000";
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", Constants.DELETE_RESULT_FAILED_UPDATE_DATE);
				return null;
			}
		}).when(tmp).deleteData(any(HashMap.class));
		bnn0009SearchBlockService.setAppContext(appContext);
		// delete block
		Date lastUpdateDate = null;
		String result = bnn0009SearchBlockService.deleteData(farmId, areaId, blockId, lastUpdateDate);
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataWidthDeleteResultNull() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String farmId = "F001";
		String areaId = "A000001";
		String blockId = "B000";
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", null);
				return null;
			}
		}).when(tmp).deleteData(any(HashMap.class));
		bnn0009SearchBlockService.setAppContext(appContext);
		// delete block
		Date lastUpdateDate = null;
		String result = bnn0009SearchBlockService.deleteData(farmId, areaId, blockId, lastUpdateDate);
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_BLOCK, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataWidthDeleteResultEmpty()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String farmId = "F001";
		String areaId = "A000001";
		String blockId = "B000";
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", "");
				return null;
			}
		}).when(tmp).deleteData(any(HashMap.class));
		bnn0009SearchBlockService.setAppContext(appContext);
		// delete block
		Date lastUpdateDate = null;
		String result = bnn0009SearchBlockService.deleteData(farmId, areaId, blockId, lastUpdateDate);
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_BLOCK, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteData() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String farmId = "F001";
		String areaId = "A000001";
		String blockId = "B000";
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", Constants.DELETE_RESULT_SUCCESSFUL);
				return null;
			}
		}).when(tmp).deleteData(any(HashMap.class));
		bnn0009SearchBlockService.setAppContext(appContext);
		// delete block
		Date lastUpdateDate = null;
		String result = bnn0009SearchBlockService.deleteData(farmId, areaId, blockId, lastUpdateDate);
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataResultAnyString()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String farmId = "F001";
		String areaId = "A000001";
		String blockId = "B000";
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", "Error");
				return null;
			}
		}).when(tmp).deleteData(any(HashMap.class));
		bnn0009SearchBlockService.setAppContext(appContext);
		// delete block
		Date lastUpdateDate = null;
		String result = bnn0009SearchBlockService.deleteData(farmId, areaId, blockId, lastUpdateDate);
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataOuterException()  throws Exception {
		createUserLogin();
		String farmId = "F001";
		String areaId = "A000001";
		String blockId = "B000";
		Bnn0009SearchBlockMapper tmp = Mockito.mock(Bnn0009SearchBlockMapper.class);
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(tmp);
		when(tmp.deleteData(any(HashMap.class))).thenReturn(null);
		// delete block
		Date lastUpdateDate = null;
		String result = bnn0009SearchBlockService.deleteData(farmId, areaId, blockId, lastUpdateDate);
		// so the result will be failure
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}

	@Test
	public void DeleteDataInnerException()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String farmId = null;
		String areaId = "A000001";
		String blockId = "B000";
		when(dao.getBnn0009SearchBlockMapper()).thenReturn(null);
		
		bnn0009SearchBlockService.setAppContext(appContext);
		// delete block
		Date lastUpdateDate = null;
		String result = bnn0009SearchBlockService.deleteData(farmId, areaId, blockId, lastUpdateDate);
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}

	@Test
	@Transactional
	public void testGetProductData() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String farmId = "F027";
		String areaId = "A000";
		String blockId = "B000";
		Boolean previousRound = false;

		List<IvbTProduct> result = new ArrayList<IvbTProduct>();
		IvbTProduct temp = new IvbTProduct();
		result.add(temp);

		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.selectByExample(any(IvbTProductExample.class))).thenReturn(result);

		List<IvbTProduct> list = bnn0009SearchBlockService.getProductData(farmId, areaId, blockId, previousRound);
		Assert.assertEquals(1, list.size());
	}

	@Test
	@Transactional
	public void testGetProductDataInnerException() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String farmId = "F027";
		String areaId = "A000";
		String blockId = "B000";
		Boolean previousRound = false;

		List<IvbTProduct> result = new ArrayList<IvbTProduct>();
		IvbTProduct temp = new IvbTProduct();
		result.add(temp);

		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(null);
		when(tmp.selectByExample(any(IvbTProductExample.class))).thenReturn(result);

		List<IvbTProduct> list = bnn0009SearchBlockService.getProductData(farmId, areaId, blockId, previousRound);
		Assert.assertEquals(null, list);
	}

	@Test
	@Transactional
	public void testGetProductDataOuterException() throws Exception {
		createUserLogin();
		String farmId = "F027";
		String areaId = "A000";
		String blockId = "B000";
		Boolean previousRound = false;

		List<IvbTProduct> list = bnn0009SearchBlockService.getProductData(farmId, areaId, blockId, previousRound);
		Assert.assertEquals(null, list);
	}

	@Test
	@Transactional
	public void testgetSingleData()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String farmId = "F027";
		String areaId = "A000";
		String blockId = "B000";
		IvbMBlock result = new IvbMBlock();
		result.setBlockId("0088");

		IvbMBlockMapper tmp = Mockito.mock(IvbMBlockMapper.class);
		when(dao.getIvbMBlockMapper()).thenReturn(tmp);
		when(tmp.selectByPrimaryKey(any(IvbMBlockKey.class))).thenReturn(result);

		IvbMBlock list = bnn0009SearchBlockService.getSingleData(farmId, areaId, blockId);
		Assert.assertEquals("0088", list.getBlockId());
	}

	@Test
	@Transactional
	public void testgetSingleDataException()  throws Exception {
		createUserLogin();
		String farmId = "F027";
		String areaId = "A000";
		String blockId = "B000";
		IvbMBlock result = new IvbMBlock();
		result.setBlockId("0088");

		IvbMBlockMapper tmp = Mockito.mock(IvbMBlockMapper.class);
		when(dao.getIvbMBlockMapper()).thenReturn(tmp);
		when(tmp.selectByPrimaryKey(any(IvbMBlockKey.class))).thenReturn(result);

		IvbMBlock list = bnn0009SearchBlockService.getSingleData(farmId, areaId, blockId);
		Assert.assertEquals(null, list);
	}

	@Test
	public void testUpdateProductData() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String idString = "F027:A000:B000:L000:C000:true";
		String previousRound = "true";

		Bnn0009SearchBlockProductAdjust bnn0009SearchBlockProductAdjust = new Bnn0009SearchBlockProductAdjust();
		bnn0009SearchBlockProductAdjust.setIdString(idString);
		bnn0009SearchBlockProductAdjust.setPreviousRound(previousRound);

		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.updateByPrimaryKeySelective(any(IvbTProduct.class))).thenReturn(1);

		bnn0009SearchBlockService.setAppContext(appContext);
		String result = bnn0009SearchBlockService.updateProductData(bnn0009SearchBlockProductAdjust);
		Assert.assertEquals(Constants.UPDATE_RESULT_SUCCESSFUL, result);
	}

	@Test
	public void testUpdateProductDataRollBack()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String idString = "F027:A000:B000:L000:C000:true";
		String previousRound = "true";

		Bnn0009SearchBlockProductAdjust bnn0009SearchBlockProductAdjust = new Bnn0009SearchBlockProductAdjust();
		bnn0009SearchBlockProductAdjust.setIdString(idString);
		bnn0009SearchBlockProductAdjust.setPreviousRound(previousRound);

		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.updateByPrimaryKeySelective(any(IvbTProduct.class))).thenReturn(0);

		bnn0009SearchBlockService.setAppContext(appContext);
		String result = bnn0009SearchBlockService.updateProductData(bnn0009SearchBlockProductAdjust);
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_DUPLLICATE, result);
	}

	@Test
	@Transactional
	public void testUpdateProductDataException() throws Exception {
		createUserLogin();
		String idString = "F027:A000:B000:L000:C000:true";
		String previousRound = "false";

		Bnn0009SearchBlockProductAdjust bnn0009SearchBlockProductAdjust = new Bnn0009SearchBlockProductAdjust();
		bnn0009SearchBlockProductAdjust.setIdString(idString);
		bnn0009SearchBlockProductAdjust.setPreviousRound(previousRound);
		String result = bnn0009SearchBlockService.updateProductData(bnn0009SearchBlockProductAdjust);
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}

	@Test
	@Transactional
	public void testUpdateProductDataException1() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String idString = "F027:A000:B000:C000:C000:true";
		String previousRound = "false";

		Bnn0009SearchBlockProductAdjust bnn0009SearchBlockProductAdjust = new Bnn0009SearchBlockProductAdjust();
		bnn0009SearchBlockProductAdjust.setIdString(idString);
		bnn0009SearchBlockProductAdjust.setPreviousRound(previousRound);

		IvbTProduct bananaObj = new IvbTProduct();
		// farm id
		bananaObj.setFarmId("F027");
		// area id
		bananaObj.setAreaId("A000");
		// block id
		bananaObj.setBlockId("B000");
		// line id
		bananaObj.setLineId("L000");
		// column id
		bananaObj.setColumnId("C001");
		// delete flag
		Boolean deleteFlag = Boolean.valueOf("true");
		bananaObj.setDeleteFlag(deleteFlag);
		// previous round
		bananaObj.setPreviousRound(Boolean.valueOf(false));
		// update user id
		bananaObj.setUpdateUserId((new Util()).getUserInfo().getID());
		// Mock setup
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).updateByPrimaryKeySelective(bananaObj);

		String result = bnn0009SearchBlockService.updateProductData(bnn0009SearchBlockProductAdjust);
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_DUPLLICATE, result);
	}

	@Test
	@Transactional
	public void testUpdateProductDataException2() throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String idString = "F027:A000:B000ssd:C000:C000:true";
		String previousRound = "false";

		Bnn0009SearchBlockProductAdjust bnn0009SearchBlockProductAdjust = new Bnn0009SearchBlockProductAdjust();
		bnn0009SearchBlockProductAdjust.setIdString(idString);
		bnn0009SearchBlockProductAdjust.setPreviousRound(previousRound);

		bnn0009SearchBlockService.setAppContext(appContext);
		// Mock setup
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).updateByPrimaryKeySelective(any(IvbTProduct.class));

		String result = bnn0009SearchBlockService.updateProductData(bnn0009SearchBlockProductAdjust);
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}

	@Test
	@Transactional
	public void testUpdateProductDataException3()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String idString = "F027:A000:B000:C000:C000:true";
		String previousRound = "false";

		Bnn0009SearchBlockProductAdjust bnn0009SearchBlockProductAdjust = new Bnn0009SearchBlockProductAdjust();
		bnn0009SearchBlockProductAdjust.setIdString(idString);
		bnn0009SearchBlockProductAdjust.setPreviousRound(previousRound);

		bnn0009SearchBlockService.setAppContext(appContext);
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).updateByPrimaryKeySelective(any(IvbTProduct.class));

		String result = bnn0009SearchBlockService.updateProductData(bnn0009SearchBlockProductAdjust);
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}

	@Test
	@Transactional
	public void testUpdateProductBlankField()  throws Exception {
		bnn0009SearchBlockService.setAppContext(appContext);
		createUserLogin();
		String idString = "";
		String previousRound = "false";

		Bnn0009SearchBlockProductAdjust bnn0009SearchBlockProductAdjust = new Bnn0009SearchBlockProductAdjust();
		bnn0009SearchBlockProductAdjust.setIdString(idString);
		bnn0009SearchBlockProductAdjust.setPreviousRound(previousRound);
		String result = bnn0009SearchBlockService.updateProductData(bnn0009SearchBlockProductAdjust);
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}

}
