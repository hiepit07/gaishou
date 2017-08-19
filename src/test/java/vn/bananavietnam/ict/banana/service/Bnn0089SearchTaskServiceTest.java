package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import vn.bananavietnam.ict.banana.component.Bnn0089SearchTaskConditions;
import vn.bananavietnam.ict.banana.component.Bnn0089SearchTaskResult;
import vn.bananavietnam.ict.banana.dao.Bnn0089SearchTaskDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0089SearchTaskMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.mapper.IvbMTaskMapper;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0089SearchTaskServiceTest {

    @Autowired
    private ApplicationContext appContext;

    @InjectMocks
    private Bnn0089SearchTaskService bnn0089SearchTaskService_Mock;

    @Mock
    Bnn0089SearchTaskDao dao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0089");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        Authentication authToken = new UsernamePasswordAuthenticationToken(u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchData() {
        Bnn0089SearchTaskConditions searchConditions = new Bnn0089SearchTaskConditions();
        // task Id
        searchConditions.setTaskId("T001");
        // task Name
        searchConditions.setTaskName("Task Name 001");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        List<Bnn0089SearchTaskResult> bnn0089SearchTaskResult = new ArrayList<Bnn0089SearchTaskResult>();
        bnn0089SearchTaskResult.add(new Bnn0089SearchTaskResult());
        // Mock setup
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0089SearchTaskResult);
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("1");
        // get search result
        List<Bnn0089SearchTaskResult> bnn0089ResultList = bnn0089SearchTaskService_Mock.searchData(searchConditions);
        // start testing
        Assert.assertEquals(1, bnn0089ResultList.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataNull() {
        Bnn0089SearchTaskConditions searchConditions = new Bnn0089SearchTaskConditions();
        // task Id
        searchConditions.setTaskId("");
        // task Name
        searchConditions.setTaskName("Task Name 001");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        // Mock setup
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));
        // get search result
        List<Bnn0089SearchTaskResult> bnn0089ResultList = bnn0089SearchTaskService_Mock.searchData(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0089ResultList);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataNoResult() {
        Bnn0089SearchTaskConditions searchConditions = new Bnn0089SearchTaskConditions();
        // task Id
        searchConditions.setTaskId("T001");
        // task Name
        searchConditions.setTaskName("Task Name 001");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        List<Bnn0089SearchTaskResult> bnn0089SearchTaskResult = new ArrayList<Bnn0089SearchTaskResult>();
        // Mock setup
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0089SearchTaskResult);
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
        // get search result
        List<Bnn0089SearchTaskResult> bnn0089ResultList = bnn0089SearchTaskService_Mock.searchData(searchConditions);
        // start testing
        Assert.assertEquals(0, bnn0089ResultList.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataOutOfMemoryException() {
        Bnn0089SearchTaskConditions searchConditions = new Bnn0089SearchTaskConditions();
        // task Id
        searchConditions.setTaskId("T005");
        // task Name
        searchConditions.setTaskName("");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");

        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        // mock setup
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);
        doThrow(new OutOfMemoryError()).when(tmp).searchData(any(HashMap.class));
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("-1");
        // get searchData
        List<Bnn0089SearchTaskResult> bnn0089ResultList = bnn0089SearchTaskService_Mock.searchData(searchConditions);
        // start testing
        Assert.assertEquals("-1", bnn0089ResultList.get(0).getSearchDataTotalCounts());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataException() {
        Bnn0089SearchTaskConditions searchConditions = new Bnn0089SearchTaskConditions();
        // task Id
        searchConditions.setTaskId("T005");
        // task Name
        searchConditions.setTaskName("");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        // mock setup
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));

        // get searchData
        List<Bnn0089SearchTaskResult> bnn0089ResultList = bnn0089SearchTaskService_Mock.searchData(searchConditions);
        // star testing
        Assert.assertEquals(null, bnn0089ResultList);
    }

    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testUpdateData() {
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("T1");
        // task Id
        taskData.setTaskId("T001");
        // Working Details
        taskData.setWorkingDetails("abv company");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("");
        // updateUserId
        taskData.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        taskData.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        taskData.setDeleteFlag(Constants.DELETE_FLAG_OFF);

        // Mock setup
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);
        when(dao.getBnn0089SearchTaskMapper().updateData(any(Map.class)))
        		.thenReturn(1);
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.updateData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_SUCCESSFUL, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testUpdateDataFail() {
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("T1");
        // task Id
        taskData.setTaskId("T001");
        // Working Details
        taskData.setWorkingDetails("abv company");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("");
        // updateUserId
        taskData.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        taskData.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        taskData.setDeleteFlag(Constants.DELETE_FLAG_OFF);

        // Mock setup
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);
        when(dao.getBnn0089SearchTaskMapper().updateData(any(Map.class)))
        		.thenReturn(0);
        
        IvbMTaskMapper tmp2 = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmp2);
        when(dao.getIvbMTaskMapper().selectByPrimaryKey(anyString()))
        .thenReturn(taskData);
        
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.updateData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_TASK, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testUpdateDataFail2() throws ParseException {
    	String date = "06-04-2007 07:05:00.999";
    	SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.S");
    	Date myDate = fmt.parse(date);

    	IvbMTask taskData2 = new IvbMTask();
    	taskData2.setLastUpdateDate(myDate);
    	
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("T1");
        // task Id
        taskData.setTaskId("T001");
        // Working Details
        taskData.setWorkingDetails("abv company");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("");
        // updateUserId
        taskData.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        taskData.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        taskData.setDeleteFlag(Constants.DELETE_FLAG_OFF);

        // Mock setup
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);
        when(dao.getBnn0089SearchTaskMapper().updateData(any(Map.class)))
        		.thenReturn(0);
        
        IvbMTaskMapper tmp2 = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmp2);
        when(dao.getIvbMTaskMapper().selectByPrimaryKey(anyString()))
        .thenReturn(taskData2);
        
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.updateData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_UPDATE_DATE, result);
    }
    
    @Test
    @Transactional
    public void testUpdateDataWithBlank() {
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("");
        // task Id
        taskData.setTaskId("");
        // Working Details
        taskData.setWorkingDetails("abv company");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("");
        // delete flag
        taskData.setDeleteFlag(true);
        // Mock setup
        IvbMTaskMapper tmp = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmp);
        when(dao.getIvbMTaskMapper().updateByPrimaryKeySelective(any(IvbMTask.class))).thenReturn(1);
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.updateData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }
    
    
    @Test
    @Transactional
    public void testUpdateDataRollback() {
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("T1");
        // task Id
        taskData.setTaskId("T001");
        // Working Details
        taskData.setWorkingDetails("abv company");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("");
        // delete flag
        taskData.setDeleteFlag(true);

        // mock setup
        IvbMTaskMapper tmp = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmp);
        when(tmp.updateByPrimaryKeySelective(taskData)).thenReturn(0);

        // update user id
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.updateData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testUpdateDataInnerException() {
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("T1");
        // task Id
        taskData.setTaskId("T001");
        // Working Details
        taskData.setWorkingDetails("abv company");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("");
        // delete flag
        taskData.setDeleteFlag(true);

        // mock setup
        when(dao.getIvbMTaskMapper()).thenReturn(null);
        // update user id
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.updateData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testUpdateDataOuterException() {
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("T1");
        // task Id
        taskData.setTaskId("T001");
        // Working Details
        taskData.setWorkingDetails("abv company");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("");
        // delete flag
        taskData.setDeleteFlag(true);

        // update user id
        String result = bnn0089SearchTaskService_Mock.updateData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testUpdateDataCheckTaskId() {
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("a5");
        // task Id
        taskData.setTaskId("a005");
        // Working Details
        taskData.setWorkingDetails("bui tan hung");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(false);
        // note
        taskData.setNote("buitanhung");
        // delete flag
        taskData.setDeleteFlag(true);

        // update user id
        String result = bnn0089SearchTaskService_Mock.updateData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testInsertDataRollback() {
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("T5");
        // task Id
        taskData.setTaskId("T005");
        // Working Details
        taskData.setWorkingDetails("bui tan hung");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(false);
        // note
        taskData.setNote("buitanhung");
        // delete flag
        taskData.setDeleteFlag(true);

        // mock setup
        IvbMTaskMapper tmp = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmp);
        when(tmp.insert(taskData)).thenReturn(0);

        // update user id
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.insertData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testInsertDataInnerException() {
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("T1");
        // task Id
        taskData.setTaskId("T001");
        // Working Details
        taskData.setWorkingDetails("abv company");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("");
        // delete flag
        taskData.setDeleteFlag(true);

        // mock setup
        when(dao.getIvbMTaskMapper()).thenReturn(null);
        // update user id
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.insertData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testInsertDataOuterException() {
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("T1");
        // task Id
        taskData.setTaskId("T001");
        // Working Details
        taskData.setWorkingDetails("abv company");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("");
        // delete flag
        taskData.setDeleteFlag(true);
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        // update user id
        String result = bnn0089SearchTaskService_Mock.insertData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testInsertDataSuccess() {
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("Task Name 001");
        // task Id
        taskData.setTaskId("T100");
        // Working Details
        taskData.setWorkingDetails("Working Details 001");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("Note 001");
        // delete flag
        taskData.setDeleteFlag(true);
        // update user
        // Mock setup
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);
        when(tmp.getLastTaskId()).thenReturn("T100");
        
        IvbMTaskMapper tmpTask = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmpTask);
        when(tmpTask.insert(any(IvbMTask.class))).thenReturn(1);
        
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.insertData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
    }
    
    @Test
    @Transactional
    public void testInsertDataSuccess1() {
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("Task Name 001");
        // task Id
        taskData.setTaskId("T100");
        // Working Details
        taskData.setWorkingDetails("Working Details 001");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("Note 001");
        // delete flag
        taskData.setDeleteFlag(true);
        // update user
        // Mock setup
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);
        when(tmp.getLastTaskId()).thenReturn(null);
        
        IvbMTaskMapper tmpTask = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmpTask);
        when(tmpTask.insert(any(IvbMTask.class))).thenReturn(1);
        
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.insertData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
    }
    
    @Test
    @Transactional
    public void testInsertDataFail() {
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("Task Name 001");
        // task Id
        taskData.setTaskId("T100");
        // Working Details
        taskData.setWorkingDetails("Working Details 001");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("Note 001");
        // delete flag
        taskData.setDeleteFlag(true);
        // update user
        // Mock setup
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);
        when(tmp.getLastTaskId()).thenReturn("T100");
        
        IvbMTaskMapper tmpTask = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmpTask);
        when(tmpTask.insert(any(IvbMTask.class))).thenReturn(0);
        
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.insertData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }
    
    @Test
    @Transactional
    public void testInsertDataWithBlank() {
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("");
        // task Id
        taskData.setTaskId("");
        // Working Details
        taskData.setWorkingDetails("Working Details 001");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("Note 001");
        // delete flag
        taskData.setDeleteFlag(true);
        // update user
        // Mock setup
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);
        when(tmp.getLastTaskId()).thenReturn("T100");
        
        IvbMTaskMapper tmpTask = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmpTask);
        when(tmpTask.insert(any(IvbMTask.class))).thenReturn(1);
        
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.insertData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }

    @Test
    @Transactional
    public void testInsertDataWithBlank1() {
        IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("Task");
        // task Id
        taskData.setTaskId("");
        // Working Details
        taskData.setWorkingDetails("");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("Note 001");
        // delete flag
        taskData.setDeleteFlag(true);
        // update user
        // Mock setup
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);
        when(tmp.getLastTaskId()).thenReturn("T100");
        
        IvbMTaskMapper tmpTask = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmpTask);
        when(tmpTask.insert(any(IvbMTask.class))).thenReturn(1);
        
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.insertData(taskData);
        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }


    @Test
    @Transactional
    public void testDeleteDataInnerException() {
    	IvbMTask ivbMTask = new IvbMTask();
        // Process Id
    	ivbMTask.setTaskId("T001");
        // Process Name
    	ivbMTask.setTaskName("Task");
        // update user id
    	ivbMTask.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
    	ivbMTask.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
    	ivbMTask.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        when(dao.getIvbMTaskMapper()).thenReturn(null);
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.deleteData(ivbMTask);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testDeleteDataOuterException() {
    	IvbMTask ivbMTask = new IvbMTask();
        // Process Id
    	ivbMTask.setTaskId("T001");
        // Process Name
    	ivbMTask.setTaskName("Task");
        // update user id
    	ivbMTask.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
    	ivbMTask.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
    	ivbMTask.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
    	IvbMTaskMapper tmp = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmp);
        when(dao.getIvbMTaskMapper().updateByPrimaryKey(any(IvbMTask.class)))
                .thenReturn(1);
        String result = bnn0089SearchTaskService_Mock.deleteData(ivbMTask);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testDeleteData() {
    	IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("T1");
        // task Id
        taskData.setTaskId("T001");
        // Working Details
        taskData.setWorkingDetails("abv company");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("");
        // updateUserId
        taskData.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        taskData.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        taskData.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);
        when(tmp.updateData(any(Map.class))).thenReturn(1);
        
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.deleteData(taskData);
        
        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testDeleteDataFail() {
    	IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("T1");
        // task Id
        taskData.setTaskId("T001");
        // Working Details
        taskData.setWorkingDetails("abv company");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("");
        // updateUserId
        taskData.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        taskData.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        taskData.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);

        //(new RuntimeException()).when(tmp).searchSingleData(any(HashMap.class));

        // get searchData
        //List<Bnn0089SearchTaskResult> bnn0089ResultList = bnn0089SearchTaskService_Mock.getSingleData(searchConditions);
        // star testing
        //Assert.assertEquals(null, bnn0089ResultList);

        when(tmp.updateData(any(Map.class))).thenReturn(0);
        
        IvbMTaskMapper tmp2 = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmp2);
        when(dao.getIvbMTaskMapper().selectByPrimaryKey(anyString()))
        .thenReturn(taskData);
        
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.deleteData(taskData);
        
        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_TASK, result);

    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testDeleteDataFail2() throws ParseException {
    	
    	String date = "06-04-2007 07:05:00.999";
    	SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.S");
    	Date myDate = fmt.parse(date);

    	IvbMTask taskData2 = new IvbMTask();
    	taskData2.setLastUpdateDate(myDate);
    	
    	IvbMTask taskData = new IvbMTask();
        // task Name
        taskData.setTaskName("T1");
        // task Id
        taskData.setTaskId("T001");
        // Working Details
        taskData.setWorkingDetails("abv company");
        // Quarantine Handling Flag
        taskData.setQuarantineHandlingFlag(true);
        // note
        taskData.setNote("");
        // updateUserId
        taskData.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        taskData.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        taskData.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        
        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);

        //doThrow(new RuntimeException()).when(tmp).searchSingleData(any(HashMap.class));

        // get searchData
        //List<Bnn0089SearchTaskResult> bnn0089ResultList = bnn0089SearchTaskService_Mock.getSingleData(searchConditions);
        // star testing
       // Assert.assertEquals(null, bnn0089ResultList);

        when(tmp.updateData(any(Map.class))).thenReturn(0);
        
        IvbMTaskMapper tmp2 = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmp2);
        when(dao.getIvbMTaskMapper().selectByPrimaryKey(anyString()))
        .thenReturn(taskData2);
        
        bnn0089SearchTaskService_Mock.setAppContext(appContext);
        String result = bnn0089SearchTaskService_Mock.deleteData(taskData);
        
        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_UPDATE_DATE, result);

    }

    @Test
    public void testGetSingleDataInnerException() {
		String taskId = "T001";
		Bnn0089SearchTaskConditions searchConditions = new Bnn0089SearchTaskConditions();
		searchConditions.setTaskId(taskId);
		IvbMTask ivbMTask = new IvbMTask();
		ivbMTask.setTaskName("T0001");
		bnn0089SearchTaskService_Mock.setAppContext(appContext);

		List<Bnn0089SearchTaskResult> bnn0089ResultListReturn = new ArrayList<Bnn0089SearchTaskResult>();
		bnn0089ResultListReturn.add(new Bnn0089SearchTaskResult());
		IvbMTaskMapper tmp = Mockito.mock(IvbMTaskMapper.class);
		when(dao.getIvbMTaskMapper()).thenReturn(null);
		when(tmp.selectByPrimaryKey(any(String.class))).thenReturn(ivbMTask);
		IvbMTask result = bnn0089SearchTaskService_Mock.getSingleData(searchConditions);
		Assert.assertEquals(null, result);
    }

    
    @Test
    public void testGetSingleDataOuterException() {
    	String taskId = "T001";
    	Bnn0089SearchTaskConditions searchConditions = new Bnn0089SearchTaskConditions();
    	searchConditions.setTaskId(taskId);
        IvbMTask ivbMTask = new IvbMTask();
        ivbMTask.setTaskName("T0001");
        // Mock setup
        IvbMTaskMapper tmp = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).selectByPrimaryKey(any(String.class));
        // get user data
        IvbMTask taskData = bnn0089SearchTaskService_Mock.getSingleData(searchConditions);
        // start testing
        Assert.assertEquals(null, taskData);
    }

    @Test
    public void testGetSingleData() {
    	String taskId = "T001";
    	Bnn0089SearchTaskConditions searchConditions = new Bnn0089SearchTaskConditions();
    	searchConditions.setTaskId(taskId);
        IvbMTask ivbMTask = new IvbMTask();
        ivbMTask.setTaskName("T0001");
        bnn0089SearchTaskService_Mock.setAppContext(appContext);

        Bnn0089SearchTaskMapper tmp = Mockito.mock(Bnn0089SearchTaskMapper.class);
        when(dao.getBnn0089SearchTaskMapper()).thenReturn(tmp);
        /*when(tmp.searchSingleData(any(HashMap.class))).thenReturn(bnn0089ResultListReturn);
        // get search result
        List<Bnn0089SearchTaskResult> bnn0089ResultList = bnn0089SearchTaskService_Mock.getSingleData(searchConditions);
=======
        // Mock setup
        IvbMTaskMapper tmp = Mockito.mock(IvbMTaskMapper.class);
        when(dao.getIvbMTaskMapper()).thenReturn(tmp);
        when(tmp.selectByPrimaryKey(any(String.class))).thenReturn(ivbMTask);
        // get user data
        IvbMTask taskData = bnn0089SearchTaskService_Mock.getSingleData(searchConditions);
>>>>>>> .r174733
        // start testing
<<<<<<< .mine
        Assert.assertEquals(0, bnn0089ResultList.size());*/

        Assert.assertEquals("T0001", ivbMTask.getTaskName());

    }

}
