package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0003SearchUserMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMUsersMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0003SearchUserDaoTest {
	@Autowired
    private Bnn0003SearchUserDao bnn0003SearchUserDao;
     
	@Test
    public void testGetBnn0003SearchUserMapper() {
		Bnn0003SearchUserMapper tmp = bnn0003SearchUserDao.getBnn0003SearchUserMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testGetIvbMUsersMapper() {
		IvbMUsersMapper tmp = bnn0003SearchUserDao.getIvbMUsersMapper();
		Assert.assertNotNull(tmp);
    }
}
