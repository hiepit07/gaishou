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
import java.util.Date;
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

import vn.bananavietnam.ict.banana.component.Bnn0007SearchAreaConditions;
import vn.bananavietnam.ict.banana.component.Bnn0007SearchAreaFormResult;
import vn.bananavietnam.ict.banana.component.Bnn0007SearchAreaResult;
import vn.bananavietnam.ict.banana.service.Bnn0007SearchAreaService;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.model.IvbMKind;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0007SearchAreaControllerTest {

	@InjectMocks
	Bnn0007SearchAreaController controller;

	@Mock
	Bnn0007SearchAreaService service;

	MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
    	controller = new Bnn0007SearchAreaController();
    	service = mock(Bnn0007SearchAreaService.class);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    
    @Test
    public void testTopGet() throws Exception {
    	mockMvc.perform(get("/0007/").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("Bnn0005FarmData","F001"))
	    	.andExpect(status().isOk())
	        .andExpect(view().name("banana/Bnn0007SearchArea"));
    }

    @Test
    public void testTopPost() throws Exception {
    	mockMvc.perform(post("/0007/").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("Bnn0005FarmData","F001"))
	    	.andExpect(status().isOk())
	        .andExpect(view().name("banana/Bnn0007SearchArea"));
    }
    @Test
    public void testSearchData() throws Exception {
    	List<Bnn0007SearchAreaResult> bnn0007ResultList = new ArrayList<Bnn0007SearchAreaResult>();
    	Bnn0007SearchAreaResult bnn0007Result = new Bnn0007SearchAreaResult();
    	bnn0007Result.setFarmId("F001");
    	bnn0007ResultList.add(0, bnn0007Result);
 
    	String input = "{\"farmId\":\"F001\",\"areaId\":\"\",\"fromRow\":0,\"itemCount\":20}";
    	when(service.searchData(any(Bnn0007SearchAreaConditions.class))).thenReturn(bnn0007ResultList);
    	mockMvc.perform(post("/0007/searchData").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void testGetSingleData() throws Exception {
    	List<Bnn0007SearchAreaFormResult> bnn0007ResultList = new ArrayList<Bnn0007SearchAreaFormResult>();
    	Bnn0007SearchAreaFormResult bnn0007Result = new Bnn0007SearchAreaFormResult();
    	bnn0007Result.setFarmId("F001");
    	bnn0007ResultList.add(0, bnn0007Result);
 
    	String input = "{\"farmId\":\"F001\",\"areaId\":\"\",\"fromRow\":0,\"itemCount\":20}";
    	when(service.getSingleData(any(Bnn0007SearchAreaConditions.class))).thenReturn(bnn0007ResultList);
    	mockMvc.perform(post("/0007/getSingleData").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAreaData() throws Exception {
    	List<UtilComponent> listActuals = new ArrayList<UtilComponent>();
    	UtilComponent actuals = new UtilComponent();
    	actuals.setAreaId("A001");
    	actuals.setAreaName("A001");
    	listActuals.add(actuals);
    	String processData = "[{\"areaId\":\"A001\",\"farmId\":null,\"farmName\":null,\"areaName\":\"A001\"}]";
    	
    	when(service.getAreaDataByFarmId(anyString())).thenReturn(listActuals);
    	mockMvc.perform(post("/0007/getAreaDataByFarmId").param("farmId", "F074"))
	        .andExpect(MockMvcResultMatchers.status().isOk())                
	        .andExpect(MockMvcResultMatchers.content().string(processData));
    }
   
	@Test
    public void testInsertData() throws Exception {
		String areaObj = "{\"areaId\":\"\",\"areaName\":\"A045\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
		String managerObj = "{\"farmId\":\"F027\",\"areaId\":\"\",\"usersId\":\"A0001\"}";
    	String output = Constants.INSERT_RESULT_SUCCESSFUL;

    	when(service.insertData(any(String.class),any(String.class))).thenReturn(Constants.INSERT_RESULT_SUCCESSFUL);
    	mockMvc.perform(post("/0007/insertData").param("areaData", areaObj).param("managerData", managerObj)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }

	@Test
    public void testUpdateData() throws Exception {
		String areaObj = "{\"areaId\":\"\",\"areaName\":\"A045\",\"farmId\":\"F027\",\"areaManager\":\"A0001\",\"kindId\":\"K067\",\"sugarContent\":\"1\",\"texture\":\"1\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false,\"note\":\"1\",\"numberOfBlock\":\"2\"}";
    	String output = Constants.UPDATE_RESULT_SUCCESSFUL;

    	when(service.updateData(any(String.class),any(Date.class))).thenReturn(Constants.UPDATE_RESULT_SUCCESSFUL);
    	mockMvc.perform(post("/0007/updateData").param("areaData", areaObj).param("lastUpdateDate", "1494995125000")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }

    @Test
    public void testDeleteData() throws Exception {
    	String farmId = "F001";
    	String areaId = "A001";
    	String output = Constants.DELETE_RESULT_SUCCESSFUL;

    	when(service.deleteData(any(String.class),any(String.class),any(String.class),any(Date.class))).thenReturn(Constants.DELETE_RESULT_SUCCESSFUL);
    	mockMvc.perform(post("/0007/deleteData").param("farmId", farmId).param("areaId", areaId).param("usersId", "U0000000001").param("lastUpdateDate", "1494995125000")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }
    
    @Test
    public void testGetSingleDataKind() throws Exception {
    	String kindId = "K001";
    	String output = "{\"kindId\":\"K001\",\"kindName\":\"Koo1\",\"prospectiveHarvestAmount\":null,\"estimatedDaysFlowering\":null,\"estimatedDaysBagging\":null,\"estimatedDaysHarvest\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null}";
    	IvbMKind kind = new IvbMKind();
    	kind.setKindId("K001");
    	kind.setKindName("Koo1");
    	when(service.getSingleDataKind(any(String.class))).thenReturn(kind);
    	mockMvc.perform(post("/0007/getSingleDataKind").param("kindId", kindId)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }
}