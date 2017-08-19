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

import vn.bananavietnam.ict.banana.component.Bnn0089SearchTaskConditions;
import vn.bananavietnam.ict.banana.component.Bnn0089SearchTaskResult;
import vn.bananavietnam.ict.banana.service.Bnn0089SearchTaskService;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.model.IvbMTask;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0089SearchTaskControllerTest {
	@InjectMocks
	Bnn0089SearchTaskController controller;

	@Mock
	Bnn0089SearchTaskService service;

	MockMvc mockMvc;
	
	@Before
    public void setUp() throws Exception {
    	controller = new Bnn0089SearchTaskController();
    	service = mock(Bnn0089SearchTaskService.class);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
	
	@Test
	public void testTop() throws Exception { 
        mockMvc.perform(get("/0089/"))
        .andExpect(status().isOk())
        .andExpect(view().name("banana/Bnn0089SearchTask"));  
    }
	
	@Test
	public void testsearchData() throws Exception { 
		String search = "{\"taskName\":\"\",\"fromRow\":0,\"itemCount\":20}";
		String expecteds = "[{\"taskId\":null,\"taskName\":null,\"workingDetails\":null,\"note\":null,\"quarantineHandlingFlag\":null,\"changePointCode\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null,\"searchDataTotalCounts\":\"1\"}]";
		List<Bnn0089SearchTaskResult> result = new ArrayList<Bnn0089SearchTaskResult>();
		Bnn0089SearchTaskResult temp = new Bnn0089SearchTaskResult();
		temp.setSearchDataTotalCounts("1");
		result.add(temp);
		when(service.searchData(any(Bnn0089SearchTaskConditions.class))).thenReturn(result);
		
        mockMvc.perform(post("/0089/searchData").contentType(MediaType.APPLICATION_JSON).content(search))
		.andExpect(MockMvcResultMatchers.status().isOk())                
	    .andExpect(MockMvcResultMatchers.content().string(expecteds)); 
    }
	
	@Test
	public void testgetSingleData() throws Exception { 
		String search = "{\"taskId\":\"T001\"}";
		String expecteds = "{\"taskId\":\"hihi\",\"taskName\":\"hihi\",\"workingDetails\":null,\"note\":null,\"quarantineHandlingFlag\":null,\"changePointCode\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":false}";
		
		IvbMTask result = new IvbMTask();
		result.setDeleteFlag(false);
		result.setTaskId("hihi");
		result.setTaskName("hihi");
		when(service.getSingleData(any(Bnn0089SearchTaskConditions.class))).thenReturn(result);
		
        mockMvc.perform(post("/0089/getSingleData").contentType(MediaType.APPLICATION_JSON).content(search))
		.andExpect(MockMvcResultMatchers.status().isOk())                
	    .andExpect(MockMvcResultMatchers.content().string(expecteds));
    }
	
	@Test
	public void testupdateData() throws Exception { 
		String search = "{\"taskId\":\"T005\",\"taskName\":\"Task 005\",\"workingDetails\":\"1\",\"deleteFlag\":false,\"quarantineHandlingFlag\":true,\"changePointCode\":\"4\",\"note\":\"1\"}";
	        
		when(service.updateData(any(IvbMTask.class))).thenReturn(Constants.UPDATE_RESULT_SUCCESSFUL);
		mockMvc.perform(post("/0089/updateData").contentType(MediaType.APPLICATION_JSON).content(search))
		.andExpect(MockMvcResultMatchers.status().isOk())                
	    .andExpect(MockMvcResultMatchers.content().string(Constants.UPDATE_RESULT_SUCCESSFUL)); 
    }
	
	@Test
	public void testinsertData() throws Exception { 
		String search = "{\"taskId\":\"\",\"taskName\":\"hehehe\",\"workingDetails\":\"oke\",\"deleteFlag\":false,\"quarantineHandlingFlag\":false,\"changePointCode\":\"0\",\"note\":\"\"}";
		
		when(service.insertData(any(IvbMTask.class))).thenReturn(Constants.INSERT_RESULT_SUCCESSFUL);
        mockMvc.perform(post("/0089/insertData").contentType(MediaType.APPLICATION_JSON).content(search))
		.andExpect(MockMvcResultMatchers.status().isOk())                
	    .andExpect(MockMvcResultMatchers.content().string(Constants.INSERT_RESULT_SUCCESSFUL)); 
    }
	
	@Test
	public void testdeleteData() throws Exception {
		String search = "{\"taskId\":\"\",\"taskName\":\"hehehe\",\"workingDetails\":\"oke\",\"deleteFlag\":false,\"quarantineHandlingFlag\":false,\"changePointCode\":\"0\",\"note\":\"\"}";
		
		when(service.deleteData(any(IvbMTask.class))).thenReturn(Constants.DELETE_RESULT_SUCCESSFUL);
        mockMvc.perform(post("/0089/deleteData").contentType(MediaType.APPLICATION_JSON).content(search))
		.andExpect(MockMvcResultMatchers.status().isOk())                
	    .andExpect(MockMvcResultMatchers.content().string(Constants.DELETE_RESULT_SUCCESSFUL)); 
    }
}
