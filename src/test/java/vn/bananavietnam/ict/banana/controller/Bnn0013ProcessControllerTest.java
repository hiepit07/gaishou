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

import vn.bananavietnam.ict.banana.component.Bnn0013SearchProcessConditions;
import vn.bananavietnam.ict.banana.component.Bnn0013SearchProcessResult;
import vn.bananavietnam.ict.banana.service.Bnn0013SearchProcessService;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0013ProcessControllerTest {
	@InjectMocks
	Bnn0013ProcessController controller;

	@Mock
	Bnn0013SearchProcessService service;

	MockMvc mockMvc;
	
	@Before
    public void setUp() throws Exception {
    	controller = new Bnn0013ProcessController();
    	service = mock(Bnn0013SearchProcessService.class);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
	
	@Test
	public void testTop() throws Exception { 
        mockMvc.perform(get("/0013/"))
        .andExpect(status().isOk())
        .andExpect(view().name("banana/Bnn0013CultivationProcess"));  
    }
	
	@Test
	public void testsearchData() throws Exception { 
		String search = "{\"processName\":\"\",\"fromRow\":0,\"itemCount\":20}";
		String expecteds = "[{\"processId\":null,\"processName\":null,\"lastUpdateDate\":null,\"searchDataTotalCounts\":\"1\"}]";
		List<Bnn0013SearchProcessResult> result = new ArrayList<Bnn0013SearchProcessResult>();
		Bnn0013SearchProcessResult temp = new Bnn0013SearchProcessResult();
		temp.setSearchDataTotalCounts("1");
		result.add(temp);
		when(service.searchData(any(Bnn0013SearchProcessConditions.class))).thenReturn(result);
		
        mockMvc.perform(post("/0013/searchData").contentType(MediaType.APPLICATION_JSON).content(search))
		.andExpect(MockMvcResultMatchers.status().isOk())                
	    .andExpect(MockMvcResultMatchers.content().string(expecteds)); 
    }
	
	@Test
	public void testgetSingleData() throws Exception { 
		String search = "{\"processId\":\"P001\"}";
		String expecteds = "{\"processId\":\"hihi\",\"processName\":\"hihi\",\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":false}";
		
		IvbMProcess result = new IvbMProcess();
		result.setDeleteFlag(false);
		result.setProcessId("hihi");
		result.setProcessName("hihi");
		when(service.getSingleData(any(Bnn0013SearchProcessConditions.class))).thenReturn(result);
		
        mockMvc.perform(post("/0013/getSingleData").contentType(MediaType.APPLICATION_JSON).content(search))
		.andExpect(MockMvcResultMatchers.status().isOk())                
	    .andExpect(MockMvcResultMatchers.content().string(expecteds));
    }
	
	@Test
	public void testupdateData() throws Exception { 
		String search = "{\"processId\":\"P001\",\"processName\":\"Process 001\",\"deleteFlag\":false}";
	        
		when(service.updateData(any(IvbMProcess.class))).thenReturn(Constants.UPDATE_RESULT_SUCCESSFUL);
		mockMvc.perform(post("/0013/updateData").contentType(MediaType.APPLICATION_JSON).content(search))
		.andExpect(MockMvcResultMatchers.status().isOk())                
	    .andExpect(MockMvcResultMatchers.content().string(Constants.UPDATE_RESULT_SUCCESSFUL)); 
    }
	
	@Test
	public void testinsertData() throws Exception { 
		String search = "{\"processId\":\"\",\"processName\":\"A a a d\",\"deleteFlag\":false}";
		
		when(service.insertData(any(IvbMProcess.class))).thenReturn(Constants.INSERT_RESULT_SUCCESSFUL);
        mockMvc.perform(post("/0013/insertData").contentType(MediaType.APPLICATION_JSON).content(search))
		.andExpect(MockMvcResultMatchers.status().isOk())                
	    .andExpect(MockMvcResultMatchers.content().string(Constants.INSERT_RESULT_SUCCESSFUL)); 
    }
	
	@Test
	public void testdeleteData() throws Exception { 
		String search = "{\"processId\":\"P102\"}";
		
		when(service.deleteData(any(IvbMProcess.class))).thenReturn(Constants.DELETE_RESULT_SUCCESSFUL);
        mockMvc.perform(post("/0013/deleteData").contentType(MediaType.APPLICATION_JSON).content(search))
		.andExpect(MockMvcResultMatchers.status().isOk())                
	    .andExpect(MockMvcResultMatchers.content().string(Constants.DELETE_RESULT_SUCCESSFUL)); 
    }
}
