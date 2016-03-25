/**
 * 
 */
package com.haint.oauth.controller;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.haint.oauth.dao.UserDAO;
import com.haint.oauth.model.User;
import com.haint.oauth.net.FacebookAPIWrapper;
import com.haint.oauth.net.GoogleAPIWrapper;
import com.haint.oauth.net.SocialNetworkAPIWrapper;
import com.haint.oauth.net.SocialNetworkAPIWrapperFactory;
import com.haint.oauth.net.SocialNetworkType;

/**
 * @author HAINT
 *
 */
@Controller
public class BaseController {
	private final static Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
	private UserDAO userDAO = new UserDAO();
	private FacebookAPIWrapper fbAPIWrapper = new FacebookAPIWrapper();
	private GoogleAPIWrapper googleAPIWrapper = new GoogleAPIWrapper();
	private User user = null;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(ModelMap model) {

		LOGGER.debug("[BaseController] Welcome to OAuth Sample ");

		model.put("userForm", new User());
		model.put("fbAPIWrapper", fbAPIWrapper);
		model.put("googleAPIWrapper", googleAPIWrapper);
		model.put("errorMsg", "");
		// Spring uses InternalResourceViewResolver and return back login.jsp
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("userForm") User user, ModelMap model) {

		LOGGER.debug("[BaseController] Welcome to OAuth Sample ");
		LOGGER.debug(String.format("Username = %s >>> Password = %s", user.getUsername(), user.getPassword()));

		user = userDAO.checkLogin(user, false);

		if (user == null) {
			model.put("errorMsg", "Username or password is incorrect!");
			return "login";
		} else {
			model.put("user", user);
			return "index";
		}
	}

	@RequestMapping(value = "/snlogin/{type}", method = RequestMethod.GET)
	public String snlogin(ModelMap model, @RequestParam("code") String code, @PathVariable("type") String type) {

		LOGGER.debug(String.format(">>> Code = %s", code));
		LOGGER.debug(">>> Social type: " + type);

		SocialNetworkAPIWrapperFactory snApiWrapperFactory = new SocialNetworkAPIWrapperFactory();
		SocialNetworkAPIWrapper snApiWrapper = snApiWrapperFactory.getSocialNetworkAPIWrapper(SocialNetworkType.fromString(type));

		String accessToken = snApiWrapper.getAccessToken(code);
		snApiWrapper.setAccessToken(accessToken);
		User user = snApiWrapper.getUser();
		boolean userExist = userDAO.checkLogin(user, true) != null;

		// If user does not exist -> register new user
		if (!userExist) {
			userDAO.register(user, true);
		}

		this.user = user;
		return "redirect:/index";
	}

	@RequestMapping("/index")
	public String displayHomepage(ModelMap model) {
		model.put("user", user);
		return "index";
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String text = "Sá»­ dá»¥ng chÆ°Æ¡ng trÃ¬nh khÃ´ng chÃ­nh xÃ¡c. HÃ£y xem láº¡i tÃ i liá»‡u 'HÆ°á»›ng dáº«n ngÆ°á» i sá»­ dá»¥ng', vÃ  lÃ m theo.";
		System.out.println(new String(text.getBytes("Unicode")));
	}
}
