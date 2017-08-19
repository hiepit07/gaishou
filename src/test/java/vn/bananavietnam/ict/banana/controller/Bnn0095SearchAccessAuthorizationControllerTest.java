package vn.bananavietnam.ict.banana.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Date;
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

import vn.bananavietnam.ict.banana.component.Bnn0095SearchAccessAuthorizationConditions;
import vn.bananavietnam.ict.banana.component.Bnn0095SearchAccessAuthorizationResult;
import vn.bananavietnam.ict.banana.service.Bnn0095SearchAccessAuthorizationService;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.model.IvbMAccessAuthorization;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0095SearchAccessAuthorizationControllerTest {
	@InjectMocks
	Bnn0095SearchAccessAuthorizationController controller;

	@Mock
	Bnn0095SearchAccessAuthorizationService service;

	MockMvc mockMvc;
	
	@Before
    public void setUp() throws Exception {
    	controller = new Bnn0095SearchAccessAuthorizationController();
    	service = mock(Bnn0095SearchAccessAuthorizationService.class);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
	
	@Test
    public void testTopOK() throws Exception { 
        mockMvc.perform(get("/0095/"))
        .andExpect(status().isOk())
        .andExpect(view().name("banana/Bnn0095SearchAccessAuthorizarion"));
	}
	
	@Test
    public void testsearchData() throws Exception {
		List<Bnn0095SearchAccessAuthorizationResult> result = new ArrayList<Bnn0095SearchAccessAuthorizationResult>();
		Bnn0095SearchAccessAuthorizationResult temp = new Bnn0095SearchAccessAuthorizationResult();
		temp.setSearchDataTotalCounts("1");
		result.add(temp);
		
		when(service.searchData(any(Bnn0095SearchAccessAuthorizationConditions.class))).thenReturn(result);		
		String search = "{\"accessAuthorityId\":\"\",\"screenId\":\"\",\"screenDisplayEnableFlag\":true,\"addableFlag\":true,\"updatableFlag\":true,\"deletableFlag\":true,\"referenceFlag\":true,\"fromRow\":0,\"itemCount\":20}";
		String expecteds = "[{\"accessAuthorityId\":null,\"screenId\":null,\"screenDisplayEnableFlag\":null,\"addableFlag\":null,\"updatableFlag\":null,\"deletableFlag\":null,\"referenceFlag\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null,\"searchDataTotalCounts\":\"1\"}]";
		mockMvc.perform(post("/0095/searchData").contentType(MediaType.APPLICATION_JSON).content(search))
        .andExpect(MockMvcResultMatchers.status().isOk())                
        .andExpect(MockMvcResultMatchers.content().string(expecteds));
	}
	
	@Test
    public void testgetSingleData() throws Exception { 
		List<Bnn0095SearchAccessAuthorizationResult> result = new ArrayList<Bnn0095SearchAccessAuthorizationResult>();
		Bnn0095SearchAccessAuthorizationResult temp = new Bnn0095SearchAccessAuthorizationResult();
		temp.setSearchDataTotalCounts("1");
		result.add(temp);
		when(service.getSingleData(any(Bnn0095SearchAccessAuthorizationConditions.class))).thenReturn(result);	
		String search = "{\"accessAuthorityId\":\"\",\"screenId\":\"\",\"screenDisplayEnableFlag\":true,\"addableFlag\":true,\"updatableFlag\":true,\"deletableFlag\":true,\"referenceFlag\":true,\"fromRow\":0,\"itemCount\":20}";
		String expecteds = "[{\"accessAuthorityId\":null,\"screenId\":null,\"screenDisplayEnableFlag\":null,\"addableFlag\":null,\"updatableFlag\":null,\"deletableFlag\":null,\"referenceFlag\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null,\"searchDataTotalCounts\":\"1\"}]";
		mockMvc.perform(post("/0095/getSingleData").contentType(MediaType.APPLICATION_JSON).content(search))
        .andExpect(MockMvcResultMatchers.status().isOk())                
        .andExpect(MockMvcResultMatchers.content().string(expecteds));
	}
	
	@Test
    public void testupdateData() throws Exception {
		when(service.updateData(any(IvbMAccessAuthorization.class))).thenReturn(Constants.UPDATE_RESULT_SUCCESSFUL);
		String search = "{\"accessAuthorityId\":\"1\",\"screenId\":\"0005\",\"screenDisplayEnableFlag\":true,\"addableFlag\":true,\"updatableFlag\":true,\"deletableFlag\":true,\"referenceFlag\":true,\"deleteFlag\":false}";
		
		mockMvc.perform(post("/0095/updateData").contentType(MediaType.APPLICATION_JSON).content(search))
        .andExpect(MockMvcResultMatchers.status().isOk())                
        .andExpect(MockMvcResultMatchers.content().string(Constants.UPDATE_RESULT_SUCCESSFUL));
	}
	
	@Test
    public void testinsertData() throws Exception { 
		when(service.insertData(any(IvbMAccessAuthorization.class))).thenReturn(Constants.INSERT_RESULT_SUCCESSFUL);
		String search = "{\"accessAuthorityId\":\"1\",\"screenId\":\"0005\",\"screenDisplayEnableFlag\":true,\"addableFlag\":true,\"updatableFlag\":true,\"deletableFlag\":true,\"referenceFlag\":true,\"deleteFlag\":false}";
		
		mockMvc.perform(post("/0095/insertData").contentType(MediaType.APPLICATION_JSON).content(search))
        .andExpect(MockMvcResultMatchers.status().isOk())                
        .andExpect(MockMvcResultMatchers.content().string(Constants.INSERT_RESULT_SUCCESSFUL));
	}
	
	@Test
    public void testdeleteData() throws Exception { 
		when(service.deleteData(any(String.class), any(String.class), any(Date.class))).thenReturn(Constants.DELETE_RESULT_SUCCESSFUL);
		
		mockMvc.perform(post("/0095/deleteData").param("accessAuthorityId", "1").param("screenId", "0095").param("lastUpdateDate", "1494995125000"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(Constants.DELETE_RESULT_SUCCESSFUL));
	}
}
