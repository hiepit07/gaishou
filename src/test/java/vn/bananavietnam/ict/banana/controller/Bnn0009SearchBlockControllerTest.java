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

import vn.bananavietnam.ict.banana.component.Bnn0009SearchBlockConditions;
import vn.bananavietnam.ict.banana.component.Bnn0009SearchBlockProductAdjust;
import vn.bananavietnam.ict.banana.component.Bnn0009SearchBlockResult;
import vn.bananavietnam.ict.banana.service.Bnn0009SearchBlockService;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.component.UtilComponent;
import vn.bananavietnam.ict.common.db.model.IvbMBlock;
import vn.bananavietnam.ict.common.db.model.IvbTProduct;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0009SearchBlockControllerTest {

	@InjectMocks
	Bnn0009SearchBlockController controller;

	@Mock
	Bnn0009SearchBlockService service;

	MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
    	controller = new Bnn0009SearchBlockController();
    	service = mock(Bnn0009SearchBlockService.class);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    
    @Test
    public void testTopPost() throws Exception {
    	// Farm ID  = null 
    	mockMvc.perform(post("/0009/")                        
    			.flashAttr("farmId", "F001")
    			.flashAttr("areaId", "A001")).andExpect(status().isOk())
    		.andExpect(view().name("banana/Bnn0009SearchBlock"));
    }

    @Test
    public void testTopGet() throws Exception {
    	// Farm ID  = null 
    	mockMvc.perform(get("/0009/")                        
    			.flashAttr("farmId", "F001")
    			.flashAttr("areaId", "A001")).andExpect(status().isOk())
    		.andExpect(view().name("banana/Bnn0009SearchBlock"));
    }

    @Test
    public void testSearchData() throws Exception {
    	List<Bnn0009SearchBlockResult> bnn0009ResultList = new ArrayList<Bnn0009SearchBlockResult>();
    	Bnn0009SearchBlockResult bnn0007Result = new Bnn0009SearchBlockResult();
    	bnn0007Result.setFarmId("F001");
    	bnn0009ResultList.add(0, bnn0007Result);
 
    	String input = "{\"farmId\":\"F001\",\"areaId\":\"-2\",\"blockId\":\"-2\",\"blockName\":\"\",\"fromRow\":0,\"itemCount\":20}";
    	when(service.searchData(any(Bnn0009SearchBlockConditions.class))).thenReturn(bnn0009ResultList);
    	mockMvc.perform(post("/0009/searchData").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAreaData() throws Exception {
    	List<UtilComponent> listActuals = new ArrayList<UtilComponent>();
    	UtilComponent actuals = new UtilComponent();
    	listActuals.add(0, actuals);
    	String areaData = "[{\"areaId\":null,\"farmId\":null,\"farmName\":null,\"areaName\":null}]";
    	
    	when(service.getAreaDataByFarmId(anyString())).thenReturn(listActuals);
    	mockMvc.perform(post("/0009/getAreaDataByFarmId").param("farmId", "F074"))
	        .andExpect(MockMvcResultMatchers.status().isOk())                
	        .andExpect(MockMvcResultMatchers.content().string(areaData));
    }

    @Test
    public void testGetBlockDataByAreaId() throws Exception {
    	List<IvbMBlock> listActuals = new ArrayList<IvbMBlock>();
    	IvbMBlock actuals = new IvbMBlock();
    	listActuals.add(0, actuals);
    	String blockData = "[{\"farmId\":null,\"areaId\":null,\"blockId\":null,\"blockName\":null,\"note\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null}]";
    	
    	when(service.getBlockDataByAreaId(anyString(), anyString())).thenReturn(listActuals);
    	mockMvc.perform(post("/0009/getBlockDataByAreaId").param("farmId", "F001").param("areaId", "A001"))
	        .andExpect(MockMvcResultMatchers.status().isOk())                
	        .andExpect(MockMvcResultMatchers.content().string(blockData));
    }

    @Test
    public void testGetSingleData() throws Exception {
    	IvbMBlock bnn0005Object = new IvbMBlock();
    	bnn0005Object.setFarmId("F001");
    	when(service.getSingleData(any(String.class),any(String.class),any(String.class))).thenReturn(bnn0005Object);
    	mockMvc.perform(post("/0009/getSingleData").param("farmId", "F001").param("areaId", "AAAA").param("blockId", "B000")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testInsertData() throws Exception {
    	String input = "{\"farmId\":\"F100\",\"areaId\":\"A100\",\"blockId\":\"B100\",\"blockName\":\"B100\",\"note\":null,\"createUserId\":\"A0001\",\"createDate\":null,\"updateUserId\":\"A0001\",\"lastUpdateDate\":null,\"deleteFlag\":0}";

    	String output = Constants.INSERT_RESULT_SUCCESSFUL;

    	when(service.insertData(any(IvbMBlock.class))).thenReturn(Constants.INSERT_RESULT_SUCCESSFUL);
    	mockMvc.perform(post("/0009/insertData").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }

    @Test
    public void testUpdateData() throws Exception {
    	String input = "{\"farmId\":\"F001\",\"areaId\":\"A001\",\"blockId\":\"B000\",\"blockName\":\"B100\",\"note\":null,\"createUserId\":\"A0001\",\"createDate\":null,\"updateUserId\":\"A0001\",\"lastUpdateDate\":null,\"deleteFlag\":0}";

    	String output = Constants.UPDATE_RESULT_SUCCESSFUL;

    	when(service.updateData(any(IvbMBlock.class))).thenReturn(Constants.UPDATE_RESULT_SUCCESSFUL);
    	mockMvc.perform(post("/0009/updateData").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }

    @Test
    public void testDeleteData() throws Exception {
    	String farmId = "F001";
    	String areaId = "A001";
    	String blockId = "B001";
    	String output = Constants.DELETE_RESULT_SUCCESSFUL;

    	when(service.deleteData(any(String.class),any(String.class),any(String.class),any(Date.class))).thenReturn(Constants.DELETE_RESULT_SUCCESSFUL);
    	mockMvc.perform(post("/0009/deleteData").param("farmId", farmId).param("areaId", areaId).param("blockId", blockId).param("lastUpdateDate", "1494995125000")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }
    
    @Test
    public void testGetFarmLinesAndColumnsCount() throws Exception {
    	String output = "2:3";

    	when(service.getFarmLinesAndColumnsCount(any(String.class))).thenReturn(output);
    	mockMvc.perform(post("/0009/getFarmLinesAndColumnsCount").param("farmId", "F001")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }

    @Test
    public void testGetProductData() throws Exception {
    	List<IvbTProduct> listGetProductData = new ArrayList<IvbTProduct>();
    	IvbTProduct actuals = new IvbTProduct();
    	listGetProductData.add(0, actuals);
    	String farmId = "F001";
    	String areaId = "A001";
    	String blockId = "B001";
    	String previousRound = "true";
    	when(service.getProductData(any(String.class),any(String.class),any(String.class),any(Boolean.class))).thenReturn(listGetProductData);
    	mockMvc.perform(post("/0009/getProductData").param("farmId", farmId).param("areaId", areaId).param("blockId", blockId).param("previousRound", previousRound)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateProductData() throws Exception {
    	String input = "{\"idString\":\"F001:A001:B000:L000:C000:true\",\"previousRound\":\"true\"}";
    	
    	String output = Constants.UPDATE_RESULT_SUCCESSFUL;

    	when(service.updateProductData(any(Bnn0009SearchBlockProductAdjust.class))).thenReturn(Constants.UPDATE_RESULT_SUCCESSFUL);
    	mockMvc.perform(post("/0009/updateProductData").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(output));
    }
}