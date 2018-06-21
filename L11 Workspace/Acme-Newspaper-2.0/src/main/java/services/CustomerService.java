package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Customer;
import domain.SubscriptionNewspaper;
import domain.SubscriptionVolume;
import forms.CustomerForm;

@Service
@Transactional
public class CustomerService {

	// Managed repository

	@Autowired
	private CustomerRepository customerRepository;

	// Supporting services
	
	@Autowired
	private ActorService actorService;

	@Autowired
	private Validator validator;

	// Constructors

	public CustomerService() {
		super();
	}

	// Simple CRUD methods

	public Customer create() {
		
		Actor principal = actorService.findByPrincipal();
		Assert.isTrue(principal == null);
		
		final Customer result = new Customer();

		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		Collection<SubscriptionNewspaper> subscriptionsNewspaper = new ArrayList<SubscriptionNewspaper>();
		Collection<SubscriptionVolume> subscriptionsVolume = new ArrayList<SubscriptionVolume>();

		authority.setAuthority(Authority.CUSTOMER);
		userAccount.addAuthority(authority);
	
		result.setUserAccount(userAccount);
		result.setSubscriptionsNewspaper(subscriptionsNewspaper);
		result.setSubscriptionsVolume(subscriptionsVolume);

		return result;
	}

	public Collection<Customer> findAll() {
		
		Collection<Customer> result = this.customerRepository.findAll();
		return result;
	}

	public Customer findOne(final int customerId) {
		
		Assert.isTrue(customerId != 0);
		
		Customer result = this.customerRepository.findOne(customerId);
		return result;
	}

	public Customer save(final Customer customer) {
		
		Customer result;

		if (customer.getId() == 0) {
			String pass = customer.getUserAccount().getPassword();
			final Md5PasswordEncoder code = new Md5PasswordEncoder();
			pass = code.encodePassword(pass, null);
			customer.getUserAccount().setPassword(pass);
		}
		
		result = this.customerRepository.save(customer);
		return result;
	}

	// Other business methods

	public Customer findByPrincipal() {
		
		Customer result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		if (userAccount == null)
			result = null;
		else
			result = this.customerRepository.findCustomerByUserAccountId(userAccount
					.getId());
		return result;
	}

	public boolean checkAuthority() {
		boolean result = false;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("USER");
		if (authority.contains(res)) {
			result = true;
		}
		return result;
	}

	public void flush() {
		this.customerRepository.flush();
	}

	public Customer reconstruct(final CustomerForm customerForm, final BindingResult binding) {

		Assert.notNull(customerForm);
		Assert.isTrue(customerForm.getTermsAndConditions() == true);

		Customer result = new Customer();

		if (customerForm.getId() != 0)
			result = this.findOne(customerForm.getId());
		else
			result = this.create();

		result.setName(customerForm.getName());
		result.setSurname(customerForm.getSurname());
		result.setEmail(customerForm.getEmail());
		result.setPhone(customerForm.getPhone());
		result.setAddress(customerForm.getAddress());
		result.getUserAccount().setUsername(customerForm.getUsername());
		result.getUserAccount().setPassword(customerForm.getPassword());

		this.validator.validate(result, binding);

		return result;
	}

	public CustomerForm construct(Customer customer) {

		Assert.notNull(customer);
		CustomerForm result = new CustomerForm();

		result.setId(customer.getId());
		result.setName(customer.getName());
		result.setSurname(customer.getSurname());
		result.setEmail(customer.getEmail());
		result.setPhone(customer.getPhone());
		result.setAddress(customer.getAddress());
		result.setUsername(customer.getUserAccount().getUsername());
		result.setPassword(customer.getUserAccount().getPassword());
		result.setRepeatPassword(customer.getUserAccount().getPassword());
		result.setTermsAndConditions(false);

		return result;
	}

}
