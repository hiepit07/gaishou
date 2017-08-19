package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0075SearchShippSreenMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTProductMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTShippingControlMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTShippingNumberMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0075ShippingScreenDaoTest {
	@Autowired
    private Bnn0075shippingScreenDao bnn0075shippingScreenDao;
     
	@Test
    public void testGetBnn0075SearchShippSreenMapper() {	
		Bnn0075SearchShippSreenMapper tmp = bnn0075shippingScreenDao.getBnn0075SearchShippSreenMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testIvbMAreaMapper() {	
		IvbMAreaMapper tmp = bnn0075shippingScreenDao.getIvbMAreaMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testIvbMFarmMapper() {	
		IvbMFarmMapper tmp = bnn0075shippingScreenDao.getIvbMFarmMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testIvbTShippingControlMapper() {	
		IvbTShippingControlMapper tmp = bnn0075shippingScreenDao.getIvbTShippingControlMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testIvbTProductMapper() {	
		IvbTProductMapper tmp = bnn0075shippingScreenDao.getIvbTProductMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testIvbTShippingNumberMapper() {	
		IvbTShippingNumberMapper tmp = bnn0075shippingScreenDao.getIvbTShippingNumberMapper();
		Assert.assertNotNull(tmp);
    }
}