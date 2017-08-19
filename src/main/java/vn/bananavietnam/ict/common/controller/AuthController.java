package vn.bananavietnam.ict.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class AuthController {

    /**
     * Process the login event
     * @return
     */
    @RequestMapping(value = "/" ,method = RequestMethod.GET)
    public String login() {
        return "common/PageWrapper";
    }

    /**
     * Process the logout event
     * @return
     */
    @RequestMapping(value = "/logout" ,method = RequestMethod.GET)
    public String logout() {
        return "common/logout";
    }

    /**
     * Process when access is denied
     * @param model
     * @return
     */
    @RequestMapping(value = "/accessdenied" ,method = RequestMethod.GET)
    public String accessdenied(Model model) {
        return "common/403";
    }

    /**
     * Process when login failed
     * @param model
     * @return
     */
    @RequestMapping(value = "/autherror" ,method = RequestMethod.GET)
    public String autherror(Model model) {
        return "common/autherror";
    }

    /**
     * Process the error event
     * @return
     */
    @RequestMapping(value = "/error" ,method = RequestMethod.GET)
    public String error() {
        return "common/error";
    }

    @RequestMapping(value = "/session" ,method = RequestMethod.GET)
    public String session() {
        return "common/session";
    }
}
