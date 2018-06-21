package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NewspaperService;
import services.VolumeService;
import controllers.AbstractController;
import domain.Newspaper;
import domain.Volume;
import forms.NewspaperForm;

@Controller
@RequestMapping("/newspaper/user")
public class NewspaperUserController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private NewspaperService newspaperService;
	
	@Autowired
	private VolumeService volumeService;

	// Constructors --------------------------------------------------

	public NewspaperUserController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		Collection<Newspaper> newspapers;

		newspapers = newspaperService.findByPrincipal();

		ModelAndView result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/user/list.do");

		return result;
	}
	
	@RequestMapping(value = "/list-nonPublished", method = RequestMethod.GET)
	public ModelAndView listNonPublished() {

		Collection<Newspaper> newspapers;

		newspapers = newspaperService.findNonPublished();

		ModelAndView result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/user/list-nonPublished.do");

		return result;
	}
	
	@RequestMapping(value = "/listAddNewspapers", method = RequestMethod.GET)
	public ModelAndView listAddNewspapers(@RequestParam int volumeId) {

		Collection<Newspaper> newspapers;
		Volume volume = volumeService.findOne(volumeId);

		newspapers = newspaperService.findAvalibleNewspapers();

		ModelAndView result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("volume", volume);
		result.addObject("requestURI", "newspaper/user/listAddNewspapers.do");

		return result;
	}

	// Create ---------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final Newspaper newspaper = this.newspaperService.create();
		final NewspaperForm newspaperForm = this.newspaperService
				.construct(newspaper);

		final ModelAndView result = this.createEditModelAndView(newspaperForm);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid NewspaperForm newspaperForm, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(newspaperForm);
		else
			try {
				final Newspaper newspaper = this.newspaperService.reconstruct(newspaperForm, binding);
				this.newspaperService.save(newspaper);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(newspaperForm, "newspaper.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(
			final NewspaperForm newspaperForm) {

		ModelAndView result;

		result = this.createEditModelAndView(newspaperForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(
			final NewspaperForm newspaperForm, final String messageCode) {

		ModelAndView result;

		result = new ModelAndView("newspaper/edit");
		result.addObject("newspaperForm", newspaperForm);
		result.addObject("message", messageCode);

		return result;
	}

}
