package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0091CultivationMasterMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMCultivationMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMFarmMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMProcessMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMTaskMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0091CultivationMasterDaoTest {
	@Autowired Bnn0091CultivationMasterDao dao;

	@Test
    public void testGetIvbMFarmMapper(){
		IvbMFarmMapper tmp = dao.getIvbMFarmMapper();
		Assert.assertNotNull(tmp);
	}

	@Test
    public void testGetIvbMKindMapper(){
		IvbMKindMapper tmp = dao.getIvbMKindMapper();
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
    public void testGetIvbMCultivationMapper(){
		IvbMCultivationMapper tmp = dao.getIvbMCultivationMapper();
		Assert.assertNotNull(tmp);
	}
	
	@Test
    public void testGetBnn0091CultivationMasterMapper(){
		Bnn0091CultivationMasterMapper tmp = dao.getBnn0091CultivationMasterMapper();
		Assert.assertNotNull(tmp);
	}
}