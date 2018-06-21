
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.UserService;
import domain.Chirp;

@Controller
@RequestMapping("/chirp")
public class ChirpController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private UserService		userService;

	@Autowired
	private ChirpService	chirpService;


	// Constructors ---------------------------------------------------------

	public ChirpController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer userId, @RequestParam(required = false) final Integer followingId) {
		ModelAndView result;
		Collection<Chirp> chirps;

		if (userId != null) {
			Assert.notNull(this.userService.findOne(userId));
			chirps = this.chirpService.findChirpsByUser(this.userService.findOne(userId));
		} else if (followingId != null) {
			Assert.notNull(this.userService.findOne(followingId));
			chirps = this.chirpService.findChirpsByFollowedFromUser(this.userService.findOne(followingId));

		} else
			chirps = this.chirpService.findAll();

		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);
		result.addObject("requestURI", "chirp/list.do");

		return result;
	}

	//	@RequestMapping(value = "/user/display", method = RequestMethod.GET)
	//	public ModelAndView listUser(@RequestParam final int chirpId) {
	//		ModelAndView result;
	//		Chirp chirp;
	//
	//		chirp = this.chirpService.findOne(chirpId);
	//
	//		result = new ModelAndView("chirp/display");
	//		result.addObject("chirp", chirp);
	//		result.addObject("requestURI", "chirp/display.do");
	//
	//		return result;
	//	}

	// Editing ---------------------------------------------------------------

	//	@RequestMapping(value = "/user/edit", method = RequestMethod.GET)
	//	public ModelAndView edit(final int chirpId) {
	//		ModelAndView result;
	//		final Chirp chirp = this.chirpService.findOne(chirpId);
	//
	//		result = this.createEditModelAndView(chirp);
	//
	//		return result;
	//	}
	//
	//	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView editSave(@Valid final Chirp c, final BindingResult binding) {
	//		ModelAndView res;
	//		if (binding.hasErrors())
	//			res = this.createEditModelAndView(c, "user.params.error");
	//		else
	//			try {
	//				this.chirpService.save(c);
	//				res = new ModelAndView("redirect:/user/chirp/list.do");
	//			} catch (final Throwable oops) {
	//				res = this.createEditModelAndView(c, "user.commit.error");
	//			}
	//		System.out.println(binding);
	//
	//		return res;
	//	}

}
