package vn.bananavietnam.ict.banana.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0001BananaMenuControllerTest {
	@InjectMocks
	Bnn0001BananaMenuController controller;

	MockMvc mockMvc;

	 @Before
	    public void setUp() throws Exception {
	    	controller = new Bnn0001BananaMenuController();

	        MockitoAnnotations.initMocks(this);
	        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	    }

	    @Test
	    public void testTop() throws Exception {
	    	mockMvc.perform(get("/0001/")).andExpect(status().isOk())
	    		.andExpect(view().name("banana/Bnn0001BananaMenu"));
	    }
}