package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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

import vn.bananavietnam.ict.banana.component.Bnn0037ProductExtendObject;
import vn.bananavietnam.ict.banana.component.BnnBlockCultivationResult;
import vn.bananavietnam.ict.banana.dao.Bnn0037BlockDetailUpdateDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0037BlockDetailUpdateMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMBlockMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMStatusMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTCultivationResultMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTProductMapper;
import vn.bananavietnam.ict.common.db.mapper.UtilMapper;
import vn.bananavietnam.ict.common.db.model.IvbMArea;
import vn.bananavietnam.ict.common.db.model.IvbMAreaKey;
import vn.bananavietnam.ict.common.db.model.IvbMBlock;
import vn.bananavietnam.ict.common.db.model.IvbMBlockExample;
import vn.bananavietnam.ict.common.db.model.IvbMFarm;
import vn.bananavietnam.ict.common.db.model.IvbMKind;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.db.model.IvbMStatus;
import vn.bananavietnam.ict.common.db.model.IvbMStatusExample;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.db.model.IvbTCultivationResult;
import vn.bananavietnam.ict.common.db.model.IvbTCultivationResultKey;
import vn.bananavietnam.ict.common.db.model.IvbTProduct;
import vn.bananavietnam.ict.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0037BlockDetailUpdateServiceTest {

	@Autowired
    private ApplicationContext appContext;

	@Autowired
	private Bnn0037BlockDetailUpdateService bnn0037BlockDetailUpdateService;

	@InjectMocks
	private Bnn0037BlockDetailUpdateService bnn0037BlockDetailUpdateService_Mock;

	@Mock
	ObjectMapper mapper = new ObjectMapper();
	PlatformTransactionManager txManager;

	@Mock
	Bnn0037BlockDetailUpdateDao dao;

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
        screenId.add("0037");
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
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		bnn0037BlockDetailUpdateService_Mock.initData(model, "", "1", "1", "1", "1", "1");
	}
	@Test
	@Transactional
	public void testInitDataBlankParameter1() {
		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).selectFarmDataMaster();

		// Mock setup
		Model model = Mockito.mock(Model.class);

		// start testing
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		bnn0037BlockDetailUpdateService_Mock.initData(model, "1", "", "1", "1", "1", "1");
	}
	@Test
	@Transactional
	public void testInitDataBlankParameter2() {
		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).selectFarmDataMaster();

		// Mock setup
		Model model = Mockito.mock(Model.class);

		// start testing
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		bnn0037BlockDetailUpdateService_Mock.initData(model, "1", "1", "", "1", "1", "1");
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
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		bnn0037BlockDetailUpdateService_Mock.initData(model, "1", "1", "1", "", "1", "1");
	}
	@Test
	@Transactional
	public void testInitDataBlankParameter4() {
		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).selectFarmDataMaster();

		// Mock setup
		Model model = Mockito.mock(Model.class);

		// start testing
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		bnn0037BlockDetailUpdateService_Mock.initData(model, "1", "1", "1", "1", "", "1");
	}
	@Test
	@Transactional
	public void testInitDataBlankParameter5() {
		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).selectFarmDataMaster();

		// Mock setup
		Model model = Mockito.mock(Model.class);

		// start testing
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		bnn0037BlockDetailUpdateService_Mock.initData(model, "1", "1", "1", "1", "1", "");
	}
	@Test
	@Transactional
	public void testInitDataBlankParameter6() {
		// Mock setup
		when(dao.getIvbMAreaMapper()).thenReturn(null);

		// Mock setup
		Model model = Mockito.mock(Model.class);

		// start testing
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		bnn0037BlockDetailUpdateService_Mock.initData(model, "1", "1", "1", "1", "1", "1");
	}
	@Test
	@Transactional
	public void testInitDataOuterException() {
		// Mock setup
		Model model = Mockito.mock(Model.class);

		// start testing
		bnn0037BlockDetailUpdateService_Mock.initData(model, "1", "1", "1", "1", "1", "1");
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testInitDataSuccess() {
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
		List<IvbMBlock> blockResult = new ArrayList<IvbMBlock>();
		IvbMBlock blockData = new IvbMBlock();
		blockData.setBlockId("1");
		blockResult.add(blockData);
		IvbMBlockMapper tmp = Mockito.mock(IvbMBlockMapper.class);
		when(dao.getIvbMBlockMapper()).thenReturn(tmp);
		when(tmp.selectByExample(any(IvbMBlockExample.class))).thenReturn(blockResult);

		// Mock setup
		IvbMAreaMapper tmp1 = Mockito.mock(IvbMAreaMapper.class);
		when(dao.getIvbMAreaMapper()).thenReturn(tmp1);
		when(tmp1.selectByPrimaryKey(any(IvbMAreaKey.class))).thenReturn(new IvbMArea());

		// Mock setup
		IvbMKindMapper tmp2 = Mockito.mock(IvbMKindMapper.class);
		when(dao.getIvbMKindMapper()).thenReturn(tmp2);
		when(tmp2.selectByPrimaryKey(any(String.class))).thenReturn(new IvbMKind());

		// Mock setup
		List<IvbMProcess> processResult = new ArrayList<IvbMProcess>();
		processResult.add(new IvbMProcess());
		Bnn0037BlockDetailUpdateMapper tmp3 = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp3);
		when(tmp3.getProcessData(any(HashMap.class))).thenReturn(processResult);

		// Mock setup
		List<IvbMStatus> statusResult = new ArrayList<IvbMStatus>();
		statusResult.add(new IvbMStatus());
		IvbMStatusMapper tmp4 = Mockito.mock(IvbMStatusMapper.class);
		when(dao.getIvbMStatusMapper()).thenReturn(tmp4);
		when(tmp4.selectByExample(any(IvbMStatusExample.class))).thenReturn(statusResult);

		// Mock setup
		IvbMFarmMapper tmp5 = Mockito.mock(IvbMFarmMapper.class);
		when(dao.getIvbMFarmMapper()).thenReturn(tmp5);
		when(tmp5.selectByPrimaryKey(any(String.class))).thenReturn(new IvbMFarm());

		// Mock setup
		Model model = Mockito.mock(Model.class);

		// start testing
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		bnn0037BlockDetailUpdateService_Mock.initData(model, "", "", "", "", "", "");
	}

	@Test
	@Transactional
	public void testGetProductDataInnerException() {
		// Mock setup
		when(dao.getIvbMBlockMapper()).thenReturn(null);

		// get data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		List<Bnn0037ProductExtendObject> productData = bnn0037BlockDetailUpdateService_Mock.getProductData("", "", "");
		// start testing
		Assert.assertEquals(0, productData.size());
	}
	@Test
	@Transactional
	public void testGetProductDataOuterException() {
		// get data
		List<Bnn0037ProductExtendObject> productData = bnn0037BlockDetailUpdateService_Mock.getProductData("", "", "");
		// start testing
		Assert.assertEquals(0, productData.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testGetProductDataSuccess() {
		// Mock setup
		ArrayList<Bnn0037ProductExtendObject> productResult = new ArrayList<Bnn0037ProductExtendObject>();
		productResult.add(new Bnn0037ProductExtendObject());
		Bnn0037BlockDetailUpdateMapper tmp = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp);
		when(tmp.getProductExtendData(any(HashMap.class))).thenReturn(productResult);

		// get data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		List<Bnn0037ProductExtendObject> productData = bnn0037BlockDetailUpdateService_Mock.getProductData("", "", "");
		// start testing
		Assert.assertEquals(1, productData.size());
	}

	@Test
	@Transactional
	public void testGetCultivationResultDataInnerException() {
		// Mock setup
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(null);

		// get data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		List<BnnBlockCultivationResult> cultivationResultList = bnn0037BlockDetailUpdateService_Mock.getCultivationResultData("", "", "", "");
		// start testing
		Assert.assertEquals(0, cultivationResultList.size());
	}
	@Test
	@Transactional
	public void testGetCultivationResultDataOuterException() {
		// get data
		List<BnnBlockCultivationResult> cultivationResultList = bnn0037BlockDetailUpdateService_Mock.getCultivationResultData("", "", "", "");
		// start testing
		Assert.assertEquals(0, cultivationResultList.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testGetCultivationResultDataSuccess() {
		// Mock setup
		ArrayList<BnnBlockCultivationResult> cultivationResult = new ArrayList<BnnBlockCultivationResult>();
		cultivationResult.add(new BnnBlockCultivationResult());
		Bnn0037BlockDetailUpdateMapper tmp = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp);
		when(tmp.getCultivationResultData(any(HashMap.class))).thenReturn(cultivationResult);

		// get data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		List<BnnBlockCultivationResult> cultivationResultList = bnn0037BlockDetailUpdateService_Mock.getCultivationResultData("", "", "", "");
		// start testing
		Assert.assertEquals(1, cultivationResultList.size());
	}

	@Test
	@Transactional
	public void testGetProcessDataInnerException() {
		// Mock setup
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(null);

		// get data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		List<IvbMProcess> processResultList = bnn0037BlockDetailUpdateService_Mock.getProcessData("", "");
		// start testing
		Assert.assertEquals(0, processResultList.size());
	}
	@Test
	@Transactional
	public void testGetProcessDataOuterException() {
		// get data
		List<IvbMProcess> processResultList = bnn0037BlockDetailUpdateService_Mock.getProcessData("", "");
		// start testing
		Assert.assertEquals(0, processResultList.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testGetProcessDataSuccess() {
		// Mock setup
		ArrayList<IvbMProcess> processResult = new ArrayList<IvbMProcess>();
		processResult.add(new IvbMProcess());
		Bnn0037BlockDetailUpdateMapper tmp = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp);
		when(tmp.getProcessData(any(HashMap.class))).thenReturn(processResult);

		// get data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		List<IvbMProcess> processResultList = bnn0037BlockDetailUpdateService_Mock.getProcessData("", "");
		// start testing
		Assert.assertEquals(1, processResultList.size());
	}

	@Test
	@Transactional
	public void testGetTaskDataInnerException() {
		// Mock setup
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(null);

		// get data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		List<IvbMTask> taskResultList = bnn0037BlockDetailUpdateService_Mock.getTaskData("", "", "");
		// start testing
		Assert.assertEquals(null, taskResultList);
	}
	@Test
	@Transactional
	public void testGetTaskDataOuterException() {
		// get data
		List<IvbMTask> taskResultList = bnn0037BlockDetailUpdateService_Mock.getTaskData("", "", "");
		// start testing
		Assert.assertEquals(null, taskResultList);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testGetTaskData() {
		// Mock setup
		ArrayList<IvbMTask> taskResult = new ArrayList<IvbMTask>();
		taskResult.add(new IvbMTask());
		Bnn0037BlockDetailUpdateMapper tmp = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp);
		when(tmp.getTaskData(any(HashMap.class))).thenReturn(taskResult);

		// get data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		List<IvbMTask> taskResultList = bnn0037BlockDetailUpdateService_Mock.getTaskData("", "", "");
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
		String result = bnn0037BlockDetailUpdateService.insertData(cultivationResultData);
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
		String result = bnn0037BlockDetailUpdateService.insertData(cultivationResultData);
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
		String result = bnn0037BlockDetailUpdateService.insertData(cultivationResultData);
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
		String result = bnn0037BlockDetailUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataChangePointCodeNONE() {
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
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
		cultivationResultData.setCurrentProductIdString("L000:C000,");
		// current selected ids
		cultivationResultData.setCurrentLastUpdateDateString("-56328802475000,");


		Bnn0037BlockDetailUpdateMapper tmp0037 = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp0037);
		when(tmp0037.updateProductPlantingDate(any(HashMap.class))).thenReturn(1);
		
		IvbTCultivationResultMapper tmpResult = Mockito.mock(IvbTCultivationResultMapper.class);
		when(dao.getIvbTCultivationResultMapper()).thenReturn(tmpResult);
		when(tmpResult.insert(any(IvbTCultivationResult.class))).thenReturn(0);
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);

		// insert data
		String result = bnn0037BlockDetailUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION, result);
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
		cultivationResultData.setCurrentProductIdString("L000:C000,");
		// current selected ids
		cultivationResultData.setCurrentLastUpdateDateString("-56328802475000,");


		Bnn0037BlockDetailUpdateMapper tmp0037 = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp0037);
		when(tmp0037.updateProductPlantingDate(any(HashMap.class))).thenReturn(1);
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_CULTIVATION_START);

		// insert data
		String result = bnn0037BlockDetailUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT, result);
	}
	
	@SuppressWarnings("unchecked")
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
		cultivationResultData.setCurrentProductIdString("L000:C000,");
		// current selected ids
		cultivationResultData.setCurrentLastUpdateDateString("-56328802475000,");


		Bnn0037BlockDetailUpdateMapper tmp0037 = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp0037);
		when(tmp0037.updateProductPlantingDate(any(HashMap.class))).thenReturn(1);
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_PLANTING);

		// insert data
		String result = bnn0037BlockDetailUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT, result);
	}
	
	@SuppressWarnings("unchecked")
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
		cultivationResultData.setCurrentProductIdString("L000:C000,");
		// current selected ids
		cultivationResultData.setCurrentLastUpdateDateString("-56328802475000,");


		Bnn0037BlockDetailUpdateMapper tmp0037 = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp0037);
		when(tmp0037.updateProductPlantingDate(any(HashMap.class))).thenReturn(1);
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_FLOWERING);

		// insert data
		String result = bnn0037BlockDetailUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataChangePointCodeBAGGED() {
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
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
		cultivationResultData.setCurrentProductIdString("L000:C000,");
		// current selected ids
		cultivationResultData.setCurrentLastUpdateDateString("-56328802475000,");


		Bnn0037BlockDetailUpdateMapper tmp0037 = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp0037);
		when(tmp0037.updateProductPlantingDate(any(HashMap.class))).thenReturn(1);
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_BAGGED);

		// insert data
		String result = bnn0037BlockDetailUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataChangePointCodeHARVESTED() throws ParseException {
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		String dateTestStr = "2017/05/18 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(dateTestStr);
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
		cultivationResultData.setCurrentProductIdString("L000:C000,");
		// current selected ids
		cultivationResultData.setCurrentLastUpdateDateString("-56297266444000,");


		Bnn0037BlockDetailUpdateMapper tmp0037 = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp0037);
		when(tmp0037.updateProductPlantingDate(any(HashMap.class))).thenReturn(1);
		
		IvbTProduct productData = new IvbTProduct();
		productData.setLastUpdateDate(date);
		when(tmp0037.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(productData);
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);

		// insert data
		String result = bnn0037BlockDetailUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT_UPDATE_DATE, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataChangePointCodeHARVESTED1() throws ParseException {
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		String dateTestStr = "2017/05/18 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(dateTestStr);
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
		cultivationResultData.setCurrentProductIdString("L000:C000,");
		// current selected ids
		cultivationResultData.setCurrentLastUpdateDateString("-56297266444000,");


		Bnn0037BlockDetailUpdateMapper tmp0037 = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp0037);
		when(tmp0037.updateProductPlantingDate(any(HashMap.class))).thenReturn(1);
		
		IvbTProduct productData = new IvbTProduct();
		productData.setLastUpdateDate(date);
		when(tmp0037.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(productData);
		when(tmp0037.updateProductHarvestedDate(any(HashMap.class))).thenReturn(1);
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);

		// insert data
		String result = bnn0037BlockDetailUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataChangePointCodeHARVESTED3() throws ParseException {
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		String dateTestStr = "2017/05/18 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(dateTestStr);
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
		cultivationResultData.setCurrentProductIdString("L000:C000,");
		// current selected ids
		cultivationResultData.setCurrentLastUpdateDateString("-56297266475000,");


		Bnn0037BlockDetailUpdateMapper tmp0037 = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp0037);
		when(tmp0037.updateProductPlantingDate(any(HashMap.class))).thenReturn(0);
		
		IvbTProduct productData = new IvbTProduct();
		productData.setLastUpdateDate(date);
		when(tmp0037.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(productData);
		when(tmp0037.updateProductHarvestedDate(any(HashMap.class))).thenReturn(0);
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);

		// insert data
		String result = bnn0037BlockDetailUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT, result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataChangePointCodeHARVESTED2() throws ParseException {
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		String dateTestStr = "2017/05/18 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(dateTestStr);
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
		cultivationResultData.setCurrentProductIdString("L000:C000,");
		// current selected ids
		cultivationResultData.setCurrentLastUpdateDateString("-56297266475000,");


		Bnn0037BlockDetailUpdateMapper tmp0037 = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp0037);
		when(tmp0037.updateProductPlantingDate(any(HashMap.class))).thenReturn(0);
		
		IvbTProduct productData = new IvbTProduct();
		productData.setLastUpdateDate(date);
		when(tmp0037.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(productData);
		when(tmp0037.updateProductHarvestedDate(any(HashMap.class))).thenReturn(1);

		IvbTCultivationResultMapper tmpCul = Mockito.mock(IvbTCultivationResultMapper.class);
		when(dao.getIvbTCultivationResultMapper()).thenReturn(tmpCul);
		when(tmpCul.insert(any(IvbTCultivationResult.class))).thenReturn(1);
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);

		// insert data
		String result = bnn0037BlockDetailUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
	}

	@SuppressWarnings("unchecked")
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
		cultivationResultData.setCurrentProductIdString("L000:C000,");
		// current selected ids
		cultivationResultData.setCurrentLastUpdateDateString("-56328802415000,");


		Bnn0037BlockDetailUpdateMapper tmp0037 = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp0037);
		when(tmp0037.updateProductPlantingDate(any(HashMap.class))).thenReturn(1);
		
		
		// change point code
		cultivationResultData.setChangePointCode(Constants.DEFAULT_LINE_ID);

		// insert data
		String result = bnn0037BlockDetailUpdateService.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataProductUpdateSuccessful() throws ParseException {
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
		cultivationResultData.setCurrentProductIdString("L000:C000,");
		// current selected ids
		cultivationResultData.setCurrentLastUpdateDateString("-56328802475000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_PLANTING);

		// Mock setup
		IvbTProductMapper tmp = Mockito.mock(IvbTProductMapper.class);
		when(dao.getIvbTProductMapper()).thenReturn(tmp);
		when(tmp.updateByPrimaryKeySelective(any(IvbTProduct.class))).thenReturn(1);

		Bnn0037BlockDetailUpdateMapper tmp0037 = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp0037);
		when(tmp0037.updateProductPlantingDate(any(HashMap.class))).thenReturn(0);
		// insert data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		String result = bnn0037BlockDetailUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_PRODUCT, result);
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
		cultivationResultData.setCurrentProductIdString("L000:C000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);

		// insert data
		String result = bnn0037BlockDetailUpdateService_Mock.insertData(cultivationResultData);
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
		cultivationResultData.setCurrentProductIdString("L000:C000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);

		// Mock setup
		IvbTCultivationResultMapper tmp = Mockito.mock(IvbTCultivationResultMapper.class);
		when(dao.getIvbTCultivationResultMapper()).thenReturn(tmp);
		when(tmp.insert(any(IvbTCultivationResult.class))).thenReturn(0);

		// insert data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		String result = bnn0037BlockDetailUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
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
		cultivationResultData.setCurrentProductIdString("L000:C000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);

		// Mock setup
		IvbTCultivationResultMapper tmp = Mockito.mock(IvbTCultivationResultMapper.class);
		when(dao.getIvbTCultivationResultMapper()).thenReturn(tmp);
		when(tmp.insert(any(IvbTCultivationResult.class))).thenReturn(1);

		// insert data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		String result = bnn0037BlockDetailUpdateService_Mock.insertData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
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
		cultivationResultData.setCurrentProductIdString("L000:C000,");
		// current selected ids
		cultivationResultData.setCurrentLastUpdateDateString("-56328802475000,");
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);

		// Mock setup
		IvbTCultivationResultMapper tmp = Mockito.mock(IvbTCultivationResultMapper.class);
		when(dao.getIvbTCultivationResultMapper()).thenReturn(tmp);
		doThrow(new DuplicateKeyException("")).when(tmp).insert(any(IvbTCultivationResult.class));

		// insert data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		String result = bnn0037BlockDetailUpdateService_Mock.insertData(cultivationResultData);
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
		String result = bnn0037BlockDetailUpdateService.deleteData(cultivationResultData);
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
		String result = bnn0037BlockDetailUpdateService.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT, result);
	}
	@Test
	@Transactional
	public void testDeleteDataChangePointCodePLANTING() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_PLANTING);

		// delete data
		String result = bnn0037BlockDetailUpdateService.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT, result);
	}
	@Test
	@Transactional
	public void testDeleteDataChangePointCodeFLOWERING() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_FLOWERING);

		// delete data
		String result = bnn0037BlockDetailUpdateService.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT, result);
	}
	@Test
	@Transactional
	public void testDeleteDataChangePointCodeBAGGED() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_BAGGED);

		// delete data
		String result = bnn0037BlockDetailUpdateService.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT, result);
	}
	@Test
	@Transactional
	public void testDeleteDataChangePointCodeHARVESTED() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);

		// delete data
		String result = bnn0037BlockDetailUpdateService.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT, result);
	}
	@Test
	@Transactional
	public void testDeleteDataChangePointCodeWRONG() {
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.DEFAULT_LINE_ID);

		// delete data
		String result = bnn0037BlockDetailUpdateService.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT, result);
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
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		String result = bnn0037BlockDetailUpdateService_Mock.deleteData(cultivationResultData);
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
		String result = bnn0037BlockDetailUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataSuccess() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateCultivation(date);
		cultivationResultData.setLastUpdateDateProduct(date);
		// Mock setup
		Bnn0037BlockDetailUpdateMapper tmp = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);

		// delete data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		String result = bnn0037BlockDetailUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataFailedUpdateDate() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateCultivation(format.parse("2017/05/18 13:25:25"));
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/18 13:25:25"));
		
		// Mock setup
		Bnn0037BlockDetailUpdateMapper tmp = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(0);
		IvbTCultivationResult cultivationResult = new IvbTCultivationResult();
		cultivationResult.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateCultivation(any(HashMap.class))).thenReturn(cultivationResult);
		// delete data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		String result = bnn0037BlockDetailUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataRollBack() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_NONE);
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateCultivation(format.parse("2017/05/17 13:25:25"));
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/17 13:25:25"));
		
		// Mock setup
		Bnn0037BlockDetailUpdateMapper tmp = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(0);
		IvbTCultivationResult cultivationResult = new IvbTCultivationResult();
		cultivationResult.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateCultivation(any(HashMap.class))).thenReturn(cultivationResult);
		// delete data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		String result = bnn0037BlockDetailUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataFailedUpdateDatediffenentTASK_CHANGE_POINT_CODE_NONE() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_CULTIVATION_START);
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateCultivation(format.parse("2017/05/18 13:25:25"));
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/18 13:25:25"));
		
		// Mock setup
		Bnn0037BlockDetailUpdateMapper tmp = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(0);
		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		// delete data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		String result = bnn0037BlockDetailUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT_UPDATE_DATE, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataFailedUpdateDatediffenentTASK_CHANGE_POINT_CODE_HARVESTED() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_HARVESTED);
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateCultivation(format.parse("2017/05/18 13:25:25"));
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/18 13:25:25"));
		
		// Mock setup
		Bnn0037BlockDetailUpdateMapper tmp = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(0);
		when(tmp.updateProductHarvestedDate(any(HashMap.class))).thenReturn(1);
		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		// delete data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		String result = bnn0037BlockDetailUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataRollBackdiffenentTASK_CHANGE_POINT_CODE_NONE() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		BnnBlockCultivationResult cultivationResultData = new BnnBlockCultivationResult();
		// change point code
		cultivationResultData.setChangePointCode(Constants.TASK_CHANGE_POINT_CODE_CULTIVATION_START);
		cultivationResultData.setWorkingDate(date);
		cultivationResultData.setLastUpdateDate(date);
		cultivationResultData.setLastUpdateDateCultivation(format.parse("2017/05/17 13:25:25"));
		cultivationResultData.setLastUpdateDateProduct(format.parse("2017/05/17 13:25:25"));
		
		// Mock setup
		Bnn0037BlockDetailUpdateMapper tmp = Mockito.mock(Bnn0037BlockDetailUpdateMapper.class);
		when(dao.getBnn0037BlockDetailUpdateMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(0);
		IvbTProduct ivbTProduct = new IvbTProduct();
		ivbTProduct.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(ivbTProduct);
		// delete data
		bnn0037BlockDetailUpdateService_Mock.setAppContext(appContext);
		String result = bnn0037BlockDetailUpdateService_Mock.deleteData(cultivationResultData);
		// start testing
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PRODUCT, result);
	}
}
