package controllers.user;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FollowUpService;
import controllers.AbstractController;
import domain.FollowUp;
import forms.FollowUpForm;

@Controller
@RequestMapping("/followUp/user")
public class FollowUpUserController extends AbstractController {
	// Services -------------------------------------------------------------

	@Autowired
	private FollowUpService followUpService;

	// Constructors ---------------------------------------------------------

	public FollowUpUserController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		
		ModelAndView result;
		Collection<FollowUp> followUps;

		followUps = followUpService.findByPrincipal();

		result = new ModelAndView("followUp/list");
		result.addObject("followUps", followUps);

		return result;
	}
	
	// Creation --------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int articleId) {

		final FollowUp followUp = this.followUpService.create(articleId);
		final FollowUpForm followUpForm = this.followUpService
				.construct(followUp);

		final ModelAndView result = this.createEditModelAndView(followUpForm);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid FollowUpForm followUpForm, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(followUpForm);
		else
			try {
				final FollowUp followUp = this.followUpService.reconstruct(followUpForm, binding);
				this.followUpService.save(followUp);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(followUpForm, "followUp.commit.error");
			}
		return result;
	}
	
	// Display --------------------------------------------------------------

	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(@RequestParam final int followUpId){
		ModelAndView res;
		FollowUp followUp;
		
		followUp = this.followUpService.findOne(followUpId);
		Collection<String> pictures = Arrays.asList(followUp.getPictures().split("\r\n"));
		
		res = new ModelAndView("followUp/display");
		res.addObject("followUp",followUp);
		res.addObject("pictures", pictures);
		
		return res;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(
			final FollowUpForm followUpForm) {

		ModelAndView result;

		result = this.createEditModelAndView(followUpForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(
			final FollowUpForm followUpForm, final String messageCode) {

		ModelAndView result;
		
		if(followUpForm.getId() == 0){
			result = new ModelAndView("followUp/create");
		}else{
			result = new ModelAndView("followUp/edit");
		}

		result.addObject("followUpForm", followUpForm);
		result.addObject("message", messageCode);

		return result;
	}
}
