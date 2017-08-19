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
import org.springframework.ui.Model;

import vn.bananavietnam.ict.banana.component.Bnn0037ProductExtendObject;
import vn.bananavietnam.ict.banana.component.BnnBlockCultivationResult;
import vn.bananavietnam.ict.banana.service.Bnn0037BlockDetailUpdateService;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.model.IvbMTask;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0037BlockDetailUpdateControllerTest {

	@InjectMocks
	Bnn0037BlockDetailUpdateController controller;

	@Mock
	Bnn0037BlockDetailUpdateService service;

	MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
    	controller = new Bnn0037BlockDetailUpdateController();
    	service = mock(Bnn0037BlockDetailUpdateService.class);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    
    @Test
    public void testTopOK() throws Exception { 
        when(service.initData(any(Model.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn(Constants.INIT_RESULT_SUCCESSFUL);
        mockMvc.perform(get("/0037/")
        				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
        				.param("farmId","F001")
        	            .param("farmName","F1")
        	            .param("areaId","A001")
        	            .param("areaName","A1")
        	            .param("blockId","B001")
        	            .param("blockName","B1"))
        .andExpect(status().isOk())
        .andExpect(view().name("banana/Bnn0037BlockDetailUpdate"));
	}
    
    @Test
	public void testTopError() throws Exception { 
        when(service.initData(any(Model.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn(Constants.INIT_RESULT_FAILED);
        mockMvc.perform(get("/0037/")
        				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
        				.param("farmId","")
        	            .param("farmName","F1")
        	            .param("areaId","A001")
        	            .param("areaName","A1")
        	            .param("blockId","B001")
        	            .param("blockName","B1"))
        .andExpect(status().isOk())
        .andExpect(view().name("common/error"));  
    }

    @Test
    public void testTopPostOK() throws Exception { 
        when(service.initData(any(Model.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn(Constants.INIT_RESULT_SUCCESSFUL);
        mockMvc.perform(post("/0037/")
        				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
        				.param("farmId","F001")
        	            .param("farmName","F1")
        	            .param("areaId","A001")
        	            .param("areaName","A1")
        	            .param("blockId","B001")
        	            .param("blockName","B1"))
        .andExpect(status().isOk())
        .andExpect(view().name("banana/Bnn0037BlockDetailUpdate"));
	}
    
    @Test
	public void testTopPostError() throws Exception { 
        when(service.initData(any(Model.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class), any(String.class))).thenReturn(Constants.INIT_RESULT_FAILED);
        mockMvc.perform(post("/0037/")
        				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
        				.param("farmId","")
        	            .param("farmName","F1")
        	            .param("areaId","A001")
        	            .param("areaName","A1")
        	            .param("blockId","B001")
        	            .param("blockName","B1"))
        .andExpect(status().isOk())
        .andExpect(view().name("common/error"));  
    }

    @Test
    public void testGetProductData() throws Exception {
    	List<Bnn0037ProductExtendObject> listActuals = new ArrayList<Bnn0037ProductExtendObject>();
    	Bnn0037ProductExtendObject actuals = new Bnn0037ProductExtendObject();
    	actuals.setFarmId("F002");
    	actuals.setAreaId("A002");
    	actuals.setBlockId("B002");
    	listActuals.add(0, actuals);
    	String processData = "[{\"farmId\":\"F002\",\"areaId\":\"A002\",\"blockId\":\"B002\",\"lineId\":null,\"columnId\":null,\"previousRound\":null,\"cultivationStartDate\":null,\"plantingDate\":null,\"floweringDate\":null,\"bagClosingDate\":null,\"harvestDate\":null,\"shippingDate\":null,\"inforId\":null,\"inforDisplayStartDate\":null,\"inforDisplayEndDate\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null,\"cultivationStartDateString\":null,\"plantingDateString\":null,\"floweringDateString\":null,\"bagClosingDateString\":null,\"harvestDateString\":null}]";
    	
    	when(service.getProductData(anyString(),anyString(),anyString())).thenReturn(listActuals);
    	mockMvc.perform(post("/0037/getProductData").param("farmId", "F002").param("areaId", "A002").param("blockId", "B002"))
	        .andExpect(MockMvcResultMatchers.status().isOk())                
	        .andExpect(MockMvcResultMatchers.content().string(processData));
    }
    
    @Test
    public void testGetCultivationResultData() throws Exception {
    	List<BnnBlockCultivationResult> listActuals = new ArrayList<BnnBlockCultivationResult>();
    	BnnBlockCultivationResult actuals = new BnnBlockCultivationResult();
    	actuals.setFarmId("F002");
    	actuals.setAreaId("A002");
    	actuals.setBlockId("B002");
    	listActuals.add(0, actuals);
    	String processData = "[{\"farmId\":\"F002\",\"areaId\":\"A002\",\"blockId\":\"B002\",\"lineId\":null,\"columnId\":null,\"previousRound\":null,\"workingDate\":null,\"processId\":null,\"taskId\":null,\"blockWorkFlag\":null,\"statusId\":null,\"note\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null,\"blockName\":null,\"workingDateString\":null,\"processName\":null,\"processOrder\":null,\"taskName\":null,\"taskOrder\":null,\"changePointCode\":null,\"workingContent\":null,\"precaution\":null,\"statusName\":null,\"plantingDate\":null,\"floweringDate\":null,\"bagClosingDate\":null,\"harvestDate\":null,\"shippingDate\":null,\"lastUpdateDateCultivation\":null,\"lastUpdateDateProduct\":null,\"currentProductIdString\":null,\"currentBlockIdString\":null,\"currentLastUpdateDateString\":null}]";
    	
    	when(service.getCultivationResultData(anyString(),anyString(),anyString(),anyString())).thenReturn(listActuals);
    	mockMvc.perform(post("/0037/getCultivationResultData").param("farmId", "F002").param("areaId", "A002").param("blockId", "B002").param("kindId", "K002"))
	        .andExpect(MockMvcResultMatchers.status().isOk())                
	        .andExpect(MockMvcResultMatchers.content().string(processData));
    }

    @Test
    public void testGetTaskData() throws Exception {
    	List<IvbMTask> listActuals = new ArrayList<IvbMTask>();
    	IvbMTask actuals = new IvbMTask();
    	listActuals.add(0, actuals);
    	String processData = "[{\"taskId\":null,\"taskName\":null,\"workingDetails\":null,\"note\":null,\"quarantineHandlingFlag\":null,\"changePointCode\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null}]";
    	
    	when(service.getTaskData(anyString(),anyString(),anyString())).thenReturn(listActuals);
    	mockMvc.perform(post("/0037/getTaskData").param("farmId", "F002").param("kindId", "K002").param("processId", "P002"))
	        .andExpect(MockMvcResultMatchers.status().isOk())                
	        .andExpect(MockMvcResultMatchers.content().string(processData));
    }

    @Test
    public void testInsertData() throws Exception {
    	String actuals = Constants.INSERT_RESULT_SUCCESSFUL;

    	BnnBlockCultivationResult bnnBlockCultivationResult = new BnnBlockCultivationResult();
    	// Farm id
    	bnnBlockCultivationResult.setFarmId("F001");
		// Area id
    	bnnBlockCultivationResult.setAreaId("A001");
    	// Block id
    	bnnBlockCultivationResult.setBlockId("B001");
    	// Line id
    	bnnBlockCultivationResult.setLineId("L001");
    	// Column id
    	bnnBlockCultivationResult.setColumnId("C001");
    	// Previous round
    	bnnBlockCultivationResult.setPreviousRound(Constants.CURRENT_ROUND);
    	
    	String expecteds = Constants.INSERT_RESULT_SUCCESSFUL;
    	when(service.insertData(any(BnnBlockCultivationResult.class))).thenReturn(actuals);
    	String insertData = "{\"farmId\":\"F001\",\"farmName\":null,\"areaId\":\"A001\",\"areaName\":null,\"shippingNumber\":null,\"areaManager\":null,\"harvestDate\":null,\"shipDate\":null}";

    	mockMvc.perform(post("/0037/insertData").contentType(MediaType.APPLICATION_JSON).content(insertData))
    			.andExpect(MockMvcResultMatchers.status().isOk())                
    	        .andExpect(MockMvcResultMatchers.content().string(expecteds));
    }
    
    @Test
    public void testDeleteData() throws Exception {
    	String actuals = Constants.INSERT_RESULT_SUCCESSFUL;

    	BnnBlockCultivationResult bnnBlockCultivationResult = new BnnBlockCultivationResult();
    	// Farm id
    	bnnBlockCultivationResult.setFarmId("F001");
		// Area id
    	bnnBlockCultivationResult.setAreaId("A001");
    	// Block id
    	bnnBlockCultivationResult.setBlockId("B001");
    	// Line id
    	bnnBlockCultivationResult.setLineId("L001");
    	// Column id
    	bnnBlockCultivationResult.setColumnId("C001");
    	// Previous round
    	bnnBlockCultivationResult.setPreviousRound(Constants.CURRENT_ROUND);
    	
    	String expecteds = Constants.INSERT_RESULT_SUCCESSFUL;
    	when(service.deleteData(any(BnnBlockCultivationResult.class))).thenReturn(actuals);
    	String deleteData = "{\"farmId\":\"F001\",\"farmName\":null,\"areaId\":\"A001\",\"areaName\":null,\"shippingNumber\":null,\"areaManager\":null,\"harvestDate\":null,\"shipDate\":null}";

    	mockMvc.perform(post("/0037/deleteData").contentType(MediaType.APPLICATION_JSON).content(deleteData))
    			.andExpect(MockMvcResultMatchers.status().isOk())                
    	        .andExpect(MockMvcResultMatchers.content().string(expecteds));
    }
}