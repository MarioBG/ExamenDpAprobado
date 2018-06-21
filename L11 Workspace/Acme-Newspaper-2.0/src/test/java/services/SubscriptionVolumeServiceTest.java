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
import domain.Newspaper;
import domain.SubscriptionVolume;
import forms.SubscriptionVolumeForm;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SubscriptionVolumeServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private SubscriptionVolumeService subscriptionVolumeService;

	@Autowired
	private NewspaperService newspaperService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private VolumeService volumeService;

	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 9.1: Subscribe to a volume by providing a credit card. Note
	 * that subscribing to a volume implies subscribing automatically to all of
	 * the newspapers of which it is composed, including newspapers that might
	 * be published after the subscription takes place.
	 */

	@Test
	public void driverCreate() {
		final Object testingCreateData[][] = {

				// Casos positivos
				{ "customer1", "volume2", null },
				// Casos negativos
				{ null, "volume1", IllegalArgumentException.class }, /*
																	 * Un
																	 * anonimo
																	 * no puede
																	 * crear una
																	 * subscripción
																	 * a volumen
																	 */
				{ "customer2", "volumeTest", NumberFormatException.class }, /*
																			 * No
																			 * se
																			 * puede
																			 * crear
																			 * una
																			 * subscripción
																			 * para
																			 * un
																			 * volumen
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

	protected void templateCreate(String authenticate, String volumeBeanName,
			Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			int volumeId = super.getEntityId(volumeBeanName);
			super.authenticate(authenticate);
			SubscriptionVolume subscriptionVolume = subscriptionVolumeService
					.create(volumeId);
			SubscriptionVolumeForm subscriptionVolumeForm = subscriptionVolumeService
					.construct(subscriptionVolume);
			subscriptionVolumeForm.setHolder("Customer");
			subscriptionVolumeForm.setBrand("Visa");
			subscriptionVolumeForm.setNumber("4532851367456386");
			subscriptionVolumeForm.setExpirationMonth(03);
			subscriptionVolumeForm.setExpirationYear(2020);
			subscriptionVolumeForm.setCvv(856);
			SubscriptionVolume subscriptionVolume2 = subscriptionVolumeService
					.reconstruct(subscriptionVolumeForm, null);
			subscriptionVolumeService.save(subscriptionVolume2);
			subscriptionVolumeService.flush();
			Collection<Newspaper> newspapers = newspaperService
					.findNewspapersSubscribedVolumeByCustomerId(customerService
							.findByPrincipal().getId());
			Assert.isTrue(newspapers.containsAll(volumeService
					.findOne(volumeId).getNewspapers()));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			subscriptionVolumeService.flush();
		}

		this.checkExceptions(expected, caught);
	}

}
