package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Agent;
import forms.AgentForm;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AgentServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private AgentService agentService;

	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 3.1: Register to the system as an agent.
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
				{ "agent1", true, IllegalArgumentException.class }, /*
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
			Agent agent = agentService.create();
			AgentForm agentForm = agentService.construct(agent);
			agentForm.setName("Agent name");
			agentForm.setSurname("Agent surname");
			agentForm.setAddress("Agent address");
			agentForm.setEmail("email@agent.com");
			agentForm.setPhone("+1234");
			agentForm.setUsername("Agent username");
			agentForm.setPassword("Agent password");
			agentForm.setRepeatPassword("Agent password");
			agentForm.setTermsAndConditions(acceptTerms);
			Agent agent2 = agentService.reconstruct(agentForm, null);
			agentService.save(agent2);
			agentService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			agentService.flush();
		}

		this.checkExceptions(expected, caught);
	}

}
