package vn.bananavietnam.ict.common.controller;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import vn.bananavietnam.ict.common.component.FilePath;
import vn.bananavietnam.ict.common.util.Util;
import vn.bananavietnam.ict.common.util.XmlConfigFunction;

/**
 * @author Khoa Le
 */
public class RequestInterceptor implements HandlerInterceptor {
	@Autowired
    private ApplicationContext appContext;
	
	private XmlConfigFunction xmlConfigFunction = new XmlConfigFunction();
	private Util util = new Util();
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		xmlConfigFunction.setFilePath(((FilePath)appContext.getBean("xml_path")).getConfigPath());
		
		// get current URL
		String currentUrl = request.getRequestURL().toString();
		// get current screen id
		Pattern pattern = Pattern.compile("/\\d{4}/");
		Matcher matcher = pattern.matcher(currentUrl);
		if (matcher.find()) {
			String screenId = matcher.group().replaceAll("/", "");
			// if current user doesn't have authority to access this screen
			try {
				String contextPath = request.getContextPath().replace("/", "");
				String[] screenList = xmlConfigFunction.getScreenList(contextPath.toLowerCase());
				if (Arrays.asList(screenList).indexOf(screenId) == -1 
					|| !util.getUserInfo().getSCREENID().contains(screenId)) {
					// redirect to error page
					response.sendRedirect(request.getContextPath() + "/accessdenied");
				}
			} catch (Exception ex) {
				// session timeout
				// redirect to login page
				// check if current request is a AJAX request or not
				String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");

                if (ajaxHeader != null && ajaxHeader.equals("XMLHttpRequest")) {
                	// AJAX case
                    HttpServletResponse resp = (HttpServletResponse) response;
                    resp.getWriter().write("login:-1,j_username:-1,j_password:-1");
                } else {
                	// Submit case
                	response.sendRedirect(request.getContextPath());
                }
                return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}
}