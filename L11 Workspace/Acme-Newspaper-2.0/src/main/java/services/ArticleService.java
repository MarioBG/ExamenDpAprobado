
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ArticleRepository;
import domain.Article;
import domain.Configuration;
import domain.FollowUp;
import domain.Newspaper;
import domain.User;
import forms.ArticleForm;

@Service
@Transactional
public class ArticleService {

	// Managed repository
	@Autowired
	private ArticleRepository		articleRepository;

	// Supporting services
	@Autowired
	private NewspaperService		newspaperService;

	@Autowired
	private UserService				userService;

	@Autowired
	private AdminService			adminService;

	@Autowired
	private FollowUpService			followUpService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors
	public ArticleService() {
		super();
	}

	// Simple CRUD methods
	public Article create(final int newspaperId) {

		Assert.notNull(this.userService.findByPrincipal());

		Article res = new Article();
		Newspaper newspaper = this.newspaperService.findOne(newspaperId);

		Assert.notNull(newspaper);

		Date date = new Date();

		Assert.isTrue(newspaper.getPublicationDate().after(date));

		Collection<FollowUp> followUps = new ArrayList<FollowUp>();
		User user = this.userService.findByPrincipal();

		res.setWriter(user);
		res.setFollowUps(followUps);
		res.setPublicationMoment(date);
		res.setNewspaper(newspaper);

		return res;
	}

	public Article save(final Article article) {

		Assert.notNull(article);
		if (article.getId() == 0) {
			article.setPublicationMoment(new Date(System.currentTimeMillis() - 1000));
		}

		this.checkPrincipal(article);
		Article res;

		res = this.articleRepository.save(article);
		if (article.getId() == 0) {
			res.getWriter().getArticles().add(res);
		}

		return res;
	}

	public Collection<Article> findAll() {
		Collection<Article> res = new ArrayList<Article>();

		res = this.articleRepository.findAll();

		Assert.notNull(res);
		return res;
	}

	public Article findOne(final int articleId) {
		Assert.isTrue(articleId != 0);
		Article res;

		res = this.articleRepository.findOne(articleId);

		return res;
	}

	public Article findOneToEdit(final int articleId) {
		Assert.isTrue(articleId != 0);
		Article res;

		res = this.articleRepository.findOne(articleId);

		this.checkPrincipal(res);
		Assert.isTrue(res.getIsFinal() == false);

		return res;
	}

	public void delete(final Article article) {
		this.adminService.checkAuthority();

		Assert.notNull(article);
		Assert.isTrue(article.getId() != 0);

		article.getWriter().getArticles().remove(article);
		for (FollowUp followUp : article.getFollowUps()) {
			this.followUpService.delete(followUp);
		}

		this.articleRepository.delete(article);
	}

	// Other busines methods

	public void flush() {
		this.articleRepository.flush();
	}

	private void checkPrincipal(Article article) {
		User principal = this.userService.findByPrincipal();
		Assert.isTrue(principal.equals(article.getWriter()));

	}

	public ArticleForm construct(Article article) {
		Assert.notNull(article);
		ArticleForm res = new ArticleForm();

		res.setId(article.getId());
		res.setTitle(article.getTitle());
		res.setSummary(article.getSummary());
		res.setBody(article.getBody());
		res.setPictures(article.getPictures());
		res.setIsFinal(article.getIsFinal());
		res.setNewspaperId(article.getNewspaper().getId());

		return res;
	}

	public Article reconstruct(ArticleForm articleForm, BindingResult binding) {
		Assert.notNull(articleForm);
		Newspaper newspaper = this.newspaperService.findOne(articleForm.getNewspaperId());
		Article res;

		if (articleForm.getId() != 0)
			res = this.findOne(articleForm.getId());
		else
			res = this.create(newspaper.getId());

		res.setTitle(articleForm.getTitle());
		res.setSummary(articleForm.getSummary());
		res.setBody(articleForm.getBody());
		res.setPictures(articleForm.getPictures());
		res.setIsFinal(articleForm.getIsFinal());
		res.setNewspaper(newspaper);

		if (binding != null)
			this.validator.validate(res, binding);

		return res;
	}

	public Collection<Article> findPerKeyword(String keyword, int newspaperId) {

		Assert.notNull(keyword);

		Collection<Article> articles = null;
		articles = new ArrayList<Article>();
		String aux = "Article";

		if (keyword.equals("")) {
			Newspaper newspaper = this.newspaperService.findOne(newspaperId);
			articles = newspaper.getArticles();
		} else {
			aux = keyword;
			articles = this.articleRepository.findPerKeyword(aux, newspaperId);
		}
		return articles;
	}

	public Collection<Article> findByWriterId(int writerId) {

		Collection<Article> articles = this.articleRepository.findByWriterId(writerId);

		return articles;
	}

	public Collection<Article> findByPrincipal() {

		User principal = this.userService.findByPrincipal();
		Collection<Article> articles = this.findByWriterId(principal.getId());

		return articles;
	}

	public Collection<Article> articleContainTabooWord() {
		Assert.notNull(this.adminService.findByPrincipal());
		Collection<Article> res = new ArrayList<>();
		Configuration configuration;
		Collection<String> tabooWords = new ArrayList<>();
		Collection<Article> allArticles = new ArrayList<>();

		configuration = this.configurationService.findAll().iterator().next();
		tabooWords = Arrays.asList(configuration.getTabooWords().split(","));
		allArticles = this.findAll();

		for (Article article : allArticles) {
			for (String tabooWord : tabooWords) {
				String lowTabooWord = tabooWord.toLowerCase();
				if (article.getTitle().toLowerCase().contains(lowTabooWord.trim()) || article.getSummary().toLowerCase().contains(lowTabooWord.trim()) || article.getBody().toLowerCase().contains(lowTabooWord.trim())) {
					if (!res.contains(article)) {
						res.add(article);
					}
				}
			}
		}
		return res;
	}

}
