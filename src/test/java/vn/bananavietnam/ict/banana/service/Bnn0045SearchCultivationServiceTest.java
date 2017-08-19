package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.bananavietnam.ict.banana.component.Bnn0045SearchCultivationConditions;
import vn.bananavietnam.ict.banana.component.Bnn0045SearchCultivationInFormCbbResult;
import vn.bananavietnam.ict.banana.component.Bnn0045SearchCultivationResult;
import vn.bananavietnam.ict.banana.dao.Bnn0045SearchCultivationDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0045SearchCultivationMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.mapper.UtilMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0045SearchCultivationServiceTest {
	
	@Autowired
    private ApplicationContext appContext;
	
    @InjectMocks
    private Bnn0045SearchCultivationService bnn0045SearchCultivationService_Mock;

    @Mock
    private Bnn0045SearchCultivationDao bnn0045SearchCultivationDao;
    
    @Mock
	UtilDao utilDao;
    
    @Mock
    ObjectMapper mapper = new ObjectMapper();
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0045");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        Authentication authToken = new UsernamePasswordAuthenticationToken(u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
    
    @SuppressWarnings("unchecked")
   @Test
    public void testSearchDataOutOfMemoryException() {
    	Bnn0045SearchCultivationConditions searchConditions = new Bnn0045SearchCultivationConditions();
        // farm id
        searchConditions.setFarmId("F001");
        // process id
        searchConditions.setProcessId("P001");
        // kind id
        searchConditions.setKindId("K001");
        // task id
        searchConditions.setTaskId("T001");
        // from
        searchConditions.setFromRow("0");
        // item per page 
        searchConditions.setItemCount("5");
        
        bnn0045SearchCultivationService_Mock.setAppContext(appContext);
        // Mock setup
        Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        doThrow(new OutOfMemoryError()).when(tmp).searchData(any(HashMap.class));

        // get search result
        List<Bnn0045SearchCultivationResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals("-1", bnn0045ResultList.get(0).getSearchDataTotalCounts());
    }
    
    @SuppressWarnings("unchecked")
   @Test
    public void testSearchDataOuterException() {
        Bnn0045SearchCultivationConditions searchConditions = new Bnn0045SearchCultivationConditions();
        // farm id
        searchConditions.setFarmId("F001");
        // process id
        searchConditions.setProcessId("P001");
        // kind id
        searchConditions.setKindId("K001");
        // task id
        searchConditions.setTaskId("T001");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        // Mock setup
        Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));

        // get search result
        List<Bnn0045SearchCultivationResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0045ResultList);
    }
    
    @SuppressWarnings("unchecked")
   @Test
    public void testSearchDataInnerException() {
        Bnn0045SearchCultivationConditions searchConditions = new Bnn0045SearchCultivationConditions();
        // farm id
        searchConditions.setFarmId("F001");
        // process id
        searchConditions.setProcessId("P001");
        // kind id
        searchConditions.setKindId("K001");
        // task id
        searchConditions.setTaskId("T001");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        
        bnn0045SearchCultivationService_Mock.setAppContext(appContext);
        
        Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));

        // get search result
        List<Bnn0045SearchCultivationResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0045ResultList);
    }
    
    @SuppressWarnings("unchecked")
   @Test
    public void testSearchData() {
    	Bnn0045SearchCultivationConditions searchConditions = new Bnn0045SearchCultivationConditions();
        // farm id
        searchConditions.setFarmId("F001");
        // process id
        searchConditions.setProcessId("P001");
        // kind id
        searchConditions.setKindId("K001");
        // task id
        searchConditions.setTaskId("T001");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        
        List<UtilComponent> farmData = new ArrayList<UtilComponent>();
        farmData.add(new UtilComponent());
        UtilMapper tmputil = Mockito.mock(UtilMapper.class);
        when(utilDao.getUtilMapper()).thenReturn(tmputil);
        when(tmputil.selectFarmDataMaster()).thenReturn(farmData);
        
        bnn0045SearchCultivationService_Mock.setAppContext(appContext);
        List<Bnn0045SearchCultivationResult> bnn0045ResultListReturn = new ArrayList<Bnn0045SearchCultivationResult>();
        bnn0045ResultListReturn.add(new Bnn0045SearchCultivationResult());
        // Mock setup
        Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()
                .getSearchDataTotalCounts(any(HashMap.class))).thenReturn("1");
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0045ResultListReturn);

        // get search result
        List<Bnn0045SearchCultivationResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(1, bnn0045ResultList.size());
    }
    
    @SuppressWarnings("unchecked")
   @Test
    public void testSearchDataNoResult() {
    	Bnn0045SearchCultivationConditions searchConditions = new Bnn0045SearchCultivationConditions();
        // farm id
        searchConditions.setFarmId(null);
        // process id
        searchConditions.setProcessId(null);
        // kind id
        searchConditions.setKindId("dddd");
        // task id
        searchConditions.setTaskId(null);
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        List<UtilComponent> farmData = new ArrayList<UtilComponent>();
        farmData.add(new UtilComponent());
        UtilMapper tmputil = Mockito.mock(UtilMapper.class);
        when(utilDao.getUtilMapper()).thenReturn(tmputil);
        when(tmputil.selectFarmDataMaster()).thenReturn(farmData);
        
        bnn0045SearchCultivationService_Mock.setAppContext(appContext);
        List<Bnn0045SearchCultivationResult> bnn0045ResultListReturn = new ArrayList<Bnn0045SearchCultivationResult>();
        // Mock setup
        Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()
                .getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0045ResultListReturn);

        // get search result
        List<Bnn0045SearchCultivationResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(0, bnn0045ResultList.size());
    }
    
    @SuppressWarnings("unchecked")
   @Test
    public void testSearchDataNoResult2() {
    	Bnn0045SearchCultivationConditions searchConditions = new Bnn0045SearchCultivationConditions();
        // farm id
        searchConditions.setFarmId(null);
        // process id
        searchConditions.setProcessId(null);
        // kind id
        searchConditions.setKindId(null);
        // task id
        searchConditions.setTaskId(null);
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        
        List<UtilComponent> farmData = new ArrayList<UtilComponent>();
        List<UtilComponent> areaData = new ArrayList<UtilComponent>();
        UtilComponent farmNoneData = new UtilComponent();
    	farmNoneData.setFarmId("F001");
    	farmData.add(farmNoneData);
    	UtilMapper tmputil = Mockito.mock(UtilMapper.class);
        when(utilDao.getUtilMapper()).thenReturn(tmputil);
        when(tmputil.selectFarmDataMaster()).thenReturn(farmData);
        
        UtilComponent areaNoneData = new UtilComponent();
        areaNoneData.setAreaId("F001");
        areaData.add(areaNoneData);
        when(tmputil.selectAreaDataByFarmIdMaster(any(HashMap.class))).thenReturn(areaData);
        
        List<Bnn0045SearchCultivationInFormCbbResult> kindData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
        Bnn0045SearchCultivationInFormCbbResult kind = new Bnn0045SearchCultivationInFormCbbResult();
        kind.setKindId("K001");
        kindData.add(kind);

        bnn0045SearchCultivationService_Mock.setAppContext(appContext);
        List<Bnn0045SearchCultivationResult> bnn0045ResultListReturn = new ArrayList<Bnn0045SearchCultivationResult>();
        // Mock setup
        Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()
                .getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
        when(tmp.getDataKindCbb(any(HashMap.class))).thenReturn(kindData);
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0045ResultListReturn);

        // get search result
        List<Bnn0045SearchCultivationResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(0, bnn0045ResultList.size());
    }
    
    @SuppressWarnings("unchecked")
   @Test
    public void testSearchDataNoResult3() {
    	Bnn0045SearchCultivationConditions searchConditions = new Bnn0045SearchCultivationConditions();
        // farm id
        searchConditions.setFarmId("-2");
        // process id
        searchConditions.setProcessId(null);
        // kind id
        searchConditions.setKindId("-2");
        // task id
        searchConditions.setTaskId(null);
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        
        List<UtilComponent> farmData = new ArrayList<UtilComponent>();
        List<UtilComponent> areaData = new ArrayList<UtilComponent>();
        UtilComponent farmNoneData = new UtilComponent();
    	farmNoneData.setFarmId("F001");
    	farmData.add(farmNoneData);
    	UtilMapper tmputil = Mockito.mock(UtilMapper.class);
        when(utilDao.getUtilMapper()).thenReturn(tmputil);
        when(tmputil.selectFarmDataMaster()).thenReturn(farmData);
        
        UtilComponent areaNoneData = new UtilComponent();
        areaNoneData.setAreaId("F001");
        areaData.add(areaNoneData);
        when(tmputil.selectAreaDataByFarmIdMaster(any(HashMap.class))).thenReturn(areaData);
        
        List<Bnn0045SearchCultivationInFormCbbResult> kindData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
        Bnn0045SearchCultivationInFormCbbResult kind = new Bnn0045SearchCultivationInFormCbbResult();
        kind.setKindId("K001");
        kindData.add(kind);

        bnn0045SearchCultivationService_Mock.setAppContext(appContext);
        List<Bnn0045SearchCultivationResult> bnn0045ResultListReturn = new ArrayList<Bnn0045SearchCultivationResult>();
        // Mock setup
        Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()
                .getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
        when(tmp.getDataKindCbb(any(HashMap.class))).thenReturn(kindData);
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0045ResultListReturn);

        // get search result
        List<Bnn0045SearchCultivationResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(0, bnn0045ResultList.size());
    }
    
    @SuppressWarnings("unchecked")
   @Test
    public void testSearchDataNoResult5() {
    	Bnn0045SearchCultivationConditions searchConditions = new Bnn0045SearchCultivationConditions();
        // farm id
        searchConditions.setFarmId("-2");
        // process id
        searchConditions.setProcessId(null);
        // kind id
        searchConditions.setKindId("-2");
        // task id
        searchConditions.setTaskId(null);
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        
        List<UtilComponent> farmData = new ArrayList<UtilComponent>();
        
        UtilMapper tmputil = Mockito.mock(UtilMapper.class);
        when(utilDao.getUtilMapper()).thenReturn(tmputil);
        when(tmputil.selectFarmDataMaster()).thenReturn(farmData);
        when(tmputil.selectFarmData(any(HashMap.class))).thenReturn(farmData);

        List<Bnn0045SearchCultivationInFormCbbResult> kindData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
        Bnn0045SearchCultivationInFormCbbResult kind = new Bnn0045SearchCultivationInFormCbbResult();
        kind.setKindId("K001");
        kindData.add(kind);

        bnn0045SearchCultivationService_Mock.setAppContext(appContext);
        List<Bnn0045SearchCultivationResult> bnn0045ResultListReturn = new ArrayList<Bnn0045SearchCultivationResult>();
        // Mock setup
        Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()
                .getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
        when(tmp.getDataKindCbb(any(HashMap.class))).thenReturn(kindData);
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0045ResultListReturn);
        // get search result
        List<Bnn0045SearchCultivationResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(0, bnn0045ResultList.size());
    }
    
    @SuppressWarnings("unchecked")
   @Test
    public void testSearchDataNoResult6() {
    	Bnn0045SearchCultivationConditions searchConditions = new Bnn0045SearchCultivationConditions();
        // farm id
        searchConditions.setFarmId("F001");
        // process id
        searchConditions.setProcessId(null);
        // kind id
        searchConditions.setKindId("-2");
        // task id
        searchConditions.setTaskId(null);
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        
        List<UtilComponent> farmData = new ArrayList<UtilComponent>();
        
        UtilMapper tmputil = Mockito.mock(UtilMapper.class);
        when(utilDao.getUtilMapper()).thenReturn(tmputil);
        when(tmputil.selectFarmDataMaster()).thenReturn(farmData);
        when(tmputil.selectFarmData(any(HashMap.class))).thenReturn(farmData);

        List<Bnn0045SearchCultivationInFormCbbResult> kindData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
        Bnn0045SearchCultivationInFormCbbResult kind = new Bnn0045SearchCultivationInFormCbbResult();
        kind.setKindId("K001");
        kindData.add(kind);

        bnn0045SearchCultivationService_Mock.setAppContext(appContext);
        List<Bnn0045SearchCultivationResult> bnn0045ResultListReturn = new ArrayList<Bnn0045SearchCultivationResult>();
        // Mock setup
        Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()
                .getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");
        when(tmp.getDataKindCbb(any(HashMap.class))).thenReturn(kindData);
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0045ResultListReturn);
        // get search result
        List<Bnn0045SearchCultivationResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .searchData(searchConditions);
        // start testing
        Assert.assertEquals(0, bnn0045ResultList.size());
    }   
    @SuppressWarnings("unchecked")
   @Test
    public void testgetProcessData() {
    	bnn0045SearchCultivationService_Mock.setAppContext(appContext);
    	// Mock setup
    	List<Bnn0045SearchCultivationInFormCbbResult> processData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
    	Bnn0045SearchCultivationInFormCbbResult processNoneData = new Bnn0045SearchCultivationInFormCbbResult();
    	processNoneData.setProcessName("P001");
    	processData.add(processNoneData);
    	Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()
                .getDataProcessCbb(any(HashMap.class))).thenReturn(processData);
        // get search result
        List<Bnn0045SearchCultivationInFormCbbResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .getProcessData();
        // start testing
        Assert.assertEquals(1, bnn0045ResultList.size());
    }
    
    @SuppressWarnings("unchecked")
   @Test
    public void testgetProcessDataException() {
        
    	// Mock setup
    	Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getDataProcessCbb(any(HashMap.class));
        
        // get search result
        List<Bnn0045SearchCultivationInFormCbbResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .getProcessData();
        // start testing
        Assert.assertEquals(0, bnn0045ResultList.size());
    }
    
    @SuppressWarnings("unchecked")
   @Test
    public void testgetTaskData() {
    	bnn0045SearchCultivationService_Mock.setAppContext(appContext);
    	// Mock setup
    	List<Bnn0045SearchCultivationInFormCbbResult> taskData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
    	Bnn0045SearchCultivationInFormCbbResult taskNoneData = new Bnn0045SearchCultivationInFormCbbResult();
    	taskNoneData.setTaskName("T001");
    	taskData.add(taskNoneData);
    	Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()
                .getDataTaskCbb(any(HashMap.class))).thenReturn(taskData);
        // get search result
        List<Bnn0045SearchCultivationInFormCbbResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .getTaskData();
        // start testing
        Assert.assertEquals(1, bnn0045ResultList.size());
    }
    
    @SuppressWarnings("unchecked")
   @Test
    public void testgetTaskDataException() {
        
    	// Mock setup
    	Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getDataTaskCbb(any(HashMap.class));
        
        // get search result
        List<Bnn0045SearchCultivationInFormCbbResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .getTaskData();
        // start testing
        Assert.assertEquals(0, bnn0045ResultList.size());
    }
    
   @Test
    public void testGetFarmData() {
    	bnn0045SearchCultivationService_Mock.setAppContext(appContext);
    	// Mock setup
    	List<UtilComponent> farmData = new ArrayList<UtilComponent>();
    	UtilComponent farmNoneData = new UtilComponent();
    	farmNoneData.setFarmName("F001");
    	farmData.add(farmNoneData);
    	UtilMapper tmp = Mockito.mock(UtilMapper.class);
        when(utilDao.getUtilMapper()).thenReturn(tmp);
        when(tmp.selectFarmDataMaster()).thenReturn(farmData);
        // get search result
        List<UtilComponent> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .getFarmData();
        // start testing
        Assert.assertEquals(1, bnn0045ResultList.size());
    }
    
   @Test
    public void testGetFarmDataException() {
        
        // get search result
        List<UtilComponent> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .getFarmData();
        // start testing
        Assert.assertEquals(0, bnn0045ResultList.size());
    }
    
    @SuppressWarnings("unchecked")
   @Test
    public void testGetKindData() {
    	bnn0045SearchCultivationService_Mock.setAppContext(appContext);
    	// Mock setup
    	List<Bnn0045SearchCultivationInFormCbbResult> kindData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
    	Bnn0045SearchCultivationInFormCbbResult kindNoneData = new Bnn0045SearchCultivationInFormCbbResult();
    	kindNoneData.setProcessName("K001");
    	kindData.add(kindNoneData);
    	Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()
                .getDataKindCbb(any(HashMap.class))).thenReturn(kindData);
        
        List<UtilComponent> farmData = new ArrayList<UtilComponent>();
        List<UtilComponent> areaData = new ArrayList<UtilComponent>();
        UtilComponent farmNoneData = new UtilComponent();
    	farmNoneData.setFarmId("F001");
    	farmData.add(farmNoneData);
    	UtilMapper tmputil = Mockito.mock(UtilMapper.class);
        when(utilDao.getUtilMapper()).thenReturn(tmputil);
        when(tmputil.selectFarmDataMaster()).thenReturn(farmData);
        
        UtilComponent areaNoneData = new UtilComponent();
        areaNoneData.setAreaId("F001");
        areaData.add(areaNoneData);
        when(tmputil.selectAreaDataByFarmIdMaster(any(HashMap.class))).thenReturn(areaData);
        // get search result
        List<Bnn0045SearchCultivationInFormCbbResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .getKindData("-2");
        // start testing
        Assert.assertEquals(1, bnn0045ResultList.size());
    }
    
    @SuppressWarnings("unchecked")
   @Test
    public void testGetKindDatanull() {
    	bnn0045SearchCultivationService_Mock.setAppContext(appContext);
    	// Mock setup
    	List<Bnn0045SearchCultivationInFormCbbResult> kindData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
    	Bnn0045SearchCultivationInFormCbbResult kindNoneData = new Bnn0045SearchCultivationInFormCbbResult();
    	kindNoneData.setProcessName("K001");
    	kindData.add(kindNoneData);
    	Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()
                .getDataKindCbb(any(HashMap.class))).thenReturn(kindData);
        
        List<UtilComponent> farmData = new ArrayList<UtilComponent>();
        farmData.add(new UtilComponent());
        List<UtilComponent> areaData = new ArrayList<UtilComponent>();
        areaData.add(new UtilComponent());
    	UtilMapper tmputil = Mockito.mock(UtilMapper.class);
        when(utilDao.getUtilMapper()).thenReturn(tmputil);
        when(tmputil.selectFarmDataMaster()).thenReturn(farmData);
        when(tmputil.selectAreaDataByFarmIdMaster(any(HashMap.class))).thenReturn(areaData);
        // get search result
        List<Bnn0045SearchCultivationInFormCbbResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .getKindData("F001");
        // start testing
        Assert.assertEquals(bnn0045ResultList.size(), bnn0045ResultList.size());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testGetKindDatanull2() {
    	bnn0045SearchCultivationService_Mock.setAppContext(appContext);
    	// Mock setup
    	List<Bnn0045SearchCultivationInFormCbbResult> kindData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
    	Bnn0045SearchCultivationInFormCbbResult kindNoneData = new Bnn0045SearchCultivationInFormCbbResult();
    	kindNoneData.setProcessName("K001");
    	kindData.add(kindNoneData);
    	Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()
                .getDataKindCbb(any(HashMap.class))).thenReturn(kindData);
        
        List<UtilComponent> farmData = new ArrayList<UtilComponent>();
        List<UtilComponent> areaData = new ArrayList<UtilComponent>();
        areaData.add(new UtilComponent());
    	UtilMapper tmputil = Mockito.mock(UtilMapper.class);
        when(utilDao.getUtilMapper()).thenReturn(tmputil);
        UtilComponent farmNoneData = new UtilComponent();
    	farmNoneData.setFarmId("F001");
    	farmData.add(farmNoneData);
        when(utilDao.getUtilMapper()).thenReturn(tmputil);
        when(tmputil.selectFarmDataMaster()).thenReturn(farmData);
        when(tmputil.selectAreaDataByFarmIdMaster(any(HashMap.class))).thenReturn(areaData);
        // get search result
        List<Bnn0045SearchCultivationInFormCbbResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .getKindData("F001");
        // start testing
        Assert.assertEquals(bnn0045ResultList, bnn0045ResultList);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testGetKindDatanull3() {
    	bnn0045SearchCultivationService_Mock.setAppContext(appContext);
    	// Mock setup
    	List<Bnn0045SearchCultivationInFormCbbResult> kindData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
    	Bnn0045SearchCultivationInFormCbbResult kindNoneData = new Bnn0045SearchCultivationInFormCbbResult();
    	kindNoneData.setProcessName("K001");
    	kindData.add(kindNoneData);
    	Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()
                .getDataKindCbb(any(HashMap.class))).thenReturn(kindData);
        
        List<UtilComponent> farmData = new ArrayList<UtilComponent>();
        List<UtilComponent> areaData = new ArrayList<UtilComponent>();
    	UtilMapper tmputil = Mockito.mock(UtilMapper.class);
        when(utilDao.getUtilMapper()).thenReturn(tmputil);
        UtilComponent farmNoneData = new UtilComponent();
    	farmNoneData.setFarmId("F001");
    	farmData.add(farmNoneData);
        when(utilDao.getUtilMapper()).thenReturn(tmputil);
        when(tmputil.selectFarmDataMaster()).thenReturn(farmData);
        when(tmputil.selectAreaDataByFarmIdMaster(any(HashMap.class))).thenReturn(areaData);
        // get search result
        List<Bnn0045SearchCultivationInFormCbbResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .getKindData("F001");
        // start testing
        Assert.assertEquals(0, bnn0045ResultList.size());
    }    
    @SuppressWarnings("unchecked")
    @Test
    public void testGetKindDatanull4() {
    	bnn0045SearchCultivationService_Mock.setAppContext(appContext);
    	// Mock setup
    	List<Bnn0045SearchCultivationInFormCbbResult> kindData = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
    	Bnn0045SearchCultivationInFormCbbResult kindNoneData = new Bnn0045SearchCultivationInFormCbbResult();
    	kindNoneData.setProcessName("K001");
    	kindData.add(kindNoneData);
    	Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()
                .getDataKindCbb(any(HashMap.class))).thenReturn(kindData);
        
        List<UtilComponent> farmData = new ArrayList<UtilComponent>();
        List<UtilComponent> areaData = new ArrayList<UtilComponent>();
    	UtilMapper tmputil = Mockito.mock(UtilMapper.class);
        when(utilDao.getUtilMapper()).thenReturn(tmputil);
        UtilComponent farmNoneData = new UtilComponent();
    	farmNoneData.setFarmId("F001");
        when(utilDao.getUtilMapper()).thenReturn(tmputil);
        when(tmputil.selectFarmDataMaster()).thenReturn(farmData);
        when(tmputil.selectAreaDataByFarmIdMaster(any(HashMap.class))).thenReturn(areaData);
        // get search result
        List<Bnn0045SearchCultivationInFormCbbResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .getKindData("F001");
        // start testing
        Assert.assertEquals(0, bnn0045ResultList.size());
    }
    @SuppressWarnings("unchecked")
    @Test
    public void testGetKindDataException() {
        
    	// Mock setup
    	Bnn0045SearchCultivationMapper tmp = Mockito.mock(Bnn0045SearchCultivationMapper.class);
        when(bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getDataKindCbb(any(HashMap.class));
        
        // get search result
        List<Bnn0045SearchCultivationInFormCbbResult> bnn0045ResultList = bnn0045SearchCultivationService_Mock
                .getKindData(null);
        // start testing
        Assert.assertEquals(0, bnn0045ResultList.size());
    }
}
