package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SubscriptionVolumeService;
import services.VolumeService;
import controllers.AbstractController;
import domain.SubscriptionVolume;
import domain.Volume;
import forms.SubscriptionVolumeForm;

@Controller
@RequestMapping("/subscriptionVolume/customer")
public class SubscriptionVolumeCustomerController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private SubscriptionVolumeService subscriptionVolumeService;

	@Autowired
	private VolumeService volumeService;

	// Constructors --------------------------------------------------

	public SubscriptionVolumeCustomerController() {
		super();
	}

	// Create -------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int volumeId) {

		final SubscriptionVolume subscriptionVolume = this.subscriptionVolumeService.create(volumeId);
		final SubscriptionVolumeForm subscriptionVolumeForm = this.subscriptionVolumeService
				.construct(subscriptionVolume);

		final ModelAndView result = this.createEditModelAndView(subscriptionVolumeForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid SubscriptionVolumeForm subscriptionVolumeForm, BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(subscriptionVolumeForm);
		else
			try {
				final SubscriptionVolume subscriptionVolume = this.subscriptionVolumeService
						.reconstruct(subscriptionVolumeForm, binding);
				this.subscriptionVolumeService.save(subscriptionVolume);
				result = new ModelAndView("redirect:../../volume/customer/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(subscriptionVolumeForm, "subscriptionVolume.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final SubscriptionVolumeForm subscriptionVolumeForm) {

		ModelAndView result;

		result = this.createEditModelAndView(subscriptionVolumeForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SubscriptionVolumeForm subscriptionVolumeForm,
			final String messageCode) {

		ModelAndView result;

		Volume volume = volumeService.findOne(subscriptionVolumeForm.getVolumeId());

		result = new ModelAndView("subscriptionVolume/create");
		result.addObject("subscriptionVolumeForm", subscriptionVolumeForm);
		result.addObject("volume", volume);
		result.addObject("message", messageCode);

		return result;
	}

}
