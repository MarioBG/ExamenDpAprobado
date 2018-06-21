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
import domain.Volume;
import forms.VolumeForm;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class VolumerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private VolumeService volumeService;

	@Autowired
	private NewspaperService newspaperService;

	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 8.1: List the volumes in the system and browse their
	 * newspapers as long as they are authorised (for instance, a private
	 * newspaper cannot be fully displayed to unauthenticated actors).
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
			Collection<Volume> volumes = volumeService.findAll();
			if (!volumes.isEmpty()) {
				newspaperService.findOne(volumes.iterator().next().getId());
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 10.1: Create a volume with as many published newspapers as he
	 * or she wishes. Note that the newspapers in a volume can be added or
	 * removed at any time. The same newspaper may be used to create different
	 * volumes.
	 */

	@Test
	public void driverCreate() {
		final Object testingCreateData[][] = {

				// Casos positivos
				{ "user1", "newspaper1", null },
				// Casos negativos
				{ null, "newspaper2", IllegalArgumentException.class },/*
																		 * No se
																		 * puede
																		 * añadir
																		 * un
																		 * periódico
																		 * que
																		 * no
																		 * esté
																		 * disponible
																		 */
				{ "user2", "newspaperTest", NumberFormatException.class }, /*
																			 * No
																			 * se
																			 * puede
																			 * añadir
																			 * o
																			 * eliminar
																			 * un
																			 * periódico
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
			int newspaperId = getEntityId(newspaperBeanName);
			super.authenticate(authenticate);
			Volume volume = volumeService.create();
			VolumeForm volumeForm = volumeService.construct(volume);
			volumeForm.setTitle("Volume title");
			volumeForm.setDescription("Volume description");
			volumeForm.setYear("2018");
			Volume volume2 = volumeService.reconstruct(volumeForm, null);
			Volume volume3 = volumeService.save(volume2);
			volumeService.addNewspaper(volume3.getId(), newspaperId);
			Assert.isTrue(volume3.getNewspapers().contains(
					newspaperService.findOne(newspaperId)));
			Assert.isTrue(newspaperService.findOne(newspaperId).getVolumes()
					.contains(volume3));
			volumeService.removeNewspaper(volume3.getId(), newspaperId);
			Assert.isTrue(!volume3.getNewspapers().contains(
					newspaperService.findOne(newspaperId)));
			Assert.isTrue(!newspaperService.findOne(newspaperId).getVolumes()
					.contains(volume3));
			super.unauthenticate();
			volumeService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			volumeService.flush();
		}

		this.checkExceptions(expected, caught);
	}

}
