package vn.bananavietnam.ict.common.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import vn.bananavietnam.ict.common.cnst.Constants;
import vn.bananavietnam.ict.common.util.XmlConfigFunction;

public class MySuccessHandler implements AuthenticationSuccessHandler {
	
	private XmlConfigFunction xmlConfigFunction = new XmlConfigFunction();
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {	
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		
		String contextPath = request.getContextPath().replace("/", "");
		String[] appScreenList = xmlConfigFunction.getScreenList(contextPath.toLowerCase());
		ArrayList<String> authScreenList = ((MyUser)authentication.getPrincipal()).getSCREENID();
		
		changePageOnAuth(roles, response, appScreenList, authScreenList);

    }
	
    public void changePageOnAuth(Set<String> roles, HttpServletResponse response, String[] appScreenList, ArrayList<String> authScreenList) throws IOException, ServletException {
		if (roles.contains(Constants.SYSTEM_MANAGER)){
			if (Arrays.asList(appScreenList).indexOf("0001") != -1) {
				if (authScreenList.contains("0001")){
					response.getWriter().write("/0001/");
		            return;
				}
			}
			if (Arrays.asList(appScreenList).indexOf("0047") != -1) {
				if (authScreenList.contains("0047")){
					response.getWriter().write("/0047/");
		            return;
				}
			}
	    }
		if (roles.contains(Constants.CCO)){
			if (Arrays.asList(appScreenList).indexOf("0047") != -1) {
				if (authScreenList.contains("0047")){
					response.getWriter().write("/0047/");
		            return;
				}
			}
			if (Arrays.asList(appScreenList).indexOf("0001") != -1) {
				if (authScreenList.contains("0001")){
					response.getWriter().write("/0001/");
		            return;
				}
			}
	    }
		if (roles.contains(Constants.FARM_MANAGER)){
			if (Arrays.asList(appScreenList).indexOf("0049") != -1) {
				if (authScreenList.contains("0049")){
					response.getWriter().write("/0049/");
					return;
				}
			}
	    }
		if (roles.contains(Constants.AREA_MANAGER)){
			if (Arrays.asList(appScreenList).indexOf("0035") != -1) {
				if (authScreenList.contains("0035")){
					response.getWriter().write("/0035/");
			        return;
				}
			}
	    }
		response.getWriter().write("/accessdenied/");
		return;
    }
}
