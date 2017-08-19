package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0035BlockUpdateMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMBlockMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMStatusMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMTaskMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTCultivationResultMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTProductMapper;

/**
 * @author Khoa Le
 */
@Component
public class Bnn0035BlockUpdateDao {

	@Autowired
	private IvbMFarmMapper ivbMFarmMapper;

	@Autowired
	private IvbMAreaMapper ivbMAreaMapper;

	@Autowired
	private IvbMBlockMapper ivbMBlockMapper;

	@Autowired
	private IvbTProductMapper ivbTProductMapper;

	@Autowired
	private IvbMKindMapper ivbMKindMapper;

	@Autowired
	private Bnn0035BlockUpdateMapper bnn0035BlockUpdateMapper;

	@Autowired
	private IvbMTaskMapper ivbMTaskMapper;

	@Autowired
	private IvbMStatusMapper ivbMStatusMapper;

	@Autowired
	private IvbTCultivationResultMapper ivbTCultivationResultMapper;

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
		return ivbTProductMapper;
	}

	public IvbMKindMapper getIvbMKindMapper() {
		return ivbMKindMapper;
	}

	public Bnn0035BlockUpdateMapper getBnn0035BlockUpdateMapper() {
		return bnn0035BlockUpdateMapper;
	}

	public IvbMTaskMapper getIvbMTaskMapper() {
		return ivbMTaskMapper;
	}

	public IvbMStatusMapper getIvbMStatusMapper() {
		return ivbMStatusMapper;
	}

	public IvbTCultivationResultMapper getIvbTCultivationResultMapper() {
		return ivbTCultivationResultMapper;
	}
}