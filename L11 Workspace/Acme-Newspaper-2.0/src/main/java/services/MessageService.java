
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository		messageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private FolderService			folderService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;
	
	@Autowired
	private Validator validator;

	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Message create() {

		Message message;
		Actor actor;
		Folder folder;

		message = new Message();
		message.setMoment(new Date(System.currentTimeMillis() - 1000));

		actor = this.actorService.findByPrincipal();
		message.setSender(actor);

		folder = this.folderService.findByFolderName(actor.getUserAccount().getId(), "out box");
		message.setFolder(folder);

		return message;
	}

	public void delete(final Message message) {

		this.checkByPrincipal(message);

		Message saved;
		final Actor actor;
		Folder trashbox;

		actor = this.actorService.findByPrincipal();
		trashbox = this.folderService.findByFolderName(actor.getUserAccount().getId(), "trash box");

		if (message.getFolder() != trashbox) {
			message.setFolder(trashbox);
			saved = this.messageRepository.save(message);
			trashbox.getMessages().add(saved);
		} else
			this.messageRepository.delete(message);
	}

	public Message findOne(final int id) {

		Message result;

		result = this.messageRepository.findOne(id);

		return result;
	}

	public Message findOneToEdit(final int id) {

		Message result;

		result = this.findOne(id);
		this.checkByPrincipal(result);

		return result;
	}

	public Collection<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message save(final Message message) {

		Assert.notNull(message);

		Message saved, copy;
		Message savedCopy = null;
		Folder outboxSender, inboxRecipient, spamboxRecipient;

		if (message.getId() == 0) {
			final Date newMoment = new Date(System.currentTimeMillis() - 1000);
			copy = this.copy(message);
			if (this.isSpamMessage(message)) {
				spamboxRecipient = this.folderService.findByFolderName(copy.getRecipient().getUserAccount().getId(), "spam box");
				copy.setFolder(spamboxRecipient);
				savedCopy = this.messageRepository.save(copy);
				savedCopy.setMoment(newMoment);
				spamboxRecipient.getMessages().add(savedCopy);
			} else {
				inboxRecipient = this.folderService.findByFolderName(copy.getRecipient().getUserAccount().getId(), "in box");
				copy.setFolder(inboxRecipient);
				savedCopy = this.messageRepository.save(copy);
				savedCopy.setMoment(newMoment);
				inboxRecipient.getMessages().add(savedCopy);
			}

			outboxSender = message.getFolder();
			saved = this.messageRepository.save(message);
			saved.setMoment(newMoment);
			outboxSender.getMessages().add(saved);
		} else
			saved = this.messageRepository.save(message);

		return saved;
	}

	public Message notify(final Message message) {

		Assert.isTrue(!this.isSpamMessage(message));
		Assert.notNull(message);

		Message saved = null;
		Message copy, savedCopy;
		Folder outboxSender = null;
		Folder notificationboxRecipient;

		message.setMoment(new Date(System.currentTimeMillis() - 1000));
		outboxSender = this.folderService.findByFolderName(message.getSender().getUserAccount().getId(), "out box");
		message.setFolder(outboxSender);
		final Collection<Actor> recipients = this.actorService.findAll();
		recipients.remove(this.actorService.findByPrincipal());
		for (final Actor recipient : recipients) {
			message.setRecipient(recipient);
			copy = this.copy(message);

			notificationboxRecipient = this.folderService.findByFolderName(recipient.getUserAccount().getId(), "notification box");
			copy.setFolder(notificationboxRecipient);
			saved = this.messageRepository.save(message);
			outboxSender.getMessages().add(saved);
			savedCopy = this.messageRepository.save(copy);
			notificationboxRecipient.getMessages().add(savedCopy);
		}

		return saved;
	}
	// Other business methods -------------------------------------------------
	
	public MessageForm construct(final Message message) {

		Assert.notNull(message);

		MessageForm messageForm;

		messageForm = new MessageForm();

		messageForm.setId(message.getId());
		messageForm.setSenderId(message.getSender().getId());
		if(message.getRecipient() == null){
			messageForm.setRecipientId(null);
		}else{
			messageForm.setRecipientId(message.getRecipient().getId());
		}
		messageForm.setFolderId(message.getFolder().getId());
		messageForm.setMoment(message.getMoment());
		messageForm.setSubject(message.getSubject());
		messageForm.setBody(message.getBody());
		messageForm.setPriority(message.getPriority());
		
		return messageForm;
	}

	public Message reconstruct(final MessageForm messageForm, final BindingResult binding) {

		Assert.notNull(messageForm);

		Message message;

		if (messageForm.getId() != 0)
			message = this.findOne(messageForm.getId());
		else
			message = this.create();

		message.setMoment(messageForm.getMoment());
		message.setSubject(messageForm.getSubject());
		message.setBody(messageForm.getBody());
		message.setPriority(messageForm.getPriority());
		message.setSender(actorService.findOne(messageForm.getSenderId()));
		if(messageForm.getRecipientId() == null){
			message.setRecipient(actorService.findOne(messageForm.getSenderId()));
		}else{
			message.setRecipient(actorService.findOne(messageForm.getRecipientId()));
		}
		message.setFolder(folderService.findOne(messageForm.getFolderId()));

		if (binding != null)
			this.validator.validate(message, binding);

		return message;
	}

	public Message copy(final Message message) {

		Assert.notNull(message);

		Message result;

		result = this.create();
		result.setSubject(message.getSubject());
		result.setBody(message.getBody());
		result.setMoment(message.getMoment());
		result.setPriority(message.getPriority());
		result.setRecipient(message.getRecipient());
		result.setSender(message.getSender());

		return result;
	}

	public Collection<Message> findByFolderId(final int folderId) {

		Collection<Message> result;

		final Folder folder = this.folderService.findOne(folderId);
		this.folderService.checkPrincipal(folder);
		result = folder.getMessages();

		return result;
	}

	public void deleteByFolder(final Folder folder) {

		final Collection<Message> messages = folder.getMessages();
		this.messageRepository.delete(messages);
	}

	public void moveMessageToFolder(final Message message, final Folder folder) {
		Assert.notNull(folder);
		Assert.notNull(message);
		this.folderService.checkPrincipal(folder);
		Assert.isTrue(!folder.getMessages().contains(message));

		final Actor actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor.getFolders().contains(message.getFolder()));

		final List<Message> messages = new ArrayList<Message>(folder.getMessages());
		final Folder folderSource = message.getFolder();
		final List<Message> messages2 = new ArrayList<Message>(folderSource.getMessages());

		messages.add(message);
		folder.setMessages(messages);
		messages2.remove(message);
		folderSource.setMessages(messages2);

		this.folderService.save(folder);
		message.setFolder(folder);
		this.save(message);
	}

	public void checkByPrincipal(final Message message) {

		Assert.notNull(message);

		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(message.getRecipient().equals(actor) || message.getSender().equals(actor));
	}

	private boolean isSpamMessage(final Message message) {
		boolean result = false;
		Pattern p;
		Matcher isAnyMatcherBody;
		Matcher isAnyMatcherSubject;

		p = this.spamWords();
		isAnyMatcherBody = p.matcher(message.getBody());
		isAnyMatcherSubject = p.matcher(message.getSubject());

		if (isAnyMatcherBody.find() || isAnyMatcherSubject.find())
			result = true;

		return result;
	}

	public Pattern spamWords() {
		Pattern result;
		List<String> spamWords;

		final String spamlist = this.configurationService.findAll().iterator().next().getTabooWords();
		spamWords = Arrays.asList(spamlist.split(","));

		String str = ".*\\b(";
		for (int i = 0; i <= spamWords.size(); i++)
			if (i < spamWords.size())
				str += spamWords.get(i) + "|";
			else
				str += spamWords.iterator().next() + ")\\b.*";

		result = Pattern.compile(str, Pattern.CASE_INSENSITIVE);

		return result;
	}

}
