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

import services.VolumeService;
import controllers.AbstractController;
import domain.Volume;
import forms.VolumeForm;

@Controller
@RequestMapping("/volume/user")
public class VolumeUserController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private VolumeService volumeService;

	// Constructors --------------------------------------------------

	public VolumeUserController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		Collection<Volume> volumes;

		volumes = volumeService.findByPrincipal();

		ModelAndView result = new ModelAndView("volume/list");
		result.addObject("volumes", volumes);
		result.addObject("requestURI", "volume/user/list.do");

		return result;
	}

	// Creation ------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		Volume volume = volumeService.create();
		VolumeForm volumeForm = volumeService.construct(volume);

		ModelAndView result = createEditModelAndView(volumeForm);
		return result;
	}

	// Edition --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int volumeId) {

		final Volume volume = this.volumeService.findOne(volumeId);
		final VolumeForm volumeForm = this.volumeService.construct(volume);

		final ModelAndView result = this.createEditModelAndView(volumeForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid VolumeForm volumeForm, BindingResult binding) {
		
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(volumeForm);
		else
			try {
				final Volume volume = this.volumeService.reconstruct(
						volumeForm, binding);
				this.volumeService.save(volume);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(volumeForm,
						"volume.commit.error");
			}
		return result;
	}
	
	@RequestMapping(value = "/addNewspaper", method = RequestMethod.GET)
	public ModelAndView addNewspaper(@RequestParam int newspaperId, @RequestParam int volumeId){
		
		ModelAndView result;
		
		try{
			volumeService.addNewspaper(volumeId, newspaperId);
			result = new ModelAndView("redirect:../../newspaper/user/listAddNewspapers.do?volumeId="+volumeId);
		}catch(Throwable oops){
			result = new ModelAndView("redirect:../../newspaper/user/listAddNewspapers.do?volumeId="+volumeId);
			result.addObject("message", "volume.commit.error");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/removeNewspaper", method = RequestMethod.GET)
	public ModelAndView removeNewspaper(@RequestParam int newspaperId, @RequestParam int volumeId){
		
		ModelAndView result;
		
		try{
			volumeService.removeNewspaper(volumeId, newspaperId);
			result = new ModelAndView("redirect:../../newspaper/user/listAddNewspapers.do?volumeId="+volumeId);
		}catch(Throwable oops){
			result = new ModelAndView("redirect:../../newspaper/user/listAddNewspapers.do?volumeId="+volumeId);
			result.addObject("message", "volume.commit.error");
		}
		
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final VolumeForm volumeForm) {

		ModelAndView result;

		result = this.createEditModelAndView(volumeForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final VolumeForm volumeForm,
			final String messageCode) {

		ModelAndView result;

		result = new ModelAndView("volume/edit");
		result.addObject("volumeForm", volumeForm);
		result.addObject("message", messageCode);

		return result;
	}

}
