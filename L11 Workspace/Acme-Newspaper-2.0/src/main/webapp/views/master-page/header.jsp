<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<a href="/Acme-Newspaper-2.0"><img src="images/logo.png"
		alt="Acme-Newspaper-2.0 Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li><a href="admin/display.do"><spring:message
								code="master.page.administrator.information" /></a></li>
					<li><a href="newspaper/admin/list.do"><spring:message
								code="master.page.newspaper.list" /></a></li>
					<li><a href="article/admin/list.do"><spring:message
								code="master.page.article.list" /></a></li>
					<li><a href="chirp/admin/list.do"><spring:message
								code="master.page.chirp.list" /></a></li>
					<li><a href="advertisement/admin/listTaboo.do"><spring:message
								code="master.page.advertisement.suspicious" /></a></li>
				</ul>
			<li><a href="configuration/admin/list.do"><spring:message
						code="master.page.configuration" /></a>
		</security:authorize>

		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message code="master.page.user" /></a>
				<ul>
					<li><a href="newspaper/user/create.do"><spring:message
								code="master.page.user.newspaperCreate" /></a></li>
					<li><a href="newspaper/user/list.do"><spring:message
								code="master.page.user.listNewspapers" /></a></li>
					<li><a href="newspaper/user/list-nonPublished.do"><spring:message
								code="master.page.user.listNewspapersNonPublished" /></a></li>
					<li><a href="article/user/list.do"><spring:message
								code="master.page.user.listArticles" /></a></li>
					<li><a href="followUp/user/list.do"><spring:message
								code="master.page.user.listFollowUps" /></a></li>
					<li><a href="volume/user/list.do"><spring:message
								code="master.page.user.yourVolumes" /></a></li>
					<li><a href="user/user/list-followers.do"><spring:message
								code="master.page.user.listFollowers" /></a></li>
					<li><a href="user/user/list-followed.do"><spring:message
								code="master.page.user.listFollowed" /></a></li>
					<li><a href="chirp/user/create.do"><spring:message
								code="master.page.chirp.publish" /></a></li>
					<li><a href="chirp/user/list-timeline.do"><spring:message
								code="master.page.chirp.timeline" /></a></li>

				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message
						code="master.page.customer" /></a>
				<ul>
					<li><a href="volume/customer/list.do"><spring:message
								code="master.page.customer.subscribedVolumes" /></a></li>
					<li><a href="newspaper/customer/list.do"><spring:message
								code="master.page.customer.subscribedNewspapers" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('AGENT')">
			<li><a class="fNiv"><spring:message code="master.page.agent" /></a>
				<ul>
					<li><a href="newspaper/agent/listAdvertised.do"><spring:message
								code="master.page.agent.listAdvertised" /></a></li>
					<li><a href="newspaper/agent/listNotAdvertised.do"><spring:message
								code="master.page.agent.listNotAdvertised" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li><a href="user/register.do"><spring:message
								code="master.page.registerUser" /></a></li>
					<li><a href="agent/register.do"><spring:message
								code="master.page.registerAgent" /></a></li>
				</ul>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>

				</ul></li>

			<li><a class="fNiv"> <spring:message
						code="master.page.messages" />
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/create.do"><spring:message
								code="master.page.newmessage" /> </a></li>
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="message/admin/create-notification.do"><spring:message
									code="master.page.newnotification" /></a></li>
					</security:authorize>
					<li><a href="folder/list.do"><spring:message
								code="master.page.myfolders" /></a></li>

				</ul></li>


		</security:authorize>

		<li><a class="fNiv" href="newspaper/list.do"><spring:message
					code="master.page.availableNewspapers" /></a></li>
		<li><a class="fNiv" href="volume/list.do"><spring:message
					code="master.page.listVolumes" /></a></li>
		<li><a class="fNiv" href="user/list.do"><spring:message
					code="master.page.listUsers" /></a></li>
		<li><a class="fNiv" href="terms/list.do"><spring:message
					code="master.page.termsAndConditions" /></a></li>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

