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

import vn.bananavietnam.ict.banana.component.Bnn0075SearchShippingScreenConditions;
import vn.bananavietnam.ict.banana.component.Bnn0075SearchShippingScreenResult;
import vn.bananavietnam.ict.banana.service.Bnn0075ShippingScreenService;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.model.IvbTShippingControl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0075ShippingScreenControllerTest {

	@InjectMocks
	Bnn0075ShippingScreenController Bnn0075ShippingScreenController;

	@Mock
	Bnn0075ShippingScreenService Bnn0075ShippingScreenService;

	MockMvc mockMvc;	
	
    @Before
    public void setUp() throws Exception {
    	Bnn0075ShippingScreenService = mock(Bnn0075ShippingScreenService.class);
    	Bnn0075ShippingScreenController = new Bnn0075ShippingScreenController();    	
    	
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(Bnn0075ShippingScreenController).build();
    }
    
    @Test
    public void testTop() throws Exception {
    	mockMvc.perform(get("/0075/")).andExpect(status().isOk())
    		.andExpect(view().name("banana/Bnn0075shippingScreen"));
    }
    
    @Test
    public void testSearchData() throws Exception {
    	List<Bnn0075SearchShippingScreenResult> listActuals = new ArrayList<Bnn0075SearchShippingScreenResult>();
    	Bnn0075SearchShippingScreenResult actuals = new Bnn0075SearchShippingScreenResult();
    	actuals.setAreaId("A001");
    	actuals.setFarmId("F001");
    	listActuals.add(0, actuals);
    	
    	Bnn0075SearchShippingScreenConditions searchConditions = new Bnn0075SearchShippingScreenConditions();
    	searchConditions.setShippingNumber("000000020");
    	searchConditions.setFarmId("F001");
    	searchConditions.setAreaId("A001");
    	searchConditions.setHarvestStartDate("");
    	searchConditions.setHarvestEndDate("");
    	searchConditions.setShipStartDate("");
    	searchConditions.setShipEndDate("");
    	searchConditions.setFromRow("0");
    	searchConditions.setItemCount("5");
    	String taskData = "{\"farmId\":\"F001\",\"farmName\":null,\"areaId\":\"A001\",\"areaName\":null,\"shippingNumber\":null,\"areaManager\":null,\"harvestDate\":null,\"shipDate\":null}";
    	
    	String ex = "[{\"farmId\":\"F001\",\"areaId\":\"A001\",\"farmName\":null,\"areaName\":null,\"shippingNumber\":null,\"areaManager\":null,\"harvestDate\":null,\"shipDate\":null,\"deleteFlag\":null,\"lastUpdateDate\":null,\"lastUpdateDateProduct\":null,\"shippingDate\":null,\"searchDataTotalCounts\":null}]";
    	when(Bnn0075ShippingScreenService.searchData(any(Bnn0075SearchShippingScreenConditions.class))).thenReturn(listActuals);
    	mockMvc.perform(post("/0075/searchData").contentType(MediaType.APPLICATION_JSON).content(taskData))
	        .andExpect(MockMvcResultMatchers.status().isOk())                
	        .andExpect(MockMvcResultMatchers.content().string(ex));
    }
    
    
    @Test
    public void testInsertData() throws Exception {
    	String actuals = Constants.INSERT_RESULT_SUCCESSFUL;

    	IvbTShippingControl IvbTShippingControl = new IvbTShippingControl();
    	Date newDate = new Date(2017-01-01);
    	// shipping number
    	IvbTShippingControl.setShippingNumber("00000099");
		// farm id
    	IvbTShippingControl.setFarmId("F001");
		// area id
    	IvbTShippingControl.setAreaId("A001");
		// harvest date
    	IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
    	IvbTShippingControl.setShippingDate(newDate);
    	//
    	IvbTShippingControl.setCreateDate(null);
    	
    	IvbTShippingControl.setCreateUserId(null);
    	IvbTShippingControl.setDeleteFlag(null);
    	IvbTShippingControl.setLastUpdateDate(null);
    	IvbTShippingControl.setUpdateUserId(null);
    	String expecteds = Constants.INSERT_RESULT_SUCCESSFUL;
    	when(Bnn0075ShippingScreenService.insertData(any(Bnn0075SearchShippingScreenResult.class))).thenReturn(actuals);
    	String taskData = "{\"farmId\":\"F001\",\"farmName\":null,\"areaId\":\"A001\",\"areaName\":null,\"shippingNumber\":null,\"areaManager\":null,\"harvestDate\":null,\"shipDate\":null}";
    	mockMvc.perform(post("/0075/insertData").contentType(MediaType.APPLICATION_JSON).content(taskData))
    			.andExpect(MockMvcResultMatchers.status().isOk())                
    	        .andExpect(MockMvcResultMatchers.content().string(expecteds));
    }
    
    @Test
    public void testUpdateData() throws Exception {
    	String actuals = Constants.UPDATE_RESULT_SUCCESSFUL;

    	IvbTShippingControl IvbTShippingControl = new IvbTShippingControl();
    	Date newDate = new Date(2017-01-01);
    	// shipping number
    	IvbTShippingControl.setShippingNumber("00000099");
		// farm id
    	IvbTShippingControl.setFarmId("F001");
		// area id
    	IvbTShippingControl.setAreaId("A001");
		// harvest date
    	IvbTShippingControl.setHarvestDate(newDate);
		// shipping date
    	IvbTShippingControl.setShippingDate(newDate);

    	String expecteds = Constants.UPDATE_RESULT_SUCCESSFUL;
    	when(Bnn0075ShippingScreenService.updateData(any(Bnn0075SearchShippingScreenResult.class))).thenReturn(actuals);
    	String taskData = "{\"farmId\":\"F001\",\"farmName\":null,\"areaId\":\"A001\",\"areaName\":null,\"shippingNumber\":null,\"areaManager\":null,\"harvestDate\":null,\"shipDate\":null}";
    	mockMvc.perform(post("/0075/updateData").contentType(MediaType.APPLICATION_JSON).content(taskData))
    			.andExpect(MockMvcResultMatchers.status().isOk())                
    	        .andExpect(MockMvcResultMatchers.content().string(expecteds));
    }
    
    @Test
    public void testCreateShipNumber() throws Exception {
    	String actuals = "";

    	String expecteds = "";
    	when(Bnn0075ShippingScreenService.updateData(any(Bnn0075SearchShippingScreenResult.class))).thenReturn(actuals);
    	String taskData = "{\"farmId\":\"F001\",\"areaId\":\"A001\"}";
    	mockMvc.perform(post("/0075/createShipNumber").contentType(MediaType.APPLICATION_JSON).content(taskData))
    			.andExpect(MockMvcResultMatchers.status().isOk())                
    	        .andExpect(MockMvcResultMatchers.content().string(expecteds));
    }
    
    @Test
    public void testGetAreaDataByFarmId() throws Exception {

    	List<UtilComponent> area = new ArrayList<UtilComponent>();
    	
    	when(Bnn0075ShippingScreenService.getAreaDataByFarmId(any(String.class))).thenReturn(area);
    	mockMvc.perform(post("/0075/getAreaDataByFarmId").param("farmId", "F001"))
                .andExpect(MockMvcResultMatchers.status().isOk())                
                .andExpect(MockMvcResultMatchers.content().string(area.toString()))
                .andReturn();
    }
    
}
