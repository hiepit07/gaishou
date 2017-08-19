package vn.bananavietnam.ict.banana.controller;

import static org.mockito.Matchers.any;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import vn.bananavietnam.ict.banana.component.Bnn0003SearchUserConditions;
import vn.bananavietnam.ict.banana.component.Bnn0003SearchUserDataObject;
import vn.bananavietnam.ict.banana.component.Bnn0003SearchUserResult;
import vn.bananavietnam.ict.banana.service.Bnn0003SearchUserService;
import vn.bananavietnam.ict.common.auth.MyUser;
import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.db.model.IvbMUsers;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context-test.xml")
public class Bnn0003SearchUserControllerTest { 
	@InjectMocks
	Bnn0003SearchUserController bnn0003SearchUserController;

	@Mock
	Bnn0003SearchUserService bnn0003SearchUserService;

	MockMvc mockMvc;
	
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bnn0003SearchUserController).build();
        //mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        ArrayList<String> screenId = new ArrayList<String>();
        screenId.add("0003");
        MyUser u = new MyUser("A0007", "A0007", true, true, true, true, authorities, "1", "A0007", "A0007", screenId);
        Authentication authToken = new UsernamePasswordAuthenticationToken (u, "A0007", authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
    
    @Test
    public void testTop() throws Exception { 
        mockMvc.perform(get("/0003/"))
        .andExpect(status().isOk())
        .andExpect(view().name("banana/Bnn0003SearchUser"));
	}
    
    @Test
    public void testsearchData() throws Exception {
    	List<Bnn0003SearchUserResult> actual = new ArrayList<Bnn0003SearchUserResult>();
    	Bnn0003SearchUserResult temp = new Bnn0003SearchUserResult();
    	temp.setUsersId("A0007");
    	temp.setUsersName("A0007");
    	temp.setDeleteFlag(false);
    	temp.setSearchDataTotalCounts("1");
    	actual.add(temp);
    	when(bnn0003SearchUserService.searchData(any(Bnn0003SearchUserConditions.class))).thenReturn(actual);
    	String search = "{\"usersId\":\"A0007\",\"usersName\":\"\",\"usersRoleTypeString\":\"-2\",\"fromRow\":0,\"itemCount\":20}";
    	String expecteds = "[{\"usersId\":\"A0007\",\"usersName\":\"A0007\",\"password\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":false,\"searchDataTotalCounts\":\"1\"}]";
    	
    	mockMvc.perform(post("/0003/searchData").contentType(MediaType.APPLICATION_JSON).content(search))
		.andExpect(MockMvcResultMatchers.status().isOk())                
        .andExpect(MockMvcResultMatchers.content().string(expecteds));
	}
    
    @Test
    public void testupdateData() throws Exception { 
     	when(bnn0003SearchUserService.updateData(any(Bnn0003SearchUserDataObject.class))).thenReturn(Constants.UPDATE_RESULT_SUCCESSFUL);
    	String search = "{\"usersId\":\"A0007\",\"usersName\":\"A0007\",\"password\":\"b2803ecbfc23d5fb85ed2da191089474\",\"passwordChanged\":false,\"deleteFlag\":false}";

    	mockMvc.perform(post("/0003/updateData").contentType(MediaType.APPLICATION_JSON).content(search))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(Constants.UPDATE_RESULT_SUCCESSFUL));
	}
    
    @Test
    public void testinsertData() throws Exception { 
    	when(bnn0003SearchUserService.insertData(any(IvbMUsers.class))).thenReturn(Constants.INSERT_RESULT_SUCCESSFUL);
    	String search = "{\"usersId\":\"U1234567890\",\"usersName\":\"U1234567890\",\"password\":\"A00001\",\"deleteFlag\":false}";

    	mockMvc.perform(post("/0003/insertData").contentType(MediaType.APPLICATION_JSON).content(search))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(Constants.INSERT_RESULT_SUCCESSFUL));
	}
    
    @Test
    public void testdeleteData() throws Exception { 
        when(bnn0003SearchUserService.deleteData(any(IvbMUsers.class))).thenReturn(Constants.DELETE_RESULT_SUCCESSFUL);
        String search = "{\"usersId\":\"U1234567890\",\"usersName\":\"U1234567890\",\"password\":\"A00001\",\"deleteFlag\":false}";

    	mockMvc.perform(post("/0003/deleteData").contentType(MediaType.APPLICATION_JSON).content(search))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(Constants.DELETE_RESULT_SUCCESSFUL));
	}
    
    @Test
    public void testgetSingleData() throws Exception { 
    	IvbMUsers u = new IvbMUsers();
    	u.setUsersId("U1234567890");
    	u.setUsersName("U1234567890");
    	String expecteds = "{\"usersId\":\"U1234567890\",\"usersName\":\"U1234567890\",\"password\":null,\"createUserId\":null,\"createDate\":null,\"updateUserId\":null,\"lastUpdateDate\":null,\"deleteFlag\":null}";
    	
    	when(bnn0003SearchUserService.getSingleData(any(String.class))).thenReturn(u);
        mockMvc.perform(post("/0003/getSingleData").param("usersId", "U1234567890"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(expecteds));
	}
}
