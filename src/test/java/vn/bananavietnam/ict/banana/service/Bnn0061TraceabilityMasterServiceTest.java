package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;

import vn.bananavietnam.ict.banana.component.Bnn0061TraceabilityMasterDataConditions;
import vn.bananavietnam.ict.banana.component.Bnn0061TraceabilityMasterDataObject;
import vn.bananavietnam.ict.banana.component.Bnn0075SearchShippingScreenResult;
import vn.bananavietnam.ict.banana.dao.Bnn0061TraceabilityMasterDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0061TraceabilityMasterMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.mapper.IvbMBlockMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMProcessMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMStatusMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMTaskMapper;
import vn.bananavietnam.ict.common.db.model.IvbMBlock;
import vn.bananavietnam.ict.common.db.model.IvbMBlockExample;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.db.model.IvbMProcessExample;
import vn.bananavietnam.ict.common.db.model.IvbMStatus;
import vn.bananavietnam.ict.common.db.model.IvbMStatusExample;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.db.model.IvbMTaskExample;
import vn.bananavietnam.ict.common.db.model.IvbTProduct;
import vn.bananavietnam.ict.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0061TraceabilityMasterServiceTest {

	@Autowired
	private ApplicationContext appContext;

	@InjectMocks
	private Bnn0061TraceabilityMasterService service;

	@Mock
	Bnn0061TraceabilityMasterDao dao;

	@Mock
	private UtilDao utilDao;

	@Mock
	private Util util;

	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0061");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        when(util.getUserInfo()).thenReturn(u);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInitData() throws SQLException {

		ExtendedModelMap model = new ExtendedModelMap();
		UtilComponent utilComponent = new UtilComponent();
		Bnn0075SearchShippingScreenResult bnn0075result = new Bnn0075SearchShippingScreenResult();
		List<UtilComponent> farmResult = new ArrayList<UtilComponent>();
		List<IvbMProcess> processResult = new ArrayList<IvbMProcess>();
		List<IvbMTask> taskResult = new ArrayList<IvbMTask>();
		List<IvbMStatus> statusResult = new ArrayList<IvbMStatus>();
		utilComponent.setFarmId(null);
		utilComponent.setFarmName(null);
		utilComponent.setFarmId(null);
		utilComponent.setAreaName(null);
		bnn0075result.setFarmId("");
		farmResult.add(utilComponent);
		processResult.add(new IvbMProcess());
		taskResult.add(new IvbMTask());
		statusResult.add(new IvbMStatus());

        service.setAppContext(appContext);

		// Mock setup
    	when(util.getFarmData(any(UtilDao.class))).thenReturn(farmResult);

        IvbMProcessMapper tmpProcess = Mockito.mock(IvbMProcessMapper.class);
    	when(dao.getIvbMProcessMapper()).thenReturn(tmpProcess);
        when(tmpProcess.selectByExample(any(IvbMProcessExample.class))).thenReturn(processResult);

        IvbMTaskMapper tmpTask = Mockito.mock(IvbMTaskMapper.class);
    	when(dao.getIvbMTaskMapper()).thenReturn(tmpTask);
        when(tmpTask.selectByExample(any(IvbMTaskExample.class))).thenReturn(taskResult);

        IvbMStatusMapper tmpStatus = Mockito.mock(IvbMStatusMapper.class);
    	when(dao.getIvbMStatusMapper()).thenReturn(tmpStatus);
        when(tmpStatus.selectByExample(any(IvbMStatusExample.class))).thenReturn(statusResult);

        Bnn0061TraceabilityMasterMapper tmp0061 = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
        when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp0061);
        // process Data
        List<IvbMProcess> listProcess = new ArrayList<IvbMProcess>();
        when(tmp0061.getProcessList(any(HashMap.class))).thenReturn(listProcess);
        
    	// task Data
        List<IvbMTask> listTask = new ArrayList<IvbMTask>();
        when(tmp0061.getTaskList(any(HashMap.class))).thenReturn(listTask);
        
    	// task Data
        List<IvbMStatus> listStatus = new ArrayList<IvbMStatus>();
        when(tmp0061.getStatusList(any(HashMap.class))).thenReturn(listStatus);     
        
        // start testing
		service.initData(model, bnn0075result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testInitDataResultFarmId0075Null() throws SQLException {

		ExtendedModelMap model = new ExtendedModelMap();
		UtilComponent utilComponent = new UtilComponent();
		Bnn0075SearchShippingScreenResult bnn0075result = new Bnn0075SearchShippingScreenResult();
		List<UtilComponent> farmResult = new ArrayList<UtilComponent>();
		List<IvbMProcess> processResult = new ArrayList<IvbMProcess>();
		List<IvbMTask> taskResult = new ArrayList<IvbMTask>();
		List<IvbMStatus> statusResult = new ArrayList<IvbMStatus>();
		utilComponent.setFarmId(null);
		utilComponent.setFarmName(null);
		utilComponent.setFarmId(null);
		utilComponent.setAreaName(null);
		bnn0075result.setFarmId("");
		farmResult.add(utilComponent);
		processResult.add(new IvbMProcess());
		taskResult.add(new IvbMTask());
		statusResult.add(new IvbMStatus());

        service.setAppContext(appContext);

		// Mock setup
    	when(util.getFarmData(any(UtilDao.class))).thenReturn(farmResult);
    	Bnn0075SearchShippingScreenResult mock0075 = Mockito.mock(Bnn0075SearchShippingScreenResult.class);
    	when(mock0075.getFarmId()).thenReturn(null);
    	
    	bnn0075result = mock0075;
        IvbMProcessMapper tmpProcess = Mockito.mock(IvbMProcessMapper.class);
    	when(dao.getIvbMProcessMapper()).thenReturn(tmpProcess);
        when(tmpProcess.selectByExample(any(IvbMProcessExample.class))).thenReturn(processResult);

        IvbMTaskMapper tmpTask = Mockito.mock(IvbMTaskMapper.class);
    	when(dao.getIvbMTaskMapper()).thenReturn(tmpTask);
        when(tmpTask.selectByExample(any(IvbMTaskExample.class))).thenReturn(taskResult);

        IvbMStatusMapper tmpStatus = Mockito.mock(IvbMStatusMapper.class);
    	when(dao.getIvbMStatusMapper()).thenReturn(tmpStatus);
        when(tmpStatus.selectByExample(any(IvbMStatusExample.class))).thenReturn(statusResult);
        
        Bnn0061TraceabilityMasterMapper tmp0061 = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
        when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp0061);
        
        // process Data
        List<IvbMProcess> listProcess = new ArrayList<IvbMProcess>();
        when(tmp0061.getProcessList(any(HashMap.class))).thenReturn(listProcess);
        
    	// task Data
        List<IvbMTask> listTask = new ArrayList<IvbMTask>();
        when(tmp0061.getTaskList(any(HashMap.class))).thenReturn(listTask);
        
    	// task Data
        List<IvbMStatus> listStatus = new ArrayList<IvbMStatus>();
        when(tmp0061.getStatusList(any(HashMap.class))).thenReturn(listStatus);        
        // start testing
		service.initData(model, bnn0075result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testInitDataResultFarmId0075Null1() throws SQLException {

		ExtendedModelMap model = new ExtendedModelMap();
		Bnn0075SearchShippingScreenResult bnn0075result = new Bnn0075SearchShippingScreenResult();
		List<UtilComponent> farmResult = new ArrayList<UtilComponent>();
		List<IvbMProcess> processResult = new ArrayList<IvbMProcess>();
		List<IvbMTask> taskResult = new ArrayList<IvbMTask>();
		List<IvbMStatus> statusResult = new ArrayList<IvbMStatus>();
		bnn0075result.setFarmId("");
		processResult.add(new IvbMProcess());
		taskResult.add(new IvbMTask());
		statusResult.add(new IvbMStatus());

        service.setAppContext(appContext);

		// Mock setup
    	when(util.getFarmData(any(UtilDao.class))).thenReturn(farmResult);
    	Bnn0075SearchShippingScreenResult mock0075 = Mockito.mock(Bnn0075SearchShippingScreenResult.class);
    	when(mock0075.getFarmId()).thenReturn(null);
    	
    	bnn0075result = mock0075;
        IvbMProcessMapper tmpProcess = Mockito.mock(IvbMProcessMapper.class);
    	when(dao.getIvbMProcessMapper()).thenReturn(tmpProcess);
        when(tmpProcess.selectByExample(any(IvbMProcessExample.class))).thenReturn(processResult);

        IvbMTaskMapper tmpTask = Mockito.mock(IvbMTaskMapper.class);
    	when(dao.getIvbMTaskMapper()).thenReturn(tmpTask);
        when(tmpTask.selectByExample(any(IvbMTaskExample.class))).thenReturn(taskResult);

        IvbMStatusMapper tmpStatus = Mockito.mock(IvbMStatusMapper.class);
    	when(dao.getIvbMStatusMapper()).thenReturn(tmpStatus);
        when(tmpStatus.selectByExample(any(IvbMStatusExample.class))).thenReturn(statusResult);
        
        Bnn0061TraceabilityMasterMapper tmp0061 = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
        when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp0061);
        
        // process Data
        List<IvbMProcess> listProcess = new ArrayList<IvbMProcess>();
        when(tmp0061.getProcessList(any(HashMap.class))).thenReturn(listProcess);
        
    	// task Data
        List<IvbMTask> listTask = new ArrayList<IvbMTask>();
        when(tmp0061.getTaskList(any(HashMap.class))).thenReturn(listTask);
        
    	// task Data
        List<IvbMStatus> listStatus = new ArrayList<IvbMStatus>();
        when(tmp0061.getStatusList(any(HashMap.class))).thenReturn(listStatus);        
        // start testing
		service.initData(model, bnn0075result);
	}

	@Test
	public void testInitDataExceptionIn() throws SQLException {

		ExtendedModelMap model = new ExtendedModelMap();

        service.setAppContext(appContext);

		// Mock setup
		IvbMStatusMapper tmp = Mockito.mock(IvbMStatusMapper.class);
    	when(dao.getIvbMStatusMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).selectByExample(any(IvbMStatusExample.class));

        // start testing
        service.initData(model, null);
	}

	@Test
	public void testInitDataExceptionOut() throws SQLException {

		ExtendedModelMap model = new ExtendedModelMap();

		// Mock setup
		IvbMStatusMapper tmp = Mockito.mock(IvbMStatusMapper.class);
    	when(dao.getIvbMStatusMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).selectByExample(any(IvbMStatusExample.class));

        // start testing
        service.initData(model, null);
	}

	@Test
	public void testGetAreaData() throws SQLException {
		String farmId = "F001";
		List<UtilComponent> result = new ArrayList<UtilComponent>();
		result.add(new UtilComponent());

		// Mock setup
        when(util.getAreaDataByFarmId(any(UtilDao.class), any(String.class))).thenReturn(result);

        service.setAppContext(appContext);

        // get data result list
        List<UtilComponent> resultList = service.getAreaData(farmId);
        // start testing
        Assert.assertEquals(1, resultList.size());
	}

	@Test
	public void testGetAreaDataException() throws SQLException {

		// Mock setup
        doThrow(new RuntimeException()).when(util).getAreaDataByFarmId(any(UtilDao.class), any(String.class));

        // get data result list
        List<UtilComponent> resultList = service.getAreaData(null);
        // start testing
        Assert.assertEquals(new ArrayList<UtilComponent>(), resultList);
	}

	@Test
	public void testGetBlockData() throws SQLException {
		
		IvbMBlock object = new IvbMBlock();
		object.setFarmId("F001");
		object.setAreaId("A001");
		List<IvbMBlock> result = new ArrayList<IvbMBlock>();
		result.add(new IvbMBlock());

        service.setAppContext(appContext);

		// Mock setup
        IvbMBlockMapper tmp = Mockito.mock(IvbMBlockMapper.class);
    	when(dao.getIvbMBlockMapper()).thenReturn(tmp);
        when(tmp.selectByExample(any(IvbMBlockExample.class))).thenReturn(result);

        // get data result list
        List<IvbMBlock> resultList = service.getBlockData(object);
        // start testing
        Assert.assertEquals(1, resultList.size());
	}

	@Test
	public void testGetBlockDataException() throws SQLException {

		// Mock setup
		IvbMBlockMapper tmp = Mockito.mock(IvbMBlockMapper.class);
    	when(dao.getIvbMBlockMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).selectByExample(any(IvbMBlockExample.class));

        // get data result list
        List<IvbMBlock> resultList = service.getBlockData(null);
        // start testing
        Assert.assertEquals(new ArrayList<IvbMBlock>(), resultList);
	}

	@Test
	public void testGetLineData() throws SQLException {
		IvbTProduct object = new IvbTProduct();
		object.setFarmId("F001");
		object.setAreaId("A001");
		List<String> result = new ArrayList<String>();
		result.add(new String());

        service.setAppContext(appContext);

		// Mock setup
		Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
    	when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
        when(tmp.getLineIdList(any(IvbTProduct.class))).thenReturn(result);

        // get data result list
        List<String> resultList = service.getLineData(object);
        // start testing
        Assert.assertEquals(1, resultList.size());
	}

	@Test
	public void testGetLineDataException() throws SQLException {

		// Mock setup
		Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
    	when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getLineIdList(any(IvbTProduct.class));

        // get data result list
        List<String> resultList = service.getLineData(null);
        // start testing
        Assert.assertEquals(new ArrayList<String>(), resultList);
	}

	@Test
	public void testGetColumnData() throws SQLException {
		IvbTProduct object = new IvbTProduct();
		object.setFarmId("F001");
		object.setAreaId("A001");
		List<String> result = new ArrayList<String>();
		result.add(new String());

        service.setAppContext(appContext);

		// Mock setup
		Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
    	when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
        when(tmp.getColumnIdList(any(IvbTProduct.class))).thenReturn(result);

        // get data result list
        List<String> resultList = service.getColumnData(object);
        // start testing
        Assert.assertEquals(1, resultList.size());
	}

	@Test
	public void testGetColumnDataException() throws SQLException {

		// Mock setup
		Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
    	when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getColumnIdList(any(IvbTProduct.class));

        // get data result list
        List<String> resultList = service.getColumnData(null);
        // start testing
        Assert.assertEquals(new ArrayList<String>(), resultList);
	}

    @Test
    public void testSearchData() throws SQLException {
		Bnn0061TraceabilityMasterDataConditions searchConditions = new Bnn0061TraceabilityMasterDataConditions();
        // farm id
        searchConditions.setFarmId("");
        // from
        searchConditions.setFromRow(0);
        // item per page
        searchConditions.setItemCount(5);

        List<Bnn0061TraceabilityMasterDataObject> resultListReturn = new ArrayList<Bnn0061TraceabilityMasterDataObject>();
        resultListReturn.add(new Bnn0061TraceabilityMasterDataObject());

        service.setAppContext(appContext);

        // Mock setup
        Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
        when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
        when(tmp.searchData(any(Bnn0061TraceabilityMasterDataConditions.class))).thenReturn(resultListReturn);
        when(tmp.getSearchDataTotalCounts(any(Bnn0061TraceabilityMasterDataConditions.class))).thenReturn("1");

        // get search result
        List<Bnn0061TraceabilityMasterDataObject> resultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(1, resultList.size());
    }

    @Test
    public void testSearchDataNoResult() throws SQLException {
		Bnn0061TraceabilityMasterDataConditions searchConditions = new Bnn0061TraceabilityMasterDataConditions();
        // farm id
        searchConditions.setFarmId("");
        // from
        searchConditions.setFromRow(0);
        // item per page
        searchConditions.setItemCount(5);

        List<Bnn0061TraceabilityMasterDataObject> resultListReturn = new ArrayList<Bnn0061TraceabilityMasterDataObject>();

        service.setAppContext(appContext);

        // Mock setup
        Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
        when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
        when(tmp.searchData(any(Bnn0061TraceabilityMasterDataConditions.class))).thenReturn(resultListReturn);
        when(tmp.getSearchDataTotalCounts(any(Bnn0061TraceabilityMasterDataConditions.class))).thenReturn("0");

        // get search result
        List<Bnn0061TraceabilityMasterDataObject> resultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(0, resultList.size());
    }

    @Test
    public void testSearchDataOutOfMemoryException() throws SQLException {
		Bnn0061TraceabilityMasterDataConditions searchConditions = new Bnn0061TraceabilityMasterDataConditions();
        // farm id
        searchConditions.setFarmId(null);
        // from
        searchConditions.setFromRow(0);
        // item per page
        searchConditions.setItemCount(5);

        service.setAppContext(appContext);

        // Mock setup
        Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
        when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
        doThrow(new OutOfMemoryError()).when(tmp).searchData(any(Bnn0061TraceabilityMasterDataConditions.class));

        // get search result
        List<Bnn0061TraceabilityMasterDataObject> resultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals("-1", resultList.get(0).getSearchDataTotalCounts());
	}

    @Test
    public void testSearchDataExceptionIn() throws SQLException {
		Bnn0061TraceabilityMasterDataConditions searchConditions = null;

        service.setAppContext(appContext);

        // Mock setup
        Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
        when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(Bnn0061TraceabilityMasterDataConditions.class));

        // get search result
        List<Bnn0061TraceabilityMasterDataObject> resultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(null, resultList);
	}

    @Test
    public void testSearchDataExceptionOut() throws SQLException {
		Bnn0061TraceabilityMasterDataConditions searchConditions = new Bnn0061TraceabilityMasterDataConditions();
        // farm id
        searchConditions.setFarmId(null);
        // from
        searchConditions.setFromRow(0);
        // item per page
        searchConditions.setItemCount(5);

        // Mock setup
        Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
        when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(Bnn0061TraceabilityMasterDataConditions.class));

        // get search result
        List<Bnn0061TraceabilityMasterDataObject> resultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(null, resultList);
	}

    @Test
    public void testGetSingleData() throws SQLException {
    	Bnn0061TraceabilityMasterDataConditions condition = new Bnn0061TraceabilityMasterDataConditions();
    	condition.setFarmId("F001");

    	Bnn0061TraceabilityMasterDataObject resultReturn = new Bnn0061TraceabilityMasterDataObject();
    	resultReturn.setFarmId("F001");

        service.setAppContext(appContext);

        // Mock setup
    	Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
        when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
        when(tmp.getSingleData(any(Bnn0061TraceabilityMasterDataConditions.class))).thenReturn(resultReturn);

        // get single data result
        Bnn0061TraceabilityMasterDataObject result = service.getSingleData(condition);
        // start testing
        Assert.assertEquals("F001", result.getFarmId());
    }

    @Test
    public void testGetSingleDataExceptionIn() throws SQLException {
    	Bnn0061TraceabilityMasterDataConditions condition = null;

    	Bnn0061TraceabilityMasterDataObject resultReturn = new Bnn0061TraceabilityMasterDataObject();
    	resultReturn.setFarmId("F001");

        service.setAppContext(appContext);

        // Mock setup
    	Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
        when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getSingleData(any(Bnn0061TraceabilityMasterDataConditions.class));

        // get single data result
        Bnn0061TraceabilityMasterDataObject result = service.getSingleData(condition);
        // start testing
        Assert.assertEquals(null, result);
    }

    @Test
    public void testGetSingleDataExceptionOut() throws SQLException {
    	Bnn0061TraceabilityMasterDataConditions condition = new Bnn0061TraceabilityMasterDataConditions();
    	condition.setFarmId("F001");

    	Bnn0061TraceabilityMasterDataObject resultReturn = new Bnn0061TraceabilityMasterDataObject();
    	resultReturn.setFarmId("F001");

        // Mock setup
    	Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
        when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getSingleData(any(Bnn0061TraceabilityMasterDataConditions.class));

        // get single data result
        Bnn0061TraceabilityMasterDataObject result = service.getSingleData(condition);
        // start testing
        Assert.assertEquals(null, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testgetStatusDataSuccess() {
    	List<IvbMStatus> statusData = new ArrayList<IvbMStatus>();
    	IvbMStatus ivbMStatus = new IvbMStatus();
    	ivbMStatus.setStatusName("S001");
    	statusData.add(ivbMStatus);

    	service.setAppContext(appContext);

    	Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
    	when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
    	when(tmp.getStatusList(any(HashMap.class))).thenReturn(statusData);
    	
    	List<IvbMStatus> result = service.getStatusData("F001");
    	
    	// start testing
        Assert.assertEquals(statusData, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testgetStatusDataError() {
    	List<IvbMStatus> statusData = new ArrayList<IvbMStatus>();
    	IvbMStatus ivbMStatus = new IvbMStatus();
    	ivbMStatus.setStatusName("S001");
    	statusData.add(ivbMStatus);

    	service.setAppContext(appContext);

    	Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
    	when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
    	when(tmp.getStatusList(any(HashMap.class))).thenReturn(null);
    	
    	List<IvbMStatus> result = service.getStatusData("F001");
    	
    	// start testing
        Assert.assertEquals(null, result);
    }


    @Test
    public void testgetStatusDataException() {
    	List<IvbMStatus> result = service.getStatusData("F001");
    	
    	// start testing
        Assert.assertEquals(0, result.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testgetTaskDataSuccess() {
    	List<IvbMTask> taskData = new ArrayList<IvbMTask>();
    	IvbMTask ivbMTask = new IvbMTask();
    	ivbMTask.setTaskName("T001");
    	ivbMTask.setWorkingDetails("demo");
    	ivbMTask.setNote("1");;
    	taskData.add(ivbMTask);

    	service.setAppContext(appContext);

    	Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
    	when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
    	when(tmp.getTaskList(any(HashMap.class))).thenReturn(taskData);
    	
    	List<IvbMTask> result = service.getTaskData("F001", "P001");
    	
    	// start testing
        Assert.assertEquals(taskData, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testgetTaskDataError() {
    	List<IvbMTask> taskData = new ArrayList<IvbMTask>();
    	IvbMTask ivbMTask = new IvbMTask();
    	ivbMTask.setTaskName("T001");
    	ivbMTask.setWorkingDetails("demo");
    	ivbMTask.setNote("1");;
    	taskData.add(ivbMTask);

    	service.setAppContext(appContext);

    	Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
    	when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
    	when(tmp.getTaskList(any(HashMap.class))).thenReturn(null);
    	
    	List<IvbMTask> result = service.getTaskData("F001", "P001");
    	
    	// start testing
        Assert.assertEquals(null, result);
    }


    @Test
    public void testgetTaskDataException() {
    	List<IvbMTask> result = service.getTaskData("F001", "P001");
    	
    	// start testing
        Assert.assertEquals(0, result.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testgetProcessDataSuccess() {
    	List<IvbMProcess> processData = new ArrayList<IvbMProcess>();
    	IvbMProcess ivbMProcess = new IvbMProcess();
    	ivbMProcess.setProcessName("P001");
    	processData.add(ivbMProcess);

    	service.setAppContext(appContext);

    	Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
    	when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
    	when(tmp.getProcessList(any(HashMap.class))).thenReturn(processData);
    	
    	List<IvbMProcess> result = service.getProcessData("F001");
    	
    	// start testing
        Assert.assertEquals(processData, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testgetProcessDataError() {
    	List<IvbMProcess> processData = new ArrayList<IvbMProcess>();
    	IvbMProcess ivbMProcess = new IvbMProcess();
    	ivbMProcess.setProcessName("P001");
    	processData.add(ivbMProcess);

    	service.setAppContext(appContext);

    	Bnn0061TraceabilityMasterMapper tmp = Mockito.mock(Bnn0061TraceabilityMasterMapper.class);
    	when(dao.getBnn0061TraceabilityMasterMapper()).thenReturn(tmp);
    	when(tmp.getProcessList(any(HashMap.class))).thenReturn(null);
    	
    	List<IvbMProcess> result = service.getProcessData("F001");
    	
    	// start testing
        Assert.assertEquals(null, result);
    }

    @Test
    public void testgetProcessDataException() {
    	List<IvbMProcess> result = service.getProcessData("F001");
    	
    	// start testing
        Assert.assertEquals(0, result.size());
    }
}