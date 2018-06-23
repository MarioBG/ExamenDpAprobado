
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

import services.NewspaperService;
import services.ZustService;
import controllers.AbstractController;
import domain.Newspaper;
import domain.Zust;

@Controller
@RequestMapping("/zust/admin")
public class ZustAdminController extends AbstractController {

	// Services --------------------------------
	@Autowired
	private ZustService			zustService;

	@Autowired
	private NewspaperService	newspaperService;


	// Constructors ----------------------------
	public ZustAdminController() {
		super();
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
	public ModelAndView save(@Valid final Zust zust, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(zust, "zust.params.error");
		else
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
	public ModelAndView delete(final int zustId) {
		final Zust zust = this.zustService.findOne(zustId);
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
		final int newspaperId = zust.getNewspaper().getId();
		final Newspaper newspaper = this.newspaperService.findOne(newspaperId);
		final Collection<Newspaper> newspapers = new ArrayList<Newspaper>();
		newspapers.add(newspaper);

		res.addObject("zust", zust);
		res.addObject("newspapers", newspapers);
		res.addObject("message", message);

		return res;
	}
}
