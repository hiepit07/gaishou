package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.Date;
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

import vn.bananavietnam.ict.banana.component.Bnn0075SearchShippingScreenConditions;
import vn.bananavietnam.ict.banana.component.Bnn0075SearchShippingScreenResult;
import vn.bananavietnam.ict.banana.dao.Bnn0075shippingScreenDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0075SearchShippSreenMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.dao.UtilDao;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTProductMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTShippingControlMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTShippingNumberMapper;
import vn.bananavietnam.ict.common.db.mapper.UtilMapper;
import vn.bananavietnam.ict.common.db.model.IvbMFarmExample;
import vn.bananavietnam.ict.common.db.model.IvbTProduct;
import vn.bananavietnam.ict.common.db.model.IvbTProductExample;
import vn.bananavietnam.ict.common.db.model.IvbTShippingControl;
import vn.bananavietnam.ict.common.db.model.IvbTShippingControlExample;
import vn.bananavietnam.ict.common.db.model.IvbTShippingNumber;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0075ShippingScreenServiceTest {
	@Autowired
	private ApplicationContext appContext;

	@InjectMocks
	private Bnn0075ShippingScreenService bnn0075ShippingScreenService;

	@Mock
	Bnn0075shippingScreenDao bnn0075shippingScreenDao;

	@Mock
	UtilDao utilDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0075");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		Authentication authToken = new UsernamePasswordAuthenticationToken(u, "A0007", authorities);
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

	@Test
	public void testInitData() {

		ExtendedModelMap model = new ExtendedModelMap();
		List<UtilComponent> farmResult = new ArrayList<UtilComponent>();
		farmResult.add(new UtilComponent());

		bnn0075ShippingScreenService.setAppContext(appContext);

		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		when(tmp.selectFarmDataMaster()).thenReturn(farmResult);
		// when(util.getFarmData(any(UtilDao.class))).thenReturn(farmResult);

		// start testing
		bnn0075ShippingScreenService.initData(model);
	}

	@Test
	public void testInitDataException() {

		ExtendedModelMap model = new ExtendedModelMap();

		// Mock setup
		IvbMFarmMapper tmp = Mockito.mock(IvbMFarmMapper.class);
		when(bnn0075shippingScreenDao.getIvbMFarmMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).selectByExample(any(IvbMFarmExample.class));

		// start testing
		bnn0075ShippingScreenService.initData(model);
	}

	// Test search data result
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchData() {

		Bnn0075SearchShippingScreenConditions searchConditions = new Bnn0075SearchShippingScreenConditions();
		// shipping number
		searchConditions.setShippingNumber("");
		// farm id
		searchConditions.setFarmId("");
		// area id
		searchConditions.setAreaId("");
		// harvest start date
		searchConditions.setHarvestStartDate("");
		// harvest end date
		searchConditions.setHarvestEndDate("");
		// shipping start date
		searchConditions.setShipStartDate("");
		// shipping end date
		searchConditions.setShipEndDate("");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");

		bnn0075ShippingScreenService.setAppContext(appContext);

		List<Bnn0075SearchShippingScreenResult> bnn0075ResultListReturn = new ArrayList<Bnn0075SearchShippingScreenResult>();
		bnn0075ResultListReturn.add(new Bnn0075SearchShippingScreenResult());
		bnn0075ResultListReturn.add(new Bnn0075SearchShippingScreenResult());
		// Mock setup
		Bnn0075SearchShippSreenMapper tmp = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp);
		when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0075ResultListReturn);
		when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("100");

		// get search result
		List<Bnn0075SearchShippingScreenResult> bnn0075ResultList = bnn0075ShippingScreenService
				.searchData(searchConditions);
		// start testing
		Assert.assertEquals(2, bnn0075ResultList.size());
	}

	// Test search data no result
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataNoResult() {

		Bnn0075SearchShippingScreenConditions searchConditions = new Bnn0075SearchShippingScreenConditions();
		// shipping number
		searchConditions.setShippingNumber("00000000");
		// farm id
		searchConditions.setFarmId(null);
		// area id
		searchConditions.setAreaId(null);
		// harvest start date
		searchConditions.setHarvestStartDate("2017-01-01");
		// harvest end date
		searchConditions.setHarvestEndDate("2017-01-01");
		// shipping start date
		searchConditions.setShipStartDate("2017-01-01");
		// shipping end date
		searchConditions.setShipEndDate("2017-01-01");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");

		bnn0075ShippingScreenService.setAppContext(appContext);

		List<Bnn0075SearchShippingScreenResult> bnn0075ResultListReturn = new ArrayList<Bnn0075SearchShippingScreenResult>();
		// Mock setup
		Bnn0075SearchShippSreenMapper tmp = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp);
		when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0075ResultListReturn);
		when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");

		// get search result
		List<Bnn0075SearchShippingScreenResult> bnn0075ResultList = bnn0075ShippingScreenService
				.searchData(searchConditions);
		// start testing
		Assert.assertEquals(0, bnn0075ResultList.size());
	}

	// Test search data out of memory exception
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataOutOfMemoryException() {

		Bnn0075SearchShippingScreenConditions searchConditions = new Bnn0075SearchShippingScreenConditions();
		// shipping number
		searchConditions.setShippingNumber("00000000");
		// farm id
		searchConditions.setFarmId("F999");
		// area id
		searchConditions.setAreaId("A999");
		// harvest start date
		searchConditions.setHarvestStartDate("2017-01-01");
		// harvest end date
		searchConditions.setHarvestEndDate("2017-01-01");
		// shipping start date
		searchConditions.setShipStartDate("2017-01-01");
		// shipping end date
		searchConditions.setShipEndDate("2017-01-01");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");

		bnn0075ShippingScreenService.setAppContext(appContext);

		// Mock setup
		Bnn0075SearchShippSreenMapper tmp = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp);
		doThrow(new OutOfMemoryError()).when(tmp).searchData(any(HashMap.class));

		// get search result
		List<Bnn0075SearchShippingScreenResult> bnn0075ResultList = bnn0075ShippingScreenService
				.searchData(searchConditions);
		// start testing
		Assert.assertEquals("-1", bnn0075ResultList.get(0).getSearchDataTotalCounts());
	}

	// Test search data exception
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataException() {

		Bnn0075SearchShippingScreenConditions searchConditions = new Bnn0075SearchShippingScreenConditions();
		// shipping number
		searchConditions.setShippingNumber("00000000");
		// farm id
		searchConditions.setFarmId("F999");
		// area id
		searchConditions.setAreaId("A999");
		// harvest start date
		searchConditions.setHarvestStartDate("2017-01-01");
		// harvest end date
		searchConditions.setHarvestEndDate("2017-01-01");
		// shipping start date
		searchConditions.setShipStartDate("2017-01-01");
		// shipping end date
		searchConditions.setShipEndDate("2017-01-01");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");

		bnn0075ShippingScreenService.setAppContext(appContext);

		// Mock setup
		Bnn0075SearchShippSreenMapper tmp = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));

		// get search result
		List<Bnn0075SearchShippingScreenResult> bnn0075ResultList = bnn0075ShippingScreenService
				.searchData(searchConditions);
		// start testing
		Assert.assertEquals(null, bnn0075ResultList);
	}

	// Test search data exception cache
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataExceptionCache() {

		Bnn0075SearchShippingScreenConditions searchConditions = new Bnn0075SearchShippingScreenConditions();
		// shipping number
		searchConditions.setShippingNumber("00000000");
		// farm id
		searchConditions.setFarmId("F999");
		// area id
		searchConditions.setAreaId("A999");
		// harvest start date
		searchConditions.setHarvestStartDate("2017-01-01");
		// harvest end date
		searchConditions.setHarvestEndDate("2017-01-01");
		// shipping start date
		searchConditions.setShipStartDate("2017-01-01");
		// shipping end date
		searchConditions.setShipEndDate("2017-01-01");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");

		// Mock setup
		Bnn0075SearchShippSreenMapper tmp = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));

		// get search result
		List<Bnn0075SearchShippingScreenResult> bnn0075ResultList = bnn0075ShippingScreenService
				.searchData(searchConditions);
		// start testing
		Assert.assertEquals(null, bnn0075ResultList);
	}

	// Test input shippingNumber blank field
	@Test
	@Transactional
	public void testInputShippingNumberBlankFields() {
		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("1");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// // shipping date
		// IvbTShippingControl.setShippingDate(newDate);

		// input data result
		String result = bnn0075ShippingScreenService.insertData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}

	// Test input farm id blank field
	@Test
	@Transactional
	public void testInputFarmIdBlankFields1() {
		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000001");
		// farm id
		IvbTShippingControl.setFarmId("");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// // shipping date
		// IvbTShippingControl.setShippingDate(newDate);

		// input data result
		String result = bnn0075ShippingScreenService.insertData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}

	// Test input area id blank field
	@Test
	@Transactional
	public void testInputAreaIdBlankFields2() {
		
		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000001");
		// farm id
		IvbTShippingControl.setFarmId("A001");
		// area id
		IvbTShippingControl.setAreaId("");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// // shipping date
		// IvbTShippingControl.setShippingDate(newDate);

		// input data result
		String result = bnn0075ShippingScreenService.insertData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}

	// Test input harvest date blank field
	@Test
	@Transactional
	public void testInputHarvestDateBlankFields3() {
		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = null;
		// shipping number
		IvbTShippingControl.setShippingNumber("00000001");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// // shipping date
		// IvbTShippingControl.setShippingDate(newDate);

		// input data result
		String result = bnn0075ShippingScreenService.insertData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}

	// Test insert data successful
	@Test
	@Transactional
	public void testInsertDataSuccess() {

		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmpShip = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmpShip);
		when(tmpShip.insert(any(IvbTShippingControl.class))).thenReturn(1);

		IvbTProductMapper tmpProduct = Mockito.mock(IvbTProductMapper.class);
		when(bnn0075shippingScreenDao.getIvbTProductMapper()).thenReturn(tmpProduct);
		when(tmpProduct.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(1);

		Bnn0075SearchShippSreenMapper tmp0075 = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp0075);
		when(tmp0075.getLastShippingNumber()).thenReturn("00000001");

		// insert data result
		String result = bnn0075ShippingScreenService.insertData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_SHIPPING, result);
	}


	// Test insert data successful
	@Test
	@Transactional
	public void testInsertDataSuccess1() {

		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmpShip = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmpShip);
		when(tmpShip.insert(any(IvbTShippingControl.class))).thenReturn(1);

		IvbTProductMapper tmpProduct = Mockito.mock(IvbTProductMapper.class);
		when(bnn0075shippingScreenDao.getIvbTProductMapper()).thenReturn(tmpProduct);
		when(tmpProduct.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(1);

		Bnn0075SearchShippSreenMapper tmp0075 = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp0075);
		when(tmp0075.getLastShippingNumber()).thenReturn(null);

		// insert data result
		String result = bnn0075ShippingScreenService.insertData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_SHIPPING, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataSuccessWithIdshippingNumberIsMax() {

		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmpShip = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmpShip);
		when(tmpShip.insert(any(IvbTShippingControl.class))).thenReturn(1);

		IvbTProductMapper tmpProduct = Mockito.mock(IvbTProductMapper.class);
		when(bnn0075shippingScreenDao.getIvbTProductMapper()).thenReturn(tmpProduct);
		when(tmpProduct.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(1);

		Bnn0075SearchShippSreenMapper tmp0075 = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp0075);
		when(tmp0075.getLastShippingNumber()).thenReturn("99999999");
		when(tmp0075.updateProductData(any(HashMap.class))).thenReturn(1);

		// insert data result
		String result = bnn0075ShippingScreenService.insertData(IvbTShippingControl);

		// so the result will be failure
		/*Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);*/
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataSuccessWithIdshippingNumberIsMax1() {

		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmpShip = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmpShip);
		when(tmpShip.insert(any(IvbTShippingControl.class))).thenReturn(0);

		IvbTProductMapper tmpProduct = Mockito.mock(IvbTProductMapper.class);
		when(bnn0075shippingScreenDao.getIvbTProductMapper()).thenReturn(tmpProduct);
		when(tmpProduct.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(1);

		Bnn0075SearchShippSreenMapper tmp0075 = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp0075);
		when(tmp0075.getLastShippingNumber()).thenReturn("99999999");
		when(tmp0075.updateProductData(any(HashMap.class))).thenReturn(0);
		List<Bnn0075SearchShippingScreenResult> listIvbShippingControl = new ArrayList<Bnn0075SearchShippingScreenResult>();
		listIvbShippingControl.add(new Bnn0075SearchShippingScreenResult());
		when(tmp0075.getLastUpdateDate(any(HashMap.class))).thenReturn(listIvbShippingControl);

		// insert data result
		String result = bnn0075ShippingScreenService.insertData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_DUPLICATED, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataSuccessWithIdshippingNumberIsMax2() {

		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmpShip = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmpShip);
		when(tmpShip.insert(any(IvbTShippingControl.class))).thenReturn(0);

		IvbTProductMapper tmpProduct = Mockito.mock(IvbTProductMapper.class);
		when(bnn0075shippingScreenDao.getIvbTProductMapper()).thenReturn(tmpProduct);
		when(tmpProduct.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(1);

		Bnn0075SearchShippSreenMapper tmp0075 = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp0075);
		when(tmp0075.getLastShippingNumber()).thenReturn("99999999");
		when(tmp0075.updateProductData(any(HashMap.class))).thenReturn(0);
		List<Bnn0075SearchShippingScreenResult> listIvbShippingControl = new ArrayList<Bnn0075SearchShippingScreenResult>();
		when(tmp0075.getLastUpdateDate(any(HashMap.class))).thenReturn(listIvbShippingControl);

		// insert data result
		String result = bnn0075ShippingScreenService.insertData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testInsertDataDupplicateException() {

		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTProductMapper tmpProduct = Mockito.mock(IvbTProductMapper.class);
		when(bnn0075shippingScreenDao.getIvbTProductMapper()).thenReturn(tmpProduct);
		when(tmpProduct.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(1);

		Bnn0075SearchShippSreenMapper tmp0075 = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp0075);
		when(tmp0075.getLastShippingNumber()).thenReturn("99999999");
		when(tmp0075.updateProductData(any(HashMap.class))).thenReturn(0);
		List<Bnn0075SearchShippingScreenResult> listIvbShippingControl = new ArrayList<Bnn0075SearchShippingScreenResult>();
		when(tmp0075.getLastUpdateDate(any(HashMap.class))).thenReturn(listIvbShippingControl);
		// insert data result
		IvbTShippingControlMapper tmpShip = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmpShip);
		
		IvbTShippingControl shipObj = new IvbTShippingControl();
    	//shipping number
    	shipObj.setShippingNumber("00000099");
    	//farm id
    	shipObj.setFarmId(IvbTShippingControl.getFarmId());
        //area id
    	shipObj.setAreaId(IvbTShippingControl.getAreaId());
    	//harvest date
    	shipObj.setHarvestDate(IvbTShippingControl.getHarvestDate()); 
    	// shipping date 
    	shipObj.setShippingDate(IvbTShippingControl.getShippingDate()); 
        // create user id
        shipObj.setCreateUserId("A0007");
        // update user id
        shipObj.setUpdateUserId("A0007");
        // delete flag
        shipObj.setDeleteFlag(IvbTShippingControl.getDeleteFlag());
		doThrow(new DuplicateKeyException("")).doReturn(1).when(tmpShip).insert(any(IvbTShippingControl.class));
		
		String result = bnn0075ShippingScreenService.insertData(IvbTShippingControl);
		

		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_SHIPPING, result);
	}
	// Test insert data no successful IvbTProduct table

	//
	@Test
	@Transactional
	public void testInsertDataNoSuccessIvbTProductTable() {

		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmpShip = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmpShip);
		when(tmpShip.insert(any(IvbTShippingControl.class))).thenReturn(1);

		IvbTProductMapper tmpProduct = Mockito.mock(IvbTProductMapper.class);
		when(bnn0075shippingScreenDao.getIvbTProductMapper()).thenReturn(tmpProduct);
		when(tmpProduct.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(0);

		// insert data result
		String result = bnn0075ShippingScreenService.insertData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}

	// Test insert data no successful IvbTShipping table
	@Test
	@Transactional
	public void testInsertDataNoSuccessIvbTShippingTable() {

		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmpShip = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmpShip);
		when(tmpShip.insert(any(IvbTShippingControl.class))).thenReturn(0);

		// insert data result
		String result = bnn0075ShippingScreenService.insertData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}

	// Test insert data roll back
	@Test
	@Transactional
	public void testInsertDataRollback() {
		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmp = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).insert(any(IvbTShippingControl.class));

		// insert data result
		String result = bnn0075ShippingScreenService.insertData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}

	// Test insert data exception
	@Test
	@Transactional
	public void testInsertDataException() {
		Bnn0075SearchShippingScreenResult IvbTShippingControl = null;

		// insert data result
		String result = bnn0075ShippingScreenService.insertData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}

	// Test input update shippingNumber blank field, farm id blank field, area
	// id blank field, harvest date blank field
	@Test
	@Transactional
	public void testInputUpdateBlankFields() {
		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(0);
		// shipping number
		IvbTShippingControl.setShippingNumber("");
		// farm id
		IvbTShippingControl.setFarmId("");
		// area id
		IvbTShippingControl.setAreaId("");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);

		// input data result
		String result = bnn0075ShippingScreenService.updateData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}

	// Test update data successful
	@Test
	@Transactional
	public void testUpdateDataSuccess() {
		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmpShip = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmpShip);
		when(tmpShip.updateByExampleSelective(any(IvbTShippingControl.class), any(IvbTShippingControlExample.class)))
				.thenReturn(1);

		IvbTProductMapper tmpProduct = Mockito.mock(IvbTProductMapper.class);
		when(bnn0075shippingScreenDao.getIvbTProductMapper()).thenReturn(tmpProduct);
		when(tmpProduct.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(1);

		// update data result
		String result = bnn0075ShippingScreenService.updateData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}

	// Test update data no successful IvbTProduct table
	@Test
	@Transactional
	public void testUpdateDataNoSuccessIvbTProductTable() {

		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmpShip = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmpShip);
		when(tmpShip.updateByExampleSelective(any(IvbTShippingControl.class), any(IvbTShippingControlExample.class)))
				.thenReturn(1);

		IvbTProductMapper tmpProduct = Mockito.mock(IvbTProductMapper.class);
		when(bnn0075shippingScreenDao.getIvbTProductMapper()).thenReturn(tmpProduct);
		when(tmpProduct.updateByExampleSelective(any(IvbTProduct.class), any(IvbTProductExample.class))).thenReturn(0);

		// update data result
		String result = bnn0075ShippingScreenService.updateData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}

	// Test update data no successful IvbTShipping table
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataNoSuccessIvbTShippingTable() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);
		
		IvbTShippingControl.setLastUpdateDate(date);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmpShip = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmpShip);
		when(tmpShip.updateByExampleSelective(any(IvbTShippingControl.class), any(IvbTShippingControlExample.class)))
				.thenReturn(0);

		Bnn0075SearchShippSreenMapper tmp = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(0);
		
		Bnn0075SearchShippingScreenResult bnn0075SearchShippingScreenResult = new Bnn0075SearchShippingScreenResult();
		bnn0075SearchShippingScreenResult.setLastUpdateDateProduct(date);
		bnn0075SearchShippingScreenResult.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(bnn0075SearchShippingScreenResult);
		
		IvbTShippingControl ivbTShippingControl2 = new IvbTShippingControl();
		ivbTShippingControl2.setLastUpdateDate(date);
		when(tmpShip.selectByPrimaryKey(any(String.class))).thenReturn(ivbTShippingControl2);
		// update data result
		String result = bnn0075ShippingScreenService.updateData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_CULTIVATION_SHIPPING, result);
	}

	// Test update data no successful IvbTShipping table
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataNoSuccessIvbTShippingTable1() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);
		
		IvbTShippingControl.setLastUpdateDate(date);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmpShip = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmpShip);
		when(tmpShip.updateByExampleSelective(any(IvbTShippingControl.class), any(IvbTShippingControlExample.class)))
				.thenReturn(0);

		Bnn0075SearchShippSreenMapper tmp = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(0);
		
		Bnn0075SearchShippingScreenResult bnn0075SearchShippingScreenResult = new Bnn0075SearchShippingScreenResult();
		bnn0075SearchShippingScreenResult.setLastUpdateDateProduct(date);
		bnn0075SearchShippingScreenResult.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(bnn0075SearchShippingScreenResult);
		
		IvbTShippingControl ivbTShippingControl2 = new IvbTShippingControl();
		ivbTShippingControl2.setLastUpdateDate(format.parse("2017/05/18 13:25:25"));
		when(tmpShip.selectByPrimaryKey(any(String.class))).thenReturn(ivbTShippingControl2);
		// update data result
		String result = bnn0075ShippingScreenService.updateData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_UPDATE_DATE, result);
	}

	// Test update data no successful IvbTShipping table
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataNoSuccessIvbTShippingTable2() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);
		
		IvbTShippingControl.setLastUpdateDate(date);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmpShip = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmpShip);
		when(tmpShip.updateByExampleSelective(any(IvbTShippingControl.class), any(IvbTShippingControlExample.class)))
				.thenReturn(0);

		Bnn0075SearchShippSreenMapper tmp = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		
		Bnn0075SearchShippingScreenResult bnn0075SearchShippingScreenResult = new Bnn0075SearchShippingScreenResult();
		bnn0075SearchShippingScreenResult.setLastUpdateDateProduct(date);
		bnn0075SearchShippingScreenResult.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(bnn0075SearchShippingScreenResult);
		when(tmp.updateProductData(any(HashMap.class))).thenReturn(1);
		
		IvbTShippingControl ivbTShippingControl2 = new IvbTShippingControl();
		ivbTShippingControl2.setLastUpdateDate(format.parse("2017/05/18 13:25:25"));
		when(tmpShip.selectByPrimaryKey(any(String.class))).thenReturn(ivbTShippingControl2);
		// update data result
		String result = bnn0075ShippingScreenService.updateData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_SUCCESSFUL, result);
	}

	// Test update data no successful IvbTShipping table
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataNoSuccessIvbTShippingTable3() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);
		
		IvbTShippingControl.setLastUpdateDate(date);
		IvbTShippingControl.setLastUpdateDateProduct(date);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmpShip = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmpShip);
		when(tmpShip.updateByExampleSelective(any(IvbTShippingControl.class), any(IvbTShippingControlExample.class)))
				.thenReturn(0);

		Bnn0075SearchShippSreenMapper tmp = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		
		Bnn0075SearchShippingScreenResult bnn0075SearchShippingScreenResult = new Bnn0075SearchShippingScreenResult();
		bnn0075SearchShippingScreenResult.setLastUpdateDateProduct(date);
		bnn0075SearchShippingScreenResult.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(bnn0075SearchShippingScreenResult);
		when(tmp.updateProductData(any(HashMap.class))).thenReturn(0);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(IvbTShippingControl);
		
		IvbTShippingControl ivbTShippingControl2 = new IvbTShippingControl();
		ivbTShippingControl2.setLastUpdateDate(format.parse("2017/05/18 13:25:25"));
		when(tmpShip.selectByPrimaryKey(any(String.class))).thenReturn(ivbTShippingControl2);
		// update data result
		String result = bnn0075ShippingScreenService.updateData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_CULTIVATION_SHIPPING, result);
	}


	// Test update data no successful IvbTShipping table
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataNoSuccessIvbTShippingTable4() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);
		
		IvbTShippingControl.setLastUpdateDate(date);
		IvbTShippingControl.setLastUpdateDateProduct(date);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmpShip = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmpShip);
		when(tmpShip.updateByExampleSelective(any(IvbTShippingControl.class), any(IvbTShippingControlExample.class)))
				.thenReturn(0);

		Bnn0075SearchShippSreenMapper tmp = Mockito.mock(Bnn0075SearchShippSreenMapper.class);
		when(bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper()).thenReturn(tmp);
		when(tmp.updateData(any(HashMap.class))).thenReturn(1);
		
		Bnn0075SearchShippingScreenResult bnn0075SearchShippingScreenResult = new Bnn0075SearchShippingScreenResult();
		bnn0075SearchShippingScreenResult.setLastUpdateDateProduct(date);
		bnn0075SearchShippingScreenResult.setLastUpdateDate(date);
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(bnn0075SearchShippingScreenResult);
		when(tmp.updateProductData(any(HashMap.class))).thenReturn(0);
		Bnn0075SearchShippingScreenResult IvbTShippingControl1 = new Bnn0075SearchShippingScreenResult();
		IvbTShippingControl1.setLastUpdateDateProduct(format.parse("2017/05/18 13:25:25"));
		when(tmp.getLastUpdateDateProduct(any(HashMap.class))).thenReturn(IvbTShippingControl1);
		
		IvbTShippingControl ivbTShippingControl2 = new IvbTShippingControl();
		ivbTShippingControl2.setLastUpdateDate(format.parse("2017/05/18 13:25:25"));
		when(tmpShip.selectByPrimaryKey(any(String.class))).thenReturn(ivbTShippingControl2);
		// update data result
		String result = bnn0075ShippingScreenService.updateData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_CULTIVATION_SHIPPING_UPDATE_DATE, result);
	}
	// Test update data roll back
	@Test
	@Transactional
	public void testupdateDataRollback() {
		Bnn0075SearchShippingScreenResult IvbTShippingControl = new Bnn0075SearchShippingScreenResult();
		Date newDate = new Date(2017 - 01 - 01);
		// shipping number
		IvbTShippingControl.setShippingNumber("00000099");
		// farm id
		IvbTShippingControl.setFarmId("F001");
		// area id
		IvbTShippingControl.setAreaId("A001");
		// harvest date
		IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
		IvbTShippingControl.setShippingDate(newDate);

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingControlMapper tmp = Mockito.mock(IvbTShippingControlMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingControlMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).updateByExampleSelective(any(IvbTShippingControl.class),
				any(IvbTShippingControlExample.class));

		// update data result
		String result = bnn0075ShippingScreenService.updateData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}

	// Test update data exception
	@Test
	@Transactional
	public void testUpdateDataException() {
		Bnn0075SearchShippingScreenResult IvbTShippingControl = null;

		// upate data result
		String result = bnn0075ShippingScreenService.updateData(IvbTShippingControl);

		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}

	// Test create shipping number data successful
	@Test
	@Transactional
	public void testCreateShippingNumberDataSuccess() {

		bnn0075ShippingScreenService.setAppContext(appContext);
		IvbTShippingNumberMapper tmp = Mockito.mock(IvbTShippingNumberMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingNumberMapper()).thenReturn(tmp);
		// when(tmp.insert(any(IvbTShippingNumber.class))).thenReturn(1);

		// mockito for parameter
		doAnswer(new Answer<Integer>() {
			@Override
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				IvbTShippingNumber params = (IvbTShippingNumber) invocation.getArguments()[0];
				params.setId(1);
				return 1;
			}
		}).when(tmp).insert(any(IvbTShippingNumber.class));

		// update data result
		//String result = bnn0075ShippingScreenService.createData();
		// so the result will be failure
		//Assert.assertEquals("00000001", result);
	}

	// Test create shipping number data no successful
	@Test
	@Transactional
	public void testCreateShippingNumberDataNoSuccess() {

		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingNumberMapper tmp = Mockito.mock(IvbTShippingNumberMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingNumberMapper()).thenReturn(tmp);
		when(tmp.insert(any(IvbTShippingNumber.class))).thenReturn(0);

		// update data result
		// String result = bnn0075ShippingScreenService.createData();
		// so the result will be failure
		// Assert.assertEquals("", result);
	}

	// Test create shipping number data roll back
	@Test
	@Transactional
	public void testCreateShippingNumberDataRollback() {
		bnn0075ShippingScreenService.setAppContext(appContext);

		IvbTShippingNumberMapper tmp = Mockito.mock(IvbTShippingNumberMapper.class);
		when(bnn0075shippingScreenDao.getIvbTShippingNumberMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).insert(any(IvbTShippingNumber.class));
		// create data result
		//String result = bnn0075ShippingScreenService.createData();
		// so the result will be failure
		//Assert.assertEquals("", result);
	}

	@Test
	@Transactional
	public void testGetSingleData() {
		bnn0075ShippingScreenService.getSingleData("000000001");
	}
	// Test create shipping number data exception
	@Test
	@Transactional
	public void testCreateShippingNumberDataException() {
		// create data result
		//String result = bnn0075ShippingScreenService.createData();
		// so the result will be failure
		//Assert.assertEquals("", result);
	}

	// Test Get data for Area combobox by farm id
	@SuppressWarnings("unchecked")
	@Test
	public void testGetAreaDataByFarmId() throws SQLException {
		String farmId = "F002";
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0002");

		ResultSet resultSetMock = Mockito.mock(ResultSet.class);
		Mockito.when(resultSetMock.next()).thenReturn(true).thenReturn(false);
		Mockito.when(resultSetMock.getString("id")).thenReturn("A0007").thenReturn("A0007");
		Mockito.when(resultSetMock.getString("username")).thenReturn("A0007").thenReturn("A0007");
		Mockito.when(resultSetMock.getString("userfullname")).thenReturn("A0007").thenReturn("A0007");
		Mockito.when(resultSetMock.getString("screenid")).thenReturn("0003").thenReturn("0002");
		Mockito.when(resultSetMock.getString("roleid")).thenReturn("3").thenReturn("4");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, resultSetMock);

		Authentication authToken = new UsernamePasswordAuthenticationToken(u, "A0007", authorities);
		SecurityContextHolder.getContext().setAuthentication(authToken);

		List<UtilComponent> area = new ArrayList<UtilComponent>();
		area.add(new UtilComponent());
		bnn0075ShippingScreenService.setAppContext(appContext);
		// Mock setup
		UtilMapper tmp = Mockito.mock(UtilMapper.class);
		when(utilDao.getUtilMapper()).thenReturn(tmp);
		when(tmp.selectAreaDataByFarmIdMaster(any(HashMap.class))).thenReturn(area);

		// get area data
		List<UtilComponent> areaData = bnn0075ShippingScreenService.getAreaDataByFarmId(farmId);
		// start testing
		Assert.assertEquals(1, areaData.size());
	}
}