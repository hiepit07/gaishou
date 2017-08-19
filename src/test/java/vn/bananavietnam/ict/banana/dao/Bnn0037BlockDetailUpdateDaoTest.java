package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0037BlockDetailUpdateMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMBlockMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMStatusMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMTaskMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTCultivationResultMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTProductMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0037BlockDetailUpdateDaoTest {
	@Autowired
    private Bnn0037BlockDetailUpdateDao bnn0037BlockDetailUpdateDao;
     
	@Test
    public void testGetIvbMFarmMapper() {	
		IvbMFarmMapper tmp = bnn0037BlockDetailUpdateDao.getIvbMFarmMapper();
		Assert.assertNotNull(tmp);
    }
	@Test
    public void testGetIvbMAreaMapper() {	
		IvbMAreaMapper tmp = bnn0037BlockDetailUpdateDao.getIvbMAreaMapper();
		Assert.assertNotNull(tmp);
    }
	@Test
    public void testGetIvbMBlockMapper() {	
		IvbMBlockMapper tmp = bnn0037BlockDetailUpdateDao.getIvbMBlockMapper();
		Assert.assertNotNull(tmp);
    }
	@Test
    public void testGetIvbTProductMapper() {	
		IvbTProductMapper tmp = bnn0037BlockDetailUpdateDao.getIvbTProductMapper();
		Assert.assertNotNull(tmp);
    }
	@Test
    public void testGetIvbMKindMapper() {	
		IvbMKindMapper tmp = bnn0037BlockDetailUpdateDao.getIvbMKindMapper();
		Assert.assertNotNull(tmp);
    }
	@Test
    public void testGetBnn0037BlockDetailUpdateMapper() {	
		Bnn0037BlockDetailUpdateMapper tmp = bnn0037BlockDetailUpdateDao.getBnn0037BlockDetailUpdateMapper();
		Assert.assertNotNull(tmp);
    }
	@Test
    public void testGetIvbMTaskMapper() {	
		IvbMTaskMapper tmp = bnn0037BlockDetailUpdateDao.getIvbMTaskMapper();
		Assert.assertNotNull(tmp);
    }
	@Test
    public void testGetIvbMStatusMapper() {	
		IvbMStatusMapper tmp = bnn0037BlockDetailUpdateDao.getIvbMStatusMapper();
		Assert.assertNotNull(tmp);
    }
	@Test
    public void testGetIvbTCultivationResultMapper() {	
		IvbTCultivationResultMapper tmp = bnn0037BlockDetailUpdateDao.getIvbTCultivationResultMapper();
		Assert.assertNotNull(tmp);
    }
	
	
}