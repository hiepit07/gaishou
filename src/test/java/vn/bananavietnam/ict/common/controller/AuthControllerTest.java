package vn.bananavietnam.ict.common.controller;

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

import vn.bananavietnam.ict.banana.controller.Bnn0001BananaMenuController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class AuthControllerTest {

	@InjectMocks
	AuthController authController;

	@Mock
	Bnn0001BananaMenuController service;

	MockMvc mockMvc;	
	
    @Before
    public void setUp() throws Exception {
    	service = mock(Bnn0001BananaMenuController.class);
    	authController = new AuthController();    	
    	
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }
    
    @Test
    public void testLogin() throws Exception {
    	mockMvc.perform(get("/")).andExpect(status().isOk())
    		.andExpect(view().name("common/PageWrapper"));
    }
    
    @Test
    public void testLogout() throws Exception {
    	mockMvc.perform(get("/logout/")).andExpect(status().isOk())
    		.andExpect(view().name("common/logout"));
    }
    
    @Test
    public void testAccessdenied() throws Exception {
    	mockMvc.perform(get("/accessdenied/")).andExpect(status().isOk())
    		.andExpect(view().name("common/403"));
    }
    
    @Test
    public void testAutherror() throws Exception {
    	mockMvc.perform(get("/autherror/")).andExpect(status().isOk())
    		.andExpect(view().name("common/autherror"));
    }
    
    @Test
    public void testError() throws Exception {
    	mockMvc.perform(get("/error/")).andExpect(status().isOk())
    		.andExpect(view().name("common/error"));
    }
    
    @Test
    public void testSession() throws Exception {
    	mockMvc.perform(get("/session/")).andExpect(status().isOk())
    		.andExpect(view().name("common/session"));
    }

}
