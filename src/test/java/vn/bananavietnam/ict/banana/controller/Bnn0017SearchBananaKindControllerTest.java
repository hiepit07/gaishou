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

import vn.bananavietnam.ict.banana.component.Bnn0017SearchBananaKindConditions;
import vn.bananavietnam.ict.banana.component.Bnn0017SearchBananaKindResult;
import vn.bananavietnam.ict.banana.service.Bnn0017SearchBananaKindService;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.model.IvbMKind;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0017SearchBananaKindControllerTest {
	@InjectMocks
	Bnn0017SearchBananaKindController controller;

	@Mock
	Bnn0017SearchBananaKindService service;

	MockMvc mockMvc;
	
	@Before
    public void setUp() throws Exception {
    	controller = new Bnn0017SearchBananaKindController();
    	service = mock(Bnn0017SearchBananaKindService.class);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
	
	@Test
	public void testTop() throws Exception { 
        mockMvc.perform(get("/0017/"))
        .andExpect(status().isOk())
        .andExpect(view().name("banana/Bnn0017SearchBananaKind"));  
    }
	
	@Test
	public void testsearchData() throws Exception { 
		String search = "{\"kindName\":\"\",\"fromRow\":0,\"itemCount\":20}";
		String expecteds = "[{\"kindId\":null,\"kindName\":null,\"prospectiveHarvestAmount\":null,\"estimatedDaysFlowering\":null,\"estimatedDaysBagging\":null,\"estimatedDaysHarvest\":null,\"deleteFlag\":null,\"lastUpdateDate\":null,\"searchDataTotalCounts\":\"1\"}]";
		List<Bnn0017SearchBananaKindResult> result = new ArrayList<Bnn0017SearchBananaKindResult>();
		Bnn0017SearchBananaKindResult temp = new Bnn0017SearchBananaKindResult();
		temp.setSearchDataTotalCounts("1");
		result.add(temp);
		when(service.searchData(any(Bnn0017SearchBananaKindConditions.class))).thenReturn(result);
		
        mockMvc.perform(post("/0017/searchData").contentType(MediaType.APPLICATION_JSON).content(search))
		.andExpect(MockMvcResultMatchers.status().isOk())            
	    .andExpect(MockMvcResultMatchers.content().string(expecteds)); 
    } 
	 
	@Test
	public void testgetSingleData() throws Exception { 
		String expecteds = "{\"kindId\":\"hihi\",\"kindName\":\"hihi\",\"prospectiveHarvestAmount\":null,\"estimatedDaysFlowering\":null,\"estimatedDaysBagging\":null,\"estimatedDaysHarvest\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":false}";
		
		IvbMKind result = new IvbMKind();
		result.setDeleteFlag(false);
		result.setKindId("hihi");
		result.setKindName("hihi");
		when(service.getSingleData(any(String.class))).thenReturn(result);
		
        mockMvc.perform(post("/0017/getSingleData").param("kindId", "aaa"))
		.andExpect(MockMvcResultMatchers.status().isOk())                
	    .andExpect(MockMvcResultMatchers.content().string(expecteds));
    }
	
	@Test
	public void testupdateData() throws Exception { 
		String search = "{\"kindId\":\"K001\",\"kindName\":\"Kind 001\",\"prospectiveHarvestAmount\":\"1\",\"estimatedDaysFlowering\":\"1\",\"estimatedDaysBagging\":\"1\",\"estimatedDaysHarvest\":\"1\",\"deleteFlag\":false}";
	        
		when(service.updateData(any(IvbMKind.class))).thenReturn(Constants.UPDATE_RESULT_SUCCESSFUL);
		mockMvc.perform(post("/0017/updateData").contentType(MediaType.APPLICATION_JSON).content(search))
		.andExpect(MockMvcResultMatchers.status().isOk())                
	    .andExpect(MockMvcResultMatchers.content().string(Constants.UPDATE_RESULT_SUCCESSFUL)); 
    }
	
	@Test
	public void testinsertData() throws Exception { 
		String search = "{\"kindId\":\"\",\"kindName\":\"KK08\",\"prospectiveHarvestAmount\":\"12.552\",\"estimatedDaysFlowering\":\"23\",\"estimatedDaysBagging\":\"32\",\"estimatedDaysHarvest\":\"321\",\"deleteFlag\":false}";
		
		when(service.insertData(any(IvbMKind.class))).thenReturn(Constants.INSERT_RESULT_SUCCESSFUL);
        mockMvc.perform(post("/0017/insertData").contentType(MediaType.APPLICATION_JSON).content(search))
		.andExpect(MockMvcResultMatchers.status().isOk())                
	    .andExpect(MockMvcResultMatchers.content().string(Constants.INSERT_RESULT_SUCCESSFUL)); 
    }
	
	@Test
	public void testdeleteData() throws Exception { 
		String search = "{\"kindId\":\"\",\"kindName\":\"KK08\",\"prospectiveHarvestAmount\":\"12.552\",\"estimatedDaysFlowering\":\"23\",\"estimatedDaysBagging\":\"32\",\"estimatedDaysHarvest\":\"321\",\"deleteFlag\":false}";
		when(service.deleteData(any(IvbMKind.class))).thenReturn(Constants.DELETE_RESULT_SUCCESSFUL);
        mockMvc.perform(post("/0017/deleteData").contentType(MediaType.APPLICATION_JSON).content(search))
		.andExpect(MockMvcResultMatchers.status().isOk())                
	    .andExpect(MockMvcResultMatchers.content().string(Constants.DELETE_RESULT_SUCCESSFUL)); 
    }
}
