package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Zust;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ZustServiceTest extends AbstractTest {

	// It must include one positive and one negative test case regarding an
	// admin-istrator who writes a XXXX, saves it in draft mode, then changes it,
	// and saves it in final mode.

	// System under test ------------------------------------------------------

	@Autowired
	private ZustService zustService;

	// Tests ------------------------------------------------------------------

	/*
	 * 1.1 Write a Zust, save it in draft mode , then changes it, and saves it in
	 * final mode.
	 */

	@Test
	public void driverZust() {
		final Object testingZustData[][] = {
				// Casos positivos
				{ "admin", "title1", "description", 2, "12/12/2018 12:12", false, null },
				// Casos negativos
				{ null, "title1", "description", 2, "12/12/2018 12:12", false, IllegalArgumentException.class },
				/*
				 * Tengo que estar logeado para poder crear un zust
				 */

		};

		for (int i = 0; i < testingZustData.length; i++)
			this.templateZust((String) testingZustData[i][0], (String) testingZustData[i][1],
					(String) testingZustData[i][2], (Integer) testingZustData[i][3], (String) testingZustData[i][4],
					(boolean) testingZustData[i][5], (Class<?>) testingZustData[i][6]);

	}

	protected void templateZust(String authenticate, String title, String description, Integer gauge, String moment,
			boolean isFinal, Class<?> expected) {

		Class<?> caught;
		caught = null;

		Date moment1 = null;
		try {
			moment1 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(moment);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			super.authenticate(authenticate);
			Zust zust = this.zustService.create();
			zust.setDescription(description);
			zust.setGauge(gauge);
			zust.setIsFinal(isFinal);
			zust.setTitle(title);
			zust.setMoment(moment1);
			zustService.save(zust);
			zust.setIsFinal(true);
			zustService.save(zust);
			zustService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			zustService.flush();
		}

		this.checkExceptions(expected, caught);
	}

}
