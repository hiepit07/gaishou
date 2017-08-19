package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0023SearchUserPopupMapper;

/**
 * @author Hieu Dao
 */
@Component
public class Bnn0023SearchUserPopupDao {
	@Autowired
	private Bnn0023SearchUserPopupMapper bnn0023SearchUserPopupServiceMapper;

	public Bnn0023SearchUserPopupMapper getBnn0023SearchUserPopupServiceMapper() {
		return bnn0023SearchUserPopupServiceMapper;
	}
}