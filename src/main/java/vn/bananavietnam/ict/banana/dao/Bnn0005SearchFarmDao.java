package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0005SearchFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;

/**
 * @author Hieu Dao
 */

@Component
public class Bnn0005SearchFarmDao {

	@Autowired
	private Bnn0005SearchFarmMapper bnn0005SearchFarmMapper;

	@Autowired
	private IvbMFarmMapper ivbMFarmMapper;

	public Bnn0005SearchFarmMapper getBnn0005SearchFarmMapper() {
		return bnn0005SearchFarmMapper;
	}

	public IvbMFarmMapper getIvbMFarmMapper() {
		return ivbMFarmMapper;
	}
}