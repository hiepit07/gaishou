package vn.bananavietnam.ict.common.auth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

public class MyUserDetailsService extends JdbcDaoImpl {

	@Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        return getMyJdbcTemplate().query(getUsersByUsernameQuery(), new String[] { username }, new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString("username");
                String password = rs.getString("password");
                
                boolean enabled = rs.getBoolean("enabled");
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority(rs.getString("roledisplay") + rs.getString("roleadd") + rs.getString("roleupdate") + rs.getString("roledelete") + rs.getString("rolereference")));
                
                return new MyUser(username, password, enabled, true, true, true, authorities, rs);
            }

        });
    }
	
	protected JdbcTemplate getMyJdbcTemplate(){
		return getJdbcTemplate();
	}

    @Override
    protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        UserDetails user = super.createUserDetails(username, userFromUserQuery, combinedAuthorities);

        if (userFromUserQuery instanceof MyUser) {
            MyUser myUser = (MyUser) userFromUserQuery;
            return createUserDetailsFromMyuser (user, myUser);
        } else {
            return user;
        }
    }
    
    protected UserDetails createUserDetailsFromMyuser (UserDetails user, MyUser myUser){
        try {
			return new MyUser(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities(), myUser);
		} catch (Exception e) {
			e.printStackTrace();
			return user;
		}
    }
}
