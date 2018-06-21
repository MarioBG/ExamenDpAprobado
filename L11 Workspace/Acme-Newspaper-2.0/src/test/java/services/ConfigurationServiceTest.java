package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Article;
import domain.Chirp;
import domain.Configuration;
import domain.Newspaper;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private NewspaperService newspaperService;
	
	@Autowired
	private ChirpService chirpService;

	// Tests ------------------------------------------------------------------

	@Test
	public void driverEditTabooWords() {
		final Object testingEditData[][] = {

				// Casos positivos
				{ "admin", "test1,test2,test3", null },
				// Casos negativos
				{ null, "test1,test2,test3", NullPointerException.class }, 
				{ "user1", "test1,test2,test3", IllegalArgumentException.class }, 
		};

		for (int i = 0; i < testingEditData.length; i++)
			this.templateEdit((String) testingEditData[i][0],
					(String) testingEditData[i][1],
					(Class<?>) testingEditData[i][2]);
	}

	
	private void templateEdit(String userName, String words, Class<?> expected) {

		Class<?> caught;
		caught = null;
		Configuration configuration;
		String tabooWords;
		
		try{
			super.authenticate(userName);
			configuration = this.configurationService.findAll().iterator().next();
			tabooWords = words;
			configuration.setTabooWords(tabooWords);
			this.configurationService.save(configuration);
			this.configurationService.flush();
			this.unauthenticate();
		}catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void driverListTabooWords() {
		final Object testingEditData[][] = {

				// Casos positivos
				{ "admin", null },
				// Casos negativos
				{ null, NullPointerException.class }, 
				{ "user1", IllegalArgumentException.class }, 
		};

		for (int i = 0; i < testingEditData.length; i++)
			this.templateListTabooWords((String) testingEditData[i][0],
					(Class<?>) testingEditData[i][1]);
	}
	
	private void templateListTabooWords(String userName, Class<?> expected) {

		Class<?> caught;
		caught = null;
		Configuration configuration;
		
		@SuppressWarnings("unused")
		Collection<String> tabooWords = new ArrayList<>();
		
		try{
			super.authenticate(userName);
			configuration = this.configurationService.findAllByAdmin().iterator().next();
			tabooWords = Arrays.asList(configuration.getTabooWords().split(","));
			this.unauthenticate();
		}catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void driverListArticlesWithTabooWords() {
		final Object testingEditData[][] = {

				// Casos positivos
				{ "admin", null },
				// Casos negativos
				{ null, NullPointerException.class }, 
				{ "user1", IllegalArgumentException.class }, 
		};

		for (int i = 0; i < testingEditData.length; i++)
			this.templateListArticleswithTabooWords((String) testingEditData[i][0],
					(Class<?>) testingEditData[i][1]);
	}
	
	private void templateListArticleswithTabooWords(String userName, Class<?> expected) {

		Class<?> caught;
		caught = null;
		
		@SuppressWarnings("unused")
		Collection<Article> articles = new ArrayList<>();
		
		try{
			super.authenticate(userName);
			articles = this.articleService.articleContainTabooWord();
			this.unauthenticate();
		}catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void driverListNewspapersWithTabooWords() {
		final Object testingEditData[][] = {

				// Casos positivos
				{ "admin", null },
				// Casos negativos
				{ null, NullPointerException.class }, 
				{ "user1", IllegalArgumentException.class }, 
		};

		for (int i = 0; i < testingEditData.length; i++)
			this.templateListNewspapersWithTabooWords((String) testingEditData[i][0],
					(Class<?>) testingEditData[i][1]);
	}
	
	private void templateListNewspapersWithTabooWords(String userName, Class<?> expected) {

		Class<?> caught;
		caught = null;
		
		@SuppressWarnings("unused")
		Collection<Newspaper> newspapers = new ArrayList<>();
		
		try{
			super.authenticate(userName);
			newspapers = this.newspaperService.newspaperContainTabooWord();
			this.unauthenticate();
		}catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void driverListChirpWithTabooWords() {
		final Object testingEditData[][] = {

				// Casos positivos
				{ "admin", null },
				// Casos negativos
				{ null, NullPointerException.class }, 
				{ "user1", IllegalArgumentException.class }, 
		};

		for (int i = 0; i < testingEditData.length; i++)
			this.templateListChirpWithTabooWords((String) testingEditData[i][0],
					(Class<?>) testingEditData[i][1]);
	}
	
	private void templateListChirpWithTabooWords(String userName, Class<?> expected) {

		Class<?> caught;
		caught = null;
		
		@SuppressWarnings("unused")
		Collection<Chirp> chirps = new ArrayList<>();
		
		try{
			super.authenticate(userName);
			chirps = this.chirpService.chirpContainTabooWord();
			this.unauthenticate();
		}catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
}
