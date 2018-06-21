package services;

import java.util.Calendar;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SubscriptionVolumeRepository;
import domain.CreditCard;
import domain.SubscriptionVolume;
import forms.SubscriptionVolumeForm;

@Service
@Transactional
public class SubscriptionVolumeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SubscriptionVolumeRepository subscriptionVolumeRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CustomerService customerService;

	@Autowired
	private VolumeService volumeService;

	@Autowired
	private Validator validator;

	// Constructors -----------------------------------------------------------

	public SubscriptionVolumeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public SubscriptionVolume create(int volumeId) {

		Assert.notNull(customerService.findByPrincipal());

		SubscriptionVolume result = new SubscriptionVolume();
		result.setCreditCard(new CreditCard());
		result.setCustomer(this.customerService.findByPrincipal());
		result.setVolume(this.volumeService.findOne(volumeId));

		return result;
	}

	public Collection<SubscriptionVolume> findAll() {

		Collection<SubscriptionVolume> result = subscriptionVolumeRepository.findAll();
		return result;
	}

	public SubscriptionVolume findOne(int subscriptionVolumeId) {

		SubscriptionVolume result = subscriptionVolumeRepository.findOne(subscriptionVolumeId);
		return result;
	}

	public SubscriptionVolume save(SubscriptionVolume subscriptionVolume) {

		Assert.notNull(customerService.findByPrincipal());
		Assert.isTrue(
				subscriptionVolume.getCreditCard().getExpirationYear() >= Calendar.getInstance().get(Calendar.YEAR));

		if (subscriptionVolume.getCreditCard().getExpirationYear() == Calendar.getInstance().get(Calendar.YEAR)) {
			Assert.isTrue(subscriptionVolume.getCreditCard().getExpirationMonth() > Calendar.getInstance()
					.get(Calendar.MONTH));
		}

		SubscriptionVolume result = subscriptionVolumeRepository.save(subscriptionVolume);
		result.getCustomer().getSubscriptionsVolume().add(result);
		result.getVolume().getSubscriptionsVolume().add(result);
		return result;
	}

	public void delete(SubscriptionVolume subscriptionVolume) {

		subscriptionVolume.getCustomer().getSubscriptionsVolume().remove(subscriptionVolume);
		subscriptionVolume.getVolume().getSubscriptionsVolume().remove(subscriptionVolume);
	}

	// Other bussiness methods ----------------------------------------------------

	public SubscriptionVolumeForm construct(final SubscriptionVolume subscriptionVolume) {

		Assert.notNull(subscriptionVolume);

		SubscriptionVolumeForm subscriptionVolumeForm;

		subscriptionVolumeForm = new SubscriptionVolumeForm();

		subscriptionVolumeForm.setId(subscriptionVolume.getId());
		subscriptionVolumeForm.setCustomerId(subscriptionVolume.getCustomer().getId());
		subscriptionVolumeForm.setVolumeId(subscriptionVolume.getVolume().getId());
		subscriptionVolumeForm.setHolder(subscriptionVolume.getCreditCard().getHolder());
		subscriptionVolumeForm.setBrand(subscriptionVolume.getCreditCard().getBrand());
		subscriptionVolumeForm.setNumber(subscriptionVolume.getCreditCard().getNumber());
		if (subscriptionVolume.getId() == 0) {
			subscriptionVolumeForm.setExpirationMonth(null);
			subscriptionVolumeForm.setExpirationYear(null);
			subscriptionVolumeForm.setCvv(null);
		} else {
			subscriptionVolumeForm.setExpirationMonth(subscriptionVolume.getCreditCard().getExpirationMonth());
			subscriptionVolumeForm.setExpirationYear(subscriptionVolume.getCreditCard().getExpirationYear());
			subscriptionVolumeForm.setCvv(subscriptionVolume.getCreditCard().getCvv());
		}

		return subscriptionVolumeForm;
	}

	public SubscriptionVolume reconstruct(final SubscriptionVolumeForm subscriptionVolumeForm,
			final BindingResult binding) {

		Assert.notNull(subscriptionVolumeForm);

		SubscriptionVolume subscriptionVolume;

		if (subscriptionVolumeForm.getId() != 0)
			subscriptionVolume = this.findOne(subscriptionVolumeForm.getId());
		else
			subscriptionVolume = this.create(subscriptionVolumeForm.getVolumeId());

		subscriptionVolume.getCreditCard().setHolder(subscriptionVolumeForm.getHolder());
		subscriptionVolume.getCreditCard().setBrand(subscriptionVolumeForm.getBrand());
		subscriptionVolume.getCreditCard().setNumber(subscriptionVolumeForm.getNumber());
		subscriptionVolume.getCreditCard().setExpirationMonth(subscriptionVolumeForm.getExpirationMonth());
		subscriptionVolume.getCreditCard().setExpirationYear(subscriptionVolumeForm.getExpirationYear());
		subscriptionVolume.getCreditCard().setCvv(subscriptionVolumeForm.getCvv());

		if (binding != null)
			this.validator.validate(subscriptionVolume, binding);

		return subscriptionVolume;
	}
	
	public void flush(){
		subscriptionVolumeRepository.flush();
	}

}
