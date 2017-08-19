package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0002ChangePasswordMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMUsersMapper;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0002ChangePasswordDaoTest {
	@Autowired
    private Bnn0002ChangePasswordDao bnn0002ChangePasswordDao;
     
	@Test
    public void testIvbMUsersMapper() {
		IvbMUsersMapper tmp = bnn0002ChangePasswordDao.getIvbMUsersMapper();
		Assert.assertNotNull(tmp);
    }
	
	@Test
    public void testBnn0002ChangePasswordMapper() {
		Bnn0002ChangePasswordMapper tmp = bnn0002ChangePasswordDao.getBnn0002ChangePasswordMapper();
		Assert.assertNotNull(tmp);
    }
}
