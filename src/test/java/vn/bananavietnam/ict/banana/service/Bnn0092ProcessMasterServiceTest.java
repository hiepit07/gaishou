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
import vn.bananavietnam.ict.banana.component.Bnn0092ProcessMasterResult;
import vn.bananavietnam.ict.banana.dao.Bnn0092ProcessMasterDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0091CultivationMasterMapper;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0092ProcessMasterMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.mapper.IvbMCultivationMapper;
import vn.bananavietnam.ict.common.db.model.IvbMCultivation;
import vn.bananavietnam.ict.common.db.model.IvbMCultivationExample;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0092ProcessMasterServiceTest {
	@Autowired
	private ApplicationContext appContext;

	@InjectMocks
	private Bnn0092ProcessMasterService service;

	@Mock
	Bnn0092ProcessMasterDao dao;

	@Mock
	Util util;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0092");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        when(util.getUserInfo()).thenReturn(u);	
	}

	@Test
	public void testGetData() throws SQLException {
		ExtendedModelMap model = new ExtendedModelMap();

		Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();
		// farm id
		bnn0091Object.setFarmId("F001");
		// farm name
		bnn0091Object.setFarmName("Farm 001");
		// kind id
		bnn0091Object.setKindId("K001");
		// kind name
		bnn0091Object.setKindName("Kind 001");

    	service.setAppContext(appContext);

        // start testing
		service.getData(model, bnn0091Object);
	}

	@Test
	public void testgetDataExceptionIn() throws SQLException {
		ExtendedModelMap model = new ExtendedModelMap();

		Bnn0091CultivationMasterDataObject bnn0091Object = null;

    	service.setAppContext(appContext);

        // start testing
		service.getData(model, bnn0091Object);
	}

	@Test
	public void testgetDataExceptionOut() throws SQLException {
		ExtendedModelMap model = new ExtendedModelMap();

		Bnn0091CultivationMasterDataObject bnn0091Object = new Bnn0091CultivationMasterDataObject();

        // start testing
		service.getData(model, bnn0091Object);
	}

    @Test
    public void testInitData() throws SQLException {
        IvbMCultivation cultivationData = new IvbMCultivation();
        
        List<IvbMProcess> unreList = new ArrayList<IvbMProcess>();
		List<IvbMProcess> regiList = new ArrayList<IvbMProcess>();
    	IvbMProcess temppro = new IvbMProcess();
    	unreList.add(temppro);
    	regiList.add(temppro);

        List<Bnn0092ProcessMasterResult> bnn0092ResultListReturn = new ArrayList<Bnn0092ProcessMasterResult>();
        bnn0092ResultListReturn.add(new Bnn0092ProcessMasterResult());

    	service.setAppContext(appContext);

        // Mock setup
        Bnn0092ProcessMasterMapper tmp = Mockito.mock(Bnn0092ProcessMasterMapper.class);
        when(dao.getBnn0092ProcessMasterMapper()).thenReturn(tmp);
        when(tmp.getUnregisteredProcess(any(IvbMCultivation.class))).thenReturn(unreList);
        when(tmp.getRegisteredProcess(any(IvbMCultivation.class))).thenReturn(regiList);

        // get search result
        List<List<IvbMProcess>> bnn0092ResultList = service.initData(cultivationData);
        // start testing
        Assert.assertEquals(2, bnn0092ResultList.size());
    }

    @Test
    public void testInitDataExceptionIn() throws SQLException {
        IvbMCultivation cultivationData = null;
        
        List<Bnn0092ProcessMasterResult> bnn0092ResultListReturn = new ArrayList<Bnn0092ProcessMasterResult>();
        bnn0092ResultListReturn.add(new Bnn0092ProcessMasterResult());

    	service.setAppContext(appContext);

        // Mock setup
        Bnn0092ProcessMasterMapper tmp = Mockito.mock(Bnn0092ProcessMasterMapper.class);
        when(dao.getBnn0092ProcessMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getUnregisteredProcess(any(IvbMCultivation.class));

        // get search result
        List<List<IvbMProcess>> bnn0092ResultList = service.initData(cultivationData);
        // start testing
        Assert.assertEquals(0, bnn0092ResultList.size());
    }

    @Test
    public void testInitDataExceptionOut() throws SQLException {
        IvbMCultivation cultivationData = new IvbMCultivation();
        
        List<Bnn0092ProcessMasterResult> bnn0092ResultListReturn = new ArrayList<Bnn0092ProcessMasterResult>();
        bnn0092ResultListReturn.add(new Bnn0092ProcessMasterResult());
        // Mock setup
        Bnn0092ProcessMasterMapper tmp = Mockito.mock(Bnn0092ProcessMasterMapper.class);
        when(dao.getBnn0092ProcessMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getUnregisteredProcess(any(IvbMCultivation.class));

        // get search result
        List<List<IvbMProcess>> bnn0092ResultList = service.initData(cultivationData);
        // start testing
        Assert.assertEquals(0, bnn0092ResultList.size());
    }
    
    @Test
    public void testgetProcessDetailData() throws SQLException {
    	IvbMCultivation searchConditions = new IvbMCultivation();
       
    	List<Bnn0092ProcessMasterResult> processDetailData = new ArrayList<Bnn0092ProcessMasterResult>();
    	Bnn0092ProcessMasterResult temp = new Bnn0092ProcessMasterResult();
    	processDetailData.add(temp);

    	service.setAppContext(appContext);

    	Bnn0092ProcessMasterMapper tmp = Mockito.mock(Bnn0092ProcessMasterMapper.class);
        when(dao.getBnn0092ProcessMasterMapper()).thenReturn(tmp);
        when(tmp.getProcessDetailData(any(IvbMCultivation.class))).thenReturn(processDetailData);
        when(tmp.getSearchDataTotalCounts(any(IvbMCultivation.class))).thenReturn("1");

        // get search result
        List<Bnn0092ProcessMasterResult> bnn0092ResultList = service.getProcessDetailData(searchConditions);
        // start testing
        Assert.assertEquals(1, bnn0092ResultList.size());
    }
    
    @Test
    public void testgetProcessDetailDataNoData() throws SQLException {
    	IvbMCultivation searchConditions = new IvbMCultivation();
       
    	List<Bnn0092ProcessMasterResult> processDetailData = new ArrayList<Bnn0092ProcessMasterResult>();

    	service.setAppContext(appContext);

    	Bnn0092ProcessMasterMapper tmp = Mockito.mock(Bnn0092ProcessMasterMapper.class);
        when(dao.getBnn0092ProcessMasterMapper()).thenReturn(tmp);
        when(tmp.getProcessDetailData(any(IvbMCultivation.class))).thenReturn(processDetailData);

        // get search result
        List<Bnn0092ProcessMasterResult> bnn0092ResultList = service.getProcessDetailData(searchConditions);
        // start testing
        Assert.assertEquals(0, bnn0092ResultList.size());
    }
    
    @Test
    public void testgetProcessDetailDataOutofMemory() throws SQLException {
    	IvbMCultivation searchConditions = new IvbMCultivation();

    	service.setAppContext(appContext);

    	Bnn0092ProcessMasterMapper tmp = Mockito.mock(Bnn0092ProcessMasterMapper.class);
        when(dao.getBnn0092ProcessMasterMapper()).thenReturn(tmp);
        doThrow(new OutOfMemoryError()).when(tmp).getProcessDetailData(any(IvbMCultivation.class));

        // get search result
        List<Bnn0092ProcessMasterResult> bnn0092ResultList = service.getProcessDetailData(searchConditions);
        // start testing
        Assert.assertEquals(1, bnn0092ResultList.size());
        Assert.assertEquals("-1", bnn0092ResultList.get(0).getSearchDataTotalCounts());
    }
    
    @Test
    public void testgetProcessDetailDataExceptionInt() throws SQLException {
    	IvbMCultivation searchConditions = null;

    	service.setAppContext(appContext);
     	
    	Bnn0092ProcessMasterMapper tmp = Mockito.mock(Bnn0092ProcessMasterMapper.class);
        when(dao.getBnn0092ProcessMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getProcessDetailData(any(IvbMCultivation.class));

        // get search result
        List<Bnn0092ProcessMasterResult> bnn0092ResultList = service.getProcessDetailData(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0092ResultList);
    }
    
    @Test
    public void testgetProcessDetailDataExceptionOut() throws SQLException {
    	IvbMCultivation searchConditions = new IvbMCultivation();
     	
    	Bnn0092ProcessMasterMapper tmp = Mockito.mock(Bnn0092ProcessMasterMapper.class);
        when(dao.getBnn0092ProcessMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getProcessDetailData(any(IvbMCultivation.class));

        // get search result
        List<Bnn0092ProcessMasterResult> bnn0092ResultList = service.getProcessDetailData(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0092ResultList);
    }
	
    @Test
    public void testgetUnregisteredProcessDataTotal() {
        
    	IvbMCultivation searchConditions = new IvbMCultivation();

    	service.setAppContext(appContext);
     	
    	Bnn0091CultivationMasterMapper tmp = Mockito.mock(Bnn0091CultivationMasterMapper.class);
        when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp);
        when(tmp.getUnregisteredProcessTotal(any(IvbMCultivation.class))).thenReturn("1");

        // get search result
        String bnn0092ResultList = service.getUnregisteredProcessDataTotal(searchConditions);
        // start testing
        Assert.assertEquals("1", bnn0092ResultList);
    }
	
    @Test
    public void testgetUnregisteredProcessDataTotalExcptionIn() {
        
    	IvbMCultivation searchConditions = null;

    	service.setAppContext(appContext);
     	
    	Bnn0091CultivationMasterMapper tmp = Mockito.mock(Bnn0091CultivationMasterMapper.class);
        when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getUnregisteredProcessTotal(any(IvbMCultivation.class));

        // get search result
        String bnn0092ResultList = service.getUnregisteredProcessDataTotal(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0092ResultList);
    }
	
    @Test
    public void testgetUnregisteredProcessDataTotalExcptionOut() {
        
    	IvbMCultivation searchConditions = new IvbMCultivation();
     	
    	Bnn0091CultivationMasterMapper tmp = Mockito.mock(Bnn0091CultivationMasterMapper.class);
        when(dao.getBnn0091CultivationMasterMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).getUnregisteredProcessTotal(any(IvbMCultivation.class));

        // get search result
        String bnn0092ResultList = service.getUnregisteredProcessDataTotal(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0092ResultList);
    }
    
    @Test
    public void testUpdateDataSuccessHasProcess() throws SQLException {
    	List<IvbMCultivation> cultivationList = new ArrayList<IvbMCultivation>();
    	
    	IvbMCultivation cultivationObject = new IvbMCultivation();
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("1");
    	cultivationList.add(cultivationObject);
    	cultivationObject = new IvbMCultivation();
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001,P003");
    	cultivationList.add(cultivationObject);

    	service.setAppContext(appContext);

    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.updateByExampleSelective(any(IvbMCultivation.class), any(IvbMCultivationExample.class))).thenReturn(1);
        
        // insert data result
        String result = service.updateData(cultivationList);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }
    
    @Test
    public void testUpdateDataSuccessNoneProcess() throws SQLException {
    	List<IvbMCultivation> cultivationList = new ArrayList<IvbMCultivation>();
    	
    	IvbMCultivation cultivationObject = new IvbMCultivation();
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("0");
    	cultivationList.add(cultivationObject);
    	cultivationObject = new IvbMCultivation();
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001,P003");
    	cultivationList.add(cultivationObject);

    	service.setAppContext(appContext);

    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.updateByExampleSelective(any(IvbMCultivation.class), any(IvbMCultivationExample.class))).thenReturn(1);
        
        // insert data result
        String result = service.updateData(cultivationList);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }
    
    @Test
    public void testUpdateDataSuccessNoneData() throws SQLException {
    	List<IvbMCultivation> cultivationList = new ArrayList<IvbMCultivation>();
    	
    	IvbMCultivation cultivationObject = new IvbMCultivation();
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationList.add(cultivationObject);

    	service.setAppContext(appContext);

    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.updateByExampleSelective(any(IvbMCultivation.class), any(IvbMCultivationExample.class))).thenReturn(1);
        
        // insert data result
        String result = service.updateData(cultivationList);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    public void testUpdateDataFaildListHasMoreObject() throws SQLException {
    	List<IvbMCultivation> cultivationList = new ArrayList<IvbMCultivation>();
    	
    	IvbMCultivation cultivationObject = new IvbMCultivation();
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001");
    	cultivationList.add(cultivationObject);
    	cultivationObject = new IvbMCultivation();
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001,P003");
    	cultivationList.add(cultivationObject);

    	service.setAppContext(appContext);

    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
    	when(tmp.updateByExampleSelective(any(IvbMCultivation.class), any(IvbMCultivationExample.class))).thenReturn(0);
        
        // insert data result
        String result = service.updateData(cultivationList);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }

    @Test
    public void testUpdateDataFaildListHasOneObject() throws SQLException, ParseException {
    	List<IvbMCultivation> cultivationList = new ArrayList<IvbMCultivation>();
    	
    	IvbMCultivation cultivationObject = new IvbMCultivation();
    	// farm id
    	cultivationObject.setFarmId("F001");
    	// kind id
    	cultivationObject.setKindId("K001");
    	// process id
    	cultivationObject.setProcessId("P001,P003");
    	cultivationList.add(cultivationObject);

    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001");
    	cultivationObject.setLastUpdateDate(date);
    	cultivationList.add(cultivationObject);
    	cultivationObject = new IvbMCultivation();
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001,P003");
    	cultivationObject.setLastUpdateDate(date);
    	cultivationList.add(cultivationObject);

    	service.setAppContext(appContext);
    	
    	IvbMCultivationMapper tmpSecond = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmpSecond);
    	when(tmpSecond.updateByExampleSelective(any(IvbMCultivation.class), any(IvbMCultivationExample.class))).thenReturn(0);
        
    	Bnn0092ProcessMasterMapper tmp = Mockito.mock(Bnn0092ProcessMasterMapper.class);
    	when(dao.getBnn0092ProcessMasterMapper()).thenReturn(tmp);
    	
    	List<Bnn0092ProcessMasterResult> list = new ArrayList<Bnn0092ProcessMasterResult>();
    	Bnn0092ProcessMasterResult bnn0092ProcessMasterResult = new Bnn0092ProcessMasterResult();
    	bnn0092ProcessMasterResult.setProcessName("P001");
    	bnn0092ProcessMasterResult.setTaskName("T001");
    	bnn0092ProcessMasterResult.setWorkingDetails("P001");
    	bnn0092ProcessMasterResult.setNote("P001");
    	when(tmp.getSearchDataTotalCounts(any(IvbMCultivation.class))).thenReturn("1");
    	bnn0092ProcessMasterResult.setLastUpdateDate(date);
    	list.add(bnn0092ProcessMasterResult);
    	when(tmp.getProcessDetailData(any(IvbMCultivation.class))).thenReturn(list);
        // insert data result
        String result = service.updateData(cultivationList);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_CULTIVATION_PROCESS_LAST_UPDATE_DATE_RESULT, result);
    }

    @Test
    public void testUpdateDataFaildListHasOneObjectUpdateDate() throws SQLException, ParseException {
    	List<IvbMCultivation> cultivationList = new ArrayList<IvbMCultivation>();
    	
    	IvbMCultivation cultivationObject = new IvbMCultivation();
    	// farm id
    	cultivationObject.setFarmId("F001");
    	// kind id
    	cultivationObject.setKindId("K001");
    	// process id
    	cultivationObject.setProcessId("P001,P003");
    	cultivationList.add(cultivationObject);

    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001");
    	cultivationObject.setLastUpdateDate(date);
    	cultivationList.add(cultivationObject);
    	cultivationObject = new IvbMCultivation();
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001,P003");
    	cultivationObject.setLastUpdateDate(date);
    	cultivationList.add(cultivationObject);

    	service.setAppContext(appContext);
    	
    	IvbMCultivationMapper tmpSecond = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmpSecond);
    	when(tmpSecond.updateByExampleSelective(any(IvbMCultivation.class), any(IvbMCultivationExample.class))).thenReturn(0);
        
    	Bnn0092ProcessMasterMapper tmp = Mockito.mock(Bnn0092ProcessMasterMapper.class);
    	when(dao.getBnn0092ProcessMasterMapper()).thenReturn(tmp);
    	
    	List<Bnn0092ProcessMasterResult> list = new ArrayList<Bnn0092ProcessMasterResult>();
    	Bnn0092ProcessMasterResult bnn0092ProcessMasterResult = new Bnn0092ProcessMasterResult();
    	bnn0092ProcessMasterResult.setProcessName("P001");
    	bnn0092ProcessMasterResult.setTaskName("T001");
    	bnn0092ProcessMasterResult.setWorkingDetails("P001");
    	bnn0092ProcessMasterResult.setNote("P001");
    	when(tmp.getSearchDataTotalCounts(any(IvbMCultivation.class))).thenReturn("1");
    	bnn0092ProcessMasterResult.setLastUpdateDate(format.parse("2017/05/18 13:25:25"));
    	list.add(bnn0092ProcessMasterResult);
    	when(tmp.getProcessDetailData(any(IvbMCultivation.class))).thenReturn(list);
        // insert data result
        String result = service.updateData(cultivationList);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_CULTIVATION_PROCESS_LAST_UPDATE_DATE_RESULT, result);
    }

    @Test
    public void testUpdateDataFaildListHasOneObjectUpdateDateRollBack() throws SQLException, ParseException {
    	List<IvbMCultivation> cultivationList = new ArrayList<IvbMCultivation>();
    	
    	IvbMCultivation cultivationObject = new IvbMCultivation();
    	// farm id
    	cultivationObject.setFarmId("F001");
    	// kind id
    	cultivationObject.setKindId("K001");
    	// process id
    	cultivationObject.setProcessId("P001,P003");
    	cultivationList.add(cultivationObject);

    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001");
    	cultivationObject.setLastUpdateDate(date);
    	cultivationList.add(cultivationObject);
    	cultivationObject = new IvbMCultivation();
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001,P003");
    	cultivationObject.setLastUpdateDate(date);
    	cultivationList.add(cultivationObject);

    	service.setAppContext(appContext);
    	
    	IvbMCultivationMapper tmpSecond = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmpSecond);
    	when(tmpSecond.updateByExampleSelective(any(IvbMCultivation.class), any(IvbMCultivationExample.class))).thenReturn(0);
        
    	Bnn0092ProcessMasterMapper tmp = Mockito.mock(Bnn0092ProcessMasterMapper.class);
    	when(dao.getBnn0092ProcessMasterMapper()).thenReturn(tmp);
    	when(tmp.getLastUpdateDate(any(IvbMCultivation.class))).thenReturn(date);
    	
    	List<Bnn0092ProcessMasterResult> list = new ArrayList<Bnn0092ProcessMasterResult>();
    	Bnn0092ProcessMasterResult bnn0092ProcessMasterResult = new Bnn0092ProcessMasterResult();
    	bnn0092ProcessMasterResult.setProcessName("P001");
    	bnn0092ProcessMasterResult.setTaskName("T001");
    	bnn0092ProcessMasterResult.setWorkingDetails("P001");
    	bnn0092ProcessMasterResult.setNote("P001");
    	when(tmp.getSearchDataTotalCounts(any(IvbMCultivation.class))).thenReturn("1");
    	bnn0092ProcessMasterResult.setLastUpdateDate(format.parse("2017/05/17 13:25:25"));
    	list.add(bnn0092ProcessMasterResult);
    	when(tmp.getProcessDetailData(any(IvbMCultivation.class))).thenReturn(list);
        // insert data result
        String result = service.updateData(cultivationList);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_CULTIVATION_PROCESS_RESULT, result);
    }

    @Test
    public void testUpdateDataFaildListHasOneObjectSuccess() throws SQLException, ParseException {
    	List<IvbMCultivation> cultivationList = new ArrayList<IvbMCultivation>();
    	
    	IvbMCultivation cultivationObject = new IvbMCultivation();
    	// farm id
    	cultivationObject.setFarmId("F001");
    	// kind id
    	cultivationObject.setKindId("K001");
    	// process id
    	cultivationObject.setProcessId("P001,P003");
    	cultivationList.add(cultivationObject);

    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001");
    	cultivationObject.setLastUpdateDate(date);
    	cultivationList.add(cultivationObject);
    	cultivationObject = new IvbMCultivation();
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001,P003");
    	cultivationObject.setLastUpdateDate(date);
    	cultivationList.add(cultivationObject);

    	service.setAppContext(appContext);
    	
    	IvbMCultivationMapper tmpSecond = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmpSecond);
    	when(tmpSecond.updateByExampleSelective(any(IvbMCultivation.class), any(IvbMCultivationExample.class))).thenReturn(1);
        
    	Bnn0092ProcessMasterMapper tmp = Mockito.mock(Bnn0092ProcessMasterMapper.class);
    	when(dao.getBnn0092ProcessMasterMapper()).thenReturn(tmp);
    	
    	List<Bnn0092ProcessMasterResult> list = new ArrayList<Bnn0092ProcessMasterResult>();
    	Bnn0092ProcessMasterResult bnn0092ProcessMasterResult = new Bnn0092ProcessMasterResult();
    	bnn0092ProcessMasterResult.setProcessName("P001");
    	bnn0092ProcessMasterResult.setTaskName("T001");
    	bnn0092ProcessMasterResult.setWorkingDetails("P001");
    	bnn0092ProcessMasterResult.setNote("P001");
    	when(tmp.getSearchDataTotalCounts(any(IvbMCultivation.class))).thenReturn("1");
    	bnn0092ProcessMasterResult.setLastUpdateDate(format.parse("2017/05/18 13:25:25"));
    	list.add(bnn0092ProcessMasterResult);
    	when(tmp.getProcessDetailData(any(IvbMCultivation.class))).thenReturn(list);
        // insert data result
        String result = service.updateData(cultivationList);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_SUCCESSFUL, result);
    }
    @Test
    public void testUpdateDataRollback() throws SQLException, ParseException {
    	List<IvbMCultivation> cultivationList = new ArrayList<IvbMCultivation>();
    	
    	IvbMCultivation cultivationObject = new IvbMCultivation();
    	// farm id
    	cultivationObject.setFarmId("F001");
    	// kind id
    	cultivationObject.setKindId("K001");
    	// process id
    	cultivationObject.setProcessId("P001,P003");
    	cultivationList.add(cultivationObject);

    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001");
    	cultivationObject.setLastUpdateDate(date);
    	cultivationList.add(cultivationObject);
    	cultivationObject = new IvbMCultivation();
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001,P003");
    	cultivationObject.setLastUpdateDate(date);
    	cultivationList.add(cultivationObject);
    	
    	service.setAppContext(appContext);
    	
    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).updateByExampleSelective(any(IvbMCultivation.class), any(IvbMCultivationExample.class));
        
        // insert data result
        String result = service.updateData(cultivationList);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }
    
    @Test
    public void testUpdateDataExceptionIn() throws SQLException {
    	List<IvbMCultivation> cultivationList = null;

    	service.setAppContext(appContext);
    	
    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.updateByExampleSelective(any(IvbMCultivation.class), any(IvbMCultivationExample.class))).thenReturn(1);
        
        // insert data result
        String result = service.updateData(cultivationList);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }
    
    @Test
    public void testUpdateDataExceptionOut() throws SQLException, ParseException {
    	List<IvbMCultivation> cultivationList = new ArrayList<IvbMCultivation>();
    	
    	String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
    	
    	IvbMCultivation cultivationObject = new IvbMCultivation();
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001");
    	cultivationObject.setLastUpdateDate(date);
    	cultivationList.add(cultivationObject);
    	cultivationObject = new IvbMCultivation();
    	cultivationObject.setFarmId("F001");
    	cultivationObject.setKindId("K001");
    	cultivationObject.setProcessId("P001,P003");
    	cultivationObject.setLastUpdateDate(date);
    	cultivationList.add(cultivationObject);
    	
    	IvbMCultivationMapper tmp = Mockito.mock(IvbMCultivationMapper.class);
    	when(dao.getIvbMCultivationMapper()).thenReturn(tmp);
        when(tmp.updateByExampleSelective(any(IvbMCultivation.class), any(IvbMCultivationExample.class))).thenReturn(1);
        
        // insert data result
        String result = service.updateData(cultivationList);

        // so the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
    }
}
