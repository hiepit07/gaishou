package vn.bananavietnam.ict.banana.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0075SearchShippSreenMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTShippingControlMapper;;

/**
 * 
 * @author Dung Trinh
 *
 */
@Component
public class Bnn0075shippingScreenDao {
	@Autowired
	private IvbTShippingControlMapper ivbTShippingControlMapper;
	
	@Autowired
	private Bnn0075SearchShippSreenMapper bnn0075SearchShippSreenMapper;

	public IvbTShippingControlMapper getIvbTShippingControlMapper() {
		return ivbTShippingControlMapper;
	}

	public Bnn0075SearchShippSreenMapper getBnn0075SearchShippSreenMapper() {
		return bnn0075SearchShippSreenMapper;
	}

}
