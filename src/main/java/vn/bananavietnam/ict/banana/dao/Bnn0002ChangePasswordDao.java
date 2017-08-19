package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0002ChangePasswordMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMUsersMapper;

/**
 * @author Khoa Le
 */
@Component
public class Bnn0002ChangePasswordDao {

	@Autowired
	private Bnn0002ChangePasswordMapper bnn0002ChangePasswordMapper;

	@Autowired
	private IvbMUsersMapper ivbMUsersMapper;

	public IvbMUsersMapper getIvbMUsersMapper() {
		return ivbMUsersMapper;
	}

	public Bnn0002ChangePasswordMapper getBnn0002ChangePasswordMapper() {
		return bnn0002ChangePasswordMapper;
	}
}