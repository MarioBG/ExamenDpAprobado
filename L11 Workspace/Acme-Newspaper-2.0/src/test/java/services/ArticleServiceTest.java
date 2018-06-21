package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Article;
import domain.Newspaper;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ArticleServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ArticleService articleService;

	@Autowired
	private FollowUpService followUpService;

	@Autowired
	private NewspaperService newspaperService;

	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 6.3: Write an article and attach it to any newspaper that has
	 * not been published, yet. Note that articles may be saved in draft mode,
	 * which allows to modify them later, or final model, which freezes them
	 * forever.
	 */

	@Test
	public void driverCreateAndEdit() {
		final Object testingCreateAndEditData[][] = {

				// Casos positivos
				{ "user1", "newspaper3", false, null },
				{ "user2", "newspaper3", false, null },
				{ "user3", "newspaper3", false, null },
				{ "user1", "newspaper7", false, null },
				{ "user2", "newspaper7", false, null },
				// Casos negativos
				{ null, "newspaper3", false, IllegalArgumentException.class }, /*
																				 * Un
																				 * anonimo
																				 * no
																				 * puede
																				 * crear
																				 * un
																				 * articulo
																				 */
				{ "user1", "newspaper1", false, IllegalArgumentException.class }, /*
																				 * No
																				 * se
																				 * puede
																				 * crear
																				 * un
																				 * artículo
																				 * para
																				 * un
																				 * periódico
																				 * pasado
																				 */
				{ "user2", "newspaperTest", false, NumberFormatException.class }, /*
																				 * No
																				 * se
																				 * puede
																				 * crear
																				 * un
																				 * artículo
																				 * para
																				 * un
																				 * pediódico
																				 * que
																				 * no
																				 * existe
																				 */
				{ "user3", "newspaper3", true, IllegalArgumentException.class }, /*
																				 * No
																				 * se
																				 * puede
																				 * editar
																				 * un
																				 * artículo
																				 * final
																				 */
				{ "admin", "newspaper7", false, IllegalArgumentException.class }, /*
																				 * Un
																				 * admin
																				 * no
																				 * puede
																				 * crear
																				 * un
																				 * artículo
																				 */
		};

		for (int i = 0; i < testingCreateAndEditData.length; i++)
			this.templateCreateAndEdit((String) testingCreateAndEditData[i][0],
					(String) testingCreateAndEditData[i][1],
					(boolean) testingCreateAndEditData[i][2],
					(Class<?>) testingCreateAndEditData[i][3]);

	}

	protected void templateCreateAndEdit(String authenticate,
			String newspaperBeanName, boolean isFalse, Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			int newspaperId = super.getEntityId(newspaperBeanName);
			super.authenticate(authenticate);
			Article article = articleService.create(newspaperId);
			article.setTitle("Article title");
			article.setSummary("Article summary");
			article.setBody("Article body");
			article.setPictures("https://cdn1.iconfinder.com/data/icons/social-media-3/512/615556-Pencil_Document-256.png\r\nhttps://cdn1.iconfinder.com/data/icons/social-media-3/512/615556-Pencil_Document-256.png");
			article.setIsFinal(isFalse);
			Article saved = articleService.save(article);
			Article article2 = articleService.findOneToEdit(saved.getId());
			article2.setTitle("Article title 2");
			articleService.save(article2);
			articleService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			articleService.flush();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 4.4: Search for a published article using a single key word
	 * that must appear somewhere in its title, summary, or body.
	 */

	@Test
	public void driverSearch() {
		final Object testingSearchData[][] = {

				// Casos positivos
				{ null, "newspaper6", "Coche", null },
				{ null, "newspaper6", "", null } };

		for (int i = 0; i < testingSearchData.length; i++)
			this.templateSearch((String) testingSearchData[i][0],
					(String) testingSearchData[i][1],
					(String) testingSearchData[i][2],
					(Class<?>) testingSearchData[i][3]);

	}

	protected void templateSearch(String authenticate, String newspaperBean,
			String keyword, Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			int newspaperId = super.getEntityId(newspaperBean);
			super.authenticate(authenticate);
			Newspaper newspaper = newspaperService.findOne(newspaperId);
			Collection<Article> articles = articleService.findPerKeyword(
					keyword, newspaperId);
			if (!keyword.equals("")) {
				for (Article article : articles) {
					Assert.isTrue(article.getTitle().contains(keyword)
							|| article.getSummary().contains(keyword)
							|| article.getBody().contains(keyword));
				}
			} else {
				Assert.isTrue(articles.size() == newspaper.getArticles().size());
			}

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 7.1: Remove an article that he or she thinks is
	 * inappropriate.
	 */

	@Test
	public void driverDelete() {
		final Object testingDeleteData[][] = {

				// Casos positivos
				{ "admin", "article1", null },

				// Cassos negativos
				{ "user1", "article1", IllegalArgumentException.class }, /*
																		 * Solamente
																		 * el
																		 * admin
																		 * puede
																		 * borrar
																		 * los
																		 * artículos
																		 */
				{ "admin", "articleTest", NumberFormatException.class },/*
																		 * No se
																		 * puede
																		 * eliminar
																		 * un
																		 * articulo
																		 * que
																		 * no
																		 * existe
																		 */
		};

		for (int i = 0; i < testingDeleteData.length; i++)
			this.templateDelete((String) testingDeleteData[i][0],
					(String) testingDeleteData[i][1],
					(Class<?>) testingDeleteData[i][2]);

	}

	protected void templateDelete(String authenticate, String articleBean,
			Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			int articleId = super.getEntityId(articleBean);
			super.authenticate(authenticate);
			Article article = articleService.findOne(articleId);
			articleService.delete(article);
			articleService.flush();
			Assert.isTrue(!article.getNewspaper().getArticles()
					.contains(article));
			Assert.isTrue(!article.getWriter().getArticles().contains(article));
			Assert.isTrue(!followUpService.findAll().containsAll(
					article.getFollowUps()));
			Assert.isTrue(!articleService.findAll().contains(article));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			articleService.flush();
		}

		this.checkExceptions(expected, caught);
	}

}
