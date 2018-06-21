
package controllers.admin;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Folder;
import domain.Message;
import domain.Priority;
import forms.MessageForm;

@Controller
@RequestMapping("/message/admin")
public class MessageAdminController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private FolderService	folderService;


	// Constructors --------------------------------------------------

	public MessageAdminController() {
		super();
	}

	// Creation ------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Message message;

		message = this.messageService.create();
		MessageForm messageForm = messageService.construct(message);

		result = this.createEditModelAndView(messageForm);

		return result;
	}

	// Edition -------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MessageForm messageForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(messageForm);
		else
			try {
				Message message = messageService.reconstruct(messageForm, binding);
				final Message saved = this.messageService.notify(message);
				result = new ModelAndView("redirect:list.do?folderId=" + saved.getFolder().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messageForm, "message.commit.error");
			}

		return result;
	}

	// Notification --------------------------------------------------

	@RequestMapping(value = "/create-notification", method = RequestMethod.GET)
	public ModelAndView createNotification() {
		ModelAndView result = null;
		Message message = null;

		message = this.messageService.create();
		MessageForm messageForm = messageService.construct(message);
		message.setRecipient(this.actorService.findByPrincipal());
		
		result = this.createNotificationModelAndView(messageForm);

		return result;
	}

	// Notificate a  message -----------------------------------------

	@RequestMapping(value = "/notification", method = RequestMethod.POST, params = "notify")
	public ModelAndView notification(@Valid @ModelAttribute("messageNotification") final MessageForm messageForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createNotificationModelAndView(messageForm);
		else
			try {
				Message message = messageService.reconstruct(messageForm, binding);
				final Message saved = this.messageService.notify(message);
				result = new ModelAndView("redirect:../list.do?folderId=" + saved.getFolder().getId());
			} catch (final Throwable oops) {
				result = this.createNotificationModelAndView(messageForm, "message.commit.error");
			}

		return result;
	}

	// Ancillary methods ---------------------------------------------

	protected ModelAndView createEditModelAndView(final MessageForm messageForm) {

		ModelAndView result;

		result = this.createEditModelAndView(messageForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm, final String messageCode) {

		ModelAndView result;
		Collection<Actor> actors;
		final Collection<Priority> priorities;
		Collection<Folder> folders;

		actors = this.actorService.findAll();
		actors.remove(this.actorService.findByPrincipal());

		priorities = new ArrayList<Priority>();
		priorities.add(Priority.LOW);
		priorities.add(Priority.NEUTRAL);
		priorities.add(Priority.HIGH);

		folders = this.folderService.findByPrincipal();

		result = new ModelAndView("message/edit");
		result.addObject("messageForm", messageForm);
		result.addObject("actors", actors);
		result.addObject("priorities", priorities);
		result.addObject("folders", folders);
		result.addObject("message", messageCode);
		result.addObject("actionURI", "message/admin/edit.do");

		return result;
	}

	protected ModelAndView createNotificationModelAndView(final MessageForm messageForm) {
		ModelAndView result;

		result = this.createNotificationModelAndView(messageForm, null);

		return result;
	}

	protected ModelAndView createNotificationModelAndView(final MessageForm messageForm, final String messageCode) {

		ModelAndView result = null;
		Collection<Actor> actors = null;
		Collection<Priority> priorities = null;

		actors = this.actorService.findAll();
		actors.remove(this.actorService.findByPrincipal());

		priorities = new ArrayList<Priority>();
		priorities.add(Priority.LOW);
		priorities.add(Priority.NEUTRAL);
		priorities.add(Priority.HIGH);

		result = new ModelAndView("message/notify");
		result.addObject("messageForm", messageForm);
		result.addObject("priorities", priorities);
		result.addObject("actionURI", "message/admin/notification.do");

		return result;
	}
}
