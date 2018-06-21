
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ConfigurationRepository configurationRepository;

	@Autowired
	private AdminService adminService;

	// Constructors -----------------------------------------------------------

	public ConfigurationService() {
		super();
	}

	// Simple CRUD methods

	public Configuration create() {
		Configuration configuration;
		configuration = new Configuration();
		return configuration;
	}

	public Collection<Configuration> findAll() {
		Collection<Configuration> res;
		res = this.configurationRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Configuration findOne(final int configuration) {
		Assert.isTrue(configuration != 0);
		Assert.notNull(this.adminService.findByPrincipal());
		Configuration res;
		res = this.configurationRepository.findOne(configuration);
		Assert.notNull(res);
		return res;
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);
		Assert.notNull(this.adminService.findByPrincipal());
		Configuration res;
		res = this.configurationRepository.save(configuration);
		return res;
	}

	public void delete(final Configuration configuration) {
		Assert.notNull(configuration);
		Assert.isTrue(configuration.getId() != 0);
		Assert.isTrue(this.configurationRepository.exists(configuration.getId()));
		this.configurationRepository.delete(configuration);
	}

	public Collection<String> getQuitarPosicionesVaciasTabooWords(final Collection<String> tabooWords) {
		final Collection<String> res = new ArrayList<>();
		for (final String word : tabooWords)
			if (!word.isEmpty())
				res.add(word);
		return res;
	}

	public Collection<String> getTabooWordsFromConfiguration() {
		Assert.notEmpty(this.configurationRepository.findAll());
		final Collection<String> res = new ArrayList<String>();
		for (final String s : this.configurationRepository.findAll().get(0).getTabooWords().split(","))
			res.add(s.trim());
		return res;
	}

	public void flush() {
		this.configurationRepository.flush();
	}

	public Collection<Configuration> findAllByAdmin() {

		Assert.notNull(this.adminService.findByPrincipal());

		Collection<Configuration> res;
		res = this.findAll();
		Assert.notNull(res);
		return res;
	}

}
