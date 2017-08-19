package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;

import vn.bananavietnam.ict.banana.component.Bnn0087AffiliationDelete;
import vn.bananavietnam.ict.banana.component.Bnn0087IvbMManager;
import vn.bananavietnam.ict.banana.component.Bnn0087SearchAffiliationConditions;
import vn.bananavietnam.ict.banana.component.Bnn0087SearchAffiliationResult;
import vn.bananavietnam.ict.banana.component.Bnn0088SearchAreaResult;
import vn.bananavietnam.ict.banana.component.Bnn0088SearchFarmResult;
import vn.bananavietnam.ict.banana.dao.Bnn0087SearchAffiliationDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0087SearchAffiliationMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.mapper.IvbMAuthorizationMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMManagerMapper;
import vn.bananavietnam.ict.common.db.model.IvbMAuthorization;
import vn.bananavietnam.ict.common.db.model.IvbMAuthorizationExample;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0087SearchAffiliationServiceTest {
    @Autowired
    private ApplicationContext appContext;
    @Mock
    private Bnn0087SearchAffiliationDao bnn0087SearchAffiliationDao;

    @InjectMocks
    private Bnn0087SearchAffiliationService bnn0087SearchAffiliationService_Mock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0087");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        Authentication authToken = new UsernamePasswordAuthenticationToken(u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
    
    @Test
    public void testInitData() {
        ExtendedModelMap model = new ExtendedModelMap();
        List<IvbMAuthorization> processData = new ArrayList<IvbMAuthorization>();
        processData.add(new IvbMAuthorization());
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        bnn0087SearchAffiliationService_Mock.initData(model);
     // Mock setup
        IvbMAuthorizationMapper ivbMAuthorizationMapper = Mockito.mock(IvbMAuthorizationMapper.class);
        when(bnn0087SearchAffiliationDao.getIvbMAuthorizationMapper()).thenReturn(ivbMAuthorizationMapper);
        when(ivbMAuthorizationMapper.selectByExample(any(IvbMAuthorizationExample.class))).thenReturn(processData);
        bnn0087SearchAffiliationService_Mock.initData(model);
    }

    @Test
    public void testInitDataNull() {
        ExtendedModelMap model = new ExtendedModelMap();
        List<IvbMAuthorization> processData = new ArrayList<IvbMAuthorization>();
        // Mock setup
        IvbMAuthorizationMapper ivbMAuthorizationMapper = Mockito.mock(IvbMAuthorizationMapper.class);
        when(bnn0087SearchAffiliationDao.getIvbMAuthorizationMapper()).thenReturn(ivbMAuthorizationMapper);
        when(ivbMAuthorizationMapper.selectByExample(any(IvbMAuthorizationExample.class))).thenReturn(processData);
        bnn0087SearchAffiliationService_Mock.initData(model);
    }
    
    @Test
    public void testInitDataException() {
        ExtendedModelMap model = new ExtendedModelMap();
        IvbMAuthorizationMapper ivbMAuthorizationMapper = Mockito.mock(IvbMAuthorizationMapper.class);
        when(bnn0087SearchAffiliationDao.getIvbMAuthorizationMapper()).thenReturn(ivbMAuthorizationMapper);
        doThrow(new RuntimeException()).when(ivbMAuthorizationMapper).selectByExample(any(IvbMAuthorizationExample.class));
        bnn0087SearchAffiliationService_Mock.initData(model);

    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSearchData() {
        Bnn0087SearchAffiliationConditions searchConditions = new Bnn0087SearchAffiliationConditions();
        searchConditions.setUsersName("Huynh Ngoc Lam");
        searchConditions.setAuthorityName("");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");

        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        
        List<Bnn0087SearchAffiliationResult> bnn0087ResultList = new ArrayList<Bnn0087SearchAffiliationResult>();
        bnn0087ResultList.add(new Bnn0087SearchAffiliationResult());
        // Mock setup
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0087ResultList);
        
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("1");
        // get search result
        bnn0087ResultList = bnn0087SearchAffiliationService_Mock.searchData(searchConditions);
        // start testing
        Assert.assertEquals(1, bnn0087ResultList.size());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataWithoutUserName() {
        Bnn0087SearchAffiliationConditions searchConditions = new Bnn0087SearchAffiliationConditions();
        searchConditions.setUsersName("");
        searchConditions.setAuthorityName("3");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        List<Bnn0087SearchAffiliationResult> bnn0087ResultList = new ArrayList<Bnn0087SearchAffiliationResult>();
        // Mock setup
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0087ResultList);
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
        // get search result
        bnn0087ResultList = bnn0087SearchAffiliationService_Mock.searchData(searchConditions);
        // start testing
        Assert.assertEquals(0, bnn0087ResultList.size());
    }
    
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataOutOfMemoryException() {
        Bnn0087SearchAffiliationConditions searchConditions = new Bnn0087SearchAffiliationConditions();
        searchConditions.setUsersName("A0001");
        searchConditions.setAuthorityName("3");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        List<Bnn0087SearchAffiliationResult> bnn0087ResultList = new ArrayList<Bnn0087SearchAffiliationResult>();
        bnn0087ResultList.add(new Bnn0087SearchAffiliationResult());
        // Mock setup
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        doThrow(new OutOfMemoryError()).when(tmp).searchData(any(HashMap.class));
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("-1");
        // get search result
        bnn0087ResultList = bnn0087SearchAffiliationService_Mock.searchData(searchConditions);
        // start testing
        Assert.assertEquals("-1", bnn0087ResultList.get(0).getSearchDataTotalCounts());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataException() {
        Bnn0087SearchAffiliationConditions searchConditions = new Bnn0087SearchAffiliationConditions();
        searchConditions.setUsersName("A0001");
        searchConditions.setAuthorityName("3");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        List<Bnn0087SearchAffiliationResult> bnn0087ResultList = new ArrayList<Bnn0087SearchAffiliationResult>();
        bnn0087ResultList.add(new Bnn0087SearchAffiliationResult());
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        // Mock setup
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));
        // get search result
        bnn0087ResultList = bnn0087SearchAffiliationService_Mock.searchData(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0087ResultList);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSearchDataExceptionCache() {
        Bnn0087SearchAffiliationConditions searchConditions = new Bnn0087SearchAffiliationConditions();
        searchConditions.setUsersName("A0001");
        searchConditions.setAuthorityName("3");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        List<Bnn0087SearchAffiliationResult> bnn0087ResultList = new ArrayList<Bnn0087SearchAffiliationResult>();
        bnn0087ResultList.add(new Bnn0087SearchAffiliationResult());
        // Mock setup
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));
        // get search result
        bnn0087ResultList = bnn0087SearchAffiliationService_Mock.searchData(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0087ResultList);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void getDataForAffiliation() {
        ArrayList<Bnn0088SearchFarmResult> arrDataFarm = new ArrayList<Bnn0088SearchFarmResult>();
        arrDataFarm.add(new Bnn0088SearchFarmResult());
        List<Bnn0088SearchAreaResult> bnn0088ResultList = new ArrayList<Bnn0088SearchAreaResult>();
        bnn0088ResultList.add(new Bnn0088SearchAreaResult());
        
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.selectDataFarm()).thenReturn(arrDataFarm);
        when(tmp.selectDataArea(any(HashMap.class))).thenReturn(bnn0088ResultList);
        
        List<Bnn0088SearchAreaResult> result = bnn0087SearchAffiliationService_Mock.getDataForAffiliation();
        Assert.assertEquals(1, result.size());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void getDataForAffiliationNodata() {
        ArrayList<Bnn0088SearchFarmResult> arrDataFarm = new ArrayList<Bnn0088SearchFarmResult>();
        List<Bnn0088SearchAreaResult> bnn0088ResultList = new ArrayList<Bnn0088SearchAreaResult>();
        
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.selectDataFarm()).thenReturn(arrDataFarm);
        when(tmp.selectDataArea(any(HashMap.class))).thenReturn(bnn0088ResultList);
        
        List<Bnn0088SearchAreaResult> result = bnn0087SearchAffiliationService_Mock.getDataForAffiliation();
        Assert.assertEquals(1, result.size());
    }
    
    @Test
    public void getDataForAffiliationException() {

        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).selectDataFarm();
        bnn0087SearchAffiliationService_Mock.getDataForAffiliation();
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void getDataForAffiliationOutOfMemory() {
        ArrayList<Bnn0088SearchFarmResult> arrDataFarm = new ArrayList<Bnn0088SearchFarmResult>();
        
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.selectDataFarm()).thenReturn(arrDataFarm);
        doThrow(new OutOfMemoryError()).when(tmp).selectDataArea(any(HashMap.class));
        bnn0087SearchAffiliationService_Mock.getDataForAffiliation();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteDataFailWithCheckAreaManager() {
        Bnn0087AffiliationDelete bnn0087AffiliationDelete = new Bnn0087AffiliationDelete();
        List<Bnn0088SearchAreaResult> bnn0088ResultListReturn = new ArrayList<Bnn0088SearchAreaResult>();
        bnn0088ResultListReturn.add(new Bnn0088SearchAreaResult());
        //Bnn0088SearchAreaResult resultCheckAreaManager = new Bnn0088SearchAreaResult();
        // Mock setup
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088ResultListReturn);
        when(tmp.deleteData(any(HashMap.class))).thenReturn(0);

        // delete 
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        String result = bnn0087SearchAffiliationService_Mock.deleteData(bnn0087AffiliationDelete);
        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_MANAGER, result);
    }
   
    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteDataFailWithCheckAreaManagerRollback() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	
        Bnn0087AffiliationDelete bnn0087AffiliationDelete = new Bnn0087AffiliationDelete();
        bnn0087AffiliationDelete.setLastUpdateDate(date);
        bnn0087AffiliationDelete.setAuthorizationTypeId("1");
        List<Bnn0088SearchAreaResult> bnn0088ResultListReturn = new ArrayList<Bnn0088SearchAreaResult>();
        //Bnn0088SearchAreaResult resultCheckAreaManager = new Bnn0088SearchAreaResult();
        // Mock setup
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088ResultListReturn);
        when(tmp.deleteData(any(HashMap.class))).thenReturn(0);
        when(tmp.getLastUpdateDate(any(HashMap.class))).thenReturn(date);
        // delete 
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        String result = bnn0087SearchAffiliationService_Mock.deleteData(bnn0087AffiliationDelete);
        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteDataFailWithCheckAreaManagerRollback4() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	
        Bnn0087AffiliationDelete bnn0087AffiliationDelete = new Bnn0087AffiliationDelete();
        bnn0087AffiliationDelete.setLastUpdateDate(date);
        bnn0087AffiliationDelete.setAuthorizationTypeId("null");
        List<Bnn0088SearchAreaResult> bnn0088ResultListReturn = new ArrayList<Bnn0088SearchAreaResult>();
        //Bnn0088SearchAreaResult resultCheckAreaManager = new Bnn0088SearchAreaResult();
        // Mock setup
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088ResultListReturn);
        when(tmp.deleteData(any(HashMap.class))).thenReturn(0);
        when(tmp.getLastUpdateDate(any(HashMap.class))).thenReturn(date);
        // delete 
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        String result = bnn0087SearchAffiliationService_Mock.deleteData(bnn0087AffiliationDelete);
        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteDataFailWithCheckAreaManagerRollback1() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	
        Bnn0087AffiliationDelete bnn0087AffiliationDelete = new Bnn0087AffiliationDelete();
        bnn0087AffiliationDelete.setLastUpdateDate(date);
        List<Bnn0088SearchAreaResult> bnn0088ResultListReturn = new ArrayList<Bnn0088SearchAreaResult>();
        //Bnn0088SearchAreaResult resultCheckAreaManager = new Bnn0088SearchAreaResult();
        // Mock setup
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088ResultListReturn);
        when(tmp.deleteData(any(HashMap.class))).thenReturn(0);
        when(tmp.getLastUpdateDate(any(HashMap.class))).thenReturn(date);
        // delete 
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        String result = bnn0087SearchAffiliationService_Mock.deleteData(bnn0087AffiliationDelete);
        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteDataFailWithCheckAreaManagerRollback2() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	
        Bnn0087AffiliationDelete bnn0087AffiliationDelete = new Bnn0087AffiliationDelete();
        bnn0087AffiliationDelete.setLastUpdateDate(date);
        List<Bnn0088SearchAreaResult> bnn0088ResultListReturn = new ArrayList<Bnn0088SearchAreaResult>();
        //Bnn0088SearchAreaResult resultCheckAreaManager = new Bnn0088SearchAreaResult();
        // Mock setup
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088ResultListReturn);
        when(tmp.deleteData(any(HashMap.class))).thenReturn(0);
        when(tmp.getLastUpdateDate(any(HashMap.class))).thenReturn(null);
        // delete 
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        String result = bnn0087SearchAffiliationService_Mock.deleteData(bnn0087AffiliationDelete);
        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
    }
 
    
    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteDataFailWithCheckAreaManagerRollback3() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	
        Bnn0087AffiliationDelete bnn0087AffiliationDelete = new Bnn0087AffiliationDelete();
        bnn0087AffiliationDelete.setLastUpdateDate(date);
        List<Bnn0088SearchAreaResult> bnn0088ResultListReturn = new ArrayList<Bnn0088SearchAreaResult>();
        //Bnn0088SearchAreaResult resultCheckAreaManager = new Bnn0088SearchAreaResult();
        // Mock setup
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088ResultListReturn);
        when(tmp.deleteData(any(HashMap.class))).thenReturn(0);
        when(tmp.getLastUpdateDate(any(HashMap.class))).thenReturn(date);
        // delete 
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        String result = bnn0087SearchAffiliationService_Mock.deleteData(bnn0087AffiliationDelete);
        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteDataFailWithCheckAreaManagerUpdateDate() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	
        Bnn0087AffiliationDelete bnn0087AffiliationDelete = new Bnn0087AffiliationDelete();
        bnn0087AffiliationDelete.setLastUpdateDate(date);
        bnn0087AffiliationDelete.setAuthorizationTypeId("1");
        List<Bnn0088SearchAreaResult> bnn0088ResultListReturn = new ArrayList<Bnn0088SearchAreaResult>();
        //Bnn0088SearchAreaResult resultCheckAreaManager = new Bnn0088SearchAreaResult();
        // Mock setup
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088ResultListReturn);
        when(tmp.deleteData(any(HashMap.class))).thenReturn(0);
        when(tmp.getLastUpdateDate(any(HashMap.class))).thenReturn(format.parse("2017/05/18 13:25:25"));
        // delete 
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        String result = bnn0087SearchAffiliationService_Mock.deleteData(bnn0087AffiliationDelete);
        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteDataFailWithCheckAreaManagerSuccess() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	
        Bnn0087AffiliationDelete bnn0087AffiliationDelete = new Bnn0087AffiliationDelete();
        bnn0087AffiliationDelete.setLastUpdateDate(date);
        bnn0087AffiliationDelete.setAuthorizationTypeId("1");
        List<Bnn0088SearchAreaResult> bnn0088ResultListReturn = new ArrayList<Bnn0088SearchAreaResult>();
        //Bnn0088SearchAreaResult resultCheckAreaManager = new Bnn0088SearchAreaResult();
        // Mock setup
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088ResultListReturn);
        when(tmp.deleteData(any(HashMap.class))).thenReturn(1);
        when(tmp.getLastUpdateDate(any(HashMap.class))).thenReturn(format.parse("2017/05/18 13:25:25"));
        // delete 
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        String result = bnn0087SearchAffiliationService_Mock.deleteData(bnn0087AffiliationDelete);
        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteDataFailWithCheckAreaManager1() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	
        Bnn0087AffiliationDelete bnn0087AffiliationDelete = new Bnn0087AffiliationDelete();
        bnn0087AffiliationDelete.setLastUpdateDate(date);
        List<Bnn0088SearchAreaResult> bnn0088ResultListReturn = new ArrayList<Bnn0088SearchAreaResult>();
        bnn0088ResultListReturn.add(new Bnn0088SearchAreaResult());
        // Mock setup
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088ResultListReturn);
        when(tmp.deleteData(any(HashMap.class))).thenReturn(1);
        when(tmp.getLastUpdateDate(any(HashMap.class))).thenReturn(format.parse("2017/05/18 13:25:25"));
        // delete 
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        String result = bnn0087SearchAffiliationService_Mock.deleteData(bnn0087AffiliationDelete);
        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_MANAGER, result);
    }
    
    
    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteDataInnerException() {
        Bnn0087AffiliationDelete bnn0087AffiliationDelete = new Bnn0087AffiliationDelete();
        
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        
        // Mock setup
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.deleteData(any(HashMap.class))).thenReturn(0);

        // delete 
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        String result = bnn0087SearchAffiliationService_Mock.deleteData(bnn0087AffiliationDelete);
        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
    }
    
    @Test
	@Transactional
	public void testDeleteDataOuterException() {
		// user id
    	Bnn0087AffiliationDelete bnn0087AffiliationDelete = new Bnn0087AffiliationDelete();
    	
		// delete user
		String result = bnn0087SearchAffiliationService_Mock.deleteData(bnn0087AffiliationDelete);
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}
    
    //////////////////////////////////////////////////////
    
    
    
    @Test
    @Transactional
    public void testInsertDataInnerException() {
        ArrayList<Bnn0087IvbMManager> ivbMManagerDataForInsert = new ArrayList<Bnn0087IvbMManager>();
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(null);
        // insert 
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        String result = bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(ivbMManagerDataForInsert);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }
    
    @Test
    @Transactional
    public void testInsertDataOuterException() {
        ArrayList<Bnn0087IvbMManager> ivbMManagerDataForInsert = new ArrayList<Bnn0087IvbMManager>();
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(null);
        // insert 
        String result = bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(ivbMManagerDataForInsert);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataFailWithCheckAreaManager() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	List<Bnn0088SearchAreaResult> bnn0088SearchAreaResult = new ArrayList<Bnn0088SearchAreaResult>();
    	
    	ArrayList<Bnn0087IvbMManager> bnn0087IvbMManager = new ArrayList<Bnn0087IvbMManager>();
    	Bnn0087IvbMManager temp2 = new Bnn0087IvbMManager();
    	temp2.setFarmId("ggfff");
    	temp2.setAreaId("llfff");
    	bnn0087IvbMManager.add(temp2);
    	
    	Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088SearchAreaResult);
        when(tmp.getLastUpdateDate(any(HashMap.class))).thenReturn(date);

        // delete 
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        String result = bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(bnn0087IvbMManager);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataFailWithCheckAreaManagerElse() {
    	List<Bnn0088SearchAreaResult> bnn0088SearchAreaResult = new ArrayList<Bnn0088SearchAreaResult>();
    	
    	ArrayList<Bnn0087IvbMManager> bnn0087IvbMManager = new ArrayList<Bnn0087IvbMManager>();
    	Bnn0087IvbMManager temp2 = new Bnn0087IvbMManager();
    	temp2.setFarmId("ggfff");
    	temp2.setAreaId("llfff");
    	bnn0087IvbMManager.add(temp2);
    	
    	// delete 
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        
    	Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088SearchAreaResult);
        
        IvbMManagerMapper mapperTemp = Mockito.mock(IvbMManagerMapper.class);
        when(bnn0087SearchAffiliationDao.getIvbMManagerMapper()).thenReturn(mapperTemp);
        when(mapperTemp.insert(bnn0087IvbMManager.get(0))).thenReturn(0);
        
        bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(bnn0087IvbMManager);
    }

    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataFailWithCheckAreaManagerElseToDB() {
    	List<Bnn0088SearchAreaResult> bnn0088SearchAreaResult = new ArrayList<Bnn0088SearchAreaResult>();
    	
    	ArrayList<Bnn0087IvbMManager> bnn0087IvbMManager = new ArrayList<Bnn0087IvbMManager>();
    	Bnn0087IvbMManager temp2 = new Bnn0087IvbMManager();
    	temp2.setFarmId("ggfff");
    	temp2.setAreaId("llfff");
    	bnn0087IvbMManager.add(temp2);
    	
    	// delete 
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        
    	Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088SearchAreaResult);
        
        IvbMManagerMapper mapperTemp = Mockito.mock(IvbMManagerMapper.class);
        when(bnn0087SearchAffiliationDao.getIvbMManagerMapper()).thenReturn(mapperTemp);
        when(mapperTemp.insert(bnn0087IvbMManager.get(0))).thenReturn(1);
        
        bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(bnn0087IvbMManager);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataException() {

        List<Bnn0088SearchAreaResult> bnn0088SearchAreaResult = new ArrayList<Bnn0088SearchAreaResult>();
    	
    	ArrayList<Bnn0087IvbMManager> bnn0087IvbMManager = new ArrayList<Bnn0087IvbMManager>();
    	Bnn0087IvbMManager temp2 = new Bnn0087IvbMManager();
    	temp2.setFarmId("ggfff");
    	temp2.setAreaId("llfff");
    	bnn0087IvbMManager.add(temp2);
    	
    	// delete 
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        
    	Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088SearchAreaResult);
        
        IvbMManagerMapper mapperTemp = Mockito.mock(IvbMManagerMapper.class);
        when(bnn0087SearchAffiliationDao.getIvbMManagerMapper()).thenReturn(mapperTemp);
        doThrow(new RuntimeException()).when(mapperTemp).insert(bnn0087IvbMManager.get(0));
        
        // insert 
        String result = bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(bnn0087IvbMManager);
        
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
        
    }

    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataWithRepeatInsert() {
        ArrayList<Bnn0087IvbMManager> ivbMManagerDataForInsert = new ArrayList<Bnn0087IvbMManager>();
        
        List<Bnn0088SearchAreaResult> bnn0088SearchAreaResult = new ArrayList<Bnn0088SearchAreaResult>();
        
        Bnn0088SearchAreaResult resultCheckAreaManager = new Bnn0088SearchAreaResult();
        resultCheckAreaManager.setFarmId("F0001");
        resultCheckAreaManager.setAreaId("A0001");
        bnn0088SearchAreaResult.add(resultCheckAreaManager);
        
        Bnn0087IvbMManager temp =  new Bnn0087IvbMManager();
        temp.setFarmId("F0001");
        temp.setAreaId("A0001");
        temp.setUsersId("A0001");
        temp.setTypeIdEdit("4");
        ivbMManagerDataForInsert.add(temp);
        IvbMManagerMapper mapperTemp = Mockito.mock(IvbMManagerMapper.class);
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getIvbMManagerMapper()).thenReturn(mapperTemp);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(mapperTemp.insert(ivbMManagerDataForInsert.get(0))).thenReturn(1);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088SearchAreaResult);
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        // insert 
        String result = bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(ivbMManagerDataForInsert);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
        
    }
    
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataWithoutRepeatInsert() {
        ArrayList<Bnn0087IvbMManager> ivbMManagerDataForInsert = new ArrayList<Bnn0087IvbMManager>();
        List<Bnn0088SearchAreaResult> bnn0088SearchAreaResult = new ArrayList<Bnn0088SearchAreaResult>();
        
        Bnn0088SearchAreaResult resultCheckAreaManager = new Bnn0088SearchAreaResult();
        resultCheckAreaManager.setFarmId("F0002");
        resultCheckAreaManager.setAreaId("A0002");
        bnn0088SearchAreaResult.add(resultCheckAreaManager);
        Bnn0087IvbMManager temp =  new Bnn0087IvbMManager();
        temp.setFarmId("F0001");
        temp.setAreaId("A0001");
        temp.setUsersId("A0001");
        temp.setTypeIdEdit("4");
        ivbMManagerDataForInsert.add(temp);
        IvbMManagerMapper mapperTemp = Mockito.mock(IvbMManagerMapper.class);
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getIvbMManagerMapper()).thenReturn(mapperTemp);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(mapperTemp.insert(ivbMManagerDataForInsert.get(0))).thenReturn(1);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088SearchAreaResult);
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        // insert 
        String result = bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(ivbMManagerDataForInsert);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_MANAGER, result);
        
    }

    @Test
    @Transactional
    public void testInsertDataWithTypeIdEditNull() {
        ArrayList<Bnn0087IvbMManager> ivbMManagerDataForInsert = new ArrayList<Bnn0087IvbMManager>();
        Bnn0087IvbMManager temp =  new Bnn0087IvbMManager();
        temp.setFarmId("F0001");
        temp.setAreaId("A0002");
        temp.setUsersId("A0001");
        ivbMManagerDataForInsert.add(temp);
        IvbMManagerMapper mapperTemp = Mockito.mock(IvbMManagerMapper.class);
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getIvbMManagerMapper()).thenReturn(mapperTemp);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(mapperTemp.insert(ivbMManagerDataForInsert.get(0))).thenReturn(1);
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        // insert 
        String result = bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(ivbMManagerDataForInsert);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
        
    }
    
    @Test
    @Transactional
    public void testInsertDataWithTypeIdEditNullString() {
        ArrayList<Bnn0087IvbMManager> ivbMManagerDataForInsert = new ArrayList<Bnn0087IvbMManager>();
        Bnn0087IvbMManager temp =  new Bnn0087IvbMManager();
        temp.setFarmId("F0001");
        temp.setAreaId("A0002");
        temp.setUsersId("A0001");
        temp.setTypeIdEdit("null");
        ivbMManagerDataForInsert.add(temp);
        IvbMManagerMapper mapperTemp = Mockito.mock(IvbMManagerMapper.class);
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getIvbMManagerMapper()).thenReturn(mapperTemp);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(mapperTemp.insert(ivbMManagerDataForInsert.get(0))).thenReturn(1);
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        // insert 
        String result = bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(ivbMManagerDataForInsert);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
        
    }

    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDatawidthdeleteDataisZero() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
        ArrayList<Bnn0087IvbMManager> ivbMManagerDataForInsert = new ArrayList<Bnn0087IvbMManager>();
        
        Bnn0087IvbMManager temp =  new Bnn0087IvbMManager();
        temp.setFarmId("F0001");
        temp.setAreaId("A0002");
        temp.setUsersId("A0001");
        temp.setLastUpdateDate(date);
        temp.setTypeIdEdit("1");
        ivbMManagerDataForInsert.add(temp);
        IvbMManagerMapper mapperTemp = Mockito.mock(IvbMManagerMapper.class);
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getIvbMManagerMapper()).thenReturn(mapperTemp);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(mapperTemp.insert(ivbMManagerDataForInsert.get(0))).thenReturn(1);
        when(tmp.deleteData(any(HashMap.class))).thenReturn(0);
        when(tmp.getLastUpdateDate(any(HashMap.class))).thenReturn(date);
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        // insert 
        String result = bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(ivbMManagerDataForInsert);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
        
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertData() {
        ArrayList<Bnn0087IvbMManager> ivbMManagerDataForInsert = new ArrayList<Bnn0087IvbMManager>();
        
        List<Bnn0088SearchAreaResult> bnn0088SearchAreaResult = new ArrayList<Bnn0088SearchAreaResult>();
        
        Bnn0087IvbMManager temp =  new Bnn0087IvbMManager();
        temp.setFarmId("F0002");
        temp.setAreaId("A0002");
        temp.setUsersId("A0001");
        temp.setTypeIdEdit("null");
        ivbMManagerDataForInsert.add(temp);
        
        Bnn0088SearchAreaResult resultCheckAreaManager = new Bnn0088SearchAreaResult();
        resultCheckAreaManager.setFarmId("F0001");
        resultCheckAreaManager.setAreaId("A0001");
        bnn0088SearchAreaResult.add(resultCheckAreaManager);
        
        IvbMManagerMapper mapperTemp = Mockito.mock(IvbMManagerMapper.class);
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getIvbMManagerMapper()).thenReturn(mapperTemp);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(mapperTemp.insert(ivbMManagerDataForInsert.get(0))).thenReturn(1);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088SearchAreaResult);
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        // insert 
        String result = bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(ivbMManagerDataForInsert);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_MANAGER, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertData1() {
        ArrayList<Bnn0087IvbMManager> ivbMManagerDataForInsert = new ArrayList<Bnn0087IvbMManager>();
        
        List<Bnn0088SearchAreaResult> bnn0088SearchAreaResult = new ArrayList<Bnn0088SearchAreaResult>();
        
        Bnn0087IvbMManager temp =  new Bnn0087IvbMManager();
        temp.setFarmId("F0002");
        temp.setAreaId("A0002");
        temp.setUsersId("A0001");
        temp.setTypeIdEdit("null");
        ivbMManagerDataForInsert.add(temp);
        Bnn0088SearchAreaResult resultCheckAreaManager = new Bnn0088SearchAreaResult();
        resultCheckAreaManager.setFarmId("F0002");
        resultCheckAreaManager.setAreaId("A0001");
        bnn0088SearchAreaResult.add(resultCheckAreaManager);
        
        IvbMManagerMapper mapperTemp = Mockito.mock(IvbMManagerMapper.class);
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getIvbMManagerMapper()).thenReturn(mapperTemp);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(mapperTemp.insert(ivbMManagerDataForInsert.get(0))).thenReturn(1);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088SearchAreaResult);
        when(tmp.deleteData(any(HashMap.class))).thenReturn(0);
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        // insert 
        String result = bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(ivbMManagerDataForInsert);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_MANAGER, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertData2() throws ParseException {
        ArrayList<Bnn0087IvbMManager> ivbMManagerDataForInsert = new ArrayList<Bnn0087IvbMManager>();
        
        List<Bnn0088SearchAreaResult> bnn0088SearchAreaResult = new ArrayList<Bnn0088SearchAreaResult>();
        
        String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
        Bnn0087IvbMManager temp =  new Bnn0087IvbMManager();
        temp.setFarmId("F0002");
        temp.setAreaId("A0002");
        temp.setUsersId("A0001");
        temp.setTypeIdEdit("1");
        temp.setLastUpdateDate(date);
        ivbMManagerDataForInsert.add(temp);
        
        IvbMManagerMapper mapperTemp = Mockito.mock(IvbMManagerMapper.class);
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getIvbMManagerMapper()).thenReturn(mapperTemp);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(mapperTemp.insert(ivbMManagerDataForInsert.get(0))).thenReturn(1);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088SearchAreaResult);
        when(tmp.deleteData(any(HashMap.class))).thenReturn(0);
        when(tmp.getLastUpdateDate(any(HashMap.class))).thenReturn(date);
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        // insert 
        String result = bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(ivbMManagerDataForInsert);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_AFFILIATION, result);
    }

    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertData3() throws ParseException {
        ArrayList<Bnn0087IvbMManager> ivbMManagerDataForInsert = new ArrayList<Bnn0087IvbMManager>();
        
        List<Bnn0088SearchAreaResult> bnn0088SearchAreaResult = new ArrayList<Bnn0088SearchAreaResult>();
        
        String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
        Bnn0087IvbMManager temp =  new Bnn0087IvbMManager();
        temp.setFarmId("F0002");
        temp.setAreaId("A0002");
        temp.setUsersId("A0001");
        temp.setTypeIdEdit("1");
        temp.setLastUpdateDate(date);
        ivbMManagerDataForInsert.add(temp);
        
        IvbMManagerMapper mapperTemp = Mockito.mock(IvbMManagerMapper.class);
        Bnn0087SearchAffiliationMapper tmp = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getIvbMManagerMapper()).thenReturn(mapperTemp);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmp);
        when(mapperTemp.insert(ivbMManagerDataForInsert.get(0))).thenReturn(1);
        when(tmp.checkAreaManager(any(HashMap.class))).thenReturn(bnn0088SearchAreaResult);
        when(tmp.deleteData(any(HashMap.class))).thenReturn(0);
        when(tmp.getLastUpdateDate(any(HashMap.class))).thenReturn(format.parse("2017/05/18 13:25:25"));
        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);
        // insert 
        String result = bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(ivbMManagerDataForInsert);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_AFFILIATION_LAST_UPDATE_DATE, result);
    }
    
    ///////////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataWithMoreManagerRepeatInsertTrueAuthorization1AndTypeIdEdit0() {

    	ArrayList<Bnn0087IvbMManager> managerDataList = new ArrayList<Bnn0087IvbMManager>();
    	Bnn0087IvbMManager managerData = new Bnn0087IvbMManager();
    	managerData.setUsersId("UserTest");
    	managerData.setAuthorizationTypeId("1");
    	managerData.setTypeIdEdit("null");
    	managerData.setFarmId("F0001");
    	managerData.setAreaId("A0001");
    	managerData.setDeleteFlag(false);
    	managerDataList.add(managerData);

    	ArrayList<Bnn0088SearchAreaResult> areaDataList = new ArrayList<Bnn0088SearchAreaResult>();
        Bnn0088SearchAreaResult areaData = new Bnn0088SearchAreaResult();
        areaData.setFarmId("F0001");
        areaData.setAreaId("A0001");
        areaDataList.add(areaData);
        areaData = new Bnn0088SearchAreaResult();
        areaData.setFarmId("F0001");
        areaData.setAreaId("A0001");
        areaDataList.add(areaData);

        bnn0087SearchAffiliationService_Mock.setAppContext(appContext);

    	// set up mock
    	Bnn0087SearchAffiliationMapper tmpArea = Mockito.mock(Bnn0087SearchAffiliationMapper.class);
        when(bnn0087SearchAffiliationDao.getBnn0087SearchAffiliationMapper()).thenReturn(tmpArea);
    	when(tmpArea.checkAreaManager(any(HashMap.class))).thenReturn(areaDataList);

        String result = bnn0087SearchAffiliationService_Mock.insertDataForAffiliation(managerDataList);
        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result); 
    }

}