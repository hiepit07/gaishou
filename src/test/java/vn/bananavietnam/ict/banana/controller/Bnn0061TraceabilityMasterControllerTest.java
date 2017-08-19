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

import vn.bananavietnam.ict.banana.component.Bnn0061TraceabilityMasterDataConditions;
import vn.bananavietnam.ict.banana.component.Bnn0061TraceabilityMasterDataObject;
import vn.bananavietnam.ict.banana.service.Bnn0061TraceabilityMasterService;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.model.IvbMBlock;
import vn.bananavietnam.ict.common.db.model.IvbMProcess;
import vn.bananavietnam.ict.common.db.model.IvbMStatus;
import vn.bananavietnam.ict.common.db.model.IvbMTask;
import vn.bananavietnam.ict.common.db.model.IvbTProduct;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0061TraceabilityMasterControllerTest {

	@InjectMocks
	Bnn0061TraceabilityMasterController controller;

	@Mock
	Bnn0061TraceabilityMasterService service;

	MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
    	controller = new Bnn0061TraceabilityMasterController();
    	service = mock(Bnn0061TraceabilityMasterService.class);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testPost() throws Exception {
    	mockMvc.perform(post("/0061/")
    			.flashAttr("shipNumber", "000000001")
    			.flashAttr("farmId", "F001")
    			.flashAttr("areaId", "A001")
    			.flashAttr("harvestDate", "2017-04-21")
    			.flashAttr("shipDate", "2017-04-21")).andExpect(status().isOk())
    		.andExpect(view().name("banana/Bnn0061TraceabilityMaster"));
    }

    @Test
    public void testGet() throws Exception {
    	mockMvc.perform(get("/0061/")).andExpect(status().isOk())
    		.andExpect(view().name("banana/Bnn0061TraceabilityMaster"));
    }

    @Test
    public void testGetAreaData() throws Exception {
    	List<UtilComponent> dataResultList = new ArrayList<UtilComponent>();
    	UtilComponent dataResult = new UtilComponent();
    	dataResult.setAreaId("A001");
    	dataResultList.add(0, dataResult);

    	String input = "{\"farmId\":\"F001\"}";

    	String output = "[{\"areaId\":\"A001\",\"farmId\":null,\"farmName\":null,\"areaName\":null}]";

    	when(service.getAreaData(any(String.class))).thenReturn(dataResultList);
    	mockMvc.perform(post("/0061/getAreaData").param("farmId", input)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }

    @Test
    public void testGetBlockData() throws Exception {
    	List<IvbMBlock> dataResultList = new ArrayList<IvbMBlock>();
    	IvbMBlock dataResult = new IvbMBlock();
    	dataResult.setBlockId("B001");
    	dataResultList.add(0, dataResult);;

    	String input = "{\"farmId\":\"F001\",\"areaId\":\"A001\"}";

    	String output = "[{\"farmId\":null,\"areaId\":null,\"blockId\":\"B001\",\"blockName\":null,\"note\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null}]";

    	when(service.getBlockData(any(IvbMBlock.class))).thenReturn(dataResultList);
    	mockMvc.perform(post("/0061/getBlockData").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(MockMvcResultMatchers.content().string(output));
    }

    @Test
    public void testGetLineData() throws Exception {
    	List<String> dataResultList = new ArrayList<String>();
    	String dataResult = new String();
    	dataResult = "L001";
    	dataResultList.add(0, dataResult);;

    	String input = "{\"farmId\":\"F001\",\"areaId\":\"A001\"}";

    	String output = "[\"L001\"]";

    	when(service.getLineData(any(IvbTProduct.class))).thenReturn(dataResultList);
    	mockMvc.perform(post("/0061/getLineData").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(MockMvcResultMatchers.content().string(output));
    }

    @Test
    public void testGetColumnData() throws Exception {
    	List<String> dataResultList = new ArrayList<String>();
    	String dataResult = new String();
    	dataResult = "C001";
    	dataResultList.add(0, dataResult);;

    	String input = "{\"farmId\":\"F001\",\"areaId\":\"A001\"}";

    	String output = "[\"C001\"]";

    	when(service.getColumnData(any(IvbTProduct.class))).thenReturn(dataResultList);
    	mockMvc.perform(post("/0061/getColumnData").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(MockMvcResultMatchers.content().string(output));
    }

    @Test
    public void testSearchData() throws Exception {
    	List<Bnn0061TraceabilityMasterDataObject> dataResultList = new ArrayList<Bnn0061TraceabilityMasterDataObject>();
    	Bnn0061TraceabilityMasterDataObject dataResult = new Bnn0061TraceabilityMasterDataObject();
    	dataResult.setFarmId("F001");
    	dataResultList.add(0, dataResult);;

    	String input = "{\"farmId\":\"F001\",\"areaId\":\"A001\"}";

    	String output = "[{\"farmId\":\"F001\",\"farmName\":null,\"areaId\":null,\"areaName\":null,\"blockId\":null,\"blockName\":null,\"kindId\":null,\"kindName\":null,\"lineId\":null,\"columnId\":null,\"previousRound\":false,\"workingDate\":null,\"cultivationStartDate\":null,\"plantingDate\":null,\"floweringDate\":null,\"bagClosingDate\":null,\"harvestDate\":null,\"shippingDate\":null,\"processId\":null,\"processName\":null,\"taskId\":null,\"taskName\":null,\"statusId\":null,\"statusName\":null,\"workingDetails\":null,\"taskNote\":null,\"cultivationNote\":null,\"searchDataTotalCounts\":null}]";

    	when(service.searchData(any(Bnn0061TraceabilityMasterDataConditions.class))).thenReturn(dataResultList);
    	mockMvc.perform(post("/0061/searchData").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(MockMvcResultMatchers.content().string(output));
    }

    @Test
    public void testGetSingleData() throws Exception {
    	Bnn0061TraceabilityMasterDataObject dataResult = new Bnn0061TraceabilityMasterDataObject();
    	dataResult.setFarmId("F001");

    	String input = "{\"farmId\":\"F001\",\"areaId\":\"A001\"}";

    	String output = "{\"farmId\":\"F001\",\"farmName\":null,\"areaId\":null,\"areaName\":null,\"blockId\":null,\"blockName\":null,\"kindId\":null,\"kindName\":null,\"lineId\":null,\"columnId\":null,\"previousRound\":false,\"workingDate\":null,\"cultivationStartDate\":null,\"plantingDate\":null,\"floweringDate\":null,\"bagClosingDate\":null,\"harvestDate\":null,\"shippingDate\":null,\"processId\":null,\"processName\":null,\"taskId\":null,\"taskName\":null,\"statusId\":null,\"statusName\":null,\"workingDetails\":null,\"taskNote\":null,\"cultivationNote\":null,\"searchDataTotalCounts\":null}";

    	when(service.getSingleData(any(Bnn0061TraceabilityMasterDataConditions.class))).thenReturn(dataResult);
    	mockMvc.perform(post("/0061/getSingleData").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(MockMvcResultMatchers.content().string(output));
    }
    
    @Test
    public void testGetSingleDataKind() throws Exception {
    	String farmId = "K001";
    	String output = "[{\"farmId\":null,\"statusId\":null,\"statusName\":\"P\",\"inforId\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null}]";
    	List<IvbMStatus> statusData = new ArrayList<IvbMStatus>();
    	IvbMStatus status = new IvbMStatus();
    	status.setStatusName("P");
    	statusData.add(status);
    	
    	when(service.getStatusData(any(String.class))).thenReturn(statusData);
    	mockMvc.perform(post("/0061/getStatusData").param("farmId", farmId))
    	.andExpect(MockMvcResultMatchers.status().isOk())
    	.andExpect(MockMvcResultMatchers.content().string(output));
    }
    
    @Test
    public void testGetTaskData() throws Exception {
    	String farmId = "K001";
    	String processId = "P001";
    	String output = "[{\"taskId\":null,\"taskName\":\"P\",\"workingDetails\":null,\"note\":null,\"quarantineHandlingFlag\":null,\"changePointCode\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null}]";
    	List<IvbMTask> statusData = new ArrayList<IvbMTask>();
    	IvbMTask status = new IvbMTask();
    	status.setTaskName("P");
    	statusData.add(status);
    	
    	when(service.getTaskData(any(String.class), any(String.class))).thenReturn(statusData);
    	mockMvc.perform(post("/0061/getTaskData").param("farmId", farmId).param("processId", processId))
    	.andExpect(MockMvcResultMatchers.status().isOk())
    	.andExpect(MockMvcResultMatchers.content().string(output));
    }
    
    @Test
    public void testGetProcessData() throws Exception {
    	String farmId = "K001";
    	String output = "[{\"processId\":null,\"processName\":\"P\",\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null}]";
    	List<IvbMProcess> statusData = new ArrayList<IvbMProcess>();
    	IvbMProcess status = new IvbMProcess();
    	status.setProcessName("P");
    	statusData.add(status);
    	
    	when(service.getProcessData(any(String.class))).thenReturn(statusData);
    	mockMvc.perform(post("/0061/getProcessData").param("farmId", farmId))
    	.andExpect(MockMvcResultMatchers.status().isOk())
    	.andExpect(MockMvcResultMatchers.content().string(output));
    }
}