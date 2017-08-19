package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0045SearchCultivationMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMCultivationMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMProcessMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMTaskMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0045SearchCultivationDaoTest {
	@Autowired
    private Bnn0045SearchCultivationDao bnn0045SearchCultivationDao;
     
	@Test
    public void testGetBnn0045SearchCultivationMapper() {	
		Bnn0045SearchCultivationMapper tmp = bnn0045SearchCultivationDao.getBnn0045SearchCultivationMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testGetIvbMCultivationMapper() {
		IvbMCultivationMapper tmp = bnn0045SearchCultivationDao.getIvbMCultivationMapper();
		Assert.assertNotNull(tmp);
    }
	@Test
    public void testGetIvbMFarmMapper() {	
		IvbMFarmMapper tmp = bnn0045SearchCultivationDao.getIvbMFarmMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testGetIvbMProcessMapper() {
		IvbMProcessMapper tmp = bnn0045SearchCultivationDao.getIvbMProcessMapper();
		Assert.assertNotNull(tmp);
    }
	@Test
    public void testGetIvbMKindMapper() {	
		IvbMKindMapper tmp = bnn0045SearchCultivationDao.getIvbMKindMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testGetIvbMTaskMapper() {
		IvbMTaskMapper tmp = bnn0045SearchCultivationDao.getIvbMTaskMapper();
		Assert.assertNotNull(tmp);
    }
}