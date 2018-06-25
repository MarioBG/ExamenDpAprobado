package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Admin;
import domain.Article;
import domain.Newspaper;
import domain.Zust;
import services.AdminService;
import services.ArticleService;
import services.CustomerService;
import services.NewspaperService;
import services.UserService;
import services.ZustService;

@Controller
@RequestMapping("/newspaper")
public class NewspaperController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private NewspaperService newspaperService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private UserService userService;

	@Autowired
	private ZustService zustService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private AdminService adminService;

	// Constructors --------------------------------------------------

	public NewspaperController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer volumeId) {

		Collection<Newspaper> newspapers;

		if (keyword != null && volumeId == null) {
			newspapers = newspaperService.findPerKeyword(keyword);
		} else if (keyword == null && volumeId != null) {
			newspapers = newspaperService.findByVolumeId(volumeId);
		} else if (keyword != null && volumeId != null) {
			newspapers = newspaperService.findByVolumeIdByKeyword(volumeId, keyword);
		} else {
			newspapers = newspaperService.findAvalibleNewspapers();
		}

		ModelAndView result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/list.do");

		return result;
	}

	// Display -------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int newspaperId, @RequestParam(required = false) String keyword) {

		Newspaper newspaper = newspaperService.findOne(newspaperId);
		Collection<Article> articles;
		boolean areSubscribe = false;
		Collection<Zust> myZusts = this.zustService.zustByNewspaperId(newspaperId);
		Collection<Zust> zusts;

		if (keyword != null) {
			articles = this.articleService.findPerKeyword(keyword, newspaperId);
		} else {
			articles = newspaper.getArticles();
		}

		if ((customerService.findByPrincipal() != null
				&& newspaperService.findSubscribedNewspapersByPrincipal().contains(newspaper))
				|| userService.findByPrincipal() != null) {
			areSubscribe = true;
		}
		ModelAndView result = new ModelAndView("newspaper/display");
		result.addObject("newspaper", newspaper);
		result.addObject("articles", articles);
		result.addObject("myZusts", myZusts);
		result.addObject("areSubscribe", areSubscribe);
		result.addObject("date", new Date());
		try {
			Admin admin = this.adminService.findByPrincipal();
			zusts = this.zustService.findAllByAdminIdWithoutNewspaper(admin.getId());
			result.addObject("zusts", zusts);

		} catch (Exception e) {
		}

		return result;

	}
}
