
package controllers.agent;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;
import services.AdvertisementService;
import services.AgentService;
import services.NewspaperService;
import domain.Admin;
import domain.Advertisement;
import domain.Agent;
import domain.Newspaper;
import forms.AdvertisementForm;

@Controller
@RequestMapping("/advertisement/agent")
public class AdvertisementAgentController {

	@Autowired
	private AdvertisementService	advertisementService;

	@Autowired
	private AgentService			agentService;

	@Autowired
	private NewspaperService		newspaperService;

	@Autowired
	private AdminService			adminService;


	// Constructor
	// -------------------------------------------------------------------

	public AdvertisementAgentController() {
		super();
	}

	// List
	// -------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Advertisement> advertisements;
		Agent agent;
		agent = this.agentService.findByPrincipal();
		advertisements = agent.getAdvertisements();

		res = new ModelAndView("advertisement/list");
		res.addObject("requestURI", "advertisement/list.do");
		res.addObject("advertisements", advertisements);

		return res;
	}

	@RequestMapping(value = "/listTaboo", method = RequestMethod.GET)
	public ModelAndView listTaboo() {
		ModelAndView res;
		Collection<Advertisement> advertisements;
		Admin admin;
		admin = this.adminService.findByPrincipal();
		advertisements = this.advertisementService.getAdvertisementsTabooWords();

		res = new ModelAndView("advertisement/list");
		res.addObject("requestURI", "advertisement/list.do");
		res.addObject("advertisements", advertisements);

		return res;
	}

	// Creation --------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int newspaperId) {
		ModelAndView res;
		Advertisement advertisement;
		AdvertisementForm form;
		Collection<Newspaper> newspapers;

		advertisement = this.advertisementService.create(newspaperId);
		form = this.advertisementService.construct(advertisement);
		newspapers = this.newspaperService.findAll();

		res = this.createEditModelAndView(form);

		return res;
	}

	// Edit-------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int advertisementId) {
		ModelAndView result;
		Advertisement advertisement;
		AdvertisementForm form;

		advertisement = this.advertisementService.findOneToEdit(advertisementId);
		form = this.advertisementService.construct(advertisement);

		result = this.createEditModelAndView(form);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AdvertisementForm advertisementForm, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(advertisementForm);
		else
			try {
				Advertisement advertisement = this.advertisementService.reconstruct(advertisementForm, binding);
				this.advertisementService.save(advertisement);
				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				if (oops.getMessage() == "cardExpireError")
					res = this.createEditModelAndView(advertisementForm, "creditCard.expiration.error");
				else
					res = this.createEditModelAndView(advertisementForm, "application.commit.error");

			}
		return res;

	}

	// Display -----------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int advertisementId) {
		ModelAndView result;
		Advertisement s;

		s = this.advertisementService.findOne(advertisementId);

		result = new ModelAndView("advertisement/display");
		result.addObject("advertisement", s);

		return result;
	}

	// Ancillary methods
	// -------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final AdvertisementForm advertisementForm) {
		ModelAndView res;

		res = this.createEditModelAndView(advertisementForm, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final AdvertisementForm advertisementForm, final String message) {
		ModelAndView res;
		Collection<Newspaper> newspapers;

		res = new ModelAndView("advertisement/edit");
		newspapers = this.newspaperService.findAll();

		res.addObject("advertisementForm", advertisementForm);
		res.addObject("message", message);
		res.addObject("newspapers", newspapers);

		return res;
	}

}
