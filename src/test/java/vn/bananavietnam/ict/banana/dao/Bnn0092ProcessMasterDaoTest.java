package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0091CultivationMasterMapper;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0092ProcessMasterMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMCultivationMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0092ProcessMasterDaoTest {
	@Autowired Bnn0092ProcessMasterDao dao;

	@Test
    public void testGetBnn0092ProcessMasterMapper(){
		Bnn0092ProcessMasterMapper tmp = dao.getBnn0092ProcessMasterMapper();
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