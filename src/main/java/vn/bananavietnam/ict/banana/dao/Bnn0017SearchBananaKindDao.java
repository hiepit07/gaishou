package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0017SearchBananaKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;

/**
 * 
 * @author NghiaNguyen
 *
 */
@Component
public class Bnn0017SearchBananaKindDao {

	@Autowired
	private Bnn0017SearchBananaKindMapper bnn0017SearchBananaKindMapper;

	@Autowired
	private IvbMKindMapper IvbMKindMapper;

	public Bnn0017SearchBananaKindMapper getBnn0017SearchBananaKindMapper() {
		return bnn0017SearchBananaKindMapper;
	}

	public IvbMKindMapper getIvbMKindMapper() {
		return IvbMKindMapper;
	}
}
