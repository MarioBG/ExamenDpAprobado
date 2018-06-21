
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.UserService;
import controllers.AbstractController;
import domain.Chirp;

@Controller
@RequestMapping("/chirp/user")
public class ChirpUserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private UserService		userService;

	@Autowired
	private ChirpService	chirpService;


	// Constructors ---------------------------------------------------------

	public ChirpUserController() {
		super();
	}

	// Creating ----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Chirp chirp;

		chirp = this.chirpService.create();
		result = this.createEditModelAndView(chirp);

		return result;
	}

	@RequestMapping(value = "/list-timeline", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Chirp> chirps;

		chirps = this.chirpService.findChirpsByFollowedFromUser(this.userService.findByPrincipal());

		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);
		result.addObject("requestURI", "chirp/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Chirp chirp, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(chirp, "user.params.error");
		else
			try {
				this.chirpService.save(chirp);
				res = new ModelAndView("redirect:/chirp/list.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(chirp, "user.commit.error");
			}

		return res;
	}

	//Ancillary methods ------------------------

	protected ModelAndView createEditModelAndView(final Chirp chirp) {
		ModelAndView result;

		result = this.createEditModelAndView(chirp, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Chirp chirp, final String message) {
		ModelAndView result;

		result = new ModelAndView("chirp/create");
		result.addObject("chirp", chirp);
		result.addObject("message", message);

		return result;
	}

}
