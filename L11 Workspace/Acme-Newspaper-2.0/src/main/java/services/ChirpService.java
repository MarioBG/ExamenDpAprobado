
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import domain.Chirp;
import domain.Configuration;
import domain.User;

@Service
@Transactional
public class ChirpService {

	// Managed repository

	@Autowired
	private ChirpRepository	chirpRepository;

	// Supporting services

	@Autowired
	private UserService		userService;
	
	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private AdminService adminService;

	// Constructors

	public ChirpService() {
		super();
	}

	// Simple CRUD methods

	public Chirp create() {
		
		Assert.notNull(userService.findByPrincipal());
		
		final Chirp res = new Chirp();

		final Date publicationMoment;
		final User user;

		user = this.userService.findByPrincipal();
		publicationMoment = new Date(System.currentTimeMillis() - 1);

		res.setPublicationMoment(publicationMoment);
		res.setUser(user);

		return res;
	}

	public Collection<Chirp> findAll() {
		Collection<Chirp> res;
		res = this.chirpRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Chirp findOne(final int chirpId) {
		Assert.isTrue(chirpId != 0);
		Chirp res;
		res = this.chirpRepository.findOne(chirpId);
		Assert.notNull(res);
		return res;
	}

	public Chirp save(final Chirp chirp) {
		Assert.notNull(chirp);
		Chirp res;

		final Date publicationMoment;
		final User user;
		Collection<Chirp> chirps;

		user = this.userService.findByPrincipal();
		publicationMoment = new Date(System.currentTimeMillis() - 1);
		chirps = user.getChirps();

		chirp.setPublicationMoment(publicationMoment);
		chirp.setUser(user);
		res = this.chirpRepository.save(chirp);
		chirps.add(res);
		user.setChirps(chirps);

		return res;
	}

	public void delete(final Chirp chirp) {
		final User u = chirp.getUser();
		final Collection<Chirp> chirps = u.getChirps();
		chirps.remove(chirp);
		u.setChirps(chirps);

		this.chirpRepository.delete(chirp);
	}

	// Other business methods

	public Collection<Chirp> findChirpsByFollowedFromUser(final User u) {
		return this.chirpRepository.findChirpsByFollowedFromUserId(u.getId());
	}

	public Collection<Chirp> findChirpsByUser(final User u) {
		return this.chirpRepository.findChirpsByUserId(u.getId());
	}
	
	public Collection<Chirp> chirpContainTabooWord(){
		Assert.notNull(adminService.findByPrincipal());
		Collection<Chirp> res = new ArrayList<>();
		Configuration configuration;
		Collection<String> tabooWords = new ArrayList<>();
		Collection<Chirp> allChirp = new ArrayList<>();
		
		configuration = this.configurationService.findAll().iterator().next();
		tabooWords = Arrays.asList(configuration.getTabooWords().split(","));
		allChirp = this.findAll();
		
		for(Chirp chirp: allChirp){
			for(String tabooWord: tabooWords){
				String lowTabooWord = tabooWord.toLowerCase();
				if(chirp.getTitle().toLowerCase().contains(lowTabooWord.trim()) || chirp.getDescription().toLowerCase().contains(lowTabooWord.trim())){
					if(!res.contains(chirp)){
						res.add(chirp);
					}
				}
			}
		}
		return res;
	}

	public void flush() {
		this.chirpRepository.flush();
	}

}
