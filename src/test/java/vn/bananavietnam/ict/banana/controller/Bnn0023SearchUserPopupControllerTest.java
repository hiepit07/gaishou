package vn.bananavietnam.ict.banana.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import vn.bananavietnam.ict.banana.component.Bnn0023SearchUserPopupDataObject;
import vn.bananavietnam.ict.banana.service.Bnn0023SearchUserPopupService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0023SearchUserPopupControllerTest {
	@InjectMocks
	Bnn0023SearchUserPopupController controller;

	@Mock
	Bnn0023SearchUserPopupService service;

	MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
    	controller = new Bnn0023SearchUserPopupController();
    	service = mock(Bnn0023SearchUserPopupService.class);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testTop() throws Exception {
    	mockMvc.perform(get("/0023/")).andExpect(status().isOk()).andExpect(view().name("banana/Bnn0023SearchUserPopup"));
    }
    
    @Test
    public void testSearchData() throws Exception {
    	List<Bnn0023SearchUserPopupDataObject> bnn0023ResultList = new ArrayList<Bnn0023SearchUserPopupDataObject>();
    	Bnn0023SearchUserPopupDataObject bnn0023Result = new Bnn0023SearchUserPopupDataObject();
    	bnn0023Result.setUsersId("A0001");
    	bnn0023ResultList.add(0, bnn0023Result);

    	String input = "{\"userId\":\"A0001\"}";

    	String output = "[{\"usersId\":\"A0001\",\"farmId\":null,\"areaId\":null,\"authorizationTypeId\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null,\"userId\":null,\"userName\":null,\"fromRow\":0,\"itemCount\":0,\"searchDataTotalCounts\":null}]";

    	when(service.searchData(any(Bnn0023SearchUserPopupDataObject.class))).thenReturn(bnn0023ResultList);
    	mockMvc.perform(post("/0023/searchData").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));;
    }
}