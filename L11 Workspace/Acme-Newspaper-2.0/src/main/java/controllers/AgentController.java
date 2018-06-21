
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AgentService;
import domain.Agent;
import forms.AgentForm;

@Controller
@RequestMapping("/agent")
public class AgentController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private AgentService	agentService;


	// Constructors ---------------------------------------------------------

	public AgentController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Agent> agents;
		Agent principal;

		agents = this.agentService.findAll();
		principal = this.agentService.findByPrincipal();
		if (principal != null)
			agents.remove(principal);

		result = new ModelAndView("agent/list");
		result.addObject("agents", agents);
		result.addObject("principal", principal);
		result.addObject("requestURI", "agent/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView listUser(@RequestParam final int agentId) {
		ModelAndView result;
		Agent agent;

		agent = this.agentService.findOne(agentId);

		result = new ModelAndView("agent/display");
		result.addObject("agent", agent);
		result.addObject("requestURI", "agent/display.do");

		return result;
	}

	// Register

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;

		final Agent agent = this.agentService.create();
		final AgentForm agentForm = this.agentService.construct(agent);

		res = this.createEditModelAndView(agentForm);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AgentForm agentForm, final BindingResult binding) {
		ModelAndView res;
		Agent agent;

		if (binding.hasErrors())
			res = this.createEditModelAndView(agentForm, "user.params.error");
		else if (!agentForm.getRepeatPassword().equals(agentForm.getPassword()))
			res = this.createEditModelAndView(agentForm, "user.commit.errorPassword");
		else if (agentForm.getTermsAndConditions() == false)
			res = this.createEditModelAndView(agentForm, "user.params.errorTerms");
		else
			try {
				agent = this.agentService.reconstruct(agentForm, binding);
				this.agentService.save(agent);
				res = new ModelAndView("redirect:../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(agentForm, "user.commit.error");
			}

		return res;
	}

	// Ancillary methods ---------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final AgentForm agentForm) {
		ModelAndView result;

		result = this.createEditModelAndView(agentForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final AgentForm agentForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("agent/register");
		result.addObject("agentForm", agentForm);
		result.addObject("message", message);

		return result;
	}

}
