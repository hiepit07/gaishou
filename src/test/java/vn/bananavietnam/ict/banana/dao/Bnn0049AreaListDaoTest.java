package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0049AreaListMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0049AreaListDaoTest {
	
	@Autowired
    private Bnn0049AreaListDao bnn0049AreaListDao;

	@Test
    public void testGetIvbMAreaMapper() {
		IvbMAreaMapper tmp = bnn0049AreaListDao.getIvbMAreaMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testGetBnn0049AreaListMapper() {
		Bnn0049AreaListMapper tmp = bnn0049AreaListDao.getBnn0049AreaListMapper();
		Assert.assertNotNull(tmp);
    }
}
