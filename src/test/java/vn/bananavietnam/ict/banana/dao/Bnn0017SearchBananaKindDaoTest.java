package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0017SearchBananaKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0017SearchBananaKindDaoTest {
    @Autowired
    Bnn0017SearchBananaKindDao dao;

    @Test
    public void testBnn0017SearchBananaKindMapper() {
        Bnn0017SearchBananaKindMapper tmp = dao.getBnn0017SearchBananaKindMapper();
        Assert.assertNotNull(tmp);
    }

    @Test
    public void testIvbMKindMapper() {
        IvbMKindMapper tmp = dao.getIvbMKindMapper();
        Assert.assertNotNull(tmp);
    }
}
