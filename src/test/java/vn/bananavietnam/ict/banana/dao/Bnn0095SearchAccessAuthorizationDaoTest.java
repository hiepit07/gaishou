package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0095SearchAccessAuthorizationMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAccessAuthorizationMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0095SearchAccessAuthorizationDaoTest {
	@Autowired Bnn0095SearchAccessAuthorizationDao dao;

	@Test
    public void testGetBnn0095SearchAccessAuthorizationMapper(){
		Bnn0095SearchAccessAuthorizationMapper tmp = dao.getBnn0095SearchAccessAuthorizationMapper();
		Assert.assertNotNull(tmp);
	}

	@Test
    public void testGetIvbMAccessAuthorizationMapper(){
		IvbMAccessAuthorizationMapper tmp = dao.getIvbMAccessAuthorizationMapper();
		Assert.assertNotNull(tmp);
	}
}
