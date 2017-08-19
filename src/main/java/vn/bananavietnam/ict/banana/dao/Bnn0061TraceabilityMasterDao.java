package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0061TraceabilityMasterMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMBlockMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMProcessMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMStatusMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMTaskMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTProductMapper;

/**
 * @author Hieu Dao
 */

@Component
public class Bnn0061TraceabilityMasterDao {
	@Autowired
	private IvbMFarmMapper ivbMFarmMapper;
	@Autowired
	private IvbMAreaMapper ivbMAreaMapper;
	@Autowired
	private IvbMBlockMapper ivbMBlockMapper;
	@Autowired
	private IvbTProductMapper ivbTProductMapper;
	@Autowired
	private IvbMProcessMapper ivbMProcessMapper;
	@Autowired
	private IvbMTaskMapper ivbMTaskMapper;
	@Autowired
	private IvbMStatusMapper ivbMStatusMapper;
	@Autowired
	private Bnn0061TraceabilityMasterMapper bnn0061TraceabilityMasterMapper;

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
	public IvbMProcessMapper getIvbMProcessMapper() {
		return ivbMProcessMapper;
	}
	public IvbMTaskMapper getIvbMTaskMapper() {
		return ivbMTaskMapper;
	}
	public IvbMStatusMapper getIvbMStatusMapper() {
		return ivbMStatusMapper;
	}
	public Bnn0061TraceabilityMasterMapper getBnn0061TraceabilityMasterMapper() {
		return bnn0061TraceabilityMasterMapper;
	}
}
