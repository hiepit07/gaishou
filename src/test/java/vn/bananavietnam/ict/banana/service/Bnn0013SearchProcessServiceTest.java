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

import vn.bananavietnam.ict.banana.component.Bnn0013SearchProcessConditions;
import vn.bananavietnam.ict.banana.component.Bnn0013SearchProcessResult;
import vn.bananavietnam.ict.banana.dao.Bnn0013SearchProcessDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0013SearchProcessMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.mapper.IvbMProcessMapper;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0013SearchProcessServiceTest {

    @Autowired
    private ApplicationContext appContext;

    @InjectMocks
    private Bnn0013SearchProcessService bnn0013SearchProcessService_Mock;

    @Mock
    private Bnn0013SearchProcessDao bnn0013CultivationProcessDao;

	@Mock
	private Util util;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0013");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        Authentication authToken = new UsernamePasswordAuthenticationToken(u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        when(util.getUserInfo()).thenReturn(u);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataOutOfMemoryException() {
        Bnn0013SearchProcessConditions searchConditions = new Bnn0013SearchProcessConditions();
        // processId
        searchConditions.setProcessId("PC1");
        // processName
        searchConditions.setProcessName("PC2");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        // Mock setup
        Bnn0013SearchProcessMapper tmp = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(tmp);
        doThrow(new OutOfMemoryError()).when(tmp).searchData(any(HashMap.class));
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("-1");
        // get search result
        List<Bnn0013SearchProcessResult> bnn0013ResultList = bnn0013SearchProcessService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals("-1", bnn0013ResultList.get(0).getSearchDataTotalCounts());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataInnerException() {
        Bnn0013SearchProcessConditions searchConditions = new Bnn0013SearchProcessConditions();
        // processId
        searchConditions.setProcessId("PC1");
        // processName
        searchConditions.setProcessName("PC2");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");

        bnn0013SearchProcessService_Mock.setAppContext(appContext);

        // Mock setup
        Bnn0013SearchProcessMapper tmp = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));

        // get search result
        List<Bnn0013SearchProcessResult> bnn0013ResultList = bnn0013SearchProcessService_Mock.searchData(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0013ResultList);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataOuterException() {
        Bnn0013SearchProcessConditions searchConditions = new Bnn0013SearchProcessConditions();
        // processId
        searchConditions.setProcessId("PC1");
        // processName
        searchConditions.setProcessName("PC2");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");

        // Mock setup
        Bnn0013SearchProcessMapper tmp = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));

        // get search result
        List<Bnn0013SearchProcessResult> bnn0013ResultList = bnn0013SearchProcessService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0013ResultList);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchData() {
        Bnn0013SearchProcessConditions searchConditions = new Bnn0013SearchProcessConditions();
        // processId
        searchConditions.setProcessId("P00X1");
        // processName
        searchConditions.setProcessName("Process Name 00X1");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        List<Bnn0013SearchProcessResult> bnn0013ResultListReturn = new ArrayList<Bnn0013SearchProcessResult>();
        bnn0013ResultListReturn.add(new Bnn0013SearchProcessResult());
        // Mock setup
        Bnn0013SearchProcessMapper tmp = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()
                .getSearchDataTotalCounts(any(HashMap.class))).thenReturn("1");
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0013ResultListReturn);
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("1");
        // get search result
        List<Bnn0013SearchProcessResult> bnn0013ResultList = bnn0013SearchProcessService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(1, bnn0013ResultList.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataNoResult() {
        Bnn0013SearchProcessConditions searchConditions = new Bnn0013SearchProcessConditions();
        // processId
        searchConditions.setProcessId("P000001");
        // processName
        searchConditions.setProcessName("Process Name 000001");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        List<Bnn0013SearchProcessResult> bnn0013ResultListReturn = new ArrayList<Bnn0013SearchProcessResult>();
        // Mock setup
        Bnn0013SearchProcessMapper tmp = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()
                .getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0013ResultListReturn);

        // get search result
        List<Bnn0013SearchProcessResult> bnn0013ResultList = bnn0013SearchProcessService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(0, bnn0013ResultList.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataWithoutProcessIdAndProcessName() {
        Bnn0013SearchProcessConditions searchConditions = new Bnn0013SearchProcessConditions();
        // processId
        searchConditions.setProcessId("");
        // processName
        searchConditions.setProcessName("");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        List<Bnn0013SearchProcessResult> bnn0013ResultListReturn = new ArrayList<Bnn0013SearchProcessResult>();
        // Mock setup
        Bnn0013SearchProcessMapper tmp = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()
                .getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0013ResultListReturn);
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
        // get search result
        List<Bnn0013SearchProcessResult> bnn0013ResultList = bnn0013SearchProcessService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(0, bnn0013ResultList.size());
    }

    @Test
    public void testUpdateDataBlankFields() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("");
        // Process Name
        ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        IvbMProcessMapper tmp = Mockito.mock(IvbMProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper().updateByPrimaryKey(any(IvbMProcess.class)))
                .thenReturn(0);
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.updateData(ivbMCultivation);
        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testUpdateDataFail() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("PC001");
        // Process Name
        ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        Bnn0013SearchProcessMapper tmp = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper().updateData(any(Map.class)))
        .thenReturn(0);
        
        IvbMProcessMapper tmp2 = Mockito.mock(IvbMProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp2);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper().selectByPrimaryKey(anyString()))
        .thenReturn(ivbMCultivation);
        
        
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.updateData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_PROCESS, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testUpdateDataFail2() throws ParseException {
    	String date = "06-04-2007 07:05:00.999";
    	SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.S");
    	Date myDate = fmt.parse(date);

    	IvbMProcess ivbMCultivation2 = new IvbMProcess();
    	ivbMCultivation2.setLastUpdateDate(myDate);
    	
    	
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("PC001");
        // Process Name
        ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        Bnn0013SearchProcessMapper tmp = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper().updateData(any(Map.class)))
        .thenReturn(0);
        
        IvbMProcessMapper tmp2 = Mockito.mock(IvbMProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp2);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper().selectByPrimaryKey(anyString()))
        .thenReturn(ivbMCultivation2);
        
        
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.updateData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_UPDATE_DATE, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testUpdateDataSuccess() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("PC001");
        // Process Name
        ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
    	
        // Mock setup
        Bnn0013SearchProcessMapper tmp = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper().updateData(any(Map.class)))
                .thenReturn(1);
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.updateData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_SUCCESSFUL, result);
    }

    @Test
    public void testUpdateDataWithBlank() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("");
        // Process Name
        ivbMCultivation.setProcessName("");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        IvbMProcessMapper tmp = Mockito.mock(IvbMProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper().updateByPrimaryKey(any(IvbMProcess.class)))
                .thenReturn(1);
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.updateData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }
    
    @Test
    @Transactional
    public void testUpdateDataInnerException() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("PC001");
        // Process Name
        ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(null);
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.updateData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testUpdateDataOuterException() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("PC001");
        // Process Name
        ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        IvbMProcessMapper tmp = Mockito.mock(IvbMProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper().updateByPrimaryKey(any(IvbMProcess.class)))
                .thenReturn(1);
        String result = bnn0013SearchProcessService_Mock.updateData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    public void testInsertDataBlankFields() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("");
        // Process Name
        ivbMCultivation.setProcessName("");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        IvbMProcessMapper tmp = Mockito.mock(IvbMProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper().insert(any(IvbMProcess.class))).thenReturn(0);
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.insertData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }

    @Test
    public void testInsertDataRollback() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("P001");
        // Process Name
        ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        IvbMProcessMapper tmp = Mockito.mock(IvbMProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper().insert(any(IvbMProcess.class))).thenReturn(0);
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.insertData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    public void testInsertDataInnerException() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("P001");
        // Process Name
        ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(null);
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.insertData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testException() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("P001");
        // Process Name
        ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(null);
        String result = bnn0013SearchProcessService_Mock.insertData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testInsertData() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("P001");
        // Process Name
        ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        IvbMProcessMapper tmp = Mockito.mock(IvbMProcessMapper.class);
        Bnn0013SearchProcessMapper mapper = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(mapper);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper().insert(any(IvbMProcess.class))).thenReturn(1);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper().getLastProcessId()).thenReturn("P100");
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.insertData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
    }
    
    @Test
    @Transactional
    public void testInsertData1() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("P001");
        // Process Name
        ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        IvbMProcessMapper tmp = Mockito.mock(IvbMProcessMapper.class);
        Bnn0013SearchProcessMapper mapper = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(mapper);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper().insert(any(IvbMProcess.class))).thenReturn(1);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper().getLastProcessId()).thenReturn(null);
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.insertData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
    }
    
    @Test
    @Transactional
    public void testInsertDataWithBlank() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("");
        // Process Name
        ivbMCultivation.setProcessName("");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        IvbMProcessMapper tmp = Mockito.mock(IvbMProcessMapper.class);
        Bnn0013SearchProcessMapper mapper = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(mapper);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper().insert(any(IvbMProcess.class))).thenReturn(1);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper().getLastProcessId()).thenReturn("P100");
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.insertData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }
    
    @Test
    @Transactional
    public void testInsertDataFail() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("P001");
        // Process Name
        ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        IvbMProcessMapper tmp = Mockito.mock(IvbMProcessMapper.class);
        Bnn0013SearchProcessMapper mapper = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(mapper);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper().insert(any(IvbMProcess.class))).thenReturn(0);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper().getLastProcessId()).thenReturn("P100");
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.insertData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteDataFail() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("PC001");
        // Process Name
        //ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        Bnn0013SearchProcessMapper tmp = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper().updateData(any(Map.class)))
        .thenReturn(0);
        
        IvbMProcessMapper tmp2 = Mockito.mock(IvbMProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp2);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper().selectByPrimaryKey(anyString()))
        .thenReturn(ivbMCultivation);
        
        
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.deleteData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_PROCESS, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteDataFail2() throws ParseException {
    	String date = "06-04-2007 07:05:00.999";
    	SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.S");
    	Date myDate = fmt.parse(date);

    	IvbMProcess ivbMCultivation2 = new IvbMProcess();
    	ivbMCultivation2.setLastUpdateDate(myDate);
    	
    	
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("PC001");
        // Process Name
        //ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        Bnn0013SearchProcessMapper tmp = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper().updateData(any(Map.class)))
        .thenReturn(0);
        
        IvbMProcessMapper tmp2 = Mockito.mock(IvbMProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp2);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper().selectByPrimaryKey(anyString()))
        .thenReturn(ivbMCultivation2);
        
        
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.deleteData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_UPDATE_DATE, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testDeleteDataSuccess() {
    	IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("PC001");
        // Process Name
        //ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
    	
        // Mock setup
        Bnn0013SearchProcessMapper tmp = Mockito.mock(Bnn0013SearchProcessMapper.class);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getBnn0013CultivationProcessMapper().updateData(any(Map.class)))
                .thenReturn(1);
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.deleteData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
    }

    @Test
    @Transactional
    public void testDeleteDataInnerException() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("PC001");
        // Process Name
        ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(null);
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        String result = bnn0013SearchProcessService_Mock.deleteData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testDeleteDataOuterException() {
        IvbMProcess ivbMCultivation = new IvbMProcess();
        // Process Id
        ivbMCultivation.setProcessId("PC001");
        // Process Name
        ivbMCultivation.setProcessName("Process Name 001");
        // update user id
        ivbMCultivation.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        ivbMCultivation.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        ivbMCultivation.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        IvbMProcessMapper tmp = Mockito.mock(IvbMProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper().updateByPrimaryKey(any(IvbMProcess.class)))
                .thenReturn(1);
        String result = bnn0013SearchProcessService_Mock.deleteData(ivbMCultivation);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    public void testGetSingleData() {
        String processId = "P001";
        Bnn0013SearchProcessConditions bnn0013ProcessConditions = new Bnn0013SearchProcessConditions();
        bnn0013ProcessConditions.setProcessId(processId);
        IvbMProcess processDataReturn = new IvbMProcess();
        processDataReturn.setProcessName("A0001");
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        // Mock setup
        IvbMProcessMapper tmp = Mockito.mock(IvbMProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp);
        when(tmp.selectByPrimaryKey(any(String.class))).thenReturn(processDataReturn);
        // get user data
        IvbMProcess processData = bnn0013SearchProcessService_Mock.getSingleData(bnn0013ProcessConditions);
        // start testing
        Assert.assertEquals("A0001", processData.getProcessName());
    }

    @Test
    public void testGetSingleDataInnerException() {
        String processId = "P001";
        Bnn0013SearchProcessConditions bnn0013ProcessConditions = new Bnn0013SearchProcessConditions();
        bnn0013ProcessConditions.setProcessId(processId);
        IvbMProcess processDataReturn = new IvbMProcess();
        processDataReturn.setProcessName("A0001");
        bnn0013SearchProcessService_Mock.setAppContext(appContext);
        // Mock setup
        IvbMProcessMapper tmp = Mockito.mock(IvbMProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).selectByPrimaryKey(any(String.class));
        // get user data
        IvbMProcess processData = bnn0013SearchProcessService_Mock.getSingleData(bnn0013ProcessConditions);
        // start testing
        Assert.assertEquals(null, processData);
    }

    @Test
    public void testGetSingleDataOuterException() {
        String processId = "P001";
        Bnn0013SearchProcessConditions bnn0013ProcessConditions = new Bnn0013SearchProcessConditions();
        bnn0013ProcessConditions.setProcessId(processId);
        IvbMProcess processDataReturn = new IvbMProcess();
        processDataReturn.setProcessName("A0001");
        // Mock setup
        IvbMProcessMapper tmp = Mockito.mock(IvbMProcessMapper.class);
        when(bnn0013CultivationProcessDao.getIvbMProcessMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).selectByPrimaryKey(any(String.class));
        // get user data
        IvbMProcess processData = bnn0013SearchProcessService_Mock.getSingleData(bnn0013ProcessConditions);
        // start testing
        Assert.assertEquals(null, processData);
    }
}
