package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0061TraceabilityMasterMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAreaMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMBlockMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMProcessMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMStatusMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMTaskMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbTProductMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0061TraceabilityMasterDaoTest {

	@Autowired
	Bnn0061TraceabilityMasterDao dao;

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
    public void testGetIvbMBlockMapper(){
		IvbMBlockMapper tmp = dao.getIvbMBlockMapper();
		Assert.assertNotNull(tmp);
	}

	@Test
    public void testGetIvbTProductMapper(){
		IvbTProductMapper tmp = dao.getIvbTProductMapper();
		Assert.assertNotNull(tmp);
	}

	@Test
    public void testGetIvbMProcessMapper(){
		IvbMProcessMapper tmp = dao.getIvbMProcessMapper();
		Assert.assertNotNull(tmp);
	}

	@Test
    public void testGetIvbMTaskMapper(){
		IvbMTaskMapper tmp = dao.getIvbMTaskMapper();
		Assert.assertNotNull(tmp);
	}

	@Test
    public void testGetIvbMStatusMapper(){
		IvbMStatusMapper tmp = dao.getIvbMStatusMapper();
		Assert.assertNotNull(tmp);
	}

	@Test
    public void testGetBnn0061TraceabilityMasterMapper(){
		Bnn0061TraceabilityMasterMapper tmp = dao.getBnn0061TraceabilityMasterMapper();
		Assert.assertNotNull(tmp);
	}
}