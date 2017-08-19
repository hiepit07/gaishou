package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0049AreaListMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;

@Component
public class Bnn0049AreaListDao {

	@Autowired
	private IvbMAreaMapper ivbMAreaMapper;
	
	@Autowired
	private Bnn0049AreaListMapper bnn0049AreaListMapper;

	public IvbMAreaMapper getIvbMAreaMapper() {
		return ivbMAreaMapper;
	}

	public Bnn0049AreaListMapper getBnn0049AreaListMapper() {
		return bnn0049AreaListMapper;
	}
}
