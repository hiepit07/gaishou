package vn.bananavietnam.ict.banana.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import vn.bananavietnam.ict.banana.component.Bnn0002ChangePasswordDataObject;
import vn.bananavietnam.ict.banana.service.Bnn0002ChangePasswordService;
import vn.bananavietnam.ict.common.cnst.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0002ChangePasswordControllerTest {
	@InjectMocks
	Bnn0002ChangePasswordController controller;

	@Mock
	Bnn0002ChangePasswordService service;

	MockMvc mockMvc;
	
	@Before
    public void setUp() throws Exception {
    	controller = new Bnn0002ChangePasswordController();
    	service = mock(Bnn0002ChangePasswordService.class);

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
	
	@Test
    public void testTopOK() throws Exception { 
        mockMvc.perform(get("/0002/"))
        .andExpect(status().isOk())
        .andExpect(view().name("banana/Bnn0002ChangePassword"));
	}
	
	@Test
    public void testupdateData() throws Exception { 
		String updateData = "{\"oldPassword\":\"A0001\",\"newPassword\":\"A0001\",\"newPasswordConfirm\":\"A0001\"}";
		when(service.updateData(any(Bnn0002ChangePasswordDataObject.class))).thenReturn(Constants.UPDATE_RESULT_SUCCESSFUL);
        mockMvc.perform(post("/0002/updateData").contentType(MediaType.APPLICATION_JSON).content(updateData))
        .andExpect(MockMvcResultMatchers.status().isOk())                
        .andExpect(MockMvcResultMatchers.content().string(Constants.UPDATE_RESULT_SUCCESSFUL));
	}
}
