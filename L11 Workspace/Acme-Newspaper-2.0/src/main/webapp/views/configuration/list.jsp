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

<form:form action="configuration/admin/list.do"
	modelAttribute="configuration">

	<b><spring:message code="configuration.tabooWords" />:&nbsp;</b>
	<jstl:out value="${configuration.tabooWords}" />
	<br />

	<a
		href="configuration/admin/edit.do?configurationId=${configuration.id}">
		<spring:message code="configuration.editTabooWords" />
	</a>
	<br />

	<display:table pagesize="5" class="displaytag" keepStatus="true"
		name="articlesWhitTabooWords" id="row" defaultorder="descending">

		<display:column>
			<a href="article/admin/delete.do?articleId=${row.id}"><spring:message
					code="article.delete" /></a>
		</display:column>

		<display:column>
			<a href="article/display.do?articleId=${row.id }"> <spring:message
					code="article.display" /></a>
		</display:column>

		<spring:message code="configuration.article" var="titleHeader" />
		<display:column property="title" title="${titleHeader }"
			sortable="true" />

		<spring:message var="writerHeader" code="newspaper.writer" />
		<display:column title="${writerHeader}">
			<jstl:out value="${row.writer.name} ${row.writer.surname}" />
		</display:column>

	</display:table>

	<display:table pagesize="5" class="displaytag" keepStatus="true"
		name="newspapersWhitTabooWords" id="row" defaultorder="descending">

		<display:column>
			<a href="newspaper/admin/delete.do?newspaperId=${row.id}"><spring:message
					code="newspaper.delete" /></a>
		</display:column>

		<display:column>
			<a href="newspaper/display.do?newspaperId=${row.id }"> <spring:message
					code="newspaper.display" /></a>
		</display:column>

		<spring:message code="configuration.newspaper" var="titleHeader" />
		<display:column property="title" title="${titleHeader }"
			sortable="true" />

		<spring:message code="newspaper.publisher" var="publisherHeader" />
		<display:column title="${titleHeader }">
			<jstl:out value="${row.publisher.name} ${row.publisher.surname}" />
		</display:column>

	</display:table>

	<display:table pagesize="5" class="displaytag" keepStatus="true"
		name="chirpsWhitTabooWords" id="row" defaultorder="descending">

		<display:column>
			<a href="chirp/admin/delete.do?chirpId=${row.id}"><spring:message
					code="newspaper.delete" /></a>
		</display:column>

		<spring:message code="configuration.chirp" var="titleHeader" />
		<display:column property="title" title="${titleHeader }"
			sortable="true" />

		<spring:message code="configuration.user" var="userHeader" />
		<display:column title="${userHeader }" sortable="true">
			<jstl:out value="${row.user.name} ${row.user.surname }" />
		</display:column>

		<spring:message var="publicationMomentHeader"
			code="user.publicationMoment" />
		<spring:message var="formatDate" code="user.format.date" />
		<display:column property="publicationMoment"
			title="${publicationMomentHeader}" format="${formatDate}"
			sortable="true" />

		<spring:message var="descriptionHeader" code="chirp.description" />
		<display:column property="description" title="${descriptionHeader}" />

	</display:table>


</form:form>
