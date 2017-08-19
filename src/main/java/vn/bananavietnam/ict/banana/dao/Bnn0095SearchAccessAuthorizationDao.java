package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0095SearchAccessAuthorizationMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAccessAuthorizationMapper;

/**
 * 	Bnn0095SearchAccessAuthorizationDao
 * 
 *	@author NghiaNguyen
 *	@version 1.0.0
 * 	@since 1.0.0
 */
@Component
public class Bnn0095SearchAccessAuthorizationDao {

	@Autowired
	private Bnn0095SearchAccessAuthorizationMapper bnn0095SearchAccessAuthorizationMapper;

	@Autowired
	private IvbMAccessAuthorizationMapper ivbMAccessAuthorizationMapper;

	public Bnn0095SearchAccessAuthorizationMapper getBnn0095SearchAccessAuthorizationMapper() {
		return bnn0095SearchAccessAuthorizationMapper;
	}

	public IvbMAccessAuthorizationMapper getIvbMAccessAuthorizationMapper() {
		return ivbMAccessAuthorizationMapper;
	}
}
