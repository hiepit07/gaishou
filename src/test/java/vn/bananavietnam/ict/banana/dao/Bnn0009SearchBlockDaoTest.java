package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0009SearchBlockMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMBlockMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTProductMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0009SearchBlockDaoTest {

	@Autowired
    private Bnn0009SearchBlockDao bnn0009SearchBlockDao;

	@Test
    public void testGetBnn0009SearchBlockMapper() {
		Bnn0009SearchBlockMapper tmp = bnn0009SearchBlockDao.getBnn0009SearchBlockMapper();
		Assert.assertNotNull(tmp);
    }

	@Test
    public void testGetIvbMFarmMapper() {
		IvbMFarmMapper tmp = bnn0009SearchBlockDao.getIvbMFarmMapper();
		Assert.assertNotNull(tmp);
    }

	@Test
    public void testGetIvbMAreaMapper() {
		IvbMAreaMapper tmp = bnn0009SearchBlockDao.getIvbMAreaMapper();
		Assert.assertNotNull(tmp);
    }

	@Test
    public void testGetIvbMBlockMapper() {
		IvbMBlockMapper tmp = bnn0009SearchBlockDao.getIvbMBlockMapper();
		Assert.assertNotNull(tmp);
    }

	@Test
    public void testGetIvbTProductMapper() {
		IvbTProductMapper tmp = bnn0009SearchBlockDao.getIvbTProductMapper();
		Assert.assertNotNull(tmp);
    }
}
