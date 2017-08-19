package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0047FarmListDaoTest {
	
	@Autowired
    private Bnn0047FarmListDao bnn0047FarmListDao;
	
	@Test
    public void testGetIvbMFarmMapper() {
		IvbMFarmMapper tmp = bnn0047FarmListDao.getIvbMFarmMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testSetIvbMFarmMapper() {
    	IvbMFarmMapper tmp = bnn0047FarmListDao.getIvbMFarmMapper();
		bnn0047FarmListDao.setIvbMFarmMapper(tmp);
		Assert.assertNotNull(tmp);
    }
}
