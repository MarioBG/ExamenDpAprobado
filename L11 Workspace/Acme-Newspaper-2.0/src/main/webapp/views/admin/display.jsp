<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMIN')">


	<spring:message code="administrator.avgSqtrUserLabel" />
	<jstl:out value="${avgSqtrUserLabel}"></jstl:out>
	<table class="displaytag" name="avgSqtrUser">
		<tr>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>

			<th><spring:message code="administrator.standardDeviation"
					var="standardDeviationHeader" /> <jstl:out
					value="${standardDeviationHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${avgSqtrUser}">
				<td><jstl:out value="${datos}"></jstl:out></td>
			</jstl:forEach>
		</tr>
	</table>

	<spring:message code="administrator.avgSqtrArticlesByWriterLabel" />
	<jstl:out value="${avgSqtrUserLabel}"></jstl:out>
	<table class="displaytag" name="avgSqtrArticlesByWriter">
		<tr>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>

			<th><spring:message code="administrator.standardDeviation"
					var="standardDeviationHeader" /> <jstl:out
					value="${standardDeviationHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${avgSqtrArticlesByWriter}">
				<td><jstl:out value="${datos}"></jstl:out></td>
			</jstl:forEach>
		</tr>
	</table>

	<spring:message code="administrator.avgSqtrArticlesByNewspaperLabel" />
	<jstl:out value="${avgSqtrUserLabel}"></jstl:out>
	<table class="displaytag" name="avgSqtrArticlesByNewspaper">
		<tr>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>

			<th><spring:message code="administrator.standardDeviation"
					var="standardDeviationHeader" /> <jstl:out
					value="${standardDeviationHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${avgSqtrArticlesByNewspaper}">
				<td><jstl:out value="${datos}"></jstl:out></td>
			</jstl:forEach>
		</tr>
	</table>

	<spring:message code="administrator.newspapersMoreAverageLabel" />
	<jstl:out value="${newspapersMoreAverageLabel	}"></jstl:out>
	<table class="displaytag" name="newspapersMoreAverage">
		<tr>
			<th><spring:message code="administrator.newspapersMoreAverage"
					var="bestHeader" /> <jstl:out value="${bestHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${newspapersMoreAverage}">
				<td><jstl:out value="${datos.title}"></jstl:out></td>
			</jstl:forEach>
		</tr>

	</table>

	<spring:message code="administrator.newspapersFewerAverageLabel" />
	<jstl:out value="${newspapersFewerAverageLabel	}"></jstl:out>
	<table class="displaytag" name="newspapersFewerAverage">
		<tr>
			<th><spring:message code="administrator.newspapersFewerAverage"
					var="bestHeader" /> <jstl:out value="${bestHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${newspapersFewerAverage}">
				<td><jstl:out value="${datos.title}"></jstl:out></td>
			</jstl:forEach>
		</tr>

	</table>

	<spring:message code="administrator.ratioUserCreatedNewspaperLabel" />
	<jstl:out value="${ratioUserCreatedNewspaperLabel}"></jstl:out>
	<table class="displaytag" name="ratioUserCreatedNewspaper">
		<tr>
			<th><spring:message code="administrator.ratio" var="ratioHeader" />
				<jstl:out value="${ratioHeader}"></jstl:out></th>
		</tr>
		<tr>

			<td><jstl:out value="${ratioUserCreatedNewspaper}"></jstl:out></td>
		</tr>
	</table>

	<spring:message code="administrator.ratioUserWrittenArticleLabel" />
	<jstl:out value="${ratioUserWrittenArticleLabel}"></jstl:out>
	<table class="displaytag" name="ratioUserWrittenArticle">
		<tr>
			<th><spring:message code="administrator.ratio" var="ratioHeader" />
				<jstl:out value="${ratioHeader}"></jstl:out></th>
		</tr>
		<tr>

			<td><jstl:out value="${ratioUserWrittenArticle}"></jstl:out></td>
		</tr>
	</table>

	<spring:message code="administrator.avgFollowupsPerArticleLabel" />
	<jstl:out value="${avgFollowupsPerArticleLabel}"></jstl:out>
	<table class="displaytag" name="avgFollowupsPerArticle">
		<tr>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>
		</tr>
		<tr>

			<td><jstl:out value="${avgFollowupsPerArticle}"></jstl:out></td>
		</tr>
	</table>

	<spring:message
		code="administrator.avgNumberOfFollowUpsPerArticleAfter1WeekLabel" />
	<jstl:out value="${avgNumberOfFollowUpsPerArticleAfter1WeekLabel}"></jstl:out>
	<table class="displaytag"
		name="avgNumberOfFollowUpsPerArticleAfter1Week">
		<tr>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>
		</tr>
		<tr>

			<td><jstl:out
					value="${avgNumberOfFollowUpsPerArticleAfter1Week}"></jstl:out></td>
		</tr>
	</table>

	<spring:message
		code="administrator.avgNumberOfFollowUpsPerArticleAfter2WeekLabel" />
	<jstl:out value="${avgNumberOfFollowUpsPerArticleAfter2WeekLabel}"></jstl:out>
	<table class="displaytag"
		name="avgNumberOfFollowUpsPerArticleAfter2Week">
		<tr>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>
		</tr>
		<tr>

			<td><jstl:out
					value="${avgNumberOfFollowUpsPerArticleAfter2Week}"></jstl:out></td>
		</tr>
	</table>

	<spring:message code="administrator.avgStddevNumberOfChirpPerUserLabel" />
	<jstl:out value="${avgStddevNumberOfChirpPerUserLabel}"></jstl:out>
	<table class="displaytag" name="avgStddevNumberOfChirpPerUser">
		<tr>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>

			<th><spring:message code="administrator.standardDeviation"
					var="standardDeviationHeader" /> <jstl:out
					value="${standardDeviationHeader}"></jstl:out></th>
		</tr>
		<tr>
			<jstl:forEach var="datos" items="${avgStddevNumberOfChirpPerUser}">
				<td><jstl:out value="${datos}"></jstl:out></td>
			</jstl:forEach>
		</tr>
	</table>

	<spring:message
		code="administrator.ratioUsersMorePostedChirpsOfAveragePerUserLabel" />
	<jstl:out value="${ratioUsersMorePostedChirpsOfAveragePerUserLabel}"></jstl:out>
	<table class="displaytag"
		name="ratioUsersMorePostedChirpsOfAveragePerUser">
		<tr>
			<th><spring:message code="administrator.ratio" var="ratioHeader" />
				<jstl:out value="${ratioHeader}"></jstl:out></th>
		</tr>
		<tr>

			<td><jstl:out
					value="${ratioUsersMorePostedChirpsOfAveragePerUser}"></jstl:out></td>
		</tr>
	</table>

	<spring:message code="administrator.newspaperWithAdsRatioLabel" />
	<jstl:out value="${newspaperWithAdsRatioLabel}"></jstl:out>
	<table class="displaytag" name="newspaperWithAdsRatio">
		<tr>
			<th><spring:message code="administrator.ratio" var="ratioHeader" />
				<jstl:out value="${ratioHeader}"></jstl:out></th>
		</tr>
		<tr>

			<td><jstl:out value="${newspaperWithAdsRatio}"></jstl:out></td>
		</tr>
	</table>

	<spring:message code="administrator.advertisementTabooWordsRatioLabel" />
	<jstl:out value="${advertisementTabooWordsRatioLabel}"></jstl:out>
	<table class="displaytag" name="advertisementTabooWordsRatio">
		<tr>
			<th><spring:message code="administrator.ratio" var="ratioHeader" />
				<jstl:out value="${ratioHeader}"></jstl:out></th>
		</tr>
		<tr>

			<td><jstl:out value="${advertisementTabooWordsRatio}"></jstl:out>
			</td>
		</tr>
	</table>

	<spring:message code="administrator.avgNumberOfNewspapersPerVolume" />
	<table class="displaytag" name="avgNumberOfNewspapersPerVolume">
		<tr>
			<th><spring:message code="administrator.average"
					var="averageHeader" /> <jstl:out value="${averageHeader}"></jstl:out>
			</th>
		</tr>
		<tr>
			<td><jstl:out value="${avgNumberOfNewspapersPerVolume}"></jstl:out></td>
		</tr>
	</table>

	<spring:message code="administrator.ratioSubscriptionsVolumeVersusSubscriptionsNewspaper" />
	<table class="displaytag" name="ratioSubscriptionsVolumeVersusSubscriptionsNewspaper">
		<tr>
			<th><spring:message code="administrator.ratio" var="ratioHeader" />
				<jstl:out value="${ratioHeader}"></jstl:out></th>
		</tr>
		<tr>

			<td><jstl:out value="${ratioSubscriptionsVolumeVersusSubscriptionsNewspaper}"></jstl:out></td>
		</tr>
	</table>


</security:authorize>
