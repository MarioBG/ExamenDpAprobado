package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Zust;
import repositories.ZustRepository;

@Service
@Transactional
public class ZustService {

	// Managed repository
	@Autowired
	private ZustRepository ZustRepository;

	// Supporting services
	@Autowired
	private NewspaperService newspaperService;

	@Autowired
	private UserService userService;

	@Autowired
	private AdminService adminService;

	// Constructors
	public ZustService() {
		super();
	}

	// Simple CRUD methods
	public Zust create() {

		Assert.notNull(this.adminService.findByPrincipal());

		Zust res = new Zust();

		res.setTicker(getAutoGenerateTicker());

		return res;
	}

	private String getAutoGenerateTicker() {
		// TODO GENERAR PATRON PARA EL TICKET
		return null;
	}

	public Zust save(final Zust Zust) {

		Assert.notNull(Zust);
		if (Zust.getId() == 0) {
			Zust.setMoment(new Date(System.currentTimeMillis() - 1000));
		}

		Zust res;

		res = this.ZustRepository.save(Zust);

		return res;
	}

	public Collection<Zust> findAll() {
		Collection<Zust> res = new ArrayList<Zust>();

		res = this.ZustRepository.findAll();

		Assert.notNull(res);
		return res;
	}

	public Zust findOne(final int ZustId) {
		Assert.isTrue(ZustId != 0);
		Zust res;

		res = this.ZustRepository.findOne(ZustId);

		return res;
	}

	public Zust findOneToEdit(final int ZustId) {
		Assert.isTrue(ZustId != 0);
		Zust res;

		res = this.ZustRepository.findOne(ZustId);

		return res;
	}

	public void delete(final Zust Zust) {
		this.adminService.checkAuthority();

		Assert.notNull(Zust);
		Assert.isTrue(Zust.getId() != 0);

		this.ZustRepository.delete(Zust);
	}

	// Other busines methods

	// public ZustForm construct(Zust Zust) {
	// Assert.notNull(Zust);
	// ZustForm res = new ZustForm();
	//
	// res.setId(Zust.getId());
	// res.setTitle(Zust.getTitle());
	// res.setSummary(Zust.getSummary());
	// res.setBody(Zust.getBody());
	// res.setPictures(Zust.getPictures());
	// res.setIsFinal(Zust.getIsFinal());
	// res.setNewspaperId(Zust.getNewspaper().getId());
	//
	// return res;
	// }

	// public Zust reconstruct(ZustForm ZustForm, BindingResult binding) {
	// Assert.notNull(ZustForm);
	// Newspaper newspaper =
	// this.newspaperService.findOne(ZustForm.getNewspaperId());
	// Zust res;
	//
	// if (ZustForm.getId() != 0)
	// res = this.findOne(ZustForm.getId());
	// else
	// res = this.create(newspaper.getId());
	//
	// res.setTitle(ZustForm.getTitle());
	// res.setSummary(ZustForm.getSummary());
	// res.setBody(ZustForm.getBody());
	// res.setPictures(ZustForm.getPictures());
	// res.setIsFinal(ZustForm.getIsFinal());
	// res.setNewspaper(newspaper);
	//
	// if (binding != null)
	// this.validator.validate(res, binding);
	//
	// return res;
	// }

}
