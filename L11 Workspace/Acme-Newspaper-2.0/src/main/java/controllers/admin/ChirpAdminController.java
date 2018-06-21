
package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;
import services.ChirpService;
import controllers.AbstractController;
import domain.Chirp;

@Controller
@RequestMapping("/chirp/admin")
public class ChirpAdminController extends AbstractController {

	// Services -------------------------------------------------------------


	@Autowired
	private AdminService	adminService;

	@Autowired
	private ChirpService	chirpService;


	// Constructors ---------------------------------------------------------

	public ChirpAdminController() {
		super();
	}
	
	// Listing --------------------------------------------------------------

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result;
			Collection<Chirp> chirps;

			chirps = this.chirpService.findAll();

			result = new ModelAndView("chirp/list");
			result.addObject("chirps", chirps);
			result.addObject("requestURI", "chirp/admin/list.do");

			return result;
		}

	//Deleting ---------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int chirpId) {

		Assert.notNull(this.adminService.findByPrincipal());

		final Chirp chirp = this.chirpService.findOne(chirpId);

		this.chirpService.delete(chirp);

		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Chirp chirp, final BindingResult binding) {
		ModelAndView result;

		try {
			this.chirpService.delete(chirp);
			result = new ModelAndView("redirect:/");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(chirp, "application.commit.error");
		}

		return result;
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
