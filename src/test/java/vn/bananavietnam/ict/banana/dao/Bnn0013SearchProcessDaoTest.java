package vn.bananavietnam.ict.banana.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.db.mapper.Bnn0013SearchProcessMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMKindMapper;
import vn.bananavietnam.ict.common.db.mapper.IvbMProcessMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0013SearchProcessDaoTest {
    @Autowired
    Bnn0013SearchProcessDao dao;

    @Test
    public void testBnn0013SearchProcessMapper() {
        Bnn0013SearchProcessMapper tmp = dao.getBnn0013CultivationProcessMapper();
        Assert.assertNotNull(tmp);
    }

    @Test
    public void testIvbMKindMapper() {
        IvbMKindMapper tmp = dao.getIvbMKindMapper();
        Assert.assertNotNull(tmp);
    }

    @Test
    public void testIvbMProcessMapper() {
        IvbMProcessMapper tmp = dao.getIvbMProcessMapper();
        Assert.assertNotNull(tmp);
    }
}
