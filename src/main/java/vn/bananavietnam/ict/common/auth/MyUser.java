package vn.bananavietnam.ict.common.auth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Store User's information
 */
public class MyUser extends User {

	private static final long serialVersionUID = 1L;
	private final String ID;
	private final String USERNAME;
	private final String USERFULLNAME;
	private final String ROLEID;
	private final ArrayList<String> SCREENID;
	private final List<GrantedAuthority> AUTHORITIES;

	public MyUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, ResultSet rs) throws SQLException {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		ID = rs.getString("id");
		USERNAME = rs.getString("username");
		USERFULLNAME = rs.getString("userfullname");
		String role_id_tmp = rs.getString("roleid");
		SCREENID = new ArrayList<String>();
		SCREENID.add(rs.getString("screenid"));
		AUTHORITIES = new ArrayList<GrantedAuthority>();
		AUTHORITIES.add(new SimpleGrantedAuthority(rs.getString("roledisplay") + rs.getString("roleadd") + rs.getString("roleupdate") + rs.getString("roledelete") + rs.getString("rolereference")));
		while (rs.next()) {
			String screenId = rs.getString("screenid");
			GrantedAuthority auth= new SimpleGrantedAuthority(rs.getString("roledisplay") + rs.getString("roleadd") + rs.getString("roleupdate") + rs.getString("roledelete") + rs.getString("rolereference"));
			if (!SCREENID.contains(screenId)) {
				SCREENID.add(screenId);
				AUTHORITIES.add(auth);
			}
			//get smallest role
			if (role_id_tmp.compareTo(rs.getString("roleid")) > 0){
				role_id_tmp = rs.getString("roleid");
			}
		}
		ROLEID = role_id_tmp;
	}

	public MyUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, MyUser myUser) throws SQLException {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		ID = myUser.getID();
		USERNAME = myUser.getUsername();
		USERFULLNAME = myUser.getUSERFULLNAME();
		SCREENID = myUser.getSCREENID();
		ROLEID =  myUser.getROLEID();
		AUTHORITIES =  myUser.getAUTHORITIES();
	}

	@SuppressWarnings("unchecked")
	public MyUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String id_, String username_, String userfullname_, ArrayList<String> screenid_) throws SQLException {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		ID = id_;
		USERNAME = username_;
		USERFULLNAME = userfullname_;
		SCREENID = screenid_;
		ROLEID = "1";
		AUTHORITIES = (List<GrantedAuthority>) authorities;
	}

	public String getID() {
		return ID;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public String getUSERFULLNAME() {
		return USERFULLNAME;
	}

	public ArrayList<String> getSCREENID() {
		return SCREENID;
	}

	public String getROLEID() {
		return ROLEID;
	}

	public List<GrantedAuthority> getAUTHORITIES() {
		return AUTHORITIES;
	}
}
