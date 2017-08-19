package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0007SearchAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMManagerMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0007SearchAreaDaoTest {

	@Autowired
    private Bnn0007SearchAreaDao bnn0007SearchAreaDao;
     
	@Test
    public void testGetBnn0007SearchAreaMapper() {
		Bnn0007SearchAreaMapper tmp = bnn0007SearchAreaDao.getBnn0007SearchAreaMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testGetIvbMFarmMapper() {
		IvbMFarmMapper tmp = bnn0007SearchAreaDao.getIvbMFarmMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testGetIvbMAreaMapper() {
		IvbMAreaMapper tmp = bnn0007SearchAreaDao.getIvbMAreaMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testGetIvbMKindMapper() {
		IvbMKindMapper tmp = bnn0007SearchAreaDao.getIvbMKindMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testGetIvbMManagerMapper() {
		IvbMManagerMapper tmp = bnn0007SearchAreaDao.getIvbMManagerMapper();
		Assert.assertNotNull(tmp);
    }
}
