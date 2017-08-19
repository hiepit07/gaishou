package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0089SearchTaskMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMProcessMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMTaskMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0089SearchTaskDaoTest {
    @Autowired
    Bnn0089SearchTaskDao dao;

    @Test
    public void testIvbMTaskMapper() {
        IvbMTaskMapper tmp = dao.getIvbMTaskMapper();
        Assert.assertNotNull(tmp);
    }

    @Test
    public void testBnn0089SearchTaskMapper() {
        Bnn0089SearchTaskMapper tmp = dao.getBnn0089SearchTaskMapper();
        Assert.assertNotNull(tmp);
    }

    @Test
    public void testIvbMProcessMapper() {
        IvbMProcessMapper tmp = dao.getIvbMProcessMapper();
        Assert.assertNotNull(tmp);
    }
}
