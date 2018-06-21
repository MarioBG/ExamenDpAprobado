
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FolderRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Folder;
import domain.Message;
import forms.FolderForm;

@Service
@Transactional
public class FolderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FolderRepository	folderRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageService		messageService;
	
	@Autowired
	private Validator validator;


	// Constructors -----------------------------------------------------------

	public FolderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Folder create(final boolean predefined, final Folder folder) {

		Folder result = null;

		result = new Folder();

		if (folder != null)
			result.setParent(folder);

		result.setPredefined(predefined);
		result.setChildren(new ArrayList<Folder>());
		result.setMessages(new ArrayList<Message>());

		return result;
	}

	public Folder findOne(final int folderId) {

		Folder result = null;
		result = this.folderRepository.findOne(folderId);
		return result;
	}

	public Folder findOneToEdit(final int folderId) {

		Folder result = null;
		result = this.folderRepository.findOne(folderId);
		this.checkPrincipal(result);
		Assert.isTrue(result.getPredefined() == false);
		return result;
	}

	public Collection<Folder> findAll() {

		Collection<Folder> result = null;
		result = this.folderRepository.findAll();
		return result;
	}

	public Folder save(final Folder folder) {

		Assert.notNull(folder);

		Actor actor;
		Folder saved, parent;

		if (folder.getId() == 0) {
			saved = this.folderRepository.save(folder);
			actor = this.actorService.findByPrincipal();
			actor.getFolders().add(saved);
			parent = folder.getParent();
			if (parent != null)
				parent.getChildren().add(saved);
		} else
			saved = this.folderRepository.save(folder);

		return saved;
	}

	public void delete(final Folder folder) {

		Assert.notNull(folder);
		this.checkPrincipal(folder);

		Actor actor;
		Folder parent;

		actor = this.actorService.findByPrincipal();
		parent = folder.getParent();

		this.messageService.deleteByFolder(folder);
		actor.getFolders().remove(folder);
		if (parent != null)
			parent.getChildren().remove(folder);
		this.folderRepository.delete(folder);
	}

	// Other business methods -------------------------------------------------
	
	public FolderForm construct(final Folder folder) {

		Assert.notNull(folder);

		FolderForm folderForm;

		folderForm = new FolderForm();

		folderForm.setId(folder.getId());
		if(folder.getParent() == null){
			folderForm.setParentId(null);
		}else{
			folderForm.setParentId(folder.getParent().getId());
		}
		folderForm.setName(folder.getName());

		return folderForm;
	}

	public Folder reconstruct(final FolderForm folderForm, final BindingResult binding) {

		Assert.notNull(folderForm);

		Folder folder;

		if (folderForm.getId() != 0)
			folder = this.findOne(folderForm.getId());
		else
			folder = this.create(false, null);

		folder.setParent(findOne(folderForm.getParentId()));
		folder.setName(folderForm.getName());

		if (binding != null)
			this.validator.validate(folder, binding);

		return folder;
	}

	public Collection<Folder> defaultFolders() {

		final Collection<Folder> result = new ArrayList<Folder>();
		Folder inbox, outbox, notificationbox, spambox, trashbox;

		inbox = this.create(true, null);
		outbox = this.create(true, null);
		notificationbox = this.create(true, null);
		spambox = this.create(true, null);
		trashbox = this.create(true, null);

		inbox.setName("in box");
		inbox.setMessages(new ArrayList<Message>());
		inbox.setChildren(new ArrayList<Folder>());
		outbox.setName("out box");
		outbox.setMessages(new ArrayList<Message>());
		outbox.setChildren(new ArrayList<Folder>());
		trashbox.setName("trash box");
		trashbox.setMessages(new ArrayList<Message>());
		trashbox.setChildren(new ArrayList<Folder>());
		notificationbox.setName("notification box");
		notificationbox.setMessages(new ArrayList<Message>());
		notificationbox.setChildren(new ArrayList<Folder>());
		spambox.setName("spam box");
		spambox.setMessages(new ArrayList<Message>());
		spambox.setChildren(new ArrayList<Folder>());

		result.add(inbox);
		result.add(outbox);
		result.add(notificationbox);
		result.add(spambox);
		result.add(trashbox);

		return result;
	}

	public Folder save(final Folder folder, final Actor actor) {
		Assert.notNull(folder);
		Assert.notNull(actor);

		Folder result = null;

		result = this.folderRepository.save(folder);
		actor.getFolders().add(result);
		this.actorService.save(actor);

		return result;
	}

	public Collection<Folder> findByPrincipal() {

		Collection<Folder> result = null;
		final UserAccount userAccount = LoginService.getPrincipal();
		result = this.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Collection<Folder> findByFolderId(final Integer folderId) {

		Collection<Folder> result;
		Folder folder;
		Actor actor;

		actor = this.actorService.findByPrincipal();

		if (folderId == null)
			result = this.findFoldersWithoutParent(actor.getUserAccount().getId());
		else {
			folder = this.findOne(folderId);
			this.checkPrincipal(folder);
			result = folder.getChildren();
		}

		return result;
	}

	public Collection<Folder> findFoldersWithoutParent(final int userAccountId) {

		Collection<Folder> result;

		result = this.folderRepository.findFoldersWithoutParent(userAccountId);

		return result;
	}

	public Collection<Folder> findByUserAccountId(final int userAccountId) {

		Collection<Folder> result = null;
		result = this.actorService.findFoldersByUserAccountId(userAccountId);
		return result;
	}

	public Folder findByFolderName(final int userAccountId, final String folderName) {

		Folder result = null;
		result = this.folderRepository.findByFolderName(userAccountId, folderName);
		return result;
	}

	public void checkNotRepeat(final Folder folder) {

		Assert.notNull(folder);

		final Actor actor = this.actorService.findByPrincipal();
		final Folder folderActor = this.findByFolderName(actor.getUserAccount().getId(), folder.getName());
		Assert.isNull(folderActor);
	}

	public void checkPrincipal(final Folder folder) {

		Assert.notNull(folder);

		final Actor actor = this.actorService.findByPrincipal();
		final Folder folderActor = this.findByFolderName(actor.getUserAccount().getId(), folder.getName());
		Assert.isTrue(folder.equals(folderActor));
	}

	public Collection<Folder> save(final Collection<Folder> folders) {
		return this.folderRepository.save(folders);
	}

	public void delete(final Collection<Folder> folders) {
		this.folderRepository.delete(folders);
	}

}
