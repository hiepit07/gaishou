package vn.bananavietnam.ict.common.auth;

import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class MyUserTest {
	@Mock
	ResultSet rsMock;

	
	@Test
	public void testMyUserWhileTrue() throws Exception{
		MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0000");
        when(rsMock.getString("id")).thenReturn("A0007");
        when(rsMock.getString("username")).thenReturn("A0007");
        when(rsMock.getString("userfullname")).thenReturn("A0007");
        when(rsMock.getString("roleid")).thenReturn("1");
        when(rsMock.getString("screenid")).thenReturn("0000");
        when(rsMock.next()).thenReturn(true)
        					.thenReturn(true)
        					.thenReturn(false);
        
        List<ResultSet> rs = new ArrayList<ResultSet>();
        rs.add(rsMock);
        
        MyUser user = new MyUser("A0007", "A0007", true, true, true, true, authorities, rs.get(0));
        Assert.assertEquals("A0007", user.getUsername());
	}
	
	@Test
	public void testMyUserWhileTrueIfOneTrue() throws Exception{
		MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0000");
        when(rsMock.getString("id")).thenReturn("A0007");
        when(rsMock.getString("username")).thenReturn("A0007");
        when(rsMock.getString("userfullname")).thenReturn("A0007");
        when(rsMock.getString("roleid")).thenReturn("1");
        when(rsMock.getString("screenid")).thenReturn("0000")
        									.thenReturn(null);
        when(rsMock.next()).thenReturn(true)
        					.thenReturn(true)
        					.thenReturn(false);
        
        List<ResultSet> rs = new ArrayList<ResultSet>();
        rs.add(rsMock);
        
        MyUser user = new MyUser("A0007", "A0007", true, true, true, true, authorities, rs.get(0));
        Assert.assertEquals("A0007", user.getUsername());
	}
	
	@Test
	public void testMyUserWhileTrueIfTwo() throws Exception{
		MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0000");
        when(rsMock.getString("id")).thenReturn("A0007");
        when(rsMock.getString("username")).thenReturn("A0007");
        when(rsMock.getString("userfullname")).thenReturn("A0007");
        when(rsMock.getString("roleid")).thenReturn("1")
        								.thenReturn("");
        when(rsMock.getString("screenid")).thenReturn("0000");
        when(rsMock.next()).thenReturn(true)
        					.thenReturn(true)
        					.thenReturn(false);
        
        List<ResultSet> rs = new ArrayList<ResultSet>();
        rs.add(rsMock);
        
        MyUser user = new MyUser("A0007", "A0007", true, true, true, true, authorities, rs.get(0));
        Assert.assertEquals("A0007", user.getUsername());
	}
	
	@Test
	public void testMyUserWhileFalse() throws Exception{
		MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0000");
        when(rsMock.getString("id")).thenReturn("A0007");
        when(rsMock.getString("username")).thenReturn("A0007");
        when(rsMock.getString("userfullname")).thenReturn("A0007");
        when(rsMock.getString("roleid")).thenReturn("1");
        when(rsMock.getString("screenid")).thenReturn("0000");
        when(rsMock.next()).thenReturn(false);
        
        List<ResultSet> rs = new ArrayList<ResultSet>();
        rs.add(rsMock);
        
        MyUser user = new MyUser("A0007", "A0007", true, true, true, true, authorities, rs.get(0));
        Assert.assertEquals("A0007", user.getUsername());
	}
	
	@Test
	public void testMyUser1() throws Exception{
		MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0000");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        MyUser user = new MyUser("A0007", "A0007", true , true , true , true , authorities , u);
        Assert.assertEquals("A0007", user.getUsername());
	}
	
	@Test
	public void testMyUser2() throws Exception{
		MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0000");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        Assert.assertEquals("1", u.getID());
        Assert.assertEquals("1", u.getROLEID());
        Assert.assertEquals("A0007", u.getUSERNAME());
        Assert.assertEquals("A0007", u.getUSERFULLNAME());
        Assert.assertEquals(screenId, u.getSCREENID());
        
	}

}