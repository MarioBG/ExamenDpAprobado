package controllers.admin;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Newspaper;
import domain.Zust;
import services.NewspaperService;
import services.ZustService;

@Controller
@RequestMapping("/zust/admin")
public class ZustAdminController extends AbstractController {

	// Services --------------------------------
	@Autowired
	private ZustService zustService;

	@Autowired
	private NewspaperService newspaperService;

	// Constructors ----------------------------
	public ZustAdminController() {
		super();
	}

	// List -----------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) Integer newspaperId,
			@RequestParam(required = false) String keyword) {
		ModelAndView res;
		Collection<Zust> zusts;

		zusts = this.zustService.findAll();

		res = new ModelAndView("zust/list");
		res.addObject("zust", zusts);
		res.addObject("requestURI", "zust/admin/list.do");

		return res;
	}

	// Create ----------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Zust zust;

		zust = this.zustService.create();

		res = this.createEditModelAndView(zust);
		return res;
	}

	// Edit ----------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int zustId) {
		ModelAndView res;
		Zust zust;

		zust = this.zustService.findOne(zustId);

		res = this.createEditModelAndView(zust);
		return res;
	}

	// Save ----------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Zust zust, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(zust, "zust.params.error");
		} else
			try {
				this.zustService.save(zust);

				res = new ModelAndView("redirect:/zust/user/list.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(zust, "zust.commit.error");
			}

		return res;
	}

	// Delete ----------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(int zustId) {
		Zust zust = this.zustService.findOne(zustId);
		this.zustService.delete(zust);
		return new ModelAndView("redirect:list.do");
	}

	// Ancillary methods ----------------------------------------

	protected ModelAndView createEditModelAndView(final Zust zust) {
		ModelAndView res;

		res = this.createEditModelAndView(zust, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final Zust zust, final String message) {
		ModelAndView res;
		res = new ModelAndView("zust/edit");
		int newspaperId = zust.getNewspaper().getId();
		Newspaper newspaper = this.newspaperService.findOne(newspaperId);
		Collection<Newspaper> newspapers = new ArrayList<Newspaper>();
		newspapers.add(newspaper);

		res.addObject("zust", zust);
		res.addObject("newspapers", newspapers);
		res.addObject("message", message);

		return res;
	}
}
