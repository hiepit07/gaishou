package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0091CultivationMasterMapper;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0092ProcessMasterMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMCultivationMapper;

/**
 * @author Hieu Dao
 */

@Component
public class Bnn0092ProcessMasterDao {

	@Autowired
	private IvbMCultivationMapper ivbMCultivationMapper;

	@Autowired
	private Bnn0092ProcessMasterMapper bnn0092ProcessMasterMapper;
	
	@Autowired
	private Bnn0091CultivationMasterMapper bnn0091CultivationMasterMapper;

	public IvbMCultivationMapper getIvbMCultivationMapper() {
		return ivbMCultivationMapper;
	}

	public Bnn0092ProcessMasterMapper getBnn0092ProcessMasterMapper() {
		return bnn0092ProcessMasterMapper;
	}

	public Bnn0091CultivationMasterMapper getBnn0091CultivationMasterMapper() {
		return bnn0091CultivationMasterMapper;
	}
}