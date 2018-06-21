
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdvertisementRepository;
import domain.Advertisement;
import domain.Agent;
import domain.CreditCard;
import domain.Newspaper;
import forms.AdvertisementForm;

@Service
@Transactional
public class AdvertisementService {

	// Managed repository
	@Autowired
	private AdvertisementRepository	advertisementRepository;

	// Supporting services
	@Autowired
	private NewspaperService		newspaperService;

	@Autowired
	private AgentService			agentService;

	@Autowired
	private AdminService			adminService;

	@Autowired
	private ConfigurationService	configService;

	@Autowired
	private Validator				validator;


	// Constructors
	public AdvertisementService() {
		super();
	}

	// Simple CRUD methods
	public Advertisement create(final int newspaperId) {

		Assert.notNull(this.agentService.findByPrincipal());

		final Advertisement res = new Advertisement();
		final CreditCard c = new CreditCard();
		final Newspaper newspaper = this.newspaperService.findOne(newspaperId);

		Assert.notNull(newspaper);

		final Agent agent = this.agentService.findByPrincipal();
		res.setAgent(agent);
		res.setCreditCard(c);
		res.setNewspaper(newspaper);
		return res;
	}

	public Advertisement save(final Advertisement advertisement) {
		Assert.notNull(advertisement);
		Assert.isTrue(this.checkExpiration(advertisement.getCreditCard()), "cardExpireError");
		Advertisement res;
		res = this.advertisementRepository.save(advertisement);

		return res;
	}

	public void delete(final Advertisement ad) {

		Assert.notNull(ad);
		Assert.notNull(this.adminService.findByPrincipal());

		final Newspaper newspaper = this.newspaperService.findOne(ad.getNewspaper().getId());
		final Collection<Advertisement> ads = newspaper.getAdvertisements();
		ads.remove(ad);
		newspaper.setAdvertisements(ads);

		//this.newspaperService.save(newspaper);
		this.advertisementRepository.delete(ad);

	}

	public Collection<Advertisement> findAll() {
		Collection<Advertisement> res = new ArrayList<Advertisement>();

		res = this.advertisementRepository.findAll();

		Assert.notNull(res);
		return res;
	}

	public Advertisement findOne(final int advertisementId) {
		Assert.isTrue(advertisementId != 0);
		Advertisement res;

		res = this.advertisementRepository.findOne(advertisementId);

		return res;
	}

	public Advertisement getRandomForNewspaper(final int newspaperId) {
		final Random rn = new Random();
		Advertisement ans = null;
		final Collection<Advertisement> ads = this.advertisementRepository.findRandomForNewspaper(newspaperId);
		if (ads.size() > 0)
			ans = (Advertisement) ads.toArray()[rn.nextInt(ads.size())];
		return ans;
	}

	public Collection<Advertisement> getAdvertisementsTabooWords() {

		Assert.notNull(this.adminService.findByPrincipal());

		String pattern = "^";
		for (final String tabooWord : this.configService.getTabooWordsFromConfiguration())
			pattern += ".*" + tabooWord + ".*" + "|";
		pattern = pattern.substring(0, pattern.length() - 1);
		pattern += "$";

		final List<Advertisement> ans = new ArrayList<Advertisement>();
		for (final Advertisement a : this.advertisementRepository.findAll())
			if (a.getTitle().matches(pattern))
				ans.add(a);
		return new HashSet<Advertisement>(ans);
	}

	//TODO Arreglar query de Lucene
	//	public Collection<Advertisement> getAdvertisementsTabooWords() {
	//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Acme-Newspaper-2.0");
	//		EntityManager mgr = factory.createEntityManager();
	//		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(mgr);
	//		mgr.getTransaction().begin();
	//		String pattern = "";
	//
	//		for (String tabooWord : this.configService.getTabooWordsFromConfiguration())
	//			pattern += tabooWord + "|";
	//
	//		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Advertisement.class).get();
	//		org.apache.lucene.search.Query luceneQuery = qb.keyword().onFields("title").ignoreFieldBridge().matching(pattern).createQuery();
	//		javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Advertisement.class);
	//		@SuppressWarnings("unchecked")
	//		List<Advertisement> res = jpaQuery.getResultList();
	//		Set<Advertisement> cc = new HashSet<>(res);
	//		mgr.getTransaction().commit();
	//
	//		mgr.close();
	//
	//		System.out.println(cc);
	//
	//		return cc;
	//	}

	public Advertisement findOneToEdit(final int sponsorshipId) {
		Advertisement result;
		result = this.advertisementRepository.findOne(sponsorshipId);
		this.checkPrincipal(result);
		return result;
	}

	public void checkPrincipal(final Advertisement s) {
		Agent agent;

		agent = this.agentService.findByPrincipal();

		Assert.isTrue(agent.getAdvertisements().contains(s));
	}

	public boolean checkExpiration(final CreditCard c) {
		Boolean res = true;

		if ((c.getExpirationYear() == LocalDate.now().getYear() && (c.getExpirationMonth() == LocalDate.now().getMonthOfYear() || c.getExpirationMonth() < LocalDate.now().getMonthOfYear())) || c.getExpirationYear() < LocalDate.now().getYear())
			res = false;

		return res;
	}

	public AdvertisementForm construct(final Advertisement advertisement) {

		Assert.notNull(advertisement);

		AdvertisementForm advertisementForm;

		advertisementForm = new AdvertisementForm();

		advertisementForm.setId(advertisement.getId());
		advertisementForm.setAgentId(advertisement.getAgent().getId());
		advertisementForm.setNewspaperId(advertisement.getNewspaper().getId());
		advertisementForm.setTitle(advertisement.getTitle());
		advertisementForm.setBanner(advertisement.getBanner());
		advertisementForm.setPage(advertisement.getPage());

		if (advertisement.getId() == 0) {
			advertisementForm.setHolder(null);
			advertisementForm.setBrand(null);
			advertisementForm.setNumber(null);
			advertisementForm.setExpirationMonth(null);
			advertisementForm.setExpirationYear(null);
			advertisementForm.setCvv(null);
		} else {
			advertisementForm.setHolder(advertisement.getCreditCard().getHolder());
			advertisementForm.setBrand(advertisement.getCreditCard().getBrand());
			advertisementForm.setNumber(advertisement.getCreditCard().getNumber());
			advertisementForm.setExpirationMonth(advertisement.getCreditCard().getExpirationMonth());
			advertisementForm.setExpirationYear(advertisement.getCreditCard().getExpirationYear());
			advertisementForm.setCvv(advertisement.getCreditCard().getCvv());
		}

		return advertisementForm;
	}

	public Advertisement reconstruct(final AdvertisementForm advertisementForm, final BindingResult binding) {

		Assert.notNull(advertisementForm);

		Advertisement advertisement;

		if (advertisementForm.getId() != 0)
			advertisement = this.findOne(advertisementForm.getId());
		else
			advertisement = this.create(advertisementForm.getNewspaperId());

		advertisement.setTitle(advertisementForm.getTitle());
		advertisement.setBanner(advertisementForm.getBanner());
		advertisement.setPage(advertisementForm.getPage());
		advertisement.getCreditCard().setHolder(advertisementForm.getHolder());
		advertisement.getCreditCard().setBrand(advertisementForm.getBrand());
		advertisement.getCreditCard().setNumber(advertisementForm.getNumber());
		advertisement.getCreditCard().setExpirationMonth(advertisementForm.getExpirationMonth());
		advertisement.getCreditCard().setExpirationYear(advertisementForm.getExpirationYear());
		advertisement.getCreditCard().setCvv(advertisementForm.getCvv());

		if (binding != null)
			this.validator.validate(advertisement, binding);

		return advertisement;
	}

	public void flush() {
		this.advertisementRepository.flush();
	}

}
