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

import vn.bananavietnam.ict.banana.component.Bnn0002ChangePasswordDataObject;
import vn.bananavietnam.ict.banana.dao.Bnn0002ChangePasswordDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0002ChangePasswordMapper;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.mapper.IvbMUsersMapper;
import vn.bananavietnam.ict.common.db.model.IvbMUsers;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0002ChangePasswordServiceTest {

	@Autowired
    private ApplicationContext appContext;

	@Autowired
	private Bnn0002ChangePasswordService bnn0002ChangePasswordService;

	@InjectMocks
	private Bnn0002ChangePasswordService bnn0002ChangePasswordService_Mock;

	@Mock
	Bnn0002ChangePasswordDao dao;

	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0002");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
	}

	@Test
	public void testUpdateDataBlankFields() {
		Bnn0002ChangePasswordDataObject passwordData = new Bnn0002ChangePasswordDataObject();
		// old password
		passwordData.setOldPassword("");
		// update password
		String result = bnn0002ChangePasswordService.updateData(passwordData);
		// the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}
	@Test
	public void testUpdateDataBlankFields1() {
		Bnn0002ChangePasswordDataObject passwordData = new Bnn0002ChangePasswordDataObject();
		// old password
		passwordData.setOldPassword("1");
		// new password
		passwordData.setNewPassword("");
		// update password
		String result = bnn0002ChangePasswordService.updateData(passwordData);
		// the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}
	@Test
	public void testUpdateDataBlankFields2() {
		Bnn0002ChangePasswordDataObject passwordData = new Bnn0002ChangePasswordDataObject();
		// old password
		passwordData.setOldPassword("1");
		// new password
		passwordData.setNewPassword("1");
		// new password confirm
		passwordData.setNewPasswordConfirm("");
		// update password
		String result = bnn0002ChangePasswordService.updateData(passwordData);
		// the result will be failure
		Assert.assertEquals(Constants.VALIDATE_BLANK_FIELDS, result);
	}
	@Test
	public void testUpdateDataPasswordNotMatch() {
		Bnn0002ChangePasswordDataObject passwordData = new Bnn0002ChangePasswordDataObject();
		// old password
		passwordData.setOldPassword("1");
		// new password
		passwordData.setNewPassword("2");
		// new password confirm
		passwordData.setNewPasswordConfirm("3");
		// update password
		String result = bnn0002ChangePasswordService.updateData(passwordData);
		// the result will be failure
		Assert.assertEquals(Constants.VALIDATE_PASSWORD_NOT_MATCH, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateDataPasswordNotCorrect() {
		Bnn0002ChangePasswordDataObject passwordData = new Bnn0002ChangePasswordDataObject();
		// old password
		passwordData.setOldPassword("1");
		// new password
		passwordData.setNewPassword("1");
		// new password confirm
		passwordData.setNewPasswordConfirm("1");
		bnn0002ChangePasswordService_Mock.setAppContext(appContext);
		// Mock setup
		Bnn0002ChangePasswordMapper tmp = Mockito.mock(Bnn0002ChangePasswordMapper.class);
		when(dao.getBnn0002ChangePasswordMapper()).thenReturn(tmp);
		when(tmp.comparePassword(any(HashMap.class))).thenReturn(null);
		
		IvbMUsers ivbMUsers = new IvbMUsers();
		ivbMUsers.setUsersId("1");
		ivbMUsers.setPassword("1");
		IvbMUsersMapper tmp1 = Mockito.mock(IvbMUsersMapper.class);
		when(dao.getIvbMUsersMapper()).thenReturn(tmp1);
		when(tmp1.selectByPrimaryKey(any(String.class))).thenReturn(ivbMUsers);

		// update password
		String result = bnn0002ChangePasswordService_Mock.updateData(passwordData);
		// the result will be failure
		Assert.assertEquals(Constants.VALIDATE_PASSWORD_NOT_CORRECT, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataRollback() throws ParseException {
		Bnn0002ChangePasswordDataObject passwordData = new Bnn0002ChangePasswordDataObject();
		// old password
		passwordData.setOldPassword("1");
		// new password
		passwordData.setNewPassword("1");
		// new password confirm
		passwordData.setNewPasswordConfirm("1");

		// Mock setup
		Bnn0002ChangePasswordMapper tmp = Mockito.mock(Bnn0002ChangePasswordMapper.class);
		when(dao.getBnn0002ChangePasswordMapper()).thenReturn(tmp);
		when(tmp.comparePassword(any(HashMap.class))).thenReturn("1");
		
		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		IvbMUsers ivbMUsers = new IvbMUsers();
		ivbMUsers.setUsersId("1");
		ivbMUsers.setPassword("1");
		ivbMUsers.setLastUpdateDate(date);
		
		IvbMUsersMapper tmp1 = Mockito.mock(IvbMUsersMapper.class);
		when(dao.getIvbMUsersMapper()).thenReturn(tmp1);
		when(tmp1.selectByPrimaryKey(any(String.class))).thenReturn(ivbMUsers);
		
		// Mock setup
        when(dao.getIvbMUsersMapper()).thenReturn(tmp1);
        when(tmp1.updateByPrimaryKeySelective(any(IvbMUsers.class))).thenReturn(0);

        // update password
        bnn0002ChangePasswordService_Mock.setAppContext(appContext);
        String result = bnn0002ChangePasswordService_Mock.updateData(passwordData);
        // the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_CULTIVATION_PASSWORD, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataInnerException() {
		Bnn0002ChangePasswordDataObject passwordData = new Bnn0002ChangePasswordDataObject();
		// old password
		passwordData.setOldPassword("1");
		// new password
		passwordData.setNewPassword("1");
		// new password confirm
		passwordData.setNewPasswordConfirm("1");

		// Mock setup
		Bnn0002ChangePasswordMapper tmp = Mockito.mock(Bnn0002ChangePasswordMapper.class);
		when(dao.getBnn0002ChangePasswordMapper()).thenReturn(tmp);
		when(tmp.comparePassword(any(HashMap.class))).thenReturn("1");
		// Mock setup
		IvbMUsersMapper tmp1 = Mockito.mock(IvbMUsersMapper.class);
        when(dao.getIvbMUsersMapper()).thenReturn(tmp1);
        doThrow(new RuntimeException()).when(tmp1).updateByPrimaryKeySelective(any(IvbMUsers.class));
        bnn0002ChangePasswordService_Mock.setAppContext(appContext);
        String result = bnn0002ChangePasswordService_Mock.updateData(passwordData);
        // the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataOuterException() {
		Bnn0002ChangePasswordDataObject passwordData = new Bnn0002ChangePasswordDataObject();
		// old password
		passwordData.setOldPassword("1");
		// new password
		passwordData.setNewPassword("1");
		// new password confirm
		passwordData.setNewPasswordConfirm("1");
		
		IvbMUsers ivbMUsers = new IvbMUsers();
		ivbMUsers.setUsersId("1");
		ivbMUsers.setPassword("1");
		
		IvbMUsersMapper tmp1 = Mockito.mock(IvbMUsersMapper.class);
		when(dao.getIvbMUsersMapper()).thenReturn(tmp1);
		when(tmp1.selectByPrimaryKey(any(String.class))).thenReturn(ivbMUsers);
		
		// Mock setup
		Bnn0002ChangePasswordMapper tmp = Mockito.mock(Bnn0002ChangePasswordMapper.class);
		when(dao.getBnn0002ChangePasswordMapper()).thenReturn(tmp);
		when(tmp.comparePassword(any(HashMap.class))).thenReturn("1");

        // update password
        String result = bnn0002ChangePasswordService_Mock.updateData(passwordData);
        // the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_FAILED_EXCEPTION, result);
	}
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testUpdateDataSuccess() throws ParseException {
		Bnn0002ChangePasswordDataObject passwordData = new Bnn0002ChangePasswordDataObject();
		// old password
		passwordData.setOldPassword("1");
		// new password
		passwordData.setNewPassword("1");
		// new password confirm
		passwordData.setNewPasswordConfirm("1");

		String string = "2017/05/17 13:25:25";
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		java.util.Date date = format.parse(string);
		
		IvbMUsers ivbMUsers = new IvbMUsers();
		ivbMUsers.setUsersId("1");
		ivbMUsers.setPassword("1");
		ivbMUsers.setLastUpdateDate(date);
		
		// Mock setup
		Bnn0002ChangePasswordMapper tmp = Mockito.mock(Bnn0002ChangePasswordMapper.class);
		when(dao.getBnn0002ChangePasswordMapper()).thenReturn(tmp);
		when(tmp.comparePassword(any(HashMap.class))).thenReturn("1");
		IvbMUsersMapper tmp1 = Mockito.mock(IvbMUsersMapper.class);
		when(dao.getIvbMUsersMapper()).thenReturn(tmp1);
		when(tmp1.selectByPrimaryKey(any(String.class))).thenReturn(ivbMUsers);
		// Mock setup
        when(tmp.updateData(any(HashMap.class))).thenReturn(1);

        // update password
        bnn0002ChangePasswordService_Mock.setAppContext(appContext);
        String result = bnn0002ChangePasswordService_Mock.updateData(passwordData);
        // the result will be failure
        Assert.assertEquals(Constants.UPDATE_RESULT_SUCCESSFUL, result);
	}
}
