package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0023SearchUserPopupMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0023SearchUserPopupDaoTest {
	@Autowired 
	Bnn0023SearchUserPopupDao dao;

	@Test
    public void testGetBnn0023SearchUserPopupServiceMapper() {
		Bnn0023SearchUserPopupMapper tmp = dao.getBnn0023SearchUserPopupServiceMapper();
		Assert.assertNotNull(tmp);
	}
}
