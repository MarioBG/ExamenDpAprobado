package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import controllers.AbstractController;
import domain.Article;

@Controller
@RequestMapping("/article/admin")
public class ArticleAdminController extends AbstractController{
	
	// Services --------------------------------
	@Autowired
	private ArticleService articleService;
	
	
	// Constructors ----------------------------
	public ArticleAdminController() {
		super();
	}
	
	// List -----------------------------------
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=false) Integer newspaperId, 
							@RequestParam(required=false) String keyword){
		ModelAndView res;
		Collection<Article> articles;
		
		articles = this.articleService.findAll();
		
		res = new ModelAndView("article/list");
		res.addObject("article",articles);
		res.addObject("requestURI", "article/admin/list.do");
		
		return res;
	}
	
	// Delete ----------------------------------
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public ModelAndView delete(int articleId){
		Article article = this.articleService.findOne(articleId);
		this.articleService.delete(article);
		return new ModelAndView("redirect:list.do");
	}
}
