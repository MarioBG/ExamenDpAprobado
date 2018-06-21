<%--
 * list.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- displaying grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="followUps" requestURI="followUp/user/list.do" id="row">

	<display:column>
		<a href="followUp/user/display.do?followUpId=${row.id}"><spring:message code="followUp.display"/></a>
	</display:column>

	<!-- Attributes -->
	<spring:message code="followUp.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="followUp.summary" var="summaryHeader" />
	<display:column property="summary" title="${summaryHeader}" />

	<spring:message var="publicationMomentHeader"
		code="followUp.publicationMoment" />
	<spring:message var="formatDate" code="newspaper.format.date" />
	<display:column property="publicationMoment"
		title="${publicationMomentHeader}" format="${formatDate}"
		sortable="true" />

	<spring:message code="followUp.user" var="userHeader" />
	<display:column title="${userHeader}" sortable="true">
		<jstl:out value="${row.article.writer.name} ${row.article.writer.surname}"/>
	</display:column>

</display:table>

<a href="javascript:window.history.back();">&laquo; <spring:message
		code="followUp.back" /></a>