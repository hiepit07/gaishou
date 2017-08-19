package vn.bananavietnam.ict.banana.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.bananavietnam.ict.banana.component.Bnn0023SearchUserPopupDataObject;
import vn.bananavietnam.ict.banana.service.Bnn0023SearchUserPopupService;

/**
 * @author Hieu Dao
 */

@Controller
@RequestMapping(value = "/0023")
public class Bnn0023SearchUserPopupController {

	@Autowired
	private Bnn0023SearchUserPopupService bnn0023SearchUserPopupService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String top(Locale locale, Model model) {
		return "banana/Bnn0023SearchUserPopup";
	}

	/**
	 * Search user in DB based on search conditions received from client
	 * @param searchConditions : data received from client
	 * @return List of users data
	 */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Bnn0023SearchUserPopupDataObject> searchData(@RequestBody Bnn0023SearchUserPopupDataObject searchConditions) {
		return bnn0023SearchUserPopupService.searchData(searchConditions);
	}
}