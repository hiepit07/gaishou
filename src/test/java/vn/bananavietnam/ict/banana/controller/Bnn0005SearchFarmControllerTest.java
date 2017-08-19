package vn.bananavietnam.ict.banana.controller;

import static org.mockito.Matchers.any;
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

import vn.bananavietnam.ict.banana.component.Bnn0005SearchFarmConditions;
import vn.bananavietnam.ict.banana.component.Bnn0005SearchFarmDataObject;
import vn.bananavietnam.ict.banana.component.Bnn0005SearchFarmResult;
import vn.bananavietnam.ict.banana.service.Bnn0005SearchFarmService;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.component.UtilComponent;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0005SearchFarmControllerTest {

	@InjectMocks
	Bnn0005SearchFarmController controller;

	@Mock
	Bnn0005SearchFarmService service;

	MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
    	controller = new Bnn0005SearchFarmController();
    	service = mock(Bnn0005SearchFarmService.class);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testTop() throws Exception {
    	mockMvc.perform(get("/0005/")).andExpect(status().isOk())
    		.andExpect(view().name("banana/Bnn0005SearchFarm"));
    }

    @Test
    public void testSearchData() throws Exception {
    	List<Bnn0005SearchFarmResult> bnn0005ResultList = new ArrayList<Bnn0005SearchFarmResult>();
    	Bnn0005SearchFarmResult bnn0005Result = new Bnn0005SearchFarmResult();
    	bnn0005Result.setFarmId("F001");
    	bnn0005ResultList.add(0, bnn0005Result);
 
    	String input = "{\"farmId\":\"F001\",\"fromRow\":0,\"itemCount\":20}";

    	String output = "[{\"farmId\":\"F001\",\"farmName\":null,\"usersId\":null,\"usersName\":null,\"lineOfPlan\":null,\"columnOfPlan\":null,\"openDate\":null,\"timeFrom\":null,\"timeTo\":null,\"address\":null,\"climate\":null,\"soil\":null,\"sizeOfPlan\":null,\"email\":null,\"phone\":null,\"fax\":null,\"note\":null,\"deleteFlag\":null,\"lastUpdateDate\":null,\"searchDataTotalCounts\":null}]";

    	when(service.searchData(any(Bnn0005SearchFarmConditions.class))).thenReturn(bnn0005ResultList);
    	mockMvc.perform(post("/0005/searchFarmData").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }

    @Test
    public void testGetSingleData() throws Exception {
    	Bnn0005SearchFarmDataObject bnn0005Object = new Bnn0005SearchFarmDataObject();
    	bnn0005Object.setFarmId("F001");

    	String input = "F001";

    	String output = "{\"farmId\":\"F001\",\"farmName\":null,\"numberOfLines\":null,\"numberOfColumns\":null,\"openDate\":null,\"timeFrom\":null,\"timeTo\":null,\"farmLocation\":null,\"sizeOfPlan\":null,\"climate\":null,\"soil\":null,\"emailAddress\":null,\"phone\":null,\"fax\":null,\"note\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null,\"usersId\":null,\"usersName\":null,\"usersIdNew\":null,\"dateOpen\":null,\"fromTime\":null,\"toTime\":null}";

    	when(service.getSingleData(any(String.class))).thenReturn(bnn0005Object);
    	mockMvc.perform(post("/0005/getSingleData").param("farmId", input)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }

    @Test
    public void testInsertData() throws Exception {
    	String input = "{\"farmName\":\"Farm001\"}";

    	String output = Constants.INSERT_RESULT_SUCCESSFUL;

    	when(service.insertData(any(Bnn0005SearchFarmDataObject.class))).thenReturn(Constants.INSERT_RESULT_SUCCESSFUL);
    	mockMvc.perform(post("/0005/insertData").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }

    @Test
    public void testUpdateData() throws Exception {
    	String input = "{\"farmId\":\"F001\"}";

    	String output = Constants.UPDATE_RESULT_SUCCESSFUL;

    	when(service.updateData(any(Bnn0005SearchFarmDataObject.class))).thenReturn(Constants.UPDATE_RESULT_SUCCESSFUL);
    	mockMvc.perform(post("/0005/updateData").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }

    @Test
    public void testDeleteData() throws Exception {
    	String input = "F001";

    	String output = Constants.DELETE_RESULT_SUCCESSFUL;

    	when(service.deleteData(any(String.class), any(Date.class))).thenReturn(Constants.DELETE_RESULT_SUCCESSFUL);
    	mockMvc.perform(post("/0005/deleteData").param("farmId", input).param("lastUpdateDate", "1494995125000")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }

    @Test
    public void testGetFarmName() throws Exception {

    	List<UtilComponent> bnn0005ResultList = new ArrayList<UtilComponent>();
    	UtilComponent bnn0005Result = new UtilComponent();
    	bnn0005Result.setFarmId("F001");
    	bnn0005ResultList.add(0, bnn0005Result);

    	String output = "[{\"areaId\":null,\"farmId\":\"F001\",\"farmName\":null,\"areaName\":null}]";

    	when(service.getFarmName()).thenReturn(bnn0005ResultList);
    	mockMvc.perform(post("/0005/getFarmName")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }
    
}