package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Chirp;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChirpServiceTest extends AbstractTest {

	@Autowired
	private ChirpService chirpService;
	
	// Test ------------------------------------------
	
	@Test
	public void driver(){
		final Object testingCreate[][] ={
				// Casos positivos
				{"user1", "chirp title", "chirp description", null},
				// Casos negativos
				{"pepitoperez", "chirp title", "chirp description", IllegalArgumentException.class}, // Usuario no registrado
				{"admin", "chirp title", "chirp description", IllegalArgumentException.class}, // Usuario no autenticado
		};
		
		for(int i=0;i<testingCreate.length;i++){
			this.templateCreateAndEdit((String)testingCreate[i][0],
					(String)testingCreate[i][1],
					(String)testingCreate[i][2],
					(Class<?>)testingCreate[i][3]);
		}
	}

	private void templateCreateAndEdit(String username, String title,
			String description, Class<?> expected) {
		Class<?> caught;
		caught = null;
		
		Chirp chirp;
		
		try{
			super.authenticate(username);
			chirp = this.chirpService.create();
			chirp.setTitle(title);
			chirp.setDescription(description);
			
		}catch (final Throwable oops) {
			caught = oops.getClass();
			this.chirpService.flush();
		}
		this.checkExceptions(expected, caught);
		
	}
}
