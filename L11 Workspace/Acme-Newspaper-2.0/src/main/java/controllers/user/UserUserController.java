package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;
import controllers.AbstractController;
import domain.User;

@Controller
@RequestMapping("/user/user")
public class UserUserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private UserService userService;

	// Constructors ---------------------------------------------------------

	public UserUserController() {
		super();
	}
	
	// Listing ---------------------------------------------------------------
	
	@RequestMapping(value = "/list-followers", method = RequestMethod.GET)
	public ModelAndView followers(){
		
		Collection<User> users = userService.findFollowersByPrincipal();
		User principal = userService.findByPrincipal();
		
		ModelAndView result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("principal", principal);
		result.addObject("requestURI", "user/user/list-followers.do");
		
		return result;
	}
	
	@RequestMapping(value = "/list-followed", method = RequestMethod.GET)
	public ModelAndView followed(){
		
		Collection<User> users = userService.findFollowedUsersByPrincipal();
		User principal = userService.findByPrincipal();
		
		ModelAndView result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("principal", principal);
		result.addObject("requestURI", "user/user/list-followed.do");
		
		return result;
	}

	// Follow ----------------------------------------------------------------

	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public ModelAndView follow(@RequestParam int userId) {

		ModelAndView result;
		try {
			userService.follow(userId);
			result = new ModelAndView("redirect:list-followed.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:list-followed.do");
			result.addObject("message", "user.commit.error");
		}

		return result;
	}

	// Unfollow ----------------------------------------------------------------

	@RequestMapping(value = "/unfollow", method = RequestMethod.GET)
	public ModelAndView unfollow(@RequestParam int userId) {

		ModelAndView result;
		try {
			userService.unfollow(userId);
			result = new ModelAndView("redirect:list-followed.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:list-followed.do");
			result.addObject("message", "user.commit.error");
		}

		return result;
	}

}
