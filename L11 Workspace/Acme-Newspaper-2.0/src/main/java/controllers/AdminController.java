/*
 * AdminController.java
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
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;
import domain.Newspaper;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

	@Autowired
	private AdminService			adminService;

	// Constructors -----------------------------------------------------------

	public AdminController() {
		super();
	}

	// display
	// --------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("admin/display");

		Object avgSqtrUser[];
		Object avgSqtrArticlesByWriter[];
		Object avgSqtrArticlesByNewspaper[];
		Collection<Newspaper> newspapersMoreAverage;
		Collection<Newspaper> newspapersFewerAverage;
		String ratioUserCreatedNewspaper;
		String ratioUserWrittenArticle;
		Double avgFollowupsPerArticle;
		Double avgNumberOfFollowUpsPerArticleAfter1Week;
		Double avgNumberOfFollowUpsPerArticleAfter2Week;
		Object avgStddevNumberOfChirpPerUser[];
		String ratioUsersMorePostedChirpsOfAveragePerUser;
		Double newspaperWithAdsRatio;
		Double advertisementTabooWordsRatio;
		Double avgNumberOfNewspapersPerVolume;
		String ratioSubscriptionsVolumeVersusSubscriptionsNewspaper;

		avgSqtrUser = this.adminService.avgSqtrUser();
		avgSqtrArticlesByWriter = this.adminService.avgSqtrArticlesByWriter();
		avgSqtrArticlesByNewspaper = this.adminService.avgSqtrArticlesByNewspaper();
		newspapersMoreAverage = this.adminService.newspapersMoreAverage();
		newspapersFewerAverage = this.adminService.newspapersFewerAverage();
		ratioUserCreatedNewspaper = this.adminService.ratioUserCreatedNewspaper();
		ratioUserWrittenArticle = this.adminService.ratioUserWrittenArticle();
		avgFollowupsPerArticle = this.adminService.avgFollowupsPerArticle();
		avgNumberOfFollowUpsPerArticleAfter1Week = this.adminService.avgNumberOfFollowUpsPerArticleAfter1Week();
		avgNumberOfFollowUpsPerArticleAfter2Week = this.adminService.avgNumberOfFollowUpsPerArticleAfter2Week();
		avgStddevNumberOfChirpPerUser = this.adminService.avgStddevNumberOfChirpPerUser();
		ratioUsersMorePostedChirpsOfAveragePerUser = this.adminService.ratioUsersMorePostedChirpsOfAveragePerUser();
		newspaperWithAdsRatio = this.adminService.ratioNewspapersAds();
		advertisementTabooWordsRatio = this.adminService.ratioAdsTabooWords();
		avgNumberOfNewspapersPerVolume = adminService.avgNumberOfNewspapersPerVolume();
		ratioSubscriptionsVolumeVersusSubscriptionsNewspaper = adminService.ratioSubscriptionsVolumeVersusSubscriptionsNewspaper();

		result.addObject("avgSqtrUser", avgSqtrUser);
		result.addObject("avgSqtrArticlesByWriter", avgSqtrArticlesByWriter);
		result.addObject("avgSqtrArticlesByNewspaper", avgSqtrArticlesByNewspaper);
		result.addObject("newspapersMoreAverage", newspapersMoreAverage);
		result.addObject("newspapersFewerAverage", newspapersFewerAverage);
		result.addObject("ratioUserCreatedNewspaper", ratioUserCreatedNewspaper);
		result.addObject("ratioUserWrittenArticle", ratioUserWrittenArticle);
		result.addObject("avgFollowupsPerArticle", avgFollowupsPerArticle);
		result.addObject("avgNumberOfFollowUpsPerArticleAfter1Week", avgNumberOfFollowUpsPerArticleAfter1Week);
		result.addObject("avgNumberOfFollowUpsPerArticleAfter2Week", avgNumberOfFollowUpsPerArticleAfter2Week);
		result.addObject("avgStddevNumberOfChirpPerUser", avgStddevNumberOfChirpPerUser);
		result.addObject("ratioUsersMorePostedChirpsOfAveragePerUser", ratioUsersMorePostedChirpsOfAveragePerUser);
		result.addObject("newspaperWithAdsRatio", newspaperWithAdsRatio);
		result.addObject("advertisementTabooWordsRatio", advertisementTabooWordsRatio);
		result.addObject("avgNumberOfNewspapersPerVolume", avgNumberOfNewspapersPerVolume);
		result.addObject("ratioSubscriptionsVolumeVersusSubscriptionsNewspaper", ratioSubscriptionsVolumeVersusSubscriptionsNewspaper);

		return result;
	}

}
