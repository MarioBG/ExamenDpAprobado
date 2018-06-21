package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.VolumeService;
import domain.Volume;

@Controller
@RequestMapping("/volume")
public class VolumeController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private VolumeService volumeService;
	
	@Autowired
	private CustomerService customerService;
	
	// Constructors --------------------------------------------------

	public VolumeController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		Collection<Volume> volumes = new ArrayList<Volume>();
		Collection<Volume> subscribedVolumes = new ArrayList<Volume>();

		volumes = volumeService.findAll();
		
		if(customerService.findByPrincipal() != null){
			subscribedVolumes = volumeService.findSubscribedVolumesByPrincipal();
		}

		ModelAndView result = new ModelAndView("volume/list");
		result.addObject("volumes", volumes);
		result.addObject("subscribedVolumes", subscribedVolumes);
		result.addObject("requestURI", "volume/list.do");

		return result;
	}

}
