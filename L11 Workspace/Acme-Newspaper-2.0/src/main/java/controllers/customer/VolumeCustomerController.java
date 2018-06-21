package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.VolumeService;
import controllers.AbstractController;
import domain.Volume;

@Controller
@RequestMapping("/volume/customer")
public class VolumeCustomerController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private VolumeService volumeService;

	// Constructors --------------------------------------------------

	public VolumeCustomerController() {
		super();
	}

	// List -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		Collection<Volume> volumes;

		volumes = volumeService.findSubscribedVolumesByPrincipal();

		ModelAndView result = new ModelAndView("volume/list");
		result.addObject("volumes", volumes);
		result.addObject("requestURI", "volume/customer/list.do");

		return result;
	}

}
