package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0007SearchAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMManagerMapper;

/**
 * 
 * @author NghiaNguyen
 *
 */
@Component
public class Bnn0007SearchAreaDao {

	@Autowired
	private Bnn0007SearchAreaMapper bnn0007SearchAreaMapper;

	@Autowired
	private IvbMAreaMapper ivbMAreaMapper;

	@Autowired
	private IvbMFarmMapper ivbMFarmMapper;

	@Autowired
	private IvbMKindMapper IvbMKindMapper;

	public Bnn0007SearchAreaMapper getBnn0007SearchAreaMapper() {
		return bnn0007SearchAreaMapper;
	}

	public IvbMAreaMapper getIvbMAreaMapper() {
		return ivbMAreaMapper;
	}

	public IvbMFarmMapper getIvbMFarmMapper() {
		return ivbMFarmMapper;
	}

	public IvbMKindMapper getIvbMKindMapper() {
		return IvbMKindMapper;
	}
}
