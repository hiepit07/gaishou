package vn.bananavietnam.ict.common.auth;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class MyUserDetailsServiceTest {
	
	private MyUserDetailsService detail = new MyUserDetailsService();
	
	@Test
	public void testCreateUserDetails() throws SQLException {
		MockitoAnnotations.initMocks(this);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		ArrayList<String> screenId = new ArrayList<String>();
		screenId.add("0075");
		MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
		
		detail.createUserDetails("A0007", u, authorities);
		
	}
	
	@Test
	public void testCreateUserDetailsNotMyuser() throws SQLException {
		MockitoAnnotations.initMocks(this);
		
		UserDetails userFromUserQuery = Mockito.mock(UserDetails.class);
		when(userFromUserQuery.getUsername()).thenReturn("A0007");
		when(userFromUserQuery.getPassword()).thenReturn("A0007");
		when(userFromUserQuery.isEnabled()).thenReturn(true);
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		detail.createUserDetails("A0007", userFromUserQuery, authorities);
		
	}
	
	@Test
	public void testCreateUserDetailsException(){
		MockitoAnnotations.initMocks(this);
		
		UserDetails userFromUserQuery = Mockito.mock(UserDetails.class);
		MyUser myUser = Mockito.mock(MyUser.class);
		doThrow(new RuntimeException()).when(userFromUserQuery).getUsername();
		detail.createUserDetailsFromMyuser(userFromUserQuery, myUser);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testLoadUsersByUsername() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		MyUserDetailsService detail = new MyUserDetailsService(){
			public List<UserDetails> loadUsersByUsername(String username){
				return super.loadUsersByUsername(username);
			}
			
			protected JdbcTemplate getMyJdbcTemplate(){
				JdbcTemplate jdbc = Mockito.mock(JdbcTemplate.class);
				doAnswer(new Answer<Void>() {
					@Override
					public Void answer(InvocationOnMock invocation) throws Throwable {
						RowMapper<UserDetails> params = (RowMapper<UserDetails>) invocation.getArguments()[2];
						ResultSet rsMock = Mockito.mock(ResultSet.class);
				        ArrayList<String> screenId = new ArrayList<String>();
				        screenId.add("0000");
				        when(rsMock.getString("id")).thenReturn("A0007");
				        when(rsMock.getString("username")).thenReturn("A0007");
				        when(rsMock.getString("password")).thenReturn("A0007");
				        when(rsMock.getBoolean("enabled")).thenReturn(true);
				        when(rsMock.getString("userfullname")).thenReturn("A0007");
				        when(rsMock.getString("roleid")).thenReturn("1");
				        when(rsMock.getString("screenid")).thenReturn("0000");
				        when(rsMock.next()).thenReturn(true)
				        					.thenReturn(true)
				        					.thenReturn(false);
				        
				        List<ResultSet> rs = new ArrayList<ResultSet>();
				        rs.add(rsMock);
				        params.mapRow(rs.get(0), 10);
						return null;
					}
				  }).when(jdbc).query(anyString(), (String[])any(), (RowMapper<UserDetails>)any());
				return jdbc;
			}
		};

		detail.loadUsersByUsername("A0007");
	}
	
	@Test
	public void testGetMyJdbcTemplate(){
		detail.getMyJdbcTemplate();
	}

}