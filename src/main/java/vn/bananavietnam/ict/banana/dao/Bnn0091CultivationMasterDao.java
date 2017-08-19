package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0091CultivationMasterMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMCultivationMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMProcessMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMTaskMapper;

/**
 * @author Hieu Dao
 */

@Component
public class Bnn0091CultivationMasterDao {

	@Autowired
	private IvbMFarmMapper ivbMFarmMapper;

	@Autowired
	private IvbMKindMapper IvbMKindMapper;

	@Autowired
	private IvbMProcessMapper ivbMProcessMapper;

	@Autowired
	private IvbMTaskMapper ivbMTaskMapper;

	@Autowired
	private IvbMCultivationMapper ivbMCultivationMapper;
	
	@Autowired
	private Bnn0091CultivationMasterMapper bnn0091CultivationMasterMapper;

	public IvbMFarmMapper getIvbMFarmMapper() {
		return ivbMFarmMapper;
	}

	public IvbMKindMapper getIvbMKindMapper() {
		return IvbMKindMapper;
	}

	public IvbMProcessMapper getIvbMProcessMapper() {
		return ivbMProcessMapper;
	}

	public IvbMTaskMapper getIvbMTaskMapper() {
		return ivbMTaskMapper;
	}

	public IvbMCultivationMapper getIvbMCultivationMapper() {
		return ivbMCultivationMapper;
	}

	public Bnn0091CultivationMasterMapper getBnn0091CultivationMasterMapper() {
		return bnn0091CultivationMasterMapper;
	}
}