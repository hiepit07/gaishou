package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.ui.ExtendedModelMap;

import vn.bananavietnam.ict.banana.component.Bnn0091CultivationMasterDataObject;
import vn.bananavietnam.ict.banana.dao.Bnn0091CultivationMasterDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0091CultivationMasterMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.mapper.IvbMCultivationMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMProcessMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMTaskMapper;
import vn.bananavietnam.ict.common.db.model.IvbMCultivation;
import vn.bananavietnam.ict.common.db.model.IvbMCultivationExample;
import vn.bananavietnam.ict.common.db.model.IvbMFarm;
import vn.bananavietnam.ict.common.db.model.IvbMFarmExample;
import vn.bananavietnam.ict.common.db.model.IvbMKind;
import vn.bananavietnam.ict.common.db.model.IvbMKindExample;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.db.model.IvbMProcessExample;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.db.model.IvbMTaskExample;
import vn.bananavietnam.ict.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0091CultivationMasterServiceTest {

	@Autowired
    private ApplicationContext appContext;
	
	@InjectMocks
	private Bnn0091CultivationMasterService service;

	@Mock
	Bnn0091CultivationMasterDao dao;

	@Mock
	Util util;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0091");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        when(util.getUserInfo()).thenReturn(u);
	}

	@Test
	public void testInitData() throws SQLException {
		ExtendedModelMap model = new ExtendedModelMap();
		List<IvbMFarm> farmResult = new ArrayList<IvbMFarm>();
		List<IvbMKind> kindResult = new ArrayList<IvbMKind>();
		List<IvbMProcess> processResult = new ArrayList<IvbMProcess>();
		List<IvbMTask> taskResult = new ArrayList<IvbMTask>();
		farmResult.add(new IvbMFarm());
		kindResult.add(new IvbMKind());
		processResult.add(new IvbMProcess());
		taskResult.add(new IvbMTask());

    	service.setAppContext(appContext);

		// Mock setup
		IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
    	when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
        when(tmpFarm.selectByExample(any(IvbMFarmExample.class))).thenReturn(farmResult);

        IvbMKindMapper tmpKind = Mockito.mock(IvbMKindMapper.class);
    	when(dao.getIvbMKindMapper()).thenReturn(tmpKind);
        when(tmpKind.selectByExample(any(IvbMKindExample.class))).thenReturn(kindResult);

        IvbMProcessMapper tmpProcess = Mockito.mock(IvbMProcessMapper.class);
    	when(dao.getIvbMProcessMapper()).thenReturn(tmpProcess);
        when(tmpProcess.selectByExample(any(IvbMProcessExample.class))).thenReturn(processResult);

        IvbMTaskMapper tmpTask = Mockito.mock(IvbMTaskMapper.class);
    	when(dao.getIvbMTaskMapper()).thenReturn(tmpTask);
        when(tmpTask.selectByExample(any(IvbMTaskExample.class))).thenReturn(taskResult);

        // start testing
		service.initData(model);
	}

	@Test
	public void testInitDataExceptionIn() throws SQLException {
		ExtendedModelMap model = new ExtendedModelMap();

    	service.setAppContext(appContext);

		// Mock setup
		IvbMTaskMapper tmp = Mockito.mock(IvbMTaskMapper.class);
    	when(dao.getIvbMTaskMapper()).thenReturn(tmp);
    	when(tmp.selectByExample(any(IvbMTaskExample.class))).thenReturn(null);
        doThrow(new RuntimeException()).when(tmp).selectByExample(any(IvbMTaskExample.class));

        // start testing
		service.initData(model);
	}

	@Test
	public void testInitDataExceptionOut() throws SQLException {
		ExtendedModelMap model = new ExtendedModelMap();

		// Mock setup
		IvbMTaskMapper tmp = Mockito.mock(IvbMTaskMapper.class);
    	when(dao.getIvbMTaskMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).selectByExample(any(IvbMTaskExample.class));

        // start testing
		service.initData(model);
	}
	
    @Test
    public void testGetSingleData() {
		// task id
        String taskId = "T001";

        IvbMTask bnn0091ObjectReturn = new IvbMTask();
        bnn0091ObjectReturn.setTaskId("T001");

    	service.setAppContext(appContext);

        // Mock setup
        IvbMTaskMapper tmp = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmp);
        when(tmp.selectByPrimaryKey(any(String.class))).thenReturn(bnn0091ObjectReturn);

        // get single data result
        IvbMTask bnn0091Object = service.getSingleData(taskId);
        // start testing
        Assert.assertEquals("T001", bnn0091Object.getTaskId());
    }
	
    @Test
    public void testGetSingleDataExceptionIn() {
		// task id
        String taskId = null;

        IvbMTask bnn0091ObjectReturn = new IvbMTask();
        bnn0091ObjectReturn.setTaskId("T001");

    	service.setAppContext(appContext);

        // Mock setup
        IvbMTaskMapper tmp = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).selectByPrimaryKey(any(String.class));

        // get single data result
        IvbMTask bnn0091Object = service.getSingleData(taskId);
        // start testing
        Assert.assertEquals(null, bnn0091Object);
    }
	
    @Test
    public void testGetSingleDataExceptionOut() {
		// task id
        String taskId = "T001";

        IvbMTask bnn0091ObjectReturn = new IvbMTask();
        bnn0091ObjectReturn.setTaskId("T001");
        // Mock setup
        IvbMTaskMapper tmp = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).selectByPrimaryKey(any(String.class));

        // get single data result
        IvbMTask bnn0091Object = service.getSingleData(taskId);
        // start testing
        Assert.assertEquals(null, bnn0091Object);
    }
    
    @Test
    public void testgetUnregisteredProcessDataTotal() {
    	IvbMCultivation cultivationData = new IvbMCultivation();

    	service.setAppContext(appContext);

    	Bnn0091CultivationMasterMapper tmp = Mockito.mock(Bnn0091CultivationMasterMapper.class);
        when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp);
        when(tmp.getUnregisteredProcessTotal(any(IvbMCultivation.class))).thenReturn("1");

        // get single data result
        String bnn0091Object = service.getUnregisteredProcessDataTotal(cultivationData);
        // start testing
        Assert.assertEquals("1", bnn0091Object);
    }
    
    @Test
    public void testgetUnregisteredProcessDataTotalExceptionIn() {
    	IvbMCultivation cultivationData = null;

    	service.setAppContext(appContext);

    	Bnn0091CultivationMasterMapper tmp = Mockito.mock(Bnn0091CultivationMasterMapper.class);
        when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getUnregisteredProcessTotal(any(IvbMCultivation.class));

        // get single data result
        String bnn0091Object = service.getUnregisteredProcessDataTotal(cultivationData);
        // start testing
        Assert.assertEquals(null, bnn0091Object);
    }
    
    @Test
    public void testgetUnregisteredProcessDataTotalExceptionOut() {
    	IvbMCultivation cultivationData = new IvbMCultivation();

    	Bnn0091CultivationMasterMapper tmp = Mockito.mock(Bnn0091CultivationMasterMapper.class);
        when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getUnregisteredProcessTotal(any(IvbMCultivation.class));

        // get single data result
        String bnn0091Object = service.getUnregisteredProcessDataTotal(cultivationData);
        // start testing
        Assert.assertEquals(null, bnn0091Object);
    }
    
    @SuppressWarnings("rawtypes")
	@Test
    public void testGetTaskData() throws SQLException {
    	IvbMCultivation cultivationData = new IvbMCultivation();

		List<IvbMTask> unreList = new ArrayList<IvbMTask>();
		IvbMTask task1 = new IvbMTask();
		IvbMTask task2 = new IvbMTask();
		unreList.add(task1);
		unreList.add(task2);
		List<Bnn0091CultivationMasterDataObject> regiList = new ArrayList<Bnn0091CultivationMasterDataObject>();
		Bnn0091CultivationMasterDataObject temp = new Bnn0091CultivationMasterDataObject();
		regiList.add(temp);

    	service.setAppContext(appContext);

    	Bnn0091CultivationMasterMapper tmp = Mockito.mock(Bnn0091CultivationMasterMapper.class);
        when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp);
        when(tmp.getUnregisteredTask(any(IvbMCultivation.class))).thenReturn(unreList);
        when(tmp.getRegisteredTask(any(IvbMCultivation.class))).thenReturn(regiList);

        // get single data result
        List<List> bnn0091Object = service.getTaskData(cultivationData);
        // start testing
        Assert.assertEquals(2, bnn0091Object.size());
        Assert.assertEquals(2, bnn0091Object.get(0).size());
        Assert.assertEquals(1, bnn0091Object.get(1).size());
    }
    
    @SuppressWarnings("rawtypes")
	@Test
    public void testGetTaskDataExceptionIn() throws SQLException {
    	IvbMCultivation cultivationData = null;

    	service.setAppContext(appContext);
		   	
    	Bnn0091CultivationMasterMapper tmp = Mockito.mock(Bnn0091CultivationMasterMapper.class);
        when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getUnregisteredTask(any(IvbMCultivation.class));

        // get single data result
        List<List> bnn0091Object = service.getTaskData(cultivationData);
        // start testing
        Assert.assertEquals(0, bnn0091Object.size());
    }
    
    @SuppressWarnings("rawtypes")
	@Test
    public void testGetTaskDataExceptionOut() throws SQLException {
    	IvbMCultivation cultivationData = new IvbMCultivation();
		   	
    	Bnn0091CultivationMasterMapper tmp = Mockito.mock(Bnn0091CultivationMasterMapper.class);
        when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getUnregisteredTask(any(IvbMCultivation.class));

        // get single data result
        List<List> bnn0091Object = service.getTaskData(cultivationData);
        // start testing
        Assert.assertEquals(0, bnn0091Object.size());
    }
    
    @Test
    public void testInsertDataRollbackWhenOnlyInsert() throws SQLException, ParseException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("1");
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	bnn0091Object.setLastUpdateDate(date);
    	cultivationData.add(bnn0091Object);

    	service.setAppContext(appContext);

    	Bnn0091CultivationMasterDataObject bnn0091CultivationMasterDataObject = new Bnn0091CultivationMasterDataObject();
    	bnn0091CultivationMasterDataObject.setLastUpdateDate(date);
    	Bnn0091CultivationMasterMapper tmp1 = Mockito.mock(Bnn0091CultivationMasterMapper.class);
    	when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp1);
    	when(tmp1.getLastUpdateDate(any(Bnn0091CultivationMasterDataObject.class))).thenReturn(date);
    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.insert(any(IvbMCultivation.class))).thenReturn(1);
        // insert data result
        String result = service.insertData(cultivationData);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_TASK_RESULT, result);
    }
    
    @Test
    public void testInsertDataFailedUpdateDateWhenOnlyInsert() throws SQLException, ParseException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("1");
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	bnn0091Object.setLastUpdateDate(date);
    	cultivationData.add(bnn0091Object);

    	service.setAppContext(appContext);

    	Bnn0091CultivationMasterDataObject bnn0091CultivationMasterDataObject = new Bnn0091CultivationMasterDataObject();
    	bnn0091CultivationMasterDataObject.setLastUpdateDate(date);
    	Bnn0091CultivationMasterMapper tmp1 = Mockito.mock(Bnn0091CultivationMasterMapper.class);
    	when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp1);
    	when(tmp1.getLastUpdateDate(any(Bnn0091CultivationMasterDataObject.class))).thenReturn(format.parse("2017/05/18 13:25:25"));
    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.insert(any(IvbMCultivation.class))).thenReturn(1);
        // insert data result
        String result = service.insertData(cultivationData);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_TASK_LAST_UPDATE_DATE_RESULT, result);
    }
    
    @Test
    public void testInsertDataWhenOnlyInsert() throws SQLException, ParseException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F001");
    	bnn0091Object.setKindId("K001");
    	bnn0091Object.setProcessId("P001");
    	bnn0091Object.setTaskId("1");
    	bnn0091Object.setBlockWorkFlag(false);
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	bnn0091Object.setLastUpdateDate(date);
    	cultivationData.add(bnn0091Object);

    	Bnn0091CultivationMasterDataObject bnn0091Object1 = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object1.setFarmId("F001");
    	bnn0091Object1.setKindId("K001");
    	bnn0091Object1.setProcessId("P001");
    	bnn0091Object1.setTaskId("1");
    	bnn0091Object1.setBlockWorkFlag(false);
    	bnn0091Object1.setLastUpdateDate(date);
    	cultivationData.add(bnn0091Object1);
    	
    	Bnn0091CultivationMasterDataObject bnn0091Object2 = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object2.setFarmId("F001");
    	bnn0091Object2.setKindId("K001");
    	bnn0091Object2.setProcessId("P001");
    	bnn0091Object2.setTaskId("1"); 
    	bnn0091Object2.setBlockWorkFlag(false);
    	bnn0091Object2.setLastUpdateDate(date);
    	cultivationData.add(bnn0091Object2);
    	service.setAppContext(appContext);

    	Bnn0091CultivationMasterDataObject bnn0091CultivationMasterDataObject = new Bnn0091CultivationMasterDataObject();
    	bnn0091CultivationMasterDataObject.setLastUpdateDate(date);
    	Bnn0091CultivationMasterMapper tmp1 = Mockito.mock(Bnn0091CultivationMasterMapper.class);
    	when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp1);
    	when(tmp1.getLastUpdateDate(any(Bnn0091CultivationMasterDataObject.class))).thenReturn(format.parse("2017/05/18 13:25:25"));
    	
    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.insert(any(IvbMCultivation.class))).thenReturn(0);
        
        when(tmp.deleteByExample(any(IvbMCultivationExample.class))).thenReturn(1);
        // insert data result
        String result = service.insertData(cultivationData);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_TASK_RESULT, result);
    }
    
    @Test
    public void testInsertDataWhenOnlyInsert10() throws SQLException, ParseException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F001"); 
    	bnn0091Object.setKindId("K001");
    	bnn0091Object.setProcessId("P001");
    	bnn0091Object.setTaskId("2");
    	bnn0091Object.setBlockWorkFlag(false);
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	bnn0091Object.setLastUpdateDate(date);
    	cultivationData.add(bnn0091Object);

    	Bnn0091CultivationMasterDataObject bnn0091Object1 = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object1.setFarmId("F001");
    	bnn0091Object1.setKindId("K001");
    	bnn0091Object1.setProcessId("P001");
    	bnn0091Object1.setTaskId("1");
    	bnn0091Object1.setBlockWorkFlag(false);
    	bnn0091Object1.setLastUpdateDate(date);
    	cultivationData.add(bnn0091Object1);
    	
    	Bnn0091CultivationMasterDataObject bnn0091Object2 = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object2.setFarmId("F001");
    	bnn0091Object2.setKindId("K001");
    	bnn0091Object2.setProcessId("P001");
    	bnn0091Object2.setTaskId("1"); 
    	bnn0091Object2.setBlockWorkFlag(false);
    	bnn0091Object2.setLastUpdateDate(date);
    	cultivationData.add(bnn0091Object2);
    	service.setAppContext(appContext);

    	Bnn0091CultivationMasterDataObject bnn0091CultivationMasterDataObject = new Bnn0091CultivationMasterDataObject();
    	bnn0091CultivationMasterDataObject.setLastUpdateDate(date);
    	Bnn0091CultivationMasterMapper tmp1 = Mockito.mock(Bnn0091CultivationMasterMapper.class);
    	when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp1);
    	when(tmp1.getLastUpdateDate(any(Bnn0091CultivationMasterDataObject.class))).thenReturn(format.parse("2017/05/18 13:25:25"));
    	
    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.insert(any(IvbMCultivation.class))).thenReturn(1);
        
        when(tmp.deleteByExample(any(IvbMCultivationExample.class))).thenReturn(1);
        // insert data result
        String result = service.insertData(cultivationData);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
    }
    
    @Test
    public void testInsertDataWhenOnlyInsert7() throws SQLException, ParseException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F001");
    	bnn0091Object.setKindId("K001");
    	bnn0091Object.setProcessId("P001");
    	bnn0091Object.setTaskId("1");
    	bnn0091Object.setBlockWorkFlag(false);
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	bnn0091Object.setLastUpdateDate(date);
    	cultivationData.add(bnn0091Object);
    	service.setAppContext(appContext);

    	Bnn0091CultivationMasterDataObject bnn0091CultivationMasterDataObject = new Bnn0091CultivationMasterDataObject();
    	bnn0091CultivationMasterDataObject.setLastUpdateDate(date);
    	Bnn0091CultivationMasterMapper tmp1 = Mockito.mock(Bnn0091CultivationMasterMapper.class);
    	when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp1);
    	when(tmp1.getLastUpdateDate(any(Bnn0091CultivationMasterDataObject.class))).thenReturn(format.parse("2017/05/18 13:25:25"));
    	
    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.insert(any(IvbMCultivation.class))).thenReturn(1);
        when(tmp.insert(any(IvbMCultivation.class))).thenReturn(0);
        
        when(tmp.deleteByExample(any(IvbMCultivationExample.class))).thenReturn(1);
        // insert data result
        String result = service.insertData(cultivationData);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
    } 

    @Test
    public void testInsertDataWhenOnlyInsert1() throws SQLException, ParseException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F001");
    	bnn0091Object.setKindId("K001");
    	bnn0091Object.setProcessId("P001");
    	bnn0091Object.setTaskId("1");
    	bnn0091Object.setBlockWorkFlag(false);
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	bnn0091Object.setLastUpdateDate(date);
    	cultivationData.add(bnn0091Object);

    	Bnn0091CultivationMasterDataObject bnn0091Object1 = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object1.setFarmId("F001");
    	bnn0091Object1.setKindId("K001");
    	bnn0091Object1.setProcessId("P001");
    	bnn0091Object1.setTaskId("1");
    	bnn0091Object1.setBlockWorkFlag(true);
    	bnn0091Object1.setLastUpdateDate(date);
    	cultivationData.add(bnn0091Object1);
    	
    	Bnn0091CultivationMasterDataObject bnn0091Object2 = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object2.setFarmId("F001");
    	bnn0091Object2.setKindId("K001");
    	bnn0091Object2.setProcessId("P001");
    	bnn0091Object2.setTaskId("1");
    	bnn0091Object2.setBlockWorkFlag(false);
    	bnn0091Object2.setLastUpdateDate(date);
    	cultivationData.add(bnn0091Object2);
    	service.setAppContext(appContext);

    	Bnn0091CultivationMasterDataObject bnn0091CultivationMasterDataObject = new Bnn0091CultivationMasterDataObject();
    	bnn0091CultivationMasterDataObject.setLastUpdateDate(date);
    	Bnn0091CultivationMasterMapper tmp1 = Mockito.mock(Bnn0091CultivationMasterMapper.class);
    	when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp1);
    	when(tmp1.getLastUpdateDate(any(Bnn0091CultivationMasterDataObject.class))).thenReturn(format.parse("2017/05/18 13:25:25"));
    	
    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.insert(any(IvbMCultivation.class))).thenReturn(1);
        
        when(tmp.deleteByExample(any(IvbMCultivationExample.class))).thenReturn(1);
        // insert data result
        String result = service.insertData(cultivationData);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
    }

    @Test
    public void testInsertDataWhenOnlyInsert11() throws SQLException, ParseException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F001");
    	bnn0091Object.setKindId("K001");
    	bnn0091Object.setProcessId("P001");
    	bnn0091Object.setTaskId("1");
    	bnn0091Object.setBlockWorkFlag(false);
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	bnn0091Object.setLastUpdateDate(null);
    	cultivationData.add(bnn0091Object);
    	service.setAppContext(appContext);

    	Bnn0091CultivationMasterDataObject bnn0091CultivationMasterDataObject = new Bnn0091CultivationMasterDataObject();
    	bnn0091CultivationMasterDataObject.setLastUpdateDate(date);
    	Bnn0091CultivationMasterMapper tmp1 = Mockito.mock(Bnn0091CultivationMasterMapper.class);
    	when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp1);
    	when(tmp1.getLastUpdateDate(any(Bnn0091CultivationMasterDataObject.class))).thenReturn(null);
    	
    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.insert(any(IvbMCultivation.class))).thenReturn(1);
        
        when(tmp.deleteByExample(any(IvbMCultivationExample.class))).thenReturn(0);
        // insert data result
        String result = service.insertData(cultivationData);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
	public void testInsertDataWhenOnlyInsert2() throws SQLException, ParseException {
		List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
		Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
		bnn0091Object.setFarmId("F001");
		bnn0091Object.setKindId("K001");
		bnn0091Object.setProcessId("P001");
		bnn0091Object.setTaskId("1");
		bnn0091Object.setBlockWorkFlag(false);
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		bnn0091Object.setLastUpdateDate(date);
		cultivationData.add(bnn0091Object);

		Bnn0091CultivationMasterDataObject bnn0091Object1 = new Bnn0091CultivationMasterDataObject();
		bnn0091Object1.setFarmId("F001");
		bnn0091Object1.setKindId("K001");
		bnn0091Object1.setProcessId("P001");
		bnn0091Object1.setTaskId("1");
		bnn0091Object1.setBlockWorkFlag(true);
		bnn0091Object1.setLastUpdateDate(date);
		cultivationData.add(bnn0091Object1);

		Bnn0091CultivationMasterDataObject bnn0091Object2 = new Bnn0091CultivationMasterDataObject();
		bnn0091Object2.setFarmId("F001");
		bnn0091Object2.setKindId("K001");
		bnn0091Object2.setProcessId("P001");
		bnn0091Object2.setTaskId("1");
		bnn0091Object2.setBlockWorkFlag(false);
		bnn0091Object2.setLastUpdateDate(date);
		cultivationData.add(bnn0091Object2);
		service.setAppContext(appContext);

		Bnn0091CultivationMasterDataObject bnn0091CultivationMasterDataObject = new Bnn0091CultivationMasterDataObject();
		bnn0091CultivationMasterDataObject.setLastUpdateDate(date);
		Bnn0091CultivationMasterMapper tmp1 = Mockito.mock(Bnn0091CultivationMasterMapper.class);
		when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp1);
		when(tmp1.getLastUpdateDate(any(Bnn0091CultivationMasterDataObject.class)))
				.thenReturn(format.parse("2017/05/18 13:25:25"));

		IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
		when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
		when(tmp.deleteByExample(any(IvbMCultivationExample.class))).thenReturn(0);
		// insert data result
		String result = service.insertData(cultivationData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_TASK_LAST_UPDATE_DATE_RESULT, result);
	}
    @Test
    public void testInsertDataSuccessWhenOnlyDeleteRollBack() throws SQLException, ParseException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("0");
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		bnn0091Object.setLastUpdateDate(date);
    	cultivationData.add(bnn0091Object);

    	Bnn0091CultivationMasterDataObject bnn0091Object2 = new Bnn0091CultivationMasterDataObject();
		bnn0091Object2.setFarmId("F001");
		bnn0091Object2.setKindId("K001");
		bnn0091Object2.setProcessId("P001");
		bnn0091Object2.setTaskId("1");
		bnn0091Object2.setBlockWorkFlag(false);
		bnn0091Object2.setLastUpdateDate(date);
		cultivationData.add(bnn0091Object2);
    	service.setAppContext(appContext);

    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.deleteByExample(any(IvbMCultivationExample.class))).thenReturn(1);
        Bnn0091CultivationMasterMapper tmp0091 = Mockito.mock(Bnn0091CultivationMasterMapper.class);
        when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp0091);
        when(tmp0091.getLastUpdateDate(any(Bnn0091CultivationMasterDataObject.class))).thenReturn(date);

        doThrow(new DuplicateKeyException("")).when(tmp).insert(any(IvbMCultivation.class));
        // insert data result
        String result = service.insertData(cultivationData);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }
    
    @Test
    public void testInsertDataSuccessWhenOnlyDeleteUpdateDate() throws SQLException, ParseException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("0");
    	String string = "2017/05/18 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		bnn0091Object.setLastUpdateDate(date);
    	cultivationData.add(bnn0091Object);

    	Bnn0091CultivationMasterDataObject bnn0091Object2 = new Bnn0091CultivationMasterDataObject();
		bnn0091Object2.setFarmId("F001");
		bnn0091Object2.setKindId("K001");
		bnn0091Object2.setProcessId("P001");
		bnn0091Object2.setTaskId("1");
		bnn0091Object2.setBlockWorkFlag(false);
		bnn0091Object2.setLastUpdateDate(date);
		cultivationData.add(bnn0091Object2);
    	service.setAppContext(appContext);

    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.deleteByExample(any(IvbMCultivationExample.class))).thenReturn(1);
        Bnn0091CultivationMasterMapper tmp0091 = Mockito.mock(Bnn0091CultivationMasterMapper.class);
        when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp0091);
        when(tmp0091.getLastUpdateDate(any(Bnn0091CultivationMasterDataObject.class))).thenReturn(format.parse("2017/05/17 13:25:25"));

        doThrow(new DuplicateKeyException("")).when(tmp).insert(any(IvbMCultivation.class));
        // insert data result
        String result = service.insertData(cultivationData);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_TASK_LAST_UPDATE_DATE_RESULT, result);
    }
    
    @Test
    public void testInsertDataSuccessWhenDeleteAndInsert() throws SQLException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("1");
    	cultivationData.add(bnn0091Object);

    	bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("sda");
    	bnn0091Object.setBlockWorkFlag(true);
    	cultivationData.add(bnn0091Object);
    	bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("sddsda");
    	bnn0091Object.setBlockWorkFlag(false);
    	cultivationData.add(bnn0091Object);

    	service.setAppContext(appContext);

    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.deleteByExample(any(IvbMCultivationExample.class))).thenReturn(1);
        when(tmp.insert(any(IvbMCultivation.class))).thenReturn(1);
        // insert data result
        String result = service.insertData(cultivationData);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    public void testInsertDataFailWhenDelete() throws SQLException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("1");
    	cultivationData.add(bnn0091Object);

    	service.setAppContext(appContext);

    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.deleteByExample(any(IvbMCultivationExample.class))).thenReturn(0);
        // insert data result
        String result = service.insertData(cultivationData);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    public void testInsertDataFailWhenDeleteAndInsert() throws SQLException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("1");
    	cultivationData.add(bnn0091Object);

    	bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("sda");
    	bnn0091Object.setBlockWorkFlag(true);
    	cultivationData.add(bnn0091Object);
    	bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("sddsda");
    	bnn0091Object.setBlockWorkFlag(false);
    	cultivationData.add(bnn0091Object);

    	service.setAppContext(appContext);
    	
    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.deleteByExample(any(IvbMCultivationExample.class))).thenReturn(1);
        when(tmp.insert(any(IvbMCultivation.class))).thenReturn(0);
        // insert data result
        String result = service.insertData(cultivationData);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    public void testInsertDataFailWhenOnlyInsertWorkFlagTrue() throws SQLException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("0");
    	cultivationData.add(bnn0091Object);

    	bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("sda");
    	bnn0091Object.setBlockWorkFlag(true);
    	cultivationData.add(bnn0091Object);

    	service.setAppContext(appContext);
    	
    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.insert(any(IvbMCultivation.class))).thenReturn(0);
        // insert data result
        String result = service.insertData(cultivationData);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_TASK_RESULT, result);
    }

    @Test
    public void testInsertDataFailWhenOnlyInsertWorkFlagFalse() throws SQLException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("0");
    	cultivationData.add(bnn0091Object);

    	bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("sda");
    	bnn0091Object.setBlockWorkFlag(false);
    	cultivationData.add(bnn0091Object);

    	service.setAppContext(appContext);
    	
    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.insert(any(IvbMCultivation.class))).thenReturn(0);
        // insert data result
        String result = service.insertData(cultivationData);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_TASK_RESULT, result);
    }

    @Test
    public void testInsertDataInnerException() throws SQLException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	cultivationData.add(bnn0091Object);

    	bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("sda");
    	bnn0091Object.setBlockWorkFlag(true);
    	cultivationData.add(bnn0091Object);
    	bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("sddsda");
    	bnn0091Object.setBlockWorkFlag(false);
    	cultivationData.add(bnn0091Object);
    	
    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.deleteByExample(any(IvbMCultivationExample.class))).thenReturn(0);
        doThrow(new RuntimeException()).when(tmp).insert(any(IvbMCultivation.class));
        
        // insert data result
        String result = service.insertData(cultivationData);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }
    
    @Test
    public void testInsertDataOuterException() throws SQLException {
        List<Bnn0091CultivationMasterDataObject> cultivationData = new ArrayList<Bnn0091CultivationMasterDataObject>();
    	Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	cultivationData.add(bnn0091Object);

    	bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("sda");
    	bnn0091Object.setBlockWorkFlag(true);
    	cultivationData.add(bnn0091Object);
    	bnn0091Object = new Bnn0091CultivationMasterDataObject();
    	bnn0091Object.setFarmId("F0001");
    	bnn0091Object.setKindId("dsds");
    	bnn0091Object.setProcessId("dsds");
    	bnn0091Object.setTaskId("sddsda");
    	bnn0091Object.setBlockWorkFlag(false);
    	cultivationData.add(bnn0091Object);

    	service.setAppContext(appContext);
    	
    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.deleteByExample(any(IvbMCultivationExample.class))).thenReturn(0);
        when(tmp.insert(any(IvbMCultivation.class))).thenReturn(1);
        // insert data result
        String result = service.insertData(cultivationData);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }
}