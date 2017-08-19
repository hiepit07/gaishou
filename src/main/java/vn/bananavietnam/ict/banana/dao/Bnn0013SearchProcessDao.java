package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0013SearchProcessMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMProcessMapper;

/**
 * 
 * @author NghiaNguyen
 *
 */
@Component
public class Bnn0013SearchProcessDao {
	@Autowired
	private Bnn0013SearchProcessMapper bnn0013CultivationProcessMapper;

	@Autowired
	private IvbMKindMapper IvbMKindMapper;

	@Autowired
	private IvbMProcessMapper ivbMProcessMapper;

	public Bnn0013SearchProcessMapper getBnn0013CultivationProcessMapper() {
		return bnn0013CultivationProcessMapper;
	}

	public IvbMKindMapper getIvbMKindMapper() {
		return IvbMKindMapper;
	}

	public IvbMProcessMapper getIvbMProcessMapper() {
		return ivbMProcessMapper;
	}
}
