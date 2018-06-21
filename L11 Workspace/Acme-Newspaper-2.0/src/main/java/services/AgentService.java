package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Advertisement;
import domain.Agent;
import domain.Folder;
import forms.AgentForm;
import repositories.AgentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AgentService {

	// Managed repository

	@Autowired
	private AgentRepository agentRepository;

	// Supporting services

	@Autowired
	private ActorService actorService;

	@Autowired
	private FolderService folderService;

	@Autowired
	private Validator validator;

	// Constructors

	public AgentService() {
		super();
	}

	// Simple CRUD methods

	public Agent create() {

		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal == null);

		final Agent res = new Agent();

		final UserAccount agentAccount = new UserAccount();
		final Authority authority = new Authority();
		final Collection<Advertisement> advertisements = new ArrayList<Advertisement>();
		Collection<Folder> folders = new ArrayList<Folder>();
		authority.setAuthority(Authority.AGENT);
		agentAccount.addAuthority(authority);
		res.setUserAccount(agentAccount);

		res.setAdvertisements(advertisements);
		res.setFolders(folders);

		return res;
	}

	public Collection<Agent> findAll() {
		Collection<Agent> res;
		res = this.agentRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Agent findOne(final int agentId) {
		Assert.isTrue(agentId != 0);
		Agent res;
		res = this.agentRepository.findOne(agentId);
		return res;
	}

	public Agent save(final Agent agent) {
		Agent res;
		if (agent.getId() == 0) {
			final Collection<Folder> folders = this.folderService
					.save(this.folderService.defaultFolders());
			agent.setFolders(folders);
			agent.getUserAccount().setPassword(
					new Md5PasswordEncoder().encodePassword(agent
							.getUserAccount().getPassword(), null));
		}
		res = this.agentRepository.save(agent);

		return res;
	}

	// Other business methods

	public Agent findAgentByUserAccountId(final int uA) {
		return this.agentRepository.findAgentByUserAccountId(uA);
	}

	public Agent findByPrincipal() {
		Agent res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		if (userAccount == null)
			res = null;
		else
			res = this.agentRepository.findAgentByUserAccountId(userAccount
					.getId());
		return res;
	}

	public Agent reconstruct(final AgentForm agentForm,
			final BindingResult binding) {

		Assert.notNull(agentForm);
		Assert.isTrue(agentForm.getTermsAndConditions() == true);

		Agent res = new Agent();

		if (agentForm.getId() != 0)
			res = this.findOne(agentForm.getId());
		else
			res = this.create();

		res.setName(agentForm.getName());
		res.setSurname(agentForm.getSurname());
		res.setEmail(agentForm.getEmail());
		res.setPhone(agentForm.getPhone());
		res.setAddress(agentForm.getAddress());
		res.getUserAccount().setUsername(agentForm.getUsername());
		res.getUserAccount().setPassword(agentForm.getPassword());

		if(binding != null){
			this.validator.validate(res, binding);
		}
		
		return res;
	}

	public AgentForm construct(final Agent agent) {

		Assert.notNull(agent);
		final AgentForm editAgentForm = new AgentForm();

		editAgentForm.setId(agent.getId());
		editAgentForm.setName(agent.getName());
		editAgentForm.setSurname(agent.getSurname());
		editAgentForm.setEmail(agent.getEmail());
		editAgentForm.setPhone(agent.getPhone());
		editAgentForm.setAddress(agent.getAddress());
		editAgentForm.setUsername(agent.getUserAccount().getUsername());
		editAgentForm.setPassword(agent.getUserAccount().getPassword());
		editAgentForm.setRepeatPassword(agent.getUserAccount().getPassword());
		editAgentForm.setTermsAndConditions(false);

		return editAgentForm;
	}
	
	public void flush() {
		this.agentRepository.flush();
	}

}
