package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
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

import vn.bananavietnam.ict.banana.component.Bnn0017SearchBananaKindConditions;
import vn.bananavietnam.ict.banana.component.Bnn0017SearchBananaKindResult;
import vn.bananavietnam.ict.banana.dao.Bnn0017SearchBananaKindDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0017SearchBananaKindMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;
import vn.bananavietnam.ict.common.db.model.IvbMKind;
import vn.bananavietnam.ict.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0017SearchBananaKindServiceTest {
    @Autowired
    private ApplicationContext appContext;
    @InjectMocks
    private Bnn0017SearchBananaKindService bnn0017SearchBananaKindService_Mock;

    @Mock
    private Bnn0017SearchBananaKindDao bnn0017SearchBananaKindDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0017");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        Authentication authToken = new UsernamePasswordAuthenticationToken(u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataOutOfMemoryException() {
        Bnn0017SearchBananaKindConditions searchConditions = new Bnn0017SearchBananaKindConditions();
        // processId
        searchConditions.setKindId("K001");
        // processName
        searchConditions.setKindName("K6");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        // Mock setup
        Bnn0017SearchBananaKindMapper tmp = Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(tmp);
        doThrow(new OutOfMemoryError()).when(tmp).searchData(any(HashMap.class));
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("-1");
        // get search result
        List<Bnn0017SearchBananaKindResult> bnn0017ResultList = bnn0017SearchBananaKindService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals("-1", bnn0017ResultList.get(0).getSearchDataTotalCounts());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataInnerException() {
        Bnn0017SearchBananaKindConditions searchConditions = new Bnn0017SearchBananaKindConditions();
        // processId
        searchConditions.setKindId("K001");
        // processName
        searchConditions.setKindName("K6");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");

        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);

        // Mock setup
        Bnn0017SearchBananaKindMapper tmp = Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));

        // get search result
        List<Bnn0017SearchBananaKindResult> bnn0017ResultList = bnn0017SearchBananaKindService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0017ResultList);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataOuterException() {
        Bnn0017SearchBananaKindConditions searchConditions = new Bnn0017SearchBananaKindConditions();
        // processId
        searchConditions.setKindId("K001");
        // processName
        searchConditions.setKindName("K6");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");

        // Mock setup
        Bnn0017SearchBananaKindMapper tmp = Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));

        // get search result
        List<Bnn0017SearchBananaKindResult> bnn0017ResultList = bnn0017SearchBananaKindService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0017ResultList);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSearchData() {
        Bnn0017SearchBananaKindConditions searchConditions = new Bnn0017SearchBananaKindConditions();
        // processId
        searchConditions.setKindId("K001");
        // processName
        searchConditions.setKindName("K6");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        List<Bnn0017SearchBananaKindResult> bnn0017ResultListReturn = new ArrayList<Bnn0017SearchBananaKindResult>();
        bnn0017ResultListReturn.add(new Bnn0017SearchBananaKindResult());
        // Mock setup
        Bnn0017SearchBananaKindMapper tmp = Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()
                .getSearchDataTotalCounts(any(HashMap.class))).thenReturn("1");
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0017ResultListReturn);
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("1");
        // get search result
        List<Bnn0017SearchBananaKindResult> bnn0017ResultList = bnn0017SearchBananaKindService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(1, bnn0017ResultList.size());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataNoResult() {
        Bnn0017SearchBananaKindConditions searchConditions = new Bnn0017SearchBananaKindConditions();
        // processId
        searchConditions.setKindId("K001");
        // processName
        searchConditions.setKindName("K6");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        List<Bnn0017SearchBananaKindResult> bnn0017ResultListReturn = new ArrayList<Bnn0017SearchBananaKindResult>();
        // Mock setup
        Bnn0017SearchBananaKindMapper tmp = Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()
                .getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0017ResultListReturn);
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
        // get search result
        List<Bnn0017SearchBananaKindResult> bnn0017ResultList = bnn0017SearchBananaKindService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(0, bnn0017ResultList.size());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataWithoutProcessIdAndProcessName() {
        Bnn0017SearchBananaKindConditions searchConditions = new Bnn0017SearchBananaKindConditions();
        // processId
        searchConditions.setKindId("");
        // processName
        searchConditions.setKindName("");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        List<Bnn0017SearchBananaKindResult> bnn0017ResultListReturn = new ArrayList<Bnn0017SearchBananaKindResult>();
        // Mock setup
        Bnn0017SearchBananaKindMapper tmp = Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()
                .getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0017ResultListReturn);
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
        // get search result
        List<Bnn0017SearchBananaKindResult> bnn0017ResultList = bnn0017SearchBananaKindService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(0, bnn0017ResultList.size());
    }
    
    @Test
    public void testUpdateDataBlankFields() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        // note 20170203
        //bananaKindObj.setNote(bananaKindData.getNote());
        // update user id
        bananaKindObj.setUpdateUserId((new Util()).getUserInfo().getID());
        // delete flag
        bananaKindObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper().updateByPrimaryKeySelective(any(IvbMKind.class)))
                .thenReturn(0);
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.updateData(bananaKindObj);
        // so the result will be failure
       Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testUpdateDataFail() {
       
    	IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K6");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        bananaKindObj.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        bananaKindObj.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        bananaKindObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        Bnn0017SearchBananaKindMapper tmp = Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().updateData(any(Map.class)))
        		.thenReturn(0);
        
        IvbMKindMapper tmp2 = Mockito.mock(IvbMKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp2);
        when(tmp2.selectByPrimaryKey(any(String.class))).thenReturn(bananaKindObj);

        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.updateData(bananaKindObj);
        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_KIND, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testUpdateDataFail2() throws ParseException {
    	
    	String date = "06-04-2007 07:05:00.999";
    	SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.S");
    	Date myDate = fmt.parse(date);

    	IvbMKind bananaKindObj2 = new IvbMKind();
    	bananaKindObj2.setLastUpdateDate(myDate);
    	
    	
    	IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K6");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        bananaKindObj.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        bananaKindObj.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        bananaKindObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        Bnn0017SearchBananaKindMapper tmp = Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().updateData(any(Map.class)))
        		.thenReturn(0);
        
        IvbMKindMapper tmp2 = Mockito.mock(IvbMKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp2);
        when(tmp2.selectByPrimaryKey(any(String.class))).thenReturn(bananaKindObj2);

        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.updateData(bananaKindObj);
        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_UPDATE_DATE, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testUpdateDataSuccess() {
    	
    	IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K6");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        bananaKindObj.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        bananaKindObj.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        bananaKindObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        Bnn0017SearchBananaKindMapper tmp = Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().updateData(any(Map.class)))
        		.thenReturn(1);
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.updateData(bananaKindObj);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_SUCCESSFUL, result);
    }
    
    @Test
    @Transactional
    public void testUpdateDataInnerException() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K6");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        // note 20170203
        //bananaKindObj.setNote(bananaKindData.getNote());
        // update user id
        bananaKindObj.setUpdateUserId((new Util()).getUserInfo().getID());
        // delete flag
        bananaKindObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(null);
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.updateData(bananaKindObj);
        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testUpdateDataOuterException() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K6");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        // note 20170203
        //bananaKindObj.setNote(bananaKindData.getNote());
        // update user id
        bananaKindObj.setUpdateUserId((new Util()).getUserInfo().getID());
        // delete flag
        bananaKindObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper().updateByPrimaryKeySelective(any(IvbMKind.class)))
                .thenReturn(1);
        String result = bnn0017SearchBananaKindService_Mock.updateData(bananaKindObj);
        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }
    
    @Test
    @Transactional
    public void testInsertDataRollback() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K6");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        // note 20170203
        //bananaKindObj.setNote(bananaKindData.getNote());
        // update user id
        bananaKindObj.setUpdateUserId((new Util()).getUserInfo().getID());
        // delete flag
        bananaKindObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper().updateByPrimaryKeySelective(any(IvbMKind.class)))
                .thenReturn(1);
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.insertData(bananaKindObj);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }
    
    @Test
    @Transactional
    public void testInsertDataInnerException() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K6");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        // note 20170203
        //bananaKindObj.setNote(bananaKindData.getNote());
        // update user id
        bananaKindObj.setUpdateUserId((new Util()).getUserInfo().getID());
        // delete flag
        bananaKindObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(null);
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.insertData(bananaKindObj);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }
    
    @Test
    @Transactional
    public void testException() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K6");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        // note 20170203
        //bananaKindObj.setNote(bananaKindData.getNote());
        // update user id
        bananaKindObj.setUpdateUserId((new Util()).getUserInfo().getID());
        // delete flag
        bananaKindObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(null);
        String result = bnn0017SearchBananaKindService_Mock.insertData(bananaKindObj);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataSuccess() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K6");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        // Mock setup
        IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
        Bnn0017SearchBananaKindMapper mapper =  Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper().insert(any(IvbMKind.class))).thenReturn(1);
        
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(mapper);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().getLastKindId(any(HashMap.class))).thenReturn("K110");
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.insertData(bananaKindObj);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataWithBlank() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        // Mock setup
        IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
        Bnn0017SearchBananaKindMapper mapper =  Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper().insert(any(IvbMKind.class))).thenReturn(1);
        
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(mapper);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().getLastKindId(any(HashMap.class))).thenReturn("K110");
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.insertData(bananaKindObj);

        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataWithBlank1() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K001");
        // ProspectiveHarvestAmount
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        // Mock setup
        IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
        Bnn0017SearchBananaKindMapper mapper =  Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper().insert(any(IvbMKind.class))).thenReturn(1);
        
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(mapper);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().getLastKindId(any(HashMap.class))).thenReturn("K110");
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.insertData(bananaKindObj);

        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataWithBlank2() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K001");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        // Mock setup
        IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
        Bnn0017SearchBananaKindMapper mapper =  Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper().insert(any(IvbMKind.class))).thenReturn(1);
        
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(mapper);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().getLastKindId(any(HashMap.class))).thenReturn("K110");
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.insertData(bananaKindObj);

        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataWithBlank3() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K001");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        // Mock setup
        IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
        Bnn0017SearchBananaKindMapper mapper =  Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper().insert(any(IvbMKind.class))).thenReturn(1);
        
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(mapper);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().getLastKindId(any(HashMap.class))).thenReturn("K110");
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.insertData(bananaKindObj);

        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataWithBlank4() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K001");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Mock setup
        IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
        Bnn0017SearchBananaKindMapper mapper =  Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper().insert(any(IvbMKind.class))).thenReturn(1);
        
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(mapper);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().getLastKindId(any(HashMap.class))).thenReturn(null);
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.insertData(bananaKindObj);

        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataIdNumberStrNull() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K6");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        // Mock setup
        IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
        Bnn0017SearchBananaKindMapper mapper =  Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper().insert(any(IvbMKind.class))).thenReturn(1);
        
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(mapper);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().getLastKindId(any(HashMap.class))).thenReturn(null);
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.insertData(bananaKindObj);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataFail() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K6");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        // Mock setup
        IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
        Bnn0017SearchBananaKindMapper mapper =  Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper().insert(any(IvbMKind.class))).thenReturn(0);
        
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(mapper);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().getLastKindId(any(HashMap.class))).thenReturn("K110");
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.insertData(bananaKindObj);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testDeleteDataFail() {
       
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        bananaKindObj.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        bananaKindObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        Bnn0017SearchBananaKindMapper tmp = Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().updateData(any(Map.class)))
        		.thenReturn(0);
        
        IvbMKindMapper tmp2 = Mockito.mock(IvbMKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp2);
        when(tmp2.selectByPrimaryKey(any(String.class))).thenReturn(bananaKindObj);

        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.deleteData(bananaKindObj);
        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_KIND, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testDeleteDataFail2() throws ParseException {
    	
    	String date = "06-04-2007 07:05:00.999";
    	SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.S");
    	Date myDate = fmt.parse(date);

    	IvbMKind bananaKindObj2 = new IvbMKind();
    	bananaKindObj2.setLastUpdateDate(myDate);
    	
    	
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        bananaKindObj.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        bananaKindObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        Bnn0017SearchBananaKindMapper tmp = Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().updateData(any(Map.class)))
        		.thenReturn(0);
        
        IvbMKindMapper tmp2 = Mockito.mock(IvbMKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp2);
        when(tmp2.selectByPrimaryKey(any(String.class))).thenReturn(bananaKindObj2);

        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.deleteData(bananaKindObj);
        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_UPDATE_DATE, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testDeleteDataSuccess() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K6");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        bananaKindObj.setUpdateUserId((new Util()).getUserInfo().getID());
        // last update date
        bananaKindObj.setLastUpdateDate(Calendar.getInstance().getTime());
        // delete flag
        bananaKindObj.setDeleteFlag(Constants.DELETE_FLAG_OFF);
        // Mock setup
        Bnn0017SearchBananaKindMapper tmp = Mockito.mock(Bnn0017SearchBananaKindMapper.class);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getBnn0017SearchBananaKindMapper().updateData(any(Map.class)))
        		.thenReturn(1);
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.deleteData(bananaKindObj);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
    }
    @Test
    @Transactional
    public void testDeleteDataInnerException() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K6");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        // Mock setup
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(null);
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        String result = bnn0017SearchBananaKindService_Mock.deleteData(bananaKindObj);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testDeleteDataOuterException() {
        IvbMKind bananaKindObj = new IvbMKind();
        // kind id
        bananaKindObj.setKindId("K001");
        // kind name
        bananaKindObj.setKindName("K6");
        // ProspectiveHarvestAmount
        bananaKindObj.setProspectiveHarvestAmount(0.1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysFlowering(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysBagging(1);
        // Estimated Days Flowering 20170203
        bananaKindObj.setEstimatedDaysHarvest(1);
        // Mock setup
        IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper().updateByPrimaryKeySelective(any(IvbMKind.class)))
                .thenReturn(1);
        String result = bnn0017SearchBananaKindService_Mock.deleteData(bananaKindObj);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    public void testGetSingleData() {
        String kindId = "P001";
        Bnn0017SearchBananaKindConditions bnn0017SearchBananaKindConditions = new Bnn0017SearchBananaKindConditions();
        bnn0017SearchBananaKindConditions.setKindId(kindId);
        IvbMKind kindDataReturn = new IvbMKind();
        kindDataReturn.setKindName("A0001");
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        // Mock setup
        IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp);
        when(tmp.selectByPrimaryKey(any(String.class))).thenReturn(kindDataReturn);
        // get user data
        IvbMKind kindData = bnn0017SearchBananaKindService_Mock.getSingleData(bnn0017SearchBananaKindConditions.getKindId());
        // start testing
        Assert.assertEquals("A0001", kindData.getKindName());
    }

    @Test
    public void testGetSingleInnerData() {
        String kindId = "P001";
        Bnn0017SearchBananaKindConditions bnn0017SearchBananaKindConditions = new Bnn0017SearchBananaKindConditions();
        bnn0017SearchBananaKindConditions.setKindId(kindId);
        IvbMKind kindDataReturn = new IvbMKind();
        kindDataReturn.setKindName("A0001");
        bnn0017SearchBananaKindService_Mock.setAppContext(appContext);
        // Mock setup
        IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).selectByPrimaryKey(any(String.class));
        // get user data
        IvbMKind kindData = bnn0017SearchBananaKindService_Mock.getSingleData(bnn0017SearchBananaKindConditions.getKindId());
        // start testing
        Assert.assertEquals(null, kindData);
    }

    @Test
    public void testGetSingleOuterData() {
        String kindId = "P001";
        Bnn0017SearchBananaKindConditions bnn0017SearchBananaKindConditions = new Bnn0017SearchBananaKindConditions();
        bnn0017SearchBananaKindConditions.setKindId(kindId);
        IvbMKind kindDataReturn = new IvbMKind();
        kindDataReturn.setKindName("A0001");
        // Mock setup
        IvbMKindMapper tmp = Mockito.mock(IvbMKindMapper.class);
        when(bnn0017SearchBananaKindDao.getIvbMKindMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).selectByPrimaryKey(any(String.class));
        // get user data
        IvbMKind kindData = bnn0017SearchBananaKindService_Mock.getSingleData(bnn0017SearchBananaKindConditions.getKindId());
        // start testing
        Assert.assertEquals(null, kindData);
    }
}
