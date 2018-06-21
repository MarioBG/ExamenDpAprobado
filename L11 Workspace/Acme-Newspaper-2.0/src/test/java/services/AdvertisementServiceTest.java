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
import domain.Advertisement;
import forms.AdvertisementForm;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdvertisementServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private AdvertisementService advertisementService;

	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 4.2: Register an advertisement and place it in a newspaper.
	 */

	@Test
	public void driverCreate() {
		final Object testingCreateData[][] = {

				// Casos positivos
				{ "agent1", "newspaper3", null },
				// Casos negativos
				{ null, "newspaper1", IllegalArgumentException.class }, /*
																		 * Un
																		 * anonimo
																		 * no
																		 * puede
																		 * crear
																		 * un
																		 * anuncio
																		 */
				{ "agent2", "newspaperTest", NumberFormatException.class }, /*
																			 * No
																			 * se
																			 * puede
																			 * crear
																			 * un
																			 * anuncio
																			 * para
																			 * un
																			 * pediódico
																			 * que
																			 * no
																			 * existe
																			 */
		};

		for (int i = 0; i < testingCreateData.length; i++)
			this.templateCreate((String) testingCreateData[i][0],
					(String) testingCreateData[i][1],
					(Class<?>) testingCreateData[i][2]);

	}

	protected void templateCreate(String authenticate,
			String newspaperBeanName, Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			int newspaperId = super.getEntityId(newspaperBeanName);
			super.authenticate(authenticate);
			Advertisement advertisement = advertisementService
					.create(newspaperId);
			AdvertisementForm advertisementForm = advertisementService
					.construct(advertisement);
			advertisementForm.setTitle("Advertisement title");
			advertisementForm
					.setBanner("http://www.pngall.com/wp-content/uploads/2016/07/Advertising-PNG-Image-180x180.png");
			advertisementForm.setPage("http://www.pngall.com/advertising-png");
			advertisementForm.setHolder("Agent");
			advertisementForm.setBrand("Visa");
			advertisementForm.setNumber("4532851367456386");
			advertisementForm.setExpirationMonth(03);
			advertisementForm.setExpirationYear(2020);
			advertisementForm.setCvv(856);
			Advertisement advertisement2 = advertisementService.reconstruct(
					advertisementForm, null);
			advertisementService.save(advertisement2);
			advertisementService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			advertisementService.flush();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 5.1: List the advertisements that contain taboo words in its
	 * title.
	 */

	@Test
	public void driverListTabooAdvertisements() {
		final Object testingListTabooAdvertisementsData[][] = {

				// Casos positivos
				{ "admin", null },
				// Casos negativos
				{ null, NullPointerException.class }, /*
													 * Un anonimo no puede
													 * acceder a la vista
													 */
				{ "user1", IllegalArgumentException.class } /*
															 * Un usuario no
															 * puede acceder a
															 * la vista
															 */
		};

		for (int i = 0; i < testingListTabooAdvertisementsData.length; i++)
			this.templateListTabooAdvertisements(
					(String) testingListTabooAdvertisementsData[i][0],
					(Class<?>) testingListTabooAdvertisementsData[i][1]);

	}

	protected void templateListTabooAdvertisements(String authenticate,
			Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			Collection<Advertisement> advertisements = advertisementService
					.getAdvertisementsTabooWords();
			if (!advertisements.isEmpty()) {
				advertisementService.findOne(advertisements.iterator().next()
						.getId());
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 5.2: Remove an advertisement that he or she thinks is
	 * inappropriate.
	 */

	@Test
	public void driverDelete() {
		final Object testingDeleteData[][] = {

				// Casos positivos
				{ "admin", "advertisement1", null },

				// Cassos negativos
				{ "user1", "advertisement1", IllegalArgumentException.class }, /*
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

	protected void templateDelete(String authenticate,
			String advertisementBeanName, Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			int advertisementId = super.getEntityId(advertisementBeanName);
			super.authenticate(authenticate);
			Advertisement advertisement = advertisementService
					.findOne(advertisementId);
			advertisementService.delete(advertisement);
			advertisementService.flush();
			Assert.isTrue(!advertisementService.findAll().contains(
					advertisement));
			Assert.isTrue(!advertisement.getAgent().getAdvertisements()
					.contains(advertisement));
			Assert.isTrue(!advertisement.getNewspaper().getAdvertisements()
					.contains(advertisement));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			advertisementService.flush();
		}

		this.checkExceptions(expected, caught);
	}

}
