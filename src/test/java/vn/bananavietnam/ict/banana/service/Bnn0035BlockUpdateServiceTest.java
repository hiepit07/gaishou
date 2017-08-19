package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.bananavietnam.ict.banana.component.Bnn0035AreaExtendObject;
import vn.bananavietnam.ict.banana.component.BnnBlockCultivationResult;
import vn.bananavietnam.ict.banana.dao.Bnn0035BlockUpdateDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0035BlockUpdateMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMBlockMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMStatusMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTCultivationResultMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTProductMapper;
import vn.bananavietnam.ict.common.db.mapper.UtilMapper;
import vn.bananavietnam.ict.common.db.model.IvbMArea;
import vn.bananavietnam.ict.common.db.model.IvbMAreaKey;
import vn.bananavietnam.ict.common.db.model.IvbMBlock;
import vn.bananavietnam.ict.common.db.model.IvbMBlockExample;
import vn.bananavietnam.ict.common.db.model.IvbMKind;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.db.model.IvbMStatus;
import vn.bananavietnam.ict.common.db.model.IvbMStatusExample;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.db.model.IvbTCultivationResult;
import vn.bananavietnam.ict.common.db.model.IvbTCultivationResultKey;
import vn.bananavietnam.ict.common.db.model.IvbTProduct;
import vn.bananavietnam.ict.common.db.model.IvbTProductExample;
import vn.bananavietnam.ict.common.db.model.IvbTProductKey;
import vn.bananavietnam.ict.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0035BlockUpdateServiceTest {

	@Autowired
    private ApplicationContext appContext;

	@Autowired
	private Bnn0035BlockUpdateService bnn0035BlockUpdateService;

	@InjectMocks
	private Bnn0035BlockUpdateService bnn0035BlockUpdateService_Mock;

	@Mock
	ObjectMapper mapper = new ObjectMapper();
	PlatformTransactionManager txManager;

	@Mock
	Bnn0035BlockUpdateDao dao;

	@Mock
	UtilDao utilDao;

	@Mock
	Util util;

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
        when(util.getUserInfo()).thenReturn(u);
	}

	@Test
	@Transactional
	public void testInitDataBlankParameter() {
		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).selectFarmDataMaster();

		// Mock setup
		Model model = Mockito.mock(Model.class);

		// start testing
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		bnn0035BlockUpdateService_Mock.initData(model, "", "1", "1", "1");
	}
	@Test
	@Transactional
	public void testInitDataBlankParameter1() throws SQLException {
		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).selectFarmDataMaster();

		// Mock setup
		Model model = Mockito.mock(Model.class);

		// start testing
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		bnn0035BlockUpdateService_Mock.initData(model, "1", "", "1", "1");
	}
	@Test
	@Transactional
	public void testInitDataBlankParameter2() throws SQLException {
		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).selectFarmDataMaster();

		// Mock setup
		Model model = Mockito.mock(Model.class);

		// start testing
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		bnn0035BlockUpdateService_Mock.initData(model, "1", "1", "", "1");
	}
	@Test
	@Transactional
	public void testInitDataBlankParameter3() {
		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).selectFarmDataMaster();

		// Mock setup
		Model model = Mockito.mock(Model.class);

		// start testing
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		bnn0035BlockUpdateService_Mock.initData(model, "1", "1", "1", "");
	}
	@Test
	@Transactional
	public void testInitDataBlankParameter4() {
		// Mock setup
		when(dao.getIvbMAreaMapper()).thenReturn(null);

		// Mock setup
		Model model = Mockito.mock(Model.class);

		// start testing
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		bnn0035BlockUpdateService_Mock.initData(model, "1", "1", "1", "1");
	}
	@Test
	@Transactional
	public void testInitDataOuterException() {
		// Mock setup
		Model model = Mockito.mock(Model.class);

		// start testing
        bnn0035BlockUpdateService_Mock.initData(model, "1", "1", "1", "1");
	}
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInitDataSuccessRoleId1() {
		// Mock setup
		List<UtilComponent> farmResult = new ArrayList<UtilComponent>();
		UtilComponent utilData = new UtilComponent();
		utilData.setFarmId("1");
		farmResult.add(utilData);
		when(util.getFarmData(any(UtilDao.class))).thenReturn(farmResult);

		// Mock setup
		List<UtilComponent> areaResult = new ArrayList<UtilComponent>();
		utilData = new UtilComponent();
		utilData.setAreaId("1");
		areaResult.add(utilData);
		when(util.getAreaDataByFarmId(any(UtilDao.class), any(String.class))).thenReturn(areaResult);

		// Mock setup
		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
		when(dao.getIvbMAreaMapper()).thenReturn(tmp);
		when(tmp.selectByPrimaryKey(any(IvbMAreaKey.class))).thenReturn(new IvbMArea());

		// Mock setup
		IvbMKindMapper tmp1 = Mockito.mock(IvbMKindMapper.class);
		when(dao.getIvbMKindMapper()).thenReturn(tmp1);
		when(tmp1.selectByPrimaryKey(any(String.class))).thenReturn(new IvbMKind());

		// Mock setup
		List<IvbMProcess> processResult = new ArrayList<IvbMProcess>();
		processResult.add(new IvbMProcess());
		Bnn0035BlockUpdateMapper tmp2 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp2);
		when(tmp2.getProcessData(any(HashMap.class))).thenReturn(processResult);

		// Mock setup
		List<IvbMStatus> statusResult = new ArrayList<IvbMStatus>();
		statusResult.add(new IvbMStatus());
		IvbMStatusMapper tmp3 = Mockito.mock(IvbMStatusMapper.class);
		when(dao.getIvbMStatusMapper()).thenReturn(tmp3);
		when(tmp3.selectByExample(any(IvbMStatusExample.class))).thenReturn(statusResult);

		// Mock setup
		List<Bnn0035AreaExtendObject> areaItemsArrayResult = new ArrayList<Bnn0035AreaExtendObject>();
		areaItemsArrayResult.add(new Bnn0035AreaExtendObject());
		Bnn0035BlockUpdateMapper tmp4 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp4);
		when(tmp4.getAreaAndKindDataFromMaster(any(HashMap.class))).thenReturn(areaItemsArrayResult);

		// Mock setup
		Model model = Mockito.mock(Model.class);

		// start testing
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		bnn0035BlockUpdateService_Mock.initData(model, "", "", "", "");
	}
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInitDataSuccessRoleId2() throws SQLException {
		// Mock setup
		List<UtilComponent> farmResult = new ArrayList<UtilComponent>();
		UtilComponent utilData = new UtilComponent();
		utilData.setFarmId("1");
		farmResult.add(utilData);
		when(util.getFarmData(any(UtilDao.class))).thenReturn(farmResult);

		// Mock setup
		List<UtilComponent> areaResult = new ArrayList<UtilComponent>();
		utilData = new UtilComponent();
		utilData.setAreaId("1");
		areaResult.add(utilData);
		when(util.getAreaDataByFarmId(any(UtilDao.class), any(String.class))).thenReturn(areaResult);

		// Mock setup
		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
		when(dao.getIvbMAreaMapper()).thenReturn(tmp);
		when(tmp.selectByPrimaryKey(any(IvbMAreaKey.class))).thenReturn(new IvbMArea());

		// Mock setup
		IvbMKindMapper tmp1 = Mockito.mock(IvbMKindMapper.class);
		when(dao.getIvbMKindMapper()).thenReturn(tmp1);
		when(tmp1.selectByPrimaryKey(any(String.class))).thenReturn(new IvbMKind());

		// Mock setup
		List<IvbMProcess> processResult = new ArrayList<IvbMProcess>();
		processResult.add(new IvbMProcess());
		Bnn0035BlockUpdateMapper tmp2 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp2);
		when(tmp2.getProcessData(any(HashMap.class))).thenReturn(processResult);

		// Mock setup
		List<IvbMStatus> statusResult = new ArrayList<IvbMStatus>();
		statusResult.add(new IvbMStatus());
		IvbMStatusMapper tmp3 = Mockito.mock(IvbMStatusMapper.class);
		when(dao.getIvbMStatusMapper()).thenReturn(tmp3);
		when(tmp3.selectByExample(any(IvbMStatusExample.class))).thenReturn(statusResult);

		// Mock setup
		List<Bnn0035AreaExtendObject> areaItemsArrayResult = new ArrayList<Bnn0035AreaExtendObject>();
		areaItemsArrayResult.add(new Bnn0035AreaExtendObject());
		Bnn0035BlockUpdateMapper tmp4 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp4);
		when(tmp4.getAreaAndKindDataFromMaster(any(HashMap.class))).thenReturn(areaItemsArrayResult);

		// Mock setup
		Model model = Mockito.mock(Model.class);

		// Mock setup
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0035");
		ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSetMock.getString("id")).thenReturn("A0007");
        Mockito.when(resultSetMock.getString("username")).thenReturn("A0007");
        Mockito.when(resultSetMock.getString("userfullname")).thenReturn("A0007");
        Mockito.when(resultSetMock.getString("screenid")).thenReturn("0035");
        Mockito.when(resultSetMock.getString("roleid")).thenReturn("2");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, resultSetMock);
        Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        when(util.getUserInfo()).thenReturn(u);

		// start testing
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		bnn0035BlockUpdateService_Mock.initData(model, "", "", "", "");
	}
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInitDataSuccessRoleId3() throws SQLException {
		// Mock setup
		List<UtilComponent> farmResult = new ArrayList<UtilComponent>();
		UtilComponent utilData = new UtilComponent();
		utilData.setFarmId("1");
		farmResult.add(utilData);
		when(util.getFarmData(any(UtilDao.class))).thenReturn(farmResult);

		// Mock setup
		List<UtilComponent> areaResult = new ArrayList<UtilComponent>();
		utilData = new UtilComponent();
		utilData.setAreaId("1");
		areaResult.add(utilData);
		when(util.getAreaDataByFarmId(any(UtilDao.class), any(String.class))).thenReturn(areaResult);

		// Mock setup
		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
		when(dao.getIvbMAreaMapper()).thenReturn(tmp);
		when(tmp.selectByPrimaryKey(any(IvbMAreaKey.class))).thenReturn(new IvbMArea());

		// Mock setup
		IvbMKindMapper tmp1 = Mockito.mock(IvbMKindMapper.class);
		when(dao.getIvbMKindMapper()).thenReturn(tmp1);
		when(tmp1.selectByPrimaryKey(any(String.class))).thenReturn(new IvbMKind());

		// Mock setup
		List<IvbMProcess> processResult = new ArrayList<IvbMProcess>();
		processResult.add(new IvbMProcess());
		Bnn0035BlockUpdateMapper tmp2 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp2);
		when(tmp2.getProcessData(any(HashMap.class))).thenReturn(processResult);

		// Mock setup
		List<IvbMStatus> statusResult = new ArrayList<IvbMStatus>();
		statusResult.add(new IvbMStatus());
		IvbMStatusMapper tmp3 = Mockito.mock(IvbMStatusMapper.class);
		when(dao.getIvbMStatusMapper()).thenReturn(tmp3);
		when(tmp3.selectByExample(any(IvbMStatusExample.class))).thenReturn(statusResult);

		// Mock setup
		List<Bnn0035AreaExtendObject> areaItemsArrayResult = new ArrayList<Bnn0035AreaExtendObject>();
		areaItemsArrayResult.add(new Bnn0035AreaExtendObject());
		Bnn0035BlockUpdateMapper tmp4 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp4);
		when(tmp4.getAreaAndKindDataFromMaster(any(HashMap.class))).thenReturn(areaItemsArrayResult);

		// Mock setup
		Model model = Mockito.mock(Model.class);

		// Mock setup
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0035");
		ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSetMock.getString("id")).thenReturn("A0007");
        Mockito.when(resultSetMock.getString("username")).thenReturn("A0007");
        Mockito.when(resultSetMock.getString("userfullname")).thenReturn("A0007");
        Mockito.when(resultSetMock.getString("screenid")).thenReturn("0035");
        Mockito.when(resultSetMock.getString("roleid")).thenReturn("3");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, resultSetMock);
        Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        when(util.getUserInfo()).thenReturn(u);

		// start testing
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		bnn0035BlockUpdateService_Mock.initData(model, "", "", "", "");
	}
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInitDataSuccessRoleId4() throws SQLException {
		// Mock setup
		List<UtilComponent> farmResult = new ArrayList<UtilComponent>();
		UtilComponent utilData = new UtilComponent();
		utilData.setFarmId("1");
		farmResult.add(utilData);
		when(util.getFarmData(any(UtilDao.class))).thenReturn(farmResult);

		// Mock setup
		List<UtilComponent> areaResult = new ArrayList<UtilComponent>();
		utilData = new UtilComponent();
		utilData.setAreaId("1");
		areaResult.add(utilData);
		when(util.getAreaDataByFarmId(any(UtilDao.class), any(String.class))).thenReturn(areaResult);

		// Mock setup
		IvbMAreaMapper tmp = Mockito.mock(IvbMAreaMapper.class);
		when(dao.getIvbMAreaMapper()).thenReturn(tmp);
		when(tmp.selectByPrimaryKey(any(IvbMAreaKey.class))).thenReturn(new IvbMArea());

		// Mock setup
		IvbMKindMapper tmp1 = Mockito.mock(IvbMKindMapper.class);
		when(dao.getIvbMKindMapper()).thenReturn(tmp1);
		when(tmp1.selectByPrimaryKey(any(String.class))).thenReturn(new IvbMKind());

		// Mock setup
		List<IvbMProcess> processResult = new ArrayList<IvbMProcess>();
		processResult.add(new IvbMProcess());
		Bnn0035BlockUpdateMapper tmp2 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp2);
		when(tmp2.getProcessData(any(HashMap.class))).thenReturn(processResult);

		// Mock setup
		List<IvbMStatus> statusResult = new ArrayList<IvbMStatus>();
		statusResult.add(new IvbMStatus());
		IvbMStatusMapper tmp3 = Mockito.mock(IvbMStatusMapper.class);
		when(dao.getIvbMStatusMapper()).thenReturn(tmp3);
		when(tmp3.selectByExample(any(IvbMStatusExample.class))).thenReturn(statusResult);

		// Mock setup
		List<Bnn0035AreaExtendObject> areaItemsArrayResult = new ArrayList<Bnn0035AreaExtendObject>();
		areaItemsArrayResult.add(new Bnn0035AreaExtendObject());
		Bnn0035BlockUpdateMapper tmp4 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp4);
		when(tmp4.getAreaAndKindDataFromMaster(any(HashMap.class))).thenReturn(areaItemsArrayResult);

		// Mock setup
		Model model = Mockito.mock(Model.class);

		// Mock setup
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0035");
		ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSetMock.getString("id")).thenReturn("A0007");
        Mockito.when(resultSetMock.getString("username")).thenReturn("A0007");
        Mockito.when(resultSetMock.getString("userfullname")).thenReturn("A0007");
        Mockito.when(resultSetMock.getString("screenid")).thenReturn("0035");
        Mockito.when(resultSetMock.getString("roleid")).thenReturn("4");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, resultSetMock);
        Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        when(util.getUserInfo()).thenReturn(u);

		// start testing
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		bnn0035BlockUpdateService_Mock.initData(model, "", "", "", "");
	}

	@Test
	@Transactional
	public void testGetBlockDataInnerException() {
		// Mock setup
		when(dao.getIvbMBlockMapper()).thenReturn(null);

		// get data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		List<IvbMBlock> blockData = bnn0035BlockUpdateService_Mock.getBlockData("", "");
		// start testing
		Assert.assertEquals(0, blockData.size());
	}
	@Test
	@Transactional
	public void testGetBlockDataOuterException() {
		// get data
		List<IvbMBlock> blockData = bnn0035BlockUpdateService_Mock.getBlockData("", "");
		// start testing
		Assert.assertEquals(0, blockData.size());
	}
	@Test
	@Transactional
	public void testGetBlockDataSuccess() {
		// Mock setup
		ArrayList<IvbMBlock> blockResult = new ArrayList<IvbMBlock>();
		blockResult.add(new IvbMBlock());
		IvbMBlockMapper tmp = Mockito.mock(IvbMBlockMapper.class);
		when(dao.getIvbMBlockMapper()).thenReturn(tmp);
		when(tmp.selectByExample(any(IvbMBlockExample.class))).thenReturn(blockResult);

		// get data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		List<IvbMBlock> blockData = bnn0035BlockUpdateService_Mock.getBlockData("", "");
		// start testing
		Assert.assertEquals(1, blockData.size());
	}

	@Test
	@Transactional
	public void testGetCultivationResultDataInnerException() {
		// Mock setup
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(null);

		// get data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		List<BnnBlockCultivationResult> cultivationResultList = bnn0035BlockUpdateService_Mock.getCultivationResultData("", "", "");
		// start testing
		Assert.assertEquals(0, cultivationResultList.size());
	}
	@Test
	@Transactional
	public void testGetCultivationResultDataOuterException() {
		// get data
		List<BnnBlockCultivationResult> cultivationResultList = bnn0035BlockUpdateService_Mock.getCultivationResultData("", "", "");
		// start testing
		Assert.assertEquals(0, cultivationResultList.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testGetCultivationResultDataSuccess() {
		// Mock setup
		ArrayList<BnnBlockCultivationResult> cultivationResult = new ArrayList<BnnBlockCultivationResult>();
		cultivationResult.add(new BnnBlockCultivationResult());
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getCultivationResultData(any(HashMap.class))).thenReturn(cultivationResult);

		// get data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		List<BnnBlockCultivationResult> cultivationResultList = bnn0035BlockUpdateService_Mock.getCultivationResultData("", "", "");
		// start testing
		Assert.assertEquals(1, cultivationResultList.size());
	}

	@Test
	@Transactional
	public void testGetProcessDataInnerException() {
		// Mock setup
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(null);

		// get data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		List<IvbMProcess> processResultList = bnn0035BlockUpdateService_Mock.getProcessData("", "");
		// start testing
		Assert.assertEquals(0, processResultList.size());
	}
	@Test
	@Transactional
	public void testGetProcessDataOuterException() {
		// get data
		List<IvbMProcess> processResultList = bnn0035BlockUpdateService_Mock.getProcessData("", "");
		// start testing
		Assert.assertEquals(0, processResultList.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testGetProcessDataSuccess() {
		// Mock setup
		ArrayList<IvbMProcess> processResult = new ArrayList<IvbMProcess>();
		processResult.add(new IvbMProcess());
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProcessData(any(HashMap.class))).thenReturn(processResult);

		// get data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		List<IvbMProcess> processResultList = bnn0035BlockUpdateService_Mock.getProcessData("", "");
		// start testing
		Assert.assertEquals(1, processResultList.size());
	}

	@Test
	@Transactional
	public void testGetTaskDataInnerException() {
		// Mock setup
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(null);

		// get data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		List<IvbMTask> taskResultList = bnn0035BlockUpdateService_Mock.getTaskData("", "", "");
		// start testing
		Assert.assertEquals(null, taskResultList);
	}
	@Test
	@Transactional
	public void testGetTaskDataOuterException() {
		// get data
		List<IvbMTask> taskResultList = bnn0035BlockUpdateService_Mock.getTaskData("", "", "");
		// start testing
		Assert.assertEquals(null, taskResultList);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testGetTaskData() {
		// Mock setup
		ArrayList<IvbMTask> taskResult = new ArrayList<IvbMTask>();
		taskResult.add(new IvbMTask());
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getTaskData(any(HashMap.class))).thenReturn(taskResult);

		// get data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		List<IvbMTask> taskResultList = bnn0035BlockUpdateService_Mock.getTaskData("", "", "");
		// start testing
		Assert.assertEquals(1, taskResultList.size());
	}

	@Test
	@Transactional
	public void testInsertDataBlankFields() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Working date
		cultivationResultData.setWorkingDate(null);

		// insert data
		String result = bnn0035BlockUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}
	@Test
	@Transactional
	public void testInsertDataBlankFields1() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId(null);

		// insert data
		String result = bnn0035BlockUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}
	@Test
	@Transactional
	public void testInsertDataBlankFields2() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId(null);

		// insert data
		String result = bnn0035BlockUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}
	@Test
	@Transactional
	public void testInsertDataBlankFields3() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId(null);

		// insert data
		String result = bnn0035BlockUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}
	@Test
	@Transactional
	public void testInsertDataChangePointCodeNONE() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);

		// insert data
		String result = bnn0035BlockUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataChangePointCodeCULTIVATIONSTART() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// list of IvbTProductKey
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductData(any(HashMap.class))).thenReturn(productList);
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_CULTIVATION_START);

		// insert data
		String result = bnn0035BlockUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_BLOCK_DELETE, result);
	}
	@Test
	@Transactional
	public void testInsertDataChangePointCodePLANTING() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_PLANTING);

		// insert data
		String result = bnn0035BlockUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_BLOCK_DELETE, result);
	}
	@Test
	@Transactional
	public void testInsertDataChangePointCodeFLOWERING() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_FLOWERING);

		// insert data
		String result = bnn0035BlockUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_BLOCK_DELETE, result);
	}
	@Test
	@Transactional
	public void testInsertDataChangePointCodeBAGGED() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_BAGGED);

		// insert data
		String result = bnn0035BlockUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_BLOCK_DELETE, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataChangePointCodeHARVESTED() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// change point code
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp1 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp1);
		when(tmp1.getProductData(any(HashMap.class))).thenReturn(productList);
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);

		// insert data
		String result = bnn0035BlockUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_BLOCK_DELETE, result);
	}
	@Test
	@Transactional
	public void testInsertDataChangePointCodeWRONG() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.DEFAULT_LINE_ID);

		// insert data
		String result = bnn0035BlockUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_BLOCK_DELETE, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataProductUpdateFailed() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Farm id
		cultivationResultData.setFarmId("");
		// Area id
		cultivationResultData.setAreaId("");
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// list of IvbTProductKey

		// change point code		
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp1 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp1);
		when(tmp1.getProductData(any(HashMap.class))).thenReturn(productList);
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_PLANTING);

		// Mock setup
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(0);

		// insert data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataProduct1() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Farm id
		cultivationResultData.setFarmId("");
		// Area id
		cultivationResultData.setAreaId("");
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// list of IvbTProductKey

		// change point code		
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp1 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp1);
		when(tmp1.getProductData(any(HashMap.class))).thenReturn(productList);
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_CULTIVATION_START);

		// Mock setup
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(0);

		// insert data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataProduct2() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Farm id
		cultivationResultData.setFarmId("");
		// Area id
		cultivationResultData.setAreaId("");
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// list of IvbTProductKey

		// change point code		
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp1 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp1);
		when(tmp1.getProductData(any(HashMap.class))).thenReturn(productList);
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_FLOWERING);

		// Mock setup
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(0);

		// insert data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataProduct3() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Farm id
		cultivationResultData.setFarmId("");
		// Area id
		cultivationResultData.setAreaId("");
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// list of IvbTProductKey

		// change point code		
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp1 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp1);
		when(tmp1.getProductData(any(HashMap.class))).thenReturn(productList);
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_BAGGED);

		// Mock setup
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(0);

		// insert data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataProduct8() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Farm id
		cultivationResultData.setFarmId("");
		// Area id
		cultivationResultData.setAreaId("");
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// list of IvbTProductKey

		// change point code		
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp1 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp1);
		when(tmp1.getProductData(any(HashMap.class))).thenReturn(productList);
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_BAGGED);

		// Mock setup
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(0);

		// insert data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataProduct4() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Farm id
		cultivationResultData.setFarmId("");
		// Area id
		cultivationResultData.setAreaId("");
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// lastUpdate
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/17 13:25:25"));
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// list of IvbTProductKey

		// change point code		
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp1 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp1);

		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		// set getLastUpdateDate 
		when(tmp1.updateProductDate(any(HashMap.class))).thenReturn(0);
		when(tmp1.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		when(tmp1.getProductData(any(HashMap.class))).thenReturn(productList);
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);

		// Mock setup
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(0);

		// insert data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT_UPDATE_DATE, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataProduct9() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Farm id
		cultivationResultData.setFarmId("");
		// Area id
		cultivationResultData.setAreaId("");
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// lastUpdate
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/18 13:25:25"));
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// list of IvbTProductKey

		// change point code		
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp1 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp1);

		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		// set getLastUpdateDate 
		when(tmp1.updateProductDate(any(HashMap.class))).thenReturn(0);
		when(tmp1.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		when(tmp1.getProductData(any(HashMap.class))).thenReturn(productList);
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);

		// Mock setup
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(0);

		// insert data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataProduct10() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Farm id
		cultivationResultData.setFarmId("");
		// Area id
		cultivationResultData.setAreaId("");
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// lastUpdate
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/18 13:25:25"));
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// list of IvbTProductKey

		// change point code		
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp1 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp1);

		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		// set getLastUpdateDate 
		when(tmp1.updateProductDate(any(HashMap.class))).thenReturn(0);
		when(tmp1.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		when(tmp1.getProductData(any(HashMap.class))).thenReturn(productList);
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);
		// Mock setup
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(0);

		// insert data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataProduct7() throws ParseException {
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Farm id
		cultivationResultData.setFarmId("");
		// Area id
		cultivationResultData.setAreaId("");
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// lastUpdate
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/18 13:25:25"));
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// list of IvbTProductKey

		// change point code		
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp1 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp1);

		IvbTProduct ivbTProduct = new IvbTProduct();
		// set getLastUpdateDate 
		when(tmp1.updateProductDate(any(HashMap.class))).thenReturn(0);
		when(tmp1.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		when(tmp1.getProductData(any(HashMap.class))).thenReturn(productList);
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);

		// Mock setup
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(0);

		// insert data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataProduct6() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Farm id
		cultivationResultData.setFarmId("");
		// Area id
		cultivationResultData.setAreaId("");
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// lastUpdate
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/17 13:25:25"));
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// list of IvbTProductKey

		// change point code		
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp1 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp1);

		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		// set getLastUpdateDate 
		when(tmp1.updateProductDate(any(HashMap.class))).thenReturn(1);
		when(tmp1.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		when(tmp1.getProductData(any(HashMap.class))).thenReturn(productList);
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);

		// Mock setup
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(0);

		// insert data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataProduct5() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Farm id
		cultivationResultData.setFarmId("");
		// Area id
		cultivationResultData.setAreaId("");
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// list of IvbTProductKey
		
		// change point code		
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp1 = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp1);
		when(tmp1.getProductData(any(HashMap.class))).thenReturn(productList);
		cultivationResultData.setChangePointCode(Constants.DEFAULT_LINE_ID);

		// Mock setup
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(0);

		// insert data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT, result);
	}
	@Test
	@Transactional
	public void testInsertDataProductUpdateSuccessful() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Farm id
		cultivationResultData.setFarmId("");
		// Area id
		cultivationResultData.setAreaId("");
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_PLANTING);

		// Mock setup
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(1);

		// insert data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}
	@Test
	@Transactional
	public void testInsertDataOuterException() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);

		// insert data
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}
	@Test
	@Transactional
	public void testInsertDataFailed() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);

		// Mock setup
		IvbTCultivationResultMapper tmp = Mockito.mock(IvbTCultivationResultMapper.class);
		when(dao.getIvbTCultivationResultMapper()).thenReturn(tmp);
		when(tmp.insert(any(IvbTCultivationResult.class))).thenReturn(0);

		// insert data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION, result);
	}
	@Test
	@Transactional
	public void testInsertDataSuccess() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);

		// Mock setup
		IvbTCultivationResultMapper tmp = Mockito.mock(IvbTCultivationResultMapper.class);
		when(dao.getIvbTCultivationResultMapper()).thenReturn(tmp);
		when(tmp.insert(any(IvbTCultivationResult.class))).thenReturn(1);

		// insert data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
	}
	@Test
	@Transactional
	public void testInsertDataDuplicate() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// Working date
		cultivationResultData.setWorkingDate(Calendar.getInstance().getTime());
    	// Process id
		cultivationResultData.setProcessId("");
    	// Task id
		cultivationResultData.setTaskId("");
    	// Status id
		cultivationResultData.setStatusId("");
		// current selected ids
		cultivationResultData.setCurrentBlockIdString("B000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);

		// Mock setup
		IvbTCultivationResultMapper tmp = Mockito.mock(IvbTCultivationResultMapper.class);
		when(dao.getIvbTCultivationResultMapper()).thenReturn(tmp);
		doThrow(new DuplicateKeyException("")).when(tmp).insert(any(IvbTCultivationResult.class));

		// insert data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_DUPLICATED, result);
	}

	@Test
	@Transactional
	public void testDeleteDataChangePointCodeNONE() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);

		// delete data
		String result = bnn0035BlockUpdateService.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}
	@Test
	@Transactional
	public void testDeleteDataChangePointCodeCULTIVATIONSTART() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_CULTIVATION_START);

		// delete data
		String result = bnn0035BlockUpdateService.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}
	@Test
	@Transactional
	public void testDeleteDataChangePointCodePLANTING() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_PLANTING);

		// delete data
		String result = bnn0035BlockUpdateService.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}
	@Test
	@Transactional
	public void testDeleteDataChangePointCodeFLOWERING() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_FLOWERING);

		// delete data
		String result = bnn0035BlockUpdateService.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}
	@Test
	@Transactional
	public void testDeleteDataChangePointCodeBAGGED() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_BAGGED);

		// delete data
		String result = bnn0035BlockUpdateService.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}
	@Test
	@Transactional
	public void testDeleteDataChangePointCodeHARVESTED() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);

		// delete data
		String result = bnn0035BlockUpdateService.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}
	@Test
	@Transactional
	public void testDeleteDataChangePointCodeWRONG() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.DEFAULT_LINE_ID);

		// delete data
		String result = bnn0035BlockUpdateService.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}
	@Test
	@Transactional
	public void testDeleteDataInnerException() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);

		// Mock setup
		IvbTCultivationResultMapper tmp = Mockito.mock(IvbTCultivationResultMapper.class);
		when(dao.getIvbTCultivationResultMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).deleteByPrimaryKey(any(IvbTCultivationResultKey.class));

		// delete data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}
	@Test
	@Transactional
	public void testDeleteDataOuterException() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);

		// delete data
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataRollback() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateCultivation(date);
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);
		// list of IvbTProductKey
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductData(any(HashMap.class))).thenReturn(productList);
		
		IvbTCultivationResult ivbTCultivationResult = new IvbTCultivationResult();
		ivbTCultivationResult.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateCultivation(any(HashMap.class))).thenReturn(ivbTCultivationResult);
		// delete data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataFailedUpdateDate() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateCultivation(format.parse("2017/05/18 13:25:25"));
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);
		// list of IvbTProductKey
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductData(any(HashMap.class))).thenReturn(productList);
		
		IvbTCultivationResult ivbTCultivationResult = new IvbTCultivationResult();
		ivbTCultivationResult.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateCultivation(any(HashMap.class))).thenReturn(ivbTCultivationResult);
		
		// delete data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataSuccess() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateCultivation(format.parse("2017/05/18 13:25:25"));
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);
		// list of IvbTProductKey
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductData(any(HashMap.class))).thenReturn(productList);
		
		IvbTCultivationResult ivbTCultivationResult = new IvbTCultivationResult();
		ivbTCultivationResult.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateCultivation(any(HashMap.class))).thenReturn(ivbTCultivationResult);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		// delete data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataSuccessTASK_CHANGE_POINT_CODE_NONE() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateCultivation(format.parse("2017/05/18 13:25:25"));
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);
		// list of IvbTProductKey
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductData(any(HashMap.class))).thenReturn(productList);
		
		IvbTCultivationResult ivbTCultivationResult = new IvbTCultivationResult();
		ivbTCultivationResult.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateCultivation(any(HashMap.class))).thenReturn(ivbTCultivationResult);
		when(tmp.updateData(any(HashMap.class))).thenReturn(0);
		when(tmp.getLastUpdateDateCultivation(any(HashMap.class))).thenReturn(null);
		// delete data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataTASK_CHANGE_POINT_CODE_CULTIVATION_STARTFaieldUpdateDate() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/18 13:25:25"));
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_CULTIVATION_START);
		// list of IvbTProductKey
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductData(any(HashMap.class))).thenReturn(productList);
		
		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		// delete data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataTASK_CHANGE_POINT_CODE_CULTIVATION_STARTRollBack() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/18 13:25:25"));
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_CULTIVATION_START);
		// list of IvbTProductKey
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductData(any(HashMap.class))).thenReturn(productList);
		
		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		// delete data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataTASK_CHANGE_POINT_CODE_PLANTINGRollBack() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/18 13:25:25"));
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_PLANTING);
		// list of IvbTProductKey
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductData(any(HashMap.class))).thenReturn(productList);
		
		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		// delete data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataTASK_CHANGE_POINT_CODE_FLOWERINGRollBack() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/17 13:25:25"));
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_FLOWERING);
		// list of IvbTProductKey
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductData(any(HashMap.class))).thenReturn(productList);
		
		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		// delete data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataTASK_CHANGE_POINT_CODE_BAGGEDRollBack() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/18 13:25:25"));
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_BAGGED);
		// list of IvbTProductKey
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductData(any(HashMap.class))).thenReturn(productList);
		
		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		// delete data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataTASK_CHANGE_POINT_CODE_HARVESTEDRollBack() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/18 13:25:25"));
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);
		// list of IvbTProductKey
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductData(any(HashMap.class))).thenReturn(productList);
		
		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		// delete data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataTASK_CHANGE_POINT_CODE_HARVESTEDRollBack12() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/18 13:25:25"));
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);
		// list of IvbTProductKey
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductData(any(HashMap.class))).thenReturn(productList);
		
		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(null);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		// delete data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataTASK_CHANGE_POINT_CODE_HARVESTEDRollBack2() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/18 13:25:25"));
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);
		// list of IvbTProductKey
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductData(any(HashMap.class))).thenReturn(productList);
		when(tmp.updateProductHarvestedDate(any(HashMap.class))).thenReturn(1);
		
		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		// delete data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataTASK_CHANGE_POINT_CODE_HARVESTEDRollBack3() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/17 13:25:25"));
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);
		// list of IvbTProductKey
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductData(any(HashMap.class))).thenReturn(productList);
		when(tmp.updateProductHarvestedDate(any(HashMap.class))).thenReturn(1);
		
		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		// delete data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataTASK_CHANGE_POINT_CODE_HARVESTEDRollBack1() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/18 13:25:25"));
		// change point code
		cultivationResultData.setChangePointCode("-9");
		// list of IvbTProductKey
		List<IvbTProductKey> productList = new ArrayList<IvbTProductKey>();
		// create a object Data
		IvbTProductKey productKey = new IvbTProductKey();
		productList.add(productKey);
		// Mock setup
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductData(any(HashMap.class))).thenReturn(productList);
		
		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		// delete data
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		String result = bnn0035BlockUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test 
	@Transactional
	public void testGetLastUpdateDateProduct() {
		IvbTProduct product = new IvbTProduct();
		Bnn0035BlockUpdateMapper tmp = Mockito.mock(Bnn0035BlockUpdateMapper.class);
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(tmp);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(product);
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		
		bnn0035BlockUpdateService_Mock.getLastUpdateDateProduct("F001", "A001");
	}

	@Test 
	@Transactional
	public void testGetLastUpdateDateProductInnerException() {
		when(dao.getBnn0035BlockUpdateMapper()).thenReturn(null);
		bnn0035BlockUpdateService_Mock.setAppContext(appContext);
		
		bnn0035BlockUpdateService_Mock.getLastUpdateDateProduct("F001", "A001");
	}

	@Test 
	@Transactional
	public void testGetLastUpdateDateProductOuterException() {
		bnn0035BlockUpdateService_Mock.getLastUpdateDateProduct("F001", "A001");
	}
}
