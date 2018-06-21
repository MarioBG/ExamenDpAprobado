package controllers.admin;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.ChirpService;
import services.ConfigurationService;
import services.NewspaperService;
import controllers.AbstractController;
import domain.Article;
import domain.Chirp;
import domain.Configuration;
import domain.Newspaper;

@Controller
@RequestMapping("/configuration/admin")
public class ConfigurationAdminController extends AbstractController {

	// Services --------------------------------
	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private NewspaperService newspaperService;
	
	@Autowired
	private ChirpService chirpService;

	// Constructors ----------------------------
	public ConfigurationAdminController() {
		super();
	}

	// List -----------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView res;
		Configuration configuration;
		
		Collection<Article> articlesWhitTabooWords = new ArrayList<>();
		Collection<Newspaper> newspapersWhitTabooWords = new ArrayList<>();
		Collection<Chirp> chirpsWhitTabooWords = new ArrayList<>();
		
		articlesWhitTabooWords = this.articleService.articleContainTabooWord();
		newspapersWhitTabooWords = this.newspaperService.newspaperContainTabooWord();
		chirpsWhitTabooWords = this.chirpService.chirpContainTabooWord();
		
		configuration = this.configurationService.findAll().iterator().next();

		res = new ModelAndView("configuration/list");
		res.addObject("configuration", configuration);		
		res.addObject("articlesWhitTabooWords", articlesWhitTabooWords);
		res.addObject("newspapersWhitTabooWords", newspapersWhitTabooWords);
		res.addObject("chirpsWhitTabooWords", chirpsWhitTabooWords);

		return res;
	}
	
	// Edit -----------------------------------

	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView editTabooWords(){
		ModelAndView res;
		Configuration configuration;
		
		configuration = this.configurationService.findAll().iterator().next();
		
		res = this.createEditModelAndView(configuration);
		return res;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Configuration configuration, final BindingResult binding) {

		ModelAndView result;
		
		configuration.setTabooWords(configuration.getTabooWords().replace(" ", ""));
		
		if (binding.hasErrors())
			result = this.createEditModelAndView(configuration);
		else
			try {
				this.configurationService.save(configuration);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(configuration, "configuration.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView( final Configuration configuration) {

		ModelAndView result;

		result = this.createEditModelAndView(configuration, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(
			final Configuration configuration, final String messageCode) {

		ModelAndView result;
		
		

		result = new ModelAndView("configuration/edit");
		result.addObject("configuration", configuration);
		result.addObject("message", messageCode);

		return result;
	}
	

}
