package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Folder;

@Service
@Transactional
public class ActorService {

	// Managed repository
	@Autowired
	private ActorRepository actorRepository;
	
	// Constructors
	public ActorService() {
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Actor findOne(final int actorId) {

		Actor result = null;
		result = this.actorRepository.findOne(actorId);
		return result;
	}

	public Collection<Actor> findAll() {

		Collection<Actor> result = null;
		result = this.actorRepository.findAll();
		return result;
	}

	public Actor save(final Actor actor) {

		Actor result = null;

		result = this.actorRepository.save(actor);

		return result;
	}

	// Ancillary methods

	public Actor findActorByUserAccountId(int uA) {

		Actor actor = actorRepository.findActorByUserAccountId(uA);
		return actor;
	}

	public Actor findByPrincipal() {

		Actor actor;

		UserAccount principalUserAccount = LoginService.getPrincipal();
		if (principalUserAccount == null) {
			actor = null;
		} else {
			actor = actorRepository.findActorByUserAccountId(principalUserAccount.getId());
		}

		return actor;
	}

	public String getType(UserAccount userAccount) {
		List<Authority> authorities = new ArrayList<Authority>(userAccount.getAuthorities());

		return authorities.get(0).getAuthority();
	}
	
	public Collection<Folder> findFoldersByUserAccountId(final int userAccountId) {
		Collection<Folder> result = null;
		result = this.actorRepository.findFoldersByUserAccountId(userAccountId);
		return result;
	}

	public Actor findSenderByMessageId(final int messageId) {

		Actor result;

		result = this.actorRepository.findSenderByMessageId(messageId);

		return result;
	}

	public Actor findRecipientByMessageId(final int messageId) {

		Actor result;

		result = this.actorRepository.findRecipientByMessageId(messageId);

		return result;
	}

}
