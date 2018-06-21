package services;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Admin;
import domain.Agent;
import domain.Customer;
import domain.Newspaper;
import domain.User;
import repositories.AdminRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdminService {

	// Managed repository
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private ActorService actorService;
	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private ConfigurationService configurationService;

	// Constructors
	public AdminService() {
		super();
	}

	// Simple CRUD methods

	public Admin create() {
		final Admin res = new Admin();

		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();

		authority.setAuthority(Authority.ADMIN);
		userAccount.addAuthority(authority);
		res.setUserAccount(userAccount);

		return res;

	}

	public Collection<Admin> findAll() {
		Collection<Admin> res;
		res = this.adminRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public Admin findOne(final int adminid) {
		Assert.isTrue(adminid != 0);
		Admin res;
		res = this.adminRepository.findOne(adminid);
		Assert.notNull(res);

		return res;
	}

	public Admin save(final Admin admin) {
		Admin res;

		if (admin.getId() == 0) {
			String pass = admin.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			admin.getUserAccount().setPassword(pass);
		}

		res = this.adminRepository.save(admin);
		return res;
	}

	// Ancillary methods

	public Admin findByPrincipal() {
		Admin res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		res = this.adminRepository.findAdminByUserAccountId(userAccount.getId());
		Assert.notNull(res);
		return res;
	}

	public boolean checkAuthority() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("ADMIN");
		if (authority.contains(res))
			return true;
		else
			return false;
	}

	public void flush() {
		this.adminRepository.flush();
	}

	public boolean checkIsSpam(String text) {
		Collection<String> spamWords;
		Boolean isSpam = false;
		Actor a = actorService.findByPrincipal();
		String type = actorService.getType(a.getUserAccount());

		if (type.equals("CUSTOMER")) {
			a = (Customer) a;
		} else if (type.equals("USER")) {
			a = (User) a;
		} else if (type.equals("AGENT")) {
			a = (Agent) a;
		}

		if (text == null) {
			return isSpam;
		} else {
			text = text.toLowerCase();
			spamWords = configurationService.getTabooWordsFromConfiguration();
			for (String spamword : spamWords) {
				if (text.contains(spamword)) {
					isSpam = true;
				}
			}
		}
		return isSpam;
	}

	// C-1
	public Object[] avgSqtrUser() {

		Assert.isTrue(checkAuthority());

		Object[] res;
		res = this.adminRepository.avgSqtrUser();
		return res;
	}

	// C-2
	public Object[] avgSqtrArticlesByWriter() {

		Assert.isTrue(checkAuthority());

		Object[] res;
		res = this.adminRepository.avgSqtrArticlesByWriter();
		return res;
	}

	// C-3
	public Object[] avgSqtrArticlesByNewspaper() {

		Assert.isTrue(checkAuthority());

		Object[] res;
		res = this.adminRepository.avgSqtrArticlesByNewspaper();
		return res;
	}

	// C-4
	public Collection<Newspaper> newspapersMoreAverage() {

		Assert.isTrue(checkAuthority());

		Collection<Newspaper> res = this.adminRepository.newspapersMoreAverage();
		return res;
	}

	// C-5
	public Collection<Newspaper> newspapersFewerAverage() {

		Assert.isTrue(checkAuthority());

		Collection<Newspaper> res = this.adminRepository.newspapersFewerAverage();
		return res;
	}

	// C-6
	public String ratioUserCreatedNewspaper() {

		Assert.isTrue(checkAuthority());

		String res;
		res = this.adminRepository.ratioUserCreatedNewspaper();
		return res;
	}

	// C-7
	public String ratioUserWrittenArticle() {

		Assert.isTrue(checkAuthority());

		String res;
		res = this.adminRepository.ratioUserWrittenArticle();
		return res;
	}

	// B-1
	public Double avgFollowupsPerArticle() {

		Assert.isTrue(checkAuthority());

		Double res;
		res = this.adminRepository.avgFollowupsPerArticle();
		return res;
	}

	// B-2
	public Double avgNumberOfFollowUpsPerArticleAfter1Week() {

		Assert.isTrue(checkAuthority());

		Double res;
		long oneWeek = TimeUnit.DAYS.toMillis(7);
		Date f = new Date(System.currentTimeMillis() - oneWeek);
		res = this.adminRepository.avgNumberOfFollowUpsPerArticleAfter1Week(f);
		return res;
	}

	// B-3
	public Double avgNumberOfFollowUpsPerArticleAfter2Week() {

		Assert.isTrue(checkAuthority());

		Double res;
		long twoWeeks = TimeUnit.DAYS.toMillis(7);
		Date f = new Date(System.currentTimeMillis() - twoWeeks);
		res = this.adminRepository.avgNumberOfFollowUpsPerArticleAfter2Week(f);
		return res;
	}

	// B-4
	public Object[] avgStddevNumberOfChirpPerUser() {

		Assert.isTrue(checkAuthority());

		Object[] res;
		res = this.adminRepository.avgStddevNumberOfChirpPerUser();
		return res;
	}

	// B-5
	public String ratioUsersMorePostedChirpsOfAveragePerUser() {

		Assert.isTrue(checkAuthority());

		String res;
		res = this.adminRepository.ratioUsersMorePostedChirpsOfAveragePerUser();
		return res;
	}

	// ACME-NEWSPAPER 2.0
	
	// C-1
	public Double ratioNewspapersAds() {
		return this.adminRepository.ratioNewspapersWithVsWithoutAdvertisements();
	}
	
	// C-2
	public Double ratioAdsTabooWords() {
		return (double) this.advertisementService.getAdvertisementsTabooWords().size() / (double) this.advertisementService.findAll().size();
	}

	// B-1
	public Double avgNumberOfNewspapersPerVolume() {

		Double result = adminRepository.avgNumberOfNewspapersPerVolume();
		return result;
	}

	// B-2
	
	public String ratioSubscriptionsVolumeVersusSubscriptionsNewspaper(){
		
		String result = adminRepository.ratioSubscriptionsVolumeVersusSubscriptionsNewspaper();
		return result;
	}

}
