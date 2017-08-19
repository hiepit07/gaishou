package vn.bananavietnam.ict.common.db.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.common.db.mapper.UtilMapper;

/**
 * @author Dung Trinh
 */
@Component
public class UtilDao {
	
	@Autowired
	private UtilMapper utilMapper;

	public UtilMapper getUtilMapper() {
		return utilMapper;
	}
}
