package vn.bananavietnam.ict.banana.controller;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import vn.bananavietnam.ict.banana.service.Bnn0047FarmListService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0047FarmListControllerTest {

	@InjectMocks
	Bnn0047FarmListController bnn0047FarmListController;

	@Mock
	Bnn0047FarmListService bnn0047FarmListService;

	MockMvc mockMvc;	
	
    @Before
    public void setUp() throws Exception {
    	bnn0047FarmListService = mock(Bnn0047FarmListService.class);
    	bnn0047FarmListController = new Bnn0047FarmListController();    	
    	
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bnn0047FarmListController).build();
    }
    
    @Test
    public void testTop() throws Exception {
    	mockMvc.perform(get("/0047/")).andExpect(status().isOk())
    		.andExpect(view().name("banana/Bnn0047FarmList"));
    }
}
