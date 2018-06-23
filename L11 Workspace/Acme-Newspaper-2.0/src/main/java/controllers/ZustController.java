/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NewspaperService;
import services.ZustService;
import domain.Zust;

@Controller
@RequestMapping("/zust")
public class ZustController extends AbstractController {

	// Support services -------------------------------------------------------

	@Autowired
	private NewspaperService	newspaperService;
	@Autowired
	private ZustService			zustService;


	// Constructors -----------------------------------------------------------

	public ZustController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer newspaperId, @RequestParam(required = false) final String keyword) {
		ModelAndView res;
		Collection<Zust> zusts;

		zusts = this.zustService.findAll();

		res = new ModelAndView("zust/list");
		res.addObject("zust", zusts);
		res.addObject("requestURI", "zust/list.do");

		return res;
	}
}
