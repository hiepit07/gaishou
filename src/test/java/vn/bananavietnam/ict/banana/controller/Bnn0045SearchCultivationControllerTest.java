package vn.bananavietnam.ict.banana.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
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

import vn.bananavietnam.ict.banana.component.Bnn0045SearchCultivationConditions;
import vn.bananavietnam.ict.banana.component.Bnn0045SearchCultivationInFormCbbResult;
import vn.bananavietnam.ict.banana.component.Bnn0045SearchCultivationResult;
import vn.bananavietnam.ict.banana.service.Bnn0045SearchCultivationService;
import vn.bananavietnam.ict.common.component.UtilComponent;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0045SearchCultivationControllerTest {

	@InjectMocks
	Bnn0045SearchCultivationController bnn0045SearchCultivationController;

	@Mock
	Bnn0045SearchCultivationService bnn0045SearchCultivationService;

	MockMvc mockMvc;	
	
    @Before
    public void setUp() throws Exception {
    	bnn0045SearchCultivationService = mock(Bnn0045SearchCultivationService.class);
    	bnn0045SearchCultivationController = new Bnn0045SearchCultivationController();    	
    	
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bnn0045SearchCultivationController).build();
    }
    
    @Test
    public void testTop() throws Exception {
    	mockMvc.perform(get("/0045/")).andExpect(status().isOk())
    		.andExpect(view().name("banana/Bnn0045SearchCultivation"));
    }
    
    @Test
    public void testGetProcessData() throws Exception {
    	List<Bnn0045SearchCultivationInFormCbbResult> listActuals = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
    	Bnn0045SearchCultivationInFormCbbResult actuals = new Bnn0045SearchCultivationInFormCbbResult();
    	actuals.setFarmId("F002");
    	actuals.setKindId("K002");
    	listActuals.add(0, actuals);
    	String processData = "[{\"farmId\":\"F002\",\"farmName\":null,\"processId\":null,\"processName\":null,\"kindId\":\"K002\",\"kindName\":null,\"taskId\":null,\"taskName\":null}]";
    	
    	when(bnn0045SearchCultivationService.getProcessData()).thenReturn(listActuals);
    	mockMvc.perform(post("/0045/getProcessData").param("farmId", "F002").param("kindId", "K002"))
	        .andExpect(MockMvcResultMatchers.status().isOk())                
	        .andExpect(MockMvcResultMatchers.content().string(processData));
    }
    
    @Test
    public void testGetTaskData() throws Exception {
    	List<Bnn0045SearchCultivationInFormCbbResult> listActuals = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
    	Bnn0045SearchCultivationInFormCbbResult actuals = new Bnn0045SearchCultivationInFormCbbResult();
    	actuals.setFarmId("F002");
    	actuals.setKindId("K002");
    	listActuals.add(0, actuals);
    	String taskData = "[{\"farmId\":\"F002\",\"farmName\":null,\"processId\":null,\"processName\":null,\"kindId\":\"K002\",\"kindName\":null,\"taskId\":null,\"taskName\":null}]";
    	
    	when(bnn0045SearchCultivationService.getTaskData()).thenReturn(listActuals);
    	mockMvc.perform(post("/0045/getTaskData").param("farmId", "F002").param("kindId", "K002"))
	        .andExpect(MockMvcResultMatchers.status().isOk())                
	        .andExpect(MockMvcResultMatchers.content().string(taskData));
    }
    
    @Test
    public void testGetFarmData() throws Exception {
    	List<UtilComponent> listActuals = new ArrayList<UtilComponent>();
    	UtilComponent actuals = new UtilComponent();
    	listActuals.add(0, actuals);
    	String farmData = "[{\"areaId\":null,\"farmId\":null,\"farmName\":null,\"areaName\":null}]";
    	when(bnn0045SearchCultivationService.getFarmData()).thenReturn(listActuals);
    	mockMvc.perform(post("/0045/getFarmData").param("farmId", "F002").param("kindId", "K002"))
	        .andExpect(MockMvcResultMatchers.status().isOk())                
	        .andExpect(MockMvcResultMatchers.content().string(farmData));
    }
    
    @Test
    public void testGetKindData() throws Exception {
    	List<Bnn0045SearchCultivationInFormCbbResult> listActuals = new ArrayList<Bnn0045SearchCultivationInFormCbbResult>();
    	Bnn0045SearchCultivationInFormCbbResult actuals = new Bnn0045SearchCultivationInFormCbbResult();
    	actuals.setFarmId("F002");
    	actuals.setKindId("K002");
    	listActuals.add(0, actuals);
    	String kindData = "[{\"farmId\":\"F002\",\"farmName\":null,\"processId\":null,\"processName\":null,\"kindId\":\"K002\",\"kindName\":null,\"taskId\":null,\"taskName\":null}]";
    	
    	when(bnn0045SearchCultivationService.getKindData(anyString())).thenReturn(listActuals);
    	mockMvc.perform(post("/0045/getKindData").param("farmId", "F002").param("kindId", "K002"))
	        .andExpect(MockMvcResultMatchers.status().isOk())                
	        .andExpect(MockMvcResultMatchers.content().string(kindData));
    }
    
    @Test
    public void testSearchData() throws Exception {
    	List<Bnn0045SearchCultivationResult> listActuals = new ArrayList<Bnn0045SearchCultivationResult>();
    	Bnn0045SearchCultivationResult actuals = new Bnn0045SearchCultivationResult();
    	actuals.setFarmId("F002");
    	actuals.setKindId("K002");
    	listActuals.add(0, actuals);
    	
    	Bnn0045SearchCultivationConditions searchConditions = new Bnn0045SearchCultivationConditions();
    	searchConditions.setFarmId("F002");
    	searchConditions.setKindId("K002");
    	searchConditions.setProcessId("");
    	searchConditions.setTaskId("");
    	searchConditions.setFromRow("0");
    	searchConditions.setItemCount("5");
    	String taskData = "{\"farmId\":\"F002\",\"farmName\":null,\"processId\":null,\"processName\":null,\"kindId\":\"K002\",\"kindName\":null,\"taskId\":null,\"taskName\":null}";
    	
    	String ex = "[{\"farmId\":\"F002\",\"farmName\":null,\"processId\":null,\"processName\":null,\"kindId\":\"K002\",\"kindName\":null,\"taskId\":null,\"taskName\":null,\"quarantimehandlingflag\":null,\"workingdetails\":null,\"note\":null,\"searchDataTotalCounts\":null}]";
    	
    	when(bnn0045SearchCultivationService.searchData(any(Bnn0045SearchCultivationConditions.class))).thenReturn(listActuals);
    	mockMvc.perform(post("/0045/searchData").contentType(MediaType.APPLICATION_JSON).content(taskData))
	        .andExpect(MockMvcResultMatchers.status().isOk())                
	        .andExpect(MockMvcResultMatchers.content().string(ex));
    }
   
}
