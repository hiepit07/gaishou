package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0005SearchFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMManagerMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0005SearchFarmDaoTest {
	@Autowired
	Bnn0005SearchFarmDao dao;

	@Test
    public void testGetBnn0005SearchFarmMapper(){
		Bnn0005SearchFarmMapper tmp = dao.getBnn0005SearchFarmMapper();
		Assert.assertNotNull(tmp);
	}
	
	@Test
    public void testGetIvbMFarmMapper(){
		IvbMFarmMapper tmp = dao.getIvbMFarmMapper();
		Assert.assertNotNull(tmp);
	}
	
	@Test
    public void testGetIvbMAreaMapper(){
		IvbMAreaMapper tmp = dao.getIvbMAreaMapper();
		Assert.assertNotNull(tmp);
	}
	
	@Test
    public void testGetIvbMManagerMapper(){
		IvbMManagerMapper tmp = dao.getIvbMManagerMapper();
		Assert.assertNotNull(tmp);
	}
}
