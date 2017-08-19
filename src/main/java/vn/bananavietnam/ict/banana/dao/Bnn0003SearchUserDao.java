package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0003SearchUserMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMUsersMapper;

/**
 * @author Khoa Le
 */
@Component
public class Bnn0003SearchUserDao {
	
	@Autowired
	private Bnn0003SearchUserMapper bnn0003SearchUserMapper;

	@Autowired
	private IvbMUsersMapper ivbMUsersMapper;

	public Bnn0003SearchUserMapper getBnn0003SearchUserMapper() {
		return bnn0003SearchUserMapper;
	}

	public IvbMUsersMapper getIvbMUsersMapper() {
		return ivbMUsersMapper;
	}
}
