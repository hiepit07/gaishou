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

import vn.bananavietnam.ict.banana.component.Bnn0091CultivationMasterDataObject;
import vn.bananavietnam.ict.banana.service.Bnn0091CultivationMasterService;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.model.IvbMCultivation;
import vn.bananavietnam.ict.common.db.model.IvbMTask;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0091CultivationMasterControllerTest {

	@InjectMocks
	Bnn0091CultivationMasterController controller;

	@Mock
	Bnn0091CultivationMasterService service;

	MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
    	controller = new Bnn0091CultivationMasterController();
    	service = mock(Bnn0091CultivationMasterService.class);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testTop() throws Exception {
    	mockMvc.perform(get("/0091/")).andExpect(status().isOk())
    		.andExpect(view().name("banana/Bnn0091CultivationMaster"));
    }

    @Test
    public void testGetSingleData() throws Exception {
    	IvbMTask bnn0091Object = new IvbMTask();
    	bnn0091Object.setTaskId("T001");

    	String input = "T001";

    	String output = "{\"taskId\":\"T001\",\"taskName\":null,\"workingDetails\":null,\"note\":null,\"quarantineHandlingFlag\":null,\"changePointCode\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null}";

    	when(service.getSingleData(any(String.class))).thenReturn(bnn0091Object);
    	mockMvc.perform(post("/0091/getSingleData").param("taskId", input)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }

    @SuppressWarnings("rawtypes")
	@Test
    public void testgetTaskData() throws Exception {  	
    	List<List> taskList = new ArrayList<List>();
		List<IvbMTask> unreList = new ArrayList<IvbMTask>();
		List<Bnn0091CultivationMasterDataObject> regiList = new ArrayList<Bnn0091CultivationMasterDataObject>();
		
		IvbMTask task = new IvbMTask();
		task.setTaskId("iiiii");
		task.setTaskName("iiiii");
		unreList.add(task);
		
		Bnn0091CultivationMasterDataObject data = new Bnn0091CultivationMasterDataObject();
		data.setFarmId("sas");
		data.setFarmName("sas");
		regiList.add(data);
		
		taskList.add(unreList);
		taskList.add(regiList);
    		
    	when(service.getTaskData(any(IvbMCultivation.class))).thenReturn(taskList);
    	String gettask = "{\"farmId\":\"F001\",\"kindId\":\"K001\",\"processId\":\"P001\"}";
    	String expecteds = "[[{\"taskId\":\"iiiii\",\"taskName\":\"iiiii\",\"workingDetails\":null,\"note\":null,\"quarantineHandlingFlag\":null,\"changePointCode\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null}],[{\"farmId\":\"sas\",\"kindId\":null,\"processId\":null,\"taskId\":null,\"processOrder\":null,\"taskOrder\":null,\"blockWorkFlag\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null,\"farmName\":\"sas\",\"kindName\":null,\"taskName\":null,\"blockFlag\":null}]]";

    	mockMvc.perform(post("/0091/getTaskData").contentType(MediaType.APPLICATION_JSON).content(gettask))
    			.andExpect(MockMvcResultMatchers.status().isOk())                
    	        .andExpect(MockMvcResultMatchers.content().string(expecteds));
    }
    
    @Test
    public void testgetProcessDataTotal() throws Exception {
    	when(service.getUnregisteredProcessDataTotal(any(IvbMCultivation.class))).thenReturn("1");
    	String gettask = "{\"farmId\":\"F001\",\"kindId\":\"K017\"}";
    	
    	mockMvc.perform(post("/0091/getProcessDataTotal").contentType(MediaType.APPLICATION_JSON).content(gettask))
    			.andExpect(MockMvcResultMatchers.status().isOk())                
    	        .andExpect(MockMvcResultMatchers.content().string("1"));
    }    
    
    @Test
    public void testinsertData() throws Exception {	
    		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Class<List<Bnn0091CultivationMasterDataObject>> klass = (Class<List<Bnn0091CultivationMasterDataObject>>) (Class) List.class;  
		
    	when(service.insertData(any(klass))).thenReturn(Constants.INSERT_RESULT_SUCCESSFUL);
    	String gettask = "[{\"farmId\":\"F001\",\"kindId\":\"K017\",\"processId\":\"P001\"},{\"farmId\":\"F001\",\"kindId\":\"K017\",\"processId\":\"P001\",\"taskId\":\"T034\",\"blockWorkFlag\":true}]";
    	
    	mockMvc.perform(post("/0091/insertData").contentType(MediaType.APPLICATION_JSON).content(gettask))
    			.andExpect(MockMvcResultMatchers.status().isOk())                
    	        .andExpect(MockMvcResultMatchers.content().string(Constants.INSERT_RESULT_SUCCESSFUL));
    }
}