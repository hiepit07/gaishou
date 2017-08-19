package vn.bananavietnam.ict.banana.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import vn.bananavietnam.ict.banana.component.Bnn0023SearchUserPopupDataObject;
import vn.bananavietnam.ict.banana.dao.Bnn0023SearchUserPopupDao;
import vn.bananavietnam.ict.banana.db.mapper.Bnn0023SearchUserPopupMapper;
import vn.bananavietnam.ict.common.auth.MyUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
@Transactional
public class Bnn0023SearchUserPopupServiceTest {

	@Autowired
	private ApplicationContext appContext;

	@InjectMocks
	private Bnn0023SearchUserPopupService service;

	@Mock
	Bnn0023SearchUserPopupDao dao;

	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0023");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
	}

    @Test
    public void testSearchData() {

		Bnn0023SearchUserPopupDataObject searchConditions = new Bnn0023SearchUserPopupDataObject();
        // user id
        searchConditions.setUserId("A0001");
        // name id
        searchConditions.setUserName("A0001");
        // from
        searchConditions.setFromRow(0);
        // item per page
        searchConditions.setItemCount(5);

        List<Bnn0023SearchUserPopupDataObject> bnn0023ResultListReturn = new ArrayList<Bnn0023SearchUserPopupDataObject>();
        bnn0023ResultListReturn.add(new Bnn0023SearchUserPopupDataObject());
        
        service.setAppContext(appContext);
        
        // Mock setup
        Bnn0023SearchUserPopupMapper tmp = Mockito.mock(Bnn0023SearchUserPopupMapper.class);
        when(dao.getBnn0023SearchUserPopupServiceMapper()).thenReturn(tmp);
        when(tmp.searchData(any(Bnn0023SearchUserPopupDataObject.class))).thenReturn(bnn0023ResultListReturn);
        when(tmp.getSearchDataTotalCounts(any(Bnn0023SearchUserPopupDataObject.class))).thenReturn("1");

        // get search result
        List<Bnn0023SearchUserPopupDataObject> bnn0003ResultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(1, bnn0003ResultList.size());
    }

    @Test
    public void testSearchDataWithUserId() {

        Bnn0023SearchUserPopupDataObject searchConditions = new Bnn0023SearchUserPopupDataObject();
        // user id
        searchConditions.setUserId("A0001");
        // name id
        searchConditions.setUserName("");
        // from
        searchConditions.setFromRow(0);
        // item per page
        searchConditions.setItemCount(5);

        List<Bnn0023SearchUserPopupDataObject> bnn0023ResultListReturn = new ArrayList<Bnn0023SearchUserPopupDataObject>();
        bnn0023ResultListReturn.add(new Bnn0023SearchUserPopupDataObject());

        service.setAppContext(appContext);

        // Mock setup
        Bnn0023SearchUserPopupMapper tmp = Mockito.mock(Bnn0023SearchUserPopupMapper.class);
        when(dao.getBnn0023SearchUserPopupServiceMapper()).thenReturn(tmp);
        when(tmp.searchData(any(Bnn0023SearchUserPopupDataObject.class))).thenReturn(bnn0023ResultListReturn);
        when(tmp.getSearchDataTotalCounts(any(Bnn0023SearchUserPopupDataObject.class))).thenReturn("1");

        // get search result
        List<Bnn0023SearchUserPopupDataObject> bnn0003ResultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(1, bnn0003ResultList.size());
    }

    @Test
    public void testSearchDataWithUserName() {

        Bnn0023SearchUserPopupDataObject searchConditions = new Bnn0023SearchUserPopupDataObject();
        // user id
        searchConditions.setUserId("");
        // name id
        searchConditions.setUserName("A0001");
        // from
        searchConditions.setFromRow(0);
        // item per page
        searchConditions.setItemCount(5);

        List<Bnn0023SearchUserPopupDataObject> bnn0023ResultListReturn = new ArrayList<Bnn0023SearchUserPopupDataObject>();
        bnn0023ResultListReturn.add(new Bnn0023SearchUserPopupDataObject());
        
        service.setAppContext(appContext);

        // Mock setup
        Bnn0023SearchUserPopupMapper tmp = Mockito.mock(Bnn0023SearchUserPopupMapper.class);
        when(dao.getBnn0023SearchUserPopupServiceMapper()).thenReturn(tmp);
        when(tmp.searchData(any(Bnn0023SearchUserPopupDataObject.class))).thenReturn(bnn0023ResultListReturn);
        when(tmp.getSearchDataTotalCounts(any(Bnn0023SearchUserPopupDataObject.class))).thenReturn("1");

        // get search result
        List<Bnn0023SearchUserPopupDataObject> bnn0003ResultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(1, bnn0003ResultList.size());
    }

    @Test
    public void testSearchDataNoResult() {

        Bnn0023SearchUserPopupDataObject searchConditions = new Bnn0023SearchUserPopupDataObject();
        // user id
        searchConditions.setUserId("A0001");
        // name id
        searchConditions.setUserName("A0001");
        // from
        searchConditions.setFromRow(0);
        // item per page
        searchConditions.setItemCount(5);

        List<Bnn0023SearchUserPopupDataObject> bnn0023ResultListReturn = new ArrayList<Bnn0023SearchUserPopupDataObject>();

        service.setAppContext(appContext);

        // Mock setup
        Bnn0023SearchUserPopupMapper tmp = Mockito.mock(Bnn0023SearchUserPopupMapper.class);
        when(dao.getBnn0023SearchUserPopupServiceMapper()).thenReturn(tmp);
        when(tmp.searchData(any(Bnn0023SearchUserPopupDataObject.class))).thenReturn(bnn0023ResultListReturn);
        when(tmp.getSearchDataTotalCounts(any(Bnn0023SearchUserPopupDataObject.class))).thenReturn("0");

        // get search result
        List<Bnn0023SearchUserPopupDataObject> bnn0003ResultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(0, bnn0003ResultList.size());
    }

    @Test
    public void testSearchDataOutOfMemoryException() {

        Bnn0023SearchUserPopupDataObject searchConditions = new Bnn0023SearchUserPopupDataObject();
        // user id
        searchConditions.setUserId("A0001");
        // name id
        searchConditions.setUserName("A0001");
        // from
        searchConditions.setFromRow(0);
        // item per page
        searchConditions.setItemCount(5);

        service.setAppContext(appContext);

        // Mock setup
        Bnn0023SearchUserPopupMapper tmp = Mockito.mock(Bnn0023SearchUserPopupMapper.class);
        when(dao.getBnn0023SearchUserPopupServiceMapper()).thenReturn(tmp);
        doThrow(new OutOfMemoryError()).when(tmp).searchData(any(Bnn0023SearchUserPopupDataObject.class));

        // get search result
        List<Bnn0023SearchUserPopupDataObject> bnn0003ResultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals("-1", bnn0003ResultList.get(0).getSearchDataTotalCounts());
	}

    @Test
    public void testSearchDataExceptionIn() {

        Bnn0023SearchUserPopupDataObject searchConditions = null;

        service.setAppContext(appContext);

        // Mock setup
        Bnn0023SearchUserPopupMapper tmp = Mockito.mock(Bnn0023SearchUserPopupMapper.class);
        when(dao.getBnn0023SearchUserPopupServiceMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(Bnn0023SearchUserPopupDataObject.class));
        
        // get search result
        List<Bnn0023SearchUserPopupDataObject> bnn0003ResultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0003ResultList);
	}

    @Test
    public void testSearchDataExceptionOut() {

        Bnn0023SearchUserPopupDataObject searchConditions = new Bnn0023SearchUserPopupDataObject();
        // user id
        searchConditions.setUserId("A0001");
        // name id
        searchConditions.setUserName("A0001");
        // from
        searchConditions.setFromRow(0);
        // item per page
        searchConditions.setItemCount(5);

        // Mock setup
        Bnn0023SearchUserPopupMapper tmp = Mockito.mock(Bnn0023SearchUserPopupMapper.class);
        when(dao.getBnn0023SearchUserPopupServiceMapper()).thenReturn(tmp);
        doThrow(new RuntimeException()).when(tmp).searchData(any(Bnn0023SearchUserPopupDataObject.class));
        
        // get search result
        List<Bnn0023SearchUserPopupDataObject> bnn0003ResultList = service.searchData(searchConditions);
        // start testing
        Assert.assertEquals(null, bnn0003ResultList);
	}
}