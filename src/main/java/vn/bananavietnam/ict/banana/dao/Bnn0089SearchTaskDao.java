package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0089SearchTaskMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMProcessMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMTaskMapper;

/**
 * 
 * @author NghiaNguyen
 *
 */
@Component
public class Bnn0089SearchTaskDao {

	@Autowired
	private Bnn0089SearchTaskMapper bnn0089SearchTaskMapper;

	@Autowired
	private IvbMProcessMapper ivbMProcessMapper;

	@Autowired
	private IvbMTaskMapper ivbMTaskMapper;

	public IvbMTaskMapper getIvbMTaskMapper() {
		return ivbMTaskMapper;
	}

	public Bnn0089SearchTaskMapper getBnn0089SearchTaskMapper() {
		return bnn0089SearchTaskMapper;
	}

	public IvbMProcessMapper getIvbMProcessMapper() {
		return ivbMProcessMapper;
	}
}
