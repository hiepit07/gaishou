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

import vn.bananavietnam.ict.banana.component.Bnn0087AffiliationDelete;
import vn.bananavietnam.ict.banana.component.Bnn0087IvbMManager;
import vn.bananavietnam.ict.banana.component.Bnn0087SearchAffiliationConditions;
import vn.bananavietnam.ict.banana.component.Bnn0087SearchAffiliationResult;
import vn.bananavietnam.ict.banana.component.Bnn0088SearchAreaResult;
import vn.bananavietnam.ict.banana.service.Bnn0087SearchAffiliationService;
import vn.bananavietnam.ict.common.cnst.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0087SearchAffiliationControllerTest {
    @InjectMocks
    Bnn0087SearchAffiliationController bnn0087SearchAffiliationController;

    @Mock
    Bnn0087SearchAffiliationService bnn0087SearchAffiliationService;

    MockMvc mockMvc;    
    

    
    @Before
    public void setUp() throws Exception {
        bnn0087SearchAffiliationService = mock(Bnn0087SearchAffiliationService.class);
        bnn0087SearchAffiliationController = new Bnn0087SearchAffiliationController();        
        
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bnn0087SearchAffiliationController).build();
    }
    
    @Test
    public void testTop() throws Exception {
        mockMvc.perform(get("/0087/")).andExpect(status().isOk())
            .andExpect(view().name("banana/Bnn0087SearchAffiliation"));
    }
    
    @Test
    public void testSearchData() throws Exception {
        List<Bnn0087SearchAffiliationResult> listActuals = new ArrayList<Bnn0087SearchAffiliationResult>();
        Bnn0087SearchAffiliationResult actuals = new Bnn0087SearchAffiliationResult();
        actuals.setUserName("A001");
        actuals.setTypeId("1");
        
        listActuals.add(0, actuals);
        
        Bnn0087SearchAffiliationConditions searchConditions = new Bnn0087SearchAffiliationConditions();
        searchConditions.setFromRow("0");
        searchConditions.setItemCount("5");
        String taskData = "{\"farmId\":\"F001\",\"farmName\":null,\"areaId\":\"A001\",\"areaName\":null,\"shippingNumber\":null,\"areaManager\":null,\"harvestDate\":null,\"shipDate\":null}";
        when(bnn0087SearchAffiliationService.searchData(any(Bnn0087SearchAffiliationConditions.class))).thenReturn(listActuals);
        String exper = "[{\"userName\":\"A001\",\"typeName\":null,\"typeId\":\"1\",\"userId\":null,\"farmIdList\":null,\"areaIdList\":null,\"lastUpdateDate\":null,\"searchDataTotalCounts\":null}]";
        mockMvc.perform(post("/0087/searchData").contentType(MediaType.APPLICATION_JSON).content(taskData))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(exper));
    }
    
    @Test
    public void testDeleteData() throws Exception {
        String actuals = Constants.DELETE_RESULT_SUCCESSFUL;
        String taskData = "{\"farmId\":\"F002\",\"farmName\":null,\"processId\":null,\"processName\":null,\"kindId\":\"K002\",\"kindName\":null,\"taskId\":null,\"taskName\":null}";
        when(bnn0087SearchAffiliationService.deleteData(any(Bnn0087AffiliationDelete.class))).thenReturn(actuals);
        mockMvc.perform(post("/0087/deleteData").contentType(MediaType.APPLICATION_JSON).content(taskData))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Constants.DELETE_RESULT_SUCCESSFUL));
        
    }
    
    @Test
    public void testGetDataForAffiliation() throws Exception {
        List<Bnn0088SearchAreaResult> listReturn = new ArrayList<Bnn0088SearchAreaResult>();
        String taskData = "{\"farmId\":\"F002\",\"farmName\":null,\"processId\":null,\"processName\":null,\"kindId\":\"K002\",\"kindName\":null,\"taskId\":null,\"taskName\":null}";
        String exper = "[]";
        when(bnn0087SearchAffiliationService.getDataForAffiliation()).thenReturn(listReturn);
        mockMvc.perform(post("/0087/getDataForAffiliation").contentType(MediaType.APPLICATION_JSON).content(taskData))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(exper));
        
    }
    
    @Test
    public void testInsertDataForAffiliation() throws Exception {
    	ArrayList<Bnn0087IvbMManager> mockArrayList = new ArrayList<Bnn0087IvbMManager>();
        Bnn0087IvbMManager mockMg = new Bnn0087IvbMManager();
        mockMg.setUsersId("A0007");
        mockMg.setAuthorizationTypeId("2");
        mockMg.setTypeIdEdit(null);
        mockMg.setFarmId("9999");
        mockMg.setAreaId("9999");
        mockMg.setDeleteFlag(false);
        mockArrayList.add(mockMg);
        String actuals = Constants.INSERT_RESULT_SUCCESSFUL;
        String taskData = "[{\"usersId\":\"A0007\",\"authorizationTypeId\":\"2\",\"typeIdEdit\":\"null\",\"farmId\":\"9999\",\"areaId\":\"9999\",\"deleteFlag\":false}]";
        @SuppressWarnings({ "unchecked", "rawtypes" })
		Class<ArrayList<Bnn0087IvbMManager>> klass = (Class<ArrayList<Bnn0087IvbMManager>>) (Class) ArrayList.class;        
        when(bnn0087SearchAffiliationService.insertDataForAffiliation(any(klass))).thenReturn(actuals);
        mockMvc.perform(post("/0087/insertDataForAffiliation").contentType(MediaType.APPLICATION_JSON).content(taskData))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(actuals));
        
    }
}
