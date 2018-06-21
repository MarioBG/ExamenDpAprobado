package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FollowUpRepository;
import domain.Article;
import domain.FollowUp;
import domain.User;
import forms.FollowUpForm;

@Service
@Transactional
public class FollowUpService {

	// Managed repository
	@Autowired
	private FollowUpRepository followUpRepository;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserService userService;

	@Autowired
	private Validator validator;

	// Constructors
	public FollowUpService() {
		super();
	}

	// Simple CRUD methods

	public FollowUp create(int articleId) {

		Article article = articleService.findOne(articleId);

		Assert.isTrue(article.getWriter().equals(userService.findByPrincipal()));
		Assert.isTrue(article.getIsFinal() == true);
		Assert.isTrue(article.getNewspaper().getPublicationDate().before(new Date()));

		FollowUp res = new FollowUp();

		Date moment = new Date(System.currentTimeMillis() - 1000);

		res.setPublicationMoment(moment);
		res.setArticle(article);

		return res;

	}

	public Collection<FollowUp> findAll() {
		Collection<FollowUp> res;
		res = this.followUpRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public FollowUp findOne(final int followUpid) {
		Assert.isTrue(followUpid != 0);
		FollowUp res;
		res = this.followUpRepository.findOne(followUpid);
		Assert.notNull(res);

		return res;
	}

	public FollowUp save(FollowUp followUp) {

		Assert.notNull(followUp);
		Assert.isTrue(followUp.getArticle().getWriter().equals(userService.findByPrincipal()));
		Assert.isTrue(followUp.getArticle().getIsFinal() == true);
		Assert.isTrue(followUp.getArticle().getNewspaper().getPublicationDate().before(new Date()));

		followUp.setPublicationMoment(new Date(
				System.currentTimeMillis() - 1000));

		FollowUp res;
		res = this.followUpRepository.save(followUp);
		
		res.getArticle().getFollowUps().add(res);

		Assert.notNull(res);
		return res;

	}

	public void delete(FollowUp followUp) {

		this.adminService.checkAuthority();
		Assert.notNull(followUp);
		Assert.isTrue(followUp.getId() != 0);
		Assert.isTrue(this.followUpRepository.exists(followUp.getId()));

		this.followUpRepository.delete(followUp);
	}

	// Other busines methods

	public Collection<FollowUp> findFollowUpsByArticle(int articleId) {
		
		Collection<FollowUp> res = this.followUpRepository
				.findFollowUpsByArticle(articleId);
		return res;
	}
	
	public Collection<FollowUp> findFollowUpsByUserId(int userId) {
		
		Collection<FollowUp> res = this.followUpRepository
				.findFollowUpsByUserId(userId);
		return res;
	}
	
	public Collection<FollowUp> findByPrincipal() {
	
		User principal = userService.findByPrincipal();
		Collection<FollowUp> res = this.followUpRepository
				.findFollowUpsByUserId(principal.getId());
		return res;
	}

	public void flush() {
		this.followUpRepository.flush();
	}

	public FollowUpForm construct(final FollowUp followUp) {

		Assert.notNull(followUp);

		FollowUpForm followUpForm;

		followUpForm = new FollowUpForm();

		followUpForm.setId(followUp.getId());
		followUpForm.setArticleId(followUp.getArticle().getId());
		followUpForm.setTitle(followUp.getTitle());
		followUpForm.setPublicationMoment(followUp.getPublicationMoment());
		followUpForm.setSummary(followUp.getSummary());
		followUpForm.setText(followUp.getText());
		followUpForm.setPictures(followUp.getPictures());

		return followUpForm;
	}

	public FollowUp reconstruct(final FollowUpForm followUpForm,
			final BindingResult binding) {

		Assert.notNull(followUpForm);

		FollowUp followUp;

		if (followUpForm.getId() != 0)
			followUp = this.findOne(followUpForm.getId());
		else
			followUp = this.create(followUpForm.getArticleId());

		followUp.setTitle(followUpForm.getTitle());
		followUp.setPublicationMoment(followUpForm.getPublicationMoment());
		followUp.setSummary(followUpForm.getSummary());
		followUp.setText(followUpForm.getText());
		followUp.setPictures(followUpForm.getPictures());

		if (binding != null)
			this.validator.validate(followUp, binding);

		return followUp;
	}

}
