package vn.bananavietnam.ict.banana.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import vn.bananavietnam.ict.banana.component.Bnn0047FarmListDataObject;
import vn.bananavietnam.ict.banana.service.Bnn0049AreaListService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0049AreaListControllerTest {

	@InjectMocks
	Bnn0049AreaListController bnn0049AreaListController;

	@Mock
	Bnn0049AreaListService bnn0049AreaListService;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		bnn0049AreaListService = mock(Bnn0049AreaListService.class);
		bnn0049AreaListController = new Bnn0049AreaListController();

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(bnn0049AreaListController).build();
	}

	@Test
    public void testTop() throws Exception {
    	Bnn0047FarmListDataObject bnn0047FarmListDataObject = Mockito.mock(Bnn0047FarmListDataObject.class);
    	// have RequestParams
    	when(bnn0047FarmListDataObject.getFarmId()).thenReturn("F001");
    	when(bnn0047FarmListDataObject.getFarmName()).thenReturn("F1");
    	RequestBuilder requestError = get("/0049/").flashAttr("Bnn0049AreaData", bnn0047FarmListDataObject);
    	mockMvc
        .perform(requestError)
        .andDo(MockMvcResultHandlers.print())
        .andExpect(view().name("banana/Bnn0049AreaList")); 
    }
	
	@Test
    public void testPost() throws Exception {
    	Bnn0047FarmListDataObject bnn0047FarmListDataObject = Mockito.mock(Bnn0047FarmListDataObject.class);
    	// have RequestParams
    	when(bnn0047FarmListDataObject.getFarmId()).thenReturn("F001");
    	when(bnn0047FarmListDataObject.getFarmName()).thenReturn("F1");
    	RequestBuilder requestError = post("/0049/").flashAttr("Bnn0049AreaData", bnn0047FarmListDataObject);
    	mockMvc
        .perform(requestError)
        .andDo(MockMvcResultHandlers.print())
        .andExpect(view().name("banana/Bnn0049AreaList")); 
    }
	
}
