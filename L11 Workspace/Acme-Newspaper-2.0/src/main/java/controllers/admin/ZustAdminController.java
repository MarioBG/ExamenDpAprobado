
package controllers.admin;

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
import domain.Admin;
import domain.Newspaper;
import domain.Zust;
import services.AdminService;
import services.NewspaperService;
import services.ZustService;

@Controller
@RequestMapping("/zust/admin")
public class ZustAdminController extends AbstractController {

	// Services --------------------------------
	@Autowired
	private ZustService zustService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private NewspaperService newspaperService;

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

				res = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(zust, "zust.commit.error");
			}

		return res;
	}

	// Delete ----------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int zustId) {

		ModelAndView result = new ModelAndView("redirect:/welcome/index.do");
		final Zust zust = this.zustService.findOne(zustId);
		try {
			this.zustService.delete(zust);
		} catch (Throwable oops) {
			result.addObject("message", "zust.commit.error");
		}
		return result;
	}

	// List ---------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer newspaperId,
			@RequestParam(required = false) final String keyword) {
		ModelAndView res;
		Collection<Zust> zusts;
		Collection<Zust> zustToEdit;
		Admin admin = this.adminService.findByPrincipal();
		int adminId = admin.getId();
		zusts = this.zustService.findAllByAdminId(adminId);
		zustToEdit = this.zustService.findOneToEdit(adminId);

		res = new ModelAndView("zust/list");
		res.addObject("zust", zusts);
		res.addObject("zustToEdit", zustToEdit);
		res.addObject("requestURI", "zust/admin/list.do");

		return res;
	}

	// ADD ZUST

	@RequestMapping(value = "/addToNewspaper", method = RequestMethod.GET)
	public ModelAndView addToNewspaper(@RequestParam int newspaperId, @RequestParam int zustId) {
		ModelAndView result;
		Admin admin = this.adminService.findByPrincipal();
		final Collection<Zust> zusts = this.zustService.findAllByAdminIdWithoutNewspaper(admin.getId());

		this.zustService.addZust(newspaperId, zustId);
		result = new ModelAndView("redirect:/newspaper/display.do?newspaperId=" + newspaperId);
		result.addObject(zusts);

		return result;
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
		final Collection<Newspaper> newspapers = this.newspaperService.findAll();

		res.addObject("zust", zust);
		res.addObject("newspapers", newspapers);
		res.addObject("message", message);

		return res;
	}
}
