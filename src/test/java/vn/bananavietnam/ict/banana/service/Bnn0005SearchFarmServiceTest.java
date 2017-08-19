package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import vn.bananavietnam.ict.banana.component.Bnn0005SearchFarmConditions;
import vn.bananavietnam.ict.banana.component.Bnn0005SearchFarmDataObject;
import vn.bananavietnam.ict.banana.component.Bnn0005SearchFarmResult;
import vn.bananavietnam.ict.banana.dao.Bnn0005SearchFarmDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0005SearchFarmMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.UtilMapper;
import vn.bananavietnam.ict.common.db.model.IvbMArea;
import vn.bananavietnam.ict.common.db.model.IvbMFarm;
import vn.bananavietnam.ict.common.db.model.IvbMFarmExample;
import vn.bananavietnam.ict.common.db.model.IvbTCultivationResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0005SearchFarmServiceTest {
	@Autowired
	private ApplicationContext appContext;

	@InjectMocks
	private Bnn0005SearchFarmService service;

	@Mock
	UtilDao utilDao;
	@Mock
	Bnn0005SearchFarmDao dao;

	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0005");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
	}

	@Test
	public void testInitData() {
		// Mock setup
		Model model = Mockito.mock(Model.class);

		// Mock setup
		ArrayList<UtilComponent> farmData = new ArrayList<UtilComponent>();
		UtilComponent item = new UtilComponent();
		item.setFarmName("111");
		farmData.add(item);
		
		service.setAppContext(appContext);
		
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
    	when(utilDao.getUtilMapper()).thenReturn(tmp);
        when(tmp.selectFarmDataMaster()).thenReturn(farmData);

        // start testing
		service.initData(model);
	}

	@Test
	public void testGetFarmName() {
		// Mock setup
		ArrayList<UtilComponent> farmData = new ArrayList<UtilComponent>();
		UtilComponent item = new UtilComponent();
		item.setFarmName("111");
		farmData.add(item);
		
		service.setAppContext(appContext);
		
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
    	when(utilDao.getUtilMapper()).thenReturn(tmp);
        when(tmp.selectFarmDataMaster()).thenReturn(farmData);

        // start testing
		service.getFarmName();
	}

	@Test
	public void testInitDataException() {

		ExtendedModelMap model = new ExtendedModelMap();

		// Mock setup
		IvbMFarmMapper tmp = Mockito.mock(IvbMFarmMapper.class);
    	when(dao.getIvbMFarmMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).selectByExample(any(IvbMFarmExample.class));

        // start testing
		service.initData(model);
	}

	@SuppressWarnings("unchecked")
    @Test
    public void testSearchData() {

        Bnn0005SearchFarmConditions searchConditions = new Bnn0005SearchFarmConditions();
        // farm id
        searchConditions.setFarmId("");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        
        service.setAppContext(appContext);

        List<Bnn0005SearchFarmResult> bnn0005ResultListReturn = new ArrayList<Bnn0005SearchFarmResult>();
        bnn0005ResultListReturn.add(new Bnn0005SearchFarmResult());
        // Mock setup
        Bnn0005SearchFarmMapper tmp = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp);
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0005ResultListReturn);
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("1");

        // get search result
        List<Bnn0005SearchFarmResult> bnn0005ResultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(1, bnn0005ResultList.size());
    }
	
	@SuppressWarnings("unchecked")
    @Test
    public void testSearchDataWithFarmId() {
        Bnn0005SearchFarmConditions searchConditions = new Bnn0005SearchFarmConditions();
        // farm id
        searchConditions.setFarmId("F001");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        
        service.setAppContext(appContext);

        List<Bnn0005SearchFarmResult> bnn0005ResultListReturn = new ArrayList<Bnn0005SearchFarmResult>();
        bnn0005ResultListReturn.add(new Bnn0005SearchFarmResult());
        // Mock setup
        Bnn0005SearchFarmMapper tmp = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp);
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0005ResultListReturn);
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("1");

        // get search result
        List<Bnn0005SearchFarmResult> bnn0005ResultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(1, bnn0005ResultList.size());
    }

	@SuppressWarnings("unchecked")
    @Test
    public void testSearchDataWithoutFarmId() {
        Bnn0005SearchFarmConditions searchConditions = new Bnn0005SearchFarmConditions();
        // farm id
        searchConditions.setFarmId(null);
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        
        service.setAppContext(appContext);

        List<Bnn0005SearchFarmResult> bnn0005ResultListReturn = new ArrayList<Bnn0005SearchFarmResult>();
        bnn0005ResultListReturn.add(new Bnn0005SearchFarmResult());
        // Mock setup
        Bnn0005SearchFarmMapper tmp = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp);
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0005ResultListReturn);
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("1");

        // get search result
        List<Bnn0005SearchFarmResult> bnn0005ResultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(1, bnn0005ResultList.size());
    }

	@SuppressWarnings("unchecked")
    @Test
    public void testSearchDataNoResult() {
        Bnn0005SearchFarmConditions searchConditions = new Bnn0005SearchFarmConditions();
        // farm id
        searchConditions.setFarmId("F001");
        // from 
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        
        service.setAppContext(appContext);

        List<Bnn0005SearchFarmResult> bnn0005ResultListReturn = new ArrayList<Bnn0005SearchFarmResult>();
        // Mock setup
        Bnn0005SearchFarmMapper tmp = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp);
        when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0005ResultListReturn);
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");

        // get search result
        List<Bnn0005SearchFarmResult> bnn0005ResultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(0, bnn0005ResultList.size());
    }
	
	@SuppressWarnings("unchecked")
    @Test
    public void testSearchDataOutOfMemoryException() {

        Bnn0005SearchFarmConditions searchConditions = new Bnn0005SearchFarmConditions();
        // farm id
        searchConditions.setFarmId("F001");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");
        
        service.setAppContext(appContext);

        // Mock setup
        Bnn0005SearchFarmMapper tmp = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp);
        doThrow(new OutOfMemoryError()).when(tmp).searchData(any(HashMap.class));

        // get search result
        List<Bnn0005SearchFarmResult> bnn0005ResultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals("-1", bnn0005ResultList.get(0).getSearchDataTotalCounts());
	}
	
	@SuppressWarnings("unchecked")
    @Test
    public void testSearchDataException() {

        Bnn0005SearchFarmConditions searchConditions = new Bnn0005SearchFarmConditions();
        // farm id
        searchConditions.setFarmId("F001");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");

        // Mock setup
        Bnn0005SearchFarmMapper tmp = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));
        
        // get search result
        List<Bnn0005SearchFarmResult> bnn0005ResultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0005ResultList);
	}

	@SuppressWarnings("unchecked")
    @Test
    public void testSearchDataExceptionCache() {

        Bnn0005SearchFarmConditions searchConditions = new Bnn0005SearchFarmConditions();
        // farm id
        searchConditions.setFarmId("F001");
        // from
        searchConditions.setFromRow("0");
        // item per page
        searchConditions.setItemCount("5");

        service.setAppContext(appContext);
        
        // Mock setup
        Bnn0005SearchFarmMapper tmp = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));
        
        // get search result
        List<Bnn0005SearchFarmResult> bnn0005ResultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0005ResultList);
	}
	
    @Test
    public void testGetSingleData() {
		// farm id
        String farmId = "F001";

        Bnn0005SearchFarmDataObject bnn0005ObjectReturn = new Bnn0005SearchFarmDataObject();
        bnn0005ObjectReturn.setFarmId("F001");
        
        service.setAppContext(appContext);
        
        // Mock setup
        Bnn0005SearchFarmMapper tmp = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp);
        when(tmp.getSingleData(any(String.class))).thenReturn(bnn0005ObjectReturn);

        // get single data result
        Bnn0005SearchFarmDataObject bnn0005Object = service.getSingleData(farmId);
        // start testing
        Assert.assertEquals("F001", bnn0005Object.getFarmId());
    }
    
    @Test
    public void testGetSingleDataExceptionCache() {

		// farm id
        String farmId = "F001";

        Bnn0005SearchFarmDataObject bnn0005ObjectReturn = new Bnn0005SearchFarmDataObject();
        bnn0005ObjectReturn.setFarmId("F001");

        // Mock setup
        Bnn0005SearchFarmMapper tmp = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp);
        when(tmp.getSingleData(any(String.class))).thenReturn(bnn0005ObjectReturn);

        // get single data result
        Bnn0005SearchFarmDataObject bnn0005Object = service.getSingleData(farmId);
        // start testing
        Assert.assertEquals(null, bnn0005Object);
	}
    
    @Test
    @Transactional
    public void testInputInsertUserIdBlankFields() {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm name
    	bnn0005Object.setFarmName("");
    	// user id
    	bnn0005Object.setUsersId("A0001");
        
        // input data result
        String result = service.insertData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }
    
    @Test
    @Transactional
    public void testInputUpdateUserIdBlankFields() {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm name
    	bnn0005Object.setFarmName("");
    	// user id
    	bnn0005Object.setUsersId("A0001");
        
        // input data result
        String result = service.updateData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDataSuccess() {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm name
    	bnn0005Object.setFarmName("Farm 006");
    	// farm colection
    	bnn0005Object.setFarmLocation("Farm Lacation");
    	// Climate 
    	bnn0005Object.setClimate("Climate");
    	// soil 
    	bnn0005Object.setSoil("Climate");
    	// user id
    	bnn0005Object.setUsersId("A0001");

    	service.setAppContext(appContext);

    	Bnn0005SearchFarmMapper tmp0005 = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp0005);
        when(tmp0005.getLastFarmId(any(HashMap.class))).thenReturn("F005");

        IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
    	when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
        when(tmpFarm.insert(any(IvbMFarm.class))).thenReturn(1);

        IvbMAreaMapper tmpArea = Mockito.mock(IvbMAreaMapper.class);
    	when(dao.getIvbMAreaMapper()).thenReturn(tmpArea);
        when(tmpArea.insert(any(IvbMArea.class))).thenReturn(1);
        
        // insert data result
        String result = service.insertData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDatagetAllFarmName() {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm name
    	bnn0005Object.setFarmName("Farm 006");
    	// farm colection
    	bnn0005Object.setFarmLocation("Farm Lacation");
    	// Climate 
    	bnn0005Object.setClimate("Climate");
    	// soil 
    	bnn0005Object.setSoil("Climate");
    	// user id
    	bnn0005Object.setUsersId("A0001");

    	service.setAppContext(appContext);
    	List<IvbMFarm> listAllFarmName = new ArrayList<IvbMFarm>();
    	IvbMFarm ivbMFarm = new IvbMFarm();
    	ivbMFarm.setFarmName("Farm 006");
    	listAllFarmName.add(ivbMFarm);
    	Bnn0005SearchFarmMapper tmp0005 = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp0005);
        when(tmp0005.getLastFarmId(any(HashMap.class))).thenReturn("F005");
        when(tmp0005.getAllFarmName(any(HashMap.class))).thenReturn(listAllFarmName);

        IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
    	when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
        when(tmpFarm.insert(any(IvbMFarm.class))).thenReturn(1);

        IvbMAreaMapper tmpArea = Mockito.mock(IvbMAreaMapper.class);
    	when(dao.getIvbMAreaMapper()).thenReturn(tmpArea);
        doThrow(new DuplicateKeyException("")).when(tmpFarm).insert(any(IvbMFarm.class));
        
        // insert data result
        String result = service.insertData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_DUPLICATED, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testInsertDatagetAllFarmNameNUll() {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm name
    	bnn0005Object.setFarmName("Farm 006");
    	// farm colection
    	bnn0005Object.setFarmLocation("Farm Lacation");
    	// Climate 
    	bnn0005Object.setClimate("Climate");
    	// soil 
    	bnn0005Object.setSoil("Climate");
    	// user id
    	bnn0005Object.setUsersId("A0001");

    	service.setAppContext(appContext);
    	List<IvbMFarm> listAllFarmName = new ArrayList<IvbMFarm>();
    	IvbMFarm ivbMFarm = new IvbMFarm();
    	ivbMFarm.setFarmName("Farm 016");
    	listAllFarmName.add(ivbMFarm);
    	Bnn0005SearchFarmMapper tmp0005 = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp0005);
        when(tmp0005.getLastFarmId(any(HashMap.class))).thenReturn("F005");
        when(tmp0005.getAllFarmName(any(HashMap.class))).thenReturn(listAllFarmName);

        IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
    	when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
        when(tmpFarm.insert(any(IvbMFarm.class))).thenReturn(1);

        IvbMAreaMapper tmpArea = Mockito.mock(IvbMAreaMapper.class);
    	when(dao.getIvbMAreaMapper()).thenReturn(tmpArea);
        when(tmpArea.insert(any(IvbMArea.class))).thenReturn(1);
        
        // insert data result
        String result = service.insertData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
    }

    @SuppressWarnings("unchecked")
	@Test
    @Transactional
    public void testInsertDataSuccessNull() {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm name
    	bnn0005Object.setFarmName("Farm 006");
    	// farm colection
    	bnn0005Object.setFarmLocation("Farm Lacation");
    	// Climate 
    	bnn0005Object.setClimate("Climate");
    	// soil 
    	bnn0005Object.setSoil("Climate");
    	// user id
    	bnn0005Object.setUsersId("A0001");

    	service.setAppContext(appContext);

    	Bnn0005SearchFarmMapper tmp0005 = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp0005);
        when(tmp0005.getLastFarmId(any(HashMap.class))).thenReturn("F999");

        IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
    	when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
        when(tmpFarm.insert(any(IvbMFarm.class))).thenReturn(0);

        IvbMAreaMapper tmpArea = Mockito.mock(IvbMAreaMapper.class);
    	when(dao.getIvbMAreaMapper()).thenReturn(tmpArea);
        when(tmpArea.insert(any(IvbMArea.class))).thenReturn(0);
        
        // insert data result
        String result = service.insertData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_FARM, result);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    @Transactional
    public void testInsertFarmDataFaild() {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm name
    	bnn0005Object.setFarmName("Farm 006");
    	// user id
    	bnn0005Object.setUsersId("A0001");

    	service.setAppContext(appContext);

    	Bnn0005SearchFarmMapper tmp0005 = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp0005);
        when(tmp0005.getLastFarmId(any(HashMap.class))).thenReturn("F999");

        IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
    	when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
        when(tmpFarm.insert(any(IvbMFarm.class))).thenReturn(1);

        IvbMAreaMapper tmpArea = Mockito.mock(IvbMAreaMapper.class);
    	when(dao.getIvbMAreaMapper()).thenReturn(tmpArea);
        when(tmpArea.insert(any(IvbMArea.class))).thenReturn(0);
        
        // insert data result
        String result = service.insertData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testInsertDataRollback() {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm name
    	bnn0005Object.setFarmName("Farm 006");
    	// user id
    	bnn0005Object.setUsersId("A0001");

    	service.setAppContext(appContext);
    	
    	IvbMFarmMapper tmp = Mockito.mock(IvbMFarmMapper.class);
    	when(dao.getIvbMFarmMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).insert(any(IvbMFarm.class));
        
        // insert data result
        String result = service.insertData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    @Transactional
    public void testInsertDataException() {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm name
    	bnn0005Object.setFarmName("Farm 006");
    	// farm colection
    	bnn0005Object.setFarmLocation("Farm Lacation");
    	// Climate 
    	bnn0005Object.setClimate("Climate");
    	// soil 
    	bnn0005Object.setSoil("Soil");
    	// user id
    	bnn0005Object.setUsersId("A0001");

    	Bnn0005SearchFarmMapper tmp0005 = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp0005);
        when(tmp0005.getLastFarmId(null)).thenReturn(null);
        
    	service.setAppContext(appContext);
        // insert data result
        String result = service.insertData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
    }
    
    @Test
    @Transactional
    public void testUpdateDataSuccess() {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm id
    	bnn0005Object.setFarmId("F005");
    	// farm name
    	bnn0005Object.setFarmName("Farm 007");
    	// farm colection
    	bnn0005Object.setFarmLocation("Farm Lacation");
    	// Climate 
    	bnn0005Object.setClimate("Climate");
    	// soil 
    	bnn0005Object.setSoil("Soil");
    	// old user id
    	bnn0005Object.setUsersId("A0001,A0003");
    	// new user id
    	bnn0005Object.setUsersIdNew("A0003,A0005");

    	service.setAppContext(appContext);

        IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
    	when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
        when(tmpFarm.updateByPrimaryKeySelective(any(IvbMFarm.class))).thenReturn(1);
        
        // insert data result
        String result = service.updateData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }
    
    @Test
    @Transactional
    public void testUpdateDataGetAllFarmName() {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm id
    	bnn0005Object.setFarmId("F005");
    	// farm name
    	bnn0005Object.setFarmName("Farm 007");
    	// farm colection
    	bnn0005Object.setFarmLocation("Farm Lacation");
    	// Climate 
    	bnn0005Object.setClimate("Climate");
    	// soil 
    	bnn0005Object.setSoil("Soil");
    	// old user id
    	bnn0005Object.setUsersId("A0001,A0003");
    	// new user id
    	bnn0005Object.setUsersIdNew("A0003,A0005");

    	service.setAppContext(appContext);

    	List<IvbMFarm> listAllFarmName = new ArrayList<IvbMFarm>();
    	IvbMFarm ivbMFarm = new IvbMFarm();
    	ivbMFarm.setFarmName("Farm 007");
    	listAllFarmName.add(ivbMFarm);
    	
        IvbMFarmMapper tmpFarm = Mockito.mock(IvbMFarmMapper.class);
    	when(dao.getIvbMFarmMapper()).thenReturn(tmpFarm);
    	
        when(tmpFarm.updateByPrimaryKeySelective(any(IvbMFarm.class))).thenReturn(1);
        Bnn0005SearchFarmMapper tmp0005 = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp0005);
        when(tmp0005.getAllFarmName(any(HashMap.class))).thenReturn(listAllFarmName);

        doThrow(new DuplicateKeyException("")).when(tmp0005).updateData(any(HashMap.class));
        // insert data result
        String result = service.updateData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_DUPLLICATE, result);
    }
    
    @Test
    @Transactional
    public void testUpdateDataGetAllFarmNameNUll111() {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm id
    	bnn0005Object.setFarmId("F005");
    	// farm name
    	bnn0005Object.setFarmName("Farm 007");
    	// farm colection
    	bnn0005Object.setFarmLocation("Farm Lacation");
    	// Climate 
    	bnn0005Object.setClimate("Climate");
    	// soil 
    	bnn0005Object.setSoil("Soil");
    	// old user id
    	bnn0005Object.setUsersId("A0001,A0003");
    	// new user id
    	bnn0005Object.setUsersIdNew("A0003,A0005");

    	service.setAppContext(appContext);

    	List<IvbMFarm> listAllFarmName = new ArrayList<IvbMFarm>();
    	IvbMFarm ivbMFarm = new IvbMFarm();
    	ivbMFarm.setFarmName("Farm 017");
    	listAllFarmName.add(ivbMFarm);
        Bnn0005SearchFarmMapper tmp0005 = Mockito.mock(Bnn0005SearchFarmMapper.class);
        when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp0005);
        when(tmp0005.getAllFarmName(any(HashMap.class))).thenReturn(listAllFarmName);
        // insert data result
        String result = service.updateData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    @Transactional
    public void testUpdateNoNewUserDataSuccess() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm id
    	bnn0005Object.setFarmId("F005");
    	// farm colection
    	bnn0005Object.setFarmLocation("Farm Lacation");
    	// Climate 
    	bnn0005Object.setClimate("Climate");
    	// soil 
    	bnn0005Object.setSoil("Soil");
    	// farm name
    	bnn0005Object.setFarmName("Farm 007");
    	// old user id
    	bnn0005Object.setUsersId("A0001");
    	// new user id
    	bnn0005Object.setUsersIdNew("A0005");
    	// last Update Date
    	bnn0005Object.setLastUpdateDate(date);

    	service.setAppContext(appContext);

    	Bnn0005SearchFarmMapper tmpFarm = Mockito.mock(Bnn0005SearchFarmMapper.class);
    	when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmpFarm);
        when(tmpFarm.updateData(any(HashMap.class))).thenReturn(1);
        
        // insert data result
        String result = service.updateData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_SUCCESSFUL, result);
    }

    @SuppressWarnings("unchecked")
	@Test
    @Transactional
    public void testUpdateDataRollback() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm id
    	bnn0005Object.setFarmId("F005");
    	// farm colection
    	bnn0005Object.setFarmLocation("Farm Lacation");
    	// Climate 
    	bnn0005Object.setClimate("Climate");
    	// soil 
    	bnn0005Object.setSoil("Soil");
    	// farm name
    	bnn0005Object.setFarmName("Farm 007");
    	// old user id
    	bnn0005Object.setUsersId("A0001");
    	// new user id
    	bnn0005Object.setUsersIdNew("A0005");
    	// last Update Date
    	bnn0005Object.setLastUpdateDate(date);
    	
    	Bnn0005SearchFarmDataObject bnn0005SearchFarmDataObject = new Bnn0005SearchFarmDataObject();
    	bnn0005SearchFarmDataObject.setLastUpdateDate(date);
		// Mock setup
		Bnn0005SearchFarmMapper tmp1 = Mockito.mock(Bnn0005SearchFarmMapper.class);
		when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp1);
		when(tmp1.getSingleData(any(String.class))).thenReturn(bnn0005SearchFarmDataObject);
		
		when(tmp1.updateData(any(HashMap.class))).thenReturn(0);
		service.setAppContext(appContext);
        // insert data result
        String result = service.updateData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_CULTIVATION_FARM, result);
    }

    @SuppressWarnings("unchecked")
	@Test
    @Transactional
    public void testUpdateDataFailedUpdateDate() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm id
    	bnn0005Object.setFarmId("F005");
    	// farm colection
    	bnn0005Object.setFarmLocation("Farm Lacation");
    	// Climate 
    	bnn0005Object.setClimate("Climate");
    	// soil 
    	bnn0005Object.setSoil("Soil");
    	// farm name
    	bnn0005Object.setFarmName("Farm 007");
    	// old user id
    	bnn0005Object.setUsersId("A0001");
    	// new user id
    	bnn0005Object.setUsersIdNew("A0005");
    	// last Update Date
    	bnn0005Object.setLastUpdateDate(date);
    	
    	Bnn0005SearchFarmDataObject bnn0005SearchFarmDataObject = new Bnn0005SearchFarmDataObject();
    	bnn0005SearchFarmDataObject.setLastUpdateDate(format.parse("2017/05/18 13:25:25"));
		// Mock setup
		Bnn0005SearchFarmMapper tmp1 = Mockito.mock(Bnn0005SearchFarmMapper.class);
		when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp1);
		when(tmp1.getSingleData(any(String.class))).thenReturn(bnn0005SearchFarmDataObject);
		
		when(tmp1.updateData(any(HashMap.class))).thenReturn(0);
		service.setAppContext(appContext);
        // insert data result
        String result = service.updateData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_UPDATE_DATE, result);
    }
   
	@Test
    @Transactional
    public void testUpdateDataFailedCheckInputFarmname() throws ParseException {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm id
    	bnn0005Object.setFarmId("F005");
    	// farm colection
    	bnn0005Object.setFarmLocation("Farm Lacation");
    	// Climate 
    	bnn0005Object.setClimate("Climate");
    	// soil 
    	bnn0005Object.setSoil("Soil");
    	// farm name
    	bnn0005Object.setFarmName("");
    	// old user id
    	bnn0005Object.setUsersId("A0001");
    	// new user id
    	bnn0005Object.setUsersIdNew("A0005");
		service.setAppContext(appContext);
        // insert data result
        String result = service.updateData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }

	@Test
    @Transactional
    public void testUpdateDataFailedCheckInputFarmlocation() throws ParseException {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm id
    	bnn0005Object.setFarmId("F005");
    	// farm colection
    	bnn0005Object.setFarmLocation("");
    	// Climate 
    	bnn0005Object.setClimate("Climate");
    	// soil 
    	bnn0005Object.setSoil("Soil");
    	// farm name
    	bnn0005Object.setFarmName("F001");
    	// old user id
    	bnn0005Object.setUsersId("A0001");
    	// new user id
    	bnn0005Object.setUsersIdNew("A0005");
		service.setAppContext(appContext);
        // insert data result
        String result = service.updateData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }

	@Test
    @Transactional
    public void testUpdateDataFailedCheckInputFarmClimate() throws ParseException {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm id
    	bnn0005Object.setFarmId("F005");
    	// farm colection
    	bnn0005Object.setFarmLocation("dfaef");
    	// Climate 
    	bnn0005Object.setClimate("");
    	// soil 
    	bnn0005Object.setSoil("Soil");
    	// farm name
    	bnn0005Object.setFarmName("F001");
    	// old user id
    	bnn0005Object.setUsersId("A0001");
    	// new user id
    	bnn0005Object.setUsersIdNew("A0005");
		service.setAppContext(appContext);
        // insert data result
        String result = service.updateData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }

	@Test
    @Transactional
    public void testUpdateDataFailedCheckInputSoil() throws ParseException {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	// farm id
    	bnn0005Object.setFarmId("F005");
    	// farm colection
    	bnn0005Object.setFarmLocation("dfaef");
    	// Climate 
    	bnn0005Object.setClimate("climate");
    	// soil 
    	bnn0005Object.setSoil("");
    	// farm name
    	bnn0005Object.setFarmName("F001");
    	// old user id
    	bnn0005Object.setUsersId("A0001");
    	// new user id
    	bnn0005Object.setUsersIdNew("A0005");
		service.setAppContext(appContext);
        // insert data result
        String result = service.updateData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
    }

    @Test
    @Transactional
    public void testUpdateDataException() {
    	Bnn0005SearchFarmDataObject bnn0005Object = null;

        // insert data result
        String result = service.updateData(bnn0005Object);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    @Transactional
    public void testDeleteDataSuccess() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	String farmId= "F005";
    	service.setAppContext(appContext);

    	Bnn0005SearchFarmMapper tmpArea = Mockito.mock(Bnn0005SearchFarmMapper.class);
    	when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmpArea);
    	//mockito for parameter
		  doAnswer(new Answer<Void>() {
		  @Override
		  public Void answer(InvocationOnMock invocation) throws Throwable {
		   Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
		      params.put("result", Constants.DELETE_RESULT_SUCCESSFUL);
		      return null;
		  }
		  }).when(tmpArea).deleteData(any(HashMap.class));
        
        // insert data result
        String result = service.deleteData(farmId,date);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    @Transactional
    public void testDeleteDataElse() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	String farmId= "F005";
    	service.setAppContext(appContext);

    	Bnn0005SearchFarmMapper tmpArea = Mockito.mock(Bnn0005SearchFarmMapper.class);
    	when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmpArea);
    	//mockito for parameter
		  doAnswer(new Answer<Void>() {
		  @Override
		  public Void answer(InvocationOnMock invocation) throws Throwable {
		   Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
		      params.put("result", "-1");
		      return null;
		  }
		  }).when(tmpArea).deleteData(any(HashMap.class));
        
        // insert data result
        String result = service.deleteData(farmId,date);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
    }    
    @SuppressWarnings("unchecked")
	@Test
    @Transactional
    public void testDeleteDataSuccessNoIf() throws ParseException {
    	String farmId= "F005";
    	service.setAppContext(appContext);

    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);

    	Bnn0005SearchFarmMapper tmpArea = Mockito.mock(Bnn0005SearchFarmMapper.class);
    	when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmpArea);
    	//mockito for parameter
		  doAnswer(new Answer<Void>() {
		  @Override
		  public Void answer(InvocationOnMock invocation) throws Throwable {
		   Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
		      params.put("result", Constants.DELETE_RESULT_SUCCESSFUL);
		      return null;
		  }
		  }).when(tmpArea).deleteData(any(HashMap.class));
        
		// insert data result
	        String result = service.deleteData(farmId,date);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    @Transactional
    public void testDeleteDataError() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	String farmId= "F005";
    	service.setAppContext(appContext);

    	Bnn0005SearchFarmMapper tmpArea = Mockito.mock(Bnn0005SearchFarmMapper.class);
    	when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmpArea);
    	//mockito for parameter
		  doAnswer(new Answer<Void>() {
		  @Override
		  public Void answer(InvocationOnMock invocation) throws Throwable {
		   Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
		      params.put("result", Constants.DELETE_RESULT_FAILED);
		      return null;
		  }
		  }).when(tmpArea).deleteData(any(HashMap.class));
        
		// insert data result
	        String result = service.deleteData(farmId,date);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_FARM, result);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    @Transactional
    public void testDeleteDataErrorBlank() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
    	String farmId= "F005";
    	service.setAppContext(appContext);

    	Bnn0005SearchFarmMapper tmpArea = Mockito.mock(Bnn0005SearchFarmMapper.class);
    	when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmpArea);
    	//mockito for parameter
		  doAnswer(new Answer<String>() {
		  @Override
		  public String answer(InvocationOnMock invocation) throws Throwable {
		   Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
		      params.put("result", "");
		      return null;
		  }
		  }).when(tmpArea).deleteData(any(HashMap.class));
        
		// insert data result
	        String result = service.deleteData(farmId,date);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_FARM, result);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    @Transactional
    public void testDeleteDataErrorFailedUpdateDate() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
    	String farmId= "F005";
    	service.setAppContext(appContext);

    	Bnn0005SearchFarmMapper tmpArea = Mockito.mock(Bnn0005SearchFarmMapper.class);
    	when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmpArea);
    	//mockito for parameter
		  doAnswer(new Answer<String>() {
		  @Override
		  public String answer(InvocationOnMock invocation) throws Throwable {
		   Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
		      params.put("result", Constants.DELETE_RESULT_FAILED_UPDATE_DATE);
		      return null;
		  }
		  }).when(tmpArea).deleteData(any(HashMap.class));
        
		// insert data result
	        String result = service.deleteData(farmId,date);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_UPDATE_DATE, result);
    }
   
    @SuppressWarnings("unchecked")
	@Test
    @Transactional
    public void testDeleteDataErrorNull() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	String farmId= "F005";
    	service.setAppContext(appContext);

    	Bnn0005SearchFarmMapper tmpArea = Mockito.mock(Bnn0005SearchFarmMapper.class);
    	when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmpArea);
    	//mockito for parameter
		  doAnswer(new Answer<String>() {
		  @Override
		  public String answer(InvocationOnMock invocation) throws Throwable {
		   Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
		      params.put("result", null);
		      return null;
		  }
		  }).when(tmpArea).deleteData(any(HashMap.class));
        
		// insert data result
	        String result = service.deleteData(farmId,date);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_FARM, result);
    }


    @SuppressWarnings("unchecked")
	@Test
    @Transactional
    public void testDeleteDataNoSuccessIsNull() throws ParseException {
    	String farmId= "F005";
    	service.setAppContext(appContext);

		String dateTestStr = "2017/05/18 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(dateTestStr);
		
		Bnn0005SearchFarmMapper tmp = Mockito.mock(Bnn0005SearchFarmMapper.class);
    	when(dao.getBnn0005SearchFarmMapper()).thenReturn(tmp);
		// mockito for parameter
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Map<String, Object> params = (Map<String, Object>) invocation.getArguments()[0];
				params.put("result", null);
				return null;
			}
		}).when(tmp).deleteData(any(HashMap.class));
        
     // insert data result
        String result = service.deleteData(farmId,date);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_FARM, result);
    }
    
    @Test
    @Transactional
    public void testDeleteAreaDataFaild() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	String farmId= "F005";

    	service.setAppContext(appContext);

        IvbMAreaMapper tmpArea = Mockito.mock(IvbMAreaMapper.class);
    	when(dao.getIvbMAreaMapper()).thenReturn(tmpArea);
        when(tmpArea.updateByPrimaryKeySelective(any(IvbMArea.class))).thenReturn(0);
        
     // insert data result
        String result = service.deleteData(farmId,date);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
    }

      @Test
    @Transactional
    public void testDeleteDataException() throws ParseException {
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	// farm id
    	String farmId = null;
        
    	// insert data result
        String result = service.deleteData(farmId,date);

        // so the result will be failure
        Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
    }
}