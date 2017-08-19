package vn.bananavietnam.ict.common.db.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.common.db.mapper.UtilMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class UtilDaoTest {
	@Autowired
    private UtilDao utilDao;
     
	@Test
    public void testGetUtilMapper() {	
		UtilMapper tmp = utilDao.getUtilMapper();
		Assert.assertNotNull(tmp);
    }
}