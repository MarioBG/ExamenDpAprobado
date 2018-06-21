package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SubscriptionNewspaperRepository;
import domain.CreditCard;
import domain.SubscriptionNewspaper;

@Service
@Transactional
public class SubscriptionNewspaperService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SubscriptionNewspaperRepository subscriptionNewspaperRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService customerService;

	@Autowired
	private NewspaperService newspaperService;

	// Constructors -----------------------------------------------------------

	public SubscriptionNewspaperService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public SubscriptionNewspaper create(int newspaperId) {

		SubscriptionNewspaper result = new SubscriptionNewspaper();
		result.setCreditCard(new CreditCard());
		result.setCustomer(this.customerService.findByPrincipal());
		result.setNewspaper(this.newspaperService.findOne(newspaperId));

		return result;
	}

	public Collection<SubscriptionNewspaper> findAll() {

		Collection<SubscriptionNewspaper> result = subscriptionNewspaperRepository
				.findAll();
		return result;
	}

	public SubscriptionNewspaper findOne(int subscriptionNewspaperId) {

		SubscriptionNewspaper result = subscriptionNewspaperRepository.findOne(subscriptionNewspaperId);
		return result;
	}

	public SubscriptionNewspaper save(SubscriptionNewspaper subscriptionNewspaper) {
		
		SubscriptionNewspaper result = subscriptionNewspaperRepository.save(subscriptionNewspaper);
		result.getCustomer().getSubscriptionsNewspaper().add(result);
		result.getNewspaper().getSubscriptionsNewspaper().add(subscriptionNewspaper);
		return result;
	}

	public void delete(SubscriptionNewspaper subscription) {

		Assert.notNull(subscription);
		Assert.isTrue(subscription.getId() != 0);

		subscription.getCustomer().getSubscriptionsNewspaper()
				.remove(subscription);
		subscriptionNewspaperRepository.delete(subscription);

	}
	
	public void flush(){
		subscriptionNewspaperRepository.flush();
	}
}
