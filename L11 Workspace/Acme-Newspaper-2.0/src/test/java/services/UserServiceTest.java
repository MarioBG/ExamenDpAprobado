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
import domain.User;
import forms.UserForm;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private UserService userService;

	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 4.1: Register to the system as an user.
	 */

	@Test
	public void driverRegister() {
		final Object testingRegisterData[][] = {

				// Casos positivos
				{ null, true, null },
				// Casos negativos
				{ null, false, IllegalArgumentException.class }, /*
																 * No se puede
																 * registrar si
																 * no acepta los
																 * términos
																 */
				{ "user1", true, IllegalArgumentException.class }, /*
																	 * Un
																	 * usuario
																	 * autenticado
																	 * no puede
																	 * registrarse
																	 */
		};

		for (int i = 0; i < testingRegisterData.length; i++)
			this.templateRegister((String) testingRegisterData[i][0],
					(boolean) testingRegisterData[i][1],
					(Class<?>) testingRegisterData[i][2]);

	}

	protected void templateRegister(String authenticate, boolean acceptTerms,
			Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			User user = userService.create();
			UserForm userForm = userService.construct(user);
			userForm.setName("User name");
			userForm.setSurname("User surname");
			userForm.setAddress("User address");
			userForm.setEmail("email@user.com");
			userForm.setPhone("+1234");
			userForm.setUsername("User username");
			userForm.setPassword("User password");
			userForm.setRepeatPassword("User password");
			userForm.setTermsAndConditions(acceptTerms);
			User user2 = userService.reconstruct(userForm, null);
			userService.save(user2);
			userService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			userService.flush();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 4.3: List the users of the system and display their profiles,
	 * which must include their personal data and the list of articles that they
	 * have written as long as they are published in a newspaper.
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
			Collection<User> users = userService.findAll();
			if(!users.isEmpty()){
				userService.findOne(users.iterator().next().getId());
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 16.2: Follow or unfollow another user.
	 */
	
	@Test
	public void driverFollow(){
		final Object testingFollow[][] = {
				// Caso positivo
				{"user1", "user4", null},
				// Caso negativo
				{"user1", "user3316", NumberFormatException.class}, // No se puede seguir un usuario que no existe.
				{"user1", "user1", IllegalArgumentException.class}, // Un usuario no se puede seguir a sí mismo.
		};
		for (int i=0;i<testingFollow.length;i++){
			this.templateFollow((String)testingFollow[i][0],
					(String)testingFollow[i][1],
					(Class<?>)testingFollow[i][2]);
		}
	}

	private void templateFollow(String username1, String username2, Class<?> expected) {
		Class<?> caught;
		caught = null;
		
		try{
			super.authenticate(username1);
			User user2 = this.userService.findOne(super.getEntityId(username2));
			int followersLength = user2.getFollowers().size();
			this.userService.follow(user2.getId());
			Assert.isTrue(user2.getFollowers().size() == followersLength+1);
			super.unauthenticate();
		
		}catch (final Throwable oops) {
			caught = oops.getClass();
			this.userService.flush();
		}
		this.checkExceptions(expected, caught);
	}
	
	@Test
	public void driverUnfollow(){
		final Object testingFollow[][] = {
				// Caso positivo
				{"user1", "user2", null},
				// Caso negativo
				{"user1", "user4", IllegalArgumentException.class}, // No se puede dejar de seguir a un usuario que no sigue.
				{"user1", "user3316", NumberFormatException.class}, // No se puede dejar de seguir a un usuario que no existe.
		};
		for (int i=0;i<testingFollow.length;i++){
			this.templateUnfollow((String)testingFollow[i][0],
					(String)testingFollow[i][1],
					(Class<?>)testingFollow[i][2]);
		}
	}

	private void templateUnfollow(String username1, String username2, Class<?> expected) {
		Class<?> caught;
		caught = null;
		
		try{
			super.authenticate(username1);
			User user2 = this.userService.findOne(super.getEntityId(username2));
			int followersLength = user2.getFollowers().size();
			this.userService.unfollow(user2.getId());
			Assert.isTrue(user2.getFollowers().size() == followersLength-1);
			super.unauthenticate();
		
		}catch (final Throwable oops) {
			caught = oops.getClass();
			this.userService.flush();
		}
		this.checkExceptions(expected, caught);
	}
	
	
	
}
