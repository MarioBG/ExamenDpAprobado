package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.VolumeRepository;
import domain.Customer;
import domain.Newspaper;
import domain.SubscriptionVolume;
import domain.User;
import domain.Volume;
import forms.VolumeForm;

@Service
@Transactional
public class VolumeService {

	// Managed repository
	@Autowired
	private VolumeRepository volumeRepository;

	// Supporting services

	@Autowired
	private NewspaperService newspaperService;

	@Autowired
	private UserService userService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private Validator validator;

	// Constructors
	public VolumeService() {
		super();
	}

	// Simple CRUD methods
	public Volume create() {

		Assert.notNull(userService.findByPrincipal());

		Volume res = new Volume();
		Collection<Newspaper> newspapers = new ArrayList<Newspaper>();
		User user = this.userService.findByPrincipal();

		res.setUser(user);
		res.setNewspapers(newspapers);
		res.setYear(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
		res.setSubscriptionsVolume(new ArrayList<SubscriptionVolume>());

		return res;
	}

	public Volume save(final Volume volume) {
		Assert.notNull(volume);

		Volume res;
		res = this.volumeRepository.save(volume);
		return res;
	}

	public Collection<Volume> findAll() {
		Collection<Volume> res = new ArrayList<Volume>();

		res = this.volumeRepository.findAll();

		Assert.notNull(res);
		return res;
	}

	public Volume findOne(final int volumeId) {
		Assert.isTrue(volumeId != 0);
		Volume res;

		res = this.volumeRepository.findOne(volumeId);

		return res;
	}

	public void delete(final Volume volume) {
		this.adminService.checkAuthority();

		Assert.notNull(volume);
		Assert.isTrue(volume.getId() != 0);

		this.volumeRepository.delete(volume);
	}

	// Other bussiness methods

	public VolumeForm construct(final Volume volume) {

		Assert.notNull(volume);

		VolumeForm volumeForm;

		volumeForm = new VolumeForm();

		volumeForm.setId(volume.getId());
		volumeForm.setTitle(volume.getTitle());
		volumeForm.setDescription(volume.getDescription());
		volumeForm.setYear(volume.getYear());
		volumeForm.setUserId(volume.getUser().getId());

		return volumeForm;
	}

	public Volume reconstruct(final VolumeForm volumeForm, final BindingResult binding) {

		Assert.notNull(volumeForm);

		Volume volume;

		if (volumeForm.getId() != 0)
			volume = this.findOne(volumeForm.getId());
		else
			volume = this.create();

		volume.setTitle(volumeForm.getTitle());
		volume.setDescription(volumeForm.getDescription());
		volume.setYear(volumeForm.getYear());

		if (binding != null)
			this.validator.validate(volume, binding);

		return volume;
	}

	public Collection<Volume> findByUserId(int userId) {

		Assert.isTrue(userId != 0);

		Collection<Volume> result = volumeRepository.findByUserId(userId);
		return result;
	}

	public Collection<Volume> findByPrincipal() {

		User principal = userService.findByPrincipal();

		Assert.isTrue(principal != null);

		Collection<Volume> result = findByUserId(principal.getId());
		return result;
	}

	public Collection<Volume> findSubscribedVolumesByCustomerId(int customerId) {

		Assert.isTrue(customerId != 0);

		Collection<Volume> result = volumeRepository.findSubscribedVolumesByCustomerId(customerId);
		return result;
	}

	public Collection<Volume> findSubscribedVolumesByPrincipal() {

		Customer principal = customerService.findByPrincipal();

		Assert.notNull(principal);

		Collection<Volume> result = findSubscribedVolumesByCustomerId(principal.getId());
		return result;
	}

	public void addNewspaper(int volumeId, int newspaperId) {

		Assert.isTrue(volumeId != 0);
		Assert.isTrue(newspaperId != 0);

		Volume volume = findOne(volumeId);
		Newspaper newspaper = newspaperService.findOne(newspaperId);

		Assert.isTrue(newspaperService.findAvalibleNewspapers().contains(newspaper));
		Assert.isTrue(!volume.getNewspapers().contains(newspaper));

		volume.getNewspapers().add(newspaper);
		newspaper.getVolumes().add(volume);
	}

	public void removeNewspaper(int volumeId, int newspaperId) {

		Assert.isTrue(volumeId != 0);
		Assert.isTrue(newspaperId != 0);

		Volume volume = findOne(volumeId);
		Newspaper newspaper = newspaperService.findOne(newspaperId);

		Assert.isTrue(newspaperService.findAvalibleNewspapers().contains(newspaper));
		Assert.isTrue(volume.getNewspapers().contains(newspaper));

		volume.getNewspapers().remove(newspaper);
		newspaper.getVolumes().remove(volume);
	}

	public void flush(){
		volumeRepository.flush();
	}
}
