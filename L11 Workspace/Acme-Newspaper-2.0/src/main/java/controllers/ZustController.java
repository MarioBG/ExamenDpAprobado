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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Zust;
import services.ZustService;

@Controller
@RequestMapping("/zust")
public class ZustController extends AbstractController {

	// Support services -------------------------------------------------------

	@Autowired
	private ZustService zustService;

	// Constructors -----------------------------------------------------------

	public ZustController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int zustId) {
		ModelAndView res;
		Zust zust;

		zust = this.zustService.findOne(zustId);

		res = new ModelAndView("zust/display");
		res.addObject("zust", zust);

		return res;
	}
}
