package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;

/**
 * 
 * @author NghiaNguyen
 *
 */
@Component
public class Bnn0047FarmListDao {

	@Autowired
	private IvbMFarmMapper ivbMFarmMapper;

	public IvbMFarmMapper getIvbMFarmMapper() {
		return ivbMFarmMapper;
	}

	public void setIvbMFarmMapper(IvbMFarmMapper ivbMFarmMapper) {
		this.ivbMFarmMapper = ivbMFarmMapper;
	}
	
	
}
