package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdminServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private AdminService adminService;

	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 7.3, 17.6, 5.3 y 11.1: Display a dashboard with the following
	 * information.
	 */

	@Test
	public void driverDashboard() {
		final Object testingDashboardData[][] = {

				// Casos positivos
				{ "admin", null },

				// Casos negativos
				{ null, IllegalArgumentException.class },
				{ "user1", IllegalArgumentException.class } /*
															 * Solamente el
															 * admin puede
															 * mostrar el
															 * dashboard.
															 */
		};

		for (int i = 0; i < testingDashboardData.length; i++)
			this.templateDashboard((String) testingDashboardData[i][0],
					(Class<?>) testingDashboardData[i][1]);

	}

	protected void templateDashboard(String authenticate, Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			adminService.avgSqtrUser();
			adminService.avgSqtrArticlesByWriter();
			adminService.avgSqtrArticlesByNewspaper();
			adminService.newspapersMoreAverage();
			adminService.newspapersFewerAverage();
			adminService.ratioUserCreatedNewspaper();
			adminService.ratioUserWrittenArticle();
			adminService.avgFollowupsPerArticle();
			adminService.avgNumberOfFollowUpsPerArticleAfter1Week();
			adminService.avgNumberOfFollowUpsPerArticleAfter2Week();
			adminService.avgStddevNumberOfChirpPerUser();
			adminService.ratioUsersMorePostedChirpsOfAveragePerUser();
			adminService.ratioNewspapersAds();
			adminService.ratioAdsTabooWords();
			adminService.avgNumberOfNewspapersPerVolume();
			adminService.ratioSubscriptionsVolumeVersusSubscriptionsNewspaper();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
