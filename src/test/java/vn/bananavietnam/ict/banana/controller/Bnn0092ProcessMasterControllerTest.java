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

import vn.bananavietnam.ict.banana.component.Bnn0092ProcessMasterResult;
import vn.bananavietnam.ict.banana.service.Bnn0092ProcessMasterService;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.model.IvbMCultivation;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0092ProcessMasterControllerTest {

	@InjectMocks
	Bnn0092ProcessMasterController controller;

	@Mock
	Bnn0092ProcessMasterService service;

	MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
    	controller = new Bnn0092ProcessMasterController();
    	service = mock(Bnn0092ProcessMasterService.class);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testPost() throws Exception {
    	mockMvc.perform(post("/0092/")).andExpect(status().isOk())
    		.andExpect(view().name("banana/Bnn0092ProcessMaster"));
    }

    @Test
    public void testGet() throws Exception {
    	mockMvc.perform(get("/0092/")).andExpect(status().isOk())
    		.andExpect(view().name("banana/Bnn0092ProcessMaster"));
    }
    
    @Test
    public void testinitData() throws Exception {
    	String input = "{\"farmId\":\"F001\",\"kindId\":\"K001\"}";
    	
    	String output = "[[{\"processId\":null,\"processName\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null}]]";

    	List<List<IvbMProcess>> result = new ArrayList<List<IvbMProcess>>();
    	List<IvbMProcess> temp = new ArrayList<IvbMProcess>();
    	IvbMProcess temppro = new IvbMProcess();
    	temp.add(temppro);
    	result.add(temp);
    	
    	when(service.initData(any(IvbMCultivation.class))).thenReturn(result);
    	mockMvc.perform(post("/0092/initData").contentType(MediaType.APPLICATION_JSON).content(input))
    	.andExpect(MockMvcResultMatchers.status().isOk())
    	.andExpect(MockMvcResultMatchers.content().string(output));
    }
    
    @Test
    public void testgetProcessDetailData() throws Exception {
    	String input = "{\"farmId\":\"F001\",\"kindId\":\"K001\",\"processId\":\"P030\"}";
    	
    	String output = "[{\"processName\":null,\"taskOrder\":null,\"taskName\":null,\"workingDetails\":null,\"note\":null,\"quarantineHandlingFlag\":null,\"changePointCode\":0,\"lastUpdateDate\":null,\"searchDataTotalCounts\":null}]";

    	List<Bnn0092ProcessMasterResult> result =  new ArrayList<Bnn0092ProcessMasterResult>();
    	Bnn0092ProcessMasterResult temp = new Bnn0092ProcessMasterResult();
    	result.add(temp);
    	
    	when(service.getProcessDetailData(any(IvbMCultivation.class))).thenReturn(result);
    	mockMvc.perform(post("/0092/getProcessDetailData").contentType(MediaType.APPLICATION_JSON).content(input))
    	.andExpect(MockMvcResultMatchers.status().isOk())
    	.andExpect(MockMvcResultMatchers.content().string(output));
    }

    @Test
    public void testUpdateData() throws Exception {
    	String input = "[{\"farmId\":\"F001\",\"kindId\":\"K001\"},{\"farmId\":\"F001\",\"kindId\":\"K001\",\"processId\":\"P004\"},{\"farmId\":\"F001\",\"kindId\":\"K001\",\"processId\":\"P002\"},{\"farmId\":\"F001\",\"kindId\":\"K001\",\"processId\":\"P003\"},{\"farmId\":\"F001\",\"kindId\":\"K001\",\"processId\":\"P006\"},{\"farmId\":\"F001\",\"kindId\":\"K001\",\"processId\":\"P001\"},{\"farmId\":\"F001\",\"kindId\":\"K001\",\"processId\":\"P030\"}]";

    	String output = Constants.UPDATE_RESULT_SUCCESSFUL;

    	@SuppressWarnings({ "unchecked", "rawtypes" })
		Class<List<IvbMCultivation>> klass = (Class<List<IvbMCultivation>>) (Class) List.class;  
    	
    	when(service.updateData(any(klass))).thenReturn(Constants.UPDATE_RESULT_SUCCESSFUL);
    	mockMvc.perform(post("/0092/updateData").contentType(MediaType.APPLICATION_JSON).content(input))
    	.andExpect(MockMvcResultMatchers.status().isOk())
    	.andExpect(MockMvcResultMatchers.content().string(output));
    }
    
    @Test
    public void testgetProcessDataTotal() throws Exception {
    	String input = "{\"farmId\":\"F001\",\"kindId\":\"K001\"}";
    	
    	when(service.getUnregisteredProcessDataTotal(any(IvbMCultivation.class))).thenReturn("1");
    	mockMvc.perform(post("/0092/getProcessDataTotal").contentType(MediaType.APPLICATION_JSON).content(input))
    	.andExpect(MockMvcResultMatchers.status().isOk())
    	.andExpect(MockMvcResultMatchers.content().string("1"));
    }
}