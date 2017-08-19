package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0009SearchBlockMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMBlockMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTProductMapper;

/**
 * @author Khoa Le
 */
@Component
public class Bnn0009SearchBlockDao {

	@Autowired
	private Bnn0009SearchBlockMapper bnn0009SearchBlockMapper;

	@Autowired
	private IvbMFarmMapper ivbMFarmMapper;

	@Autowired
	private IvbMAreaMapper ivbMAreaMapper;

	@Autowired
	private IvbMBlockMapper ivbMBlockMapper;

	@Autowired
	private IvbTProductMapper IvbTProductMapper;

	public Bnn0009SearchBlockMapper getBnn0009SearchBlockMapper() {
		return bnn0009SearchBlockMapper;
	}

	public IvbMFarmMapper getIvbMFarmMapper() {
		return ivbMFarmMapper;
	}

	public IvbMAreaMapper getIvbMAreaMapper() {
		return ivbMAreaMapper;
	}

	public IvbMBlockMapper getIvbMBlockMapper() {
		return ivbMBlockMapper;
	}

	public IvbTProductMapper getIvbTProductMapper() {
		return IvbTProductMapper;
	}
}