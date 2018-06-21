package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

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
import forms.ArticleForm;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class NewspaperServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private NewspaperService newspaperService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private SubscriptionNewspaperService subscriptionService;

	@Autowired
	private AgentService agentService;

	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 4.2: List the newspapers that are published and browse their
	 * articles.
	 */

	@Test
	public void driverList() {
		final Object testingListData[][] = {

		// Casos positivos
		{ null, null } };

		for (int i = 0; i < testingListData.length; i++)
			this.templateList((String) testingListData[i][0],
					(Class<?>) testingListData[i][1]);

	}

	protected void templateList(String authenticate, Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			Collection<Newspaper> newspapers = newspaperService
					.findAvalibleNewspapers();
			if (!newspapers.isEmpty()) {
				Newspaper newspaper = newspaperService.findOne(newspapers
						.iterator().next().getId());
				if (!newspaper.getArticles().isEmpty()) {
					articleService.findOne(newspaper.getArticles().iterator()
							.next().getId());
				}
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 4.5: Search for a published newspaper using a single keyword
	 * that must appear somewhere in its title or its description.
	 */

	@Test
	public void driverSearch() {
		final Object testingSearchData[][] = {

				// Casos positivos
				{ null, "Newspaper", null }, { null, "", null } };

		for (int i = 0; i < testingSearchData.length; i++)
			this.templateSearch((String) testingSearchData[i][0],
					(String) testingSearchData[i][1],
					(Class<?>) testingSearchData[i][2]);

	}

	protected void templateSearch(String authenticate, String keyword,
			Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			Collection<Newspaper> newspapers = newspaperService
					.findPerKeyword(keyword);
			if (!keyword.equals("")) {
				for (Newspaper newspaper : newspapers) {
					Assert.isTrue(newspaper.getTitle().contains(keyword)
							|| newspaper.getDescription().contains(keyword));
				}
			} else {
				Assert.isTrue(newspapers.size() == newspaperService
						.findAvalibleNewspapers().size());
			}

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 6.1: Create a newspaper. A user who has created a newspaper
	 * is commonly referred to as a publisher.
	 */

	@Test
	public void driverCreateAndSaveNewspaper() {
		final Object testingEditData[][] = {

				// Casos positivos
				{ "user1", "titulo", "descripción", "18/10/2019",
						"https://iconfinder.com/data.png", false, null },
				// Casos negativos
				// Se intenta crear un newspaper sin registrar
				{ null, "titulo", "descripción", "18/10/2019",
						"https://iconfinder.com/data.png", false,
						NullPointerException.class },
				// Se intenta crear un newspaper con admin
				{ "admin", "titulo", "descripción", "18/10/2019",
						"https://iconfinder.com/data.png", false,
						NullPointerException.class }, };

		for (int i = 0; i < testingEditData.length; i++)
			this.templateCreateAndSaveNewspaper((String) testingEditData[i][0],
					(String) testingEditData[i][1],
					(String) testingEditData[i][2],
					(String) testingEditData[i][3],
					(String) testingEditData[i][4],
					(boolean) testingEditData[i][5],
					(Class<?>) testingEditData[i][6]);
	}

	private void templateCreateAndSaveNewspaper(String userName, String title,
			String description, String publicationDate, String picture,
			boolean isPrivate, Class<?> expected) {

		Class<?> caught;
		caught = null;
		Newspaper newspaper;

		Date date = new Date();

		SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = pattern.parse(publicationDate);
		} catch (ParseException e) {
			e.getClass();
		}

		try {
			super.authenticate(userName);
			newspaper = this.newspaperService.create();
			newspaper.setTitle(title);
			newspaper.setDescription(description);
			newspaper.setPublicationDate(date);
			newspaper.setPicture(picture);
			newspaper.setIsPrivate(isPrivate);
			this.newspaperService.save(newspaper);
			this.newspaperService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

	/*
	 * Caso de uso 6.2: Publish a newspaper that he or she's created. Note that
	 * no newspaper can be published until each of the articles of which it is
	 * composed is saved in final mode.
	 * 
	 * NOTA: Entendimos que los periódicos se publicaban automáticamente cuando
	 * su fecha de publicación es pasado y cuando todos artículos eran salvados
	 * en como finales. Si la fecha de publicación es pasada y quedan articulos
	 * no finales, este periódico no formará parte del conjunto de periódicos
	 * disponibles hasta que el escritos del artículo salve este como final. Le
	 * comentamos nuestro planteamiento al profesor Carlos Muller y le parecio
	 * bien esta implementación.
	 */

	@Test
	public void driverPublish() {
		final Object testingPublishData[][] = {

				// Casos positivos
				{ "user2", "article2", null }, { "user3", "article7", null } };

		for (int i = 0; i < testingPublishData.length; i++)
			this.templatePublish((String) testingPublishData[i][0],
					(String) testingPublishData[i][1],
					(Class<?>) testingPublishData[i][2]);
	}

	private void templatePublish(String userName, String articleBean,
			Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			int articleId = super.getEntityId(articleBean);
			Collection<Newspaper> newspapers = newspaperService
					.findAvalibleNewspapers();
			super.authenticate(userName);
			Article article = this.articleService.findOneToEdit(articleId);
			Assert.isTrue(!newspapers.contains(article.getNewspaper()));
			ArticleForm articleForm = articleService.construct(article);
			articleForm.setIsFinal(true);
			Article article2 = articleService.reconstruct(articleForm, null);
			articleService.save(article2);
			articleService.flush();
			Collection<Newspaper> newspapers2 = newspaperService
					.findAvalibleNewspapers();
			Assert.isTrue(newspapers2.contains(article2.getNewspaper()));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			articleService.flush();
		}
		this.checkExceptions(expected, caught);

	}

	/*
	 * Caso de uso 7.2: Remove a newspaper that he or she thinks is
	 * inappropriate. Removing a newspaper implies removing all of the articles
	 * of which it is composed.
	 */

	@Test
	public void driverDelete() {
		final Object testingDeleteData[][] = {

				// Casos positivos
				{ "admin", "newspaper1", null },

				// Cassos negativos
				{ "user1", "newspaper1", IllegalArgumentException.class }, /*
																			 * Solamente
																			 * el
																			 * admin
																			 * puede
																			 * borrar
																			 * los
																			 * periódicos
																			 */
				{ "admin", "newspaperTest", NumberFormatException.class },/*
																		 * No se
																		 * puede
																		 * eliminar
																		 * un
																		 * periódico
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

	protected void templateDelete(String authenticate, String newspaperBean,
			Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			int newspaperId = super.getEntityId(newspaperBean);
			super.authenticate(authenticate);
			Newspaper newspaper = newspaperService.findOne(newspaperId);
			newspaperService.delete(newspaper);
			newspaperService.flush();
			Assert.isTrue(!articleService.findAll().containsAll(
					newspaper.getArticles()));
			Assert.isTrue(!newspaper.getPublisher().getNewspapers()
					.contains(newspaper));
			Assert.isTrue(!subscriptionService.findAll().containsAll(
					newspaper.getSubscriptionsNewspaper()));
			Assert.isTrue(!newspaperService.findAll().contains(newspaper));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			articleService.flush();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 4.3: List the newspapers in which they have placed an
	 * advertisement.
	 */

	@Test
	public void driverListWithAdvertisement() {
		final Object testingListWithAdvertisementData[][] = {

				// Casos positivos
				{ "agent1", null },
				// Casos negativos
				{ null, NullPointerException.class }, /*
													 * Un anonimo no puede
													 * acceder a la vista
													 */
				{ "user1", NullPointerException.class } /*
														 * Un usuario no puede
														 * acceder a la vista
														 */
		};

		for (int i = 0; i < testingListWithAdvertisementData.length; i++)
			this.templateListWithAdvertisement(
					(String) testingListWithAdvertisementData[i][0],
					(Class<?>) testingListWithAdvertisementData[i][1]);

	}

	protected void templateListWithAdvertisement(String authenticate,
			Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			Collection<Newspaper> newspapers = newspaperService
					.findByAdvertisementAgentId(agentService.findByPrincipal()
							.getId());
			if (!newspapers.isEmpty()) {
				newspaperService.findOne(newspapers.iterator().next().getId());
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 4.4: List the newspapers in which they have not placed any
	 * advertisements.
	 */

	@Test
	public void driverListWithoutAdvertisement() {
		final Object testingListWithoutAdvertisementData[][] = {

				// Casos positivos
				{ "agent1", null },
				// Casos negativos
				{ null, NullPointerException.class }, /*
													 * Un anonimo no puede
													 * acceder a la vista
													 */
				{ "user1", NullPointerException.class } /*
														 * Un usuario no puede
														 * acceder a la vista
														 */
		};

		for (int i = 0; i < testingListWithoutAdvertisementData.length; i++)
			this.templateListWithoutAdvertisement(
					(String) testingListWithoutAdvertisementData[i][0],
					(Class<?>) testingListWithoutAdvertisementData[i][1]);

	}

	protected void templateListWithoutAdvertisement(String authenticate,
			Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			Collection<Newspaper> newspapers = newspaperService
					.findByNotAdvertisementAgentId(agentService.findByPrincipal()
							.getId());
			if (!newspapers.isEmpty()) {
				newspaperService.findOne(newspapers.iterator().next().getId());
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
