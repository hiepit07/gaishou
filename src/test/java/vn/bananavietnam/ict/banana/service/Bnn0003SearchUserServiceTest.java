package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.component.Bnn0003SearchUserConditions;
import vn.bananavietnam.ict.banana.component.Bnn0003SearchUserDataObject;
import vn.bananavietnam.ict.banana.component.Bnn0003SearchUserResult;
import vn.bananavietnam.ict.banana.dao.Bnn0003SearchUserDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0003SearchUserMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.mapper.IvbMUsersMapper;
import vn.bananavietnam.ict.common.db.model.IvbMUsers;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0003SearchUserServiceTest {

	@Autowired
    private ApplicationContext appContext;

	@Autowired
	private Bnn0003SearchUserService bnn0003SearchUserService;

	@InjectMocks
	private Bnn0003SearchUserService bnn0003SearchUserService_Mock;

	@Mock
	Bnn0003SearchUserDao dao;

	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
            
        ResultSet resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSetMock.getString("id")).thenReturn("A0007").thenReturn("A0007");
        Mockito.when(resultSetMock.getString("username")).thenReturn("A0007").thenReturn("A0007");
        Mockito.when(resultSetMock.getString("userfullname")).thenReturn("A0007").thenReturn("A0007");
        Mockito.when(resultSetMock.getString("screenid")).thenReturn("0003").thenReturn("0005");
        Mockito.when(resultSetMock.getString("roleid")).thenReturn("4").thenReturn("1");
        
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, resultSetMock);

        Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        
    }

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataNoResult() {
		Bnn0003SearchUserConditions searchConditions = new Bnn0003SearchUserConditions();
		// user id
		searchConditions.setUsersId("");
		// user name
		searchConditions.setUsersName("");
		// from
		searchConditions.setFromRow("0");
		// item per page
		searchConditions.setItemCount("5");

		bnn0003SearchUserService_Mock.setAppContext(appContext);
		List<Bnn0003SearchUserResult> bnn0003ResultListReturn = new ArrayList<Bnn0003SearchUserResult>();
		// Mock setup
		Bnn0003SearchUserMapper tmp = Mockito.mock(Bnn0003SearchUserMapper.class);
		when(dao.getBnn0003SearchUserMapper()).thenReturn(tmp);
		when(tmp.searchData(any(HashMap.class))).thenReturn(bnn0003ResultListReturn);
		when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("0");

		// get search result
		List<Bnn0003SearchUserResult> bnn0003ResultList = bnn0003SearchUserService_Mock.searchData(searchConditions);
		// start testing
		Assert.assertEquals(0, bnn0003ResultList.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataNoResultWithIdAndName() {
		Bnn0003SearchUserConditions searchConditions = new Bnn0003SearchUserConditions();
		// user id
		searchConditions.setUsersId("1");
		// user name
		searchConditions.setUsersName("1");
		// from
		searchConditions.setFromRow("1");
		// item per page
		searchConditions.setItemCount("1");
		
		bnn0003SearchUserService_Mock.setAppContext(appContext);
		// Mock setup
		Bnn0003SearchUserMapper tmp = Mockito.mock(Bnn0003SearchUserMapper.class);
		when(dao.getBnn0003SearchUserMapper()).thenReturn(tmp);
		when(tmp.searchData(any(HashMap.class))).thenReturn(new ArrayList<Bnn0003SearchUserResult>());

		// get search result
		List<Bnn0003SearchUserResult> bnn0003ResultList = bnn0003SearchUserService_Mock.searchData(searchConditions);
		// start testing
		Assert.assertEquals(0, bnn0003ResultList.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataOutOfMemoryException() {
		Bnn0003SearchUserConditions searchConditions = new Bnn0003SearchUserConditions();
		// user id
		searchConditions.setUsersId("1");
		// user name
		searchConditions.setUsersName("1");
		// from
		searchConditions.setFromRow("1");
		// item per page
		searchConditions.setItemCount("1");
		
		bnn0003SearchUserService_Mock.setAppContext(appContext);
		// Mock setup
		Bnn0003SearchUserMapper tmp = Mockito.mock(Bnn0003SearchUserMapper.class);
		when(dao.getBnn0003SearchUserMapper()).thenReturn(tmp);
        doThrow(new OutOfMemoryError()).when(tmp).searchData(any(HashMap.class));

		// get search result
		List<Bnn0003SearchUserResult> bnn0003ResultList = bnn0003SearchUserService_Mock.searchData(searchConditions);
		// start testing
		Assert.assertEquals("-1", bnn0003ResultList.get(0).getSearchDataTotalCounts());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataInnerException() {
		Bnn0003SearchUserConditions searchConditions = new Bnn0003SearchUserConditions();
		// user id
		searchConditions.setUsersId("1");
		// user name
		searchConditions.setUsersName("1");
		// from
		searchConditions.setFromRow("1");
		// item per page
		searchConditions.setItemCount("1");

		bnn0003SearchUserService_Mock.setAppContext(appContext);
		// Mock setup
		Bnn0003SearchUserMapper tmp = Mockito.mock(Bnn0003SearchUserMapper.class);
        when(dao.getBnn0003SearchUserMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));

		// get search result
		List<Bnn0003SearchUserResult> bnn0003ResultList = bnn0003SearchUserService_Mock.searchData(searchConditions);
		// start testing
		Assert.assertEquals(null, bnn0003ResultList);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataOuterException() {
		Bnn0003SearchUserConditions searchConditions = new Bnn0003SearchUserConditions();
		// user id
		searchConditions.setUsersId("1");
		// user name
		searchConditions.setUsersName("1");
		// from
		searchConditions.setFromRow("1");
		// item per page
		searchConditions.setItemCount("1");

		// Mock setup
		Bnn0003SearchUserMapper tmp = Mockito.mock(Bnn0003SearchUserMapper.class);
        when(dao.getBnn0003SearchUserMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(HashMap.class));

		// get search result
		List<Bnn0003SearchUserResult> bnn0003ResultList = bnn0003SearchUserService_Mock.searchData(searchConditions);
		// start testing
		Assert.assertEquals(null, bnn0003ResultList);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchDataSuccess() {
		Bnn0003SearchUserConditions searchConditions = new Bnn0003SearchUserConditions();
		// user id
		searchConditions.setUsersId("1");
		// user name
		searchConditions.setUsersName("1");
		// from
		searchConditions.setFromRow("1");
		// item per page
		searchConditions.setItemCount("1");
		
		bnn0003SearchUserService_Mock.setAppContext(appContext);
		// Mock setup
		List<Bnn0003SearchUserResult> userResult = new ArrayList<Bnn0003SearchUserResult>();
		Bnn0003SearchUserResult userData = new Bnn0003SearchUserResult();
		userResult.add(userData);
		Bnn0003SearchUserMapper tmp = Mockito.mock(Bnn0003SearchUserMapper.class);
        when(dao.getBnn0003SearchUserMapper()).thenReturn(tmp);
        when(tmp.searchData(any(HashMap.class))).thenReturn(userResult);

        // Mock setup
        when(tmp.getSearchDataTotalCounts(any(HashMap.class))).thenReturn("1");

		// get search result
		List<Bnn0003SearchUserResult> bnn0003ResultList = bnn0003SearchUserService_Mock.searchData(searchConditions);
		// start testing
		Assert.assertEquals(1, bnn0003ResultList.size());
	}

	@Test
	public void testUpdateDataBlankFields() {
		Bnn0003SearchUserDataObject userData = new Bnn0003SearchUserDataObject();
		// user id
		userData.setUsersId("");

		// update user
		String result = bnn0003SearchUserService.updateData(userData);
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}
	@Test
	public void testUpdateDataBlankFields1() {
		Bnn0003SearchUserDataObject userData = new Bnn0003SearchUserDataObject();
		// user id
		userData.setUsersId("1");
		// users name
		userData.setUsersName("");

		// update user
		String result = bnn0003SearchUserService.updateData(userData);
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}
	@Test
	public void testUpdateDataBlankFields2() {
		Bnn0003SearchUserDataObject userData = new Bnn0003SearchUserDataObject();
		// user id
		userData.setUsersId("1");
		// users name
		userData.setUsersName("1");
        // password
		userData.setPassword("");

		// update user
		String result = bnn0003SearchUserService.updateData(userData);
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataRollback() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		Bnn0003SearchUserDataObject userData = new Bnn0003SearchUserDataObject();
		// user id
		userData.setUsersId("1");
		// users name
		userData.setUsersName("1");
        // password
		userData.setPassword("1");
		// last Update Date
		userData.setLastUpdateDate(date);

		IvbMUsers ivbMUsers = new IvbMUsers();
		ivbMUsers.setLastUpdateDate(date);
		// Mock setup
		IvbMUsersMapper tmp1 = Mockito.mock(IvbMUsersMapper.class);
		when(dao.getIvbMUsersMapper()).thenReturn(tmp1);
		when(tmp1.selectByPrimaryKey(any(String.class))).thenReturn(ivbMUsers);
		
		Bnn0003SearchUserMapper tmp = Mockito.mock(Bnn0003SearchUserMapper.class);
		
        when(dao.getBnn0003SearchUserMapper()).thenReturn(tmp);
        when(tmp.updateData(any(HashMap.class))).thenReturn(0);

        // update user
        bnn0003SearchUserService_Mock.setAppContext(appContext);
 		String result = bnn0003SearchUserService_Mock.updateData(userData);
 		// so the result will be failure
 		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_CULTIVATION_USER, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataFailedUpdateDate() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		Bnn0003SearchUserDataObject userData = new Bnn0003SearchUserDataObject();
		// user id
		userData.setUsersId("1");
		// users name
		userData.setUsersName("1");
        // password
		userData.setPassword("1");
		// last Update Date
		userData.setLastUpdateDate(date);

		IvbMUsers ivbMUsers = new IvbMUsers();
		ivbMUsers.setLastUpdateDate(format.parse("2017/05/18 13:25:25"));
		// Mock setup
		IvbMUsersMapper tmp1 = Mockito.mock(IvbMUsersMapper.class);
		when(dao.getIvbMUsersMapper()).thenReturn(tmp1);
		when(tmp1.selectByPrimaryKey(any(String.class))).thenReturn(ivbMUsers);
		
		Bnn0003SearchUserMapper tmp = Mockito.mock(Bnn0003SearchUserMapper.class);
		
        when(dao.getBnn0003SearchUserMapper()).thenReturn(tmp);
        when(tmp.updateData(any(HashMap.class))).thenReturn(0);

        // update user
        bnn0003SearchUserService_Mock.setAppContext(appContext);
 		String result = bnn0003SearchUserService_Mock.updateData(userData);
 		// so the result will be failure
 		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_UPDATE_DATE, result);
	}

	@Test
	@Transactional
	public void testUpdateDataInnerException() {
		Bnn0003SearchUserDataObject userData = new Bnn0003SearchUserDataObject();
		// user id
		userData.setUsersId("1");
		// users name
		userData.setUsersName("1");
        // password
		userData.setPassword("1");
		userData.setPasswordChanged(true);

		// Mock setup
		IvbMUsersMapper tmp = Mockito.mock(IvbMUsersMapper.class);
        when(dao.getIvbMUsersMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).updateByPrimaryKeySelective(any(IvbMUsers.class));

        // update user
        bnn0003SearchUserService_Mock.setAppContext(appContext);
 		String result = bnn0003SearchUserService_Mock.updateData(userData);
 		// so the result will be failure
 		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}
	@Test
	@Transactional
	public void testUpdateDataOuterException() {
		Bnn0003SearchUserDataObject userData = new Bnn0003SearchUserDataObject();
		// user id
		userData.setUsersId("1");
		// users name
		userData.setUsersName("1");
        // password
		userData.setPassword("1");

        // update user
 		String result = bnn0003SearchUserService_Mock.updateData(userData);
 		// so the result will be failure
 		Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataSuccess() throws ParseException {
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		Bnn0003SearchUserDataObject userData = new Bnn0003SearchUserDataObject();
		// user id
		userData.setUsersId("1");
		// users name
		userData.setUsersName("1");
        // password
		userData.setPassword("1");
		// last Update Date
		userData.setLastUpdateDate(date);

		// Mock setup
		Bnn0003SearchUserMapper tmp = Mockito.mock(Bnn0003SearchUserMapper.class);
        when(dao.getBnn0003SearchUserMapper()).thenReturn(tmp);
        when(tmp.updateData(any(HashMap.class))).thenReturn(1);

		// update user
        bnn0003SearchUserService_Mock.setAppContext(appContext);
		String result = bnn0003SearchUserService_Mock.updateData(userData);
		// so the result will be failure
		Assert.assertEquals(Constants.UPDATE_RESULT_SUCCESSFUL, result);
	}

	@Test
	public void testInsertDataBlankFields() {
		IvbMUsers userData = new IvbMUsers();
		// user id
		userData.setUsersId("");

		// update user
		String result = bnn0003SearchUserService.insertData(userData);
		// so the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}
	@Test
	@Transactional
	public void testInsertDataRollback() {
		IvbMUsers userData = new IvbMUsers();
		// user id
		userData.setUsersId("U1111111111");
		// users name
		userData.setUsersName("1");
        // password
		userData.setPassword("1");

		// Mock setup
		IvbMUsersMapper tmp = Mockito.mock(IvbMUsersMapper.class);
        when(dao.getIvbMUsersMapper()).thenReturn(tmp);
		when(tmp.insert(any(IvbMUsers.class))).thenReturn(0);

		// update user
		bnn0003SearchUserService_Mock.setAppContext(appContext);
		String result = bnn0003SearchUserService_Mock.insertData(userData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_CULTIVATION_USER, result);
	}
	@Test
	@Transactional
	public void testInsertDataInnerException() {
		IvbMUsers userData = new IvbMUsers();
		// user id
		userData.setUsersId("U1111111111");
		// users name
		userData.setUsersName("1");
        // password
		userData.setPassword("1");

		// Mock setup
		IvbMUsersMapper tmp = Mockito.mock(IvbMUsersMapper.class);
        when(dao.getIvbMUsersMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).insert(any(IvbMUsers.class));

		// update user
		bnn0003SearchUserService_Mock.setAppContext(appContext);
		String result = bnn0003SearchUserService_Mock.insertData(userData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}
	@Test
	@Transactional
	public void testInsertDataOuterException() {
		IvbMUsers userData = new IvbMUsers();
		// user id
		userData.setUsersId("U1111111111");
		// users name
		userData.setUsersName("1");
        // password
		userData.setPassword("1");

		// update user
		String result = bnn0003SearchUserService_Mock.insertData(userData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_FAILED_EXCEPTION, result);
	}
	@Test
	@Transactional
	public void testInsertDataSuccess() {
		IvbMUsers userData = new IvbMUsers();
		// user id
		userData.setUsersId("U1111111111");
		// users name
		userData.setUsersName("1");
        // password
		userData.setPassword("1");

		// Mock setup
		IvbMUsersMapper tmp = Mockito.mock(IvbMUsersMapper.class);
        when(dao.getIvbMUsersMapper()).thenReturn(tmp);
		when(tmp.insert(any(IvbMUsers.class))).thenReturn(1);

		// update user
		bnn0003SearchUserService_Mock.setAppContext(appContext);
		String result = bnn0003SearchUserService_Mock.insertData(userData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_SUCCESSFUL, result);
	}
	@Test
	@Transactional
	public void testInsertDataDuplicate() {
		IvbMUsers userData = new IvbMUsers();
		// user id
		userData.setUsersId("U1111111111");
		// users name
		userData.setUsersName("1");
        // password
		userData.setPassword("1");

		// Mock setup
		IvbMUsersMapper tmp = Mockito.mock(IvbMUsersMapper.class);
        when(dao.getIvbMUsersMapper()).thenReturn(tmp);
		doThrow(new DuplicateKeyException("")).when(tmp).insert(any(IvbMUsers.class));

		// update user
		bnn0003SearchUserService_Mock.setAppContext(appContext);
		String result = bnn0003SearchUserService_Mock.insertData(userData);
		// so the result will be failure
		Assert.assertEquals(Constants.INSERT_RESULT_DUPLICATED, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataRollback() throws ParseException {
		
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		// user id
		String usersId = "U1111111111";

		// Mock setup
		Bnn0003SearchUserMapper tmp = Mockito.mock(Bnn0003SearchUserMapper.class);
        when(dao.getBnn0003SearchUserMapper()).thenReturn(tmp);
        when(tmp.updateData(any(HashMap.class))).thenReturn(0);
        IvbMUsers ivbMUsers = new IvbMUsers();
        ivbMUsers.setUsersId(usersId);
        ivbMUsers.setLastUpdateDate(date);
        IvbMUsersMapper tmpUser = Mockito.mock(IvbMUsersMapper.class);
        when(dao.getIvbMUsersMapper()).thenReturn(tmpUser);
        when(tmpUser.selectByPrimaryKey(any(String.class))).thenReturn(ivbMUsers);
		// delete user
        bnn0003SearchUserService_Mock.setAppContext(appContext);
		String result = bnn0003SearchUserService_Mock.deleteData(ivbMUsers);
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_CULTIVATION_USER, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataFailedUpdateDate() throws ParseException {
		
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		// user id
		String usersId = "U1111111111";

		// Mock setup
		Bnn0003SearchUserMapper tmp = Mockito.mock(Bnn0003SearchUserMapper.class);
        when(dao.getBnn0003SearchUserMapper()).thenReturn(tmp);
        when(tmp.updateData(any(HashMap.class))).thenReturn(0);
        IvbMUsers ivbMUsers = new IvbMUsers();
        ivbMUsers.setUsersId(usersId);
        ivbMUsers.setLastUpdateDate(date);
        
        IvbMUsersMapper tmpUser = Mockito.mock(IvbMUsersMapper.class);
        when(dao.getIvbMUsersMapper()).thenReturn(tmpUser);
        when(tmpUser.selectByPrimaryKey(any(String.class))).thenReturn(ivbMUsers);
		// delete user
        IvbMUsers ivbMUser1 = new IvbMUsers();
        ivbMUser1.setUsersId(usersId);
        ivbMUser1.setLastUpdateDate(format.parse("2017/05/18 13:25:25"));
        bnn0003SearchUserService_Mock.setAppContext(appContext);
		String result = bnn0003SearchUserService_Mock.deleteData(ivbMUser1);
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_UPDATE_DATE, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testDeleteDataSuccess() {
		// user id
		String usersId = "U1111111111";

		// Mock setup
		Bnn0003SearchUserMapper tmp = Mockito.mock(Bnn0003SearchUserMapper.class);
        when(dao.getBnn0003SearchUserMapper()).thenReturn(tmp);
        when(tmp.updateData(any(HashMap.class))).thenReturn(1);

        IvbMUsers ivbMUsers = new IvbMUsers();
        ivbMUsers.setUsersId(usersId);
		// delete user
        bnn0003SearchUserService_Mock.setAppContext(appContext);
		String result = bnn0003SearchUserService_Mock.deleteData(ivbMUsers);
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_SUCCESSFUL, result);
	}
	@Test
	@Transactional
	public void testDeleteDataInnerException() {
		// user id
		String usersId = "U1111111111";

		// Mock setup
		IvbMUsersMapper tmp = Mockito.mock(IvbMUsersMapper.class);
        when(dao.getIvbMUsersMapper()).thenReturn(tmp);
		doThrow(new RuntimeException()).when(tmp).updateByPrimaryKeySelective(any(IvbMUsers.class));
		IvbMUsers ivbMUsers = new IvbMUsers();
        ivbMUsers.setUsersId(usersId);
		// delete user
        bnn0003SearchUserService_Mock.setAppContext(appContext);
		String result = bnn0003SearchUserService_Mock.deleteData(ivbMUsers);
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}
	
	@Test
	@Transactional
	public void testDeleteDataOuterException() {
		// user id
		String usersId = "U1111111111";

		IvbMUsers ivbMUsers = new IvbMUsers();
        ivbMUsers.setUsersId(usersId);
		// delete user
		String result = bnn0003SearchUserService_Mock.deleteData(ivbMUsers);
		// so the result will be failure
		Assert.assertEquals(Constants.DELETE_RESULT_FAILED_EXCEPTION, result);
	}

	@Test
	public void testGetSingleData() {
		String usersId = "U1111111111";

		// Mock setup
		IvbMUsers userResult = new IvbMUsers();
		userResult.setUsersId("U1111111111");
		IvbMUsersMapper tmp = Mockito.mock(IvbMUsersMapper.class);
        when(dao.getIvbMUsersMapper()).thenReturn(tmp);
        when(tmp.selectByPrimaryKey(any(String.class))).thenReturn(userResult);

		// get user data
        bnn0003SearchUserService_Mock.setAppContext(appContext);
		IvbMUsers userData = bnn0003SearchUserService_Mock.getSingleData(usersId);
		// start testing
		Assert.assertEquals("U1111111111", userData.getUsersId());
	}

	@Test
	public void testGetSingleDataInnerException() {
		String usersId = "U1111111111";

		// Mock setup
		IvbMUsers userResult = new IvbMUsers();
		userResult.setUsersId("U1111111111");
		IvbMUsersMapper tmp = Mockito.mock(IvbMUsersMapper.class);
        when(dao.getIvbMUsersMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).selectByPrimaryKey(any(String.class));

		// get user data
        bnn0003SearchUserService_Mock.setAppContext(appContext);
		IvbMUsers userData = bnn0003SearchUserService_Mock.getSingleData(usersId);
		// start testing
		Assert.assertEquals(null, userData);
	}

	@Test
	public void testGetSingleDataOuterException() {
		String usersId = "U1111111111";

		// Mock setup
		IvbMUsers userResult = new IvbMUsers();
		userResult.setUsersId("U1111111111");
		IvbMUsers userData = bnn0003SearchUserService_Mock.getSingleData(usersId);
		// start testing
		Assert.assertEquals(null, userData);
	}
}