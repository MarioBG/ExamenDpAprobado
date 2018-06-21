
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FolderService;
import domain.Folder;
import forms.FolderForm;

@Controller
@RequestMapping("/folder")
public class FolderController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private FolderService	folderService;


	// Constructors --------------------------------------------------

	public FolderController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer folderId) {

		final ModelAndView result;
		Collection<Folder> folders;

		folders = this.folderService.findByFolderId(folderId);

		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);

		return result;
	}

	// Creation ------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Folder folder;

		folder = this.folderService.create(false, null);
		FolderForm folderForm = folderService.construct(folder);

		result = this.createEditModelAndView(folderForm);

		return result;
	}

	// Edition -------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int folderId) {

		final ModelAndView result;
		Folder folder;

		folder = this.folderService.findOneToEdit(folderId);
		FolderForm folderForm = folderService.construct(folder);

		result = this.createEditModelAndView(folderForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FolderForm folderForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(folderForm);
		else
			try {
				Folder folder = folderService.reconstruct(folderForm, binding);
				this.folderService.save(folder);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folderForm, "folder.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final FolderForm folderForm, final BindingResult binding) {

		ModelAndView result;

		try {
			Folder folder = folderService.reconstruct(folderForm, binding);
			this.folderService.delete(folder);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(folderForm, "folder.commit.error");
		}

		return result;
	}

	// Ancillary methods ---------------------------------------------

	protected ModelAndView createEditModelAndView(final FolderForm folderForm) {

		ModelAndView result;

		result = this.createEditModelAndView(folderForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FolderForm folderForm, final String messageCode) {

		ModelAndView result;
		Collection<Folder> folders;

		folders = this.folderService.findByPrincipal();
		folders.remove(folderService.findOne(folderForm.getId()));

		result = new ModelAndView("folder/edit");
		result.addObject("folderForm", folderForm);
		result.addObject("folders", folders);

		return result;
	}
}
