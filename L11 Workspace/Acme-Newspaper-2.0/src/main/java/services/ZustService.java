package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Admin;
import domain.Zust;
import repositories.ZustRepository;

@Service
@Transactional
public class ZustService {

	// Managed repository
	@Autowired
	private ZustRepository zustRepository;

	// Supporting services
	@Autowired
	private AdminService adminService;

	// Constructors
	public ZustService() {
		super();
	}

	// Simple CRUD methods
	public Zust create() {

		Admin admin = this.adminService.findByPrincipal();
		Assert.notNull(admin);

		Zust res = new Zust();

		res.setTicker(getAutoGenerateTicker());
		res.setAdmin(admin);

		return res;
	}

	// Compruebo que ese ticker no este en uso y le asigno uno único
	private String getAutoGenerateTicker() {
		Collection<Zust> zusts = zustRepository.findAll();
		String ticker = generateTicker();
		for (Zust zust : zusts) {
			while (zust.getTicker().equals(ticker)) {
				ticker = generateTicker();
			}
		}
		return ticker;

	}

	private String generateTicker() {

		String ticker = "";
		final LocalDate date;
		date = new LocalDate();

		int dd = date.getDayOfMonth();
		int mm = date.getMonthOfYear();
		int y = date.getYear();
		int yy = y % 100;

		String day = Integer.toString(dd);
		String month = Integer.toString(mm);
		String year = Integer.toString(yy);

		if (day.length() == 0) {
			day = "0" + day;
		}
		Integer num1 = (int) (Math.random() * 10000);
		String number = num1.toString();

		// ddMMyy-XXXX

		ticker = day + month + year + "-" + number;

		return ticker;
	}

	public Zust save(final Zust zust) {

		Assert.notNull(zust);

		Zust res;

		if (zust.getMoment() == null) {
			zust.setMoment(new Date());
		}
		res = this.zustRepository.save(zust);

		return res;
	}

	public Collection<Zust> findAll() {
		Collection<Zust> res = new ArrayList<Zust>();

		res = this.zustRepository.findAll();

		Assert.notNull(res);
		return res;
	}

	public Zust findOne(final int zustId) {
		Assert.isTrue(zustId != 0);
		Zust res;

		res = this.zustRepository.findOne(zustId);

		return res;
	}

	public Zust findOneToEdit(final int zustId) {
		Assert.isTrue(zustId != 0);
		Zust res;

		res = this.zustRepository.findOne(zustId);

		return res;
	}

	public void delete(final Zust zust) {
		this.adminService.checkAuthority();

		Assert.notNull(zust);
		Assert.isTrue(zust.getId() != 0);

		this.zustRepository.delete(zust);
	}

	public Collection<Zust> zustByNewspaperId(int newspaperId) {
		return this.zustRepository.zustByNewspaperId(newspaperId);
	}

	public Collection<Zust> findAllByAdminId(int adminId) {
		return this.zustRepository.findAllByAdminId(adminId);
	}

}
