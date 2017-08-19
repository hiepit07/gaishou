package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0087SearchAffiliationMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMAuthorizationMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMManagerMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0087SearchAffiliationDaoTest {
    @Autowired
    Bnn0087SearchAffiliationDao dao;

    @Test
    public void testGetBnn0087SearchAffiliationMapper() {
        Bnn0087SearchAffiliationMapper tmp = dao.getBnn0087SearchAffiliationMapper();
        Assert.assertNotNull(tmp);
    }

    @Test
    public void testGetIvbMAuthorizationMapper() {
        IvbMAuthorizationMapper tmp = dao.getIvbMAuthorizationMapper();
        Assert.assertNotNull(tmp);
    }

    @Test
    public void testGetIvbMManagerMapper() {
        IvbMManagerMapper tmp = dao.getIvbMManagerMapper();
        Assert.assertNotNull(tmp);
    }

}
