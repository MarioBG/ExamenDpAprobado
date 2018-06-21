
package controllers.agent;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AgentService;
import services.NewspaperService;
import controllers.AbstractController;
import domain.Agent;
import domain.Newspaper;

@Controller
@RequestMapping("/newspaper/agent")
public class NewspaperAgentController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private AgentService		agentService;


	// Constructors --------------------------------------------------

	public NewspaperAgentController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/listAdvertised", method = RequestMethod.GET)
	public ModelAndView listAdvertised() {

		Collection<Newspaper> newspapers;

		Agent agent = this.agentService.findByPrincipal();
		Assert.notNull(agent);
		newspapers = this.newspaperService.findByAdvertisementAgentId(agent.getId());

		ModelAndView result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/agent/listAdvertised.do");

		return result;
	}

	@RequestMapping(value = "/listNotAdvertised", method = RequestMethod.GET)
	public ModelAndView listNotAdvertised() {

		Collection<Newspaper> newspapers;

		Agent agent = this.agentService.findByPrincipal();
		Assert.notNull(agent);
		newspapers = this.newspaperService.findByNotAdvertisementAgentId(agent.getId());

		ModelAndView result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/agent/listNotAdvertised.do");

		return result;
	}

}
