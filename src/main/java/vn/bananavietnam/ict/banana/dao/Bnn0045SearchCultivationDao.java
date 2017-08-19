package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0045SearchCultivationMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMCultivationMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMProcessMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMTaskMapper;



/**
 * 
 * @author Hung Bui
 *
 */
@Component
public class Bnn0045SearchCultivationDao {
	
	@Autowired
	private Bnn0045SearchCultivationMapper bnn0045SearchCultivationMapper;
	
	@Autowired
	private IvbMCultivationMapper ivbMCultivationMapper;
	
	@Autowired
	private IvbMFarmMapper ivbMFarmMapper;
	
	@Autowired
	private IvbMProcessMapper ivbMProcessMapper;
	
	@Autowired
	private IvbMKindMapper ivbMKindMapper;
	
	@Autowired
	private IvbMTaskMapper ivbMTaskMapper;

	public Bnn0045SearchCultivationMapper getBnn0045SearchCultivationMapper() {
		return bnn0045SearchCultivationMapper;
	}

	public IvbMCultivationMapper getIvbMCultivationMapper() {
		return ivbMCultivationMapper;
	}

	public IvbMFarmMapper getIvbMFarmMapper() {
		return ivbMFarmMapper;
	}

	public IvbMProcessMapper getIvbMProcessMapper() {
		return ivbMProcessMapper;
	}

	public IvbMKindMapper getIvbMKindMapper() {
		return ivbMKindMapper;
	}

	public IvbMTaskMapper getIvbMTaskMapper() {
		return ivbMTaskMapper;
	}
	
	
}